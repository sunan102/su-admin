package com.sunan.admin.api.controller;

import com.sunan.admin.api.model.admin.user.AdminUserDetailReq;
import com.sunan.admin.api.model.admin.user.ResetPwdReq;
import com.sunan.admin.api.model.admin.user.UserListReq;
import com.sunan.admin.api.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/getList")
    public Object getList(UserListReq req) {
        return adminUserService.getList(req);
    }

    @GetMapping("/getDetail")
    public Object getDetail(String id) {
        return adminUserService.getDetail(id);
    }

    @PostMapping("/save")
    public Object save(@RequestBody AdminUserDetailReq req) {
        return adminUserService.save(req);
    }

    @PostMapping("/disable")
    public Object disable(String id) {
        return adminUserService.disable(id);
    }

    @PostMapping("/enable")
    public Object enable(String id) {
        return adminUserService.enable(id);
    }

    @PostMapping("/resetPwd")
    public Object resetPwd(@RequestBody ResetPwdReq req) {
        return adminUserService.resetPwd(req);
    }

}
