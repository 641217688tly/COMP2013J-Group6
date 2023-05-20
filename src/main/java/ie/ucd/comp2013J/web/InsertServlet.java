package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.Classroom;
import ie.ucd.comp2013J.pojo.Course;
import ie.ucd.comp2013J.service.ClassroomCourseService;
import ie.ucd.comp2013J.service.ClassroomService;
import ie.ucd.comp2013J.service.CourseService;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


@WebServlet(urlPatterns = "/insertServlet")
@MultipartConfig
public class InsertServlet extends HttpServlet {
    private final ClassroomService classroomservice = new ClassroomService();
    private final CourseService courseService = new CourseService();
    private final ClassroomCourseService classroomCourseService = new ClassroomCourseService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
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
                        request.setAttribute("success_message2", "Excel内的所有课程信息都已被正确上传!");
                        request.getRequestDispatcher("/insert.jsp").forward(request, response);
                    } else {
                        request.setAttribute("failure_message4", "抱歉,Excel课表文件解析存在错误,部分课程可能没有被正确上传");
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

                boolean flag2 = classroomCourseService.insertSingleClassroomCourse(courseService.insertCourse(course), classroomservice.insertClassroom(classroom));
                if (flag2) {
                    request.setAttribute("success_message1", "Upload Successfully!");
                    request.getRequestDispatcher("/insert.jsp").forward(request, response);
                } else {
                    request.setAttribute("failure_message2", "因服务器内部原因导致上传失败,请联系管理员");
                    request.getRequestDispatcher("/insert.jsp").forward(request, response);
                }
            } else {
                request.setAttribute("failure_message1", "必填的信息为空!");
                request.getRequestDispatcher("/insert.jsp").forward(request, response);
            }
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}