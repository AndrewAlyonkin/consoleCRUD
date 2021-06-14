package edu.alenkin.repository.hibernate;

import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.PostRepository;
import edu.alenkin.utils.hiber.HibernateWorker;
import org.hibernate.Session;

import java.util.Collections;
import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class PostRepositoryHiber implements PostRepository {
    @Override
    public List<Post> getByWriterId(Long writerId) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            Writer dummyWriter = session.load(Writer.class, writerId);
            List<Post> posts = dummyWriter.getPosts();
            posts = (posts == null || posts.isEmpty())
                    ? Collections.emptyList()
                    : posts;
            return posts;
        }
    }

    @Override
    public Long save(Post post, Long writerId) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            session.beginTransaction();
            Writer dummyWriter = session.load(Writer.class, writerId);
            Long id = null;
            if (post.getId() != null) {
                Post current = session.get(Post.class, post.getId());
                current.setStatus(post.getStatus());
                current.setContent(post.getContent());
                current.setLabels(post.getLabels());
                id = post.getId();
            } else {
                dummyWriter.addPost(post);
                id = (Long) session.save(post);
            }
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    public void delete(Long postId) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            session.beginTransaction();
            Post currentPost = session.get(Post.class, postId);
            session.delete(currentPost);
            session.getTransaction().commit();
        }
    }

    @Override
    public Post get(Long postId) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            return session.get(Post.class, postId);
        }
    }
}
