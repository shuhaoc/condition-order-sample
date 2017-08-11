package me.caosh.condition.infrastructure.repository.impl;

import me.caosh.condition.domain.model.share.SnowflakeIdWorker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;

/**
 * Created by caosh on 2017/8/6.
 */
@Configuration
@ConfigurationProperties(prefix = "me.caosh.condition.conditionOrder")
@Validated
public class ConditionOrderIdGenerator {
    private static final Logger logger = LoggerFactory.getLogger(ConditionOrderIdGenerator.class);

    @NotNull
    private Integer workerId;

    @NotNull
    private Integer datacenterId;

    private SnowflakeIdWorker snowflakeIdWorker;

    public void setWorkerId(Integer workerId) {
        this.workerId = workerId;
    }

    public void setDatacenterId(Integer datacenterId) {
        this.datacenterId = datacenterId;
    }

    public long nextId() {
        return snowflakeIdWorker.nextId();
    }

    @PostConstruct
    public void init() {
        logger.info("Load config for condition order id generator: workerId={}, datacenterId={}",
                workerId, datacenterId);

        snowflakeIdWorker = new SnowflakeIdWorker(workerId, datacenterId);
    }

}
