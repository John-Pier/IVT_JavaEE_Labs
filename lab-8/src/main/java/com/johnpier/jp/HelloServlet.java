package com.johnpier.jp;

import entities.Artist;
import org.hibernate.*;
import untils.HibernateSessionFactoryUtil;

import java.io.*;
import java.util.List;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", urlPatterns = "test")
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.println("<html><body>");

        try(Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession()) {
            Transaction transaction = session.beginTransaction();

            Artist test = new Artist();
            test.setName("test a");

            List<Artist> singers = session.createQuery("from artists").getResultList();
            for (Artist singer : singers) {
                out.println(singer.getName() + ": Artist");
            }

            session.persist(test);
            transaction.commit();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        out.println("<h1>" + message + "</h1>");
        out.println("</body></html>");
    }

    public void destroy() {
    }
}