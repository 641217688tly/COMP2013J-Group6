<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="ie.ucd.comp2013J.service.ClassroomService" %>
<%@ page import="ie.ucd.comp2013J.service.CourseService" %>
<%@ page import="ie.ucd.comp2013J.pojo.Course" %>
<%@ page import="ie.ucd.comp2013J.pojo.Classroom" %>
<html>
<head>
    <title>Update Course</title>
    <link href="css/update.css" rel="stylesheet">
</head>
<body>
<div class="simple-linear">
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

    <!-- Display links to upload course information and view course information -->
<ul class="topnav">
  <li><a class="active" href="showCourseTableServlet">Return to Course Information Page</a></li>
  <li><a href="showClassroomTableServlet">Return to Classrooms Information Page</a></li>
</ul>

<!-- Create a form to submit data to UpdateServlet -->
<form action="updateServlet" method="post" align="center">
    Course Name: <input type="text" name="name"  value="${course.name}"><br>
    Start Week: <input type="text" name="startWeek"  value="${course.startWeek}"><br>
    End Week: <input type="text" name="endWeek"  value="${course.endWeek}"><br>
    Week Day: <input type="text" name="weekDay"  value="${course.weekDay}"><br>
    School Time: <input type="text" name="schooltime"  value="${course.schooltime}"><br>
    Classroom Number: <input type="text" name="classroomNumber"  value="${classroom.number}"><br>
    Detail: <input type="text" name="detail"  value="${course.detail}"><br>
    <!-- Hidden fields to pass the course id and classroom id to UpdateServlet -->
    <input type="hidden" name="courseId" value="${course.id}">
    <input type="hidden" name="classroomId" value="${classroom.id}">
    <!-- Update button -->
    <input type="submit" value="update" class="button1">
    <p>${failure_message1}${failure_message2}${failure_message3}${success_message}</p>
</form>
</div>
</body>
</html>
