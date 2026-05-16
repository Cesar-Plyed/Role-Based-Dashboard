# Role-Based Dashboard - Backend

**Estado:** **PRODUCTION READY**  
**Última actualización:** 5 de Febrero, 2026  
**Java Version:** 25 LTS  
**Spring Boot:** 4.0.2  
**Docker:** Multi-stage Alpine optimizado

---

## Descripción

Aplicación de **microservicios completa** con:

- 5 servicios backend Java 25
- Frontend React integrado (../frontend)
- Orquestación con Docker Compose
- Autenticación JWT
- Service Discovery (Eureka)
- PostgreSQL con 3 databases
- **0 errores de compilación**

---

## Características

### Backend (Java 25)

- **Auth Service** (:8081) - JWT + BCrypt
- **Product Service** (:8082) - Catálogo
- **Cart Service** (:8083) - Carrito
- **Gateway Service** (:8080) - Router API
- **Discovery Service** (:8761) - Eureka Registry

### DevOps

- Docker multi-stage optimizado (~200MB cada servicio)
- docker-compose.yml con 7 servicios
- Health checks en todos
- Networking automático
- Persistencia de datos BD

### Frontend (React)

- Node.js 25-alpine builder
- Nginx alpine runtime
- Integrado en docker-compose

### Documentación

- 20+ guías y referencias
- Troubleshooting avanzado
- Verificación checklist
- Diagramas de arquitectura

---

## Inicio Rápido

### Ejecutar (30 segundos)

```bash
cd backend
docker-compose up -d
```

### Esperar (5-8 minutos)

```bash
docker-compose ps  # Verificar que todos estén UP/healthy
```

### Acceder

```
Frontend:   http://localhost:3000
Gateway:    http://localhost:8080
Auth:       http://localhost:8081
Product:    http://localhost:8082
Cart:       http://localhost:8083
Discovery:  http://localhost:8761
```

---

## Testing Rápido

### Registrar usuario

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"Pass123!"}'
```

### Login (obtener JWT)

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"Pass123!"}'
```

### Obtener productos

```bash
curl http://localhost:8080/api/products
```

### Health check

```bash
curl http://localhost:8080/actuator/health
```

---

## Estructura del Proyecto

```
backend/
├── docker-compose.yml              ← Configuración principal (ACTUALIZADO)
├── docker-compose.prod.yml         ← Para producción
├── README.md                        ← Este archivo
├── src/                            ← Código fuente
│
├── ... (Documentación movida a: ../../AI-DIMP/Role-Based-Dashboard/Backend)
│
├── auth-service/                   ← Autenticación
│   ├── Dockerfile                  Java 25 Alpine
│   ├── .dockerignore               Optimizado
│   ├── pom.xml                     Actualizado
│   └── src/main/java/...
│
├── product-service/                ← Productos
│   ├── Dockerfile                  Java 25 Alpine
│   ├── .dockerignore               Optimizado
│   ├── pom.xml                     Actualizado
│   └── src/main/java/...
│
├── cart-service/                   ← Carrito
│   ├── Dockerfile                  Java 25 Alpine
│   ├── .dockerignore               Optimizado
│   ├── pom.xml                     Actualizado
│   └── src/main/java/...
│
├── gateway-service/                ← API Gateway
│   ├── Dockerfile                  Java 25 Alpine
│   ├── .dockerignore               Optimizado
│   └── src/main/java/...
│
├── discovery-service/              ← Eureka Registry
│   ├── Dockerfile                  Java 25 Alpine
│   ├── .dockerignore               Optimizado
│   └── src/main/java/...
│
└── init.sql                        ← (Opcional) SQL inicial
```

---

## Arquitectura

```
┌─────────────────────────────────────────────────────────┐
│                   USUARIO (React)                       │
│              http://localhost:3000                      │
└───────────────────────┬─────────────────────────────────┘
                        │
                        ▼
        ┌───────────────────────────────┐
        │    API GATEWAY (:8080)        │
        │  Spring Cloud Gateway         │
        │  + CORS + Routing             │
        └────┬──────────────┬───────────┘
             │              │
      /api/auth      /api/products
      /api/cart            │
             │              │
             ▼              ▼
        ┌─────────┐  ┌──────────────┐
        │  AUTH   │  │  PRODUCT     │
        │ :8081   │  │  :8082       │
        └────┬────┘  └──────┬───────┘
             │               │
             │        Feign Client
             │               │
        ┌────┴───────────────┤
        │                    ▼
        │            ┌─────────┐
        │            │  CART   │
        │            │ :8083   │
        │            └────┬────┘
        │                 │
        └────────┬────────┘
                 │
        ┌────────▼──────────────┐
        │   DISCOVERY (Eureka)  │
        │      :8761            │
        │   Service Registry    │
        └────────┬──────────────┘
                 │
        ┌────────▼──────────────┐
        │  PostgreSQL 15        │
        │  :5432                │
        │  ├─ auth_db           │
        │  ├─ product_db        │
        │  └─ cart_db           │
        └───────────────────────┘
```

---