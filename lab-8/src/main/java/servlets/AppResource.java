package servlets;

import javax.ws.rs.*;

@Path("/app-world")
public class AppResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }

    @DELETE
    public void deleteAlbum() {
        System.out.println("deleteAlbum");
    }
}