package hbec.commons.domain.intellitrade.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import hbec.commons.domain.intellitrade.condition.ConditionDTO;
import hbec.commons.domain.intellitrade.condition.PriceConditionDTO;
import hbec.commons.domain.intellitrade.condition.TurnPointConditionDTO;
import hbec.commons.domain.intellitrade.signal.*;
import me.caosh.condition.domain.dto.order.GridConditionDTO;
import me.caosh.condition.domain.dto.order.NewStockPurchaseConditionDTO;
import me.caosh.condition.domain.dto.order.SimpleTimeConditionDTO;


/**
 * Created by caosh on 2017/8/6.
 */
public class ConditionOrderDTOGSONUtils {

    private static final Gson CONDITION_GSON = createConditionGSON();
    private static final Gson MARKET_GSON = new GsonBuilder().setDateFormat("yyyyMMddHHmmss").create();

    public static Gson getConditionGSON() {
        return CONDITION_GSON;
    }

    public static Gson getMarketGSON() {
        return MARKET_GSON;
    }

    private static Gson createConditionGSON() {
        return new GsonBuilder()
                .setDateFormat("yyyy-MM-dd HH:mm:ss")
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(ConditionDTO.class)
                                                                     .registerSubtype(PriceConditionDTO.class)
                                                                     .registerSubtype(TurnPointConditionDTO.class)
                                                                     .registerSubtype(SimpleTimeConditionDTO.class)
                                                                     .registerSubtype(GridConditionDTO.class)
                                                                     .registerSubtype(NewStockPurchaseConditionDTO.class))
                .registerTypeAdapterFactory(RuntimeTypeAdapterFactory.of(SignalDTO.class)
                        .registerSubtype(BsSignalDTO.class)
                        .registerSubtype(CacheSyncSignalDTO.class)
                        .registerSubtype(BuySignalDTO.class)
                        .registerSubtype(SellSignalDTO.class)
                        .registerSubtype(ExpireSignalDTO.class))
                .create();
    }

    private ConditionOrderDTOGSONUtils() {
    }
}
