package edu.alenkin.model;

import java.time.LocalDateTime;
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
    private List<Label> labels;

    public Post(long id, String content, LocalDateTime created) {
        this.id = id;
        this.content = content;
        this.created = created;
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
}
