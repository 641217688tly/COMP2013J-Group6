<%@ page import="ie.ucd.comp2013J.pojo.User" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<head>
    <title>Reservation</title>
</head>

<body>
<%
    /*TODO BUG:预约时classroom number是错的*/
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

<form action="reservationServlet" method="post">
    User Name: <input type="text" name="userName" value="<%=user.getUsername()%>" readonly><br>
    Classroom Number: <input type="text" name="classroomNumber" value="<%=classroomNumber%>" readonly><br>
    Week: <input type="text" name="week" value="<%=week%>" readonly><br>
    Day Of The Week: <input type="text" name="weekDay" value="<%=weekDay%>" readonly><br>
    schooltime: <input type="text" name="schooltime" value="<%=schooltime%>" readonly><br>
    Purpose of appointment: <input type="text" name="purpose" required><br>
    <input type="submit" value="Submit">
</form>

</body>
</html>
