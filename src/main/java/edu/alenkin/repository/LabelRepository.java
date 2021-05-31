package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;

import java.util.List;

/**
 * Provides the ability to manipulate {@link edu.alenkin.model.Label label} entity in storage,
 * like: create new {@link edu.alenkin.model.Label label}, delete it, update or get list
 * of all labels from storage
 */
public interface LabelRepository {

    /**
     * Takes new {@link edu.alenkin.model.Label} and save it in the the storage
     *
     * @param label the new {@link edu.alenkin.model.Label} for saving in storage
     * @throws ExistException if current Label is already exists in storage
     */
    void addLabel(Label label) throws ExistException;

    /**
     * Takes {@link edu.alenkin.model.Label label}, finds it in storage and deletes it
     *
     * @param label the {@link edu.alenkin.model.Label} for deleting from storage
     * @throws NotExistException if the {@link edu.alenkin.model.Label}
     *                           to be deleted is not present in storage
     */
    void removeLabel(Label label) throws NotExistException;

    /**
     * Takes modified {@link edu.alenkin.model.Label} and update it in the storage
     *
     * @param label the modified {@link edu.alenkin.model.Label} for update
     * @throws NotExistException if the {@link edu.alenkin.model.Label} for update
     *                           is not exists in storage
     */
    void updateLabel(Label label) throws NotExistException;

    /**
     * Takes id of {@link edu.alenkin.model.Label}, find it in storage and returns.
     *
     * @param id the id of searching {@link Label}
     * @return {@link edu.alenkin.model.Label} by it id
     * or null if {@link edu.alenkin.model.Label} is not exist in storage
     */
    List<Label> getLabelsByPostId(long id);

    /**
     * Method for getting all {@link edu.alenkin.model.Label labels} from storage
     *
     * @return List of {@link edu.alenkin.model.Label labels}
     * or null if {@link edu.alenkin.model.Label labels} storage is empty
     */
    List<Label> getAllLabels();

    /**
     * Clear all Labels data fom data storage
     * @return
     */
    boolean clear();
}
