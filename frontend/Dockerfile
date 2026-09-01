# ============================================================================
# Etapa 1: build
# Compila la app Angular (por defecto usa la config "production" ->
# environment.prod.ts, definida en angular.json via fileReplacements).
# ============================================================================
FROM node:20-alpine AS build
WORKDIR /app

COPY package*.json ./
RUN npm ci

COPY . .
RUN npm run build

# ============================================================================
# Etapa 2: runtime (SSR con Node)
# ============================================================================
FROM node:20-alpine AS runtime
WORKDIR /app

ENV NODE_ENV=production
ENV PORT=4000


COPY package*.json ./
RUN npm ci --omit=dev

COPY --from=build /app/dist ./dist

EXPOSE 4000

# El nombre de la carpeta dentro de dist/ depende del "name" del proyecto
CMD ["sh", "-c", "node dist/*/server/server.mjs"]
