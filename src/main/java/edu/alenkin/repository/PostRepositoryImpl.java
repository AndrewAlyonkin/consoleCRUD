package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Post;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link edu.alenkin.repository.PostRepository} for manipulating
 * {@link edu.alenkin.model.Post} entity in storage
 */
public class PostRepositoryImpl implements PostRepository{

    @Override
    public void createPost(Post post) throws ExistException {

    }

    @Override
    public void deletePost(Post post) throws NotExistException {

    }

    @Override
    public void updatePost(Post post) throws NotExistException {

    }

    @Override
    public Post getPostById(long id) {
        return null;
    }

    @Override
    public List<Post> getAllPosts() {
        return null;
    }
}
