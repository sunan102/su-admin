package com.sunan.admin.api.model.admin.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPwdReq {

    private String adminUserId;

    private String password;
}
