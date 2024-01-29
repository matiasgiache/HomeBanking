package com.TP.TP.services;

import com.TP.TP.exceptions.AccountNotFoundException;
import com.TP.TP.exceptions.UserNotExistsException;
import com.TP.TP.mappers.AccountMapper;
import com.TP.TP.models.Account;
import com.TP.TP.models.User;
import com.TP.TP.models.dtos.AccountDTO;
import com.TP.TP.models.enums.AccountType;
import com.TP.TP.repositories.AccountRepository;
import com.TP.TP.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AccountService {

    @Autowired
    private UserService userService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AccountMapper accountMapper;

    public List<AccountDTO> getAccountsByUserId(Long idUser){
        List<Account> accounts = accountRepository.findByUserId(idUser);

        return accounts.stream()
                .map(accountMapper::accountToDto)
                .collect(Collectors.toList());
    }

    public List<AccountDTO> getAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accounts.stream()
                .map(accountMapper::accountToDto)
                .collect(Collectors.toList());
    }

    public AccountDTO createAccount(AccountDTO dto) {
        Optional<User> user = userRepository.findById(dto.getIdOwner());
        if (user.isEmpty()){
            throw new UserNotExistsException("El usuario no existe.");
        }
        dto.setType(AccountType.SAVINGS_BANK);
        dto.setBalance(BigDecimal.ZERO);
        Account newAccount = accountRepository.save(accountMapper.dtoToAccount(dto));
        userService.addAccount(dto.getIdOwner(),newAccount);
        return accountMapper.accountToDto(newAccount);
    }

    public AccountDTO getAccountById(Long id) {
        Optional<Account> entity = accountRepository.findById(id);
        if (entity.isPresent()){
            return accountMapper.accountToDto(entity.get());
        }else{
            throw new AccountNotFoundException("La cuenta solicitada no existe.");
        }
    }
    public String deleteAccount(Long id) throws Exception {
        if (accountRepository.existsById(id)){
            accountRepository.deleteById(id);
            return "La cuenta con id: " + id + " ha sido eliminada";
        } else {
            throw new AccountNotFoundException("La cuenta a eliminar no existe");
        }
    }

    public AccountDTO updateAccount(Long id, AccountDTO dto) {
        if (accountRepository.existsById(id)) {
            Account accountToModify = accountRepository.findById(id).get();

            if (dto.getAlias() != null) {
                accountToModify.setAlias(dto.getAlias());
            }

            if (dto.getType() != null) {
                accountToModify.setType(dto.getType());
            }

            if (dto.getCbu() != null) {
                accountToModify.setCbu(dto.getCbu());
            }

            if (dto.getBalance() != null) {
                accountToModify.setBalance(dto.getBalance());
            }

            Account accountModified = accountRepository.save(accountToModify);

            return accountMapper.accountToDto(accountModified);
        }
        return null;
    }
}
