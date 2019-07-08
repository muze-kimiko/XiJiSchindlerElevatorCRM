package com.gzunicorn.struts.action.infomanager.servicingcontractmaster;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.ResultSetDynaClass;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractdetail.ServicingContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.servicingcontractmaster.ServicingContractMaster;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.struts.action.engcontractmanager.servicingcontract.ServicingContractAction;
import com.gzunicorn.struts.action.xjsgg.XjsggAction;

public class ServicingContractMasterTaskAction extends DispatchAction {
	
	Log log = LogFactory.getLog(ServicingContractAction.class);
	XjsggAction xj=new XjsggAction();
	BaseDataImpl bd = new BaseDataImpl();
	DataOperation bo=new DataOperation();
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

		/** **********��ʼ�û�Ȩ�޹���*********** */
		SysRightsUtil.filterModuleRight(request, response,
				SysRightsUtil.NODE_ID_FORWARD + "ServicingContractMasterTask", null);
		/** **********�����û�Ȩ�޹���*********** */

		// Set default method is toSearchRecord
		String name = request.getParameter("method");
		if (name == null || name.equals("")) {
			name = "toSearchRecord";
			return dispatchMethod(mapping, form, request, response, name);
		} else {
			if( "arrival".equals(name)){
				name="toAddRecord";
				return dispatchMethod(mapping, form, request, response, name);
			}
			if( "complete".equals(name)){
				name="toAddRecord";
				return dispatchMethod(mapping, form, request, response, name);
			}
			ActionForward forward = super.execute(mapping, form, request,
					response);
			return forward;
		}

	}
	
	/**
	 * Method toSearchRecord execute, Search record
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */

	@SuppressWarnings("unchecked")
	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {				
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform=(DynaActionForm)form;
		ActionForward  Forward=null;
		Session hs=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		String maintDivision2="";
		List list=new ArrayList();
		String typejsp=request.getParameter("typejsp");
		if(typejsp== null){
			typejsp="search";
		}
		List maintDivisionList=Grcnamelist1.getgrcnamelist(userInfo.getUserID());
		if(maintDivisionList!=null && maintDivisionList.size()>0){
			Map map = (Map)maintDivisionList.get(0);
			maintDivision2 = (String)map.get("grcid");
		}
		String busType=request.getParameter("busType");//ҵ�����
		String maintContractNo=request.getParameter("maintContractNo");//��ͬ��
		String maintDivision=request.getParameter("maintDivision");//����ά���ֲ�
		String finishFlag=request.getParameter("FinishFlag22");//�깤��־
		String ForecastDatestatr=request.getParameter("ForecastDatestatr");//Ԥ�Ƶ������ڿ�ʼ
		String ForecastDateend=request.getParameter("ForecastDateend");//Ԥ�Ƶ������ڽ���
		try {
			hs=HibernateUtil.getSession();
			String sql="exec ContractMasterTask ";
			if(maintDivision==null || "".equals(maintDivision)){		
			sql+="'"+maintDivision2.trim()+"'";
			}else{
			sql+="'%"+maintDivision.trim()+"%'";
			}		
			sql+=",'"+typejsp.trim()+"'";
			if(busType==null || "".equals(busType)){		
			sql+=",'%'";
			}else{
			sql+=",'"+busType.trim()+"'";
			request.setAttribute("busType", busType);
			}
			if(maintContractNo==null || "".equals(maintContractNo)){
			sql+=",'%'";
			}else{
			sql+=",'%"+maintContractNo.trim()+"%'";
			request.setAttribute("maintContractNo", maintContractNo);
			}
			if(finishFlag==null || "".equals(finishFlag)){
			sql+=",'N'";
			request.setAttribute("finishFlag", "N");
			}else{
			sql+=",'"+finishFlag.trim()+"'";
			request.setAttribute("finishFlag", finishFlag);
			}
			if(ForecastDatestatr==null || "".equals(ForecastDatestatr)){
			sql+=",'0000-00-00'";
			}else{
			sql+=",'"+ForecastDatestatr.trim()+"'";
			request.setAttribute("ForecastDatestatr", ForecastDatestatr);
			}
			if(ForecastDateend==null || "".equals(ForecastDateend)){
			sql+=",'9999-99-99'";
			}else{
			sql+=",'"+ForecastDateend.trim()+"'";
			request.setAttribute("ForecastDateend", ForecastDateend);
			}
			//System.out.println(sql);
			if(typejsp!=null && !"search".equals(typejsp)){	
				if( "arrival".equals(typejsp)){
					request.setAttribute("navigator.location", "Ԥ��/ȷ�ϵ�������  >> ¼����Ϣ");
				}else {
					request.setAttribute("navigator.location", "��Ŀ�깤 >> ¼����Ϣ");
				}
				bo.setCon(hs.connection());
				list=bo.queryToList(sql);
				if(list!=null &&list.size()>0){
					for(int i=0;i<list.size();i++){
					HashMap hm=new HashMap();
					hm=(HashMap)list.get(i);
					hm.put("bustype", hm.get("bustype").equals("W")?"ά��":"����");
					hm.put("taskuserid",bd.getName("Loginuser", "username", "userid", hm.get("taskuserid").toString()));
					hm.put("companyid",bd.getName("Customer", "companyName", "companyId", hm.get("companyid").toString()));
					hm.put("maintdivision", bd.getName("Company", "comname", "comid", hm.get("maintdivision").toString()));
					if("complete".equals(typejsp)){
					hm.put("epibolyFlag", "");	
					}
					}
				}
				request.setAttribute("ServicingContractMasterList", list);
			}else{	
				request.setAttribute("navigator.location", "ά�������´����  >> �����б�");
				ps=hs.connection().prepareStatement(sql);			
				rs=ps.executeQuery();
				while(rs.next()){
					request.setAttribute("Arrival", rs.getString("num"));
					request.setAttribute("Complete", rs.getString("numtwo"));
				}	
			}	
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}try {
			hs.close();
			if (rs != null) {
				rs.close();
			}
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			DebugUtil
					.print("DataOperation.queryToList() " + e.getMessage());
		}
		request.setAttribute("typejsp", typejsp);
		request.setAttribute("maintDivisionList",maintDivisionList);
		Forward=mapping.findForward("toArrivalComplete");
		return Forward;
	}
	
	/**
	 * Method toUpdateRecord execute,Update data to database and return list
	 * page or modifiy page
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 */
	public ActionForward toAddRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)throws IOException, ServletException {
		
		ActionForward forward = null;
		DynaActionForm dform = (DynaActionForm)form;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ActionErrors errors = new ActionErrors();
		String name = request.getParameter("method");
		String[] isboxs=request.getParameterValues("isbox");//ҳ�湴ѡ��Ҫ���������
		String[] wgbillno=request.getParameterValues("wgbillno");//��ˮ��
		String[] forecastdate=request.getParameterValues("forecastdate");//Ԥ�Ƶ�������
		String[] comfirmdate=request.getParameterValues("comfirmdate");//ȷ�ϵ�������
		String[] ItemUserId=request.getParameterValues("itemUserId");//��Ŀ������
		String[] AppWorkDate=request.getParameterValues("appWorkDate");//�ɹ�����	
		String[] EnterArenaDate=request.getParameterValues("enterArenaDate");//��������
		String[] EpibolyFlag=request.getParameterValues("epibolyFlag");//�����־
		String[] FinishFlag=request.getParameterValues("finishFlag");//�깤��־
		String[] FinishDate=request.getParameterValues("finishDate");//�깤����
		String[] finishRem=request.getParameterValues("finishRem");//�깤��ע
		String finishId=userInfo.getUserID();//�깤ȷ����
		
		Session hs = null;
		Transaction tx = null;
		ServicingContractMaster scm=null;
		if(isboxs!=null && isboxs.length>0){
			try {
				hs = HibernateUtil.getSession();
				tx = hs.beginTransaction();
              if(name!=null && "arrival".equals(name)){
				for(int i=0;i<isboxs.length;i++){
					if(isboxs[i]!=null && isboxs[i].equals("Y")){
						scm=(ServicingContractMaster)hs.get(ServicingContractMaster.class, wgbillno[i]);
						scm.setForecastDate(forecastdate[i]);
						scm.setComfirmDate(comfirmdate[i]);
						hs.update(scm);
					}
				}
				}else if(name!=null && "complete".equals(name)){
					for(int i=0;i<isboxs.length;i++){
						if(isboxs[i]!=null && isboxs[i].equals("Y")){
							scm=(ServicingContractMaster)hs.get(ServicingContractMaster.class, wgbillno[i]);
							scm.setItemUserId(ItemUserId[i]);
							scm.setAppWorkDate(AppWorkDate[i]);
							scm.setEnterArenaDate(EnterArenaDate[i]);
							scm.setEpibolyFlag(EpibolyFlag[i]);
							scm.setFinishFlag(FinishFlag[i]);
							scm.setFinishDate(FinishDate[i]);
							scm.setFinishRem(finishRem[i]);
							scm.setFinishId(finishId);
							hs.update(scm);
						}
					}
				}
				tx.commit();
			} catch (Exception e2) {
				try {
					tx.rollback();
				} catch (HibernateException e3) {
					log.error(e3.getMessage());
				}
				e2.printStackTrace();
				log.error(e2.getMessage());
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.fail"));
			} finally {
				try {
					if(hs!=null){
						hs.close();
					}
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "Hibernate close error!");
				}
			}
		}

		
		String isreturn = request.getParameter("isreturn");
		if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			forward = mapping.findForward("returnList");
		} else {
			if (errors.isEmpty()) {
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
			} else {
				request.setAttribute("error", "Yes");
			}
			if(name!=null && "arrival".equals(name)){
			forward = mapping.findForward("returnArrivalList");
			}else{
			forward = mapping.findForward("returnCompleteList");	
			}
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		return forward;
	}
	
	/**
	 * ����鿴�ķ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDisplayRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","ά�������´� >> �鿴");
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;			
		String id = request.getParameter("id");
		Session hs = null;
		ServicingContractMaster scm=null;
		Customer ctj=null;
		Customer cty=null;
		List ServicingContractDetailList = new ArrayList();
	   if (id != null) {				
		try {
			hs = HibernateUtil.getSession();
			scm=(ServicingContractMaster)hs.get(ServicingContractMaster.class, id);
			scm.setAuditStatus(scm.getAuditStatus().equals("Y")?"�����":"δ���");
			scm.setTaskSubFlag(scm.getTaskSubFlag().equals("Y")?"���´�":"δ�´�");
			scm.setAttn(bd.getName("Loginuser", "username", "userid", scm.getAttn()));
			scm.setMaintDivision(bd.getName("Company", "comname", "comid", scm.getMaintDivision()));
			request.setAttribute("maintStationName", bd.getName("Company", "comname", "comid", scm.getMaintDivision()));
			scm.setAuditOperid(bd.getName("Loginuser", "username", "userid", scm.getAuditOperid()));
			scm.setTaskUserId(bd.getName("Loginuser", "username", "userid", scm.getTaskUserId()));
			//�׷���λ
			String companyId=scm.getCompanyId();
			if(companyId!=null && !"".equals(companyId)){
				ctj=(Customer)hs.get(Customer.class, companyId);
			}
			//�ҷ���λ
			String companyId2=scm.getCompanyId2();
			if(companyId2!=null && !"".equals(companyId2)){
				cty=(Customer)hs.get(Customer.class, companyId2);
			}
			
			List list=hs.createQuery("select a from ServicingContractDetail a where a.servicingContractMaster.wgBillno='"+id+"'").list();	
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					ServicingContractDetail scd=(ServicingContractDetail)list.get(i);
					ServicingContractDetailList.add(scd);
				}
			}
			
		} catch (DataStoreException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}				
		
	}else{
		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
	}
	request.setAttribute("ServicingContractMasterList", scm);
	request.setAttribute("CustomerList", ctj);
	request.setAttribute("CustomerList2", cty);
	request.setAttribute("ServicingContractDetailList", ServicingContractDetailList);
	request.setAttribute("typejsp", "display");
	if (!errors.isEmpty()) {
		saveErrors(request, errors);
	}
		forward = mapping.findForward("toArrivalComplete");
		return forward;
	}
}
