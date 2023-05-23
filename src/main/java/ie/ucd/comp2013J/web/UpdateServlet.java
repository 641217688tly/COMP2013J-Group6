package ie.ucd.comp2013J.web;

import ie.ucd.comp2013J.service.ClassroomCourseService;
import ie.ucd.comp2013J.service.ClassroomService;
import ie.ucd.comp2013J.service.CourseService;
import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.pojo.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/updateServlet")
public class UpdateServlet extends HttpServlet {
    private ClassroomService classroomService = new ClassroomService();
    private CourseService courseService = new CourseService();
    private ClassroomCourseService classroomCourseService = new ClassroomCourseService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        // Get the user object
        User user = (User) request.getSession().getAttribute("user");
        // Get the parameters
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int classroomId = Integer.parseInt(request.getParameter("classroomId"));
        // Check user role
        if (user != null && "administrator".equals(user.getRole())) {
            // Get the parameters
            String name = request.getParameter("name");
            String startWeek = request.getParameter("startWeek");
            String endWeek = request.getParameter("endWeek");
            String weekDay = request.getParameter("weekDay");
            String schooltime = request.getParameter("schooltime");
            String classroomNumber = request.getParameter("classroomNumber");
            String detail = request.getParameter("detail");
            // Check if the parameters are empty
            if (name != null && startWeek != null && endWeek != null && weekDay != null && schooltime != null && classroomNumber != null && !name.isEmpty() && !startWeek.isEmpty() && !endWeek.isEmpty() && !weekDay.isEmpty() && !schooltime.isEmpty() && !classroomNumber.isEmpty()) {
                // Get the old course and classroom
                Course oldCourse = courseService.getByCourseId(courseId);
                Classroom oldClassroom = classroomService.getByClassroomId(classroomId);

                // Update the course
                Course newCourse = new Course();
                newCourse.setId(courseId);
                newCourse.setName(name);
                newCourse.setStartWeek(Integer.parseInt(startWeek));
                newCourse.setEndWeek(Integer.parseInt(endWeek));
                newCourse.setWeekDay(Integer.parseInt(weekDay));
                newCourse.setSchooltime(Integer.parseInt(schooltime));
                newCourse.setDetail(detail);
                courseService.updateCourse(newCourse);

                // If the classroom number has changed
                if (!(Integer.parseInt(classroomNumber) == oldClassroom.getNumber())) { //classroom number也被更新
                    // Delete the old relationship
                    classroomCourseService.deleteByCourseIdAndClassroomId(courseId, classroomId);
                    // Insert the new classroom
                    Classroom newClassroom = new Classroom();
                    newClassroom.setNumber(Integer.parseInt(classroomNumber));
                    newClassroom = classroomService.insertClassroom(newClassroom); // If the classroom corresponding to the new classroom number already exists, classroomService.insertClassroom(newClassroom) will return the existing classroom object
                    // Re-establish the relationship in the classroom_course table
                    boolean flag = classroomCourseService.insertSingleClassroomCourse(newCourse, newClassroom);
                    if (flag) {
                        request.setAttribute("courseId", courseId);
                        request.setAttribute("classroomId", newClassroom.getId());
                        request.setAttribute("success_message", "Update successful!");
                        request.getRequestDispatcher("/update.jsp").forward(request, response);
                    } else {
                        request.setAttribute("courseId", courseId);
                        request.setAttribute("classroomId", classroomId);
                        request.setAttribute("failure_message3", "Unknown reason caused the update to fail, please contact the administrator!");
                        request.getRequestDispatcher("/update.jsp").forward(request, response);
                    }
                }
                request.setAttribute("courseId", courseId);
                request.setAttribute("classroomId", classroomId);
                request.setAttribute("success_message", "Update successful!");
                request.getRequestDispatcher("/update.jsp").forward(request, response);
            } else {
                // Required parameters are empty
                request.setAttribute("courseId", courseId);
                request.setAttribute("classroomId", classroomId);
                request.setAttribute("failure_message2", "Update failed, necessary information not submitted!");
                request.getRequestDispatcher("/update.jsp").forward(request, response);
            }
        } else {
            // User is not an administrator
            request.setAttribute("courseId", courseId);
            request.setAttribute("classroomId", classroomId);
            request.setAttribute("failure_message1", "Update failed, you do not have administrator privileges!");
            request.getRequestDispatcher("/update.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
