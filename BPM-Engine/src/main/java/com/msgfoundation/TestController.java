package com.msgfoundation;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {
    @GetMapping("/respuesta")
    public String respuesta(){
        System.out.println("respuesta curl");
        return "respuesta";

    }
}
