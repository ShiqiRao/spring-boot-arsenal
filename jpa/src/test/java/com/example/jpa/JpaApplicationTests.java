package com.example.jpa;

import com.example.jpa.entity.Patient;
import com.example.jpa.entity.User;
import com.example.jpa.entity.Vehicle;
import com.example.jpa.repository.PatientRepository;
import com.example.jpa.repository.UserRepository;
import com.example.jpa.repository.VehicleRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.lang.Nullable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Transactional
@SpringBootTest
class JpaApplicationTests {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

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
    public void testJpaRepositoryComplicated() {
        //根据时间与关键字查询
        List<User> queryWithTimeAndKeyWord = getUser(LocalDateTime.of(2019, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                "陈");
        assert queryWithTimeAndKeyWord != null;
        //根据时间查询
        List<User> queryWithTime = getUser(LocalDateTime.of(2019, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                null);
        assert queryWithTime != null;
        //普通查询
        List<User> query = getUser(null, null, "张");
        assert query != null;
    }

    //以下示例为不推荐的查询实现方式,属于错误示范
    private List<User> getUser(@Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable String keyWord) {
        //String.format中%为特殊字符需要再加一个%进行转义，以下格式化的结果为%{keyWord}%
        String nameLike = keyWord == null ? null : String.format("%%%s%%", keyWord);
        if (start != null && end != null && !StringUtils.isEmpty(nameLike)) {
            //查询条件同时包含时间与关键字
            return userRepository.findByCreateTimeBetweenAndNameLike(start, end, nameLike);
        } else if (start != null && end != null && StringUtils.isEmpty(nameLike)) {
            //查询条件仅包含时间
            return userRepository.findByCreateTimeBetween(start, end);
        } else if ((start == null || end == null) && !StringUtils.isEmpty(nameLike)) {
            //查询条件仅包含关键字
            return userRepository.findByNameLike(keyWord);
        } else {
            return userRepository.findAll();
        }
    }

    @Test
    public void testJpaSpecificationExecutor() {
        //根据时间与关键字查询
        List<User> queryWithTimeAndKeyWord = getUserWithJpaSpecificationExecutor(LocalDateTime.of(2019, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                "陈");
        assert queryWithTimeAndKeyWord != null;
        //根据时间查询
        List<User> queryWithTime = getUserWithJpaSpecificationExecutor(LocalDateTime.of(2019, 1, 1, 0, 0, 0),
                LocalDateTime.of(2020, 1, 1, 0, 0, 0),
                null);
        assert queryWithTime != null;
        //普通查询
        List<User> query = getUserWithJpaSpecificationExecutor(null, null, "张");
        assert query != null;
    }

    private List<User> getUserWithJpaSpecificationExecutor(@Nullable LocalDateTime start, @Nullable LocalDateTime end, @Nullable String keyWord) {
        //String.format中%为特殊字符需要再加一个%进行转义，以下格式化的结果为%{keyWord}%
        String nameLike = keyWord == null ? null : String.format("%%%s%%", keyWord);
        return userRepository.findAll(((root, query, criteriaBuilder) -> {
            //根据传入参数的不同构造谓词列表
            List<Predicate> predicates = new ArrayList<>();
            if (start != null && end != null) {
                predicates.add(criteriaBuilder.between(root.get("createTime"), start, end));
            }
            if (!StringUtils.isEmpty(nameLike)) {
                predicates.add(criteriaBuilder.like(root.get("name"), nameLike));
            }
            query.where(predicates.toArray(new Predicate[0]));
            return query.getRestriction();
        }));
    }

    @Test
    public void testOneToMany() {
        User user = userRepository.getOne(1);
        List<Vehicle> vehicles = user.getVehicles();
        assert vehicles.size() > 0;
    }

    @Test
    public void testManyToOne() {
        Vehicle vehicle = vehicleRepository.getOne(1);
        User user = vehicle.getUser();
        assert user != null;
        System.out.println(user.toString());
    }

}
