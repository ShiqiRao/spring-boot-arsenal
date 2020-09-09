package com.example.jpa.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@Table(name = "class_room")
public class ClassRoom implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "location")
    private String location;

    @OneToOne(mappedBy = "classRoom", fetch = FetchType.LAZY)
    private Class clazz;

}
