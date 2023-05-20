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
        if (user == null) { // 检查用户是否已登录，否则重定向到login.jsp
            response.sendRedirect("login.jsp");
            return;
        }

        int classroomId = Integer.parseInt(request.getParameter("classroomId"));
        int week = Integer.parseInt(request.getParameter("week"));
        int weekDay = Integer.parseInt(request.getParameter("weekDay"));
        int schooltime = Integer.parseInt(request.getParameter("schooltime"));
        int userId = user.getId();
        String purpose = null;
        if (!request.getParameter("purpose").isEmpty() && request.getParameter("purpose") != null) {//purpose已经正确收到
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

        //先复查该时段是否有课/预约,如果有则返回预约失败消息
        Classroom classroom = classroomService.getByClassroomId(classroomId);
        List<Course> relatedCourses = courseService.getByClassroomCourses(classroomCourseService.getByClassroom(classroom));
        List<Reservation> relatedReservations = reservationService.getByClassroomIdWeekSchooltimeWeekDay(classroomId, week, weekDay, schooltime);
        if (relatedReservations.size() == 0) { //该时段没有被预约
            if (relatedCourses.size() != 0) { //该教室有课
                for (int i = 0; i < relatedCourses.size(); i++) {
                    if (relatedCourses.get(i).getSchooltime() == schooltime && relatedCourses.get(i).getWeekDay() == weekDay) { //星期和时段相同
                        if (relatedCourses.get(i).getStartWeek() <= week && relatedCourses.get(i).getEndWeek() >= week) {
                            //预约失败
                            request.setAttribute("appointmentResponse_message", "Appointment failed! There are classes at that time!");
                            request.getRequestDispatcher("/reservation.jsp").forward(request, response);
                            return;
                        }
                    }
                }
                //预约成功,虽然这个教室有课,但所有的课都与预约不冲突
                boolean flag = reservationService.makeAppointment(reservation);
                if (flag) {
                    request.setAttribute("appointmentResponse_message", "The appointment is successful!");
                    request.getRequestDispatcher("/reservation.jsp").forward(request, response);
                } else {
                    request.setAttribute("appointmentResponse_message", "The reservation error occurs due to internal reasons. Contact the administrator!");
                    request.getRequestDispatcher("/reservation.jsp").forward(request, response);
                }
            } else {
                //该教室没有课,预约成功
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
            //预约失败
            request.setAttribute("appointmentResponse_message", "Appointment failed! The time slot has been booked!");
            request.getRequestDispatcher("/reservation.jsp").forward(request, response);
        }
    }
}
