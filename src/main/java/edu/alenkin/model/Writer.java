package edu.alenkin.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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
    private List<Post> posts = new ArrayList<>();

    public Writer(long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Writer(String firstName, String lastName) {
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

    @Override
    public String toString() {
        return "| " + String.format("%-5s", this.id) + "| " +
                String.format("%-15s", this.firstName) + "| " +
                String.format("%-15s", this.lastName) + "|";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Writer writer = (Writer) o;
        return id == writer.id && Objects.equals(firstName, writer.firstName) && Objects.equals(lastName, writer.lastName) && Objects.equals(posts, writer.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, posts);
    }
}
