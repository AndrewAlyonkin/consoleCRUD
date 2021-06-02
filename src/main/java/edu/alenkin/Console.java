package edu.alenkin;

import edu.alenkin.exception.ExistException;
import edu.alenkin.exception.NotExistException;
import edu.alenkin.model.Label;
import edu.alenkin.model.Post;
import edu.alenkin.model.PostStatus;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.WriterRepositoryImpl;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class Console {
    public static void main(String[] args) throws InterruptedException {
        Label label1 = new Label(1L, "first-first");
        Label label2 = new Label(2L, "first-second");
        Label label3 = new Label(3L, "first-third");
        Label label11 = new Label(4L, "second-first");
        Label label12 = new Label(4L, "second-second");
        Label label13 = new Label(4L, "second-third");

        Post post1 = new Post(1L, "first post");
        post1.addLabel(label1);
        post1.addLabel(label2);
        post1.addLabel(label3);

        Post post2 = new Post(2L, "second post");
        post2.addLabel(label11);
        post2.addLabel(label12);
        post2.addLabel(label13);

        Writer writer = new Writer(1L, "Andrew", "Alenkin");
        writer.addPost(post1);
        writer.addPost(post2);

        WriterRepositoryImpl repo = new WriterRepositoryImpl();

        repo.clear();
        repo.addWriter(writer);
        Thread.sleep(3000);

        post2.setContent("UPDATED CONTENT FOR TEST");
        repo.updateWriter(writer);
    }
}
