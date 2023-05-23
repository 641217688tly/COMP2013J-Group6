<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <title>Courses Table</title>
    <link href="css/showCourseTable.css" rel="stylesheet">
</head>
<body class="simple-linear">

<!-- Display links to upload course information and view classroom information -->
<ul class="topnav">
    <li><a class="active" href="insert.jsp">Upload Course Information</a></li>
    <li><a href="searchClassroomServlet">View Classroom Information</a></li>
    <li><a href="login.jsp">Switch Account</a></li>
</ul>

<form action="showCourseTableServlet" method="post" align="center" class="nextPage">
    Course Name Query: <input type="text" name="specificName"/>
    <input type="submit" value="Search" color="blue"/>
</form>

<table border="0" width="100%" class="tabletop">  <!-- Create a table with borders -->
    <tr><!-- Create table headers -->
        <th width="10%">Course Name</th>
        <th width="10%">Start Week</th>
        <th width="10%">End Week</th>
        <th width="10%">Week Day</th>
        <th width="10%">School Time</th>
        <th width="10%">Classroom Number</th>
        <th width="10%">Detail</th>
    </tr>
    <!-- Use JSTL forEach tag to iterate over the course list -->
    <c:forEach var="i" begin="0" end="${coursesList.size()-1}">
        <tr> <!-- Create a row for each course -->
            <!-- Course name is now a link to update.jsp with the course id as a request parameter -->
            <td width="10%" class="btbg1"><a
                    href="update.jsp?courseId=${coursesList[i].id}&classroomId=${classroomsList[i].id}">${coursesList[i].name}</a>
            </td>
            <td width="10%" class="btbg2">${coursesList[i].startWeek}</td>
            <td width="10%" class="btbg2">${coursesList[i].endWeek}</td>
            <td width="10%" class="btbg2">${coursesList[i].weekDay}</td>
            <td width="10%" class="btbg2">${coursesList[i].schooltime}</td>
            <td width="10%" class="btbg2">${classroomsList[i].number}</td>
            <td width="10%" class="btbg2">${coursesList[i].detail}</td>
        </tr>
    </c:forEach>

</table>

<!-- Create pagination navigation links -->
<div align="center" class="nextPage">
    <a href="showCourseTableServlet?page=1">First</a> <!-- Link to the first page -->
    <!-- Iterate over all page numbers -->
    <c:forEach var="i" begin="1" end="${totalPageNumber}">
        <a href="showCourseTableServlet?page=${i}">${i}</a> <!-- Create a link to the corresponding page number -->
    </c:forEach>
    <a href="showCourseTableServlet?page=${totalPageNumber}">Last</a> <!-- Link to the last page -->
</div>

</body>
</html>
