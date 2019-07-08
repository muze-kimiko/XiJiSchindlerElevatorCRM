package com.mobileserver;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.sql.ResultSet;
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
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.hibernate.hotlinemanagement.calloutmaster.CalloutMaster;
import com.gzunicorn.hibernate.hotlinemanagement.calloutprocess.CalloutProcess;
import com.gzunicorn.hibernate.mobileofficeplatform.technologysupport.TechnologySupport;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.struts.action.xjsgg.SmsService;
import com.gzunicorn.struts.action.xjsgg.XjsggAction;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * �ֻ�APP�˵��ã�����֧��������
 * @author Crunchify
 */
@Path("/Technology")
public class Technology {
	
	BaseDataImpl bd = new BaseDataImpl();
	
	/**
	 * ����֧������-�б�(��ѯ)
	 * @param userid
	 * @param singleno
	 * @param sdate
	 * @param edate
	 * @return
	 */
	@GET
	@Path("/jszclist/{data}")
	@Produces("application/json")
	public Response getTechnologyList(
			@PathParam("data")  JSONObject data
			){
		Session session = null;
		Transaction tx = null;
		Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>����֧������-�б�(��ѯ)");
		
        try{
        	 String sdate=(String) data.get("sdate");
             String edate=(String) data.get("edate");
             String userid=(String) data.get("userid");
             String singleno=(String) data.get("singleno");

			session = HibernateUtil.getSession();
			
			List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
			if(userList!=null&&userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			
			//1��վ������ 2�������� 3����˾����֧�ִ��� 4���������
			String roleid=user.getRoleid();
			String sql="from TechnologySupport t where 1=1 ";
			if(roleid.trim().equals("A03")){
				//ά������
				sql+=" and t.proStatus ='2' and t.maintStation like '"+user.getStorageid().trim()+"%'";
			}else if(roleid.trim().equals("A49")){
				//ά��վ��
				sql+=" and t.proStatus ='1' and t.maintStation like '"+user.getStorageid().trim()+"'";
			}else if(roleid.trim().equals("A50")){
				//ά����
				sql+=" and t.assignUser = '"+userid+"' ";
			}else if(roleid.trim().equals("A04") || roleid.trim().equals("A11")){
				//��˾����֧��
				sql+=" and t.proStatus = '3'";
			}else{
				//������
				sql+=" and t.maintStation = ''";
			}
				
			if (singleno != null && !"".equals(singleno)){
				sql+=" and t.singleNo like '%"+singleno+"%'";		
			}
			if (sdate != null && !"".equals(sdate)) {
				sql += " and t.operDate >= '"+sdate.trim()+" 00:00:00'";
			}
			if (edate != null && !"".equals(edate)) {
				sql += " and t.operDate <= '"+edate.trim()+" 99:99:99'";
			}
			
			sql +=" order by t.operDate desc";
			Query query = session.createQuery(sql);
            ArrayList tList = (ArrayList) query.list();
             //cpList�Ƿ���ֵ
            if (tList != null && tList.size()>0 ) {
            	for(int i=0;i<tList.size();i++){
            	  	TechnologySupport t=(TechnologySupport) tList.get(i);
            	  	JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("billno", t.getBillno());
            	  	jsonObject.put("singleno", t.getSingleNo());
            	  	jsonObject.put("elevatorno", t.getElevatorNo());
            	  	jsonObject.put("assignuser", t.getAssignUser());
            	  	jsonObject.put("assignuserName", bd.getName(session, "Loginuser", "username", "userid", t.getAssignUser()));
            	  	jsonObject.put("operdate", t.getOperDate());
            	  	jsonObject.put("prostatus", t.getProStatus());
            	  	
            	  	if(t.getProStatus().trim().equals("1")){
            	  		jsonObject.put("prostatusName", "վ������");
            	  	}else if(t.getProStatus().trim().equals("2")){
            	  		jsonObject.put("prostatusName", "������");
            	  	}else if(t.getProStatus().trim().equals("3")){
            	  		jsonObject.put("prostatusName", "����֧�ִ���");
            	  	}else if(t.getProStatus().trim().equals("4")){
            	  		jsonObject.put("prostatusName", "�������");
            	  	}
            	  	
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
	 * ����֧������-�鿴
	 * @param userid
	 * @param billno
	 * @return
	 */
	@GET
	@Path("/jszcdetail/{data}")
	@Produces("application/json")
	public Response getTechnologyDisplay(@PathParam("data")  JSONObject data){
		Session session = null;
		Transaction tx = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>����֧������-�鿴");
		//Loginuser user=null;
		try{
			String userid=(String)data.get("userid");
			String billno=(String)data.get("billno");
			session = HibernateUtil.getSession();

			String sql="from TechnologySupport t where t.billno ='"+billno.trim()+"'";
			Query query = session.createQuery(sql);
            ArrayList tList = (ArrayList) query.list();
             //cpList�Ƿ���ֵ
            if (tList != null && tList.size()>0 ) {
            	
            	  	TechnologySupport t=(TechnologySupport) tList.get(0);
            	  	JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("billno", t.getBillno());
            	  	jsonObject.put("singleno", t.getSingleNo());
            	  	jsonObject.put("elevatorno", t.getElevatorNo());
            	  	jsonObject.put("assignuser", bd.getName(session, "Loginuser", "username", "userid", t.getAssignUser()));
            	  	jsonObject.put("operdate", t.getOperDate());
            	  	jsonObject.put("prostatus", t.getProStatus());
            	  	if(t.getProStatus().trim().equals("1")){
            	  		jsonObject.put("prostatusName", "վ������");
            	  	}else if(t.getProStatus().trim().equals("2")){
            	  		jsonObject.put("prostatusName", "������");
            	  	}else if(t.getProStatus().trim().equals("3")){
            	  		jsonObject.put("prostatusName", "��˾����֧�ִ���");
            	  	}else if(t.getProStatus().trim().equals("4")){
            	  		jsonObject.put("prostatusName", "�������");
            	  	}
            	  	jsonObject.put("assignusertel", t.getAssignUserTel());
            	  	jsonObject.put("maintdivision",bd.getName(session, "Company", "comfullname", "comid", t.getMaintDivision()));
            	  	jsonObject.put("maintstation",bd.getName(session, "Storageid", "storagename", "storageid",t.getMaintStation()));
            	  	jsonObject.put("hmtid", t.getHmtId());
            	  	jsonObject.put("hmtname",bd.getName(session, "HotlineMotherboardType", "hmtName", "hmtId", t.getHmtId()));
            	  	jsonObject.put("faultcode", t.getFaultCode());
            	  	jsonObject.put("faultstatus", t.getFaultStatus());
            	  	//ά��վ��������
            	  	jsonObject.put("msprocesspeople",bd.getName(session, "Loginuser", "username", "userid", t.getMsprocessPeople()));
            		jsonObject.put("msprocessdate", t.getMsprocessDate());
            		jsonObject.put("msprocessrem", t.getMsprocessRem());
            		 if(t.getMsisResolve()!=null&&!"".equals(t.getMsisResolve())){
             	    	jsonObject.put("msisresolve",  t.getMsisResolve().equals("Y") ? "�ѽ��" : "δ���");	
             	    }
            		//ά����������
            		jsonObject.put("mmprocesspeople", bd.getName(session, "Loginuser", "username", "userid", t.getMmprocessPeople()));
            		jsonObject.put("mmprocessdate", t.getMmprocessDate());
            		jsonObject.put("mmprocessrem", t.getMmprocessRem());
            		if(t.getMmisResolve()!=null&&!"".equals(t.getMmisResolve())){
            	    	jsonObject.put("mmisresolve", t.getMmisResolve().equals("Y") ? "�ѽ��" : "δ���");	
            	    }
            		//��˾����֧�ִ�����
            	    jsonObject.put("tsprocesspeople", bd.getName(session, "Loginuser", "username", "userid", t.getTsprocessPeople()));
            		jsonObject.put("tsprocessdate", t.getTsprocessDate());
            		jsonObject.put("tsprocessrem", t.getTsprocessRem());
            		
            		/**=====================��ȡͼƬ=========================*/
    				String folder = PropertiesUtil.getProperty("TechnologySupport.file.upload.folder");
    				BASE64Encoder base64=new BASE64Encoder();
    				//����ͼƬ
    				if(t.getGzImage()!=null && !t.getGzImage().trim().equals("")){
    					String filepath=folder+t.getGzImage();
    					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������
    					jsonObject.put("gzimg", base64.encode(imgbyte));//��������������
    				}else{
    					jsonObject.put("gzimg", "");
    				}
    				/**=====================��ȡͼƬ=========================*/
    				
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
	 * ����֧������-����
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/jszcadd")
	@Produces("application/json")
	public Response saveTechnology(@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		Loginuser user=null;

		System.out.println(">>>>>>>>����֧������-����");
		
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			String singleno=(String) data.get("singleno");//���ޱ��
			String elevatorno=(String) data.get("elevatorno");//���ݱ��
			String hmtid=(String) data.get("hmtid");//�������ʹ���
			String faultcode= CommonUtil.URLDecoder_decode((String) data.get("faultcode")) ;//���ϴ���
			String faultstatus=CommonUtil.URLDecoder_decode((String) data.get("faultstatus"));//����״̬
			String assignuser=(String) data.get("assignuser");//������
			String assignusertel=(String) data.get("assignusertel");//�����˵绰
			//String operdate=(String) data.get("operdate");//��������
			
			//���ϴ�����", ��"�ָ�
			String faultCodestr="";
			if(faultcode!=null && !faultcode.trim().equals("")){
				String[] faultCodes=faultcode.split(",");
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
			
			TechnologySupport t=new TechnologySupport();
			String year1=CommonUtil.getToday().substring(2,4);
			String billno1 = CommonUtil.getBillno(year1,"TechnologySupport", 1)[0];	
			
			List userList=session.createQuery("from Loginuser where userid ='"+assignuser+"'").list();
			if(userList!=null && userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			String parentstorageid=bd.getName(session, "Storageid", "parentstorageid", "storageid",user.getStorageid());
			
			t.setBillno(billno1);
			t.setSingleNo(singleno);
			t.setElevatorNo(elevatorno);
			t.setHmtId(hmtid);
			t.setFaultCode(faultCodestr);
			t.setFaultStatus(faultstatus);
			t.setAssignUser(assignuser);
			t.setAssignUserTel(assignusertel);
			t.setOperDate(CommonUtil.getNowTime());
			t.setMaintDivision(user.getGrcid());
			t.setMaintStation(user.getStorageid());
			
			String gzimg= (String) data.get("gzimg");//����ͼƬ
			/**=======================����ͼƬ=============================*/
			String folder = PropertiesUtil.getProperty("TechnologySupport.file.upload.folder");
			String filepath=CommonUtil.getNowTime("yyyy-MM-dd")+"/";
			if(gzimg!=null && !gzimg.trim().equals("")){
				//customerimage=customerimage.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
				String[] cimages=gzimg.split(",");
				BASE64Decoder base64=new BASE64Decoder();
				if(cimages!=null && cimages.length>1){
					byte[] image=base64.decodeBuffer(cimages[1]);
					String newfilename=billno1+"_old.jpg";
					//����ͼƬ
					File f=new File(folder+filepath);
					f.mkdirs();
					FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
					fos.write(image);
					fos.flush();
					fos.close();
					//����ͼƬ��Ϣ�����ݿ�
					t.setGzImage(filepath+newfilename);
				}
			}
			/**=======================����ͼƬ=============================*/
			
			//1��վ������ 2�������� 3����˾����֧�ִ��� 4���������
			if(parentstorageid.trim().equals("0")){
				t.setProStatus("2");
			}else{
				t.setProStatus("1");
			}
			session.save(t);
			
			
			//�������ݵ����޹�����Ϣ��
			CalloutProcess cp=(CalloutProcess)session.get(CalloutProcess.class, singleno);
			cp.setFaultCode(faultCodestr);
			cp.setFaultStatus(faultstatus);
			cp.setHmtId(hmtid);
			session.save(cp);
			
			tx.commit();
			
			/****************************���Ͷ��Ÿ�ά������ ��ʼ**********************************/
			//A49  ά��վ��,A03  ά������
			String sqlqu="";
			String phone="";
			//1��վ������ 2�������� 3����˾����֧�ִ��� 4���������
			if(parentstorageid.trim().equals("0")){
				sqlqu="select userid,phone from loginuser where '"+user.getStorageid()+"' like StorageID+'%' and RoleID in('A03')";
			}else{
				sqlqu="select userid,phone from loginuser where '"+user.getStorageid()+"' like StorageID+'%' and RoleID in('A49')";
			}
			
			ResultSet rs=session.connection().prepareStatement(sqlqu).executeQuery();
			if(rs.next()){
				phone=rs.getString("phone");
			}
			
			if(phone!=null && !phone.equals("")){
				boolean issms=SmsService.techSendSMS(phone);
			}
			/****************************���Ͷ��Ÿ�ά������ ����**********************************/
			
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
            } catch (Exception hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	/**
	 * ����֧������-������
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/jszccladd")
	@Produces("application/json")
	public Response saveTechnologycl(@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []

		System.out.println(">>>>>>>>����֧������-������");
		
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			String billno=(String) data.get("billno");//��ˮ��
			String prostatus=(String) data.get("prostatus");//����״̬
			
			String msprocesspeople=(String) data.get("msprocesspeople");//ά��վ��������
			//String msprocessdate=(String) data.get("msprocessdate");//ά��վ����������
			String msprocessrem=CommonUtil.URLDecoder_decode((String) data.get("msprocessrem"));//ά��վ���������
			String msisresolve=(String) data.get("msisresolve");//ά��վ���Ƿ���
			
			String mmprocesspeople=(String) data.get("mmprocesspeople");//ά����������
			//String mmprocessdate=(String) data.get("mmprocessdate");//ά������������
			String mmprocessrem=CommonUtil.URLDecoder_decode((String) data.get("mmprocessrem"));//ά�����������
			String mmisresolve=(String) data.get("mmisresolve");//ά�������Ƿ���
			
			String tsprocesspeople=(String) data.get("tsprocesspeople");//��˾����֧�ִ�����
			//String tsprocessdate=(String) data.get("tsprocessdate");//��˾����֧�ִ�������
			String tsprocessrem=CommonUtil.URLDecoder_decode((String) data.get("tsprocessrem"));//��˾����֧�ִ������
			
			TechnologySupport t=(TechnologySupport) session.get(TechnologySupport.class, billno);
			String prostatusold=t.getProStatus();
			if(t.getProStatus().trim().endsWith("1")||t.getProStatus()=="1"){
				t.setMsprocessPeople(msprocesspeople);
				t.setMsprocessDate(CommonUtil.getNowTime());
				t.setMsprocessRem(msprocessrem);
				t.setMsisResolve(msisresolve);
			}else if(t.getProStatus().trim().endsWith("2")||t.getProStatus()=="2"){
				t.setMmprocessPeople(mmprocesspeople);
				t.setMmprocessDate(CommonUtil.getNowTime());
				t.setMmprocessRem(mmprocessrem);
				t.setMmisResolve(mmisresolve);
			}else if(t.getProStatus().trim().endsWith("3")||t.getProStatus()=="3"){
				 t.setTsprocessPeople(tsprocesspeople);
				 t.setTsprocessDate(CommonUtil.getNowTime());
				 t.setTsprocessRem(tsprocessrem);
			}
		    t.setProStatus(prostatus);
			session.update(t);
			
			/****************************���Ͷ��Ÿ�ά������ ��ʼ**********************************/
			//վ�������ˣ��͸���������
			if(prostatusold!=null && prostatusold.equals("1")){
				//A49  ά��վ��,A03  ά������
				String sqlqu="select userid,phone from loginuser where '"+t.getMaintStation()+"' like StorageID+'%' and RoleID in('A03')";
				String phone="";
				
				ResultSet rs=session.connection().prepareStatement(sqlqu).executeQuery();
				if(rs.next()){
					phone=rs.getString("phone");
				}
				
				if(phone!=null && !phone.equals("")){
					boolean issms=SmsService.techSendSMS(phone);
				}
			}
			/****************************���Ͷ��Ÿ�ά������ ����**********************************/
			
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
            } catch (Exception hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }

		return Response.status(200).entity(rejson.toString()).build();
	}

	
	/**
	 * ����֧������--���ޱ��
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/jxbh/{data}")
	@Produces("application/json")
	public Response getgzlxList(
			@PathParam("data") JSONObject data) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>����֧������--���ޱ��");
		
		String userid=(String) data.get("userid");
		String calloutMasterNo=CommonUtil.URLDecoder_decode((String) data.get("calloutMasterNo")); 
		
		try{
			session = HibernateUtil.getSession();
			//0 ��ת�ɣ�1 �ѽ��գ�2 �ѵ�����3 ��ͣ�ݣ�4 ���깤��5 ��¼�룬6 ����ˣ�7 �ѹرգ�8 ��ȫ�������
			String sql="select cm from CalloutMaster cm,CalloutProcess cp "
					+ "where cm.calloutMasterNo=cp.calloutMasterNo "
					+ "and cm.handleStatus in ('2','3') "
					+ "and cp.assignObject2 ='"+userid+"'";
			if(calloutMasterNo!=null && !calloutMasterNo.equals("")){
				sql+=" and cm.calloutMasterNo like '%"+calloutMasterNo+"%'";
			}
			
			List list =session.createQuery(sql).list();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					CalloutMaster cNo=(CalloutMaster) list.get(i);
					JSONObject jsonObject = new JSONObject(); 
					jsonObject.put("calloutMasterNo", cNo.getCalloutMasterNo());
					jsonObject.put("elevatorno", cNo.getElevatorNo());
					jobiArray.put(i, jsonObject);
				}
			   }
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
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
