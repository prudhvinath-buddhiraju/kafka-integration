package org.example.kafkaintegration.kafka;

import org.apache.kafka.clients.producer.ProducerConfig;

import java.util.HashMap;
import java.util.Map;

public class ProducerConfigs {
    public static Map<String, Object> getProducerConfigs(String env) {
        Map<String, Object> configs = new HashMap<>();
        if (env.toUpperCase().equals("RND")) {
            configs.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "kafka.broker.com:9092");
            configs.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
            configs.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "io.confluent.kafka.serializers.KafkaAvroSerializer");
            configs.put("security.protocol","SASL_SSL");
            configs.put("sasl.mechanism","PLAIN");
            configs.put("request.timeout.ms","1000");
            configs.put(
                    "sasl.jaas.config",
                    "org.apache.kafka.common.security.plain.PlainLoginModule required " +
                            "username=\"producer\" " +
                            "password=\"producer-secret\";"
            );
            configs.put("ssl.truststore.location","C:\\Users\\KF979BQ\\OneDrive - EY\\Documents\\kafka.client.truststore.jks");
            configs.put("ssl.truststore.password","client");
            configs.put("schema.registry.url", "http://schema.registry.com:8081");
            configs.put("basic.auth.credentials.source", "USER_INFO");
            configs.put("schema.registry.basic.auth.user.info", "admin:admin-secret");


        }
        else
        {
            System.out.println(env + " is not found");
        }
        return configs;
    }
}
