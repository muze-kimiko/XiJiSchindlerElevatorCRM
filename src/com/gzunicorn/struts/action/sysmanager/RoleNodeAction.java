/*
 * Created on 2005-8-16
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.struts.action.sysmanager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;

import com.gzunicorn.bean.RoleNodeBean;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.sysmanager.Operationref;
import com.gzunicorn.hibernate.sysmanager.OperationrefKey;
import com.gzunicorn.hibernate.sysmanager.Rolenode;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.hibernate.viewmanager.Viewuseroperation;

/**
 * Created on 2005-7-12
 * <p>
 * Title: ��ɫȨ�޿���ģ��
 * </p>
 * <p>
 * Description: ��ɫȨ�޿���Action
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
public class RoleNodeAction extends DispatchAction {

	
    public RoleNodeAction() {

	}

    public ActionForward execute(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        ActionForward forward = super.execute(mapping, form, request, response);
        return forward;
    }

    /**
     * ���õ�����ʾ
     * 
     * @param request
     * @param navigation
     */

    private void setNavigation(HttpServletRequest request, String navigation) {
        java.util.Locale locale = getLocale(request);
        MessageResources messages = getResources(request);
        request.setAttribute("navigator.location", messages.getMessage(locale,
                navigation));
    }

    /**
     * ָ��ѡ���ɫ��ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward toChooseRoleMain(ActionMapping mapping, ActionForm form,//lee
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String naigation = new String();
        setNavigation(request, "navigator.base.rolenode.modify");//
        
        Locale locale = getLocale(request);
        MessageResources messages = getResources(request);
        HttpSession httpSession = request.getSession();
        ActionErrors errors = new ActionErrors();
        Log log = LogFactory.getLog(RoleNodeAction.class);
        Session session = null;
        ArrayList roles = null;


        try {
            session = HibernateUtil.getSession();
            
            Query query = session
                    .createQuery("from Role");
            
            roles = (ArrayList) query.list();

            request.setAttribute("roles",roles);
            
        } catch (DataStoreException dex) {
            log.error(dex.getMessage());
            DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
        } catch (HibernateException hex) {
            log.error(hex.getMessage());
            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
        } finally {
            try {
                session.close();
            } catch (HibernateException hex) {
                log.error(hex.getMessage());
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
        
        return mapping.findForward("chooseRoleMain");
    }
    
    /**
     * ���ص�Ȩ�޹�����ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward toMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String naigation = new String();

        request.setAttribute("navigator.location", "��ɫȨ�� >> �� ��");
        return mapping.findForward("main");
    }


    
    /**
     * Lee Yang
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward toAssignUserMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String naigation = new String();
        setNavigation(request, "navigator.base.rolenode.modify");
        return mapping.findForward("assignMain");
    }
    
    /**
     * ���ص�Ȩ�޹�����ҳ��
     * �˷�����������Ա���������Ŀ��ʹ��
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     * 
     * create by cwy 2007-06-13
     */
    public ActionForward toRoleAreaMain(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        String naigation = new String();
        setNavigation(request, "navigator.base.rolenode.modify");
        return mapping.findForward("areaMain");
    }
    
    /**
     * ���ص���Frame��ʾ��ɫ����ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward toLeftFrm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        return mapping.findForward("leftFrm");
    }

    /**
     * @author Lee Yang
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward assignUser(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		// the attribute 'method=post(or get)' of the action should be played attention.
		String addedIds = (String) request.getParameter("addedIds");
		String deletedIds = (String) request.getParameter("deletedIds");
		String headerId = (String) request.getParameter("headerId");

		// datas also can be obtained via the following block.
		// DynaActionForm dform = (DynaActionForm) form;
		// String deletedIds = (String) dform.get("deletedIds");
		// String headerId = (String) dform.get("headerId");

		// logic below -------------------------------------------------
		ActionErrors errors = new ActionErrors();
		Log log = LogFactory.getLog(Operationref.class);

		Transaction tx = null;
		Session session = null;
		Operationref opt = null;
		OperationrefKey optKey = null;
		boolean isExist = false;
		boolean isExist_d = false;
		try {
			session = HibernateUtil.getSession();
			Loginuser loginUser = (Loginuser) session.get(Loginuser.class,
					headerId);
			
			tx = session.beginTransaction();
			// �������
			String[] operationIds_add = addedIds.split(",");
			for (int j = 0; j < operationIds_add.length; j++) {
				opt = new Operationref();
				optKey = new OperationrefKey();
				optKey.setUserid(loginUser.getUserid());
				optKey.setOperationid(operationIds_add[j]);
				opt.setId(optKey);
//				opt.setUserid(loginUser.getUserid());
//				opt.setOperationid(operationIds_add[j]);
				
				try {
					isExist =this.isExist(
							operationIds_add[j], headerId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				if (!isExist) {
					session.save(opt);
					//session.flush();
				}
			}

			// ɾ������
			String[] operationIds_del = deletedIds.split(",");
			for (int i = 0; i < operationIds_del.length; i++) {
				opt = new Operationref();
				optKey = new OperationrefKey();
				optKey.setUserid(loginUser.getUserid());
				optKey.setOperationid(operationIds_del[i]);
				opt.setId(optKey);
//				opt.setUserid(loginUser.getUserid());
//				opt.setOperationid(operationIds_del[i]);

				try {
					isExist_d =  this.isExist(
							operationIds_del[i], headerId);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (isExist_d) {
					session.delete(opt);
				}

			}

			tx.commit();

		} catch (DataStoreException dex) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"role.insert.duplicatekeyerror"));
			DebugUtil.print(dex, "Hibernate Operation assigned error!");
			try {
				tx.rollback();
			} catch (HibernateException e) {
				log.error(e.getMessage());
			}
		} catch (HibernateException hex) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"role.insert.duplicatekeyerror"));
			DebugUtil.print(hex, "Hibernate Operation assigned error!");
			try {
				tx.rollback();
			} catch (HibernateException e) {
				DebugUtil.print(e, "Hibernate Operation assigned error!");
			}
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
			}

		}
		if (errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"update.success"));
			saveErrors(request, errors);
		} else {
			saveErrors(request, errors);
		}

		// -------------------------------------------------------------

		request.setAttribute("headerId", headerId);

		// attemp to keep the status of the form
		return this.toAssignUserRightFrm(mapping, form, request, response);
	}
    
    /*
     * @author Lee Yang
     * 
     * */
    private boolean isExist(String operationId, String headerId)
			{
		Log log = LogFactory.getLog(Operationref.class);
		ActionErrors errors = new ActionErrors();
		Operationref opt = null;
		boolean isExist = false;
		Session session = null;
		try {
			session = HibernateUtil.getSession();
//			Loginuser loginUser = (Loginuser) session.get(Loginuser.class,headerId);
//			OperationrefKey optKey = new OperationrefKey();
//			optKey.setUserid(loginUser.getUserid());
//			optKey.setOperationid(operationId);
//			String userid=loginUser.getUserid();
			
			Query query = session
					.createQuery("from Operationref a where a.id.operationid=:operationid AND a.id.userid=:userid");
		    query.setString("userid", headerId);
			query.setString("operationid", operationId);
			List list=query.list();
			if (list.size() > 0){
				isExist = true;
				// make sure the session cache has already been cleared up
				session.clear();
				session.close();
			}else{
				isExist = false;
				session.clear();
				session.close();
			}
				
	
			/* anther way to search the certain object  */
//			 opt = (Operationref) query.uniqueResult();		
//			 Criteria criteria =
//			 session.createCriteria(Operationref.class);
//			 OperationrefKey optKey = new OperationrefKey();
//			 optKey.setLoginuser(loginUser);
//			 optKey.setOperationid(operationId);
//			 opt = (Operationref) criteria.add(Expression.eq("id", optKey))
//								                       .setMaxResults(1)
//										               .uniqueResult();

		} catch (DataStoreException dex) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"Search Operation error!"));
			DebugUtil.print(dex, "��ѯ Operation ����");
		
		} catch (HibernateException hex) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
					"Search Operation error!"));
			DebugUtil.print(hex, "��ѯ Operation ����");
		}
		return isExist;
	}
    
    /**
	 * @author Lee Yang
	 * 
	 */
    public ActionForward toAssignUserRightFrm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {
        Locale locale = getLocale(request);
        MessageResources messages = getResources(request);
    	
    	String headerId = (String)request.getParameter("headerId");
    	//��'assignUser'�������õĴ���
    	String _headerId = (String) request.getAttribute("headerId");
    	if(_headerId != null && !"".equals(_headerId)){
    		headerId = _headerId;
    	}
    	
        Session session = null;
        List operationMen = null;
        List userOpts = null;
        
        try {
			session = HibernateUtil.getSession();
			//lzy  �������
			Query query = session.createQuery("from Viewuseroperation order by username");
			operationMen = (ArrayList) query.list();// Viewuseroperation ��
			
			Query query2 = session.createQuery("from Operationref as a where a.id.userid=:userid");
			query2.setString("userid",headerId);
			userOpts = (ArrayList) query2.list();// Operationref ��
			
			   // Ŀ����ʹҳ���'checkbox'����״̬ 	 
			for(int j=0;j < operationMen.size();j++){
				Viewuseroperation vuo = (Viewuseroperation) operationMen.get(j);
				for(int i=0;i<userOpts.size();i++){
					Operationref _opt =(Operationref) userOpts.get(i);
					String optId =  _opt.getId().getOperationid();
					if(vuo.getUserid().equals(optId)){
						vuo.setHeaderId(headerId);
					}
					     		
				}
			}

        } catch (DataStoreException dex) {
            log.error(dex.getMessage());
            DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
        } catch (HibernateException hex) {
            log.error(hex.getMessage());
            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
        } finally {
            try {
                session.close();
            } catch (HibernateException hex) {
                log.error(hex.getMessage());
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
        
		request.setAttribute("userOpts",userOpts);
		request.setAttribute("operationMen",operationMen);
        request.setAttribute("headerId",headerId);
    	return mapping.findForward("rightUserFrm");
    }
    
    /**
     * ���ص���Frame��ʾ���ܽڵ���ҳ��
     * 
     * @param mapping
     * @param form
     * @param request
     * @param response
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public ActionForward toRightFrm(ActionMapping mapping, ActionForm form,//lee
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Locale locale = getLocale(request);
        MessageResources messages = getResources(request);
        HttpSession httpSession = request.getSession();
        ActionErrors errors = new ActionErrors();
        Log log = LogFactory.getLog(RoleNodeAction.class);
        ViewLoginUserInfo UserInfo = new ViewLoginUserInfo();
        Session session = null;
        ArrayList list = null;
        ArrayList list1 = null;
        ArrayList list2 = null;
        
        String roleID = request.getParameter("roleID");
        String moduleID = request.getParameter("moduleID");

        try {
            session = HibernateUtil.getSession();
            // ��ȡģ���ɫ�������нڵ�����ݣ��ȹ������еĿսڵ�
            Query query = session
                    .createQuery("SELECT NEW com.gzunicorn.bean.RoleNodeBean(a.nodeid,a.nodename,a.nodeurl) " +
                    		"FROM  Modulenode as a WHERE a.moduleid=:moduleid and " +
                    		"a.allowflag=:allowflag  ORDER BY a.nodeid");
            
            query.setString("moduleid", moduleID);
            query.setString("allowflag", "N");
            
            list = (ArrayList) query.list();

            //�ж��û��Ƿ���ȷ
            if (list == null || list.isEmpty()) {
                DebugUtil.println("��ǰģ��û����Ҫ�Ĺ��ܽڵ�!");
            }
            
            // ��ȡ��ɫ��Ӧ�ڵ������
            query = session
                    .createQuery("SELECT NEW com.gzunicorn.bean.RoleNodeBean(a.nodeid,a.writeflag) " +
                    		"FROM Rolenode as a WHERE a.roleid=:roleid " +
                    		"ORDER BY a.roleid");
            
            query.setString("roleid", roleID);
            
            list1 = (ArrayList) query.list();
          
            //���¹������пսڵ�״̬WriteFlag
            list2 = new ArrayList();
            if(list != null && !list.isEmpty()){
               if(list1 != null && !list1.isEmpty()){
                for(int i=0; i<list.size(); i++){
                    RoleNodeBean roleNodeBean = (RoleNodeBean)list.get(i);
                    String nodeID = roleNodeBean.getNodeID();
                    String nodeName = roleNodeBean.getNodeName();
                    String writeFlag = roleNodeBean.getWriteFlag();
                    String nodeURL = roleNodeBean.getNodeURL().trim();
                    
                    for(int j=0; j<list1.size(); j++){
                        RoleNodeBean roleNodeBean1 = (RoleNodeBean)list1.get(j);
                        if(nodeID.equals(roleNodeBean1.getNodeID())){
                            writeFlag = roleNodeBean1.getWriteFlag();
                        }
                    }
                    RoleNodeBean roleNodeBean2 = new RoleNodeBean();
                    roleNodeBean2.setNodeID(nodeID);
                    roleNodeBean2.setNodeName(nodeName);
                    roleNodeBean2.setWriteFlag(writeFlag);
                    roleNodeBean2.setNodeURL(nodeURL);
                    
                    list2.add(roleNodeBean2);
                }
               }else{
                   list2 = list;
               }
            }

            
            request.setAttribute("roleNodeList", list2);
            request.setAttribute("roleID",roleID);
            
        } catch (DataStoreException dex) {
            log.error(dex.getMessage());
            DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
        } catch (HibernateException hex) {
            log.error(hex.getMessage());
            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
        } finally {
            try {
                session.close();
            } catch (HibernateException hex) {
                log.error(hex.getMessage());
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }

        return mapping.findForward("rightFrm");
    }

    public ActionForward toSaveFrm(ActionMapping mapping, ActionForm form,
            HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        Locale locale = getLocale(request);
        MessageResources messages = getResources(request);
        HttpSession httpSession = request.getSession();
        ActionErrors errors = new ActionErrors();
        Log log = LogFactory.getLog(RoleNodeAction.class);
        ViewLoginUserInfo UserInfo = new ViewLoginUserInfo();//*
        Session session = null;
        ArrayList list = new ArrayList();
        
        String roleID = request.getParameter("roleID");//*
        String nodeRoles = request.getParameter("nodeRole");//*
        
        Transaction tx = null;
        try {
            String[] nodeRole = nodeRoles.split("#");
            for(int i=0; i<nodeRole.length; i++){
                String[] str = nodeRole[i].split(":");
                if(str.length>=2)//��ѡ��ȫ��Ϊ���ɶ�ʱnodeRoleΪ��,str����С��2,���ԼӴ��ж�
                {
                	Rolenode rolenode = new Rolenode();
                	rolenode.setRoleid(roleID);
                	rolenode.setNodeid(str[0]);
                	rolenode.setWriteflag(str[1]);//����ĳ����ɫ��Ȩ�ޱ��
                	//DebugUtil.println(roleID + str[0] + str[1]);
                	list.add(rolenode);
                }
            }
            
            session = HibernateUtil.getSession();
			tx = session.beginTransaction();
            Criteria criteria = session.createCriteria(Rolenode.class);
			if (roleID != null && !roleID.equals("")) {
			    criteria.add(Expression.eq("roleid", roleID));
			}
			
			List roleNodeList = criteria.list();//ֻ����һ����¼

			// ��ɾ��RoleID��ɫ��Ӧ�Ĺ��ܽڵ�����м�¼
			if(roleNodeList != null && !roleNodeList.isEmpty()){
				for(int i=0; i<roleNodeList.size(); i++){
				    session.delete((Rolenode)roleNodeList.get(i));
				    //��ʹɾ���Ľ��ͬ���������У���δcommit,Ϊ�˲�Ӱ������µļ�¼
				    session.flush();//***������
				}
			}
			//����������RoleID��ɫ��Ӧ���ܽڵ�����м�¼
			if(list != null && !list.isEmpty()){
			    for(int i=0; i<list.size(); i++){
			        session.save((Rolenode)list.get(i));
			    }
			    
			}
			tx.commit();
        } catch (DataStoreException dex) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
			"role.insert.duplicatekeyerror"));
            DebugUtil.print(dex, "Hibernate Rolenode Save error!"); 
			try {
                tx.rollback();
            } catch (HibernateException e) {
            	log.error(e.getMessage());
           }
        }
            catch (HibernateException hex) {
            errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
			"role.insert.duplicatekeyerror"));
            DebugUtil.print(hex, "Hibernate Rolenode Save error!");
            try {
                tx.rollback();
            } catch (HibernateException e) {
                DebugUtil.print(e, "Hibernate Rolenode Save error!");
            }
            log.error(hex.getMessage());
            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
        } finally {
            try {
                session.close();
            } catch (HibernateException hex) {
                log.error(hex.getMessage());
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
        if(errors.isEmpty()){
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError(
			"update.success"));
			saveErrors(request, errors);
		}else{
		    saveErrors(request, errors);
		}

        return mapping.findForward("saveFrm");//ֱ�ӷ��ر���form�����״̬
    }

    



    
}