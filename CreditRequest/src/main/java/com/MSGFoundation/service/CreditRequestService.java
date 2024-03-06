package com.MSGFoundation.service;

import com.MSGFoundation.model.Couple;
import com.MSGFoundation.model.CreditRequest;

import java.util.List;

public interface CreditRequestService {
    List<CreditRequest> getAllCreditRequest();
    void createCreditRequest(CreditRequest creditRequest);
    void updateCreditRequest(Long id, CreditRequest creditRequest);
    void deleteCreditRequest(Long id);
    List<CreditRequest> findCreditByCouple(Couple couple);
    CreditRequest getCreditRequestByProcessId(String processId);
    CreditRequest findFirstByOrderByRequestDateDesc();
}
