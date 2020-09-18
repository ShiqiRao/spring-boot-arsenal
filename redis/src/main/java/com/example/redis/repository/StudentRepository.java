package com.example.redis.repository;

import com.example.redis.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends CrudRepository<Student, String> {

    //自定义查询方式。使用标注了@Indexed的name属性进行查询
    Student findByName(String name);

}
