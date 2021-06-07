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
public interface WriterService extends Service<Writer, Long> {
    Long add(Writer writer);

    Long update(Writer writer);

    List<Writer> getAll();
}
