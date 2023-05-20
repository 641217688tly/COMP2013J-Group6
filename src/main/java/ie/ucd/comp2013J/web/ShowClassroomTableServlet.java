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
import java.util.ArrayList;
import java.util.List;


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
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) { // 检查用户是否已登录，否则重定向到index.jsp
            response.sendRedirect("login.jsp");
            return;
        }

        List<Classroom> classroomList = (List<Classroom>) session.getAttribute("classroomList");
        ArrayList<List<Course>> correspondingCourses = (ArrayList<List<Course>>) session.getAttribute("correspondingCourses");
        Integer currentWeek = (Integer) session.getAttribute("currentWeek");
        int currentPage = 1; //默认第一次进入时展示page 1
        if (request.getParameter("currentPage") != null) {
            if (!request.getParameter("currentPage").isEmpty()) {
                currentPage = Integer.parseInt(request.getParameter("currentPage"));
            }
        }

        ArrayList<List<Course>> coursesInCurrentWeek = new ArrayList<>(); //对correspondingCourses中的各个ArrayList<Course>中的Course进行筛选
        // 筛选出在指定周有课的课程
        for (int i = 0; i < correspondingCourses.size(); i++) {
            ArrayList<Course> tempCourses = new ArrayList<>();
            for (int j = 0; j < correspondingCourses.get(i).size(); j++) {
                if (correspondingCourses.get(i).get(j).getStartWeek() <= currentWeek && correspondingCourses.get(i).get(j).getEndWeek() >= currentWeek) {
                    tempCourses.add(correspondingCourses.get(i).get(j));
                }
            }
            coursesInCurrentWeek.add(i, tempCourses);
        }
        request.setAttribute("response_message", (String)request.getAttribute("response_message"));
        request.setAttribute("currentPage", currentPage);
        request.setAttribute("currentWeek", currentWeek);
        request.setAttribute("classroomList", classroomList);
        request.setAttribute("coursesInCurrentWeek", coursesInCurrentWeek);

        // 转发请求到JSP页面
        request.getRequestDispatcher("/showClassroomTable.jsp").forward(request, response);
    }
}
