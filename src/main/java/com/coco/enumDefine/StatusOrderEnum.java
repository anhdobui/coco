package com.coco.enumDefine;

import com.coco.exception.CustomRuntimeException;

public enum StatusOrderEnum {
    Ordered(1),
    Delivery(2),
    Completed(3),

    Canceled(0);

    private final int value;

    private StatusOrderEnum(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
    public static StatusOrderEnum fromString(String status) {
        switch (status) {
            case "Ordered":
                return Ordered;
            case "Delivery":
                return Delivery;
            case "Completed":
                return Completed;
            case "Canceled":
                return Canceled;
            default:
                throw new CustomRuntimeException("Unknown status: " + status);
        }
    }
    public static StatusOrderEnum fromValue(int value) {
        for (StatusOrderEnum status : StatusOrderEnum.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new CustomRuntimeException("Invalid value for StatusOrderEnum: " + value);
    }
}
