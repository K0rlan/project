package kz.koko.agaionline.repo;

import kz.koko.agaionline.models.Email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends JpaRepository<Email, Integer> {
    Email getEmailUserById(Integer id);
}
