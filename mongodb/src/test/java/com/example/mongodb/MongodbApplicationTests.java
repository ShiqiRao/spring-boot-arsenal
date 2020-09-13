package com.example.mongodb;

import com.example.mongodb.domain.Store;
import com.example.mongodb.domain.Ware;
import com.example.mongodb.repository.WareRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class MongodbApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    WareRepository wareRepository;

    @Test
    void contextLoads() {
    }

    @Test
    void testSave() {
        Ware mineralWater = new Ware();
        mineralWater
                .setStatus("selling")
                .setName("西瓜");
        mongoTemplate.insert(mineralWater, "ware");
    }

    @Test
    void testSaveOrUpdate() {
        Ware melonSeeds = new Ware();
        melonSeeds
                .setId("1")
                .setName("瓜子");
        mongoTemplate.save(melonSeeds);
        Ware cola = new Ware();
        cola
                .setId("1")
                .setName("Coca-Cola");
        mongoTemplate.save(cola);
    }

    @Test
    void testUpdateFirst() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("Milk"));
        Update update = new Update();
        update.set("name", "Yogurt");
        mongoTemplate.updateFirst(query, update, Ware.class);
    }

    @Test
    void testGet() {
        Ware ware = mongoTemplate.findOne(Query.query(Criteria.where("id").is(1)), Ware.class);
        assert ware != null;
        assert ware.getId().equals("1");
    }

    @Test
    void testAnnotations() {
        Store store = new Store()
                .setId(1)
                .setName("No.18")
                .setLocation("The Bund 18.");
        mongoTemplate.save(store);
        Store mysteryStore = new Store()
                .setId(2)
                .setName("Mystery Store")
                .setLocation("In the jungle");
        mongoTemplate.save(mysteryStore);
    }

    @Test
    void testMultiUpdate() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("矿泉水"));
        Update update = new Update();
        update.set("status", "sold out");
        mongoTemplate.updateMulti(query, update, Ware.class);
    }

    @Test
    void testFindAndModify() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("矿泉水"));
        Update update = new Update();
        update.set("status", "selling");
        Ware result = mongoTemplate.findAndModify(query, update, Ware.class);
        assert Optional.ofNullable(result).orElse(new Ware()).getStatus().equals("sold out");
    }

    @Test
    void testInsertWithRepository() {
        Ware ware = new Ware()
                .setName("Milk");
        wareRepository.insert(ware);
    }

    @Test
    void testUpsert() {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").is("西瓜"));
        Update update = new Update();
        update.set("name", "哈密瓜");
        mongoTemplate.upsert(query, update, Ware.class);
    }

    @Test
    void testRemove() {
        mongoTemplate.remove(Query.query(Criteria.where("status").is("sold out")), Ware.class);
    }

    @Test
    void testExist() {
        boolean isSoldOutExist = wareRepository.exists(Example.of(new Ware().setStatus("sold out")));
        assert !isSoldOutExist;
        boolean isSellingExist = wareRepository.exists(Example.of(new Ware().setStatus("selling")));
        assert isSellingExist;
    }

    @Test
    void testSaveAll() {
        List<Ware> wares = new ArrayList<>();
        wares.add(new Ware().setName("酒水").setStatus("selling"));
        wares.add(new Ware().setName("瓜子").setStatus("selling"));
        wares.add(new Ware().setName("饮料").setStatus("selling"));
        wareRepository.saveAll(wares);
    }

    @Test
    void testSort() {
        List<Ware> wareList = wareRepository.findAll(Sort.by(Sort.Direction.ASC, "id"));
        wareList.forEach(w -> System.out.println(w.toString()));
    }

    @Test
    void testPage() {
        Page<Ware> warePage = wareRepository.findAll(PageRequest.of(0, 10));
    }
}
