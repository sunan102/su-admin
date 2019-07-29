package com.sunan.admin.api.model.admin.user;

import com.sunan.admin.api.common.enums.AdminUserStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserDetailResp {

    /**
     * id
     */
    private String adminUserId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 状态
     */
    private String statusStr;

    public String getStatusStr() {
        return AdminUserStatus.getTextByValue(status);
    }

}
