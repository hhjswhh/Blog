package tech.acodesigner.servlet;

import tech.acodesigner.dao.ArticleDao;
import tech.acodesigner.dto.AboutDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/**
 * Created by 77239 on 2017/2/13/0013.
 */
@WebServlet(name = "AboutManageServlet", urlPatterns = "/aboutManage")
public class AboutManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action.equals("save")) {
            try {
                String content = request.getParameter("content");
                ArticleDao.updateAbout(content);
                response.sendRedirect("aboutManage");
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        try {
            AboutDto about = ArticleDao.getAbout();
            request.setAttribute("about", about);
            if (action == null || action.equals("")) {
                request.setAttribute("mainPage", "aboutManage.jsp");
            } else if (action.equals("preSave")) {
                request.setAttribute("mainPage", "aboutSaveManage.jsp");
            }
            request.getRequestDispatcher("manage.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
