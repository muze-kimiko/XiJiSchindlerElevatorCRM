package com.gzunicorn.struts.action.wgjcontractqeury.wgexcel;

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
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.action.ServeTableForm;

/**
 * ά�޸����װ���ϲ�ѯ��
 * @author Administrator
 *
 */
public class ServiceWGJDataLookupTableAction extends DispatchAction {

	Log log = LogFactory.getLog("ServiceWGJDataLookupTableAction.class");
	BaseDataImpl bd = new BaseDataImpl();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/************��ʼ�û�Ȩ�޹���************/
		SysRightsUtil.filterModuleRight(request,response,SysRightsUtil.NODE_ID_FORWARD + "servicewgjdatalookuptable",null);
		/************�����û�Ȩ�޹���************/
		
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
		request.setAttribute("navigator.location", "ά�޸����װ���ϲ�ѯ�� >> ��ѯ");
		List mugStorages = new ArrayList();
		Session hs = null;
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = null;
		try {
			userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			hs = HibernateUtil.getSession();
			request.setAttribute("grcidlist", Grcnamelist1.getgrcnamelist(hs,userInfo.getUserID()));
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
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
		String contractid = (String) tableForm.getProperty("contractid");//��ͬ���
		String finishdates = (String) tableForm.getProperty("finishdates");// �깤���ڣ�ʼ��
		String finishdatee = (String) tableForm.getProperty("finishdatee");// �깤���ڣ��գ�
		String custname = (String) tableForm.getProperty("custname");// �׷���λ����
		String username = (String) tableForm.getProperty("username");// ҵ��Ա����
		String budgetid = (String) tableForm.getProperty("budgetid");// Ԥ���
		String nodeid = (String) tableForm.getProperty("nodeid");// ����Դ
		String grcid = (String) tableForm.getProperty("grcid");// ����ά���ֲ�����

		//����ѯ���������map�У����ڲ鿴ҳ��ĵ���excel����ʱ��ѯ����
		conditionmap.put("contractid", contractid);
		conditionmap.put("finishdates", finishdates);
		conditionmap.put("finishdatee", finishdatee);
		conditionmap.put("custname", custname);
		conditionmap.put("username", username);
		conditionmap.put("budgetid", budgetid);
		conditionmap.put("nodeid", nodeid);
		conditionmap.put("grcid", grcid);
		//conditionmap.put("timerange", "N");
		//if ((null != predates && !"".equals(predates)) || (null != predatee && !"".equals(predates))) {
		//	conditionmap.put("timerange", "Y");
		//}
		request.setAttribute("conditionmap", conditionmap);
		
		if (contractid == null || "".equals(contractid.trim())) {
			contractid = "%";
		} else {
			contractid = "%"+contractid.trim()+"%";
		}
		if (finishdates == null || "".equals(finishdates.trim())) {
			finishdates = "0000-00-00";
		} else {
			finishdates = finishdates.trim();
		}
		if (finishdatee == null || "".equals(finishdatee.trim())) {
			finishdatee = "9999-99-99";
		} else {
			finishdatee = finishdatee.trim();
		}
		if (budgetid == null || "".equals(budgetid.trim())) {
			budgetid = "%";
		} else {
			budgetid = "%"+budgetid.trim()+"%";
		}
		if (custname == null || "".equals(custname.trim())) {
			custname = "%";
		} else {
			custname = "%"+custname.trim()+"%";
		}
		if (username == null || "".equals(username.trim())) {
			username = "%";
		} else {
			username = "%"+username.trim()+"%";
		}
		if (nodeid == null || "".equals(nodeid.trim())) {
			nodeid = "%";
		} else {
			nodeid = nodeid.trim();
		}
		if (grcid == null || "".equals(grcid.trim())) {
			grcid = "%";
		} else {
			grcid = grcid.trim();
		}
		
		List tempList = new ArrayList();
		HashMap map = null;
		Session hs = null;
		String sql = "";
		//double nowfee = 0;
		int count = 0;
		try {
			hs = HibernateUtil.getSession();
			sql = "EXEC SP_SERVICE_WGJ_DATA_LOOKUP_TABLE '"+contractid+"','"+finishdates+"','"+finishdatee+"','"
				+custname+"','"+grcid+"'"  ;
			
			DebugUtil.println(sql);
			ResultSet rs = hs.connection().createStatement().executeQuery(sql);
			while (rs.next()) {
				count++;
				map = new HashMap();
				map.put("xuhao", count);
				map.put("contractid", rs.getString("contractid"));				
				map.put("tasksubdate", rs.getString("tasksubdate"));				
				map.put("custname", rs.getString("custname"));				
				map.put("totalnum", rs.getString("totalnum"));				
				map.put("totalamt", rs.getString("totalamt"));
				map.put("finishdate", rs.getString("finishdate"));
				map.put("grcname", rs.getString("grcname"));

				tempList.add(map);
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			hs.close();
		}
		
		//NumberFormat nf = new DecimalFormat("###,###.00");
		//request.setAttribute("nowfee", nf.format(nowfee));
		
		ActionForward forward = null;
		if (null != tableForm.getProperty("genReport") && "Y".equals(tableForm.getProperty("genReport"))) {
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
		cs.setBorderTop(XSSFCellStyle.BORDER_THIN);//�����ϱ߿���ʾ
		cs.setBorderBottom(XSSFCellStyle.BORDER_THIN);//�����±߿���ʾ
		cs.setBorderLeft(XSSFCellStyle.BORDER_THIN);//������߿���ʾ
		cs.setBorderRight(XSSFCellStyle.BORDER_THIN);//�����ұ߿���ʾ

		//���ñ����ݵĵ�Ԫ����ʽ
//		XSSFCellStyle cc = wb.createCellStyle();
//		cc.setDataFormat((short)0x31);//XSSFDataFormat�����ݸ�ʽ	�ı�����
//		cc.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//���Ҿ���
//		cc.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
//		cc.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderRight(XSSFCellStyle.BORDER_THIN);

		int rowlistLen=resultList.size();	
		try{
			String headstr="���,��ͬ��,����������,ά�޸��쵥λ,̨��,��ͬ�ܶ�,�깤����,����ά���ֲ�";
			String key1str="xuhao,contractid,tasksubdate,custname,totalnum,totalamt,finishdate,grcname";
			
			String[] headName = headstr.split(",");
			int headNameLen = headName.length;//��ͷ����		
			int row0lineNo=0;
			int rowno=0;
			XSSFSheet sheet = wb.createSheet();
			wb.setSheetName(0, "ά�޸����װ���ϲ�ѯ��");					
					
			/*�����һ�б�ͷ��ʼ*/
			XSSFRow row0 = sheet.createRow( rowno); // ������һ��
			XSSFCell cell0=null;
			for (int i = 0; i < headNameLen; i++) {				
				cell0 = row0.createCell((short) row0lineNo);
				cell0.setCellValue(headName[i]);
				cell0.setCellStyle(cs);					
				row0lineNo++;
			}
			rowno++;
			
			/*�����Ӧ��ͷ����ֵ*/
			String[] key1Name = key1str.split(",");
			int key1NameLen = key1Name.length;
			HashMap rowMap=null;
			int rownlineNo=0;
			if(rowlistLen>0){				
				for (int k = 0 ;  k < rowlistLen; k++) {	
					rownlineNo=0;
					row0 = sheet.createRow( rowno);
					rowMap = (HashMap) resultList.get(k);	
					for (int m = 0; m < key1NameLen; m++) {
						cell0 = row0.createCell((short) rownlineNo);
						if (rowMap.get(key1Name[m])!=null){
							if ("totalnum".equals(key1Name[m]) || "totalamt".equals(key1Name[m])) {
								cell0.setCellValue(Double.valueOf(rowMap.get(key1Name[m])+""));
							}else {
								cell0.setCellValue(rowMap.get(key1Name[m]) + "");
							}
						}
						rownlineNo++;
					}
					rowno++;
				}	
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="
				+ URLEncoder.encode("ά�޸����װ���ϲ�ѯ��", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());			
		return response;	
		}
		
  }


