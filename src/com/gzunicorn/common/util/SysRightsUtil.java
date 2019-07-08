package com.gzunicorn.common.util;

import java.util.*;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.apache.commons.beanutils.BasicDynaClass;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.DynaProperty;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspException;
import java.io.IOException;

import com.gzunicorn.hibernate.*;
import com.gzunicorn.hibernate.viewmanager.Viewuserrolenodewrite;
import com.gzunicorn.common.*;

/**
 * 
 * Created on 2005-10-20
 * <p>
 * Title: Ȩ������
 * </p>
 * <p>
 * Description: ϵͳȨ������
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * <p>
 * Company:��������
 * </p>
 * 
 * @author wyj
 * @version 1.0
 */

public class SysRightsUtil {

	public static String NODE_ID_FORWARD = "n";// ���ܽڵ�ǰ׺
	
	/**
	 * ��������д��ťȨ������
	 * 
	 * @param userID
	 * @return DynaBean
	 */

	public static DynaBean toolBarRights(String userID){
	
		
		DynaBean toolBarRights = null;
		Session session = null;
		Query query = null;
		ArrayList list = null;
		try{
			session = HibernateUtil.getSession();
			query = session.createQuery("FROM Viewuserrolenodewrite WHERE userid=:userID AND nodedata<>:nodedata1 AND nodedata<>:nodedata2");
			
			query.setString("userID",userID);
			query.setString("nodedata1","NULL");
			query.setString("nodedata2","");
			
			list = (ArrayList)query.list();
			if(list!=null && list.size()>0 ){
				
				DynaProperty[] props = new DynaProperty[list.size()];
				String[] propsName = new String[list.size()];
				String[] writeFlag = new String[list.size()];
				
				for(int i=0; i<list.size(); i++){
					Viewuserrolenodewrite viewuserrolenodewrite = new Viewuserrolenodewrite();
					viewuserrolenodewrite = (Viewuserrolenodewrite)list.get(i);
					if(viewuserrolenodewrite != null){
						props[i] = new DynaProperty(NODE_ID_FORWARD + viewuserrolenodewrite.getNodedata());
						// NodedataΪͬһģ��ı�����ͬһģ���NodeID�ǲ�һ����
						propsName[i] = NODE_ID_FORWARD + viewuserrolenodewrite.getNodedata();
						writeFlag[i] = viewuserrolenodewrite.getWriteflag();
					}
				}
				BasicDynaClass dynaClass = new BasicDynaClass("toolBarRights", null, props);
				
				toolBarRights = dynaClass.newInstance();
				for(int i=0; i<propsName.length; i++){
					toolBarRights.set(propsName[i],writeFlag[i]);
				}
			}
			
		}catch(Exception e){
			DebugUtil.println(e.getMessage());
		}finally{
			if(session != null){
				try {
					session.close();
				} catch (HibernateException e) {
					DebugUtil.println(e.getMessage());
				}
			}
		}
		
		return toolBarRights;
	}

	/**
	 * ģ�����Ȩ�޹��ˣ���ֹ�û�ֱ������URL������ģ��
	 * 
	 * @param request
	 * @param response
	 * @param module
	 *            ģ�����
	 * @param errorPage
	 *            ���س����ҳ�棨û��Ȩ�ޣ�
	 * @throws JspException
	 */
	public static void filterModuleRight(	HttpServletRequest request,
											HttpServletResponse response,
											String module,
											String errorPage)
	throws JspException{

		DynaBean toolBarRights  = (DynaBean)request.getSession().getAttribute(SysConfig.TOOLBAR_RIGHT);
		boolean rightURL = false;// �Ƿ�����URL���ʱ�־
		
		if(toolBarRights != null){
			try{
				String allowFlag = (String)toolBarRights.get(module);	
				if(allowFlag != null && (allowFlag.equals("N") || allowFlag.equals("Y"))){
					rightURL = true;
				}
			}catch(IllegalArgumentException e){
				//����������
				rightURL = false;
			}

		}
		if(!rightURL){
			// URL��ͨ������������ҳ�棨ûȨ���ʣ�
			String url = errorPage;
		      if(errorPage == null || errorPage.trim().equals("")){
		        url = "/" + SysConfig.WEB_APPNAME;
		        url += request.getSession().getServletContext().getInitParameter("ErrorRightPage");
		        DebugUtil.println("��ǰ�û�ûȨ���� :" + url);
		      }
		      try{
		        response.sendRedirect(url);
		      }catch(IOException e){
		        throw new JspException(e);
		      }
		}
		
		
		
	}
}
