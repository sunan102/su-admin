package com.sunan.admin.api.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class AdminMenu {
    @Id
    private String adminMenuId;
    private String menuName;
    private Integer type;
    private Integer num;
    private String pid;
}
