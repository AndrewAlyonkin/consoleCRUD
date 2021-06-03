package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
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
    public void addWriter(Writer writer) throws SQLException {
        dbWorker.executeTransactional(connection -> {
            //insert writer to writers table
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO writers " +
                            "(first_name, last_name) " +
                            "VALUES (?, ?);");
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.executeUpdate();

            //insert writers posts to posts table
            List<Post> posts = writer.getPosts();
            if (posts == null || posts.isEmpty()) {
                return null;
            }
            PreparedStatement postsStatement = connection.prepareStatement(
                    "INSERT INTO posts" +
                            "(content, status, create_time, update_time, writer_id)" +
                            "VALUES (?, ?, ?, ?, ?)");
            for (Post post : posts) {
                postsStatement.setString(1, post.getContent());
                postsStatement.setString(2, post.getStatus().name());
                postsStatement.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
                postsStatement.setTimestamp(4, Timestamp.valueOf(post.getUpdated() == null
                        ? post.getCreated()
                        : post.getUpdated()));
                postsStatement.setLong(5, writer.getId());
                postsStatement.addBatch();
            }
            postsStatement.executeUpdate();

            //insert labels for each writers post to labels table
            boolean execute = false;
            PreparedStatement labelsStatement = connection.prepareStatement(
                    "INSERT INTO labels (name, post_id)" +
                            "VALUES (?,?)");
            for (Post p : writer.getPosts()) {
                for (Label l : p.getLabels()) {
                    labelsStatement.setString(1, l.getName());
                    labelsStatement.setLong(2, p.getId());
                    labelsStatement.addBatch();
                    execute = true;
                }
            }
            if (execute) {
                labelsStatement.executeUpdate();
            }
            return null;
        });
    }

    @Override
    public void removeWriter(Writer writer) throws SQLException, NotExistException, ExistException {
        removeWriterById(writer.getId());
    }

    @Override
    public void removeWriterById(long id) throws SQLException, NotExistException, ExistException {
        dbWorker.executePrepared("DELETE FROM writers WHERE id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, id);
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        throw new NotExistException();
                    }
                    return null;
                });
    }

    @Override
    public void updateWriter(Writer writer) throws SQLException, NotExistException, ExistException {
        dbWorker.executeTransactional(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE writers" +
                    " SET first_name=?, last_name=? WHERE id=?");
            preparedStatement.setString(1, writer.getFirstName());
            preparedStatement.setString(2, writer.getLastName());
            preparedStatement.setLong(3, writer.getId());
            preparedStatement.executeUpdate();
            return null;
        });

        PostRepository postRepository = new PostRepositoryImpl();
        postRepository.updatePostsForWriter(writer);
    }

    @Override
    public Writer getWriterById(long id) throws NotExistException, SQLException, ExistException {
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
    public List<Writer> getAllWriters() throws SQLException, NotExistException, ExistException {
        List<Writer> resultWriters = dbWorker.executePrepared("SELECT * FROM writers", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return parseWritersResultSet(resultSet);
        });
        return fillWritersLabels(resultWriters);
    }

    @Override
    public void clear() {
        dbWorker.execute("DELETE FROM writers");
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
    private List<Writer> fillWritersLabels(List<Writer> resultWriters) throws SQLException, NotExistException, ExistException {
        if (resultWriters == null || resultWriters.isEmpty()) {
            return null;
        }
        PostRepository postRepository = new PostRepositoryImpl();
        List<Writer> list = new ArrayList<>();
        for (Writer writer : resultWriters) {
            setWriterPosts(writer, postRepository);
            list.add(writer);
        }
        return list;
    }

    /**
     * Util method for setting current writer posts and labels from dataBase
     *
     * @param writer         the current writer for filling its posts
     * @param postRepository the {@link PostRepository} object for access to writers posts
     */
    private void setWriterPosts(Writer writer, PostRepository postRepository) throws SQLException, NotExistException, ExistException {
        writer.setPosts(postRepository.getPostsByWriterId(writer.getId()));
    }
}

