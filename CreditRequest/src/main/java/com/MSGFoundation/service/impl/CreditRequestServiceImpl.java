package com.MSGFoundation.service.impl;

import com.MSGFoundation.model.Couple;
import com.MSGFoundation.model.CreditRequest;
import com.MSGFoundation.repository.ICreditRequestRepository;
import com.MSGFoundation.service.CreditRequestService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditRequestServiceImpl implements CreditRequestService {
    private final ICreditRequestRepository creditRequestRepository;

    public List<CreditRequest> getAllCreditRequest(){
        return creditRequestRepository.findAll();
    }

    public void createCreditRequest(CreditRequest creditRequest){
        creditRequestRepository.save(creditRequest);
    }

    public void updateCreditRequest(Long id, CreditRequest creditRequest){
        if(creditRequestRepository.existsById(id)){
            creditRequest.setCodRequest(id);
            creditRequestRepository.save(creditRequest);
        }
    }

    public void deleteCreditRequest(Long id){
        creditRequestRepository.deleteById(id);
    }

    public List<CreditRequest> findCreditByCouple(Couple couple) {
        return creditRequestRepository.findByApplicantCouple(couple);
    }

    public CreditRequest getCreditRequestByProcessId(String processId) {
        return creditRequestRepository.findByProcessId(processId);
    }

    public CreditRequest findFirstByOrderByRequestDateDesc() {
        return creditRequestRepository.findFirstByOrderByRequestDateDesc();
    }
}
