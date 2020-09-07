package com.example.jpa;

import com.example.jpa.entity.Patient;
import com.example.jpa.entity.User;
import com.example.jpa.repository.PatientRepository;
import com.example.jpa.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.math.BigDecimal;
import java.util.List;

@Transactional
@SpringBootTest
class JpaApplicationTests {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Test
    void contextLoads() {
    }

    @Test
    public void testSave() {
        // 1.加载配置文件创建工厂（实体管理器工厂）对象
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("jpaConfig");
        // 2.通过实体管理器工厂获取实体管理器
        EntityManager em = factory.createEntityManager();
        // 3.获取事务对象，开启事务
        EntityTransaction tx = em.getTransaction();
        tx.begin();// 开启事务
        // 4.完成增删改查操作
        User user = new User();
        user.setName("张三");
        // 保存
        em.persist(user);
        // 5.提交事务
        tx.commit();
        // 6.释放资源
        em.close();
        factory.close();
    }

    @Test
    public void testJpaRepository() {
        List<Patient> san = patientRepository.getByFirstName("san");
        assert san != null;
        assert san.size() > 0;
        Patient lisi = patientRepository.findByFirstNameAndLastName("si", "li");
        assert lisi != null;
        List<Patient> tallPatients = patientRepository.readByHeightGreaterThan(new BigDecimal(190));
        assert tallPatients != null;
        assert tallPatients.size() == 0;
    }

    @Test
    public void testJpaSpecificationExecutor() {
        //todo
    }

}
