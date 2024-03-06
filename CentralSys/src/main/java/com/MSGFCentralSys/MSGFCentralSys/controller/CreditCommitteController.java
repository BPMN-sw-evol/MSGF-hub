package com.MSGFCentralSys.MSGFCentralSys.controller;

import com.MSGFCentralSys.MSGFCentralSys.dto.CreditRequestDTO;
import com.MSGFCentralSys.MSGFCentralSys.dto.TaskInfo;
import com.MSGFCentralSys.MSGFCentralSys.services.CreditCommitteeServices;
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
public class CreditCommitteController {
    private final CreditCommitteeServices creditCommitteeServices;
    List<CreditRequestDTO> processVariablesListCC = new ArrayList<>();

    @GetMapping({"/credit-committee"})
    public String CreditCommitteView(Model model) throws IOException {
        List<String> processIds = this.creditCommitteeServices.getAllProcessByActivityId("Activity_0rzlyv4");
        processVariablesListCC.clear();

        // Crear una lista para almacenar información de variables de proceso
        // Iterar a través de los processIds y obtener las variables para cada uno
        for (String processId : processIds) {
            CreditRequestDTO creditRequestDTO = this.creditCommitteeServices.getProcessVariablesById(processId);
            TaskInfo taskInfo = this.creditCommitteeServices.getTaskInfoByProcessId(processId);
            creditRequestDTO.setTaskInfo(taskInfo);
            processVariablesListCC.add(creditRequestDTO);
        }


        // Agregar la lista de variables de proceso al modelo para pasarla a la vista
        model.addAttribute("processIds", processIds);
        model.addAttribute("processVariablesList", processVariablesListCC);
        model.addAttribute("titulo","EVALUATE CREDIT BY CREDIT COMMITTEE");
        return "views/CreditCommittee";
    }

    @PostMapping("/view-credit-committee")
    public  String viewTaskValidate(@RequestParam(name = "processId") String processId, Model model){
        CreditRequestDTO creditRequestDTO = this.creditCommitteeServices.getProcessVariablesById(processId);
        TaskInfo taskInfo = this.creditCommitteeServices.getTaskInfoByProcessId(processId);
        creditRequestDTO.setTaskInfo(taskInfo);
        model.addAttribute("creditRequestDTO", creditRequestDTO);
        model.addAttribute("titulo", "EVALUATE CREDIT BY CREDIT COMMITTEE");

            return  "modals/Committee";
    }

    @PostMapping("/approve-credit-committee")
    public String approveTaskCouple(@RequestParam(name = "processId") String processId){
        this.creditCommitteeServices.approveTask(processId);
        return "redirect:/credit-committee";
    }

    @PostMapping("/reject-credit-committee")
    public String rejectTaskCreditAnalyst(@RequestParam(name = "processId") String processId){
        this.creditCommitteeServices.rejectTask(processId);
        return "redirect:/credit-committee";
    }
}
