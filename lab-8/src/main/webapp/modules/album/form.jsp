<%@ page import="java.util.List" %>
<%@ page import="dao.*" %>
<%@ page import="entities.*" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<%
    int id = 0;
    boolean isIdExist = false;
    Album album = null;
    int artistId = 0;
    ArtistDAO artistDAO = new ArtistDAO();
    AlbumDAO albumDAO = new AlbumDAO();
    List<Artist> artists = artistDAO.getAll();
    if (request.getParameter("id") != null) {
        id = Integer.parseInt(request.getParameter("id"));
        isIdExist = true;
        album = albumDAO.getById(id);
        artistId = album.getArtist().getId();
    }
%>
<html>
<head>
    <title>Album Form</title>
    <link href="${pageContext.request.contextPath}/src/assets/styles.css" rel="stylesheet">
</head>
<body>
<header class="app-header">
    <jsp:include page="../../static/header.jsp"/>
</header>
<main class="app-main">
    <div class="app-main__inner">
        <h1>Album Create/Edit form</h1>
        <form name="albumForm" action="view.jsp">
            <label <%=!isIdExist ? "hidden" : ""%> > ID
                <input readonly name="id" value="<%=isIdExist ? id : ""%>" <%=!isIdExist ? "hidden" : ""%> >
            </label>
            <label> Name
                <input name="name" type="text" required value="<%=isIdExist ? album.getName() : ""%>">
            </label>
            <label> Genre
                <input name="genre" type="text" value="<%=isIdExist ? album.getGenre() : ""%>">
            </label>
            <label> Artist
                <select name="artistId" required>
                    <%
                        for (Artist current : artists) {
                    %>
                    <option <%=isIdExist & current.getId() == artistId ? "selected" : ""%> value="<%=current.getId()%>">ID: <%=current.getId()%>, Name: <%=current.getName()%></option>
                    <%  }%>
                </select>
            </label>
            <button type="submit">Отправить</button>
        </form>
        <a href="view.jsp">Back to table view</a>
        <script>
            const form = document.forms.namedItem("albumForm");
            form.addEventListener("submit", ev => {
                ev.preventDefault();
                const nameValue = form.elements.namedItem("name").value;
                const genreValue = form.elements.namedItem("genre").value;
                const artistIdValue = form.elements.namedItem("artistId").value;
                const urlParams = "?<%= isIdExist ? "id=" + id + "&" : ""%>name=" + nameValue +
                    (genreValue ? "&genre=" + genreValue : "") +
                    "&artistId=" + artistIdValue;
                fetch("${pageContext.request.contextPath}/albums" + urlParams, {
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
