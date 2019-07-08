package com.gzunicorn.struts.action.infomanager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.Order;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.infomanager.elevatorarchivesinfo.ElevatorArchivesInfo;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.DefaultHTMLTable;
import com.zubarev.htmltable.HTMLTableCache;
import com.zubarev.htmltable.action.ServeTableForm;

public class ElevatorArchivesInfoAction extends DispatchAction {

	Log log = LogFactory.getLog(ElevatorArchivesInfoAction.class);

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

		/** **********��ʼ�û�Ȩ�޹���*********** */
		SysRightsUtil.filterModuleRight(request, response,
				SysRightsUtil.NODE_ID_FORWARD + "elevatorarchivesinfo", null);
		/** **********�����û�Ȩ�޹���*********** */

		// Set default method is toSearchRecord
		String name = request.getParameter("method");
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

		request.setAttribute("navigator.location","���ݵ�����Ϣ >> ��ѯ�б�");		
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();
		
		request.setAttribute("showroleid", userInfo.getRoleID());

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
			HTMLTableCache cache = new HTMLTableCache(session, "elevatorArchivesInfoList");

			DefaultHTMLTable table = new DefaultHTMLTable();
			table.setMapping("fElevatorArchivesInfo");
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

			String elevatorNo=tableForm.getProperty("elevatorNo");//���ݱ��
			String elevatorType=tableForm.getProperty("elevatorType");//��������
			String maintDivision = tableForm.getProperty("maintDivision");// ά���ֲ�
			String salesContractNo = tableForm.getProperty("salesContractNo");// ���ۺ�ͬ��
			String maintContractNo = tableForm.getProperty("maintContractNo");//ά����ͬ��
			String projectName = tableForm.getProperty("projectName");// ��Ŀ��ַ
			String projectAddress = tableForm.getProperty("projectAddress");// ���ñ�־
			if (maintDivision == null || maintDivision == "") {
				maintDivision = userInfo.getComID();
				if (maintDivision.equals("00")) {
					maintDivision = "%";
				}
			}
			Session hs = null;

			try {

				hs = HibernateUtil.getSession();
				
				Criteria criteria = hs.createCriteria(ElevatorArchivesInfo.class);
				if(elevatorNo!=null && !elevatorNo.equals("")){
					criteria.add(Expression.like("elevatorNo", "%"+elevatorNo.trim()+"%"));
				}
				if(elevatorType!=null && !elevatorType.equals("")){
					criteria.add(Expression.eq("elevatorType", elevatorType));
				}
				if (salesContractNo != null && !salesContractNo.equals("")) {
					criteria.add(Expression.like("salesContractNo", "%" + salesContractNo.trim() + "%"));
				}
				if (maintContractNo != null && !maintContractNo.equals("")) {
					criteria.add(Expression.like("maintContractNo", "%" + maintContractNo.trim() + "%"));
				}
				if (projectName != null && !projectName.equals("")) {
					criteria.add(Expression.like("projectName", "%" + projectName.trim() + "%"));
				}
				if (projectAddress != null && !projectAddress.equals("")) {
					criteria.add(Expression.like("projectAddress", "%" + projectAddress.trim() + "%"));
				}
				if (maintDivision != null && !maintDivision.equals("")) {
					criteria.add(Expression.like("maintDivision",  maintDivision));
				}
				if (table.getIsAscending()) {
					criteria.addOrder(Order.asc(table.getSortColumn()));
				} else {
					criteria.addOrder(Order.desc(table.getSortColumn()));
				}
				table.setVolume(criteria.list().size());// ��ѯ�ó����ݼ�¼��;

				// �ó���һҳ�����һ����¼����;
				criteria.setFirstResult(table.getFrom()); // pagefirst
				criteria.setMaxResults(table.getLength());

				cache.check(table);

				List elevatorArchivesInfoList = criteria.list();
				if(elevatorArchivesInfoList!=null || elevatorArchivesInfoList.size()>0){
					int len=elevatorArchivesInfoList.size();
					for(int i=0;i<len;i++){
						ElevatorArchivesInfo eai=(ElevatorArchivesInfo) elevatorArchivesInfoList.get(i);
						String eaiType=bd.getName_Sql("Pulldown", "pullname", "pullid", eai.getElevatorType());
						String eaiDivision=bd.getName_Sql("Company", "comName", "comId", eai.getMaintDivision());
						String eaiStation=bd.getName_Sql("Storageid", "storagename", "storageid", eai.getMaintStation());
						eai.setElevatorType(eaiType);
						eai.setMaintDivision(eaiDivision);
						eai.setMaintStation(eaiStation);
						
					}
				}

				table.addAll(elevatorArchivesInfoList);
				session.setAttribute("elevatorArchivesInfoList", table);
				session.setAttribute("maintDivisionList", Grcnamelist1.getgrcnamelist(userInfo.getUserID()));
				session.setAttribute("elevatorTypeList", bd.getPullDownList("ElevatorSalesInfo_type"));

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
			forward = mapping.findForward("elevatorArchivesInfoList");
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

		request.setAttribute("navigator.location","���ݵ�����Ϣ >> �鿴");
		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		ActionForward forward = null;

		String id = (String) dform.get("id");
		Session hs = null;
		List calloutList=new ArrayList();
		List mwpList=new ArrayList();

		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				Query query = hs.createQuery("from 	ElevatorArchivesInfo p where p.numno = :numno");
				query.setString("numno", id);
				List list = query.list();
				if (list != null && list.size() > 0) {
					ElevatorArchivesInfo elevatorArchivesInfo = (ElevatorArchivesInfo) list.get(0);
					elevatorArchivesInfo.setElevatorType(bd.getName_Sql("Pulldown", "pullname", "pullid", elevatorArchivesInfo.getElevatorType()));
					elevatorArchivesInfo.setMaintDivision(bd.getName_Sql("Company", "comName", "comId", elevatorArchivesInfo.getMaintDivision()));
					elevatorArchivesInfo.setMaintStation(bd.getName_Sql("Storageid", "storagename", "storageid", elevatorArchivesInfo.getMaintStation()));
					request.setAttribute("elevatorArchivesInfoBean", elevatorArchivesInfo);	
					
					//����,�������Ѿ��رյļ��޼�¼ 
					//2017-11-23�޸�Ϊ [6 ����ˣ�7 �ѹر�]
					//���ϼ�¼������״̬�������--�ط�����״̬�£��Ϳ���ͬ�������ϼ�¼���ˡ�����ͼ��ʾ������ͼ״̬�¾Ϳ���ͬ�������ϼ�¼���ˡ�
					String sql="select a.operDate,b.completeTime,a.repairDesc,b.processDesc,"
							+ "a.hfcId,b.assignUser,b.serviceRem from CalloutMaster a,CalloutProcess b "
							+ "where a.CalloutMasterNo=b.CalloutMasterNo "
							+ "and a.ElevatorNo='"+elevatorArchivesInfo.getElevatorNo()+"' "
							+ "and isnull(a.HandleStatus,'0') in('6','7') "
							+ "order by a.operDate desc";
							//+ "and isnull(a.IsColse,'')<>'' ";
					List list1=hs.createSQLQuery(sql).list();
					for(Object object : list1){
						Object[] value=(Object[])object;
						Map map=new HashMap();
						map.put("operDate", value[0]);
						map.put("completeTime", value[1]);
						map.put("repairDesc", value[2]);
						map.put("processDesc", value[3]);
						map.put("hfcId", bd.getName("HotlineFaultClassification", "hfcName", "hfcId", String.valueOf(value[4])));
						map.put("assignUser", bd.getName("Loginuser", "username", "userid", String.valueOf(value[5])));
						map.put("serviceRem", value[6]);
						calloutList.add(map);
					}
					
					//�������������Ѿ���˹��ı�����¼
					//2017-11-23�޸�Ϊ [3 ���깤]
					//������¼����δ��˵�����£����б�������ʱ�䡢�����÷ֵ�����£��Ϳ���ͬ����������¼�����ˡ�
					String sql2="select b.maintDate,b.maintType,b.maintPersonnel,b.r4,ISNULL(l.UserName,'') as username "
							+ "from MaintenanceWorkPlanMaster a,MaintenanceWorkPlanDetail b "
							+ "left join Loginuser l on l.UserID=b.MaintPersonnel "
							+ "where a.billno=b.billno "
							+ "and a.ElevatorNo='"+elevatorArchivesInfo.getElevatorNo()+"' "
							+ "and isnull(b.HandleStatus,'0')='3' "
							+ "order by b.maintDate desc";
							//+ "and isnull(b.byAuditOperid,'')<>''";
					list1=hs.createSQLQuery(sql2).list();
					for(Object object : list1){
						Object[] value=(Object[])object;
						Map map=new HashMap();
						map.put("maintDate", value[0]);
						map.put("maintType", value[1]);
						map.put("maintPersonnel",value[4]);
						map.put("rem", value[3]);
						mwpList.add(map);
					}
					request.setAttribute("calloutList", calloutList);
					request.setAttribute("mwpList", mwpList);

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
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}

			request.setAttribute("display", "yes");
			forward = mapping.findForward("elevatorArchivesInfoDisplay");
		}


		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}

	     /***
	      * Method toSearchRecord execute, to Excel Record �б��ѯ����Excel
	      * 
	      * @param mapping
	      * @param form
	      * @param request
	      * @param response
	      * @return ActionForward
	      * @throws IOException
	      **/
	public HttpServletResponse toExcelRecord(ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		String naigation = new String();
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ServeTableForm tableForm = (ServeTableForm) form;
		String action = tableForm.getAction();

		String elevatorNo = tableForm.getProperty("elevatorNo");// ����
		String elevatorType = tableForm.getProperty("elevatorType");// ��ͬ��
		String salesContractNo = tableForm.getProperty("salesContractNo");// ��ʼ�볧����
		String maintContractNo = tableForm.getProperty("maintContractNo");// �����볧����
		String projectName = tableForm.getProperty("projectName");// ���ñ�־
		String maintDivision = tableForm.getProperty("maintDivision");// ���ñ�־
		String maintStation = tableForm.getProperty("maintStation");// ���ñ�־
		Session hs = null;
		XSSFWorkbook wb = new XSSFWorkbook();

		try {
			hs = HibernateUtil.getSession();

			Criteria criteria = hs.createCriteria(ElevatorArchivesInfo.class);
			if (elevatorNo != null && !elevatorNo.equals("")) {
				criteria.add(Expression.like("elevatorNo", "%" + elevatorNo.trim() + "%"));
			}
			if (salesContractNo != null && !salesContractNo.equals("")) {
				criteria.add(Expression.like("salesContractNo", "%" + salesContractNo.trim() + "%"));
			}
			if (maintContractNo != null && !maintContractNo.equals("")) {
				criteria.add(Expression.ge("startDate", maintContractNo.trim()));
			}
			if (projectName != null && !projectName.equals("")) {
				criteria.add(Expression.le("startDate", projectName.trim()));
			}
			if (maintDivision != null && !maintDivision.equals("")) {
				criteria.add(Expression.eq("maintDivision", maintDivision));
			}
			if (elevatorType != null && !elevatorType.equals("")) {
				criteria.add(Expression.eq("elevatorType", elevatorType));
			}

			if (maintStation != null && !maintStation.equals("")) {
				criteria.add(Expression.le("maintStation", maintStation.trim()));
			}


			criteria.addOrder(Order.asc("numno"));

			List roleList = criteria.list();

			XSSFSheet sheet = wb.createSheet("���ݵ�����Ϣ");

			Locale locale = this.getLocale(request);
			MessageResources messages = getResources(request);

			String[] columnNames = { "numno", "salesContractNo", "maintContractNo", "projectName",
					"projectAddress", "maintDivision", "elevatorNo",
					"elevatorType", "elevatorParam", "floor", "stage", "door",
					"high", "detailConfig", "deliveryDate", "certificatDate", "customerDate",
					"confirmDate","rem","operId", "operDate" };

			if (roleList != null && !roleList.isEmpty()) {
				int l = roleList.size();
				XSSFRow row0 = sheet.createRow( 0);

				for (int i = 0; i < columnNames.length; i++) {
					XSSFCell cell0 = row0.createCell((short)i);
					cell0.setCellValue(messages.getMessage(locale,"personnelManage." + columnNames[i]));
				}

				Class<?> superClazz = ElevatorArchivesInfo.class.getSuperclass();
				for (int i = 0; i < l; i++) {
					ElevatorArchivesInfo master = (ElevatorArchivesInfo) roleList.get(i);
					// ����Excel�У���0�п�ʼ
					XSSFRow row = sheet.createRow( i+1);					
					for (int j = 0; j < columnNames.length; j++) {
						// ����Excel��
						XSSFCell cell = row.createCell((short)j);
						cell.setCellValue(getValue(master, superClazz, columnNames[j]));						
					}
				}
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

		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("���ݵ�����Ϣ", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());

		return response;
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
	public ActionForward toDownloadFileRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws IOException, ServletException {

//		FileOperatingUtil uf = new FileOperatingUtil();
//		uf.toDownLoadFiles(mapping, form, request, response);
		String filename=request.getParameter("filesname");
		
		String folder=request.getParameter("folder");
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

		fis = new FileInputStream(folder+"/"+filename);
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
	
/**
 * ��ö���ָ����get����������ִ�и�get�������ֵ
 * @param object javabean����
 * @param superClazz object���࣬����û����Ӧget����ʱ�봫��object�ĸ���
 * @param fieldName ������
 * @return ActionForward
 */
private String getValue(Object object, Class<?> superClazz, String fieldName){
	String value = null;	
	String methodName = "get" + fieldName.replaceFirst(fieldName.substring(0, 1),fieldName.substring(0, 1).toUpperCase());
	try {
		Method method = superClazz.getMethod(methodName);
		value = method.invoke(object, null) + "";
	} catch (Exception e) {
		e.printStackTrace();
	} 
	return value;

}
/**
 * ͬ������
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws IOException
 * @throws ServletException
 */
public ActionForward toSynchRecord(ActionMapping mapping, ActionForm form,
		HttpServletRequest request, HttpServletResponse response)
		throws IOException, ServletException, HibernateException {
	
	ActionErrors errors = new ActionErrors();
	HttpSession session = request.getSession();

	ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
	Session hs = null;
	Transaction tx = null;
		
	try {
		
		hs = HibernateUtil.getSession();		
		tx = hs.beginTransaction();

		//ͬ�������ݵ�����Ϣͬ���ڱ���ά����ͬ���� 
		String sql="exec sp_SYNCH_ELEVATORARCHIVESINFO '"+userInfo.getUserID()+"' ";
		hs.connection().prepareCall(sql).execute();
		
		tx.commit();
		
		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","ͬ���ɹ���"));

	} catch (Exception e) {
		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","ͬ��ʧ�ܣ�"));
		
		if(tx!=null){
			tx.rollback();
		}
		e.printStackTrace();
	} finally {
		if(hs != null){
			hs.close();				
		}				
	}
		
	if (!errors.isEmpty()){
		this.saveErrors(request, errors);
	}

	return mapping.findForward("returnList");

}

}
