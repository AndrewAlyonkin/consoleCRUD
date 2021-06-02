package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.WriterRepository;
import edu.alenkin.repository.WriterRepositoryImpl;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link WriterService} for manipulating {@link Writer} data in repository.
 */
public class WriterServiceImpl implements WriterService {
    private WriterRepository wRepo = new WriterRepositoryImpl();

    @Override
    public void addWriter(Writer writer) {
        try {
            wRepo.addWriter(writer);
        } catch (ExistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Writer getWriter(long writerId) {
        try {
            return wRepo.getWriterById(writerId);
        } catch (NotExistException e) {
            return null;
        }
    }

    @Override
    public void removeWriter(Writer writer) {
        try {
            wRepo.removeWriter(writer);
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateWriter(Writer writer) {
        try {
            wRepo.updateWriter(writer);
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void clearWriters() {
        wRepo.clear();
    }

    @Override
    public List<Writer> getAllWriters() {
        return wRepo.getAllWriters();
    }
}
