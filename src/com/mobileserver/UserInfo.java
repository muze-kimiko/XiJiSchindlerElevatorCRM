package com.mobileserver;

/**
 * �ֻ�APP�˵��ã���¼�û�������
 * @author Crunchify
 */

import java.util.ArrayList;

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

import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CryptUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

@Path("/UserInfo")
public class UserInfo {

	BaseDataImpl bd = new BaseDataImpl();
	
	@GET
	@Path("{userid}")
	@Produces("application/json")
	public Response _UserInfo(@PathParam("userid") String userid)
			throws JSONException {

		//System.out.println("�ֻ�APP����==��¼���ƣ�"+userid);
		
		JSONObject rejson = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		// �����û��������ݿ��л�ȡ�û���Ϣ
		jsonObject.put("userid", userid);
        jsonObject.put("username", "");
        jsonObject.put("roleid", "");
        jsonObject.put("rolename", "");
        jsonObject.put("storageid", "");
        jsonObject.put("storagename", "");
        jsonObject.put("comid", "");
        jsonObject.put("comname", "");
        
        jobiArray.put(0, jsonObject);

        json.put("code", "400");
		json.put("info", "�Ƿ��û���¼!");
		
		rejson.put("status", json);
		rejson.put("data", jobiArray);

		return Response.status(200).entity(rejson.toString()).build();
	}

	@GET
	@Path("{userid}/{password}/{phoneimei}")
	@Produces("application/json")
	public Response loginUser(
			@PathParam("userid") String userid,
			@PathParam("password") String password,
			@PathParam("phoneimei") String phoneimei) throws JSONException {
		
		System.out.println("==========�ֻ���¼��"+userid+" "+phoneimei+" ��ʼ==========");
		Session session = null;
		Transaction tx = null;
		JSONObject rejson = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		try{
			session = HibernateUtil.getSession();
			Query query = session.createQuery("from ViewLoginUserInfo a where a.userID = :userID and a.enabledFlag = :enabledFlag");
			
			query.setString("userID", userid);
            query.setString("enabledFlag","Y");
            ArrayList userList = (ArrayList) query.list();
             //�ж��û��Ƿ���ȷ
            if (userList == null || userList.isEmpty() || userList.size() == 0) {
                DebugUtil.println("�Ƿ��û���¼!");
                
                jsonObject.put("userid", userid);
                jsonObject.put("username", "");
                jsonObject.put("roleid", "");
                jsonObject.put("rolename", "");
                jsonObject.put("storageid", "");
                jsonObject.put("storagename", "");
                jsonObject.put("comid", "");
                jsonObject.put("comname", "");
    			
    			jobiArray.put(0, jsonObject);

                json.put("code", "400");
    			json.put("info", "�Ƿ��û���¼!");
            } else {
            	ViewLoginUserInfo userInfo = (ViewLoginUserInfo) userList.get(0);
                // �ж������Ƿ���ȷ
                if ( password == null || !new CryptUtil().decode(password, "LO").equals(userInfo.getPasswd())) {
                    DebugUtil.println("�û������������!");
                    
                    jsonObject.put("userid", userInfo.getUserID());
                    jsonObject.put("username", userInfo.getUserName());
                    jsonObject.put("roleid", "");
                    jsonObject.put("rolename", "");
                    jsonObject.put("storageid", "");
                    jsonObject.put("storagename", "");
                    jsonObject.put("comid", "");
                    jsonObject.put("comname", "");
                    jsonObject.put("phone","");
        			jobiArray.put(0, jsonObject);

                    json.put("code", "400");
        			json.put("info", "�����������!");
                } else {
                	String isok="Y";
                	String phoneimei2=userInfo.getPhoneimei();
                	if(phoneimei2==null || phoneimei2.trim().equals("") 
                			|| phoneimei2.trim().equals("null") || phoneimei2.trim().equals("NULL")){
                		tx = session.beginTransaction();
                		String sqlu="update Loginuser set phoneimei='"+phoneimei.trim()+"' where UserID='"+userid+"'";
                		session.connection().prepareStatement(sqlu).execute();
                		tx.commit();
                	}else{
                		//if(!phoneimei2.trim().equals(phoneimei.trim())){
                		if(phoneimei2.trim().indexOf(phoneimei.trim())>-1){
                			isok="Y";
                		}else{
                			isok="N";
                		}
                	}
                	
            		DebugUtil.println(userInfo.getUserName()+ " �û���¼�ɹ�.");
                    
                    jsonObject.put("userid", userInfo.getUserID());
                    jsonObject.put("username", userInfo.getUserName());
                    jsonObject.put("roleid", userInfo.getRoleID());
                    jsonObject.put("rolename", userInfo.getRoleName());
                    jsonObject.put("storageid", userInfo.getStorageId());
                    jsonObject.put("storagename", userInfo.getStorageName());
                    jsonObject.put("comid", userInfo.getComID());
                    jsonObject.put("comname", userInfo.getComName());
                    jsonObject.put("phone",userInfo.getPhone());
                    
                    String competence ="99";

                    json.put("code", "200");
        			json.put("info", "��¼�ɹ�!");
        			
                    if(userInfo.getRoleID().equals("A03") || userInfo.getRoleID().equals("A50") 
                    		|| userInfo.getRoleID().equals("A49") || userInfo.getRoleID().equals("A53")){
                    	//ά��վ��A49��ά����A50,ά������ A03��ά�޼���Ա A53��
                    	competence ="1";
                    }else if(userInfo.getRoleID().equals("A51")){
                        //����Ա
                    	competence ="2";
                    }else if(userInfo.getRoleID().equals("A12")){
                        //����Ա 
                    	competence ="3";
                    }else if(userInfo.getRoleID().equals("A01") || userInfo.getRoleID().equals("A04") 
                    		|| userInfo.getRoleID().equals("A11")){
                    	//A01 ����Ա ,A04  ��˾����֧��  ��A11 ����������ȫ����
                    	competence ="0";
                    }else{
                        json.put("code", "400");
            			json.put("info", "��Ȩ��ʹ�ô�Ӧ�ã�");
                    }
                    jsonObject.put("competence", competence);
                    jobiArray.put(0, jsonObject);
                    
                	if(isok.equals("N")){
                		json.put("code", "400");
            			json.put("info", "�Ƿ��ֻ��豸���޷���¼��<br/>����ϵ����Ա��");
                    }
                }
            }
            
            rejson.put("status", json);
    		rejson.put("data", jobiArray);
    		
    		System.out.println("===="+jobiArray.toString());
    		System.out.println("==========�ֻ���¼��"+userid+" ���� ==========");
    		
		}catch(Exception ex){
			if(tx!=null){
				tx.rollback();
			}
			ex.printStackTrace();
			
			json.put("code", "400");
  			json.put("info", "��¼���ִ���");
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

	
	@POST
	@Path("{userid}/{oldPassword}/{newPassword}")
	@Produces("application/json")
	public Response MyChangePwd(
			@PathParam("userid") String userid,
			@PathParam("oldPassword") String oldPassword,@PathParam("newPassword") String newPassword) throws JSONException {
		//System.out.println("�ֻ�APP����==���ƣ�"+userid+" ; �����룺"+oldPassword+" ; �����룺"+newPassword);
		
		Session session = null;
		Transaction tx=null;
		JSONObject rejson = new JSONObject();
		JSONObject jsonObject = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>�ҵ�����--�޸�����");
		
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			Query query = session.createQuery("from ViewLoginUserInfo a where a.userID = :userID and a.enabledFlag = :enabledFlag");
			query.setString("userID", userid);
            query.setString("enabledFlag","Y");
            ArrayList userList = (ArrayList) query.list();
             //�ж��û��Ƿ���
            if (userList == null || userList.isEmpty() || userList.size() == 0) {
                DebugUtil.println("�Ƿ��û���¼!");
                
                jsonObject.put("userid", userid);
                jsonObject.put("username", "");
    			jobiArray.put(0, jsonObject);

                json.put("code", "400");
    			json.put("info", "�Ƿ��û���¼!");
            } else {
            	ViewLoginUserInfo userInfo = (ViewLoginUserInfo) userList.get(0);
            	Loginuser lu=(Loginuser) session.get(Loginuser.class, userInfo.getUserID());
                // �ж������Ƿ���ȷ
                if (oldPassword == null || !new CryptUtil().decode(oldPassword, "LO").equals(userInfo.getPasswd())) {
                    
                    jsonObject.put("userid", userInfo.getUserID());
                    jsonObject.put("username", userInfo.getUserName());
        			
        			jobiArray.put(0, jsonObject);

                    json.put("code", "400");
        			json.put("info", "�����������!");
                } else {
                    
                    jsonObject.put("userid", userInfo.getUserID());
                    jsonObject.put("username", userInfo.getUserName());
                   
                    
                    lu.setPasswd(new CryptUtil().decode(newPassword, "LO"));
					
                    session.update(lu);
					tx.commit();
                    jobiArray.put(0, jsonObject);
        			
                    json.put("code", "200");
        			json.put("info", "�޸ĳɹ�!");
                }
            }
            
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
	 * ��ȡ�û���Ϣ
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/getuserinfo")
	@Produces("application/json")
	public Response getUserInfo (@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []

		System.out.println(">>>>>>>>�ҵ�����--��ȡ�û���Ϣ");
		try{
			session = HibernateUtil.getSession();
			
			String userid=(String) data.get("userid");//��¼�û�����
			
			Loginuser l =(Loginuser) session.get(Loginuser.class, userid);
			
			JSONObject object=new JSONObject();
			object.put("userid", userid);
			object.put("newphone", l.getPhone());//�绰
			object.put("newemail", l.getEmail());//����
			object.put("nweidcardno", l.getIdcardno());//���֤
			jobiArray.put(0, object);

			json.put("code", "200");
			json.put("info", "OK");
	        rejson.put("status", json);
	  		rejson.put("data", jobiArray);
		}catch(Exception ex){
			json.put("code", "400");
			json.put("info", "NOT OK");
	        rejson.put("status", json);
	  		rejson.put("data", jobiArray);
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
	 * �����û���Ϣ
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/userinfoadd")
	@Produces("application/json")
	public Response userInfoAdd (@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []

		System.out.println(">>>>>>>>�ҵ�����--�����û���Ϣ");
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String userid=(String) data.get("userid");//��¼�û�����
			String newphone=(String) data.get("newphone");//�绰
			String newemail=(String) data.get("newemail");//����
			String nweidcardno=(String) data.get("nweidcardno");//���֤
			
			Loginuser l =(Loginuser) session.get(Loginuser.class, userid);
			l.setNewphone(newphone);
			l.setNewemail(newemail);
			l.setNweidcardno(nweidcardno);
			l.setAuditoperid(null);
			l.setAuditdate(null);
			l.setAuditrem(null);
			session.save(l);
			
			tx.commit();
			
			json.put("code", "200");
			json.put("info", "�޸��û����ϳɹ���");
	        rejson.put("status", json);
	  		rejson.put("data", jobiArray);
		}catch(Exception ex){
			if(tx!=null){
				tx.rollback();
			}
			json.put("code", "400");
			json.put("info", "�޸��û�����ʧ�ܣ�");
	        rejson.put("status", json);
	  		rejson.put("data", jobiArray);
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