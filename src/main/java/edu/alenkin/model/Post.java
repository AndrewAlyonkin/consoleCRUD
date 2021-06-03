package edu.alenkin.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Class represents the entity of Post, written by the {@link edu.alenkin.model.Writer writer}.
 * <br> It can contains many different {@link edu.alenkin.model.Label labels}.
 */
public class Post {
    private long id;
    private String content;
    private LocalDateTime created;
    private LocalDateTime updated;
    private List<Label> labels = new ArrayList<>();
    private PostStatus status;

    public Post(long id, String content, LocalDateTime created) {
        this.id = id;
        this.content = content;
        this.created = created;
        this.status = PostStatus.UNDER_REVIEW;
        this.updated = LocalDateTime.now();
    }

    public Post(String content, LocalDateTime created, PostStatus status) {
        this.content = content;
        this.created = created;
        this.status = status;
        this.updated = LocalDateTime.now();
    }

    public Post(String content, PostStatus status) {
        this(content, LocalDateTime.now(), status);
    }

    /**
     * Takes new {@link edu.alenkin.model.Label Label} and adds it to the list of labels for the current post
     *
     * @param label the new {@link edu.alenkin.model.Label Label} to add in labels list
     */
    public void addLabel(Label label) {
        this.labels.add(label);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public LocalDateTime getUpdated() {
        return updated;
    }

    public void setUpdated(LocalDateTime updated) {
        this.updated = updated;
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

}
