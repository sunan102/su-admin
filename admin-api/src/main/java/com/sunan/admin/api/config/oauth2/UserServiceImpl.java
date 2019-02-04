package com.sunan.admin.api.config.oauth2;

import com.sunan.admin.api.config.exception.PasswordErrorException;
import com.sunan.admin.api.domain.entity.AdminUser;
import com.sunan.admin.api.domain.repository.AdminUserRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service(value = "userDetailsService")
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private AdminUserRepository adminUserRepository;

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser users = adminUserRepository.findByUsername(username);
        if (users == null) {
            throw new PasswordErrorException("用户名不存在");
        }

        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        System.out.println(bCryptPasswordEncoder.encode("1"));
        UserDetailModel userDetailModel = new UserDetailModel();
        BeanUtils.copyProperties(users, userDetailModel);
        return userDetailModel;
    }

//    private List getAuthority() {
//        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//    }

}
