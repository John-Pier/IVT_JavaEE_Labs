<%@ page import="entities.Artist" %>
<%@ page import="dao.ArtistDAO" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    int id = 0;
    boolean isIdExist = false;
    Artist artist = null;
    ArtistDAO artistDAO = new ArtistDAO();
    if (request.getParameter("id") != null) {
        id = Integer.parseInt(request.getParameter("id"));
        isIdExist = true;
        artist = artistDAO.getById(id);
    }
%>
<html>
<head>
    <title>Artist Form</title>
    <link href="${pageContext.request.contextPath}/src/assets/styles.css" rel="stylesheet">
</head>
<body>
<header class="app-header">
    <jsp:include page="../../static/header.jsp"/>
</header>
<main class="app-main">
    <div class="app-main__inner">
        <h1>Artist Create/Edit form</h1>
        <form name="artistForm" action="">
            <%
                if(isIdExist) {
                  out.print("<input name=\"id\" value=\"" + id + "\" hidden>");
                }
            %>
            <label>Name
                <input name="name" type="text" required value="<%=isIdExist ? artist.getName() : ""%>">
            </label>
            <button id="artistFormSubmit" type="submit" onclick="onSubmit(event)"><%=isIdExist ? "Update" : "Create"%></button>
        </form>
        <a href="view.jsp">Back to table view</a>
        <script>
            const form = document.forms.namedItem("artistForm");
            form.addEventListener("submit", ev => {
                ev.preventDefault();
                const urlParams = "?<%= isIdExist ? "id=" + id + "&" : ""%>name=" + form.elements.namedItem("name").value;
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
