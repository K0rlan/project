package kz.koko.agaionline.service;

import kz.koko.agaionline.mail.MyConstants;
import kz.koko.agaionline.service.Interfaces.EmailSendService;
import kz.koko.agaionline.service.Interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class EmailSendServiceImpl implements EmailSendService {
    @Autowired
    public JavaMailSender emailSender;
    @Autowired
    private EmailService emailService;
    @Override
    public void sendEmail(Integer curr) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(MyConstants.to_email);
        message.setSubject(emailService.getEmailUserById(curr).getGuestmessagetitle());
        message.setText(emailService.getEmailUserById(curr).getGuestmessage());
        this.emailSender.send(message);

        SimpleMailMessage message2 = new SimpleMailMessage();
        message2.setTo(emailService.getEmailUserById(curr).getGuestemail());
        message2.setSubject("Omarova Korlan");
        message2.setText("Thanks, for attention!");
        this.emailSender.send(message2);
    }
}