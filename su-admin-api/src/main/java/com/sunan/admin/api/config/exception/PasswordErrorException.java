package com.sunan.admin.api.config.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * 密码错误异常
 */
public class PasswordErrorException extends AccessDeniedException {
    public PasswordErrorException(String msg, Throwable t) {
        super(msg, t);
    }

    public PasswordErrorException(String msg) {
        super(msg);
    }
}
