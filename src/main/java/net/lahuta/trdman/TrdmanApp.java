package net.lahuta.trdman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJms
@EnableAsync
public class TrdmanApp {

    public static void main(String[] args) {
        SpringApplication.run(TrdmanApp.class, args);
    }

}