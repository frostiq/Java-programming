<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <link rel="stylesheet" href="style.css">
  <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
  <title>Шаг 1</title>
</head>
<body>
  <h1>Шаг 1</h1>
  <p>Введите текст для шифрования:</p>
  <form action="handler" method="post">
    <input type="hidden" name="stageNumber" value="1">
    <input type="text" name="text" value="${sessionScope.text}">
    <input type="submit" value="Далее">
  </form>
  <p>${requestScope.message}</p>
</body>
</html>