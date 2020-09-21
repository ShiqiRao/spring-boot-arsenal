package com.example.shield.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.management.relation.Role;
import javax.persistence.*;
import java.util.Collection;

@Accessors(chain = true)
@Setter
@Getter
@Entity
@Table(name = "authority")
public class TAuthority {

    @GeneratedValue(strategy = GenerationType.AUTO)
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "name", nullable = false)
    private String name;

    @ManyToMany(mappedBy = "authorities")
    private Collection<Role> roles;

}
