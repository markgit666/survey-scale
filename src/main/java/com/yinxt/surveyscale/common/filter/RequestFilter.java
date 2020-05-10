package com.yinxt.surveyscale.common.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@WebFilter(urlPatterns = "/*", filterName = "requestFilter")
public class RequestFilter implements Filter {

    private static final String REQUEST_ID = "requestId";

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        MDC.put(REQUEST_ID, UUID.randomUUID().toString().replace("-", ""));
        filterChain.doFilter(servletRequest, servletResponse);
    }

    @Override
    public void destroy() {

    }
}
