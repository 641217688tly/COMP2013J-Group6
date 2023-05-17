<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ie.ucd.comp2013J.service.ClassroomService"%>
<%@ page import="ie.ucd.comp2013J.service.CourseService"%>
<%@ page import="ie.ucd.comp2013J.pojo.Course" %>
<%@ page import="ie.ucd.comp2013J.pojo.Classroom" %>
<html>
<body>

<!-- 假设在这里，我们已经从request对象中获取到了course对象和classroom对象 -->
<%
    int courseId = Integer.parseInt(request.getParameter("courseId"));
    int classroomId = Integer.parseInt(request.getParameter("classroomId"));
    ClassroomService classroomService = new ClassroomService();
    CourseService courseService = new CourseService();
    Course course = courseService.getByCourseId(courseId);
    Classroom classroom = classroomService.getByClassroomId(classroomId);
    request.setAttribute("course", course);
    request.setAttribute("classroom", classroom);
%>

<!-- 创建一个表单，用于提交数据到UpdateServlet -->
<form action="updateServlet" method="post">
    Course Name: <input type="text" name="name" value="${course.name}"><br>
    Start Week: <input type="text" name="startWeek" value="${course.startWeek}"><br>
    End Week: <input type="text" name="endWeek" value="${course.endWeek}"><br>
    Week Day: <input type="text" name="weekDay" value="${course.weekDay}"><br>
    School Time: <input type="text" name="schooltime" value="${course.schooltime}"><br>
    Classroom Number: <input type="text" name="classroomNumber" value="${classroom.number}"><br>
    Detail: <input type="text" name="detail" value="${course.detail}"><br>
    <!-- 隐藏字段，用于将course的id和classroom的id传递给UpdateServlet -->
    <input type="hidden" name="courseId" value="${course.id}">
    <input type="hidden" name="classroomId" value="${classroom.id}">
    <!-- 更新按钮 -->
    <input type="submit" value="更新">
    <p>${failure_message1}${failure_message2}${failure_message3}${success_message}</p>
</form>
<a href="showCourseTableServlet"><h2>返回课程信息页</h2></a><br>
<a href="showClassroomTableServlet"><h2>返回教室信息页</h2></a>
</body>
</html>
