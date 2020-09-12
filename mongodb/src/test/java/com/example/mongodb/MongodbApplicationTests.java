package com.example.mongodb;

import com.example.mongodb.domain.Store;
import com.example.mongodb.domain.Ware;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@SpringBootTest
class MongodbApplicationTests {

    @Autowired
    MongoTemplate mongoTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void testSave() {
        Ware mineralWater = new Ware();
        mineralWater
                .setId(1)
                .setName("矿泉水");
        mongoTemplate.insert(mineralWater, "ware");
    }

    @Test
    void testSaveOrUpdate() {
        Ware melonSeeds = new Ware();
        melonSeeds
                .setId(1)
                .setName("瓜子");
        mongoTemplate.save(melonSeeds);
        Ware cola = new Ware();
        cola
                .setId(2)
                .setName("Coca-Cola");
        mongoTemplate.save(cola);
    }

    @Test
    void testGet() {
        Ware ware = mongoTemplate.findOne(Query.query(Criteria.where("id").is(1)), Ware.class);
        assert ware != null;
        assert ware.getId() == 1;
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
        query.addCriteria(Criteria.where("id").lt(3));
        Update update = new Update();
        update.set("location", null);
        mongoTemplate.updateMulti(query, update, Store.class);
    }
}
