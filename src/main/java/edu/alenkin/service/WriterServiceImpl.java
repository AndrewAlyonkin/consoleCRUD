package edu.alenkin.service;

import edu.alenkin.model.Writer;
import edu.alenkin.repository.WriterRepository;
import edu.alenkin.repository.hibernate.WriterRepositoryHiber;
import edu.alenkin.repository.jdbc.WriterRepositoryJdbc;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link WriterService} for manipulating {@link Writer} data in repository.
 */
public class WriterServiceImpl implements WriterService {
    private WriterRepository wRepo;

    public WriterServiceImpl() {
        //this.wRepo = new WriterRepositoryJdbc();
        this.wRepo = new WriterRepositoryHiber();
    }

    @Override
    @Deprecated
    public Long add(Writer addEntity, Long ownerId) {
       throw new IllegalArgumentException("Writer can not have the owner, operation with ownerId parameter is forbidden!");
    }

    @Override
    public Long update(Writer entity, Long ownerId) {
        throw new IllegalArgumentException("Writer can not have the owner, operation with ownerId parameter is forbidden!");
    }

    @Override
    public void remove(Long writerId) {
        wRepo.delete(writerId);
    }

    @Override
    public Writer get(Long writerId) {
        return wRepo.get(writerId);
    }

    @Override
    public Long add(Writer writer) {
        return wRepo.save(writer);
    }

    @Override
    public Long update(Writer writer) {
        return wRepo.save(writer);
    }

    @Override
    public List<Writer> getAll() {
        return wRepo.getAll();
    }
}
