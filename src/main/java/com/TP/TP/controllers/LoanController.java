package com.TP.TP.controllers;
import com.TP.TP.models.dtos.LoanDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.TP.TP.services.LoanService;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    @Autowired
    private  LoanService service;

    @PostMapping
        public ResponseEntity<LoanDTO> createLoan(@RequestBody LoanDTO loanDtoRequest) {
        LoanDTO loan = service.createLoan(loanDtoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(loan);
    }

    @GetMapping
    public ResponseEntity<List<LoanDTO>> getLoans(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.getLoans());
    }
}
