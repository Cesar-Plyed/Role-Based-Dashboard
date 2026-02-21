package dev.BSC.cart_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import dev.BSC.cart_service.entity.CartItem;
import java.util.List;
import java.util.Optional;

/**
 * CartRepository - Repositorio de acceso a datos para la entidad CartItem
 * 
 * Responsabilidades:
 * - Gestionar operaciones CRUD en la tabla de items del carrito
 * - Proporcionar métodos de consulta personalizados
 * - Comunicarse con la base de datos PostgreSQL
 * 
 * Métodos heredados:
 * - save: Guarda o actualiza un item del carrito
 * - findById: Busca un item por su ID
 * - findAll: Obtiene todos los items del carrito
 * - delete: Elimina un item del carrito
 * - deleteAll: Limpia el carrito
 */
@Repository
public interface CartRepository extends JpaRepository<CartItem, Long> {
    
    /**
     * Busca un item del carrito por el ID del producto
     * @param productId ID del producto
     * @return Optional con el item si existe
     */
    Optional<CartItem> findByProductId(Long productId);
    
    /**
     * Obtiene todos los items relacionados con un producto
     * @param productId ID del producto
     * @return Lista de items con ese producto
     */
    List<CartItem> findByProductIdIn(List<Long> productIds);
}
