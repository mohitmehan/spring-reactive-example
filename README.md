# Reactive User Management Service

A Spring WebFlux based reactive REST API for user management.

## Features

- Reactive REST API using Spring WebFlux
- MongoDB reactive repository for data persistence
- CRUD operations for user management
- Both annotation-based and functional endpoints
- Reactive error handling

## Prerequisites

- Java 17+
- Maven 3.6+
- MongoDB 4.0+

## Running the Application

1. Start MongoDB:
   ```bash
   # Using Docker
   docker run -d -p 27017:27017 --name mongodb mongo:latest
   ```

2. Build the application:
   ```bash
   mvn clean install
   ```

3. Run the application:
   ```bash
   mvn spring-boot:run
   ```

The application will start on port 8080.

## API Endpoints

### Annotation-based Controller (v1)

- **GET /api/users** - Get all users
- **GET /api/users/{id}** - Get user by ID
- **POST /api/users** - Create a new user
- **PUT /api/users/{id}** - Update an existing user
- **DELETE /api/users/{id}** - Delete a user
- **GET /api/users/email/{email}** - Get user by email
- **GET /api/users/search?name={name}&salary={salary}** - Get users by name and salary
- **GET /api/users/{id}/email/{email}** - Get user by ID and email

### Functional Endpoints (v2)

- **GET /api/v2/users** - Get all users
- **GET /api/v2/users/{id}** - Get user by ID
- **POST /api/v2/users** - Create a new user
- **PUT /api/v2/users/{id}** - Update an existing user
- **DELETE /api/v2/users/{id}** - Delete a user
- **GET /api/v2/users/email/{email}** - Get user by email

## Example User JSON

```json
{
  "firstName": "John",
  "lastName": "Doe",
  "email": "john.doe@example.com",
  "phone": "1234567890",
  "active": true,
  "salary": 75000.00
}
``` 