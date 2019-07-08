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
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFColor;
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
 * �տ��ѯ��
 * @author Administrator
 *
 */
public class CollectionInquiresTableAction extends DispatchAction {

	Log log = LogFactory.getLog("CollectionInquiresTableAction.class");
	BaseDataImpl bd = new BaseDataImpl();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		/************��ʼ�û�Ȩ�޹���************/
		SysRightsUtil.filterModuleRight(request,response,SysRightsUtil.NODE_ID_FORWARD + "collectioninquirestable",null);
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
		request.setAttribute("navigator.location", "�տ��ѯ�� >> ��ѯ");
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
		String predates = (String) tableForm.getProperty("predates");// Ӧ�տ����ڣ�ʼ��
		String predatee = (String) tableForm.getProperty("predatee");// Ӧ�տ����ڣ��գ�
		String custname = (String) tableForm.getProperty("custname");// �׷���λ
		String grcid = (String) tableForm.getProperty("grcid");// ����ά���ֲ�����
		//String contracttype = (String) tableForm.getProperty("contracttype");// ��ͬ����
		
		//����ѯ���������map�У����ڲ鿴ҳ��ĵ���excel����ʱ��ѯ����
		conditionmap.put("contractid", contractid);
		conditionmap.put("predates", predates);
		conditionmap.put("predatee", predatee);
		conditionmap.put("custname", custname);
		conditionmap.put("grcid", grcid);

		request.setAttribute("conditionmap", conditionmap);
		
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
		int count = 0;
		Double premoney=0.0;//Ӧ�ս��
		Double nowfee=0.0;//�ѿ�Ʊ���
		Double nonowfee=0.0;//δ��Ʊ���
		Double billatm=0.0;//�ѿ�Ʊ���տ���
		Double billnoatm=0.0;//�ѿ�Ʊ����Ƿ����
		Double nobillatm=0.0;//δ��Ʊ����Ƿ����
		Double nobillnoatm=0.0;//�ѿ�Ʊ��Ƿ����
		try {
			hs = HibernateUtil.getSession();
			sql = "EXEC SP_COLLECTION_INQUIRES_TABLE '"+contractid+"','"+predates+"','"+predatee+"','"
				+custname+"','"+grcid+"'" ;
			
			DebugUtil.println(sql);
			ResultSet rs = hs.connection().createStatement().executeQuery(sql);
			while (rs.next()) {
				count++;
				map = new HashMap();
				

				map.put("xuhao", count);
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
				
				tempList.add(map);
			}
			DecimalFormat df=new DecimalFormat("#,##0.00");
			String sj="ͳ�ƣ���¼��"+count+"��, "
					+ "Ӧ�տ����ܼ�Ϊ��"+df.format(premoney)+"��Ԫ��,"
					+ "�ѿ�Ʊ����ܼ�Ϊ��"+df.format(nowfee)+"��Ԫ), "
					+ "δ��Ʊ����ܼ�Ϊ��"+df.format(nonowfee)+"��Ԫ��, "
					+ "�ѿ�Ʊ���տ����ܼ�Ϊ��"+df.format(billatm)+"��Ԫ��, "
					+ "�ѿ�Ʊ����Ƿ�����ܼ�Ϊ��"+df.format(billnoatm)+"��Ԫ��, "
			        + "δ��Ʊ����Ƿ�����ܼ�Ϊ��"+df.format(nobillatm)+"��Ԫ��, "
			        + "�ѿ�Ʊ��Ƿ�����ܼ�Ϊ��"+df.format(nobillnoatm)+"��Ԫ��. ";
			request.setAttribute("sj", sj);
		} catch (DataStoreException e) {
			e.printStackTrace();
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
			Double premoney=0.0;//Ӧ�ս��
			Double nowfee=0.0;//�ѿ�Ʊ���
			Double nonowfee=0.0;//δ��Ʊ���
			Double billatm=0.0;//�ѿ�Ʊ���տ���
			Double billnoatm=0.0;//�ѿ�Ʊ����Ƿ����
			Double nobillatm=0.0;//δ��Ʊ����Ƿ����
			Double nobillnoatm=0.0;//�ѿ�Ʊ��Ƿ����
			XSSFSheet sheet = wb.createSheet();	
			wb.setSheetName(0,"ά���տ��ѯ��");
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
							if("premoney".equals(key1Name[j])){
								premoney+=Double.valueOf(value).doubleValue();
							}else if("nowfee".equals(key1Name[j])){
								nowfee+=Double.valueOf(value).doubleValue();
							}else if("nonowfee".equals(key1Name[j])){
								nonowfee+=Double.valueOf(value).doubleValue();
							}else if("billatm".equals(key1Name[j])){
								billatm+=Double.valueOf(value).doubleValue();
							}else if("billnoatm".equals(key1Name[j])){
								billnoatm+=Double.valueOf(value).doubleValue();
							}else if("nobillatm".equals(key1Name[j])){
								nobillatm+=Double.valueOf(value).doubleValue();
							}else if("nobillnoatm".equals(key1Name[j])){
								nobillnoatm+=Double.valueOf(value).doubleValue();
							}
						}
						cell0.setCellStyle(cs);
					}
				}	
			}
			//���β��
			DecimalFormat df=new DecimalFormat("#,##0.00");
			XSSFCellStyle cellstyle=wb.createCellStyle();
			cellstyle.setWrapText(true);
			sheet.addMergedRegion(new CellRangeAddress(rowno,rowno+4,0,headNameLen-1));
			row0=sheet.createRow(rowno);
			cell0=row0.createCell(0);
			String sj="ͳ�ƣ���¼��"+rowlistLen+"��, "
					+ "Ӧ�տ����ܼ�Ϊ��"+df.format(premoney)+"��Ԫ��,"
					+ "�ѿ�Ʊ����ܼ�Ϊ��"+df.format(nowfee)+"��Ԫ), "
					+ "δ��Ʊ����ܼ�Ϊ��"+df.format(nonowfee)+"��Ԫ��, "
					+ "�ѿ�Ʊ���տ����ܼ�Ϊ��"+df.format(billatm)+"��Ԫ��, "
					+ "�ѿ�Ʊ����Ƿ�����ܼ�Ϊ��"+df.format(billnoatm)+"��Ԫ��, "
			        + "δ��Ʊ����Ƿ�����ܼ�Ϊ��"+df.format(nobillatm)+"��Ԫ��, "
			        + "�ѿ�Ʊ��Ƿ�����ܼ�Ϊ��"+df.format(nobillnoatm)+"��Ԫ��. ";
			cell0.setCellValue(sj);
			cell0.setCellStyle(cellstyle);
		}catch(Exception e){
			e.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="
		+ URLEncoder.encode("ά���տ��ѯ��", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());			
		return response;	
		}
		
 }




