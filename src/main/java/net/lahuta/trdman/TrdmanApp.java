package net.lahuta.trdman;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class TrdmanApp {

    public static void main(String[] args) {
        SpringApplication.run(TrdmanApp.class, args);
    }

}