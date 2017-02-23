package tech.acodesigner.servlet;

import tech.acodesigner.dao.CategoryDao;
import tech.acodesigner.dao.MessageDao;
import tech.acodesigner.dto.CategoryDto;
import tech.acodesigner.dto.MessageDto;
import tech.acodesigner.po.CategoryPo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 张秦遥 on 2017/2/21/0021.
 */
@WebServlet(name = "MessageManageServlet", urlPatterns = "/messageManage")
public class MessageManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        try {
            String action = request.getParameter("action");
            if (action == null || action.equals("")) {
                ArrayList<MessageDto> messages = MessageDao.getAllMessages();
                request.setAttribute("messages", messages);
                request.setAttribute("mainPage", "messageManage.jsp");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                String id = request.getParameter("messageId");
                MessageDao.delete(Integer.parseInt(id));
                request.getRequestDispatcher("messageManage?action").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
