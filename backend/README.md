# Backend documentation

This folder contains the backend side of the Role-Based Dashboard project. The backend is a set of Spring Boot microservices coordinated through Eureka and a gateway.

## Project purpose

The backend provides:

- Authentication and user management
- Role-based authorization
- Product catalog operations
- Cart operations
- Service discovery and routing
- Dockerized local development environment

## Service structure

```text
backend/
├── auth-service/
├── cart-service/
├── discovery-service/
├── gateway-service/
├── product-service/
├── RoleBasedDashboard/
├── init.sql
├── README.md
├── docker-compose.yml
└── .gitignore
```

## Services

### auth-service

Responsible for authentication, registration, token creation and user validation.

Main responsibilities:

- user registration
- login
- JWT generation
- password validation or hashing
- user-role handling

Default port:

- 8081

### product-service

Responsible for product management and catalog access.

Main responsibilities:

- CRUD of products
- product listing
- product metadata handling
- inventory-related operations

Default port:

- 8082

### cart-service

Responsible for shopping cart logic.

Main responsibilities:

- add, remove and list cart items
- user cart management
- communication with other services when needed

Default port:

- 8083

### gateway-service

Acts as the entry point for the frontend and all external requests.

Main responsibilities:

- route requests to the correct microservice
- expose a single API endpoint
- centralize access rules
- simplify frontend integration

Default port:

- 8080

### discovery-service

Runs Spring Cloud Netflix Eureka.

Main responsibilities:

- register all services
- enable service discovery
- allow internal communication between services

Default port:

- 8761

## Infrastructure

The project uses PostgreSQL and Docker Compose to run the application locally.

### Databases

The `docker-compose.yml` file at the repository root configures PostgreSQL and each service with its own database or environment values.

Databases used in the project include:

- auth_db
- product_db
- cart_db

## Startup commands

From the root of the repository:

```bash
docker compose up --build -d
```

To inspect the running containers:

```bash
docker compose ps
```

To check logs:

```bash
docker compose logs -f
```

To stop the services:

```bash
docker compose down
```

## Local service execution

Each service can also be run individually using Maven.

Example:

```bash
cd backend/auth-service
./mvnw clean package
java -jar target/auth-service-0.0.1-SNAPSHOT.jar
```

If permission errors happen with Maven wrapper scripts, run:

```bash
chmod +x mvnw
```

## API access examples

### Register user

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"Pass123!"}'
```

### Login

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user1","password":"Pass123!"}'
```

### Product list

```bash
curl http://localhost:8080/api/products
```

### Health check

```bash
curl http://localhost:8080/actuator/health
```

## Typical architecture

```text
Browser / Frontend
        |
        v
Gateway Service (8080)
        |
        +--> Auth Service (8081)
        +--> Product Service (8082)
        +--> Cart Service (8083)
        |
        v
Discovery Service (8761)
        |
        v
PostgreSQL (5432)
```

## Security considerations

The backend is prepared for local and development usage, but for production it should include:

- stronger environment variable management
- HTTPS/TLS configuration
- secret management
- CORS restrictions
- rate limiting
- request validation and logging policies

## Troubleshooting

### Service not starting

Check the logs:

```bash
docker compose logs <service-name>
```

### Database connection errors

Verify that PostgreSQL is running and the service environment variables match the Docker database names.

### Maven wrapper permission errors

Run:

```bash
chmod +x mvnw
```

### Port conflict

If a port is already in use, change the port mapping in the root `docker-compose.yml` file.

## Notes

This backend is designed to work as part of a full-stack application together with the Angular frontend in the sibling `frontend/` folder. The root repository README should be used as the main project overview, while this file is the backend-focused reference.
