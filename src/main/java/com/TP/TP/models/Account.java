package com.TP.TP.models;

import com.TP.TP.models.enums.AccountType;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "tipo_cuenta")
    private AccountType type;

    private String cbu;

    private String alias;

    @Column(name = "saldo")
    private BigDecimal balance;

    @ManyToOne
    private User owner;

    @OneToMany
    @Column(name = "prestamos")
    private List<Loan> loans;

    @OneToMany
    @Column(name = "transferencias")
    private List<Transfer> transfers;

}
