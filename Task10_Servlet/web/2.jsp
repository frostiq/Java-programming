<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
    <title>Шаг 2</title>
</head>
<body>
<h1>Шаг 2</h1>

<p>Введите ключ для шифрования:</p>

<form action="handler" method="post">
    <input type="hidden" name="stageNumber" value="2">
    <input type="text" name="key" value="${sessionScope.key}">
    <input type="submit" value="Далее"/>
</form>
<form action="1.jsp" method="post">
    <input type="submit" value="Назад"/>
</form>
<p>${requestScope.message}</p>
</body>
</html>