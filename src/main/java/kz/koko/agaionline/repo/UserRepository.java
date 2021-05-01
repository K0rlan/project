package kz.koko.agaionline.repo;

import kz.koko.agaionline.models.Post;
import kz.koko.agaionline.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUsernameIgnoreCase(String username);
    Iterable<User> findAllByPostsContains(Post post);
    Iterable<User> findAllByOrderById();

}
