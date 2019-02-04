package com.sunan.admin.api.config.oauth2;

import com.sunan.admin.api.common.RetVal;
import com.sunan.admin.api.config.exception.PasswordErrorException;
import com.sunan.admin.api.config.exception.UserDisableException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

/**
 * oauth2授权服务器捕获异常
 */
@Component
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity translate(Exception e) throws Exception {
        int flag = 401;
        if (e instanceof UserDisableException) {
            flag = 4001;//用户禁用
        } else if (e instanceof PasswordErrorException) {
            flag = 4002;//密码错误
        }
        RetVal retVal = new RetVal(flag, e.getMessage());
        HttpHeaders headers = new HttpHeaders();
        ResponseEntity<RetVal> response = new ResponseEntity<>(retVal,headers , HttpStatus.UNAUTHORIZED);
        return response;
    }
}
