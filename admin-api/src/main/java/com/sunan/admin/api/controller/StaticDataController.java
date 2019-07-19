package com.sunan.admin.api.controller;

import com.sunan.admin.api.common.enums.AdminUserStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/static_data")
public class StaticDataController {
    @RequestMapping("/getAdminUserStatus")
    public Object test() {
        return AdminUserStatus.getSelectItemList();
    }
}
