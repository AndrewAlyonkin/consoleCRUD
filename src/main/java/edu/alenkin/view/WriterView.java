package edu.alenkin.view;

import edu.alenkin.controller.Controller;
import edu.alenkin.model.Label;
import edu.alenkin.model.ModelBuffer;
import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;

import java.util.List;
import java.util.Scanner;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */

/**
 * Represent local window for editing the {@link edu.alenkin.model.Writer writers} data
 */
public class WriterView implements View {
    private Controller controller;

    @Override
    public void setController(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void refresh(ModelBuffer buffer) {
        System.out.println("\n==============================================================");
        System.out.println("ОКНО РЕДАКТИРОВАНИЯ АВТОРА:");
        System.out.println("1 : Удалить пост");
        System.out.println("2 : Добавить пост");
        System.out.println("3 : Редактировать пост");
        System.out.println("  4 : Удалить комментарий");
        System.out.println("  5 : Добавить комментарий");
        System.out.println("q : Назад");
        System.out.println("==============================================================");

        Writer activeWriter = buffer.getActiveWriter();
        System.out.println(activeWriter);
        List<Post> posts = activeWriter.getPosts();
        if (posts == null || posts.isEmpty()) {
            System.out.println("У автора еще нет постов, нужно добавить");
        } else {
            for (Post post : posts) {
                System.out.println("\t" + post);
                for (Label label : post.getLabels()) {
                    System.out.println("\t\t" + label);
                }
            }
        }
        System.out.println("Выберите действие - ");
        controller.switchAction();
    }
}
