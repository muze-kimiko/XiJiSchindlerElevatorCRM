/*
 * Created on 2005-7-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.web.filters;

/**
 * Created on 2005-7-12
 * <p>
 * Title: ����Web�Ĺ��÷���
 * </p>
 * <p>
 * Description: ʵ��url��������ʱ�ַ�����ת��(��Ҫ���form��������������)
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * <p>
 * Company:�����Ƽ�
 * </p>
 * 
 * @author wyj
 * @version 1.0
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.DynaBean;

import com.gzunicorn.common.util.*;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

public class LogonFilter implements Filter {

    protected FilterConfig filterConfig = null;

    protected boolean ignore = true;

    private String webApp = "/" + SysConfig.WEB_APPNAME + "/";
    private String loginActionName = webApp + SysConfig.LOGIN_ACTION_NAME;
    private String logoutpage = webApp+SysConfig.LOGIN_OUT_PAGE;

    public void destroy() {
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
        
        HttpServletRequest hreq = (HttpServletRequest) request;
        HttpServletResponse hres = (HttpServletResponse) response;
        
        DynaBean toolBarRights;

        /*** �������Է�ֹ��¼2�� 
         * && !hreq.getRequestURI().equals(this.loginActionName)
         * ���޸�Ϊ 
         * && !hreq.getRequestURI().startsWith(this.loginActionName) 
         * ���Է�ֹ��¼2�� 
         */
        HttpSession session = hreq.getSession();
        if (!hreq.getRequestURI().equals(this.webApp)
                && !hreq.getRequestURI().startsWith(this.loginActionName) 
                && !hreq.getRequestURI().equals(logoutpage)
                && session.getAttribute(SysConfig.LOGIN_USER_INFO) == null) {
            hres.sendRedirect(webApp+"logout/logout.jsp");
            return;
        }
//      ���õ�ǰ�û��Ķ�дȨ��
		if (session.getAttribute(SysConfig.LOGIN_USER_INFO) != null
				&& session.getAttribute(SysConfig.TOOLBAR_RIGHT) == null) {
			// �е�¼�û���Ϣ��û��ToolbarȨ�޿��Ƶ���Ϣ

			toolBarRights = SysRightsUtil.toolBarRights(((ViewLoginUserInfo) session
								.getAttribute(SysConfig.LOGIN_USER_INFO)).getUserID());
			//��ŵ�Session��
			session.setAttribute(SysConfig.TOOLBAR_RIGHT,toolBarRights);
			toolBarRights = (DynaBean)session.getAttribute(SysConfig.TOOLBAR_RIGHT);
						
		}
        chain.doFilter(request, response);

    }

    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
    }

}
