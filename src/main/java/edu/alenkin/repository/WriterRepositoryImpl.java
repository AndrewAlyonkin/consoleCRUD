package edu.alenkin.repository;

import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;
import edu.alenkin.utils.DBWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link edu.alenkin.repository.WriterRepository} for manipulating
 * {@link edu.alenkin.model.Writer} entity in storage
 */
public class WriterRepositoryImpl implements WriterRepository {

    @Override
    public void delete(Long id) {
        try {
            DBWorker.executeVoid("DELETE FROM writers WHERE id=?", id);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    // Бросаю исключение для этого метода, так как Writer не может иметь владельца с id.
    @Override
    @Deprecated
    public Long save(Writer writer, Long ownerId) {
        throw new IllegalArgumentException("Writer can not have the owner, operation with ownerId parameter is forbidden!");
    }

    @Override
    public Writer get(Long writerId) {
        return DBWorker.executeGet("SELECT * FROM writers WHERE id=?", writerId, this::parse);
    }


    @Override
    public void delete(Writer writer) {
        delete(writer.getId());
    }

    @Override
    public Long save(Writer writer) {
        Long writerId = writer.getId();
        try {
            if (writerId == null) {
                writerId = DBWorker.executeSave("INSERT INTO writers (first_name, last_name) VALUES (?, ?);",
                        prepSt -> {
                            prepSt.setString(1, writer.getFirstName());
                            prepSt.setString(2, writer.getLastName());
                        });
            } else {
                Long finalWriterId = writerId;
                writerId = DBWorker.executeSave("UPDATE writers SET first_name=?, last_name=? WHERE id=?",
                        prepSt -> {
                            prepSt.setString(1, writer.getFirstName());
                            prepSt.setString(2, writer.getLastName());
                            prepSt.setLong(3, finalWriterId);
                        });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        PostRepositoryImpl postRepo = new PostRepositoryImpl();
        for (Post post : writer.getPosts()) {
            postRepo.save(post, writerId);
        }
        return writerId;
    }

    @Override
    public List<Writer> getAll(){
        return DBWorker.executeGet("SELECT * FROM writers", null, this::parseList);
    }

    private Writer parse(ResultSet resultSet) throws SQLException {
        Writer currentWriter = null;
        if (resultSet.next()) {
            currentWriter =  new Writer(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
            currentWriter.setPosts(new PostRepositoryImpl().getByWriterId(currentWriter.getId()));
        }
        return currentWriter;
    }

    private List<Writer> parseList(ResultSet resultSet) throws SQLException {
        List<Writer> writers = new ArrayList<>();
        PostRepositoryImpl postRepo = new PostRepositoryImpl();
        while (resultSet.next()) {
            Writer currentWriter = new Writer(
                    resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"));
            currentWriter.setPosts(postRepo.getByWriterId(currentWriter.getId()));
            writers.add(currentWriter);
        }
        return writers;
    }
}

