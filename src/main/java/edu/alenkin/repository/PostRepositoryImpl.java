package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
import edu.alenkin.model.Post;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link edu.alenkin.repository.PostRepository} for manipulating
 * {@link edu.alenkin.model.Post} entity in storage
 */
public class PostRepositoryImpl extends Repository implements PostRepository {

    @Override
    public void addPost(Post post) throws ExistException {

    }

    @Override
    public void removePost(Post post) throws NotExistException {
        dbWorker.executePrepared("DELETE FROM posts WHERE id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, post.getId());
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        throw new NotExistException();
                    }
                    return null;
                });
    }

    @Override
    public void updatePost(Post post) throws NotExistException {

    }

    @Override
    public List<Post> getPostsByWriterId(long id) {
        List<Post> resultPosts = dbWorker.executeQuery("SELECT * FROM posts WHERE writer_id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return parsePostResultSet(resultSet);
                });
        return fillPostsLabels(resultPosts);
    }

    @Override
    public List<Post> getAllPosts() {
        List<Post> resultPosts = dbWorker.executeQuery("SELECT * FROM posts", preparedStatement -> {
            ResultSet resultSet = preparedStatement.executeQuery();
            return parsePostResultSet(resultSet);
        });
        return fillPostsLabels(resultPosts);
    }

    @Override
    public boolean clear() {
        return dbWorker.execute("DELETE FROM posts");
    }

    /**
     * Util method for parsing result {@link ResultSet resultset} of prepared statement execution
     *
     * @param resultSet the resultSet, contains data about {@link Post post} from dataBase, for parsing
     * @return list of {@link Post posts}, parsed from current resultSet
     * @throws SQLException
     */
    private List<Post> parsePostResultSet(ResultSet resultSet) throws SQLException {
        List<Post> posts = new ArrayList<>();
        while (resultSet.next()) {
            posts.add(new Post(resultSet.getLong("id"), resultSet.getString("content"),
                    dbWorker.convertToLocalDateTime(resultSet.getTimestamp("update_time"))));
        }
        return posts;
    }

    /**
     * Util method takes list of {@link Post posts} and for each sets it {@link Label labels} list
     *
     * @param resultPosts the list of {@link Post posts} for setting labels
     * @return the list of {@link Post posts} with filled lists of it labels
     * or null if input list is empty
     */
    private List<Post> fillPostsLabels(List<Post> resultPosts) {
        if (resultPosts == null || resultPosts.isEmpty()) {
            return null;
        }
        LabelRepositoryImpl labelRepository = new LabelRepositoryImpl();
        return resultPosts.stream().peek(post -> post.setLabels(labelRepository.getLabelsByPostId(post.getId())))
                .collect(Collectors.toList());
    }
}
