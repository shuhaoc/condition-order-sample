package me.caosh.condition.domain.model.signalpayload;

import org.joda.time.LocalTime;

/**
 * @author caosh/caoshuhao@touker.com
 * @date 2018/2/1
 */
public class SignalPayloads {
    public static int triggerId(int strategyId, LocalTime localTime) {
        // in [0,21600], 15 bits
        int secondsFrom9Clock = localTime.minusHours(9).getMillisOfDay() / 1000;

        return 0x7FFFFFFF  /* 符号位取0，保证正数 */ & (
                0x40000000 /* 第二位取1，保证数值大于10亿 */
                        | ((strategyId & 0x00007FFF) << 15) /* 策略ID取后15位，置于3-17位 */
                        | (secondsFrom9Clock & 0x00007FFF) /* 秒级时间，置于后15位 */
        );
    }
}
