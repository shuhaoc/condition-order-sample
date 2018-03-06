package me.caosh.condition.domain.adapter;

import me.caosh.condition.domain.model.order.TradeSystemAdapter;
import me.caosh.condition.domain.model.order.constant.ExchangeType;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.EntrustResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/2
 */
public enum MockTradeSystemAdapter implements TradeSystemAdapter {
    /**
     * 单例
     */
    INSTANCE;

    private static final Logger logger = LoggerFactory.getLogger(MockTradeSystemAdapter.class);

    private static int theEntrustCode = 0;

    @Override
    public EntrustResult entrust(String customerNo, EntrustCommand entrustCommand) {
        logger.info("Executing entrust command for {} ==> {}", customerNo, entrustCommand);
        int seed = Integer.parseInt(entrustCommand.getSecurityInfo().getCode()) % 4;
        int mod = seed % 4;
        if (mod == 0) {
            int entrustCode = ++theEntrustCode;
            return EntrustResult.ofSuccess("委托成功,您这笔委托的合同号是:" + entrustCode, entrustCode);
        } else if (mod == 1) {
            if (entrustCommand.getExchangeType() == ExchangeType.BUY) {
                return EntrustResult.ofFail(-1, "资金余额不足");
            } else {
                return EntrustResult.ofFail(-2, "超出证券可用数量");
            }
        } else if (mod == 2) {
            return EntrustResult.ofFail(-3, "获取数据超时");
        } else {
            return EntrustResult.ofFail(-4, "其他错误");
        }
    }
}
