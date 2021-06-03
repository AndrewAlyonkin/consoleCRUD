package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;

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
    public void addLabel(Label label, long postId) throws SQLException, NotExistException, ExistException {
        dbWorker.executePrepared("INSERT INTO labels (name, post_id) VALUES(?, ?)",
                preparedStatement -> {
                    preparedStatement.setString(1, label.getName());
                    preparedStatement.setLong(2, postId);
                    preparedStatement.executeUpdate();
                    return null;
                });
    }


    @Override
    public void removeLabel(long labelId) throws SQLException, NotExistException, ExistException {
        dbWorker.executePrepared("DELETE FROM labels WHERE id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, labelId);
                    preparedStatement.executeUpdate();
                    return null;
                });
    }

    @Override
    public List<Label> getLabelsByPostId(long id) throws SQLException, NotExistException, ExistException {
        return dbWorker.executePrepared("SELECT * FROM labels WHERE post_id=? ", preparedStatement -> {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseLabelResultSet(resultSet);
        });
    }

    @Override
    public void updateLabelsForWriter(Writer writer) throws SQLException, NotExistException, ExistException {
        dbWorker.executePrepared("UPDATE labels SET name=? WHERE id=?", preparedStatement -> {
            boolean labelsExist = false;
            for (Post post : writer.getPosts()) {
                if (post.getCreated() != post.getUpdated()) {
                    for (Label label : post.getLabels()) {
                        preparedStatement.setString(1, label.getName());
                        preparedStatement.setLong(2, label.getId());
                        labelsExist = true;
                    }
                    preparedStatement.addBatch();
                }
            }
            if (labelsExist) {
                preparedStatement.executeUpdate();
            }
            return null;
        });
    }

    /**
     * Util method for parsing result {@link ResultSet resultset} of prepared statement execution
     *
     * @param resultSet the resultSet, contains data about {@link Label label} from dataBase, for parsing
     * @return list of {@link Label labels}, parsed from current resultSet
     * @throws SQLException
     */
    private List<Label> parseLabelResultSet(ResultSet resultSet) throws SQLException {
        List<Label> labels = new ArrayList<>();
        while (resultSet.next()) {
            labels.add(new Label(resultSet.getLong("id"), resultSet.getString("name")));
        }
        return labels;
    }
}
