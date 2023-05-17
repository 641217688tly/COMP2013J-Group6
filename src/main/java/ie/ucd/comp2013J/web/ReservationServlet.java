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
import java.util.List;

@WebServlet(urlPatterns = "/reservationServlet")
public class ReservationServlet extends HttpServlet {
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
            return;
        }

        // 获取页码参数，默认为1
        int page = 1;
        String pageStr = request.getParameter("page");
        if (pageStr != null && !pageStr.isEmpty()) {
            page = Integer.parseInt(pageStr);
        }

        // 获取每页显示的教室数量，默认为1
        int pageSize = 1;
        String pageSizeStr = request.getParameter("pageSize");
        if (pageSizeStr != null && !pageSizeStr.isEmpty()) {
            pageSize = Integer.parseInt(pageSizeStr);
        }


        // 获取教室信息
        List<Classroom> classrooms = classroomservice.getClassroomsForPage(page, pageSize); //根据showClassroomTable.jsp上的页码数和每页呈现的教室个数来获取教室
        if (!classrooms.isEmpty()) {
            Classroom classroom = classrooms.get(0); // 取出第一个教室

            // 获取教室相关的课程信息
            List<ClassroomCourse> classroomCourses = classroomCourseService.getByClassroom(classroom);
            List<Course> courses = courseService.getByClassroomCourses(classroomCourses);
            request.setAttribute("currentClassroom", classroom);
            request.setAttribute("courseList", courses);
            request.setAttribute("totalPages", (classroomservice.getTotalClassrooms() + pageSize - 1) / pageSize); // 计算总页数
        }

        // 转发请求到JSP页面
        request.getRequestDispatcher("/showClassroomTable.jsp").forward(request, response);
    }
}
