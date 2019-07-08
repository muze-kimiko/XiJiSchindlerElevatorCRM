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
import com.gzunicorn.hibernate.basedata.customer.Customer;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.hotlinemanagement.calloutmaster.CalloutMaster;
import com.gzunicorn.hibernate.hotlinemanagement.calloutprocess.CalloutProcess;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplandetail.MaintenanceWorkPlanDetail;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.struts.action.xjsgg.XjsggAction;
/**
 * �ֻ�APP�˵��ã���ͬ��Ϣ��ѯ��
 * @author Maintcontractinfo
 */
@Path("/Maintcontractinfo")
public class Maintcontractinfo {
	
	BaseDataImpl bd = new BaseDataImpl();
	
            /* ����  */
            /**
             * ����-�б�
             * @param data
             * @return
             */
			@GET
			@Path("/bynslist/{data}")
			@Produces("application/json")
			public Response getNsList(
					@PathParam("data") JSONObject data
					){
				Session session = null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				
				System.out.println(">>>����-�б�");
				try{
					
					 String userid=(String) data.get("userid");
					 String maintcontractno=CommonUtil.URLDecoder_decode((String) data.get("maintcontractno"));
					 String elevatorno=CommonUtil.URLDecoder_decode((String) data.get("elevatorno"));
					 String companyname=CommonUtil.URLDecoder_decode((String) data.get("companyname"));
					 
					session = HibernateUtil.getSession();
					//tx = session.beginTransaction();
					String sql="select mcm,mcd,c.companyName from MaintContractDetail mcd,MaintContractMaster mcm,Customer c "
							+ "where mcd.billNo=mcm.billNo "
							+ " and c.companyId=mcm.companyId"
							+ " and mcm.contractStatus in ('ZB','XB')";
					if(maintcontractno!=null&&!maintcontractno.trim().equals("")){
						sql+=" and mcm.maintContractNo like '%"+maintcontractno.trim()+"%'";
					}
					if(elevatorno!=null&&!elevatorno.trim().equals("")){
						sql+=" and mcd.elevatorNo like '%"+elevatorno.trim()+"%'";
					}
					if(companyname!=null&&!companyname.trim().equals("")){
						sql+=" and (c.companyId like '"+companyname.trim()+"%' or c.companyName like '"+companyname.trim()+"%')";
					}
					
					List list =session.createQuery(sql).list();
					if(list!=null&&list.size()>0){
						for(int i=0;i<list.size();i++){
							JSONObject jsonObject=new JSONObject();
							Object[] objects=(Object[]) list.get(i);
							MaintContractMaster mcm=(MaintContractMaster) objects[0];        
							MaintContractDetail mcd=(MaintContractDetail) objects[1];
							
							jsonObject.put("rowid", mcd.getRowid());//ά����ͬ��ϸ�к�
							jsonObject.put("maintcontractno", mcm.getMaintContractNo());//ά����ͬ��
							jsonObject.put("elevatorno", mcd.getElevatorNo());//���ݱ��
							jsonObject.put("annualinspectiondate", mcd.getAnnualInspectionDate());//�������
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
			 * ����-�鿴
			 * @param userid
			 * @param rowid
			 * @return
			 */
			@GET
			@Path("/bynsdetail/{userid}/{rowid}")
			@Produces("application/json")
			public Response getNsDisplay(
					@PathParam("userid") String userid,
					@PathParam("rowid") String rowid
					){
				Session session = null;
				Transaction tx = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				
				System.out.println(">>>����-�鿴");
				
				try{
					session = HibernateUtil.getSession();
					List typelist=bd.getPullDownList("ElevatorSalesInfo_type");
					String sql="select mcm,mcd,c from MaintContractDetail mcd,MaintContractMaster mcm,Customer c "
							+ "where mcd.billNo=mcm.billNo "
							+ " and c.companyId=mcm.companyId and mcd.rowid='"+rowid.trim()+"'";
					List list =session.createQuery(sql).list();
					if(list!=null&&list.size()>0){
							JSONObject jsonObject=new JSONObject();
							Object[] objects=(Object[]) list.get(0);
							MaintContractMaster mcm=(MaintContractMaster) objects[0];        
							MaintContractDetail mcd=(MaintContractDetail) objects[1];
							Customer c=(Customer) objects[2];
		            		jsonObject.put("rowid", mcd.getRowid());//ά����ͬ��ϸ�к�
		            		jsonObject.put("companyname", c.getCompanyName());//�׷���λ����
		            		jsonObject.put("contacts", c.getContacts());//��ϵ��
		            		jsonObject.put("contactphone", c.getContactPhone());//��ϵ�˵绰
							jsonObject.put("maintcontractno", mcm.getMaintContractNo());//ά����ͬ��
							jsonObject.put("elevatorno", mcd.getElevatorNo());//���ݱ��
							jsonObject.put("annualinspectiondate", mcd.getAnnualInspectionDate());//�������
							jsonObject.put("maintdivision",bd.getName(session, "Company", "comfullname", "comid",mcm.getMaintDivision() ));
		            	  	jsonObject.put("maintstation",bd.getName(session, "Storageid", "storagename", "storageid",mcm.getMaintStation()));
		            		jsonObject.put("elevatortype", bd.getOptionName(mcd.getElevatorType(), typelist));//��������
		            		
							jobiArray.put(0, jsonObject);
					}
					json.put("code", "200");
		  			json.put("info", "OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
				}catch(Exception ex){
					//tx.rollback();
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

			/* ��ͬ��Ч��  */
			/**
			 * ��ͬ��Ч��(����)-�б�
			 * @param userid
			 * @param maintcontractno
			 * @param elevatorno
			 * @param companyname
			 * @return
			 */
			@GET
			@Path("/yxqlist/{data}")
			@Produces("application/json")
			public Response getYxqList(
					@PathParam("data") JSONObject data
					){
				Session session = null;
				Transaction tx = null;
				//Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				
				System.out.println(">>>��ͬ��Ч��(����)-�б�");
				
				try{
					session = HibernateUtil.getSession();
					 String userid=(String) data.get("userid");
					 String maintcontractno=CommonUtil.URLDecoder_decode((String) data.get("maintcontractno"));
					 String elevatorno=CommonUtil.URLDecoder_decode((String) data.get("elevatorno"));
					 String companyname=CommonUtil.URLDecoder_decode((String) data.get("companyname"));
					
					
					String sql="select mcm,mcd,c.companyName from MaintContractDetail mcd,MaintContractMaster mcm,Customer c "
							+ "where mcd.billNo=mcm.billNo "
							+ " and c.companyId=mcm.companyId";
							//+ " and mcm.contractStatus in ('ZB','XB')";
					if(maintcontractno!=null&&!maintcontractno.trim().equals("")){
						sql+=" and mcm.maintContractNo like '%"+maintcontractno.trim()+"%'";
					}
					if(elevatorno!=null&&!elevatorno.trim().equals("")){
						sql+=" and mcd.elevatorNo like '%"+elevatorno.trim()+"%'";
					}
					if(companyname!=null&&!companyname.trim().equals("")){
						sql+=" and (c.companyId like '"+companyname.trim()+"%' or c.companyName like '"+companyname.trim()+"%')";
					}
					
					List list =session.createQuery(sql).list();
					if(list!=null&&list.size()>0){
						for(int i=0;i<list.size();i++){
							JSONObject jsonObject=new JSONObject();
							Object[] objects=(Object[]) list.get(i);
							MaintContractMaster mcm=(MaintContractMaster) objects[0];        
							MaintContractDetail mcd=(MaintContractDetail) objects[1];
							jsonObject.put("rowid", mcd.getRowid());//ά����ͬ��ϸ�к�
		            		jsonObject.put("maintcontractno", mcm.getMaintContractNo());//ά����ͬ��
		            		jsonObject.put("elevatorno", mcd.getElevatorNo());//���ݱ��
		            		jsonObject.put("contractsdate", mcm.getContractSdate());//��ͬ��ʼ����
		            		jsonObject.put("contractedate", mcm.getContractEdate());//��ͬ��������
							jobiArray.put(i, jsonObject);
						}
					}
					json.put("code", "200");
		  			json.put("info", "OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
				}catch(Exception ex){
					//tx.rollback();
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
			 * ��ͬ��Ч��(����)-�鿴
			 * @param userid
			 * @param rowid
			 * @return
			 */
			@GET
			@Path("/yxqdetail/{userid}/{rowid}")
			@Produces("application/json")
			public Response getYxqDisplay(
					@PathParam("userid") String userid,
					@PathParam("rowid") String rowid
					){
				Session session = null;
				Transaction tx = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				
				System.out.println(">>>��ͬ��Ч��(����)-�鿴");
				
				try{
					List typelist=bd.getPullDownList("ElevatorSalesInfo_type");
					session = HibernateUtil.getSession();
					String sql="select mcm,mcd,c from MaintContractDetail mcd,MaintContractMaster mcm,Customer c "
							+ "where mcd.billNo=mcm.billNo "
							+ " and c.companyId=mcm.companyId and mcd.rowid='"+rowid.trim()+"'";
					List list =session.createQuery(sql).list();
					if(list!=null&&list.size()>0){
							JSONObject jsonObject=new JSONObject();
							Object[] objects=(Object[]) list.get(0);
							MaintContractMaster mcm=(MaintContractMaster) objects[0];        
							MaintContractDetail mcd=(MaintContractDetail) objects[1];
							Customer c=(Customer) objects[2];
		            		jsonObject.put("rowid", mcd.getRowid());//ά����ͬ��ϸ�к�
		            		jsonObject.put("maintdivision",bd.getName(session, "Company", "comfullname", "comid",mcm.getMaintDivision() ));
		            	  	jsonObject.put("maintstation",bd.getName(session, "Storageid", "storagename", "storageid",mcm.getMaintStation()));
		            		jsonObject.put("companyname", c.getCompanyName());//�׷���λ����
		            		jsonObject.put("contacts", c.getContacts());//��ϵ��
		            		jsonObject.put("contactphone", c.getContactPhone());//��ϵ�˵绰
		            		jsonObject.put("maintcontractno", mcm.getMaintContractNo());//ά����ͬ��
		            		jsonObject.put("elevatorno", mcd.getElevatorNo());//���ݱ��
		            		jsonObject.put("elevatortype", mcd.getElevatorType());//��������
		            		jsonObject.put("elevatortype", bd.getOptionName(mcd.getElevatorType(), typelist));//��������
		            		jsonObject.put("contractsdate", mcm.getContractSdate());//��ͬ��ʼ����
		            		jsonObject.put("contractedate", mcm.getContractEdate());//��ͬ��������
		            		
		            		if(mcd.getIsSurrender()!=null&&!mcd.getIsSurrender().equals("")){
			            		if(mcd.getIsSurrender().trim().equals("Y")){
			            			jsonObject.put("issurrender", "��");//�Ƿ��˱�
			            		}else{
			            			jsonObject.put("issurrender", "��");//�Ƿ��˱�
			            		}
		            		}else{
		            			jsonObject.put("issurrender", "��");//�Ƿ��˱�
		            		}
		            		jobiArray.put(0, jsonObject);
					}
					json.put("code", "200");
		  			json.put("info", "OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
				}catch(Exception ex){
					//tx.rollback();
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

			/* ���ݻ�������  */
			/**
			 * ���ݻ�������(����)-�б�
			 * @param userid
			 * @param maintcontractno
			 * @param elevatorno
			 * @param companyname
			 * @return
			 */
			@GET
			@Path("/dtjclist/{data}")
			@Produces("application/json")
			public Response getDtjcList(
					@PathParam("data") JSONObject data
					){
				Session session = null;
				Transaction tx = null;
				//Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				
				System.out.println(">>>���ݻ�������(����)-�б�");
				
				try{
					
					 String userid=(String) data.get("userid");
					 String maintcontractno=CommonUtil.URLDecoder_decode((String) data.get("maintcontractno"));
					 String elevatorno=CommonUtil.URLDecoder_decode((String) data.get("elevatorno"));
					 String companyname=CommonUtil.URLDecoder_decode((String) data.get("companyname"));
					
					session = HibernateUtil.getSession();
					//tx = session.beginTransaction();
					String sql="select mcm,mcd,c.companyName "
							+ "from MaintContractDetail mcd,MaintContractMaster mcm,Customer c "
							+ "where mcd.billNo=mcm.billNo "
							+ " and c.companyId=mcm.companyId";
					if(maintcontractno!=null&&!maintcontractno.trim().equals("")){
						sql+=" and mcm.maintContractNo like '%"+maintcontractno.trim()+"%'";
					}
					if(elevatorno!=null&&!elevatorno.trim().equals("")){
						sql+=" and mcd.elevatorNo like '%"+elevatorno.trim()+"%'";
					}
					if(companyname!=null&&!companyname.trim().equals("")){
						sql+=" and (c.companyId like '"+companyname.trim()+"%' or c.companyName like '"+companyname.trim()+"%')";
					}
					//System.out.println(sql);
					List list =session.createQuery(sql).list();
					if(list!=null&&list.size()>0){
						for(int i=0;i<list.size();i++){
							JSONObject jsonObject=new JSONObject();
							Object[] objects=(Object[]) list.get(i);
							MaintContractMaster mcm=(MaintContractMaster) objects[0];        
							MaintContractDetail mcd=(MaintContractDetail) objects[1];
							jsonObject.put("rowid", mcd.getRowid());//ά����ͬ��ϸ�к�
		            		jsonObject.put("maintcontractno", mcm.getMaintContractNo());//ά����ͬ��
		            		jsonObject.put("elevatorno", mcd.getElevatorNo());//���ݱ��
		            		jsonObject.put("companyname", (String)objects[2]);//�׷���λ����
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
			
			/**
			 * ���ݻ�������(����)-�鿴
			 * @param userid
			 * @param rowid
			 * @return
			 */
			@GET
			@Path("/dtjcdetail/{userid}/{rowid}")
			@Produces("application/json")
			public Response getDtjcDisplay(
					@PathParam("userid") String userid,
					@PathParam("rowid") String rowid){
				Session session = null;
				//Transaction tx = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				
				System.out.println(">>>���ݻ�������(����)-�鿴");
				
				try{
					List typelist=bd.getPullDownList("ElevatorSalesInfo_type");
					
					
					session = HibernateUtil.getSession();
					//tx = session.beginTransaction();
					String sql="select mcm,mcd,c from MaintContractDetail mcd,MaintContractMaster mcm,Customer c "
							+ "where mcd.billNo=mcm.billNo "
							+ " and c.companyId=mcm.companyId and mcd.rowid='"+rowid.trim()+"'";
					List list =session.createQuery(sql).list();
					if(list!=null&&list.size()>0){
							JSONObject jsonObject=new JSONObject();
							Object[] objects=(Object[]) list.get(0);
							MaintContractMaster mcm=(MaintContractMaster) objects[0];        
							MaintContractDetail mcd=(MaintContractDetail) objects[1];
							Customer c=(Customer) objects[2];
		            		jsonObject.put("rowid", mcd.getRowid());//ά����ͬ��ϸ�к�
		            		jsonObject.put("maintdivision",bd.getName(session, "Company", "comfullname", "comid",mcm.getMaintDivision() ));
		            	  	jsonObject.put("maintstation",bd.getName(session, "Storageid", "storagename", "storageid",mcm.getMaintStation()));
		            		jsonObject.put("companyname", c.getCompanyName());//�׷���λ����
		            		jsonObject.put("contacts", c.getContacts());//��ϵ��
		            		jsonObject.put("contactphone", c.getContactPhone());//��ϵ�˵绰
		            		jsonObject.put("maintcontractno", mcm.getMaintContractNo());//ά����ͬ��
		            		jsonObject.put("elevatorno", mcd.getElevatorNo());//���ݱ��
		            		jsonObject.put("elevatortype", bd.getOptionName(mcd.getElevatorType(), typelist));//��������
		            		jsonObject.put("annualinspectiondate", mcd.getAnnualInspectionDate());//�������
		            		jsonObject.put("floor", mcd.getFloor());//��
		            		jsonObject.put("stage", mcd.getStage());//վ
		            		jsonObject.put("door", mcd.getDoor());//��
		            		jsonObject.put("high", mcd.getHigh());//�����߶�,
		            		jsonObject.put("elevatorparam", mcd.getElevatorParam());//����ͺ�
		            		jsonObject.put("maintaddress", mcd.getMaintAddress());//������ַ
		            		//jsonObject.put("contacts", mcd.getRowid());//��ϵ��
		            		//jsonObject.put("contactphone", mcm.getcon);//��ϵ�˵绰
		            		jobiArray.put(0, jsonObject);
					}
					json.put("code", "200");
		  			json.put("info", "OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
				}catch(Exception ex){
					//tx.rollback();
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
