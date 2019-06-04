package com.sunan.admin.api.model.admin.user;

import com.sunan.admin.api.model.admin.menu.MenuListResp;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class AdminUserInfoResp {
    private String adminUserId;
    private String username;
    private Integer status;
    private String adminRoleId;
    private String roleName;
    private List<MenuListResp> menuList;
}
