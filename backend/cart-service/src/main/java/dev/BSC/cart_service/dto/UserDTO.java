package dev.BSC.cart_service.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
        private Long id;
        private String username;
        private String role;

        public Long getId() {
            return id;
        }
        
        public void setId(Long id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        @Override
        public String toString() {
                return "UserDTO [id=" + id + ", username=" + username + ", role=" + role + "]";
        }
}
