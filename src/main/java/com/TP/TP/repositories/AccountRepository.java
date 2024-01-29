package com.TP.TP.repositories;

import com.TP.TP.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    Account findByAlias(String alias);

    @Query("SELECT a FROM Account a WHERE a.owner.id =:idUser")
    public List<Account> findByUserId(@Param("idUser") Long idUser);
}
