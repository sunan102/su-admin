package com.sunan.admin.api.domain.repository;


import com.sunan.admin.api.domain.entity.AdminCompany;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/4/11.
 */
@Repository
public interface AdminCompanyRepository extends JpaRepository<AdminCompany, String> {
    AdminCompany findByCompanyName(String companyName);
}
