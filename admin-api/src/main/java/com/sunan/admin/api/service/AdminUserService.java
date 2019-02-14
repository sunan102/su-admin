package com.sunan.admin.api.service;

import com.sunan.admin.api.common.MyPage;
import com.sunan.admin.api.common.RetVal;
import com.sunan.admin.api.common.enums.AdminUserStatus;
import com.sunan.admin.api.common.strings.MsgStatic;
import com.sunan.admin.api.domain.dao.CommonQuery;
import com.sunan.admin.api.domain.entity.AdminRole;
import com.sunan.admin.api.domain.entity.AdminUser;
import com.sunan.admin.api.domain.repository.AdminRoleRepository;
import com.sunan.admin.api.domain.repository.AdminUserRepository;
import com.sunan.admin.api.model.admin.user.*;
import com.sunan.admin.api.utils.IdUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * 通过用户名查询用户信息
 */
@Service
public class AdminUserService {

    @Autowired
    private AdminUserRepository adminUserRepository;
    @Autowired
    private AdminRoleRepository adminRoleRepository;
    @Autowired
    private CommonQuery commonQuery;

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
            //查询role
            if (adminUser.get().getAdminRoleId()!=null){
                Optional<AdminRole> adminRole = adminRoleRepository.findById(adminUser.get().getAdminRoleId());
                if (adminRole.isPresent()) {
                    userInfoResp.setAdminRoleId(adminRole.get().getAdminRoleId());
                    userInfoResp.setRoleName(adminRole.get().getRoleName());
                    //查询menu
                    RetVal<List<AdminUserMenuResp>> menuList = getAdminUserMenuByRoleId(adminRole.get().getAdminRoleId());
                    if (menuList.getData()!=null){
                        userInfoResp.setMenuList(menuList.getData());
                    }
                }
            }
        }
        return new RetVal<>(0, null, userInfoResp);
    }

    /**
     * 获取角色的menu信息
     *
     * @return
     * @pauid
     */
    public RetVal<List<AdminUserMenuResp>> getAdminUserMenuByRoleId(String roleId) {
        StringBuilder sb = new StringBuilder();
        Map<String, Object> params = new HashMap<>();
        if (roleId.equalsIgnoreCase("0")){
            //开发用的管理员（可以获取所有的菜单权限）
            sb.append("SELECT admin_menu.admin_menu_id,url ");
            sb.append("FROM admin_menu ");
            sb.append("ORDER BY num ");
        }else{
            //普通的角色
            sb.append("SELECT admin_menu.admin_menu_id,url ");
            sb.append("FROM admin_menu ");
            sb.append("INNER JOIN admin_role_menu ");
            sb.append("ON admin_menu.admin_menu_id=admin_role_menu.admin_menu_id ");
            sb.append("WHERE admin_role_id=:roleId ");
            sb.append("ORDER BY num ");
            params.put("roleId", roleId);
        }
        return commonQuery.queryListEntityWithRetVal(sb, params, AdminUserMenuResp.class);
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
        sb.append("SELECT admin_user.*,company_name,role_name ");
        sb.append("FROM admin_user ");
        sb.append("INNER JOIN admin_company ");
        sb.append("ON admin_user.admin_company_id = admin_company.admin_company_id ");
        sb.append("INNER JOIN admin_role ");
        sb.append("ON admin_user.admin_role_id = admin_role.admin_role_id ");
        sb.append("WHERE 1=1 ");
        if (!StringUtils.isEmpty(req.getUsername())) {
            sb.append("AND username LIKE CONCAT('%',:username,'%') ");
            params.put("username", req.getUsername());
        }
        if (!StringUtils.isEmpty(req.getCompanyId())) {
            sb.append("AND admin_user.admin_company_id=:company_id ");
            params.put("company_id", req.getCompanyId());
        }
        if (!StringUtils.isEmpty(req.getMobile())) {
            sb.append("AND mobile = :mobile ");
            params.put("mobile", req.getMobile());
        }
        if (!StringUtils.isEmpty(req.getJobNo())) {
            sb.append("AND job_no = :job_no ");
            params.put("job_no", req.getJobNo());
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
    public RetVal<UserDetailResp> detail(String id) {
        UserDetailResp userDetailResp = new UserDetailResp();
        AdminUser adminUser = adminUserRepository.getOne(id);
        BeanUtils.copyProperties(adminUser, userDetailResp);
        return new RetVal(0, null, userDetailResp);
    }

    /**
     * 保存
     *
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    public RetVal save(UserDetailReq userDetailReq) {
        AdminUser adminUser = null;
        if (StringUtils.isEmpty(userDetailReq.getId())) {
            //判断用户名是否存在
            AdminUser user_repeat = adminUserRepository.findByUsername(userDetailReq.getUsername());
            if (user_repeat != null) {
                return new RetVal(1, "用户名已存在");
            }
            adminUser = new AdminUser();
            BeanUtils.copyProperties(userDetailReq, adminUser);
            adminUser.setAdminUserId(IdUtils.createUuid());
            adminUser.setCreateDate(new Date());
            adminUser.setStatus(AdminUserStatus.Enable.getValue());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            adminUser.setPassword(bCryptPasswordEncoder.encode(userDetailReq.getPassword()));
        } else {
            //修改
            adminUser = adminUserRepository.getOne(userDetailReq.getId());
            BeanUtils.copyProperties(userDetailReq, adminUser, "username", "password");
        }
        adminUser.setModifyDate(new Date());
        adminUserRepository.save(adminUser);

        return new RetVal(0, MsgStatic.OperationSuccess);
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
        return new RetVal(0, MsgStatic.OperationSuccess);
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
        return new RetVal(0, MsgStatic.OperationSuccess);
    }

}
