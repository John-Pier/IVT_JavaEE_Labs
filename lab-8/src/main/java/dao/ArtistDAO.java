package dao;

import entities.Artist;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.util.List;

public class ArtistDAO extends AbstractEntityDAO<Artist> {
    @Override
    protected List<Artist> getAllEntitiesList(Session session) {
        return session.createQuery("from artists").list();
    }

    @Override
    protected Query<Artist> getUniqueQueryById(int id, Session session) {
        Query query = session.createQuery("from artists where id=:id");
        query.setParameter("id", id);
        return query;
    }
}
