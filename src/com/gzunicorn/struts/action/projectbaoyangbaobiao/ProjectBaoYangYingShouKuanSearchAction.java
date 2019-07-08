package com.gzunicorn.struts.action.projectbaoyangbaobiao;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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
import org.apache.struts.actions.DispatchAction;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.logic.DataOperation;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SqlBeanUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.action.ServeTableForm;

public class ProjectBaoYangYingShouKuanSearchAction extends DispatchAction {
	/**
	 * ���ݱ����տ��ѯ����
	 * 
	 * @author Administrator
	 * 
	 */
		Log log = LogFactory.getLog("ProjectBaoYangYingShouKuanSearchAction.class");

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
		@Override
		public ActionForward execute(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			String name = request.getParameter("method");
			if (name == null || name.equals("")) {
				name = "toSearchCondition";
			}
			ActionForward forward = dispatchMethod(mapping, form, request,
					response, name);
			return forward;
		}

		/**
		 * ��ʾ��ѯ��������
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
			request.setAttribute("navigator.location", "�տ��ѯ���� >> ��ѯ");
			
			HttpSession session=request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			
			request.setAttribute("maintDivisionList", Grcnamelist1.getgrcnamelist(userInfo.getUserID()));

			return mapping.findForward("condition");
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
		
		public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) {
			ActionForward forward = null;
			ServeTableForm tableForm = (ServeTableForm) form;
		    //HttpSession session = request.getSession();
			//ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			
			String genReport=tableForm.getProperty("genReport");//����excel��־						
			String maintContractNo = tableForm.getProperty("maintContractNo"); // ά����ͬ��
			String startDate = tableForm.getProperty("startDate"); // ��ѯ��ʼʱ��
			String endDate = tableForm.getProperty("endDate"); // ��ѯ��������
			String companyName = tableForm.getProperty("companyName"); // �׷���λ
			String maintDivision = tableForm.getProperty("maintDivision"); //����ά��վ
			
			maintContractNo = maintContractNo == null || "".equals(maintContractNo) ? "%" : "%"+maintContractNo.trim()+"%";
			startDate = startDate == null || "".equals(startDate) ? "0000-00-00" : startDate.trim();	
			endDate = endDate == null || "".equals(endDate) ? "9999-99-99" : endDate.trim();	
			companyName = companyName == null || "".equals(companyName) ? "%" : "%"+companyName.trim()+"%";
			maintDivision = maintDivision == null || "".equals(maintDivision) ? "%" : maintDivision.trim();
		
			List<Map<String,String>> reportList = null;
			Double premoney=0.0;//Ӧ�ս��
			Double nowfee=0.0;//�ѿ�Ʊ���
			Double nonowfee=0.0;//δ��Ʊ���
			Double billatm=0.0;//�ѿ�Ʊ���տ���
			Double billnoatm=0.0;//�ѿ�Ʊ����Ƿ����
			Double nobillatm=0.0;//δ��Ʊ����Ƿ����
			Double nobillnoatm=0.0;//�ѿ�Ʊ��Ƿ���� 
		
			Connection conn = null;
			CallableStatement cs = null;
			ResultSet rs = null;
		    Session hs = null;
		    HashMap map = null;
			try {
				hs = HibernateUtil.getSession();
				conn = hs.connection();
				cs = conn.prepareCall("{call SP_ENG_ARREBLANCFEE_QUERY (?,?,?,?,?)}");
				cs.setString(1, maintContractNo);
				cs.setString(2, companyName);				
				cs.setString(3, startDate);
				cs.setString(4, endDate);
				cs.setObject(5, maintDivision);
				cs.executeQuery();
				rs = cs.getResultSet();					
				reportList = new ArrayList<Map<String,String>>();
		
				while(rs.next()){
					map = new HashMap();
					map.put("contractid", rs.getString("contractid"));
					map.put("custname", rs.getString("custname"));
					map.put("predate", rs.getString("predate"));
					map.put("premoney", rs.getString("premoney"));
					map.put("nowfee", rs.getString("nowfee"));
					map.put("nonowfee", rs.getString("nonowfee"));
					map.put("billatm", rs.getString("billatm"));
					map.put("billnoatm", rs.getString("billnoatm"));
					map.put("nobillatm", rs.getString("nobillatm"));
					map.put("nobillnoatm", rs.getString("nobillnoatm"));
					map.put("grcname", rs.getString("grcname"));
					
					premoney+=Double.valueOf(rs.getString("premoney")).doubleValue();
					nowfee+=Double.valueOf(rs.getString("nowfee")).doubleValue();
					nonowfee+=Double.valueOf(rs.getString("nonowfee")).doubleValue();
					billatm+=Double.valueOf(rs.getString("billatm")).doubleValue();
					billnoatm+=Double.valueOf(rs.getString("billnoatm")).doubleValue();
					nobillatm+=Double.valueOf(rs.getString("nobillatm")).doubleValue();
					nobillnoatm+=Double.valueOf(rs.getString("nobillnoatm")).doubleValue();
					reportList.add(map);
				}
				DecimalFormat df=new DecimalFormat("#,##0.00");
				String sj="ͳ�ƣ���¼��"+reportList.size()+"��, "
						+ "Ӧ�տ����ܼ�Ϊ��"+df.format(premoney)+"��Ԫ��,"
						+ "�ѿ�Ʊ����ܼ�Ϊ��"+df.format(nowfee)+"��Ԫ), "
						+ "δ��Ʊ����ܼ�Ϊ��"+df.format(nonowfee)+"��Ԫ��, "
						+ "�ѿ�Ʊ���տ����ܼ�Ϊ��"+df.format(billatm)+"��Ԫ��, "
						+ "�ѿ�Ʊ����Ƿ�����ܼ�Ϊ��"+df.format(billnoatm)+"��Ԫ��, "
				        + "δ��Ʊ����Ƿ�����ܼ�Ϊ��"+df.format(nobillatm)+"��Ԫ��, "
				        + "�ѿ�Ʊ��Ƿ�����ܼ�Ϊ��"+df.format(nobillnoatm)+"��Ԫ��. ";
				request.setAttribute("sj", sj);
				request.setAttribute("reportList", reportList);// ��ѯ����б�
				request.setAttribute("rowNums", reportList.size());// ��ѯ���������
				request.setAttribute("search", tableForm.getProperties());// ��ѯ����
				
				
				if ("Y".equals(genReport)) {
					response = toExcelRecord(reportList,sj, request, response);				
				} else {
					forward = mapping.findForward("returnList");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					conn.close();
					hs.close();
				} catch (Exception e1) {
					e1.printStackTrace();
				}
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
		public HttpServletResponse toExcelRecord(List resultList,String sj,
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
			int rowlistLen=resultList.size();
			try{
				String headstr="���,��ͬ��,�׷���λ,Ӧ������,Ӧ�ս��," +
						"�ѿ�Ʊ���,δ��Ʊ���,�ѿ�Ʊ���տ���,�ѿ�Ʊ����Ƿ����,δ��Ʊ����Ƿ����,�ѿ�Ʊ��Ƿ����,����ά���ֲ�";
				String key1str="xuhao,contractid,custname,predate,premoney," +
						"nowfee,nonowfee,billatm,billnoatm,nobillatm,nobillnoatm,grcname";		
				String[] headName = headstr.split(",");
				int headNameLen = headName.length;//��ͷ����		
				int rowno=0;
				XSSFSheet sheet = wb.createSheet();	
				wb.setSheetName(0,"�����տ��ѯ��");
				/*�����һ�б�ͷ��ʼ*/
				XSSFRow row0 = sheet.createRow( rowno); // ������һ��
				XSSFCell cell0=null;
				for (int i = 0; i < headNameLen; i++) {				
					cell0 = row0.createCell((short) i);
					cell0.setCellValue(headName[i]);
					cell0.setCellStyle(cs);					
				}
				rowno++;
				String[] key1Name = key1str.split(",");
				int key1NameLen = key1Name.length;
				/*�����Ӧ��ͷ����ֵ*/
				HashMap rowMap=null;
				int rownlineNo=0;
				if(rowlistLen>0){				
					for(int i=0;i<resultList.size();i++,rowno++){
						HashMap hm=(HashMap)resultList.get(i);
						  row0=sheet.createRow(rowno);
						for(int j=0;j<key1NameLen;j++){
							cell0=row0.createCell((short)j);
							if(j==0){
								cell0.setCellValue(i+1);
							}else{			
								String value=String.valueOf(hm.get(key1Name[j]));
								cell0.setCellValue(value);							
							}
							cell0.setCellStyle(cs);
						}
					}	
				}
				//���β��
				XSSFCellStyle cellstyle=wb.createCellStyle();
				cellstyle.setWrapText(true);
				sheet.addMergedRegion(new CellRangeAddress(rowno,rowno+4,0,headNameLen-1));
				row0=sheet.createRow(rowno);
				cell0=row0.createCell(0);
				cell0.setCellValue(sj);
				cell0.setCellStyle(cellstyle);
		}catch(Exception e){
			e.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="
				+ URLEncoder.encode("�տ��ѯ����", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());			
		return response;	
		}
		
		
		
	}