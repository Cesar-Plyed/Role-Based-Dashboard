package dev.BSC.auth_service.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * User - Entidad que representa un usuario del sistema
 * 
 * Propiedades:
 * - id: Identificador único del usuario
 * - username: Nombre de usuario (único)
 * - password: Contraseña encriptada con BCrypt
 * - role: Rol del usuario (ADMIN, USER, SUPERVISOR)
 * 
 * Anotaciones:
 * - @Entity: Indica que es una entidad JPA mapeada a una tabla
 * - @Data: Lombok genera getters, setters, toString, equals, hashCode
 * - @NoArgsConstructor: Genera constructor sin argumentos
 * - @AllArgsConstructor: Genera constructor con todos los argumentos
 * - @Table: Define el nombre de la tabla en la base de datos
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password; // Se guarda encriptada
    private String role;     // Ej: "ADMIN", "USER", "SUPERVISOR"

    // Explicit getters and setters (in case Lombok annotation processing fails)
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
