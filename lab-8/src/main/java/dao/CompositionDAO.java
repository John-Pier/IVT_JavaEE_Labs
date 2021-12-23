package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.query.Query;
import untils.HibernateSessionFactoryUtil;

import java.util.List;

public class CompositionDAO extends AbstractEntityDAO<Composition> {
    @Override
    protected List<Composition> getAllEntitiesList(Session session) {
        return session.createQuery("from compositions").list();
    }

    @Override
    protected Query<Composition> getUniqueQueryById(int id, Session session) {
        Query query = session.createQuery("from compositions where id=:id");
        query.setParameter("id", id);
        return query;
    }

    public int deleteByIdNative(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("delete from compositions as c where c.id =:id");
            query.setParameter("id", id);
            int i = query.executeUpdate();
            session.getTransaction().commit();
            return i;
        }
    }

    public List<Composition> getByAlbumId(int albumId) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from compositions where album.id =:id");
            query.setParameter("id", albumId);
            List<Composition> list = query.getResultList();
            session.getTransaction().commit();
            return list;
        }
    }

    public List<Composition> getByAlbumName(String albumName) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from compositions where album.name =:name");
            query.setParameter("name", albumName);
            List<Composition> list = query.getResultList();
            session.getTransaction().commit();
            return list;
        }
    }
}
