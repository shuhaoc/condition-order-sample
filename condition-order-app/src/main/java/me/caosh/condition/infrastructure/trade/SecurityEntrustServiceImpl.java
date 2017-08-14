package me.caosh.condition.infrastructure.trade;

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
public class SecurityEntrustServiceImpl implements SecurityEntrustService {
    private static final Logger logger = LoggerFactory.getLogger(SecurityEntrustServiceImpl.class);
    private static int theEntrustCode = 0;

    @Override
    public EntrustResult execute(EntrustCommand entrustCommand) {
        logger.info("Executing entrust command ==> {}", entrustCommand);
        int entrustCode = ++theEntrustCode;
        return new EntrustResult(1, "委托成功,您这笔委托的合同号是:" + entrustCode, entrustCode);
    }
}
