package com.example.jpa.repository;

import com.example.jpa.entity.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ClassRepository extends JpaRepository<Class, Integer>, JpaSpecificationExecutor<Class> {

}