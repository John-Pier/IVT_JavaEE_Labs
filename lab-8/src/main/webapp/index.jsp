<%@ page import="java.util.*" %>
<%@ page import="dao.*" %>
<%@ page import="entities.*" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%
    String idParam = request.getParameter("id");
    int albumId = 0;
    boolean isAlbumIdSelected = idParam != null;
    List<Composition> compositionList = new ArrayList<>();
    CompositionDAO dao = new CompositionDAO();
    AlbumDAO albumDAO = new AlbumDAO();
    Album album = null;
    if (isAlbumIdSelected) {
        try {
            albumId = Integer.parseInt(idParam);
            album = albumDAO.getById(albumId);
            compositionList = dao.getByAlbumName(album.getName());
        } catch (Exception e) {
            isAlbumIdSelected = false;
            response.sendRedirect("/");
        }
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
                    <select id="albumId" name="id" required>
                        <option selected>Loading...</option>
                    </select>
                    <input type="submit">
                    <script>
                        const form = document.forms.namedItem("albumCompositionsForm");
                        const selectContainer = document.getElementById("albumId");
                        const selectedId = <%=isAlbumIdSelected ? idParam : ""%>

                            fetch("${pageContext.request.contextPath}/albums", {
                                method: "GET",
                            })
                                .then(response => response.json())
                                .then(albums => {
                                    selectContainer.innerHTML = "";
                                    selectContainer.append(...albums.map(album => {
                                        const option = document.createElement("option");
                                        option.setAttribute("value", album["id"]);
                                        if (selectedId) {
                                            option.setAttribute("selected", "");
                                        }
                                        option.textContent = "Name: " + album["name"] + ", Id: " + album["id"];
                                        return option;
                                    }))
                                });
                    </script>
                </label>
            </form>
            <p>
                Album selected:&nbsp;
                <span style="font-weight: 700">
                    <%=isAlbumIdSelected ? album.getName() : "-"%>
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
                    for (Composition current : compositionList) {
                %>
                <tr>
                    <td colspan=""><%=current.getId()%>
                    </td>
                    <td><%=current.getName()%>
                    </td>
                    <td><%=current.getDuration()%>
                    </td>
                </tr>
                <% }
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
                    <tbody id="albumsWithMinDurations">
                        <tr>
                            <td colspan="2">Loading ....</td>
                        </tr>
                    </tbody>
                    <script>
                        const tableBodyContainer = document.getElementById("albumsWithMinDurations");
                        fetch("${pageContext.request.contextPath}/albums?minDurations=true", {
                            method: "GET",
                        })
                            .then(response => response.json())
                            .then(values => {
                                tableBodyContainer.innerHTML = "";
                                tableBodyContainer.append(...values.map(value => {
                                    const tr = document.createElement("tr");
                                    tr.innerHTML = "<td>" + value[0] + "</td><td>" + value[1] + "</td>";
                                    return tr;
                                }))
                            });
                    </script>
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