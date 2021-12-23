package servlets;

import com.google.gson.Gson;
import dao.*;
import entities.*;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@WebServlet("/albums")
public class AlbumServlet extends HttpServlet {
    ArtistDAO artistDAO;
    AlbumDAO albumDAO;

    private final String ID = "id";
    private final String NAME = "name";
    private final String GENRE = "genre";
    private final String ARTIST_ID = "artistId";

    @Override
    public void init() throws ServletException {
        artistDAO = new ArtistDAO();
        albumDAO = new AlbumDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter printWriter = resp.getWriter();
        List<ShortEntity> objects = albumDAO.getAll().stream()
                .map(album -> new ShortEntity(album.getId(), album.getName()))
                .collect(Collectors.toList());
        String json = new Gson().toJson(objects);
        System.out.println(json);
        printWriter.print(json);
        printWriter.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/json");
        String name = req.getParameter(NAME);
        String genre = req.getParameter(GENRE);
        String artistIdParam = req.getParameter(ARTIST_ID);
        if(name == null | artistIdParam == null) {
            resp.sendError(500, "Error!");
            return;
        }

        Artist selectedArtist = artistDAO.getById(Integer.parseInt(artistIdParam));
        if(selectedArtist == null) {
            resp.sendError(500, "Error! Bed artistId!");
            return;
        }

        Album album = new Album();
        album.setName(name);
        album.setGenre(genre);
        album.setArtist(selectedArtist);

        PrintWriter printWriter = resp.getWriter();
        if(req.getParameter(ID) != null) {
            // Update section
            int id = Integer.parseInt(req.getParameter("id"));
            album.setId(id);
            albumDAO.update(album);
            printWriter.print("Entity with id" + id + "successfully edited");
        } else {
            // Create section
            albumDAO.save(album);
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
            albumDAO.deleteById(id);

            PrintWriter printWriter = resp.getWriter();
            printWriter.print("Entity with id" + id + "successfully deleted");
            printWriter.close();
        } catch (Exception ex) {
            resp.sendError(500, "Error! No correct id!");
        }
        resp.setContentType("text/html");
        resp.setStatus(200);
    }

    private class ShortEntity {
        private int id;
        private String name;

        ShortEntity(int id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
