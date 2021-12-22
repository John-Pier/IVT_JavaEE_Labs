<%@ page import="entities.Composition" %>
<%@ page import="java.util.List" %>
<%@ page import="dao.CompositionDAO" %>
<%
    CompositionDAO dao = new CompositionDAO();
    List<Composition> compositionList = dao.getAll();
%>
<html>
<head>
    <title>Compositions Table</title>
    <link href="${pageContext.request.contextPath}/src/assets/styles.css" rel="stylesheet">
</head>
<body>
<header class="app-header">
    <jsp:include page="../../static/header.jsp"/>
</header>
<main class="app-main">
    <div class="app-main__inner">
        <h1>Compositions Table</h1>
        <table>
            <thead>
            <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Duration</th>
                <th>Album Name</th>
                <th></th>
                <th></th>
            </tr>
            </thead>
            <tbody>
            <%
                for (Composition current : compositionList) {
            %>
            <tr class="_hover-tap">
                <td><%=current.getId()%></td>
                <td><%=current.getName()%></td>
                <td><%=current.getDuration()%></td>
                <td><%=current.getAlbum().getName()%></td>
                <td class="_hover-tap-element">
                    <a href="form.jsp?id=<%=current.getId()%>">update</a>
                </td>
                <td class="_hover-tap-element">
                    <button>delete</button>
                </td>
            </tr>
            <% } %>
            </tbody>
        </table>
        <a href="${pageContext.request.contextPath}/">Back</a>
    </div>
</main>
<footer class="app-footer">
    <jsp:include page="../../static/footer.jsp"/>
</footer>
</body>
</html>