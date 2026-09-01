package dev.BSC.cart_service.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;

import dev.BSC.cart_service.client.ProductClient;
import dev.BSC.cart_service.client.UserClient;
import dev.BSC.cart_service.dto.AddItemRequest;
import dev.BSC.cart_service.dto.ProductDTO;
import dev.BSC.cart_service.dto.UserDTO;
import dev.BSC.cart_service.entity.CartItem;
import dev.BSC.cart_service.repository.CartRepository;

@Service
public class CartService {

    private final ProductClient productClient;
    private final CartRepository cartRepository;
    private final UserClient userClient;

    public CartService(ProductClient productClient, CartRepository cartRepository, UserClient userClient) {
        this.productClient = productClient;
        this.cartRepository = cartRepository;
        this.userClient = userClient;
    }

    public List<CartItem> getCartByUserId(Long userId) {
        return cartRepository.findByUserId(userId);
    }

    public CartItem addItem(Long userId, AddItemRequest request) {
        // 1. Validar que el usuario existe (comunicación interna con auth-service)
        UserDTO user = userClient.getUserById(userId);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario no existe");
        }

        // 2. Validar que el producto existe y traer datos reales (precio, nombre)
        ProductDTO product = productClient.getProductById(request.productId());
        if (product == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no existe");
        }

        double price = product.getPrice() != null ? product.getPrice() : 0.0;

        // 3. Crear y guardar el item
        CartItem item = new CartItem();
        item.setUserId(userId);
        item.setProductId(request.productId());
        item.setProductName(product.getName());
        item.setPrice(price);
        item.setQuantity(request.quantity());
        item.setTotal(price * request.quantity());

        return cartRepository.save(item);
    }

    public void removeItem(Long userId, Long itemId) {
        CartItem item = cartRepository.findById(itemId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Item no existe"));

        // Seguridad: que el item sea realmente del usuario que pide borrarlo
        if (!item.getUserId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "El item no pertenece a este usuario");
        }

        cartRepository.delete(item);
    }
}