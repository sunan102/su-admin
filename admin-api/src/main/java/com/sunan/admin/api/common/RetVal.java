package com.sunan.admin.api.common;

import com.sunan.admin.api.common.enums.RetFlag;

/**
 * Created by Administrator on 2018/4/12.
 */
public class RetVal<T> {
    /**
     * 0-正常；大于1为异常
     */
    private int flag;
    private String msg;
    private T data;

    public RetVal() {
    }

    public RetVal(RetFlag flag, String msg) {
        this.flag = flag.getValue();
        this.msg = msg;
    }

    public RetVal(int flag, String msg) {
        this.flag = flag;
        this.msg = msg;
    }

    public RetVal(RetFlag flag, String msg, T data) {
        this.flag = flag.getValue();
        this.msg = msg;
        this.data = data;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
