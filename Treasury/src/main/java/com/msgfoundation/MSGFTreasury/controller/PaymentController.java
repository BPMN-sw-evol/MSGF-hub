package com.msgfoundation.MSGFTreasury.controller;

import com.msgfoundation.MSGFTreasury.model.Payment;
import com.msgfoundation.MSGFTreasury.services.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final PaymentService paymentService;
    @PostMapping("/message")
    public String paymentMessage(@RequestBody Payment payment){
        System.out.println("pago realizado: "+payment);
        this.paymentService.sendPaymentNotification(payment);
        return "MESSAGE: The payment was sent";
    }

    @GetMapping("/update/{codRequest}")
    public String paymentSuccessfully(@PathVariable Long codRequest) throws SQLException {
        paymentService.updatePayment(codRequest);
        return "CONTROLLER: The payment was made";
    }

}
