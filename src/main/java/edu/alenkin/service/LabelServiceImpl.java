package edu.alenkin.service;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
import edu.alenkin.model.Post;
import edu.alenkin.repository.LabelRepository;
import edu.alenkin.repository.LabelRepositoryImpl;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link LabelService} for manipulating {@link Label} data in repository.
 */
public class LabelServiceImpl implements LabelService{
    private LabelRepository lRepo = new LabelRepositoryImpl();

    @Override
    public void addLabel(Label label, long postId) {
        try {
            lRepo.addLabel(label, postId);
        } catch (ExistException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeLabel(Label label) {
        try {
            lRepo.removeLabel(label);
        } catch (NotExistException e) {
            e.printStackTrace();
        }
    }
}
