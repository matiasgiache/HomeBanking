package com.TP.TP.mappers;

import com.TP.TP.models.Transfer;
import com.TP.TP.models.dtos.TransferDTO;
import com.TP.TP.services.AccountService;
import com.TP.TP.services.UserService;
import lombok.experimental.UtilityClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransferMapper {

    @Autowired
    AccountService accountService;
    @Autowired
    private AccountMapper accountMapper;

    public Transfer dtoToTransfer(TransferDTO dto){
        return Transfer.builder()
                .amount(dto.getAmount())
                .date(dto.getDate())
                .origin(accountMapper.dtoToAccount(accountService.getAccountById(dto.getIdOrigin())))
                .target(accountMapper.dtoToAccount(accountService.getAccountById(dto.getIdTarget())))
                .build();
    }

    public TransferDTO transferToDto(Transfer transfer){
        return TransferDTO.builder()
                .id(transfer.getId())
                .amount(transfer.getAmount())
                .idTarget(transfer.getTarget().getId())
                .idOrigin(transfer.getOrigin().getId())
                .date(transfer.getDate())
                .build();
    }
}
