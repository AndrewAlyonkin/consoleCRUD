package edu.alenkin.model;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import javax.persistence.*;
import java.util.Objects;

/**
 * Class represents the entity of label of {@link edu.alenkin.model.Post post}.
 * <br>It belongs to the {@link edu.alenkin.model.Post post} entity
 */
@Entity
@Table(name = "labels")
public class Label {
    @Id
    @Column(name = "id", columnDefinition = "BIGINT", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "VARCHAR")
    private String name;

    @Column(name = "post_id")
    private Long post_id;

    public Label(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Label() {
    }

    public Label(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "| " + String.format("%-5s", this.id) + "| " +
                String.format("%-15s", this.name) + "|";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Label label = (Label) o;
        return id.equals(label.id) && name.equals(label.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}
