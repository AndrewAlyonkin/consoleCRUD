package edu.alenkin.view;

import edu.alenkin.controller.Controller;
import edu.alenkin.model.ModelBuffer;
import edu.alenkin.model.Writer;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Represent the main window of the program, shows possible actions and common data
 * about {@link edu.alenkin.model.Writer writers} in the storage
 */
public class FullView implements View {
    private Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void refresh(ModelBuffer buffer) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("Окно редактирования данных:");
        System.out.println("1: удалить автора");
        System.out.println("2: добавить автора");
        System.out.println("3: редактировать автора");
        System.out.println("q : Выход \n");
        for (Writer writer: buffer.getWriters()) {
            System.out.println(writer);
        }
        controller.onMainWindowAction();
    }
}
