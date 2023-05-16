<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<body>
<table border="1">
    <tr>
        <th>Course Name</th>
        <th>Start Week</th>
        <th>End Week</th>
        <th>Week Day</th>
        <th>School Time</th>
        <th>Classroom Number</th>
        <th>Detail</th>
    </tr>
    <!-- 遍历课程并显示 -->
    <c:forEach var="i" begin="0" end="${fn:length(coursesList)-1}">
        <tr>
            <td>${coursesList[i].name}</td>
            <td>${coursesList[i].startWeek}</td>
            <td>${coursesList[i].endWeek}</td>
            <td>${coursesList[i].weekDay}</td>
            <td>${coursesList[i].schooltime}</td>
            <td>${classroomsList[i].number}</td>
            <td>${coursesList[i].detail}</td>
        </tr>
    </c:forEach>

    </table>
    <!-- 添加导航链接 -->
    <div class="pagination">
        <a href="?page=1">First</a>
        <c:forEach var="i" begin="1" end="${totalPageNumber}">
            <a href="?page=${i}">${i}</a>
        </c:forEach>
        <a href="?page=${totalPageNumber}">Last</a>
    </div>
</body>
</html>