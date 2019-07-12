package com.sunan.admin.api.model.admin.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AdminUserDetailReq {

    /**
     * id
     */
    private String id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户名
     */
    private String password;

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
    private String companyId;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 角色id
     */
    private String roleId;
}
