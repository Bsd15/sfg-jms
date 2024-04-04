package guru.springframework.sfgjms.sender;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.domain.HelloWorld;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
@Slf4j
public class HelloSender {

    private final JmsTemplate jmsTemplate;

    @Scheduled(fixedDelay = 2000)
    public void send() {
        log.debug("Sending hello world message to events");
        HelloWorld helloWorld = HelloWorld.builder()
                .id(UUID.randomUUID())
                .message("Hello world")
                .build();
        log.debug(helloWorld.toString());
        jmsTemplate.convertAndSend(JmsConfig.MY_QUEUE, helloWorld);
        log.info("Sent message to queue");
    }
}
