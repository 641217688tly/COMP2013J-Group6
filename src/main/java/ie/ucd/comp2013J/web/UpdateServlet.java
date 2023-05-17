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
        // 获取user对象
        User user = (User) request.getSession().getAttribute("user");
        // 获取参数
        int courseId = Integer.parseInt(request.getParameter("courseId"));
        int classroomId = Integer.parseInt(request.getParameter("classroomId"));
        // 判断用户角色
        if (user != null && "administrator".equals(user.getRole())) {
            // 获取参数
            String name = request.getParameter("name");
            String startWeek = request.getParameter("startWeek");
            String endWeek = request.getParameter("endWeek");
            String weekDay = request.getParameter("weekDay");
            String schooltime = request.getParameter("schooltime");
            String classroomNumber = request.getParameter("classroomNumber");
            String detail = request.getParameter("detail");
            // 判断参数是否为空
            if (name != null && startWeek != null && endWeek != null && weekDay != null && schooltime != null && classroomNumber != null && !name.isEmpty() && !startWeek.isEmpty() && !endWeek.isEmpty() && !weekDay.isEmpty() && !schooltime.isEmpty() && !classroomNumber.isEmpty()) {
                // 获取旧的course和classroom
                Course oldCourse = courseService.getByCourseId(courseId);
                Classroom oldClassroom = classroomService.getByClassroomId(classroomId);

                // 更新course
                Course newCourse = new Course();
                newCourse.setId(courseId);
                newCourse.setName(name);
                newCourse.setStartWeek(Integer.parseInt(startWeek));
                newCourse.setEndWeek(Integer.parseInt(endWeek));
                newCourse.setWeekDay(Integer.parseInt(weekDay));
                newCourse.setSchooltime(Integer.parseInt(schooltime));
                newCourse.setDetail(detail);
                courseService.updateCourse(newCourse);

                // 如果教室号发生变化
                if (!(Integer.parseInt(classroomNumber) == oldClassroom.getNumber())) { //classroom number也被更新
                    // 删除旧的关系
                    classroomCourseService.deleteByCourseIdAndClassroomId(courseId, classroomId);
                    // 插入新的教室
                    Classroom newClassroom = new Classroom();
                    newClassroom.setNumber(Integer.parseInt(classroomNumber));
                    newClassroom = classroomService.insertClassroom(newClassroom); //如果新的教室号对应的教室已经存在,则classroomService.insertClassroom(newClassroom)会返回已存在的classroom对象
                    // 重新在classroom_course表中建立关系
                    boolean flag = classroomCourseService.insertSingleClassroomCourse(newCourse, newClassroom);
                    if (flag) {
                        request.setAttribute("courseId", courseId);
                        request.setAttribute("classroomId", newClassroom.getId());
                        request.setAttribute("success_message", "更新成功!");
                        request.getRequestDispatcher("/update.jsp").forward(request, response);
                    } else {
                        request.setAttribute("courseId", courseId);
                        request.setAttribute("classroomId", classroomId);
                        request.setAttribute("failure_message3", "未知的原因导致更新失败,请联系管理员!");
                        request.getRequestDispatcher("/update.jsp").forward(request, response);
                    }
                }
                request.setAttribute("courseId", courseId);
                request.setAttribute("classroomId", classroomId);
                request.setAttribute("success_message", "更新成功!");
                request.getRequestDispatcher("/update.jsp").forward(request, response);
            } else {
                // 必填的参数为空
                request.setAttribute("courseId", courseId);
                request.setAttribute("classroomId", classroomId);
                request.setAttribute("failure_message2", "更新失败,必要的信息没有被提交");
                request.getRequestDispatcher("/update.jsp").forward(request, response);
            }
        } else {
            // 用户不是管理员
            request.setAttribute("courseId", courseId);
            request.setAttribute("classroomId", classroomId);
            request.setAttribute("failure_message1", "更新失败,你没有管理员权限!");
            request.getRequestDispatcher("/update.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
