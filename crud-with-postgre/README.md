# CRUD with PostgreSQL

Spring Boot REST API with PostgreSQL database for Todo management.

## Features
- Create, Read, Update, Delete (CRUD) operations
- PostgreSQL database
- Docker containerized database
- RESTful API endpoints

## Tech Stack
- Java 17
- Spring Boot 3.5.0
- Spring Data JPA
- PostgreSQL
- Docker & Docker Compose
- Maven

## Setup

1. Start PostgreSQL:
```bash
docker-compose up -d
```

2. Run application:
```bash
mvn spring-boot:run
```

## API Endpoints

- `POST /api/todos` - Create todo
- `GET /api/todos` - Get all todos
- `GET /api/todos/{id}` - Get todo by ID
- `PUT /api/todos/{id}` - Update todo
- `DELETE /api/todos/{id}` - Delete todo

## Example Usage

Create:
```bash
curl -X POST http://localhost:8081/api/todos -H "Content-Type: application/json" -d '{"title": "Test", "completed": false}'
```

Get All:
```bash
curl http://localhost:8081/api/todos
```

Update:
```bash
curl -X PUT http://localhost:8081/api/todos/{id} -H "Content-Type: application/json" -d '{"title": "Updated", "completed": true}'
```

Delete:
```bash
curl -X DELETE http://localhost:8081/api/todos/{id}
```
