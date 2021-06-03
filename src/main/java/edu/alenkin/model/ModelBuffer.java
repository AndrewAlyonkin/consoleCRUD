package edu.alenkin.model;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

import java.util.ArrayList;
import java.util.List;

/**
 * Class that is a buffer and allows data exchange between the {@link edu.alenkin.view.View}
 * and {@link edu.alenkin.repository.Repository}
 */
public class ModelBuffer {
    private Writer activeWriter;
    private List<Writer> writers = new ArrayList<>();

    public Writer getActiveWriter() {
        return activeWriter;
    }

    public void setActiveWriter(Writer activeWriter) {
        this.activeWriter = activeWriter;
    }

    public List<Writer> getWriters() {
        return writers;
    }

    public void setWriters(List<Writer> writers) {
        this.writers = writers;
    }

}
