package by.bazhanau.kr2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Locale;

/**
 * Created by Аляксандр on 22.05.15.
 */
@WebServlet(name = "LocaleServlet", urlPatterns = "/lang/*")
public class LocaleServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Locale locale = new Locale(request.getParameter("lang"));
        request.getSession().setAttribute("lang", locale);
        response.sendRedirect("/main/");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
