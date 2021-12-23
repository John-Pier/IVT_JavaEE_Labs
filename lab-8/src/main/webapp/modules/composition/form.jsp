<%@ page import="dao.*" %>
<%@ page import="java.util.List" %>
<%@ page import="entities.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    int id = 0;
    boolean isIdExist = false;
    Composition composition = null;
    int albumId = 0;
    CompositionDAO compositionDAO = new CompositionDAO();
    AlbumDAO albumDAO = new AlbumDAO();
    List<Album> albumList = albumDAO.getAll();
    if (request.getParameter("id") != null) {
        id = Integer.parseInt(request.getParameter("id"));
        isIdExist = true;
        composition = compositionDAO.getById(id);
        albumId = composition.getAlbum().getId();
    }
%>
<html>
<head>
    <title>Composition Form</title>
    <link href="${pageContext.request.contextPath}/src/assets/styles.css" rel="stylesheet">
</head>
<body>
<header class="app-header">
    <jsp:include page="../../static/header.jsp"/>
</header>
<main class="app-main">
    <div class="app-main__inner">
        <h1>Composition Create/Edit form</h1>
        <form name="compositionForm">
            <label> ID
                <input readonly name="id" value="<%=isIdExist ? id : ""%>" <%=!isIdExist ? "hidden" : ""%> >
            </label>
            <label> Name
                <input name="name" type="text" required value="<%=isIdExist ? composition.getName() : ""%>">
            </label>
            <label> Duration
                <input type="time" name="duration" required value="<%=isIdExist ? composition.getDuration() : ""%>">
            </label>
            <label> Album
                <select name="albumId" required>
                    <%
                        for (Album current : albumList) {
                    %>
                    <option <%=isIdExist & current.getId() == albumId ? "selected" : ""%> value="<%=current.getId()%>">ID: <%=current.getId()%>, Name: <%=current.getName()%></option>
                    <%  }%>
                </select>
            </label>
            <button type="submit"><%=isIdExist ? "Update" : "Create"%></button>
        </form>
        <a href="view.jsp">Back to table view</a>
        <script>
            const form = document.forms.namedItem("compositionForm");
            form.addEventListener("submit", ev => {
                ev.preventDefault();
                const nameValue = form.elements.namedItem("name").value;
                const durationValue = new Date(form.elements.namedItem("duration").value);
                const albumIdValue = form.elements.namedItem("albumId").value;
                const urlParams = "?<%= isIdExist ? "id=" + id + "&" : ""%>name=" + nameValue +
                    "&duration=" + durationValue.getTime() +
                    "&albumId=" + albumIdValue;
                fetch("${pageContext.request.contextPath}/compositions" + urlParams, {
                    method: "POST",
                })
                    .then(response => {
                        if(response.ok) {
                            location.href = "view.jsp"
                        }
                    });
            });
        </script>
    </div>
</main>
<footer class="app-footer">
    <jsp:include page="../../static/footer.jsp"/>
</footer>
</body>
</html>
