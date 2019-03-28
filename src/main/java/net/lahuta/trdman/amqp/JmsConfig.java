package net.lahuta.trdman.amqp;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.jms.JmsAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AutoConfigureBefore(JmsAutoConfiguration.class)
public class JmsConfig {

    @Value("${spring.qpidjms.uri}")
    private String uri;
    @Value("${spring.qpidjms.username}")
    private String username;
    @Value("${spring.qpidjms.password}")
    private String password;

    @Bean
    public JmsConnectionFactory connectionFactory() {
        JmsConnectionFactory connectionFactory = new JmsConnectionFactory();
        connectionFactory.setRemoteURI(uri);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        return connectionFactory;
    }

}