<%@ page isELIgnored="false" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Insert Course</title>
    <%--    <meta charset="UTF-8">
        <link href="css/insert.css" rel="stylesheet">--%>
</head>
<body style = "background-image: url(imgs/1.jpg);">

<!-- 显示链接以上传课程信息和查看课程信息 -->
<ul class="topnav">
  <li><a class="active" href="showCourseTableServlet">返回课程页</a></li>
  <li><a href="showClassroomTableServlet">返回教室页</a></li>
</ul>

<!-- 一.用户可以上传一个课程的信息 -->
<h2 align="center"><b>Upload Single Course Information</b></h2>
<form action="insertServlet" method="post" id="Single Course" align="center">

    <label for="courseName">Name:</label><br>
    <input type="text" id="courseName" name="courseName"><br>

    <label for="startWeek">Start Week:</label><br>
    <input type="number" id="startWeek" name="startWeek"><br>

    <label for="endWeek">End Week:</label><br>
    <input type="number" id="endWeek" name="endWeek"><br>

    <label for="weekDay">Week Day:</label><br>
    <input type="number" id="weekDay" name="weekDay"><br>

    <label for="schooltime">School Time:</label><br>
    <input type="number" id="schooltime" name="schooltime"><br>

    <label for="classroomNumber">Classroom Number:</label><br>
    <input type="text" id="classroomNumber" name="classroomNumber"><br>

    <label for="detail">Detail:</label><br>
    <textarea id="detail" name="detail"></textarea><br>

    <input type="hidden" name="formType" value="singleCourse">
    <input type="submit" value="Submit" class="button1">
</form>
<p>${failure_message5}${failure_message1}${failure_message2}${success_message1}</p>

<!-- 二.用户可以上传一个Excel文件 -->
<h2 align="center"><b>Upload Excel for Multiple Courses</b></h2>
<form action="insertServlet" method="post" enctype="multipart/form-data" id="Multiple Course">
    <label for="file">Select Excel file:</label>
    <input type="file" id="file" name="file" accept=".xls,.xlsx" class="button1"><br>
    <input type="hidden" name="formType" value="excelUpload">
    <input type="submit" value="Upload" class="button1">
</form>

<p>${failure_message5}${failure_message3}${failure_message4}${success_message2}</p>

</body>
</html>
