package hbec.intellitrade.condorder.domain.tradeplan;

import hbec.intellitrade.common.security.SecurityExchange;
import hbec.intellitrade.common.security.SecurityInfo;
import hbec.intellitrade.common.security.SecurityType;
import hbec.intellitrade.condorder.domain.trigger.TradingMarketSupplier;
import hbec.intellitrade.mock.MockMarkets;
import hbec.intellitrade.strategy.domain.signal.Signals;
import hbec.intellitrade.trade.domain.EntrustCommand;
import hbec.intellitrade.trade.domain.ExchangeType;
import hbec.intellitrade.trade.domain.OrderType;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;

/**
 * @author caoshuhao@touker.com
 * @date 2018/3/24
 */
public class BaseTradePlanTest {
    private static final SecurityInfo SECURITY_INFO = new SecurityInfo(SecurityType.STOCK,
                                                                       "600000",
                                                                       SecurityExchange.SH,
                                                                       "浦发银行");

    @Mock
    private TradingMarketSupplier tradingMarketSupplier;

    @BeforeMethod
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        when(tradingMarketSupplier.getTradingMarket()).thenReturn(MockMarkets.withCurrentPrice(new BigDecimal("10.10")));
    }

    @Test
    public void testOfferedPrice() throws Exception {
        OfferedPriceTradePlan offeredPriceTradePlan = new OfferedPriceTradePlan(ExchangeType.BUY,
                                                                                EntrustStrategy.SELL2,
                                                                                new TradeNumberDirect(100));
        OfferedPriceTradePlan offeredPriceTradePlan1 = new OfferedPriceTradePlan(ExchangeType.BUY,
                                                                                 EntrustStrategy.SELL2,
                                                                                 new TradeNumberDirect(100));
        assertEquals(offeredPriceTradePlan, offeredPriceTradePlan1);
        assertEquals(offeredPriceTradePlan.hashCode(), offeredPriceTradePlan1.hashCode());
        System.out.println(offeredPriceTradePlan);

        assertEquals(offeredPriceTradePlan.getExchangeType(), ExchangeType.BUY);
        assertEquals(offeredPriceTradePlan.getEntrustStrategy(), EntrustStrategy.SELL2);
        assertEquals(offeredPriceTradePlan.getTradeNumber(), new TradeNumberDirect(100));
        assertEquals(offeredPriceTradePlan.getOrderType(), OrderType.LIMITED);

        EntrustCommand entrustCommand = offeredPriceTradePlan.createEntrustCommand(
                Signals.buyOrSell(),
                SECURITY_INFO,
                tradingMarketSupplier);
        assertEquals(entrustCommand, new EntrustCommand(SECURITY_INFO,
                                                        ExchangeType.BUY,
                                                        new BigDecimal("10.11"),
                                                        100,
                                                        OrderType.LIMITED));
        verify(tradingMarketSupplier, times(1)).getTradingMarket();
    }

    @Test
    public void testCustomizedPriceTradePlan() throws Exception {
        CustomizedPriceTradePlan customizedPriceTradePlan = new CustomizedPriceTradePlan(
                ExchangeType.BUY,
                new BigDecimal("10.23"),
                new TradeNumberByAmount(new BigDecimal("2100")));
        CustomizedPriceTradePlan customizedPriceTradePlan1 = new CustomizedPriceTradePlan(
                ExchangeType.BUY,
                new BigDecimal("10.23"),
                new TradeNumberByAmount(new BigDecimal("2100")));

        assertEquals(customizedPriceTradePlan, customizedPriceTradePlan1);
        assertEquals(customizedPriceTradePlan.hashCode(), customizedPriceTradePlan1.hashCode());
        System.out.println(customizedPriceTradePlan);

        assertEquals(customizedPriceTradePlan.getEntrustStrategy(), EntrustStrategy.CUSTOMIZED_PRICE);
        assertEquals(customizedPriceTradePlan.getEntrustPrice(), new BigDecimal("10.23"));
        assertEquals(customizedPriceTradePlan.getOrderType(), OrderType.LIMITED);

        EntrustCommand entrustCommand = customizedPriceTradePlan.createEntrustCommand(Signals.buyOrSell(),
                                                                                      SECURITY_INFO,
                                                                                      tradingMarketSupplier);
        assertEquals(entrustCommand, new EntrustCommand(SECURITY_INFO,
                                                        ExchangeType.BUY,
                                                        new BigDecimal("10.23"),
                                                        200,
                                                        OrderType.LIMITED));

        verify(tradingMarketSupplier, never()).getTradingMarket();
    }

    @Test
    public void testMarketPriceTradePlanWithTradeNumberDirect() throws Exception {
        MarketPriceTradePlan marketPriceTradePlan = new MarketPriceTradePlan(
                ExchangeType.BUY, new TradeNumberDirect(100), OrderType.SH_WDJSCJSYCX);
        MarketPriceTradePlan marketPriceTradePlan1 = new MarketPriceTradePlan(
                ExchangeType.BUY, new TradeNumberDirect(100), OrderType.SH_WDJSCJSYCX);

        assertEquals(marketPriceTradePlan, marketPriceTradePlan1);
        assertEquals(marketPriceTradePlan.hashCode(), marketPriceTradePlan1.hashCode());
        System.out.println(marketPriceTradePlan);

        assertEquals(marketPriceTradePlan.getEntrustStrategy(), EntrustStrategy.MARKET_PRICE);
        assertEquals(marketPriceTradePlan.getOrderType(), OrderType.SH_WDJSCJSYCX);

        EntrustCommand entrustCommand = marketPriceTradePlan.createEntrustCommand(Signals.buyOrSell(),
                                                                                  SECURITY_INFO,
                                                                                  tradingMarketSupplier);
        assertEquals(entrustCommand, new EntrustCommand(SECURITY_INFO,
                                                        ExchangeType.BUY,
                                                        null,
                                                        100,
                                                        OrderType.SH_WDJSCJSYCX));
        // 数量下单情况下，市价委托不需要行情
        verify(tradingMarketSupplier, never()).getTradingMarket();
    }

    @Test
    public void testMarketPriceTradePlanWithTradeNumberByAmount() throws Exception {
        MarketPriceTradePlan marketPriceTradePlan = new MarketPriceTradePlan(
                ExchangeType.BUY, new TradeNumberByAmount(new BigDecimal("3000")), OrderType.SH_WDJSCJSYCX);

        assertEquals(marketPriceTradePlan.getEntrustStrategy(), EntrustStrategy.MARKET_PRICE);
        assertEquals(marketPriceTradePlan.getOrderType(), OrderType.SH_WDJSCJSYCX);

        EntrustCommand entrustCommand = marketPriceTradePlan.createEntrustCommand(Signals.buyOrSell(),
                                                                                  SECURITY_INFO,
                                                                                  tradingMarketSupplier);
        assertEquals(entrustCommand, new EntrustCommand(SECURITY_INFO,
                                                        ExchangeType.BUY,
                                                        null,
                                                        200,
                                                        OrderType.SH_WDJSCJSYCX));

        verify(tradingMarketSupplier, times(1)).getTradingMarket();
    }
}
