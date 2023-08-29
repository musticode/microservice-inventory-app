# Inventory Microservices

### Project Overview:

**Business Logic:**

1.  **Product Management:**

    -   Add a new product to the inventory.
    -   Update product details such as name, description, price, and quantity.
    -   Remove a product from the inventory.
2.  **Stock Management:**

    -   Receive new stock for a product.
    -   Deduct stock when a product is sold.
    -   Set reorder points to automatically notify when stock is low.
3.  **Supplier Management:**

    -   Add, update, or remove supplier information.
    -   Associate products with suppliers.
4.  **Order Management:**

    -   Create purchase orders for restocking.
    -   Track order status (pending, delivered, etc.).
    -   Deduct stock upon order fulfillment.
    -    Place Order (Order Placed) -> Ship Order (Order Shipped) -> Deliver Order (Order Delivered), Update Inventory (Inventory Normal) -> Low Inventory Alert (Inventory Low)
5.  **Reporting:**

    -   Generate inventory reports (e.g., stock levels, low stock items).
    -   Analyze sales data and product performance.

**UML Class Diagram:**

Here's a simplified UML class diagram for the Inventory Management System:


```
`+-------------------+          +---------------+
|     Product       |          |     Supplier  |
+-------------------+          +---------------+
| - id: Long        |<>--------|- id: Long     |
| - name: String    |          | - name: String|
| - description: String     1..*| - contactInfo: String|
| - price: BigDecimal|          +---------------+
| - quantity: int   |
| - reorderPoint: int|
+-------------------+
         ^
         |
         1
         | 
+-------------------+
|   StockTransaction|
+-------------------+
| - id: Long        |
| - transactionType: TransactionType|
| - product: Product|
| - quantity: int   |
| - transactionDate: Date|
+-------------------+
         ^
         |
         1
         | 
+-------------------+
|      PurchaseOrder|
+-------------------+
| - id: Long        |
| - orderDate: Date |
| - status: OrderStatus|
| - supplier: Supplier|
| - orderItems: List<OrderItem>|
+-------------------+
         ^
         |
         1
         | 
+-------------------+
|     OrderItem     |
+-------------------+
| - id: Long        |
| - product: Product|
| - quantity: int   |
| - pricePerUnit: BigDecimal|
+-------------------+

```


In this simplified diagram:

-   `Product` represents the products in the inventory.
-   `Supplier` represents the suppliers that provide products to the inventory.
-   `StockTransaction` tracks the stock changes (receipts and deductions).
-   `PurchaseOrder` represents a purchase order made to suppliers.
-   `OrderItem` represents individual items within a purchase order.

### Order Management:


**Business Logic:**

1.  **Order Creation:**

    -   Customers can create new orders by selecting products and quantities.
    -   Orders contain information about the customer, order date, and status.
2.  **Order Processing:**

    -   Orders move through various stages: Pending, Processing, Shipped, Delivered.
    -   Staff can update order statuses as they progress.
3.  **Inventory Deduction:**

    -   Upon order confirmation, products' quantities are deducted from inventory.
4.  **Payment Processing:**

    -   Handle payment processing and confirmation before order shipment.
5.  **Shipping and Delivery:**

    -   Generate shipping labels and track order shipments.
    -   Update order status upon successful delivery.
6.  **Cancellation and Returns:**

    -   Allow customers to cancel orders or initiate returns.
    -   Handle refunds or replacements accordingly.
7.  **Reporting:**

    -   Generate reports on order status, sales, and customer data.

**UML Class Diagram:**

Here's a simplified UML class diagram for the Order Management System:


    `+-------------------+          +---------------+
    |      Customer     |          |     Product   |
    +-------------------+          +---------------+
    | - id: Long        |          | - id: Long    |
    | - name: String    |          | - name: String|
    | - email: String   |     1..* | - price: BigDecimal|
    | - address: String |<>--------|- quantity: int|
    +-------------------+          +---------------+
             ^                           ^
             |                           |
             1                           1
             |                           | 
    +-------------------+          +---------------+
    |       Order       |          | OrderItem    |
    +-------------------+          +---------------+
    | - id: Long        |          | - id: Long   |
    | - orderDate: Date |          | - product: Product|
    | - status: OrderStatus     1..* | - quantity: int|
    | - customer: Customer|<>--------|- pricePerUnit: BigDecimal|
    | - orderItems: List<OrderItem>|  +---------------+
    +-------------------+
             ^
             |
             1
             |
    +-------------------+
    |    OrderItem      |
    +-------------------+
    | - id: Long        |
    | - product: Product|
    | - quantity: int   |
    | - pricePerUnit: BigDecimal|
    +-------------------+` 

In this diagram:

-   `Customer` represents the customers who place orders.
-   `Product` represents the products available for purchase.
-   `Order` represents the orders placed by customers.
-   `OrderItem` represents individual items within an order.

**State Transition Diagram:**

Here's a simplified state transition diagram for order statuses:

```
    +-------------+
    |   Pending   |         +------------+
    +-------------+         | Processing |
       |    ^               +------------+
       v    |                     |    ^
    +------------+                v    |
    |   Shipped  |             +----------+
    +------------+             | Delivered|
                               +----------+
```


In this diagram:

-   Orders start in the "Pending" state.
-   They can transition to "Processing" when they are being prepared for shipment.
-   From "Processing," they can transition to "Shipped" when they are out for delivery.
-   Finally, from "Shipped," they transition to "Delivered" when they are successfully delivered to the customer.

Please note that this is a simplified representation, and real-world systems might have more complex state transitions and additional classes for handling various scenarios.



---
### Test endpoints: 
order create
when order created, order event is created and send to kafka. Payment service handles this method to make payment

```
curl --location 'http://localhost:8222/api/v1/orders' \
--header 'Content-Type: application/json' \
--data '{
 "productId":1,
 "totalAmount":1,
 "quantity":1,
 "buyerId": 1,
 "sellerId":2
}'
```


get products by id:
```
curl --location 'http://localhost:8222/api/v1/products/1'
```

get product list:
```
curl --location 'http://localhost:8222/api/v1/products'
```

update balance of user
```
curl --location 'http://localhost:8222/api/v1/users//update-balance/1' \
--header 'Content-Type: application/json' \
--data '{
    "balance": 12
}'
```


---
### Dependencies:
```
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-actuator</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-config</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-devtools</artifactId>
        <scope>runtime</scope>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.postgresql</groupId>
        <artifactId>postgresql</artifactId>
        <scope>runtime</scope>
    </dependency>
    <dependency>
        <groupId>org.projectlombok</groupId>
        <artifactId>lombok</artifactId>
        <optional>true</optional>
    </dependency>
    <dependency>
        <groupId>org.modelmapper</groupId>
        <artifactId>modelmapper</artifactId>
        <version>3.0.0</version>
    </dependency>
    <dependency>
        <groupId>org.springframework.cloud</groupId>
        <artifactId>spring-cloud-starter-openfeign</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>
```

---
### Project Configurations

- config-server `application.yml`
```
spring:
  application:
    name: SCHOOL-SERVICE
  config:
    import: optional:configserver:http://localhost:8888
```
**All services have `application.yml` files like below**
```
spring:
  application:
    name: USER-SERVICE
  config:
    import: optional:configserver:http://localhost:8888
```


- api-gateway `application.yml`
```
eureka:
  client:
    fetch-registry: false
server:
  port: 8222
spring:
  application:
    name: API-GATEWAY
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
        - id: PRODUCT-SERVICE
          uri: http://localhost:8080
          predicates:
            - Path=/api/v1/products/**
        - id: ORDER-SERVICE
          uri: http://localhost:8081
          predicates:
            - Path=/api/v1/orders/**
        - id: STOCK-SERVICE
          uri: http://localhost:8084
          predicates:
            - Path=/api/v1/stocks/**
        - id: USER-SERVICE
          uri: http://localhost:8085
          predicates:
            - Path=/api/v1/users/**
```

- service-discovery `application.yml`
```
eureka:
  instance:
    hostname: localhost
  client:
    register-with-eureka: false
    fetch-registry: false
    service-url:
      defaultzone: http://${eureka.instance.hostname}:${server.port}/eureka/
```

- order-service `application.yml`
```
eureka:
  instance:
    hostname: localhost
  client:
    service-url:
      defaultzone: http://localhost:8761/eureka
server:
  port: 8081
spring:
  application:
    name: ORDER-SERVICE
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/orders
    username: username
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect
  kafka:
    topic:
      name: order_topics
    consumer:
      bootstrap-servers: 'localhost:9092'
      group-id: stock
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      properties:
        spring:
          json:
            trusted:
              packages: '*'
    producer:
      bootstrap-servers: 'localhost:9092'
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.springframework.kafka.support.serializer.JsonSerializer
application:
  config:
    product-url: http://localhost:8080/api/v1/products
```
- payment-service `application.yml`
```
eureka:
  instance:
    hostname: localhost
  client:
    service-url:
      defaultzone: http://localhost:8761/eureka
server:
  port: 8084
spring:
  application:
    name: PAYMENT-SERVICE
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/stock
    username: username
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect
  kafka:
    topic:
      name: order_topics
    consumer:
      bootstrap-servers: 'localhost:9092'
      group-id: stock
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
#      value-deserializer: org.springframework.kafka.support.serializer.JsonDeserializer
      value-deserializer: org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
      properties:
        spring:
          deserializer:
            value:
              delegate:
                class: org.springframework.kafka.support.serializer.JsonDeserializer
          json:
            trusted:
              packages: '*'
application:
  config:
    stock-url: http://localhost:8084/api/v1/stocks
```
- user-service `application.yml`
```
eureka:
  instance:
    hostname: localhost
  client:
    service-url:
      defaultzone: http://localhost:8761/eureka
server:
  port: 8085
spring:
  application:
    name: USER-SERVICE
  datasource:
    driver-class-name: org.postgresql.Driver
    url: jdbc:postgresql://localhost:5432/supplier
    username: username
    password: password
  jpa:
    hibernate:
      ddl-auto: update
    database: postgresql
    database-platform: org.hibernate.dialect.PostgreSQLDialect
application:
  config:
    supplier-url: http://localhost:8080/api/v1/users
```

---
### `docker-compose.yml`
```
services:
  postgresql:
    #container_name: postgresql
    image: postgres
    environment:
      POSTGRES_USER: username
      POSTGRES_PASSWORD: password
      PGDATA: /data/postgres
    volumes:
      - postgres:/data/postgres
    ports:
      - "5432:5432"
    networks:
      - postgres
    restart: unless-stopped
  pgadmin:
    #container_name: pgadmin
    image: dpage/pgadmin4
    environment:
      PGADMIN_DEFAULT_EMAIL: ${PGADMIN_DEFAULT_EMAIL:-pgadmin4@pgadmin.org}
      PGADMIN_DEFAULT_PASSWORD: ${PGADMIN_DEFAULT_PASSWORD:-admin}
      PGADMIN_CONFIG_SERVER_MODE: 'False'
    volumes:
      - pgadmin:/var/lib/pgadmin
    ports:
      - "5050:80"
    networks:
      - postgres
    restart: unless-stopped
  zipkin:
    #container_name: zipkin
    image: openzipkin/zipkin
    ports:
      - "9411:9411"
    networks:
      - zipkin
  zookeeper:
    image: confluentinc/cp-zookeeper:7.0.1
    #container_name: zookeeper
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
  broker:
    image: confluentinc/cp-kafka:7.0.1
    #container_name: broker
    ports:
      - "9092:9092"
    depends_on:
      - zookeeper
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: 'zookeeper:2181'
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT,PLAINTEXT_INTERNAL:PLAINTEXT
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092,PLAINTEXT_INTERNAL://broker:29092
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
      KAFKA_TRANSACTION_STATE_LOG_MIN_ISR: 1
      KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR: 1


networks:
  postgres:
    driver: bridge
  zipkin:
    driver: bridge

volumes:
  postgres:
  pgadmin:
```