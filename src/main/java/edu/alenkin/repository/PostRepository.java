package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;

import java.util.List;

/**
 * Provides the ability to manipulate {@link edu.alenkin.model.Post post} entity in storage,
 * like: create new {@link edu.alenkin.model.Post post}, delete it, update or get list
 * of all posts from storage
 */
public interface PostRepository {

    /**
     * Takes new {@link edu.alenkin.model.Post post} and save it into storage
     *
     * @param post the new {@link edu.alenkin.model.Post post} for saving in storage
     * @param writerId the id of {@link Writer} who owns this post
     * @throws ExistException if current post is already exist in storage
     */
    void addPost(Post post, long writerId) throws ExistException;

    /**
     * Takes {@link edu.alenkin.model.Post post}, finds it in storage and deletes it
     *
     * @param post the {@link edu.alenkin.model.Post post} for deleting from storage
     * @throws NotExistException if current post doesnt exist in storage
     */
    void removePost(Post post) throws NotExistException;

    /**
     * Takes the id of {@link edu.alenkin.model.Post post} and returns it from storage
     *
     * @param writerId the id of searching {@link Writer} who owns this post in storage
     * @return {@link edu.alenkin.model.Post post} with current id from storage
     * or null if post doesnt exist in storage
     */
    List<Post> getPostsByWriterId(long writerId);

    /**
     * Takes existing writer and update its posts
     * @param writer the owner of posts for updating
     */
    void updatePostsForWriter(Writer writer);

    /**
     * Clear all Posts for current writer from data storage
     */
    void clearForWriter(long writerId);
}
