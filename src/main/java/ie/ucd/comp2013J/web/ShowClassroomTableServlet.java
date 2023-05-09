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

//该Servlet用于向前端页面发送一个List<Course>或者List<Classroom>
@WebServlet(urlPatterns = "/showClassroomTableServlet")
public class ShowClassroomTableServlet extends HttpServlet {
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
        //当用户登录后,将其request信息转发到此Servlet中进行处理
        //该Servlet能够为用户呈现最初状态下的课程和教室信息表
        //TODO 先共同设计前端页面上该以怎样的形式来呈现课表和教室,以及应该设计哪些筛选条件?(Step3)
        //TODO 需要在ClassroomService,CourseService和ClassroomCourseService中分别添加对应的查询方法来帮助呈现课程和教室信息(step4)
    }


}