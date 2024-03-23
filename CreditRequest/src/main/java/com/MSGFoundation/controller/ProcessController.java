package com.MSGFoundation.controller;

import com.MSGFoundation.dto.CreditInfoDTO;
import com.MSGFoundation.service.impl.MarriedCoupleServiceImpl;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ProcessController {
    private final MarriedCoupleServiceImpl marriedCoupleService;

    public ProcessController(MarriedCoupleServiceImpl marriedCoupleService) {
        this.marriedCoupleService = marriedCoupleService;
    }

    @PostMapping("/startProcess")
    public String startProcessInstance(@ModelAttribute CreditInfoDTO creditInfoDTO) {
        this.marriedCoupleService.startProcessInstance(creditInfoDTO);
        return "redirect:/view-credit";
    }

    @GetMapping("/complete")
    public String completeTask(@RequestParam(name = "taskId") String taskId) {
        String resultado = this.marriedCoupleService.completeTask(taskId);
        return "redirect:/view-credit?coupleId="+resultado;
    }

    @GetMapping("/message-event")
    public String messageEvent(@RequestParam(name = "taskId") String taskId){
        marriedCoupleService.messageEvent(taskId);
        return "redirect:/view-credit";
    }
}
