# Spring_Boot_Caching_With_Redis

# Weather Service

A comprehensive Spring Boot microservice for weather data management with Redis caching and multi-instance Redis support.

## 🚀 Overview

The Weather Service is a robust RESTful API built with Spring Boot that provides weather data management capabilities. It features advanced caching mechanisms using Redis, supports multiple Redis instances for different purposes, and includes comprehensive data persistence with MySQL.

## ✨ Features

- **Weather Data Management**: CRUD operations for weather information
- **Advanced Caching**: Redis-based caching with configurable cache names
- **Multi-Redis Support**: Separate Redis instances for production and testing
- **Data Persistence**: MySQL database integration with JPA/Hibernate
- **RESTful API**: Clean and intuitive REST endpoints
- **Cache Inspection**: Built-in cache monitoring and inspection tools
- **Docker Ready**: Complete Docker Compose setup with Redis clustering

## ��️ Architecture

### Technology Stack

- **Framework**: Spring Boot 3.5.5
- **Java Version**: 17
- **Database**: MySQL 8.0
- **Cache**: Redis 6.0.10
- **ORM**: Spring Data JPA with Hibernate
- **Build Tool**: Maven
- **Containerization**: Docker & Docker Compose

## 🛠️ Installation & Setup

### Prerequisites

- Java 17 or higher
- Maven 3.6+
- MySQL 8.0+
- Docker & Docker Compose (optional)

### Local Development Setup

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd weather-service
   ```

2. **Configure Database**
   ```sql
   CREATE DATABASE weather_service;
   ```

3. **Update Configuration**
   Edit `src/main/resources/application.properties`:
   ```properties
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   ```

4. **Build and Run**
   ```bash
   mvn clean install
   mvn spring-boot:run
   ```

### Docker Setup

1. **Start Redis Infrastructure**
   ```bash
   docker-compose up -d
   ```

2. **Run Application**
   ```bash
   mvn spring-boot:run
   ```

## 📚 API Documentation

### Base URL
```
http://localhost:8001/api/v1/weather
```

### Weather Management Endpoints

| Method | Endpoint | Description | Parameters |
|--------|----------|-------------|------------|
| `GET` | `/{cityName}` | Get weather by city name | `cityName` (path) |
| `POST` | `/add` | Add new weather data | Weather object (body) |
| `GET` | `/all` | Get all weather data | - |
| `PUT` | `/update` | Update weather data | Weather object (body) |
| `GET` | `/cacheData` | Inspect cache contents | - |

### Redis Test Endpoints

| Method | Endpoint | Description | Parameters |
|--------|----------|-------------|------------|
| `POST` | `/redis-test/set` | Set key-value data | `key`, `value` (query) |
| `POST` | `/redis-test/set-with-expiry` | Set data with expiry | `key`, `value`, `timeout`, `timeUnit` (query) |
| `GET` | `/redis-test/get/{key}` | Get data by key | `key` (path) |
| `DELETE` | `/redis-test/delete/{key}` | Delete data | `key` (path) |
| `GET` | `/redis-test/exists/{key}` | Check key existence | `key` (path) |
| `GET` | `/redis-test/keys` | Get all keys | `pattern` (query, default: "*") |
| `POST` | `/redis-test/expire/{key}` | Set expiry on key | `key` (path), `timeout`, `timeUnit` (query) |
| `GET` | `/redis-test/ttl/{key}` | Get TTL for key | `key` (path) |

### Request/Response Examples

#### Add Weather Data
```bash
curl -X POST "http://localhost:8001/api/v1/weather/add" \
  -H "Content-Type: application/json" \
  -d '{
    "cityName": "Dhaka",
    "forecast": "Sunny, 32°C"
  }'
```

#### Set Redis Test Data
```bash
curl -X POST "http://localhost:8001/api/v1/weather/redis-test/set?key=test-key&value=test-value"
```

#### Set Data with Expiry
```bash
curl -X POST "http://localhost:8001/api/v1/weather/redis-test/set-with-expiry?key=temp-key&value=temp-value&timeout=60&timeUnit=SECONDS"
```

## ️ Database Schema

### Weather Entity
```sql
CREATE TABLE weather (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    city_name VARCHAR(255) NOT NULL,
    forecast TEXT
);
```

## 🐳 Docker Configuration

The project includes a comprehensive Docker Compose setup with:
- **Redis**: Dedicated testing instance (Port 6381)

### Redis Configuration Features

- **Persistence**: Data persistence with volume mounts
- **Timezone**: Asia/Dhaka timezone configuration
- **Networking**: Isolated bridge network

##  Configuration

### Application Properties

```properties
# Server Configuration
server.port=8001
spring.application.name=weather-service

# Database Configuration
spring.datasource.url=jdbc:mysql://localhost:3306/weather-service
spring.datasource.username=root
spring.datasource.password=mosiur
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA Configuration
spring.jpa.show-sql=false
spring.jpa.hibernate.ddl-auto=update

# Redis Configuration
spring.cache.type=redis
spring.data.redis.host=localhost
spring.data.redis.port=6379
spring.cache.cache-names=weather

# Redis Test Configuration
redis.test.host=localhost
redis.test.port=6381
```

## ️ Project Structure

```
src/
├── main/
│   ├── java/com/project/weather_service/weather_service/
│   │   ├── configuration/
│   │   │   └── AppConfig.java              # Redis and caching configuration
│   │   ├── controller/
│   │   │   └── WeatherController.java      # REST API endpoints
│   │   ├── entity/
│   │   │   └── Weather.java                # Weather data model
│   │   ├── repository/
│   │   │   └── WeatherRepository.java      # Data access layer
│   │   ├── service/
│   │   │   ├── WeatherService.java         # Weather service interface
│   │   │   ├── WeatherServiceImpl.java     # Weather service implementation
│   │   │   ├── RedisTestService.java       # Redis test operations
│   │   │   └── CacheInspectionService.java # Cache monitoring
│   │   └── WeatherServiceApplication.java  # Main application class
│   └── resources/
│       ├── application.properties          # Application configuration
│       ├── static/                         # Static resources
│       └── templates/                      # Template files
└── test/
    └── java/.../                           # Test classes
```
