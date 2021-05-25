package kz.koko.agaionline.controllers;

import kz.koko.agaionline.models.Email;
import kz.koko.agaionline.models.Post;
import kz.koko.agaionline.service.Interfaces.EmailSendService;
import kz.koko.agaionline.service.Interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class EmailController {
    @Autowired
    private EmailService emailService;

    @Autowired
    private EmailSendService emailSend;
    int curr_id;

    @GetMapping("/email")
    public String sendEmail(Model model) {
        model.addAttribute("title", "Отправить письмо");
        return "email";
    }
    @PostMapping("/email")
    public String addUser(@RequestParam String guestname,
                        @RequestParam String guestemail,
                        @RequestParam Long guestphone,
                        @RequestParam String guestmessagetitle,
                        @RequestParam String guestmessage, Model model) {
        Email newUser = new Email(guestname, guestemail, guestphone, guestmessagetitle, guestmessage);
        emailService.addEmailUserInfo(newUser);
        curr_id = newUser.getId();
        emailSend.sendEmail(curr_id);
        return "home";
    }
}
