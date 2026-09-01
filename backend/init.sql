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

-- Datos de ejemplo: usuarios (passwords hasheados con BCrypt)
-- admin -> changeme | alice -> password1 | bob -> password2
INSERT INTO users (username, password, role) VALUES
        ('admin', '$2b$10$b1.8a.lkt5LOD/sXXSFtLe0s/frEi4NZeZi3gFbHh6BRf3KIpkAvC', 'ADMIN'),
        ('alice', '$2b$10$rZoBjGTKsOgoCnTG1dcbb.z3rXISkqS243ZuVVh4PAMh/4EmDBuji', 'USER'),
        ('bob',   '$2b$10$LuRzBwtIVuHvxPDRs0FZAeyBoTjpdCOSo7.s1Z/TLS7IEjooHOAiS', 'USER')
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
-- cart_db: items de carrito
-- --------------------------------------------------
\connect cart_db

CREATE TABLE IF NOT EXISTS cart_items (
	id SERIAL PRIMARY KEY,
	user_id INTEGER NOT NULL,
	product_id INTEGER NOT NULL,
	product_name VARCHAR(200),
	price NUMERIC(10,2) NOT NULL,
	quantity INTEGER NOT NULL DEFAULT 1,
	total NUMERIC(12,2) NOT NULL
);

-- Índice para acelerar findByUserId()
CREATE INDEX IF NOT EXISTS idx_cart_items_user_id ON cart_items (user_id);

-- Datos de ejemplo: carrito de alice (user_id = 2)
-- 2 x Auriculares, 1 x Teclado
INSERT INTO cart_items (user_id, product_id, product_name, price, quantity, total) VALUES
	(2, 2, 'Auriculares', 79.90, 2, 159.80),
	(2, 4, 'Teclado', 89.00, 1, 89.00)
ON CONFLICT (id) DO NOTHING;