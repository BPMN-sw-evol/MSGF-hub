package com.MSGFoundation.dto;

import com.MSGFoundation.model.Person;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreditInfoDTO {
    private Long codRequest;
    private List<Person> people;
    private Long applicantCoupleId;
    private String processId;
    private Long marriageYears;
    private Boolean bothEmployees;
    private Long housePrices;
    private Long quotaValue;
    private Long coupleSavings;
    private String status;
    private LocalDateTime requestDate;
    private TaskInfo taskInfo;
    private Long countReviewCR;
    private MultipartFile pdfSupport;
    private String pdfSupportName;
    private MultipartFile workSupport;
    private String workSupportName;
}
