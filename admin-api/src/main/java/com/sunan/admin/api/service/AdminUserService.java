package com.sunan.admin.api.service;

import com.sunan.admin.api.domain.repository.AdminUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 通过用户名查询用户信息
 */
@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;


}
