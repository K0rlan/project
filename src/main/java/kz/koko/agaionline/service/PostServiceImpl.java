package kz.koko.agaionline.service;

import kz.koko.agaionline.models.Post;
import kz.koko.agaionline.models.User;
import kz.koko.agaionline.models.Video;
import kz.koko.agaionline.repo.PostRepository;
import kz.koko.agaionline.repo.UserRepository;
import kz.koko.agaionline.repo.VideoRepository;
import kz.koko.agaionline.service.Interfaces.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private VideoRepository videoRepository;
    @Autowired
    private UserRepository userRepository;

    @Transactional
    public Iterable<Post> findAllByTitleContainingIgnoreCase(String filter) {
        return postRepository.findAllByTitleContainingIgnoreCase(filter);
    }

    @Transactional
    public Iterable<Post> findAll() {
        return postRepository.findAll();
    }

    @Transactional
    public boolean existsById(Long id) {
        return postRepository.existsById(id);
    }

    @Transactional
    public Post findById(Long id) {
        return postRepository.findById(id).get();
    }

    @Transactional
    public void save(Post post) {
        postRepository.save(post);
    }

    @Transactional
    public void delete(Post post) {
        for (Video v : videoRepository.findAllByPostId(post.getId())) {
            videoRepository.delete(v);
        }
        for (User u : userRepository.findAllByPostsContains(post)) {
            u.getPosts().remove(post);
            post.getUsers().remove(u);
            userRepository.save(u);
            postRepository.save(post);
        }
        postRepository.delete(post);
    }
}
