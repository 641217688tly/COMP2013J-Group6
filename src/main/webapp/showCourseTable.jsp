<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- 引入 JSTL 标准标签库的核心标签 -->
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<body>
<table border="1"> <!-- 创建一个有边框的表格 -->
    <tr> <!-- 创建表头 -->
        <th>Course Name</th>
        <th>Start Week</th>
        <th>End Week</th>
        <th>Week Day</th>
        <th>School Time</th>
        <th>Classroom Number</th>
        <th>Detail</th>
    </tr>
    <!-- 使用 JSTL 的 forEach 标签遍历课程列表 -->
    <c:forEach var="i" begin="0" end="${coursesList.size()-1}">
        <tr> <!-- 对于每一个课程，创建一行 -->
            <!-- 课程名称现在是一个指向 update.jsp 的链接，并将课程 id 作为请求参数 -->
            <td><a href="update.jsp?courseId=${coursesList[i].id}&classroomId=${classroomsList[i].id}">${coursesList[i].name}</a></td>
            <td>${coursesList[i].startWeek}</td>
            <td>${coursesList[i].endWeek}</td>
            <td>${coursesList[i].weekDay}</td>
            <td>${coursesList[i].schooltime}</td>
            <td>${classroomsList[i].number}</td>
            <td>${coursesList[i].detail}</td>
        </tr>
    </c:forEach>

</table>
<!-- 创建分页导航链接 -->
<div class="pagination">
    <a href="?page=1">First</a> <!-- 链接到第一页 -->
    <!-- 遍历所有的页码 -->
    <c:forEach var="i" begin="1" end="${totalPageNumber}">
        <a href="?page=${i}">${i}</a> <!-- 创建一个链接到对应页码的链接 -->
    </c:forEach>
    <a href="?page=${totalPageNumber}">Last</a> <!-- 链接到最后一页 -->
</div>



<a href="insert.jsp"><h2>上传课程信息</h2></a><br>
<a href="searchClassroomServlet"><h2>查看教室信息</h2></a>
</body>
</html>
