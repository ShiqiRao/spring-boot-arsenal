package com.example.jwt.repository;


import com.example.jwt.entity.TUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface UserRepository extends JpaRepository<TUser, Integer>, JpaSpecificationExecutor<TUser> {

    TUser findByUsername(String username);

}
