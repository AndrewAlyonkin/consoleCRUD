package edu.alenkin.repository;

import edu.alenkin.repository.hibernate.LabelRepositoryHiber;
import edu.alenkin.repository.hibernate.PostRepositoryHiber;
import edu.alenkin.repository.hibernate.WriterRepositoryHiber;
import edu.alenkin.repository.jdbc.LabelRepositoryJdbc;
import edu.alenkin.repository.jdbc.PostRepositoryJdbc;
import edu.alenkin.repository.jdbc.WriterRepositoryJdbc;

import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class RepositoryFactory {
    private static WriterRepository writerRepository;
    public static PostRepository postRepository;
    public static LabelRepository labelRepository;
    static {
        if (Files.exists(Paths.get("src/main/resources/hiber.properties"))) {
            writerRepository = new WriterRepositoryHiber();
            postRepository = new PostRepositoryHiber();
            labelRepository = new LabelRepositoryHiber();
        } else if (Files.exists(Paths.get("src/main/resources/jdbc.properties"))) {
            writerRepository = new WriterRepositoryJdbc();
            postRepository = new PostRepositoryJdbc();
            labelRepository = new LabelRepositoryJdbc();
        }
    }

    public static WriterRepository getWriterRepository() {
        return writerRepository;
    }

    public static PostRepository getPostRepository() {
        return postRepository;
    }

    public static LabelRepository getLabelRepository() {
        return labelRepository;
    }
}
