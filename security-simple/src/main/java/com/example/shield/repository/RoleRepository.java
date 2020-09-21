package com.example.shield.repository;

import com.example.shield.entity.TRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RoleRepository extends JpaRepository<TRole, Integer>, JpaSpecificationExecutor<TRole> {
}
