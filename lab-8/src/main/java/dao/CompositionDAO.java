package dao;

import entities.*;
import org.hibernate.Session;
import org.hibernate.query.Query;

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
}
