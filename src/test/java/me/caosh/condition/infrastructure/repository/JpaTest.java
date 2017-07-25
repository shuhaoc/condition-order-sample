package me.caosh.condition.infrastructure.repository;

import me.caosh.condition.infrastructure.Order;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by caosh on 2017/7/23.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class JpaTest {

    @Autowired
    private OrderRepository orderRepository;

//    @Test
    public void testCurd() throws Exception {
        Order order = new Order(null, "a1");
        orderRepository.save(order);
        Integer id = order.getId();
        Order one = orderRepository.findOne(id);
        assertEquals(order, one);
        order.setName("b2");
        orderRepository.save(order);
        Order two = orderRepository.findOne(id);
        assertEquals(order, two);

        List<Order> byName = orderRepository.findByName(order.getName());
        assertEquals(byName.size(), 1);
    }
}
