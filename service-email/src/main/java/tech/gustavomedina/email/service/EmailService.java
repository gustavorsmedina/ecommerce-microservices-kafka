package tech.gustavomedina.email.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import tech.gustavomedina.domains.dto.Event;

public interface EmailService {

    public void sendEmail(Event event, String message, String body);

}
