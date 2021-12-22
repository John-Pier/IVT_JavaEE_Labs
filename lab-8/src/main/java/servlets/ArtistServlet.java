package servlets;

import dao.ArtistDAO;
import entities.Artist;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;

@WebServlet(value = "/artists", name = "ArtistServlet")
public class ArtistServlet extends HttpServlet {

    private ArtistDAO artistDao;

    @Override
    public void init() throws ServletException {
        artistDao = new ArtistDAO();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        out.print("<html><body>");
        out.print("<h3>Hello Servlet</h3>");
        out.print("</body></html>");
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        String name = req.getParameter("name");
        if(name == null) {
            resp.sendError(500, "Error!");
            return;
        }

        if(req.getParameter("id") != null) {
            // Update section
            int id = Integer.parseInt(req.getParameter("id"));
            Artist artist = new Artist();
            artist.setId(id);
            artist.setName(name);
            artistDao.update(artist);
        } else {
            // Create section
            Artist artist = new Artist();
            artist.setName(name);
            artistDao.save(artist);
        }
        resp.setStatus(200);
        resp.sendRedirect("/modules/artist/r");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("doDelete!");
        try {
            resp.setContentType("text/html");
            int id = Integer.parseInt(req.getParameter("id"));
            artistDao.deleteById(id);
            resp.setStatus(200);
        } catch (Exception ex) {
            resp.sendError(500, "Error!");
        }
    }
}