package com.TP.TP.models.dtos;


import com.TP.TP.models.Account;
import com.TP.TP.models.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {

    private String username;

    private Long id;

    private String email;

    private String password;

    private String name;

    private String surname;

    private String dni;

    private UserRole role;

    private List<Account> accounts;
}

