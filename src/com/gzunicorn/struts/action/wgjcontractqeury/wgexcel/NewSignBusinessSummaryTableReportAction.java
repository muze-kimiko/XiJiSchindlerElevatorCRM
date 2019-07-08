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

import org.apache.poi.hssf.util.Region;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
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
 * ��ǩҵ����ܱ�
 * 
 * @author Administrator
 * 
 */
public class NewSignBusinessSummaryTableReportAction extends DispatchAction {

	Log log = LogFactory
			.getLog("NewSignBusinessSummaryTableReportAction.class");
	BaseDataImpl bd = new BaseDataImpl();

	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		/************ ��ʼ�û�Ȩ�޹��� ************/
		SysRightsUtil.filterModuleRight(request, response,
				SysRightsUtil.NODE_ID_FORWARD + "newsignbusinesssummarytable",
				null);
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
		request.setAttribute("navigator.location", "ǩ��ҵ����ܱ� >> ��ѯ");
//		List mugStorages = new ArrayList();
		Session hs = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = null;
		try {
			userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
			hs = HibernateUtil.getSession();
//			mugStorages = Grcnamelist1.getStorageName(hs,userInfo.getUserID());
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
//		request.setAttribute("mugStorages", mugStorages);
		return mapping.findForward("toCondition");
	}

	public ActionForward toSearchRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {

		ServeTableForm tableForm = (ServeTableForm) form;
		// HttpSession session = request.getSession();
		HashMap conditionmap = new HashMap();
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = null;

		// ��ȡǰ̨��ѯ����
		// String contractid = (String)
		// tableForm.getProperty("contractid");//��ͬ���
		String lotdates = (String) tableForm.getProperty("lotdates");// ǩ�����ڣ�ʼ��
		String lotdatee = (String) tableForm.getProperty("lotdatee");// ǩ�����ڣ��գ�
		// String custname = (String) tableForm.getProperty("custname");//
		// �׷���λ����
		String username = (String) tableForm.getProperty("username");// ҵ��Ա����
		// String nodeid = (String) tableForm.getProperty("nodeid");// ����Դ
		String grcid = (String) tableForm.getProperty("grcid");// ����ά���ֲ�����
		// ����ѯ���������map�У����ڲ鿴ҳ��ĵ���excel����ʱ��ѯ����
		// conditionmap.put("contractid", contractid);
		conditionmap.put("lotdates", lotdates);
		conditionmap.put("lotdatee", lotdatee);
		// conditionmap.put("custname", custname);
		conditionmap.put("username", username);
		// conditionmap.put("nodeid", nodeid);
		conditionmap.put("grcid", grcid);
		conditionmap.put("timerange", "N");
		if ((null != lotdates && !"".equals(lotdates.trim()))
				|| (null != lotdatee && !"".equals(lotdatee.trim()))) {
			conditionmap.put("timerange", "Y");
		}
		request.setAttribute("conditionmap", conditionmap);

		// if (contractid == null || "".equals(contractid.trim())) {
		// contractid = "%";
		// } else {
		// contractid = "%"+contractid.trim()+"%";
		// }
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
		// if (custname == null || "".equals(custname.trim())) {
		// custname = "%";
		// } else {
		// custname = "%"+custname.trim()+"%";
		// }
		if (username == null || "".equals(username.trim())) {
			username = "%";
		} else {
			username = "%" + username.trim() + "%";
		}
		// if (nodeid == null || "".equals(nodeid.trim())) {
		// nodeid = "%";
		// } else {
		// nodeid = nodeid.trim();
		// }
		if (grcid == null || "".equals(grcid.trim())) {
			grcid = "%";
		} else {
			grcid = grcid.trim();
		}

		List tempList = new ArrayList();
		HashMap map = null;
		Session hs = null;
		String sql = "";
		// double nowfee = 0;
		int count = 0;
		try {
			hs = HibernateUtil.getSession();
			sql = "EXEC SP_NEW_SIGN_BUSINESS_SUMMARY_TABLE '" + lotdates
					+ "','" + lotdatee + "','" + username +"','"+grcid+"'" ;

			DebugUtil.println(sql);
			ResultSet rs = hs.connection().createStatement().executeQuery(sql);
			while (rs.next()) {
				count++;
				map = new HashMap();
				map.put("xuhao", count);
				map.put("operationid", rs.getString("operationid"));// ҵ��Ա
				map.put("username", rs.getString("username"));// ҵ��Ա����
				map.put("wnum", rs.getString("wnum"));// ά��̨��
				map.put("wamt", rs.getString("wamt"));// ά�޽��
				map.put("gnum", rs.getString("gnum"));// ����̨��
				map.put("gamt", rs.getString("gamt"));// ������
				map.put("jnum", rs.getString("jnum"));// ��װ̨��
				map.put("jamt", rs.getString("jamt"));// ��װ���
				map.put("bnum", rs.getString("bnum"));// ����̨��
				map.put("bamt", rs.getString("bamt"));// �������
				map.put("grcname", rs.getString("grcname"));//����ά���ֲ�
				tempList.add(map);
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
				response = toExcelRecord(tempList, request, response);
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			request.setAttribute("count", count);// ��ѯ�����
			request.setAttribute("resultList", tempList);// ��ѯ�����
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
	public HttpServletResponse toExcelRecord(List resultList,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		HSSFWorkbook wb = new HSSFWorkbook();

		// ���ñ�ͷ�Ĺ��õ�Ԫ����ʽ
		HSSFCellStyle cs = wb.createCellStyle();
		cs.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���Ҿ���
		cs.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ���¾���
		HSSFFont f = wb.createFont();
		f.setFontHeightInPoints((short) 11);// �ֺ�
		f.setBoldweight(HSSFFont.BOLDWEIGHT_NORMAL);// �Ӵ�
		cs.setFont(f);
		cs.setBorderTop(HSSFCellStyle.BORDER_THIN);// �����ϱ߿���ʾ
		cs.setBorderBottom(HSSFCellStyle.BORDER_THIN);// �����±߿���ʾ
		cs.setBorderLeft(HSSFCellStyle.BORDER_THIN);// ������߿���ʾ
		cs.setBorderRight(HSSFCellStyle.BORDER_THIN);// �����ұ߿���ʾ

		// ���ñ����ݵĵ�Ԫ����ʽ
		HSSFCellStyle cc = wb.createCellStyle();
		cc.setDataFormat((short) 0x31);// HSSFDataFormat�����ݸ�ʽ �ı���ʽ
		cc.setAlignment(HSSFCellStyle.ALIGN_CENTER);// ���Ҿ���
		cc.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// ���¾���
		// cc.setBorderTop(HSSFCellStyle.BORDER_THIN);
		// cc.setBorderBottom(HSSFCellStyle.BORDER_THIN);
		// cc.setBorderLeft(HSSFCellStyle.BORDER_THIN);
		// cc.setBorderRight(HSSFCellStyle.BORDER_THIN);

		int rowlistLen = resultList.size();
		try {
			String headstr = "���,ҵ��Ա,����ά���ֲ�,��ǩ��ά�޺�ͬ,'',��ǩ�������ͬ,'',��ǩ����װ��ͬ,'',��ǩ��������ͬ,''";
			String head1str = "'','','',����,���,����,���,����,���,����,���";
			String key1str = "xuhao,username,grcname,wnum,wamt,gnum,gamt,jnum,jamt,bnum,bamt";

			int rowno = 0;
			HSSFSheet sheet = wb.createSheet();
			wb.setSheetName(0, "ά����ǩҵ����ܱ�");

			/* �����һ�б�ͷ��ʼ */
			HSSFRow row0 = sheet.createRow( rowno); // ������һ��
			HSSFCell cell0 = null;
			String[] headName = headstr.split(",");
			int headNameLen = headName.length;
			int colcount = 0;
			for (int i = 0; i < headNameLen; i++) {
				cell0 = row0.createCell((short) i);
				String headname2 = headName[i];
				cell0.setCellValue(headname2);
				cell0.setCellStyle(cs);

				if (headname2.equals("���")) {
					// �ĸ������������˼�ǣ� �ֱ������ʼ�С���ʼ�С������С������� (����)
					sheet.addMergedRegion(new Region((short) 0, (short) 0,
							(short) 1, (short) 0));
				} else if (headname2.equals("ҵ��Ա")) {
					// �ĸ������������˼�ǣ� �ֱ������ʼ�С���ʼ�С������С������� (����)
					sheet.addMergedRegion(new Region((short) 0, (short) 1,
							(short) 1, (short) 1));
				}else if (headname2.equals("����ά���ֲ�")) {
					// �ĸ������������˼�ǣ� �ֱ������ʼ�С���ʼ�С������С������� (����)
					sheet.addMergedRegion(new Region((short) 0, (short) 2,
							(short) 1, (short) 2));
				} else if (headname2.indexOf("��ǩ��") > -1) {
					// �ĸ������������˼�ǣ� �ֱ������ʼ�С���ʼ�С������С������� (����)
					sheet.addMergedRegion(new Region((short) 0,
							(short) colcount, (short) 0, (short) (colcount + 1)));
				}
				colcount++;
			}
			rowno++;

			/* ����ڶ��б�ͷ��ʼ */
			String[] head1Name = head1str.split(",");
			int head1NameLen = head1Name.length;
			HSSFRow row1 = sheet.createRow( rowno); // �����ڶ���
			HSSFCell cell1 = null;
			for (int i = 0; i < head1NameLen; i++) {
				cell1 = row1.createCell((short) i);
				cell1.setCellValue(head1Name[i]);
				cell1.setCellStyle(cs);
			}
			rowno++;

			// �����Ӧ��ͷ����ֵ
			String[] key1Name = key1str.split(",");
			int key1NameLen = key1Name.length;
			HashMap rowMap = null;
			HSSFRow row2 = null;
			HSSFCell cell2 = null;
			int rownlineNo = 0;
			if (rowlistLen > 0) {
				for (int k = 0; k < rowlistLen; k++) {
					rownlineNo = 0;
					row2 = sheet.createRow( rowno);
					rowMap = (HashMap) resultList.get(k);
					for (int m = 0; m < key1NameLen; m++) {
						cell2 = row2.createCell((short) rownlineNo);
						if (rowMap.get(key1Name[m]) != null) {
							if ("xuhao".equals(key1Name[m])
									|| "username".equals(key1Name[m]) 
									|| "grcname".equals(key1Name[m])) {
								cell2.setCellValue(rowMap.get(key1Name[m]) + "");
								cell2.setCellStyle(cc);
							} else {
								cell2.setCellValue(Double.valueOf(rowMap
										.get(key1Name[m]) + ""));
							}
						}
						rownlineNo++;
					}
					rowno++;
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="
				+ URLEncoder.encode("��ǩҵ����ܱ�", "utf-8") + ".xlsx");
		wb.write(response.getOutputStream());
		return response;
	}

}
