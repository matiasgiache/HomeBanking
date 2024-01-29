package com.TP.TP.mappers;

import com.TP.TP.models.Account;
import com.TP.TP.models.Loan;
import com.TP.TP.models.dtos.LoanDTO;
import com.TP.TP.repositories.AccountRepository;
import com.TP.TP.services.UserService;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class LoanMapper {

    @Autowired
    private AccountRepository accountRepository;

    public LoanDTO loanToDTO(Loan loan){
        LoanDTO dto = new LoanDTO();
        dto.setId(loan.getId());
        dto.setDescription(loan.getDescription());
        dto.setAmount(loan.getAmount());
        dto.setExpiration(loan.getExpiration());
        dto.setIdAccount(loan.getAccount().getId());
        return dto;
    }

    public Loan dtoToLoan(LoanDTO dto){

        Optional<Account> answer = accountRepository.findById(dto.getIdAccount());
        if (answer.isPresent()){
            Loan loan = new Loan();
            loan.setDescription(dto.getDescription());
            loan.setAmount(dto.getAmount());
            loan.setExpiration(dto.getExpiration());
            Account account = answer.get();
            loan.setAccount(account);
            return loan;
        }
        return null;
    }
}
