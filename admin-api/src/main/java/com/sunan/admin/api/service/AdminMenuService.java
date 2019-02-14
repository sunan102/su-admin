package com.sunan.admin.api.service;

import com.sunan.admin.api.common.RetVal;
import com.sunan.admin.api.domain.dao.CommonQuery;
import com.sunan.admin.api.domain.repository.AdminMenuRepository;
import com.sunan.admin.api.model.admin.menu.MenuListReq;
import com.sunan.admin.api.model.admin.menu.MenuListResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdminMenuService {
    @Autowired
    private CommonQuery commonQuery;
    @Autowired
    private AdminMenuRepository adminMenuRepository;


    /**
     * 获取列表
     *
     * @param req
     * @return
     */
    public RetVal<List<MenuListResp>> getList(MenuListReq req) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sb.append("SELECT * ");
        sb.append("FROM admin_menu ");
        sb.append("WHERE 1=1 ");
        sb.append("ORDER BY num ");
        RetVal<List<MenuListResp>> retVal = commonQuery.queryListEntityWithRetVal(sb, params, MenuListResp.class);
        return retVal;
    }



}
