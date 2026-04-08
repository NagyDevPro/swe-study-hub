## RabbitMQ vs kafka

- Architecture: Message broker.
- Model: Uses a "push" model where the broker sends messages to consumers.
- Message Handling: Messages are deleted after being successfully consumed.
- Performance: Lower throughput (4K-10K messages/sec) but lower latency.
- Routing: Supports complex routing with various exchange types (direct, fanout, topic, header).
- Message broker – Designed to receive, store, and forward messages between producers and consumers. It’s more about reliable delivery and flexible routing
- in-memory


## Kafka
- Architecture: Distributed streaming platform. 

- Model: Uses a "pull" model where consumers request messages from the broker. 

- Message Handling: Stores messages in a persistent log, retaining them based on policy for potential replay. 

- Performance: Higher throughput (up to millions of messages/sec) with higher latency due to batching and persistence. 

- Routing: Uses a publish-subscribe model based on partitions 

- Streaming platform – Designed to handle high-throughput, real-time data streams. It treats messages as an immutable log that consumers can read at their own pace.

- disk based

### kafka acknowleadge modes
- acks modes: 0 (the producer doesn't wait for any acknowleadgment from the broker)
- acks 1: the producer waits for the leader broker to acknowleadgement the write
- acks all: the producer waits for all in-sync replicas to acknowleadgement the write, this is the safest mode but also the slowest one 

* in spring and spring kafka there are two basic types
  
- **Automatic**: In these modes, Spring Kafka automatically calls `commitSync` or `commitAsync` for you. You just write your processing logic, and if your listener method finishes without throwing an exception, Spring handles the commit

* **AckMode.BATCH (The Default)**

**What it is**: Spring Kafka processes all the records returned from the consumer's poll() call. After your listener has successfully processed the entire batch of records, the container commits the offset for the last record in the batch.

**Usage**: This is the default and offers the best performance. It's ideal for high-throughput scenarios.

**Downside**: If your listener fails on the 90th record out of 100, the entire batch of 100 records will be redelivered on the next poll (after a retry/error handling policy)




* **AckMode.RECORD (Likely your "Immediate")**

**What it is**: The container commits the offset after each individual record is processed by your listener.

**Usage**: Use this when processing is time-consuming or expensive, and you cannot afford to re-process an entire batch. If one record fails, only that record (and those after it) will be redelivered.

**Downside**: This is much slower than BATCH because it involves many more network round-trips to the broker to commit offsets one by one


- **Manual**: by the developer himself he manage the acknowleadge




## @Transactional
- Propagation defines how a method annotated with @Transactional behaves when it’s called by another method that already has a transaction

**PROPAGATION_REQUIRED (✅ Default)**

* If a transaction exists → join it.

* If no transaction exists → start a new one


**PROPAGATION_REQUIRES_NEW**: Always start a new transaction, even if one already exists, The existing transaction is suspended until the new one finishes


**PROPAGATION_SUPPORTS**: If a transaction exists → join it, If not → run without a transaction

**PROPAGATION_MANDATORY**: If a transaction exists → join it, If no transaction exists → throw an exception.




## Hibernate
- hibernate doesn't by default manages the transaction, it delegates it to JDBC or JTA.



mock ()



## Multi-threading

- we can implement a thread using  



- server side discovery -> api gw
- client side discovery -> service registry (eureka, consul, zookeeper)
- load balancing -> ribbon, feign, rest template, open feign
- circuit breaker -> resilience4j, hystrix
- service mesh -> istio, linkerd
- distributed tracing -> zipkin, jaeger
- configuration management -> spring cloud config, consul, zookeeper
- distributed caching -> redis, hazelcast, caffeine

- Saga Cherography: Each service manages its own transaction and publishes events when a transaction is completed. Other services listen for these events and perform their own transactions accordingly. This approach is more decentralized and can be easier to implement, but it can also lead to more complex error handling and data consistency issues.

- saga orchestration: A central coordinator (orchestrator) manages the transactions across multiple services. The orchestrator sends commands to each service to perform its part of the transaction and waits for responses. This approach can provide better control and error handling but can also introduce a single point of failure and increased complexity in the orchestrator.

- bulkhead pattern: is a design pattern that helps to isolate different parts of a system to prevent a failure in one part from affecting the entire system. It is often used in microservices architecture to improve the resilience and fault tolerance of the system. The idea is to divide the system into smaller, independent components (or "bulkheads") that can operate independently of each other. If one component fails, it does not affect the others, allowing the system to continue functioning.

- Example of bulkhead pattern: if you have a microservice that handles user auth and another that handles user profile, you can use the bulkhead pattern to isolate the auth service from the profile service. If the auth service fails, the profile service can still function and serve user profiles without being affected by the failure of the auth service.

- microservices principles: 
- Single Responsibility Principle: Each microservice should have a single responsibility and should be focused on a specific business capability.

- Loose Coupling: Microservices should be loosely coupled, meaning that they should not depend on each other and should be able to function independently.

- High Cohesion: Each microservice should have high cohesion, meaning that its components should be closely related and work together to achieve a specific goal.

- Scalability: Microservices should be designed to scale independently, allowing for better performance and resource utilization.

- Resilience: Microservices should be designed to be resilient, meaning that they should be able to handle failures and continue to function without affecting the entire system.

- Observability: Microservices should be designed to be observable, meaning that they should provide metrics and logging to help identify and troubleshoot issues.

- API First: Microservices should be designed with an API-first approach, meaning that the API should be designed and documented before the implementation of the service.