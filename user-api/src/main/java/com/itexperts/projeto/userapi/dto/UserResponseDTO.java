package com.itexperts.projeto.userapi.dto;

import com.itexperts.projeto.userapi.model.User;

public class UserResponseDTO {

    private Long id;
    private String name;
    private String lastName;

    public UserResponseDTO() {
    }

    public UserResponseDTO(String name, String lastName, Long id) {
        this.id = id;
        this.name = name;
        this.lastName = lastName;
    }

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.lastName = user.getLastName();
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
