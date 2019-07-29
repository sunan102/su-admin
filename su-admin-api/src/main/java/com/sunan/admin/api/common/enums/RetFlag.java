package com.sunan.admin.api.common.enums;

/**
 * 返回值标识
 */
public enum RetFlag {
    Success("成功", 0),
    Error("失败", 1);

    RetFlag(String text, Integer value) {
        this.text = text;
        this.value = value;
    }

    private String text;
    private int value;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
