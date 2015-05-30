package by.bazhanau.kr2;

import Bazhanau.Logging.ConsoleCatcher;
import by.bazhanau.kr2.models.AggregateData;
import by.bazhanau.kr2.models.Deal;
import by.bazhanau.kr2.models.DealType;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.jsp.jstl.fmt.LocalizationContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by Аляксандр on 22.05.15.
 */

@WebServlet(name = "MainServlet", urlPatterns = "/main/*")
public class MainServlet extends HttpServlet {
    SqlManager sqlManager = new SqlManager(new ConsoleCatcher());

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DealType dealType = sqlManager.getDealType(Integer.parseInt(request.getParameter("dealType")));
        ArrayList<Deal> deals = sqlManager.getDeals(dealType);
        AggregateData data = sqlManager.getAggregateData(dealType);
        request.setAttribute("deals", deals);
        request.setAttribute("dealType", dealType);
        request.setAttribute("aggData", data);

        Locale locale = getLocale(request.getSession());
        ResourceBundle bundle = ResourceBundle.getBundle("lang", locale);
        request.setAttribute("lang", new LocalizationContext(bundle));

        getServletContext().getRequestDispatcher("/result.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ArrayList<DealType> dealTypes = sqlManager.getDialTypes();
        request.setAttribute("dealTypes", dealTypes);
        getServletContext().getRequestDispatcher("/index.jsp").forward(request, response);
    }

    private Locale getLocale(HttpSession sessionState){
        Locale res = (Locale) sessionState.getAttribute("lang");
        if(res == null){
            res = Locale.getDefault();
        }
        return res;
    }
}
