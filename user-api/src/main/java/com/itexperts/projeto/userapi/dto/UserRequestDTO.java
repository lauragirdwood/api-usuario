package com.itexperts.projeto.userapi.dto;

public class UserRequestDTO {

    private String name;
    private String lastName;

    public UserRequestDTO() {
    }

    public UserRequestDTO(String name, String lastName) {
        this.name = name;
        this.lastName = lastName;
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
