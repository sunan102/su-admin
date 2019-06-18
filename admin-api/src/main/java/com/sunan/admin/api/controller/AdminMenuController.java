package com.sunan.admin.api.controller;

import com.sunan.admin.api.model.admin.menu.SaveMenuReq;
import com.sunan.admin.api.service.AdminMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/menu")
public class AdminMenuController {
    @Autowired
    private AdminMenuService adminMenuService;


    /**
     * 获取列表
     *
     * @return
     */
    @GetMapping("/getList")
    public Object getList() {
        return adminMenuService.getList();
    }

    /**
     * 保存
     *
     * @return
     */
    @PostMapping("/save")
    public Object save(@RequestBody SaveMenuReq req) {
        return adminMenuService.save(req);
    }

    /**
     * 删除
     *
     * @return
     */
    @PostMapping("/delete")
    public Object delete(String id) {
        return adminMenuService.delete(id);
    }

}
