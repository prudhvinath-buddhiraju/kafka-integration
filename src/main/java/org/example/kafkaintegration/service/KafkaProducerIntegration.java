package org.example.kafkaintegration.service;

import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.example.kafkaintegration.kafka.KafkaAvroProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
@Service
public class KafkaProducerIntegration
{
    public void sendMessages(String env,String topic, String key) throws IOException,InterruptedException, ExecutionException
    {
        KafkaAvroProducer.send(env, topic, key);
    }
}
