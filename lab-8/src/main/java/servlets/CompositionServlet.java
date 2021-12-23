package servlets;

import dao.*;
import entities.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.sql.Time;

@WebServlet("/compositions")
public class CompositionServlet extends HttpServlet {
    CompositionDAO compositionDAO;
    AlbumDAO albumDAO;

    private final String ID = "id";
    private final String NAME = "name";
    private final String DURATION = "id";
    private final String ALBUM_ID = "albumId";

    @Override
    public void init() throws ServletException {
       compositionDAO = new CompositionDAO();
       albumDAO = new AlbumDAO();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        String name = req.getParameter(NAME);
        String duration = req.getParameter(DURATION);
        String albumId = req.getParameter(ALBUM_ID);
        if(name == null | duration == null | albumId == null) {
            resp.sendError(500, "Error!");
            return;
        }

        Album selectedAlbum = albumDAO.getById(Integer.parseInt(albumId));
        if(selectedAlbum == null) {
            resp.sendError(500, "Error! Bed albumId!");
            return;
        }

        Composition composition = new Composition();
        composition.setName(name);
        composition.setDuration(new Time(Long.getLong(duration, 0)));
        composition.setAlbum(selectedAlbum);

        PrintWriter printWriter = resp.getWriter();
        if(req.getParameter(ID) != null) {
            // Update section
            int id = Integer.parseInt(req.getParameter("id"));
            composition.setId(id);
            compositionDAO.update(composition);
            printWriter.print("Entity with id" + id + "successfully edited");
        } else {
            // Create section
            compositionDAO.save(composition);
            printWriter.print("Entity successfully created");
        }
        printWriter.close();
        resp.setStatus(200);
        resp.sendRedirect("/modules/artist/r");
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idParam = req.getParameter(ID);
        if (idParam == null) {
            resp.sendError(500, "Error! No correct id!");
            return;
        }
        try {
            int id = Integer.parseInt(idParam);
            compositionDAO.deleteById(id);

            PrintWriter printWriter = resp.getWriter();
            printWriter.print("Entity with id" + id + "successfully deleted");
            printWriter.close();
        } catch (Exception ex) {
            resp.sendError(500, "Error! No correct id!");
        }
        resp.setContentType("text/html");
        resp.setStatus(200);
    }
}
