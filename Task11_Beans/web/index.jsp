<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="my_tags" prefix="my"%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" errorPage="error.jsp" %>

<html>
<head>
    <my:bundle scripts="jquery-2.1.4.min.js;main.js" styles="style.css"/>
    <title>Кіраванне складам</title>
</head>
<body>

<table id="items" title="Кіраванне складам" border="1" cellpadding="5" cellspacing="0">
    <%@ include file="content/table-header.jsp"%>
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
                <form name="remove" action="/main/RemoveItem" method="post">
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

</body>
</html>
