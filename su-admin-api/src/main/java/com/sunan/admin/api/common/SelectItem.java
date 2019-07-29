package com.sunan.admin.api.common;

/**
 * Created by Administrator on 2018/4/21.
 */
public class SelectItem {
    public SelectItem() {

    }

    public SelectItem(String text, String value, boolean selected) {
        this.text = text;
        this.value = value;
        this.selected = selected;
    }

    private String text;
    private String value;
    private boolean selected;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public boolean getSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }
}
