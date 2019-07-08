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
 * ���̺�ͬ����ģ��=>�ڱ���ͬ����
 * @author Lijun
 *
 */
public class ZbReportAction extends DispatchAction {

	Log log = LogFactory.getLog(ZbReportAction.class);

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "zbreport", null);
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

		request.setAttribute("navigator.location", " �ڱ���ͬ���� >> ��ѯ");
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

		forward = mapping.findForward("zbReportSearch");		
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

		request.setAttribute("navigator.location", " �ڱ���ͬ���� >> ��ѯ���");
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
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			hs = HibernateUtil.getSession();
			conn=hs.connection();

			//��ȡ��ͬ�������������
			List ccalist=bd.getPullDownAllList("MaintContractQuoteMaster_ContractContentApply");
			
			//�ų�ά����ͬ,�ų��������˱���,���������´�ģ��ų�����״̬����ʷ��  
			String hql="select a.ContractTotal,a.MainMode,a.billno,a.MaintDivision,c.ComName,a.MaintStation,b.StorageName," +
					"a.MaintContractNo,a.CompanyID,c1.CompanyName," +
					"(select count(md.billNo) from MaintContractDetail md where md.billNo=a.billno and md.elevatorType='T' and isnull(md.issurrender,'N')<>'Y' and isnull(md.ElevatorStatus,'')='') as tconnum," +
					"(select count(md.billNo) from MaintContractDetail md where md.billNo=a.billno and md.elevatorType='F' and isnull(md.issurrender,'N')<>'Y' and isnull(md.ElevatorStatus,'')='') as fconnum," +
					"(select sum(md.floor) from MaintContractDetail md where md.billNo=a.billno and isnull(md.issurrender,'N')<>'Y' and isnull(md.ElevatorStatus,'')='') as floornum," +
					
					"(select SalesContractNo = stuff((select ',' + SalesContractNo from MaintContractDetail tb "+
					"where billno = tb.billno and billno=a.billno group by SalesContractNo for xml path('')),1,1,'') "+
					"from MaintContractDetail where billno=a.billno group by billno) as SalesContractNo,"+
					
					"(select MaintAddress = stuff((select ',' + MaintAddress from MaintContractDetail tb "+
					"where billno = tb.billno and billno=a.billno group by MaintAddress for xml path('')),1,1,'') "+
					"from MaintContractDetail where billno=a.billno group by billno) as MaintAddress,"+
					
					"a.ContractSDate,a.ContractEDate,a.ContractTerms,a.rem,a.auditDate " +
					"from MaintContractMaster a,Storageid b,Company c,Customer c1 " +
					"where a.MaintDivision=c.ComID and a.MaintStation=b.StorageID and a.CompanyID=c1.CompanyID " +
					"and a.ContractStatus in('ZB','XB') and a.auditStatus='Y' "+
					"and isnull(a.TaskSubFlag,'N')='Y' ";
					//"and a.billno not in(select maint_billNo from EntrustContractMaster where R1 not in('END')) ";
			
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
            
            //System.out.println("�ڱ���ͬ����>>>"+hql);
            
            ps=conn.prepareStatement(hql);
            rs=ps.executeQuery();

            List maintenanceReportList=new ArrayList();
            int totalnum=0;
            int totalnumt=0;
            int totalnumf=0;
            int totalfloornum=0;
            int freetotalnum=0;
            int paidtotalnum=0;
            while(rs.next()){
            	String csdate=rs.getString("ContractSDate");
            	String cedate=rs.getString("ContractEDate");
            	double contracttotal=rs.getDouble("ContractTotal");
            	
            	HashMap map= new HashMap();
            	map.put("billno", rs.getString("billno"));
            	map.put("ComName", rs.getString("ComName"));
            	map.put("StorageName", rs.getString("StorageName"));
            	map.put("MaintContractNo", rs.getString("MaintContractNo"));
            	map.put("CompanyName", rs.getString("CompanyName"));
            	map.put("tconnum", rs.getString("tconnum"));
            	map.put("fconnum", rs.getString("fconnum"));
            	map.put("ContractSDate", csdate);
            	map.put("ContractEDate", cedate);
            	map.put("SalesContractNo", rs.getString("SalesContractNo"));
            	map.put("MaintAddress", rs.getString("MaintAddress"));
            	map.put("auditDate", rs.getString("auditDate"));
            	
            	int tconnum=rs.getInt("tconnum");//ֱ������
            	totalnumt=totalnumt+tconnum;
            	int fconnum=rs.getInt("fconnum");//��������
            	totalnumf=totalnumf+fconnum;
            	map.put("tfconnum", String.valueOf(tconnum+fconnum));//�ϼ�̨��
            	
            	int floornum=rs.getInt("floornum");//�ڱ��ܲ�վ��
            	totalfloornum=totalfloornum+floornum;
            	
            	String mainmode=rs.getString("MainMode");
            	//������ʽ:FREE-���; PAID-�շ�
            	if(mainmode!=null && mainmode.trim().equals("FREE")){
            		freetotalnum+=tconnum+fconnum;
            		map.put("mainmode", "���");
            	}else if(mainmode!=null && mainmode.trim().equals("PAID")){
            		map.put("mainmode", "�շ�");
            		paidtotalnum+=tconnum+fconnum;
            	}
            	map.put("contracttotal", String.valueOf(contracttotal));

            	map.put("rem", rs.getString("rem"));
            	//��ͬ�������������
				String ccastrname="";
				String ccastr=rs.getString("ContractTerms");
				if(ccastr!=null && !ccastr.trim().equals("")){
					String[] ccarr=ccastr.split(",");
					for(int i=0;i<ccarr.length;i++){
						if(i==ccarr.length-1){
							ccastrname+=bd.getOptionName(ccarr[i].trim(), ccalist);
						}else{
							ccastrname+=bd.getOptionName(ccarr[i].trim(), ccalist)+"��";
						}
					}
				}
				map.put("ContractTerms", ccastrname);
				
				//���ͬ��� ���㹫ʽ����ͬ�ܶ�/(��ͬ�ڱ�����+1)*365
				int dday=DateUtil.compareDay(csdate, cedate);//��������֮����������
				String yearcon=df.format(contracttotal/(dday+1)*365);//����2λС��
				map.put("yearcontracttotal", yearcon);

            	maintenanceReportList.add(map);
            	
            }

        	totalnum=totalnumt+totalnumf;
        	
            HashMap hmap=new HashMap();
            hmap.put("totalnum", df.format(totalnum));//�ڱ���̨��
            hmap.put("totalnumt", df.format(totalnumt));//ֱ����̨��
            hmap.put("totalnumf", df.format(totalnumf));//������̨��
            hmap.put("totalfloornum", df.format(totalfloornum));//�ڱ��ܲ�վ��
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
                
    			forward = mapping.findForward("zbReportList");	
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

		String[] titlename={"ά���ֲ�","ά��վ","ά����ͬ��","���ۺ�ͬ��","�׷�����","ʹ�õ�λ����",
				"ֱ��̨��","����̨��","�ϼ�̨��","��ͬ��ʼ����","��ͬ��������","������ʽ","��ͬ�ܶ�","��ͬ�������������","��ע","���ͬ���","��ͬ���ͨ������"};
		String[] titleid={"ComName","StorageName","MaintContractNo","SalesContractNo","CompanyName","MaintAddress",
				"tconnum","fconnum","tfconnum","ContractSDate","ContractEDate","mainmode","contracttotal","ContractTerms","rem","yearcontracttotal","auditDate"};
		
		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("�ڱ���ͬ����");
        
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
				    if(titleid[c].equals("tconnum") || titleid[c].equals("fconnum") 
				    		|| titleid[c].equals("tfconnum") || titleid[c].equals("contracttotal")
				    		|| titleid[c].equals("yearcontracttotal")){
				    	cell.setCellValue(Double.parseDouble((String)map.get(titleid[c])));
				    }else{
				    	cell.setCellValue((String)map.get(titleid[c]));
				    }
				}
			}
		}
		
		String totalstr="�ڱ���̨��:"+(String)hmap.get("totalnum")+"  ֱ����̨��:"+(String)hmap.get("totalnumt")
				+"  ������̨��:"+(String)hmap.get("totalnumf")+"  �ڱ��ܲ�վ��:"+(String)hmap.get("totalfloornum")
				+"  �г���̨��:"+(String)hmap.get("paidtotalnum")+"  �Ᵽ��̨��:"+(String)hmap.get("freetotalnum");
		//��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
		sheet.addMergedRegion(new CellRangeAddress(toolnum, toolnum, 0, 15));
		XSSFRow row1 = sheet.createRow(toolnum);
		XSSFCell cell1 = row1.createCell((short)0);
		cell1.setCellValue(totalstr);
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("�ڱ���ͬ����", "utf-8") + ".xlsx");
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
