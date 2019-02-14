package com.sunan.admin.api.model.admin.user;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserInfoResp {
    private String id;

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
     * 公司id
     */
    private String companyId;

    /**
     * 拥有权限菜单列表
     */
    private List<String> menuList;

    /**
     * 调查项目
     */
    private List<Integer> surveyItem;

    /**
     * 角色id列表
     */
    private List<String> roleId;

    /**
     * 是否为超级管理员
     */
    private Boolean superAdmin;

}
