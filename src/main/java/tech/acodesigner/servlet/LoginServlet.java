package tech.acodesigner.servlet;

import tech.acodesigner.dao.UserDao;
import tech.acodesigner.dto.UserDto;
import tech.acodesigner.po.UserPo;
import tech.acodesigner.util.MD5Util;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

/**
 * Created by 77239 on 2017/2/11/0011.
 */
@WebServlet(name = "LoginServlet", urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        if (action.equals("register")) {
            UserPo user = new UserPo();
            user.setUsername(request.getParameter("username"));
            try {
                user.setPassword(MD5Util.encoderPassword(request.getParameter("password")));
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
//            user.setImage();
            try {
                UserDao.save(user);
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            response.sendRedirect("login");
        } else {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            request.setAttribute("mainPage", "loginCard.jsp");
            try {
                UserDto user = UserDao.login(username, MD5Util.encoderPassword(password));
                if (user == null) {
                    request.getRequestDispatcher("login.jsp").forward(request, response);
                } else {
                    Cookie c = new Cookie("user", username + "#" + password);
                    c.setMaxAge(60 * 60 * 24 * 7);
                    response.addCookie(c);
                    session.setAttribute("user", user);
                    if (user.getType() == 0) {
                        response.sendRedirect("home");
                    } else {
                        response.sendRedirect("manage");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String action = request.getParameter("action");
        if (action == null || action == "") {
            request.setAttribute("mainPage", "loginCard.jsp");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (action.equals("preRegister")) {
            request.setAttribute("mainPage", "registerCard.jsp");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        } else if (action.equals("quit")) {
            session.invalidate();
            response.sendRedirect("home");
        }
    }
}
