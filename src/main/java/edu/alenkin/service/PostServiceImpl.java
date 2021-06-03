package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.PostRepository;
import edu.alenkin.repository.PostRepositoryImpl;

import java.sql.SQLException;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link PostService} for manipulating {@link Post} data in repository.
 */
public class PostServiceImpl implements PostService {
    private PostRepository pRepo = new PostRepositoryImpl();

    @Override
    public void removePost(long postId) throws SQLException, NotExistException, ExistException {
        pRepo.removePost(postId);
    }

    @Override
    public void addPost(Post post, long writerId) throws SQLException, NotExistException, ExistException {
        pRepo.addPost(post, writerId);
    }

    @Override
    public void updatePostsForWriter(Writer writer) throws SQLException, NotExistException, ExistException {
        pRepo.updatePostsForWriter(writer);
    }
}
