package com.gzunicorn.struts.action.projectbaoyangbaobiao;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.action.ServeTableForm;

/**
 * ���ݱ�����Ʊ���뱨��
 * @author Administrator
 *
 */
public class ProjectBaoYangKaiPiaoShenQingAction extends DispatchAction {

	Log log = LogFactory.getLog("ProjectBaoYangKaiPiaoShenQingAction.class");
	BaseDataImpl bd = new BaseDataImpl();

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String name = request.getParameter("method");
		if (name == null || name.equals("")) {
			name = "toSearchCondition";
		}
		return dispatchMethod(mapping, form, request, response, name);
	}

	/**
	 * ��ѯ����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toSearchCondition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("navigator.location", "��Ʊ���뱨�� >> ��ѯ");
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			request.setAttribute("grcidlist", Grcnamelist1.getgrcnamelist(hs,userInfo.getUserID()));
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}		
		return mapping.findForward("toCondition");
	}


	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		ServeTableForm tableForm = (ServeTableForm) form;
		HttpSession session = request.getSession();
		HashMap conditionmap = new HashMap();
		
		//��ȡǰ̨��ѯ����
		String contractid = tableForm.getProperty("contractid");//��ͬ���
		String predates = tableForm.getProperty("predates");// Ӧ�տ����ڣ�ʼ��
		String predatee = tableForm.getProperty("predatee");// Ӧ�տ����ڣ��գ�
		String invoiceno = tableForm.getProperty("invoiceno");// ��Ʊ����
		String date2s = tableForm.getProperty("date2s");// ��Ʊ�������ڣ�ʼ��
		String date2e = tableForm.getProperty("date2e");// ��Ʊ�������ڣ��գ�
		String custname = tableForm.getProperty("custname");// �������
		String grcid = tableForm.getProperty("grcid");// ����ά���ֲ�
		if ((null != predates && !"".equals(predates)) || (null != predatee && !"".equals(predatee))) {
			request.setAttribute("timerange", "Y");
		}
		String genReport=tableForm.getProperty("genReport");
		
		//����ѯ���������map�У����ڲ鿴ҳ��ĵ���excel����ʱ��ѯ����
		request.setAttribute("conditionmap", tableForm.getProperties());		
		if (contractid == null || "".equals(contractid)) {
			contractid = "%";
		} else {  
			contractid = "%"+contractid.trim()+"%";
		}
		if (predates == null || "".equals(predates)) {
			predates = "0000-00-00";
		} else {
			predates = predates.trim();
		}
		if (predatee == null || "".equals(predatee)) {
			predatee = "9999-99-99";
		} else {
			predatee = predatee.trim();
		}
		if (invoiceno == null || "".equals(invoiceno)) {
			invoiceno = "%";
		} else {
			invoiceno = "%"+invoiceno.trim()+"%";
		}
		if (date2s == null || "".equals(date2s)) {
			date2s = "0000-00-00";
		}
		if (date2e == null || "".equals(date2e)) {
			date2e = "9999-99-99";
		}
		if (custname == null || "".equals(custname)) {
			custname = "%";
		} else {
			custname = "%"+custname.trim()+"%";
		}
		if (grcid == null || "".equals(grcid)) {
		    grcid="%";
		}else{
		    grcid = "%"+grcid.trim()+"%";
		}

		List tempList = new ArrayList();
		HashMap map = null;
		Session hs = null;
		String sql = "";
		double nowfee = 0;
		int count = 0;
		try {
			hs = HibernateUtil.getSession();
            sql = "exec SP_BILLING_APPLY_QUERY '"+contractid+"','"+predates+"','"+predatee+"','"+invoiceno+"','"
			+date2s+"','"+date2e+"','"+custname+"','"+grcid+"'" ;			

			//DebugUtil.println(sql);			
			ResultSet rs = hs.connection().createStatement().executeQuery(sql);
			//System.out.println(rs.getMetaData());
			while (rs.next()) {
				count++;
				map = new HashMap();
				map.put("xuhao", count);
				map.put("invoiceno", rs.getString("invoiceno"));
				map.put("date2", rs.getString("date2"));
				map.put("custname", rs.getString("custname"));
				map.put("contractid", rs.getString("contractid"));
				map.put("predate", rs.getString("predate"));
				map.put("nowfee", rs.getString("nowfee"));
				nowfee += Double.valueOf(rs.getString("nowfee")).doubleValue();
				map.put("grcname", rs.getString("grcname"));
				tempList.add(map);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			hs.close();
		}
		
		NumberFormat nf = new DecimalFormat("###,###.00");
		request.setAttribute("grcid22", grcid);
		request.setAttribute("nowfee", nf.format(nowfee));
		ActionForward forward = null;
		if ( genReport!= null && genReport.equals("Y")) {
			try {
				response = toExcelRecord(tempList, request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}		
		} else {
			request.setAttribute("count", count);//��ѯ�����
			request.setAttribute("resultList", tempList);//��ѯ�����
			forward = mapping.findForward("toList");
		}
		return forward;
	}
		
		/**
		 * ������ѯ���ݵ�Excel
		 * @param resultList
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		public HttpServletResponse toExcelRecord(List resultList,
				HttpServletRequest request, HttpServletResponse response) throws IOException {
			
		XSSFWorkbook wb = new XSSFWorkbook();
		
		//���ñ�ͷ�Ĺ��õ�Ԫ����ʽ
		XSSFCellStyle cs = wb.createCellStyle();
		cs.setAlignment(XSSFCellStyle.ALIGN_CENTER);//���Ҿ���
		cs.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
		XSSFFont f  = wb.createFont();
		f.setFontHeightInPoints((short) 11);//�ֺ�
		f.setBoldweight(XSSFFont.BOLDWEIGHT_NORMAL);//�Ӵ�
		cs.setFont(f);
		cs.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cs.setBorderRight(XSSFCellStyle.BORDER_THIN);

		//���ñ����ݵĵ�Ԫ����ʽ
		XSSFCellStyle cc = wb.createCellStyle();
		cc.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//���Ҿ���
		cc.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
		cc.setBorderTop(XSSFCellStyle.BORDER_THIN);
		cc.setBorderBottom(XSSFCellStyle.BORDER_THIN);
		cc.setBorderLeft(XSSFCellStyle.BORDER_THIN);
		cc.setBorderRight(XSSFCellStyle.BORDER_THIN);

		int rowlistLen=resultList.size();	
		try{
			String headstr="���,��Ʊ����,��Ʊ��������,�������,��ͬ��,Ӧ�տ�����,���,����ά���ֲ�";
			String key1str="xuhao,invoiceno,date2,custname,contractid,predate,nowfee,grcname";		
			String[] headName = headstr.split(",");
			int headNameLen = headName.length;//��ͷ����		
			int row0lineNo=0;
			int rowno=0;
			XSSFSheet sheet = wb.createSheet();
			wb.setSheetName(0, "��Ʊ���뱨��");					
					
			/*�����һ�б�ͷ��ʼ*/
			XSSFRow row0 = sheet.createRow(rowno); // ������һ��
			XSSFCell cell0=null;
			for (int i = 0; i < headNameLen; i++) {				
				cell0 = row0.createCell((int) row0lineNo);
				cell0.setCellValue(headName[i]);
				cell0.setCellStyle(cs);					
				row0lineNo++;
			}
			rowno++;
			String[] key1Name = key1str.split(",");
			int key1NameLen = key1Name.length;
			/*�����Ӧ��ͷ����ֵ*/
			HashMap rowMap=null;
			int rownlineNo=0;
			if(rowlistLen>0){				
				for (int k = 0 ;  k < rowlistLen; k++) {	
					rownlineNo=0;
					row0 = sheet.createRow(rowno);
					rowMap = (HashMap) resultList.get(k);	
					for (int m = 0; m < key1NameLen; m++) {
						cell0 = row0.createCell((int) rownlineNo);
						if (rowMap.get(key1Name[m])!=null){
							if ("nowfee".equals(key1Name[m]) || "feeamt".equals(key1Name[m])) {
								cell0.setCellValue(Double.valueOf(rowMap.get(key1Name[m])+""));
							} else {
								cell0.setCellValue(rowMap.get(key1Name[m]) + "");
							}
						}
						rownlineNo++;
					}
					rowno++;
				}
				/*�������β��ʼ*/
				String footstr="ͳ���ܼ�¼��:"+rowlistLen+"��,��Ʊ����ܼ�Ϊ:"+request.getAttribute("nowfee")+"��Ԫ��";
				XSSFRow rowo = sheet.createRow(rowno); // �������һ��			
				cell0 = rowo.createCell(2,5);
				cell0.setCellValue(footstr);	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="
				+ URLEncoder.encode("��Ʊ���뱨��", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());			
		return response;	
		}
		
		
		
	}