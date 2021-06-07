package edu.alenkin.view;

import edu.alenkin.model.Post;

import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class PostView implements View<Post> {

    @Override
    public void show(Post post) {
        System.out.println(post);
    }

    @Override
    public void show(List<Post> posts) {
        System.out.println("\n==============================================================");
        System.out.println("ОКНО РЕДАКТИРОВАНИЯ ПОСТОВ АВТОРА:");
        System.out.println("q - назад");
        System.out.println("1 - добавить пост");
        System.out.println("2 - редактировать пост");
        System.out.println("3 - удалить пост");
        System.out.println("4 - редактировать комментарии");
        for (Post post : posts) {
            System.out.println(post);
        }
    }
}
