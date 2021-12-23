import entities.*;
import org.hibernate.*;
import org.hibernate.query.Query;
import untils.HibernateSessionFactoryUtil;

import java.sql.*;
import java.util.*;

public class HibernateTest {
    public static void main(String[] args) {
        //testTableDataCreation();
       // printTablesData();
        testCrudForArtist();
    }

    private static void testTableDataCreation() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            double testIdNumber = getRandomNumber();

            Artist test = new Artist();
            test.setName("Artist " + testIdNumber);
            session.persist(test);

            Album album = new Album();
            album.setName("album a" + testIdNumber);
            album.setGenre("Rock");
            album.setArtist(test);
            session.persist(album);

            Composition composition = new Composition();
            composition.setName("composition a" + testIdNumber);
            composition.setDuration(new Time(Math.round(Math.random() * 170)));
            composition.setAlbum(album);
            session.persist(composition);

            session.flush();
            transaction.commit();
        }
    }

    private static void testCrudForArtist() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {

            Transaction transaction = session.beginTransaction();
            String name = "Artist " + getRandomNumber();
            String newName = "New Artist " + getRandomNumber();
            Artist artist = new Artist();
            artist.setName(name);
            session.save(artist);
            transaction.commit();
            printArtistData();

            transaction = session.beginTransaction();
            Query query = session.createQuery("from artists where name=:name");
            query.setParameter("name", name);

            Artist result = (Artist) query.uniqueResult();
            result.setName(newName);
            session.update(artist);
            transaction.commit();
            printArtistData();

            transaction = session.beginTransaction();
            query = session.createQuery("from artists where name=:name");
            query.setParameter("name", newName);
            session.delete(query.uniqueResult());
            transaction.commit();
            printArtistData();
        }
    }


    private static void printTablesData() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            List<Artist> singers = session.createQuery("from artists").getResultList();
            for (Artist singer : singers) {
                System.out.println(singer.getName() + ": Artist");
            }
        }
    }

    private static void printArtistData() {
        try (Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            printArtistTable(session.createQuery("from artists").getResultList());
        }
    }

    private static void printArtistTable(List<Artist> artists) {
        System.out.format("%8s\t%18s\n", "ID", "Name");
        System.out.format(
                "%8s\t%18s\n",
                "________",
                "__________________"
        );
        for (Artist artist : artists) {
            System.out.format(
                    "%8s\t%18s\n",
                    artist.getId(),
                    artist.getName()
            );
            System.out.println();
        }
    }


    private static double getRandomNumber() {
        return Math.random() * 100;
    }
}
