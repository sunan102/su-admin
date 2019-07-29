package com.sunan.admin.api.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class AdminRoleMenu {
    @Id
    private String adminRoleMenuId;
    private String adminRoleId;
    private String adminMenuId;
}
