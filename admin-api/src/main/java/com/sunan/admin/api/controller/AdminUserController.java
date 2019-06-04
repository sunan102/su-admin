package com.sunan.admin.api.controller;

import com.sunan.admin.api.common.RetVal;
import com.sunan.admin.api.model.admin.user.AdminUserInfoResp;
import com.sunan.admin.api.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.GetMapping;
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

    /**
     * 获取当前登录用户的权限菜单
     *
     * @param authentication
     * @return
     */
    @GetMapping("/getMenuList")
    public Object getMenuList(OAuth2Authentication authentication) {
        String id = authentication.getPrincipal().toString();
        RetVal<AdminUserInfoResp> userInfoResp = adminUserService.getAdminUserInfo(id);
        return adminUserService.getAdminUserMenuByRoleId(userInfoResp.getData().getAdminRoleId());
    }

}
