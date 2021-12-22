<%@ page import="entities.Album" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.*" %>
<%
    AlbumDAO dao = new AlbumDAO();
    List<Album> albumsList = dao.getAll();
%>
<html>
<head>
    <title>Artists Table</title>
    <link href="${pageContext.request.contextPath}/src/assets/styles.css" rel="stylesheet">
</head>
<body>
<header class="app-header">
    <jsp:include page="../../static/header.jsp"/>
</header>
<main class="app-main">
    <div class="app-main__inner">
        <h1>Artists Table</h1>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Genre</th>
                <th>Artist Name</th>
                <th>Composition count</th>
            </tr>
            </thead>
            <tbody>
            <%= "<tr><td>1</td><td>No name</td></tr>" %>
            <%
                for (Album current : albumsList) {
            %>
            <tr>
                <td><%=current.getId()%></td>
                <td><%=current.getName()%></td>
                <td><%=current.getGenre()%></td>
                <td><%=current.getArtist().getName()%></td>
                <td><%=current.getCompositionList().size()%></td>
            </tr>
            <% } %>
            </tbody>
        </table>
    </div>
</main>
<footer class="app-footer">
    <jsp:include page="../../static/footer.jsp"/>
</footer>
</body>
</html>