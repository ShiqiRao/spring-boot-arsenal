package com.example.jwt.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.io.Serializable;
import java.util.List;

@Accessors(chain = true)
@Setter
@Getter
public class JwtUser implements Serializable {

    private static final long serialVersionUID = -5954106717635750452L;
    private String username;
    private String password;
    private List<String> authorities;

}
