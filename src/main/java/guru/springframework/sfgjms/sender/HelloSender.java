package guru.springframework.sfgjms.sender;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.domain.HelloWorld;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.Session;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelloSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 2000)
//    public void send() {
////        log.debug("Sending hello world message to events");
//        HelloWorld helloWorld = HelloWorld.builder()
//                .id(UUID.randomUUID())
//                .message("Hello world")
//                .build();
//        log.debug(helloWorld.toString());
//        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorld);
////        log.info("Sent message to queue");
//    }

    @Scheduled(fixedDelay = 2000)
    public void sendAndReceive() throws JMSException {
        log.debug("Sending message from HelloSender");
        HelloWorld helloWorld = HelloWorld.builder()
                .id(UUID.randomUUID())
                .message("Hello")
                .build();
        Message message = jmsTemplate.sendAndReceive(JmsConfig.MY_SEND_RCV_QUEUE, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                Message helloMessage;
                try {
                    helloMessage = session.createTextMessage(objectMapper.writeValueAsString(helloWorld));
                    helloMessage.setStringProperty("_type", "guru.springframework.sfgjms.domain.HelloWorld");
                    return helloMessage;
                } catch (JsonProcessingException e) {
                    throw new JMSException(e.getMessage());
                }
            }
        });

        log.info("Received back message: {}", message.getBody(String.class));
    }
}
