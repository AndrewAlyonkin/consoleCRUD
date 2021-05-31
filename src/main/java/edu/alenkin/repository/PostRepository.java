package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Post;

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
     * @throws ExistException if current post is already exist in storage
     */
    void addPost(Post post) throws ExistException;

    /**
     * Takes {@link edu.alenkin.model.Post post}, finds it in storage and deletes it
     *
     * @param post the {@link edu.alenkin.model.Post post} for deleting from storage
     * @throws NotExistException if current post doesnt exist in storage
     */
    void removePost(Post post) throws NotExistException;

    /**
     * Takes modified {@link edu.alenkin.model.Post post}, finds it in the storage and updates it
     *
     * @param post the {@link edu.alenkin.model.Post post} for updating in storage
     * @throws NotExistException if current post doesnt exist in storage
     */
    void updatePost(Post post) throws NotExistException;

    /**
     * Takes the id of {@link edu.alenkin.model.Post post} and returns it from storage
     *
     * @param id the id of searching {@link Post post} in storage
     * @return {@link edu.alenkin.model.Post post} with current id from storage
     * or null if post doesnt exist in storage
     */
    List<Post> getPostsByWriterId(long id);

    /**
     * Method for getting all {@link edu.alenkin.model.Post post} from storage
     *
     * @return list of {@link edu.alenkin.model.Post posts}
     * or null if {@link edu.alenkin.model.Post posts} storage is empty
     */
    List<Post> getAllPosts();

    /**
     * Clear all Posts data form data storage
     * @return
     */
    boolean clear();
}
