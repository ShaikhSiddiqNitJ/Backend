# 📨 CRUD with Kafka & PostgreSQL

Event-driven Spring Boot application with Apache Kafka and PostgreSQL.

## 🎯 Features
- ✅ CRUD operations on Orders
- ✅ Event-driven architecture with Kafka
- ✅ Async processing (Email, SMS, Analytics)
- ✅ PostgreSQL database
- ✅ Docker containers for Kafka & PostgreSQL

## 🏗️ Architecture

```
User → REST API → Order Service → PostgreSQL
                       ↓
                   Kafka Topic
                       ↓
        ┌──────────────┼──────────────┐
        ↓              ↓               ↓
   Email Service   SMS Service   Analytics Service
```

## 🛠️ Tech Stack
- Spring Boot 3.2.0
- Apache Kafka
- PostgreSQL 15
- Docker

## 🚀 Setup

### 1. Start Docker Containers
```bash
docker-compose up -d
```

This will start:
- Zookeeper (port 2181)
- Kafka (port 9092)
- PostgreSQL (port 5433)

### 2. Run Application
```bash
mvn spring-boot:run
```

Application runs on: `http://localhost:8083`

## 📡 API Endpoints

### Create Order (Triggers Kafka Events)
```bash
curl -X POST http://localhost:8083/api/orders \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Laptop",
    "quantity": 2,
    "price": 1200.00,
    "customerEmail": "customer@example.com"
  }'
```

**What Happens:**
1. Order saved to PostgreSQL
2. `ORDER_CREATED` event published to Kafka
3. Email Service sends confirmation email
4. SMS Service sends SMS notification
5. Analytics Service tracks metrics

### Get All Orders
```bash
curl http://localhost:8083/api/orders
```

### Get Order by ID
```bash
curl http://localhost:8083/api/orders/1
```

### Update Order (Triggers Kafka Events)
```bash
curl -X PUT http://localhost:8083/api/orders/1 \
  -H "Content-Type: application/json" \
  -d '{
    "productName": "Laptop Pro",
    "quantity": 3,
    "price": 1500.00,
    "customerEmail": "customer@example.com",
    "status": "CONFIRMED"
  }'
```

### Delete Order (Triggers Kafka Events)
```bash
curl -X DELETE http://localhost:8083/api/orders/1
```

## 🎬 Event Flow

### Order Created:
```
1. POST /api/orders
2. Save to PostgreSQL
3. Publish to Kafka topic: "order-events"
4. Consumers receive event:
   - Email Service → Sends confirmation email
   - SMS Service → Sends SMS
   - Analytics Service → Tracks metrics
```

### Console Output Example:
```
💾 Saving order to DATABASE...
📤 Publishing ORDER_CREATED event to Kafka...

📧 EMAIL SERVICE - Received event: ORDER_CREATED
✅ Sending confirmation email to: customer@example.com
   Order ID: 1
   Product: Laptop
   Total: $2400.0

📱 SMS SERVICE - Received event: ORDER_CREATED
✅ Sending SMS notification
   Message: Your order #1 has been placed successfully!
   Product: Laptop

📊 ANALYTICS SERVICE - Received event: ORDER_CREATED
✅ Tracking order metrics
   Event: ORDER_CREATED
   Order ID: 1
   Revenue: $2400.0
   Product: Laptop
```

## 🔍 Kafka Topics

| Topic | Purpose | Consumers |
|-------|---------|-----------|
| `order-events` | Order lifecycle events | Email, SMS, Analytics |

## 📊 Event Types

- `ORDER_CREATED` - New order placed
- `ORDER_UPDATED` - Order modified
- `ORDER_DELETED` - Order cancelled

## 🎯 Consumer Groups

| Group | Service | Purpose |
|-------|---------|---------|
| `email-group` | Email Service | Send email notifications |
| `sms-group` | SMS Service | Send SMS notifications |
| `analytics-group` | Analytics Service | Track order metrics |

## 🧪 Testing Kafka

### Check Kafka Topics
```bash
docker exec -it kafka kafka-topics --list --bootstrap-server localhost:9092
```

### Monitor Messages
```bash
docker exec -it kafka kafka-console-consumer \
  --bootstrap-server localhost:9092 \
  --topic order-events \
  --from-beginning
```

## 💡 Key Concepts

### Producer (Order Service)
- Publishes events to Kafka when orders are created/updated/deleted
- Async - doesn't wait for consumers

### Consumers (Email, SMS, Analytics)
- Listen to Kafka topics
- Process events independently
- Can scale horizontally

### Benefits
- **Decoupling**: Services don't depend on each other
- **Scalability**: Add more consumers easily
- **Reliability**: Events stored in Kafka
- **Async**: Fast API response

## 🏢 Real-World Use Cases

Similar to:
- **Amazon**: Order → Email + SMS + Inventory + Analytics
- **Uber**: Ride Complete → Payment + Rating + Analytics
- **Netflix**: Video Watched → Recommendations + Analytics

## 📈 Scaling

```bash
# Add more consumers for load balancing
# Same group-id = load balancing
# Different group-id = broadcast
```

## 🔧 Configuration

Edit `application.properties`:
- Change Kafka broker: `spring.kafka.bootstrap-servers`
- Change database: `spring.datasource.url`
- Change port: `server.port`

## 🎓 Learning Points

1. **Event-Driven Architecture**
2. **Kafka Producer/Consumer**
3. **Async Processing**
4. **Microservices Communication**
5. **Consumer Groups**

## 🚀 Next Steps

- Add more event types
- Implement retry logic
- Add dead letter queue
- Monitor with Kafka UI
- Add authentication

---

**Perfect for FAANG interviews!** 🎯
