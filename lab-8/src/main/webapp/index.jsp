<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <link href="src/assets/styles.css" rel="stylesheet">
    <title>JSP - Hibernate assistance</title>
</head>
<body>
<header>
    <jsp:include page="header.jsp"/>
</header>
<main>
    <h1><%= "Hello World!" %>
    </h1>
    <br/>
    <a href="${pageContext.request.contextPath}/test">Hibernate assistance</a>
</main>
<footer>
    <jsp:include page="footer.jsp"/>
</footer>
</body>
</html>