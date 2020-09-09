package com.example.jpa;

import com.example.jpa.entity.*;
import com.example.jpa.entity.Class;
import com.example.jpa.repository.*;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Transactional
@SpringBootTest
class JpaApplicationTests {

    @Autowired
    PatientRepository patientRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    ClassRepository classRepository;

    @Autowired
    StudentRepository studentRepository;

    @Autowired
    TeacherRepository teacherRepository;

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
    public void testOneToOne() {
        Class clazz = classRepository.getOne(1);
        ClassRoom room = clazz.getClassRoom();
        assert room != null;
        assert room.getLocation() != null;
    }

    @Test
    public void testOneToMany() {
        Class clazz = classRepository.getOne(1);
        List<Student> students = clazz.getStudents();
        assert students.size() > 0;
    }

    @Test
    public void testManyToOne() {
        Student student = studentRepository.getOne(1);
        Class clazz = student.getClazz();
        assert clazz != null;
        assert clazz.getClassName() != null;
    }

    @Test
    public void testManyToMany() {
        Teacher teacher = teacherRepository.getOne(1);
        Set<Class> classSet = teacher.getClasses();
        assert classSet.size() > 0;
        Class clazz = classRepository.getOne(1);
        Set<Teacher> teachers = clazz.getTeachers();
        assert teachers.size() > 0;
    }

}
