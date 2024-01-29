package com.TP.TP.mappers;

import com.TP.TP.models.Account;
import com.TP.TP.models.User;
import com.TP.TP.models.dtos.UserDTO;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {


    public User dtoToUser(UserDTO dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setRole(dto.getRole());
        user.setName(dto.getName());
        user.setSurname(dto.getSurname());
        user.setDni(dto.getDni());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    public UserDTO userToDto(User user){
        UserDTO dto = new UserDTO();
        dto.setRole(user.getRole());
        dto.setUsername(user.getUsername());
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setSurname(user.getSurname());
        dto.setDni(user.getDni());
        dto.setEmail(user.getEmail());
        dto.setPassword(user.getPassword());
        return dto;
    }
}
