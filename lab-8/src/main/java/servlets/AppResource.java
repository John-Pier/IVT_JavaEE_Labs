package servlets;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("/app-world")
public class AppResource {
    @GET
    @Produces("text/plain")
    public String hello() {
        return "Hello, World!";
    }
}