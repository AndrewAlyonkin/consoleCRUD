package edu.alenkin.service;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.model.Writer;

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
    void addWriter(Writer writer);

    /**
     * Method for taking existing writer from storage
     * @param writerId the writer id retrieved from the repository
     * @return the {@link Writer} with it required id
     */
    Writer getWriter(long writerId);

    /**
     * Method for deleting {@link Writer} from repository
     *
     * @param writer the {@link Writer} for deleting
     */
    void removeWriter(Writer writer);

    /**
     * Takes existing {@link Writer} and updates it data in repository
     * @param writer the {@link Writer} for updating in storage
     */
    void updateWriter(Writer writer);

    /**
     * Removes all {@link Writer writers} data from storage
     */
    void clearWriters();

    /**
     * Get data about all existing {@link Writer writers} from repository
     * @return {@link List} of writers
     */
    List<Writer> getAllWriters();
}
