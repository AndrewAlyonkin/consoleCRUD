package edu.alenkin.view;

import edu.alenkin.model.Label;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class LabelView implements View<Label> {

    @Override
    public void show(Label label) {
        System.out.println(label);
    }

    @Override
    public void show(List<Label> labels) {
        System.out.println("\n==============================================================");
        System.out.println("ОКНО РЕДАКТИРОВАНИЯ КОММЕНТАРИЕВ ПОСТА:");
        System.out.println("q - назад");
        System.out.println("1 - добавить комментарий");
        System.out.println("2 - удалить комментарий");
        for (Label label : labels) {
            show(label);
        }
    }
}
