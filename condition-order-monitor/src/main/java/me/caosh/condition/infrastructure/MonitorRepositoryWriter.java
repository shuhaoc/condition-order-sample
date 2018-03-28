package me.caosh.condition.infrastructure;

import hbec.intellitrade.condorder.domain.ConditionOrder;
import hbec.intellitrade.strategy.domain.Strategy;
import hbec.intellitrade.strategy.container.StrategyWriter;
import me.caosh.condition.infrastructure.repository.MonitorRepository;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/3/12
 */
public class MonitorRepositoryWriter implements StrategyWriter {
    private final MonitorRepository monitorRepository;

    public MonitorRepositoryWriter(MonitorRepository monitorRepository) {
        this.monitorRepository = monitorRepository;
    }

    @Override
    public void write(Strategy strategy) {
        monitorRepository.update((ConditionOrder) strategy);
    }
}
