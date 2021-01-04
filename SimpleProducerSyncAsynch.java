//import util.properties packages
import java.util.Properties;

//import simple producer packages
import org.apache.kafka.clients.producer.Producer;

//import KafkaProducer packages
import org.apache.kafka.clients.producer.KafkaProducer;

//import ProducerRecord packages
import org.apache.kafka.clients.producer.ProducerRecord;

//import the New Relic Java agent API
import com.newrelic.api.agent.Trace;

//Create java class named “SimpleProducer”
public class SimpleProducer {
         
   static Producer getProducer(Properties props) {
      return new KafkaProducer<String, String>(props);
   }

   @Trace(dispatcher = true)
   static void sendMessageSynch(String topicName, int count, Producer producer) throws Exception {
      try {
         producer.send(new ProducerRecord<String, String>(topicName, 
         Integer.toString(count), Integer.toString(count))).get();    // Synchronous Call to Kafka send()
            System.out.println("Message sent successfully");
      } finally {
         //producer.flush();
      }
   }

   @Trace(dispatcher = true)
   static void sendMessageASynch(String topicName, int count, Producer producer) {
         producer.send(new ProducerRecord<String, String>(topicName, 
         Integer.toString(count), Integer.toString(count)));         // Asynchronous Call to Kafka send()
            System.out.println("Message sent successfully");
   }

   public static void main(String[] args) throws Exception{
      
      // Check arguments length value
      if(args.length < 2){
         System.out.println("Enter hostname and topic");
         return;
      }
      
      //Assign topicName to string variable
      String hostName = args[0].toString();

      //Assign hostname to string variable
      String topicName = args[1].toString();
      
      // create instance for properties to access producer configs   
      Properties props = new Properties();
      
      //Assign localhost id
      props.put("bootstrap.servers", hostName+":9092");
      
      //Set acknowledgements for producer requests.      
      props.put("acks", "all");
      
      //If the request fails, the producer can automatically retry,
      props.put("retries", 0);
      
      //Specify buffer size in config
      props.put("batch.size", 16384);
      
      //Reduce the no of requests less than 0   
      props.put("linger.ms", 1);
      
      //The buffer.memory controls the total amount of memory available to the producer for buffering.   
      props.put("buffer.memory", 33554432);
      
      props.put("key.serializer", 
         "org.apache.kafka.common.serialization.StringSerializer");
         
      props.put("value.serializer", 
         "org.apache.kafka.common.serialization.StringSerializer");

      Producer<String, String> producer = getProducer(props);

      long startKafkaTimer, endKafkaTimer;
            
      for(int i = 0; i < 10000; i++) {
         Thread.sleep(2000);

         // Send the message to Kafka Synchronously
         startKafkaTimer = System.currentTimeMillis();
         System.out.println("Synchronous Send start time is "+startKafkaTimer);

         sendMessageSynch(topicName, i, producer);    // Call the Synchrous method

         endKafkaTimer = System.currentTimeMillis();
         System.out.println("Synchronous Send end time is "+endKafkaTimer);
         System.out.println("Synchronous Send total time is "+(endKafkaTimer-startKafkaTimer));


         startKafkaTimer = System.currentTimeMillis();
         System.out.println("Asynchronous Send start time is "+startKafkaTimer);

         sendMessageASynch(topicName, i, producer);   // Call the Asynchronous method

         endKafkaTimer = System.currentTimeMillis();
         System.out.println("Asynchronous Send end time is "+endKafkaTimer);
         System.out.println("Asynchronous Send total time is "+(endKafkaTimer-startKafkaTimer));
      }
      producer.close();

   }
}