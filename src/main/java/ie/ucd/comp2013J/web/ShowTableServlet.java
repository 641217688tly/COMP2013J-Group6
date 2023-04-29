package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.service.ClassroomService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//该Servlet用于向前端页面发送一个List<Course>或者List<Classroom>
@WebServlet(urlPatterns = "/showTableServlet")
public class ShowTableServlet extends HttpServlet {
    private ClassroomService service = new ClassroomService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request,response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");

    }


}