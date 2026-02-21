package dev.BSC.cart_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * CartItem - Entidad que representa un item en el carrito de compras
 * 
 * Propiedades:
 * - id: Identificador único del item
 * - productId: Referencia al producto (almacenado en otro servicio)
 * - productName: Nombre del producto (desnormalizado para información)
 * - price: Precio unitario del producto
 * - quantity: Cantidad de unidades en el carrito
 * - total: Precio total (price * quantity)
 * 
 * Anotaciones:
 * - @Entity: Indica que es una entidad JPA
 * - @Table: Define el nombre de la tabla en la BD
 * - @Data: Lombok genera getters, setters, toString, equals, hashCode
 * - @NoArgsConstructor: Genera constructor sin argumentos
 * - @AllArgsConstructor: Genera constructor con todos los argumentos
 */
@Entity
@Table(name = "cart_items")
public class CartItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    // ID del producto (referencia a product-service)
    private Long productId;
    
    // Datos desnormalizados del producto para facilitar consultas
    private String productName;
    private Double price;      // Precio unitario
    private Integer quantity;  // Cantidad en el carrito
    private Double total;      // Total = price * quantity
    
    // Constructores
    public CartItem() {
    }
    
    public CartItem(Long id, Long productId, String productName, Double price, Integer quantity, Double total) {
        this.id = id;
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantity = quantity;
        this.total = total;
    }
    
    // Getters y Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Long getProductId() {
        return productId;
    }
    
    public void setProductId(Long productId) {
        this.productId = productId;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    public Double getPrice() {
        return price;
    }
    
    public void setPrice(Double price) {
        this.price = price;
    }
    
    public Integer getQuantity() {
        return quantity;
    }
    
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
    
    public Double getTotal() {
        return total;
    }
    
    public void setTotal(Double total) {
        this.total = total;
    }
    
    @Override
    public String toString() {
        return "CartItem{" +
                "id=" + id +
                ", productId=" + productId +
                ", productName='" + productName + '\'' +
                ", price=" + price +
                ", quantity=" + quantity +
                ", total=" + total +
                '}';
    }
}
