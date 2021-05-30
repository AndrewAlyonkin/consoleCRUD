package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link edu.alenkin.repository.LabelRepository} for manipulating
 * {@link edu.alenkin.model.Label} entity in storage
 */
public class LabelRepositoryImpl implements LabelRepository{

    @Override
    public void createLabel(Label label) throws ExistException {

    }

    @Override
    public void deleteLabel(Label label) throws NotExistException {

    }

    @Override
    public void updateLabel(Label label) throws NotExistException {

    }

    @Override
    public Label getLabelById(long id) {
        return null;
    }

    @Override
    public List<Label> getAllLabels() {
        return null;
    }
}
