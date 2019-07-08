package com.gzunicorn.struts.action.projectbaoyangbaobiao;
import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.util.CellRangeAddress;
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
import com.gzunicorn.common.logic.DataOperation;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.zubarev.htmltable.action.ServeTableForm;

public class ProjectBaoYangYingShouKuanAction extends DispatchAction {
	/**
	 * ���ݱ���Ӧ�տ��
	 * 
	 * @author Administrator
	 * 
	 */
		Log log = LogFactory.getLog("ProjectBaoYangYingShouKuanAction.class");

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
			request.setAttribute("navigator.location", "Ӧ�տ���� >> ��ѯ");
			HttpSession session=request.getSession();
			ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			List mugStorages = null;
			Session hs = null;
			List grcidlist=new ArrayList();
			try {
				hs = HibernateUtil.getSession();
                grcidlist=Grcnamelist1.getgrcnamelist(hs,userInfo.getUserID());
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
			request.setAttribute("grcidlist",grcidlist);
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
			ServeTableForm tableForm = (ServeTableForm) form;
			HttpSession session = request.getSession();
			// �����ǲ��ǴӲ�ѯ��������excel,����ǵĻ�,flag�е�ֵӦ��ΪY
			String flag = tableForm.getProperty("genReport");
			//��ͬ��
			String contractid = tableForm.getProperty("contractid");
			if (contractid == null || "".equals(contractid)) {
				contractid = "%";
			} else {
				contractid = "%"+contractid.trim()+"%";
			}
			session.setAttribute("contractid", contractid);
			// Ӧ�տʼ����
			String startdate = tableForm.getProperty("startdate");
			if (startdate == null || "".equals(startdate)) {
				startdate = "0000-00-00";
			} else {
				startdate = startdate.trim();
			}
			// Ӧ�տ��������
			String enddate = tableForm.getProperty("enddate");
			if (enddate == null || "".equals(enddate)) {
				enddate = "9999-99-99";
			} else {
				enddate = enddate.trim();
			}
			// �׷���λ
			String custname = tableForm.getProperty("custname");
			if (custname == null || "".equals(custname)) {
				custname = "%";
			} else {
				custname = "%"+custname.trim()+"%";
			}
			
			//����ά���ֲ�
			String genReport=tableForm.getProperty("genReport");
			String grcid22=tableForm.getProperty("grcid22");
			String grcid = tableForm.getProperty("grcid");
			if (grcid == null || "".equals(grcid)) {
				if(genReport!= null && (genReport.equals("Y") || genReport.equals("C") || "Y".equals(flag))){
					grcid=grcid22;
				}else{
				grcid = "";
				}
			} else {
				grcid = grcid.trim();
			}
			List tempList = new ArrayList();
			float _nowfeeprice = 0;
			float _nonowfeeprice = 0;
			float _countpremoneyprice = 0;
			Connection conn = null;
			String sql = null;
			HashMap map = new HashMap();

			try {
				conn = HibernateUtil.getSession().connection();
				if (startdate=="0000-00-00") {
					map.put("startdate", "");
				}else{
					map.put("startdate", startdate);
				}
				if (enddate=="9999-99-99") {
					map.put("enddate", "");
					map.put("logicmatch", "N");
				}else{
					map.put("logicmatch", "Y");	
					map.put("enddate", enddate);
				}

				sql = "exec SP_ENG_ARFEE_STATUS_QUERY '" + contractid + "','"
				+ custname +"','"+startdate+"','"+enddate+"','"+grcid+"'" ;

				////System.out.println(sql);
				
				DataOperation op = new DataOperation();
				op.setCon(conn);
				tempList = op.getSPList(sql);
				List list = new ArrayList();
				String _newnowfeeprice = null;
				String _newnonowfeeprice = null;
				String _newpremoney = null;
				int xuhao=0;
				for (Iterator it = tempList.iterator(); it.hasNext();) {
					HashMap _map = (HashMap) it.next();
					xuhao++;
					_map.put("xuhao",xuhao);
					
					if ( genReport!= null && (genReport.equals("C")) ) {
						
					}else{
						if (_map.get("nowfee") != null){
							_nowfeeprice += Float.parseFloat((String) _map.get("nowfee"));
							_newnowfeeprice = CommonUtil.formatThousand(_map.get("nowfee").toString(),2);
						}
						if (_map.get("nonowfee") != null){
							_nonowfeeprice += Float.parseFloat((String) _map.get("nonowfee"));
							_newnonowfeeprice = CommonUtil.formatThousand(_map.get("nonowfee").toString(),2);
						}
						if (_map.get("premoney") != null){
							_countpremoneyprice += Float.parseFloat((String) _map.get("premoney"));
						   _newpremoney = CommonUtil.formatThousand(_map.get("premoney").toString(),2);
						}

						_map.put("nowfee",_newnowfeeprice);
						_map.put("nonowfee",_newnonowfeeprice);
						_map.put("premoney",_newpremoney);
						list.add(_map);		
					}
				}
				request.setAttribute("grcid22", grcid);
				request.setAttribute("dateinfo", map);
				request.setAttribute("nowfeeprice", CommonUtil.formatThousand(String.valueOf(_nowfeeprice),2));
				request.setAttribute("nonowfeeprice", CommonUtil.formatThousand(String.valueOf(_nonowfeeprice),2));
				request.setAttribute("countprice", CommonUtil.formatThousand(String.valueOf(_countpremoneyprice),2));
				request.setAttribute("report_list", list);
				request.setAttribute("search", tableForm.getProperties());// ��ѯ����
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				if (conn != null)
					try {
						conn.close();
					} catch (SQLException e) {
						e.printStackTrace();
					}
			}
			ActionForward forward = null;
			session.setAttribute("nowfeeprice", String.valueOf(_nowfeeprice));
			session.setAttribute("nonowfeeprice", String.valueOf(_nonowfeeprice));
			session.setAttribute("totalprice", String.valueOf(_countpremoneyprice));
			if (!"Y".equals(flag)) {
				session.setAttribute("result", tempList);
			}

			if ( genReport!= null && genReport.equals("Y") || "Y".equals(flag)) {
				try {
					response = toExcelRecord(tempList, request, response);
				} catch (Exception e) {
					e.printStackTrace();
				}			
			} else {
				forward = mapping.findForward("returnList");
			}
			return forward;
		}
		
		/**
		 * ����ͬ�ŵ���EXCEL
		 * @param resultList
		 * @param request
		 * @param response
		 * @return
		 * @throws IOException
		 */
		public HttpServletResponse toExcelRecord2(List resultList,
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

			XSSFCellStyle cc = wb.createCellStyle();
			//���ñ����ݵĵ�Ԫ����ʽΪ�ַ�����ʽ
			cc.setDataFormat((short)0x31); //XSSFDataFormat�����ݸ�ʽ �ı���ʽ		
			
//			XSSFCellStyle cc = wb.createCellStyle();
//			cc.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//���Ҿ���
//			cc.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
//			cc.setBorderTop(XSSFCellStyle.BORDER_THIN);
//			cc.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//			cc.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//			cc.setBorderRight(XSSFCellStyle.BORDER_THIN);

			int rowlistLen=resultList.size();	
			try{
				String headstr="���,��ͬ��,վ��,�׷���λ,�˱���ʶ,Ӧ������,Ӧ��ϵ��,Ӧ�ս��,�ѿ�Ʊ���,δ��Ʊ���,��������,������־,����ά���ֲ�";
				String key1str="xuhao,contractid,nodename,custname,liftbackmark,predate,feeamt,premoney,nowfee,nonowfee,payrem,r1,grcname";		
				String[] headName = headstr.split(",");
				int headNameLen = headName.length;//��ͷ����		
				int row0lineNo=0;
				int rowno=0;
				XSSFSheet sheet = wb.createSheet();
				wb.setSheetName(0, "Ӧ�տ�����տ�ϵ������");					
						
				/*�����һ�б�ͷ��ʼ*/
				XSSFRow row0 = sheet.createRow( rowno); // ������һ��
				XSSFCell cell0=null;
				for (int i = 0; i < headNameLen; i++) {				
					cell0 = row0.createCell((short) row0lineNo);
					cell0.setCellValue(headName[i]);
					cell0.setCellStyle(cs);					
					row0lineNo++;
				}
				rowno+=1;
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
							if("premoney".equals(key1Name[m]) || "nowfee".equals(key1Name[m]) || "nonowfee".equals(key1Name[m])){
								cell0.setCellValue(Double.valueOf(rowMap.get(key1Name[m])+""));
							}else if ("liftbackmark".equals(key1Name[m])){
								if("B".equals(rowMap.get(key1Name[m])+"")){
									cell0.setCellValue("��");
								}else{
									cell0.setCellValue("��");
								}
							}else if ("r1".equals(key1Name[m])){
								if("S".equals(rowMap.get(key1Name[m])+"")){
									cell0.setCellValue("��");
								}else{
									cell0.setCellValue("��");
								}
							}else {
								cell0.setCellValue(rowMap.get(key1Name[m]) + "");
							}
							cell0.setCellStyle(cc);
							rownlineNo++;
						}
						rowno++;
					}
					/*�������β��ʼ*/
//					HttpSession session = request.getSession();
//					String footstr="ͳ���ܼ�¼��:"+rowlistLen+"��,�տ����ܼ�Ϊ:"+CommonUtil.formatThousand(session.getAttribute("totalprice").toString(), 2)+"��Ԫ��,�ѿ�Ʊ����ܼ�Ϊ��"+CommonUtil.formatThousand(session.getAttribute("nowfeeprice").toString(), 2)+"��Ԫ��,δ��Ʊ����ܼ�Ϊ��"+CommonUtil.formatThousand(session.getAttribute("nonowfeeprice").toString(), 2)+"��Ԫ��";
//					XSSFRow rowo = sheet.createRow( (rowno)); // �������һ��			
//						cell0 = rowo.createCell(7,5);
//						cell0.setCellValue(footstr);
//						cell0.setCellStyle(cs);					
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-disposition", "offline; filename="
					+ URLEncoder.encode("Ӧ�տ�����տ�ϵ������", "utf-8") + ".xls");
			wb.write(response.getOutputStream());			
			return response;	
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
		//���ñ����ݵĵ�Ԫ����ʽΪ�ַ�����ʽ
		cs.setDataFormat((short)0x31); //XSSFDataFormat�����ݸ�ʽ �ı���ʽ
		
//		XSSFCellStyle cc = wb.createCellStyle();
//		cc.setAlignment(XSSFCellStyle.ALIGN_RIGHT);//���Ҿ���
//		cc.setVerticalAlignment(XSSFCellStyle.VERTICAL_CENTER);//���¾���
//		cc.setBorderTop(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderBottom(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderLeft(XSSFCellStyle.BORDER_THIN);
//		cc.setBorderRight(XSSFCellStyle.BORDER_THIN);

		int rowlistLen=resultList.size();	
		try{
			String headstr="��ͬ��,�׷���λ,Ӧ������,Ӧ�ս��,�ѿ�Ʊ���,δ��Ʊ���,����ά���ֲ�";
			String key1str="contractid,custname,predate,premoney,nowfee,nonowfee,grcname";		
			String[] headName = headstr.split(",");
			int headNameLen = headName.length;//��ͷ����		
			int row0lineNo=0;
			int rowno=0;
			XSSFSheet sheet = wb.createSheet();
			wb.setSheetName(0, "Ӧ�տ��������");					
					
			/*�����һ�б�ͷ��ʼ*/
			XSSFRow row0 = sheet.createRow( rowno); // ������һ��
			XSSFCell cell0=null;
			for (int i = 0; i < headNameLen; i++) {				
				cell0 = row0.createCell((short) row0lineNo);
				cell0.setCellValue(headName[i]);
				cell0.setCellStyle(cs);					
				row0lineNo++;
			}
			rowno+=1;
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
						if("premoney".equals(key1Name[m]) || "nowfee".equals(key1Name[m]) || "nonowfee".equals(key1Name[m])){
							cell0.setCellValue(rowMap.get(key1Name[m]) + "");
							cell0.setCellStyle(cs);						
						}else {
							cell0.setCellValue(rowMap.get(key1Name[m]) + "");
							cell0.setCellStyle(cs);
						}
						rownlineNo++;
					}
					rowno++;
				}
				/*�������β��ʼ*/
				HttpSession session = request.getSession();
				String footstr="ͳ���ܼ�¼��:"+rowlistLen+"��,�տ����ܼ�Ϊ:"+CommonUtil.formatThousand(session.getAttribute("totalprice").toString(), 2)+"��Ԫ��,�ѿ�Ʊ����ܼ�Ϊ��"+CommonUtil.formatThousand(session.getAttribute("nowfeeprice").toString(), 2)+"��Ԫ��,δ��Ʊ����ܼ�Ϊ��"+CommonUtil.formatThousand(session.getAttribute("nonowfeeprice").toString(), 2)+"��Ԫ��";
				int num=rowno+1;
				sheet.addMergedRegion(new CellRangeAddress(rowno,num,0,key1NameLen-1));
				XSSFRow rowo = sheet.createRow( rowno); // �������һ��					
				XSSFCellStyle cs1=wb.createCellStyle();
				   cs1.setWrapText(true);
				   cell0 = rowo.createCell(0);
				   cell0.setCellValue(footstr);					
				   cell0.setCellStyle(cs1);					
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="
				+ URLEncoder.encode("Ӧ�տ����", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());			
		return response;	
		}
		
		
		
	}