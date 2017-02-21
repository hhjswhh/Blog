package tech.acodesigner.servlet;

import tech.acodesigner.dao.ArticleDao;
import tech.acodesigner.dto.ArticleDto;
import tech.acodesigner.dto.ArticleLiteDto;
import tech.acodesigner.util.PageUtil;
import tech.acodesigner.util.PropertiesUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 77239 on 2017/2/11/0011.
 */
@WebServlet(name = "HomeServlet", urlPatterns = "/home")
public class HomeServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            ArrayList<ArticleLiteDto> recentArticles = ArticleDao.getRecentArticles();
            getServletContext().setAttribute("recentArticles", recentArticles);
            String page = request.getParameter("page");
            String search = request.getParameter("search");
            String s_content = request.getParameter("s_content");
            if (page == null || page == "") {
                page = "1";
            }
            PageUtil pageUtil = new PageUtil(Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
            ArrayList<ArticleDto> articles = null;
            if (search!=null && search.equals("true") && s_content != null && !s_content.equals("")) {
                articles = ArticleDao.search(s_content);
            } else {
                articles = ArticleDao.pagination(pageUtil);
            }
            int total = ArticleDao.count();
            String pageCode = this.genPagination(total, Integer.parseInt(page), Integer.parseInt(PropertiesUtil.getValue("pageSize")));
            request.setAttribute("pageCode", pageCode);
            request.setAttribute("articles", articles);
            request.setAttribute("mainPage", "article.jsp");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String genPagination(int totalNum, int curPage, int pageSize) {
        int totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        pageCode.append("<ul class=\"pagination\">");
        pageCode.append("<li class='waves-effect'><a href='home?page=1'><i class=\"material-icons\">first_page</i></a></li>");
        if (curPage == 1) {
            pageCode.append("<li class=\"disabled\"><a><i class=\"material-icons\">chevron_left</i></a></li>");
        } else {
            pageCode.append("<li class='waves-effect'><a href=\"home?page=" + (curPage - 1) + "\"><i class=\"material-icons\">chevron_left</i></a></li>");
        }
        for (int i = curPage - 2; i <= curPage + 2; i++) {
            if (i < 1 || i > totalPage) {
                continue;
            }
            if (i == curPage) {
                pageCode.append("<li class='active waves-effect'><a href='#'>" + i
                        + "</a></li>");
            } else {
                pageCode.append("<li class='waves-effect'><a href='home?page=" + i + "'>" + i
                        + "</a></li>");
            }
        }
        if (curPage == totalPage) {
            pageCode.append("<li class='disabled'><a><i class=\"material-icons\">chevron_right</i></a></li>");
        } else {
            pageCode.append("<li class='waves-effect'><a href='home?page=" + (curPage + 1)
                    + "'><i class=\"material-icons\">chevron_right</i></a></li>");
        }
        pageCode.append("<li class='waves-effect'><a href='home?page=" + totalPage + "'><i class=\"material-icons\">last_page</i></a></li>");
        pageCode.append("</ul>");
        return pageCode.toString();
    }
}
