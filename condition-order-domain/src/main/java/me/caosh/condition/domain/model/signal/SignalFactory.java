package me.caosh.condition.domain.model.signal;

/**
 * Created by caosh on 2017/8/1.
 */
public class SignalFactory {
    private static final SignalFactory INSTANCE = new SignalFactory();

    public static SignalFactory getInstance() {
        return INSTANCE;
    }

    private static final General general = new General();
    private static final None none = new None();
    private static final CacheSync cacheSync = new CacheSync();
    private static final Buy buy = new Buy();
    private static final Sell sell = new Sell();

    public None none() {
        return none;
    }

    public General general() {
        return general;
    }

    public CacheSync cacheSync() {
        return cacheSync;
    }

    public Buy buy() {
        return buy;
    }

    public Sell sell() {
        return sell;
    }

    private SignalFactory() {
    }
}
