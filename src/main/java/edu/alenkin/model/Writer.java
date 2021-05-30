package edu.alenkin.model;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Class represents the entity of Author of {@link edu.alenkin.model.Post posts}.
 * <br>It can contains many different {@link edu.alenkin.model.Post posts}
 */
public class Writer {
    private long id;
    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Takes a new {@link edu.alenkin.model.Post post} and adds it to the posts list
     *
     * @param post the new {@link edu.alenkin.model.Post post} for adding
     */
    public void addPost(Post post) {
        this.posts.add(post);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }
}
