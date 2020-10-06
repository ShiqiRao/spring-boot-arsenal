package com.example.unit;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Accessors(chain = true)
@Setter
@Getter
public class User {
    private String username;
    private String firstName;
    private String lastName;
}
