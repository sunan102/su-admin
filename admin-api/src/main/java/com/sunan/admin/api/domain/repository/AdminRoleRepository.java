package com.sunan.admin.api.domain.repository;


import com.sunan.admin.api.domain.entity.AdminRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleRepository extends JpaRepository<AdminRole, String> {

}
