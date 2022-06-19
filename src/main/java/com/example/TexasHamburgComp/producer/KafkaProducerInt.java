package com.example.TexasHamburgComp.producer;

import com.example.TexasHamburgComp.model.DailyOrders;
import com.fasterxml.jackson.databind.JsonNode;
import org.apache.kafka.clients.producer.Producer;

public interface KafkaProducerInt {
    void runProducer(JsonNode jsonNode);
}
