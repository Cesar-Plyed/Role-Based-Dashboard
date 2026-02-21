package dev.BSC.cart_service.service;

import org.springframework.stereotype.Service;

import dev.BSC.cart_service.client.ProductClient;
import dev.BSC.cart_service.dto.ProductDTO;
import dev.BSC.cart_service.entity.CartItem;
import dev.BSC.cart_service.repository.CartRepository;

@Service
public class CartService {

    private final ProductClient productClient; // Inyectamos el cliente Feign
    private final CartRepository cartRepository;
    
    public CartService(ProductClient productClient, CartRepository cartRepository) {
        this.productClient = productClient;
        this.cartRepository = cartRepository;
    }

    public CartItem addToCart(Long productId, Integer quantity) {
        // 1. COMUNICACIÓN: Preguntamos al otro servicio si existe el producto
        ProductDTO product = productClient.getProductById(productId);
        
        if (product == null) throw new RuntimeException("Producto no existe");

        // 2. Creamos el item del carrito con los datos reales (precio)
        CartItem item = new CartItem();
        item.setProductId(productId);
        item.setProductName(product.getName());
        item.setPrice(product.getPrice() != null ? product.getPrice() : 0.0);
        item.setQuantity(quantity);
        item.setTotal((product.getPrice() != null ? product.getPrice() : 0.0) * quantity);

        return cartRepository.save(item);
    }
}