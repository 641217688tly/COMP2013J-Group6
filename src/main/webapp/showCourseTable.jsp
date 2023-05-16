<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Show Course Table</title>
</head>
<body>
<table>
    <tr>
        <th>Course Id</th>
        <th>Course Name</th>
        <th>Start Week</th>
        <th>End Week</th>
        <th>Week Day</th>
        <th>School Time</th>
    </tr>
    <%-- Iterate over the courses and create a table row for each one --%>
    <c:forEach var="course" items="${courses}">
        <tr>
            <td>${course.id}</td>
            <td>${course.name}</td>
            <td>${course.startWeek}</td>
            <td>${course.endWeek}</td>
            <td>${course.weekDay}</td>
            <td>${course.schoolTime}</td>
        </tr>
    </c:forEach>
</table>

<%-- Links to previous and next pages --%>
<a href="showCourseTableServlet?page=${page - 1}">Previous</a>
<a href="showCourseTableServlet?page=${page + 1}">Next</a>
</body>
</html>


