package com.mobileserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.stream.FileImageInputStream;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.hibernate.basedata.hotlinefaulttype.HotlineFaultType;
import com.gzunicorn.hibernate.basedata.hotlinemotherboardtype.HotlineMotherboardType;
import com.gzunicorn.hibernate.hotlinemanagement.calloutmaster.CalloutMaster;
import com.gzunicorn.hibernate.hotlinemanagement.calloutprocess.CalloutProcess;
import com.gzunicorn.hibernate.hotlinemanagement.smshistory.SmsHistory;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.struts.action.xjsgg.SmsService;
import com.gzunicorn.struts.action.xjsgg.XjsggAction;

/**
 * �ֻ�APP�˵��ã����ϴ�����
 * @author Crunchify
 */
@Path("/Callout")
public class Callout {
	
	BaseDataImpl bd = new BaseDataImpl();
	
    /**  
	 * ������ҵ--��Ϣ�б�
	 * @param JSONObject data
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/sxlist/{data}")
	@Produces("application/json")
	public Response getCalloutList(
			@PathParam("data") JSONObject data) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		String jsonSrt="";
		
		System.out.println(">>>>>>>>������ҵ--��Ϣ�б�");
		try{
			
			String userid =(String) data.get("userid");
			String workstardate=(String) data.get("workstardate");
			String workenddate =(String) data.get("workenddate");
			
			if(workstardate==null || workstardate.trim().equals("")){
				workstardate=CommonUtil.getNowTime("yyyy-MM-dd");
			}
			if(workenddate==null || workenddate.trim().equals("")){
				workenddate=CommonUtil.getNowTime("yyyy-MM-dd");
			}
			session = HibernateUtil.getSession();

			String sql="select c.* from app_sxList c where c.assignobject = '"+userid.trim()+"'";
			if (workstardate != null && !"".equals(workstardate)) {
				sql += " and c.repairTime >= '"+workstardate.trim()+" 00:00:00'";
			}
			if (workenddate != null && !"".equals(workenddate)) {
				sql += " and c.repairTime <= '"+workenddate.trim()+" 99:99:99'";
			}
			
			sql +=" order by c.repairTime desc";
			
			//System.out.println(sql);
			
			Query query = session.createSQLQuery(sql);
            ArrayList cpList = (ArrayList) query.list();

            if (cpList != null && cpList.size()>0 ) {
            	
            	for(int i=0;i<cpList.size();i++){
            	
                  Object[] objects=(Object[]) cpList.get(i);
                  String calloutMasterNo =(String) objects[0];
                  String elevatorNo =(String) objects[1];
                  String companyName =(String) objects[2];
                  String projectAddress =(String) objects[3];
                  String isTrap =(String) objects[4];
                  String repairTime =(String) objects[5];
                  String submitType =(String) objects[6];
            	  String handleStatus =(String) objects[7];
            	  String isgzbgs =(String) objects[8];
            	  String assignobject =(String) objects[9];
            	  String stoprem =(String) objects[10];
            	  JSONObject jsonObject = new JSONObject(); 
             	 
            	  if(submitType.trim()=="Y"||submitType.trim().equals("Y")){
            	  
             	if(handleStatus!=null&& !handleStatus.trim().trim().equals("")){
             		if(handleStatus.trim().equals("0")){
             			jsonSrt="[{'name':'����','url':'http://127.0.0.1/����'},{'name':'ת��','url':'http://127.0.0.1/ת��'}]";
               		    JSONArray nextstate = new JSONArray(jsonSrt);
               		    jsonObject.put("handlestatus","δ����");
               		    jsonObject.put("nextstate",nextstate);
	             	 }else if(handleStatus.trim().equals("1")){
	             		jsonSrt="[{'name':'����','url':'http://127.0.0.1/����'}]";
	           		    JSONArray nextstate = new JSONArray(jsonSrt);
	           		    jsonObject.put("handlestatus","�ѽ���");
	           		    jsonObject.put("nextstate",nextstate);
	             	 }else if(handleStatus.trim().equals("2")){
	             		jsonSrt="[{'name':'ͣ��','url':'http://127.0.0.1/ͣ��'},{'name':'�깤','url':'http://127.0.0.1/�깤'}]";
	           		    JSONArray nextstate = new JSONArray(jsonSrt);
	           		    jsonObject.put("handlestatus","�ѵ���");
	           		    jsonObject.put("nextstate",nextstate);
	             	 }else if(handleStatus.trim().equals("3")){
	             		jsonSrt="[{'name':'�깤','url':'http://127.0.0.1/�깤'}]";
	             		 JSONArray nextstate = new JSONArray(jsonSrt);
	             		 jsonObject.put("handlestatus","��ͣ��"); 
	             		 jsonObject.put("nextstate",nextstate);
	             	 }else if(handleStatus.trim().equals("4")&&(isgzbgs==null||isgzbgs.trim().equals(""))){
	             		 jsonSrt="[{'name':'¼�뱨����','url':'http://127.0.0.1/¼�뱨����'}]";
	             		 JSONArray nextstate = new JSONArray(jsonSrt);
	             		 jsonObject.put("handlestatus","���깤"); 
	             		 jsonObject.put("nextstate",nextstate); 
	             	 }else{
	             		jsonSrt="[]";
	             		 JSONArray nextstate = new JSONArray(jsonSrt);
	             		 jsonObject.put("handlestatus","���깤"); 
	             		 jsonObject.put("nextstate",nextstate); 
	             	 }
             		}else{
             		jsonSrt="[{'name':'����','url':'http://127.0.0.1/����'},{'name':'ת��','url':'http://127.0.0.1/ת��'}]";
           		    JSONArray nextstate = new JSONArray(jsonSrt);
           		    jsonObject.put("handlestatus","δ����");
           		    jsonObject.put("nextstate",nextstate);
             		}
            	 }else{
            		 jsonSrt="[{'name':'�޸�','url':'http://127.0.0.1/�޸�'},{'name':'�ύ','url':'http://127.0.0.1/�ύ'}]";
            		    JSONArray nextstate = new JSONArray(jsonSrt);
            		    jsonObject.put("handlestatus","δ�ύ");
            		    jsonObject.put("nextstate",nextstate); 
            	 }
            	  
            	 jsonObject.put("calloutmasterno", calloutMasterNo.trim());
            	 jsonObject.put("elevatorno", elevatorNo.trim());
            	 jsonObject.put("companyName", companyName.trim());
            	 jsonObject.put("projectaddress",projectAddress.trim());
            	 
            	 if(isTrap.equals("Y")){
            		 jsonObject.put("istrap","����");
            	 }else{
            		 jsonObject.put("istrap","������");
            	 }
             	 jsonObject.put("repairtime", repairTime.trim().subSequence(0, 10));
            	 jsonObject.put("stoprem",stoprem);
             	 
        		 jobiArray.put(i, jsonObject);
            	
            	
            	}
                 
		      }
            json.put("code", "200");
  			json.put("info", "OK");
              rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**  
	 * ������ҵ--��ϸ��Ϣ
	 * @param String userid
	 * @param String trno
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/sxdetail/{userid}/{trno}")
	@Produces("application/json")
	public Response getCalloutDisplay(
			@PathParam("userid") String userid,
			@PathParam("trno") String trno) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>������ҵ--��ϸ��Ϣ");
		try{
			session = HibernateUtil.getSession();
			String sql="select cp,cm,lu.username,s.storagename "
					+ "from CalloutProcess cp,CalloutMaster cm, "
					+ "Loginuser lu,Storageid s "
					+ "where s.storageid=cm.maintStation and cp.assignObject2=lu.userid "
					+ "and cp.calloutMasterNo=cm.calloutMasterNo "
					+ "and (cp.assignObject2='"+userid.trim()+"' or cm.assignObject ='"+userid.trim()+"') "
					+ "and cm.calloutMasterNo = '"+trno+"'";
			Query query = session.createQuery(sql);
            ArrayList cpList = (ArrayList) query.list();
             //cpList�Ƿ���ֵ
            if (cpList != null && cpList.size()>0 ) {
            	Object[] objects=(Object[]) cpList.get(0);
                CalloutProcess cp=(CalloutProcess) objects[0];
                CalloutMaster cm=(CalloutMaster) objects[1];
          	  	JSONObject jsonObject = new JSONObject(); 
          	 
			jsonObject.put("calloutmasterno", cm.getCalloutMasterNo());
			jsonObject.put("handlestatus", cm.getHandleStatus());
			jsonObject.put("repairtime", cm.getRepairTime());
			
			if(cm.getRepairMode()=="1"||"1".equals(cm.getRepairMode())){
				jsonObject.put("repairmode", "��������");
			}
			if(cm.getRepairMode()=="2"||"2".equals(cm.getRepairMode())){
				jsonObject.put("repairmode", "��������");
			}
			if(cm.getServiceObjects()=="1"||"1".equals(cm.getServiceObjects())){
				jsonObject.put("serviceobjects", "�Ա�����");
			}
			if(cm.getServiceObjects()=="2"||"2".equals(cm.getServiceObjects())){
				jsonObject.put("serviceobjects", "��������");
			}
			if(cm.getServiceObjects()=="3"||"3".equals(cm.getServiceObjects())){
				jsonObject.put("serviceobjects", "���ҷ�����");
			}
			if(cm.getServiceObjects()=="4"||"4".equals(cm.getServiceObjects())){
				jsonObject.put("serviceobjects", "����֧��");
			}
			
			String companyName=bd.getName("Customer", "companyName", "companyId", cm.getCompanyId());
			if(companyName==null || companyName.trim().equals("")){
				companyName=cm.getCompanyId();
			}
					
			jsonObject.put("elevatorno", cm.getElevatorNo());
			jsonObject.put("repairuser", cm.getRepairUser());
			jsonObject.put("repairtel", cm.getRepairTel());
			jsonObject.put("companyid", cm.getCompanyId());
			jsonObject.put("companyName", companyName);
			jsonObject.put("projectaddress", cm.getProjectAddress());
			jsonObject.put("salescontractno", cm.getSalesContractNo());
			jsonObject.put("elevatorparam", cm.getElevatorParam());
			jsonObject.put("maintstation", cm.getMaintStation());
			jsonObject.put("storageName", (String)objects[3]);
			jsonObject.put("assignobjectName",(String)objects[2]);
			jsonObject.put("assignedMainStation", cm.getMaintStation());
			jsonObject.put("salesContractNo", cm.getSalesContractNo());
			jsonObject.put("operdate", cm.getOperDate());		
			jsonObject.put("assignobject", cp.getAssignObject2());
			jsonObject.put("phone", cm.getPhone());
			String tel=cp.getTurnSendTel();
			if(tel!=null){
			jsonObject.put("phone", tel);	
		    }
			String istrap=cm.getIsTrap();
			if(istrap!=null && istrap.trim().equals("Y")){
				istrap="����";
			}else{
				istrap="������";
			}
			jsonObject.put("istrap",istrap);
			jsonObject.put("stoprem",cp.getStopRem());//ͣ�ݱ�ע
			
			if(cp.getArriveDate()==null){
				jsonObject.put("arrivedate2", "");
			}else{
				jsonObject.put("arrivedate2", cp.getArriveDate());
			}
			if(cp.getArriveTime()==null){
				jsonObject.put("arrivetime2", "");
			}else{
				jsonObject.put("arrivetime2", cp.getArriveTime());
			}
			
			jsonObject.put("repairdesc", cm.getRepairDesc());
			jsonObject.put("auser", (String)objects[3]);
			jsonObject.put("atime", cp.getArriveDateTime());
			jsonObject.put("areamrk", cp.getArriveLocation());
			jsonObject.put("suser", (String)objects[3]);
			jsonObject.put("stime", cp.getStopTime());
			jsonObject.put("sreamrk", cp.getStopLocation());
			jsonObject.put("fuser", (String)objects[3]);
			jsonObject.put("ftime", cp.getCompleteTime());
			jsonObject.put("fremark", cp.getFninishLocation());
			jsonObject.put("isCanTurn", "N");
			 if(cm.getHandleStatus()==null||cm.getHandleStatus().trim().equals("")||cm.getHandleStatus().trim().equals("0")){
				jsonObject.put("isCanTurn", "Y");
//				jsonObject.put("assignObject", "");
//				jsonObject.put("phone", "");
//				jsonObject.put("assignobjectName","");
			 }
    		jobiArray.put(0, jsonObject);
		    }
            json.put("code", "200");
   			json.put("info", "OK");
            rejson.put("status", json);
       		rejson.put("data", jobiArray);
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**  
	 * ������ҵ--������������(������)
	 * @param JSONObject data
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/companList/{data}")
	@Produces("application/json")
	public Response getCompanList(
			@PathParam("data") JSONObject data) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>������ҵ--������������(���޵�λ������)");
		
		try{
			session = HibernateUtil.getSession();
			String userid=(String) data.get("userid");
			String elevatorNo=CommonUtil.URLDecoder_decode((String) data.get("elevatorno"));
            String stat=(String) data.get("stat");
            String companyid=CommonUtil.URLDecoder_decode((String) data.get("companyid")); 
            String sqlsrt="";
            
            if(stat=="elevatorno"||stat.equals("elevatorno")){
            	sqlsrt=" distinct b.elevatorNo,b.elevatorParam,b.salesContractNo,b.assignedMainStation,"
            			+ "d.storageName,isnull(ec.rem,'') as MaintAddress,";
			}else{
				
				sqlsrt=" distinct ";
			}
            //���´ﱣ��վ���ų��Ƿ����
			String sql="select "+sqlsrt+"a.companyId,c.companyName"
					+ " from MaintContractMaster as a,Customer as c,"
					+ "MaintContractDetail as b left join Storageid as d on b.assignedMainStation=d.storageId "
					+ "left join ElevatorCoordinateLocation ec on ec.ElevatorNo=b.ElevatorNo "
					+ "where a.billNo=b.billNo and (a.contractStatus='XB'or a.contractStatus='ZB' ) "
					//+ "and isnull(b.maintPersonnel,'') != ''  "
					+ "and a.companyId =c.companyId "
					+ "and b.AssignedMainStation like "
					+ "(select case when s.ParentStorageID=0 then s.StorageID else s.ParentStorageID end " +
					"from LoginUser l,Storageid s where l.StorageID=s.StorageID and l.UserID='"+userid.trim()+"')";
			
			if(companyid!=null && !companyid.equals("") && !companyid.equals("undefined")){
				sql +=" and (a.companyId like '%"+companyid.trim()+"%' or c.companyName like '%"+companyid.trim()+"%'" +
						" or b.ElevatorNo like '%"+companyid.trim()+"%' )";
			}
			if(elevatorNo!=null && !elevatorNo.equals("")){
				sql +=" and b.elevatorNo like '%"+elevatorNo+"%'";
			}
			
			//sSystem.out.println(sql);
			
			List list =session.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
			
				for(int i=0;i<list.size();i++){
					Object[] objects=(Object[]) list.get(i);
					JSONObject jsonObject = new JSONObject(); 
					
					if(stat=="elevatorno"|| stat.equals("elevatorno")){
						
						jsonObject.put("elevatorNo", objects[0]);
						jsonObject.put("elevatorParam", objects[1]);
						jsonObject.put("salesContractNo", objects[2]);
						jsonObject.put("assignedMainStation", objects[3]);
						jsonObject.put("storageName", objects[4]);
						jsonObject.put("addr", objects[5]);

					}else{
						jsonObject.put("companyId", objects[0]);
						jsonObject.put("companyName", objects[1]);
					}
					jobiArray.put(i, jsonObject);
					
				}
				
			   }
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
            //rejson.put("data", jobiArray);
      		rejson.put("data", CommonUtil.Pagination(data, jobiArray));//����Pagination���� ʵ�ֵ��"����"�Ĺ���
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (Exception hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**  
	 * ������ҵ--ת�����ɹ�
	 * @param String userid
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/sxzhuanpai/{userid}")
	@Produces("application/json")
	public Response sxzhuanpai(
			@PathParam("userid") String userid) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		try{
			System.out.println(">>>>>>>>������ҵ--ѡ��ά����");
			session = HibernateUtil.getSession();
			int p=0;
			
			//ά��վ��A49��ά����A50,ά������ A03��ά�޼���ԱA53
			String sql="select l.UserID,l.UserName,l.Phone,l.StorageID,s.StorageName,r.rolename "
					+ "from LoginUser l,StorageID s,role r where l.enabledflag='Y' and l.RoleID in('A49','A50','A03','A53') "
					+ "and s.StorageID=l.StorageID and l.roleid=r.roleid and l.StorageID like ("
					+ "select case when s.ParentStorageID=0 then s.StorageID else s.ParentStorageID "
					+ "end  from StorageID s where s.StorageID like "
					+ "(select StorageID from LoginUser where UserID='"+userid+"'))+'%' ";
			List list =session.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Object[] objects=(Object[]) list.get(i);
					JSONObject jsonObject = new JSONObject(); 
						jsonObject.put("dutyuserid", objects[0]);
						jsonObject.put("dutyusername", objects[1]);
						jsonObject.put("linkphone", objects[2]);
						jsonObject.put("storageid", objects[3]);
						jsonObject.put("storagename", objects[4]);
						jsonObject.put("rolename", objects[5]);
						jobiArray.put(p, jsonObject);
						p++;
				}
			}
			
			//����������ȫ���� A11 
			String sql2="select a.UserID,a.UserName,a.Phone,a.StorageID,s.StorageName,r.rolename  "
					+ "from LoginUser a,LoginUser b,StorageID s,role r "
					+ "where a.RoleID='A11' and a.roleid=r.roleid  and a.StorageID=s.StorageID "
					+ "and a.grcid=b.grcid and a.EnabledFlag='Y' and b.UserID='"+userid+"'";
			List list2 =session.createSQLQuery(sql2).list();
			if(list2!=null && list2.size()>0){
				for(int i=0;i<list2.size();i++){
					Object[] objects=(Object[]) list2.get(i);
					JSONObject jsonObject = new JSONObject(); 
					jsonObject.put("dutyuserid", objects[0]);
					jsonObject.put("dutyusername", objects[1]);
					jsonObject.put("linkphone", objects[2]);
					jsonObject.put("storageid", objects[3]);
					jsonObject.put("storagename", objects[4]);
					jsonObject.put("rolename", objects[5]);
					jobiArray.put(p, jsonObject);
					p++;
				}
			}
			
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (Exception hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**  
	 * ������ҵ--�������
	 * @param JSONObject data
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/sxscens")
	@Produces("application/json")
	public Response sxscens(@FormParam("data") JSONObject data) throws JSONException {
		
		Session session = null;
		Transaction tx = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		CalloutMaster cm = null;
		CalloutProcess cp=null;
		
		System.out.println(">>>>>>>>������ҵ--����");
		
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			String act=(String) data.get("act");//����
			String calloutmasterno=(String) ((JSONObject) data.get("tr")).get("calloutmasterno");//: ���޵���,
			String userid=(String) data.get("userid");//¼����
			String repairuser=(String) ((JSONObject) data.get("tr")).get("repairuser");//: ������,
			String repairtel=(String) ((JSONObject) data.get("tr")).get("repairtel");//="";// �����˵绰,
			
			cm =(CalloutMaster) session.get(CalloutMaster.class, calloutmasterno);
			cp =(CalloutProcess) session.get(CalloutProcess.class, calloutmasterno);
			
			if(act.equals("����")){
				//DebugUtil.println("�ֻ��������޵�: "+data.toString());
				
				//�ֻ��½�����
				cm = new CalloutMaster();
				cp = new CalloutProcess();
				String calloutMasterNo=XjsggAction.genCalloutMasterNum();//�½����޵���
	            cm.setCalloutMasterNo(calloutMasterNo);
	            cp.setCalloutMasterNo(calloutMasterNo);

	            cm_cpSave(cm,cp,data);
	            cm.setIsSendSms2("N");//�����Ͱ�������
		        cm.setSubmitType("Y");
		        cm.setR1("Y");//�Ƿ��ֻ�����
		        
		        //Ĭ�Ͻ���
		        //cp.setAssignUser(userid);
				//cp.setAssignTime(CommonUtil.getNowTime());
				//cp.setAssignUserTel((String) ((JSONObject) data.get("tr")).get("phone"));
				//cm.setHandleStatus("1");

	            session.save(cm);
	            session.save(cp);
	            
	            /****************************���Ͷ��Ÿ�ά���� ��ʼ**********************************/
		        String istrap=cm.getIsTrap();// �Ƿ�����(N������,Y����)
		        String istraptext="������";
            	if(istrap!=null && istrap.equals("Y")){
            		istraptext="����";
            	}
            	//���Ͷ��Ÿ�ά����
            	System.out.println(">>>���Ͷ��Ÿ�ά����");
            	String smsmes="���������"+istraptext+"�����ݱ�ţ�"+cm.getElevatorNo()+"����Ŀ���Ƽ�¥���ţ�"+cm.getProjectAddress()+"���������ĵ����й��ϣ��뼰ʱ���� [����Ѹ��]";
            	boolean issms=SmsService.sendSMS(istraptext,cm.getElevatorNo(),cm.getProjectAddress(),cm.getPhone());

	            SmsHistory sh=new SmsHistory();
				sh.setSmsContent(smsmes);
				sh.setSmsSendTime(CommonUtil.getNowTime());
				sh.setSmsTel(cm.getPhone());
				if(issms){
					sh.setFlag(1);
				}else{
					sh.setFlag(0);	
				}
				session.save(sh);
				/****************************���Ͷ��Ÿ�ά���� ����**********************************/
			}else 
				/*���̴������*/
			if(act.equals("����")||act=="����"){
				cp.setAssignUser(userid);
				cp.setAssignTime(CommonUtil.getNowTime());
				if(cm.getAssignObject().equals(cp.getAssignObject2())){
				   cp.setAssignUserTel(cm.getPhone());
				}else{
					cp.setAssignUserTel(cp.getTurnSendTel());
				}
				
				cm.setHandleStatus("1");
				session.update(cp);
				session.update(cm);
			}else if(act.equals("ת��")||act=="ת��"){
				cp.setTurnSendTime(CommonUtil.getNowTime());
				cp.setTurnSendId(repairuser);
				cp.setTurnSendTel(repairtel);
				cp.setAssignObject2(repairuser);
				cm.setHandleStatus("0");
				session.update(cp);
			}else if(act.equals("����")||act=="����"){

				Double gpslon=(Double) ((JSONObject) data.get("tr")).get("gpslon");
				Double gpslat=(Double) ((JSONObject) data.get("tr")).get("gpslat");
				Double lon=(Double) ((JSONObject) data.get("tr")).get("lon");
				Double lat=(Double) ((JSONObject) data.get("tr")).get("lat");
				String addr=(String) ((JSONObject) data.get("tr")).get("addr");
				
				String arrivedate=(String) ((JSONObject) data.get("tr")).get("arrivedate");//���ֳ�����(ҳ������)
				String arrivetime=(String) ((JSONObject) data.get("tr")).get("arrivetime");//���ֳ�ʱ��(ҳ��ʱ��)
				
				cp.setArriveDate(arrivedate);
				cp.setArriveTime(arrivetime);
				cp.setArriveDateTime(CommonUtil.getNowTime());//ϵͳ��̨�ĵ���ʱ��
				cp.setArriveLongitude(lon);
				cp.setArriveLatitude(lat);
				cp.setArriveLongitudeGPS(gpslon);//GPSԭʼ���꾭��
				cp.setArriveLatitudeGPS(gpslat);//GPSԭʼ����γ��
				cp.setArriveLocation(addr);	
				cm.setHandleStatus("2");
				session.update(cp);
				session.update(cm);
			}else if(act.equals("ͣ��")||act=="ͣ��"){
				
				Double gpslon=(Double) ((JSONObject) data.get("tr")).get("gpslon");
				Double gpslat=(Double) ((JSONObject) data.get("tr")).get("gpslat");
				Double lon=(Double) ((JSONObject) data.get("tr")).get("lon");
				Double lat=(Double) ((JSONObject) data.get("tr")).get("lat");
				String addr=(String) ((JSONObject) data.get("tr")).get("addr");
				String stoprem=(String) ((JSONObject) data.get("tr")).get("stoprem");//ͣ�ݱ�ע
				
				cp.setStopTime(CommonUtil.getNowTime());
				cp.setStopLongitude(lon);
				cp.setStopLatitude(lat);
				cp.setStopLongitudeGPS(gpslon);//GPSԭʼ���꾭��
				cp.setStopLatitudeGPS(gpslat);//GPSԭʼ����γ��
				cp.setStopLocation(addr);
				cp.setIsStop("Y");
				cp.setStopRem(stoprem);//ͣ�ݱ�ע
				cm.setHandleStatus("3");
				session.update(cp);
				session.update(cm);
			}else if(act.equals("�깤")||act=="�깤"){

				Double gpslon=(Double) ((JSONObject) data.get("tr")).get("gpslon");
				Double gpslat=(Double) ((JSONObject) data.get("tr")).get("gpslat");
				Double lon=(Double) ((JSONObject) data.get("tr")).get("lon");
				Double lat=(Double) ((JSONObject) data.get("tr")).get("lat");
				String addr=(String) ((JSONObject) data.get("tr")).get("addr");

				cp.setCompleteTime(CommonUtil.getNowTime());
				cp.setFninishLongitude(lon);
				cp.setFninishLatitude(lat);
				cp.setFninishLongitudeGPS(gpslon);//GPSԭʼ���꾭��
				cp.setFninishLatitudeGPS(gpslat);//GPSԭʼ����γ��
				cp.setFninishLocation(addr);
				cm.setHandleStatus("4");
				session.update(cp);
				session.update(cm);
			}
			
			tx.commit();
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			tx.rollback();
			ex.printStackTrace();
			json.put("code", "400");
  			json.put("info", "����ʧ��");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
      		
      		ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (Exception hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**  
	 * �����½��޸ı���������
	 * @param CalloutMaster cm
	 * @param CalloutProcess cp
	 * @param JSONObject data
	 * @throws Exception
	 */
	public static void cm_cpSave(CalloutMaster cm,CalloutProcess cp,JSONObject data) throws Exception{
		String userid=(String) data.get("userid");//¼����
		String act=(String) data.get("act");//����
		String repairmode=(String) data.get("repairmode"); //���޷�ʽ
		String serviceobjects=(String) data.get("serviceobjects");//�������
		String istrap=(String) data.get("istrap");// �Ƿ�����(N������,Y����)
		JSONObject tr=(JSONObject) data.get("tr");
		String repairtime=(String) tr.get("repairtime");//: ����ʱ��,
		String repairuser=(String) tr.get("repairuser");//: ������,
		String repairtel=(String) tr.get("repairtel");//="";// �����˵绰,
		String companyid=(String) tr.get("companyid");// ���޵�λ����,
		String projectaddress=(String) tr.get("projectaddress");// ��Ŀ��ַ,
		String elevatorno=(String) tr.get("elevatorno");// ���ݱ��,

		String salescontractno=(String) tr.get("salesContractNo");// ���ۺ�ͬ��
		//���н���,������н���  
		byte[] salebyte=new BASE64Decoder().decodeBuffer(salescontractno);
		String salescontractno2=new String(salebyte);
		
		String elevatorparam=(String) tr.get("elevatorparam");// ����ͺ�,
		//���н���,������н���  
		byte[] elebyte=new BASE64Decoder().decodeBuffer(elevatorparam);
		String elevatorparam2=new String(elebyte);
		
		String maintstation=(String) tr.get("assignedMainStation");// ����ά��վ����,
		String assignobject=(String) tr.get("assignobject");// �ɹ��������,
		String phone=(String) tr.get("phone");// �绰,
		String repairdesc=(String) tr.get("repairdesc");// ��������
        
		cm.setAssignObject(assignobject);
		cm.setPhone(phone);
		cm.setOperId(userid);
        cm.setOperDate(CommonUtil.getNowTime());
        cm.setRepairTime(repairtime);
        cm.setRepairMode(repairmode);;
        cm.setRepairUser(repairuser);
        cm.setRepairTel(repairtel);
        cm.setServiceObjects(serviceobjects);
        cm.setCompanyId(companyid);
        cm.setProjectAddress(projectaddress);
        cm.setSalesContractNo(salescontractno2);
        cm.setElevatorNo(elevatorno);
        cm.setElevatorParam(elevatorparam2);
        cm.setMaintStation(maintstation);
        cm.setIsTrap(istrap);
        cm.setRepairDesc(repairdesc);
        cm.setHandleStatus("");//����״̬
        
        cp.setAssignObject2(assignobject);
        
	}
	
    /** 
     * ���ϱ�����--��Ϣ�б�
     * @param data
     * @return
     * @throws JSONException
     */
	@GET
	@Path("/gzlist/{data}")
	@Produces("application/json")
	public Response getCalloutbgList(
			@PathParam("data") JSONObject data) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		String jsonSrt="";
		
		System.out.println(">>>>>>>>���ϱ�����--��Ϣ�б�");
		try{
			
			String userid =(String) data.get("userid");
			String startrptime=(String) data.get("startrptime");
			String endrptime =(String) data.get("endrptime");
			
			if(startrptime==null || startrptime.trim().equals("")){
				startrptime=CommonUtil.getNowTime("yyyy-MM-dd");
			}
			if(endrptime==null || endrptime.trim().equals("")){
				endrptime=CommonUtil.getNowTime("yyyy-MM-dd");
			}

			session = HibernateUtil.getSession();
			
			String sql="select c.* from app_sxList c where c.assignobject = '"+userid.trim()+"'" +
					" and c.handleStatus in ('4','5','6','7')";
			if (startrptime != null && !"".equals(startrptime)) {
				sql += " and c.repairTime >= '"+startrptime.trim()+" 00:00:00'";
			}
			if (endrptime != null && !"".equals(endrptime)) {
				sql += " and c.repairTime <= '"+endrptime.trim()+" 99:99:99'";
			}
			
			sql +=" order by c.repairTime desc";
			//System.out.println(sql);
			Query query = session.createSQLQuery(sql);
            ArrayList cpList = (ArrayList) query.list();
             //mwpList�Ƿ���ֵ
            if (cpList != null && cpList.size()>0 ) {
            	
            	for(int i=0;i<cpList.size();i++){
            	
	              Object[] objects=(Object[]) cpList.get(i);
	              String calloutMasterNo =(String) objects[0];
	              String elevatorNo =(String) objects[1];
	              String companyName =(String) objects[2];
	              String projectAddress =(String) objects[3];
	              String isTrap =(String) objects[4];
	              String repairTime =(String) objects[5];
	              String submitType =(String) objects[6];
              	  String handleStatus =(String) objects[7];
              	  String isgzbgs =(String) objects[8];
            	  String assignObject =(String) objects[9];
            	  
            	  JSONObject jsonObject = new JSONObject(); 
            	   if(handleStatus.trim().equals("4")){
                    	 jsonSrt="[{'name':'����','url':'http://127.0.0.1/����'},{'name':'�ύ','url':'http://127.0.0.1/�ύ'}]";
	             		 JSONArray nextstate = new JSONArray(jsonSrt);
	             		 jsonObject.put("handlestatus","δ�ύ"); 
	             		 jsonObject.put("nextstate",nextstate); 
            	  }else if(handleStatus.trim().equals("5")||handleStatus.trim()=="5"){
                 	     jsonSrt="[]";
	             		 JSONArray nextstate = new JSONArray(jsonSrt);
	             		 jsonObject.put("handlestatus","���ύ"); 
	             		 jsonObject.put("nextstate",nextstate); 
         	      }else if(handleStatus.trim().equals("6")||handleStatus.trim()=="6"){
                 	     jsonSrt="[]";
	             		 JSONArray nextstate = new JSONArray(jsonSrt);
	             		 jsonObject.put("handlestatus","�����"); 
	             		 jsonObject.put("nextstate",nextstate); 
         	      }else{
         	    	     jsonSrt="[]";
             		     JSONArray nextstate = new JSONArray(jsonSrt);
             		     jsonObject.put("handlestatus","�ѹر�"); 
             		     jsonObject.put("nextstate",nextstate); 
         	      }
            	  
            	 jsonObject.put("calloutmasterno", calloutMasterNo.trim());
              	 jsonObject.put("elevatorno", elevatorNo.trim());
              	 jsonObject.put("companyName", companyName.trim());
              	 jsonObject.put("projectaddress",projectAddress.trim());
                 if(isTrap.equals("Y")){
           		 jsonObject.put("istrap","����");
           	     }else{
           		 jsonObject.put("istrap","������");
           	     }
               	 jsonObject.put("repairtime", repairTime.trim().subSequence(0, 10));
             	 jobiArray.put(i, jsonObject);
            	}
                 
		      }
            json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**
	 * ���ϱ�����--��ϸ��Ϣ
	 * @param userid
	 * @param trno
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/gzdetail/{userid}/{trno}")
	@Produces("application/json")
	public Response getCalloubgtDisplay(
			@PathParam("userid") String userid,
			@PathParam("trno") String trno) throws JSONException {
		
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>���ϱ�����--��ϸ��Ϣ");
		
		try{
			session = HibernateUtil.getSession();
			String sql="select cp,cm,lu.username,s.storagename "
					+ "from CalloutProcess cp,CalloutMaster cm,"
					+ "Loginuser lu,Storageid s "
					+ "where s.storageid=cm.maintStation and cp.assignObject2=lu.userid "
					+ "and cp.calloutMasterNo=cm.calloutMasterNo "
					+ "and (cp.assignObject2='"+userid.trim()+"' or cm.assignObject ='"+userid.trim()+"') "
					+ "and cm.calloutMasterNo = '"+trno+"'";
			Query query = session.createQuery(sql);
            ArrayList cpList = (ArrayList) query.list();
             //cpList�Ƿ���ֵ
	         if (cpList != null && cpList.size()>0 ) {
	            Object[] objects=(Object[]) cpList.get(0);
	            CalloutProcess cp=(CalloutProcess) objects[0];
	            CalloutMaster cm=(CalloutMaster) objects[1];
	          	JSONObject jsonObject = new JSONObject(); 
	          	
	          	String companyName=bd.getName("Customer", "companyName", "companyId", cm.getCompanyId());
				if(companyName==null || companyName.trim().equals("")){
					companyName=cm.getCompanyId();
				}
	          	 
				jsonObject.put("calloutmasterno", cm.getCalloutMasterNo());
				jsonObject.put("elevatorno", cm.getElevatorNo());
				jsonObject.put("companyid", cm.getCompanyId());
				jsonObject.put("companyName", companyName);
				jsonObject.put("projectaddress", cm.getProjectAddress());
				jsonObject.put("assignobjectName",(String)objects[2]);
				jsonObject.put("phone", cp.getTurnSendTel());
				jsonObject.put("bhauditrem", cm.getBhAuditRem());//��˲������
				
				//���豸���Ϊ��ʱ��Ĭ��Ϊ���ݱ��
				String deviceid=cp.getDeviceId();
				if(deviceid==null || deviceid.trim().equals("")){
					deviceid=cm.getElevatorNo();
				}
				jsonObject.put("deviceId", deviceid);
				jsonObject.put("faultCode", cp.getFaultCode());
				jsonObject.put("faultStatus", cp.getFaultStatus());
				jsonObject.put("hmtId", cp.getHmtId());
				jsonObject.put("newFittingName", cp.getNewFittingName());
				jsonObject.put("hftId", cp.getHftId());
				jsonObject.put("money", cp.getMoney());
				jsonObject.put("processdesc", cp.getProcessDesc());
				jsonObject.put("servicerem", cp.getServiceRem());
				jsonObject.put("hmtname",  bd.getName(session, "HotlineMotherboardType", "hmtName", "hmtId", cp.getHmtId()));
				//jsonObject.put("hftdesc", bd.getName(session, "HotlineFaultType", "hftDesc", "hftId", cp.getHftId()));
				jsonObject.put("istoll", cp.getIsToll());
				jsonObject.put("isgzbgs", cp.getIsgzbgs());
				
				String hftid=cp.getHftId();//��������
	           	 String hftdesc="";
	           	 if(hftid!=null && !hftid.trim().equals("")){
	           		 String sqls="select a from HotlineFaultType a where a.hftId in('"+hftid.replaceAll(",", "','")+"')";
	           		 List loginlist=session.createQuery(sqls).list();
	           		 if(loginlist!=null && loginlist.size()>0){
	           			 for(int l=0;l<loginlist.size();l++){
	           				HotlineFaultType hft=(HotlineFaultType)loginlist.get(l);
	           				 if(l==loginlist.size()-1){
	           					hftdesc+=hft.getHftDesc();
	           				 }else{
	           					hftdesc+=hft.getHftDesc()+",";
	           				 }
	           			 }
	           		 }
	           	 }
	           	jsonObject.put("hftdesc", hftdesc);
				/**
				String r5=cp.getR5();//���޲�����Ա
	           	 String r5name="";
	           	 if(r5!=null && !r5.trim().equals("")){
	           		 String sqls="select a from Loginuser a where a.userid in('"+r5.replaceAll(",", "','")+"')";
	           		 List loginlist=session.createQuery(sqls).list();
	           		 if(loginlist!=null && loginlist.size()>0){
	           			 for(int l=0;l<loginlist.size();l++){
	           				 Loginuser login=(Loginuser)loginlist.get(l);
	           				 if(l==loginlist.size()-1){
	           					 r5name+=login.getUsername();
	           				 }else{
	           					 r5name+=login.getUsername()+",";
	           				 }
	           			 }
	           		 }
	           		 
	           	 }
	           	 */
	           	 jsonObject.put("engcyuserid", cp.getR5());//���޲�����Ա
	           	 jsonObject.put("engcyusername", cp.getR5());//���޲�����Ա

				//��ȡ���ص���ά�޼�¼��·��
				String path="D:\\contract\\���ص���ά�޼�¼��·��.txt";
				BufferedReader reader= new BufferedReader(new FileReader(path));
				String downloadaddr=reader.readLine();
				reader.close();
				//String downloadaddr="http://10.10.0.5:8080/XJSCRM/PrintCalloutProcessServlet?id=";//����
				//String downloadaddr="http://www.xjelevator.com:9000/XJSCRM/PrintCalloutProcessServlet?id=";//��ʽ
				if(downloadaddr!=null && !downloadaddr.trim().equals("")){
					jsonObject.put("downloadaddr", downloadaddr.trim()+cp.getCalloutMasterNo());//����֪ͨ�����ص�ַ
				}else{
					jsonObject.put("downloadaddr", "");//����֪ͨ�����ص�ַ
				}
				
				/**=====================��ȡͼƬ=========================*/
				String folder = PropertiesUtil.getProperty("CalloutProcess.file.upload.folder");
				BASE64Encoder base=new BASE64Encoder();
				//�ͻ�ǩ��
				if(cp.getCustomerSignature()!=null && !cp.getCustomerSignature().trim().equals("")){
					String filepath=folder+cp.getCustomerSignature();
					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������
					jsonObject.put("customersignature", base.encode(imgbyte));//��������������
				}else{
					jsonObject.put("customersignature", "");
				}
				//�ͻ���Ƭ
				if(cp.getCustomerImage()!=null && !cp.getCustomerImage().trim().equals("")){
					String filepath=folder+cp.getCustomerImage();
					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������		
					jsonObject.put("customerimage", base.encode(imgbyte));//��������������
				}else{
					jsonObject.put("customerimage", "");
				}
				/**=====================��ȡͼƬ=========================*/
				
				if(cm.getHandleStatus()==null || cm.getHandleStatus().trim().equals("") || cm.getHandleStatus().trim().equals("0")){
					jsonObject.put("isCanTurn", "Y");
				}
	    		jobiArray.put(0, jsonObject);
			}
	         
            json.put("code", "200");
   			json.put("info", "OK");
            rejson.put("status", json);
       		rejson.put("data", jobiArray);
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**
	 * ���ϱ�����--����
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/gzbgadd")
	@Produces("application/json")
	public Response saveCalloutgz(@FormParam("data") JSONObject data) throws JSONException {
		
		System.out.println(">>>>>>>>���ϱ�����--����");
		
		String usetid=(String) data.get("userid");
		String servicerem=(String) ((JSONObject) data.get("rp")).get("servicerem");
		String processdesc=(String) ((JSONObject) data.get("rp")).get("processdesc");//ά�޹�������
		String faultStatus=(String) ((JSONObject) data.get("rp")).get("faultStatus");//����״̬
		String hftId=(String) ((JSONObject) data.get("rp")).get("hftId");
		String newFittingName=(String) ((JSONObject) data.get("rp")).get("newFittingName");
		String calloutmasterno=(String) ((JSONObject) data.get("rp")).get("calloutmasterno");
		String money=(String) ((JSONObject) data.get("rp")).get("money");
		String faultCode=(String) ((JSONObject) data.get("rp")).get("faultCode");//���ϴ���
		String deviceId=(String) ((JSONObject) data.get("rp")).get("deviceId");
		String istoll=(String) ((JSONObject) data.get("rp")).get("istoll");
		String hmtId=(String) ((JSONObject) data.get("rp")).get("hmtId");
		String status=(String) ((JSONObject) data.get("rp")).get("status");//0 ���棬1�ύ

		String engcyusername=(String) ((JSONObject) data.get("rp")).get("engcyusername");//ά����Ա
		
		
		//���ϴ�����", ��"�ָ�
		String faultCodestr="";
		if(faultCode!=null && !faultCode.trim().equals("")){
			String[] faultCodes=faultCode.split(",");
			for(int i=0;i<faultCodes.length;i++){
				String[] faultCodes2=faultCodes[i].split("��");
				for(int j=0;j<faultCodes2.length;j++){
					if(!faultCodes2[j].trim().equals("")){
						if(faultCodes2[j].indexOf("ER")>-1){
							faultCodestr+=faultCodes2[j]+",";
						}else{
							faultCodestr+="ER"+faultCodes2[j]+",";
						}
					}
				}
			}
			faultCodestr=faultCodestr.substring(0,faultCodestr.length()-1);
		}
		
		Session session = null;
		Transaction tx = null;
		CalloutMaster cm=null;
		CalloutProcess cp =null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		try{
			session = HibernateUtil.getSession();
			tx=session.beginTransaction();
			cm =(CalloutMaster) session.get(CalloutMaster.class, calloutmasterno);
			cp =(CalloutProcess) session.get(CalloutProcess.class, calloutmasterno);
			cp.setServiceRem(servicerem);
			cp.setProcessDesc(processdesc);
			cp.setFaultStatus(faultStatus);
			cp.setHftId(hftId);
			cp.setHmtId(hmtId);
			cp.setNewFittingName(newFittingName);
			cp.setIsToll(istoll);
			if(istoll.trim().equals("Y")){
				cp.setMoney(Double.valueOf(money));
			}
			cp.setFaultCode(faultCodestr);//���ϴ���
			cp.setDeviceId(deviceId);

			if(status.equals("1")){
				cm.setHandleStatus("5");
			 	cp.setIsgzbgs("Y");
			}else{
				cp.setIsgzbgs("N");	
			}
			
			cp.setR5(engcyusername);
			
			/**=======================����ͼƬ=============================*/
			String folder = PropertiesUtil.getProperty("CalloutProcess.file.upload.folder");
			String filepath=CommonUtil.getNowTime("yyyy-MM-dd")+"/";
			//����ǩ��
			String customersignature=(String) ((JSONObject) data.get("rp")).get("customersignature");
			if(customersignature!=null && !customersignature.trim().equals("")){
				//customersignature=customersignature.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
				String[] signatures=customersignature.split(",");
				if(signatures!=null && signatures.length>1){
					byte[] image=new BASE64Decoder().decodeBuffer(signatures[1]);
					String newfilename=calloutmasterno+"_0.jpg";
					//����ͼƬ
					File f=new File(folder+filepath);
					f.mkdirs();
					FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
					fos.write(image);
					fos.flush();
					fos.close();
					//����ͼƬ��Ϣ�����ݿ�
					cp.setCustomerSignature(filepath+newfilename);
				}
			}
			//��������
			String customerimage=(String) ((JSONObject) data.get("rp")).get("customerimage");
			if(customerimage!=null && !customerimage.trim().equals("")){
				//customerimage=customerimage.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
				String[] cimages=customerimage.split(",");
				if(cimages!=null && cimages.length>1){
					byte[] image=new BASE64Decoder().decodeBuffer(cimages[1]);
					String newfilename=calloutmasterno+"_1.jpg";
					//����ͼƬ
					File f=new File(folder+filepath);
					f.mkdirs();
					FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
					fos.write(image);
					fos.flush();
					fos.close();
					//����ͼƬ��Ϣ�����ݿ�
					cp.setCustomerImage(filepath+newfilename);
				}
			}
			/**=======================����ͼƬ=============================*/
			
			session.update(cm);
			session.update(cp);
			tx.commit();
          	json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			tx.rollback();
			ex.printStackTrace();
			json.put("code", "400");
  			json.put("info", "NOT OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}

	/**
	 * ���ϱ�����--��������
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/zblx/{data}")
	@Produces("application/json")
	public Response getzblxList(
			@PathParam("data") JSONObject data) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		String hmtName=CommonUtil.URLDecoder_decode((String) data.get("hmtname")); 
		hmtName = hmtName.replaceAll("::", "/");
		
		System.out.println(">>>>>>>>���ϱ�����--��������");
		try{
			session = HibernateUtil.getSession();
			String sql="from HotlineMotherboardType h "
					+ "where h.enabledFlag='Y' "
					+ "and (h.hmtName like '%"+hmtName+"%' or h.hmtId like '%"+hmtName+"%' ) "
					+ "order by h.hmtId ";
			//System.out.println(sql);
			List list =session.createQuery(sql).list();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HotlineMotherboardType h=(HotlineMotherboardType) list.get(i);
					JSONObject jsonObject = new JSONObject(); 
						jsonObject.put("hmtid", h.getHmtId());
						jsonObject.put("hmtname", h.getHmtName());
						jobiArray.put(i, jsonObject);
				}
			   }
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
            //rejson.put("data",jobiArray);
      		rejson.put("data",CommonUtil.Pagination(data, jobiArray));//����Pagination���� ʵ�ֵ��"����"�Ĺ���
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (Exception hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**
	 * ���ϱ�����--��������
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/gzlx/{data}")
	@Produces("application/json")
	public Response getgzlxList(
			@PathParam("data") JSONObject data) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		String hftdesc=CommonUtil.URLDecoder_decode((String) data.get("hftdesc")); 
		
		System.out.println(">>>>>>>>���ϱ�����--��������");
		try{
			session = HibernateUtil.getSession();
			String sql="from HotlineFaultType h "
					+ "where h.enabledFlag='Y' "
					+ "and (h.hftDesc like '%"+hftdesc+"%' or h.hftId like '%"+hftdesc+"%' ) "
					+ "order by h.hftId ";
			List list =session.createQuery(sql).list();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					HotlineFaultType h=(HotlineFaultType) list.get(i);
					JSONObject jsonObject = new JSONObject(); 
						jsonObject.put("hftid", h.getHftId());
						jsonObject.put("hftdesc", h.getHftId()+" "+h.getHftDesc());
						jobiArray.put(i, jsonObject);
				}
			   }
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		//rejson.put("data",jobiArray);
      		rejson.put("data",CommonUtil.Pagination(data, jobiArray));//����Pagination���� ʵ�ֵ��"����"�Ĺ���
		}catch(Exception ex){
			ex.printStackTrace();
		} finally {
            try {
            	if(session!=null){
            		session.close();
            	}
            } catch (Exception hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}

}
