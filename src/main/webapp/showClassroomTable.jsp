<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
<link href="css/showClassroomTable.css" rel="stylesheet">
</head>
<body class="simple-linear">

<!-- 显示链接以上传课程信息和查看课程信息 -->
<ul class="topnav">
  <li><a class="active" href="insert.jsp">上传课程信息</a></li>
  <li><a href="showCourseTableServlet">查看课程信息</a></li>
</ul>

<!-- 在标题中显示当前教室的信息 -->
<c:if test="${classroomList.get(currentPage-1).status}">
    <h2 align="center" class="title" height="60">教室状态: 可用</h2>
</c:if>
<c:if test="${!classroomList.get(currentPage-1).status}">
    <h2 align="center" class="title" height="60">教室状态: 不可用</h2>
</c:if>
<h2 align="center" height="60">
    当前教室: ${classroomList.get(currentPage-1).number} 教室容量: ${classroomList.get(currentPage-1).capacity}
    教室楼层: ${classroomList.get(currentPage-1).floor} 当前教学周:Week${currentWeek}
</h2>

<!-- 创建一个表单用于提交筛选条件 -->
<div align="center" height="25">
    <form action="searchClassroomServlet" method="post">
        Current Week: <select name="currentWeek" required>
        <c:forEach var="i" begin="1" end="18">
            <option value="${i}">Week ${i}</option>
        </c:forEach>
    </select>
        Floor: <select name="floor">
        <c:forEach var="i" begin="1" end="9">
            <option value="${i}">${i}</option>
        </c:forEach>
    </select>
        Capacity: <select name="capacity">
        <option value="大">大</option>
        <option value="中">中</option>
        <option value="小">小</option>
    </select>
        Status: <select name="status">
        <option value="available">Available</option>
        <option value="unavailable">Unavailable</option>
    </select>
        Classroom Number (optional): <input type="number" name="specificNumber" min="0" max="999">
        <input type="submit" value="Search">
    </form>
</div>
<p align="center">${searchResponse_message}</p>

<!-- 如果有搜索结果，显示它们 -->
<c:if test="${not empty classroomList}">
    <table border="1">
        <tr>
            <th></th>
            <th>星期一</th>
            <th>星期二</th>
            <th>星期三</th>
            <th>星期四</th>
            <th>星期五</th>
            <th>星期六</th>
            <th>星期日</th>
        </tr>
        <!-- 对每个时段进行循环 -->
        <c:forEach var="schooltime" begin="1" end="6">
            <tr>
                <th>时段${schooltime}</th>
                <!-- 对每个星期几进行循环 -->
                <c:forEach var="weekDay" begin="1" end="7">
                    <td>
                        <!-- 创建一个变量，用来检查是否找到了对应的课程 -->
                        <c:set var="foundCourseAndReservation" value="false"/>

                        <!-- 对每个课程进行循环 -->
                        <c:forEach items="${coursesInCurrentWeek.get(currentPage-1)}" var="course">
                            <!-- 如果课程的星期和时段匹配，显示课程名称，并设置foundCourse为true -->
                            <c:if test="${course.weekDay == weekDay && course.schooltime == schooltime}">
                                <a href="${pageContext.request.contextPath}/update.jsp?courseId=${course.id}&classroomId=${classroomList.get(currentPage-1).id}">${course.name}</a>
                                <c:set var="foundCourseAndReservation" value="true"/>
                            </c:if>
                        </c:forEach>
                        <!-- 对每个预约进行循环 -->

                        <c:forEach items="${reservationsInCurrentWeek.get(currentPage-1)}" var="reservation">
                            <!-- 如果预约的星期和时段匹配，显示预约的purpose，并设置foundCourse为true -->
                            <c:if test="${reservation.weekDay == weekDay && reservation.schooltime == schooltime}">
                                <div>${reservation.purpose}</div>
                                <c:set var="foundCourseAndReservation" value="true"/>
                            </c:if>
                        </c:forEach>

                        <!-- 如果没有找到课程,说明此处没有课,则显示预约链接 -->
                        <c:if test="${!foundCourseAndReservation}">
                            <a href="${pageContext.request.contextPath}/reservation.jsp?classroomId=${classroomList.get(currentPage-1).id}&classroomNumber=${classroomList.get(currentPage-1).number}&weekDay=${weekDay}&schooltime=${schooltime}&week=${currentWeek}">预约</a>
                        </c:if>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</c:if>
<!-- 创建一个表格显示课程表 -->

<!-- 如果有多于一个的搜索结果，显示分页链接 -->
<c:if test="${classroomList.size() > 1}">
    <!-- 显示分页链接 -->
    <div align="center" class="nextPage">
        <c:forEach var="i" begin="1" end="${classroomList.size()}">
            <a href="showClassroomTableServlet?currentPage=${i}">Page${i}</a>
        </c:forEach>
    </div>
</c:if>


</body>
</html>
