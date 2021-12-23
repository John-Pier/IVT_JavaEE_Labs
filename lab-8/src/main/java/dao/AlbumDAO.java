package dao;

import entities.Album;
import org.hibernate.Session;
import org.hibernate.query.Query;
import untils.HibernateSessionFactoryUtil;

import java.util.List;

public class AlbumDAO extends AbstractEntityDAO<Album> {

    public void deleteByName(String name) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from albums where name=:name");
            query.setParameter("name", name);
            Album album = (Album) query.uniqueResult();
            session.delete(album);
            session.getTransaction().commit();
        }
    }

    @Override
    protected List<Album> getAllEntitiesList(Session session) {
        return session.createQuery("from albums").list();
    }

    @Override
    protected Query<Album> getUniqueQueryById(int id, Session session) {
        Query query = session.createQuery("from albums where id=:id");
        query.setParameter("id", id);
        return query;
    }


    public List<Object[]> getAlbumsWithMinDurations() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("select a.name, MIN(c.duration) " +
                    "from albums as a join a.compositionList as c " +
                    "group by a.id "
            );
            List<Object[]> list = query.getResultList();
            session.getTransaction().commit();
            return list;
        }
    }
}
