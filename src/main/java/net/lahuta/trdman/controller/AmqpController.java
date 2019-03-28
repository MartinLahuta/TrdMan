package net.lahuta.trdman.controller;

import lombok.extern.slf4j.Slf4j;
import net.lahuta.trdman.service.AmqpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Component
public class AmqpController {

    @Autowired
    private AmqpService amqpService;

    @JmsListener(destination = "broadcast.CCP.RAPPIDDCASH_Trade")
    public void receivedTrade(byte[] data) {
        String message = new String(data, UTF_8);
        log.info("Received message:\n" + message);
        amqpService.receiveMessage(message);
    }

}
