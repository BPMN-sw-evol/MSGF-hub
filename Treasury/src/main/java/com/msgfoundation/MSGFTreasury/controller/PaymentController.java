package com.msgfoundation.MSGFTreasury.controller;

import com.msgfoundation.MSGFTreasury.model.Payment;
import com.msgfoundation.MSGFTreasury.services.Treasury_InfDesAParYAVen;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final Treasury_InfDesAParYAVen treasuryInfDesAParYAVen;
    @PostMapping("/message")
    public String paymentMessage(@RequestBody Payment payment){
        System.out.println("pago realizado: "+payment);
        this.treasuryInfDesAParYAVen.sendPaymentNotification(payment);
        return "MESSAGE: The payment was sent";
    }

    @GetMapping("/update/{codRequest}")
    public String paymentSuccessfully(@PathVariable Long codRequest) throws SQLException {
        treasuryInfDesAParYAVen.updatePayment(codRequest);
        return "CONTROLLER: The payment was made";
    }

}
