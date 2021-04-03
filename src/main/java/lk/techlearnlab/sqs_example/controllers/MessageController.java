/**
 * @author anthonydonx
 */
package lk.techlearnlab.sqs_example.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.core.QueueMessagingTemplate;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
public class MessageController {
    private static final Logger logger = LoggerFactory.getLogger(MessageController.class);
    @Value("${cloud.aws.end-point.uri}")
    private String endpoint;
    final QueueMessagingTemplate queueMessagingTemplate;

    public MessageController(QueueMessagingTemplate queueMessagingTemplate) {
        this.queueMessagingTemplate = queueMessagingTemplate;
    }
    @GetMapping("/send/{message}")
    public ResponseEntity<?> send(@PathVariable String message) {
        logger.info("New message added to the queue : {}", message);
        queueMessagingTemplate.send(endpoint, MessageBuilder.withPayload(message).build());
        return ResponseEntity.ok("Message added to the queue");
    }
    @SqsListener("techlearnlab-queue")
    public void loadMessageFromQueue(String message){
        logger.info("message from queue : {}",message);
    }
}
