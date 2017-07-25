package me.caosh.condition;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import java.util.Arrays;

/**
 * Created by caosh on 2017/7/22.
 */
@Configuration
@ConfigurationProperties(prefix = "hbec")
@Validated
public class CommandLineHandler implements CommandLineRunner, DisposableBean {
    private static final Logger logger = LoggerFactory.getLogger(CommandLineHandler.class);

//    @Value("${name}")
    @NotNull
    private String name;

    @NotNull
    private String address;

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @ConfigurationProperties
    @Bean
    SomeDTO getSomeDTO() {
        return new SomeDTO();
    }

    @PostConstruct
    public void init() {
        logger.info("Load configuration, name={}", name);
        logger.info("Load configuration, someDTO={}", getSomeDTO());
    }

    @Override
    public void run(String... strings) throws Exception {
        logger.info("Command line arguments, length={} <== {}", strings.length, Arrays.toString(strings));
    }

    @Override
    public void destroy() throws Exception {
        logger.info("Destroy me ......");
    }
}
