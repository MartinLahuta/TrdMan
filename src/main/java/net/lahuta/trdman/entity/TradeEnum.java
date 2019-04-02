package net.lahuta.trdman.entity;

import java.util.EnumSet;

public enum TradeEnum {
    QUANTITY("Quantity"),
    AMOUNT("Amount"),
    PRICE("Price"),
    BUYSELLTYP("BuySellTyp"),
    CURRENCY("Currency"),
    TRDNO("TradeNo"),
    MEMBERID("MemberId"),
    ISIN("ISIN"),
    STLDATE("SettlementDate");

    private final String valueName;

    public static final EnumSet<TradeEnum> ALL_VALUES = EnumSet.allOf(TradeEnum.class);

    TradeEnum(String valueName) {
        this.valueName = valueName;
    }

    public static boolean containsAll(EnumSet<TradeEnum> tradeValues) {
        return ALL_VALUES.containsAll(tradeValues);
    }

    public String getValueName() {
        return this.valueName;
    }

    @Override
    public String toString() {
        return this.valueName;
    }

}
