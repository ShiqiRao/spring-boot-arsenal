package com.example.shield.repository;

import com.example.shield.entity.TAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AuthorityRepository extends JpaRepository<TAuthority, Integer>, JpaSpecificationExecutor<TAuthority> {
}
