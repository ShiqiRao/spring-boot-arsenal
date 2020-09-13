package com.example.mongodb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 货物
 *
 * @Author Shiqi Rao
 * @Date 2020-09-11
 */
@Document
@Accessors(chain = true)
@Setter
@Getter
public class Ware {
    @Id
    private String id;
    private String name;
    private String status;

    @Override
    public String toString() {
        return "Ware{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
