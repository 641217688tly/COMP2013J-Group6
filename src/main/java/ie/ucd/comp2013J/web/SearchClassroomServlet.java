package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.pojo.Reservation;
import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.service.ClassroomCourseService;
import ie.ucd.comp2013J.service.ClassroomService;
import ie.ucd.comp2013J.service.CourseService;
import ie.ucd.comp2013J.service.ReservationService;

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
    private final ReservationService reservationService = new ReservationService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) { // Check if the user is logged in, otherwise redirect to login.jsp
            response.sendRedirect("login.jsp");
            return;
        }

        // Retrieve search criteria from the request
        Integer currentWeek = 1; // Default to week 1 for the first visit
        Integer floor = null;
        String capacity = null;
        Boolean status = true;
        Integer specificNumber = null;

        if (request.getParameter("currentWeek") != null && !request.getParameter("currentWeek").isEmpty()) {
            currentWeek = Integer.parseInt(request.getParameter("currentWeek"));
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
        if (request.getParameter("specificNumber") != null) {
            if (!request.getParameter("specificNumber").isEmpty()) {
                specificNumber = Integer.parseInt(request.getParameter("specificNumber"));
            }
        }
        // search classrooms from the database based on the search criteria
        List<Classroom> classroomList = classroomservice.getClassroomsByFilterAndSpecificNumber(floor, capacity, status, specificNumber);

        if (classroomList.size() < 1) { // No qualified classroom found under the given conditions
            request.setAttribute("searchResponse_message", "Unable to find a qualified classroom under the current conditions!");
            request.getRequestDispatcher("/showClassroomTableServlet").forward(request, response);
        } else {
            ArrayList<List<Course>> correspondingCourses = new ArrayList<List<Course>>();
            for (int i = 0; i < classroomList.size(); i++) {
                correspondingCourses.add(i, courseService.getByClassroomCourses(classroomCourseService.getByClassroom(classroomList.get(i))));
            }
            ArrayList<List<Reservation>> correspondingReservations = new ArrayList<>();
            for (int i = 0; i < classroomList.size(); i++) {
                correspondingReservations.add(i, reservationService.getReservationsByClassroomId(classroomList.get(i).getId()));
            }
            // Save the search results to request attributes
            session.setAttribute("classroomList", classroomList);
            session.setAttribute("correspondingReservations", correspondingReservations);
            session.setAttribute("correspondingCourses", correspondingCourses);
            session.setAttribute("currentWeek", currentWeek);
            // Forward the request to showClassroomTable.jsp page
            request.setAttribute("searchResponse_message", "Successful query!");
            request.getRequestDispatcher("/showClassroomTableServlet").forward(request, response);
        }

    }
}