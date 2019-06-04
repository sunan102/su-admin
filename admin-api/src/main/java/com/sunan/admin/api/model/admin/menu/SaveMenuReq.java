package com.sunan.admin.api.model.admin.menu;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SaveMenuReq {
    private String adminMenuId;
    private String menuName;
    private String url;
    private String pid;
    private String mark;
    private Integer num;
}
