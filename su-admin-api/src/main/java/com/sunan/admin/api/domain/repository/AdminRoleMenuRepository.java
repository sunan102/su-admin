package com.sunan.admin.api.domain.repository;


import com.sunan.admin.api.domain.entity.AdminRoleMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRoleMenuRepository extends JpaRepository<AdminRoleMenu, String> {
}
