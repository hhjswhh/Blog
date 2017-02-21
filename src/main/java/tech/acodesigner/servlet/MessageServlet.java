package tech.acodesigner.servlet;

import tech.acodesigner.dao.MessageDao;
import tech.acodesigner.dto.MessageDto;
import tech.acodesigner.dto.UserDto;
import tech.acodesigner.po.MessagePo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 张秦遥 on 2017/2/20/0020.
 */
@WebServlet(name = "MessageServlet", urlPatterns = "/message")
public class MessageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action.equals("leaveMessage")) {
                ArrayList<MessageDto> leaveMessages = MessageDao.getLeaveMessages();
                request.setAttribute("type", 1);
                request.setAttribute("replytype", 3);
                request.setAttribute("pid", 0);
                request.setAttribute("articleId", 0);
                request.setAttribute("messages", leaveMessages);
                request.setAttribute("mainPage", "message.jsp");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            } else if (action.equals("save")) {
                String type = request.getParameter("type");
                String pid = request.getParameter("pid");
                String content = request.getParameter("comment");
                String articleId = request.getParameter("articleId");
                HttpSession session = request.getSession();
                MessagePo message = new MessagePo();
                UserDto user = (UserDto) session.getAttribute("curUser");

                message.setUserId(user.getId());
                message.setContent(content);
                message.setMessageType(Integer.parseInt(type));
                message.setPid(Integer.parseInt(pid));
                MessageDao.save(message);
                if (Integer.parseInt(type) % 2 != 0) {
                    response.sendRedirect("message?action=leaveMessage");
                } else {
                    response.sendRedirect("article?articleId=" + articleId);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}
