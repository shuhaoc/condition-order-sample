package me.caosh.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class Application implements ApplicationListener {
    private static final Logger logger = LoggerFactory.getLogger(Application.class);

    @RequestMapping("/")
    String home() {
        return "Hello World!3";
    }

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
//        logger.info("Application event ==> {}", applicationEvent);
    }
}