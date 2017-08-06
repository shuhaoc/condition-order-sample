package me.caosh.condition.infrastructure.repository.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import me.caosh.condition.domain.model.order.Condition;
import me.caosh.condition.domain.model.order.SimplePriceCondition;

/**
 * Created by caosh on 2017/8/6.
 */
public class ConditionOrderGsonUtils {

    private static final Gson GSON = createSpecialGson();

    public static Gson getGSON() {
        return GSON;
    }

    private static Gson createSpecialGson() {
        return new GsonBuilder()
                .registerTypeAdapterFactory(
                        RuntimeTypeAdapterFactory.of(Condition.class)
                                .registerSubtype(SimplePriceCondition.class)
                ).create();
    }

    private ConditionOrderGsonUtils() {
    }
}
