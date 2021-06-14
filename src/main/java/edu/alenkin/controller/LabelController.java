package edu.alenkin.controller;

import edu.alenkin.model.Label;
import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;
import edu.alenkin.service.LabelService;
import edu.alenkin.service.LabelServiceImpl;
import edu.alenkin.service.Service;
import edu.alenkin.view.LabelView;
import edu.alenkin.view.View;

import java.util.List;
import java.util.Scanner;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class LabelController {
    private View<Label> labelView;
    private LabelService labelService;
    private Scanner scanner;

    public LabelController(Scanner scanner) {
        this.scanner = scanner;
        labelView = new LabelView();
        labelService = new LabelServiceImpl();
    }

    public void refresh(Post post) {
        String action;
        boolean working = true;
        while (working) {
            labelView.show(labelService.getByPostId(post.getId()));
            labelView.show("Выберите действие из списка");
            action = scanner.nextLine();
            switch (action) {
                case "q": { //выход
                    working = false;
                    break;
                }
                case "1": { //добавить комментарий
                    onAddAction(post.getId());
                    break;
                }
                case "2": { //удалить комментарий
                    onDeleteAction();
                    break;
                }
            }
        }
    }

    private void onDeleteAction() {
        labelView.show("Введите id комментария из таблицы для удаления");
        try {
            String id = scanner.nextLine();
            Long labelId = Long.parseLong(id);
            labelService.remove(labelId);
        } catch (Exception e) {
            e.printStackTrace();
            labelView.err("Введите корректный id!");
        }
    }

    private void onAddAction(Long id) {
        labelView.show("Введите комментарий");
        String name = scanner.nextLine();
        labelService.add(new Label(name), id);
    }
}
