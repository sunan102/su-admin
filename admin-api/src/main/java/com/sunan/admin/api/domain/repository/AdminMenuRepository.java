package com.sunan.admin.api.domain.repository;


import com.sunan.admin.api.domain.entity.AdminMenu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminMenuRepository extends JpaRepository<AdminMenu, String> {
}
