package me.caosh.condition.infrastructure.tunnel.model;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;

/**
 * Created by caosh on 2017/8/6.
 */
public class ConditionOrderDOGSONUtils {

    private static final Gson GSON = createConditionGSON();

    public static Gson getGSON() {
        return GSON;
    }

    private static Gson createConditionGSON() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ConditionDO.class)
                        .registerSubtype(PriceConditionDO.class)
                        .registerSubtype(TurnPointConditionDO.class)
                        .registerSubtype(TimeReachedConditionDO.class)
                        .registerSubtype(GridConditionDO.class)
                        .registerSubtype(NewStockPurchaseConditionDO.class))
                .create();
    }

    private ConditionOrderDOGSONUtils() {
    }
}
