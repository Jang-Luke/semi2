package kr.co.khacademy.semi2.web.error;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/error")
@Slf4j
public class ErrorController extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String errorRequestUri = (String) req.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        Exception exception = (Exception) req.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        log.error(errorRequestUri, exception);
        req.getRequestDispatcher("/WEB-INF/views/error.jsp").forward(req, resp);
    }
}
