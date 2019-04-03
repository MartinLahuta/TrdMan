package net.lahuta.trdman.controller;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController implements ApplicationContextAware {

    private ApplicationContext context;
    
    // http://localhost:8080/hello
    @GetMapping("/hello")
    public String message() {
        return "Spring Boot funguje !!!";
    }

    // http://localhost:8080/shutdown
    @GetMapping("/shutdown")
    public void shutdown() {
        ((ConfigurableApplicationContext)context).close();
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }
    
}
