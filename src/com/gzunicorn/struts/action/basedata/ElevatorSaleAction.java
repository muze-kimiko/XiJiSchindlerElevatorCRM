package com.gzunicorn.struts.action.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFComment;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.upload.FormFile;
import org.apache.struts.util.MessageResources;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;
import com.gzunicorn.hibernate.basedata.city.City;
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.basedata.elevatorsales.ElevatorSalesInfo;
import com.gzunicorn.hibernate.basedata.principal.Principal;
import com.gzunicorn.hibernate.basedata.region.Region;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class ElevatorSaleAction extends DispatchAction {

	Log log = LogFactory.getLog(ElevatorSaleAction.class);
	
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
		
		if(!"toDisplayRecord".equals(name)){
			/** **********��ʼ�û�Ȩ�޹���*********** */
			SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "elevatorSale", null);
			/** **********�����û�Ȩ�޹���*********** */
		}

		// Set default method is toSearchRecord
		if (name == null || name.equals("")) {
			name = "toSearchRecord";
			return dispatchMethod(mapping, form, request, response, name);
		} else {
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
		
		request.setAttribute("navigator.location","����������Ϣ >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		if (tableForm.getProperty("genReport") != null
				&& !tableForm.getProperty("genReport").equals("")) {
			try {
				response = toExcelRecord(form,request,response);
			} catch (IOException e) {
				e.printStackTrace();
			}
			forward = mapping.findForward("exportExcel");
			tableForm.setProperty("genReport","");

		} else {
			
			HTMLTableCache cache = new HTMLTableCache(session, "elevatorSaleList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fElevatorSale");
			table.setLength(SysConfig.HTML_TABLE_LENGTH);
			cache.updateTable(table);
			table.setSortColumn("elevatorNo");
			table.setIsAscending(true);
			cache.updateTable(table);

			if (action.equals(ServeTableForm.NAVIGATE)
					|| action.equals(ServeTableForm.SORT)) {
				cache.loadForm(tableForm);
			} else if (action.equals("Submit")) {
				cache.loadForm(tableForm);
			} else {
				table.setFrom(0);
			}
			cache.saveForm(tableForm);

			String elevatorNo = tableForm.getProperty("elevatorNo");
			String elevatorType = tableForm.getProperty("elevatorType");
			String salesContractNo = tableForm.getProperty("salesContractNo");
			String inspectDates = tableForm.getProperty("inspectDates");
			String inspectDatee = tableForm.getProperty("inspectDatee");
			String enabledFlag = tableForm.getProperty("enabledFlag");

			String eler1 = tableForm.getProperty("eler1");
			String r2 = tableForm.getProperty("r2");
			String r3 = tableForm.getProperty("r3");
			String r4 = tableForm.getProperty("r4");
			String daddress = tableForm.getProperty("daddress");
			
			Session hs = null;
			Query query = null;
			try {

				hs = HibernateUtil.getSession();
				//�Ƿ��ڱ��ֶ��Զ��ж�=��ͬ�����������ģ���ͬ״̬����ǩ����ǩ�����´��
				String sql="select a.elevatorNo,a.salesContractNo,a.salesContractName,a.inspectDate, "
						+ "a.weight,a.speed,a.seriesName,a.elevatorType,a.floor,a.stage,a.door,a.high, "
						+ "a.elevatorParam,a.enabledFlag,c.eler1,a.R2,a.R3,a.R4,a.DeliveryAddress "
						+ "from ElevatorSalesInfo a, "
						+ "	 (select e.elevatorNo,case when isnull(e2.ElevatorNo,'')='' then '��' else '��' end as eler1 "
						+ "	   from ElevatorSalesInfo e "
						+ "			left join ("
						+ "				select distinct b.ElevatorNo from MaintContractMaster m,MaintContractDetail b "
						+ "				where m.BillNo=b.BillNo and m.ContractStatus in('ZB','XB') "
						+ "				and ISNULL(m.TaskSubFlag,'N')='Y' and ISNULL(b.IsSurrender,'N')='N' "
						+ "			) e2 on e2.ElevatorNo=e.ElevatorNo "    
						+ "	 where isnull(e.isOutsideFootball,'N')='N') c "
						+ "where isnull(a.isOutsideFootball,'N')='N' and a.ElevatorNo=c.ElevatorNo ";
				if (elevatorNo != null && !elevatorNo.equals("")) {
					sql+=" and a.elevatorNo like '%"+elevatorNo.trim()+"%'";
				}
				if (elevatorType != null && !elevatorType.equals("")) {
					sql+=" and a.elevatorType like '"+elevatorType.trim()+"'";
				}
				if (salesContractNo != null && !salesContractNo.equals("")) {
					sql+=" and a.salesContractNo like '%"+salesContractNo.trim()+"%'";
				}
				if (enabledFlag != null && !enabledFlag.equals("")) {
					sql+=" and a.enabledFlag like '"+enabledFlag.trim()+"'";
				}
				if (inspectDates != null && !inspectDates.equals("")) {
					sql+=" and a.InspectDate >='"+inspectDates.trim()+"'";
				}
				if (inspectDatee != null && !inspectDatee.equals("")) {
					sql+=" and a.InspectDate <='"+inspectDatee.trim()+"'";
				}
				if (eler1 != null && !eler1.equals("")) {
					sql+=" and c.eler1 like '"+eler1.trim()+"'";
				}
				if (r2 != null && !r2.equals("")) {
					sql+=" and a.r2 like '%"+r2.trim()+"%'";
				}
				if (r3 != null && !r3.equals("")) {
					sql+=" and a.r3 like '%"+r3.trim()+"%'";
				}
				if (r4 != null && !r4.equals("")) {
					sql+=" and a.r4 like '%"+r4.trim()+"%'";
				}
				if (daddress != null && !daddress.equals("")) {
					sql+=" and a.DeliveryAddress like '%"+daddress.trim()+"%'";
				}
				if (table.getIsAscending()) {
					sql += " order by "+ table.getSortColumn() +" asc";
				} else {
					sql += " order by "+ table.getSortColumn() +" desc";
				}
				//System.out.println(">>>"+sql);
				
				query = hs.createSQLQuery(sql);
				table.setVolume(query.list().size());// ��ѯ�ó����ݼ�¼��;

				//�ó���һҳ�����һ����¼����;
				query.setFirstResult(table.getFrom()); // pagefirst
				query.setMaxResults(table.getLength());

				cache.check(table);

				List elevatorSaleList = query.list();
				List relist=new ArrayList();
	            if(elevatorSaleList!=null && elevatorSaleList.size()>0){
	            	for(Object object : elevatorSaleList){
	            		Object[] objs=(Object[])object;
	            		Map map=new HashMap();
	            		map.put("elevatorNo",objs[0]);
	            		map.put("salesContractNo",objs[1]);
	            		map.put("salesContractName",objs[2]);
	            		map.put("inspectDate",objs[3]);
	            		map.put("weight",objs[4]);
	            		map.put("speed",objs[5]);
	            		map.put("seriesName",objs[6]);
	            		map.put("elevatorType",objs[7]);
	            		map.put("floor",objs[8]);
	            		map.put("stage",objs[9]);
	            		map.put("door",objs[10]);
	            		map.put("high",objs[11]);
	            		map.put("elevatorParam",objs[12]);
	            		map.put("enabledFlag",objs[13]);
	            		
	            		map.put("eler1",objs[14]);
	            		map.put("r2",objs[15]);
	            		map.put("r3",objs[16]);
	            		map.put("r4",objs[17]);
	            		map.put("DeliveryAddress",objs[18]);
	            		relist.add(map);
	            		
	            	}
	            }

				table.addAll(relist);
				session.setAttribute("elevatorSaleList", table);
				request.setAttribute("elevatorTypeList", bd.getPullDownList("ElevatorSalesInfo_type"));				
			} catch (DataStoreException e) {
				e.printStackTrace();
			} catch (HibernateException e1) {

				e1.printStackTrace();
			} finally {
				try {
					hs.close();
					// HibernateSessionFactory.closeSession();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			forward = mapping.findForward("elevatorSaleList");
		}
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

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		
		request.setAttribute("navigator.location","����������Ϣ >> �鿴");

		ActionForward forward = null;
		String id = (String) dform.get("id");
		String isOpen=request.getParameter("isOpen");
		Session hs = null;
		ElevatorSalesInfo elevatorSalesInfo = null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from ElevatorSalesInfo e where e.elevatorNo = :elevatorNo");
				query.setString("elevatorNo", id);
				List list = query.list();
				if (list != null && list.size() > 0) {
					elevatorSalesInfo = (ElevatorSalesInfo) list.get(0);
					
					//�������Ƿ��ڱ�����ͬ״̬����ǩ����ǩ�����´��,û���˱��ġ�
					String sqlk="select m.ContractStatus from MaintContractMaster m,MaintContractDetail b "
							+ "where m.BillNo=b.BillNo and b.ElevatorNo='"+id+"' "
							+ "and m.ContractStatus in('ZB','XB') and ISNULL(m.TaskSubFlag,'N')='Y' "
							+ "and ISNULL(b.IsSurrender,'N')='N'";
					List seqlist=hs.createSQLQuery(sqlk).list();
					if(seqlist!=null && seqlist.size()>0){
						elevatorSalesInfo.setR1("��");
					}else{
						elevatorSalesInfo.setR1("��");
					}
					
				} 
				
				if (elevatorSalesInfo == null) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
			} catch (DataStoreException e) {
				e.printStackTrace();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			} finally {
				try {
					hs.close();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}

			request.setAttribute("display", "yes");
			request.setAttribute("isOpen", isOpen);
			request.setAttribute("elevatorSaleBean", elevatorSalesInfo);
			forward = mapping.findForward("elevatorSaleDisplay");

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	@SuppressWarnings("unchecked")
	public ActionForward toPrepareImportRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

		request.setAttribute("navigator.location","����������Ϣ >> ����");

		DynaActionForm dform = (DynaActionForm) form;
		if (request.getAttribute("error") == null
				|| request.getAttribute("error").equals("")) {
			dform.initialize(mapping);
		}

		return mapping.findForward("elevatorSaleImport");
	}
	
	/**
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	
	public ActionForward toImportRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		FormFile file = (FormFile) dform.get("file"); //��ȡ�ϴ��ļ�
		String fileName = file.getFileName();
		String fileFromt=fileName.substring(
				fileName.lastIndexOf(".")+1,fileName.length()); //��ȡ�ϴ��ļ��ĺ�׺��
		
		InputStream in = null;
		Session hs = null;
		Transaction tx = null;
		StringBuffer reStr = new StringBuffer(); //���󷵻���Ϣ
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();

			if (fileFromt!=null && fileFromt.equals("xlsx")) {//excel 2007
				
				ElevatorSalesInfo master = null; //����������Ϣ��
				List<ElevatorSalesInfo> list = new ArrayList<ElevatorSalesInfo>();
				
				in = file.getInputStream();
				XSSFWorkbook wb = new XSSFWorkbook(in);
				XSSFSheet sheet = wb.getSheetAt(0);
				XSSFRow row = null;			
				
				int rowSum = sheet.getLastRowNum()+1; //�������
				String elevatorNo = ""; //���ݱ��
				String elevatorType = ""; //��������
				String ids = "";
				
				String userid=userInfo.getUserID();
				String today=CommonUtil.getToday();
				for(int rowNum = 1; rowNum < rowSum; rowNum++){					
					row = sheet.getRow(rowNum);			
					
					master = new ElevatorSalesInfo();						
					elevatorNo = cellValueToString(row, 2, reStr); //���ݱ��
					elevatorType = cellValueToString(row, 6, reStr); //��������					
					if("ֱ��".equals(elevatorType)){
						elevatorType = "T";
					} else if("����".equals(elevatorType)) {
						elevatorType = "F";
						//master.setSeriesId(cellValueToString(row, 22, reStr)); //��������
					}
					
					master.setSalesContractNo(cellValueToString(row, 0, reStr)); //��ͬ���
					master.setInspectDate(cellDateValueToString(row, 1, reStr)); //ǩ������
					master.setElevatorNo(elevatorNo); //���ݱ��
					master.setWeight(cellValueToString(row, 3, reStr)); //����
					master.setSpeed(cellValueToString(row, 4, reStr)); //�ٶ�
					master.setSeriesName(cellValueToString(row, 5, reStr)); //����ϵ��
					master.setElevatorType(elevatorType); //��������
					master.setFloor(cellValueToInt(row, 7, reStr)); //��
					master.setStage(cellValueToInt(row, 8, reStr)); //վ
					master.setDoor(cellValueToInt(row, 9, reStr)); //��
					master.setHigh(cellValueToDouble(row, 10, reStr)); //�����߶�
					master.setElevatorParam(cellValueToString(row, 11, reStr)); //����ͺ�
					master.setSalesContractName(cellValueToString(row, 12, reStr)); //��ͬ����
					master.setSalesContractType(cellValueToString(row, 13, reStr)); //��ͬ����
					master.setDealer(cellValueToString(row, 14, reStr)); //�ͻ���λ
					master.setUseUnit(cellValueToString(row, 15, reStr)); //ʹ�õ�λ
					master.setDepartment(cellValueToString(row, 16, reStr)); //���۲���
					master.setOperationName(cellValueToString(row, 17, reStr)); //ҵ��Ա
					master.setOperationPhone(cellValueToString2(row, 18, reStr)); //ҵ��Ա��ϵ�绰
					master.setDeliveryAddress(cellValueToString2(row, 19, reStr)); //������ַ
					master.setMaintenanceClause(cellValueToString2(row, 20, reStr)); //ά������	
					master.setWarranty(cellValueToString2(row, 21, reStr)); //�ʱ���
					master.setOperId(userid);
					master.setOperDate(today);
					master.setIsOutsideFootball("N");
					master.setEnabledFlag("Y");
					
					if(reStr != null && reStr.length() > 0){
						break;
					}
					
					list.add(master);	
					
					ids +=  rowNum < rowSum-1 ? "'" + elevatorNo + "'," : "'" + elevatorNo + "'";
				}
	
				if(reStr == null || reStr.length() == 0){
					String hql = "delete ElevatorSalesInfo where elevatorNo in ("+ids+")"; //ɾ���ɰ汾����
					Query query = hs.createQuery(hql); 
					query.executeUpdate();
					hs.flush();
					
					for (ElevatorSalesInfo einfo : list) {						
						hs.save(einfo);
						//hs.flush();
					}
				} else {
					request.setAttribute("reStr", reStr);//���󷵻���Ϣ
				}
			
			}

			tx.commit();
		}catch (Exception e1) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>����ʧ�ܣ�</font>"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate region Insert error!");
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		ActionForward forward = mapping.findForward("returnImport");
		
		if (errors.isEmpty() && reStr.length() == 0) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
				
		} else {
			request.setAttribute("error", "Yes");
		}
			
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		return forward;
	}
	
	/**
	 * ��ת���޸ļ���ҳ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public ActionForward toPrepareUpdateRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		
		request.setAttribute("navigator.location","����������Ϣ >> �޸�");	
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();		
		ActionForward forward = null;
		
		String id = (String) dform.get("elevatorNo");
		if (id == null || "".equals(id)) {
			id = (String) dform.get("id");
		}

		Session hs = null;
		ElevatorSalesInfo elevatorSalesInfo = null;
		if (id != null) {
			if (request.getAttribute("error") == null
					|| request.getAttribute("error").equals("")) {
				try {
					hs = HibernateUtil.getSession();
					Query query = hs.createQuery("from ElevatorSalesInfo e where e.elevatorNo = :elevatorNo");
					query.setString("elevatorNo", id);
					List list = query.list();
					if (list != null && list.size() > 0) {
						elevatorSalesInfo = (ElevatorSalesInfo) list.get(0);
						dform.set("enabledFlag", elevatorSalesInfo.getEnabledFlag());
						dform.set("seriesId", elevatorSalesInfo.getSeriesId());
						
						//�������Ƿ��ڱ�����ͬ״̬����ǩ����ǩ�����´��,û���˱��ġ�
						String sqlk="select m.ContractStatus from MaintContractMaster m,MaintContractDetail b "
								+ "where m.BillNo=b.BillNo and b.ElevatorNo='"+id+"' "
								+ "and m.ContractStatus in('ZB','XB') and ISNULL(m.TaskSubFlag,'N')='Y' "
								+ "and ISNULL(b.IsSurrender,'N')='N'";
						List seqlist=hs.createSQLQuery(sqlk).list();
						if(seqlist!=null && seqlist.size()>0){
							elevatorSalesInfo.setR1("��");
						}else{
							elevatorSalesInfo.setR1("��");
						}
					} else {
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
					}

				} catch (DataStoreException e) {
					e.printStackTrace();
				} catch (HibernateException e1) {
					e1.printStackTrace();
				} finally {
					try {
						hs.close();
					} catch (HibernateException hex) {
						log.error(hex.getMessage());
						DebugUtil
								.print(hex, "HibernateUtil Hibernate Session ");
					}
				}
			}
			request.setAttribute("elevatorSaleBean", elevatorSalesInfo);
			request.setAttribute("elevatorTypeList", bd.getPullDownList("ElevatorSalesInfo_type"));
			request.setAttribute("pullDownList",bd.getPullDownList("enabledflag"));
			request.setAttribute("seriesIdList",bd.getPullDownList("ElevatorSalesInfo_SeriesId"));
			forward = mapping.findForward("elevatorSaleModify");
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
		
	/**
	 * �޸ı���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toUpdateRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		Session hs = null;
		Transaction tx = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);	
        String configuring=null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			//String r1 = request.getParameter("r1");//�Ƿ��ڱ�
			String r2 = request.getParameter("r2");//����ʡ��
			String r3 = request.getParameter("r3");//������
			String r4 = request.getParameter("r4");//������/��
			
			ElevatorSalesInfo elevatorSalesInfo = (ElevatorSalesInfo) hs.get(ElevatorSalesInfo.class, (String) dform.get("id"));
			if (dform.get("id") != null
					&& dform.get("elevatorNo") != null
					&& !((String) dform.get("id")).equals((String) dform.get("elevatorNo"))) {			
				configuring=elevatorSalesInfo.getConfiguring();
				hs.delete(elevatorSalesInfo);
				elevatorSalesInfo = new ElevatorSalesInfo();
			}
			
			elevatorSalesInfo = (ElevatorSalesInfo) dform.get("elevatorSaleBean");
			elevatorSalesInfo.setEnabledFlag((String) dform.get("enabledFlag"));
			elevatorSalesInfo.setOperId(userInfo.getUserID());
			elevatorSalesInfo.setOperDate(CommonUtil.getToday());
			elevatorSalesInfo.setConfiguring(configuring);
			elevatorSalesInfo.setSeriesId((String) dform.get("seriesId"));
			//elevatorSalesInfo.setR1(r1);
			elevatorSalesInfo.setR2(r2);
			elevatorSalesInfo.setR3(r3);
			elevatorSalesInfo.setR4(r4);
			hs.save(elevatorSalesInfo);
			
			String newFile= savePicter(form, request, response, "ElevatorArchivesInfo.file.upload.folder", (String) dform.get("id"));
			if(newFile!=null&&!newFile.trim().equals("")){

			    savePicterTodb(hs, request, newFile, (String) dform.get("id"));
			}
			
			
			tx.commit();
		
		} catch (HibernateException e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("customer.update.duplicatekeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate region Update error!");
		} catch (Exception e1) {
			e1.printStackTrace();
			log.error(e1.getMessage());
			DebugUtil.print(e1, "Hibernate region Update error!");
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		ActionForward forward = null;
		String isreturn = request.getParameter("isreturn");
		try {
			if (isreturn != null && isreturn.equals("Y") && errors.isEmpty()) {
				// return list page
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.success"));
				forward = mapping.findForward("returnList");
			} else {
				// return modify page
				if (errors.isEmpty()) {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("update.success"));
				} else {
					request.setAttribute("error", "Yes");
				}
				forward = mapping.findForward("returnModify");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}

	/**
	 * ɾ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toDeleteRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		Session hs = null;
		Transaction tx = null;
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			String id=(String) dform.get("id");
			id = new String(id.getBytes("ISO-8859-1"),"GBK");

			ElevatorSalesInfo elevatorSalesInfo = (ElevatorSalesInfo) hs.get(ElevatorSalesInfo.class,id);
			if (elevatorSalesInfo != null) {
				hs.delete(elevatorSalesInfo);
				 errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.succeed"));
			}
			tx.commit();
		} catch (Exception e2) {
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("delete.foreignkeyerror"));
			try {
				tx.rollback();
			} catch (HibernateException e3) {
				log.error(e3.getMessage());
				DebugUtil.print(e3, "Hibernate Transaction rollback error!");
			}
			log.error(e2.getMessage());
			DebugUtil.print(e2, "Hibernate region Update error!");
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}

		ActionForward forward = null;
		try {
			forward = mapping.findForward("returnList");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return forward;
	}
	
	/**
	 * Method toSearchRecord execute, to Excel Record �б��ѯ����Excel
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return ActionForward
	 * @throws IOException
	 */
	public HttpServletResponse toExcelRecord(ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String naigation = new String();
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		String elevatorNo = tableForm.getProperty("elevatorNo");
		String elevatorType = tableForm.getProperty("elevatorType");
		String salesContractNo = tableForm.getProperty("salesContractNo");
		String inspectDates = tableForm.getProperty("inspectDates");
		String inspectDatee = tableForm.getProperty("inspectDatee");
		String enabledFlag = tableForm.getProperty("enabledFlag");
		
		String eler1 = tableForm.getProperty("eler1");
		String r2 = tableForm.getProperty("r2");
		String r3 = tableForm.getProperty("r3");
		String r4 = tableForm.getProperty("r4");
		String daddress = tableForm.getProperty("daddress");
		
		Session hs = null;
		ResultSet rs=null;
		XSSFWorkbook wb = null;
		String SeriesId =null ;
		try {
			hs = HibernateUtil.getSession();

			//�Ƿ��ڱ��ֶ��Զ��ж�=��ͬ�����������ģ���ͬ״̬����ǩ����ǩ�����´��
			String sql="select a.salescontractno,a.inspectdate,a.elevatorno,a.weight,a.speed,a.seriesname,a.elevatortype, "
					+ "a.floor,a.stage,a.door,a.high,a.elevatorparam,a.salescontractname,a.salescontracttype, "
					+ "a.dealer,a.useunit,a.department,a.operationname,a.operationphone,a.deliveryaddress, "
					+ "a.maintenanceclause,a.warranty,a.seriesid,c.eler1,a.r2,a.r3,a.r4 "
					+ "from ElevatorSalesInfo a, "
					+ "	 (select e.elevatorNo,case when isnull(e2.ElevatorNo,'')='' then '��' else '��' end as eler1 "
					+ "	   from ElevatorSalesInfo e "
					+ "			left join ("
					+ "				select distinct b.ElevatorNo from MaintContractMaster m,MaintContractDetail b "
					+ "				where m.BillNo=b.BillNo and m.ContractStatus in('ZB','XB') "
					+ "				and ISNULL(m.TaskSubFlag,'N')='Y' and ISNULL(b.IsSurrender,'N')='N' "
					+ "			) e2 on e2.ElevatorNo=e.ElevatorNo "    
					+ "	 where isnull(e.isOutsideFootball,'N')='N') c "
					+ "where isnull(a.isOutsideFootball,'N')='N' and a.ElevatorNo=c.ElevatorNo ";
			if (elevatorNo != null && !elevatorNo.equals("")) {
				sql+=" and a.elevatorNo like '%"+elevatorNo.trim()+"%'";
			}
			if (elevatorType != null && !elevatorType.equals("")) {
				sql+=" and a.elevatorType like '"+elevatorType.trim()+"'";
			}
			if (salesContractNo != null && !salesContractNo.equals("")) {
				sql+=" and a.salesContractNo like '%"+salesContractNo.trim()+"%'";
			}
			if (enabledFlag != null && !enabledFlag.equals("")) {
				sql+=" and a.enabledFlag like '"+enabledFlag.trim()+"'";
			}
			if (inspectDates != null && !inspectDates.equals("")) {
				sql+=" and a.InspectDate >='"+inspectDates.trim()+"'";
			}
			if (inspectDatee != null && !inspectDatee.equals("")) {
				sql+=" and a.InspectDate <='"+inspectDatee.trim()+"'";
			}
			if (eler1 != null && !eler1.equals("")) {
				sql+=" and c.eler1 like '"+eler1.trim()+"'";
			}
			if (r2 != null && !r2.equals("")) {
				sql+=" and a.r2 like '%"+r2.trim()+"%'";
			}
			if (r3 != null && !r3.equals("")) {
				sql+=" and a.r3 like '%"+r3.trim()+"%'";
			}
			if (r4 != null && !r4.equals("")) {
				sql+=" and a.r4 like '%"+r4.trim()+"%'";
			}
			if (daddress != null && !daddress.equals("")) {
				sql+=" and a.DeliveryAddress like '%"+daddress.trim()+"%'";
			}
			sql += " order by elevatorNo asc";

			rs=hs.connection().prepareStatement(sql).executeQuery();

			String[] columNames = {"��ͬ���", "ǩ������", "���ݱ��", "����", "�ٶ�", "����ϵ��",
					"��������", "��", "վ", "��", "�����߶�", "����ͺ�", "��ͬ����", "��ͬ����",
					"�ͻ���λ", "ʹ�õ�λ", "���۲���", "ҵ��Ա", "ҵ��Ա��ϵ�绰", "������ַ" ,"ά������","�ʱ���","��������",
					"�Ƿ��ڱ�","����ʡ��","������","������/��"};
			String[] columids = {"salescontractno","inspectdate","elevatorno","weight","speed","seriesname",
					"elevatortype","floor","stage","door","high","elevatorparam","salescontractname","salescontracttype",
					"dealer","useunit","department","operationname","operationphone","deliveryaddress",
					"maintenanceclause","warranty","seriesid","eler1","r2","r3","r4"};
			
			wb = new XSSFWorkbook();
			XSSFSheet sheet = wb.createSheet("����������Ϣ");
			XSSFRow row0 = sheet.createRow( 0);
			XSSFCellStyle style = wb.createCellStyle();
			XSSFFont font = wb.createFont();
			font.setBold(true);
			style.setFont(font);
			List SeriesIdList = bd.getPullDownList("ElevatorSalesInfo_SeriesId");

			//��������
			for (int i = 0; i < columNames.length; i++) {
				XSSFCell cell0 = row0.createCell((short)i);
				cell0.setCellValue(columNames[i]);
				cell0.setCellStyle(style);
			}
			//��������
			int j=1;
			XSSFCell cell = null;
			while(rs.next()) {
				// ����Excel�У���1�п�ʼ
				XSSFRow row = sheet.createRow(j);
				
				for(int c=0;c<columids.length;c++){
					String valstr=rs.getString(columids[c]);
					// ����Excel��
					cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
					
				    if(columids[c].equals("elevatortype")){
				    	if("T".equals(valstr)){
				    		valstr = "ֱ��";
						} else if("F".equals(valstr)) {
							valstr = "����";
						}
				    }else if(columids[c].equals("seriesid")){
				    	valstr = bd.getOptionName(valstr, SeriesIdList);
				    }
				    cell.setCellValue(valstr);
				}
				j++;
			}
			
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
			}
		}
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("����������Ϣ", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
	}
	
	/**
	 * ���ص�Ԫ���ַ���ֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public String cellValueToString(XSSFRow row, int cellNum, StringBuffer reStr){
		String value = "";
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) { 
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ�ַ���;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value.trim();
	}
	
	/**
	 * ���ص�Ԫ���ַ���ֵ����ֵʱ����Ӵ�����Ϣ��
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public String cellValueToString2(XSSFRow row, int cellNum, StringBuffer reStr){
		String value = "";
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) { 
			
		} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
			value = cell.getStringCellValue();
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ�ַ���;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value.trim();
	}
		
	/**
	 * ��Ԫ�����ڸ�ʽ��ֵתΪ�ַ���ֵ������
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public String cellDateValueToString(XSSFRow row, int cellNum, StringBuffer reStr){
		String value = "";
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");   
			value = sdf.format(cell.getDateCellValue());  
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ����;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value.trim();		
	}
	
	/**
	 * ���ص�Ԫ������ֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public int cellValueToInt(XSSFRow row, int cellNum, StringBuffer reStr){
		int value = 0;
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value = (int) cell.getNumericCellValue();
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ��ֵ;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value;
	}
	
	/**
	 * ���ص�Ԫ��doubleֵ
	 * @param XSSFRow �ж���
	 * @param cellNum ��������
	 * @param reStr ������Ϣ  
	 * @return String
	 */
	public double cellValueToDouble(XSSFRow row, int cellNum, StringBuffer reStr){
		double value = 0;
		XSSFCell cell = row.getCell(cellNum);
		if (cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK) {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)Ϊ��;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		} else if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
			value =  cell.getNumericCellValue();
		} else {
			reStr.append("��Ԫ��(" + (row.getRowNum() + 1) + "�У�"+ getCellChar(cellNum) + "��)���ݸ�ʽ����Ϊ��ֵ;<br>&nbsp;&nbsp;&nbsp;&nbsp;");
		}
		return value;
	}
	
	/**
	 * ����Ԫ������ת��Ϊ��д��ĸ
	 * @param cellNum ���� 
	 * @return char
	 */
	public char getCellChar(int cellNum){
		return (char) (cellNum + 65);
	}

	/**
	 * �ϴ����渽��
	 * @param form
	 * @param request
	 * @param response
	 * @param folder
	 * @param billno
	 * @return
	 */
	public String savePicter(ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String folder,String id) {
		List returnList = new ArrayList();
        folder = PropertiesUtil.getProperty(folder).trim();
		
		
		FormFile formFile = null;
		String newFileName=null;
        
		if (form.getMultipartRequestHandler() != null) {
			Hashtable hash = form.getMultipartRequestHandler().getFileElements();

			if (hash != null&&hash.size()>0) {
				HandleFile hf = new HandleFileImpA();
				for(Iterator it = hash.keySet().iterator(); it.hasNext();){
					String key=(String)it.next();
					formFile=(FormFile)hash.get(key);				
					if(formFile!=null){
						try {
							if(!formFile.getFileName().trim().equals("")){
								
								String fileName = new String(formFile.getFileName());   
								String fileType = fileName.substring(fileName.lastIndexOf(".")+1,fileName.length());
								newFileName=id+"."+fileType;
								hf.createFile(formFile.getInputStream(), folder+"ElevatorSalesInfo"+"/", newFileName);
							}else{
								continue;
							}
							
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}

					}
				}
			}
		}
		return newFileName;
	}
		
	/**
	 * ���渽����Ϣ�����ݿ�
	 * @param hs
	 * @param request
	 * @param fileName
	 * @param billno
	 * @return
	 */
	public boolean savePicterTodb(Session hs,HttpServletRequest request,String fileName,String elevatorNo){
		boolean saveFlag = true;
		if(null != fileName && !fileName.equals("")){
			String sql="";
			try {   
					sql="update ElevatorSalesInfo set configuring='"+fileName+"' where elevatorNo='"+elevatorNo+"'";
					hs.createQuery(sql).executeUpdate();
				   
			} catch (Exception e) {
				e.printStackTrace();
				saveFlag = false;
			}
		}
		return saveFlag;
	}
	
	
	
	/**
	 * �ļ�ɾ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDeleteFileRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) { 
		Session hs = null;
		Transaction tx = null;
		String id = request.getParameter("filesid"); 
		List list=null;
		String tableName=request.getParameter("tablename");
		String elevatorNo=request.getParameter("elevatorNo");
		String folder = request.getParameter("folder");
		String Level="";
		//�������������
        PrintWriter out=null;
        //������֤��������ͬ��������Ϣ	 
		if(null == folder || "".equals(folder)){
			folder ="ElevatorArchivesInfo.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			if(id!=null && id.length()>0){
				
				String sql="update "+tableName+" set configuring = null where elevatorNo='"+elevatorNo+"'";
				//System.out.println(sql);
				int exe=hs.createQuery(sql).executeUpdate();
				boolean isQualified=false;
				if(exe==1){
					isQualified=true;
				}
				hs.flush();
				
				HandleFile hf = new HandleFileImpA();
				String localpath=folder+tableName+"/"+id;
				hf.delFile(localpath);
			}			
			tx.commit();
		    
		    response.setContentType("text/xml; charset=UTF-8");
			out = response.getWriter();
			out.println("<response>");
			out.println("<res>" + "Y" + "</res>");
			out.println("</response>");
			
		} catch (Exception e) {
			try {
				out.println("<response>");
				out.println("<res>" + "N" + "</res>");
				out.println("</response>");
				tx.rollback();
				
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(out!=null){
					out.close();					
				}
				if(hs!=null){
					hs.close();
				}
			} catch (HibernateException hex) {
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}	
		return null;
	}
	/**
	 * ���ظ���
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toDownloadFileRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

//		FileOperatingUtil uf = new FileOperatingUtil();
//		uf.toDownLoadFiles(mapping, form, request, response);
		String filename=request.getParameter("filesname");
		String tablename=request.getParameter("tablename");
		String folder=request.getParameter("folder");
		
		filename=URLDecoder.decode(filename, "utf-8");
		tablename=URLDecoder.decode(tablename,"utf-8");
		
		if(folder==null || "".equals(folder)){
			folder="ElevatorArchivesInfo.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(filename, "utf-8"));

		fis = new FileInputStream(folder+tablename+"/"+filename);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);

		int bytesRead = 0;
		byte[] buffer = new byte[5 * 1024];
		while ((bytesRead = bis.read(buffer)) != -1) {
			bos.write(buffer, 0, bytesRead);// ���ļ����͵��ͻ���
			bos.flush();
		}
		if (fos != null) {fos.close();}
		if (bos != null) {bos.close();}
		if (fis != null) {fis.close();}
		if (bis != null) {bis.close();}
		return null;
	}
	
}
