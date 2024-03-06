package com.MSGFCentralSys.MSGFCentralSys.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping({"/home","/"})
    public String mainView(Model model) {
        model.addAttribute("titulo","Welcome to the MsgFoundation's CENTRAL SYS");
        return "views/init";
    }

}


