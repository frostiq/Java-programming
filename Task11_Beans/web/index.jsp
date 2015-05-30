<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>
<head>
    <script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="/style.css">
    <title>Кіраванне складам</title>
</head>
<body>

<table title="Кіраванне складам">
    <tr>
        <td>Id</td>
        <td>Назва</td>
        <td>Кошт</td>
        <td>Колькасьць</td>
        <td>Id склада</td>
        <td>Назва склада</td>
    </tr>
    <c:forEach var="item" items="${requestScope.items}">
        <tr id="${item.id}">
            <td>${item.id}
            </td>
            <td>${item.name}
            </td>
            <td>${item.price}
            </td>
            <td>${item.quantity}
            </td>
            <td>
                <c:choose>
                    <c:when test="${not empty item.storage}">
                        ${item.storage.id}
                    </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <c:choose>
                    <c:when test="${not empty item.storage}">
                        ${item.storage.name}
                    </c:when>
                    <c:otherwise>
                        -
                    </c:otherwise>
                </c:choose>
            </td>
            <td>
                <form action="/main/RemoveItem" method="post">
                    <input type="hidden" name="id" value="${item.id}">
                    <input type="submit" value="Выдаліць">
                </form>
            </td>
            <td><input type="button" onclick="edit(${item.id})" value="Рэдагаваць">
            </td>
        </tr>
    </c:forEach>
</table>

<form action="/main/CreateEmptyItem" method="post">
    <input type="submit" value="Дадаць новую рэч">
</form>

<form action="/main">
    <input name="filter" type="text">
    <input type="submit" value="Знайсьці па назве">
</form>

<form id="edit" action="/main/UpdateItem" method="post">
    <table>
        <tr>
            <td>Назва</td>
            <td>Кошт</td>
            <td>Колькасьць</td>
            <td>Id склада</td>
        </tr>
        <tr>
            <td><input name="name" type="text"></td>
            <td><input name="price" type="text"></td>
            <td><input name="quantity" type="text"></td>
            <td><input name="storageId" type="text"></td>
        </tr>
    </table>
    <input type="hidden" name="id" value="">
    <input type="submit" value="Захаваць">
</form>

<script>
    function edit(id) {
        var rows = $('tr#' + id).children();
        var edit = $('form#edit');
        edit.find('[name=id]').val(rows[0].innerText);
        edit.find('[name=name]').val(rows[1].innerText);
        edit.find('[name=price]').val(rows[2].innerText);
        edit.find('[name=quantity]').val(rows[3].innerText);
        edit.find('[name=storageId]').val(rows[4].innerText);

        edit.show();
    }
</script>
</body>
</html>
