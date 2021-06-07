package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.model.Post;

import java.util.List;

/**
 * Provides the ability to manipulate {@link edu.alenkin.model.Post post} entity in storage
 */
public interface PostRepository extends Repository<Post, Long> {
    List<Post> getByWriterId(Long writerId);
}
