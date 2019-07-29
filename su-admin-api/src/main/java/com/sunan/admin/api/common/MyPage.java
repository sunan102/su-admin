package com.sunan.admin.api.common;


import java.util.List;

/**
 * Created by Administrator on 2018/4/13.
 */
public class MyPage<T> {

    public MyPage(Integer nowPage, Integer totalElement, Integer size ,List<T>list) {
        this.nowPage = nowPage;
        this.totalElement = totalElement;
        this.size = size;
        this.totalPage = (int) (Math.ceil(totalElement / (double) size));
        this.list = list;
    }

    private Integer nowPage;//当前页码
    private Integer totalElement;//总元素个数
    private Integer totalPage;//总页数
    private Integer size;//每页元素个数
    private List<T> list;//元素列表

    public Integer getNowPage() {
        return nowPage;
    }

    public void setNowPage(Integer nowPage) {
        this.nowPage = nowPage;
    }

    public Integer getTotalElement() {
        return totalElement;
    }

    public void setTotalElement(Integer totalElement) {
        this.totalElement = totalElement;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
