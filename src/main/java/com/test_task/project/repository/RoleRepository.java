package com.test_task.project.repository;

import com.test_task.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {
    Role findRoleByName(Role.RoleName name);
}
