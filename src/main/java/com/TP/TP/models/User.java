package com.TP.TP.models;

import com.TP.TP.models.enums.UserRole;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(name = "contrasena")
    private String password;

    @Column(name = "nombre")
    private String name;

    @Column(name = "apellido")
    private String surname;

    private String dni;

    private String email;

    @OneToMany
    @Column(name = "cuentas")
    private List<Account> accounts;

    @Enumerated(EnumType.STRING)
    private UserRole role;

}
