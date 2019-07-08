package com.mobileserver;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

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
import org.zefer.html.doc.p;

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;

/**
 * �ֻ�APP�˵��ã����鷴����־��
 * @author Crunchify
 */
@Path("/QcLogmanage")
public class QcLogmanage {
	
	BaseDataImpl bd = new BaseDataImpl();
	
	/**
	 * ���鷴����־-�б�
	 * @param userid
	 * @param sdate
	 * @param edate
	 * @return
	 */
	@GET
	@Path("/fkrzlist/{userid}/{sdate}/{edate}")
	@Produces("application/json")
	public Response getLogList(
			@PathParam("userid") String userid, 
			@PathParam("sdate") String sdate,
			@PathParam("edate") String edate
			){
		Session session = null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>���鷴����־-�б�");
		
		try{
			session = HibernateUtil.getSession();
			//tx = session.beginTransaction();
			String sql ="select rowid,operid,operdate,elevatorno,maintstation"
					+ " from QualityCheckLog where operId='"+userid+"' ";
			
			if (sdate != null && !"".equals(sdate)) {
				sql += " and operDate >= '"+sdate.trim()+" 00:00:00'";
			}
			if (edate != null && !"".equals(edate)) {
				sql += " and operDate <= '"+edate.trim()+" 99:99:99'";
			}
		
			sql +=" order by operDate desc";
			
			//System.out.println(">>>>"+sql);
			
			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					Object[] objs=(Object[]) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("rowid", objs[0]);
            	  	jsonObject.put("operid", objs[1]);
            	  	jsonObject.put("opername",bd.getName(session, "Loginuser", "username", "userid", (String)objs[1]));
            	  	jsonObject.put("operdate", objs[2]);
            	  	jsonObject.put("elevatorno", objs[3]);
            	  	jsonObject.put("maintstation",bd.getName(session, "Storageid", "storagename", "storageid",(String)objs[4]));
            	  	
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
	 * ���鷴����־-�鿴
	 * @param userid
	 * @param rowed
	 * @return
	 */
	@GET
	@Path("/fkrzdetail/{userid}/{rowid}")
	@Produces("application/json")
	public Response getLogDisplay(@PathParam("userid") String userid, @PathParam("rowid") String rowed )throws JSONException{
		Session session = null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>���鷴����־-�鿴"); 
		
		try{
			session = HibernateUtil.getSession();
			//tx = session.beginTransaction();
			String sql ="select operid,operdate,maintstation,maintpersonnel,"
					+ "elevatorno,ydlh,isgzfz,iszfwt,jffkwt,ycjkwt,rem "
					+ "from QualityCheckLog  where rowid="+rowed;

			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
					Object[] objs =(Object[]) selectList.get(0);
					JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("rowid", rowed);
            	  	jsonObject.put("operid", objs[0]);
            	  	jsonObject.put("opername",bd.getName(session, "Loginuser", "username", "userid", (String)objs[0]));
            	  	jsonObject.put("operdate", objs[1]);
            	  	jsonObject.put("maintstation",bd.getName(session, "Storageid", "storagename", "storageid",(String)objs[2]));
            	  	jsonObject.put("maintpersonnel",objs[3]);
            	  	jsonObject.put("elevatorno", objs[4]);
            	  	jsonObject.put("ydlh", objs[5]);
            	  	jsonObject.put("isgzfz", objs[6]);
            	  	jsonObject.put("iszfwt", objs[7]);
            	  	jsonObject.put("jffkwt", objs[8]);
            	  	jsonObject.put("ycjkwt", objs[9]);
            	  	jsonObject.put("rem", objs[10]);
            	  	
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
	 * 	���鷴����־-�½�
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/fkrzadd")
	@Produces("application/json")
	public Response saveLogManage (@FormParam("data") JSONObject data ) throws JSONException{
		Session session = null;
		Transaction tx = null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>���鷴����־-����");
		
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String operid=(String) data.get("operid");
			String operdate=(String) data.get("operdate");
			String maintstation=(String) data.get("maintstation");
			String maintpersonnel=(String) data.get("maintpersonnel");
			String elevatorno=(String) data.get("elevatorno");
			String ydlh=(String) data.get("ydlh");
			String isgzfz=(String) data.get("isgzfz");
			String iszfwt=(String) data.get("iszfwt");
			String jffkwt=(String) data.get("jffkwt");
			String ycjkwt=(String) data.get("ycjkwt");
			String rem=(String) data.get("rem");
			
			String sqlins="insert into QualityCheckLog(operid,operdate,maintstation,maintpersonnel,"
					+ "elevatorno,ydlh,isgzfz,iszfwt,jffkwt,ycjkwt,rem) "
					+ "values('"+operid+"','"+operdate+"','"+maintstation+"','"+maintpersonnel+"','"
					+elevatorno+"','"+ydlh+"','"+isgzfz+"','"+iszfwt+"','"+jffkwt+"','"+ycjkwt+"','"+rem+"')";
			session.connection().prepareStatement(sqlins).execute();
			
			tx.commit();
			
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			tx.rollback();
			ex.printStackTrace();
			json.put("code", "400");
  			json.put("info", "NOT");
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
	 * ���鷴����־-��ȡά��վ
	 * @param userid
	 * @param rowed
	 * @return
	 */
	@GET
	@Path("/getmaintstation/{userid}")
	@Produces("application/json")
	public Response getMaintstation(@PathParam("userid") String userid )throws JSONException{
		Session session = null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>���鷴����־-��ȡά��վ"); 
		
		try{
			session = HibernateUtil.getSession();
			//tx = session.beginTransaction();
			String sql ="select StorageID,StorageName from Storageid "
					+ "where Storagetype='1' and ParentStorageID='0' and EnabledFlag='Y'";

			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					Object[] objs =(Object[]) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("storageid", objs[0]);
            	  	jsonObject.put("storagename", objs[1]);
            	  	
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
	 * ���鷴����־-��ȡά����
	 * @param userid
	 * @param rowed
	 * @return
	 */
	@GET
	@Path("/getmaintpersonnel/{maintstation}")
	@Produces("application/json")
	public Response getMaintPersonnel(@PathParam("maintstation") String maintstation )throws JSONException{
		Session session = null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>���鷴����־-��ȡά����"); 
		
		try{
			session = HibernateUtil.getSession();
			
			//ά����A50,ά��վ��A49,ά������ A03��ά�޼���ԱA53 
			String sql ="select UserID,UserName,phone from LoginUser "
					+ "where StorageID like '"+maintstation+"%' and EnabledFlag='Y' "
					+ "and RoleID in('A50','A49','A03','A53')";
			//System.out.println(">>>>"+sql); 
			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					Object[] objs =(Object[]) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("newuserid", objs[0]);
            	  	jsonObject.put("newusername", objs[1]);
            	  	jsonObject.put("newusertel",objs[2]);
            	  	
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
	 * ���鱣��������-�б�
	 * @param userid
	 * @param elevatorno
	 * @param processstatus
	 * @return
	 */
	@POST
	@Path("/qcmwpdlist")
	@Produces("application/json")
	public Response getQcmwpdList(@FormParam("data") JSONObject data){
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>���鱣��������-�б�");
		
		try{
			String userid=(String) data.get("userid");
			String maintstarttime =(String) data.get("date1");//������ʼ����
			String mainstation =(String) data.get("mainstationid");//ά��վ
			String elevatorno =(String) data.get("elevatorno");//���ݱ��
			
			//System.out.println((Integer) data.get("pageno"));
			
			session = HibernateUtil.getSession();

			//���ݱ�ţ���ʼ����ʱ�䣬��������,ά����Ա���绰��¥���ţ�ά��վ
			String sql ="select mwpd.numno,mcd.ElevatorNo,mwpd.MaintStartTime,mwpd.MaintType,"
					+ "mwpd.MaintPersonnel,l.UserName,l.Phone,isnull(ecl.Rem,'') as rem,"
					+ "mcd.AssignedMainStation,s.StorageName,"
					+ "isnull(ecl.ElevatorLocation,'') as ElevatorLocation,ecl.BeginDimension,ecl.BeginLongitude "
					+ "from MaintenanceWorkPlanDetail mwpd,MaintenanceWorkPlanMaster mwpm,LoginUser l,StorageID s,"
					+ "MaintContractDetail mcd left join ElevatorCoordinateLocation ecl on mcd.ElevatorNo=ecl.ElevatorNo "
					+ "where mwpd.billno=mwpm.billno "
					+ "and mwpm.rowid=mcd.rowid "
					+ "and mcd.AssignedMainStation=s.StorageID "
					+ "and mwpd.MaintPersonnel=l.UserID "
					+ "and isnull(mwpm.checkflag,'')='Y' "
					+ "and ISNULL(mwpd.HandleStatus,'')='2' ";//�����еĵ��ݱ��
            if(elevatorno!=null && !elevatorno.trim().equals("")){
            	sql +=" and mcd.elevatorNo like '%"+elevatorno.trim()+"%'";
            }
			if(mainstation!=null && !mainstation.trim().equals("")){
				sql+=" and mcd.AssignedMainStation like '"+mainstation.trim()+"'";
			}
            if(maintstarttime!=null && !maintstarttime.trim().equals("")){
				sql+=" and mwpd.MaintStartTime like '"+maintstarttime.trim()+"%' ";
			}
			sql +=" order by mwpd.MaintStartTime desc";
			
			//System.out.println(">>>"+sql);
			
			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null && selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					 Object[] objects=(Object[]) selectList.get(i);
					 
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("numno", objects[0]);
               	 	jsonObject.put("elevatorno", objects[1]);//���ݱ��
					jsonObject.put("maintstarttime", objects[2]);//��ʼ����ʱ��
               	 	jsonObject.put("muserid", objects[4]);//ά����Ա
					jsonObject.put("musername", objects[5]);//ά����Ա
               	 	jsonObject.put("phone", objects[6]);//�绰
					jsonObject.put("elocation", objects[7]);//¥����
               	 	jsonObject.put("mainstation", objects[8]);//ά��վ
               	 	jsonObject.put("mainstationname", objects[9]);//ά��վ
					jsonObject.put("elelocation", objects[10]);//����λ��
               	 	jsonObject.put("begindimension", objects[11]);//γ��
               	 	jsonObject.put("beginlongitude", objects[12]);//����

            	  	String maintType=(String)objects[3];//��������
            	  	jsonObject.put("mainttype", maintType); 
	               	 if(maintType.trim().equals("halfMonth")){
	               		 jsonObject.put("mainttypename", "���±���"); 
	               	 }else if(maintType.trim().equals("quarter")){
	               		 jsonObject.put("mainttypename", "���ȱ���"); 
	               	 }else if(maintType.trim().equals("halfYear")){
	               		 jsonObject.put("mainttypename", "���걣��"); 
	               	 }else if(maintType.trim().equals("yearDegree")){
	               		 jsonObject.put("mainttypename", "��ȱ���"); 
	               	 }

            		jobiArray.put(i, jsonObject);
				}	
			}
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		//rejson.put("data", jobiArray);
            rejson.put("data", CommonUtil.Pagination(data, jobiArray));
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
	
}
