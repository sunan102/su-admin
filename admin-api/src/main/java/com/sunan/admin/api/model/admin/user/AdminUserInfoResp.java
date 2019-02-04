package com.sunan.admin.api.model.admin.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserInfoResp {
    private String adminUserId;
    private String username;
    private Integer status;
}
