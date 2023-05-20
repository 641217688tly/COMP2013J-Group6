package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.Course;
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


@WebServlet(urlPatterns = "/searchClassroomServlet")

public class SearchClassroomServlet extends HttpServlet {
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
        // 从请求中获取搜索条件
        Integer currentWeek = 1; //默认第一次进入时访问week1
        Integer floor = null;
        String capacity = null;
        Boolean status = true;

        if (request.getParameter("week") != null && !request.getParameter("week").isEmpty()) {
            currentWeek = Integer.parseInt(request.getParameter("week"));
        }
        if (request.getParameter("floor") != null && !request.getParameter("floor").isEmpty()) {
            floor = Integer.parseInt(request.getParameter("floor"));
        }
        if (request.getParameter("capacity") != null && !request.getParameter("capacity").isEmpty()) {
            capacity = request.getParameter("capacity");
        }
        if (request.getParameter("status") != null && !request.getParameter("status").isEmpty()) {
            String statusString = request.getParameter("status");
            if (statusString.equals("available")) {
                status = true;
            } else if (statusString.equals("unavailable")) {
                status = false;
            }
        }
        // 根据搜索条件从数据库中检索教室
        List<Classroom> classroomList = classroomservice.getByFloorCapacityStatus(floor, capacity, status);
        HttpSession session = request.getSession();

        if (classroomList.size() < 1) { //说明该筛选条件下符合条件的教室数为0
            session.setAttribute("searchResponse_message", "Unable to find a qualified classroom under the current conditions!");
            request.getRequestDispatcher("/showClassroomTableServlet").forward(request, response);
        } else {
            ArrayList<List<Course>> correspondingCourses = new ArrayList<List<Course>>();
            for (int i = 0; i < classroomList.size(); i++) {
                correspondingCourses.add(i, courseService.getByClassroomCourses(classroomCourseService.getByClassroom(classroomList.get(i))));
            }
            // 将搜索结果保存到请求属性中
            session.setAttribute("searchResponse_message", "Successful query!");
            session.setAttribute("classroomList", classroomList);
            session.setAttribute("classroomList", classroomList);
            session.setAttribute("correspondingCourses", correspondingCourses);
            session.setAttribute("currentWeek", currentWeek);
            // 将请求转发回showClassroomTable.jsp页面
            request.getRequestDispatcher("/showClassroomTableServlet").forward(request, response);
        }
    }
}