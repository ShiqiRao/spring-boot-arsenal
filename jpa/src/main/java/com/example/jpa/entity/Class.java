package com.example.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "class")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "class_name")
    private String className;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "clazz")
    private List<Student> students;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "room_id")
    private ClassRoom classRoom;

    @ManyToMany(mappedBy = "classes")
    private Set<Teacher> teachers;

}
