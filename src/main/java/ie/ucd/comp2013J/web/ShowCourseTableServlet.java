package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.ClassroomCourse;
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
import java.util.ArrayList;
import java.util.List;

//该Servlet用于向前端页面发送一个List<Course>或者List<Classroom>
@WebServlet(urlPatterns = "/showCourseTableServlet")
public class ShowCourseTableServlet extends HttpServlet {
    private final ClassroomService classroomservice = new ClassroomService();
    private final CourseService courseService = new CourseService();
    private final ClassroomCourseService classroomCourseService = new ClassroomCourseService();

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
        // 为了初始化当前页面,我需要先获取所有的Course+对应Classroom的信息
        List<ClassroomCourse> classroomCoursesList = classroomCourseService.selectAllClassroomCourse();
        ArrayList<Course> coursesList = courseService.selectAllCourse(classroomCoursesList);
        ArrayList<Classroom> classroomsList = classroomservice.selectAllClassroom(classroomCoursesList);

    }


}