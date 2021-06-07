package edu.alenkin.repository;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import edu.alenkin.model.Writer;

import java.util.List;

/**
 * Provides the ability to manipulate {@link edu.alenkin.model.Writer writer} entity in storage
 */
public interface WriterRepository extends Repository<Writer, Long> {

    void delete(Writer writer);

    Long save(Writer writer);

    List<Writer> getAll();
}
