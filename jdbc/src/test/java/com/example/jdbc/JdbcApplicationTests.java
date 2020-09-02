package com.example.jdbc;

import com.example.jdbc.entity.Vehicle;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@Transactional(rollbackFor = Exception.class)
@SpringBootTest
class JdbcApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void queryForObject() {
        //查询vehicle表记录数
        String sql = "SELECT count(*) FROM vehicle";
        //获得SQL语句的执行结果
        Integer numOfVehicle = jdbcTemplate.queryForObject(sql, Integer.class);
        assert numOfVehicle != null;
        System.out.format("There are %d vehicles in the table", numOfVehicle);
    }

    @Test
    void queryForObject_WithRowMapper() {
        //实现RowMapper接口
        RowMapper<Vehicle> rm = (ResultSet result, int rowNum) -> new Vehicle()
                .setId(result.getInt("id"))
                .setName(result.getString("name"))
                .setPrice(result.getBigDecimal("price"));
        //使用?作为占位符,执行SQL时将会将其替换成对应的参数
        String sql = "SELECT id,name,price FROM vehicle WHERE id = ?";
        int id = 1;
        Vehicle vehicle = jdbcTemplate.queryForObject(sql, new Object[]{id}, rm);
        assert vehicle != null;
        System.out.println(vehicle.toString());
    }

    @Test
    void queryForObject_WithBeanPropertyRowMapper() {
        String sql = "SELECT id,name,price FROM vehicle WHERE id = ?";
        int id = 1;
        //使用BeanPropertyRowMapper代替手工实现RowMapper接口
        Vehicle vehicle = jdbcTemplate.queryForObject(sql, new Object[]{id}, BeanPropertyRowMapper.newInstance(Vehicle.class));
        assert vehicle != null;
        System.out.println(vehicle.toString());
    }

    @Test
    void queryForList() {
        String sql = "SELECT id,name,price FROM vehicle";
        List<Map<String, Object>> ret = jdbcTemplate.queryForList(sql);
        assert !ret.isEmpty();
        ret.forEach(System.out::println);
    }

    @Test
    void queryForObject_WithNamedParameterJdbcTemplate() {
        String sql = "SELECT id,name,price FROM vehicle WHERE name like :name AND price > :price LIMIT 1";
        //参数名与SQL中的占位符需要保持一致
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("name", "B%")
                .addValue("price", "60000");
        Vehicle vehicle = namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(Vehicle.class));
        assert vehicle != null;
        System.out.println(vehicle.toString());
    }

    @Test
    void update_SaveVehicle() {
        //新增一条记录
        String sql = "INSERT INTO vehicle(name,price) VALUES(:name,:price)";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("name", "Tesla")
                .addValue("price", "850000");
        int ret = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        assert ret > 0;
    }

    @Test
    void update_UpdateVehicle() {
        //更新数条记录
        String sql = "UPDATE vehicle SET price = price * 0.9 WHERE price > :price";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("price", "1000000");
        int ret = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        assert ret > 0;
    }

    @Test
    void update_DeleteVehicle() {
        //删除数条记录
        String sql = "DELETE FROM vehicle WHERE price < :price";
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("price", "1000000");
        int ret = namedParameterJdbcTemplate.update(sql, mapSqlParameterSource);
        assert ret > 0;
    }

}
