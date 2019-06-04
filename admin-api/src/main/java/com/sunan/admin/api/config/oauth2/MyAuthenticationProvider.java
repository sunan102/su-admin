package com.sunan.admin.api.config.oauth2;

import com.sunan.admin.api.common.enums.AdminUserStatus;
import com.sunan.admin.api.config.exception.PasswordErrorException;
import com.sunan.admin.api.config.exception.UserDisableException;
import com.sunan.admin.api.config.oauth2.model.UserDetailModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * 自定义判断用户名密码等信息是否正确，是否可以登录
 */
@Component
public class MyAuthenticationProvider implements AuthenticationProvider {
    /**
     * 注入我们自己定义的用户信息获取对象
     */
    @Autowired
    private UserDetailsService userDetailsService;
//    @Autowired
//    private AdminCompanyRepository adminCompanyRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();// 这个获取表单输入中返回的用户名;
        String password = (String) authentication.getCredentials();// 这个是表单中输入的密码；
        // 这里构建来判断用户是否存在和密码是否正确
        UserDetailModel userDetailModel = (UserDetailModel) userDetailsService.loadUserByUsername(userName); // 这里调用我们的自己写的获取用户的方法；
        //判断账户是否禁用
        boolean enable = true;
        if (!userDetailModel.getStatus().equals(AdminUserStatus.Enable.getValue())) {
            enable = false;
        } else {
            //判断所属单位是否禁用
//            if (!StringUtils.isEmpty(userInfo.getCompanyId())) {
//                Optional<AdminCompany> adminCompany = adminCompanyRepository.findById(userInfo.getCompanyId());
//                if (adminCompany.isPresent() && !adminCompany.get().getStatus().equals(AdminCompanyStatus.Enable.getValue())) {
//                    enable = false;
//                }
//            }
        }
        if (!enable) {
            throw new UserDisableException("此账号已经禁用，请联系管理员");
        }
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        if (!bCryptPasswordEncoder.matches(password, userDetailModel.getPassword())) {
            throw new PasswordErrorException("用户名或密码错误");
        }
        // 构建返回的用户登录成功的token
        // 因这里的principle最后会变为字符串类型，所以只能传id或者用户名，用户信息依据id或用户名来获得
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetailModel.getAdminUserId(), null, userDetailModel.getAuthorities());
        return authenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        // 这里直接改成retrun true;表示是支持这个执行
        return true;
    }
}