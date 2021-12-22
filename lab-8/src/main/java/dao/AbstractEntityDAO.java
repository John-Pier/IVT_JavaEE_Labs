package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import untils.HibernateSessionFactoryUtil;

import java.util.List;

public abstract class AbstractEntityDAO<E extends EntityMarker> {

    public void save(E entity) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(entity);
            session.getTransaction().commit();
        }
    }

    public List<E> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<E> entitiesList = getAllEntitiesList(session);
            System.out.println(entitiesList.size());
            session.getTransaction().commit();
            return entitiesList;
        }
    }

    protected abstract List<E> getAllEntitiesList(Session session);

    public void update(E entity) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(entity);
            session.getTransaction().commit();
        }
    }

    public E getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            E entity = getUniqueQueryById(id, session).uniqueResult();
            session.getTransaction().commit();
            return entity;
        }
    }

    public void deleteById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            E entity = getUniqueQueryById(id, session).uniqueResult();
            session.delete(entity);
            session.getTransaction().commit();
        }
    }

    protected abstract Query<E> getUniqueQueryById(int id, Session session);
}
