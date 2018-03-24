package hbec.intellitrade.condorder.domain.tradeplan;

import com.google.common.base.MoreObjects;
import me.caosh.autoasm.AutoAssemblers;

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
        EntrustMethod theEntrustMethod = AutoAssemblers.getDefault()
                                                       .disassemble(intEntrustMethod, EntrustMethod.class);
        if (theEntrustMethod == EntrustMethod.NUMBER) {
            return new TradeNumberDirect(number);
        } else if (theEntrustMethod == EntrustMethod.AMOUNT) {
            return new TradeNumberByAmount(entrustAmount);
        } else {
            throw new IllegalArgumentException("entrustMethod=" + theEntrustMethod);
        }
    }

    private TradeNumberFactory() {
    }
}
