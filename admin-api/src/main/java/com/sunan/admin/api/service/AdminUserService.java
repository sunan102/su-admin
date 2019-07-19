package com.sunan.admin.api.service;

import com.sunan.admin.api.common.AppConfig;
import com.sunan.admin.api.common.MyPage;
import com.sunan.admin.api.common.RetVal;
import com.sunan.admin.api.common.enums.AdminUserStatus;
import com.sunan.admin.api.common.enums.RetFlag;
import com.sunan.admin.api.common.strings.MsgStatic;
import com.sunan.admin.api.domain.dao.CommonQuery;
import com.sunan.admin.api.domain.entity.AdminUser;
import com.sunan.admin.api.domain.entity.AdminUserRole;
import com.sunan.admin.api.domain.repository.AdminRoleRepository;
import com.sunan.admin.api.domain.repository.AdminUserRepository;
import com.sunan.admin.api.domain.repository.AdminUserRoleRepository;
import com.sunan.admin.api.model.admin.menu.MenuListResp;
import com.sunan.admin.api.model.admin.user.*;
import com.sunan.admin.api.utils.IdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private AdminRoleRepository adminRoleRepository;
    @Autowired
    private AdminUserRoleRepository adminUserRoleRepository;
    @Autowired
    private CommonQuery commonQuery;

    /**
     * 获取用户信息
     *
     * @param id 用户id
     * @return
     */
    public RetVal<AdminUserInfoResp> getAdminUserInfo(String id) {
        AdminUserInfoResp userInfoResp = new AdminUserInfoResp();
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        if (adminUser.isPresent()) {
            BeanUtils.copyProperties(adminUser.get(), userInfoResp);
            //查询role
            List<AdminUserRole> adminUserRoleList = adminUserRoleRepository.findByAdminUserId(id);
            if (adminUserRoleList != null) {
                Map<String, MenuListResp> menuListRespMap = new HashMap<>();
                for (AdminUserRole adminUserRole : adminUserRoleList) {
                    RetVal<List<MenuListResp>> menuList = getAdminUserMenuByRoleId(adminUserRole.getAdminRoleId());
                    if (menuList.getData() != null) {
                        for (MenuListResp menuListResp : menuList.getData()) {
                            if (!menuListRespMap.containsKey(menuListResp.getAdminMenuId())) {
                                menuListRespMap.put(menuListResp.getAdminMenuId(), menuListResp);
                            }
                        }
                    }
                }
                userInfoResp.setMenuList(new ArrayList<>(menuListRespMap.values()));
            }
        }
        return new RetVal<>(RetFlag.Success, null, userInfoResp);
    }

    /**
     * 获取角色的menu信息
     *
     * @return
     * @pauid
     */
    public RetVal<List<MenuListResp>> getAdminUserMenuByRoleId(String roleId) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (roleId.equalsIgnoreCase(AppConfig.SuperAdminRoleId)) {
            //开发用的管理员（可以获取所有的菜单权限）
            sb.append("SELECT admin_menu.* ");
            sb.append("FROM admin_menu ");
            sb.append("ORDER BY num ");
        } else {
            //普通的角色
            sb.append("SELECT admin_menu.* ");
            sb.append("FROM admin_menu ");
            sb.append("INNER JOIN admin_role_menu ");
            sb.append("ON admin_menu.admin_menu_id=admin_role_menu.admin_menu_id ");
            sb.append("WHERE admin_role_id=:roleId ");
            sb.append("ORDER BY num ");
            params.put("roleId", roleId);
        }
        return commonQuery.queryListEntityWithRetVal(sb, params, MenuListResp.class);
    }


    /**
     * 获取列表
     *
     * @param req
     * @return
     */
    public RetVal<MyPage<UserListResp>> getList(UserListReq req) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        sb.append("SELECT admin_user.* ");
        sb.append("FROM admin_user ");
        sb.append("WHERE 1=1 ");
        if (!StringUtils.isEmpty(req.getUsername())) {
            sb.append("AND username LIKE CONCAT('%',:username,'%') ");
            params.put("username", req.getUsername());
        }
        sb.append("ORDER BY admin_user.create_date DESC ");
        RetVal<MyPage<UserListResp>> retVal = commonQuery.getListByPageWithRetVal(sb, params, req, UserListResp.class);
        return retVal;
    }

    /**
     * 详情
     *
     * @return
     */
    public RetVal<AdminUserDetailResp> getDetail(String id) {
        AdminUserDetailResp adminUserDetailResp = new AdminUserDetailResp();
        AdminUser adminUser = adminUserRepository.getOne(id);
        BeanUtils.copyProperties(adminUser, adminUserDetailResp);
        return new RetVal(RetFlag.Success, null, adminUserDetailResp);
    }

    /**
     * 保存
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public RetVal save(AdminUserDetailReq adminUserDetailReq) {
        AdminUser adminUser = null;
        if (StringUtils.isEmpty(adminUserDetailReq.getAdminUserId())) {
            //判断用户名是否存在
            AdminUser user_repeat = adminUserRepository.findByUsername(adminUserDetailReq.getUsername());
            if (user_repeat != null) {
                return new RetVal(RetFlag.Error, "用户名已存在");
            }
            adminUser = new AdminUser();
            BeanUtils.copyProperties(adminUserDetailReq, adminUser);
            adminUser.setAdminUserId(IdUtils.createUuid());
            adminUser.setCreateDate(new Date());
            adminUser.setStatus(AdminUserStatus.Enable.getValue());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            adminUser.setPassword(bCryptPasswordEncoder.encode(adminUserDetailReq.getPassword()));
        } else {
            //修改
            adminUser = adminUserRepository.getOne(adminUserDetailReq.getAdminUserId());
            BeanUtils.copyProperties(adminUserDetailReq, adminUser, "username", "password");
        }
        adminUser.setModifyDate(new Date());
        adminUserRepository.save(adminUser);

        return new RetVal(RetFlag.Success, MsgStatic.OperationSuccess);
    }

    /**
     * 禁用
     *
     * @return
     */
    public RetVal disable(String id) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        if (adminUser.isPresent()) {
            adminUser.get().setStatus(AdminUserStatus.Disable.getValue());
            adminUser.get().setModifyDate(new Date());
            adminUserRepository.save(adminUser.get());
        }
        return new RetVal(RetFlag.Success, MsgStatic.OperationSuccess);
    }

    /**
     * 启用
     *
     * @return
     */
    public RetVal enable(String id) {
        Optional<AdminUser> adminUser = adminUserRepository.findById(id);
        if (adminUser.isPresent()) {
            adminUser.get().setStatus(AdminUserStatus.Enable.getValue());
            adminUser.get().setModifyDate(new Date());
            adminUserRepository.save(adminUser.get());
        }
        return new RetVal(RetFlag.Success, MsgStatic.OperationSuccess);
    }

    /**
     * 重置用户密码
     * @return
     */
    public RetVal resetPwd(ResetPwdReq req){
        Optional<AdminUser> adminUser = adminUserRepository.findById(req.getAdminUserId());
        if (adminUser.isPresent()) {
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            adminUser.get().setPassword(bCryptPasswordEncoder.encode(req.getPassword()));
            adminUserRepository.save(adminUser.get());
        }
        return new RetVal(RetFlag.Success, MsgStatic.OperationSuccess);
    }
}
