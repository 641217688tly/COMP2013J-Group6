<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<body>

<!-- 在标题中显示当前教室的信息 -->

<h2>当前教室: ${currentClassroom.number} 教室容量: ${currentClassroom.capacity} 教室楼层: ${currentClassroom.floor}</h2>
<c:if test="${currentClassroom.status}">
    <h2>教室状态: 可用</h2>
</c:if>
<c:if test="${!currentClassroom.status}">
    <h2>教室状态: 不可用</h2>
</c:if>


<!-- 创建一个表格显示课程表 -->
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
                    <c:set var="foundCourse" value="false"/>
                    <!-- 对每个课程进行循环 -->
                    <c:forEach items="${courseList}" var="course">
                        <!-- 如果课程的星期和时段匹配，显示课程名称，并设置foundCourse为true -->
                        <c:if test="${course.weekDay == weekDay && course.schooltime == schooltime}">
                            <a href="${pageContext.request.contextPath}/update.jsp?courseId=${course.id}&classroomId=${currentClassroom.id}">${course.name}</a>
                            <c:set var="foundCourse" value="true"/>
                        </c:if>
                    </c:forEach>
                    <!-- 如果没有找到课程,说明此处没有课,则显示预约链接 -->
                    <c:if test="${!foundCourse}">
                        <a href="${pageContext.request.contextPath}/reservationServlet">预约</a>
                    </c:if>
                </td>
            </c:forEach>
        </tr>
    </c:forEach>
</table>

<!-- 显示分页链接 -->
<div class="pagination">
    <a href="?page=1">First</a>
    <c:forEach var="i" begin="1" end="${totalPages}">
        <a href="?page=${i}">${i}</a>
    </c:forEach>
    <a href="?page=${totalPages}">Last</a>
</div>

<!-- 显示链接以上传课程信息和查看课程信息 -->
<a href="insert.jsp"><h2>上传课程信息</h2></a><br>
<a href="showCourseTableServlet"><h2>查看课程信息</h2></a>

</body>
</html>
