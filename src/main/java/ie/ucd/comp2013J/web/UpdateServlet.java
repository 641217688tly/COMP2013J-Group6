package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.service.ClassroomCourseService;
import ie.ucd.comp2013J.service.ClassroomService;
import ie.ucd.comp2013J.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/updateServlet")
public class UpdateServlet extends HttpServlet {
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
        //判断接收的是Excel文件还是Course的信息?

    }


}