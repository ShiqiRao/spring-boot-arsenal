package com.example.jpa;

import com.example.jpa.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

@Transactional
@SpringBootTest
class JpaApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	public void testSave(){
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

}
