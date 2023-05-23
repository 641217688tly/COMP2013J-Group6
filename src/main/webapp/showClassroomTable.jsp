<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>

<html>
<head>
    <title>Classrooms Table</title>
    <link href="css/showClassroomTable.css" rel="stylesheet">
</head>
<body class="simple-linear">

<!-- Display links to upload course information and view course information -->
<ul class="topnav">
    <li><a class="active" href="insert.jsp">Upload Course Information</a></li>
    <li><a href="showCourseTableServlet">View Course Information</a></li>
    <li><a href="login.jsp">Switch Account</a></li>
</ul>

<!-- Display the information of the current classroom in the title -->

<h3 align="center" height="60">
    <%--Classroom status--%>
    <c:if test="${classroomList.get(currentPage-1).status}">
        <span style="color: green;">Classroom status:</span> Available
    </c:if>
    <c:if test="${!classroomList.get(currentPage-1).status}">
        <span style="color: green;">Classroom status:</span> Unavailable
    </c:if>

    <%--Current classroom--%>
    <span style="color: green;">Current classroom:</span> ${classroomList.get(currentPage-1).number}

    <%--Classroom capacity--%>
    <c:if test="${classroomList.get(currentPage-1).capacity.equals('大')}">
       <span style="color: green;">Classroom capacity:</span> Large
    </c:if>
    <c:if test="${classroomList.get(currentPage-1).capacity.equals('中')}">
        <span style="color: green;">Classroom capacity:</span> Medium
    </c:if>
    <c:if test="${classroomList.get(currentPage-1).capacity.equals('小')}">
       <span style="color: green;">Classroom capacity:</span> Small
    </c:if>
    <%--Classroom floor--%>
    <span style="color: green;">Classroom floor:</span> ${classroomList.get(currentPage-1).floor}

    <%--Current Teaching Week--%>
    <span style="color: green;">Current Teaching Week:</span> week ${currentWeek}
</h3>


<!-- Create a form to submit filter conditions -->
<div align="center" height="25">
    <form action="searchClassroomServlet" method="post">
        Current Week: <select name="currentWeek" required>
        <c:forEach var="i" begin="1" end="18">
            <option value="${i}">Week ${i}</option>
        </c:forEach>

    </select>
        Floor: <select name="floor">
        <option value="" selected>Please select</option>
        <c:forEach var="i" begin="1" end="9">
            <option value="${i}">${i}</option>
        </c:forEach>

    </select>
        Capacity: <select name="capacity">
        <option value="" selected>Please select</option>
        <option value="大">Large</option>
        <option value="中">Medium</option>
        <option value="小">Small</option>

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


<!-- If there are search results, display them -->
<c:if test="${not empty classroomList}">
    <table border="1" width="100%" class="tabletop" align="center">
        <tr>
            <th></th>
            <th>Monday</th>
            <th>Tuesday</th>
            <th>Wednesday</th>
            <th>Thursday</th>
            <th>Friday</th>
            <th>Saturday</th>
            <th>Sunday</th>
        </tr>
        <!-- Loop through each time slot -->
        <c:forEach var="schooltime" begin="1" end="6">
            <tr>
                <th>Schooltime${schooltime}</th>
                <!-- Loop through each day of the week -->
                <c:forEach var="weekDay" begin="1" end="7">
                    <td>
                        <!-- Create a variable to check if the corresponding course is found -->
                        <c:set var="foundCourseAndReservation" value="false"/>

                        <!-- Loop through each course -->
                        <c:forEach items="${coursesInCurrentWeek.get(currentPage-1)}" var="course">
                            <!-- If the course's week day and time slot match, display the course name and set foundCourseAndReservation to true -->
                            <c:if test="${course.weekDay == weekDay && course.schooltime == schooltime}">
                                <a href="${pageContext.request.contextPath}/update.jsp?courseId=${course.id}&classroomId=${classroomList.get(currentPage-1).id}">${course.name}</a>
                                <c:set var="foundCourseAndReservation" value="true"/>
                            </c:if>
                        </c:forEach>

                        <!-- Loop through each reservation -->
                        <c:forEach items="${reservationsInCurrentWeek.get(currentPage-1)}" var="reservation">
                            <!-- If the reservation's week day and time slot match, display the reservation's purpose and set foundCourseAndReservation to true -->
                            <c:if test="${reservation.weekDay == weekDay && reservation.schooltime == schooltime}">
                                <div>${reservation.purpose}</div>
                                <c:set var="foundCourseAndReservation" value="true"/>
                            </c:if>
                        </c:forEach>

                        <!-- If no course is found, it means there is no class scheduled, so display the reservation link -->
                        <c:if test="${!foundCourseAndReservation}">
                            <a href="${pageContext.request.contextPath}/reservation.jsp?classroomId=${classroomList.get(currentPage-1).id}&classroomNumber=${classroomList.get(currentPage-1).number}&weekDay=${weekDay}&schooltime=${schooltime}&week=${currentWeek}">Book</a>
                        </c:if>
                    </td>
                </c:forEach>
            </tr>
        </c:forEach>
    </table>
</c:if>

<!-- If there are more than one search results, display the pagination links -->
<c:if test="${classroomList.size() > 1}">
    <!-- Display the pagination links -->
    <div align="center" class="nextPage">
        <c:forEach var="i" begin="1" end="${classroomList.size()}">
            <a href="showClassroomTableServlet?currentPage=${i}">[Page${i}]</a>
        </c:forEach>
    </div>
</c:if>

</body>
</html>
