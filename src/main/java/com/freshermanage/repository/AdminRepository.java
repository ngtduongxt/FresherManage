package com.freshermanage.repository;

import com.freshermanage.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdminRepository extends JpaRepository<Users, Long> {
}
