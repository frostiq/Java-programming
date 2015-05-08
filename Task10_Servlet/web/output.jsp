<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head lang="en">
    <link rel="stylesheet" href="style.css">
    <meta charset="UTF-8">
    <title>Результат</title>
</head>
<body>
<p>Результат:</p>
<div class="result">
    ${requestScope.result}
</div>
<br>

<form action="2.jsp" method="post">
    <input type="submit" value="Назад"/>
</form>
<form action="handler" method="post">
    <input type="submit" value="Заново"/>
</form>
</body>
</html>