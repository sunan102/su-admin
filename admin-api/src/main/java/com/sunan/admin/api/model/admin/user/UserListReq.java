package com.sunan.admin.api.model.admin.user;

import com.sunan.admin.api.common.PageRequest;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserListReq extends PageRequest {

    /**
     * 用户名
     */
    private String username;

}
