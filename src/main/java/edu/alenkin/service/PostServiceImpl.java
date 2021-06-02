package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Post;
import edu.alenkin.repository.PostRepository;
import edu.alenkin.repository.PostRepositoryImpl;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link PostService} for manipulating {@link Post} data in repository.
 */
public class PostServiceImpl implements PostService{
    private PostRepository pRepo = new PostRepositoryImpl();

    @Override
    public void removePost(Post post) {
        try {
            pRepo.removePost(post);
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addPost(Post post, long writerId) {
        try {
            pRepo.addPost(post, writerId);
        } catch (ExistException e) {
            e.printStackTrace();
        }

    }

}
