package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link edu.alenkin.repository.WriterRepository} for manipulating
 * {@link edu.alenkin.model.Writer} entity in storage
 */
public class WriterRepositoryImpl extends Repository implements WriterRepository {
    @Override
    public void addWriter(Writer writer) throws ExistException {
        dbWorker.executeTransactional(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO writers " +
                            "(id, first_name, last_name) " +
                            "VALUES (?, ?, ?);");
            preparedStatement.setLong(1, writer.getId());
            preparedStatement.setString(2, writer.getFirstName());
            preparedStatement.setString(3, writer.getLastName());
            preparedStatement.executeUpdate();

            PreparedStatement postsStatement = connection.prepareStatement(
                    "INSERT INTO posts" +
                            "(id, content, create_time, update_time, writer_id)" +
                            "VALUES (?, ?, ?, ?, ?)");
            for (Post p: writer.getPosts()) {
                postsStatement.setLong(1, p.getId());
                postsStatement.setString(2, p.getContent());
                postsStatement.setTimestamp(3, Timestamp.valueOf(p.getCreated()));
                postsStatement.setTimestamp(4, Timestamp.valueOf(p.getUpdated()));
                postsStatement.setLong(5, writer.getId());
                postsStatement.addBatch();
            }
            postsStatement.executeUpdate();

            return null;
        });

    }

    @Override
    public void removeWriter(Writer writer) throws NotExistException {
        dbWorker.executePrepared("DELETE FROM writers WHERE id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, writer.getId());
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        throw new NotExistException();
                    }
                    return null;
                });
    }

    @Override
    public void updateWriter(Writer writer) throws NotExistException {

    }

    @Override
    public Writer getWriterById(long id) {
        return dbWorker.executePrepared("SELECT * FROM writers WHERE id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();


                    if (resultSet.next()) {
                        PostRepositoryImpl postRepository = new PostRepositoryImpl();
                        Writer currentWriter = new Writer(
                                resultSet.getLong("id"),
                                resultSet.getString("first_name"),
                                resultSet.getString("last_name"));
                        setWriterPosts(currentWriter, postRepository);
                        return currentWriter;
                    }
                    throw new NotExistException();
                });
    }

    @Override
    public List<Writer> getAllWriters() {
        List<Writer> resultWriters = dbWorker.executeQuery("SELECT * FROM writers", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseWritersResultSet(resultSet);
        });
        return fillWritersLabels(resultWriters);
    }

    @Override
    public boolean clear() {
        return dbWorker.execute("DELETE FROM writers");
    }

    /**
     * Util method for parsing result {@link ResultSet resultset} of prepared statement execution
     *
     * @param resultSet the resultSet, contains data about {@link Writer writer} from dataBase, for parsing
     * @return list of {@link Writer writers}, parsed from current resultSet
     * @throws SQLException
     */
    private List<Writer> parseWritersResultSet(ResultSet resultSet) throws SQLException {
        List<Writer> writers = new ArrayList<>();
        while (resultSet.next()) {
            writers.add(new Writer(resultSet.getLong("id"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name")
            ));
        }
        return writers;
    }

    /**
     * Util method takes list of {@link Writer writers} and for each sets it {@link Post posts} list
     *
     * @param resultWriters the list of {@link Writer writers} for setting posts
     * @return the list of {@link Writer writers} with filled lists of it posts
     * or null if input list is empty
     */
    private List<Writer> fillWritersLabels(List<Writer> resultWriters) {
        if (resultWriters == null || resultWriters.isEmpty()) {
            return null;
        }
        PostRepositoryImpl postRepository = new PostRepositoryImpl();
        return resultWriters.stream().peek(writer -> setWriterPosts(writer, postRepository))
                .collect(Collectors.toList());
    }

    private void setWriterPosts(Writer writer, PostRepository postRepository) {
        writer.setPosts(postRepository.getPostsByWriterId(writer.getId()));
    }
}

