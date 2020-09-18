package com.example.shield.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class User {

    private Integer id;
    private String username;
    private String password;

}
