<%@ page import="java.util.List" %>
<%@ page import="dao.*" %>
<%@ page import="entities.*" %>
<%
    AlbumDAO dao = new AlbumDAO();
    CompositionDAO compositionDAO = new CompositionDAO();
    List<Album> albumsList = dao.getAll();
%>
<html>
<head>
    <title>Albums Table</title>
    <link href="${pageContext.request.contextPath}/src/assets/styles.css" rel="stylesheet">
</head>
<body>
<header class="app-header">
    <jsp:include page="../../static/header.jsp"/>
</header>
<main class="app-main">
    <div class="app-main__inner">
        <h1>Albums Table</h1>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Genre</th>
                <th>Artist Name</th>
                <th>Composition count</th>
                <th></th>
                <th>
                    <a href="form.jsp">Add New</a>
                </th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Album current : albumsList) {
                    List<Composition> compositionList = compositionDAO.getByAlbumId(current.getId());
            %>
            <tr class="_hover-tap" id="<%=current.getId()%>">
                <td>
                    <%=current.getId()%>
                </td>
                <td>
                    <%=current.getName()%>
                </td>
                <td>
                    <%=current.getGenre()%>
                </td>
                <td>
                    <%=current.getArtist().getName()%>
                </td>
                <td>
                    <%=compositionList != null ? compositionList.size() : 0%>
                </td>
                <td class="_hover-tap-element">
                    <a href="form.jsp?id=<%=current.getId()%>">Update</a>
                </td>
                <td class="_hover-tap-element">
                    <button onclick="onDeleteClick(<%=current.getId()%>)">Delete</button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/">Back</a>
        <script>
            function onDeleteClick(id) {
                fetch("${pageContext.request.contextPath}/albums?id=" + id, {
                    method: "delete",
                }).then(it => {
                    if (it.ok) {
                        document.getElementById(id)?.remove();
                    }
                });
            }
        </script>
    </div>
</main>
<footer class="app-footer">
    <jsp:include page="../../static/footer.jsp"/>
</footer>
</body>
</html>