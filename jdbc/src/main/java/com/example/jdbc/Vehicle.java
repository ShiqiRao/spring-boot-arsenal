package com.example.jdbc;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@Accessors(chain = true)
@Setter
@Getter
@ToString
public class Vehicle {
    private Integer id;
    private String name;
    private BigDecimal price;
}
