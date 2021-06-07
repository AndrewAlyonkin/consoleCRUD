package edu.alenkin.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Class represents the entity of Post, written by the {@link edu.alenkin.model.Writer writer}.
 * <br> It can contains many different {@link edu.alenkin.model.Label labels}.
 */
public class Post {
    private Long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<Label> labels;
    private PostStatus status;

    public Post(Long id, String content, LocalDateTime created, LocalDateTime updated, List<Label> labels, PostStatus status) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.updated = updated;
        this.labels = labels;
        this.status = status;
    }

    public Post(Long id, String content, LocalDateTime created) {
        this(id, content, created, LocalDateTime.now(), new ArrayList<>(), PostStatus.UNDER_REVIEW);
    }

    public Post(String content, LocalDateTime created, PostStatus status) {
       this(null, content, created, LocalDateTime.now(), new ArrayList<>(),status);
    }

    public Post(String content, PostStatus status) {
        this(content, LocalDateTime.now(), status);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        this.updated = LocalDateTime.now();
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public List<Label> getLabels() {
        return labels;
    }

    public void setLabels(List<Label> labels) {
        this.labels = labels;
    }

    public PostStatus getStatus() {
        return status;
    }

    public void setStatus(PostStatus status) {
        this.status = status;
        this.updated = LocalDateTime.now();
    }

    @Override
    public String toString() {
        return "| " + String.format("%-5s", this.id) +
                "| " + String.format("%-25s", this.content) +
                "| " + String.format("%-15s", this.created) +
                "| " + String.format("%-15s", this.updated) +
                "| " + String.format("%-20s", this.status.getStatus()) +
                " |";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Post post = (Post) o;
        return id.equals(post.id) && Objects.equals(content, post.content) && created.equals(post.created) && updated.equals(post.updated) && Objects.equals(labels, post.labels) && status == post.status;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, content, created, updated, labels, status);
    }
}
