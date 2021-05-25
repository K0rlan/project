package kz.koko.agaionline.service.Interfaces;

import kz.koko.agaionline.models.Post;
import kz.koko.agaionline.models.Reviews;
import org.springframework.stereotype.Service;

public interface ReviewsService {
    Iterable<Reviews> findAll();

    void save(Reviews reviews);

    void delete(Reviews review);

    Reviews findById(Integer id);
}
