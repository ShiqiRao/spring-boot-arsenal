package com.example.jpa.entity;

import lombok.Data;
import lombok.experimental.Accessors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "patient")
@Data
@Accessors(chain = true)
public class Patient implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 名
     */
    @Column(name = "first_name")
    private String firstName;

    /**
     * 姓
     */
    @Column(name = "last_name")
    private String lastName;

    /**
     * 身高
     */
    @Column(name = "height")
    private BigDecimal height;

    /**
     * 体重
     */
    @Column(name = "body_weight")
    private BigDecimal bodyWeight;

    /**
     * BMI指数
     */
    @Column(name = "BMI")
    private BigDecimal BMI;

}
