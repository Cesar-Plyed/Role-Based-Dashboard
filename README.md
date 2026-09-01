# Role-Based Dashboard

This project combines an Angular frontend with a Spring Boot microservices backend for a role-based dashboard application.

## Overview

The solution is structured in two main parts:

- Frontend: Angular application in `frontend/`
- Backend: Java microservices in `backend/`
- Infrastructure: Docker Compose and PostgreSQL in the root of the repository

## Repository structure

```text
Role-Based-Dashboard/
├── backend/
│   ├── README.md
│   ├── auth-service/
│   ├── cart-service/
│   ├── discovery-service/
│   ├── gateway-service/
│   ├── product-service/
│   ├── init.sql
│   └── RoleBasedDashboard/
├── frontend/
│   ├── src/
│   ├── package.json
│   └── angular.json
├── docker-compose.yml
├── README.md
└── .gitignore
```

## Technology stack

### Frontend

- Angular 22
- TypeScript
- Reactive forms
- Angular Router
- Tailwind CSS

### Backend

- Java 17
- Spring Boot
- Spring Cloud Gateway
- Eureka Service Discovery
- PostgreSQL
- JWT authentication
- Docker Compose

## Main features

- Authentication with username/password
- Role-based access control
- Multi-role dashboard flow: ADMIN, SUPERVISOR, USER, PROVEEDOR
- Gateway routing for microservices
- Isolation between services and databases
- Local development with Docker

## Quick start

### 1. Start the backend and infrastructure

From the repository root:

```bash
docker compose up --build -d
```

This starts:

- PostgreSQL
- Discovery Service
- Gateway Service
- Auth Service
- Product Service
- Cart Service

### 2. Start the frontend

From the frontend folder:

```bash
cd frontend
npm install
npm start
```

The Angular app will run in development mode, usually on:

- http://localhost:4200

## Services and ports

- Frontend: http://localhost:4200
- Gateway: http://localhost:8080
- Auth service: http://localhost:8081
- Product service: http://localhost:8082
- Cart service: http://localhost:8083
- Eureka: http://localhost:8761
- PostgreSQL: localhost:5432

## Development notes

- The detailed backend documentation is in [backend/README.md](backend/README.md).
- The frontend documentation is in the Angular app structure under `frontend/src` and configuration files such as `angular.json` and `package.json`.
- The root README is intended as a project overview and quick-start guide.

## Common commands

### Stop the project

```bash
docker compose down
```

### Rebuild containers

```bash
docker compose up --build -d
```

### Check services

```bash
docker compose ps
docker compose logs -f
```

## Troubleshooting

- If a port is already in use, change the port mapping in `docker-compose.yml`.
- If a service does not become healthy, check the logs with `docker compose logs <service-name>`.
- If Maven scripts are not executable, run `chmod +x mvnw` inside the corresponding backend service folder.

## Environment assumptions

This project is prepared for local development and Docker-based orchestration. For production, additional security hardening, environment variables, and deployment policies should be added.
