package by.bazhanau.task10;

import Bazhanau.FileService.FileService;
import Bazhanau.FileService.IFileService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Handler extends HttpServlet
{
    private static final String PARAMETER_STAGE_NUMBER = "stageNumber";
    private static final String PARAMETER_TEXT = "text";
    private static final String PARAMETER_KEY = "key";
    private static final String ATTRIBUTE_MESSAGE = "message";
    private static final String ATTRIBUTE_RESULT = "result";
    private static final String SESSION_ATTRIBUTE_TEXT = "text";
    private static final String SESSION_ATTRIBUTE_KEY = "key";
    private IFileService fileService = new FileService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        process(request, response);
    }

    private void process(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.setCharacterEncoding("utf-8");
        try
        {
            int stage = Integer.parseInt(request.getParameter(PARAMETER_STAGE_NUMBER));
            switch (stage)
            {
                case 0:
                    request.getRequestDispatcher("/1.jsp").forward(request, response);
                    return;
                case 1:{
                    String text = request.getParameter(PARAMETER_TEXT);
                    if (text == null || text.isEmpty())
                    {
                        request.setAttribute(ATTRIBUTE_MESSAGE, "Вы не ввели текст");
                        request.getRequestDispatcher("/1.jsp").forward(request, response);
                    }
                    else
                    {
                        request.getSession().setAttribute(SESSION_ATTRIBUTE_TEXT, text);
                        request.getRequestDispatcher("/2.jsp").forward(request, response);
                    }
                    return;}
                case 2:{
                    String key = request.getParameter(PARAMETER_KEY);
                    if (key == null || key.isEmpty())
                    {
                        request.setAttribute(ATTRIBUTE_MESSAGE, "Вы не ввели ключ");
                        request.getRequestDispatcher("/2.jsp").forward(request, response);
                    }
                    else
                    {
                        request.getSession().setAttribute(SESSION_ATTRIBUTE_KEY, key);
                        String text = (String) request.getSession().getAttribute(SESSION_ATTRIBUTE_TEXT);
                        String result = fileService.encodeText(text, key);
                        request.setAttribute(ATTRIBUTE_RESULT, result);
                        request.getRequestDispatcher("/output.jsp").forward(request, response);
                    }
                    return;}
                default:
                    request.getRequestDispatcher("/index.jsp").forward(request, response);
            }
        }
        catch (Exception e)
        {
            request.getSession().invalidate();
            request.getRequestDispatcher("/1.jsp").forward(request, response);
        }
    }
}
