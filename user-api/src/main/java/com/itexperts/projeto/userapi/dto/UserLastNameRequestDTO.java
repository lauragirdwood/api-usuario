package com.itexperts.projeto.userapi.dto;

import com.itexperts.projeto.userapi.model.User;

public class UserLastNameRequestDTO {

    private String lastName;

    public UserLastNameRequestDTO() {
    }

    public UserLastNameRequestDTO(String lastName) {
        this.lastName = lastName;
    }

    public UserLastNameRequestDTO(User user) {
        this.lastName = user.getLastName();
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
