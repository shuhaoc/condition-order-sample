package hbec.intellitrade.condorder.domain.tradeplan;

import me.caosh.condition.domain.model.constants.EntrustMethod;

import java.math.BigDecimal;

/**
 * 委托数量计算功能
 * <p>
 * Created by caosh on 2017/8/17.
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/24
 */
public interface TradeNumber {
    void accept(TradeNumberVisitor visitor);

    /**
     * 委托方法
     *
     * @return 委托方法
     */
    EntrustMethod getEntrustMethod();

    /**
     * 基于给定的委托价格计算委托数量
     *
     * @param entrustPrice 委托价格
     * @return 委托数量
     */
    int getNumber(BigDecimal entrustPrice);
}
