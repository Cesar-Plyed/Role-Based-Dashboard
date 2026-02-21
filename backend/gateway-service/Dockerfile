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
LABEL description="API Gateway - Spring Cloud Gateway"
LABEL version="0.0.1-SNAPSHOT"

# Copiar JAR compilado del builder
COPY --from=builder /build/target/*.jar app.jar

# Health check - Verifica que el gateway esté disponible
HEALTHCHECK --interval=30s --timeout=10s --start-period=10s --retries=3 \
  CMD wget --no-verbose --tries=1 --spider http://localhost:8080/actuator/health || exit 1

# Puerto
EXPOSE 8080

# Variables de entorno
ENV JAVA_OPTS="-Xmx512m -Xms256m"
ENV SPRING_PROFILES_ACTIVE="docker"
ENV EUREKA_CLIENT_SERVICEURL_DEFAULTZONE="http://discovery-service:8761/eureka/"

# Ejecutar aplicación
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar app.jar"]
