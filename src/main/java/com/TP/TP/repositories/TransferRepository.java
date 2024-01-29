package com.TP.TP.repositories;

import com.TP.TP.models.Transfer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransferRepository extends JpaRepository<Transfer,Long> {

    @Query("SELECT t FROM Transfer t WHERE t.origin.id =:idOrigin")
    public List<Transfer> findByOriginId(@Param("idOrigin") Long idOrigin);
}
