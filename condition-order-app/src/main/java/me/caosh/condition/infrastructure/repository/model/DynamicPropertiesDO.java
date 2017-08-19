package me.caosh.condition.infrastructure.repository.model;

/**
 * Created by caosh on 2017/8/19.
 */
public interface DynamicPropertiesDO {
    void accept(DynamicPropertiesDOVisitor visitor);
}
