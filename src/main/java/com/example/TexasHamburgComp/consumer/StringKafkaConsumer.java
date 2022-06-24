package com.example.TexasHamburgComp.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

@Slf4j
public class StringKafkaConsumer {
    private final static String TOPIC = "com-thcstring";
    private final static String BOOTSTRAP_SERVER = "localhost:9092";
    private final static String CLIENT_ID_CONFIG = "string-consumer-adarsh";
    private final static String GROUP_ID_CONFIG = "string-consumer-adarsh";

    public static Consumer<String, String> createConsumer(){
        final Properties props = new Properties();
        props.put(ConsumerConfig.CLIENT_ID_CONFIG,CLIENT_ID_CONFIG);
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVER);
        props.put(ConsumerConfig.GROUP_ID_CONFIG,GROUP_ID_CONFIG);

        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");

        final Consumer<String, String> consumer = new KafkaConsumer<>(props);

        return consumer;
    }

    static void runConsumer(){
        final Consumer<String,String> consumer = createConsumer();

        consumer.subscribe(Collections.singletonList(TOPIC));

        try{
            while(true){
                final ConsumerRecords<String, String> consumerRecords =
                        consumer.poll(Duration.ofSeconds(1000));

                if(consumerRecords.isEmpty()){
                    log.info("No Records found for ...{}",CLIENT_ID_CONFIG);
                    continue;
                }

                consumerRecords.forEach(record ->
                        log.info("Consumer Record:({}, {}, {}, {}",
                                record.key(),record.value(),record.partition(),record.offset()));
            }
        }catch(Exception exception){
            System.err.println(exception);
            log.info("Error in Consumer ..",exception);
        } finally{
            consumer.close();
            log.info("Action completed");
        }
    }

    public static void main(String[] args){
        runConsumer();
    }

}
