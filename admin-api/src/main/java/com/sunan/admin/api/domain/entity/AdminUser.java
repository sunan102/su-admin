package com.sunan.admin.api.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class AdminUser {
    @Id
    private String adminUserId;
    private String username;
    private String password;
    private String adminRoleId;
    private String adminCompanyId;
    private Integer status;
    private Date createDate;
    private Date modifyDate;
}
