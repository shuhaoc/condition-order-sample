package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;
import hbec.intellitrade.common.ValuedEnumUtil;

import java.math.BigDecimal;

/**
 * Created by caosh on 2017/8/18.
 */
public class TradeNumberFactory {
    private static final TradeNumberFactory INSTANCE = new TradeNumberFactory();

    public static TradeNumberFactory getInstance() {
        return INSTANCE;
    }

    public TradeNumber create(Integer entrustMethod, Integer number, BigDecimal entrustAmount) {
        int intEntrustMethod = MoreObjects.firstNonNull(entrustMethod, EntrustMethod.NUMBER.getValue());
        EntrustMethod theEntrustMethod = ValuedEnumUtil.valueOf(intEntrustMethod, EntrustMethod.class);
        if (theEntrustMethod == EntrustMethod.NUMBER) {
            return new TradeNumberDirect(number);
        } else {
            Preconditions.checkArgument(theEntrustMethod ==  EntrustMethod.AMOUNT);
            return new TradeNumberByAmount(entrustAmount);
        }
    }

    private TradeNumberFactory() {
    }
}
