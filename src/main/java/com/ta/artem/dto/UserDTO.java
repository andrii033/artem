package com.ta.artem.dto;

import com.ta.artem.model.Role;
import lombok.Getter;

@Getter
public class UserDTO {
    private final Long id;
    private final String username;
    private final String email;
    private final Role role;

    public UserDTO(long id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

}
