package edu.alenkin.controller;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.*;
import edu.alenkin.service.*;
import edu.alenkin.view.FullView;
import edu.alenkin.view.View;
import edu.alenkin.view.WriterView;

import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Class to control the {@link View}. It reads user action,
 * dispatches appropriate commands to the {@link edu.alenkin.repository.Repository}
 * by it services and updates the {@link ModelBuffer}
 */
public class Controller {

    private View view;
    private FullView fullView;
    private WriterView writerView;
    private ModelBuffer buffer;
    private WriterService writerService = new WriterServiceImpl();
    private PostService postService = new PostServiceImpl();
    private LabelService labelService = new LabelServiceImpl();
    private Scanner scanner;

    public Controller(FullView fullView, WriterView writerView) {
        this.fullView = fullView;
        this.writerView = writerView;
    }

    public void init() {
        buffer = new ModelBuffer();
        refreshBuffer();
        view = fullView;
        view.refresh(buffer);
    }

    //processing action on main window of program
    public void onMainWindowAction() {
        boolean selectingAction = true;
        while (selectingAction) {
            System.out.print("Выберите действие из списка - ");
            String action = scanner.nextLine();
            switch (action) {
                case "q": {
                    return;
                }
                case "1": {
                    onDeleteWriterAction();
                    selectingAction = false;
                    break;
                }
                case "2": {
                    onAddWriterAction();
                    selectingAction = false;
                    break;
                }
                case "3": {
                    onUpdateWriterAction();
                    selectingAction = false;
                    break;
                }
            }
        }
        refreshBuffer();
        view.refresh(buffer);
    }

    // Processing action of writer editing window
    public void switchAction() {
        boolean selectingAction = true;
        while (selectingAction) {
            String action = scanner.nextLine();
            if (action.equals("q")) {
                onBackToFullViewAction();
                break;
            }
            switch (action) {
                case "1": {
                    onPostDeleteAction();
                    selectingAction = false;
                    break;
                }
                case "2": {
                    onPostAddAction();
                    selectingAction = false;
                    break;
                }
                case "3": {
                    onPostEditAction();
                    selectingAction = false;
                    break;
                }
                case "4": {
                    onLabelDeleteAction();
                    selectingAction = false;
                    break;
                }
                case "5": {
                    onLabelAddAction();
                    selectingAction = false;
                    break;
                }
                default:
                    System.out.println("Введите корректное действие из списка!!!");
            }
        }
        refreshBuffer();
        this.view.refresh(buffer);
    }

    public void setScanner(Scanner scanner) {
        this.scanner = scanner;
    }

    // main window actions
    private void onUpdateWriterAction() {
        while (true) {
            try {
                System.out.println("Введите существующий ID автора для обновления:");
                long id = Long.parseLong(scanner.nextLine());
                Writer currentWriter = writerService.getWriter(id);
                System.out.println("Автор для редактирования: " + currentWriter);
                System.out.println("Введите новое имя автора:");
                String name = scanner.nextLine();
                System.out.println("Введите новую фамилию автора:");
                String lastName = scanner.nextLine();
                currentWriter.setFirstName(name);
                currentWriter.setLastName(lastName);
                writerService.updateWriter(currentWriter);
                buffer.setActiveWriter(currentWriter);
                break;
            } catch (ExistException | SQLException | NotExistException e) {
                System.err.println(e.getMessage());
                System.out.println("Введите корректные данные!");
            }
        }
        this.view = writerView;
    }

    private void onAddWriterAction() {
        System.out.println("Введите имя автора:");
        String name = scanner.nextLine();
        System.out.println("Введите фамилию автора:");
        String lastName = scanner.nextLine();
        Writer newWriter = new Writer(name, lastName);
        try {
            writerService.addWriter(newWriter);
        } catch (ExistException | SQLException e) {
            e.printStackTrace();
        }
    }

    private void onDeleteWriterAction() {
        System.out.println("Введите номер автора для удаления - ");
        while (true) {
            int action = Integer.parseInt(scanner.nextLine());
            try {
                writerService.removeWriterById(action);
                return;
            } catch (SQLException | NotExistException | ExistException e) {
                System.err.println(e.getMessage());
                System.out.println("Введите корректный существующий ID автора!!!");
            }
        }
    }

    // writer editing window actions

    private void onBackToFullViewAction() {
        this.view = fullView;
    }

    private void onPostDeleteAction() {
        while (true) {
            System.out.println("Введите существующий id поста из таблицы для удаления");
            System.out.println("q - назад");
            String answer = scanner.nextLine();
            try {
                if (answer.equals("q")) {
                    return;
                }
                long postId = Long.parseLong(answer);
                postService.removePost(postId);
                return;
            } catch (SQLException | NotExistException | ExistException | NumberFormatException throwables) {
                System.err.println(throwables.getMessage());
            }
        }
    }

    private void onPostAddAction() {
        System.out.println("Введите содержимое поста:");
        String content = scanner.nextLine();
        PostStatus status = switchStatus();
        try {
            postService.addPost(new Post(content, status), buffer.getActiveWriter().getId());
        } catch (SQLException | NotExistException | ExistException e) {
            e.printStackTrace();
        }

    }

    private void onLabelDeleteAction() {
        while (true) {
            System.out.println("Введите ID комментария из списка для удаления:");
            System.out.println("q - назад");
            try {
                String answer = scanner.nextLine();
                if (answer.equals("q")) {
                    return;
                }
                long labelId = Long.parseLong(answer);
                labelService.removeLabelById(labelId);
                return;
            } catch (SQLException | NotExistException | ExistException | NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void onLabelAddAction() {
        System.out.println("Введите комментарий:");
        String labelName = scanner.nextLine();
        while (true) {
            System.out.println("Введите ID СУЩЕСТВУЮЩЕГО поста из списка для добавления комментария:");
            System.out.println("q - выход");
            try {
                String answer = scanner.nextLine();
                if (answer.equals("q")) {
                    return;
                }
                long postId = Long.parseLong(answer);
                Label currentLabel = new Label(labelName);
                labelService.addLabel(currentLabel, postId);
                return;
            } catch (SQLException | NotExistException | ExistException | NumberFormatException e) {
                System.err.println(e.getMessage());
            }
        }
    }

    private void onPostEditAction() {
        System.out.println("РЕДАКТИРОВАНИЕ ПОСТА:");
        Post post = null;
        List<Post> writersPosts = buffer.getActiveWriter().getPosts();
        if (writersPosts == null || writersPosts.isEmpty()) {
            return;
        }

        boolean readPostId = true;
        while (readPostId) {
            System.out.println("Введите номер существующего поста из таблицы для редактирования");
            long postId = Long.parseLong(scanner.nextLine());
            for (Post currentPost : writersPosts) {
                if (currentPost.getId() == postId) {
                    post = currentPost;
                    readPostId = false;
                    break;
                }
            }
        }
        System.out.println("Введите содержимое поста:");
        String content = scanner.nextLine();
        PostStatus status = switchStatus();
        post.setStatus(status);
        post.setContent(content);
        try {
            postService.updatePostsForWriter(buffer.getActiveWriter());
        } catch (SQLException | NotExistException | ExistException throwables) {
            throwables.printStackTrace();
        }
    }

    // util methods
    private void refreshBuffer() {
        try {
            List<Writer> writers = writerService.getAllWriters();
            if (writers == null || writers.isEmpty()) {
                System.out.println("В базе нет данных, создайте автора:");
                onAddWriterAction();
                writers = writerService.getAllWriters();
            }
            buffer.setWriters(writers);

            Writer activeWriter = buffer.getActiveWriter();
            if (activeWriter != null) {
                buffer.setActiveWriter(writerService.getWriter(activeWriter.getId()));
            }

        } catch (SQLException | NotExistException | ExistException e) {
            e.printStackTrace();
        }
    }

    private PostStatus switchStatus() {
        System.out.println("Выберите статус поста:" +
                "\n 1 - Опубликован" +
                "\n 2 - Проверяется" +
                "\n 3 - Удален:");
        PostStatus status = PostStatus.UNDER_REVIEW;
        boolean switchaction = true;
        while (switchaction) {
            String answer = scanner.nextLine();
            switch (answer) {
                case "1": {
                    status = PostStatus.ACTIVE;
                    switchaction = false;
                    break;
                }
                case "2": {
                    status = PostStatus.UNDER_REVIEW;
                    switchaction = false;
                    break;
                }
                case "3": {
                    status = PostStatus.DELETED;
                    switchaction = false;
                    break;
                }
                default:
                    System.out.println("Введите вариант из списка!");
            }
        }
        return status;
    }
}

