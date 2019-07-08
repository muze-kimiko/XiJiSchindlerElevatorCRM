package com.gzunicorn.struts.action.maintcontractreport;


import java.io.IOException;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

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
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.sysmanager.pulldown.Pulldown;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * ���̱�������=>����̨�������㱨��
 * @author Lijun
 *
 */
public class BytlReportAction extends DispatchAction {

	Log log = LogFactory.getLog(BytlReportAction.class);

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
		SysRightsUtil.filterModuleRight(request, response,SysRightsUtil.NODE_ID_FORWARD + "bytlreport", null);
		/** **********�����û�Ȩ�޹���*********** */

		// Set default method is toSearchRecord
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
	 * Get the navigation description from the properties file by navigation
	 * key;
	 * 
	 * @param request
	 * @param navigation
	 */
	
	private void setNavigation(HttpServletRequest request, String navigation) {
		Locale locale = this.getLocale(request);
		MessageResources messages = getResources(request);
		request.setAttribute("navigator.location",messages.getMessage(locale, navigation));
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

		request.setAttribute("navigator.location", " ����̨�������� >> ��ѯ");
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
			//ά��վ������
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
			
			//String day=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
			//String day1=DateUtil.getDate(day, "MM", -1);//��ǰ�����·ݼ�1 ��
			//dform.set("sdate1", day1);
			//dform.set("edate1", day);
			
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

		forward = mapping.findForward("bytlReportSearch");		
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

		request.setAttribute("navigator.location", " ����̨�������� >> ��ѯ���");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

		String maintdivision = request.getParameter("maintdivision");
		String maintstation = request.getParameter("maintstation");
		String maintcontractno = request.getParameter("maintcontractno");
		String salescontractno = request.getParameter("salescontractno");
		String elevatorno = request.getParameter("elevatorno");
		String maintpersonnel = request.getParameter("maintpersonnel");
		/**
		String sdate1 = request.getParameter("sdate1");
		if(sdate1==null || sdate1.trim().equals("")){
			sdate1="0000-00-00";
		}
		String edate1 = request.getParameter("edate1");
		if(edate1==null || edate1.trim().equals("")){
			edate1="9999-99-99";
		}
		 */
		HashMap rmap= new HashMap();			
		rmap.put("maintdivision", maintdivision);
		rmap.put("maintstation", maintstation);
		rmap.put("maintcontractno", maintcontractno);
		rmap.put("salescontractno", salescontractno);
		rmap.put("elevatorno", elevatorno);
		rmap.put("maintpersonnel", maintpersonnel);
		//rmap.put("sdate1", sdate1);
		//rmap.put("edate1", edate1);
		request.setAttribute("rmap", rmap);
		
		Session hs = null;
		ResultSet rs=null;
		try {
			hs = HibernateUtil.getSession();

			String hql="exec sp_bytlReport '"+maintdivision.trim()+"','"+maintstation.trim()+"',"
					+ "'"+maintcontractno.trim()+"','"+salescontractno.trim()+"','"+elevatorno.trim()+"',"
					+ "'"+maintpersonnel.trim()+"'";
			System.out.println("����̨�������㱨��>>>>"+hql);
			rs=hs.connection().prepareCall(hql).executeQuery();

            List reportList=new ArrayList();
            double totalmoney=0;
            while(rs.next()){
            	HashMap map= new HashMap();
            	
            	String elevatortype=rs.getString("elevatortype");
            	map.put("billno", rs.getString("billno"));//ά����ˮ��
            	map.put("maintdivision", rs.getString("maintdivision"));//�����ֲ��Ŵ���    
            	map.put("comname", rs.getString("comname"));//�����ֲ���    
            	map.put("maintstation", rs.getString("maintstation"));//����ά��վ����    
            	map.put("storagename", rs.getString("storagename"));//����ά��վ    
            	map.put("maintcontractno", rs.getString("maintcontractno"));//ά����ͬ��
            	map.put("salescontractno", rs.getString("salescontractno"));//���ۺ�ͬ��
            	map.put("elevatorno", rs.getString("elevatorno"));//���ݱ��,
            	map.put("elevatortype", elevatortype);//��������,
            	map.put("elevatortypename", rs.getString("elevatortypename"));//��������,
            	map.put("floorstage", rs.getString("floor")+"/"+rs.getString("stage")+"/"+rs.getString("door"));//��վ��
            	map.put("elevatornature", rs.getString("elevatornature"));//�������ʴ���
            	map.put("elevatornaturename", rs.getString("elevatornaturename"));//������������
            	map.put("elevatornaturexs", rs.getString("elevatornaturexs"));//��������ϵ��
            	map.put("nummoney", rs.getDouble("nummoney"));//̨����,
            	map.put("maintpersonnel", rs.getString("maintpersonnel"));//ά����  
            	map.put("maintpersonnelname", rs.getString("maintpersonnelname"));//ά��������
            	map.put("idcardno", rs.getString("idCardNo"));//���֤
            	//map.put("tasksubdate", rs.getString("tasksubdate"));//�´�����

            	if(elevatortype!=null && elevatortype.trim().equals("T")){
                	map.put("floorstagexs", rs.getString("floorstagexs"));//--��վϵ��,
                	map.put("seriesid", "");//��������
                	map.put("seriesidname", "");//������������
                	map.put("seriesidxs", "");//����ϵ��,
            	}else{
                	map.put("floorstagexs", "");//--��վϵ��,
                	map.put("seriesid", rs.getString("seriesid"));//��������
                	map.put("seriesidname", rs.getString("seriesidname"));//������������
                	map.put("seriesidxs", rs.getString("seriesidxs"));//����ϵ��,
            	}
            	map.put("weight", rs.getString("weight"));//����,
            	map.put("weightxs", rs.getString("weightxs"));//����ϵ��,
            	map.put("tlnum", rs.getString("tlnum"));//̨��,
            	map.put("tlxs", rs.getString("tlxs"));//̨��ϵ��,
            	map.put("transfercomplete", rs.getString("transfercomplete"));//�����Ƿ����,
            	map.put("r4", rs.getString("r4"));//�Ƿ�̨����,

            	double nummoney=rs.getDouble("nummoney");
            	totalmoney=totalmoney+nummoney;
            	
            	reportList.add(map);
            }
            
            HashMap hmap=new HashMap();
            hmap.put("totalmoney", df.format(totalmoney));

            List ElevatorNatureList=bd.getPullDownList("MaintContractDetail_ElevatorNature");
            request.setAttribute("ElevatorNatureList",ElevatorNatureList);
            request.setAttribute("colnum", 14+ElevatorNatureList.size());
            
            if (dform.get("genReport") != null && dform.get("genReport").equals("Y")) {
            	//����excel
    			response = toExcelRecord(response,reportList,ElevatorNatureList,hmap);
    			forward = mapping.findForward("exportExcel");
    		}else{
                if(reportList.size()>0 && reportList.size()<3001){
                	request.setAttribute("maintenanceBytlReportList", reportList);
                }else if(reportList.size()>3000){
                	request.setAttribute("showinfostr", "��ѯ���ݳ�����ǧ�м�¼���뵼��EXCEL�鿴��");
                }else{
                	request.setAttribute("showinfostr", "û�м�¼��ʾ��");
                }
                request.setAttribute("totalhmap", hmap);
                
    			forward = mapping.findForward("bytlReportList");	
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
			List ReportList,List ElevatorNatureList,HashMap hmap) throws IOException {

		String[] titlename={"ά���ֲ�","ά��վ","ά����ͬ��","���ۺ�ͬ��","���ݱ��","��������","��վ","��վϵ��","��������","����ϵ��","����","����ϵ��","�Ƿ�̨����"};
		String[] titleid={"comname","storagename","maintcontractno","salescontractno","elevatorno","elevatortypename","floorstage","floorstagexs","seriesidname","seriesidxs","weight","weightxs","r4"};
		
		XSSFWorkbook wb = new XSSFWorkbook();
        XSSFSheet sheet = wb.createSheet("����̨��������");
        
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
        int ttl=titlename.length;
		for(int i=0;i<ttl;i++){
			cell0 = row0.createCell((short)i);
			//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell0.setCellValue(titlename[i]);
			cell0.setCellStyle(cs);
		}
		int enl=ElevatorNatureList.size();
		for(int e=0;e<enl;e++){
			Pulldown p=(Pulldown)ElevatorNatureList.get(e);//��������
			cell0 = row0.createCell((short)ttl+e);
			//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
			cell0.setCellValue(p.getPullname());
			cell0.setCellStyle(cs);
		}
		
		cell0 = row0.createCell((short)ttl+enl);
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0.setCellValue("̨��");
		cell0.setCellStyle(cs);
		cell0 = row0.createCell((short)ttl+enl+1);
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0.setCellValue("̨��ϵ��");
		cell0.setCellStyle(cs);
		
		cell0 = row0.createCell((short)ttl+enl+2);
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0.setCellValue("̨����(Ԫ)");
		cell0.setCellStyle(cs);
		
		cell0 = row0.createCell((short)ttl+enl+3);
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0.setCellValue("ά�������֤��");
		cell0.setCellStyle(cs);
		cell0 = row0.createCell((short)ttl+enl+4);
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0.setCellValue("ά����");
		cell0.setCellStyle(cs);
		
		cell0 = row0.createCell((short)ttl+enl+5);
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0.setCellValue("�����Ƿ����");
		cell0.setCellStyle(cs);
		/**
		cell0 = row0.createCell((short)ttl+enl+2);
		//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
		cell0.setCellValue("�´�����");
		cell0.setCellStyle(cs);
		*/
		//��������
		if (ReportList != null && ReportList.size()>0) {
			XSSFRow row = null;
			XSSFCell cell =null;
			toolnum=toolnum+ReportList.size();
			for (int j = 0; j < ReportList.size(); j++) {
				HashMap map=(HashMap) ReportList.get(j);
				// ����Excel�У���0�п�ʼ
				row = sheet.createRow(j+1);
				int ttl2=titleid.length;
				for(int c=0;c<ttl2;c++){
				    cell = row.createCell((short)c);
					//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				    cell.setCellValue((String)map.get(titleid[c]));
				}
				String elevatornature=(String)map.get("elevatornature");
				for(int e=0;e<enl;e++){
					Pulldown p=(Pulldown)ElevatorNatureList.get(e);//��������
					String pullid=p.getId().getPullid();
					cell0 = row.createCell((short)ttl2+e);
					//cell0.setEncoding(HSSFCell.ENCODING_UTF_16);
					if(pullid.equals(elevatornature)){
						cell0.setCellValue((String)map.get("elevatornaturexs"));
					}else{
						cell0.setCellValue("");
					}
				}
				
				cell = row.createCell((short)ttl2+enl);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue((String)map.get("tlnum"));
				cell = row.createCell((short)ttl2+enl+1);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue((String)map.get("tlxs"));
				
				cell = row.createCell((short)ttl2+enl+2);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue((Double)map.get("nummoney"));
				cell = row.createCell((short)ttl2+enl+3);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue((String)map.get("idcardno"));
				
				cell = row.createCell((short)ttl2+enl+4);
				cell.setCellValue((String)map.get("maintpersonnelname"));
				
				cell = row.createCell((short)ttl2+enl+5);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue((String)map.get("transfercomplete"));
				/**
				cell = row.createCell((short)ttl2+enl+2);
				//cell.setEncoding(HSSFCell.ENCODING_UTF_16);
				cell.setCellValue((String)map.get("tasksubdate"));
				*/
				
			}
		}
		//��������ʼ�кţ���ֹ�кţ� ��ʼ�кţ���ֹ�к�
		//sheet.addMergedRegion(new CellRangeAddress(toolnum, toolnum, 0, 8));
		XSSFRow row1 = sheet.createRow(toolnum);
		XSSFCell cell1 = row1.createCell((short)0);
		cell1.setCellValue("̨�����ϼ�(Ԫ):"+(String)hmap.get("totalmoney"));
		
		response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
		response.setHeader("Content-disposition", "offline; filename="+URLEncoder.encode("����̨��������", "utf-8") + ".xlsx");
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
