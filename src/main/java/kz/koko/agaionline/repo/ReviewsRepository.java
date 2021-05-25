package kz.koko.agaionline.repo;

import kz.koko.agaionline.models.Email;
import kz.koko.agaionline.models.Post;
import kz.koko.agaionline.models.Reviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {
    Reviews findAllById(Integer id);
}
