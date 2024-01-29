package com.TP.TP.models.dtos;

import com.TP.TP.models.Loan;
import com.TP.TP.models.Transfer;
import com.TP.TP.models.User;
import com.TP.TP.models.enums.AccountType;
import lombok.*;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

    private Long id;
    private AccountType type;
    private String cbu;
    private String alias;
    private BigDecimal balance;
    private Long idOwner;
    private List<Loan> loans;
    private List<Transfer> transfers;
}
