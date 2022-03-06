package com.itexperts.projeto.userapi.model;

import com.itexperts.projeto.userapi.dto.UserRequestDTO;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class User implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name = "last_name")
    private String lastName;

    public User() {
    }

    public User(String lastName) {
        this.lastName = lastName;
    }

    public User(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
    }

    public User(Long id, String name, String lastName) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public User(UserRequestDTO userRequestDTO) {
        this.name = userRequestDTO.getName();
        this.lastName = userRequestDTO.getLastName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
