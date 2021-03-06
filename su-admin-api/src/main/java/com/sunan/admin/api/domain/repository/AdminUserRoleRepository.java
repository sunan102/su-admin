package com.sunan.admin.api.domain.repository;


import com.sunan.admin.api.domain.entity.AdminUserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminUserRoleRepository extends JpaRepository<AdminUserRole, String> {
    List<AdminUserRole> findByAdminUserId(String adminUserId);
}
