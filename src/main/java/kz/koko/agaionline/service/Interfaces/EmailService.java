package kz.koko.agaionline.service.Interfaces;

import kz.koko.agaionline.models.Email;

public interface EmailService {
    Email addEmailUserInfo(Email email);
    Email getEmailUserById(Integer id);
}
