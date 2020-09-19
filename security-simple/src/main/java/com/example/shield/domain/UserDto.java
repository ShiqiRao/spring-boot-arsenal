package com.example.shield.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotNull;

@Accessors(chain = true)
@Setter
@Getter
public class UserDto {

    @NotNull
    private String username;
    @NotNull
    private String password;

}
