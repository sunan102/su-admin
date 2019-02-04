package com.sunan.admin.api.controller;

import com.sunan.admin.api.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user")
public class AdminUserController {
    @Autowired
    private AdminUserService adminUserService;


    /**
     * 获取当前登录用户信息
     *
     * @param authentication
     * @return
     */
    @RequestMapping("/info")
    public Object info(OAuth2Authentication authentication) {
        String id = authentication.getPrincipal().toString();
        return adminUserService.getAdminUserInfo(id);
    }
}
