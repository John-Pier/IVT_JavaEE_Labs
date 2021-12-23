<%@ page import="java.util.*" %>
<%@ page import="dao.*" %>
<%@ page import="entities.*" %>
<%@ page import="java.sql.Time" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String idParam = request.getParameter("id");
    int albumId = 0;
    boolean isAlbumIdSelected = idParam != null;
    List<Composition> compositionList;
    CompositionDAO dao = new CompositionDAO();
    AlbumDAO albumDAO = new AlbumDAO();
    Album album = null;
    if(isAlbumIdSelected) {
        albumId = Integer.parseInt(idParam);
        album = albumDAO.getById(albumId);
        compositionList = dao.getByAlbumName(album.getName());
    } else {
        compositionList = new ArrayList<>();
    }
%>
<!DOCTYPE html>
<html>
<head>
    <link href="src/assets/styles.css" rel="stylesheet">
    <title>JSP - Hibernate assistance</title>
</head>
<body>
<header class="app-header">
    <jsp:include page="static/header.jsp"/>
</header>
<main class="app-main">
    <div class="app-main__inner">
        <h1>Queries</h1>
        <div class="app-main__block">
            <h2>Album Compositions</h2>
            <form name="albumCompositionsForm" action="index.jsp" method="get">
                <label> Album
                    <input value="<%=isAlbumIdSelected ? idParam : ""%>" name="id" type="search" placeholder="Type album id">
                </label>
            </form>
            <p>
                Album selected:&nbsp;
                <span style="font-weight: 700">
                    <%=isAlbumIdSelected ? album.getName() : ""%>
                </span>
            </p>
            <table>
                <thead>
                <tr>
                    <th>ID</th>
                    <th>Name</th>
                    <th>Duration</th>
                </tr>
                </thead>
                <tbody>
                <%
                    for(Composition current : compositionList){
                %>
                <tr>
                    <td colspan=""><%=current.getId()%></td>
                    <td><%=current.getName()%></td>
                    <td><%=current.getDuration()%></td>
                </tr>
                <%   }
                    if (compositionList.size() == 0) {
                        out.print("<tr><td colspan=\"3\">No values</td></tr>");
                    }
                %>
                </tbody>
            </table>
        </div>
        <div class="app-main__block">
            <h2>Albums with min composition duration</h2>
            <div>
                <table>
                    <thead>
                    <tr>
                        <th>Name</th>
                        <th>Duration</th>
                    </tr>
                    </thead>
                    <tbody>
                    <%
                        List<Object[]> resultQuery = albumDAO.getAlbumsWithMinDurations();
                        for(Object[] arr : resultQuery) {
                            String albumName = (String) arr[0];
                            Time duration = (Time)arr[1];
                    %>
                        <tr>
                            <td><%=albumName%></td>
                            <td><%=duration%></td>
                        </tr>
                    <% }%>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</main>
<footer class="app-footer">
    <jsp:include page="static/footer.jsp"/>
</footer>
</body>
</html>