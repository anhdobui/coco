package com.coco.enumDefine;

import com.coco.exception.CustomRuntimeException;

public enum StatusPaymentEnum {
    Paid(1),
    Unpaid(0),
    Refunded(2);

    private final int value;

    private StatusPaymentEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public static int toInt(StatusPaymentEnum status) {
        return status.getValue();
    }
    public static StatusPaymentEnum fromString(String status) {
        for (StatusPaymentEnum statusEnum : StatusPaymentEnum.values()) {
            if (statusEnum.name().equalsIgnoreCase(status)) {
                return statusEnum;
            }
        }
        throw new CustomRuntimeException("No constant with status: " + status);
    }
    public static StatusPaymentEnum fromValue(int value) {
        for (StatusPaymentEnum statusEnum : StatusPaymentEnum.values()) {
            if (statusEnum.getValue() == value) {
                return statusEnum;
            }
        }
        throw new CustomRuntimeException("No constant with value: " + value);
    }
}
