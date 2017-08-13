package me.caosh.condition.infrastructure.trade;

import me.caosh.condition.domain.model.trade.EntrustCommand;
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

    @Override
    public void execute(EntrustCommand entrustCommand) {
        logger.info("Executing entrust command ==> {}", entrustCommand);
    }
}
