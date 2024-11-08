package com.msgfoundation.messages;

import com.msgfoundation.annotations.BPMNGetterVariables;
import com.msgfoundation.annotations.BPMNTask;
import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Locale;

@Component
@BPMNTask(type = "sendTask", name = "Informar concepto jurídico no viable")
public class LegalOffice_InfConJurNoVia implements JavaDelegate {
    @Autowired
    private JavaMailSender mailSender;
    @Autowired
    private TemplateEngine templateEngine;
    @Override
    @BPMNGetterVariables( variables = { "coupleName1", "coupleName2", "coupleEmail2", "coupleEmail1", "bothEmployees", "pdfSupport" })
    public void execute(DelegateExecution delegateExecution) throws Exception {
        // Obtener variables del proceso
        String processID = delegateExecution.getProcessInstanceId();
        String coupleName1 = (String) delegateExecution.getVariable("coupleName1");
        String coupleName2 = (String) delegateExecution.getVariable("coupleName2");
        String coupleEmail1 = (String) delegateExecution.getVariable("coupleEmail1");
        String coupleEmail2 = (String) delegateExecution.getVariable("coupleEmail2");
        Boolean bothEmployees = (Boolean) delegateExecution.getVariable("bothEmployees");
        String pdfSupport = (String) delegateExecution.getVariable("pdfSupport");


        // Construir el mensaje de correo electrónico usando Thymeleaf
        String subject = "Informe de Rechazo por Comité de Crédito";
        String templateName = "InformeRechazoComite";
        Context context = new Context(Locale.getDefault());
        context.setVariable("processId",processID);
        context.setVariable("coupleName1", coupleName1);
        context.setVariable("coupleName2", coupleName2);
        context.setVariable("coupleEmail1", coupleEmail1);
        context.setVariable("coupleEmail2", coupleEmail2);
        context.setVariable("bothEmployees", bothEmployees);
        context.setVariable("pdfSupport", pdfSupport);

        String message = templateEngine.process(templateName, context);

        // Enviar el correo electrónico
        sendEmail(new String[]{coupleEmail1, coupleEmail2}, subject, message);
    }


    private void sendEmail(String[] to, String subject, String body) {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        try {
            mimeMessageHelper.setTo(to);
            mimeMessageHelper.setSubject(subject);
            mimeMessageHelper.setText(body, true);
            mailSender.send(mimeMessage);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}