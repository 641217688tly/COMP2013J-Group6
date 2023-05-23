package ie.ucd.comp2013J.web;

import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.service.ClassroomCourseService;
import ie.ucd.comp2013J.service.ClassroomService;
import ie.ucd.comp2013J.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

// This Servlet is used to send the List<Course> and List<Classroom> to the front-end page
@WebServlet(urlPatterns = "/showCourseTableServlet")
public class ShowCourseTableServlet extends HttpServlet {
    private final ClassroomService classroomservice = new ClassroomService();
    private final CourseService courseService = new CourseService();
    private final ClassroomCourseService classroomCourseService = new ClassroomCourseService();
    private static final int PAGE_SIZE = 4;  // Define a constant that represents the size of each page

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) { // // Check if the user is logged in, otherwise redirect to index.jsp
            response.sendRedirect("login.jsp");
        }

        String specificName = (String) session.getAttribute("specificName");  //it is null when firstly enter this servlet
        if (request.getParameter("specificName") != null) {
            if (!request.getParameter("specificName").isEmpty()) {
                specificName = request.getParameter("specificName");
            }
        }
        session.setAttribute("specificName",specificName);

        Integer pageNumber = 1; // Get the requested page number, default is 1
        if (request.getParameter("page") != null) {
            if (!request.getParameter("page").isEmpty()) {
                pageNumber = Integer.parseInt(request.getParameter("page"));
            }
        }

        // Get the courses for this page
        List<Course> coursesForSpecificNameAndPage = courseService.getCoursesBySpecificNameAndPage(specificName, pageNumber, PAGE_SIZE);  //Retrieve the List<Course> for the pageNumber (with 4 courses displayed per page)
        List<Classroom> classroomsForPage = classroomservice.getByClassroomCourses(classroomCourseService.getByCourses(coursesForSpecificNameAndPage));

        // Calculate the total number of pages and add it to the request attribute
        int totalCourses = courseService.getTotalCoursesWithSpecificName(specificName);
        int totalPageNumber = (int) Math.ceil((double) totalCourses / PAGE_SIZE);

        // Add coursesForPage to the request attributes
        request.setAttribute("totalPageNumber", totalPageNumber);
        request.setAttribute("coursesList", coursesForSpecificNameAndPage);
        request.setAttribute("classroomsList", classroomsForPage);

        // Use request dispatcher to forward the request to the JSP page
        request.getRequestDispatcher("/showCourseTable.jsp").forward(request, response);
    }

}