package com.MSGFoundation.controller;

import com.MSGFoundation.dto.CoupleDTO;
import com.MSGFoundation.dto.CreditInfoDTO;
import com.MSGFoundation.model.Couple;
import com.MSGFoundation.model.CreditRequest;
import com.MSGFoundation.model.Person;
import com.MSGFoundation.service.impl.*;
import com.MSGFoundation.util.RequestStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import javax.persistence.EntityNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("credit_request")
public class CreditRequestController {
    private final CreditRequestServiceImpl creditRequestService;
    private final CoupleServiceImpl coupleService;
    private final PersonServiceImpl personService;
    private final MarriedCoupleServiceImpl marriedCoupleService;
    private final S3ServiceImpl s3Service;

    @GetMapping("/")
    public List<CreditRequest> getAllCreditRequest(){
        return creditRequestService.getAllCreditRequest();
    }

    @GetMapping("/{coupleId}")
    public ResponseEntity<List<CreditRequest>> findCreditRequestByCouple(@PathVariable Long coupleId){
        try{
            Couple couple = coupleService.getCoupleById(coupleId);
            List<CreditRequest> creditRequest = creditRequestService.findCreditByCouple(couple);
            return ResponseEntity.ok(creditRequest);
        }catch (EntityNotFoundException e){
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/create")
    public RedirectView createCreditRequest(@ModelAttribute CreditInfoDTO creditInfoDTO,
                                            @RequestParam("pdfSupport") MultipartFile pdfSupport,
                                            @RequestParam("workSupport") MultipartFile workSupport,
                                            RedirectAttributes redirectAttributes) throws IOException {
        List<Person> people = creditInfoDTO.getPeople();
        Person partner1 = people.get(0);
        Person partner2 = people.get(1);

        personService.createPerson(partner1);
        personService.createPerson(partner2);
        CoupleDTO coupleDTO = new CoupleDTO();
        coupleDTO.setPartner1Id(partner1.getId());
        coupleDTO.setPartner2Id(partner2.getId());
        coupleService.createCouple(coupleDTO);


        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setMarriageYears(creditInfoDTO.getMarriageYears());
        creditRequest.setBothEmployees(creditInfoDTO.getBothEmployees());
        creditRequest.setHousePrices(creditInfoDTO.getHousePrices());
        creditRequest.setQuotaValue(creditInfoDTO.getQuotaValue());
        creditRequest.setCoupleSavings(creditInfoDTO.getCoupleSavings());
        creditRequest.setStatus(RequestStatus.DRAFT.toString());
        LocalDateTime currentDate = LocalDateTime.now();
        creditRequest.setRequestDate(currentDate);
        Long coupleId = coupleService.getCouplebyIds(partner1.getId(), partner2.getId());
        Couple couple = coupleService.getCoupleById(coupleId);
        creditRequest.setApplicantCouple(couple);
        creditRequest.setCountReviewCR(0L);
        creditRequest.setTermInYears(20L);

        String pdfSupportName = String.format("%s-%s-pdf-support.pdf", partner1.getId(), partner2.getId());
        String workSupportName = String.format("%s-%s-work-support.pdf", partner1.getId(), partner2.getId());

        creditRequest.setPdfSupport(pdfSupportName);
        creditRequest.setWorkSupport(workSupportName);

        creditInfoDTO.setApplicantCoupleId(coupleId);
        creditInfoDTO.setRequestDate(currentDate);
        creditInfoDTO.setCountReviewCR(0L);

        creditInfoDTO.setPdfSupportName(pdfSupportName);
        creditInfoDTO.setWorkSupportName(workSupportName);
        creditRequest.setPayment(false);

        redirectAttributes.addAttribute("coupleId", coupleId);

        creditRequestService.createCreditRequest(creditRequest);
        List<CreditRequest> updateCredit = creditRequestService.findCreditByCouple(couple);
        creditInfoDTO.setCodRequest(updateCredit.get(0).getCodRequest());
        String processId = marriedCoupleService.startProcessInstance(creditInfoDTO);
        System.out.println("***** PROCESS IDD: "+processId);
        for (CreditRequest request : updateCredit) {
            if ("DRAFT".equals(request.getStatus())) {
                request.setProcessId(processId);
                creditRequestService.updateCreditRequest(request.getCodRequest(), request);
            }
        }

        s3Service.uploadFile(pdfSupport,pdfSupportName);
        s3Service.uploadFile(workSupport, workSupportName);

        return new RedirectView("/view-credit");
    }


    @PostMapping("/update")
    public RedirectView updateCreditRequest(@ModelAttribute("creditInfoDTO") CreditInfoDTO creditInfoDTO){

        List<Person> people = creditInfoDTO.getPeople();
        personService.updatePerson(people.get(0).getId(),people.get(0));
        personService.updatePerson(people.get(1).getId(),people.get(1));

        Long coupleId = coupleService.getCouplebyIds(people.get(0).getId(), people.get(1).getId());
        Couple couple = coupleService.getCoupleById(coupleId);
        List<CreditRequest> creditId = creditRequestService.findCreditByCouple(couple);

        CreditRequest creditRequest = new CreditRequest();
        creditRequest.setMarriageYears(creditInfoDTO.getMarriageYears());
        creditRequest.setBothEmployees(creditInfoDTO.getBothEmployees());
        creditRequest.setHousePrices(creditInfoDTO.getHousePrices());
        creditRequest.setQuotaValue(creditInfoDTO.getQuotaValue());
        creditRequest.setCoupleSavings(creditInfoDTO.getCoupleSavings());
        creditRequest.setApplicantCouple(couple);
        creditRequest.setStatus(RequestStatus.DRAFT.toString());
        LocalDateTime currentDate = LocalDateTime.now();
        creditRequest.setRequestDate(currentDate);
        creditRequest.setPayment(false);
        creditRequest.setTermInYears(20L);
        creditRequest.setCountReviewCR(marriedCoupleService.getCountReview(creditId.get(0).getProcessId()));
        creditRequest.setProcessId(creditId.get(0).getProcessId());

        String pdfSupportName = String.format("%s-%s-pdf-support.pdf", people.get(0).getId(), people.get(1).getId());
        String workSupportName = String.format("%s-%s-work-support.pdf", people.get(0).getId(), people.get(1).getId());

        creditRequest.setPdfSupport(pdfSupportName);
        System.out.println("aqui pdf support: "+creditInfoDTO.getPdfSupport().getName());
        creditRequest.setWorkSupport(workSupportName);
        System.out.println("aqui work support: "+creditInfoDTO.getWorkSupport().getName());

        creditRequestService.updateCreditRequest(creditId.get(0).getCodRequest(), creditRequest);
        String result = marriedCoupleService.updateProcessVariables(creditRequest.getProcessId(),creditRequest);

        return new RedirectView("/view-credit?coupleId="+result);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteCreditRequest(@PathVariable Long id){
        creditRequestService.deleteCreditRequest(id);
    }

    @GetMapping("/findbyid/{processId}")
    public CreditRequest getCreditRequestByProcessId(@PathVariable String processId){
        return creditRequestService.getCreditRequestByProcessId(processId);
    }

    @GetMapping("/latest-credit-request")
    public CreditRequest getLatestCreditRequest() {
        return creditRequestService.findFirstByOrderByRequestDateDesc();
    }
}
