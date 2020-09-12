package com.example.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@Accessors(chain = true)
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    /**
     * 用户名
     */
    @Column(name = "name")
    private String name;

    /**
     * 账户名
     */
    @Column(name = "account")
    private String account;

    /**
     * 密码
     */
    @Column(name = "password")
    private String password;

    /**
     * 创建时间
     */
    @Column(name = "create_time", columnDefinition = "TIMESTAMP")
    private LocalDateTime createTime;

    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Vehicle> vehicles = new ArrayList<>();

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", createTime=" + createTime +
                '}';
    }
}
