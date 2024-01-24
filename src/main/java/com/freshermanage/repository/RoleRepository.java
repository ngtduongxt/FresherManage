package com.freshermanage.repository;

import com.freshermanage.model.ERole;
import com.freshermanage.model.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Roles, Long> {
    Optional<Roles> findByRoleName(ERole roleName);
}
