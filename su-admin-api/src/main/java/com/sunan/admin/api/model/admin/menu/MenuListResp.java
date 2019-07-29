package com.sunan.admin.api.model.admin.menu;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuListResp {
    private String adminMenuId;
    private String menuName;
    private String vueName;
    private String url;
    private String page;
    private String icon;
    private Integer type;
    private Integer num;
    private String pid;
    private String mark;
    private List<MenuListResp> children;
}
