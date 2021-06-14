package edu.alenkin.controller;

import edu.alenkin.model.Post;
import edu.alenkin.model.PostStatus;
import edu.alenkin.model.Writer;
import edu.alenkin.service.PostService;
import edu.alenkin.service.PostServiceImpl;
import edu.alenkin.view.PostView;

import java.util.Scanner;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class PostController {
    private PostService postService;
    private Scanner scanner;
    private LabelController labelController;
    private PostView postView;


    public PostController(Scanner scanner) {
        this.scanner = scanner;
        postService = new PostServiceImpl();
        postView = new PostView();
        labelController = new LabelController(scanner);
    }

    public void refresh(Writer writer) {
        String action;
        boolean working = true;
        while (working) {
            postView.show(postService.getByWriterId(writer.getId()));
            postView.show("Выберите действие из списка");
            action = scanner.nextLine();
            switch (action) {
                case "q": { //выход
                    working = false;
                    break;
                }
                case "1": { //добавить пост
                    onAddAction(writer.getId());
                    break;
                }
                case "2": { //редактировать пост
                    onEditAction(writer.getId());
                    break;
                }
                case "3": { //удалить пост
                    onDeleteAction();
                    break;
                }
                case "4": { //редактировать комментарии
                    onLabelsAction();
                    break;
                }
            }
        }
    }

    private void onLabelsAction() {
        postView.show("Введите номер поста из таблицы для добавления комментариев:");
        try {
            String id = scanner.nextLine();
            Long postId = Long.parseLong(id);
            labelController.refresh(postService.get(postId));
        } catch (Exception e) {
            e.printStackTrace();
            postView.err("Введите корректный id.");
        }
    }

    private void onDeleteAction() {
        try {
            postView.show("Введите id поста из таблицы для удаления:");
            String id = scanner.nextLine();
            Long postId = Long.parseLong(id);
            postService.remove(postId);
        } catch (Exception e) {
            e.printStackTrace();
            postView.err("Введите корректный id из таблицы");
        }
    }

    private void onEditAction(Long writerId) {
        postView.show("Введите содержимое поста:");
        String content = scanner.nextLine();
        postView.show("Выберите статус");
        PostStatus status = switchStatus();
        postView.show("Введите номер поста из таблицы, к которому будет прикреплен комментарий");
        try {
            String id = scanner.nextLine();
            Long postId = Long.parseLong(id);
            Post currentPost = postService.get(postId);
            currentPost.setContent(content);
            currentPost.setStatus(status);
            postService.update(currentPost, writerId);
        } catch (Exception e) {
            e.printStackTrace();
            postView.err("Введите id поста из таблицы!");
        }
    }

    private void onAddAction(Long id) {
        postView.show("Введите содержимое поста:");
        String content = scanner.nextLine();
        postView.show("Выберите статус");
        PostStatus status = switchStatus();
        postService.add(new Post(content, status), id);
    }

    private PostStatus switchStatus(){
        while (true) {
            postView.show("0 - Опубликован");
            postView.show("1 - Проверяется");
            postView.show("2 - Удален");
            String action = scanner.nextLine();
            switch (action) {
                case "0" : return PostStatus.ACTIVE;
                case "1" : return PostStatus.UNDER_REVIEW;
                case "2" : return PostStatus.DELETED;
            }
        }
    }

}
