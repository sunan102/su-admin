package com.sunan.admin.api.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class AdminUser {
    @Id
    private String adminUserId;
    private String username;
    private String password;
    private Integer status;
}
