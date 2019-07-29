package com.sunan.admin.api.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Getter
@Setter
@Entity
public class AdminRole {
    @Id
    private String adminRoleId;
    private String roleName;
    private Date createDate;
    private Date modifyDate;
}
