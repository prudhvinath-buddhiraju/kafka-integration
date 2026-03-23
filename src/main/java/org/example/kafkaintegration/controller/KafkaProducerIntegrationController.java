package org.example.kafkaintegration.controller;

import org.example.kafkaintegration.service.KafkaProducerIntegration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@RestController
public class KafkaProducerIntegrationController
{
    @Autowired
    KafkaProducerIntegration kpi;
    @PostMapping("/send")
    public ResponseEntity<String> publishMessages(String env, String topic, String key) throws IOException, ExecutionException, InterruptedException
    {
        try {
            kpi.sendMessages("RND", "transaction", "key1");
        }
        catch(IOException | ExecutionException | InterruptedException e)
        {
            throw new RuntimeException(e);
        }
        return ResponseEntity.ok("message sent to kafka successfully");
    }
}
