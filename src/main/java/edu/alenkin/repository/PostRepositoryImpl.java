package edu.alenkin.repository;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
import edu.alenkin.model.Post;
import edu.alenkin.model.PostStatus;
import edu.alenkin.model.Writer;

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
 * The base implementation of {@link edu.alenkin.repository.PostRepository} for manipulating
 * {@link edu.alenkin.model.Post} entity in storage
 */
public class PostRepositoryImpl extends Repository implements PostRepository {

    @Override
    public void addPost(Post post, long writerId) throws SQLException, NotExistException, ExistException {
        dbWorker.executePrepared("INSERT INTO posts (content, status, create_time, update_time, writer_id)" +
                "VALUES (?, ?, ?, ?, ?)", preparedStatement -> {
            preparedStatement.setString(1, post.getContent());
            preparedStatement.setString(2, post.getStatus().name());
            preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
            preparedStatement.setTimestamp(4, Timestamp.valueOf(post.getUpdated()));
            preparedStatement.setLong(5, writerId);
            preparedStatement.executeUpdate();
            return null;
        });
    }

    @Override
    public void removePost(long postId) throws SQLException, NotExistException, ExistException {
        dbWorker.executePrepared("DELETE FROM posts WHERE id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, postId);
                    int result = preparedStatement.executeUpdate();
                    if (result == 0) {
                        throw new NotExistException();
                    }
                    return null;
                });
    }

    @Override
    public List<Post> getPostsByWriterId(long id) throws SQLException, NotExistException, ExistException {
        List<Post> resultPosts = dbWorker.executePrepared("SELECT * FROM posts WHERE writer_id=?",
                preparedStatement -> {
                    preparedStatement.setLong(1, id);
                    ResultSet resultSet = preparedStatement.executeQuery();
                    return parsePostResultSet(resultSet);
                });
        return fillPostsLabels(resultPosts);
    }

    @Override
    public void updatePostsForWriter(Writer writer) throws SQLException, NotExistException, ExistException {
        List<Post> posts = writer.getPosts();
        if (posts != null && !posts.isEmpty()) {
            dbWorker.executePrepared("UPDATE posts SET content=?, status=?, update_time=? WHERE id=?", preparedStatement -> {
                boolean needUpdate = false;
                for (Post post : posts) {
                    if (post.getCreated() != post.getUpdated()) {
                        preparedStatement.setString(1, post.getContent());
                        preparedStatement.setString(2, post.getStatus().name());
                        preparedStatement.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
                        preparedStatement.setLong(4, post.getId());
                        preparedStatement.addBatch();
                        needUpdate = true;
                    }
                }
                if (needUpdate) {
                    preparedStatement.executeUpdate();
                }
                return null;
            });

            LabelRepository labelRepository = new LabelRepositoryImpl();
            labelRepository.updateLabelsForWriter(writer);
        }
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
            Post currentPost = new Post(resultSet.getLong("id"), resultSet.getString("content"),
                    dbWorker.convertToLocalDateTime(resultSet.getTimestamp("update_time")));
            currentPost.setStatus(switchStatus(resultSet.getString("status")));
            posts.add(currentPost);
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
    private List<Post> fillPostsLabels(List<Post> resultPosts) throws SQLException, NotExistException, ExistException {
        if (resultPosts == null || resultPosts.isEmpty()) {
            return null;
        }
        LabelRepositoryImpl labelRepository = new LabelRepositoryImpl();
        List<Post> list = new ArrayList<>();
        for (Post post : resultPosts) {
            post.setLabels(labelRepository.getLabelsByPostId(post.getId()));
            list.add(post);
        }
        return list;
    }

    private PostStatus switchStatus(String statusFromDb) {
        switch (statusFromDb) {
            case "ACTIVE":
                return PostStatus.ACTIVE;
            case "DELETED":
                return PostStatus.DELETED;
        }
        return PostStatus.UNDER_REVIEW;
    }
}
