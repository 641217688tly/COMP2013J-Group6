package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.ClassroomCourse;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.service.ClassroomCourseService;
import ie.ucd.comp2013J.service.ClassroomService;
import ie.ucd.comp2013J.service.CourseService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

//该Servlet用于向前端页面发送一个List<Course>或者List<Classroom>
@WebServlet(urlPatterns = "/showCourseTableServlet")
public class ShowCourseTableServlet extends HttpServlet {
    private final ClassroomService classroomservice = new ClassroomService();
    private final CourseService courseService = new CourseService();
    private final ClassroomCourseService classroomCourseService = new ClassroomCourseService();
    private static final int PAGE_SIZE = 5;  // 定义一个常量，用于表示每页的大小

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) { // 检查用户是否已登录，否则重定向到index.jsp
            response.sendRedirect("login.jsp");
        }

        // 获取请求的页数，默认为1
        int pageNumber = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            pageNumber = Integer.parseInt(pageStr);
        }

        // 获取这一页的课程
        List<Course> coursesForPage = courseService.getCoursesForPage(pageNumber, 5);
        List<Classroom> classroomsForPage = classroomservice.getByClassroomCourses(classroomCourseService.getByCourses(coursesForPage));

        // 将coursesForPage添加到请求属性中
        request.setAttribute("coursesList", coursesForPage);
        request.setAttribute("classroomsList", classroomsForPage);

        // 计算总页数并添加到请求属性中
        int totalCourses = courseService.getTotalCourses();
        int totalPageNumber = (int) Math.ceil((double) totalCourses / PAGE_SIZE);
        request.setAttribute("totalPageNumber", totalPageNumber);

        // 使用请求转发器将请求转发到JSP页面
        request.getRequestDispatcher("/showCourseTable.jsp").forward(request, response);
    }

}