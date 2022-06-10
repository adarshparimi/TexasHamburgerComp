package com.example.TexasHamburgComp.producer;

import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.connect.json.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import springfox.documentation.spring.web.json.Json;

import java.io.File;
import java.util.Properties;

@Slf4j
public class JsonKafkaProducer {

    private final static String TOPIC = "com-thc-json";
    private final static String BOOTSTRAP_SERVERS = "localhost:9092";
    private final static String CLIENT_ID_CONFIG = "json-producer-adarsh";

    private final static ObjectMapper objectMapper = new ObjectMapper();

    private static Producer<String, JsonNode> createProducer(){
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG,BOOTSTRAP_SERVERS);
        props.put(ProducerConfig.CLIENT_ID_CONFIG,CLIENT_ID_CONFIG);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class.getName());

        return new KafkaProducer<>(props);
    }

    static void runProducer(final int sendMessageCount) throws Exception{
        final Producer<String, JsonNode> producer = createProducer();

        try{
            for(int index = 0;index<sendMessageCount; index++){
                final ProducerRecord<String, JsonNode> record = new ProducerRecord<>(TOPIC, Integer.toString(index),
                        objectMapper.readValue(new File("./src/main/resources/userData.json"), JsonNode.class));
                RecordMetadata metadata = producer.send(record).get();
                log.info("sent Record(key = {},value = {}) meta = (partition = {}, offset = {})",
                        record.key(),record.value(),metadata.partition(),metadata.offset());
            }
        } finally{
            producer.flush();
            producer.close();
        }
    }

    public static void main(String[] args) throws Exception{
        runProducer(10);
    }
}
