# 🚀 Role-Based Dashboard - Backend

**Estado:** ✅ **PRODUCTION READY**  
**Última actualización:** 5 de Febrero, 2026  
**Java Version:** 25 LTS  
**Spring Boot:** 4.0.2

# Role-Based Dashboard — Backend

Resumen profesional del backend del proyecto Role-Based Dashboard. Este repositorio contiene los microservicios Java, la orquestación por Docker Compose y los scripts de inicialización de base de datos.

## Contenido y propósito

- Servicios backend desarrollados con Spring Boot.
- Orquestación y despliegue mediante Docker Compose.
- Bases de datos gestionadas con PostgreSQL y script de inicialización en `init.sql`.

Este README describe la estructura del repositorio, requisitos, pasos para construir y ejecutar el entorno, y comandos comunes de diagnóstico.

## Estructura principal

- `auth-service/` — Servicio de autenticación (puerto 8081)
- `product-service/` — Servicio de catálogo (puerto 8082)
- `cart-service/` — Servicio de carrito (puerto 8083)
- `gateway-service/` — API Gateway (puerto 8080)
- `discovery-service/` — Eureka Service Registry (puerto 8761)
- `init.sql` — Script opcional para inicializar bases de datos
- `docker-compose.yml` — Orquestación local de los servicios

## Requisitos

- Docker 20.x o superior
- Docker Compose 1.29+ (o la versión incluida en Docker Desktop)
- JDK 17 (solo si se compila localmente fuera de Docker)

Recomendación: ejecutar la plataforma mediante `docker-compose` para evitar diferencias entre entornos.

## Construcción y ejecución (Docker)

Desde la raíz del repositorio:

```bash
docker-compose build --no-cache
docker-compose up -d
```

Verificar estado:

```bash
docker-compose ps
docker-compose logs -f
```

Puntos finales principales:

- Frontend: http://localhost:3000
- Gateway: http://localhost:8080
- Auth: http://localhost:8081
- Product: http://localhost:8082
- Cart: http://localhost:8083
- Discovery (Eureka): http://localhost:8761

## Ejecutar un servicio localmente (opcional)

Si desea ejecutar un servicio de forma individual fuera de Docker, desde la carpeta del servicio:

```bash
./mvnw clean package
java -jar target/<nombre-del-servicio>.jar
```

Nota: el script `mvnw` puede no ser ejecutable después de copiar archivos entre sistemas. En los Dockerfiles del proyecto se incluye `chmod +x mvnw` antes de invocarlo para garantizar permisos adecuados.

## Inicialización de la base de datos

El archivo `init.sql` contiene la creación de esquemas y datos mínimos para desarrollo. Cuando use `docker-compose`, revise las variables de entorno en `docker-compose.yml` para confirmar nombres de base de datos y credenciales.

## Comandos útiles

- Iniciar todo: `docker-compose up -d`
- Reconstruir: `docker-compose up -d --build`
- Detener: `docker-compose down`
- Eliminar volúmenes y reconstruir: `docker-compose down -v && docker-compose up -d --build`
- Ver logs: `docker-compose logs -f`

## Notas sobre permisos y problemas comunes

- Error durante `./mvnw`: si aparece `permission denied` o exit code 126, asegúrese de que el archivo `mvnw` sea ejecutable. En los Dockerfiles se aplica `chmod +x mvnw` para resolverlo.
- Puertos en uso: cierre procesos locales que ocupen los puertos configurados o ajuste los puertos en `docker-compose.yml`.
- Servicios unhealthy: revisar logs del servicio `docker-compose logs <service>`.

## Diagnóstico rápido

Comandos de verificación:

```bash
docker-compose ps
curl http://localhost:8080/actuator/health
curl http://localhost:8761/eureka/apps
```

## Contribuciones y próximos pasos

Para contribuir o ejecutar pruebas locales:

1. Abrir un issue describiendo el cambio propuesto.
2. Crear una rama con la convención `feature/<descripción>`.
3. Enviar pull request con pruebas y descripción clara.

Próximos elementos planificados:

- Hardening de seguridad para producción (TLS, gestión de secretos)
- Instrumentación y métricas (Prometheus/Grafana)
- Pipeline CI/CD para build y pruebas

## Contacto

Para consultas técnicas, abra un issue en este repositorio indicando el servicio y los pasos para reproducir el problema.

## 💾 Tecnologías Utilizadas

### Backend

```java
Java:                25 LTS (JavaSE-25 LTS Terminal)
Spring Boot:         4.0.2
Spring Cloud:        2025.1.0
Spring Data JPA:     Latest
Spring Security:     Latest
JWT:                 JJWT 0.12.3 (modern API)
Build tool:          Maven 3.9+
```

### DevOps

```java
Docker:              Latest
Docker Compose:      3.8+
Base images:         eclipse-temurin:25 (Alpine)
PostgreSQL:          15-alpine
Node.js:             25-alpine
Nginx:               alpine
```

### Frontend

```javascript
Framework:           Angular
Language:            TypeScript
Build:               Node.js 25-alpine
Runtime:             Nginx alpine
```
