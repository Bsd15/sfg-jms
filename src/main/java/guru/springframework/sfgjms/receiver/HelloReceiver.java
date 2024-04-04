package guru.springframework.sfgjms.receiver;

import guru.springframework.sfgjms.config.JmsConfig;
import guru.springframework.sfgjms.domain.HelloWorld;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class HelloReceiver {

    @JmsListener(destination = JmsConfig.MY_QUEUE)
    public void receive(HelloWorld helloWorld) {
        log.debug("Received: {}", helloWorld);
        log.info("Received message: " + helloWorld.getMessage());
        log.info("Completed receiving the message");
    }
}
