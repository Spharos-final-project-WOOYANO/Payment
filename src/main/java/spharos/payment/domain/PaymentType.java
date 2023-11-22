package spharos.payment.domain;

import spharos.payment.global.config.CodeValue;

public enum PaymentType implements CodeValue {

    CARD("0","카드"),
    EASY_PAYMENT("1","간편결제");


    private final String code;
    private final String value;

    PaymentType(String code, String value) {
        this.code = code;
        this.value = value;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getValue() {
        return value;
    }
}
