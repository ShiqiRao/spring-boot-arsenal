package com.example.jpa.repository;

import com.example.jpa.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    List<User> findByCreateTimeBetweenAndNameLike(LocalDateTime start, LocalDateTime end, String keyWord);

    List<User> findByCreateTimeBetween(LocalDateTime start, LocalDateTime end);

    List<User> findByNameLike(String keyWord);

    //JPQL
    @Query("from User user0_ where (user0_.createTime between ?1 and ?2) and (user0_.name like ?3)")
    List<User> query(LocalDateTime start, LocalDateTime end, String keyWord);

    //SQL
    @Query(nativeQuery = true,
            value = "select user0_.id as id1_1_, " +
                    "user0_.account as account2_1_, " +
                    "user0_.create_time as create_t3_1_, " +
                    "user0_.name as name4_1_, " +
                    "user0_.password as password5_1_, " +
                    "user0_.vehicle_id as vehicle_6_1_ " +
                    "from user user0_ " +
                    "where (user0_.create_time between ? and ?) and (user0_.name like ?)")
    List<User> queryNative(LocalDateTime start, LocalDateTime end, String keyWord);
}