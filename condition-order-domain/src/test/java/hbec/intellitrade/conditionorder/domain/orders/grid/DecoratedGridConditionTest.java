package hbec.intellitrade.conditionorder.domain.orders.grid;

import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmInfo;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DelayConfirmOption;
import hbec.intellitrade.strategy.domain.condition.delayconfirm.DisabledDelayConfirm;
import hbec.intellitrade.strategy.domain.condition.deviation.DeviationCtrlInfo;
import hbec.intellitrade.strategy.domain.factor.BinaryFactorType;
import hbec.intellitrade.strategy.domain.factor.CompareOperator;
import hbec.intellitrade.strategy.domain.factor.IncrementBinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.InflexionFactor;
import hbec.intellitrade.strategy.domain.factor.PercentBinaryTargetPriceFactor;
import hbec.intellitrade.strategy.domain.factor.TargetPriceFactor;
import hbec.intellitrade.strategy.domain.signal.Signals;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.*;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/4/26
 */
public class DecoratedGridConditionTest {
    @Test
    public void testBasic() throws Exception {
        DecoratedGridCondition decoratedGridCondition1 = newDecoratedGridCondition();
        DecoratedGridCondition decoratedGridCondition2 = newDecoratedGridCondition();

        assertEquals(decoratedGridCondition1, decoratedGridCondition2);
        assertEquals(decoratedGridCondition1.hashCode(), decoratedGridCondition2.hashCode());
        System.out.println(decoratedGridCondition1);
    }

    private DecoratedGridCondition newDecoratedGridCondition() {
        BigDecimal basePrice = new BigDecimal("10.00");
        DelayConfirmInfo delayConfirm = new DelayConfirmInfo(DelayConfirmOption.CONTINUOUS, 3);
        DeviationCtrlInfo deviationCtrl = new DeviationCtrlInfo(new BigDecimal("1.00"));
        return new DecoratedGridCondition(basePrice,
                BinaryFactorType.PERCENT,
                new InflexionSubCondition(new IncrementBinaryTargetPriceFactor(
                        CompareOperator.LE, new BigDecimal("-0.50")),
                        new IncrementBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("0.10")),
                        basePrice,
                        true),
                new SimpleSubCondition(new IncrementBinaryTargetPriceFactor(
                        CompareOperator.GE, new BigDecimal("1.00")),
                        basePrice),
                true,
                delayConfirm,
                deviationCtrl,
                0,
                0);
    }

    private DecoratedGridCondition newDecoratedGridConditionNonDelay() {
        BigDecimal basePrice = new BigDecimal("10.00");
        DeviationCtrlInfo deviationCtrl = new DeviationCtrlInfo(new BigDecimal("1.00"));
        return new DecoratedGridCondition(basePrice,
                BinaryFactorType.PERCENT,
                new InflexionSubCondition(new PercentBinaryTargetPriceFactor(
                        CompareOperator.LE, new BigDecimal("-2.00")),
                        new PercentBinaryTargetPriceFactor(CompareOperator.GE, new BigDecimal("0.50")),
                        basePrice,
                        true),
                new SimpleSubCondition(new PercentBinaryTargetPriceFactor(
                        CompareOperator.GE, new BigDecimal("3.00")),
                        basePrice),
                true,
                DisabledDelayConfirm.DISABLED,
                deviationCtrl,
                0,
                0);
    }

    @Test
    public void testSellSimple() throws Exception {
        DecoratedGridCondition gridCondition = newDecoratedGridConditionNonDelay();
        TargetPriceFactor sellFactor;
        sellFactor = gridCondition.getSellCondition().getRawCondition().getTargetPriceFactor();
        assertEquals(sellFactor.getCompareOperator(), CompareOperator.GE);
        assertEquals(sellFactor.getTargetPrice(), new BigDecimal("10.300000"));

        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("10.00")), Signals.none());
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("10.29")), Signals.none());

        // 满足上涨条件
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("10.30")), Signals.sell());

        // 超出偏差控制
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("11.00")),
                Signals.sell().withDeviationExceeded());

        gridCondition.changeBasePrice(new BigDecimal("11.00"));
        assertEquals(gridCondition.getBasePrice(), new BigDecimal("11.00"));
        sellFactor = gridCondition.getSellCondition().getRawCondition().getTargetPriceFactor();
        assertEquals(sellFactor.getTargetPrice(), new BigDecimal("11.330000"));
        System.out.println(gridCondition);

        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("11.00")), Signals.none());

        // 继续上涨并触发
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("11.33")), Signals.sell());
    }

    @Test
    public void testBuyInflexion() throws Exception {
        DecoratedGridCondition gridCondition = newDecoratedGridConditionNonDelay();
        InflexionFactor buyFactor;
        buyFactor = (InflexionFactor) gridCondition.getBuyCondition().getRawCondition().getTargetPriceFactor();
        assertEquals(buyFactor.getBreakPriceFactor().getCompareOperator(), CompareOperator.LE);
        assertEquals(buyFactor.getBreakPriceFactor().getTargetPrice(), new BigDecimal("9.800000"));

        // 未突破
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.81")), Signals.none());
        assertFalse(gridCondition.isDirty());

        // 突破目标价9.8
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.80")), Signals.none());
        assertTrue(gridCondition.isDirty());
        gridCondition.clearDirty();

        // 继续下跌
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.75")), Signals.none());
        assertTrue(gridCondition.isDirty());
        gridCondition.clearDirty();

        // 反弹未触发
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.79")), Signals.none());

        // 反弹触发
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.80")), Signals.buy());

        // 超出偏差控制
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.90")),
                Signals.buy().withDeviationExceeded());

        gridCondition.changeBasePrice(new BigDecimal("9.90"));
        assertEquals(gridCondition.getBasePrice(), new BigDecimal("9.90"));
        buyFactor = (InflexionFactor) gridCondition.getBuyCondition().getRawCondition().getTargetPriceFactor();
        assertEquals(buyFactor.getBreakPriceFactor().getTargetPrice(), new BigDecimal("9.702000"));
        assertFalse(buyFactor.isBroken());
        assertFalse(buyFactor.getExtremePrice().isPresent());
        assertFalse(buyFactor.isDirty());
        System.out.println(gridCondition);
    }

    @Test
    public void testConfirmDelay() throws Exception {
        DecoratedGridCondition gridCondition = newDecoratedGridCondition();
        InflexionFactor buyFactor;
        buyFactor = (InflexionFactor) gridCondition.getBuyCondition().getRawCondition().getTargetPriceFactor();
        assertEquals(buyFactor.getBreakPriceFactor().getCompareOperator(), CompareOperator.LE);
        assertEquals(buyFactor.getBreakPriceFactor().getTargetPrice(), new BigDecimal("9.50"));

        // 未突破
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.51")), Signals.none());
        assertFalse(gridCondition.isDirty());

        // 突破目标价9.5
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.50")), Signals.none());
        assertTrue(gridCondition.isDirty());
        gridCondition.clearDirty();

        // 继续下跌
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.35")), Signals.none());
        assertTrue(gridCondition.isDirty());
        gridCondition.clearDirty();

        // 反弹未触发
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.44")), Signals.none());

        // 反弹触发，延迟确认
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.55")), Signals.none());
        assertTrue(gridCondition.isDirty());
        gridCondition.clearDirty();

        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.55")), Signals.none());
        assertEquals(gridCondition.onMarketTick(MockMarkets.withCurrentPrice("9.55")),
                Signals.buy().withDeviationExceeded());

        gridCondition.changeBasePrice(new BigDecimal("9.55"));
        assertEquals(gridCondition.getBasePrice(), new BigDecimal("9.55"));
        buyFactor = (InflexionFactor) gridCondition.getBuyCondition().getRawCondition().getTargetPriceFactor();
        assertEquals(buyFactor.getBreakPriceFactor().getTargetPrice(), new BigDecimal("9.05"));
        System.out.println(gridCondition);
    }
}