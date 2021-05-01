package kz.koko.agaionline.service.Interfaces;

import kz.koko.agaionline.models.Post;
import kz.koko.agaionline.models.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    User findByUsernameIgnoreCase(String username);

    Iterable<User> findAllByPostsContains(Post post);

    boolean addUser(User user);

    Iterable<User> findAll();

    boolean existsById(Long id);

    void save(User user);

    Object findById(Long id);
}
