package com.TP.TP.models.dtos;

import com.TP.TP.models.Account;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransferDTO {

    private Long id;

    private Long idOrigin;

    private Long idTarget;

    private Date date;

    private BigDecimal amount;
}
