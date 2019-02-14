package com.sunan.admin.api.model.admin.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListResp {
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
     * 所属角色
     */
    private String roleName;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 工号
     */
    private String jobNo;

    /**
     * 所属单位
     */
    private String companyName;

    /**
     * 状态
     */
    private Integer status;

}
