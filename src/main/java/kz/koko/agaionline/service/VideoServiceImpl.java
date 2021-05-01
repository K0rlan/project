package kz.koko.agaionline.service;

import kz.koko.agaionline.models.Video;
import kz.koko.agaionline.repo.VideoRepository;
import kz.koko.agaionline.service.Interfaces.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class VideoServiceImpl implements VideoService {
    @Autowired
    private VideoRepository videoRepository;

    @Transactional
    public Iterable<Video> findAllByPostId(Long id) {
        return videoRepository.findAllByPostId(id);
    }

    @Transactional
    public Video findById(Long videoId) {
        return videoRepository.findById(videoId).get();
    }

    @Transactional
    public void save(Video video) {
        videoRepository.save(video);
    }

    @Transactional
    public boolean existsById(Long id) {
        return  videoRepository.existsById(id);
    }
}
