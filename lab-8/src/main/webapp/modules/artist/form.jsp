<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
        <form name="artistForm" action="${pageContext.request.contextPath}/index.jsp">
            <label>Name
                <input name="artistName" type="text">
            </label>
            <button type="submit">Отправить</button>
            <button type="reset">Сбросить</button>
        </form>
    </div>
</main>
<footer class="app-footer">
    <jsp:include page="../../static/footer.jsp"/>
</footer>
</body>
</html>
