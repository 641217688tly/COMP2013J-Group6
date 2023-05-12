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
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;


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
        Part filePart = request.getPart("file"); //判断接收的是Excel文件还是单个Course的信息?
        if (filePart != null && filePart.getSize() > 0) {
            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // 获取文件名，包括扩展名
            String fileExtension = fileName.substring(fileName.lastIndexOf(".")).toLowerCase(); // 获取文件扩展名
            if (fileExtension.equals(".xls") || fileExtension.equals(".xlsx")) { // 用户上传的是一个Excel文件，处理Excel文件...
                ArrayList<Course> courses = courseService.insertExcelFile(filePart.getInputStream());//先插入课程
                ArrayList<Classroom> classrooms = classroomservice.insertExcelFile(filePart.getInputStream());//再插入教室
                boolean flag1 = classroomCourseService.insertExcelFile(courses, classrooms); //再根据教室和课程进行连接
                if (flag1) {
                    request.setAttribute("upload_message2", "Excel内的所有课程信息都已被正确上传!");
                    request.getRequestDispatcher("/insert.jsp").forward(request, response);
                } else {
                    request.setAttribute("upload_message2", "抱歉,Excel课表文件解析存在错误,部分课程可能没有被正确上传");
                    request.getRequestDispatcher("/insert.jsp").forward(request, response);
                }
            } else { // 用户上传的不是一个Excel文件，返回错误信息...
                request.setAttribute("upload_message2", "The uploaded file format is incorrect, please upload Excel file!");
                request.getRequestDispatcher("/insert.jsp").forward(request, response);
            }
        } else { // 用户上传的是单个课程的信息，处理课程信息...
            Course course = new Course();
            Classroom classroom = new Classroom();
            String name = request.getParameter("name");
            String startWeek = request.getParameter("startWeek");
            String endWeek = request.getParameter("endWeek");
            String weekDay = request.getParameter("weekDay");
            String schooltime = request.getParameter("schooltime");
            String detail = request.getParameter("detail"); //可以为空
            String classroomNumber = request.getParameter("classroomNumber");

            if (name != null && startWeek != null && endWeek != null && weekDay != null && schooltime != null && classroomNumber != null) {
                course.setName(name);
                course.setStartWeek(Integer.parseInt(startWeek));
                course.setEndWeek(Integer.parseInt(endWeek));
                course.setWeekDay(Integer.parseInt(weekDay));
                course.setSchooltime(Integer.parseInt(schooltime));
                course.setDetail(detail);
                classroom.setNumber(Integer.parseInt(classroomNumber));

                boolean flag2 = classroomCourseService.insertClassroomCourse1(courseService.insertCourse(course), classroomservice.insertClassroom(classroom));
                if (flag2) { //插入成功,数据库中之前不存在该课程信息
                    request.setAttribute("upload_message1", "Upload Successfully!");
                    request.getRequestDispatcher("/insert.jsp").forward(request, response);
                } else { //插入失败,数据库中已经存在该课程信息
                    request.setAttribute("upload_message1", "Failed To Upload!");
                    request.getRequestDispatcher("/insert.jsp").forward(request, response);
                }
            } else { //所需的参数缺失,上传失败
                request.setAttribute("upload_message1", "Failed To Upload!");
                request.getRequestDispatcher("/insert.jsp").forward(request, response);
            }
        }
    }


}