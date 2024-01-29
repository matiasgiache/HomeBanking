package com.TP.TP.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.TP.TP.models.Loan;
import org.springframework.stereotype.Repository;

@Repository
public interface LoanRepository extends JpaRepository<Loan, Long> {
}
