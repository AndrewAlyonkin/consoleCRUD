package edu.alenkin.repository;

import edu.alenkin.model.Label;
import edu.alenkin.model.Post;
import edu.alenkin.model.PostStatus;
import edu.alenkin.utils.DBWorker;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * The base implementation of {@link edu.alenkin.repository.PostRepository} for manipulating
 * {@link edu.alenkin.model.Post} entity in storage
 */
public class PostRepositoryImpl implements PostRepository {

    @Override
    public Long save(Post post, Long writerId) {
        Long postId = null;
        try {
            //если добавляем новый пост и для него еще не существует id
            if (post.getId() == null) {
                postId = DBWorker.executeSave("INSERT INTO posts (content, status, create_time, update_time, writer_id)" +
                        "VALUES (?, ?, ?, ?, ?)", prepSt -> {
                    prepSt.setString(1, post.getContent());
                    prepSt.setString(2, post.getStatus().name());
                    prepSt.setTimestamp(3, Timestamp.valueOf(post.getCreated()));
                    prepSt.setTimestamp(4, Timestamp.valueOf(post.getUpdated()));
                    prepSt.setLong(5, writerId);
                });
                //если обновляем существующий пост
            } else {
                DBWorker.executeSave("UPDATE posts SET content=?, status=?, update_time=? WHERE id=?", prepSt -> {
                    prepSt.setString(1, post.getContent());
                    prepSt.setString(2, post.getStatus().name());
                    prepSt.setTimestamp(3, Timestamp.valueOf(post.getUpdated()));
                    prepSt.setLong(4, post.getId());
                });
            }
            //записываем в базу комментарии для поста
            LabelRepositoryImpl labelRepo = new LabelRepositoryImpl();
            for (Label label : post.getLabels()) {
                labelRepo.save(label, postId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return postId;
    }

    @Override
    public void delete(Long id) {
        try {
            DBWorker.executeVoid("DELETE FROM posts WHERE id=?", id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Post get(Long id) {
        Post currentPost = DBWorker.executeGet("SELECT * FROM posts WHERE id=?;", id, this::parse);
        if (currentPost != null) {
            currentPost.setLabels(new LabelRepositoryImpl().getByPostId(id));
        }
        return currentPost;
    }

    @Override
    public List<Post> getByWriterId(Long id) {
        List<Post> result = DBWorker.executeGet("SELECT * FROM posts WHERE writer_id=?", id, this::parseList);
        try {
            fillPostsLabels(result);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * Util method takes list of {@link Post posts} and for each sets it {@link Label labels} list
     *
     * @param resultPosts the list of {@link Post posts} for setting labels
     * @return the list of {@link Post posts} with filled lists of it labels
     * or null if input list is empty
     */
    private List<Post> fillPostsLabels(List<Post> resultPosts) throws SQLException {
        if (resultPosts == null || resultPosts.isEmpty()) {
            return null;
        }
        LabelRepositoryImpl labelRepository = new LabelRepositoryImpl();
        List<Post> list = new ArrayList<>();
        for (Post post : resultPosts) {
            post.setLabels(labelRepository.getByPostId(post.getId()));
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

    private List<Post> parseList(ResultSet resultSet) {
        List<Post> posts = new ArrayList<>();
        try {
            while (resultSet.next()) {
                Post currentPost = new Post(resultSet.getLong("id"), resultSet.getString("content"),
                        DBWorker.convertToLocalDateTime(resultSet.getTimestamp("update_time")));
                currentPost.setStatus(switchStatus(resultSet.getString("status")));
                posts.add(currentPost);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return posts;
    }

    private Post parse(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Post currentPost = new Post(resultSet.getLong("id"), resultSet.getString("content"),
                    DBWorker.convertToLocalDateTime(resultSet.getTimestamp("update_time")));
            currentPost.setStatus(switchStatus(resultSet.getString("status")));
            return currentPost;
        }
        return null;
    }
}
