package com.TP.TP.controllers;

import com.TP.TP.models.dtos.AccountDTO;
import com.TP.TP.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping
    public ResponseEntity<List<AccountDTO>> getAccounts(){
        List<AccountDTO> accounts = accountService.getAccounts();
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> getAccount(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.getAccountById(id));
    }

    @GetMapping(value = "/user/{id}")
    public ResponseEntity<List<AccountDTO>> getAccountsUser(@PathVariable Long id){
        List<AccountDTO> accounts = accountService.getAccountsByUserId(id);
        return ResponseEntity.status(HttpStatus.OK).body(accounts);
    }

    @PostMapping
    public ResponseEntity<AccountDTO> createAccount(@RequestBody AccountDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(accountService.createAccount(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<AccountDTO> updateAccount(@PathVariable Long id, @RequestBody AccountDTO dto){
        return ResponseEntity.status(HttpStatus.OK).body(accountService.updateAccount(id, dto));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteAccount(@PathVariable Long id) throws Exception {
        return ResponseEntity.status(HttpStatus.OK).body(accountService.deleteAccount(id));
    }
}
