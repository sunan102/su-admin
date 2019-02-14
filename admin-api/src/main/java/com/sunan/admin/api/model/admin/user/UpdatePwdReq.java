package com.sunan.admin.api.model.admin.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePwdReq {

    /**
     * id
     */
    private String uid;

    private String password;
}
