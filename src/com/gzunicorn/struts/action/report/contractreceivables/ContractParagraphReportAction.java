package com.gzunicorn.struts.action.report.contractreceivables;


import java.io.IOException;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import org.hibernate.HibernateException;
import org.hibernate.Session;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.contractpayment.contractinvoicemanage.ContractInvoiceManage;
import com.gzunicorn.hibernate.contractpayment.contractparagraphmanage.ContractParagraphManage;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * ��ͬ�տ����=>������ϸ����
 * @author Lijun
 *
 */
public class ContractParagraphReportAction extends DispatchAction {

	Log log = LogFactory.getLog(ContractParagraphReportAction.class);

	BaseDataImpl bd = new BaseDataImpl();
	DecimalFormat df = new DecimalFormat("##.##"); 
	DecimalFormat df2 = new DecimalFormat("##"); 

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "contractparagraphreport", null);
		/** **********�����û�Ȩ�޹���*********** */

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

		request.setAttribute("navigator.location", " ������ϸ���� >> ��ѯ");
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
				 storid.setStorageid("%");
				 storid.setStoragename("ȫ��");
				 mainStationList.add(0,storid);
				 
				 request.setAttribute("mainStationList", mainStationList);
			}
			
			String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
			String day1=DateUtil.getDate(day, "MM", -1);//��ǰ�����·ݼ�1 ��
			dform.set("sdate2", day1);
			dform.set("edate2", day);
			
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

		forward = mapping.findForward("contractParagraphReportSearch");		
		return forward;
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

		request.setAttribute("navigator.location", " ������ϸ���� >> ��ѯ���");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String maintdivision = request.getParameter("maintdivision");
		String maintstation = request.getParameter("maintstation");
		String contractno = request.getParameter("contractno");
		String sdate1 = request.getParameter("sdate1");
		String edate1 = request.getParameter("edate1");
		String sdate2 = request.getParameter("sdate2");
		String edate2 = request.getParameter("edate2");

		HashMap rmap= new HashMap();			
		rmap.put("maintdivision", maintdivision);
		rmap.put("maintstation", maintstation);
		rmap.put("contractno", contractno);
		rmap.put("sdate1", sdate1);
		rmap.put("edate1", edate1);
		rmap.put("sdate2", sdate2);
		rmap.put("edate2", edate2);
		request.setAttribute("rmap", rmap);
		
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			
			String hql="select a,b.preDate,b.preMoney,c.comname,s.storagename,ct.companyName "
					+"from ContractParagraphManage a,"
					+"ProContractArfeeMaster b,Storageid s,Company c,Customer ct "
					+"where a.maintStation=s.storageid "
					+"and a.maintDivision=c.comid and ct.companyId=a.companyId and "
					+"a.arfJnlNo=b.jnlNo and a.auditStatus='Y' ";//��������˵�
			if(maintdivision!=null && !maintdivision.trim().equals("")){
				hql+=" and a.maintDivision like '"+maintdivision.trim()+"'";
			}
			if(maintstation!=null && !maintstation.trim().equals("")){
				hql+=" and a.maintStation like '"+maintstation.trim()+"'";
			}
			if(contractno!=null && !contractno.trim().equals("")){
				hql+=" and a.contractNo like '%"+contractno.trim()+"%'";
			}

			if(sdate1!=null && !sdate1.trim().equals("")){
				hql+=" and a.paragraphDate >='"+sdate1.trim()+"'";
			}
			if(edate1!=null && !edate1.trim().equals("")){
				hql+=" and a.paragraphDate <='"+edate1.trim()+"'";
			}
			if(sdate2!=null && !sdate2.trim().equals("")){
				hql+=" and a.operDate >='"+sdate2.trim()+" 00:00:00'";
			}
			if(edate2!=null && !edate2.trim().equals("")){
				hql+=" and a.operDate <='"+edate2.trim()+" 99:99:99'";
			}
			hql+=" order by a.contractNo,a.paragraphDate";
			
			//System.out.println("������ϸ����>>>>"+hql);

            List reList=hs.createQuery(hql).list();
            double totalnum=0;
            double totalnum2=0;
            List reportList=new ArrayList();
            if(reList!=null && reList.size()>0){
            	ContractParagraphManage cpm=null;
            	for(int i=0;i<reList.size();i++){
            		Object[] obj=(Object[])reList.get(i);
            		cpm=(ContractParagraphManage)obj[0];
            		
            		HashMap map= new HashMap();

            		map.put("BillNo", cpm.getBillNo());//��ͬ��ˮ��
            		map.put("ContractType", cpm.getContractType());//��ͬ����
                	map.put("MaintDivision", cpm.getMaintDivision());//�����ֲ��Ŵ���
                	map.put("ComName", obj[3].toString());//�����ֲ���
                	map.put("MaintStation", cpm.getMaintStation());//����ά��վ����
                	map.put("StorageName", obj[4].toString());//����ά��վ
                	map.put("ContractNo", cpm.getContractNo());//��ͬ��
                	map.put("CompanyName", obj[5].toString());//�׷���λ����
                	map.put("PreDate", obj[1].toString());//Ӧ�տ�����
                	map.put("PreMoney", obj[2].toString());//Ӧ�տ���
                	map.put("ParagraphDate", cpm.getParagraphDate());//��������
                	map.put("ParagraphMoney", String.valueOf(cpm.getParagraphMoney()));//������
                	map.put("ParagraphDate2", cpm.getOperDate());//����¼������

                	double money=Double.parseDouble(obj[2].toString());
                	double money2=cpm.getParagraphMoney();//������
                	
                	totalnum+=money;
                	totalnum2+=money2;

                	reportList.add(map);
            	}
            }
            
            HashMap hmap=new HashMap();
            hmap.put("totalnum", "Ӧ�տ���ϼ�:"+df.format(totalnum)+"  ������ϼ�:"+df.format(totalnum2));
            
            if (dform.get("genReport") != null && dform.get("genReport").equals("Y")) {
            	//����excel
    			response = toExcelRecord(response,reportList,hmap);
    			forward = mapping.findForward("exportExcel");
    		}else{
                if(reportList.size()>0 && reportList.size()<3001){
                	request.setAttribute("cpmReportList", reportList);
                }else if(reportList.size()>3000){
                	request.setAttribute("showinfostr", "��ѯ���ݳ�����ǧ�м�¼���뵼��EXCEL�鿴��");
                }else{
                	request.setAttribute("showinfostr", "û�м�¼��ʾ��");
                }
                request.setAttribute("totalhmap", hmap);
                
    			forward = mapping.findForward("contractParagraphReportList");	
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

		String[] titlename={"�����ֲ�","����ά��վ","ά����ͬ��","�׷���λ����","Ӧ�տ�����","Ӧ�տ���","��������","����¼������","������"};
		String[] titleid={"ComName","StorageName","ContractNo","CompanyName","PreDate","PreMoney","ParagraphDate","ParagraphDate2","ParagraphMoney"};
		
		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("������ϸ����");
        
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
				    if(titleid[c].equals("PreMoney") || titleid[c].equals("ParagraphMoney")){
				    	cell.setCellValue(Double.parseDouble((String)map.get(titleid[c])));
				    }else{
				    	cell.setCellValue((String)map.get(titleid[c]));
				    }
				}
			}
		}
		//��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
		sheet.addMergedRegion(new CellRangeAddress(toolnum, toolnum, 0, 6));
		XSSFRow row1 = sheet.createRow(toolnum);
		XSSFCell cell1 = row1.createCell((short)0);
		cell1.setCellValue((String)hmap.get("totalnum"));
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("������ϸ����", "utf-8") + ".xlsx");
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
