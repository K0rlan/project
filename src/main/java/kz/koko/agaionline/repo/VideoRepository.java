package kz.koko.agaionline.repo;

import kz.koko.agaionline.models.Video;
import org.springframework.data.repository.CrudRepository;

public interface VideoRepository extends CrudRepository<Video, Long> {
    Iterable<Video> findAllByPostId(Long id);
}
