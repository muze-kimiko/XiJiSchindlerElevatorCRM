//Created by MyEclipse Struts
// XSL source (default): platform:/plugin/com.genuitec.eclipse.cross.easystruts.eclipse_3.8.3/xslt/JavaClass.xsl

package com.gzunicorn.struts.action;
 

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Locale;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.Action;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.util.MessageResources;
import org.hibernate.*;

import com.gzunicorn.bean.UserInfoBean;
import com.gzunicorn.common.util.*;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

import com.gzunicorn.struts.form.LogonForm;

/**
 * MyEclipse Struts Creation date: 07-10-2005 �û���¼ XDoclet definition:
 *								   2007-11-27 cwy �޸� 
 * @struts:action path="/logon" name="logonForm" input="/logon/logon.jsp"
 *                scope="request" validate="true"
 * @struts:action-forward name="error" path="/logon/logon.jsp"
 * @struts:action-forward name="success" path="/main/main.jsp"
 */
public class LogonAction extends Action {

    /**
     * Method execute
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return ActionForward
     */
    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response) {

        Locale locale = getLocale(request);
        MessageResources messages = getResources(request);
        HttpSession httpSession = request.getSession();
        ActionErrors errors = new ActionErrors();
        Log log = LogFactory.getLog(LogonAction.class);
        LogonForm logonForm = (LogonForm) form;
        ViewLoginUserInfo UserInfo = new ViewLoginUserInfo();
        Session session = null;
        ActionForward forward = null;

        String userID = request.getParameter("userID");
        String passwd = request.getParameter("passwd");
        String logflag = "1";//�ж���¼�û�����ʲô��ʽ��¼,����"1"Ϊϵͳ��¼,"2"ΪOA�����¼
        
        ArrayList userList = null;
        
        //ϵͳԭ����¼���ֿ�ʼ
        try {
        	String gruser = "gruser";//oa��¼�����û�,������CRM��¼ҳ���¼
            session = HibernateUtil.getSession();
            
            Query query = session.createQuery("from ViewLoginUserInfo a where a.userID = :userID and a.userID <> :gruser and a.enabledFlag = :enabledFlag");
            query.setString("userID", userID);
            query.setString("gruser",gruser);
            query.setString("enabledFlag","Y");
            userList = (ArrayList) query.list();
            // //�ж��û��Ƿ���ȷ
            if (userList == null || userList.isEmpty() || userList.size() == 0) {
                DebugUtil.println("�Ƿ��û���¼!");
                errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("logon.user.error"));
            } else {
            	ViewLoginUserInfo userInfo = (ViewLoginUserInfo) userList.get(0);
                // �ж������Ƿ���ȷ
                if ( passwd == null || !new CryptUtil().decode(passwd, "LO").equals(userInfo.getPasswd())) {
                    DebugUtil.println("�û������������!");
                    errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("logon.passwd.error"));
                } else {
                    httpSession.setAttribute(SysConfig.LOGIN_USER_INFO,userInfo);
                    DebugUtil.print(userInfo.getUserName()+ "�û���¼��¼�ɹ�����ӦSessionID:" + httpSession.getId());
                    
                    //��ServletContext ��ӵ�¼�û�
					UserInfoBean userInfoBean = new UserInfoBean();
					ArrayList AllUserInfo = new ArrayList();

					userInfoBean.setSessionID(httpSession.getId());
					userInfoBean.setUserID(userInfo.getUserID());
					userInfoBean.setUserName(userInfo.getUserName());
					userInfoBean.setRoleName(userInfo.getRoleName());
					userInfoBean.setStorageName(userInfo.getStorageName());
					userInfoBean.setComName(userInfo.getComName());
					
					try {
						userInfoBean.setLoginDate(CommonUtil.getToday());
					} catch (ParseException e) {
						DebugUtil.println(e.getMessage());
					}
					userInfoBean.setLoginTime(CommonUtil.getTodayTime());
					userInfoBean.setIpAddress(request.getRemoteAddr());

					AllUserInfo = (ArrayList) httpSession.getServletContext()
							.getAttribute(SysConfig.ALL_LOGIN_USER_INFO);
					if (AllUserInfo == null || AllUserInfo.isEmpty()) {
						AllUserInfo = new ArrayList();
						AllUserInfo.add(userInfoBean);
						httpSession.getServletContext().setAttribute(SysConfig.ALL_LOGIN_USER_INFO, AllUserInfo);
						DebugUtil.println("��ǰServletContext������һ���û�Session: "+ userInfoBean.getSessionID());
					}else{
						int cyc = 0;
						for (int i = 0; i < AllUserInfo.size(); i++) {
							//�� SessionID �����ڲ��� ServletContext ��������һ���û�Session ID
							if (httpSession.getId().equals(((UserInfoBean) AllUserInfo.get(i)).getSessionID())) {
								cyc++;
							}
						}
						if(cyc == 0){
							AllUserInfo.add(userInfoBean);
							httpSession.getServletContext().setAttribute(SysConfig.ALL_LOGIN_USER_INFO, AllUserInfo);
							DebugUtil.println("��ǰServletContext������һ���û�Session: "+ userInfoBean.getSessionID());

						}
					}  
                }                       
            }

        } catch (DataStoreException dex) {
            log.error(dex.getMessage());
            DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
        } catch (Exception hex) {
            log.error(hex.getMessage());
            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
        } finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (HibernateException hex) {
                log.error(hex.getMessage());
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }

        if (!errors.isEmpty()) {
            saveErrors(request, errors);
            return (mapping.getInputForward());
        }

	    //forward = mapping.findForward("success");
	    forward = mapping.findForward("success_2");
        //ϵͳԭ����¼���ֽ���  
        
        httpSession.setAttribute("logflag",logflag);
        return forward;
    }
    
}
