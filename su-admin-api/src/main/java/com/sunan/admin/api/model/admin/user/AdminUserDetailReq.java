package com.sunan.admin.api.model.admin.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserDetailReq {

    /**
     * id
     */
    private String adminUserId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 状态
     */
    private Integer status;
}
