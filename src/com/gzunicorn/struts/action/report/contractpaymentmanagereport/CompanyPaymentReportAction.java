package com.gzunicorn.struts.action.report.contractpaymentmanagereport;


import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
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
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * ��ͬ�����=>ί�е�λ����������۱�
 * @author Lwj
 *
 */
public class CompanyPaymentReportAction extends DispatchAction {

	Log log = LogFactory.getLog(CompanyPaymentReportAction.class);

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "companyPaymentReport", null);
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

		request.setAttribute("navigator.location", " ί�е�λ����������۱� >> ��ѯ");
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
			
			String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
			String day1=DateUtil.getDate(day, "yyyy", -1);//��ǰ������ݼ�1 ��
			dform.set("paragraphdate1", day1);
			dform.set("paragraphdate2", day);
			
			//request.setAttribute("causeAnalysisList", bd.getPullDownList("LostElevatorReport_CauseAnalysis"));
			
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

		forward = mapping.findForward("companyPaymentReportSearch");		
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

		request.setAttribute("navigator.location", " ί�е�λ����������۱� >> ��ѯ���");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String maintdivision = request.getParameter("maintdivision");
		String contractno = request.getParameter("contractno");
		if(contractno==null || contractno.trim().equals("")){
			contractno="";
		}
		String companyname = request.getParameter("companyname");
		if(companyname==null || companyname.trim().equals("")){
			companyname="";
		}
		String paragraphdate1 = request.getParameter("paragraphdate1");
		if(paragraphdate1==null || paragraphdate1.trim().equals("")){
			paragraphdate1="0000-00-00";
		}
		String paragraphdate2 = request.getParameter("paragraphdate2");
		if(paragraphdate2==null || paragraphdate2.trim().equals("")){
			paragraphdate2="9999-99-99";
		}

		HashMap rmap= new HashMap();			
		rmap.put("maintdivision", maintdivision);
		rmap.put("contractno", contractno);
		rmap.put("companyname", companyname);
		rmap.put("paragraphdate1", paragraphdate1);
		rmap.put("paragraphdate2", paragraphdate2);
		request.setAttribute("rmap", rmap);
		
		Session hs = null;
		ResultSet rs=null;
		try {
			hs = HibernateUtil.getSession();
			
			String hql="exec sp_companyPaymentReport '"+maintdivision+"','"+contractno+"','" 
				+companyname+"','"+paragraphdate1+"','"+paragraphdate2+"'";
			//System.out.println("ƽ�˺�ͬ����>>>>"+hql);
			rs=hs.connection().prepareCall(hql).executeQuery();

            List reportList=new ArrayList();
            int totalnum=0;
            while(rs.next()){
            	HashMap map= new HashMap();

            	map.put("MaintDivision", rs.getString("MaintDivision"));//��������
            	map.put("EntrustContractNo", rs.getString("EntrustContractNo"));//ί�к�ͬ��
            	map.put("CompanyName", rs.getString("CompanyName"));//ί�е�λ����
            	map.put("elenum", rs.getString("elenum"));//ί��̨��
            	map.put("ParagraphNo", rs.getString("ParagraphNo"));//ƾ֤��
            	map.put("ParagraphMoney", rs.getString("ParagraphMoney"));//������
            	map.put("DebitMoney", rs.getString("DebitMoney"));//�ۿ���
            	map.put("ParagraphDate", rs.getString("ParagraphDate"));//��������
            	map.put("BydAuditEvaluate", rs.getString("BydAuditEvaluate"));//���������������
            	map.put("HfAuditNum", rs.getString("HfAuditNum"));//������������ĸ����������
            	map.put("HfAuditNum2", rs.getString("HfAuditNum2"));//��Ǽ�����������ĸ����������
            	map.put("RxAuditNum", rs.getString("RxAuditNum"));//�������������Ͷ�ߴ���
            	map.put("RxAuditNum2", rs.getString("RxAuditNum2"));//��Ǽ������������Ͷ�ߴ���
            	map.put("JjthAuditEvaluate", rs.getString("JjthAuditEvaluate"));//�ɼ��˻��������
            	map.put("FbzAuditEvaluate", rs.getString("FbzAuditEvaluate"));//�ֲ����������
            	map.put("ZbzAuditRem", rs.getString("ZbzAuditRem"));//�ܲ���������
            	map.put("CustLevel", rs.getString("CustLevel"));//ά�����ʼ���

            	reportList.add(map);
            }
            
            HashMap hmap=new HashMap();
            hmap.put("totalnum", df2.format(totalnum));
            
            if (dform.get("genReport") != null && dform.get("genReport").equals("Y")) {
            	//����excel
    			response = toExcelRecord(response,reportList,hmap);
    			forward = mapping.findForward("exportExcel");
    		}else{
                if(reportList.size()>0 && reportList.size()<3001){
                	request.setAttribute("companyPaymentReportList", reportList);
                }else if(reportList.size()>3000){
                	request.setAttribute("showinfostr", "��ѯ���ݳ�����ǧ�м�¼���뵼��EXCEL�鿴��");
                }else{
                	request.setAttribute("showinfostr", "û�м�¼��ʾ��");
                }
                request.setAttribute("totalhmap", hmap);
                
    			forward = mapping.findForward("companyPaymentReportList");	
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

		String[] titlename={"��������","ί�к�ͬ��","ί�е�λ����","ί��̨��","ƾ֤��","������","�ۿ���","��������","���������������",
				"������������ĸ����������","��Ǽ�����������ĸ����������","�������������Ͷ�ߴ���","��Ǽ������������Ͷ�ߴ���","�ɼ��˻��������",
				"�ֲ����������","�ܲ���������","ά�����ʼ���"};
		String[] titleid={"MaintDivision","EntrustContractNo","CompanyName","elenum","ParagraphNo","ParagraphMoney",
				"DebitMoney","ParagraphDate","BydAuditEvaluate","HfAuditNum","HfAuditNum2","RxAuditNum",
				"RxAuditNum2","JjthAuditEvaluate","FbzAuditEvaluate","ZbzAuditRem","CustLevel"};
		
		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("ί�е�λ����������۱�");
        
        //������Ԫ����ʽ
        XSSFCellStyle cs = wb.createCellStyle();
        cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);//���Ҿ���
        cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
        XSSFFont f  = wb.createFont();
        f.setBoldweight(XSSFFont.BOLDWEIGHT_BOLD);// ����Ӵ�
        cs.setFont(f);
        
        int toolnum=2;
        //��������
        XSSFRow row0 = sheet.createRow(0);
        XSSFCell cell0 = null;
        int k = 0;
        int l = 0;
		for(int i=0;i<titlename.length;i++){
			if ("������������ĸ����������".equals(titlename[i]) || "��Ǽ�����������ĸ����������".equals(titlename[i])) {
				if (k==0) {
					k = i;
					cell0 = row0.createCell((short)(i));
					cell0.setCellValue("���лطý�����");
					sheet.addMergedRegion(new CellRangeAddress(0, 0, k, k+1));
					cell0.setCellStyle(cs);
				}
				row0 = sheet.getRow(1);
				if (row0 == null) {
					row0 = sheet.createRow(1);
				}
				cell0 = row0.createCell((short)(i));
				cell0.setCellValue(titlename[i]);
				cell0.setCellStyle(cs);
				
			}else if ("�������������Ͷ�ߴ���".equals(titlename[i]) || "��Ǽ������������Ͷ�ߴ���".equals(titlename[i])) {
				if (l==0) {
					l = i;
					row0 = sheet.getRow(0);
					cell0 = row0.createCell((short)(i));
					cell0.setCellValue("���߹�����");
					sheet.addMergedRegion(new CellRangeAddress(0, 0, l, l+1));
					cell0.setCellStyle(cs);
				}
				row0 = sheet.getRow(1);
				if (row0 == null) {
					row0 = sheet.createRow(1);
				}
				cell0 = row0.createCell((short)(i));
				cell0.setCellValue(titlename[i]);
				cell0.setCellStyle(cs);
				
			}else {
				row0 = sheet.getRow(0);
				cell0 = row0.createCell((short)(i));
				//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell0.setCellValue(titlename[i]);
				sheet.addMergedRegion(new CellRangeAddress(0, 1, i, i));
				cell0.setCellStyle(cs);
			}
		}
		//��������
		if (ReportList != null && ReportList.size()>0) {
			XSSFRow row = null;
			XSSFCell cell =null;
			toolnum=toolnum+ReportList.size();
			for (int j = 0; j < ReportList.size(); j++) {
				HashMap map=(HashMap) ReportList.get(j);
				// ����Excel�У���0�п�ʼ
				row = sheet.createRow(j+2);
				for(int c=0;c<titleid.length;c++){
				    cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				    if(titleid[c].equals("elenum") 
				    		|| titleid[c].equals("HfAuditNum") || titleid[c].equals("HfAuditNum2")
				    		|| titleid[c].equals("RxAuditNum") || titleid[c].equals("RxAuditNum2")){
				    	cell.setCellValue(Integer.parseInt((String)map.get(titleid[c])));
				    }else if (titleid[c].equals("ParagraphMoney") || titleid[c].equals("DebitMoney")) {
				    	cell.setCellValue(Double.parseDouble((String)map.get(titleid[c])));
					}else{
				    	cell.setCellValue((String)map.get(titleid[c]));
				    }
				}
			}
		}
		//��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
		//sheet.addMergedRegion(new CellRangeAddress(toolnum, toolnum, 0, 8));
		//XSSFRow row1 = sheet.createRow(toolnum);
		//XSSFCell cell1 = row1.createCell((short)0);
		//cell1.setCellValue("�˱���̨��:"+(String)hmap.get("totalnum"));
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("ί�е�λ����������۱�", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		
		return response;
	}
	
}
