package net.lahuta.trdman.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
//import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@RestController
@RequestMapping("/")
public class HelloController implements ApplicationContextAware {

    private ApplicationContext context;
    
    // http://localhost:8080/hello
    @GetMapping({"/", "/hello", "/trdman/hello"})
    public ModelAndView hello() {
        ModelAndView modelAndView = new ModelAndView("hello");
        modelAndView.addObject("name", "trdman");
        return modelAndView;
    }

    // http://localhost:8080/shutdown
    @GetMapping("/shutdown")
    public void shutdown() {
        log.info("Shutting down trdman ...");
        ((ConfigurableApplicationContext)context).close();
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        this.context = ctx;
    }
    
}
