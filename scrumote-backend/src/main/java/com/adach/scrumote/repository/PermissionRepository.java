package com.adach.scrumote.repository;

import com.adach.scrumote.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends JpaRepository<Role, Long> {

}
