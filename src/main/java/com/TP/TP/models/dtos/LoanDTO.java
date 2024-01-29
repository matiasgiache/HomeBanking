package com.TP.TP.models.dtos;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class LoanDTO {

    private Long id;
    private String description;
    private BigDecimal amount;
    private LocalDate expiration;
    private Long idAccount;
    private int days;
}
