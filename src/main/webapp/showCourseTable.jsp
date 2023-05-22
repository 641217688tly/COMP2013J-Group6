<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
<link href="css/showCourseTable.css" rel="stylesheet">
</head>
<body class="simple-linear">

<!-- 显示链接以上传课程信息和查看课程信息 -->
<ul class="topnav">
  <li><a class="active" href="insert.jsp">上传课程信息</a></li>
  <li><a href="showClassroomTableServlet">查看教室信息</a></li>
</ul>

<form action="showCourseTableServlet" method="post" align="center" class="nextPage">
    Course Name Query: <input type="text" name="specificName" />
    <input type="submit" value="Search" color="blue" />
</form>

<table border="0" width="100%" class="tabletop"> <!-- 创建一个有边框的表格 -->
    <tr> <!-- 创建表头 -->
        <th width="10%" rowspan="2">Course Name</th>
        <th width="10%" rowspan="2">Start Week</th>
        <th width="10%" rowspan="2">End Week</th>
        <th width="10%" rowspan="2">Week Day</th>
        <th width="10%" rowspan="2">School Time</th>
        <th width="10%" rowspan="2">Classroom Number</th>
        <th width="10%" rowspan="2">Detail</th>
    </tr>
    <!-- 使用 JSTL 的 forEach 标签遍历课程列表 -->
    <c:forEach var="i" begin="0" end="${coursesList.size()-1}">
        <tr> <!-- 对于每一个课程，创建一行 -->
            <!-- 课程名称现在是一个指向 update.jsp 的链接，并将课程 id 作为请求参数 -->
            <td width="7%" class="btbg1"><a href="update.jsp?courseId=${coursesList[i].id}&classroomId=${classroomsList[i].id}">${coursesList[i].name}</a></td>
            <td width="7%" class="btbg2">${coursesList[i].startWeek}</td>
            <td width="7%" class="btbg2">${coursesList[i].endWeek}</td>
            <td width="7%" class="btbg2">${coursesList[i].weekDay}</td>
            <td width="7%" class="btbg2">${coursesList[i].schooltime}</td>
            <td width="7%" class="btbg2">${classroomsList[i].number}</td>
            <td width="7%" class="btbg2">${coursesList[i].detail}</td>
        </tr>
    </c:forEach>

</table>
<!-- 创建分页导航链接 -->
<div align="center" class="nextPage">
    <a href="?page=1">First</a> <!-- 链接到第一页 -->
    <!-- 遍历所有的页码 -->
    <c:forEach var="i" begin="1" end="${totalPageNumber}">
        <a href="?page=${i}">${i}</a> <!-- 创建一个链接到对应页码的链接 -->
    </c:forEach>
    <a href="?page=${totalPageNumber}">Last</a> <!-- 链接到最后一页 -->
</div>
</body>
</html>
