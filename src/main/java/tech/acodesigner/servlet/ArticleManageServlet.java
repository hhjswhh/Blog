package tech.acodesigner.servlet;

import tech.acodesigner.dao.ArticleDao;
import tech.acodesigner.dao.CategoryDao;
import tech.acodesigner.dto.AboutDto;
import tech.acodesigner.dto.ArticleDto;
import tech.acodesigner.dto.ArticleLiteDto;
import tech.acodesigner.dto.CategoryDto;
import tech.acodesigner.po.ArticlePo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by 77239 on 2017/2/16/0016.
 */
@WebServlet(name = "ArticleManageServlet", urlPatterns = "/articleManage")
public class ArticleManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action.equals("save")) {
                String title = request.getParameter("title");
                String content = request.getParameter("content");
                String categoryId = request.getParameter("category");
                String articleId = request.getParameter("articleId");
                ArticlePo article = new ArticlePo();

                article.setTitle(title);
                article.setContent(content);
                article.setCategoryId(Integer.parseInt(categoryId));
                article.setImage("root");
                if (articleId != null && !articleId.equals("")) {
                    article.setClicks(ArticleDao.getArticleById(Integer.parseInt(articleId)).getClicks());
                    article.setArticleId(Integer.parseInt(articleId));
                    ArticleDao.updateArticle(article);
                } else {
                    ArticleDao.saveArticle(article);
                }
                response.sendRedirect("articleManage");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        try {
            if (action == null || action.equals("")) {
                ArrayList<ArticleDto> articles = ArticleDao.getArticles();
                request.setAttribute("articles", articles);
                request.setAttribute("mainPage", "articleManage.jsp");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
            } else if (action.equals("preSave")) {
                String id = request.getParameter("articleId");
                if (id != null && !id.equals("")) {
                    ArticleDto article = ArticleDao.getArticleById(Integer.parseInt(id));
                    request.setAttribute("article", article);
                }
                ArrayList<CategoryDto> categories = CategoryDao.getCategories();
                request.setAttribute("categories", categories);
                request.setAttribute("mainPage", "articleSaveManage.jsp");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                String articleId = request.getParameter("articleId");
                ArticleDao.deleteArticle(Integer.parseInt(articleId));
                request.getRequestDispatcher("articleManage?action=").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
