package com.gzunicorn.struts.action.wgjcontractqeury.wgexcel;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.ss.util.Region;
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
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.action.ServeTableForm;

/**
 * �깤����
 * 
 * @author Administrator
 * 
 */
public class CompleteStatementAction extends DispatchAction {

	Log log = LogFactory.getLog("CompleteStatementAction.class");
	BaseDataImpl bd = new BaseDataImpl();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/************ ��ʼ�û�Ȩ�޹��� ************/
		SysRightsUtil.filterModuleRight(request, response,
				SysRightsUtil.NODE_ID_FORWARD + "completestatement", null);
		/************ �����û�Ȩ�޹��� ************/

		String name = request.getParameter("method");
		if (name == null || name.equals("")) {
			name = "toSearchCondition";
		}
		return dispatchMethod(mapping, form, request, response, name);
	}

	/**
	 * ��ѯ����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toSearchCondition(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {
		request.setAttribute("navigator.location", "�깤���� >> ��ѯ");
		HttpSession session = request.getSession();
		Session hs = null;
		ViewLoginUserInfo userInfo = null;
		try {
			userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
			hs = HibernateUtil.getSession();
			request.setAttribute("grcidlist",Grcnamelist1.getgrcnamelist(hs, userInfo.getUserID()));
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		return mapping.findForward("toCondition");
	}

	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServeTableForm tableForm = (ServeTableForm) form;
		HttpSession session = request.getSession();
		HashMap conditionmap = new HashMap();

		// ��ȡǰ̨��ѯ����
		String lotdates = (String) tableForm.getProperty("lotdates");// ǩ�����ڣ�ʼ��
		String lotdatee = (String) tableForm.getProperty("lotdatee");// ǩ�����ڣ��գ�

		String completes = (String) tableForm.getProperty("completes");// �깤���ڣ�ʼ��
		String completee = (String) tableForm.getProperty("completee");// �깤���ڣ��գ�
		
		String grcid = (String) tableForm.getProperty("grcid");// ����ά���ֲ�����

		// ����ѯ���������map�У����ڲ鿴ҳ��ĵ���excel����ʱ��ѯ����
		conditionmap.put("lotdates", lotdates);
		conditionmap.put("lotdatee", lotdatee);
		conditionmap.put("completes", completes);
		conditionmap.put("completee", completee);
		conditionmap.put("grcid", grcid);

		conditionmap.put("timerange", "N");
		if ((null != lotdates && !"".equals(lotdates.trim()))
				|| (null != lotdatee && !"".equals(lotdatee.trim()))) {
			conditionmap.put("timerange", "Y");
		}

		request.setAttribute("conditionmap", conditionmap);

		if (lotdates == null || "".equals(lotdates.trim())) {
			lotdates = "0000-00-00";
		} else {
			lotdates = lotdates.trim();
		}
		if (lotdatee == null || "".equals(lotdatee.trim())) {
			lotdatee = "9999-99-99";
		} else {
			lotdatee = lotdatee.trim();
		}
		if (completes == null || "".equals(completes.trim())) {
			completes = "";
		} else {
			completes = completes.trim();
		}
		if (completee == null || "".equals(completee.trim())) {
			completee = "9999-99-99";
		} else {
			completee = completee.trim();
		}
		if (grcid == null || "".equals(grcid.trim())) {
			grcid = "%";
		} else {
			grcid = grcid.trim();
		}

		Session hs = null;
		String sql = "";
		int wnum = 0;// ά����̨��
		int gnum = 0;// ������̨��
		double wprice = 0.0;// ά���ܼ�
		double gprice = 0.0;// �����ܼ�
		List wtempList = new ArrayList();// ά�޼���
		List gtempList = new ArrayList();// ���켯��

		// double nowfee = 0;
		int count = 0;
		try {
			hs = HibernateUtil.getSession();
			sql = "EXEC SP_GET_MONTH_FINISH_CONTRACT_COLLECT '" + lotdates
					+ "','" + lotdatee + "','" + completes + "','" + completee
					+"','"+grcid+"'" ;

			DebugUtil.println(sql);
			ResultSet rs = hs.connection().createStatement().executeQuery(sql);
			while (rs.next()) {
				// ά��
				if (rs.getString("contracttype").equals("W")) {
					HashMap map = new HashMap();
					map.put("billno", rs.getString("billno"));
					map.put("contractid", rs.getString("contractid"));
					map.put("custid", rs.getString("custid"));
					map.put("custname", rs.getString("custname"));
					map.put("num", rs.getString("num"));
					wnum = wnum + Integer.parseInt(rs.getString("num"));
					map.put("collectMon", rs.getString("collectMon"));
					wprice = wprice
							+ Double.parseDouble(rs.getString("collectMon"));
					map.put("finishdate", rs.getString("finishdate"));
					map.put("grcname", rs.getString("grcname"));
					wtempList.add(map);
				}
				// ����
				if (rs.getString("contracttype").equals("G")) {
					HashMap map1 = new HashMap();
					map1.put("billno", rs.getString("billno"));
					map1.put("contractid", rs.getString("contractid"));
					map1.put("custid", rs.getString("custid"));
					map1.put("custname", rs.getString("custname"));
					map1.put("num", rs.getString("num"));
					gnum = gnum + Integer.parseInt(rs.getString("num"));
					map1.put("collectMon", rs.getString("collectMon"));
					gprice = gprice
							+ Double.parseDouble(rs.getString("collectMon"));
					map1.put("finishdate", rs.getString("finishdate"));
					map1.put("grcname", rs.getString("grcname"));
					gtempList.add(map1);
				}


			}
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		} finally {
			hs.close();
		}
		// NumberFormat nf = new DecimalFormat("###,###.00");
		// request.setAttribute("nowfee", nf.format(nowfee));

		ActionForward forward = null;
		if (null != tableForm.getProperty("genReport")
				&& "Y".equals(tableForm.getProperty("genReport"))) {
			try {
				if (lotdates == null) {
					lotdates = "";
				}
				if (lotdatee == null) {
					lotdatee = "";
				}
				response = toExcelRecord(wtempList, gtempList, wnum,
						gnum,  wprice, gprice, lotdates, lotdatee,
						request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("wtempList", wtempList);// ��ѯά�޽����
			request.setAttribute("gtempList", gtempList);// ��ѯ��������

			request.setAttribute("wnum", wnum);// ��ѯά������
			request.setAttribute("gnum", gnum);// ��ѯ��������

			request.setAttribute("wprice", CommonUtil.formatprice(wprice));// ��ѯά���ܼ�
			request.setAttribute("gprice", CommonUtil.formatprice(gprice));// ��ѯ�����ܼ�
			forward = mapping.findForward("toList");
		}
		return forward;
	}

	/**
	 * ������ѯ���ݵ�Excel
	 * 
	 * @param resultList
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public HttpServletResponse toExcelRecord(List wresultList,
			List gresultList,  int wnum, int gnum, 
			double wprice, double gprice,  String lotdates,
			String lotdatee, HttpServletRequest request,
			HttpServletResponse response) throws IOException {

		XSSFWorkbook wb = new XSSFWorkbook();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) request.getSession().getAttribute(SysConfig.LOGIN_USER_INFO);
		XSSFSheet sheet=wb.createSheet();
		wb.setSheetName(0,"ά���깤����");
		XSSFCellStyle style=wb.createCellStyle();
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);//���Ҿ���
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);//���¾���
		int nos=0;
		try {
			String[] wrowname={"���","��ͬ��","ί�е�λ��ά����Ŀ��","̨��","��ֵ��Ԫ��","�깤����","	����ά���ֲ�"};
			String[] growname={"���","��ͬ��","ί�е�λ��������Ŀ��","̨��","��ֵ��Ԫ��","�깤����","	����ά���ֲ�"};
			String[] wcellvalue={"","contractid","custname","num","collectMon","finishdate","grcname"};
			//����ά����Ϣ
			XSSFRow row=sheet.createRow(nos);
			//��һ��
			for(int i=0;i<wrowname.length;i++){
				XSSFCell cell=row.createCell((short)i);
				cell.setCellValue(wrowname[i]);
				cell.setCellStyle(style);
			}
			nos++;
			//���ݵ���
			for(int i=0;i<wresultList.size();i++,nos++){
				HashMap hm=(HashMap)wresultList.get(i);
				 row=sheet.createRow(nos);
				for(int j=0;j<wrowname.length;j++){
					XSSFCell cell=row.createCell((short)j);
					if(j==0){
					cell.setCellValue(i+1);
					}else{				
				    cell.setCellValue(String.valueOf(hm.get(wcellvalue[j])));
					}
					cell.setCellStyle(style);
				}
			}
			//�ϼ�
			row=sheet.createRow(nos);
			XSSFCell cell=row.createCell((short)2);
			cell.setCellValue("�ϼƣ�");
			cell.setCellStyle(style);
			cell=row.createCell((short)3);
			cell.setCellValue(wnum);
			cell.setCellStyle(style);
			cell=row.createCell((short)4);
			cell.setCellValue(wprice);
			cell.setCellStyle(style);
			nos++;
			//����������Ϣ
			row=sheet.createRow(nos);
			//��һ��
			for(int i=0;i<growname.length;i++){
				cell=row.createCell((short)i);
				cell.setCellValue(growname[i]);
				cell.setCellStyle(style);
			}
			nos++;
			//���ݵ���
			for(int i=0;i<gresultList.size();i++,nos++){
				HashMap hm=(HashMap)gresultList.get(i);
				 row=sheet.createRow(nos);
				for(int j=0;j<growname.length;j++){
					cell=row.createCell((short)j);
					if(j==0){
					cell.setCellValue(i+1);
					}else{				
				    cell.setCellValue(String.valueOf(hm.get(wcellvalue[j])));
					}
					cell.setCellStyle(style);
				}
			}
			//�ϼ�
			row=sheet.createRow(nos);
			cell=row.createCell((short)2);
			cell.setCellValue("�ϼƣ�");
			cell.setCellStyle(style);
			cell=row.createCell((short)3);
			cell.setCellValue(gnum);
			cell.setCellStyle(style);
			cell=row.createCell((short)4);
			cell.setCellValue(gprice);
			cell.setCellStyle(style);
			nos++;
		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="
				+ URLEncoder.encode("�깤����", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		return response;
	}
}
