package guru.springframework.sfgjms.receiver;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.domain.HelloWorld;
import jakarta.jms.JMSException;
import jakarta.jms.Message;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelloReceiver {

    private final JmsTemplate jmsTemplate;

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void receive(HelloWorld helloWorld) {
        log.debug("Received: {}", helloWorld);
        log.info("Received message: " + helloWorld.getMessage());
        log.info("Completed receiving the message");
    }

    @JmsListener(destination = JmsConfig.MY_SEND_RCV_QUEUE)
    public void receiveAndSend(@Payload HelloWorld helloWorld, @Headers MessageHeaders messageHeaders, Message message) throws JMSException {
        log.debug("Received: {}", helloWorld);
        log.info("Received message: " + helloWorld.getMessage());
        HelloWorld payloadMsg = HelloWorld
                .builder()
                .id(UUID.randomUUID())
                .message("World!!")
                .build();
        log.info("Replying");
        jmsTemplate.convertAndSend(message.getJMSReplyTo(), payloadMsg);
        log.info("Replied!!");
    }
}
