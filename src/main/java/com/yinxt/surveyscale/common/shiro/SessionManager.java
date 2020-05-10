package com.yinxt.surveyscale.common.shiro;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.Serializable;

public class SessionManager extends DefaultWebSessionManager {

    private static final String TOKEN = "Token";
    private static final String REFERENCED_SESSION_ID_SOURCE = "Stateless request";


    @Override
    protected Serializable getSessionId(ServletRequest request, ServletResponse response) {
        String sessionId = StringUtils.isEmpty(WebUtils.toHttp(request).getHeader(TOKEN)) ? request.getParameter(TOKEN) : WebUtils.toHttp(request).getHeader(TOKEN);
        if (StringUtils.isNotEmpty(sessionId)) {
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_SOURCE, REFERENCED_SESSION_ID_SOURCE);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID, sessionId);
            request.setAttribute(ShiroHttpServletRequest.REFERENCED_SESSION_ID_IS_VALID, Boolean.TRUE);
            return sessionId;
        } else {
            return super.getSessionId(request, response);
//            return null;
        }
    }

}
