package me.caosh.condition.infrastructure.repository;

import me.caosh.condition.infrastructure.Order;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

/**
 * Created by caosh on 2017/7/23.
 */
public interface OrderRepository extends CrudRepository<Order, Integer> {
    List<Order> findByName(String name);
}
