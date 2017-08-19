package me.caosh.condition.infrastructure.trade;

import me.caosh.condition.domain.model.order.constant.ExchangeType;
import me.caosh.condition.domain.model.trade.EntrustCommand;
import me.caosh.condition.domain.model.trade.EntrustResult;
import me.caosh.condition.domain.service.SecurityEntrustService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by caosh on 2017/8/13.
 */
@Service
public class SecurityEntrustServiceMock implements SecurityEntrustService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityEntrustServiceMock.class);
    private static int theEntrustCode = 0;

    @Override
    public EntrustResult execute(EntrustCommand entrustCommand) {
        logger.info("Executing entrust command ==> {}", entrustCommand);
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
