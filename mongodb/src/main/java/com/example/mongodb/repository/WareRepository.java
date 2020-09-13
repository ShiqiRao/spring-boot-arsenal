package com.example.mongodb.repository;

import com.example.mongodb.domain.Ware;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface WareRepository extends MongoRepository<Ware, String> {
//Ware为实体类型，String为实体的主键类型
}
