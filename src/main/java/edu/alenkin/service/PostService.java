package edu.alenkin.service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.model.Post;

import java.util.List;

/**
 * The service interface that provides access to {@link edu.alenkin.repository.PostRepository}
 * for getting {@link edu.alenkin.model.Post} data from storage
 */
public interface PostService extends Service<Post, Long> {
    List<Post> getByWriterId(Long id);

}
