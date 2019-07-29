package com.sunan.admin.api.service;

import com.sunan.admin.api.common.RetVal;
import com.sunan.admin.api.common.enums.RetFlag;
import com.sunan.admin.api.common.strings.MsgStatic;
import com.sunan.admin.api.domain.dao.CommonQuery;
import com.sunan.admin.api.domain.entity.AdminMenu;
import com.sunan.admin.api.domain.repository.AdminMenuRepository;
import com.sunan.admin.api.model.admin.menu.MenuListResp;
import com.sunan.admin.api.model.admin.menu.SaveMenuReq;
import com.sunan.admin.api.utils.IdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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
     * @return
     */
    public RetVal<List<MenuListResp>> getList() {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sb.append("SELECT * ");
        sb.append("FROM admin_menu ");
        sb.append("WHERE 1=1 ");
        sb.append("ORDER BY num ");
        List<MenuListResp> list = commonQuery.queryListEntity(sb, params, MenuListResp.class);
        return new RetVal<>(RetFlag.Success, null, list);
    }
//
//    /**
//     * 生成菜单树
//     *
//     * @param list
//     * @return
//     */
//    public List<MenuListResp> createMenuTree(List<MenuListResp> list, String menuId) {
//        List<MenuListResp> retList = new ArrayList<>();
//        if (list != null && list.size() > 0) {
//            for (MenuListResp menuListResp : list) {
//                if (menuListResp.getPid().equalsIgnoreCase(menuId)) {
//                    menuListResp.setChildren(createMenuTree(list, menuListResp.getAdminMenuId()));
//                    retList.add(menuListResp);
//                }
//            }
//        }
//        return retList;
//    }

    /**
     * 保存
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public RetVal save(SaveMenuReq req) {
        AdminMenu adminMenu = null;
        if (StringUtils.isEmpty(req.getAdminMenuId())) {
            adminMenu = new AdminMenu();
            BeanUtils.copyProperties(req, adminMenu);
            adminMenu.setAdminMenuId(IdUtils.createUuid());
        } else {
            //修改
            adminMenu = adminMenuRepository.getOne(req.getAdminMenuId());
            BeanUtils.copyProperties(req, adminMenu);
        }
        adminMenuRepository.save(adminMenu);

        return new RetVal(RetFlag.Success, MsgStatic.OperationSuccess, adminMenu.getAdminMenuId());
    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    public RetVal delete(String id) {
        adminMenuRepository.deleteById(id);
        return new RetVal(RetFlag.Success, MsgStatic.OperationSuccess);
    }


}
