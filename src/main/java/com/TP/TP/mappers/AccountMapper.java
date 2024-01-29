package com.TP.TP.mappers;

import com.TP.TP.models.Account;
import com.TP.TP.models.User;
import com.TP.TP.models.dtos.AccountDTO;
import com.TP.TP.repositories.UserRepository;
import com.TP.TP.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AccountMapper {

    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserRepository userRepository;

    public AccountDTO accountToDto(Account account){
        AccountDTO dto = new AccountDTO();
        dto.setAlias(account.getAlias());
        dto.setCbu(account.getCbu());
        dto.setType(account.getType());
        dto.setBalance(account.getBalance());
        dto.setId(account.getId());
        dto.setIdOwner(account.getOwner().getId());
        return dto;
    }

    public Account dtoToAccount(AccountDTO dto){

        Optional<User> answer = userRepository.findById(dto.getIdOwner());
        if (answer.isPresent()) {
            Account account = new Account();
            account.setAlias(dto.getAlias());
            account.setType(dto.getType());
            account.setCbu(dto.getCbu());
            account.setBalance(dto.getBalance());
            User owner = answer.get();
            account.setOwner(owner);
            return account;
        }
        return null;
    }
}
