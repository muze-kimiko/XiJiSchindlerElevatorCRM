/*
 * Created on 2005-8-5
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.struts.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import com.gzunicorn.common.util.SysConfig;


/**
 * Created on 2005-7-12
 * <p>Title:	</p>
 * <p>Description:	�˳�ϵͳ</p>
 * <p>Copyright: Copyright (c) 2005
 * <p>Company:�����Ƽ�</p>
 * @author wyj
 * @version	1.0
 * 2007-11-28 cwy �޸�(�����ͨ��OA��¼ϵͳ��ʽ��,����ע��)
 */
public final class LogoutAction extends Action {

	
// --------------------------------------------------------- Public Methods


public ActionForward execute(ActionMapping mapping,
							 ActionForm form,
							 HttpServletRequest request,
							 HttpServletResponse response)
	throws IOException, ServletException {



	// Invalidate the current session and create a new one
	HttpSession session = request.getSession();
	//String logflag = (String)session.getAttribute("logflag");//�жϵ�¼��ʽ��־,����"1"Ϊϵͳֱ�ӵ�¼��ʽ,"2"ͨ��OA��¼)
	//�����OA��¼��,�û�����ע��
	//if(logflag != null && logflag.equals("2"))
	//{
		
	//}
	//�����ϵͳֱ�ӵ�¼��,Ҫע��
	//else{
		
		session.invalidate();
		session = request.getSession(true);
		session.removeAttribute(SysConfig.LOGIN_USER_INFO);
	//}
	// Forward control back to the logout.jsp
	return (mapping.findForward("success"));
	

}


}
