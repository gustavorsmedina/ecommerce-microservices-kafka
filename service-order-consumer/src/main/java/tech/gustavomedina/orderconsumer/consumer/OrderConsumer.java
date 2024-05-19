package tech.gustavomedina.orderconsumer.consumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tech.gustavomedina.domains.dto.Event;
import tech.gustavomedina.orderconsumer.service.OrderService;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderConsumer {

    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @Value("${spring.kafka.topic.payment}")
    private String paymentTopic;

    @KafkaListener(topics = "${spring.kafka.topic.payment}", groupId = "${spring.kafka.consumer.group-id}")
    public void consume(String payload) throws JsonProcessingException {
        log.info("Receiving event from {} with data {}", paymentTopic, payload);

        var event = objectMapper.readValue(payload, Event.class);

        orderService.updateOrder(event);
    }

}
