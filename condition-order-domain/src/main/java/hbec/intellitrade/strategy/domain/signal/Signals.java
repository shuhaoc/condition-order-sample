package hbec.intellitrade.strategy.domain.signal;

/**
 * 信号单例工具类
 *
 * @author caosh/caoshuhao@touker.com
 * @date 2018/1/29
 */
public class Signals {
    private static final BS BS = new BS();
    private static final None NONE = new None();
    private static final CacheSync CACHE_SYNC = new CacheSync();
    private static final Buy BUY = new Buy();
    private static final Sell SELL = new Sell();
    private static final Expire EXPIRE = new Expire();
    private static final CrossBaseline CROSS_BASELINE = new CrossBaseline();
    private static final AutoPurchase AUTO_PURCHASE = new AutoPurchase();

    public static BS buyOrSell() {
        return BS;
    }

    public static None none() {
        return NONE;
    }

    public static CacheSync cacheSync() {
        return CACHE_SYNC;
    }

    public static Buy buy() {
        return BUY;
    }

    public static Buy buy(boolean deviationExceeded) {
        if (!deviationExceeded) {
            return BUY;
        }
        return (Buy) BUY.withDeviationExceeded();
    }

    public static Sell sell() {
        return SELL;
    }

    public static Sell sell(boolean deviationExceeded) {
        if (!deviationExceeded) {
            return SELL;
        }
        return (Sell) SELL.withDeviationExceeded();
    }

    public static Expire expire() {
        return EXPIRE;
    }

    public static CrossBaseline crossBaseline() {
        return CROSS_BASELINE;
    }

    public static AutoPurchase autoPurchase() {
        return AUTO_PURCHASE;
    }

    private Signals() {
    }
}
