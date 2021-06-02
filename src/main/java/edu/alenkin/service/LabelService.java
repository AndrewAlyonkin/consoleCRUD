package edu.alenkin.service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.model.Label;

/**
 * The service interface that provides access to {@link edu.alenkin.repository.LabelRepository}
 * for getting {@link edu.alenkin.model.Label} data from storage.
 */
public interface LabelService {

    /**
     * Takes new {@link Label} and put it into the repository.
     *
     * @param label the {@link Label} to add in storage
     * @param postId the {@link edu.alenkin.model.Post} that the {@link Label} belongs to.
     */
    void addLabel(Label label, long postId);

    /**
     * Takes the existing post and deletes it from storage
     * @param label the {@link Label} for deleting
     */
    void removeLabel(Label label);
}
