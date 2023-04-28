package ie.ucd.comp2013J.web;


import ie.ucd.comp2013J.pojo.User;
import ie.ucd.comp2013J.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;


@WebServlet(urlPatterns = "/loginServlet")
public class LoginServlet extends HttpServlet {
    private UserService service = new UserService();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doGet(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = service.login(username, password);
        String remember = request.getParameter("remember");
        if (user != null) { //用户名存在且密码正确
            if ("1".equals(remember)) { //记住用户的账户和密码,此时使用Cookie
                Cookie c_username = new Cookie("username", username);
                Cookie c_password = new Cookie("password", password);
                c_username.setMaxAge(60 * 60 * 24 * 7);
                c_password.setMaxAge(60 * 60 * 24 * 7);
                response.addCookie(c_username);
                response.addCookie(c_password);
            }
            HttpSession session = request.getSession();
            session.setAttribute("user", user); //将本次登录的用户的对象存储在session中,以便于在整个对话期间都可以访问该用户对象的信息
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/selectAllServlet此处的Servlet名称待定,但要转发到负责呈现最初课程信息的Servlet里");
        } else {
            request.setAttribute("login_msg", "用户名或密码错误");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            String contextPath = request.getContextPath();
            response.sendRedirect(contextPath + "/login.jsp");
        }
    }


}
