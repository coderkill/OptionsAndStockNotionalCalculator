package common;

import java.util.HashMap;

public enum Currency {
    USD((short)1),
    EUR((short)2),
    CAD((short)3),
    AUD((short)4),
    GBP((short)5);
    private static HashMap<Short, Currency> values = new HashMap();
    private short value;

    private Currency(short value) {
        this.value = value;
    }

    public static Currency fromValue(short value) {
        return (Currency)values.get(value);
    }

    public int getValue() {
        return this.value;
    }
}
