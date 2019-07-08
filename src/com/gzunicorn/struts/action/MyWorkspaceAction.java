package com.gzunicorn.struts.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Session;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.SysConfig;

import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.hibernate.sysmanager.Workspacebaseproperty;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * �ҵĹ���������
 * 
 * @version 1.0
 * @author Feige
 * 
 */
public class MyWorkspaceAction extends DispatchAction {

	Log log = LogFactory.getLog(MyWorkspaceAction.class);

	BaseDataImpl bd = new BaseDataImpl();

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

		ActionForward forward = null;

		String name = request.getParameter("method");
		if (name == null || name.equals("") || "toSearchRecord".equals(name)) {
			name = "toIndex";
		}

		DebugUtil.printDoCommonAction("MyWorkspaceAction", name, "start");
		try {
			forward = dispatchMethod(mapping, form, request, response, name);
		} catch (Exception e) {
			DebugUtil.print(e);
		}

		DebugUtil.printDoCommonAction("MyWorkspaceAction", name, "end");

		return forward;
	}

	/**
	 * ��ѯ��¼��FORWARD��Ҳ��Ĭ�ϵ�forward
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toIndex(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, HibernateException {

		request.setAttribute("navigator.location", "�ҵĹ�����");
		Session hs = null;
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		ViewLoginUserInfo info = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);

		String roleid = info.getRoleID();

		//���ݽ�ɫ�����г�
		//String sql = "select b from Wsroleconfig as a,Workspacebaseproperty as b "
		//		+ "where b.enabledflag='Y' and a.id.wsid=b.wsid and a.id.roleid='" + roleid + "'";

		//ȫ���г�
		//String sql = "select b from Workspacebaseproperty as b where b.enabledflag='Y'";


		List jsList=null;
		List propertyList = null;
		List divList = null;
		Map divmap=null;

		try {
			hs = HibernateUtil.getSession();
			
			String wskeys=SysConfig.TOGET_DUTYS;

			//�п�дȨ�� �ڵ㹦��
			/**
			0604 	�������̹���
			0601	��ѯͶ�ߵǼ�
			0605 	��ѯͶ���ɹ�
			0602  	��ѯͶ�ߴ���
			0406	��װά�����ӵ����������
			7110 	��װά�����ӵ�������Ǽ�
			7111	ά���������Ǽ�
			
			0207	�ͻ��ݷ÷���
			0202	�ͻ��طù���
			0204	�ͻ��ط÷ֲ�������
			
			0304	ά����ͬ����
			--0307	ά����ͬ���
			0309	ά��ί�к�ͬ����
			--0310	ά��ί�к�ͬ���
			0311	ά����ͬ�ӱ�����
			0312	ά����ͬ�ӱ����
			
			0313 	ά����ͬ�˱�����
			0314 	ά����ͬ�˱����
			0321 	ά����ͬ�˱��ֲ������
			0324 	ά����ͬ�˱��ܲ������
			0325 	ά����ͬ�˱��ط�
			
			0301	ά�����۹���
			
			0903	��Ʊ����
			0908	��Ʊ�ܲ������
			0911	�����ܼ����
			0909	��Ʊ�������
			
			0501	ά����ͬ�´�ǩ��
			7016	�û���Ϣ���
			0609 	���ް�ȫ�������
			
			7108	������뵥(�ֿ����Ա)����
			7115	������뵥�������
			1101              ��ͬ���������ɹ� 
			1102              ��ͬ���������ϴ� 
			1103              ��ͬ����������� 
			1104              ��ͬ�������Ϸ����鿴 
			1105              ��ͬ�������Ͼ������ 
			*/
			
			String sql2="select NodeID,WriteFlag from RoleNode " +
					"where NodeID in('0601','0602','0604','0605','0406','7110','7111','0207','0202','0204'," +
					"'0304','0309','0311','0312','0313','0314','0321','0324','0325','0301','0903','0908','0909','0911','0501',"+
					"'7016','0609','7108','7115','1101','1102','1103','1104','1105') " +
					"and WriteFlag='Y' and RoleID='"+roleid+"'";
			List rlist2=hs.createSQLQuery(sql2).list();
			if(rlist2!=null && rlist2.size()>0){
				Object[] objs=null;
				String nodeid="";
				for(int n=0;n<rlist2.size();n++){
					objs=(Object[])rlist2.get(n);
					nodeid=(String)objs[0];
					
					//�пɶ�д ������뵥�������   �ڵ㹦��
					if(nodeid!=null && (nodeid.trim().equals("7115"))){
						wskeys+="','"+SysConfig.TOGET_AccessoriesRequisitionCkbl;
					}
					//�пɶ�д ������뵥(�ֿ����Ա)����  �ڵ㹦��
					if(nodeid!=null && (nodeid.trim().equals("7108"))){
						wskeys+="','"+SysConfig.TOGET_AccessoriesRequisition;
					}
					//�пɶ�д ���ް�ȫ�������  �ڵ㹦��
					if(nodeid!=null && (nodeid.trim().equals("0609"))){
						wskeys+="','"+SysConfig.TOGET_HotCalloutAudit;
					}
					//�пɶ�д �û���Ϣ��� �ڵ㹦��
					if(nodeid!=null && (nodeid.trim().equals("7016"))){
						wskeys+="','"+SysConfig.TOGET_LoginUserAudit;
					}
					//�пɶ�д ά����ͬ�´�ǩ�� �ڵ㹦��
					if(nodeid!=null && (nodeid.trim().equals("0501"))){
						wskeys+="','"+SysConfig.TOGET_ContractAssigned;
					}
					//�пɶ�д �ͻ��ط÷ֲ������� �ڵ㹦��
					if(nodeid!=null && (nodeid.trim().equals("0202") || nodeid.trim().equals("0204"))){
						wskeys+="','"+SysConfig.TOGET_CustReturnRegister;
					}
					//�пɶ�д ��Ʊ�ֲ������ �ڵ㹦��
					if(nodeid!=null && (nodeid.trim().equals("0903") || nodeid.trim().equals("0908") 
							|| nodeid.trim().equals("0909") || nodeid.trim().equals("0911"))){
						wskeys+="','"+SysConfig.TOGET_ContractInvoice;
					}
					//�пɶ�дȨ��ά�����۹���ڵ㹦��
					if(nodeid!=null && nodeid.trim().equals("0301")){
						wskeys+="','"+SysConfig.TOGET_MaintContractQuoteMaster;
					}
					//�п�дȨ�� ά����ͬ�˱����� [���,����]
					if(nodeid!=null && (nodeid.trim().equals("0313") 
							|| nodeid.trim().equals("0314") || nodeid.trim().equals("0321")
							|| nodeid.trim().equals("0324") || nodeid.trim().equals("0325") )){
						wskeys+="','"+SysConfig.TOGET_LostElevator;
					}
					//�пɶ�дȨ�� ά����ͬ����ά��ί�к�ͬ���� [���,����]
					if(nodeid!=null && (nodeid.trim().equals("0304") || nodeid.trim().equals("0307")
							|| nodeid.trim().equals("0309") || nodeid.trim().equals("0310"))){
						//A55  ������Ա  ����ʾ
						if(!"A55".equals(roleid)){
							wskeys+="','"+SysConfig.TOGET_ContractMaster;
						}
					}
					//�пɶ�дȨ�� ά����ͬ�ӱ����� [���,����]
					if(nodeid!=null && (nodeid.trim().equals("0311") || nodeid.trim().equals("0312"))){
						wskeys+="','"+SysConfig.TOGET_ContractDelay;
					}
					//�пɶ�дȨ�޼��޹���ڵ㹦��
					if(nodeid!=null && nodeid.trim().equals("0604")){
						//A55  ������Ա  ����ʾ
						if(!"A55".equals(roleid)){
							wskeys+="','"+SysConfig.TOGET_Fault;
						}
					}
					//�пɶ�дȨ�� ȫ�ʰ���ѯͶ�߹��� [����,�ɹ�,����]
					if(nodeid!=null && (nodeid.trim().equals("0605") 
							|| nodeid.trim().equals("0602") || nodeid.trim().equals("0601"))){
						wskeys+="','"+SysConfig.TOGET_Complaints;
					}
					//�пɶ�дȨ�� ά���������  �Ǽ�
					if(nodeid!=null && nodeid.trim().equals("7111")){
						wskeys+="','"+SysConfig.TOGET_QualityCheck;
					}
					//�пɶ�дȨ�� ��װά�����ӵ������ [���أ�����]
					if(nodeid!=null && (nodeid.trim().equals("0406") || nodeid.trim().equals("7110"))){
						wskeys+="','"+SysConfig.TOGET_ElevatorTrans;
					}
					//�пɶ�д�ͻ��ݷ÷����ڵ㹦��
					if(nodeid!=null && nodeid.trim().equals("0207")){
						wskeys+="','"+SysConfig.TOGET_CustomerVisit;
					}
					//�пɶ�д��ͬ���������ɹ��ڵ㹦��
					if(nodeid!=null && nodeid.trim().equals("1101")){
						wskeys+="','"+SysConfig.TOGET_TransferAssign;
					}
					//�пɶ�д��ͬ���������ϴ��ڵ㹦��
					if(nodeid!=null && nodeid.trim().equals("1102")){
						wskeys+="','"+SysConfig.TOGET_TransferUpload;
					}
					//�пɶ�д��ͬ����������˽ڵ㹦��
					if(nodeid!=null && nodeid.trim().equals("1103")){
						wskeys+="','"+SysConfig.TOGET_TransferAudit;
					}
					//�пɶ�д��ͬ�������Ϸ����鿴�ڵ㹦��
					if(nodeid!=null && nodeid.trim().equals("1104")){
						wskeys+="','"+SysConfig.TOGET_TransferFeedBack;
					}
					//�пɶ�д��ͬ�������Ͼ�����˽ڵ㹦��
					if(nodeid!=null && nodeid.trim().equals("1105")){
						wskeys+="','"+SysConfig.TOGET_TransferManagerAudit;
					}
				}
			}
			
			//�пɶ�дȨ��ά����ͬ�ڵ㹦�� 0304 ά����ͬ��������,������ A02  ά���ֲ��� ��A03  ά������  
			String sql3="select NodeID,WriteFlag from RoleNode where NodeID in('0304') " +
					"and RoleID not in('A02','A03') " +
					"and RoleID='"+roleid+"'";
			List rlist3=hs.createSQLQuery(sql3).list();
			if(rlist3!=null && rlist3.size()>0){
				//A55  ������Ա  ����ʾ
				if(!"A55".equals(roleid)){
					wskeys+="','"+SysConfig.TOGET_Contract;
				}
			}
			
			//�����нڵ�ɶ�дȨ�ޣ�������ʾ
			String sql = "select b from Workspacebaseproperty as b " +
					"where b.enabledflag='Y' and b.wskey in('"+wskeys+"') order by numno";
			propertyList = hs.createQuery(sql).list();
			if (propertyList != null && !propertyList.isEmpty()) {
				jsList=new ArrayList();
				divList=new ArrayList();
				for (int i = 0; i < propertyList.size(); i++) {
					divmap = new HashMap();
					Workspacebaseproperty config=(Workspacebaseproperty)propertyList.get(i);
					jsList.add(config.getJsfuname());
					divmap.put("id", config.getWsid());
					divmap.put("wsid", config.getWsid());
					divmap.put("wskey", config.getWskey());
					divmap.put("title", config.getTitle());
					divmap.put("link", config.getLink());
					divmap.put("tip", config.getTip());
					divmap.put("divid", config.getDivid());
					divmap.put("jsfullname", config.getJsfuname());
	
					if(i%2==0){
						divmap.put("float", "left");
					}else{
						divmap.put("float", "right");
					}
					divList.add(divmap);
				}
			}
			
			request.setAttribute("jsList", jsList);
			request.setAttribute("divList", divList);

			forward = mapping.findForward("toIndex");
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		return forward;
	}
	
	
	public ActionForward toIndexMain(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, HibernateException {

		request.setAttribute("navigator.location", "�ҵĹ�����");
		Session hs = null;
		ActionForward forward = new ActionForward();
		HttpSession session = request.getSession();
		ViewLoginUserInfo info = (ViewLoginUserInfo) session
				.getAttribute(SysConfig.LOGIN_USER_INFO);

		String roleid = info.getRoleID();

		String sql = "select b from Wsroleconfig as a,Workspacebaseproperty as b "
				+ "where b.enabledflag='Y' and a.id.wsid=b.wsid and a.id.roleid='" + roleid + "'";

		//System.out.println(sql);

	 
		List jsList=null;
		List propertyList = null;
		List divList = null;
		Map divmap=null;

		try {
			hs = HibernateUtil.getSession();
			propertyList = hs.createQuery(sql).list();
			if (propertyList != null && !propertyList.isEmpty()) {
				jsList=new ArrayList();
				divList=new ArrayList();
				
				for (int i = 0; i < propertyList.size(); i++) {
					divmap = new HashMap();
					Workspacebaseproperty config=(Workspacebaseproperty)propertyList.get(i);
					jsList.add(config.getJsfuname());
					divmap.put("id", config.getWsid());
					divmap.put("wsid", config.getWsid());
					divmap.put("wskey", config.getWskey());
					divmap.put("title", config.getTitle());
					divmap.put("link", config.getLink());
					divmap.put("tip", config.getTip());
					divmap.put("divid", config.getDivid());
					divmap.put("jsfullname", config.getJsfuname());
	
					
					if(i%2==0){
						divmap.put("float", "left");
					}else{
						divmap.put("float", "right");
					}
					divList.add(divmap);
				}
			}
			
			request.setAttribute("jsList", jsList);
			request.setAttribute("divList", divList);
			
			//System.out.println(">>>>>>>>>>");
			//System.out.println(jsList);
			//System.out.println(divList);
			//System.out.println(">>>>>>>>>>");
			
			forward = mapping.findForward("toIndexMain");
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		return forward;
	}
}
