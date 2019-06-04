package com.sunan.admin.api.model.admin.menu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MenuListResp {
    private String adminMenuId;
    private String menuName;
    private String url;
    private String pid;
    private Integer num;
    private String mark;
}
