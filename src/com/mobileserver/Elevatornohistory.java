package com.mobileserver;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
import org.zefer.html.doc.p;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.hibernate.hotlinemanagement.calloutmaster.CalloutMaster;
import com.gzunicorn.hibernate.hotlinemanagement.calloutprocess.CalloutProcess;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplandetail.MaintenanceWorkPlanDetail;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.struts.action.xjsgg.XjsggAction;
/**
 * �ֻ�APP�˵��ã���ʷ�鿴��
 * @author Elevatortrans
 */
@Path("/Elevatornohistory")
public class Elevatornohistory {
	
	BaseDataImpl bd = new BaseDataImpl();
	 /* ά����ʷ */
            /**
             * ������ʷ-�б�
             * @param userid
             * @param elevatorno
             * @return
             */
			@GET
			@Path("/bylist/{userid}/{elevatorno}")
			@Produces("application/json")
			public Response getByList(
					@PathParam("userid") String userid, 
					@PathParam("elevatorno") String elevatorno
					){
				Session session = null;
				Transaction tx = null;
				//Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();
					//tx = session.beginTransaction();
					String sql="from MaintenanceWorkPlanDetail m where m.maintDate < '"+CommonUtil.getNowTime("yyyy-MM-dd")+"'";
					if(elevatorno!=null&&!"".equals(elevatorno.trim())){
						sql+=" m.maintenanceWorkPlanMaster.elevatorNo ='"+elevatorno.trim()+"'";
					}
					sql +=" order by m.numno";
					List list =session.createQuery(sql).list();
					
					if(list!=null&&list.size()>0){
						for(int i=0;i<list.size();i++){
							MaintenanceWorkPlanDetail m=(MaintenanceWorkPlanDetail) list.get(i);
						    JSONObject jsonObject=new JSONObject();
						    jsonObject.put("numno", m.getNumno());//ά����ҵ�ƻ�����ϸ���
						    jsonObject.put("elevatorno", m.getMaintenanceWorkPlanMaster().getElevatorNo());//���ݱ��
						    jsonObject.put("maintdate", m.getMaintDate());//��������
						    jsonObject.put("mainttype", m.getMaintType());//��������
						    jobiArray.put(i,jsonObject);
						}
					}
					json.put("code", "200");
		  			json.put("info", "OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
				}catch(Exception ex){
					tx.rollback();
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
			 * ������ʷ-�鿴
			 * @param userid
			 * @param numno
			 * @return
			 */
			@GET
			@Path("/bydisplay/{userid}/{numno}")
			@Produces("application/json")
			public Response getByDisplay(
					@PathParam("userid") String userid, 
					@PathParam("numno") String numno
					){
				Session session = null;
				Transaction tx = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();
					//tx = session.beginTransaction();
					String sql="from MaintenanceWorkPlanDetail m where m.numno = '"+numno.trim()+"'";
					List list =session.createQuery(sql).list();
					if(list!=null&&list.size()>0){
						
							MaintenanceWorkPlanDetail m=(MaintenanceWorkPlanDetail) list.get(0);
						    JSONObject jsonObject=new JSONObject();
						   
						    jsonObject.put("numno", m.getNumno());//ά����ҵ�ƻ�����ϸ���
						    jsonObject.put("elevatorno", m.getMaintenanceWorkPlanMaster().getElevatorNo());//���ݱ��
						    jsonObject.put("maintdate", m.getMaintDate());//��������
						    jsonObject.put("week", m.getWeek());//����
						    jsonObject.put("mainttype", m.getMaintType());//��������
						    jsonObject.put("maintpersonnel", bd.getName(session, "Loginuser", "username", "userid", m.getMaintPersonnel()));//����ά����
						    jsonObject.put("rem", m.getRem());//��ע
						    jobiArray.put(0,jsonObject);
						
					}
					json.put("code", "200");
		  			json.put("info", "OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
				}catch(Exception ex){
					tx.rollback();
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
			 * ������ʷ-�б�
             * @param userid
             * @param elevatorno
             * @return
             */
			@GET
			@Path("/jxlist/{userid}/{elevatorno}/{repairtime}")
			@Produces("application/json")
			public Response getJxList(
					@PathParam("userid") String userid, 
					@PathParam("elevatorno") String elevatorno,
					@PathParam("repairtime") String repairtime){
				Session session = null;
				Transaction tx = null;
				//Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();
					//tx = session.beginTransaction();
					String sql="select c,cp from CalloutMaster c,CalloutProcess cp where c.calloutMasterNo=cp.calloutMasterNo and c.repairTime < '"+CommonUtil.getNowTime("yyyy-MM-dd")+"'";
					if(elevatorno!=null&&!"".equals(elevatorno.trim())){
						sql+=" c.elevatorNo ='"+elevatorno.trim()+"'";
					}
					if(repairtime!=null&&!"".equals(repairtime.trim())){
						sql+=" c.repairtime ='"+repairtime.trim()+"'";
					}
					sql +=" order by c.calloutMasterNo";
					List list =session.createQuery(sql).list();

				    if(list!=null&&list.size()>0){
				    	for(int i=0;i<list.size();i++){
				    		JSONObject jsonObject=new JSONObject();
				    		Object[] objects=(Object[]) list.get(i);
				    		
				    		CalloutMaster c=(CalloutMaster) objects[0];
				    		CalloutProcess cp=(CalloutProcess) objects[1];
				    		jsonObject.put("calloutmasterno", c.getCalloutMasterNo());//���޵���
				    		jsonObject.put("elevatorno", c.getElevatorNo());//���ݱ��
				    		jsonObject.put("repairtime", c.getRepairTime());//��������
				    		jsonObject.put("completetime", cp.getCompleteTime());//ά�����ʱ�� 
				    		jobiArray.put(i, jsonObject);
				    	}
				    }
					json.put("code", "200");
		  			json.put("info", "OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
				}catch(Exception ex){
					tx.rollback();
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
			
			
			 /* ������ʷ */
			
			/**
			 * ������ʷ-�鿴
			 * @param userid
			 * @param numno
			 * @return
			 */
			@GET
			@Path("/jxdisplay/{userid}/{calloutmasterno}")
			@Produces("application/json")
			public Response getJxDisplay(
					@PathParam("userid") String userid, 
					@PathParam("calloutmasterno") String calloutmasterno
					){
				Session session = null;
				Transaction tx = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();
					//tx = session.beginTransaction();
					String sql="select c,cp from CalloutMaster c,CalloutProcess cp where "
							+ "c.calloutMasterNo=cp.calloutMasterNo "
							+ "and c.calloutMasterNo ='"+calloutmasterno.trim()+"'";
					List list =session.createQuery(sql).list();
				    if(list!=null&&list.size()>0){
				    		JSONObject jsonObject=new JSONObject();
				    		Object[] objects=(Object[]) list.get(0);
				    		
				    		CalloutMaster c=(CalloutMaster) objects[0];
				    		CalloutProcess cp=(CalloutProcess) objects[1];
				    		jsonObject.put("calloutmasterno", c.getCalloutMasterNo());//���޵���
				    		jsonObject.put("elevatorno", c.getElevatorNo());//���ݱ��
				    		jsonObject.put("repairtime", c.getRepairTime());//��������
				    		jsonObject.put("completetime", cp.getCompleteTime());//ά�����ʱ�� 
				    		
				    		jsonObject.put("repairmode", c.getRepairMode());//���޷�ʽ
				    		jsonObject.put("repairuser", c.getRepairUser());//������
				    		jsonObject.put("repairtel", c.getRepairTel());//�����˵绰
				    		jsonObject.put("serviceobjects", c.getServiceObjects());//�������
				    		jsonObject.put("companyid", c.getCompanyId());//���޵�λ
				    		jsonObject.put("projectaddress", c.getProjectAddress());//��Ŀ��ַ
				    		jsonObject.put("repairdesc", c.getRepairDesc());//��������
				    		jsonObject.put("processdesc", cp.getProcessDesc());//ά�޹�������
				    		jsonObject.put("hftid", cp.getHftId());//���Ϸ������
				    		jsonObject.put("hftname", bd.getName(session, "HotlineFaultType", "hftDesc", "hftId", cp.getHftId()));//���Ϸ�������
				    		jsonObject.put("assignobject2",  bd.getName(session, "Loginuser", "username", "userid", cp.getAssignObject2()));//�����ɹ�����
				    		jsonObject.put("servicerem", cp.getServiceRem());//ά�ޱ�ע
				    		jsonObject.put("assigntime", cp.getAssignTime());//����ʱ��
				    		jsonObject.put("arrivedatetime", cp.getArriveDateTime());//���ֳ�ʱ��
				    		jobiArray.put(0, jsonObject);
				    
				    }
					json.put("code", "200");
		  			json.put("info", "OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
				}catch(Exception ex){
					tx.rollback();
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
			
			
}
