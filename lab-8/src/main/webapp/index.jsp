<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page errorPage="not-found.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <link href="src/assets/styles.css" rel="stylesheet">
    <title>JSP - Hibernate assistance</title>
</head>
<body>
<header class="app-header">
    <%@:include file="jsf/header.jsf" %>
</header>
<main class="app-main">
    <div class="app-main__inner">
        <h1></h1>
        <br/>
        <a href="${pageContext.request.contextPath}/test">Hibernate assistance</a>
    </div>
</main>
<footer class="app-footer">
    <jsp:include page="jsf/footer.jsp"/>
</footer>
</body>
</html>