package edu.alenkin.repository.hibernate;

import edu.alenkin.model.Post;
import edu.alenkin.model.Writer;
import edu.alenkin.repository.WriterRepository;
import edu.alenkin.utils.hiber.HibernateWorker;
import org.hibernate.Session;

import javax.persistence.Query;
import java.util.Collections;
import java.util.List;

/**
 * @author Alenkin Andrew
 * oxqq@ya.ru
 */
public class WriterRepositoryHiber implements WriterRepository {
    @Override
    @Deprecated
    public Long save(Writer object, Long ownerId) {
        throw new UnsupportedOperationException("Writer can not have the owner");
    }

    @Override
    public void delete(Long writerId) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            session.beginTransaction();
            Writer currentWriter = session.get(Writer.class, writerId);
            session.delete(currentWriter);
            session.getTransaction().commit();
        }
    }

    @Override
    public Writer get(Long writerId) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            return session.get(Writer.class, writerId);
        }
    }

    @Override
    public void delete(Writer writer) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.delete(writer);
            session.getTransaction().commit();
        }
    }

    @Override
    public Long save(Writer writer) {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            session.beginTransaction();
            Long id = null;
            if (writer.getId() != null) {
                session.saveOrUpdate(writer);
            } else {
                id = (long) session.save(writer);
            }
            session.getTransaction().commit();
            return id;
        }
    }

    @Override
    public List<Writer> getAll() {
        try (Session session = HibernateWorker.getSessionFactory().openSession()) {
            Query query = session.createQuery("from Writer");
            List<Writer> writers = query.getResultList();
            writers = (writers == null || writers.isEmpty())
                    ? Collections.emptyList()
                    : writers;
            return writers;
        }
    }
}
