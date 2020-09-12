package com.example.mongodb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * TODO
 *
 * @Author Shiqi Rao
 * @Date 2020-09-11
 */
@Document
@Accessors(chain = true)
@Setter
@Getter
public class Store {

    @Id
    private Integer id;
    private String name;
    private String location;
}
