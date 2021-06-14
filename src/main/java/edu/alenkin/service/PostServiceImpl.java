package edu.alenkin.service;

import edu.alenkin.model.Post;
import edu.alenkin.repository.PostRepository;
import edu.alenkin.repository.RepositoryFactory;
import edu.alenkin.repository.hibernate.PostRepositoryHiber;
import edu.alenkin.repository.jdbc.PostRepositoryJdbc;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link PostService} for manipulating {@link Post} data in repository.
 */
public class PostServiceImpl implements PostService {
    private PostRepository pRepo;

    public PostServiceImpl() {
        this.pRepo = RepositoryFactory.getPostRepository();
    }

    @Override
    public Long add(Post post, Long ownerId) {
        return pRepo.save(post, ownerId);
    }

    @Override
    public void remove(Long postId) {
        pRepo.delete(postId);
    }

    @Override
    public Long update(Post post, Long writerId) {
        return pRepo.save(post, writerId);
    }

    @Override
    public Post get(Long postId) {
        return pRepo.get(postId);
    }

    public List<Post> getByWriterId(Long id) {
        return pRepo.getByWriterId(id);
    }
}
