package me.caosh.condition.interfaces.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * Created by caosh on 2017/7/25.
 */
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/list")
    public String index(Map<String, Object> model) {
//        model.put("x", 123);
        return "list";
    }
    @RequestMapping("/create")
    public String create(Map<String, Object> model) {
//        model.put("x", 123);
        return "create";
    }

    @RequestMapping("/entrusts")
    public String entrusts(Map<String, Object> model) {
//        model.put("x", 123);
        return "entrusts";
    }
}
