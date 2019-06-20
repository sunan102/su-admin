package com.sunan.admin.api.common;

import org.springframework.stereotype.Component;

@Component
public class AppConfig {
    /**
     * 默认每页容量
     */
    public final static int DefaultPageSize = 10;
    /**
     * 超级管理员的role_id
     */
    public final static String SuperAdminRoleId = "0";

}
