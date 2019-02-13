package com.sunan.admin.api.service;

import com.sunan.admin.api.common.RetVal;
import com.sunan.admin.api.domain.entity.AdminUser;
import com.sunan.admin.api.domain.repository.AdminUserRepository;
import com.sunan.admin.api.model.admin.user.AdminUserInfoResp;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * 通过用户名查询用户信息
 */
@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    /**
     * 获取用户信息
     *
     * @return
     */
    public RetVal<AdminUserInfoResp> getAdminUserInfo(String id) {
        AdminUserInfoResp userInfoResp = new AdminUserInfoResp();
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        if (adminUser.isPresent()) {
            BeanUtils.copyProperties(adminUser.get(), userInfoResp);
        }
        return new RetVal<>(0,null,userInfoResp);
    }
}
