package com.gzunicorn.struts.action.maintenanceworkplanmanager;


import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.actions.DispatchAction;
import org.apache.struts.util.MessageResources;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.SysRightsUtil;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.infomanager.elevatorarchivesinfo.ElevatorArchivesInfo;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplandetail.MaintenanceWorkPlanDetail;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplanmaster.MaintenanceWorkPlanMaster;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;


public class MaintenanceReceiptAction extends DispatchAction {

	Log log = LogFactory.getLog(MaintenanceReceiptAction.class);

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
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		String name = request.getParameter("method");

		if(!"getMaintPersonnelOneDateDetail".equals(name)){
			/** **********��ʼ�û�Ȩ�޹���*********** */
			SysRightsUtil.filterModuleRight(request, response,
					SysRightsUtil.NODE_ID_FORWARD + "maintenancereceipt", null);
			/** **********�����û�Ȩ�޹���*********** */
		}

		// Set default method is toSearchRecord
		
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

		request.setAttribute("navigator.location", " ά����ͬ�´�ǩ��>> ��ѯ�б�");
		ActionForward forward = null;
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo) session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;

			String elevatorNo = (String) dform.get("elevatorNo");
			if(elevatorNo==null){
				elevatorNo=request.getParameter("elevatorNo");
				dform.set("elevatorNo", elevatorNo);
			}
			String maintContractNo = (String) dform.get("maintContractNo");
			String projectName = (String) dform.get("projectName");
			String sdate1=(String) dform.get("sdate1");
			String edate1=(String) dform.get("edate1");
			String isAssignedSign = (String) dform.get("isAssignedSign");
			String salesContractNo = (String) dform.get("salesContractNo");
			String mainStation = (String) dform.get("mainStation");
			String maintDivision = (String) dform.get("maintDivision");
			
			if(sdate1==null || sdate1.trim().equals("")){
				edate1=DateUtil.getNowTime("yyyy-MM-dd");//��ǰ����
				//sdate1=DateUtil.getDate(edate1, "MM", -1);//��ǰ�����·ݼ�1 ��
				sdate1="2017-01-01";
				dform.set("sdate1", sdate1);
				dform.set("edate1", edate1);
			}
			
            if(isAssignedSign==null || isAssignedSign.equals(""))
            {
            	isAssignedSign="N";
            	dform.set("isAssignedSign", "N");
            }else if(isAssignedSign.equals("ALL"))
            {
            	isAssignedSign=null;
            }
            
          //�ֲ��������б�
			List maintDivisionList = Grcnamelist1.getgrcnamelist(userInfo.getUserID());
			if (maintDivision == null || maintDivision.trim().equals("")) {
				HashMap hmap=(HashMap)maintDivisionList.get(0);
				maintDivision =(String)hmap.get("grcid");
			}
			request.setAttribute("maintDivisionList", maintDivisionList);
			
            List mslist= bd.getMaintStationList2(userInfo,maintDivision);
			if (mainStation == null || mainStation.trim().equals("")) {
				HashMap hmap=(HashMap)mslist.get(0);
				mainStation =(String)hmap.get("storageid");
			}
			request.setAttribute("mainStationList",mslist);
			
			Session hs = null;
			Query query = null;
			Connection conn=null;
			PreparedStatement ps=null;
			ResultSet rs=null;
			try {
				hs = HibernateUtil.getSession();
				conn=hs.connection();
				
				String[] colNames = {
						"mcm.maintContractNo as maintContractNo",
						"mcd.salesContractNo as salesContractNo",
						"mcd.MainSDate as contractSDate",
						"mcd.MainEDate as contractEDate",
						"mcm.taskSubDate as taskSubDate",
						"si.StorageName as assignedMainStation",
						"mcd.assignedSignFlag as inAssignedSignFlag",
		                "mcd.assignedSignDate as assignedSignDate",
		                "c.UserName as assignedSign",
		                "mcd.returnReason as returnReason",
		                "mcd.elevatorNo as elevatorNo",
		                "mcd.projectName as projectName",
		                "b.UserName as inMaintPersonnel",
		                "mcd.rowid as rowid",
		                "mcm.billNo as billNo",
		                "mwpm.billno as mwpmBillno",
		                "mcd.assignedMainStation as mainStation",
		                "mcd.assignedRem as assignedRem"
				};
				
				
				String hql="select "+StringUtils.join(colNames, ",")+"," +
						"isnull((select md.MaintPersonnel from MaintContractDetail md " +
						"where md.ElevatorNo=mcd.ElevatorNo and md.BillNo=mcm.HistoryBillNo),'') as hismaintpersonnel " +
						"from MaintContractMaster mcm,MaintContractDetail mcd " +
						"left join LoginUser b  on b.UserID=mcd.MaintPersonnel " +
						"left join LoginUser c on c.UserID=mcd.assignedSign " +
						"left join MaintenanceWorkPlanMaster mwpm  on mwpm.rowid=mcd.rowid , StorageID si   " +
						"where mcd.billNo=mcm.billNo and si.StorageID=mcd.assignedMainStation " +
						"and mcd.assignedMainStation is not null " +
						"and (mcm.contractStatus = 'ZB' or mcm.contractStatus='XB')";
				if(elevatorNo!=null&&!elevatorNo.equals(""))
                {
                	hql+=" and mcd.elevatorNo like '%"+elevatorNo.trim()+"%'";
                }
               if(maintContractNo!=null&&!maintContractNo.equals(""))
                {
                	hql+=" and mcm.maintContractNo like '%"+maintContractNo.trim()+"%'";
                }
               
               if(salesContractNo!=null&&!salesContractNo.equals(""))
               {
               	hql+=" and mcd.salesContractNo like '%"+salesContractNo.trim()+"%'";
               }
               
                if(projectName!=null&&!projectName.equals(""))
                {
                	hql+=" and mcd.projectName like '%"+projectName.trim()+"%'";
                }

                if (sdate1 != null && !sdate1.equals("")) {
					hql+=" and mcm.taskSubDate >= '"+sdate1.trim()+" 00:00:00'";
				}
				if (edate1 != null && !edate1.equals("")) {
					hql+=" and mcm.taskSubDate <= '"+edate1.trim()+" 23:59:59'";
				}
                if(isAssignedSign!=null&&!isAssignedSign.equals(""))
                {
                	if(isAssignedSign.equals("Y"))
                	{
                		hql+=" and mcd.assignedSignFlag = 'Y'";
                	}else if(isAssignedSign.equals("R"))
                	{
                		hql+=" and mcd.assignedSignFlag = 'R'";
                	}
                	else
                	{
                		hql+=" and (mcd.assignedSignFlag = 'N' or mcd.assignedSignFlag is null)";
                	}
                }
                if (mainStation != null && !mainStation.equals("")) {
					hql+=" and mcd.assignedMainStation like '"+mainStation.trim()+"'";
				}
                if (maintDivision != null && !maintDivision.equals("")) {
					hql+=" and mcm.maintDivision like '"+maintDivision.trim()+"'";
				}
                
                
                hql+=" order by mcd.assignedSignFlag,mcd.assignedMainStation";
                
                //System.out.println(hql);

                ps=conn.prepareStatement(hql);
                rs=ps.executeQuery();
                int a=0;
                for(int j=0;j<colNames.length;j++)
                {
                	colNames[j]=colNames[j].split(" as ")[1];
                }
                List maintenanceReceiptList=new ArrayList();
                
                
                while(rs.next())
                {
                	HashMap map= new HashMap();
                	for(int j=0;j<colNames.length;j++)
					{
                		map.put(colNames[j],rs.getString(colNames[j]));
					}
                	map.put("hismaintpersonnel", rs.getString("hismaintpersonnel"));
					map.put("assignedSignDate",CommonUtil.getDateFormatStr((String) map.get("assignedSignDate"), null));
					map.put("taskSubDate",CommonUtil.getDateFormatStr((String) map.get("taskSubDate"), null));
					//����ά��վ����ά����
					String sql = "from Loginuser where storageid like '"+(String)map.get("mainStation")+"%' " +
							"and roleid in('A49','A50') and enabledflag='Y'";
					List list =hs.createQuery(sql).list();
					map.put("Loginuserlist", list);
					
                	maintenanceReceiptList.add(map);
                	
                	a++;
                }
				request.setAttribute("maintenanceReceiptList", maintenanceReceiptList);
				request.setAttribute("maintenanceReceiptListSize", a); 	
				
				//ά����
				
				
			} catch (DataStoreException e) {
				e.printStackTrace();
			} catch (Exception e1) {
				e1.printStackTrace();
			} finally {
				try {
					hs.close();
					// HibernateSessionFactory.closeSession();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			forward = mapping.findForward("maintenanceReceiptList");
		
		return forward;
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
		request.setAttribute("navigator.location",
				messages.getMessage(locale, navigation));
	}

	/**
	 * �´�ǩ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ActionErrors errors = new ActionErrors();

		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		String[] maintPersonnels=request.getParameterValues("maintPersonnel");//ȷ�Ϸ���ά����
		String[] assignedSignFlags=request.getParameterValues("assignedSignFlag");//ȷ��ǩ�ձ�־
		String[] returnReasons=request.getParameterValues("returnReason");//ȷ���˻�ԭ��
		String[] isBoxs=request.getParameterValues("isBox");//ȷ��ѡ��ѡ��
		String[] rowids=request.getParameterValues("rowid");//ȷ��ѡ��ѡ��
		String[] elevatorNos=request.getParameterValues("elevator");//��ȡ�ĵ��ݱ��
		String[] mainStationIds=request.getParameterValues("mainStationId");//��ȡ��
		
		Session hs = null;
		Transaction tx = null;
		try{

			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			if(isBoxs.length>0) {
            	for(int i=0;i<isBoxs.length;i++){
            		if(isBoxs[i].equals("Y")){
            			if(assignedSignFlags[i].equals("Y")){       
            				//CommonUtil.toMaintenanceWorkPlan(rowids[i], maintPersonnels[i], userInfo, errors);
            				//toUpdataElevatorArchivesInfo(elevatorNos[i],mainStationIds[i],userInfo,errors);
            				
            				MaintContractDetail mcd = (MaintContractDetail) hs.get(MaintContractDetail.class, Integer.valueOf(rowids[i]));

            				String selsql="select a from MaintenanceWorkPlanMaster a where a.rowid="+mcd.getRowid();
            				List mwmlist=hs.createQuery(selsql).list();
            				if(mwmlist==null || mwmlist.size()==0){
	            				//ά����ҵ�ƻ�������
	            				String year1=CommonUtil.getToday().substring(2,4);
	            				String[] billno=CommonUtil.getBillno(year1, "MaintenanceWorkPlanMaster", 1);
	            				MaintenanceWorkPlanMaster mwpm=new MaintenanceWorkPlanMaster();
	            				mwpm.setRowid(mcd.getRowid());
	            				mwpm.setBillno(billno[0]);
	            				mwpm.setElevatorNo(mcd.getElevatorNo());
	            				mwpm.setMaintLogic("1");
	            				mwpm.setR1(userInfo.getUserID());
	            				mwpm.setR2(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
	            				hs.save(mwpm); 
            				}
            				//ά����ͬ��ϸ
            				if(mcd.getMaintPersonnel()==null || mcd.getMaintPersonnel().equals("")){
	            				mcd.setMaintPersonnel(maintPersonnels[i]);
	        					mcd.setAssignedSignFlag("Y");
	        					mcd.setAssignedSign(userInfo.getUserID());
	        					mcd.setAssignedSignDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
	        					hs.update(mcd);
            				}
            				
            				//�������ݵ� ���ݵ�����Ϣ
//            				String hql="from ElevatorArchivesInfo eaif where eaif.elevatorNo = "+elevatorNos[i];
//            				List list=hs.createQuery(hql).list();
//            				ElevatorArchivesInfo eaif=new ElevatorArchivesInfo();
//            				if(list!=null&&list.size()>0){
//            					eaif=(ElevatorArchivesInfo) list.get(0);
//            					eaif.setMaintStation(mainStationIds[i]);
//            				    hs.update(eaif);
//            				}
            			}else if(assignedSignFlags[i].equals("R")){
            				//toAssignedSignFlagR(rowids[i], returnReasons[i], userInfo,errors);
            				MaintContractDetail mcd = (MaintContractDetail) hs.get(MaintContractDetail.class,Integer.valueOf(rowids[i]));
            				if(mcd!=null){
            					mcd.setAssignedSignFlag("R");
            					mcd.setAssignedSign(userInfo.getUserID());
            					mcd.setAssignedSignDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
            					mcd.setReturnReason(returnReasons[i]);
            					MaintContractMaster mcm=(MaintContractMaster) hs.get(MaintContractMaster.class, mcd.getBillNo());
            					mcm.setTaskSubFlag("R");
            					hs.update(mcd);
            					hs.update(mcm);
            				}
            			}
            		}
            	}	
            }
			tx.commit();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","����ɹ���"));
		} catch (Exception e1) {
			e1.printStackTrace();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>����ʧ�ܣ�</font>"));
			log.error(e1.getMessage());
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		ActionForward forward = null;
		try {
		    if(errors.isEmpty()){
		    	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
		    }
			forward = mapping.findForward("returnList");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
	


	/**
	 * ά���ƻ��鿴����
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toMaintenanceWorkPlanDisplayRecord(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		DynaActionForm dform = (DynaActionForm) form;
		ActionErrors errors = new ActionErrors();
		request.setAttribute("navigator.location", "ά����ҵ�ƻ� >> �鿴");

		ActionForward forward = null;
		String id = (String) dform.get("id");
		Session hs = null;
		MaintenanceWorkPlanMaster mwp = null;
		HashMap map= new HashMap();
		List mwpList = null;
		Object object=null;
		List mwpdList=new ArrayList();
		String maintPersonnel =null;
		if (id != null) {
			try {
				hs = HibernateUtil.getSession();
				String sql="select mcm,mcd,l.username,si.storagename,c.comname,mwpm.maintLogic"
						+ " from MaintenanceWorkPlanMaster mwpm,MaintContractDetail mcd,"
						+ " MaintContractMaster mcm,Loginuser l,Storageid si,Company c "
						+ " where c.comid=maintDivision"
						+ " and si.storageid=mcd.assignedMainStation"
						+ " and l.userid=mcd.maintPersonnel"
						+ " and mwpm.rowid=mcd.rowid"
						+ " and mcd.billNo=mcm.billNo"
						+ " and mwpm.billno= '"+id+"'";
				
				mwpList = hs.createQuery(sql).list();
				if(mwpList!=null&&mwpList.size()>0)
				{
					Object[] objects= (Object[]) mwpList.get(0);
                    map.putAll(BeanUtils.describe(objects[0]));
                    map.putAll(BeanUtils.describe(objects[1]));
                    maintPersonnel=(String) map.get("maintPersonnel");
                    map.put("maintPersonnelName", objects[2]);
                    map.put("assignedMainStation", objects[3]);
                    map.put("maintDivision", objects[4]);
                    map.put("maintLogic", objects[5]);
					object=map;
				}
				
				
				
				String sql1="from MaintenanceWorkPlanDetail mwpd where mwpd.maintenanceWorkPlanMaster.billno='"+id+"'";
				List list=hs.createQuery(sql1).list();
				  if(list!=null&&list.size()>0)
				  {
					  for(int i=0;i<list.size();i++)
					  {
						  MaintenanceWorkPlanDetail mwpd= (MaintenanceWorkPlanDetail) list.get(i);
						  HashMap map1 =new HashMap();
						  map1.put("singleno", mwpd.getSingleno());
						  map1.put("maintDate", mwpd.getMaintDate());
						  map1.put("week", mwpd.getWeek());
						  if(mwpd.getMaintType()!=null&&!mwpd.getMaintType().equals(""))
						  {

								
							  if(mwpd.getMaintType().trim().equals("halfMonth"))
							  {
								  map1.put("maintType", "���±���"); 						  
							  }
							  if(mwpd.getMaintType().trim().endsWith("quarter"))
							  {
								  map1.put("maintType", "���ȱ���");
								  map1.put("style","oddRow3");
							  } 
							  if(mwpd.getMaintType().trim().endsWith("halfYear"))
							  {
								  map1.put("maintType", "���걣��");
								  map1.put("style","oddRow3");
							  }
							  if(mwpd.getMaintType().trim().equals("yearDegree"))					          
						      {
								  map1.put("maintType", "��ȱ���");
								  map1.put("style","oddRow3");
						      }
						  }

						  map1.put("maintDateTime", mwpd.getMaintDateTime());
						  
						  if(mwpd.getMaintEndTime()!=null&&!mwpd.getMaintEndTime().equals(""))
						  {
						  map1.put("maintSETime", mwpd.getMaintStartTime()+"|"+mwpd.getMaintEndTime());
						  }
						  else
						  {
							  map1.put("maintSETime", mwpd.getMaintStartTime()); 
						  }
						  
						  String sql2="select maintDateTime from MaintContractDetail mcd,MaintenanceWorkPlanMaster mwpm," +
						  		"MaintenanceWorkPlanDetail mwpd where mcd.rowid=mwpm.rowid "
						  		+ "and mwpm.billno=mwpd.billno and mcd.MaintPersonnel = '"+maintPersonnel+"' and mwpd.MaintDate ='"+mwpd.getMaintDate()+"'";
						 
						  int sumMaintDateTime=0;
						 List maintDateTimeList=hs.createSQLQuery(sql2).list();
						 if(maintDateTimeList!=null && maintDateTimeList.size()>0)
						 {
							for(int i1=0;i1<maintDateTimeList.size();i1++)
							{
								String  mdt=(String) maintDateTimeList.get(i1);
								sumMaintDateTime+=Integer.valueOf(mdt.trim());
								
							}
						 }
						  
						 map1.put("sumMaintDateTime", sumMaintDateTime);
						  mwpdList.add(map1); 
					  }
				  }
				  
				  
				
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
			
			request.setAttribute("mwpBean",object);
			request.setAttribute("mwpList",mwpdList);
			forward = mapping.findForward("toMaintenanceWorkPlan");

		}

		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}

	/**
	 * 
	 * @param hs
	 * @param tx
	 * @param rowid
	 * @param returnReason
	 * @param userInfo
	 * @param errors 
	 * @return
	 * @throws DataStoreException 
	 */
	private static void toAssignedSignFlagR(String rowid,String returnReason,ViewLoginUserInfo userInfo, ActionErrors errors)
	{
		Session hs=null;
		Transaction tx=null;
        try{
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			MaintContractDetail mcd = (MaintContractDetail) hs.get(MaintContractDetail.class,Integer.valueOf(rowid));
			if(mcd!=null){
				mcd.setAssignedSignFlag("R");
				mcd.setAssignedSign(userInfo.getUserID());
				mcd.setAssignedSignDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
				mcd.setReturnReason(returnReason);
				MaintContractMaster mcm=(MaintContractMaster) hs.get(MaintContractMaster.class, mcd.getBillNo());
				mcm.setTaskSubFlag("R");
				hs.update(mcd);
				hs.update(mcm);
			}
			tx.commit();

        }catch(Exception e){
        	if(tx!=null){
        		tx.rollback();
        	}
        	if(errors.isEmpty()){
        		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>����ʧ�ܣ�</font>"));
        	}
        	e.printStackTrace();
        }finally{
        	if(hs != null){
				hs.close();
			}
        }
	}
	
	@SuppressWarnings("unchecked")
	public void getMaintPersonnelOneDateDetail(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String maintPersonnel = request.getParameter("maintPersonnel"); //ά����
		String maintDate = request.getParameter("maintDate"); //ά������

		JSONArray jsonArr = new JSONArray();
		JSONObject json = null;
		
		if(maintPersonnel != null && !maintPersonnel.equals("")
				&& maintDate != null && !maintDate.equals("")){

			Session hs = null;
			Query query = null;
			try {
				hs = HibernateUtil.getSession();
				
				String hql = "select d.maintContractNo,c.salesContractNo,c.projectName,c.elevatorNo,a.maintType,a.maintDateTime" +
						" from MaintenanceWorkPlanDetail a,MaintenanceWorkPlanMaster b, MaintContractDetail c, MaintContractMaster d" +
						" where a.maintenanceWorkPlanMaster.billno = b.billno" +
						" and b.rowid = c.rowid" +
						" and c.billNo = d.billNo" +
						" and a.maintPersonnel = '"+maintPersonnel+"'" +
						" and maintDate = '"+maintDate+"'";
				
				query = hs.createQuery(hql);

				List<Object> list  = query.list();
				for (Object object : list) {
					Object[] objs = (Object[]) object;
					json = new JSONObject();
					json.put("maintContractNo", String.valueOf(objs[0]));// ά����ͬ��
					json.put("salesContractNo", String.valueOf(objs[1]));// ���ۺ�ͬ��
					json.put("projectName", String.valueOf((objs[2])));// ��Ŀ����
					json.put("elevatorNo", String.valueOf(objs[3]));// ���ݱ��
					json.put("maintType", String.valueOf(objs[4]));// ��������
					json.put("maintDateTime", String.valueOf(objs[5]));// ��׼����ʱ��
					jsonArr.put(json);				
				}
				
				ServletOutputStream stream = response.getOutputStream();
				stream.write(jsonArr.toString().replace("null", "").getBytes("UTF-8"));
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally { 
				if(hs != null){
					hs.close();
				}
			}
			

			
		}

	}
	
	@SuppressWarnings("unchecked")
	public void getMaintPersonnel(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) {

		String mainStation = request.getParameter("mainStation"); //ά��վ

		JSONArray jsonArr = new JSONArray();
		JSONObject json = null;
		if(mainStation != null && !mainStation.equals("")){

			Session hs = null;
			Query query = null;
			try {
				hs = HibernateUtil.getSession();
				
				String sql = "select lu.UserID,lu.UserName from Loginuser lu "
						+ "where lu.StorageID like '"+mainStation+"%' and  lu.RoleID in('A50','A49') and enabledflag='Y'";
				List list =hs.createSQLQuery(sql).list();
				List MaintPersonnelList = new ArrayList();

				if(list!=null&&list.size()>0)
				{
					for(int j = 0;j<list.size();j++)
					{
						Object[] objects = (Object[])list.get(j);
						json = new JSONObject();
						json.put("id", String.valueOf(objects[0]));// ά����ͬ��
						json.put("name", String.valueOf(objects[1]));// ���ۺ�ͬ��
						jsonArr.put(json);	
					}						
				}

				ServletOutputStream stream = response.getOutputStream();
				stream.write(jsonArr.toString().replace("null", "").getBytes("UTF-8"));
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally { 
				if(hs != null){
					hs.close();
				}
			}
			

			
		}

	}
	/**
	 * �޸ĵ��ݵ�����Ϣ������ά��վ
	 * @param hs
	 * @param tx
	 * @param elevatorNo
	 * @param mainStationId
	 * @param userInfo
	 * @param errors
	 */
	private void toUpdataElevatorArchivesInfo(
			String elevatorNo, String mainStationId,ViewLoginUserInfo userInfo, ActionErrors errors) {
		Session hs=null;
		Transaction tx=null;
          try{
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			String hql="from ElevatorArchivesInfo eaif where eaif.elevatorNo = "+elevatorNo;
			List list=hs.createQuery(hql).list();
			ElevatorArchivesInfo eaif=new ElevatorArchivesInfo();
			if(list!=null&&list.size()>0){
				eaif=(ElevatorArchivesInfo) list.get(0);
				eaif.setMaintStation(mainStationId);
			    hs.update(eaif);
			}
			
			tx.commit();

        }catch(Exception e){
        	if(tx!=null){
        		tx.rollback();
        	}
        	if(errors.isEmpty()){
        		errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>����ʧ�ܣ�</font>"));
        	}
        	e.printStackTrace();
        }finally{
        	if(hs != null){
				hs.close();
			}
        }
            
			
	}
	
	
	public static String getParentStorageID(String storageID)
	{
		Session hs = null;
		try {
			hs=HibernateUtil.getSession();
		    String sql="select case when ParentStorageID='0' then  StorageID else ParentStorageID end as StorageID from StorageId where StorageID='"+storageID+"'";
		    List rs=hs.createSQLQuery(sql).list();
		    if(rs!=null&&rs.size()>0)
		    {
		    	storageID=(String)rs.get(0);
		    }
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
        	if(hs != null){
				hs.close();
			}
        }
		
		return storageID;
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
	/**
	 * �´�ǩ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 * @throws ServletException
	 */
	public ActionForward toSaveRem(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		ActionErrors errors = new ActionErrors();

		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		
		String[] assignedRem=request.getParameterValues("assignedRem");//�´�ǩ�ձ�ע
		String[] isBoxs=request.getParameterValues("isBox");//ȷ��ѡ��ѡ��
		String[] rowids=request.getParameterValues("rowid");//ȷ��ѡ��ѡ��
		
		Session hs = null;
		Transaction tx = null;
		try{

			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			
			if(isBoxs.length>0) {
            	for(int i=0;i<isBoxs.length;i++){
            		if(isBoxs[i].equals("Y")){
            				MaintContractDetail mcd = (MaintContractDetail) hs.get(MaintContractDetail.class,Integer.valueOf(rowids[i]));
            				if(mcd!=null){
            					mcd.setAssignedRem(assignedRem[i]);
            					hs.update(mcd);
            				}
            		}
            	}	
            }
			tx.commit();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","���汸ע�ɹ���"));
		} catch (Exception e1) {
			e1.printStackTrace();
			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>���汸עʧ�ܣ�</font>"));
			log.error(e1.getMessage());
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}

		ActionForward forward = null;
		try {
		    if(errors.isEmpty()){
		    	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("insert.success"));
		    }
			forward = mapping.findForward("returnList");
				
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (!errors.isEmpty()) {
			saveErrors(request, errors);
		}
		return forward;
	}
}
