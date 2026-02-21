# Build Stage - Compilar con Maven
FROM eclipse-temurin:17-jdk-alpine AS builder

WORKDIR /build

# Copiar archivos de configuración Maven
COPY pom.xml .
COPY mvnw .
COPY .mvn .mvn

# Copiar código fuente
COPY src ./src

# Compilar aplicación
RUN ./mvnw clean package -DskipTests

# Runtime Stage - Ejecutar aplicación
FROM eclipse-temurin:17-jre-alpine

WORKDIR /app

# Metadatos
LABEL description="Cart Service - Gestión de Carrito de Compras"
LABEL version="0.0.1-SNAPSHOT"

# Copiar JAR compilado del builder
COPY --from=builder /build/target/*.jar app.jar

# Health check - Verifica que el servicio de carrito esté disponible
HEALTHCHECK --interval=30s --timeout=10s --start-period=15s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8083/actuator/health || exit 1

# Puerto
EXPOSE 8083

# Variables de entorno (pueden sobrescribirse)
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE="docker"

# Variables de conexión a BD (valores por defecto, se sobrescriben por docker-compose)
ENV SPRING_DATASOURCE_URL="jdbc:postgresql://postgres:5432/cart_db"
ENV SPRING_DATASOURCE_USERNAME="root"
ENV SPRING_DATASOURCE_PASSWORD="root"
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://discovery-service:8761/eureka/"

# Ejecutar aplicación
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
