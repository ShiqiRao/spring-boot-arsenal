package com.example.mongodb.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * 货物
 *
 * @Author Shiqi Rao
 * @Date 2020-09-11
 */
@Accessors(chain = true)
@Setter
@Getter
public class Ware {
    private Integer id;
    private String name;
}
