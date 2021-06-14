package edu.alenkin.repository.hibernate;

import edu.alenkin.model.Label;
import edu.alenkin.model.Post;
import edu.alenkin.repository.LabelRepository;
import edu.alenkin.utils.hiber.HibernateWorker;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class LabelRepositoryHiber implements LabelRepository {
    @Override
    public List<Label> getByPostId(Long postId) {
        try (Session session = HibernateWorker.getSession()) {
           Post dummyPost = session.load(Post.class, postId);
           List<Label> labels = dummyPost.getLabels();
           labels = (labels == null || labels.isEmpty())
                   ? Collections.emptyList()
                   : labels;
            session.close();
            return labels;
        }
    }

    @Override
    public Long save(Label label, Long ownerId) {
        Long id = null;
        try (Session session = HibernateWorker.getSession()) {
            session.beginTransaction();
            Post dummyPost = session.load(Post.class, ownerId);
            dummyPost.addLabel(label);
            if (label.getId() != null) {
                session.saveOrUpdate(label);
            } else {
                id = (Long) session.save(label);
            }
            session.getTransaction().commit();
        }
        return id;
    }

    @Override
    public void delete(Long labelId) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            session.beginTransaction();
            Label forDelete = (Label) session.get(Label.class, labelId);
            session.delete(forDelete);
            session.getTransaction().commit();
        }
    }

    @Override
    public Label get(Long labelId) {
        return HibernateWorker.getSession().get(Label.class, labelId);
    }
}
