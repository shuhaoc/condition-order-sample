package me.caosh.condition.application.trade;

import com.google.common.base.MoreObjects;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustSuccessResult;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.TradeCustomer;
import hbec.intellitrade.trade.domain.exception.InsufficientCapitalException;
import hbec.intellitrade.trade.domain.exception.InsufficientPositionException;
import hbec.intellitrade.trade.domain.exception.TradeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/7
 */
public class MockTradeCustomer implements TradeCustomer {
    private static final Logger logger = LoggerFactory.getLogger(MockTradeCustomer.class);

    private static int theEntrustCode = 0;
    private final String customerNo;

    public MockTradeCustomer(String customerNo) {
        this.customerNo = customerNo;
    }

    public String getCustomerNo() {
        return customerNo;
    }

    @Override
    public EntrustSuccessResult entrust(EntrustCommand entrustCommand) throws TradeException {
        logger.info("Executing entrust command for {} ==> {}", customerNo, entrustCommand);
        int seed = Integer.parseInt(entrustCommand.getSecurityInfo().getCode()) % 4;
        int mod = seed % 4;
        if (mod == 0) {
            int entrustCode = ++theEntrustCode;
            BigDecimal actualEntrustPrice = MoreObjects.firstNonNull(entrustCommand.getEntrustPrice(), new BigDecimal("12.34"));
            return new EntrustSuccessResult("委托成功,您这笔委托的合同号是:" + entrustCode, entrustCode, actualEntrustPrice);
        } else if (mod == 1) {
            if (entrustCommand.getExchangeType() == ExchangeType.BUY) {
                throw new InsufficientCapitalException("资金余额不足");
            } else {
                throw new InsufficientPositionException("超出证券可用数量");
            }
        } else if (mod == 2) {
            throw new RuntimeException("获取数据超时");
        } else {
            throw new TradeException("其他错误");
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MockTradeCustomer that = (MockTradeCustomer) o;

        return customerNo.equals(that.customerNo);
    }

    @Override
    public int hashCode() {
        return customerNo.hashCode();
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(MockTradeCustomer.class).omitNullValues()
                .add("customerNo", customerNo)
                .toString();
    }
}
