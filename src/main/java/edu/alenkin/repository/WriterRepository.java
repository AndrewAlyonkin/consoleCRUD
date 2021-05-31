package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Writer;

import java.util.List;

/**
 * Provides the ability to manipulate {@link edu.alenkin.model.Writer writer} entity in storage,
 * like: create new {@link edu.alenkin.model.Writer writer}, delete it, update or get list
 * of all writers from storage
 */
public interface WriterRepository {

    /**
     * Takes new {@link edu.alenkin.model.Writer writer} and save it into the storage
     *
     * @param writer the {@link edu.alenkin.model.Writer writer} for saving in storage
     * @throws ExistException if same {@link edu.alenkin.model.Writer writer} is already exists in storage
     */
    void addWriter(Writer writer) throws ExistException;

    /**
     * Takes {@link edu.alenkin.model.Writer writer}, finds it in storage and deletes it
     *
     * @param writer the {@link edu.alenkin.model.Writer writer} for deleting from storage
     * @throws NotExistException if current {@link edu.alenkin.model.Writer writer} is doesnt exist in storage
     */
    void removeWriter(Writer writer) throws NotExistException;

    /**
     * Takes modified {@link edu.alenkin.model.Writer writer} and update it in the storage
     *
     * @param writer the modified {@link edu.alenkin.model.Writer writer} for update in storage
     * @throws NotExistException if current {@link edu.alenkin.model.Writer writer} is doesnt exist in storage
     */
    void updateWriter(Writer writer) throws NotExistException;

    /**
     * Takes id of {@link edu.alenkin.model.Writer writer}, finds it in the storage and returns it
     *
     * @param id the id of searching {@link edu.alenkin.model.Writer writer}
     * @return {@link edu.alenkin.model.Writer writer} by it id
     * or null if writer doesnt exist in storage
     */
    Writer getWriterById(long id);

    /**
     * Method for getting of all {@link edu.alenkin.model.Writer writers} from storage
     *
     * @return list of {@link edu.alenkin.model.Writer writer} from storage
     * or null if {@link edu.alenkin.model.Writer writers} storage is empty
     */
    List<Writer> getAllWriters();

    /**
     * Clear all writers data from data storage
     * @return
     */
    boolean clear();
}
