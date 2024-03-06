package com.MSGFCentralSys.MSGFCentralSys.controller;

import com.MSGFCentralSys.MSGFCentralSys.dto.CreditRequestDTO;
import com.MSGFCentralSys.MSGFCentralSys.dto.TaskInfo;
import com.MSGFCentralSys.MSGFCentralSys.services.LegalOfficeViabilityServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LegalOfficeViabilityController {
    private final LegalOfficeViabilityServices legalOfficeViabilityServices;
    List<CreditRequestDTO> processVariablesListCA = new ArrayList<>();


    @GetMapping("/legal-office-viability")
    public String CreditAnalystValidateView(Model model) throws IOException {

        List<String> processIds = this.legalOfficeViabilityServices.getAllProcessByActivityId("Activity_012ypn5");
        System.out.println(processIds.toString());
        processVariablesListCA.clear();
        // Iterar a través de los processIds y obtener las variables para cada uno
        for (String processId : processIds) {
            CreditRequestDTO creditRequestDTO = this.legalOfficeViabilityServices.getProcessVariablesById(processId);
            TaskInfo taskInfo = this.legalOfficeViabilityServices.getTaskInfoByProcessId(processId);
            creditRequestDTO.setTaskInfo(taskInfo);
            processVariablesListCA.add(creditRequestDTO);
        }

        // Agregar la lista de variables de proceso al modelo para pasarla a la vista
        model.addAttribute("processVariablesList", processVariablesListCA);
        model.addAttribute("titulo", "DETERMINE FINANCIAL VIABILITY BY LEGAL OFFICE");
        return "views/LegalOfficeViability";

    }

    @PostMapping("/view-legal-office-viability")
    public  String viewTaskValidate(@RequestParam(name = "processId") String processId, Model model){
        CreditRequestDTO creditRequestDTO = this.legalOfficeViabilityServices.getProcessVariablesById(processId);
        TaskInfo taskInfo = this.legalOfficeViabilityServices.getTaskInfoByProcessId(processId);
        creditRequestDTO.setTaskInfo(taskInfo);
        model.addAttribute("creditRequestDTO", creditRequestDTO);
        model.addAttribute("titulo", "DETERMINE FINANCIAL VIABILITY BY LEGAL OFFICE");

        return  "modals/LegalOfficeViability";
    }

    @PostMapping("/approve-legal-office-viability")
    public String approveTaskValidate(@RequestParam(name = "processId") String processId){
        this.legalOfficeViabilityServices.approveTask(processId);
        return "redirect:/legal-office-viability";
    }

    @PostMapping("/reject-legal-office-viability")
    public String rejectTaskValidate(@RequestParam(name = "processId") String processId){
        this.legalOfficeViabilityServices.rejectTask(processId);
        return "redirect:/legal-office-viability";
    }
}
