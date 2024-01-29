package com.TP.TP.controllers;

import com.TP.TP.exceptions.TransferNotFoundException;
import com.TP.TP.models.dtos.TransferDTO;
import com.TP.TP.services.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transfers")
public class TransferController {

    @Autowired
    private TransferService transferService;

    public TransferController(TransferService transferService){
        this.transferService = transferService;
    }

    @GetMapping
    public ResponseEntity<List<TransferDTO>> getTransfers(){
        List<TransferDTO> transfers = transferService.getTransfers();
        return ResponseEntity.status(HttpStatus.OK).body(transfers);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<TransferDTO> getTransferById(@PathVariable Long id) throws TransferNotFoundException {
        TransferDTO transfer = transferService.getTransferById(id);
        return ResponseEntity.status(HttpStatus.OK).body(transfer);
    }

    @GetMapping(value = "/account/{id}")
    public ResponseEntity<List<TransferDTO>> getAccountTransfers(@PathVariable Long id){
        List<TransferDTO> transfers = transferService.getAccountTransfers(id);
        return ResponseEntity.status(HttpStatus.OK).body(transfers);
    }

    @PostMapping
    public ResponseEntity<TransferDTO> performTransfer(@RequestBody TransferDTO dto){
        return ResponseEntity.status(HttpStatus.CREATED).body(transferService.performTransfer(dto));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<TransferDTO> updateTransfer(@PathVariable Long id, @RequestBody TransferDTO transfer) throws TransferNotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(transferService.updateTransfer(id, transfer));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> deleteTransfer(@PathVariable Long id){
        return ResponseEntity.status(HttpStatus.OK).body(transferService.deleteTransfer(id));
    }
}
