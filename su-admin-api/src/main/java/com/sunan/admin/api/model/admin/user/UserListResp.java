package com.sunan.admin.api.model.admin.user;

import com.sunan.admin.api.common.enums.AdminUserStatus;
import com.sunan.admin.api.utils.CommonUtils;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserListResp {
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

    /**
     * 创建时间
     */
    private Date createDate;

    /**
     * 创建时间
     */
    private String createDateStr;

    public String getCreateDateStr() {
        return CommonUtils.getDateStr1(createDate);
    }
}
