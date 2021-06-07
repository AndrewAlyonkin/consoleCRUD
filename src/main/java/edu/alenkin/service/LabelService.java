package edu.alenkin.service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.model.Label;

import java.util.List;

/**
 * The service interface that provides access to {@link edu.alenkin.repository.LabelRepository}
 * for getting {@link edu.alenkin.model.Label} data from storage.
 */
public interface LabelService extends Service<Label, Long> {
    List<Label> getByPostId(Long postId);

}
