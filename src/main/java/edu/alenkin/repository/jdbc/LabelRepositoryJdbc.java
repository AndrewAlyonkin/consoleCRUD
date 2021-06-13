package edu.alenkin.repository.jdbc;

import edu.alenkin.model.Label;
import edu.alenkin.repository.LabelRepository;
import edu.alenkin.utils.jdbc.JdbcWorker;

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
public class LabelRepositoryJdbc implements LabelRepository {

    @Override
    public Long save(Label label, Long postId) {
        Long labelId = label.getId();
        try {
            // Если добавляем новую запись в базу данных
            if (labelId == null) {
                return JdbcWorker.executeSave("INSERT INTO labels (name, post_id) VALUES(?, ?)", prepSt -> {
                    prepSt.setString(1, label.getName());
                    prepSt.setLong(2, postId);
                });
                // если обновляем существующую запись
            } else {
                return JdbcWorker.executeSave("UPDATE labels SET name=? WHERE id=?", prepSt -> {
                    prepSt.setString(1, label.getName());
                    prepSt.setLong(2, labelId);
                });
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Long id) {
        try {
            JdbcWorker.executeVoid("DELETE FROM labels WHERE id=?", id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());
        }
    }

    @Override
    public Label get(Long id) {
        return JdbcWorker.executeGet("SELECT * FROM labels WHERE id=?;", id, this::parse);
    }

    @Override
    public List<Label> getByPostId(Long id) {
        return JdbcWorker.executeGet("SELECT * FROM labels WHERE post_id=?;", id, this::parseList);
    }

    /**
     * Util method for parsing result {@link ResultSet resultset} of prepared statement execution
     *
     * @param resultSet the resultSet, contains data about {@link Label label} from dataBase, for parsing
     * @return list of {@link Label labels}, parsed from current resultSet
     */
    private List<Label> parseList(ResultSet resultSet) {
        List<Label> labels = new ArrayList<>();
        try {
            while (resultSet.next()) {
                labels.add(new Label(resultSet.getLong("id"), resultSet.getString("name")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return labels;
    }

    /**
     * Util method for parsing result {@link ResultSet resultset} of prepared statement execution
     *
     * @param set contains data about {@link Label label} from dataBase, for parsing
     * @return new {@link Label}, parsed from current resultSet
     */
    private Label parse(ResultSet set) throws SQLException {
        if (set.next()) {
            try {
                return new Label(set.getLong("id"), set.getString("name"));
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return null;
        }
        return null;
    }
}
