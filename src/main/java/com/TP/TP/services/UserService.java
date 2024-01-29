package com.TP.TP.services;

import com.TP.TP.exceptions.UserNotExistsException;
import com.TP.TP.mappers.UserMapper;
import com.TP.TP.models.Account;
import com.TP.TP.models.User;
import com.TP.TP.models.dtos.AccountDTO;
import com.TP.TP.models.dtos.UserDTO;
import com.TP.TP.models.enums.UserRole;
import com.TP.TP.repositories.AccountRepository;
import com.TP.TP.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {


    @Autowired
    private UserRepository repository;
    @Autowired
    private UserMapper userMapper;
    public List<UserDTO> getUsers(){
        List<User> users = repository.findAll();

        return users.stream()
                .map(userMapper::userToDto)
                .collect(Collectors.toList());
    }

    public UserDTO createUser(UserDTO userDto){
        User userValidated = validateUserByEmail(userDto);
        userDto.setRole(UserRole.USER);
        if (userValidated == null){
            User userSaved = repository.save(userMapper.dtoToUser(userDto));
            return userMapper.userToDto(userSaved);
        } else{
            throw new UserNotExistsException("Usuario con mail: " + userDto.getEmail() + " ya existe");
        }
    }

    public void addAccount(Long idOwner, Account account){
        Optional<User> answer = repository.findById(idOwner);
        if (answer.isPresent()){
            User owner = answer.get();
            List<Account> accounts = owner.getAccounts();
            accounts.add(account);
            owner.setAccounts(accounts);
            repository.save(owner);
        }
    }

    public UserDTO getUserById(Long id) {
        Optional<User> entity = repository.findById(id);
        if (entity.isPresent()){
            return userMapper.userToDto(entity.get());
        }else {
            throw new UserNotExistsException("Usuario no encontrado.");
        }
    }

    public String deleteUser(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
            return "El usuario con id: " + id + " ha sido eliminado";
        } else {
            throw new UserNotExistsException("El usuario a eliminar no existe");
        }
    }

    public UserDTO updateUser(Long id, UserDTO dto) {
        if (repository.existsById(id)){
            User userToModify = repository.findById(id).get();
            // Validar qué datos no vienen en null para setearlos al objeto ya creado

            // Logica del Patch
            if (dto.getName() != null){
                userToModify.setName(dto.getName());
            }

            if (dto.getSurname() != null){
                userToModify.setSurname(dto.getSurname());
            }
            if (dto.getUsername() != null){
                userToModify.setUsername(dto.getUsername());
            }
            if (dto.getRole() != null){
                userToModify.setRole(dto.getRole());
            }

            if (dto.getEmail() != null){
                userToModify.setEmail(dto.getEmail());

            }

            if (dto.getPassword() != null){
                userToModify.setPassword(dto.getPassword());
            }

            if (dto.getDni() != null){
                userToModify.setDni(dto.getDni());
            }

            User userModified = repository.save(userToModify);

            return userMapper.userToDto(userModified);
        }else{
            throw new UserNotExistsException("El usuario no existe.");
        }
    }

    public User validateUserByEmail(UserDTO dto){
        return repository.findByEmail(dto.getEmail());
    }
}