package com.mobileserver;

/**
 * �ֻ�APP�˵��ã�ά����ҵ������
 * @author Crunchify
 */
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.hibernate.MaintainProjectInfo;
import com.gzunicorn.hibernate.basedata.elevatorcoordinatelocation.ElevatorCoordinateLocation;
import com.gzunicorn.hibernate.maintainprojectinfowork.MaintainProjectInfoWork;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplandetail.MaintenanceWorkPlanDetail;
import com.gzunicorn.hibernate.sysmanager.Loginuser;

@Path("/Maintcontract")
public class Maintcontract {
	/**
	 * ά����ҵ--��Ϣ�б�
	 * @param JSONObject data
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/bylist/{data}")
	@Produces("application/json")
	public Response bylist(
			@PathParam("data") JSONObject data) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		String jsonSrt="";
		
		System.out.println(">>>>>>>>������ҵ-�б�");
		
		try{
			String userid = (String) data.get("userid");
			String state = (String) data.get("state");
			String sdate = (String) data.get("workstardate");
			String edate= (String) data.get("workenddate");
			
			if(sdate==null || sdate.trim().equals("")){
				sdate=CommonUtil.getNowTime("yyyy-MM-dd");
			}
			if(edate==null || edate.trim().equals("")){
				edate=CommonUtil.getNowTime("yyyy-MM-dd");
			}
			
			session = HibernateUtil.getSession();
			
			//����Ƿ��б����еĵ��ݣ��ų���ͣ��,'4'
			String cjsql="select numno,HandleStatus from MaintenanceWorkPlanDetail " +
					"where isnull(HandleStatus,'') in('2') and maintPersonnel = '"+userid+"' ";
			List checklist=session.createSQLQuery(cjsql).list();
			String isdoc="N";
			if(checklist!=null && checklist.size()>0){
				isdoc="Y";
			}
			
			String sql="select mwp.Numno,mwp.Singleno,isnull(mwp.HandleStatus,'') as HandleStatus,"
					+"mwp.MaintDate,mwp.Week,mwp.MaintType,"
					+"mcd.elevatorNo,mcd.maintAddress,"
					+"isnull(e.beginLongitude,0) as beginLongitude,"
					+"isnull(e.beginDimension,0) as beginDimension,"
					+"isnull(e.rem,'') as maintrem," +
					"isnull(mwp.StopTime,'') as stoptime "
					+"from MaintenanceWorkPlanDetail mwp,"
					+"MaintenanceWorkPlanMaster mwpm " 
					+"left join ElevatorCoordinateLocation e on e.elevatorNo=mwpm.elevatorNo," 
					+"MaintContractDetail mcd "
					+ "where mwpm.billno=mwp.billno "
					+ "and mcd.rowid=mwpm.rowid and mwp.maintPersonnel = '"+userid+"' "
					+ "and mwpm.checkflag='Y'";
			if(state!=null && !"".equals(state)){
				if(Integer.valueOf(state)==0){
					sql+=" and (mwp.handleStatus like '"+state+"' or mwp.handleStatus is null)";
				}else{
					sql+=" and mwp.handleStatus like '"+state+"'";
				}
			}
			
			if(sdate!=null&&!sdate.equals("")){
				sql+=" and mwp.maintDate >= '"+sdate+"'";
			}
			if(edate!=null&&!edate.equals("")){
				sql+=" and mwp.maintDate <= '"+edate+"'";
			}
			
			sql+=" order by mwp.maintDate";
			
			//System.out.println(">>>>"+sql);

			Query query = session.createSQLQuery(sql);
            ArrayList mwpList = (ArrayList) query.list();
            if (mwpList != null && mwpList.size()>0 ) {
                 for(int i=0;i<mwpList.size();i++)
                 {
                	 Object[] objects=(Object[]) mwpList.get(i);
                	 
                	 JSONObject jsonObject = new JSONObject(); 
                	 jsonObject.put("numno", objects[0]);
                	 jsonObject.put("singleno", objects[1]);

                	 String stoptime=(String)objects[11];//��ͣʱ��
                	 
                	 // 0 ��ת�ɣ�1 �ѽ��գ�2  �ѵ�����3 ���깤��4  ��ͣ��5  ����
                	 String handlestatus=objects[2].toString();
                	 if(handlestatus!=null&& !handlestatus.equals("")){
	                	 if(handlestatus.equals("1") || handlestatus=="1"){
	                		 jsonSrt="[{'name':'����','url':'http://127.0.0.1/�깤'}]";
	                		 JSONArray nextstate = new JSONArray(jsonSrt);
	                		 jsonObject.put("handlestatus","�ѽ���");
	                		 jsonObject.put("nextstate",nextstate);
	                	 }else if(handlestatus.equals("2") || handlestatus=="2"){
	                		 if(stoptime!=null && !stoptime.trim().equals("")){
	                			 jsonSrt="[{'name':'�깤','url':'http://127.0.0.1/�깤'}]";
	                		 }else{
	                			 jsonSrt="[{'name':'�깤','url':'http://127.0.0.1/�깤'},{'name':'','url':'http://127.0.0.1/ת��'},{'name':'��ͣ','url':'http://127.0.0.1/��ͣ'}]"; 	 
	                		 }
	                		 JSONArray nextstate = new JSONArray(jsonSrt);
	                		 jsonObject.put("handlestatus","������"); 
	                		 jsonObject.put("nextstate",nextstate);
	                	 }else if(handlestatus.equals("3") || handlestatus=="3"){
	                		 jsonSrt="[]";
	                		 JSONArray nextstate = new JSONArray(jsonSrt);
	                		 jsonObject.put("handlestatus","���깤"); 
	                		 jsonObject.put("nextstate",nextstate);
	                	 }else if(handlestatus.equals("4") || handlestatus=="4"){
	                		 jsonSrt="[{'name':'����','url':'http://127.0.0.1/�깤'}]";
	                		 JSONArray nextstate = new JSONArray(jsonSrt);
	                		 jsonObject.put("handlestatus","����ͣ"); 
	                		 jsonObject.put("nextstate",nextstate);
	                	 }else{
	                		 jsonSrt="[{'name':'����','url':'http://127.0.0.1/����'},{'name':'ת��','url':'http://127.0.0.1/ת��'}]";
	                		 JSONArray nextstate = new JSONArray(jsonSrt);
	                		 jsonObject.put("handlestatus","δ����");
	                		 jsonObject.put("nextstate",nextstate);
	                	 }
                	 }else{
                		 jsonSrt="[{'name':'����','url':'http://127.0.0.1/����'},{'name':'ת��','url':'http://127.0.0.1/ת��'}]";
                		 JSONArray nextstate = new JSONArray(jsonSrt);
                		 jsonObject.put("handlestatus","δ����");
                		 jsonObject.put("nextstate",nextstate);
                	 }
                	 jsonObject.put("maintdate", objects[3]);
                	 jsonObject.put("week", objects[4]);
                	 jsonObject.put("elevatorno", objects[6]);
                	 jsonObject.put("maintaddress", objects[7]);

                	 //jsonObject.put("mainttype", objects[5]);
                	 String maintType=(String)objects[5];
                	 if(maintType.trim().equals("halfMonth")){
                		 jsonObject.put("mainttype", "���±���"); 
                	 }else if(maintType.trim().equals("quarter")){
                		 jsonObject.put("mainttype", "���ȱ���"); 
                	 }else if(maintType.trim().equals("halfYear")){
                		 jsonObject.put("mainttype", "���걣��"); 
                	 }else if(maintType.trim().equals("yearDegree")){
                		 jsonObject.put("mainttype", "��ȱ���"); 
                	 }
                	 
                	//���ݻ�������
                	 jsonObject.put("elongitude", objects[8]);//����
                	 jsonObject.put("elatitude", objects[9]);//γ��
                	 jsonObject.put("maintrem", objects[10]);//��ע
                	 
                	 jsonObject.put("isdoc", isdoc);//�Ƿ񵽳�
                	 
                	 jobiArray.put(i, jsonObject);   
                 } 
                 json.put("code", "200");
      			 json.put("info", "OK");
                 rejson.put("status", json);
          		 rejson.put("data", jobiArray);
		      }
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
	 * ά����ҵ--��ϸ��Ϣ
	 * @param String userid
 	 * @param String numno
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/bydetail/{userid}/{numno}")
	@Produces("application/json")
	public Response bydetail(
			@PathParam("userid") String userid,
			@PathParam("numno") String numno) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>������ҵ-��ϸ��Ϣ");
		
		try{
			session = HibernateUtil.getSession();
			
			String sql="select mwp,mcd.elevatorNo,mcd.maintAddress,c.companyId,c.companyName,mwpm.billno,mcd.elevatorType "
					+ "from MaintenanceWorkPlanDetail mwp,MaintenanceWorkPlanMaster mwpm,"
					+ "MaintContractDetail mcd,MaintContractMaster mcm,Customer c "
					+ "where mwpm.billno=mwp.maintenanceWorkPlanMaster.billno and mwpm.rowid=mcd.rowid "
					+ "and mcm.billNo=mcd.billNo and mcm.companyId=c.companyId and mwp.numno='"+numno+"' "
					+ "and mwp.maintPersonnel = '"+userid+"'";
			Query query = session.createQuery(sql);
            ArrayList mwpList = (ArrayList) query.list();
             //�ж��û��Ƿ���ȷ
            if (mwpList != null && mwpList.size()>0 ) {
                 
                	 Object[] objects=(Object[]) mwpList.get(0);
                	 MaintenanceWorkPlanDetail mwp=(MaintenanceWorkPlanDetail) objects[0];
                	 JSONObject jsonObject = new JSONObject(); 
                	 jsonObject.put("numno", mwp.getNumno());
                	 jsonObject.put("singleno", mwp.getSingleno());
                	 jsonObject.put("elevatorno", objects[1]);
                	 jsonObject.put("handlestatus", mwp.getHandleStatus());
                	 
                	 jsonObject.put("companyid", objects[3]);
                	 jsonObject.put("companyname", objects[4]);
                	 
                	 //��ȡ��һ�α����깤����
                	 String lastmaintdate ="";
//                	 sql="select mwpd.maintEndTime from MaintenanceWorkPlanDetail mwpd "
//                	 	+ "where mwpd.numno=(select numno-1 from MaintenanceWorkPlanDetail where numno='"+mwp.getNumno()+"' "
//                	 	+ "and billno='"+(String) objects[5]+"' and numno !=(select MIN(numno)from MaintenanceWorkPlanDetail "
//                	 	+ "where billno='"+(String) objects[5]+"'))";
                	 
                	sql="select max(maintEndTime) from MaintenanceWorkPlanDetail "
             				+ "where  billno='"+(String) objects[5]+"' "
             				+ "and MaintDate=(select MAX(MaintDate) from MaintenanceWorkPlanDetail "
             				+ "where  billno='"+(String) objects[5]+"' and MaintDate<'"+mwp.getMaintDate()+"')";
                	 
             		 List list=session.createSQLQuery(sql).list();
             	     if(list!=null && list.size()>0){
             	    	lastmaintdate=(String) list.get(0);
             	    	if(lastmaintdate!=null && !"".equals(lastmaintdate)){
             	    		lastmaintdate=lastmaintdate.substring(0, 10);	
             	    	}
             	     }
                	 jsonObject.put("lastmaintdate", lastmaintdate);
                	 
                	 jsonObject.put("maintaddress", objects[2]);
                	 jsonObject.put("maintdate", mwp.getMaintDate());
                	 jsonObject.put("week", mwp.getWeek());
                	 jsonObject.put("mainttype", mwp.getMaintType());
                	 jsonObject.put("receivingtime", mwp.getReceivingTime());
                	 jsonObject.put("maintstarttime", mwp.getMaintStartTime());
                	 jsonObject.put("maintendtime", mwp.getMaintEndTime());
                	 jsonObject.put("maintstartaddress", mwp.getMaintStartAddres());
                	 jsonObject.put("maintendaddress", mwp.getMaintEndAddres());
                	 jsonObject.put("maintDateTime", mwp.getMaintDateTime());//��׼����ʱ��
                	 jsonObject.put("stoptime", mwp.getStopTime());
                	 jsonObject.put("stopaddres", mwp.getStopAddres());
                	 jsonObject.put("restarttime", mwp.getRestartTime());
                	 jsonObject.put("restartaddres", mwp.getRestartAddres());
                	 /*
                	 String r5=mwp.getR5();//����������Ա
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
                	 jsonObject.put("engcyuserid", mwp.getR5());
                	 jsonObject.put("engcyusername", mwp.getR5());//����������Ա
                	 jsonObject.put("byrem", mwp.getR4());//������ע
                	 

                	//����Ƿ��б����еĵ���,�ų���ͣ��,'4'
         			String cjsql="select numno,HandleStatus from MaintenanceWorkPlanDetail " +
         					"where HandleStatus in('2') and maintPersonnel = '"+userid+"' ";
         			List checklist=session.createSQLQuery(cjsql).list();
         			if(checklist!=null && checklist.size()>0){
                		 jsonObject.put("isdoc", "Y");
                	 }else{
                		 jsonObject.put("isdoc", "N");
                	 }
                	 
                	 //���ݻ�������
                	 String jcsql="from ElevatorCoordinateLocation where elevatorNo='"+objects[1].toString()+"'";
                	 List jclist=session.createQuery(jcsql).list();
                	 if(jclist!=null && jclist.size()>0){
                		 ElevatorCoordinateLocation ecl=(ElevatorCoordinateLocation) jclist.get(0);

                		 if(ecl.getBeginLongitude()==null){
                    		 jsonObject.put("elongitude", 0);//����
                		 }else{
                    		 jsonObject.put("elongitude", ecl.getBeginLongitude());//����
                		 }
                		 if(ecl.getBeginDimension()==null){
                        	 jsonObject.put("elatitude", 0);//γ��
                		 }else{
                        	 jsonObject.put("elatitude", ecl.getBeginDimension());//γ��
                		 }
                		 jsonObject.put("maintrem", ecl.getRem());//��Ŀ���Ƽ�¥����
                	 }else{
                    	 jsonObject.put("elongitude", 0);//����
                    	 jsonObject.put("elatitude", 0);//γ��
                    	 jsonObject.put("maintrem", "");//��Ŀ���Ƽ�¥����
                	 }
                	 
                	 String maintType=mwp.getMaintType();
                	 if(maintType.trim().equals("halfMonth")){
                		 jsonObject.put("maintType", "���±���"); 
                	 }else if(maintType.trim().equals("quarter")){
                		 jsonObject.put("maintType", "���ȱ���"); 
                	 }else if(maintType.trim().equals("halfYear")){
                		 jsonObject.put("maintType", "���걣��"); 
                	 }else if(maintType.trim().equals("yearDegree")){
                		 jsonObject.put("maintType", "��ȱ���");
                	 }
                	 
                	 /**=====================��ȡ���ص��ݱ�����¼��·��=========================*/
      				String path="D:\\contract\\���ص��ݱ�����¼��·��.txt";
      				BufferedReader reader= new BufferedReader(new FileReader(path));
      				String downloadaddr=reader.readLine();
      				reader.close();
      				//String downloadaddr="http://10.10.0.5:8080/XJSCRM/PrintMaintenanceWorkPlanServlet?id=";//����
      				//String downloadaddr="http://www.xjelevator.com:9000/XJSCRM/PrintMaintenanceWorkPlanServlet?id=";//��ʽ
      				if(downloadaddr!=null && !downloadaddr.trim().equals("")){
      					jsonObject.put("downloadaddr", downloadaddr.trim()+mwp.getNumno());//���ݱ�������¼���ص�ַ
      				}else{
      					jsonObject.put("downloadaddr", "");//���ص��ݱ�����¼��·��
      				}
                 	 
                 	/**=====================��ȡͼƬ=========================*/
      				String folder = PropertiesUtil.getProperty("MaintenanceWorkPlanDetail.file.upload.folder");
      				BASE64Encoder base=new BASE64Encoder();
      				//�ͻ�ǩ��
      				if(mwp.getCustomerSignature()!=null && !mwp.getCustomerSignature().trim().equals("")){
      					String filepath=folder+mwp.getCustomerSignature();
      					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������
      					jsonObject.put("customersignature", base.encode(imgbyte));//��������������
      				}else{
      					jsonObject.put("customersignature", "");
      				}
      				//�ͻ���Ƭ
      				if(mwp.getCustomerImage()!=null && !mwp.getCustomerImage().trim().equals("")){
      					String filepath=folder+mwp.getCustomerImage();
      					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������		
      					jsonObject.put("customerimage", base.encode(imgbyte));//��������������
      				}else{
      					jsonObject.put("customerimage", "");
      				}
      				/**=====================��ȡͼƬ=========================*/
      				
      				/**=====================��ȡά����Ŀ��Ϣ=========================*/
      				JSONArray detaillist=new JSONArray();
      				if(mwp.getHandleStatus()!=null && mwp.getHandleStatus().equals("3")){
      					//���깤��
      					String hql ="select mpiw from  MaintainProjectInfoWork mpiw " +
      							"where mpiw.singleno='"+mwp.getSingleno()+"' " +
      							"order by mpiw.orderby,mpiw.maintItem";			
      				    List infoList=session.createQuery(hql).list();
	      				if(infoList!=null && infoList.size()>0){
	  						for(int i=0;i<infoList.size();i++){
	  							MaintainProjectInfoWork mpif=(MaintainProjectInfoWork) infoList.get(i);
	  							
	  							JSONObject object2=new JSONObject();
	  							object2.put("orderby", mpif.getOrderby());//�����
	  							object2.put("maintitem", mpif.getMaintItem());//��ά����Ŀ
	  							object2.put("maintcontents", mpif.getMaintContents());//ά������Ҫ��
	  							object2.put("ismaintain", mpif.getIsMaintain());//�Ƿ���
	  							
	  							detaillist.put(i, object2);
	  						}
	  					}
      				}else{
      					String maintsql="select a from MaintainProjectInfo a " +
                    	 		"where a.elevatorType='"+objects[6].toString()+"' " +
                    	 		"and maintType='"+maintType+"' and a.enabledFlag='Y' " +
                    	 		"order by a.orderby,a.maintItem";
                    	 List hList=session.createQuery(maintsql).list();
                    	 
                    	 if(hList!=null && hList.size()>0){
    						for(int i=0;i<hList.size();i++){
    							MaintainProjectInfo mpi=(MaintainProjectInfo) hList.get(i);
    							
    							JSONObject object2=new JSONObject();
    							object2.put("orderby", mpi.getOrderby()+"");//�����
    							object2.put("maintitem", mpi.getMaintItem());//��ά����Ŀ
    							object2.put("maintcontents", mpi.getMaintContents());//ά������Ҫ��
    							object2.put("ismaintain", "Y");//�Ƿ���
    							
    							detaillist.put(i, object2);
    						}
    					}
      				}
                	jsonObject.put("detaillist",detaillist);
                	
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
	 * ά����ҵ--����
	 * @param JSONObject bydata
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/bysave")
	@Produces("application/json")
	public Response saveHandleStatus(@FormParam("bydata") JSONObject bydata) throws JSONException{
		
		Session session = null;
		Transaction tx = null;
		MaintenanceWorkPlanDetail mwpd=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []

		System.out.println(">>>>>>>>������ҵ-����");
		
		try{
			String userid=(String) bydata.get("userid");//��¼�û�id
			int numno=(Integer) bydata.get("numno");//ά���ƻ���ϸ�к�
			String check_start=(String) bydata.get("check_start");//����״̬
			//String setDate=(String) bydata.get("setDate");//����ʱ��
			
			//System.out.println("check_start==="+check_start); 

			String elevatorno=(String) bydata.get("elevatorno");//���ݱ��
			String iseleno="Y";//�������Ƿ񱣴浽���ݻ�������
			
			String address=""; 
			//�ж� JSONObject �Ƿ����  address
			if(bydata.has("address")){
				address=(String) bydata.get("address");//��ַ
			}
			String rem="";
			//�ж� JSONObject �Ƿ����  maintrem
			if(bydata.has("maintrem")){
				rem=(String) bydata.get("maintrem");//��Ŀ���Ƽ�¥����
			}
			
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String curdate=DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss");
			
			//���ݻ�������
			ElevatorCoordinateLocation ecl=null;
	       	 String jcsql="from ElevatorCoordinateLocation where elevatorNo='"+elevatorno+"'";
	       	 List jclist=session.createQuery(jcsql).list();
	       	 if(jclist!=null && jclist.size()>0){
	       		 ecl=(ElevatorCoordinateLocation)jclist.get(0);
	       		 if(ecl.getBeginLongitude()!=null && !ecl.getBeginLongitude().equals("")){
	 	       		iseleno="N";//����
	       		 }else{
	       			iseleno="M";//�޸�
	       		 }
	       	 }
	       	//System.out.println(">>>>>>>>������ҵ-ת�� userid="+userid+";check_start="+check_start+";numno="+numno);
	       	
			String hql="from MaintenanceWorkPlanDetail mwpd where mwpd.numno ='"+numno+"' " +
					"and mwpd.maintPersonnel='"+userid.trim()+"'";
			List list=session.createQuery(hql).list();
			if(list!=null&&list.size()>0){
				mwpd=(MaintenanceWorkPlanDetail) list.get(0);
				
				// 0 ��ת�ɣ�1 �ѽ��գ�2  �ѵ�����3 ���깤��4  ��ͣ��5  ����
				if(check_start.equals("0") || check_start=="0")//ת��
				{
				    String newuserid=(String) bydata.get("newuserid");//ת����Id
				    String newusertel=(String) bydata.get("newusertel");//ת���˵绰
				    //System.out.println(">>>>>>>>������ҵ-ת�� newuserid="+newuserid+";newusertel="+newusertel);
				    
					mwpd.setIsTransfer("Y");
					mwpd.setTransferDate(curdate);
					mwpd.setMaintPersonnel(newuserid);
					mwpd.setReceivingPerson(newuserid);
					mwpd.setReceivingPhone(newusertel);
					mwpd.setHandleStatus("0");
				}
				else if(check_start.equals("1") || check_start=="1")//����
				{
					mwpd.setReceivingTime(curdate);
					mwpd.setHandleStatus("1");
				}
				else if(check_start.equals("2") || check_start=="2")//����
				{
					double gpslon=(Double) bydata.get("gpslon");//����
					double gpslat=(Double) bydata.get("gpslat");//γ��
					double lon=(Double) bydata.get("lon");//����
					double lat=(Double) bydata.get("lat");//γ��
					String distance=(String) bydata.get("distance");//����
					
					String singleno=CommonUtil.getNewSingleno(session, userid);//���ɵ���
					mwpd.setSingleno(singleno);
					mwpd.setBeginLongitude(lon);
					mwpd.setBeginDimension(lat);
					mwpd.setBeginLongitudeGPS(gpslon);//GPSԭʼ���꾭��
					mwpd.setBeginDimensionGPS(gpslat);//GPSԭʼ����γ��
					mwpd.setMaintStartAddres(address);
					mwpd.setMaintStartTime(curdate);
					mwpd.setHandleStatus("2");
					mwpd.setStartDistance(Double.parseDouble(distance));
					
					//������ݻ������� ���ݵ��������λ��
					if(iseleno.equals("Y")){
						//����
						ecl=new ElevatorCoordinateLocation();
						ecl.setElevatorNo(elevatorno);
						ecl.setElevatorLocation(address);
						ecl.setBeginLongitude(lon);
						ecl.setEndLongitude(lon);
						ecl.setBeginDimension(lat);
						ecl.setEndDimension(lat);
						ecl.setEnabledFlag("Y");
						ecl.setOperId(userid);
						ecl.setOperDate(curdate);
						session.save(ecl);
					}else if(iseleno.equals("M")){
						//�޸�
						ecl.setElevatorLocation(address);
						ecl.setBeginLongitude(lon);
						ecl.setEndLongitude(lon);
						ecl.setBeginDimension(lat);
						ecl.setEndDimension(lat);
						ecl.setOperId(userid);
						ecl.setOperDate(curdate);
						session.update(ecl);
					}
				}
				else if(check_start.equals("3") || check_start=="3")//�깤
				{
					double gpslon=(Double) bydata.get("gpslon");//����
					double gpslat=(Double) bydata.get("gpslat");//γ��
					double lon=(Double) bydata.get("lon");//����
					double lat=(Double) bydata.get("lat");//γ��
					String distance=(String) bydata.get("distance");//����
					String engcyusername=(String) bydata.get("engcyusername");//����������Ա����
					String byrem=(String) bydata.get("byrem");//����������Ա
					
					mwpd.setEndLongitude(lon);
					mwpd.setEndDimension(lat);
					mwpd.setEndLongitudeGPS(gpslon);//GPSԭʼ���꾭��
					mwpd.setEndDimensionGPS(gpslat);//GPSԭʼ����γ��
					mwpd.setMaintEndAddres(address);
					mwpd.setMaintEndTime(curdate);
					mwpd.setHandleStatus("3");
					mwpd.setEndDistance(Double.parseDouble(distance));
					mwpd.setR4(byrem);
					mwpd.setR5(engcyusername);
					
					//��������ʱ��,���� �������֣�����ʱ�����֣������÷�
					this.toCalculation(mwpd);
					
					/**=======================����ͼƬ=============================*/
					String folder = PropertiesUtil.getProperty("MaintenanceWorkPlanDetail.file.upload.folder");
					String filepath=CommonUtil.getNowTime("yyyy-MM-dd")+"/";
					//����ǩ��
					String customersignature=(String)bydata.get("customersignature");
					if(customersignature!=null && !customersignature.trim().equals("")){
						//customersignature=customersignature.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
						String[] signatures=customersignature.split(",");
						if(signatures!=null && signatures.length>1){
							byte[] image=new BASE64Decoder().decodeBuffer(signatures[1]);
							String newfilename=mwpd.getSingleno()+"_0.jpg";
							//����ͼƬ
							File f=new File(folder+filepath);
							f.mkdirs();
							FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
							fos.write(image);
							fos.flush();
							fos.close();
							//����ͼƬ��Ϣ�����ݿ�
							mwpd.setCustomerSignature(filepath+newfilename);
						}
					}
					//��������
					String customerimage=(String)bydata.get("customerimage");
					if(customerimage!=null && !customerimage.trim().equals("")){
						//customerimage=customerimage.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
						String[] cimages=customerimage.split(",");
						if(cimages!=null && cimages.length>1){
							byte[] image=new BASE64Decoder().decodeBuffer(cimages[1]);
							String newfilename=mwpd.getSingleno()+"_1.jpg";
							//����ͼƬ
							File f=new File(folder+filepath);
							f.mkdirs();
							FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
							fos.write(image);
							fos.flush();
							fos.close();
							//����ͼƬ��Ϣ�����ݿ�
							mwpd.setCustomerImage(filepath+newfilename);
						}
					}
					/**=======================����ͼƬ=============================*/
					
					/**=======================����ά����Ŀ��ϸ=============================*/
					JSONArray detaillist=(JSONArray) bydata.get("detaillist");//ά����Ŀ��ϸ
					if(detaillist!=null && detaillist.length()>0){
						
						//��ɾ�� ��ͣʱ������ı�����Ŀ��Ϣ
						//String sql3 = "delete from MaintainProjectInfoWork where Singleno='"+mwpd.getSingleno()+"'";
						//session.connection().prepareStatement(sql3).executeUpdate();
						
						for(int i=0;i<detaillist.length();i++){
							JSONObject object=(JSONObject) detaillist.get(i);
							String orderby = (String) object.get("orderby");//�����
							String maintitem = (String) object.get("maintitem");//ά����Ŀ
							String maintcontents = (String) object.get("maintcontents");//ά������Ҫ��
							String ismaintain = (String) object.get("ismaintain");//�Ƿ���
							
							MaintainProjectInfoWork mpiw=new MaintainProjectInfoWork();
							mpiw.setSingleno(mwpd.getSingleno());//��������
							mpiw.setOrderby(new Integer(orderby));
							mpiw.setMaintItem(maintitem);
							mpiw.setMaintContents(maintcontents);
							mpiw.setIsMaintain(ismaintain);
							session.save(mpiw);
						}
					}
					
					//������ݻ������� ���ݵ��������λ��
					if(iseleno.equals("Y")){
						//����
						ecl=new ElevatorCoordinateLocation();
						ecl.setElevatorNo(elevatorno);
						ecl.setElevatorLocation(address);
						ecl.setBeginLongitude(lon);
						ecl.setEndLongitude(lon);
						ecl.setBeginDimension(lat);
						ecl.setEndDimension(lat);
						ecl.setRem(rem);
						ecl.setEnabledFlag("Y");
						ecl.setOperId(userid);
						ecl.setOperDate(curdate);
						session.save(ecl);
					}else if(iseleno.equals("M")){
						//�޸�
						ecl.setElevatorLocation(address);
						ecl.setBeginLongitude(lon);
						ecl.setEndLongitude(lon);
						ecl.setBeginDimension(lat);
						ecl.setEndDimension(lat);
						ecl.setRem(rem);
						ecl.setOperId(userid);
						ecl.setOperDate(curdate);
						session.update(ecl);
					}else{
						ecl.setRem(rem);
						//ecl.setOperId(userid);
						//ecl.setOperDate(curdate);
						session.update(ecl);
					}
				}
				else if(check_start.equals("4") || check_start=="4")//��ͣ
				{
					double gpslon=(Double) bydata.get("gpslon");//����
					double gpslat=(Double) bydata.get("gpslat");//γ��
					double lon=(Double) bydata.get("lon");//����
					double lat=(Double) bydata.get("lat");//γ��
					String distance=(String) bydata.get("distance");//����

					mwpd.setHandleStatus("4");
					mwpd.setStopTime(curdate);
					mwpd.setStopLongitude(lon);
					mwpd.setStopDimension(lat);
					mwpd.setStopLongitudeGPS(gpslon);//GPSԭʼ���꾭��
					mwpd.setStopDimensionGPS(gpslat);//GPSԭʼ����γ��
					mwpd.setStopAddres(address);
					mwpd.setStopDistance(Double.parseDouble(distance));
				}
				else if(check_start.equals("5") || check_start=="5")//����
				{
					//��ͣ������
					double gpslon=(Double) bydata.get("gpslon");//����
					double gpslat=(Double) bydata.get("gpslat");//γ��
					double lon=(Double) bydata.get("lon");//����
					double lat=(Double) bydata.get("lat");//γ��
					String distance=(String) bydata.get("distance");//����

					mwpd.setHandleStatus("2");
					mwpd.setRestartTime(curdate);
					mwpd.setRestartLongitude(lon);
					mwpd.setRestartDimension(lat);
					mwpd.setRestartLongitudeGPS(gpslon);//GPSԭʼ���꾭��
					mwpd.setRestartDimensionGPS(gpslat);//GPSԭʼ����γ��
					mwpd.setRestartAddres(address);
					mwpd.setRestartDistance(Double.parseDouble(distance));
				}

				session.update(mwpd);
			}
			tx.commit();

			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			if(tx!=null){
				tx.rollback();
			}
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
	 * ά���ɹ�
	 * @param String userid
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/bypglist/{userid}")
	@Produces("application/json")
	public Response bypglist(@PathParam("userid") String userid) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		System.out.println(">>>>>>>>������ҵ-�ɹ���Ա");
		
		try{
			session = HibernateUtil.getSession();
			con=session.connection();

			String sql="exec C_pgList "+userid;

			ps=con.prepareStatement(sql);
			rs=ps.executeQuery();
			int i=0;
           while(rs.next()){
          	 JSONObject jsonObject = new JSONObject(); 
          	 jsonObject.put("newuserid",rs.getString("userid"));
          	 jsonObject.put("newusername",rs.getString("username"));
          	 jsonObject.put("newusertel",rs.getString("usertel"));
          	 jsonObject.put("storageid", rs.getString("storageid"));
          	 jsonObject.put("storagename",rs.getString("storagename"));
          	 jsonObject.put("comid", rs.getString("comid"));
          	 jsonObject.put("comname",rs.getString("comname"));
          	 jsonObject.put("rolename",rs.getString("rolename"));
             jobiArray.put(i, jsonObject); 
        	   i++;
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
	
	//��������ʱ��������ʱ������,�������֣������÷�
	private void toCalculation(MaintenanceWorkPlanDetail mwpd){
		
		//��������ʱ��
		double usedDuration=CommonUtil.getMinute(mwpd.getMaintStartTime(), mwpd.getMaintEndTime());
		//��ͣʱ��
		double stopduration=0;
		if(mwpd.getStopTime()!=null && !mwpd.getStopTime().equals("")){
			stopduration=CommonUtil.getMinute(mwpd.getStopTime(), mwpd.getRestartTime());
		}
		//����ʱ����ȥ��ͣʱ��
		usedDuration=usedDuration-stopduration;
 	    mwpd.setUsedDuration(usedDuration);
 	    
 	    /**����ʱ������
 	      	����ʱ�����ֱ�׼						����
 	      0.9��׼��ʱ~ 2��׼��ʱ					10��
 	      0.8��׼��ʱ~0.9��׼��ʱ ���� 2��׼��ʱ~3��׼��ʱ	8��
 	      0.7��׼��ʱ~0.8��׼��ʱ ���� 3��׼��ʱ~4��׼��ʱ	7��
 	      0.6��׼��ʱ~0.7��׼��ʱ ���� 4��׼��ʱ~5��׼��ʱ	6��
 	      15���� �� 0.6��׼��ʱ						5��
 	      15�������� ���� 5��׼��ʱ����					0��
 	     */
 	    double datescore=0d;//����ʱ������
 	    double maintdatetime=Double.parseDouble(mwpd.getMaintDateTime());//��׼����ʱ��
 	    double score=usedDuration/maintdatetime;
 	    if(score>=0.9 && usedDuration<=(maintdatetime*2)){
 	    	datescore=10;//0.9��׼��ʱ~ 2��׼��ʱ
 	    }else if(score>=0.8 && score<0.9){
 	    	datescore=8;//0.8��׼��ʱ~0.9��׼��ʱ
 	    }else if(usedDuration>(maintdatetime*2) && usedDuration<=(maintdatetime*3)){
 	    	datescore=8;//2��׼��ʱ~3��׼��ʱ
 	    }else if(score>=0.7 && score<0.8){
 	    	datescore=7;//0.7��׼��ʱ~0.8��׼��ʱ
 	    }else if(usedDuration>(maintdatetime*3) && usedDuration<=(maintdatetime*4)){
 	    	datescore=7;//3��׼��ʱ~4��׼��ʱ
 	    }else if(score>=0.6 && score<0.7){
 	    	datescore=6;//0.6��׼��ʱ~0.7��׼��ʱ
 	    }else if(usedDuration>(maintdatetime*4) && usedDuration<=(maintdatetime*5)){
 	    	datescore=6;//4��׼��ʱ~5��׼��ʱ
 	    }else if(usedDuration>=15 && score<0.6){
 	    	datescore=5;//15���� �� 0.6��׼��ʱ
 	    }else{
 	    	datescore=0;
 	    }
 	    mwpd.setDateScore(datescore);//����ʱ������
 	    
 	    /**��������   [���ξ�������ƽ��ֵ] 
		  	�������ֱ�׼	����
			0-200m	10��
			200-500	5��
			500������	0��
	     */
 	    double distancescore=0;
 	    double startdistance=0;//������ʼ����(��)
 	    if(mwpd.getStartDistance()!=null){
 	    	startdistance=mwpd.getStartDistance();
 	    }
 	    double enddistance=0;//������������(��)
 	    if(mwpd.getEndDistance()!=null){
 	    	enddistance=mwpd.getEndDistance();//������������(��)
 	    }
 	    int avgnum=2;
 	    double stopDistance=0;//��ͣ����(��)
 	    if(mwpd.getStopDistance()!=null){
 	    	stopDistance=mwpd.getStopDistance();
 	    	avgnum++;
 	    }
 	    double restartDistance=0;//��������(��)
 	    if(mwpd.getRestartDistance()!=null){
 	    	restartDistance=mwpd.getRestartDistance();
 	    	avgnum++;
 	    }
 	    double distance=(startdistance+enddistance+stopDistance+restartDistance)/avgnum;//ƽ��ֵ
 	    
 	    if(distance>=0 && distance<=200){
 	    	distancescore=10;//0-200m	10��
 	    }else if(distance>200 && distance<=500){
 	    	distancescore=5;//200-500	5��
 	    }else{
 	    	distancescore=0;
 	    }
 	    mwpd.setDistanceScore(distancescore);//��������
 	    
 	    /**�����÷� 
 	     * ��һһ��÷�Ϊ0�֣����ܷ�Ϊ0�֡�
 	     * 60%ʱ������+40%��������
 	     */
 	    double maintscore=0;
 	    if(datescore>0 && distancescore>0){
 	    	maintscore=datescore*0.6+distancescore*0.4;
 	    }
 	    mwpd.setMaintScore(maintscore);//�����÷� 
 	    
	}
	
}
