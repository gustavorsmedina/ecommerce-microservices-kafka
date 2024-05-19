package tech.gustavomedina.payment.producer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;

    @Value("${spring.kafka.topic.payment}")
    private String paymentTopic;

    public void sendEvent(String payload){

        kafkaTemplate.send(paymentTopic, payload);

        log.info("Sending event to topic {} with data {}", paymentTopic, payload);
    }

}
