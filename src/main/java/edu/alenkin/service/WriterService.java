package edu.alenkin.service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Writer;

import java.sql.SQLException;
import java.util.List;

/**
 * The service interface that provides access to {@link edu.alenkin.repository.WriterRepository}
 * for getting {@link edu.alenkin.model.Writer} data from storage
 */
public interface WriterService {
    /**
     * Takes new {@link Writer} and put it into the storage
     * @param writer the new {@link Writer} for adding to storage
     */
    void addWriter(Writer writer) throws ExistException, SQLException;

    /**
     * Method for taking existing writer from storage
     * @param writerId the writer id retrieved from the repository
     * @return the {@link Writer} with it required id
     */
    Writer getWriter(long writerId) throws SQLException, NotExistException, ExistException;

    /**
     * Method for deleting {@link Writer} from repository
     *
     * @param writer the {@link Writer} for deleting
     */
    void removeWriter(Writer writer) throws SQLException, NotExistException, ExistException;

    /**
     * Method search {@link Writer} by it id in repository and deletes it
     * @param id the id of {@link Writer} to delete
     */
    void removeWriterById(long id) throws SQLException, NotExistException, ExistException;

    /**
     * Takes existing {@link Writer} and updates it data in repository
     * @param writer the {@link Writer} for updating in storage
     */
    void updateWriter(Writer writer) throws SQLException, NotExistException, ExistException;

    /**
     * Removes all {@link Writer writers} data from storage
     */
    void clearWriters();

    /**
     * Get data about all existing {@link Writer writers} from repository
     * @return {@link List} of writers
     */
    List<Writer> getAllWriters() throws SQLException, NotExistException, ExistException;
}
