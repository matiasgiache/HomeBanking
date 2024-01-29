package com.TP.TP.services;

import com.TP.TP.exceptions.AccountNotFoundException;
import com.TP.TP.mappers.LoanMapper;
import com.TP.TP.models.Account;
import com.TP.TP.models.dtos.LoanDTO;
import com.TP.TP.repositories.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.TP.TP.models.Loan;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.TP.TP.repositories.LoanRepository;

@Service
public class LoanService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private LoanRepository loanRepository;
    @Autowired
    private LoanMapper loanMapper;

    public LoanDTO createLoan(LoanDTO dto) {
        Optional<Account> account = accountRepository.findById(dto.getIdAccount());
        if (account.isEmpty()) {
            throw new AccountNotFoundException("Esta cuenta no existe");
        }
        dto.setAmount(estimateInterest(dto.getAmount(), dto.getDays()));
        dto.setExpiration(LocalDate.now().plusDays(dto.getDays()));
        Account client = account.get();
        client.setBalance(client.getBalance().add(dto.getAmount()));
        accountRepository.save(client);
        Loan newLoan = new Loan();
        newLoan.setAccount(account.get());
        newLoan.setDescription(dto.getDescription());
        newLoan.setAmount(dto.getAmount());
        newLoan.setExpiration(dto.getExpiration());
        loanRepository.save(newLoan);
        return loanMapper.loanToDTO(newLoan);
    }

    public BigDecimal estimateInterest(BigDecimal amount, int days) {
        double finalAmount;
        double calculus;
        if (days < 30) {
            calculus = (amount.doubleValue() * days * 0.020);
        } else {
            calculus = (amount.doubleValue() * days * 0.030);
        }
        finalAmount = amount.doubleValue() - calculus;
        return BigDecimal.valueOf(finalAmount);
    }

    public List<LoanDTO> getLoans() {
        List<Loan> loans = loanRepository.findAll();
        return loans.stream()
                .map(loanMapper::loanToDTO)
                .collect(Collectors.toList());
    }

}
