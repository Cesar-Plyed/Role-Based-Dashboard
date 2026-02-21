package dev.BSC.cart_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import dev.BSC.cart_service.dto.ProductDTO;

@FeignClient(name = "product-service") // Nombre exacto del otro servicio en Eureka
public interface ProductClient {
    
    @GetMapping("/api/products/{id}")
    ProductDTO getProductById(@PathVariable("id") Long id);
}