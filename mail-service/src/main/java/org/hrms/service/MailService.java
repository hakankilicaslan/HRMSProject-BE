package org.hrms.service;

import lombok.RequiredArgsConstructor;
import org.hrms.dto.request.MailTestRequestDto;
import org.hrms.rabbitmq.model.MailCreateEmployeeModel;
import org.hrms.rabbitmq.model.MailForgotPasswordModel;
import org.hrms.rabbitmq.model.MailSenderModel;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/*
 * @Service annotasyonu, Spring Framework'te servis sınıflarını işaretlemek için kullanılan bir anotasyondur.
 * Bu annotasyon, Spring uygulamalarında servis katmanı bileşenlerini tanımlamak ve işaretlemek için kullanılır.
 *
 * @RequiredArgsConstructor anotasyonu dependencies injection edilen final fieldlara(JavaMailSender) constructor oluşturmak için kullanılan bir anotasyondur.
 */
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;

    public String sendTestMail(MailTestRequestDto dto) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(dto.getEmail());
        mailMessage.setSubject("HRMS Project Email Test");
        mailMessage.setText("Hello, " +  dto.getName() + " " + dto.getSurname() + " you received a test email.\n" + "Message: " + dto.getMessage());

        javaMailSender.send(mailMessage);

        return "A test email has been sent successfully to " + dto.getEmail() + " of user named " + dto.getName() + " " + dto.getSurname();
    }


    public void sendMail(MailSenderModel model) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(model.getEmail());
        mailMessage.setSubject("HRMS Project Activation Link");
        mailMessage.setText("Hello, you can click on the link below to activate your account.\n" + "Activation Link: " + model.getActivationLink());

        javaMailSender.send(mailMessage);
    }

    public void sendMailForgotPassword(MailForgotPasswordModel mailForgotPasswordModel) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(mailForgotPasswordModel.getEmail());
        mailMessage.setSubject("HRMS Project Password Reset");
        mailMessage.setText("Your password has been successfully reset. Your new password: " + mailForgotPasswordModel.getPassword());

        javaMailSender.send(mailMessage);
    }

    public void sendMailCreateEmployee(MailCreateEmployeeModel mailCreateEmployeeModel) {

        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("${spring.mail.username}");
        mailMessage.setTo(mailCreateEmployeeModel.getPersonalEmail());
        mailMessage.setSubject("HRMS Project Create Employee");
        mailMessage.setText("Hello, your email address and password have been created and your account has been activated.\n" +
                            "Your e-mail address: " + mailCreateEmployeeModel.getEmail() + "\n" +
                            "Your password: " + mailCreateEmployeeModel.getPassword());

        javaMailSender.send(mailMessage);
    }

}
