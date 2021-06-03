package edu.alenkin.view;

import edu.alenkin.controller.Controller;
import edu.alenkin.model.ModelBuffer;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Represent the program window. Shows the current state of the program in the console.
 */
public interface View {

    void setController(Controller controller);

    /**
     * Update actual state of the program and print it in the console
     * @param buffer the exchanger between view and the {@link edu.alenkin.repository.Repository}
     */
    void refresh(ModelBuffer buffer);
}
