package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {
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
        User user = service.login(username, password);
        String remember = request.getParameter("remember");
        if (user != null) { // User exists and password is correct
            if ("1".equals(remember)) { // Remember user account and password, using Cookie in this case
                Cookie c_username = new Cookie("username", username);
                Cookie c_password = new Cookie("password", password);
                c_username.setMaxAge(60 * 60 * 24 * 7);
                c_password.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(c_username);
                response.addCookie(c_password);
            }
            if (username.equals("Administrator")) { // Set permissions for the administrator account
                service.upgradeRole(user);
            }
            HttpSession session = request.getSession();
            session.setAttribute("user", user); // Store the logged-in user object in the session for accessing user information throughout the conversation
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/showCourseTableServlet");
        } else {
            request.setAttribute("login_msg", "The user name or password is incorrect");
            request.getRequestDispatcher("/login.jsp").forward(request, response);

        }
    }


}
