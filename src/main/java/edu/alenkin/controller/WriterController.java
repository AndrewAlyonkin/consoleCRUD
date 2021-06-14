package edu.alenkin.controller;

import edu.alenkin.model.Writer;
import edu.alenkin.service.WriterService;
import edu.alenkin.service.WriterServiceImpl;
import edu.alenkin.view.View;
import edu.alenkin.view.WriterView;

import java.util.Scanner;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class WriterController {
    private WriterService writerService;
    private View writerView;
    boolean working;
    private final Scanner scanner;
    private PostController postController;

    public WriterController() {
        scanner = new Scanner(System.in);
        writerView = new WriterView();
        writerService = new WriterServiceImpl();
        working = true;
        postController = new PostController(scanner);
    }

    public void refresh() {
        String action;
        while (working) {
            writerView.show(writerService.getAll());
            writerView.show("Выберите действие из списка");
            action = scanner.nextLine();
            switch (action) {
                case "q": { //выход
                    working = false;
                    break;
                }
                case "1": { //добавить автора
                    onAddAction();
                    break;
                }
                case "2": { //редактировать автора
                    onEditAction();
                    break;
                }
                case "3": { //удалить автора
                    onDeleteAction();
                    break;
                }
            }
        }
    }

    private void onDeleteAction() {
        writerView.show("Введите id автора из таблицы для удаления:");
        String action = scanner.nextLine();
        try {
            Long id = Long.parseLong(action);
            writerService.remove(id);
        } catch (Exception e) {
            e.printStackTrace();
            writerView.err("Введите корректный id.");
            return;
        }
    }

    private void onEditAction() {
        writerView.show("Введите id автора для редактирования:");
        try {
            String id = scanner.nextLine();
            Long writerId = Long.parseLong(id);
            Writer currentWriter = writerService.get(writerId);
            writerView.show(currentWriter);
            postController.refresh(currentWriter);
        } catch (Exception e) {
            e.printStackTrace();
            writerView.err("Введите корректный id.");
        }
    }

    private void onAddAction() {
        System.out.println("Введите имя автора:");
        String name = scanner.nextLine();
        System.out.println("Введите фамилию автора:");
        String lastName = scanner.nextLine();
        Writer newWriter = new Writer(name, lastName);
        writerService.add(newWriter);
    }
}
