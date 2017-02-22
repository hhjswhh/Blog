package tech.acodesigner.filter;

import tech.acodesigner.dto.UserDto;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by 77239 on 2017/2/11/0011.
 */
@WebFilter(filterName = "LoginFilter", urlPatterns = "/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        HttpSession session = request.getSession();
        UserDto user = (UserDto) session.getAttribute("curUser");
        String path = request.getServletPath();
        if (user != null) {
            if (user.getType() == 0 && path.contains("anage")) {
                response.sendRedirect("home");
            } else {
                chain.doFilter(req, resp);
            }
        } else if (path.contains("anage")) {
            response.sendRedirect("home");
        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
