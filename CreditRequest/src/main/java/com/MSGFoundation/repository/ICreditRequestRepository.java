package com.MSGFoundation.repository;

import com.MSGFoundation.model.Couple;
import com.MSGFoundation.model.CreditRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICreditRequestRepository extends JpaRepository<CreditRequest, Long> {
    List<CreditRequest> findByApplicantCouple(Couple applicantCouple);
    CreditRequest findByProcessId(String processId);
    CreditRequest findFirstByOrderByRequestDateDesc();
}
