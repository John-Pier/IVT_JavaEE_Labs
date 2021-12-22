package dao;

import entities.Composition;
import org.hibernate.Session;
import org.hibernate.query.Query;
import untils.HibernateSessionFactoryUtil;

import java.util.List;

public class CompositionDAO {
    public void save(Composition composition) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(composition);
            session.getTransaction().commit();
        }
    }

    public List<Composition> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<Composition> list = session.createQuery("from compositions ").list();
            session.getTransaction().commit();
            return list;
        }
    }

    public void update(Composition composition) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(composition);
            session.getTransaction().commit();
        }
    }

    public Composition getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from compositions where id=:id");
            query.setParameter("id", id);
            Composition composition = (Composition) query.uniqueResult();
            session.getTransaction().commit();
            return composition;
        }
    }

    public void deleteById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from compositions where id=:id");
            query.setParameter("id", id);
            Composition composition = (Composition) query.uniqueResult();
            session.delete(composition);
            session.getTransaction().commit();
        }
    }
}
