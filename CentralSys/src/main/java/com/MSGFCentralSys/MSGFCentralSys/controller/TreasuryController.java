package com.MSGFCentralSys.MSGFCentralSys.controller;

import com.MSGFCentralSys.MSGFCentralSys.dto.CreditRequestDTO;
import com.MSGFCentralSys.MSGFCentralSys.dto.TaskInfo;
import com.MSGFCentralSys.MSGFCentralSys.services.Treasury_AprProDePag;
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
public class TreasuryController {

    private final Treasury_AprProDePag treasuryAprProDePag;
    List<CreditRequestDTO> processVariablesListCC = new ArrayList<>();

    @GetMapping({"/treasury"})
    public String CreditCommitteView(Model model) throws IOException {
        List<String> processIds = this.treasuryAprProDePag.getAllProcessByActivityId("Activity_0hyf97s");
        processVariablesListCC.clear();

        // Crear una lista para almacenar información de variables de proceso
        // Iterar a través de los processIds y obtener las variables para cada uno
        for (String processId : processIds) {
            CreditRequestDTO creditRequestDTO = this.treasuryAprProDePag.getProcessVariablesById(processId);
            TaskInfo taskInfo = this.treasuryAprProDePag.getTaskInfoByProcessId(processId);
            creditRequestDTO.setTaskInfo(taskInfo);
            System.out.println("task info: "+taskInfo.toString());
            processVariablesListCC.add(creditRequestDTO);
        }


        // Agregar la lista de variables de proceso al modelo para pasarla a la vista
        model.addAttribute("processIds", processIds);
        model.addAttribute("processVariablesList", processVariablesListCC);
        model.addAttribute("titulo","APPROVE PAYMENT PROCESS BY TREASURY AREA");
        return "views/Treasury";
    }

    @PostMapping("/view-treasury")
    public  String viewTaskValidate(@RequestParam(name = "processId") String processId, Model model){
        CreditRequestDTO creditRequestDTO = this.treasuryAprProDePag.getProcessVariablesById(processId);
        TaskInfo taskInfo = this.treasuryAprProDePag.getTaskInfoByProcessId(processId);
        creditRequestDTO.setTaskInfo(taskInfo);
        model.addAttribute("creditRequestDTO", creditRequestDTO);
        model.addAttribute("titulo", "APPROVE PAYMENT PROCESS BY TREASURY AREA");

        return  "modals/Treasury";
    }

    @PostMapping("/approve-treasury")
    public String approveTaskCouple(@RequestParam(name = "processId") String processId){
        this.treasuryAprProDePag.approveTask(processId);
        return "redirect:/treasury";
    }
}
