<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Deals of <c:out value="${requestScope.dealType.name}"/> type</title>
</head>
<body>

<h3>Deals of <c:out value="${requestScope.dealType.name}"/> type</h3>

<table border="2">
  <tr>
    <td><fmt:message key="id" bundle="${lang}"/></td>
    <td><fmt:message key="name" bundle="${lang}"/></td>
    <td><fmt:message key="date" bundle="${lang}"/></td>
    <td><fmt:message key="amount" bundle="${lang}"/></td>
  </tr>
  <c:forEach var="deal" items="${requestScope.deals}">
    <tr>
      <td><c:out value="${deal.id}"/></td>
      <td><c:out value="${deal.name}"/></td>
      <td><c:out value="${deal.date}"/></td>
      <td><c:out value="${deal.amount}"/></td>
    </tr>
  </c:forEach>
</table>

<br/>
Total amount: <c:out value="${requestScope.aggData.sum}"/>
<br/>
Count: <c:out value="${requestScope.aggData.count}"/>
<br/>

<br/>
<form action="/lang/" method="post">
  <input type="submit" title="Change language">
  <select name="lang">
    <option>en</option>
    <option>be</option>
  </select>
</form>


</body>

<style>
  table{
    border-collapse: collapse;
  }
  tr:nth-child(2n){
    background-color: black;
    color: white;
  }
</style>
</html>
