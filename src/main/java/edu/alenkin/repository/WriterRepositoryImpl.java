package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Writer;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link edu.alenkin.repository.WriterRepository} for manipulating
 * {@link edu.alenkin.model.Writer} entity in storage
 */
public class WriterRepositoryImpl implements WriterRepository{
    @Override
    public void addWriter(Writer writer) throws ExistException {

    }

    @Override
    public void removeWriter(Writer writer) throws NotExistException {

    }

    @Override
    public void updateWriter(Writer writer) throws NotExistException {

    }

    @Override
    public Writer getWriterById(long id) {
        return null;
    }

    @Override
    public List<Writer> getAllWriters() {
        return null;
    }

    @Override
    public void clear() {

    }
}
