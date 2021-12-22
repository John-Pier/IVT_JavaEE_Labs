<%@ page import="entities.Composition" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.CompositionDAO" %>
<%
    CompositionDAO dao = new CompositionDAO();
    List<Composition> compositionList = dao.getAll();
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
                <th>Duration</th>
                <th>Album Name</th>
            </tr>
            </thead>
            <tbody>
            <%= "<tr><td>1</td><td>No name</td></tr>" %>
            <%
                for (Composition current : compositionList) {
            %>
            <tr>
                <td><%=current.getId()%></td>
                <td><%=current.getName()%></td>
                <td><%=current.getDuration()%></td>
                <td><%=current.getAlbum().getName()%></td>
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