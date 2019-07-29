package com.sunan.admin.api.domain.repository;


import com.sunan.admin.api.domain.entity.AdminUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
    AdminUser findByUsername(String username);
}
