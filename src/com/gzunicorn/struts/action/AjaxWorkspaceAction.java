package com.gzunicorn.struts.action;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import org.apache.struts.util.MessageResources;

import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.contractpayment.contractinvoicemanage.ContractInvoiceManage;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferMaster;
import com.gzunicorn.hibernate.custregistervisitplan.custreturnregistermaster.CustReturnRegisterMaster;
import com.gzunicorn.hibernate.engcontractmanager.entrustcontractmaster.EntrustContractMaster;
import com.gzunicorn.hibernate.engcontractmanager.lostelevatorreport.LostElevatorReport;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdelaymaster.MaintContractDelayMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractquotemaster.MaintContractQuoteMaster;
import com.gzunicorn.hibernate.hotlinemanagement.advisorycomplaintsmanage.AdvisoryComplaintsManage;
import com.gzunicorn.hibernate.hotlinemanagement.calloutmaster.CalloutMaster;
import com.gzunicorn.hibernate.infomanager.elevatortransfercaseregister.ElevatorTransferCaseRegister;
import com.gzunicorn.hibernate.infomanager.qualitycheckmanagement.QualityCheckManagement;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * ������ͳһ������Action
 * 
 * @author FeiGe
 * 
 */
public class AjaxWorkspaceAction extends DispatchAction {

	Log log = LogFactory.getLog(AjaxWorkspaceAction.class);

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
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String name = request.getParameter("method");
		if (name == null || name.equals("")) {
			name = "toSearchRecord";

		}

		DebugUtil.printDoBaseAction("AjaxWorkspaceAction", name, "start");
		ActionForward forward = dispatchMethod(mapping, form, request,
				response, name);
		DebugUtil.printDoBaseAction("AjaxWorkspaceAction", name, "end");
		return forward;
	}

	/**
	 * Get the navigation description from the properties file by navigation
	 * key;
	 * 
	 * @param request
	 * @param navigation
	 */

	private void setNavigation(HttpServletRequest request, String navigation) {
		Locale locale = this.getLocale(request);
		MessageResources messages = getResources(request);
		request.setAttribute("navigator.location", messages.getMessage(locale,
				navigation));
	}

	/**
	 * ͨ��Ajax�������ݶ�ȡ
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	
	public String toAjaxRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		String divid = request.getParameter("id");
		ActionForward forward = mapping.findForward("ajax");

		List list = new ArrayList();
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			list = MethodDistributed(divid, hs, request);
			
			request.setAttribute("divid", divid);
			response.setContentType("application/xml;charset=GBK");
			response.getWriter().write(this.toXML(list));
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	/***************************************************************************
	 * �ַ�����
	 * 
	 * @param key
	 *            �ؼ�KEY
	 * @param hs
	 * @throws HibernateException
	 */
	private List MethodDistributed(String key, Session hs,
			HttpServletRequest request) throws HibernateException {
		HttpSession httpsession = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)httpsession.getAttribute(SysConfig.LOGIN_USER_INFO);
		String userid = userInfo.getUserID();
		String roleid=userInfo.getRoleID();
		String comid=userInfo.getComID();
		String storageid=userInfo.getStorageId();
		
		if (key.equalsIgnoreCase(SysConfig.TOGET_DUTYS)) {
			//�ҵĴ�������
			return this.getMyDutysInfo(request, hs, userid, roleid,storageid);
			
		}else if (key.equalsIgnoreCase(SysConfig.TOGET_Fault)) {
			//��������
			return this.getCalloutMasterInfo(request, hs, userid, roleid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_Complaints)) {
			//ȫ�ʰ���ѯͶ�߹��� [����,�ɹ�,����]
			return this.getAdvisoryComplaints(request, hs, userid, roleid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_Contract)) {
			//ά����ͬ����
			return this.getDueMaintContract(request, hs, comid, roleid,storageid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_QualityCheck)) {
			//ά���������  
			return this.getQualityCheck(request, hs, userid, roleid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_ElevatorTrans)) {
			//��װά�����ӵ������  [���أ�����, ת��]
			return this.getElevatorTrans(request, hs, userid, roleid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_CustomerVisit)) {
			//�ͻ��ݷ÷���
			return this.getCustomerVisitPlanDetail(request, hs, userid, roleid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_ContractMaster)) {
			//�пɶ�дȨ�� ά����ͬ����ά��ί�к�ͬ���� [���,����]
			return this.getContractMaster(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_ContractDelay)) {
			//�пɶ�дȨ�� ά����ͬ�ӱ����� [���,����]
			return this.getContractDelay(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_LostElevator)) {
			//�пɶ�дȨ�� ά����ͬ�˱����� [���,����]
			return this.getLostElevator(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_MaintContractQuoteMaster)) {
			//�пɶ�дȨ��ά�����۹���ڵ㹦��
			return this.getMaintContractQuote(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_ContractInvoice)) {
			//�пɶ�д ��Ʊ�ֲ������  �ڵ㹦��
			return this.getContractInvoice(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_CustReturnRegister)) {
			//�пɶ�д �ͻ��ط÷ֲ�������  �ڵ㹦��
			return this.getCustReturnRegister(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_ContractAssigned)) {
			//�пɶ�д ά����ͬ�´�ǩ��  �ڵ㹦��
			return this.getContractAssigned(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_LoginUserAudit)) {
			//�пɶ�д �û���Ϣ���  �ڵ㹦��
			return this.getLoginUserAudit(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_HotCalloutAudit)) {
			//�пɶ�д ���ް�ȫ�������  �ڵ㹦��
			return this.getHotCalloutAudit(request, hs, comid, roleid,storageid,userid);
			
		} else if (key.equalsIgnoreCase(SysConfig.TOGET_AccessoriesRequisition)) {
			//�пɶ�д ������뵥(�ֿ����Ա)����  �ڵ㹦��
			return this.getAccessoriesRequisition(request, hs, comid, roleid,storageid,userid);

		} else if (key.equalsIgnoreCase(SysConfig.TOGET_AccessoriesRequisitionCkbl)) {
			//�пɶ�д ������뵥�������   �ڵ㹦��
			return this.getAccessoriesRequisitionCkbl(request, hs, comid, roleid,storageid,userid);
			
		} else if (key.equalsIgnoreCase(SysConfig.TOGET_TransferAssign)) {
			//�пɶ�д ��ͬ���������ɹ�   �ڵ㹦��
			return this.getTransferAssign(request, hs, comid, roleid,storageid,userid);
			
		} else if (key.equalsIgnoreCase(SysConfig.TOGET_TransferUpload)) {
			//�пɶ�д ��ͬ���������ϴ�   �ڵ㹦��
			return this.getTransferUpload(request, hs, comid, roleid,storageid,userid);
			
		} else if (key.equalsIgnoreCase(SysConfig.TOGET_TransferAudit)) {
			//�пɶ�д ��ͬ�����������   �ڵ㹦��
			return this.getTransferAudit(request, hs, comid, roleid,storageid,userid);
			
		} else if (key.equalsIgnoreCase(SysConfig.TOGET_TransferManagerAudit)) {
			//�пɶ�д ��ͬ�������Ͼ������   �ڵ㹦��
			return this.getTransferManagerAudit(request, hs, comid, roleid,storageid,userid);
			
		} else if (key.equalsIgnoreCase(SysConfig.TOGET_TransferFeedBack)) {
			//�пɶ�д ��ͬ�������Ϸ����鿴   �ڵ㹦��
			return this.getTransferFeedBack(request, hs, comid, roleid,storageid,userid);

		} else {
			return null;
		}
	}
	/**
	 * ��ҳ=�˵������б�
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getMyDutysInfo(HttpServletRequest request,
			Session hs, String userid,String roleid,String storageid) throws HibernateException {
		List reList = null;
	
		Connection conn = null;
		try {
	
			reList = new ArrayList();
			
			/**===============�������̣���������===============*/
			conn = hs.connection();
			String sql = "exec Sp_FetchToDoList '" + userid + "'";
			//System.out.println(sql);
			DataOperation op = new DataOperation();
			op.setCon(conn);
			
			List list = op.queryToList(sql);
			if (list != null && !list.isEmpty()) {
				HashMap map = null;
				String url = "";
				for (int i = 0; i < list.size(); i++) {
					url = "";
					map = new HashMap();
					HashMap obj = (HashMap) list.get(i);
					map.put("title",obj.get("showinfo"));
					map.put("sid",obj.get("taskid"));
					if(obj.get("actorid") != null && obj.get("actorid").toString().length()>0){
						url =SysConfig.WEB_APPNAME+"/"+obj.get("flowurl")+"&tokenid="+obj.get("tokenid")+"&taskid="+obj.get("taskid")+"&taskname="+obj.get("taskname")+"&taskname2="+obj.get("taskname2")+"&flowname="+obj.get("flowname")+"&tasktype=2";
					}else{
						url =SysConfig.WEB_APPNAME+"/"+obj.get("flowurl")+"&tokenid="+obj.get("tokenid")+"&taskid="+obj.get("taskid")+"&taskname="+obj.get("taskname")+"&taskname2="+obj.get("taskname2")+"&flowname="+obj.get("flowname")+"&tasktype=1";
					}
					map.put("url",url);
					map.put("date",obj.get("createdate").toString().substring(0,10));
					map.put("title2", obj.get("showinfo"));
					reList.add(map);
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}

	/**
	 * ȫ�ʰ���ѯͶ�߹��� �ɹ�
	 * 0601	��ѯͶ�ߵǼ�
	   0605   ��ѯͶ���ɹ�
	   0602  ��ѯͶ�ߴ���
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getAdvisoryComplaints(HttpServletRequest request,
			Session hs, String userid,String roleid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;	
		try {
			reList = new ArrayList();
			
			if(!roleid.equals("A01")){

				/**===============ȫ�ʰ���ѯͶ�ߵǼ� ���� �ɶ�дȨ��===============*/
				String sql1="select * from RoleNode where NodeID in('0601') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist1=hs.createSQLQuery(sql1).list();
				if(rlist1!=null && rlist1.size()>0){
					//���ص�
					String sql="from AdvisoryComplaintsManage where submitType in('R') and receivePer='"+userid+"' " +
							" order by processSingleNo desc";
					List frlist=hs.createQuery(sql).list();
					if(frlist!=null && frlist.size()>0){
						AdvisoryComplaintsManage master=null;
						for(int i=0;i<frlist.size();i++){
							String submittypename="";
							master=(AdvisoryComplaintsManage)frlist.get(i);
							String singleno = master.getProcessSingleNo();
							String submittype=master.getSubmitType();
							if(submittype!=null && submittype.trim().equals("N")){
								submittypename="δ�ύ";
							}else if(submittype!=null && submittype.trim().equals("R")){
								submittypename="����";
							}
							//��Ҫ������ת·�� �����������
							String urlstr=SysConfig.WEB_APPNAME+"/advisoryComplaintsManageAction.do?id="+singleno+"&method=toPrepareUpdateRecord";
						
							String descstr="������ţ�"+singleno+
								" | �ύ��־��"+submittypename+
								" | ����������ڣ�"+master.getReceiveDate();
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  singleno+String.valueOf(i));
							map.put("url", urlstr);
							map.put("date",  master.getReceiveDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
	
				/**===============ȫ�ʰ���ѯͶ���ɹ� �ɶ�дȨ��===============*/
				String sql2="select * from RoleNode where NodeID in('0605') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist2=hs.createSQLQuery(sql2).list();
				if(rlist2!=null && rlist2.size()>0){
					//�ύ�ģ�ί�д���ģ�δ�ɹ���
					String sql="from AdvisoryComplaintsManage where submitType='Y' " +
							" and isEntrust='Y' and dispatchType in('N') order by processSingleNo desc";
					List frlist=hs.createQuery(sql).list();
					
					if(frlist!=null && frlist.size()>0){
						AdvisoryComplaintsManage master=null;
						for(int i=0;i<frlist.size();i++){
							master=(AdvisoryComplaintsManage)frlist.get(i);
							String singleno = master.getProcessSingleNo();
		
							//��Ҫ������ת·�� �����������
							String urlstr=SysConfig.WEB_APPNAME+"/advisoryComplaintsManageAction.do?id="+singleno+"&method=toPrepareDispatchRecord";
												
							String descstr="������ţ�"+singleno+
								" | �ɹ���־��δ�ɹ�"+
								" | ����������ڣ�"+master.getReceiveDate();
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  singleno+String.valueOf(i));
							map.put("url", urlstr);
							map.put("date",  master.getReceiveDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				/**===============ȫ�ʰ���ѯͶ�ߴ��� �ɶ�дȨ��===============*/
				String sql3="select * from RoleNode where NodeID in('0602') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist3=hs.createSQLQuery(sql3).list();
				if(rlist3!=null && rlist3.size()>0){
					//�ύ�ģ����ɹ����ύ��δ����Ͳ��ص�
					String sql="from AdvisoryComplaintsManage where submitType='Y' " +
							" and dispatchType in('Y') and processType in('N','R') and processPer='"+userid+"'"+
							" order by processSingleNo desc";
					List frlist=hs.createQuery(sql).list();
					if(frlist!=null && frlist.size()>0){
						AdvisoryComplaintsManage master=null;
						for(int i=0;i<frlist.size();i++){
							String processtypename="";
							master=(AdvisoryComplaintsManage)frlist.get(i);
							String singleno = master.getProcessSingleNo();
							String processType=master.getProcessType();
							if(processType!=null && processType.trim().equals("N")){
								processtypename="δ����";
							}else if(processType!=null && processType.trim().equals("R")){
								processtypename="����";
							}
							//��Ҫ������ת·��
							String urlstr=SysConfig.WEB_APPNAME+"/advisoryComplaintsManageAction.do?id="+singleno+"" +
									"&method=toPrepareDisposeRecord&returnMethod=toSearchRecordDispose" +
									"&authority=advisorycomplaintsdispose";
						
							String descstr="������ţ�"+singleno+
								" | �����־��"+processtypename+
								" | ����������ڣ�"+master.getReceiveDate();
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  singleno+String.valueOf(i));
							map.put("url", urlstr);
							map.put("date",  master.getReceiveDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reList;
	}


	/**
	 * �������̹������б�
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getCalloutMasterInfo(HttpServletRequest request,
			Session hs, String userid,String roleid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;	
		String urlstr="";
		String statusname="";
		try {
			reList = new ArrayList();			
			
			if(!roleid.equals("A01")){

				//��ѯδ����Ĺ��ϱ���¼��
				String sql="from CalloutMaster a where a.handleStatus in('5','6') order by a.calloutMasterNo desc";
				List frlist=hs.createQuery(sql).list();
				
				if(frlist!=null && frlist.size()>0){
					CalloutMaster cm=null;
					for(int i=0;i<frlist.size();i++){
						cm=(CalloutMaster)frlist.get(i);
						String appno=cm.getCalloutMasterNo();
						String istiring=cm.getIsTrap();
						String isprocess=cm.getHandleStatus();
						//��Ҫ������ת·��
						if(isprocess!=null && "5".equals(isprocess)){
							statusname="�������";
							urlstr=SysConfig.WEB_APPNAME+"/hotphoneAction.do?id="+appno+"&typejsp=sh";
						}else if(isprocess!=null && "6".equals(isprocess)){
							statusname="�ط�����";
							urlstr=SysConfig.WEB_APPNAME+"/hotphoneAction.do?id="+appno+"&typejsp=ps";
						}
						
						String companyname=bd.getName("Customer", "companyName", "companyId", cm.getCompanyId());
						if(companyname==null || companyname.trim().equals("")){
							companyname=cm.getCompanyId();
						}
						String descstr="���ޱ�ţ�"+appno+
							" | ����״̬��"+statusname+
							//" | ���ˣ�"+CommonUtil.tranEnabledFlag(istiring)+
							" | ���޵�λ��"+companyname;
						
						map = new HashMap();
						map.put("title", descstr);
						map.put("sid",  appno+String.valueOf(i));
						map.put("url", urlstr);
						map.put("date",  cm.getOperDate());
						map.put("title2", descstr);
						
						reList.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reList;
	}
	/**
	 * �ɶ�дȨ�� ά���������  �Ǽ�
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getQualityCheck(HttpServletRequest request,
			Session hs, String userid,String roleid) throws HibernateException {
		List reList = null;

		try {

			reList = new ArrayList();
			
			if(!roleid.equals("A01")){

				/**===============δ�Ǽǵ� ά���������Ǽ�  �ɶ�дȨ��===============*/
				String sqlaa="from QualityCheckManagement where SubmitType='Y' " +
						"and ProcessStatus='0' and SuperviseId='" + userid + "' order by billno desc";
				List etfclistkk3=hs.createQuery(sqlaa).list();
				if(etfclistkk3!=null && etfclistkk3.size()>0){
					HashMap map = null;
					String url = "";
					for(int e=0;e<etfclistkk3.size();e++){
						QualityCheckManagement qcm=(QualityCheckManagement)etfclistkk3.get(e);
						
						String billno = qcm.getBillno();
	
						//��Ҫ������ת·�� 
						String urlstr=SysConfig.WEB_APPNAME+"/qualityCheckManagementAction.do?id="+billno+"&method=toPrepareRegistrationRecord";
											
						String descstr="ά�����������ˮ�ţ�"+billno +
							" | ����״̬��δ�Ǽ�"+
							" | ���ݱ�ţ�"+qcm.getElevatorNo();
						
						map = new HashMap();
						map.put("title", descstr);
						map.put("sid",  billno+String.valueOf(e));
						map.put("url", urlstr);
						map.put("date",  qcm.getOperDate());
						map.put("title2", descstr);
						
						reList.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}

	/**
	 * �ɶ�дȨ�� ��װά�����ӵ������  ���أ�����
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getElevatorTrans(HttpServletRequest request,
			Session hs, String userid,String roleid) throws HibernateException {
		List reList = null;
		try {

			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
			
				/**===============�����ص�,ת�ɵ� ��װά�����ӵ���������� �ɶ�дȨ��===============*/
				String sqlkk="select * from RoleNode where NodeID in('0406') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="from ElevatorTransferCaseRegister where SubmitType in ('R','Z') " +
							"and isnull(workisdisplay,'N')='N' order by billno desc";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						HashMap map = null;
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							ElevatorTransferCaseRegister etfc=(ElevatorTransferCaseRegister)etfclist.get(j);
							
							String billno = etfc.getBillno();
							String submittype=etfc.getSubmitType();
		
							String urlstr="";
							urlstr=SysConfig.WEB_APPNAME+"/elevatorTransferCaseRegisterManageAction.do?id="+billno+"&method=toDisplayRecord&workisdisplay=Y";
							
							String descstr="";
							if(submittype!=null && submittype.equals("Z")){
								//��Ҫ������ת·�� 
								//urlstr=SysConfig.WEB_APPNAME+"/elevatorTransferCaseRegisterManageAction.do?id="+billno+"&method=toDisplayRecord&workisdisplay=Y";
													
								descstr="��װά��������ˮ�ţ�"+billno+
									" | �ύ��־��ת��"+
									" | ���ݱ�ţ�"+etfc.getElevatorNo();
							}else{
								//��Ҫ������ת·��
								//urlstr=SysConfig.WEB_APPNAME+"/elevatorTransferCaseRegisterManageAction.do?id="+billno+"&method=toPrepareUpdateRecord";
													
								descstr="��װά��������ˮ�ţ�"+billno+
									" | �ύ��־������"+
									" | ���ݱ�ţ�"+etfc.getElevatorNo();
							}
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  etfc.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
					
				}
				
				/**===============δ���յ� ��װά�����ӵ�������Ǽ�  �ɶ�дȨ��===============*/
				String sqlkk2="select * from RoleNode where NodeID in('7110') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlistkk2=hs.createSQLQuery(sqlkk2).list();
				if(rlist!=null && rlistkk2.size()>0){
					String sqlaa="from ElevatorTransferCaseRegister where SubmitType in('Y','Z') " +
							"and ProcessStatus='0' and StaffName='" + userid + "' order by billno desc";
					List etfclistkk2=hs.createQuery(sqlaa).list();
					if(etfclistkk2!=null && etfclistkk2.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<etfclistkk2.size();e++){
							ElevatorTransferCaseRegister etfc=(ElevatorTransferCaseRegister)etfclistkk2.get(e);
							
							String billno = etfc.getBillno();
		
							//��Ҫ������ת·��
							String urlstr=SysConfig.WEB_APPNAME+"/elevatorTransferCaseRegisterAction.do?id="+billno+"&method=toReceiveDisposeRecord";
												
							String descstr="��װά��������ˮ�ţ�"+billno+
								" | ����״̬��δ����"+
								" | ���ݱ�ţ�"+etfc.getElevatorNo();
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  etfc.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
					
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}

	/**
	 * ��ҳ=ά�����ں�ͬ�б�
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getDueMaintContract(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;		
		String maintStation="%";
		try {
			reList = new ArrayList();
			
			//A37  �����ͬ��Ա ,A35  ������ͬ��Ա  
			if("A35".equals(roleid) || "A37".equals(roleid)){
				
				String maintDivision = "00".equals(comid) ? "%" : comid;
				//String maintDivision = comid;
				
				/**
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+roleid+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					maintStation=storageid;
				}
				*/
				
				//��ѯά�����ں�ͬ   ���ݺ�ͬ����������ǰ3�������� ί�к�ͬ�ڱ��Ĳ����ѡ����´�Ĳ�����
				String today=DateUtil.getNowTime("yyyy-MM-dd");
				String datestr=DateUtil.getDate(today, "MM", 3);
				
				String sql="from MaintContractMaster where contractEdate <= '"+datestr+"' " +
						" and maintDivision like '"+maintDivision+"'" +
						" and maintStation like '"+maintStation+"'"+
						" and contractStatus in ('XB','ZB') " +
						//" and warningStatus not in('S','Y')"+
						" and isnull(warningStatus,'')='' "+//δ���ݷüƻ�
						" and isnull(TaskSubFlag,'')='Y' "+//���´��
						//" and billNo not in(select maintBillNo from EntrustContractMaster where r1='ZB') "+
						" order by contractEdate asc";
				List mlist=hs.createQuery(sql).list();
				//System.out.println(sql);
				if(mlist!=null && mlist.size()>0){
					MaintContractMaster master=null;
					for(int i=0;i<mlist.size();i++){
						master=(MaintContractMaster)mlist.get(i);
						String billNo = master.getBillNo();
						String warningStatus=master.getWarningStatus();
						String warname="δ���ݷüƻ�";
	
						//��Ҫ������ת·��
						String urlstr=SysConfig.WEB_APPNAME+"/maintContractAction.do?id="+billNo+"&method=toPrepareExpireRecord";
											
						String descstr="ά����ͬ�ţ�"+master.getMaintContractNo() +
							//" | ��ʼ���ڣ�"+master.getContractSdate() +
							" | �������ڣ�"+master.getContractEdate()+
							" | ����״̬��"+warname;
						
						map = new HashMap();
						map.put("title", descstr);
						map.put("sid",  billNo+String.valueOf(i));
						map.put("url", urlstr);
						map.put("date",  master.getOperDate());
						map.put("title2", descstr);
						
						reList.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reList;
	}
	/**
	 * �ɶ�дȨ�� �ͻ��ݷ÷��� �Ǽ�
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getCustomerVisitPlanDetail(HttpServletRequest request,
			Session hs, String userid,String roleid) throws HibernateException {
		List reList = null;

		try {

			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
			
				/**===============δ�����Ļ򲵻ص�  �ͻ��ݷ÷��� �ɶ�дȨ��===============*/
				String sqlaa="select b.jnlno,a.CompanyName,b.VisitDate,ISNULL(b.SubmitType,'') as SubmitType,a.OperDate " +
						"from CustomerVisitPlanDetail b,CustomerVisitPlanMaster a " +
						"where b.billno=a.billno and ISNULL(b.SubmitType,'') in('','R') and b.VisitStaff='"+userid+"' " +
						"order by b.VisitDate";
				List etfclistkk3=hs.createSQLQuery(sqlaa).list();
				if(etfclistkk3!=null && etfclistkk3.size()>0){
					HashMap map = null;
					String url = "";
					for(int e=0;e<etfclistkk3.size();e++){
						Object[] obj=(Object[])etfclistkk3.get(e);
						String jnlno = (String)obj[0];
						String CompanyName = (String)obj[1];
						String VisitDate = (String)obj[2];
						String SubmitType = (String)obj[3];
						String OperDate=(String)obj[4];
	
						//��Ҫ������ת·�� 
						String urlstr=SysConfig.WEB_APPNAME+"/customerVisitFeedbackAction.do?id="+jnlno+"&method=toPrepareAddRecord";
						
						String descstr="";
						if(SubmitType!=null && SubmitType.trim().equals("R")){
							descstr="�ݷõ�λ���ƣ�"+CompanyName+
									" | �ݷ����ڣ�"+VisitDate+
									" | ״̬������";
						}else{
							descstr="�ݷõ�λ���ƣ�"+CompanyName+
									" | �ݷ����ڣ�"+VisitDate+
									" | ״̬��δ�ݷ�";
						}
						
						map = new HashMap();
						map.put("title", descstr);
						map.put("sid",  jnlno+String.valueOf(e));
						map.put("url", urlstr);
						map.put("date",  OperDate);
						map.put("title2", descstr);
						
						reList.add(map);
					}
				}
				
				//[A13  ά���ܲ���  ,A02  ά���ֲ��� ��A03  ά������]
				/**===============�ͻ��ݷ÷��� ά��������� [ά��վ�� 22 ��ά������רԱ 100 �ķ���]===============*/
				if(roleid.equals("A03")){
					String hql="select b.jnlno,a.CompanyName,b.VisitDate,b.fkOperDate " +
							"from CustomerVisitPlanMaster a,CustomerVisitPlanDetail b,loginuser l " +
							"where a.billno=b.billno and b.VisitPosition in('22','100') " +
							"and isnull(b.fkOperId,'')<>'' and ISNULL(b.ManagerFollow,'')='' " +
							"and l.storageid = a.MaintStation and l.userid  like '%"+userid+"%' "+
							"order by b.VisitDate";
					List jllist=hs.createSQLQuery(hql).list();
					if(jllist!=null && jllist.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<jllist.size();e++){
							Object[] obj=(Object[])jllist.get(e);
							String jnlno = (String)obj[0];
							String CompanyName = (String)obj[1];
							String VisitDate = (String)obj[2];
							String fkOperDate=(String)obj[3];
		
							//��Ҫ������ת·�� 
							String urlstr=SysConfig.WEB_APPNAME+"/MaintManagerFollowAction.do?id="+jnlno+"&method=toPrepareUpdateRecord";
							
							String descstr="�ݷõ�λ���ƣ�"+CompanyName+
										" | �ݷ����ڣ�"+VisitDate+
										" | ״̬��δ����";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  fkOperDate);
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				/**===============�ͻ��ݷ÷��� ά���ֲ������� [ά������ 20  ά��վ�� 22 ��ά������רԱ 100 �ķ���]===============*/
				if(roleid.equals("A02")){
					String hql2="select b.jnlno,a.CompanyName,b.VisitDate,b.fkOperDate " +
							"from CustomerVisitPlanMaster a,CustomerVisitPlanDetail b,loginuser l " +
							"where a.billno=b.billno " +
							"and ( (b.VisitPosition in('20') and isnull(b.fkOperId,'')<>'')  " +
							"      or  (b.VisitPosition in('22','100') and ISNULL(b.ManagerFollow,'')<>'') ) " +
							"and ISNULL(b.FMinisterFollow,'')='' " +
							"and l.grcid = a.MaintDivision and l.userid  like '%"+userid+"%'" +
							"order by b.VisitDate";
					List fbzlist=hs.createSQLQuery(hql2).list();
					if(fbzlist!=null && fbzlist.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<fbzlist.size();e++){
							Object[] obj=(Object[])fbzlist.get(e);
							String jnlno = (String)obj[0];
							String CompanyName = (String)obj[1];
							String VisitDate = (String)obj[2];
							String fkOperDate=(String)obj[3];
		
							//��Ҫ������ת·�� 
							String urlstr=SysConfig.WEB_APPNAME+"/MaintFMinisterFollowAction.do?id="+jnlno+"&method=toPrepareUpdateRecord";
							
							String descstr="�ݷõ�λ���ƣ�"+CompanyName+
										" | �ݷ����ڣ�"+VisitDate+
										" | ״̬��δ����";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  fkOperDate);
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				/**===============�ͻ��ݷ÷��� ά���ܲ������� [ά���ֲ��� 17 �ķ���]===============*/
				if(roleid.equals("A13")){
					String hql3="select b.jnlno,a.CompanyName,b.VisitDate,b.fkOperDate " +
							"from CustomerVisitPlanMaster a,CustomerVisitPlanDetail b " +
							"where a.billno=b.billno and b.VisitPosition='17' " +
							"and isnull(b.fkOperId,'')<>'' and ISNULL(b.ZMinisterFollow,'')='' " +
							"order by b.VisitDate";
					List zbzlist=hs.createSQLQuery(hql3).list();
					if(zbzlist!=null && zbzlist.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<zbzlist.size();e++){
							Object[] obj=(Object[])zbzlist.get(e);
							String jnlno = (String)obj[0];
							String CompanyName = (String)obj[1];
							String VisitDate = (String)obj[2];
							String fkOperDate=(String)obj[3];
		
							//��Ҫ������ת·�� 
							String urlstr=SysConfig.WEB_APPNAME+"/MaintZMinisterFollowAction.do?id="+jnlno+"&method=toPrepareUpdateRecord";
							
							String descstr="�ݷõ�λ���ƣ�"+CompanyName+
										" | �ݷ����ڣ�"+VisitDate+
										" | ״̬��δ����";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  fkOperDate);
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	/**
	 * ��ҳ= ά����ͬ����ά��ί�к�ͬ���� [���,����]
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getContractMaster(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;
		try {
			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
			
				/**===============�����ص�,ά����ͬ����  �ɶ�дȨ��===============*/
				String sqlkk="select * from RoleNode where NodeID in('0304') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="from MaintContractMaster " +
							"where submitType in ('R') and isnull(workisdisplay,'N')='N' and operId='"+userid+"' " +
							"order by billNo ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							MaintContractMaster mcm=(MaintContractMaster)etfclist.get(j);
							
							String billno=mcm.getBillNo();
							String maintContractNo=mcm.getMaintContractNo();
		
							String urlstr=SysConfig.WEB_APPNAME+"/maintContractAction.do?id="+billno+"&method=toDisplayRecord&workisdisplay=Y";
							
							String descstr="��ˮ�ţ�"+billno+
									" | ά����ͬ�� ��"+maintContractNo+
									" | �ύ��־������";
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
					
				}
				
				/**===============�����ص�,ά��ί�к�ͬ����  �ɶ�дȨ��===============*/
				String sqlkk2="select * from RoleNode where NodeID in('0309') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist2=hs.createSQLQuery(sqlkk2).list();
				if(rlist2!=null && rlist2.size()>0){
					String sqlaa="from EntrustContractMaster " +
							"where submitType in ('R') and isnull(workisdisplay,'N')='N' and operId='"+userid+"' " +
							"order by billNo ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							EntrustContractMaster mcdm=(EntrustContractMaster)etfclist.get(j);
							
							String billno=mcdm.getBillNo();
							String EntrustContractNo=mcdm.getEntrustContractNo();
		
							String urlstr=SysConfig.WEB_APPNAME+"/entrustContractMasterAction.do?id="+billno+"&method=toDisplayRecord&workisdisplay=Y";
							
							String descstr="��ˮ�ţ�"+billno+
									" | ί�к�ͬ�� ��"+EntrustContractNo+
									" | �ύ��־������";
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcdm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reList;
	}
	/**
	 * ��ҳ=ά����ͬ�ӱ����� [���,����]
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getContractDelay(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;
		try {
			reList = new ArrayList();
			if(!roleid.equals("A01")){
				
				//String maintDivision = "00".equals(comid) ? "%" : comid;
				String maintDivision = comid;
				
				/**===============ά����ͬ�ӱ����  �ɶ�дȨ��===============*/
				String sqlkk="select * from RoleNode where NodeID in('0312') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="select a from MaintContractDelayMaster a,MaintContractMaster b " +
							"where a.billno=b.billNo and a.submitType in ('Y') and isnull(a.auditStatus,'N')='N' " +
							"and b.maintDivision like '"+maintDivision+"' " +
							"order by jnlno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							MaintContractDelayMaster mcdm=(MaintContractDelayMaster)etfclist.get(j);
							
							String jnlno = mcdm.getJnlno();
							String billno=mcdm.getBillno();
		
							String urlstr=SysConfig.WEB_APPNAME+"/maintContractDelayAuditAction.do?id="+jnlno+"&method=toDisplayRecord";
							
							String descstr="��ˮ�ţ�"+jnlno+
									" | ά����ͬ�� ��"+bd.getName(hs, "MaintContractMaster", "maintContractNo", "billNo",billno)+
									" | ���״̬��δ���";
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcdm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
					
				}
				
				/**===============�����ص� ���ͨ����,ά����ͬ�ӱ�����  �ɶ�дȨ��===============*/
				String sqlkk2="select * from RoleNode where NodeID in('0311') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist2=hs.createSQLQuery(sqlkk2).list();
				if(rlist2!=null && rlist2.size()>0){
					String sqlaa="from MaintContractDelayMaster " +
							"where (submitType='R' or auditStatus='Y') and isnull(workisdisplay,'N')='N'" +
							" and operId='"+userid+"' order by jnlno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							MaintContractDelayMaster mcdm=(MaintContractDelayMaster)etfclist.get(j);
							
							String jnlno = mcdm.getJnlno();
							String billno=mcdm.getBillno();
							
							String submit=mcdm.getSubmitType();
							
							//��Ҫ������ת·��
							String title="���״̬�����ͨ��";
							String urlstr=SysConfig.WEB_APPNAME+"/maintContractDelayAction.do?id="+jnlno+"&method=toDisplayRecord&workisdisplay=Y";
							if(submit!=null && submit.trim().equals("R")){
								urlstr=SysConfig.WEB_APPNAME+"/maintContractDelayAction.do?id="+jnlno+"&method=toPrepareUpdateRecord&isclosework=Y";
								title="�ύ��־������";
							}
		
							String descstr="��ˮ�ţ�"+jnlno+
									" | ά����ͬ�� ��"+bd.getName(hs, "MaintContractMaster", "maintContractNo", "billNo",billno)+
									" | "+title;
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcdm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reList;
	}
	/**
	 * ��ҳ=ά����ͬ�˱����� [���,����]
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getLostElevator(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;
		try {
			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
				//String maintDivision = "00".equals(comid) ? "%" : comid;
				String maintDivision = comid;
				
				//�ȷֲ�����ˣ�����Ա���
				/**===============�����صĺ����ͨ����,ά����ͬ�˱�����  ��дȨ��===============*/
				String sqlkk="select * from RoleNode where NodeID in('0313') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="from LostElevatorReport " +
							"where (submitType='R' or AuditStatus='Y') and isnull(workisdisplay,'N')='N' " +
							"and operId='"+userid+"' order by jnlno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							LostElevatorReport mcdm=(LostElevatorReport)etfclist.get(j);
							
							String jnlno = mcdm.getJnlno();
							String submittype=mcdm.getSubmitType();
							String auditstatus=mcdm.getAuditStatus();
							
							String typename="���״̬�����ͨ��";
							String urlstr=SysConfig.WEB_APPNAME+"/lostElevatorReportAction.do?id="+jnlno+"&method=toDisplayRecord&workisdisplay=Y";
							
							if(submittype!=null && submittype.trim().equals("R")){
								typename="�ύ��־������";
								urlstr=SysConfig.WEB_APPNAME+"/lostElevatorReportAction.do?id="+jnlno+"&method=toPrepareUpdateRecord&isclosework=Y";
							}
							
							String descstr="��ˮ�ţ�"+jnlno+
									" | ά����ͬ�� ��"+mcdm.getMaintContractNo()+
									" | "+typename;
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcdm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				/**===============δ��˵�,ά����ͬ�˱���Ա���  ��дȨ��===============*/
				String sqlkk2="select * from RoleNode where NodeID in('0314') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist2=hs.createSQLQuery(sqlkk2).list();
				if(rlist2!=null && rlist2.size()>0){
					String sqlaa="from LostElevatorReport  " +
							"where submitType='Y' and isnull(auditStatus,'N')='N' " +
							//" and (isnull(isCharge,'')='Y' or isnull(auditStatus3,'N')='Y') "+ //�ۿ� ���� �ܲ������ͨ��
							" and isnull(auditStatus3,'N')='Y'"+ // �ܲ������ͨ��
							"and maintDivision like '"+maintDivision+"' order by jnlno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							LostElevatorReport mcdm=(LostElevatorReport)etfclist.get(j);
							
							String jnlno = mcdm.getJnlno();
		
							String urlstr=SysConfig.WEB_APPNAME+"/lostElevatorReportAuditAction.do?id="+jnlno+"&method=toDisplayRecord";
							
							String descstr="��ˮ�ţ�"+jnlno+
									" | ά����ͬ�� ��"+mcdm.getMaintContractNo()+
									" | ��Ա���״̬��δ���";
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcdm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				/**===============δ��˵�,ά����ͬ�˱��ܲ������  ��дȨ��===============*/
				String sqlkk4="select * from RoleNode where NodeID in('0324') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist4=hs.createSQLQuery(sqlkk4).list();
				if(rlist4!=null && rlist4.size()>0){
					String sqlaa="from LostElevatorReport  " +
							"where submitType='Y' and isnull(auditStatus3,'N')='N' " +
							//"and isnull(isCharge,'')='N' and isnull(auditStatus2,'N')='Y' "+//���ۿ���Ҫ�ܲ������
							" and isnull(auditStatus2,'N')='Y'"+//�ܲ������
							"and maintDivision like '%' order by jnlno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							LostElevatorReport mcdm=(LostElevatorReport)etfclist.get(j);
							
							String jnlno = mcdm.getJnlno();
		
							String urlstr=SysConfig.WEB_APPNAME+"/lostElevatorReportAudit3Action.do?id="+jnlno+"&method=toDisplayRecord";
							
							String descstr="��ˮ�ţ�"+jnlno+
									" | ά����ͬ�� ��"+mcdm.getMaintContractNo()+
									" | �ܲ������״̬��δ���";
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcdm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				/**===============δ��˵�,ά����ͬ�˱��ֲ������  ��дȨ��===============*/
				String sqlkk3="select * from RoleNode where NodeID in('0321') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist3=hs.createSQLQuery(sqlkk3).list();
				if(rlist3!=null && rlist3.size()>0){
					String sqlaa="from LostElevatorReport  " +
							"where submitType='Y' and isnull(auditStatus2,'N')='N' " +
							" and isnull(fhRem,'')<>'' "+//�ύ�Ĳ����лطý����
							"and maintDivision like '"+maintDivision+"' order by jnlno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							LostElevatorReport mcdm=(LostElevatorReport)etfclist.get(j);
							
							String jnlno = mcdm.getJnlno();
		
							String urlstr=SysConfig.WEB_APPNAME+"/lostElevatorReportAudit2Action.do?id="+jnlno+"&method=toDisplayRecord";
							
							String descstr="��ˮ�ţ�"+jnlno+
									" | ά����ͬ�� ��"+mcdm.getMaintContractNo()+
									" | �ֲ������״̬��δ���";
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcdm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				/**===============δ��˵�,ά����ͬ�˱��ط�  ��дȨ��===============*/
				String sqlkk5="select * from RoleNode where NodeID in('0325') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist5=hs.createSQLQuery(sqlkk5).list();
				if(rlist5!=null && rlist5.size()>0){
					String sqlaa="from LostElevatorReport  " +
							"where submitType='Y' and isnull(fhRem,'')='' "+//�ύ�Ĳ����лطý����
							"and maintDivision like '%' order by jnlno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							LostElevatorReport mcdm=(LostElevatorReport)etfclist.get(j);
							
							String jnlno = mcdm.getJnlno();
		
							String urlstr=SysConfig.WEB_APPNAME+"/lostElevatorReportHfAction.do?id="+jnlno+"&method=toDisplayRecord";
							
							String descstr="��ˮ�ţ�"+jnlno+
									" | ά����ͬ�� ��"+mcdm.getMaintContractNo()+
									" | �ط�״̬��δ�ط�";
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  mcdm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reList;
	}
	/**
	 * ��ҳ=ά�����۹���[���ͨ��]����
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getMaintContractQuote(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		List reList = null;

		try {

			reList = new ArrayList();
			
			if(!roleid.equals("A01")){

				/**===============ά�����۹������ͨ��  �ɶ�дȨ��===============*/
				String sqlaa="from MaintContractQuoteMaster where status=1 and isnull(workisdisplay,'N')='N' " +
						"and attn='" + userid + "' order by billNo";
				List etfclistkk3=hs.createQuery(sqlaa).list();
				if(etfclistkk3!=null && etfclistkk3.size()>0){
					HashMap map = null;
					String url = "";
					for(int e=0;e<etfclistkk3.size();e++){
						MaintContractQuoteMaster qcm=(MaintContractQuoteMaster)etfclistkk3.get(e);
						
						String billno = qcm.getBillNo();
	
						//��Ҫ������ת·��
						String urlstr=SysConfig.WEB_APPNAME+"/maintContractQuoteAction.do?id="+billno+"&method=toDisplayRecord&workisdisplay=Y";
											
						String descstr="ά��������ˮ�ţ�"+billno +
							" | �׷���λ��"+qcm.getCompanyName();
							//" | �������ڣ�"+qcm.getApplyDate();
						
						map = new HashMap();
						map.put("title", descstr);
						map.put("sid",  billno+String.valueOf(e));
						map.put("url", urlstr);
						map.put("date",  qcm.getOperDate());
						map.put("title2", descstr);
						
						reList.add(map);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	/**
	 * ��ҳ=��Ʊ�ֲ������ ����
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getContractInvoice(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		List reList = null;

		try {

			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
			
				String maintDivision = "00".equals(comid) ? "%" : comid;
				//String maintDivision = comid;
	
				/**===============��Ʊ�ܲ������  �ɶ�дȨ��===============*/
				String sqlkk="select * from RoleNode where NodeID in('0908') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa=" from ContractInvoiceManage " +
							"where submitType='Y' and isnull(auditStatus,'N')='Y' " +
							"and isnull(auditStatus2,'N')='N' and maintDivision like '"+maintDivision+"' order by jnlNo";
					List etfclistkk3=hs.createQuery(sqlaa).list();
					if(etfclistkk3!=null && etfclistkk3.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<etfclistkk3.size();e++){
							ContractInvoiceManage cim=(ContractInvoiceManage)etfclistkk3.get(e);
							
							String jnlno = cim.getJnlNo();
		
							//��Ҫ������ת·��
							String urlstr=SysConfig.WEB_APPNAME+"/contractInvoiceManageAudit2Action.do?id="+jnlno+"&method=toPrepareAuditRecord";
												
							String descstr="��Ʊ��ˮ�ţ�"+jnlno +
								" | ��ͬ�� ��"+cim.getContractNo()+
								" | ���״̬���ܲ���δ���";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  cim.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				/**===============��Ʊ�����ܼ����  �ɶ�дȨ��===============*/
				String sqlkk4="select * from RoleNode where NodeID in('0911') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist4=hs.createSQLQuery(sqlkk4).list();
				if(rlist4!=null && rlist4.size()>0){
					String sqlaa=" from ContractInvoiceManage " +
							"where submitType='Y' and isnull(auditStatus,'N')='Y' " +
							"and isnull(auditStatus2,'N')='Y' "+
							"and isnull(auditStatus4,'N')='N' " +
							"and maintDivision like '"+maintDivision+"' order by jnlNo";
					List etfclistkk3=hs.createQuery(sqlaa).list();
					if(etfclistkk3!=null && etfclistkk3.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<etfclistkk3.size();e++){
							ContractInvoiceManage cim=(ContractInvoiceManage)etfclistkk3.get(e);
							
							String jnlno = cim.getJnlNo();
		
							//��Ҫ������ת·��
							String urlstr=SysConfig.WEB_APPNAME+"/contractInvoiceManageAudit4Action.do?id="+jnlno+"&method=toPrepareAuditRecord";
												
							String descstr="��Ʊ��ˮ�ţ�"+jnlno +
								" | ��ͬ�� ��"+cim.getContractNo()+
								" | ���״̬�������ܼ�δ���";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  cim.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				/**===============��Ʊ�������  �ɶ�дȨ��===============*/
				String sqlkk2="select * from RoleNode where NodeID in('0909') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist2=hs.createSQLQuery(sqlkk2).list();
				if(rlist2!=null && rlist2.size()>0){
					String sqlaa=" from ContractInvoiceManage " +
							"where submitType='Y' and isnull(auditStatus,'N')='Y' " +
							"and isnull(auditStatus4,'N')='Y' "+
							"and isnull(auditStatus3,'N')='N' " +
							"and maintDivision like '"+maintDivision+"' order by jnlNo";
					List etfclistkk3=hs.createQuery(sqlaa).list();
					if(etfclistkk3!=null && etfclistkk3.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<etfclistkk3.size();e++){
							ContractInvoiceManage cim=(ContractInvoiceManage)etfclistkk3.get(e);
							
							String jnlno = cim.getJnlNo();
		
							//��Ҫ������ת·��
							String urlstr=SysConfig.WEB_APPNAME+"/contractInvoiceManageAudit3Action.do?id="+jnlno+"&method=toPrepareAuditRecord";
												
							String descstr="��Ʊ��ˮ�ţ�"+jnlno +
								" | ��ͬ�� ��"+cim.getContractNo()+
								" | ���״̬������δ���";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  cim.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				/**===============��Ʊ����  �ɶ�дȨ��===============*/
				String sqlkk3="select * from RoleNode where NodeID in('0903') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist3=hs.createSQLQuery(sqlkk3).list();
				if(rlist3!=null && rlist3.size()>0){
					String sqlaa=" from ContractInvoiceManage " +
							"where (submitType='R' or (submitType='Y' and  isnull(auditStatus3,'N')='Y')) " +
							"and isnull(workisdisplay,'N')='N' and operId='"+userid+"' order by jnlNo";
					List etfclistkk3=hs.createQuery(sqlaa).list();
					if(etfclistkk3!=null && etfclistkk3.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<etfclistkk3.size();e++){
							ContractInvoiceManage cim=(ContractInvoiceManage)etfclistkk3.get(e);
							
							String jnlno = cim.getJnlNo();
							String submit=cim.getSubmitType();
							
							//��Ҫ������ת·��
							String title="���ͨ��";
							String urlstr=SysConfig.WEB_APPNAME+"/contractInvoiceManageAction.do?id="+jnlno+"&method=toDisplayRecord&workisdisplay=Y";
							if(submit!=null && submit.trim().equals("R")){
								urlstr=SysConfig.WEB_APPNAME+"/contractInvoiceManageAction.do?id="+jnlno+"&method=toPrepareUpdateRecord";
								title="����";
							}
												
							String descstr="��Ʊ��ˮ�ţ�"+jnlno +
								" | ��ͬ�� ��"+cim.getContractNo()+
								" | ���״̬��"+title;
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  jnlno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  cim.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	/**
	 * ��ҳ=�ͻ��ط÷ֲ������� ����
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getCustReturnRegister(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		List reList = null;

		try {

			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
			
				//String maintDivision = "00".equals(comid) ? "%" : comid;
				String maintDivision = comid;
				
				/**===============�ͻ��ط÷ֲ�������  �ɶ�дȨ��===============*/
				String sqlkk="select * from RoleNode where NodeID in('0204') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa=" from CustReturnRegisterMaster " +
							"where ministerHandle='Y' and isProblem='Y' and ISNULL(handleId,'')='' " +
							"and maintDivision like '"+maintDivision+"' order by billno";
					List etfclistkk3=hs.createQuery(sqlaa).list();
					if(etfclistkk3!=null && etfclistkk3.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<etfclistkk3.size();e++){
							CustReturnRegisterMaster crrm=(CustReturnRegisterMaster)etfclistkk3.get(e);
							
							String billno = crrm.getBillno();
		
							//��Ҫ������ת·�� 
							String urlstr=SysConfig.WEB_APPNAME+"/custReturnRegisterMinisterHandleAction.do?id="+billno+"&method=toPrepareUpdateRecord";
												
							String descstr="��ϵ�ˣ�"+crrm.getContacts() +
								" | ��ϵ�˺���  ��"+crrm.getContactPhone()+
								" | ����״̬���ֲ���δ����";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  crrm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				/**===============�ط�רԱ����  �ɶ�дȨ��===============*/
				String sqlkk2="select * from RoleNode where NodeID in('0202') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist2=hs.createSQLQuery(sqlkk2).list();
				if(rlist2!=null && rlist2.size()>0){
					String sqlaa=" from CustReturnRegisterMaster " +
							"where ministerHandle='Y' and isProblem='Y' and ISNULL(handleId,'')<>'' " +
							"and operId='"+userid+"' " +
							"and isnull(workisdisplay,'N')='N' order by billno";
					List etfclistkk3=hs.createQuery(sqlaa).list();
					if(etfclistkk3!=null && etfclistkk3.size()>0){
						HashMap map = null;
						String url = "";
						for(int e=0;e<etfclistkk3.size();e++){
							CustReturnRegisterMaster crrm=(CustReturnRegisterMaster)etfclistkk3.get(e);
							
							String billno = crrm.getBillno();
		
							//��Ҫ������ת·�� 
							String urlstr=SysConfig.WEB_APPNAME+"/custReturnRegisterAction.do?id="+billno+"&method=toDisplayRecord&workisdisplay=Y";
												
							String descstr="��ϵ�ˣ�"+crrm.getContacts() +
								" | ��ϵ�˺���  ��"+crrm.getContactPhone()+
								" | ����״̬���ֲ����Ѵ���";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(e));
							map.put("url", urlstr);
							map.put("date",  crrm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	/**
	 * ��ҳ=ά����ͬ�´�ǩ��
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getContractAssigned(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = new ArrayList();
		Connection conn=null;
		
		try {
			conn = hs.connection();
			DataOperation op = new DataOperation();
			op.setCon(conn);
			
			if(!roleid.equals("A01")){
			
				/**===============ά�������´�ǩ��  �ɶ�дȨ��===============*/
				String sql="select * from RoleNode where NodeID in('0501') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sql).list();
				if(rlist!=null && rlist.size()>0){
						String edate1=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
						String sdate1=DateUtil.getDate(edate1, "MM", -1);//��ǰ�����·ݼ�1 ��
	
						String sqlkk="select mcm.maintContractNo as maintContractNo,mcd.salesContractNo as salesContractNo," +
								"mcm.taskSubDate as taskSubDate,mcd.elevatorNo as elevatorNo,mcm.taskSubDate as taskSubDate,mcd.rowid " +
								"from MaintContractMaster mcm,MaintContractDetail mcd " +
								"left join LoginUser b  on b.UserID=mcd.MaintPersonnel " +
								"left join LoginUser c on c.UserID=mcd.assignedSign " +
								"left join MaintenanceWorkPlanMaster mwpm  on mwpm.rowid=mcd.rowid," +
								"StorageID si   where mcd.billNo=mcm.billNo and si.StorageID=mcd.assignedMainStation " +
								"and mcd.assignedMainStation is not null and (mcm.contractStatus = 'ZB' or mcm.contractStatus='XB') " +
								"and mcm.taskSubDate >= '"+sdate1+" 00:00:00' and mcm.taskSubDate <= '"+edate1+" 23:59:59' " +
								"and (mcd.assignedSignFlag = 'N' or mcd.assignedSignFlag is null)  " +
								"and mcd.assignedMainStation like '"+storageid+"%' order by mcd.BillNo,mcd.rowid";
						//System.out.println(">>>>"+sqlkk);
						List listkk = op.queryToList(sqlkk);
						if(listkk!=null && listkk.size()>0){
							HashMap map=null;
							for(int i=0;i<listkk.size();i++){
								map=(HashMap)listkk.get(i);
								
								//��Ҫ������ת·��
								String urlstr=SysConfig.WEB_APPNAME+"/maintenanceReceiptAction.do?method=toSearchRecord&elevatorNo="+(String)map.get("elevatorno");
								
								String descstr="ά����ͬ�ţ�"+(String)map.get("maintcontractno")+
									" | ���ݱ�ţ�"+(String)map.get("elevatorno")+
									" | ǩ��״̬��δǩ��";
								
								map = new HashMap();
								map.put("title", descstr);
								map.put("sid",  (String)map.get("rowid")+String.valueOf(i));
								map.put("url", urlstr);
								map.put("date",  (String)map.get("tasksubdate"));
								map.put("title2", descstr);
								
								reList.add(map);
							}
						}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	/**
	 * ��ҳ=�û���Ϣ���
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getLoginUserAudit(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = new ArrayList();
		Connection conn=null;
		
		try {
			conn = hs.connection();
			DataOperation op = new DataOperation();
			op.setCon(conn);
			
			if(comid.equals("00")){
				comid="%";
			}
			
			/**===============�û���Ϣ���  �ɶ�дȨ��===============*/
			String sql="select * from RoleNode where NodeID in('7016') and WriteFlag='Y' and RoleID='"+roleid+"'";
			List rlist=hs.createSQLQuery(sql).list();
			if(rlist!=null && rlist.size()>0){
				//String edate1=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
				//String sdate1=DateUtil.getDate(edate1, "MM", -1);//��ǰ�����·ݼ�1 ��

				String sqlkk = "select a.userid,a.username,b.ComName from Loginuser a,Company b "
						+ "where a.grcid=b.ComID and isnull(a.auditoperid,'')='' "
						+ "and (isnull(a.newphone,'')<>'' or isnull(a.newemail,'')<>'' or isnull(a.nweidcardno,'')<>'') "
						+ "and a.grcid like '"+comid+"'";
				
				List listkk = op.queryToList(sqlkk);
				if(listkk!=null && listkk.size()>0){
					HashMap map=null;
					for(int i=0;i<listkk.size();i++){
						map=(HashMap)listkk.get(i);
						
						//��Ҫ������ת·��
						String urlstr=SysConfig.WEB_APPNAME+"/loginUserAuditAction.do?method=toDisplayRecord&id="+(String)map.get("userid");
						
						String descstr="�û����ƣ�"+(String)map.get("username")+
							" | �����ֲ���"+(String)map.get("comname")+
							" | �û������и���,����ˡ�";
						
						map = new HashMap();
						map.put("title", descstr);
						map.put("sid",  (String)map.get("rowid")+String.valueOf(i));
						map.put("url", urlstr);
						map.put("date",  (String)map.get("tasksubdate"));
						map.put("title2", descstr);
						
						reList.add(map);
					}
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	/**
	 * ��ҳ=���ް�ȫ�������
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getHotCalloutAudit(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = new ArrayList();
		Connection conn=null;
		
		try {
			conn = hs.connection();
			DataOperation op = new DataOperation();
			op.setCon(conn);
			
			if(!roleid.equals("A01")){
				/**===============���ް�ȫ�������  �ɶ�дȨ��===============*/
				String sql="select * from RoleNode where NodeID in('0609') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sql).list();
				if(rlist!=null && rlist.size()>0){
					//String edate1=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
					//String sdate1=DateUtil.getDate(edate1, "MM", -1);//��ǰ�����·ݼ�1 ��
	
					//if("00".equals(comid)){
					//	comid="%";
					//}
					
					String orderby=" order by calloutMasterNo asc";
					String sqlkk="exec HL_callhotsearch '%','%','%','8','Y','%','%','%','"+orderby+"','1','%','"+comid+"'";
					
					List listkk = op.queryToList(sqlkk);
					if(listkk!=null && listkk.size()>0){
						HashMap map=null;
						for(int i=0;i<listkk.size();i++){
							map=(HashMap)listkk.get(i);
							
							//��Ҫ������ת·��
							String urlstr=SysConfig.WEB_APPNAME+"/hotCalloutAuditAction.do?method=toDisplayRecord&typejsp=sh&id="+(String)map.get("calloutmasterno");
							
							String descstr="���ޱ�ţ�"+(String)map.get("calloutmasterno")+
								" | ����ά��վ��"+(String)map.get("maintstation")+
								" | ���״̬��δ��ˡ�";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  (String)map.get("rowid")+String.valueOf(i));
							map.put("url", urlstr);
							map.put("date",  (String)map.get("tasksubdate"));
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	/**
	 * ��ҳ=������뵥(�ֿ����Ա)���� 
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getAccessoriesRequisition(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = new ArrayList();
		Connection conn=null;
		
		try {
			conn = hs.connection();
			DataOperation op = new DataOperation();
			op.setCon(conn);
			
			if(!roleid.equals("A01")){
				/**===============������뵥(�ֿ����Ա)  �ɶ�дȨ��===============*/
				String sql="select * from RoleNode where NodeID in('7108') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sql).list();
				if(rlist!=null && rlist.size()>0){
					
					String sqlkstr="select ar.AppNo,ar.isCharges,ar.OperDate,lu.username,si.storagename,c.comfullname "
							+ "from AccessoriesRequisition ar,Loginuser lu,Company c ,Storageid si "
							+ "where c.comid=ar.maintDivision and si.storageid=maintStation "
							+ "and ar.operId=lu.userid and ar.handleStatus='2'"
							+ "order by ar.operdate";
					//System.out.println(sqlkstr);
					List listkk = op.queryToList(sqlkstr);
					if(listkk!=null && listkk.size()>0){
						HashMap map=null;
						for(int i=0;i<listkk.size();i++){
							map=(HashMap)listkk.get(i);
							
							//��Ҫ������ת·��
							String urlstr=SysConfig.WEB_APPNAME+"/accessoriesRequisitionStoremanAction.do?method=toPrepareUpdateRecord&id="+(String)map.get("appno");
							
							String ischarges=(String)map.get("ischarges");
							if(ischarges!=null && ischarges.trim().equals("Y")){
								ischarges="�շ�";
							}else{
								ischarges="���";
							}
							String descstr="�����ж��Ƿ��շѣ�"+ischarges+
									" | ����ά��վ��"+(String)map.get("storagename")+
									" | ����ά���ֲ���"+(String)map.get("comfullname");
									//" | ����״̬��δ����";
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  (String)map.get("appno")+String.valueOf(i));
							map.put("url", urlstr);
							map.put("date",  (String)map.get("operdate"));
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	/**
	 * ��ҳ=������뵥������� 
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getAccessoriesRequisitionCkbl(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = new ArrayList();
		Connection conn=null;
		
		try {
			conn = hs.connection();
			DataOperation op = new DataOperation();
			op.setCon(conn);
			
			if(!roleid.equals("A01")){
				String MaintStation="";
				//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
				String sqlss="select * from view_mainstation where roleid='"+roleid+"'";
				List vmlist=hs.createSQLQuery(sqlss).list();
				if(vmlist!=null && vmlist.size()>0){
					MaintStation=storageid;
				}
				/**===============������뵥�������  �ɶ�дȨ��===============*/
				String sql="select * from RoleNode where NodeID in('7115') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sql).list();
				if(rlist!=null && rlist.size()>0){
					
					String sqlkstr="select ar.AppNo,ar.ckiswc,ar.SingleNo,ar.OldNo "
							+ "from AccessoriesRequisition ar "
							+ "where ar.handleStatus in('4','5')"
							+ "and isnull(ar.ckiswc,'N')='N' "
							+ "and ar.MaintStation like '" + MaintStation.trim() + "%'"
							+ "order by ar.operdate";
					System.out.println(sqlkstr);
					List listkk = op.queryToList(sqlkstr);
					if(listkk!=null && listkk.size()>0){
						HashMap map=null;
						for(int i=0;i<listkk.size();i++){
							map=(HashMap)listkk.get(i);
							String appno=(String)map.get("appno");
							String singleno=(String)map.get("singleno");
							String oldno=(String)map.get("oldno");
							
							//��Ҫ������ת·��
							String urlstr=SysConfig.WEB_APPNAME+"/accessoriesRequisitionCkblAction.do?method=toPrepareUpdateRecord&id="+appno;

							String descstr="����/�������ţ�"+singleno+
									" | �������δ���"+
									" | �ɼ���ţ�"+oldno; 
							
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  (String)map.get("appno")+String.valueOf(i));
							map.put("url", urlstr);
							map.put("date",  (String)map.get("operdate"));
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return reList;
	}
	
	
	/**
	 * ��ҳ=��ͬ���������ɹ� [����]
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getTransferAssign(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;
		try {
			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
				//String maintDivision = "00".equals(comid) ? "%" : comid;
				String maintDivision = comid;
				
				/**===============�����ص�,��ͬ���������ɹ�  ��дȨ��===============*/
				String sqlkk="select * from RoleNode where NodeID in('1101') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="from ContractTransferMaster " +
							"where submitType='R' and maintDivision='"+maintDivision+"' order by billno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							ContractTransferMaster cm=(ContractTransferMaster)etfclist.get(j);
							
							String billno = cm.getBillNo();
							String elevatorno=cm.getElevatorNo();
							
							String typename="�ɹ��ύ��־������";
							String urlstr=SysConfig.WEB_APPNAME+"/ContractTransferAssignAction.do?id="+billno+"&method=toPrepareUpdateRecord";
							
							String descstr="��ˮ�ţ�"+billno+
									" | ���ݱ�� ��"+elevatorno+
									" | "+typename;
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  cm.getOperDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return reList;
	}
	
	/**
	 * ��ҳ=��ͬ���������ϴ� [���أ�δ�ϴ�]
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getTransferUpload(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;
		try {
			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
				//String maintDivision = "00".equals(comid) ? "%" : comid;
				String maintStation = storageid;
				
				String sqlkk="select * from RoleNode where NodeID in('1102') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="from ContractTransferMaster " +
							"where transfeSubmitType<>'Y' and submitType='Y'  ";
					
					if ("A03".equals(roleid)||"A48".equals(roleid)) {
						sqlaa += " and maintStation='"+maintStation+"' and isnull(isTrans,'N') = 'N' ";
					}else {
						sqlaa += " and isTrans = 'Y' and wbgTransfeId = '"+userid+"' ";
					}
					
					sqlaa += " order by billno ";
					
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							ContractTransferMaster cm=(ContractTransferMaster)etfclist.get(j);
							
							String billno = cm.getBillNo();
							String elevatorno=cm.getElevatorNo();
							String typename="";
							
							if(cm.getTransfeSubmitType()!=null&&"N".equals(cm.getTransfeSubmitType())){
								
								typename="�ϴ��ύ��־��δ�ϴ�";
							}else if(cm.getTransfeSubmitType()!=null&&"R".equals(cm.getTransfeSubmitType())){
								
								typename="�ϴ��ύ��־������";
							}
							
							
							String urlstr=SysConfig.WEB_APPNAME+"/ContractTransferUploadAction.do?id="+billno+"&method=toPrepareAddRecord&mainenter=Y";
							
							String descstr="��ˮ�ţ�"+billno+
									" | ���ݱ�� ��"+elevatorno+
									" | "+typename;
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  cm.getTransfeDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reList;
	}
	
	/**
	 * ��ҳ=��ͬ�����������[��������ˣ����ϴ�]
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getTransferAudit(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;
		try {
			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
				String maintDivision = "00".equals(comid) ? "%" : comid;
				//String maintDivision = comid;
				
				String sqlkk="select * from RoleNode where NodeID in('1103') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="from ContractTransferMaster " +
							"where isnull(AuditStatus,'N')<>'Y' and ((transfeSubmitType = 'Y' and isnull(isTrans,'N') = 'N') or (transfeSubmitType = 'Y' and isnull(isTrans,'N') = 'Y' and auditStatus2='Y')) and submitType='Y' "
							+ "and maintDivision like '"+maintDivision+"' order by billno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							ContractTransferMaster cm=(ContractTransferMaster)etfclist.get(j);
							
							String billno = cm.getBillNo();
							String elevatorno=cm.getElevatorNo();
							String typename="���״̬��δ���";
								
							
							
							String urlstr=SysConfig.WEB_APPNAME+"/ContractTransferAuditAction.do?id="+billno+"&method=toPrepareAddRecord";
							
							String descstr="��ˮ�ţ�"+billno+
									" | ���ݱ�� ��"+elevatorno+
									" | "+typename;
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  cm.getAuditDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reList;
	}
	
	/**
	 * ��ҳ=��ͬ�������Ϸ����鿴[��������]
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getTransferFeedBack(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;
		try {
			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
				String maintDivision = "00".equals(comid) ? "%" : comid;
				//String maintDivision = comid;
				
				String sqlkk="select * from RoleNode where NodeID in('1104') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="select cm.billNo,cm.elevatorNo,cb.OperDate "
					+"from ContractTransferMaster cm "
					+"join ContractTransferFeedback cb "
					+"on cm.BillNo=cb.BillNo and cm.workisdisplay<>'Y' and cm.maintDivision like '"+maintDivision+"' "
					+"and cb.OperDate=(select max(cfb.OperDate) from ContractTransferFeedback cfb where cm.BillNo=cfb.BillNo) ";
					List etfclist=hs.createSQLQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							Object[] objs=(Object[])((Object)etfclist.get(j));
							
							String billno = (String) objs[0];
							String elevatorno=(String) objs[1];
							String operdate=(String) objs[2];
							
							
							
							String urlstr=SysConfig.WEB_APPNAME+"/ContractTransferFeedBackAction.do?id="+billno+"&method=toDisplayRecord&workisdisplay=Y";
							
							String descstr="��ˮ�ţ�"+billno+
									" | ���ݱ�� ��"+elevatorno+
									" | �������ڣ� "+operdate;
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  operdate);
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reList;
	}
	
	/**
	 * ��ҳ=��ͬ�������Ͼ������[���ϴ���ת�ɣ�����δ���]
	 * 
	 * @param key
	 * @param hs
	 * @return
	 * @throws HibernateException
	 */
	private List getTransferManagerAudit(HttpServletRequest request,
			Session hs, String comid,String roleid,String storageid,String userid) throws HibernateException {
		
		List reList = null;
		HashMap map = null;
		try {
			reList = new ArrayList();
			
			if(!roleid.equals("A01")){
				String maintDivision = "00".equals(comid) ? "%" : comid;
				//String maintDivision = comid;
				
				String sqlkk="select * from RoleNode where NodeID in('1105') and WriteFlag='Y' and RoleID='"+roleid+"'";
				List rlist=hs.createSQLQuery(sqlkk).list();
				if(rlist!=null && rlist.size()>0){
					String sqlaa="from ContractTransferMaster " +
							"where isnull(AuditStatus2,'N')<>'Y' and transfeSubmitType = 'Y' and isTrans = 'Y' "
							+ "and MaintStation like '"+storageid+"' order by billno ";
					List etfclist=hs.createQuery(sqlaa).list();
					if(etfclist!=null && etfclist.size()>0){
						String url = "";
						for(int j=0;j<etfclist.size();j++){
							ContractTransferMaster cm=(ContractTransferMaster)etfclist.get(j);
							
							String billno = cm.getBillNo();
							String elevatorno=cm.getElevatorNo();
							String typename="���״̬��δ���";
								
							
							
							String urlstr=SysConfig.WEB_APPNAME+"/ContractTransferManagerAuditAction.do?id="+billno+"&method=toPrepareAddRecord";
							
							String descstr="��ˮ�ţ�"+billno+
									" | ���ݱ�� ��"+elevatorno+
									" | "+typename;
							map = new HashMap();
							map.put("title", descstr);
							map.put("sid",  billno+String.valueOf(j));
							map.put("url", urlstr);
							map.put("date",  cm.getAuditDate());
							map.put("title2", descstr);
							
							reList.add(map);
						}
					}
				}
				
				
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return reList;
	}
	
	
	public String toXML(List list) {
		StringBuffer sb=new StringBuffer();
		if(list!=null && list.size()>0){
			sb.append("<?xml version='1.0' encoding='gbk'?>");
			sb.append("<dataset>");
			
			int len=list.size();
			for(int i=0;i<len;i++){
				HashMap mp=(HashMap)list.get(i);
				sb.append("<row>");
				sb.append("<sid>").append((String)mp.get("sid")).append("</sid>");
				sb.append("<title>").append(escapeXML((String)mp.get("title"))).append("</title>");
				sb.append("<url>").append(escapeXML((String)mp.get("url"))).append("</url>");
				sb.append("<date>").append((String)mp.get("date")).append("</date>");
				sb.append("<title2>").append(escapeXML((String)mp.get("title2"))).append("</title2>");
				sb.append("</row>");
			}
			sb.append("</dataset>");
		}
		////System.out.println(sb.toString());
		return sb.toString();
	}
	
	public static String escapeXML (String dangerous){
		if(dangerous==null){
			return dangerous;
		}
		if( dangerous.indexOf("&")  == -1 && 
            dangerous.indexOf("\"") == -1 && 
            dangerous.indexOf("'") == -1 && 
            dangerous.indexOf("<")  == -1 && 
            dangerous.indexOf(">") == -1    
        ){
            return dangerous;
        } else {
            dangerous = dangerous.replaceAll("&" , "&amp;" );
            dangerous = dangerous.replaceAll("\"", "&quot;");
            dangerous = dangerous.replaceAll("'" , "&apos;");
            dangerous = dangerous.replaceAll("<" , "&lt;"  );
            dangerous = dangerous.replaceAll(">" , "&gt;"  );
            return dangerous;
        }
    }

}