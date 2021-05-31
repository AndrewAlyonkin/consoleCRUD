package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link edu.alenkin.repository.LabelRepository} for manipulating
 * {@link edu.alenkin.model.Label} entity in storage
 */
public class LabelRepositoryImpl extends Repository implements LabelRepository {

    @Override
    public void addLabel(Label label) throws ExistException {


    }

    @Override
    public void removeLabel(Label label) throws NotExistException {
        dbWorker.executePrepared("DELETE FROM labels WHERE id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, label.getId());
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        throw new NotExistException();
                    }
                    return null;
                });
    }

    @Override
    public void updateLabel(Label label) throws NotExistException {

    }

    @Override
    public List<Label> getLabelsByPostId(long id) {
        return dbWorker.executeQuery("SELECT * FROM labels WHERE post_id=? ", preparedStatement -> {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
           return parseLabelResultSet(resultSet);
        });
    }

    @Override
    public List<Label> getAllLabels() {
        return dbWorker.executeQuery("SELECT * FROM labels", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseLabelResultSet(resultSet);
        });
    }

    @Override
    public boolean clear() {
        return dbWorker.execute("DELETE FROM labels");
    }

    /**
     * Util method for parsing result {@link ResultSet resultset} of prepared statement execution
     *
      * @param resultSet the resultSet, contains data about {@link Label label} from dataBase, for parsing
     * @return list of {@link Label labels}, parsed from current resultSet
     * @throws SQLException
     */
    private List<Label> parseLabelResultSet(ResultSet resultSet) throws SQLException {
        List<Label> labels = new ArrayList();
        while (resultSet.next()) {
            labels.add(new Label(resultSet.getLong("id"), resultSet.getString("name")));
        }
        return labels;
    }
}
