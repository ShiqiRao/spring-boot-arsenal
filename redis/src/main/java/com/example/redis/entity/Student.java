package com.example.redis.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.io.Serializable;

@Accessors(chain = true)
@Getter
@Setter
@RedisHash(value = "Student",timeToLive = 10)
public class Student implements Serializable {

    public enum Gender {
        MALE, FEMALE
    }

    private String id;
    @Indexed
    private String name;
    private Gender gender;
    private int grade;
}
