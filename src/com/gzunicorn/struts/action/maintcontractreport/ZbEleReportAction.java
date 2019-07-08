package com.gzunicorn.struts.action.maintcontractreport;


import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * ���̺�ͬ����ģ��=>�ڱ����ݱ���
 * @author Lijun
 *
 */
public class ZbEleReportAction extends DispatchAction {

	Log log = LogFactory.getLog(ZbEleReportAction.class);

	BaseDataImpl bd = new BaseDataImpl();
	DecimalFormat df = new DecimalFormat("##.##"); 

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "zbelereport", null);
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

		request.setAttribute("navigator.location", " �ڱ����ݱ��� >> ��ѯ");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		
		Session hs=null;
		try {
			hs=HibernateUtil.getSession();
			
			//�ֲ��������б�
			List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
			request.setAttribute("maintDivisionList", maintDivisionList);
			//ά��վ������
			//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
			String sqlss="select * from view_mainstation where roleid='"+userInfo.getRoleID()+"'";
			List vmlist=hs.createSQLQuery(sqlss).list();
			if(vmlist!=null && vmlist.size()>0){
				String hql="select a from Storageid a where a.storageid= '"+userInfo.getStorageId()+"' " +
						"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";	
				List mainStationList=hs.createQuery(hql).list();
				request.setAttribute("mainStationList", mainStationList);
			}else{
				String hql="select a from Storageid a where a.comid like '"+userInfo.getComID()+"' " +
						"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
				List mainStationList=hs.createQuery(hql).list();
			  
				 Storageid storid=new Storageid();
				 storid.setStorageid("");
				 storid.setStoragename("ȫ��");
				 mainStationList.add(0,storid);
				 
				 request.setAttribute("mainStationList", mainStationList);
			}
			
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

		forward = mapping.findForward("zbEleReportSearch");		
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
		request.setAttribute("navigator.location",
				messages.getMessage(locale, navigation));
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
	public ActionForward toSearchResults(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		request.setAttribute("navigator.location", " �ڱ����ݱ��� >> ��ѯ���");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String maintdivision = request.getParameter("maintdivision");
		String maintstation = request.getParameter("maintstation");
		String contractterms = request.getParameter("contractterms");

		HashMap rmap= new HashMap();			
		rmap.put("maintdivision", maintdivision);
		rmap.put("maintstation", maintstation);
		rmap.put("contractterms", contractterms);
		request.setAttribute("rmap", rmap);
		
		Session hs = null;
		Query query = null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			hs = HibernateUtil.getSession();
			conn=hs.connection();
			//�ų�ά����ͬ,�ų��������˱���,���������´��,�ų�����״̬����ʷ��
			String hql="select a.billno,a.MaintDivision,c.ComName,a.MaintStation,b.StorageName,a.MaintContractNo,"
					+ "md.SalesContractNo,md.ElevatorNo,a.CompanyID,c1.CompanyName,md.MaintAddress,md.rowid,"
					+ "md.ElevatorType,md.Floor,md.Stage,md.Door,md.SignWay,a.ContractSDate,a.ContractEDate,"
					+ "ISNULL(e.Speed,'') as Speed,ISNULL(e.Weight,'') as weight,a.ContractTotal,"
					+ "a.AuditDate,md.MainSDate,md.MainEDate,a.MainMode,md.r1,md.r2,md.CertificateDate,"
					+ "isnull(md.DelayEDate,'') as DelayEDate,isnull(md.annualInspectionDate,'') as annualInspectionDate "
					+ "from MaintContractMaster a,"
					+ "MaintContractDetail md left join ElevatorSalesInfo e on md.ElevatorNo=e.ElevatorNo,"
					+ "Storageid b,Company c,Customer c1 "
					+ "where a.BillNo=md.BillNo and a.MaintDivision=c.ComID "
					+ "and a.MaintStation=b.StorageID and a.CompanyID=c1.CompanyID "
					+ "and a.ContractStatus in('ZB','XB') "//��ǩ����ǩ
					+ "and isnull(md.issurrender,'N')<>'Y' "//�����˱���
					+ "and a.auditStatus='Y' "//��˵�
					+ "and isnull(a.TaskSubFlag,'N')='Y' "//���´��
					+ "and isnull(md.ElevatorStatus,'')='' ";//�ų�����״̬����ʷ��
			
			if(maintdivision!=null && !maintdivision.equals("")){
            	hql+=" and a.MaintDivision like '"+maintdivision.trim()+"'";
            }
			if(maintstation!=null && !maintstation.equals("")){
            	hql+=" and a.MaintStation like '"+maintstation.trim()+"'";
            }
			if(contractterms!=null && !contractterms.equals("")){
				if(contractterms!=null && contractterms.equals("Y")){
	            	hql+=" and charindex('100',isnull(a.ContractTerms,''))>0";//������ί���ҷ����Ᵽ ��
				}else if(contractterms!=null && contractterms.equals("N")){
	            	hql+=" and charindex('100',isnull(a.ContractTerms,''))<=0";
				}
            }
            hql+=" order by a.MaintDivision,a.MaintStation,a.billno";
            
            //System.out.println("�ڱ����ݱ���>>>"+hql);
            
            ps=conn.prepareStatement(hql);
            rs=ps.executeQuery();

            List maintenanceReportList=new ArrayList();
            int totalnum=0;//�ڱ���̨��
            int totalnumt=0;//ֱ����̨��
            int totalnumf=0;//������̨��
            int freetotalnum=0;//�Ᵽ��̨��
            int paidtotalnum=0;//�г���̨��
            while(rs.next()){
            	HashMap map= new HashMap();
            	map.put("billno", rs.getString("billno"));
            	map.put("ComName", rs.getString("ComName"));
            	map.put("StorageName", rs.getString("StorageName"));
            	map.put("MaintContractNo", rs.getString("MaintContractNo"));
            	map.put("SalesContractNo", rs.getString("SalesContractNo"));
            	map.put("ElevatorNo", rs.getString("ElevatorNo"));
            	map.put("CompanyName", rs.getString("CompanyName"));
            	map.put("MaintAddress", rs.getString("MaintAddress"));
            	map.put("FloorStageDoor", rs.getString("Floor")+"/"+rs.getString("Stage")+"/"+rs.getString("Door"));
            	map.put("Speed", rs.getString("Speed"));
            	map.put("weight", rs.getString("weight"));
            	map.put("AuditDate", rs.getString("AuditDate"));
            	map.put("MainSDate", rs.getString("MainSDate"));
            	map.put("MainEDate", rs.getString("MainEDate"));
            	map.put("CertificateDate", rs.getString("CertificateDate"));//¼�����պϸ�֤����
            	map.put("r1", rs.getString("r1"));//����ǰ����
            	map.put("r2", rs.getString("r2"));//������Ϣ����
            	map.put("DelayEDate", rs.getString("DelayEDate"));//���һ�������ӱ����ӱ���������
            	map.put("annualInspectionDate", rs.getString("annualInspectionDate"));//�������
            	map.put("SignWay", bd.getName("Pulldown", "pullname", "id.typeflag = 'MaintContractDetail_SignWay' and a.id.pullid", rs.getString("SignWay")));//�������
            	
            	String etype=rs.getString("ElevatorType");
            	if(etype!=null && etype.trim().equals("T")){
            		totalnumt++;
                	map.put("ElevatorType", "ֱ��");
            	}else if(etype!=null && etype.trim().equals("F")){
            		totalnumf++;
                	map.put("ElevatorType", "����");
            	}
            	
            	String mainmode=rs.getString("MainMode");
            	//������ʽ:FREE-���; PAID-�շ�
            	if(mainmode!=null && mainmode.trim().equals("FREE")){
            		freetotalnum++;
            		map.put("mainmode", "���");
            	}else if(mainmode!=null && mainmode.trim().equals("PAID")){
            		map.put("mainmode", "�շ�");
            		paidtotalnum++;
            	}
            	
            	String csdate=rs.getString("ContractSDate");
            	String cedate=rs.getString("ContractEDate");
            	double contracttotal=rs.getDouble("ContractTotal");
            	
            	String couSql = "select COUNT(rowid) from MaintContractDetail where BillNo = '"+rs.getString("billno")+"'";
            	List couList = hs.createSQLQuery(couSql).list();
            	
            	if (couList!=null&&couList.size()>0) {
            		Integer cou = (Integer) couList.get(0);
            		
            		//���ͬ��� ���㹫ʽ����ͬ�ܶ�/(��ͬ�ڱ�����+1)*365
                	//���ݶ�Ӧ��ά��������=��ͬ��ά���ѽ��/��̨ͬ��
    				int dday=DateUtil.compareDay(csdate, cedate);//��������֮����������
    				String yearcon=df.format((contracttotal/(dday+1)*365)/cou);//����2λС��
    				map.put("yearelevatortotal", yearcon);
				}
            	
            	String neardateSql = "select mwpd.MaintDate from MaintenanceWorkPlanDetail mwpd," 
            		+"MaintenanceWorkPlanMaster mwpm,MaintContractDetail mcd " 
            		+"where mwpd.billno=mwpm.billno and mwpm.rowid=mcd.rowid " 
            		+"and mwpd.MaintDate >= GETDATE() and mcd.rowid = '"+rs.getString("rowid")+"' " 
            		+"order by mwpd.MaintDate";
            	
            	List neardateList = hs.createSQLQuery(neardateSql).list();
            	if (neardateList!=null&&neardateList.size()>0) {
					String neardate = (String) neardateList.get(0);
					map.put("neardate", neardate);
				}
            	
            	maintenanceReportList.add(map);
            	
            }

        	totalnum=totalnumt+totalnumf;
        	
            HashMap hmap=new HashMap();
            hmap.put("totalnum", df.format(totalnum));//�ڱ���̨��
            hmap.put("totalnumt", df.format(totalnumt));//ֱ����̨��
            hmap.put("totalnumf", df.format(totalnumf));//������̨��
            hmap.put("freetotalnum", df.format(freetotalnum));//�Ᵽ̨���ϼ�
            hmap.put("paidtotalnum", df.format(paidtotalnum));//�г�̨���ϼ�
            
            if (dform.get("genReport") != null && dform.get("genReport").equals("Y")) {
            	//����excel
    			response = toExcelRecord(response,maintenanceReportList,hmap);
    			forward = mapping.findForward("exportExcel");
    		}else{
                if(maintenanceReportList.size()>0 && maintenanceReportList.size()<3001){
                	request.setAttribute("maintenanceReportList", maintenanceReportList);
                }else if(maintenanceReportList.size()>3000){
                	request.setAttribute("showinfostr", "��ѯ���ݳ�����ǧ�м�¼���뵼��EXCEL�鿴��");
                }else{
                	request.setAttribute("showinfostr", "û�м�¼��ʾ��");
                }
                request.setAttribute("totalhmap", hmap);
                
    			forward = mapping.findForward("zbEleReportList");	
    		}
		}catch(Exception e) {
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
	
	public HttpServletResponse toExcelRecord(HttpServletResponse response,
			List ReportList,HashMap hmap) throws IOException {

		String[] titlename={"ά���ֲ�","ά��վ","ά����ͬ��","���ۺ�ͬ��","���ݱ��","�׷�����","ʹ�õ�λ����",
					"��������","��/վ","����","����","��ͬ���ͨ������","ά����ʼ����","ά����������",
					"¼�����պϸ�֤����","����ǰ����","������Ϣ����","������ʽ","�ӱ���������","�������","��������ƻ�����","ǩ��ʽ","ά��������"};
		 String[] titleid={"ComName","StorageName","MaintContractNo","SalesContractNo","ElevatorNo","CompanyName","MaintAddress",
					"ElevatorType","FloorStageDoor","Speed","weight","AuditDate","MainSDate","MainEDate",
					"CertificateDate","r1","r2","mainmode","DelayEDate","annualInspectionDate","neardate","SignWay","yearelevatortotal"};
		
		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("�ڱ����ݱ���");
        
        //������Ԫ����ʽ
        XSSFCellStyle cs = wb.createCellStyle();
        cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);//���Ҿ���
        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
        XSSFFont f  = wb.createFont();
        f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// ����Ӵ�
        cs.setFont(f);
        
        int toolnum=2;
        //��������
        XSSFRow row0 = sheet.createRow( 0);
        XSSFCell cell0 = null;
		for(int i=0;i<titlename.length;i++){
			cell0 = row0.createCell((short)i);
			//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell0.setCellValue(titlename[i]);
			cell0.setCellStyle(cs);
		}
		//��������
		if (ReportList != null && ReportList.size()>0) {
			XSSFRow row = null;
			XSSFCell cell =null;
			toolnum=toolnum+ReportList.size();
			for (int j = 0; j < ReportList.size(); j++) {
				HashMap map=(HashMap) ReportList.get(j);
				// ����Excel�У���0�п�ʼ
				row = sheet.createRow(j+1);
				for(int c=0;c<titleid.length;c++){
				    cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16); 
				    cell.setCellValue((String)map.get(titleid[c]));
				}
			}
		}
		
		String totalstr="�ڱ���̨��:"+(String)hmap.get("totalnum")+"  ֱ����̨��:"+(String)hmap.get("totalnumt")
				+"  ������̨��:"+(String)hmap.get("totalnumf")+"  �г���̨��:"+(String)hmap.get("paidtotalnum")
				+"  �Ᵽ��̨��:"+(String)hmap.get("freetotalnum");
		//��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
		sheet.addMergedRegion(new CellRangeAddress(toolnum, toolnum, 0, 19));
		XSSFRow row1 = sheet.createRow(toolnum);
		XSSFCell cell1 = row1.createCell((short)0);
		cell1.setCellValue(totalstr);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("�ڱ����ݱ���", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
	}
	/**
	 * ajax ���� �ֲ���ά��վ
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public void toStorageIDList(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws IOException{
		Session hs=null;
		String comid=request.getParameter("comid");
		response.setHeader("Content-Type","text/html; charset=GBK");
		
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version='1.0' encoding='GBK'?>");
		sb.append("<root>");
		try {
			hs=HibernateUtil.getSession();
			if(comid!=null && !"".equals(comid)){
				String hql="select a from Storageid a where a.comid='"+comid+"' " +
						"and a.storagetype=1 and a.parentstorageid='0' and a.enabledflag='Y'";
				List list=hs.createQuery(hql).list();
				if(list!=null && list.size()>0){
					sb.append("<rows>");
					for(int i=0;i<list.size();i++){
					Storageid sid=(Storageid)list.get(i);
					sb.append("<cols name='"+sid.getStoragename()+"' value='"+sid.getStorageid()+"'>").append("</cols>");
					}
					sb.append("</rows>");
								
					
				  }
			 }
			} catch (Exception e) {
				e.printStackTrace();
			}
		
		finally{
			hs.close();
		}
		sb.append("</root>");
		
		response.setCharacterEncoding("gbk"); 
		response.setContentType("text/xml;charset=gbk");
		response.getWriter().write(sb.toString());
	}
}
