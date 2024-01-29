package com.TP.TP.repositories;


import com.TP.TP.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    User findByEmail(String email);
    User findByDni(String dni);
    Optional<User> findByUsername(String username);

}