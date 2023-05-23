package ie.ucd.comp2013J.web;

import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.service.ClassroomCourseService;
import ie.ucd.comp2013J.service.ClassroomService;
import ie.ucd.comp2013J.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


@WebServlet(urlPatterns = "/insertServlet")
@MultipartConfig
public class InsertServlet extends HttpServlet {
    private final ClassroomService classroomservice = new ClassroomService();
    private final CourseService courseService = new CourseService();
    private final ClassroomCourseService classroomCourseService = new ClassroomCourseService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) { // Check if the user is logged in, otherwise redirect to index.jsp.
            response.sendRedirect("login.jsp");
            return;
        }

        if ("administrator".equals(user.getRole())) { // Only users with an administrator role can add new course information.
            String contentType = request.getContentType();
            if (contentType != null && contentType.startsWith("multipart/form-data")) {
                Part filePart = request.getPart("file");
                if (filePart != null && filePart.getSize() > 0) {
                    String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                    String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();
                    if (fileExtension.equals(".xls") || fileExtension.equals(".xlsx")) {
                        ArrayList<Course> courses = courseService.insertExcelFile(filePart.getInputStream());
                        ArrayList<Classroom> classrooms = classroomservice.insertExcelFile(filePart.getInputStream());
                        boolean flag1 = classroomCourseService.insertExcelFile(courses, classrooms);
                        if (flag1) {
                            request.setAttribute("success_message2", "All course information in the Excel has been successfully uploaded!");
                            request.getRequestDispatcher("/insert.jsp").forward(request, response);
                        } else {
                            request.setAttribute("failure_message4", "Sorry, there was an error in parsing the Excel timetable file, and some courses may not have been uploaded correctly.");
                            request.getRequestDispatcher("/insert.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("failure_message3", "The uploaded file format is incorrect, please upload Excel file!");
                        request.getRequestDispatcher("/insert.jsp").forward(request, response);
                    }
                }
            } else {
                Course course = new Course();
                Classroom classroom = new Classroom();
                String courseName = request.getParameter("courseName");
                String startWeek = request.getParameter("startWeek");
                String endWeek = request.getParameter("endWeek");
                String weekDay = request.getParameter("weekDay");
                String schooltime = request.getParameter("schooltime");
                String detail = request.getParameter("detail");
                String classroomNumber = request.getParameter("classroomNumber");

                if (courseName != null && startWeek != null && endWeek != null && weekDay != null && schooltime != null && classroomNumber != null && !courseName.isEmpty() && !startWeek.isEmpty() && !endWeek.isEmpty() && !weekDay.isEmpty() && !schooltime.isEmpty() && !classroomNumber.isEmpty()) {
                    course.setName(courseName);
                    course.setStartWeek(Integer.parseInt(startWeek));
                    course.setEndWeek(Integer.parseInt(endWeek));
                    course.setWeekDay(Integer.parseInt(weekDay));
                    course.setSchooltime(Integer.parseInt(schooltime));
                    course.setDetail(detail);
                    classroom.setNumber(Integer.parseInt(classroomNumber));

                    classroom = classroomservice.insertClassroom(classroom);
                    List<Course> existingCourses = courseService.getByClassroomCourses(classroomCourseService.getByClassroom(classroom));

                    boolean flag2 = true; // judge if the existing course have a time conflict with new course
                    if (existingCourses != null) {
                        if (existingCourses.size() != 0) {
                            for (int i = 0; i < existingCourses.size(); i++) {
                                if (existingCourses.get(i).getWeekDay() == course.getWeekDay() && existingCourses.get(i).getSchooltime() == course.getSchooltime()) {
                                    if ((existingCourses.get(i).getStartWeek() <= course.getStartWeek() && existingCourses.get(i).getEndWeek() >= course.getStartWeek()) || (existingCourses.get(i).getStartWeek() <= course.getEndWeek() && existingCourses.get(i).getEndWeek() >= course.getEndWeek())) {
                                        flag2 = false;
                                    }
                                }
                            }
                        }
                    }
                    if (flag2) {
                        boolean flag3 = classroomCourseService.insertSingleClassroomCourse(courseService.insertCourse(course), classroom);
                        if (flag3) {
                            request.setAttribute("success_message1", "Upload Successfully!");
                            request.getRequestDispatcher("/insert.jsp").forward(request, response);
                        } else {
                            request.setAttribute("failure_message2", "Due to internal server issues, the upload has failed. Please contact the administrator for assistance.");
                            request.getRequestDispatcher("/insert.jsp").forward(request, response);
                        }
                    } else {
                        request.setAttribute("failure_message6", "The schedule of the course to be inserted conflicts with the schedule of the existing course!");
                        request.getRequestDispatcher("/insert.jsp").forward(request, response);
                    }
                } else {
                    request.setAttribute("failure_message1", "Required information is empty!");
                    request.getRequestDispatcher("/insert.jsp").forward(request, response);
                }
            }
        } else {
            //The user is not an administrator.
            request.setAttribute("failure_message5", "Update failed, you do not have administrator privileges!");
            request.getRequestDispatcher("/insert.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}