package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(urlPatterns = "/registerServlet")
public class RegisterServlet extends HttpServlet {
    private UserService service = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role = "user"; //默认新注册的用户都不是管理员身份
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        boolean flag = service.register(user);
        if (flag) { //注册成功
            request.setAttribute("register_msg", "注册成功,请登录");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else { //注册失败
            request.setAttribute("register_msg", "注册失败,用户名已存在");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }

    }


}