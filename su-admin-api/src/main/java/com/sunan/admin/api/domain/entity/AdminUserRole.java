package com.sunan.admin.api.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class AdminUserRole {
    @Id
    private String adminUserRoleId;
    private String adminUserId;
    private String adminRoleId;
}
