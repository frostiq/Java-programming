<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Deals</title>
</head>
<body>
<h3>Choose deal type:</h3>
<p>
<form action="/main" method="post">
    <c:forEach var="dealType" items="${requestScope.dealTypes}">
        <input type="radio" name="dealType" value="${dealType.id}"> ${dealType.name} <br/>
    </c:forEach>
    <input type="submit">
</form>
</p>

</body>
</html>
