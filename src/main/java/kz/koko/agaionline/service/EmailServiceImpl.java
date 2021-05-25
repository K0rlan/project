package kz.koko.agaionline.service;

import kz.koko.agaionline.models.Email;
import kz.koko.agaionline.repo.EmailRepository;
import kz.koko.agaionline.service.Interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private EmailRepository emailRepository;

    @Override
    public Email addEmailUserInfo(Email email) {
        return emailRepository.save(email);
    }

    @Override
    public Email getEmailUserById(Integer id) {
        return emailRepository.getEmailUserById(id);
    }
}
