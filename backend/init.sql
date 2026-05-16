-- Crear bases de datos (si no existen)
CREATE DATABASE auth_db;
CREATE DATABASE product_db;
CREATE DATABASE order_db;
CREATE DATABASE cart_db;

-- --------------------------------------------------
-- auth_db: usuarios
-- --------------------------------------------------
\connect auth_db

CREATE TABLE IF NOT EXISTS users (
	id SERIAL PRIMARY KEY,
	username VARCHAR(100) NOT NULL UNIQUE,
	password VARCHAR(255) NOT NULL,
	role VARCHAR(50) NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Datos de ejemplo: usuarios
INSERT INTO users (username, password, role) VALUES
	('admin', 'changeme', 'ADMIN'),
	('alice', 'password1', 'USER'),
	('bob', 'password2', 'USER')
ON CONFLICT (username) DO NOTHING;

-- --------------------------------------------------
-- product_db: productos
-- --------------------------------------------------
\connect product_db

CREATE TABLE IF NOT EXISTS products (
	id SERIAL PRIMARY KEY,
	name VARCHAR(200) NOT NULL,
	description TEXT,
	price NUMERIC(10,2) NOT NULL,
	stock INTEGER NOT NULL DEFAULT 0,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Datos de ejemplo: productos
INSERT INTO products (name, description, price, stock) VALUES
	('Camiseta', 'Camiseta algodón talla M', 19.99, 100),
	('Auriculares', 'Auriculares inalámbricos', 79.90, 50),
	('Mouse', 'Mouse óptico ergonómico', 29.50, 150),
	('Teclado', 'Teclado mecánico compacto', 89.00, 30)
ON CONFLICT (id) DO NOTHING;

-- --------------------------------------------------
-- order_db: órdenes y items de orden
-- Nota: no se añaden FOREIGN KEY entre bases de datos.
-- Se usan identificadores numéricos que coinciden con los insertados en otras DB.
-- --------------------------------------------------
\connect order_db

CREATE TABLE IF NOT EXISTS orders (
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	status VARCHAR(50) NOT NULL DEFAULT 'CREATED',
	total NUMERIC(12,2) NOT NULL DEFAULT 0,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS order_items (
	id SERIAL PRIMARY KEY,
	order_id INTEGER NOT NULL,
	product_id INTEGER NOT NULL,
	product_name VARCHAR(200),
	price NUMERIC(10,2) NOT NULL,
	quantity INTEGER NOT NULL DEFAULT 1,
	total NUMERIC(12,2) NOT NULL,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Datos de ejemplo: órdenes
-- Asumimos que en `auth_db.users` los ids son: 1=admin,2=alice,3=bob
INSERT INTO orders (user_id, status, total) VALUES
	(2, 'COMPLETED', 0), -- order_id = 1
	(2, 'CREATED', 0),   -- order_id = 2
	(3, 'COMPLETED', 0)  -- order_id = 3
ON CONFLICT (id) DO NOTHING;

-- Items para las órdenes (varios productos por orden)
-- order_id 1 (alice) -> Camiseta + Auriculares
INSERT INTO order_items (order_id, product_id, product_name, price, quantity, total) VALUES
	(1, 1, 'Camiseta', 19.99, 2, 39.98),
	(1, 2, 'Auriculares', 79.90, 1, 79.90),
	-- order_id 2 (alice) -> Mouse + Teclado
	(2, 3, 'Mouse', 29.50, 1, 29.50),
	(2, 4, 'Teclado', 89.00, 1, 89.00),
	-- order_id 3 (bob) -> Camiseta + Mouse
	(3, 1, 'Camiseta', 19.99, 1, 19.99),
	(3, 3, 'Mouse', 29.50, 1, 29.50)
ON CONFLICT (id) DO NOTHING;

-- Actualizar totales de órdenes basados en order_items
UPDATE orders o
SET total = sub.sum_total
FROM (
	SELECT order_id, SUM(total) AS sum_total
	FROM order_items
	GROUP BY order_id
) AS sub
WHERE o.id = sub.order_id;

-- --------------------------------------------------
-- cart_db: carrito y items de carrito
-- Un carrito puede pertenecer a un usuario (user_id), un usuario puede tener 0 o 1 carrito.
-- --------------------------------------------------
\connect cart_db

CREATE TABLE IF NOT EXISTS carts (
	id SERIAL PRIMARY KEY,
	user_id INTEGER UNIQUE,
	created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE IF NOT EXISTS cart_items (
	id SERIAL PRIMARY KEY,
	cart_id INTEGER NOT NULL,
	product_id INTEGER NOT NULL,
	product_name VARCHAR(200),
	price NUMERIC(10,2) NOT NULL,
	quantity INTEGER NOT NULL DEFAULT 1,
	total NUMERIC(12,2) NOT NULL,
	added_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

-- Crear un carrito para el usuario 'alice' (user_id = 2)
INSERT INTO carts (user_id) VALUES (2) ON CONFLICT (id) DO NOTHING;

-- Obtener el id del carrito insertado
DO $$
DECLARE cid INTEGER;
BEGIN
	SELECT id INTO cid FROM carts WHERE user_id = 2 LIMIT 1;
	IF cid IS NOT NULL THEN
		-- Añadir items al carrito: 2 x Auriculares, 1 x Teclado
		INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, total)
			VALUES (cid, 2, 'Auriculares', 79.90, 2, 159.80)
		ON CONFLICT (id) DO NOTHING;

		INSERT INTO cart_items (cart_id, product_id, product_name, price, quantity, total)
			VALUES (cid, 4, 'Teclado', 89.00, 1, 89.00)
		ON CONFLICT (id) DO NOTHING;
	END IF;
END$$;

-- Fin del script de inicialización
