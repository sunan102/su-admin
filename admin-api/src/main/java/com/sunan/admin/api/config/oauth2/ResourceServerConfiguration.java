package com.sunan.admin.api.config.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.ResourceServerTokenServices;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;

/**
 * 资源服务器配置
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {

    private static final String RESOURCE_ID = "*";
    @Autowired
    private ResourceServerTokenServices tokenServices;
    @Autowired
    private LogoutSuccessHandler customLogoutSuccessHandler;
    @Autowired
    private CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true).tokenServices(tokenServices);
    }


    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
//                .antMatchers("/users/**").authenticated() //配置users访问控制，必须认证过后才可以访问
                .antMatchers("/test/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(customAuthenticationEntryPoint)  //认证失败的业务处理
                .and()
                .logout()
                .logoutUrl("/oauth/logout")
                .logoutSuccessHandler(customLogoutSuccessHandler);   //退出成功的业务处理
    }

//    @Bean
//    public LogoutSuccessHandler customLogoutSuccessHandler(){
//        return new CustomLogoutSuccessHandler();
//    }

//    @Bean
//    public AuthenticationEntryPoint customAuthenticationEntryPoint(){
//        return new CustomAuthenticationEntryPoint();
//    }

}
