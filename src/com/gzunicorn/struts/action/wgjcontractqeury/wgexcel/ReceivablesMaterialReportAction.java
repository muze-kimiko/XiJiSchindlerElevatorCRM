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
import java.util.Map;

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
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
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
 * Ӧ�տ����ϱ���
 * @author Administrator
 *
 */
public class ReceivablesMaterialReportAction extends DispatchAction {

	Log log = LogFactory.getLog("ReceivablesMaterialReportAction.class");
	BaseDataImpl bd = new BaseDataImpl();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/************��ʼ�û�Ȩ�޹���************/
		SysRightsUtil.filterModuleRight(request,response,SysRightsUtil.NODE_ID_FORWARD + "receivablesmaterialReport",null);
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
		request.setAttribute("navigator.location", "Ӧ�տ����ϱ��� >> ��ѯ");
		HttpSession session=request.getSession();
		ViewLoginUserInfo userInfo = null;
		List mugStorages = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
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
		String predates = (String) tableForm.getProperty("predates");// Ӧ�տ����ڣ�ʼ��
		String predatee = (String) tableForm.getProperty("predatee");// Ӧ�տ����ڣ��գ�
		String custname = (String) tableForm.getProperty("custname");// �������
		String contracttype = (String) tableForm.getProperty("contracttype");// ��ͬ����
		String grcid = (String) tableForm.getProperty("grcid");// ����ά���ֲ�����	
		request.setAttribute("conditionmap", tableForm.getProperties());
		
		if (contractid == null || "".equals(contractid.trim())) {
			contractid = "%";
		} else {
			contractid = "%"+contractid.trim()+"%";
		}
		if (predates == null || "".equals(predates.trim())) {
			predates = "0000-00-00";
		} else {
			predates = predates.trim();
		}
		if (predatee == null || "".equals(predatee.trim())) {
			predatee = "9999-99-99";
		} else {
			predatee = predatee.trim();
		}

		if (custname == null || "".equals(custname.trim())) {
			custname = "%";
		} else {
			custname = "%"+custname.trim()+"%";
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
			sql = "EXEC SP_ENG_WGJ_AR_FEE_QUERY '"+contractid+"','"+predates+"','"+predatee+"','"
				+custname+"','"+contracttype+"','"+grcid+"'" ;
			
			DebugUtil.println(sql);
			ResultSet rs = hs.connection().createStatement().executeQuery(sql);
			while (rs.next()) {
				count++;
				map = new HashMap();
				map.put("xuhao", count);
				map.put("contracttype", rs.getString("contracttype"));
				map.put("custid", rs.getString("custid"));
				map.put("contractid", rs.getString("contractid"));
				map.put("custname", rs.getString("custname"));
				map.put("predate", rs.getString("predate"));
				map.put("premoney", rs.getString("premoney"));
				map.put("nowfee", rs.getString("nowfee"));
				map.put("nonowfee", rs.getString("nonowfee"));
				map.put("grcname", rs.getString("grcname"));
				tempList.add(map);
			}
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (SQLException e1) {
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
		XSSFCellStyle cc = wb.createCellStyle();
		cc.setDataFormat((short)0x31);//XSSFDataFormat�����ݸ�ʽ
		
//		cc.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//���Ҿ���
//		cc.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
//		cc.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderRight(XSSFCellStyle.BORDER_THIN);

		int rowlistLen=resultList.size();	
		try{
			String headstr="���,��ͬ���,��ͬ��,�׷���λ,Ӧ�տ�����,Ӧ�ս��,�ѿ�Ʊ���,δ��Ʊ���,����ά���ֲ�";
			String key1str="xuhao,contracttype,contractid,custname,predate,premoney,nowfee,nonowfee,grcname";		
			String[] headName = headstr.split(",");
			int headNameLen = headName.length;//��ͷ����		
			int row0lineNo=0;
			int rowno=0;
			XSSFSheet sheet = wb.createSheet();
			wb.setSheetName(0, "ά��Ӧ�տ����ϱ���");					
					
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
			String[] key1Name = key1str.split(",");
			int key1NameLen = key1Name.length;
			/*�����Ӧ��ͷ����ֵ*/
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
							if ("nowfee".equals(key1Name[m]) || "feeamt".equals(key1Name[m]) ||
									"premoney".equals(key1Name[m]) || "nonowfee".equals(key1Name[m])
									) {
								cell0.setCellValue(Double.valueOf(rowMap.get(key1Name[m])+""));
							}else if("door".equals(key1Name[m])){
								String floor=(String)rowMap.get("floor");
								String stage=(String)rowMap.get("stage");
								String door=(String)rowMap.get("door");
								cell0.setCellValue(floor+"/"+stage+"/"+door);
								//���õ�Ԫ������Ϊ�ַ�����
								cell0.setCellStyle(cc);

							} else {
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
				+ URLEncoder.encode("Ӧ�տ����ϱ���", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());			
		return response;	
		}
		
		private List getGrcidList() {
			
			Session hs = null;
			Map grcidmap = null;
			List grcidList = null;
			try {
				hs = HibernateUtil.getSession();

				StringBuffer columnNames = new StringBuffer();

				columnNames.append("grcid,");
				columnNames.append("grcname");

				String sql = "select "
						+ columnNames
						+ " from GR_CompanyType";
				Query query = hs.createSQLQuery(sql);
				List tempList = query.list();
				
				if(tempList!=null){
					grcidList = new ArrayList();
					
					for (Object object : tempList) {
						Object[] obj = (Object[]) object;
						if (obj != null) {
							grcidmap = new HashMap();
							grcidmap.put("grcid", obj[0] + "");
							grcidmap.put("grcname",obj[1] + "");
							grcidList.add(grcidmap);
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
			return grcidList;

		}
		
  }




