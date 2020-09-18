package com.example.redis;

import com.example.redis.entity.Student;
import com.example.redis.repository.StudentRepository;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class RedisApplicationTests {

    @Autowired
    StudentRepository studentRepository;

    @Test
    void testSave() {
        Student student = new Student()
                .setId("20200101007")
                .setName("zbc")
                .setGender(Student.Gender.MALE)
                .setGrade(1);
        //根据id新增或更新记录
        studentRepository.save(student);
    }

    @Test
    void testFindBy() {
        //使用主键查询
        assert studentRepository.findById("20200101007").isPresent();
        //根据自定义方法查询
        assert studentRepository.findByName("zbc") != null;
    }

    @Test
    void testDelete() {
        //根据主键删除
        studentRepository.deleteById("20200101007");
        assert !studentRepository.findById("20200101007").isPresent();
    }

    @Test
    void testFindAll() {
        studentRepository.save(new Student()
                .setId("20200101007")
                .setName("zbc")
                .setGender(Student.Gender.MALE)
                .setGrade(1));
        studentRepository.save(new Student()
                .setId("20200101008")
                .setName("cc")
                .setGender(Student.Gender.FEMALE)
                .setGrade(1));
        studentRepository.save(new Student()
                .setId("20200101009")
                .setName("gf")
                .setGender(Student.Gender.MALE)
                .setGrade(1));
        studentRepository.save(new Student()
                .setId("20200101010")
                .setName("mjyl")
                .setGender(Student.Gender.FEMALE)
                .setGrade(1));
        //查询全部记录
        List<Student> studentList = Lists.newArrayList(studentRepository.findAll());
        assert studentList.size() > 0;
    }

}
