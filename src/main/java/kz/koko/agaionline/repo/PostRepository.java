package kz.koko.agaionline.repo;

import kz.koko.agaionline.models.Post;
import org.springframework.data.repository.CrudRepository;


public interface PostRepository extends CrudRepository<Post, Long> {
    Iterable<Post> findAllByTitleContainingIgnoreCase(String Title);
}
