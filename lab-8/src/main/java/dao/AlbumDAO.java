package dao;

import entities.Album;
import org.hibernate.Session;
import org.hibernate.query.Query;
import untils.HibernateSessionFactoryUtil;

import java.util.List;

public class AlbumDAO {
    public void save(Album album) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.save(album);
            session.getTransaction().commit();
        }
    }

    public List<Album> getAll() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            List<Album> list = session.createQuery("from albums").list();
            session.getTransaction().commit();
            return list;
        }
    }

    public void update(Album album) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            session.update(album);
            session.getTransaction().commit();
        }
    }

    public Album getById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from albums where id=:id");
            query.setParameter("id", id);
            Album album = (Album) query.uniqueResult();
            session.getTransaction().commit();
            return album;
        }
    }

    public void deleteById(int id) {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();
            Query query = session.createQuery("from albums where id=:id");
            query.setParameter("id", id);
            Album album = (Album) query.uniqueResult();
            session.delete(album);
            session.getTransaction().commit();
        }
    }

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

//    public List<Album> query1(String name){
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            Query query = session.createQuery("from albums where artist.name = :name");
//            query.setParameter("name", name);
//            List<Album> list = query.list();
//            session.getTransaction().commit();
//            return list;
//        }
//    }
//
//    public List<Album> query2(String name){
//        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
//            session.beginTransaction();
//            Query query = session.createQuery("from compositions where * =:name ");
//            query.setParameter("name", name);
//            List<Album> list = query.list();
//            session.getTransaction().commit();
//            return list;
//        }
//    }
}
