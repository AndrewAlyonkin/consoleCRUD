package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.WriterRepository;
import edu.alenkin.repository.WriterRepositoryImpl;

import java.sql.SQLException;
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
    public void addWriter(Writer writer) throws ExistException, SQLException {
            wRepo.addWriter(writer);
    }

    @Override
    public Writer getWriter(long writerId) throws SQLException, NotExistException, ExistException {
            return wRepo.getWriterById(writerId);
    }

    @Override
    public void removeWriter(Writer writer) throws SQLException, NotExistException, ExistException {
            wRepo.removeWriter(writer);
    }

    @Override
    public void removeWriterById(long id) throws SQLException, NotExistException, ExistException {
        wRepo.removeWriterById(id);
    }

    @Override
    public void updateWriter(Writer writer) throws SQLException, NotExistException, ExistException {
            wRepo.updateWriter(writer);
    }

    @Override
    public void clearWriters() {
        wRepo.clear();
    }

    @Override
    public List<Writer> getAllWriters() throws SQLException, NotExistException, ExistException {
        return wRepo.getAllWriters();
    }
}
