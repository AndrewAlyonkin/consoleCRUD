package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
import edu.alenkin.model.Post;
import edu.alenkin.repository.LabelRepository;
import edu.alenkin.repository.LabelRepositoryImpl;

import java.sql.SQLException;

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
        this.lRepo = new LabelRepositoryImpl();
    }

    @Override
    public void addLabel(Label label, long postId) throws SQLException, NotExistException, ExistException {
            lRepo.addLabel(label, postId);
    }

    @Override
    public void removeLabelById(long labelId) throws SQLException, NotExistException, ExistException {
            lRepo.removeLabel(labelId);
    }
}
