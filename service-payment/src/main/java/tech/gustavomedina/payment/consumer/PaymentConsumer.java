package tech.gustavomedina.payment.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.payment.service.PaymentService;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentConsumer {

    private final PaymentService paymentService;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.topic.order}")
    private String orderTopic;

    @KafkaListener(topics = "${spring.kafka.topic.order}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String payload) throws JsonProcessingException {
        log.info("Receiving event from {} with data {}", orderTopic, payload);

        var event = objectMapper.readValue(payload, Event.class);

        paymentService.processPayment(event);
    }

}
