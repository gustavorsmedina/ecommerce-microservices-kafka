package tech.gustavomedina.order.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.order}")
    private String orderTopic;

    public void sendEvent(String payload){

        log.info("Sending event to topic {} with data {}", orderTopic, payload);

        kafkaTemplate.send(orderTopic, payload);
    }

}
