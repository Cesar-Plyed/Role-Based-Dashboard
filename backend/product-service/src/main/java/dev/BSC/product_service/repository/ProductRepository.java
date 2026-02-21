package dev.BSC.product_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.BSC.product_service.entity.Product;
import java.util.List;

/**
 * ProductRepository - Repositorio de acceso a datos para la entidad Product
 * 
 * Responsabilidades:
 * - Gestionar operaciones CRUD en la tabla de productos
 * - Proporcionar métodos de consulta personalizados
 * - Comunicarse con la base de datos PostgreSQL
 * 
 * Métodos heredados:
 * - save: Guarda o actualiza un producto
 * - findById: Busca un producto por su ID
 * - findAll: Obtiene todos los productos
 * - delete: Elimina un producto
 * - count: Cuenta el número de productos
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * Busca productos por nombre (búsqueda parcial)
     * @param name Nombre del producto a buscar
     * @return Lista de productos que coinciden
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * Obtiene productos con stock disponible
     * @return Lista de productos con stock > 0
     */
    List<Product> findByStockGreaterThan(int stock);
}
