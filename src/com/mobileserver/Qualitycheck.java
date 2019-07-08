package com.mobileserver;

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
import java.util.HashMap;
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

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.grcnamelist.Grcnamelist1;
import com.gzunicorn.common.logic.BaseDataImpl;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DateUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.PropertiesUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.elevatorcoordinatelocation.ElevatorCoordinateLocation;
import com.gzunicorn.hibernate.basedata.markingscore.MarkingScore;
import com.gzunicorn.hibernate.basedata.markingscoredetail.MarkingScoreDetail;
import com.gzunicorn.hibernate.infomanager.markingscoreregister.MarkingScoreRegister;
import com.gzunicorn.hibernate.infomanager.markingscoreregisterdetail.MarkingScoreRegisterDetail;
import com.gzunicorn.hibernate.infomanager.markingscoreregisterfileinfo.MarkingScoreRegisterFileinfo;
import com.gzunicorn.hibernate.infomanager.qualitycheckmanagement.QualityCheckManagement;
import com.gzunicorn.hibernate.infomanager.qualitycheckprocess.QualityCheckProcess;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.workflow.bo.JbpmExtBridge;

/**
 * �ֻ�APP�˵��ã�ά���������Ǽ���
 * @author Crunchify
 */
@Path("/Qualitycheck")
public class Qualitycheck {
	
	BaseDataImpl bd = new BaseDataImpl();
	/**
	 * ά���������Ǽ�-�б�
	 * @param userid
	 * @param elevatorno
	 * @param processstatus
	 * @return
	 */
	@POST
	@Path("/wbzllist")
	@Produces("application/json")
	public Response getWbzlList(@FormParam("data") JSONObject data){
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>ά���������Ǽ�-�б�");
		
		try{
			String userid=(String) data.get("userid");
			String elevatorno =(String) data.get("elevatorno");
			String processstatus =(String) data.get("processstatus");
			
			//System.out.println((Integer) data.get("pageno"));
			
			session = HibernateUtil.getSession();
			//tx = session.beginTransaction();
			String sql ="from QualityCheckManagement q where q.submitType='Y' and q.superviseId='"+userid+"'";
            if(elevatorno!=null&&!elevatorno.trim().equals("")){
            	sql +=" and q.elevatorNo like '%"+elevatorno.trim()+"%'";
            }
            if(processstatus!=null && !processstatus.trim().equals("") && !processstatus.trim().equals("5")){
				sql+=" and q.processStatus like '"+processstatus.trim()+"' ";
			}
			if(processstatus!=null && !processstatus.trim().equals("") && processstatus.trim().equals("5")){
				sql+=" and q.status=0 ";
			}
			sql +=" order by q.billno desc";
			
			//System.out.println(sql);
			
			List selectList=session.createQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					QualityCheckManagement q =(QualityCheckManagement) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("billno", q.getBillno());
            	  	jsonObject.put("elevatorno", q.getElevatorNo());
            	  	jsonObject.put("maintcontractno", q.getMaintContractNo());
            	  	jsonObject.put("projectname", q.getProjectName());
            	  	//jsonObject.put("maintdivision", q.getBillno());
            	  	//jsonObject.put("maintstation", q.getMaintStation());
            		jsonObject.put("maintdivision",bd.getName(session, "Company", "comfullname", "comid", q.getMaintDivision()));
            	  	jsonObject.put("maintstation",bd.getName(session, "Storageid", "storagename", "storageid",q.getMaintStation()));
            	  	jsonObject.put("maintpersonnel",bd.getName(session, "Loginuser", "username", "userid", q.getMaintPersonnel()) );
            	  	
            	  	String statusid=q.getProcessStatus();
					String statusname="";
            	  	if(statusid.equals("0")){
            	  		statusname="δ�Ǽ�";
            	  	}else if(statusid.equals("1")){
            	  		statusname="�ѵǼ�δ�ύ";
            	  	}else if(statusid.equals("2")){
            	  		statusname="�ѵǼ����ύ";
            	  	}else if(statusid.equals("3")){
            	  		statusname="�����";
            	  	}
            	  	
            	  	int status=q.getStatus();//����״̬
            	  	if(status==0){
						statusname="�ҵĴ���";
					}
            	  	jsonObject.put("processstatus", statusname);//����״̬
            	  	
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
	
	/**
	 * ά���������Ǽ�-�鿴
	 * @param userid
	 * @param billno
	 * @return
	 */
	@GET
	@Path("/wbzldisplay/{userid}/{billno}")
	@Produces("application/json")
	public Response getWbzlDisplay(
	@PathParam("userid") String userid, 
	@PathParam("billno") String billno
	){
		Session session = null;
		Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>ά���������Ǽ�-����鿴�༭ҳ��");
		try{
			session = HibernateUtil.getSession();
			
			List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
			if(userList!=null&&userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			
			String roleid=user.getRoleid();
			String sql="from QualityCheckManagement q where q.billno ='"+billno.trim()+"'";
			Query query = session.createQuery(sql);
            ArrayList qList = (ArrayList) query.list();
             //cpList�Ƿ���ֵ
            if (qList != null && qList.size()>0 ) {
            	
            	QualityCheckManagement q=(QualityCheckManagement) qList.get(0);
            	  	JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("billno", q.getBillno());
            	  	jsonObject.put("elevatorno", q.getElevatorNo());//���ݱ��
            	  	
            	  	String elevatortype=q.getElevatorType();
            	  	String elevatortypename="";
            	  	if(elevatortype.equals("T")){
            	  		elevatortypename="ֱ��";
					}else if(elevatortype.equals("F")){
						elevatortypename="����";
					}
            	  	jsonObject.put("elevatortype", elevatortype);//��������
            	  	jsonObject.put("elevatortypename", elevatortypename);//������������
            	  	jsonObject.put("salescontractno", q.getSalesContractNo());//���ۺ�ͬ��
            	  	jsonObject.put("projectname", q.getProjectName());//��Ŀ����
            	  	jsonObject.put("maintdivision",bd.getName(session, "Company", "comfullname", "comid", q.getMaintDivision()));
            	  	jsonObject.put("maintstation",bd.getName(session, "Storageid", "storagename", "storageid",q.getMaintStation()));
            	  	jsonObject.put("maintpersonnel",bd.getName(session, "Loginuser", "username", "userid", q.getMaintPersonnel()));//ά����
            	  	jsonObject.put("personnelphone", q.getPersonnelPhone());//ά�����绰
            	  	jsonObject.put("maintcontractno", q.getMaintContractNo());//ά����ͬ��
            	  	jsonObject.put("superviseid", bd.getName(session, "Loginuser", "username", "userid", q.getSuperviseId()));//�ֳ�������Ա
            	  	jsonObject.put("supervisephone", q.getSupervisePhone());//������Ա��ϵ�绰
            	  	//jsonObject.put("checkspeople", bd.getName(session, "Loginuser", "username", "userid", q.getChecksPeople()));//�����
            	  	jsonObject.put("checksdatetime", q.getChecksDateTime());//���ʱ��
            	  	jsonObject.put("totalpoints", q.getTotalPoints());//�ܵ÷�
            	  	jsonObject.put("scorelevel", q.getScoreLevel());//�÷ֵȼ�
            	  	jsonObject.put("supervopinion", q.getSupervOpinion());//�������
            	  	
            	  	String r5=q.getR5();//����������Ա
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
	               	 jsonObject.put("qualityuserid", r5);
	               	 jsonObject.put("qualityusername", r5name);//����������Ա
	               	 jsonObject.put("checkrem", q.getR4());//��鱸ע
	               	 
	               	 //��ȡ���ݵ��������λ��
	               	ElevatorCoordinateLocation ecl=(ElevatorCoordinateLocation)session.get(ElevatorCoordinateLocation.class, q.getElevatorNo());
	               	if(ecl!=null){
						jsonObject.put("elocation", ecl.getRem());//¥����
						jsonObject.put("elelocation", ecl.getElevatorLocation());//����λ��
	               	 	jsonObject.put("begindimension", ecl.getBeginDimension());//γ��
	               	 	jsonObject.put("beginlongitude", ecl.getBeginLongitude());//����
	               	}else{
	               		jsonObject.put("elocation", "");//¥����
						jsonObject.put("elelocation", "");//����λ��
	               	 	jsonObject.put("begindimension", "");//γ��
	               	 	jsonObject.put("beginlongitude", "");//����
	               	}
            	  	
            	  	//��ȡ���ص���ά�޼�¼��·��
    				String path="D:\\contract\\����ά��������鵥·��.txt";
    				BufferedReader reader= new BufferedReader(new FileReader(path));
    				String downloadaddr=reader.readLine();
    				reader.close();
    				//String downloadaddr="http://10.10.0.5:8080/XJSCRM/PrintQualityCheckServlet?id=";//����
    				//String downloadaddr="http://www.xjelevator.com:9000/XJSCRM/PrintQualityCheckServlet?id=";//��ʽ
    				if(downloadaddr!=null && !downloadaddr.trim().equals("")){
    					jsonObject.put("downloadaddr", downloadaddr.trim()+q.getBillno());//����֪ͨ�����ص�ַ
    				}else{
    					jsonObject.put("downloadaddr", "");//����֪ͨ�����ص�ַ
    				}
            	  	
            	  	/**=====================��ȡͼƬ=========================*/
     				String folder = PropertiesUtil.getProperty("QualityCheckManagement.file.upload.folder");
     				BASE64Encoder base=new BASE64Encoder();
     				//�ͻ�ǩ��
     				if(q.getCustomerSignature()!=null && !q.getCustomerSignature().trim().equals("")){
     					String filepath=folder+q.getCustomerSignature();
     					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������
     					jsonObject.put("customersignature", base.encode(imgbyte));//��������������
     				}else{
     					jsonObject.put("customersignature", "");
     				}
     				//�ͻ���Ƭ
     				if(q.getCustomerImage()!=null && !q.getCustomerImage().trim().equals("")){
     					String filepath=folder+q.getCustomerImage();
     					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������		
     					jsonObject.put("customerimage", base.encode(imgbyte));//��������������
     				}else{
     					jsonObject.put("customerimage", "");
     				}
     				/**=====================��ȡͼƬ=========================*/
            	  	
            	  	//ά���������ֱ�Ǽ�����Ϣ
                    String hql ="from MarkingScoreRegister m where m.billno='"+q.getBillno()+"'";
                    JSONArray detaillist=new JSONArray();
                    List mList=session.createQuery(hql).list();
                    if (mList != null && mList.size()>0 ) {
                    	for(int i=0;i<mList.size();i++){
                    		JSONObject mjsonObject=new JSONObject();
                    		MarkingScoreRegister m=(MarkingScoreRegister) mList.get(i);
                    		mjsonObject.put("jnlno", m.getJnlno());//��ˮ��
                    		mjsonObject.put("msid", m.getMsId());//���ִ���
                    		mjsonObject.put("msname", m.getMsName());//��������
                    		mjsonObject.put("fraction", m.getFraction());//����
                    		mjsonObject.put("rem", m.getRem());//��ע
                    		
                    		 //ά���������ֱ�Ǽ���ϸ
                    		 JSONArray detaillist2=new JSONArray();
                    		 String hql2="from MarkingScoreRegisterDetail ms where ms.jnlno='"+m.getJnlno()+"'";
                    		 List msList=session.createQuery(hql2).list();
                             if (msList != null && msList.size()>0 ) {
                             	for(int j=0;j<msList.size();j++){
                             		JSONObject msjsonObject=new JSONObject();
                             		MarkingScoreRegisterDetail ms=(MarkingScoreRegisterDetail) msList.get(j);
                            		msjsonObject.put("numno", ms.getNumno());//���
                            		msjsonObject.put("msid", ms.getMsId());//���ִ���
                            		msjsonObject.put("detailid", ms.getDetailId());//������ϸ����
                            		msjsonObject.put("detailname", ms.getDetailName());//������ϸ����
                            		detaillist2.put(j, msjsonObject);
                             	}
                             }
                            mjsonObject.put("detaillist2", detaillist2);
                            
                    		detaillist.put(i, mjsonObject);
                    	}
                    }
                	jsonObject.put("detaillist", detaillist);
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
     * ά���������Ǽ�-��ȡά������������
     * @param userid
     * @param data
     * @return
     */
	@POST
	@Path("/markingscore")
	@Produces("application/json")
	public Response getMarkingScore (@FormParam("data") JSONObject data){
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>ά���������Ǽ�-��ȡά������������");
		try{
			session = HibernateUtil.getSession();
			String msname=(String) data.get("msname");
			String elevatortype =(String) data.get("elevatortype");
			String msidarr =(String) data.get("msidarr");
			
			String sql ="from MarkingScore q where q.enabledFlag='Y' and elevatorType='"+elevatortype+"' ";
			if(msidarr!=null && !msidarr.trim().equals("NULL") && !msidarr.trim().equals("")){
				msidarr=msidarr.replaceAll(",", "','");
				sql+=" and q.msId not in('"+msidarr+"') ";
			}
			if(msname!=null && !"".equals(msname.trim()) && !"NULL".equals(msname.trim())){
				sql+=" and (q.msName like '%"+msname.trim()+"%' or q.msId like '%"+msname.trim()+"%')";
			}
			sql+=" order by q.orderby ";
			List selectList=session.createQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					MarkingScore m =(MarkingScore) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("msid", m.getMsId());//���ִ���
					jsonObject.put("msname", m.getMsName());//��������
					jsonObject.put("fraction", m.getFraction());//����
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
	
	/**
	 * ά���������Ǽ�-��ȡά������������ϸ
	 * @param userid
	 * @param msid
	 * @param detailname
	 * @param data
	 * @return
	 */
	@POST
	@Path("/markingscoredetail")
	@Produces("application/json")
	public Response getMarkingScoreDetail(@FormParam("data") JSONObject data){

		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>ά���������Ǽ�-��ȡά������������ϸ");
		try{
			session = HibernateUtil.getSession();
			
			String msid=(String) data.get("msid");
			String detailname=(String) data.get("detailname");
			String detailidarr=(String) data.get("detailidarr");
			
			String sql ="from MarkingScoreDetail m where m.markingScore.msId='"+msid.trim()+"' ";
			if(detailidarr!=null && !detailidarr.trim().equals("NULL") && !detailidarr.trim().equals("")){
				detailidarr=detailidarr.trim().replaceAll(",", "','");
				sql+=" and m.detailId not in('"+detailidarr+"') ";
			}
			if(detailname!=null && !detailname.trim().equals("") && !detailname.trim().equals("NULL")){
				sql+=" and (m.detailName like '%"+detailname.trim()+"%' or m.detailId like '%"+detailname.trim()+"%')";
			}
			sql+=" order by m.markingScore.msId ";
			List selectList=session.createQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					MarkingScoreDetail m =(MarkingScoreDetail) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
					jsonObject.put("msid", m.getMarkingScore().getMsId());//���ִ���
					jsonObject.put("detailid", m.getDetailId());//������ϸ����
					jsonObject.put("detailname", m.getDetailName());//������ϸ����
					jobiArray.put(i, jsonObject);
				}	
			}
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
            rejson.put("data", jobiArray);
      		//rejson.put("data", CommonUtil.Pagination(data, jobiArray));
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
	 * ��ȡά���������������ϸ��ͼƬ
	 * @param userid
	 * @param elevatortype
	 * @param issuecoding
	 * @return markingscore
	 */
	@GET
	@Path("/markingscoreimgae/{jnlno}")
	@Produces("application/json")
	public Response getImageList(@PathParam("jnlno") String jnlno){
		Session session = null;
		//Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONObject json2 = new JSONObject();//������� {}
		JSONArray returnarr=new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>ά���������Ǽ�-��ȡά���������������ϸ��ͼƬ");
		
		MarkingScoreRegisterFileinfo hefile=null;
		
		String folder = PropertiesUtil.getProperty("MTSComply.file.upload.folder");
		
		BASE64Encoder base=new BASE64Encoder();
		try{
			session = HibernateUtil.getSession();
			
			/**=====================��ȡ ά���������ֱ�Ǽ���ϸ =========================*/
			JSONArray infoarr=new JSONArray();//�������� []
			//ά���������ֱ�Ǽ���ϸ
	   		 String hql2="from MarkingScoreRegisterDetail ms where ms.jnlno='"+jnlno.trim()+"'";
	   		 List msList=session.createQuery(hql2).list();
	            if (msList != null && msList.size()>0 ) {
	            	for(int i=0;i<msList.size();i++){
	            		JSONObject msjsonObject=new JSONObject();
	            		MarkingScoreRegisterDetail ms=(MarkingScoreRegisterDetail) msList.get(i);
		           		msjsonObject.put("numno", ms.getNumno());//���
		           		msjsonObject.put("msid", ms.getMsId());//���ִ���
		           		msjsonObject.put("detailid", ms.getDetailId());//������ϸ����
		           		msjsonObject.put("detailname", ms.getDetailName());//������ϸ����
		           		infoarr.put(i, msjsonObject);
	            	}
	            	json2.put("infoarr", infoarr);
	            }

			/**=====================��ȡ ά���������ֱ�����=========================*/
			String hql="from MarkingScoreRegisterFileinfo h where h.jnlno ='"+jnlno+"' ";
			List fileList=session.createQuery(hql).list();

			JSONArray imgarr=new JSONArray();//�������� []
			if(fileList!=null&&fileList.size()>0){
				for(int j=0;j<fileList.size();j++){
					hefile=(MarkingScoreRegisterFileinfo)fileList.get(j);
					String filepath=folder+"MarkingScoreRegisterFileinfo"+"/"+hefile.getNewFileName();
					//��ͼƬת��Ϊ��������
					byte[] imgbyte=CommonUtil.imageToByte(filepath);
					
					JSONObject objf=new JSONObject();
					objf.put("filesid", hefile.getFileSid());
					objf.put("oldfilename", hefile.getOldFileName());
					objf.put("imgpic", base.encode(imgbyte));//��������������
					imgarr.put(j,objf);
				}
				json2.put("imgarr", imgarr);
			}
			
			returnarr.put(0,json2);
			
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", returnarr);
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
	 * ����ͼƬ��Ϣ ���ҷ���jnlno��ˮ��
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/azwbimagesave")
	@Produces("application/json")
	public Response saveImageInfo (@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>ά���������Ǽ�-����ά���������������ϸ��ͼƬ");
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String userid=(String) data.get("userid");//��¼�û�����
			String billno=(String) data.get("billno");//������ˮ��
			String jnlno=(String) data.get("jnlno");//��ˮ��
			String msid=(String) data.get("msid");//���ִ���
			String msname=(String) data.get("msname");//��������
			String fraction=(String) data.get("fraction");//����
			if(fraction==null || fraction.trim().equals("")){
				fraction="0";
			}
			String rem=(String) data.get("rem");//��ע
			
			/**==========================ά���������ֱ�Ǽ�����Ϣ==========================*/
			MarkingScoreRegister hecir=null;
			if(jnlno!=null && !jnlno.equals("")){
				//��ˮ�Ŵ��ھ��޸�
        		hecir = (MarkingScoreRegister) session.get(MarkingScoreRegister.class, jnlno);	
            	hecir.setRem(rem);
            	session.update(hecir);
            	
            	//ɾ��ά���������ֱ�Ǽ���ϸ
            	session.createQuery("delete MarkingScoreRegisterDetail where jnlno='"+jnlno+"'").executeUpdate();

            }else{
            	//�����ھ��½�
                hecir = new MarkingScoreRegister();
				String[] jnlnos = CommonUtil.getBillno(CommonUtil.getToday().substring(2,4), "MarkingScoreRegister", 1);	
				jnlno=jnlnos[0];

				hecir.setJnlno(jnlno);
				hecir.setBillno(billno);
				hecir.setMsId(msid);
				hecir.setMsName(msname);
				hecir.setFraction(Double.parseDouble(fraction));
				hecir.setIsDelete("N");
				hecir.setRem(rem);
				session.save(hecir);
            }
			
			/**==================ά���������ֱ�Ǽ���ϸ==================*/
			JSONArray infolist=(JSONArray) data.get("infolist");//ά���������ֱ�Ǽ���ϸ
			if(infolist!=null && infolist.length()>0){
				MarkingScoreRegisterDetail msrd=null;
				for(int m=0;m<infolist.length();m++){
					msrd=new MarkingScoreRegisterDetail();
					JSONObject object=(JSONObject) infolist.get(m);
					msrd.setJnlno(jnlno);
					msrd.setMsId((String) object.get("msid"));
					msrd.setDetailId((String) object.get("detailid"));
					msrd.setDetailName((String) object.get("detailname"));
					session.save(msrd);
				}
			}
			
			/**==========================����ͼƬ==========================*/
			JSONArray imagelist=(JSONArray) data.get("imagelist");//�ϴ�ͼƬ
			if(imagelist!=null && imagelist.length()>0){
				MarkingScoreRegisterFileinfo fileInfo=null;
				String folder = PropertiesUtil.getProperty("MTSComply.file.upload.folder").trim();
				
				String curdate=DateUtil.getDateTime("yyyyMMddHHmmss");
				
				for(int i=0;i<imagelist.length();i++){
					JSONObject object=(JSONObject) imagelist.get(i);
					String imgpic=(String) object.get("imgpic");//��ˮ��
					
					byte[] image=new BASE64Decoder().decodeBuffer(imgpic);
					String newfilename=userid+"_"+curdate+"_"+i+".jpg";
					String filepath="MarkingScoreRegisterFileinfo"+"/";
	
					//����ͼƬ
					File f=new File(folder+filepath);
					f.mkdirs();
					FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
					fos.write(image);
					fos.flush();
					fos.close();
					
					//����ͼƬ��Ϣ�����ݿ�
					fileInfo=new MarkingScoreRegisterFileinfo();
					fileInfo.setJnlno(jnlno);
					fileInfo.setOldFileName(newfilename);
					fileInfo.setNewFileName(newfilename);
					fileInfo.setFileSize(Double.valueOf(0));
					fileInfo.setFilePath(folder+filepath+newfilename);
					fileInfo.setFileFormat("-");
					fileInfo.setUploadDate(CommonUtil.getNowTime());
					fileInfo.setUploader(userid);
					session.save(fileInfo);
				}
			}
			tx.commit();
			
			//����  ά���������ֱ�Ǽ�����Ϣ  �� ��ˮ��
			JSONObject object=new JSONObject();
			object.put("jnlno", jnlno);
			jobiArray.put(0, object);
			
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			tx.rollback();
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
	 * ά���������Ǽ�-����
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/wbzladd")
	@Produces("application/json")
	public Response saveWbzl (@FormParam("data")JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		JbpmExtBridge jbpmExtBridge=null;
		
		System.out.println(">>>>>>>>ά���������Ǽ�-��������Ϣ");
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			String billno=(String) data.get("billno");//��ˮ��
			String elevatorno=(String) data.get("elevatorno");//���ݱ��
			String userid=(String) data.get("userid");//¼����
			String checksdatetime=(String) data.get("checksdatetime");//���ʱ��
			//String totalpoints=(String) data.get("totalpoints");//�ܵ÷�
			//String scorelevel=(String) data.get("scorelevel");//�÷ֵȼ�
			String supervopinion=(String) data.get("supervopinion");//�������
			String processstatus=(String) data.get("processstatus");//����״̬
			String submittype=(String) data.get("submittype");//�ύ��־
			String qualityuserid=(String) data.get("qualityuserid");//���뱣����Ա
	
			JSONArray detaillist=(JSONArray) data.get("detaillist");//list
			String hecirJnlnos="";
			double fractiontotal=0;
			if(detaillist!=null&&detaillist.length()>0){
				for(int i=0;i<detaillist.length();i++){
					JSONObject object=(JSONObject) detaillist.get(i);
					String jnlno=(String) object.get("jnlno");//��ˮ��
					if(jnlno!=null && !"".equals(jnlno)){
						hecirJnlnos+=i==0 ? jnlno : "','"+jnlno;
				    }
					
					String fractionstr=(String) object.get("fraction");//����
					if(fractionstr==null || fractionstr.trim().equals("")){
						fractionstr="0";
					}
					fractiontotal+=Double.parseDouble(fractionstr);
				}
			}
			
			/**===============����÷ֵȼ�===============*/
			System.out.println(">>>>��ʼ����÷ֵȼ�===��ʼ");
			HashMap hmap=this.isQualified(fractiontotal);
			String totalpoints=(String)hmap.get("totalpoints");
			String scorelevel=(String)hmap.get("scorelevel");
			System.out.println(">>>>��ʼ����÷ֵȼ�===����");
			
			
			QualityCheckManagement q =(QualityCheckManagement)session.get(QualityCheckManagement.class, billno);
			q.setElevatorNo(elevatorno);//���ݱ��
			q.setChecksPeople(userid);
			q.setChecksDateTime(checksdatetime);
			q.setTotalPoints(Double.valueOf(totalpoints));
			q.setScoreLevel(scorelevel);
			q.setSupervOpinion(supervopinion);
			q.setProcessStatus(processstatus);
	        //q.setOperId(userid);
	        //q.setOperDate(CommonUtil.getNowTime());
	        q.setR5(qualityuserid);
			
	        Integer status=q.getStatus();
			if("Y".equals(submittype)){
				String processDefineID = Grcnamelist1.getProcessDefineID("qualitycheckmanagement", userid);// ���̻���id
				if(processDefineID == null || processDefineID.equals("")){
					System.out.println("�ֻ��˵Ǽ�>>> δ��������������Ϣ������������");
					throw new Exception();
				}
				
				if(status!=null && status==0){
					//��ȡ������Ϣ ������ͨ����
					long taskid=0;
					String taskname="";
					String sqlc="select ID_,NAME_ from JBPM_TASKINSTANCE where TOKEN_="+q.getTokenId()+" and isnull(END_,'')=''";
					ResultSet rs=session.connection().prepareStatement(sqlc).executeQuery();
					if(rs.next()){
						taskid=rs.getLong("ID_");
						taskname=rs.getString("NAME_");
					}
					/*=============== ��������������ʼ =================*/
					jbpmExtBridge = new JbpmExtBridge();
					HashMap paraMap = new HashMap();
					ProcessBean pd = jbpmExtBridge.getProcessBeanUseTI(taskid);
					
					pd.addAppointActors("");// ����̬��ӵ���������
					Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "�����鳤���", userid);
					pd = jbpmExtBridge.goToNext(taskid, "�ύ", userid, paraMap);// ���
					/*=============== ���������������� =================*/
					q.setTokenId(pd.getToken());
					q.setStatus(pd.getStatus());
					q.setProcessName(pd.getNodename());	
					session.save(q);
		
					// �����������������Ϣ
					QualityCheckProcess process = new QualityCheckProcess();
					process.setBillno(billno);
					process.setTaskId(pd.getTaskid().intValue());// �����
					process.setTaskName(taskname);// ��������
					process.setTokenId(pd.getToken());// ��������
					process.setUserId(userid);
					process.setDate1(CommonUtil.getToday());
					process.setTime1(CommonUtil.getTodayTime());
					process.setApproveResult("�ύ");
					process.setApproveRem("�ֻ�����");
					session.save(process);
					
				}else{
					/**=============== ����������ʵ����ʼ ===================**/
					HashMap paraMap = new HashMap();
					jbpmExtBridge=new JbpmExtBridge();
					ProcessBean pd = null;		
					pd = jbpmExtBridge.getPb();
		
					Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "�����鳤���", userid);
					pd=jbpmExtBridge.startProcess(WorkFlowConfig.getProcessDefine(processDefineID),userid,userid,billno,"",paraMap);
					/**==================== ���̽��� =======================**/
					q.setStatus(pd.getStatus());
					q.setTokenId(pd.getToken());
					q.setProcessName("�����鳤���");
				}
			}
			
			/**=======================����ͼƬ=============================*/
			String folder2 = PropertiesUtil.getProperty("QualityCheckManagement.file.upload.folder");
			String filepath2=CommonUtil.getNowTime("yyyy-MM-dd")+"/";
			//����ǩ��
			String customersignature=(String) data.get("customersignature");
			if(customersignature!=null && !customersignature.trim().equals("")){
				//customersignature=customersignature.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
				String[] signatures=customersignature.split(",");
				if(signatures!=null && signatures.length>1){
					byte[] image=new BASE64Decoder().decodeBuffer(signatures[1]);
					String newfilename=q.getBillno()+"_0.jpg";
					//����ͼƬ
					File f=new File(folder2+filepath2);
					f.mkdirs();
					FileOutputStream fos=new FileOutputStream(folder2+filepath2+newfilename);
					fos.write(image);
					fos.flush();
					fos.close();
					//����ͼƬ��Ϣ�����ݿ�
					q.setCustomerSignature(filepath2+newfilename);
				}
			}
			//��������
			String customerimage=(String)data.get("customerimage");
			if(customerimage!=null && !customerimage.trim().equals("")){
				//customerimage=customerimage.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
				String[] cimages=customerimage.split(",");
				if(cimages!=null && cimages.length>1){
					byte[] image=new BASE64Decoder().decodeBuffer(cimages[1]);
					String newfilename=q.getBillno()+"_1.jpg";
					//����ͼƬ
					File f=new File(folder2+filepath2);
					f.mkdirs();
					FileOutputStream fos=new FileOutputStream(folder2+filepath2+newfilename);
					fos.write(image);
					fos.flush();
					fos.close();
					//����ͼƬ��Ϣ�����ݿ�
					q.setCustomerImage(filepath2+newfilename);
				}
			}
			/**=======================����ͼƬ=============================*/
			session.save(q);
			
			/**=======================ά���������ֱ�Ǽ�����Ϣ=======================*/
			//��ɾ�����治����,��ɾ�����ݿ�����
			List filelist=null;
			if(!hecirJnlnos.equals("")){
				String filesql="from MarkingScoreRegisterFileinfo h where h.jnlno in(select jnlno from MarkingScoreRegister h where h.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"'))";
				filelist=session.createQuery(filesql).list();
				
				//ɾ��ά���������ֱ�����
				session.createQuery("delete MarkingScoreRegisterFileinfo h where h.jnlno in(select jnlno from MarkingScoreRegister h where h.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"'))").executeUpdate();
				//ά���������ֱ�Ǽ���ϸ
				session.createQuery("delete MarkingScoreRegisterDetail h where h.jnlno in(select jnlno from MarkingScoreRegister h where h.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"'))").executeUpdate();
				//ά���������ֱ�Ǽ�����Ϣ
				session.createQuery("delete MarkingScoreRegister h where h.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"')").executeUpdate();
			}else{
				String filesql="from MarkingScoreRegisterFileinfo h where h.jnlno in(select jnlno from MarkingScoreRegister h where h.billno='"+billno+"')";
				filelist=session.createQuery(filesql).list();
	
				session.createQuery("delete MarkingScoreRegisterFileinfo h where h.jnlno in(select jnlno from MarkingScoreRegister h where h.billno='"+billno+"')").executeUpdate();
				session.createQuery("delete MarkingScoreRegisterDetail h where h.jnlno in(select jnlno from MarkingScoreRegister h where h.billno='"+billno+"')").executeUpdate();
				session.createQuery("delete MarkingScoreRegister h where h.billno='"+billno+"'").executeUpdate();
			}
			//��ɾ��Ӳ�̵�ͼƬ
			if(filelist!=null && filelist.size()>0){
				String folder ="MTSComply.file.upload.folder";
				folder = PropertiesUtil.getProperty(folder).trim();
				MarkingScoreRegisterFileinfo msrf=null;
				for(int d=0;d<filelist.size();d++){
					msrf=(MarkingScoreRegisterFileinfo)filelist.get(d);
					
					HandleFile hf = new HandleFileImpA();
					String localpath=folder+"MarkingScoreRegisterFileinfo"+"/"+msrf.getNewFileName();
					hf.delFile(localpath);
				}
			}
			
			//�ڱ��� ά���������ֱ�Ǽ�����Ϣ
			if(detaillist!=null && detaillist.length()>0){
				MarkingScoreRegister msr=null;
				for(int i=0;i<detaillist.length();i++){
					JSONObject jsonObject=(JSONObject) detaillist.get(i);
					String jnlno=(String) jsonObject.get("jnlno");//��ˮ��
					String msid=(String) jsonObject.get("msid");//���ִ���
					String msname=(String) jsonObject.get("msname");//��������
					String fraction=(String) jsonObject.get("fraction");//����
					if(fraction==null || fraction.trim().equals("")){
						fraction="0";
					}
					String rem=(String) jsonObject.get("rem");//��ע
					
					if(jnlno!=null && !jnlno.equals("")){
						msr=(MarkingScoreRegister)session.get(MarkingScoreRegister.class, jnlno);
						msr.setRem(rem);
						msr.setIsDelete("N");
						session.update(msr);
					}else{
						msr =new MarkingScoreRegister(); 
						msr.setBillno(billno);
						String[] jnlnos = CommonUtil.getBillno(CommonUtil.getToday().substring(2,4), "MarkingScoreRegister", 1);		
						msr.setJnlno(jnlnos[0]);
						msr.setMsId(msid);//���ִ���
						msr.setMsName(msname);//��������
						msr.setFraction(Double.parseDouble(fraction));//����
						msr.setRem(rem);//��ע
						msr.setIsDelete("N");
						session.save(msr);
					}
				}
			}
	
			tx.commit();
			json.put("code", "200");
			json.put("info", "OK");
	        rejson.put("status", json);
	  		rejson.put("data", jobiArray);
		}catch(Exception ex){
			if (jbpmExtBridge != null) {
				jbpmExtBridge.setRollBack();
			}
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
	        	if(jbpmExtBridge!=null){
					jbpmExtBridge.close();
				}
	        } catch (Exception hex) {
	            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
	        }
	
	    }
		
		return Response.status(200).entity(rejson.toString()).build();
	}

	/**
	 * ɾ���ļ�
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@GET
	@Path("/deletefile/{filesid}")
	@Produces("application/json")
	public Response deleteFile (@PathParam("filesid") String filesid) throws JSONException{
		Session session = null;
		Transaction tx = null;
		//Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []

		System.out.println(">>>>>>>>ά���������Ǽ�-ɾ��ͼƬ");
		
		MarkingScoreRegisterFileinfo hecf=null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String folder ="MTSComply.file.upload.folder";
			folder = PropertiesUtil.getProperty(folder).trim();
			if(filesid!=null && filesid.length()>0){
				
				hecf=(MarkingScoreRegisterFileinfo) session.get(MarkingScoreRegisterFileinfo.class,Integer.valueOf(filesid));
				String filename=hecf.getNewFileName();
				session.delete(hecf);
				session.flush();
				
				HandleFile hf = new HandleFileImpA();
				String localpath=folder+"MarkingScoreRegisterFileinfo"+"/"+filename;
				hf.delFile(localpath);
			}
			
			tx.commit();
			
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", jobiArray);
		}catch(Exception ex){
			tx.rollback();
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
	
	//�������� �ܵ÷֣��÷ֵȼ�
	/** 
	  	�ܵ÷�=100-��ֵ�ĺϼƣ�
	  	--�÷ֵȼ�=�����ܵ÷ֽ����жϣ�90���������㣬80��-90�ֺϸ�80�����²��ϸ�
	  	�÷ֵȼ�=80-84��Ϊ�ϸ�85-89Ϊ���ã� 90����Ϊ���� ��2017-05-19�޸ġ�
	*/
	private HashMap isQualified(double fraction){
		HashMap hmap=new HashMap();
		
		String scorelevel="";
		double totalpoints=100-fraction;
		
		if(totalpoints>=90){
			scorelevel="����";
		}else if(totalpoints>=85 && totalpoints<90){
			scorelevel="����";
		}else if(totalpoints>=80 && totalpoints<85){
			scorelevel="�ϸ�";
		}else{
			scorelevel="���ϸ�";
		}
		
		hmap.put("totalpoints", String.valueOf(totalpoints));
		hmap.put("scorelevel", scorelevel);
		
		return hmap;
		
	}
	/**
	 * ά���������Ǽ�=ά��������Ա
	 * @param String userid
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/qualitybylist/{billno}")
	@Produces("application/json")
	public Response bypglist(@PathParam("billno") String billno) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		Connection con=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		
		System.out.println(">>>>>>>>ά���������Ǽ�-ά��������Ա");
		
		try{
			session = HibernateUtil.getSession();
			con=session.connection();

			String sql="exec sp_qualityby "+billno;

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
	  
}
