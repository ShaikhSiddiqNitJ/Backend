# CRUD with Redis Cache & PostgreSQL

Spring Boot application with Redis caching and PostgreSQL database.

## Features
- ✅ CRUD operations on Products
- ✅ Redis caching for better performance
- ✅ PostgreSQL database
- ✅ Docker containers for Redis & PostgreSQL

## Tech Stack
- Spring Boot 3.5.0
- PostgreSQL 15
- Redis 7
- Docker

## Setup

### 1. Start Docker Containers
```bash
docker-compose up -d
```

### 2. Run Application
```bash
mvn spring-boot:run
```

Application runs on: `http://localhost:8082`

## API Endpoints

### Create Product
```bash
curl -X POST http://localhost:8082/api/products \
  -H "Content-Type: application/json" \
  -d '{"name": "Laptop", "description": "Gaming Laptop", "price": 1200.00, "quantity": 10}'
```

### Get All Products (Cached)
```bash
curl http://localhost:8082/api/products
```

### Get Product by ID (Cached)
```bash
curl http://localhost:8082/api/products/1
```

### Update Product
```bash
curl -X PUT http://localhost:8082/api/products/1 \
  -H "Content-Type: application/json" \
  -d '{"name": "Laptop Pro", "description": "Updated Gaming Laptop", "price": 1500.00, "quantity": 5}'
```

### Delete Product
```bash
curl -X DELETE http://localhost:8082/api/products/1
```

### Clear Cache
```bash
curl -X DELETE http://localhost:8082/api/products/cache/clear
```

## How Caching Works

- **First Request**: Data fetched from PostgreSQL → Stored in Redis
- **Subsequent Requests**: Data served from Redis (faster)
- **Update/Delete**: Cache automatically updated/removed
- **TTL**: Cache expires after 10 minutes

## Check Logs
Watch console for cache hits:
- `📦 Fetching from DATABASE` - Cache miss
- No log = Cache hit (data from Redis)
