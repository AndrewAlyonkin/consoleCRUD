package edu.alenkin.view;

import edu.alenkin.model.Writer;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class WriterView implements View<Writer> {
    @Override
    public void show(Writer writer) {
        System.out.println(writer);
    }

    @Override
    public void show(List<Writer> writers) {
        System.out.println("\n==============================================================");
        System.out.println("ОКНО РЕДАКТИРОВАНИЯ АВТОРА:");
        System.out.println("q - выход");
        System.out.println("1 - добавить автора");
        System.out.println("2 - редактировать автора");
        System.out.println("3 - удалить автора");
        for (Writer writer : writers) {
            System.out.println(writer);
        }
    }

}
