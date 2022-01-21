package servlets;

import dao.ArtistDAO;
import entities.Artist;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.*;

public class ArtistServlet extends HttpServlet {
    private final String ID = "id";
    private final String NAME = "name";

    private ArtistDAO artistDao;

    @Override
    public void init() throws ServletException {
        artistDao = new ArtistDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        String name = req.getParameter(NAME);
        if(name == null) {
            resp.sendError(500, "Error!");
            return;
        }

        PrintWriter printWriter = resp.getWriter();
        if(req.getParameter(ID) != null) {
            // Update section
            int id = Integer.parseInt(req.getParameter(ID));
            Artist artist = new Artist();
            artist.setId(id);
            artist.setName(name);
            artistDao.update(artist);
            printWriter.print("Entity with id" + id + "successfully edited");
        } else {
            // Create section
            Artist artist = new Artist();
            artist.setName(name);
            artistDao.save(artist);
            printWriter.print("Entity successfully created");
        }
        printWriter.close();
        resp.setStatus(200);
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
            int id = Integer.parseInt(req.getParameter(ID));
            artistDao.deleteById(id);
            PrintWriter printWriter = resp.getWriter();
            printWriter.print("Entity with id" + id + "successfully deleted");
            printWriter.close();
            resp.setStatus(200);
        } catch (Exception ex) {
            resp.sendError(500, "Error!");
        }
    }
}
