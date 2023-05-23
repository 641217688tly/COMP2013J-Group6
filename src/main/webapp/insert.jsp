<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert Course</title>
    <meta charset="UTF-8">
    <link href="css/insert.css" rel="stylesheet">
</head>
<body>
<%--Set session.specificName to null so that all courses will be reloaded after entering the showCourseTable.jsp page--%>
<c:set var="specificName" value="${null}" scope="session"/>

<div class="simple-linear">
    <!-- Display link to upload course information and view course information -->
    <ul class="topnav">
        <li><a class="active" href="showCourseTableServlet">View Course Information</a></li>
        <li><a href="searchClassroomServlet">View Classroom Information</a></li>
    </ul>

    <!-- 1. Users can upload information for a single course -->
    <h2 align="center"><b>Upload Single Course Information</b></h2>
    <form action="insertServlet" method="post" id="Single Course" align="center">

        <label for="courseName">Name:</label><br>
        <input type="text" id="courseName" class="groove" name="courseName" required><br>

        <label for="startWeek">Start Week:</label><br>
        <input type="number" id="startWeek" class="groove" name="startWeek" required><br>

        <label for="endWeek">End Week:</label><br>
        <input type="number" id="endWeek" class="groove" name="endWeek" required><br>

        <label for="weekDay">Week Day:</label><br>
        <input type="number" id="weekDay" class="groove" name="weekDay" required><br>

        <label for="schooltime">School Time:</label><br>
        <input type="number" id="schooltime" class="groove" name="schooltime" required><br>

        <label for="classroomNumber">Classroom Number:</label><br>
        <input type="text" id="classroomNumber" class="groove" name="classroomNumber" required><br>

        <label for="detail">Detail:</label><br>
        <textarea id="detail" name="detail"></textarea><br>

        <input type="hidden" name="formType" value="singleCourse">
        <input type="submit" value="Submit" class="button1">
    </form>
    <p align="center">${failure_message6}${failure_message5}${failure_message1}${failure_message2}${success_message1}</p>

    <!-- 2. Users can upload an Excel file -->
    <h2 align="center"><b>Upload Excel for Multiple Courses</b></h2>
    <form action="insertServlet" method="post" enctype="multipart/form-data" id="Multiple Course" align="center">
        <label for="file">Select Excel file:</label>
        <input type="file" id="file" name="file" accept=".xls,.xlsx"><br>
        <input type="hidden" name="formType" value="excelUpload">
        <input type="submit" value="Upload">
    </form>

    <p>${failure_message5}${failure_message3}${failure_message4}${success_message2}</p>
</div>
</body>
</html>
