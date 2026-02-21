package dev.BSC.product_service.entity;

import jakarta.persistence.Id;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Product - Entidad que representa un producto en el catálogo
 * 
 * Propiedades:
 * - id: Identificador único del producto
 * - name: Nombre del producto
 * - price: Precio del producto
 * - stock: Cantidad disponible en inventario
 * 
 * Anotaciones:
 * - @Entity: Indica que es una entidad JPA mapeada a una tabla
 * - @Data: Lombok genera getters, setters, toString, equals, hashCode
 * - @NoArgsConstructor: Genera constructor sin argumentos
 * - @AllArgsConstructor: Genera constructor con todos los argumentos
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Double price;
    private Integer stock;
}