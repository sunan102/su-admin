package com.sunan.admin.api.model.admin.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDetailResp {

    /**
     * id
     */
    private String adminUserId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 真实姓名
     */
    private String realName;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 工号
     */
    private String jobNo;

    /**
     * 公司id
     */
    private String adminCompanyId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 角色id
     */
    private String adminRoleId;
}
