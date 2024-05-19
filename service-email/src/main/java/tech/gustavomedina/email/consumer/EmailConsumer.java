package tech.gustavomedina.email.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.email.service.EmailService;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmailConsumer {

    private final EmailService emailService;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.topic.order}")
    private String orderTopic;

    @Value("${spring.kafka.topic.payment}")
    private String paymentTopic;

    @KafkaListener(topics = "${spring.kafka.topic.order}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeOrder(String payload) throws JsonProcessingException {
        log.info("Receiving event from {} with data {}", orderTopic, payload);

        var event = objectMapper.readValue(payload, Event.class);

        var subject = "New order " + event.getPayload().getId();
        var body = objectMapper.writeValueAsString(event.getPayload().getProducts());

        emailService.sendEmail(event, subject, body);
    }

    @KafkaListener(topics = "${spring.kafka.topic.payment}", groupId = "${spring.kafka.consumer.group-id}")
    public void consumePayment(String payload) throws JsonProcessingException {
        log.info("Receiving event from {} with data {}", paymentTopic, payload);

        var event = objectMapper.readValue(payload, Event.class);

        var subject = "New payment " + event.getPayload().getId();
        var body = event.getPayload().getPaymentStatus().toString();

        emailService.sendEmail(event, subject, body);
    }

}
