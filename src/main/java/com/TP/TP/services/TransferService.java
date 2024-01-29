package com.TP.TP.services;

import com.TP.TP.exceptions.AccountNotFoundException;
import com.TP.TP.exceptions.InsufficientFoundsException;
import com.TP.TP.exceptions.TransferNotFoundException;
import com.TP.TP.mappers.TransferMapper;
import com.TP.TP.models.Account;
import com.TP.TP.models.Transfer;
import com.TP.TP.models.dtos.TransferDTO;
import com.TP.TP.repositories.AccountRepository;
import com.TP.TP.repositories.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Date;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TransferService {

    @Autowired
    private TransferRepository transferRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransferMapper transferMapper;

    public List<TransferDTO> getTransfers(){
        List<Transfer> transfers = transferRepository.findAll();
        return transfers.stream()
                .map(transferMapper::transferToDto)
                .collect(Collectors.toList());
    }

    public TransferDTO getTransferById(Long id) throws TransferNotFoundException {
        Transfer transfer = transferRepository.findById(id).orElseThrow(() ->
                new TransferNotFoundException("Transferencia con el id: " + id + " no encontrada"));
        return transferMapper.transferToDto(transfer);
    }

    public List<TransferDTO> getAccountTransfers(Long id){
        List<Transfer> transfers = transferRepository.findByOriginId(id);

        return  transfers.stream()
                .map(transferMapper::transferToDto)
                .collect(Collectors.toList());
    }

    public TransferDTO updateTransfer(Long id, TransferDTO transferDto) throws TransferNotFoundException {
        Transfer transfer = transferRepository.findById(id)
                .orElseThrow(() -> new TransferNotFoundException("Transfer not found with id: " + id));
        Transfer updatedTransfer = transferMapper.dtoToTransfer(transferDto);
        updatedTransfer.setId(transfer.getId());
        return transferMapper.transferToDto(transferRepository.save(updatedTransfer));
    }

    public String deleteTransfer(Long id){
        if (transferRepository.existsById(id)){
            transferRepository.deleteById(id);
            return "Se ha eliminado la transferencia";
        } else {
            return "No se ha eliminado la transferencia";
        }
    }

    @Transactional
    public TransferDTO performTransfer(TransferDTO dto) throws InsufficientFoundsException {

        Account originAccount = accountRepository.findById(dto.getIdOrigin())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: "
                        + dto.getIdOrigin()));
        Account destinationAccount = accountRepository.findById(dto.getIdTarget())
                .orElseThrow(() -> new AccountNotFoundException("Account not found with id: "
                        + dto.getIdTarget()));

        if (originAccount.getBalance().compareTo(dto.getAmount()) < 0) {
            throw new InsufficientFoundsException("La cuenta con el id: " + dto.getIdOrigin() +
                    " tiene fondos insuficientes");
        }

        originAccount.setBalance(originAccount.getBalance().subtract(dto.getAmount()));
        destinationAccount.setBalance(destinationAccount.getBalance().add(dto.getAmount()));

        // Crear la transferencia y guardarla en la base de datos
        Transfer transfer = new Transfer();
        // Creamos un objeto del tipo Date para obtener la fecha actual
        Date date = new Date();
        // Seteamos el objeto fecha actual en el transferDto
        transfer.setDate(date);
        transfer.setOrigin(originAccount);
        transfer.setTarget(destinationAccount);
        transfer.setAmount(dto.getAmount());
        transfer = transferRepository.save(transfer);

        List<Transfer> transfers = originAccount.getTransfers();
        transfers.add(transfer);
        originAccount.setTransfers(transfers);

        accountRepository.save(originAccount);
        accountRepository.save(destinationAccount);

        // Devolver el DTO de la transferencia realizada
        return transferMapper.transferToDto(transfer);
    }

}
