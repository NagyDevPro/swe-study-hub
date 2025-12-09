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
- acks all: the producer waits 

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