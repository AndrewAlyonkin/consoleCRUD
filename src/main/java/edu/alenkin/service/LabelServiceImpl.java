package edu.alenkin.service;

import edu.alenkin.model.Label;
import edu.alenkin.repository.LabelRepository;
import edu.alenkin.repository.hibernate.LabelRepositoryHiber;
import edu.alenkin.repository.jdbc.LabelRepositoryJdbc;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link LabelService} for manipulating {@link Label} data in repository.
 */
public class LabelServiceImpl implements LabelService{
    private LabelRepository lRepo;

    public LabelServiceImpl() {
       // this.lRepo = new LabelRepositoryJdbc();
        this.lRepo = new LabelRepositoryHiber();
    }

    @Override
    public Long add(Label label, Long postId) {
       return lRepo.save(label, postId);
    }

    @Override
    public void remove(Long id) {
        lRepo.delete(id);
    }

    @Override
    public Long update(Label label, Long ownerId) {
        return lRepo.save(label, ownerId);
    }

    @Override
    public Label get(Long labelId) {
        return lRepo.get(labelId);
    }


    @Override
    public List<Label> getByPostId(Long postId) {
        return lRepo.getByPostId(postId);
    }
}
