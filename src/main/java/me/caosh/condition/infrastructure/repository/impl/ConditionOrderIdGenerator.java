package me.caosh.condition.infrastructure.repository.impl;

import me.caosh.condition.domain.model.share.SnowflakeIdWorker;
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
        snowflakeIdWorker = new SnowflakeIdWorker(workerId, datacenterId);
    }

}
