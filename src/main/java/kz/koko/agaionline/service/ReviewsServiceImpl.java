package kz.koko.agaionline.service;

import kz.koko.agaionline.models.Post;
import kz.koko.agaionline.models.Reviews;
import kz.koko.agaionline.models.User;
import kz.koko.agaionline.models.Video;
import kz.koko.agaionline.repo.ReviewsRepository;
import kz.koko.agaionline.repo.VideoRepository;
import kz.koko.agaionline.service.Interfaces.ReviewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class ReviewsServiceImpl implements ReviewsService {
    @Autowired
    private ReviewsRepository reviewsRepository;

    @Transactional
    public Iterable<Reviews> findAll() {
        return reviewsRepository.findAll();
    }

    @Transactional
    public void save(Reviews review) {
        reviewsRepository.save(review);
    }

    @Transactional
    public void delete(Reviews review) {
        reviewsRepository.delete(review);
    }

    @Transactional
    public Reviews findById(Integer id) {
        return reviewsRepository.findById(id).get();
    }

}
