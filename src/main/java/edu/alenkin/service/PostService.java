package edu.alenkin.service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;

import java.sql.SQLException;

/**
 * The service interface that provides access to {@link edu.alenkin.repository.PostRepository}
 * for getting {@link edu.alenkin.model.Post} data from storage
 */
public interface PostService {

    /**
     * Remove existing {@link edu.alenkin.model.Post} from repository
     *
     * @param postId the ID of post for deleting from storage
     */
    void removePost(long postId) throws SQLException, NotExistException, ExistException;

    /**
     * Add new {@link edu.alenkin.model.Post} to the storage.
     * @param post the new {@link edu.alenkin.model.Post} to add.
     * @param writerId the owner of added {@link edu.alenkin.model.Post}
     */
    void addPost(Post post, long writerId) throws SQLException, NotExistException, ExistException;

    /**
     * Update all {@link edu.alenkin.model.Writer writers} posts
     * @param writer the writer for updating
     */
    void updatePostsForWriter(Writer writer) throws SQLException, NotExistException, ExistException;

}
