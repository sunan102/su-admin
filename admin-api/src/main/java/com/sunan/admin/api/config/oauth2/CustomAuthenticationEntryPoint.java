package com.sunan.admin.api.config.oauth2;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.provider.error.OAuth2AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 不知道干嘛的
 */
@Component
public class CustomAuthenticationEntryPoint extends OAuth2AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
        int codeStatus = HttpServletResponse.SC_UNAUTHORIZED;
        httpServletResponse.sendError(codeStatus, e.getMessage());
    }
}

