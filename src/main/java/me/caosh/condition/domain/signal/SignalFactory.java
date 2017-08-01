package me.caosh.condition.domain.signal;

/**
 * Created by caosh on 2017/8/1.
 */
public class SignalFactory {
    private static final SignalFactory INSTANCE = new SignalFactory();

    public static SignalFactory getInstance() {
        return INSTANCE;
    }

    private static final TBD tbd = new TBD();
    private static final None none = new None();

    public None none() {
        return none;
    }

    public TBD toBeDetermined() {
        return tbd;
    }

    private SignalFactory() {
    }
}
