package edu.alenkin.model;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
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
@Entity
@Table(name = "writers")
public class Writer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", columnDefinition = "BIGINT", nullable = false)
    private Long id;
    @Column(name = "first_name", columnDefinition = "VARCHAR", nullable = false)
    private String firstName;
    @Column(name = "last_name", columnDefinition = "VARCHAR", nullable = false)
    private String lastName;
    @OneToMany(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "writer_id")
    private List<Post> posts = new ArrayList<>();

    public Writer() {
    }

    public Writer(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Writer(String firstName, String lastName) {
        this(null, firstName, lastName);
    }

    /**
     * Takes a new {@link edu.alenkin.model.Post post} and adds it to the posts list
     *
     * @param post the new {@link edu.alenkin.model.Post post} for adding
     */
    public void addPost(Post post) {
        this.posts.add(post);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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
        return id.equals(writer.id) && Objects.equals(firstName, writer.firstName) && Objects.equals(lastName, writer.lastName) && Objects.equals(posts, writer.posts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, posts);
    }
}
