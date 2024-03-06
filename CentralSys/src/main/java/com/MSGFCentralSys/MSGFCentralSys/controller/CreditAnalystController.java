package com.MSGFCentralSys.MSGFCentralSys.controller;

import com.MSGFCentralSys.MSGFCentralSys.dto.CreditRequestDTO;
import com.MSGFCentralSys.MSGFCentralSys.dto.TaskInfo;
import com.MSGFCentralSys.MSGFCentralSys.services.CreditAnalystServices;
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
public class CreditAnalystController {
    private final CreditAnalystServices creditAnalystServices;
    List<CreditRequestDTO> processVariablesListCA = new ArrayList<>();


    @GetMapping("/credit-analyst")
    public String CreditAnalystView(Model model){

        List<String> processIds = this.creditAnalystServices.getAllProcessByActivityId("Activity_0h13zv2");
        processVariablesListCA.clear();
        // Iterar a través de los processIds y obtener las variables para cada uno
        for (String processId : processIds) {
            CreditRequestDTO creditRequestDTO = this.creditAnalystServices.getProcessVariablesById(processId);
            TaskInfo taskInfo = this.creditAnalystServices.getTaskInfoByProcessId(processId);
            creditRequestDTO.setTaskInfo(taskInfo);
            processVariablesListCA.add(creditRequestDTO);
        }

        // Agregar la lista de variables de proceso al modelo para pasarla a la vista
        model.addAttribute("processVariablesList", processVariablesListCA);
        model.addAttribute("titulo", "REVIEW REQUEST DETAILS BY CREDIT ANALYST");
        return "views/CreditAnalyst";
    }

    @PostMapping("/view-credit-analyst")
    public  String viewTaskCreditAnalyst(@RequestParam(name = "processId") String processId, Model model){
        CreditRequestDTO creditRequestDTO = this.creditAnalystServices.getProcessVariablesById(processId);
        TaskInfo taskInfo = this.creditAnalystServices.getTaskInfoByProcessId(processId);
        creditRequestDTO.setTaskInfo(taskInfo);
        model.addAttribute("creditRequestDTO", creditRequestDTO);
        model.addAttribute("titulo", "REVIEW REQUEST DETAILS BY CREDIT ANALYST");
        return  "modals/Analyst";
    }

    @PostMapping("/approve-credit-analyst")
    public String approveTaskCreditAnalyst(@RequestParam(name = "processId") String processId){
        this.creditAnalystServices.approveTask(processId);
        return "redirect:/credit-analyst";
    }

    @PostMapping("/reject-credit-analyst")
    public String rejectTaskCreditAnalyst(@RequestParam(name = "processId") String processId){
        this.creditAnalystServices.rejectTask(processId);
        return "redirect:/credit-analyst";
    }
}
