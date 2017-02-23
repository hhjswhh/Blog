package tech.acodesigner.servlet;

import tech.acodesigner.dao.CategoryDao;
import tech.acodesigner.dto.CategoryDto;
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
 * Created by 77239 on 2017/2/16/0016.
 */
@WebServlet(name = "CategoryManageServlet", urlPatterns = "/categoryManage")
public class CategoryManageServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String action = request.getParameter("action");
        if (action.equals("save")) {
            String id = request.getParameter("categoryId");
            String name = request.getParameter("name");
            try {
                if (id != null && !id.equals("")) {
                    CategoryDao.updateCategory(Integer.parseInt(id), name);
                } else {
                    CategoryDao.saveCategory(name);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            response.sendRedirect("categoryManage");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        try {
            String action = request.getParameter("action");
            if (action == null || action.equals("")) {
                ArrayList<CategoryDto> categories = CategoryDao.getCategories();
                request.setAttribute("categories", categories);
                request.setAttribute("mainPage", "categoryManage.jsp");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
            } else if (action.equals("preSave")) {
                String id = request.getParameter("categoryId");
                if (id != null && !id.equals("")) {
                    CategoryPo category = CategoryDao.getCategory(Integer.parseInt(id));
                    request.setAttribute("category", category);
                }
                request.setAttribute("mainPage", "categorySaveManage.jsp");
                request.getRequestDispatcher("manage.jsp").forward(request, response);
            } else if (action.equals("delete")) {
                String id = request.getParameter("categoryId");
                CategoryDao.deleteCategory(Integer.parseInt(id));
                request.getRequestDispatcher("categoryManage?action").forward(request, response);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
