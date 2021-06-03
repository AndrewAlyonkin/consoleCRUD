package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
import edu.alenkin.model.Writer;

import java.sql.SQLException;
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
    void addLabel(Label label, long postId) throws ExistException, SQLException, NotExistException;

    /**
     * Takes {@link edu.alenkin.model.Label label}, finds it in storage and deletes it
     *
     * @param labelId the id of {@link edu.alenkin.model.Label} for deleting from storage
     * @throws NotExistException if the {@link edu.alenkin.model.Label}
     *                           to be deleted is not present in storage
     */
    void removeLabel(long labelId) throws NotExistException, SQLException, ExistException;

    /**
     * Takes id of {@link edu.alenkin.model.Label}, find it in storage and returns.
     *
     * @param id the id of searching {@link Label}
     * @return {@link edu.alenkin.model.Label} by it id
     * or null if {@link edu.alenkin.model.Label} is not exist in storage
     */
    List<Label> getLabelsByPostId(long id) throws SQLException, NotExistException, ExistException;

    /**
     * Takes {@link edu.alenkin.model.Writer writer}
     * looking it posts and update labels for updated posts.
     * @param writer the owner of post for updating labels
     */
    void updateLabelsForWriter(Writer writer) throws SQLException, NotExistException, ExistException;
}
