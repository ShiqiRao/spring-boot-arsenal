package com.example.jdbc;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.sql.ResultSet;
import java.util.List;
import java.util.Map;

@SpringBootTest
class JdbcApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    @Test
    void contextLoads() {
    }

    @Test
    void queryForObject() {
        String sql = "SELECT count(*) FROM vehicle";
        Integer numOfVehicle = jdbcTemplate.queryForObject(sql, Integer.class);
        assert numOfVehicle != null;
        System.out.format("There are %d vehicles in the table", numOfVehicle);
    }

    @Test
    void queryForObject_WithRowMapper() {
        RowMapper<Vehicle> rm = (ResultSet result, int rowNum) -> new Vehicle()
                .setId(result.getInt("id"))
                .setName(result.getString("name"))
                .setPrice(result.getBigDecimal("price"));
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
        MapSqlParameterSource mapSqlParameterSource = new MapSqlParameterSource()
                .addValue("name", "B%")
                .addValue("price", "60000");
        Vehicle vehicle = namedParameterJdbcTemplate.queryForObject(sql, mapSqlParameterSource, BeanPropertyRowMapper.newInstance(Vehicle.class));
        assert vehicle != null;
        System.out.println(vehicle.toString());
    }

}
