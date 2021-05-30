package edu.alenkin.model;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Class represents the entity of label of {@link edu.alenkin.model.Post post}.
 * <br>It belongs to the {@link edu.alenkin.model.Post post} entity
 */
public class Label {
    private long id;
    private String name;

    public Label(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
