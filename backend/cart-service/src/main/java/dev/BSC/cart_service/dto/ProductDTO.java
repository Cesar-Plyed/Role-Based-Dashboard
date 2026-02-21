package dev.BSC.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * ProductDTO - Data Transfer Object para productos
 * 
 * Se utiliza para transferir datos entre servicios
 * mediante Feign (comunicación inter-servicios)
 * 
 * Contiene:
 * - id: Identificador del producto
 * - name: Nombre del producto
 * - price: Precio del producto
 */
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private Long id;
    private String name;
    private Double price;

    // Getters
    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Double getPrice() {
        return price;
    }

    // Setters
    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "ProductDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                '}';
    }
}
