package com.sunan.admin.api.config.exception;

import org.springframework.security.access.AccessDeniedException;

/**
 * 用户禁用异常
 */
public class UserDisableException extends AccessDeniedException {
    public UserDisableException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserDisableException(String msg) {
        super(msg);
    }
}
