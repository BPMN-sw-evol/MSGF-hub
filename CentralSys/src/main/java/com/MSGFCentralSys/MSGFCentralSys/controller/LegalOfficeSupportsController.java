package com.MSGFCentralSys.MSGFCentralSys.controller;

import com.MSGFCentralSys.MSGFCentralSys.dto.CreditRequestDTO;
import com.MSGFCentralSys.MSGFCentralSys.dto.TaskInfo;
import com.MSGFCentralSys.MSGFCentralSys.services.CreditAnalystServices;
import com.MSGFCentralSys.MSGFCentralSys.services.LegalOfficeSupportsServices;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class LegalOfficeSupportsController {

    private final LegalOfficeSupportsServices legalOfficeSupportsServices;
    List<CreditRequestDTO> processVariablesListCA = new ArrayList<>();


    @GetMapping("/legal-office-supports")
    public String LegalOfficeSupportsView(Model model){

        List<String> processIds = this.legalOfficeSupportsServices.getAllProcessByActivityId("Activity_15y8fg5");
        processVariablesListCA.clear();
        // Iterar a través de los processIds y obtener las variables para cada uno
        for (String processId : processIds) {
            CreditRequestDTO creditRequestDTO = this.legalOfficeSupportsServices.getProcessVariablesById(processId);
            TaskInfo taskInfo = this.legalOfficeSupportsServices.getTaskInfoByProcessId(processId);
            creditRequestDTO.setTaskInfo(taskInfo);
            processVariablesListCA.add(creditRequestDTO);
        }

        // Agregar la lista de variables de proceso al modelo para pasarla a la vista
        model.addAttribute("processVariablesList", processVariablesListCA);
        model.addAttribute("titulo", "REVIEW REQUEST SUPPORTS BY LEGAL OFFICE");
        return "views/LegalOfficeSupports";
    }

    @PostMapping("/view-legal-office-supports")
    public  String viewTaskSupports(@RequestParam(name = "processId") String processId, Model model){
        CreditRequestDTO creditRequestDTO = this.legalOfficeSupportsServices.getProcessVariablesById(processId);
        TaskInfo taskInfo = this.legalOfficeSupportsServices.getTaskInfoByProcessId(processId);
        creditRequestDTO.setTaskInfo(taskInfo);
        model.addAttribute("creditRequestDTO", creditRequestDTO);
        model.addAttribute("titulo", "REVIEW REQUEST SUPPORTS BY LEGAL OFFICE");
        return  "modals/LegalOfficeSupports";
    }

    @PostMapping("/approve-legal-office-supports")
    public String approveTaskCouple(@RequestParam(name = "processId") String processId){
        this.legalOfficeSupportsServices.approveTask(processId);
        return "redirect:/legal-office-supports";
    }

    @PostMapping("/reject-legal-office-supports")
    public String rejectTaskCouple(@RequestParam(name = "processId") String processId){
        this.legalOfficeSupportsServices.rejectTask(processId);
        return "redirect:/legal-office-supports";
    }
}
