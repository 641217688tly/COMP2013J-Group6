package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.service.ClassroomCourseService;
import ie.ucd.comp2013J.service.ClassroomService;
import ie.ucd.comp2013J.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/insertServlet")
public class InsertServlet extends HttpServlet {
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
        if (true) { //如果是通过上传单个course信息的方式进行插入新课程
            Course course = new Course();
            course.setName(request.getParameter("name"));
            course.setStartWeek(Integer.parseInt(request.getParameter("startWeek")));
            course.setEndWeek(Integer.parseInt(request.getParameter("endWeek")));
            course.setWeekDay(Integer.parseInt(request.getParameter("weekDay")));
            course.setSchooltime(Integer.parseInt(request.getParameter("schooltime")));
            course.setDetail(request.getParameter("detail"));
            boolean flag = courseService.insertCourse(course);
            if (flag) { //插入成功
                //TODO 之后在此插入Classroom和Course_Classroom

                request.setAttribute("insert_message", "Insert Successfully!");
                request.getRequestDispatcher("/insert.jsp").forward(request, response);
            } else { //插入失败
                request.setAttribute("insert_message", "Failed To Insert!");
                request.getRequestDispatcher("/insert.jsp").forward(request, response);
            }

        } else { //通过上传Excel文件的方式插入新课程

        }
    }


}