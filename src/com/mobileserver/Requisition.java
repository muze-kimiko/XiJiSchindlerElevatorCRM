package com.mobileserver;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
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
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.mobileofficeplatform.accessoriesrequisition.AccessoriesRequisition;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.wbfileinfo.Wbfileinfo;
import com.gzunicorn.struts.action.xjsgg.SmsService;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
/**
 * �ֻ�APP�˵��ã����������
 * @author Crunchify
 */
@Path("/Requisition")
public class Requisition {
	
	BaseDataImpl bd = new BaseDataImpl();
	
	/**
	 * �������-�б�(��ѯ)
	 * @param userid
	 * @param singleno
	 * @param sdate
	 * @param edate
	 * @return
	 */
	@GET
	@Path("/pjsqlist/{data}")
	@Produces("application/json")
	public Response getReqList(
			@PathParam("data") JSONObject data 
			){
		Session session = null;
		Transaction tx = null;
		Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
        
		System.out.println(">>>>>>>>�������--��Ϣ�б�");
		
		try{
			String sdate=(String) data.get("sdate");
	        String edate=(String) data.get("edate");
	        String userid=(String) data.get("userid");
	        String singleno=(String) data.get("singleno");
	        String elevatorno=(String) data.get("elevatorno");//���ݱ��
	        String handlestatus=(String) data.get("handlestatus");//����״̬

			session = HibernateUtil.getSession();
			
			List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
			if(userList!=null&&userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			
			String roleid=user.getRoleid();
			String sql="from AccessoriesRequisition a where 1=1 ";
			//ά��վ��A49��ά����A50,ά������ A03��ά�޼���Ա A53��
			if(roleid.trim().equals("A50") || roleid.trim().equals("A49") || roleid.trim().equals("A53")){
				//A50  ά����  
				sql+=" and a.operId ='"+user.getUserid().trim()+"' ";
			}else if(roleid.trim().equals("A03")){
				//A03  ά������
				sql+=" and ((a.maintStation like '"+user.getStorageid().trim()+"%' and a.handleStatus='1') "
						+ " or (a.handleStatus='3' and a.operId='"+userid+"')) ";
			}else{
				//������
				sql+=" and a.maintStation like '' ";	
			}
			if (singleno != null && !"".equals(singleno)){
				sql+=" and a.singleNo like '%"+singleno+"%'";		
			}
			if (elevatorno != null && !"".equals(elevatorno)){
				sql+=" and a.elevatorNo like '%"+elevatorno+"%'";		
			}
			if (handlestatus != null && !"".equals(handlestatus)){
				sql+=" and a.handleStatus like '"+handlestatus+"'";		
			}
			if (sdate != null && !"".equals(sdate)) {
				sql += " and a.operDate >= '"+sdate.trim()+" 00:00:00'";
			}
			if (edate != null && !"".equals(edate)) {
				sql += " and a.operDate <= '"+edate.trim()+" 99:99:99'";
			}
			
			if(roleid.trim().equals("A50") || roleid.trim().equals("A49") 
					|| roleid.trim().equals("A53") || roleid.trim().equals("A03")){
				//ά������¼�ģ��б���ʾΪ 'ά����ȷ��'����ǰ��,ά���������ں��档
				sql +=" order by case when a.handleStatus='3' then 0 "
						+ "when a.handleStatus='1' then 1 else 2 end, "
						+ "a.operDate desc";
	  		}else{
	  			sql +=" order by a.operDate desc";
	  		}

			//System.out.println(">>>"+sql);
			
			Query query = session.createQuery(sql);
            ArrayList tList = (ArrayList) query.list();
             //cpList�Ƿ���ֵ
            if (tList != null && tList.size()>0 ) {
            	for(int i=0;i<tList.size();i++){
            		AccessoriesRequisition a=(AccessoriesRequisition) tList.get(i);
            	  	JSONObject jsonObject=new JSONObject();
            	  	
            	  	String hstatus=a.getHandleStatus();
            	  	String hstatusname="";
            	  	//����״̬ ��1 ά����������ˣ�2 ��������Ա��ˣ�3 ά����ȷ�ϣ�4 �ɼ��˻أ�5 �ѹرգ�6 ��ֹ��
            	  	if(hstatus!=null && hstatus.trim().equals("1")){
            	  		if(roleid.trim().equals("A03")){
            	  			hstatusname="<b>ά�����������</b>";
            	  		}else{
            	  			hstatusname="ά�����������";
            	  		}
            	  	}else if(hstatus!=null && hstatus.trim().equals("2")){
            	  		hstatusname="��������Ա���";
            	  	}else if(hstatus!=null && hstatus.trim().equals("3")){
            	  		if(roleid.trim().equals("A50") || roleid.trim().equals("A49") || roleid.trim().equals("A53")){
            	  			hstatusname="<b>ά����ȷ��</b>";
            	  		}else if(roleid.trim().equals("A03") && userid.equals(a.getOperId())){
            	  			hstatusname="<b>ά����ȷ��</b>";
            	  		}else{
            	  			hstatusname="ά����ȷ��";
            	  		}
            	  	}else if(hstatus!=null && hstatus.trim().equals("4")){
            	  		hstatusname="�ɼ��˻�";
            	  	}else if(hstatus!=null && hstatus.trim().equals("5")){
            	  		hstatusname="�ѹر�";
            	  	}else if(hstatus!=null && hstatus.trim().equals("6")){
            	  		hstatusname="��ֹ";
            	  	}
            	  	
            	  	jsonObject.put("handlestatus", hstatus);
            	  	jsonObject.put("hstatusname", hstatusname);
            	  	jsonObject.put("appno", a.getAppNo());
            	  	jsonObject.put("oldno", a.getOldNo());
            	  	jsonObject.put("singleno", a.getSingleNo());
            	  	jsonObject.put("elevatorno", a.getElevatorNo());
            	  	jsonObject.put("operid", a.getOperId());
            	  	jsonObject.put("operName", bd.getName(session, "Loginuser", "username", "userid", a.getOperId()));
            	  	jsonObject.put("operdate", a.getOperDate());
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
	 * �������-�鿴
	 * @param userid
	 * @param appno
	 * @return
	 */
	@GET
	@Path("/pjsqdetail/{userid}/{appno}")
	@Produces("application/json")
	public Response getReqDisplay(@PathParam("userid") String userid,@PathParam("appno") String appno){
		Session session = null;
		Transaction tx = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		Loginuser user=null;
		

		System.out.println(">>>>>>>>�������--�鿴");
		
		try{
			session = HibernateUtil.getSession();
			
			String sql="select a,l.username,l.phone,c.comname,s.storagename"
					+ " from AccessoriesRequisition a,Loginuser l,Company c,Storageid s "
					+ " where a.operId=l.userid and a.maintDivision=c.comid and a.maintStation=s.storageid"
					+ " and a.appNo ='"+appno.trim()+"' ";
			//System.out.println(">>>"+sql);
			Query query = session.createQuery(sql);
            ArrayList tList = (ArrayList) query.list();
             //cpList�Ƿ���ֵ
            if (tList != null && tList.size()>0 ) {
            		Object[] objs=(Object[]) tList.get(0);
            	  	AccessoriesRequisition a=(AccessoriesRequisition) objs[0];
            	  	JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("appno", a.getAppNo());//li
            	  	jsonObject.put("singleno", a.getSingleNo());
            	  	jsonObject.put("elevatorno", a.getElevatorNo());
            	  	
                	String elerem=bd.getName(session, "ElevatorCoordinateLocation", "Rem", "ElevatorNo",a.getElevatorNo());
            	  	jsonObject.put("elenorem", elerem);//��Ŀ���Ƽ�¥����
            	  	
            	  	jsonObject.put("operid", a.getOperId());
            	  	jsonObject.put("operName", objs[1]);
            	  	jsonObject.put("operdate", a.getOperDate());
            	  	jsonObject.put("operphone",objs[2]);
            	  	jsonObject.put("oldno", a.getOldNo());//�ɼ����
            	  	jsonObject.put("newno", a.getNewNo());//�¼����
            	  	jsonObject.put("r4", a.getR4());//��������
            	  	jsonObject.put("r5", a.getR5());//�������Ƽ��ͺ�/��ע
            	  	jsonObject.put("r7", a.getR7());//�������
            	  	jsonObject.put("maintdivision",objs[3]);
            	  	jsonObject.put("maintstation",objs[4]);
            	  	jsonObject.put("personincharge", a.getPersonInCharge());//ά��������
            	  	jsonObject.put("personinchargename", bd.getName(session, "Loginuser", "username", "userid", a.getPersonInCharge()));
            	  	jsonObject.put("picauditrem", a.getPicauditRem());//ά��������������
            	  	jsonObject.put("picauditdate", a.getPicauditDate());//ά���������������
            	  	
            	  	if(a.getIsAgree()!=null && !a.getIsAgree().trim().equals("")){
            	  		jsonObject.put("isagree", a.getIsAgree().trim().equals("N") ? "��ͬ��" : "ͬ��");//�Ƿ�ͬ��
            	  	}else{
            	  		jsonObject.put("isagree", "");//�Ƿ�ͬ��
            	  	}
            	  	jsonObject.put("warehousemanager", a.getWarehouseManager());//�ֹ�¼����
            	  	jsonObject.put("warehousemanagerName", bd.getName(session, "Loginuser", "username", "userid", a.getWarehouseManager()));//�ֹ�¼����
            	  	jsonObject.put("wmdate", a.getWmdate());//�ֹ�¼������
            	  	
            	  	/**=====================��ȡͼƬ=========================*/
    				String folder = PropertiesUtil.getProperty("AccessoriesRequisitionReport.file.upload.folder");
    				BASE64Encoder base64=new BASE64Encoder();
    				//�ɼ�ͼƬ
    				JSONArray olgimgarr=new JSONArray();//�������� []
    				List olgimglist=bd.getWbFileInfoList(session,"AccessoriesRequisition_OldImage",a.getAppNo());
    				if(olgimglist!=null&&olgimglist.size()>0){
    					for(int j=0;j<olgimglist.size();j++){
    						Wbfileinfo wbfile=(Wbfileinfo)olgimglist.get(j);
    						String filepath=folder+wbfile.getFilePath()+wbfile.getNewFileName();
    						//��ͼƬת��Ϊ��������
    						byte[] imgbyte=CommonUtil.imageToByte(filepath);
    						
    						JSONObject objf=new JSONObject();
    						objf.put("filesid", wbfile.getFileSid());
    						objf.put("imgname", wbfile.getOldFileName());
    						objf.put("imgpic", base64.encode(imgbyte));//��������������
    						
    						olgimgarr.put(j,objf);
    					}
    				}
    				jsonObject.put("olgimgarr", olgimgarr);
    				/**
    				if(a.getOldImage()!=null && !a.getOldImage().trim().equals("")){
    					String filepath=folder+a.getOldImage();
    					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������
    					jsonObject.put("oldimage", base64.encode(imgbyte));//��������������
    				}else{
    					jsonObject.put("oldimage", "");
    				}
    				*/
    				//�¼�ͼƬ
    				JSONArray newimgarr=new JSONArray();//�������� []
    				List newimglist=bd.getWbFileInfoList(session,"AccessoriesRequisition_NewImage",a.getAppNo());
    				if(newimglist!=null && newimglist.size()>0){
    					for(int j=0;j<newimglist.size();j++){
    						Wbfileinfo wbfile=(Wbfileinfo)newimglist.get(j);
    						String filepath=folder+wbfile.getFilePath()+wbfile.getNewFileName();
    						//��ͼƬת��Ϊ��������
    						byte[] imgbyte=CommonUtil.imageToByte(filepath);
    						
    						JSONObject objf=new JSONObject();
    						objf.put("filesid", wbfile.getFileSid());
    						objf.put("imgname", wbfile.getOldFileName());
    						objf.put("imgpic", base64.encode(imgbyte));//��������������
    						
    						newimgarr.put(j,objf);
    					}
    				}
    				jsonObject.put("newimgarr", newimgarr);
    				/**
    				if(a.getNewImage()!=null && !a.getNewImage().trim().equals("")){
    					String filepath=folder+a.getNewImage();
    					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������		
    					jsonObject.put("newimage", base64.encode(imgbyte));//��������������
    				}else{
    					jsonObject.put("newimage", "");
    				}
    				*/
    				//��Ʊ��ϢͼƬ
    				JSONArray invoiceImagearr=new JSONArray();//�������� []
    				List invoiceImagelist=bd.getWbFileInfoList(session,"AccessoriesRequisition_invoiceImage",a.getAppNo());
    				if(invoiceImagelist!=null && invoiceImagelist.size()>0){
    					for(int j=0;j<invoiceImagelist.size();j++){
    						Wbfileinfo wbfile=(Wbfileinfo)invoiceImagelist.get(j);
    						String filepath=folder+wbfile.getFilePath()+wbfile.getNewFileName();
    						//��ͼƬת��Ϊ��������
    						byte[] imgbyte=CommonUtil.imageToByte(filepath);
    						
    						JSONObject objf=new JSONObject();
    						objf.put("filesid", wbfile.getFileSid());
    						objf.put("imgname", wbfile.getOldFileName());
    						objf.put("imgpic", base64.encode(imgbyte));//��������������
    						
    						invoiceImagearr.put(j,objf);
    					}
    				}
    				jsonObject.put("invoiceimgarr", invoiceImagearr);
    				/**=====================��ȡͼƬ=========================*/
    				
    				String hstatus=a.getHandleStatus();
            	  	String hstatusname="";
            	  	//����״̬ ��1 ά����������ˣ�2 ��������Ա��ˣ�3 ά����ȷ�ϣ�4 �ɼ��˻أ�5 �ѹرա�
            	  	if(hstatus!=null && hstatus.trim().equals("1")){
            	  		hstatusname="ά�����������";
            	  	}else if(hstatus!=null && hstatus.trim().equals("2")){
            	  		hstatusname="��������Ա���";
            	  	}else if(hstatus!=null && hstatus.trim().equals("3")){
            	  		hstatusname="ά����ȷ��";
            	  	}else if(hstatus!=null && hstatus.trim().equals("4")){
            	  		hstatusname="�ɼ��˻�";
            	  	}else if(hstatus!=null && hstatus.trim().equals("5")){
            	  		hstatusname="�ѹر�";
            	  	}else if(hstatus!=null && hstatus.trim().equals("6")){
            	  		hstatusname="��ֹ";
            	  	}
            	  	jsonObject.put("handlestatus", hstatus);
            	  	jsonObject.put("hstatusname", hstatusname);
            	  	
            	  	if(a.getIsCharges()!=null && !a.getIsCharges().trim().equals("")){
            	  		String ischargesname="";
            	  		if(a.getIsCharges().trim().equals("N")){
            	  			ischargesname="���";
            	  		}else if(a.getIsCharges().trim().equals("Y")){
            	  			ischargesname="�շ�";
            	  		}
            	  		jsonObject.put("ischarges", ischargesname);
            	  	}else{
            	  		jsonObject.put("ischarges", "");
            	  	}
            	  	if(a.getMoney1()!=null){
            	  		jsonObject.put("money1", a.getMoney1()+"");
            	  	}else{
            	  		jsonObject.put("money1", "");
            	  	}
            	  	jsonObject.put("wmrem", a.getWmRem());
            	  	
            	  	if(a.getWmIsAgree()!=null && !a.getWmIsAgree().trim().equals("")){
            	  		jsonObject.put("wmisagree", a.getWmIsAgree().trim().equals("N") ? "��ͬ��" : "ͬ��");
            	  	}else{
            	  		jsonObject.put("wmisagree", "");
            	  	}

            	  	if(a.getWmIsCharges()!=null && !a.getWmIsCharges().trim().equals("")){
            	  		String wmischargesname="";
            	  		if(a.getWmIsCharges().trim().equals("N")){
            	  			wmischargesname="���";
            	  		}else if(a.getWmIsCharges().trim().equals("Y")){
            	  			wmischargesname="�շ�";
            	  		}
            	  		jsonObject.put("wmischarges", wmischargesname);
            	  	}else{
            	  		jsonObject.put("wmischarges", "");
            	  	}
            	  	//1: �ֿ�ֱ����ȡ,2: �ܿ����
            	  	if(a.getWmPayment()!=null && !a.getWmPayment().trim().equals("")){
            	  		String wmpaymentname="";
            	  		if(a.getWmPayment().trim().equals("1")){
            	  			wmpaymentname="�ֿ�ֱ����ȡ";
            	  		}else if(a.getWmPayment().trim().equals("2")){
            	  			wmpaymentname="�ܿ����";
            	  		}else if(a.getWmPayment().trim().equals("3")){
            	  			wmpaymentname="�⹺";
            	  		}
            	  		jsonObject.put("wmpayment", wmpaymentname);
            	  	}else{
            	  		jsonObject.put("wmpayment", "");
            	  	}
            	  	if(a.getMoney2()!=null){
            	  		jsonObject.put("money2", a.getMoney2()+"");
            	  	}else{
            	  		jsonObject.put("money2", "");//�շѽ��
            	  	}
            	  	jsonObject.put("invoicetype", a.getInvoicetype());//��Ʊ����
            	  	jsonObject.put("instock", a.getInstock());//�������Ƿ��п��
            	  	jsonObject.put("expressno", a.getExpressNo());
            	  	jsonObject.put("expressname", a.getExpressName());//�׷���Ʊ����
            	  	jsonObject.put("yjaddress", a.getYjaddress());//�ʼĵ�ַ���绰

            	  	if(a.getJjReturn()!=null && !a.getJjReturn().trim().equals("")){
            	  		String jjname="";
            	  		if(a.getJjReturn().trim().equals("Y")){
            	  			jjname="���˻�";
            	  		}else if(a.getJjReturn().trim().equals("N")){
            	  			jjname="δ�˻�";
            	  		}
            	  		jsonObject.put("jjreturn", jjname);
            	  	}else{
            	  		jsonObject.put("jjreturn", "");
            	  	}
            	  	jsonObject.put("jjresult", a.getJjResult()==null?"":a.getJjResult());
            	  	jsonObject.put("jjoperid", bd.getName(session, "Loginuser", "username", "userid", a.getJjOperId()));
            	  	jsonObject.put("jjoperdate", a.getJjOperDate()==null?"":a.getJjOperDate());
            	  	
            	  	if(a.getR1()!=null&& !a.getR1().equals("")){
						 if(a.getR1().equals("Y") || a.getR1()=="Y"){
							 jsonObject.put("r1","��");
						 }else{
							 jsonObject.put("r1","��");
						 }
					}
					if(a.getR3()!=null&& !a.getR3().equals("")){
						 if(a.getR3().equals("Y") || a.getR3()=="Y"){
							 jsonObject.put("r3","��");
						 }else{
							 jsonObject.put("r3","��");
						 }
					}
					if(a.getIsConfirm()!=null&& !a.getIsConfirm().equals("")){
						 if(a.getIsConfirm().equals("Y") || a.getIsConfirm()=="Y"){
							 jsonObject.put("isconfirm","�Ѹ���");
						 }else{
							 jsonObject.put("isconfirm","�뱸����");
						 }
					}
					
            	  //�г�/�Ᵽ [FREE - �Ᵽ,PAID - �г�],
            	  	HashMap hmap=new HashMap();
        			String sqlk="select md.ElevatorNo,mm.MainMode,mm.ContractEDate,mm.BillNo "
        					+ "from MaintContractDetail md ,MaintContractMaster mm "
        					+ "where mm.BillNo=md.BillNo and mm.contractStatus in('XB','ZB') "
        					+ "and md.ElevatorNo='"+a.getElevatorNo()+"'";
        			//System.out.println(">>>>"+sqlk);
        			List krelist=session.createSQLQuery(sqlk).list();
        			if(krelist!=null && krelist.size()>0){
        				Object[] obj=(Object[])krelist.get(0);
                	  	
                	  	String r2=(String)obj[1];
                	  	if(r2!=null && r2.equals("PAID")){
                	  		r2="�г�";
                	  	}else if(r2!=null && r2.equals("FREE")){
                	  		r2="�Ᵽ";
                	  	}
        				jsonObject.put("mainmode", r2);//�г�/�Ᵽ
        				jsonObject.put("contractedate", (String)obj[2]);//��ͬ��������
        			}else{
        				jsonObject.put("mainmode", "");//�г�/�Ᵽ
        				jsonObject.put("contractedate", "");//��ͬ��������
        			}
        			
        			jsonObject.put("invoiceimage", a.getInvoiceImage()==null?"":a.getInvoiceImage());//��Ʊ����ͼƬ
        			jsonObject.put("invoicetype", a.getInvoicetype()==null?"":a.getInvoicetype());//��Ʊ����
        			jsonObject.put("instock", a.getInstock()==null?"":a.getInstock());//�������Ƿ��п��
            	  	
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
	 * �������-����
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/pjsqadd")
	@Produces("application/json")
	public Response saveRequisition(@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		Loginuser user=null;
		Wbfileinfo wbinfo=null;

		System.out.println(">>>>>>>>�������--����");
		
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			String singleno=(String) data.get("singleno");//����/��������
			String elevatorno=(String) data.get("elevatorno");//���ݱ��
			String r4=(String) data.get("r4");//��������
			String r5=(String) data.get("r5");//�������Ƽ��ͺ�/��ע
			String ischarges=(String) data.get("ischarges");//�����ж��Ƿ��շ�
			String expressname=(String) data.get("expressname");//�׷���Ʊ����
			String yjaddress=(String) data.get("yjaddress");//�ʼĵ�ַ���绰

			String money2=(String) data.get("money2");//�շѽ��
			String invoicetype=(String) data.get("invoicetype");//��Ʊ����
			String instock=(String) data.get("instock");//�������Ƿ��п��

			String oldno=  CommonUtil.URLDecoder_decode((String) data.get("oldno"));//�ɼ����
			String operid=(String) data.get("operid");//������
			String appno=(String) data.get("appno");//������ˮ��
			

			List userList=session.createQuery("from Loginuser where userid ='"+operid+"'").list();
			if(userList!=null&&userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			
			AccessoriesRequisition a=null;
			String billno1 = "";
			if(!"".equals(appno)){
				a=(AccessoriesRequisition) session.get(AccessoriesRequisition.class, appno);
				billno1=appno;
				
				a.setPersonInCharge(null);//ά��������
				a.setIsAgree(null);//ά���Ƿ�ͬ��
				a.setPicauditRem(null);//ά��������������
				a.setPicauditDate(null);//ά���������������
				a.setWmuserId(null);//�ֹܴ�����
				a.setWarehouseManager(null);//�ֹ������
				a.setWmdate(null);//�ֹ��������
				a.setWmRem(null);//�ֹ�������
				a.setWmIsAgree(null);//�ֹ��Ƿ�ͬ��
				a.setWmIsCharges(null);//�ֹ��Ƿ��շ�
				a.setWmPayment(null);//�ֹ���ȡ��ʽ
			}else{
				a=new AccessoriesRequisition();
				String year1=CommonUtil.getToday().substring(2,4);
				billno1 = CommonUtil.getBillno(year1,"AccessoriesRequisition", 1)[0];	

				a.setAppNo(billno1);
			}

			//����״̬ ��1 ά����������ˣ�2 ��������Ա��ˣ�3 ά����ȷ�ϣ�4 �ɼ��˻أ�5 �ѹرգ�6 ��ֹ��
			a.setHandleStatus("1");
			a.setSingleNo(singleno);
			a.setElevatorNo(elevatorno);
			a.setIsCharges(ischarges);
			a.setOldNo(oldno);
            a.setOperId(operid);
			a.setOperDate(CommonUtil.getNowTime());
			a.setMaintDivision(user.getGrcid());//������˾
			a.setMaintStation(user.getStorageid());//����ά��վ
			a.setR5(r5);//�������Ƽ��ͺ�/��ע
			a.setR4(r4);//��������
			a.setOldImage("");
			a.setNewImage("");
			a.setExpressName(expressname);//�׷���Ʊ����
			a.setYjaddress(yjaddress);//�ʼĵ�ַ���绰
			
			a.setInstock(instock);
			a.setInvoicetype(invoicetype);
			a.setMoney2(money2);

			String folder = PropertiesUtil.getProperty("AccessoriesRequisitionReport.file.upload.folder");
			/**=======================����ɼ�ͼƬ=============================*/
			JSONArray oldimagearr= (JSONArray) data.get("oldimagearr");//�ɼ�ͼƬ
			if(oldimagearr!=null && oldimagearr.length()>0){
				String curdate=DateUtil.getDateTime("yyyyMMddHHmmss");
				String filepath="AccessoriesRequisition_OldImage/"+CommonUtil.getNowTime("yyyy-MM-dd")+"/";
				for(int i=0;i<oldimagearr.length();i++){
					JSONObject object=(JSONObject) oldimagearr.get(i);
					String imgpic=(String) object.get("imgpic");//��ˮ��
					
					String newfilename=operid+"_"+curdate+"_"+i+".jpg";
					//customerimage=customerimage.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
					String[] cimages=imgpic.split(",");
					BASE64Decoder base64=new BASE64Decoder();
					if(cimages!=null && cimages.length>1){
						byte[] image=base64.decodeBuffer(cimages[1]);
						//����ͼƬ
						File f=new File(folder+filepath);
						f.mkdirs();
						FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
						fos.write(image);
						fos.flush();
						fos.close();
						
						//����ͼƬ��Ϣ�����ݿ�
						wbinfo=new Wbfileinfo();
						wbinfo.setBusTable("AccessoriesRequisition_OldImage");
						wbinfo.setMaterSid(billno1);
						wbinfo.setOldFileName(newfilename);
						wbinfo.setNewFileName(newfilename);
						wbinfo.setFilePath(filepath);
						wbinfo.setFileFormat("image/pjpeg");
						wbinfo.setFileSize(0d);
						wbinfo.setUploadDate(CommonUtil.getNowTime());
						wbinfo.setUploader(operid);
						wbinfo.setRemarks("������룬�ϴ��ɼ�����");
						session.save(wbinfo);
					}
				}
			}
			/**=======================����ɼ�ͼƬ=============================*/
			
			/**=======================���濪ƱͼƬ=============================*/
			JSONArray invoiceimagearr= (JSONArray) data.get("invoiceimagearr");//��ƱͼƬ
			if(invoiceimagearr!=null && invoiceimagearr.length()>0){
				String curdate=DateUtil.getDateTime("yyyyMMddHHmmss");
				String filepath="AccessoriesRequisition_InvoiceImage/"+CommonUtil.getNowTime("yyyy-MM-dd")+"/";
				for(int i=0;i<invoiceimagearr.length();i++){
					JSONObject object=(JSONObject) invoiceimagearr.get(i);
					String imgpic=(String) object.get("imgpic");//��ˮ��
					
					String newfilename=operid+"_"+curdate+"_"+i+".jpg";
					//customerimage=customerimage.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
					String[] cimages=imgpic.split(",");
					BASE64Decoder base64=new BASE64Decoder();
					if(cimages!=null && cimages.length>1){
						byte[] image=base64.decodeBuffer(cimages[1]);
						//����ͼƬ
						File f=new File(folder+filepath);
						f.mkdirs();
						FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
						fos.write(image);
						fos.flush();
						fos.close();
						
						//����ͼƬ��Ϣ�����ݿ�
						wbinfo=new Wbfileinfo();
						wbinfo.setBusTable("AccessoriesRequisition_invoiceImage");
						wbinfo.setMaterSid(billno1);
						wbinfo.setOldFileName(newfilename);
						wbinfo.setNewFileName(newfilename);
						wbinfo.setFilePath(filepath);
						wbinfo.setFileFormat("image/pjpeg");
						wbinfo.setFileSize(0d);
						wbinfo.setUploadDate(CommonUtil.getNowTime());
						wbinfo.setUploader(operid);
						wbinfo.setRemarks("������룬�ϴ���Ʊ���鸽��");
						session.save(wbinfo);
					}
				}
			}
			/**=======================���濪Ʊ����ͼƬ=============================*/
			
			//�������
			String sqlc="select r7 from AccessoriesRequisition where SingleNo='"+singleno+"'";
			List relist=session.createSQLQuery(sqlc).list();
			if(relist!=null && relist.size()>0){
				a.setR7(relist.size()+1);
			}else{
				a.setR7(1);
			}

			session.save(a);
			tx.commit();
			
			/****************************���Ͷ��Ÿ�ά������ ��ʼ**********************************/
			//A49  ά��վ��,A03  ά������
			String sqlqu="select userid,phone from loginuser where '"+user.getStorageid()+"' like StorageID+'%' and RoleID in('A03')";
			String phone="";
			
			ResultSet rs=session.connection().prepareStatement(sqlqu).executeQuery();
			if(rs.next()){
				phone=rs.getString("phone");
			}
			
			if(phone!=null && !phone.equals("")){
				boolean issms=SmsService.compSendSMS(phone);
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
	 * �������-������
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/pjsqcladd")
	@Produces("application/json")
	public Response saveRequisitioncl(@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		Loginuser user=null;
		Wbfileinfo wbinfo=null;

		System.out.println(">>>>>>>>�������--������");
		
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			String hstatus=(String) data.get("hstatus");//z״̬
			String appno=(String) data.get("appno");//��ˮ��
			//String singleno=(String) data.get("singleno");//����/��������
			String personincharge=(String) data.get("personincharge");//ά��������
			String picauditrem=CommonUtil.URLDecoder_decode((String) data.get("picauditrem"));//ά��������������
			String isagree=(String) data.get("isagree");//�Ƿ�ͬ��
			//String ischarges=(String) data.get("ischarges");//ά���Ƿ��շ�
			String instock=(String) data.get("instock");//�������Ƿ��п��
			String isconfirm=(String) data.get("isconfirm");//ά����ȷ�ϸ���/�뱸����
			String newno2=(String) data.get("newno2");//�¼����
			
			//[1 ά����������ˣ�2 ��������Ա��ˣ�3 ά����ȷ�ϣ�4 �ɼ��˻أ�5 �ѹرգ�6 ��ֹ]
			AccessoriesRequisition a=(AccessoriesRequisition) session.get(AccessoriesRequisition.class, appno);
			if(hstatus!=null && hstatus.trim().equals("1")){
				//ά�����������
				a.setPersonInCharge(personincharge);
				a.setPicauditDate(CommonUtil.getNowTime());
				a.setPicauditRem(picauditrem);
				a.setIsAgree(isagree);
				//a.setIsCharges(ischarges);
				a.setInstock(instock);
				
				if(isagree!=null && isagree.trim().equals("N")){
					a.setHandleStatus("6");//��ͬ�����ֹ
				}else{
					a.setHandleStatus("2");//��������Ա���
					//������������,����ά��վ���Ƿ��շѻ�ȡ�������Ա��
					String sqlc="select a.OperId from WarehouseManager a,loginuser b where (a.MaintStation='"+a.getMaintStation()+"'"
							+ " or a.MaintStation in(select ParentStorageID from StorageID where StorageID='"+a.getMaintStation()+"')) "
							+ "and a.IsCharges='"+a.getIsCharges()+"' and a.OperId=b.UserID and b.EnabledFlag='Y'";
					ResultSet rs=session.connection().prepareStatement(sqlc).executeQuery();
					if(rs.next()){
						a.setWmuserId(rs.getString("OperId"));
					}
				}
			}else{
				a.setNewNo(newno2);//�¼����
				a.setIsConfirm(isconfirm);//ά����ȷ�ϸ���/�뱸����[Y: �Ѹ��� , N: �뱸����]
				//��ѣ����Ѹ����ĲŻᵽ�ɼ��˻ش���ȷ�ϡ�
				if(a.getIsCharges()!=null && a.getIsCharges().trim().equals("N") && isconfirm.trim().equals("Y")){
					a.setHandleStatus("4");//�ɼ��˻�					
				}else{
					a.setHandleStatus("5");//�ر�
				}
				
				//04003 �����ά��վ��04001 �����ά��վ�ģ�������뵥������� Ĭ��Ϊ�Ѿ�����,2017-08-28����
				String sqlkk="select StorageID from StorageID "
						+ "where (StorageID='"+a.getMaintStation()+"' or ParentStorageID ='"+a.getMaintStation()+"') "
						+ "and (StorageID in('04001','04003') or ParentStorageID in('04001','04003'))";
				List kklist=session.createSQLQuery(sqlkk).list();
				if(kklist!=null && kklist.size()>0){
					a.setCkiswc("Y");
					a.setCkrem("ϵͳ�Զ���ɳ��⡣");
					a.setCkoperid("admin");
					a.setCkdate(CommonUtil.getNowTime());
				}
				
				JSONArray newimagearr= (JSONArray) data.get("newimagearr");//�¼�ͼƬ
				/**=======================�����¼�ͼƬ=============================*/
				String folder = PropertiesUtil.getProperty("AccessoriesRequisitionReport.file.upload.folder");
				if(newimagearr!=null && newimagearr.length()>0){
					String curdate=DateUtil.getDateTime("yyyyMMddHHmmss");
					String filepath="AccessoriesRequisition_NewImage/"+CommonUtil.getNowTime("yyyy-MM-dd")+"/";
					for(int i=0;i<newimagearr.length();i++){
						JSONObject object=(JSONObject) newimagearr.get(i);
						String imgpic=(String) object.get("imgpic");//��ˮ��
						
						String newfilename=a.getAppNo()+"_"+curdate+"_"+i+".jpg";
						//customerimage=customerimage.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
						String[] cimages=imgpic.split(",");
						BASE64Decoder base64=new BASE64Decoder();
						if(cimages!=null && cimages.length>1){
							byte[] image=base64.decodeBuffer(cimages[1]);
							//����ͼƬ
							File f=new File(folder+filepath);
							f.mkdirs();
							FileOutputStream fos=new FileOutputStream(folder+filepath+newfilename);
							fos.write(image);
							fos.flush();
							fos.close();
							
							//����ͼƬ��Ϣ�����ݿ�
							wbinfo=new Wbfileinfo();
							wbinfo.setBusTable("AccessoriesRequisition_NewImage");
							wbinfo.setMaterSid(a.getAppNo());
							wbinfo.setOldFileName(newfilename);
							wbinfo.setNewFileName(newfilename);
							wbinfo.setFilePath(filepath);
							wbinfo.setFileFormat("image/pjpeg");
							wbinfo.setFileSize(0d);
							wbinfo.setUploadDate(CommonUtil.getNowTime());
							wbinfo.setUploader(a.getOperId());
							wbinfo.setRemarks("������룬�ϴ��¼�����");
							session.save(wbinfo);
						}
					}
				}
				/**=======================�����¼�ͼƬ=============================*/
			}
			session.update(a);
			
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
	 * ����/�������
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@GET
	@Path("/singleno/{data}")
	@Produces("application/json")
	public Response getgzlxList(
			@PathParam("data") JSONObject data) throws JSONException {
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		String singleno=CommonUtil.URLDecoder_decode((String) data.get("singleno"));
		String userid=CommonUtil.URLDecoder_decode((String) data.get("userid"));

		System.out.println(">>>>>>>>�������--����/�������");
		
		try{
			session = HibernateUtil.getSession();
			String sql="select a.singleno,a.ElevatorNo,a.rem from "
					+ "(select cm.CalloutMasterNo as singleno,cm.ElevatorNo,isnull(ecl2.rem,'') as rem "
					+ "from CalloutMaster cm left join ElevatorCoordinateLocation ecl2 on cm.ElevatorNo=ecl2.ElevatorNo,"
					+ "CalloutProcess cp "
					+ "where HandleStatus in ('2','3') "
					+ "and cp.CalloutMasterNo=cm.CalloutMasterNo and cp.AssignObject2 ='"+userid+"' "
					+ "union all "
					+ "select b.singleno,m.ElevatorNo,isnull(ecl.rem,'') as rem "
					+ "from MaintenanceWorkPlanDetail b,MaintenanceWorkPlanMaster a,"
					+ "MaintContractDetail m left join ElevatorCoordinateLocation ecl on m.ElevatorNo=ecl.ElevatorNo "
					+ "where a.billno=b.billno and a.rowid=m.rowid and b.HandleStatus='2' and b.MaintPersonnel='"+userid+"') a "
					+ "where 1=1 ";
			if(singleno!=null && !singleno.trim().equals("")){
				sql+=" and a.singleno like '%"+singleno.trim()+"%'";
			}
			//System.out.println(">>"+sql);
			List list =session.createSQLQuery(sql).list();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Object[] obj=(Object[])list.get(i);
					JSONObject jsonObject = new JSONObject(); 
					jsonObject.put("singleno", obj[0]);
					jsonObject.put("elevatorno", obj[1]);
					jsonObject.put("elenorem", obj[2]);
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
	/**
	 * ɾ���ļ�
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/deletefile")
	@Produces("application/json")
	public Response deleteFile(@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []

		System.out.println(">>>>>>>>�������--ɾ��ͼƬ");
		
		Wbfileinfo wbfile=null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String filesid =(String) data.get("filesid");
			String imagedt=(String) data.get("imagedt");//ɾ������  1=�ɼ�ͼƬ��4=��Ʊ��ϢͼƬ
			
			System.out.println(">>>>>>>>filesid="+filesid);

			//�����ļ�·�� ��ͬ�������ϸ�����
			String folder = PropertiesUtil.getProperty("AccessoriesRequisitionReport.file.upload.folder");

			if(filesid!=null && filesid.length()>0){
				wbfile=(Wbfileinfo) session.get(Wbfileinfo.class,Integer.parseInt(filesid));
				String filepath=folder+wbfile.getFilePath()+wbfile.getNewFileName();
				session.delete(wbfile);
				session.flush();
				
				HandleFile hf = new HandleFileImpA();
				hf.delFile(filepath);
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
}
