<%--
  Created by IntelliJ IDEA.
  User: flami
  Date: 18.02.2025
  Time: 21:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals Page</title>
    <style>
        .excess {
            background-color: red
        }

        .normal {
            background-color: green
        }
    </style>
</head>
<body>
<h1 style="color:brown; font-family: 'Times New Roman';font-size: 25px ">Расписание приема пищи</h1>
<br>
<table border="1">
    <tr>
        <th>Дата и время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <c:forEach var="meal" items="${meals}">
        <tr class="${meal.excess ? 'excess' : 'normal'}">
            <td>${meal.dateTime}</td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
