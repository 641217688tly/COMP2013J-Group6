<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert Course</title>
    <meta charset="UTF-8">
    <link href="css/insert.css" rel="stylesheet">
</head>
<body style = "background-image: url(img/1.png);">
<%--TODO jsp前端页面渲染--%>
<!-- 一.用户可以上传一个课程的信息 -->
<h2><b>Upload Single Course Information</b></h2>
<form action="insertServlet" method="post"  id="Single Course">

    <label for="name">Name:</label><br>
    <input type="text" id="name" name="name"><br>

    <label for="startWeek">Start Week:</label><br>
    <input type="number" id="startWeek" name="startWeek"><br>

    <label for="endWeek">End Week:</label><br>
    <input type="number" id="endWeek" name="endWeek"><br>

    <label for="weekDay">Week Day:</label><br>
    <input type="number" id="weekDay" name="weekDay"><br>

    <label for="schooltime">School Time:</label><br>
    <input type="number" id="schoolTime" name="schoolTime"><br>

    <label for="classroomNumber">Classroom Number:</label><br>
    <input type="text" id="classroomNumber" name="classroomNumber" value="Enter Classroom Number"><br>

    <label for="detail">Detail:</label><br>
    <textarea id="detail" name="detail"></textarea><br>
    <input type="submit" value="Submit">
    <p>${upload_message1}</p>
</form>

<!-- 二.用户可以上传一个Excel文件 -->
<h2><b>Upload Excel for Multiple Courses</b></h2>
<form action="insertServlet" method="post" enc-type="multipart/form-data" id="Multiple Course">
    <label for="file">Select Excel file:</label>
    <input type="file" id="file" name="file" accept=".xls,.xlsx"><br>
    <input type="submit" value="Upload">
    <p>${upload_message2}</p>
</form>
</body>
</html>
