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
    private final UserService service = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String email = request.getParameter("email");
        String role = "user"; // By default, newly registered users are not assigned the administrator role.
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setRole(role);
        boolean flag = service.register(user);
        if (flag) { // Registration successful
            request.setAttribute("register_msg", "Registration successful. Please login");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } else { //Registration failed
            request.setAttribute("register_msg", "Registration failed. The username already exists");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }

    }


}