package edu.alenkin.service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.model.Post;

/**
 * The service interface that provides access to {@link edu.alenkin.repository.PostRepository}
 * for getting {@link edu.alenkin.model.Post} data from storage
 */
public interface PostService {

    /**
     * Remove existing {@link edu.alenkin.model.Post} from repository
     *
     * @param post the post for deleting from storage
     */
    void removePost(Post post);

    /**
     * Add new {@link edu.alenkin.model.Post} to the storage.
     * @param post the new {@link edu.alenkin.model.Post} to add.
     * @param writerId the owner of added {@link edu.alenkin.model.Post}
     */
    void addPost(Post post, long writerId);

}
