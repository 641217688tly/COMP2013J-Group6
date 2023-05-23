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
import java.util.List;

@WebServlet(urlPatterns = "/reservationServlet")
public class ReservationServlet extends HttpServlet {
    private final ClassroomService classroomService = new ClassroomService();
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

        int classroomId = Integer.parseInt(request.getParameter("classroomId"));
        int week = Integer.parseInt(request.getParameter("week"));
        int weekDay = Integer.parseInt(request.getParameter("weekDay"));
        int schooltime = Integer.parseInt(request.getParameter("schooltime"));
        int userId = user.getId();
        String purpose = null;
        if (!request.getParameter("purpose").isEmpty() && request.getParameter("purpose") != null) {// The purpose has been correctly received
            purpose = request.getParameter("purpose");
        } else {
            request.setAttribute("appointmentResponse_message", "Necessary information is missing!");
            request.getRequestDispatcher("/reservation.jsp").forward(request, response);
            return;
        }

        Reservation reservation = new Reservation();
        reservation.setUserId(userId);
        reservation.setClassroomId(classroomId);
        reservation.setWeek(week);
        reservation.setWeekDay(weekDay);
        reservation.setSchooltime(schooltime);
        reservation.setPurpose(purpose);

        // First, check if there are any classes or reservations during the specified time slot. If there are, return a message indicating that the appointment failed
        Classroom classroom = classroomService.getByClassroomId(classroomId);
        List<Course> relatedCourses = courseService.getByClassroomCourses(classroomCourseService.getByClassroom(classroom));
        List<Reservation> relatedReservations = reservationService.getByClassroomIdWeekSchooltimeWeekDay(classroomId, week, weekDay, schooltime);
        if (relatedReservations.size() == 0) { // The time slot is not booked
            if (relatedCourses.size() != 0) { // There are classes scheduled in the classroom
                for (int i = 0; i < relatedCourses.size(); i++) {
                    if (relatedCourses.get(i).getSchooltime() == schooltime && relatedCourses.get(i).getWeekDay() == weekDay) { // The weekday and time slot match
                        if (relatedCourses.get(i).getStartWeek() <= week && relatedCourses.get(i).getEndWeek() >= week) {
                            // Appointment failed
                            request.setAttribute("appointmentResponse_message", "Appointment failed! There are classes at that time!");
                            request.getRequestDispatcher("/reservation.jsp").forward(request, response);
                            return;
                        }
                    }
                }
                //Appointment successful, although the classroom has classes, there are no conflicts with the reservation.
                boolean flag = reservationService.makeAppointment(reservation);
                if (flag) {
                    request.setAttribute("appointmentResponse_message", "The appointment is successful!");
                    request.getRequestDispatcher("/reservation.jsp").forward(request, response);
                } else {
                    request.setAttribute("appointmentResponse_message", "The reservation error occurs due to internal reasons. Contact the administrator!");
                    request.getRequestDispatcher("/reservation.jsp").forward(request, response);
                }
            } else {
                //Appointment successful. The classroom is available and there are no classes scheduled during the requested time slot.
                boolean flag = reservationService.makeAppointment(reservation);
                if (flag) {
                    request.setAttribute("appointmentResponse_message", "The appointment is successful!");
                    request.getRequestDispatcher("/reservation.jsp").forward(request, response);
                } else {
                    request.setAttribute("appointmentResponse_message", "The reservation error occurs due to internal reasons. Contact the administrator!");
                    request.getRequestDispatcher("/reservation.jsp").forward(request, response);
                }
            }
        } else {
            // Appointment failed
            request.setAttribute("appointmentResponse_message", "Appointment failed! The time slot has been booked!");
            request.getRequestDispatcher("/reservation.jsp").forward(request, response);
        }
    }
}
