package org.example.kafkaintegration.kafka;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.errors.AuthenticationException;
import org.apache.kafka.common.errors.SerializationException;
import org.apache.kafka.common.errors.TimeoutException;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class KafkaAvroProducer {
    public static void send(String env,String topic, String key) throws IOException,InterruptedException,ExecutionException{

        try {
            Schema schema = new Schema.Parser().parse(new File("src/main/resources/avro/transaction-v1.avsc"));
            GenericRecord record = new GenericData.Record(schema);
            Map<String, Object> producerConfigs = ProducerConfigs.getProducerConfigs(env);
            try {
                KafkaProducer<String, GenericRecord> producer = new KafkaProducer<>(producerConfigs);
                ProducerRecord<String, GenericRecord> producerRecord = new ProducerRecord<>(topic,key,record);
                record.put("transactionid", 11);
                record.put("name", "buttler");
                record.put("amount", 11000);
                producerRecord.headers().add("type", "transaction".getBytes(StandardCharsets.UTF_8));
                producerRecord.headers().add("schema", "v1".getBytes(StandardCharsets.UTF_8));
                try {
                    Future<RecordMetadata> future=producer.send(producerRecord);
                    RecordMetadata recordMetadata=future.get();
                    System.out.println("topic is "+recordMetadata.topic());
                    System.out.println("partitions is "+recordMetadata.partition());
                    System.out.println("offset is "+recordMetadata.offset());
                    producer.flush();
                } catch (ExecutionException e)
                {
                    Throwable cause=e.getCause();
                    if(cause instanceof org.apache.kafka.common.errors.TimeoutException)
                    {
                        throw new RuntimeException("TimeoutException occured ",cause);
                    }
                    if(cause instanceof org.apache.kafka.common.errors.SaslAuthenticationException)
                    {
                        throw new RuntimeException("Authentication failed ",cause);
                    }
                    if(cause instanceof org.apache.kafka.common.errors.AuthorizationException)
                    {
                        throw new RuntimeException("producer is not authorized to publish the messages");
                    }
                }

            } catch (SerializationException se) {
                throw new RuntimeException("serialization exception occured ",se);

            }
        } catch (IOException schemafileException) {
            throw new RuntimeException("schema file not found " + schemafileException.getMessage());
        }
    }
}