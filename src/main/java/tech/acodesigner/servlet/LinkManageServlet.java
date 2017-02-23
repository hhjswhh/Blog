package tech.acodesigner.servlet;

import tech.acodesigner.dao.LinkDao;
import tech.acodesigner.po.LinkPo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 77239 on 2017/2/22/0022.
 */
@WebServlet(name = "LinkManageServlet", urlPatterns = "/linkManage")
public class LinkManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        try {
            String action = request.getParameter("action");
            if (action == null || action.equals("")) {
                ArrayList<LinkPo> links = LinkDao.getAllLinks();
                request.setAttribute("links", links);
                request.setAttribute("mainPage", "linkManage.jsp");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
            } else if (action.equals("preSave")) {
                String id = request.getParameter("linkId");
                if (id != null && !id.equals("")) {
                    LinkPo link = LinkDao.getLink(Integer.parseInt(id));
                    request.setAttribute("link", link);
                }
                request.setAttribute("mainPage", "linkSaveManage.jsp");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                String id = request.getParameter("linkId");
                LinkDao.deleteLink(Integer.parseInt(id));
                request.getRequestDispatcher("linkManage?action").forward(request, response);
            } else if (action.equals("save")) {
                String id = request.getParameter("linkId");
                String name = request.getParameter("name");
                String url = request.getParameter("url");
                LinkPo link = new LinkPo();
                link.setLinkName(name);
                link.setUrl(url);
                if (id != null && !id.equals("")) {
                    link.setLinkId(Integer.parseInt(id));
                    LinkDao.updateLink(link);
                } else {
                    LinkDao.saveLink(link);
                }
                request.getRequestDispatcher("linkManage?action").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
