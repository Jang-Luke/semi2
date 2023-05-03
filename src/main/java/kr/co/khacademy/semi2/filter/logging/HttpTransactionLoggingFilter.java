package kr.co.khacademy.semi2.filter.logging;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import kr.co.khacademy.semi2.filter.BaseFilter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebFilter(filterName = "HttpTransactionLoggingFilter")
@Slf4j
public class HttpTransactionLoggingFilter extends BaseFilter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
        throws IOException, ServletException {
        RequestMessage requestMessage = RequestMessage.of((HttpServletRequest) request);
        log.info(requestMessage.toJson());
        chain.doFilter(request, response);
    }
}
