package com.sunan.admin.api.common.enums;


import com.sunan.admin.api.common.SelectItem;

import java.util.ArrayList;
import java.util.List;

public enum AdminUserStatus {
    Enable("启用", 0),
    Disable("禁用", 1)
    ;

    AdminUserStatus(String text, Integer value) {
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

    public static List<SelectItem> getSelectItemList() {
        return getSelectItemList(0);
    }

    public static List<SelectItem> getSelectItemList(int value) {
        List<SelectItem> list = new ArrayList<>();
        for (AdminUserStatus e : AdminUserStatus.values()) {
            boolean isSelected = false;
            if (value == e.getValue()) {
                isSelected = true;
            }
            list.add(new SelectItem(e.getText(), String.valueOf(e.getValue()), isSelected));
        }
        return list;
    }

    public static String getTextByValue(Integer value) {
        if (value != null) {
            for (AdminUserStatus e : AdminUserStatus.values()) {
                if (e.getValue() == value) {
                    return e.getText();
                }
            }
        }
        return "";
    }
}
