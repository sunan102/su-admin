package com.sunan.admin.api.domain.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 后台-企业
 */
@Entity
@Getter
@Setter
public class AdminCompany {

    /**
     * id
     */
    @Id
    private String adminCompanyId;

    /**
     * 名称
     */
    private String companyName;

    /**
     * 简称
     */
    private String shortName;

    /**
     * 英文名
     */
    private String enName;

    /**
     * 曾用名
     */
    private String oldName;

    /**
     * 地址
     */
    private String address;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private Integer status;

    private Date createDate;
    private Date modifyDate;

}
