# kafka_clients

### A set of simple Java Kafka clients (Producer/Consumer) for testing basic capabilities of Kafka. The examples are based on the examples published on Tutorialspoint [here](https://www.tutorialspoint.com/apache_kafka/apache_kafka_simple_producer_example.htm). If you need additional help setting up a Kafka test environment please check out the quickstart instructions at [Apache.org](https://kafka.apache.org/documentation/#quickstart).

To compile and run you will need Java installed and the Kafka Java Client libraries such as those from [Confluent.com](https://docs.confluent.io/platform/current/clients/index.html).

**SimpleProducer.java** is an example that uses Producer.send() to send a message. To compile use the command,

`javac -cp "/path/to/kafka/libs/*" SimpleProducer.java`

The *SimpleProducer* class takes as input the hostname of the Kafka cluster and a topicName to publish the messages. To execute the Producer client use the command,

`java -cp "/path/to/kafka/libs/*":. SimpleProducer <hostame or IP> <topicName`

**SimpleConsumer.java** is an example that uses Consumer.subscribe() method to retrieve messages from a topic. To compile use the command,

`javac -cp "/path/to/kafka/libs/*" SimpleConsumer.java'

The *SimpleConsumer* class takes as input the hostname of the Kafka cluster and a topicName to subscribe. To execute the Consumer client use the command,

`java -cp "/path/to/kafka/libs/*":. SimpleConsumer <hostame or IP> <topicName`

By default, the Producer.send() method makes non-blocking (asynchronous) calls to Kafka. 

**SimpleProducerSynchAsynch.java** makes both a blocking (synchronous) and non-blocking (asynchronous) calls to Kafka.

**SimpleProducer_newrelic.java** has imports and annotations for New Relic Java instrumentation. To compile and run that file requires that you install the New Relic Java agent which you can download from [New Relic](https://newrelic.com) after creating a free account. Then compile with the command,

`javac -cp "/path/to/kafka/libs/*":"/path/to/newrelic/*" SimpleProducer.java`

To run use the command,

`java -cp "/path/to/kafka/libs/*":"/path/to/newrelic/*":. SimpleProducer <hostame or IP> <topicName`
