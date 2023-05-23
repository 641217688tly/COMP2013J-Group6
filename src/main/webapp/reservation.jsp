<%@ page import="ie.ucd.comp2013J.pojo.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Reservation</title>
    <link href="css/reservation.css" rel="stylesheet">
</head>

<body>
<div class="simple-linear">
<%
    int classroomId = Integer.parseInt(request.getParameter("classroomId"));
    int classroomNumber = Integer.parseInt(request.getParameter("classroomNumber"));
    int week = Integer.parseInt(request.getParameter("week"));
    int weekDay = Integer.parseInt(request.getParameter("weekDay"));
    int schooltime = Integer.parseInt(request.getParameter("schooltime"));
    User user = (User) session.getAttribute("user");
    request.setAttribute("classroomId", classroomId);
    request.setAttribute("classroomNumber", classroomNumber);
    request.setAttribute("week", week);
    request.setAttribute("weekDay", weekDay);
    request.setAttribute("schooltime", schooltime);
    request.setAttribute("user", user);
%>

<!-- display links to upload information and view the course information -->
<ul class="topnav">
    <li><a class="active" href="searchClassroomServlet">View Classroom Information</a></li>
    <li><a href="showCourseTableServlet">View Course Information</a></li>
</ul>

<form action="reservationServlet" method="post" align="center">
    User Name: <input type="text" name="userName"  value="<%=user.getUsername()%>" readonly><br>
    Classroom Number: <input type="text" name="classroomNumber"  value="<%=classroomNumber%>" readonly><br>
    Teaching Week: <input type="text" name="week"  value="<%=week%>" readonly><br>
    Day Of The Week: <input type="text" name="weekDay"  value="<%=weekDay%>" readonly><br>
    Schooltime: <input type="text" name="schooltime" value="<%=schooltime%>" readonly><br>
    Purpose of Appointment: <input type="text"  name="purpose" required><br>
    <input type="hidden" name="classroomId" value="<%=classroomId%>">
    <input type="submit" value="Submit" class="button1">
</form>
<p>${appointmentResponse_message}</p>
</div>
</body>
</html>
