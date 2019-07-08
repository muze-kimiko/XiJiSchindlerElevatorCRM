package com.mobileserver;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
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
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferDebugFileinfo;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferFeedback;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferFeedbackFileinfo;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferFileTypes;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferFileinfo;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferMaster;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.workflow.bo.JbpmExtBridge;

/**
 * �ֻ�APP�˵��ã���ͬ���������ϴ���
 * @author Crunchify 
 */
@Path("/ContractTransfer")
public class ContractTransfer {

	/**
	 * ��ͬ���������ϴ�-�б�
	 * @param userid
	 * @param elevatorno
	 * @param processstatus
	 * @return
	 */
	@POST
	@Path("/list")
	@Produces("application/json")
	public Response getList(@FormParam("data") JSONObject data){
		Session session = null;
		Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-�б�");
		
		try{
			String userid=(String) data.get("userid");
			String elevatorno =(String) data.get("elevatorno");//���ݱ��
			String maintcontractno =(String) data.get("maintcontractno");//ά����ͬ��
			String salescontractno =(String) data.get("salescontractno");//���ۺ�ͬ��
			//String companyid =(String) data.get("companyid");//�׷���λ
			String transfesubmittype =(String) data.get("transfesubmittype");//�ϴ��ύ��־
			//int pageno= (Integer) data.get("pageno");  //��ǰ������
			
			session = HibernateUtil.getSession();
			
			List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
			if(userList!=null&&userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			String roleid=user.getRoleid();

			String sql ="select a.billno,a.companyid,d.companyName,a.maintcontractno,a.salescontractno,"
					+ "a.elevatorno,a.maintdivision,c.comname,a.maintstation,s.storagename,"
					+ "a.contractsdate,a.contractedate,a.operid,b.username,a.operdate,a.TransfeSubmitType,"
					+ "a.auditStatus,isnull(cb.OperDate,'') as cboperdate,"
					+ "isnull(a.AuditRem,'') as auditrem,isnull(a.AuditRem2,'') as auditrem2,"
					+ "isnull(a.isTrans,'N') as isTrans,isnull(a.AuditStatus2,'N') as AuditStatus2 "
					+ "from ContractTransferMaster a "
					+ "left join ContractTransferFeedback cb on a.BillNo=cb.BillNo "
					+ "and cb.OperDate=(select max(cfb.OperDate) from ContractTransferFeedback cfb where a.BillNo=cfb.BillNo),"
					+ "Loginuser b,Customer d,Company c,Storageid s "
					+ "where a.operId=b.userid and a.companyId=d.companyId "
					+ "and a.maintDivision=c.comid and a.maintStation=s.storageid and a.submitType='Y' ";
			
			if(roleid.trim().equals("A03")){
				//A03  ά������,δת�ɵġ�
				sql += "and isnull(a.isTrans,'N')='N' and a.maintStation='"+user.getStorageid()+"' ";
			}else{
				//A50  ά����  ��ά������ת�ɸ��Լ���
				sql += "and isnull(a.isTrans,'N')='Y' and isnull(a.wbgTransfeId,'')='"+userid+"' ";
			}
			
            if(!"".equals(elevatorno)){
            	sql +=" and a.elevatorNo like '%"+elevatorno.trim()+"%'";
            }
            if(!"".equals(maintcontractno)){
            	sql +=" and a.maintContractNo like '%"+maintcontractno.trim()+"%'";
            }
            if(!"".equals(salescontractno)){
            	sql +=" and a.salesContractNo like '%"+salescontractno.trim()+"%'";
            }
            if(!"".equals(transfesubmittype) && !"%".equals(transfesubmittype) && !transfesubmittype.trim().equals("S")){
            	sql+=" and isnull(a.transfeSubmitType,'N') like '"+transfesubmittype.trim()+"' "
            	   + " and isnull(a.auditStatus,'N')='N' ";
			}
			if(!"".equals(transfesubmittype) && !"%".equals(transfesubmittype) && transfesubmittype.trim().equals("S")){
				sql+=" and isnull(a.auditStatus,'N') like 'Y' ";
			}
			sql +=" order by a.billNo";
			
			//System.out.println(sql);
			
			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					Object[] objs =(Object[]) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
					
					jsonObject.put("billno",objs[0]);//��ˮ��
					jsonObject.put("companyid",objs[1]);//�׷���λ����
            	  	jsonObject.put("companyname", objs[2]);
					jsonObject.put("maintcontractno",objs[3]);//ά����ͬ��
					jsonObject.put("salescontractno",objs[4]);//���ۺ�ͬ��
					jsonObject.put("elevatorno",objs[5]);//���ݱ��
					jsonObject.put("maintdivision",objs[6]);//����ά���ֲ�
            		jsonObject.put("maintdivisionname",objs[7]);
					jsonObject.put("maintstation",objs[8]);//����ά��վ
            	  	jsonObject.put("maintstationname",objs[9]);
					jsonObject.put("contractsdate",objs[10]);//��ͬ��ʼ����
					jsonObject.put("contractedate",objs[11]);//��ͬ��������
					jsonObject.put("operid",objs[12]);//�ɹ���
            	  	jsonObject.put("username",objs[13]);
					jsonObject.put("operdate",objs[14]);//�ɹ�����

					String tstname="";
            	  	String tstid=objs[15].toString();
					String auditStatus=objs[16].toString();
					if("Y".equals(auditStatus)){
						tstid="S";
						tstname="�����";
					}else{
	            	  	if("Y".equals(tstid)){
	            	  		tstname="���ύ";
	            	  	}else if("R".equals(tstid)){
	            	  		tstname="����";
	            	  	}else{
	            	  		tstname="δ�ύ";
	            	  	}
					}
					jsonObject.put("transfesubmittype",tstid);//�ϴ��ύ��־
            	  	jsonObject.put("transfesubmittypename", tstname);
            	  	jsonObject.put("cboperdate", objs[17]);
            	  	jsonObject.put("auditrem", objs[18]);//��˲������
            	  	
            	  	String auditrem2=objs[19].toString();
            	  	String isTrans=objs[20].toString();
            	  	String AuditStatus2=objs[21].toString();
            	  	if("R".equals(tstid) && "Y".equals(isTrans) && "N".equals(AuditStatus2)){
            	  		//����ǲ��صģ�������ת�ɵģ������Ǿ�����Ա���ص�
            	  		jsonObject.put("auditrem", auditrem2);
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
	/**
	 * ��ͬ���������ϴ�-����ѡ���б�  batch
	 * @param userid
	 * @param elevatorno
	 * @param processstatus
	 * @return
	 */
	@POST
	@Path("/batchlist")
	@Produces("application/json")
	public Response getBatchList(@FormParam("data") JSONObject data){
		Session session = null;
		Connection conn=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		Loginuser user=null;
		
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-����ѡ���б�");
		
		try{
			String userid=(String) data.get("userid");
			String elevatorno =(String) data.get("elevatorno");//���ݱ�ţ�ά����ͬ�ţ����ۺ�ͬ��
			String maintcontractno =(String) data.get("maintcontractno");//ά����ͬ��
			String salescontractno =(String) data.get("salescontractno");//���ۺ�ͬ��
			String billnoarr =(String) data.get("billnoarr");//���ۺ�ͬ��
			String operation_batch =(String) data.get("operation_batch");//��������
			
			//System.out.println(operation_batch);
			
			//int pageno= (Integer) data.get("pageno");  //��ǰ������
			
			session = HibernateUtil.getSession();
			conn=session.connection();
			
			List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
			if(userList!=null&&userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			String roleid=user.getRoleid();

			//�ɹ����ύ�ģ��ϴ�δ�ύ�ģ�����û���ϴ������ġ����������� ������ƴ��һ�С�
			String sql ="select a.billno,d.companyname,a.maintcontractno,a.salescontractno,a.elevatorno,"
					
					+ "(select jnlno= stuff((select ',' +jnlno from ContractTransferFileTypes t,pulldown b "
					+ "where t.BillNo=at.BillNo and t.FileType = b.pullid and b.typeflag = 'ContractFileTypes_FileType' order by b.orderby for xml path('')),1,1,'') "
					+ "from ContractTransferFileTypes at where at.BillNo=a.billNo group by billNo) as jnlnostr,"
					
					+ "(select FileType= stuff((select ',' +FileType from ContractTransferFileTypes t,pulldown b "
					+ "where t.BillNo=at.BillNo and t.FileType = b.pullid and b.typeflag = 'ContractFileTypes_FileType' order by b.orderby for xml path('')),1,1,'') "
					+ "from ContractTransferFileTypes at where at.BillNo=a.billNo group by billNo) as filetypestr, "
					
					+ "(select FileTypeName= stuff((select ',' +pullname from ContractTransferFileTypes t,pulldown b "
					+ "where t.BillNo=at.BillNo and t.FileType = b.pullid and b.typeflag = 'ContractFileTypes_FileType' order by b.orderby for xml path('')),1,1,'') "
					+ "from ContractTransferFileTypes at where at.BillNo=a.billNo group by billNo) as filetypenamestr "
					
					+ "from ContractTransferMaster a,Loginuser b,Customer d,Company c,Storageid s "
					+ "where a.operId=b.userid and a.companyId=d.companyId "
					+ "and a.maintDivision=c.comid and a.maintStation=s.storageid "
					+ "and a.submitType='Y' and a.TransfeSubmitType='N' ";

			if(roleid.trim().equals("A03")){
				//A03  ά������,δת�ɵġ�
				sql += "and isnull(a.isTrans,'N')='N' and a.maintStation='"+user.getStorageid()+"' ";
			}else{
				//A50  ά����  ��ά������ת�ɸ��Լ���
				sql += "and isnull(a.isTrans,'N')='Y' and isnull(a.wbgTransfeId,'')='"+userid+"' ";
			}
					
			if(!"".equals(operation_batch) && "�����ϴ�".equals(operation_batch)){	
				//����Ƿ��ϴ��˸���
				sql += "and a.BillNo not in(select ct.BillNo from ContractTransferFileinfo cf,ContractTransferFileTypes ct where cf.jnlno=ct.jnlno and ct.BillNo=a.BillNo) ";
			}
			if(!"".equals(billnoarr)){
				billnoarr=billnoarr.replaceAll(",", "','");
            	sql +=" and a.billno not in('"+billnoarr+"')";
            }
			if(!"".equals(elevatorno)){
            	sql +=" and a.elevatorNo like '%"+elevatorno.trim()+"%'";
            }
            if(!"".equals(maintcontractno)){
            	sql +=" and a.maintContractNo like '%"+maintcontractno.trim()+"%'";
            }
            if(!"".equals(salescontractno)){
            	sql +=" and a.salesContractNo like '%"+salescontractno.trim()+"%'";
            }
			sql +=" order by a.billNo";
			
			//System.out.println(sql);
			
			ps=conn.prepareStatement(sql);
            rs=ps.executeQuery();
            
            int i=0;
			while(rs.next()){
				JSONObject jsonObject=new JSONObject();
				
				jsonObject.put("billno",rs.getString("billno"));//��ˮ��
				jsonObject.put("companyname",rs.getString("companyname"));//�׷���λ
        	  	jsonObject.put("maintcontractno", rs.getString("maintcontractno"));//ά����ͬ��
				jsonObject.put("salescontractno",rs.getString("salescontractno"));//���ۺ�ͬ��
				jsonObject.put("elevatorno",rs.getString("elevatorno"));//���ݱ��
				jsonObject.put("jnlnostr",rs.getString("jnlnostr"));//����������ˮ��
				jsonObject.put("filetypestr",rs.getString("filetypestr"));//��������
				jsonObject.put("filetypenamestr",rs.getString("filetypenamestr"));//��������
        	  	
        		jobiArray.put(i, jsonObject);
        		i++;
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
	 * ��ͬ���������ϴ�-�鿴
	 * @param userid
	 * @param billno
	 * @return
	 */
	@POST
	@Path("/display")
	@Produces("application/json")
	public Response getDisplay(@FormParam("data") JSONObject data){
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-����鿴�༭ҳ��");
		try{
			session = HibernateUtil.getSession();
			
			String userid=(String) data.get("userid");
			String billno =(String) data.get("billno");
			
			JSONObject object=new JSONObject();
			object.put("billno",billno);//��ˮ��
			//��ͬ�������ϸ������ͱ�
			String sql="select ct.jnlno,ct.billNo,ct.fileType,p.pullname,"
					+ "(select count(cf.jnlno) from ContractTransferFileinfo cf where cf.jnlno=ct.jnlno) as countnum "
					+ "from ContractTransferFileTypes ct,Pulldown p "
					+ "where ct.fileType=p.pullid and p.enabledflag='Y' "
					+ "and p.typeflag='ContractFileTypes_FileType' "
					+ "and ct.billNo='"+billno.trim()+"' "
					+ "order by p.orderby";
			//System.out.println(sql);
			JSONArray detaillist=new JSONArray();
			List qList=session.createSQLQuery(sql).list();
			if(qList!=null && qList.size()>0){
	            for(int i=0;i<qList.size();i++){
	            	
	            	Object[] ojbs=(Object[]) qList.get(i);
	            	
	        	  	JSONObject jsonObject=new JSONObject();
	        	  	String jnlno=ojbs[0].toString();
	        	  	jsonObject.put("jnlno", jnlno);
	        	  	jsonObject.put("billno", ojbs[1]);
	        	  	jsonObject.put("filetype", ojbs[2]);
	        	  	jsonObject.put("filetypename",ojbs[3]);
	        	  	
	        	  	//��ʾ�Ƿ����ϴ�����
	        	  	String counum=ojbs[4].toString();
	        	  	String subname="";
	        	  	if(!"0".equals(counum)){
	        	  		subname="���ϴ�����";
	        	  	}
	        	  	jsonObject.put("sub",subname);
	
	        	  	detaillist.put(i, jsonObject);
			    }
	            object.put("detaillist",detaillist);
			}
            //��ͬ�������Ϸ�����
            sql="select a.jnlno,a.BillNo,a.OperId,a.OperDate,a.TransferRem,"
            		+ "l.username,isnull(a.FeedbackTypeId,'')  as FeedbackTypeId,"
            		+ "isnull(c.FeedbackTypeName,'') as FeedbackTypeName "
					+ "from ContractTransferFeedback a "
					+ "left join ContractTransferFeedbackType c on a.FeedbackTypeId=c.FeedbackTypeId,"
					+ "Loginuser l "
					+ "where  a.operId=l.userid and a.billNo='"+billno.trim()+"' "
					+ "order by a.operDate desc";
			//System.out.println(sql);
            JSONArray detaillist2=new JSONArray();
			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null && selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					
					Object[] objs =(Object[]) selectList.get(i);
					
					JSONObject jsonObject=new JSONObject();
					
					jsonObject.put("jnlno",objs[0]);//��ˮ��
					jsonObject.put("billno",objs[1]);//��ˮ��
					jsonObject.put("operid",objs[2]);//������
					jsonObject.put("operdate",objs[3]);//��������
					jsonObject.put("transferrem",objs[4]);//��������
					jsonObject.put("opername",objs[5]);//������
					jsonObject.put("feedbacktypeid",objs[6]);//�������ʹ���
					jsonObject.put("feedbacktypename",objs[7]);//������������

					detaillist2.put(i, jsonObject);
				}
				object.put("detaillist2",detaillist2);
			}
			
			
			JSONArray debugfilelist=new JSONArray();
			//��ȡ���ص��Ե�·��
			String pathk="D:\\contract\\���ص��Ե�·��.txt";
			BufferedReader readerk= new BufferedReader(new FileReader(pathk));
			String downloadaddrk=readerk.readLine();
			readerk.close();
			
			//�ɹ��ϴ��ĵ��Ե�
			String hqlk="from ContractTransferDebugFileinfo where billNo='"+billno.trim()+"'";
			List fileList=session.createQuery(hqlk).list();
			//System.out.println(">>>"+hql);
			int f=0;
			if(fileList!=null && fileList.size()>0){
				ContractTransferDebugFileinfo ctfdf=null;
				for(int j=0;j<fileList.size();j++){
					ctfdf=(ContractTransferDebugFileinfo)fileList.get(j);

					JSONObject objf=new JSONObject();
					objf.put("oldfilename", ctfdf.getOldFileName());
					objf.put("downloadaddrk", downloadaddrk+ctfdf.getFileSid()+"&filetype=1");
					
					debugfilelist.put(f,objf);
					f++;
				}
			}
			//����ϵͳͬ���ĵ��Ե�
			String sqld="select a.FileSid,a.OldFileName from DebugSheetFileInfo a,ContractTransferMaster b "
					+ "where a.ElevatorNo=b.ElevatorNo and billNo='"+billno.trim()+"'";
			List fileList2=session.createSQLQuery(sqld).list();
			//System.out.println(">>>"+sql);
			if(fileList2!=null && fileList2.size()>0){
				for(int j=0;j<fileList2.size();j++){
					Object[] objs=(Object[])fileList2.get(j);

					JSONObject objf=new JSONObject();
					objf.put("oldfilename", objs[1]);
					objf.put("downloadaddrk", downloadaddrk+objs[0].toString()+"&filetype=2");
					
					debugfilelist.put(f,objf);
					f++;
				}
			}
			object.put("debugfilelist",debugfilelist);
			
			//��ȡ���س���֪ͨ��·��
			String path="D:\\contract\\���س���֪ͨ��·��.txt";
			BufferedReader reader= new BufferedReader(new FileReader(path));
			String downloadaddr=reader.readLine();
			reader.close();
			//��ѯ��װά�����ӵ���������Ƿ��иõ��ݡ�����״̬ ��2���ѵǼ����ύ��3������ˡ�
			String sqlsel="select a.billno,a.ProcessStatus "
					+ "from ElevatorTransferCaseRegister a,ContractTransferMaster b "
					+ "where a.ElevatorNo=b.ElevatorNo "
					+ "and b.billno='"+billno.trim()+"' "
					+ "and a.ProcessStatus in('2','3') "
					+ "order by a.CheckNum desc";
			List sellist=session.createSQLQuery(sqlsel).list();
			
			if(sellist!=null && sellist.size()>0){
				Object[] obj=(Object[])sellist.get(0);
				downloadaddr+=obj[0].toString();
			}else{
				downloadaddr="";
			}
			object.put("downloadaddr",downloadaddr);
			
			jobiArray.put(0, object);
            
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
	 * ��ͬ���������ϴ�-�������������� batch
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/savenew")
	@Produces("application/json")
	public Response saveNew (@FormParam("data")JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		ContractTransferMaster ctm =null;
		ContractTransferFileinfo fileInfo=null;
		ContractTransferFileTypes ctft=null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		JbpmExtBridge jbpmExtBridge=null;
	
		System.out.println(">>>>>>>>��ͬ���������ϴ�-��������Ϣ");
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			String billno=(String) data.get("billno");//��ˮ��
			String userid=(String) data.get("userid");//�ϴ���
			String transfesubmittype=(String) data.get("transfesubmittype");//�ύ��־
			
			String[] billnoarr=billno.split(",");//�������沵��
			String transfedate=CommonUtil.getNowTime();//�ϴ�ʱ��
			
			for(int b=0;b<billnoarr.length;b++){
				ctm =(ContractTransferMaster)session.get(ContractTransferMaster.class, billnoarr[b]);
				ctm.setTransfeId(userid);
				ctm.setTransfeDate(transfedate);
				ctm.setTransfeSubmitType(transfesubmittype);
				
				ctm.setAuditOperid2("");
				ctm.setAuditDate2("");
				ctm.setAuditStatus2("N");
				ctm.setAuditRem2("");
				
				session.save(ctm);
			}
			
			//����������ϸ ,�ж� JSONObject �Ƿ����  detaillist
			if(data.has("detaillist")){
				String folder="ContractTransferFileinfo.file.upload.folder";
				folder = PropertiesUtil.getProperty(folder).trim();
				
				JSONArray detaillist=(JSONArray) data.get("detaillist");//��������
				for(int i=0;i<detaillist.length();i++){
					JSONObject objk=(JSONObject) detaillist.get(i);
					
					String filetype=(String) objk.get("filetype");
					JSONArray imagelist=(JSONArray)objk.get("imglist");
					//��ͬ�������ϸ�����
					if(imagelist!=null && imagelist.length()>0){
						String curdate=DateUtil.getDateTime("yyyyMMddHHmmss");
						for(int j=0;j<imagelist.length();j++){
							JSONObject object=(JSONObject) imagelist.get(j);
							String imgpic=(String) object.get("ImgBase64");//��ˮ��
							String[] imgpicarr=imgpic.split(",");
							
							byte[] image=new BASE64Decoder().decodeBuffer(imgpicarr[1]);
							String newfilename=userid+"_"+curdate+"_"+i+"_"+j+".jpg";
							String filepath=CommonUtil.getNowTime("yyyy-MM-dd")+"/";
		
							//����ͼƬ
							File f=new File(folder+"/"+filepath);
							f.mkdirs();
							FileOutputStream fos=new FileOutputStream(folder+"/"+filepath+newfilename);
							fos.write(image);
							fos.flush();
							fos.close();
							
							for(int k=0;k<billnoarr.length;k++){
								String billnostr=billnoarr[k];
								
								String sqld="from ContractTransferFileTypes where billNo='"+billnostr+"' and fileType='"+filetype+"'";
								List relist=session.createQuery(sqld).list();
								ctft=(ContractTransferFileTypes)relist.get(0);

								System.out.println(">>>>>>>>billno="+billnostr+";filetype="+filetype+";jnlno="+ctft.getJnlno());
								
								//����ͼƬ��Ϣ�����ݿ�
								fileInfo=new ContractTransferFileinfo();
								fileInfo.setJnlno(ctft.getJnlno());
								fileInfo.setOldFileName(newfilename);
								fileInfo.setNewFileName(newfilename);
								fileInfo.setFileSize(Double.valueOf(0));
								fileInfo.setFilePath(filepath);
								fileInfo.setFileFormat("-");
								fileInfo.setUploadDate(transfedate);
								fileInfo.setUploader(userid);
								session.save(fileInfo);
								
							}
						}
					}
				}
				
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
	 * ����ͼƬ��Ϣ��ͬ�������ϸ�����,���������ϴ�ͼƬ batch
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/imagesave")
	@Produces("application/json")
	public Response saveImageInfo (@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
	
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []

		System.out.println(">>>>>>>>��ͬ���������ϴ�-�����ͬ�������ϸ�����ͼƬ");
		
		ContractTransferFileinfo fileInfo=null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String userid=(String) data.get("userid");
			String jnlno =(String) data.get("jnlno");
			JSONArray imagelist=(JSONArray) data.get("imagelist");//�ϴ�ͼƬ
			
			String[] jnlnoarr=jnlno.split(",");//�������沵��
			
			String folder="ContractTransferFileinfo.file.upload.folder";
			folder = PropertiesUtil.getProperty(folder).trim();
			String transfedate=CommonUtil.getNowTime();//�ϴ�ʱ��
			
			for(int b=0;b<jnlnoarr.length;b++){
				System.out.println(">>>>>>>>jnlno = "+jnlnoarr[b]);
				//��ͬ�������ϸ�����
				if(imagelist!=null && imagelist.length()>0){
					String curdate=DateUtil.getDateTime("yyyyMMddHHmmss");
					for(int j=0;j<imagelist.length();j++){
						JSONObject object=(JSONObject) imagelist.get(j);
						String imgpic=(String) object.get("imgpic");//��ˮ��
						
						byte[] image=new BASE64Decoder().decodeBuffer(imgpic);
						String newfilename=userid+"_"+curdate+"_"+b+"_"+j+".jpg";
						String filepath=CommonUtil.getNowTime("yyyy-MM-dd")+"/";
	
						//����ͼƬ
						File f=new File(folder+"/"+filepath);
						f.mkdirs();
						FileOutputStream fos=new FileOutputStream(folder+"/"+filepath+newfilename);
						fos.write(image);
						fos.flush();
						fos.close();
						
						//����ͼƬ��Ϣ�����ݿ�
						fileInfo=new ContractTransferFileinfo();
						fileInfo.setJnlno(jnlnoarr[b]);
						fileInfo.setOldFileName(newfilename);
						fileInfo.setNewFileName(newfilename);
						fileInfo.setFileSize(Double.valueOf(0));
						fileInfo.setFilePath(filepath);
						fileInfo.setFileFormat("-");
						fileInfo.setUploadDate(transfedate);
						fileInfo.setUploader(userid);
						session.save(fileInfo);
					}
				}
			}
			tx.commit();
			
			//���� ��װά�����ӵ�������Ǽ���Ŀ �� ��ˮ��
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
	 * ��ȡ��ͬ�������ϸ�����
	 * @param userid
	 * @param elevatortype
	 * @param issuecoding
	 * @return
	 */
	@POST
	@Path("/getimagelist")
	@Produces("application/json")
	public Response getImageList(@FormParam("data") JSONObject data){
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray imgarr=new JSONArray();//�������� []
		
		ContractTransferFileinfo cttfile=null;
		BASE64Encoder base=new BASE64Encoder();
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-��ȡ��ͬ�������ϸ�����ͼƬ");
		
		//�����ļ�·��
		String folder="ContractTransferFileinfo.file.upload.folder";
		folder = PropertiesUtil.getProperty(folder).trim();
		try{
			
			String userid=(String) data.get("userid");
			String jnlno =(String) data.get("jnlno");
			
			session = HibernateUtil.getSession();

    	  	String hql="from ContractTransferFileinfo where jnlno='"+jnlno.trim()+"'";
			List fileList=session.createQuery(hql).list();
			//System.out.println(">>>"+hql);
			
			if(fileList!=null&&fileList.size()>0){
				for(int j=0;j<fileList.size();j++){
					cttfile=(ContractTransferFileinfo)fileList.get(j);
					String filepath=folder+"/"+cttfile.getFilePath()+cttfile.getNewFileName();
					//��ͼƬת��Ϊ��������
					byte[] imgbyte=CommonUtil.imageToByte(filepath);
					
					JSONObject objf=new JSONObject();
					objf.put("filesid", cttfile.getFileSid());
					objf.put("oldfilename", cttfile.getOldFileName());
					objf.put("imgpic", base.encode(imgbyte));//��������������
					
					imgarr.put(j,objf);
				}
			}
			
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", imgarr);
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

		System.out.println(">>>>>>>>��ͬ���������ϴ�-ɾ����ͬ�������ϸ�����ͼƬ");
		
		ContractTransferFileinfo ctfile=null;
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String userid=(String) data.get("userid");
			String filesid =(String) data.get("filesid");

			//�����ļ�·�� ��ͬ�������ϸ�����
			String folder="ContractTransferFileinfo.file.upload.folder";
			folder = PropertiesUtil.getProperty(folder).trim();

			if(filesid!=null && filesid.length()>0){
				ctfile=(ContractTransferFileinfo) session.get(ContractTransferFileinfo.class,Integer.valueOf(filesid));
				String filepath=ctfile.getFilePath();
				String filename=ctfile.getNewFileName();
				session.delete(ctfile);
				session.flush();
				
				HandleFile hf = new HandleFileImpA();
				String localpath=folder+"/"+filepath+filename;
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
	
	/**
	 * ��ͬ���������ϴ�-��ȡ��ʷ����ͼƬ
	 * @param userid
	 * @param elevatortype
	 * @param issuecoding
	 * @return markingscore
	 */
	@POST
	@Path("/getfkimage")
	@Produces("application/json")
	public Response getFkImage(@FormParam("data") JSONObject data){
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray imgarr=new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-��ȡ��ʷ����ͼƬ");
		
		String folder="ContractTransferFeedbackFileinfo.file.upload.folder";
		folder = PropertiesUtil.getProperty(folder).trim();
		
		ContractTransferFeedbackFileinfo ctffile=null;
		BASE64Encoder base=new BASE64Encoder();
		try{
			session = HibernateUtil.getSession();
			
			String userid=(String) data.get("userid");
			String jnlno=(String) data.get("jnlno");//��ˮ��

			String hql="from ContractTransferFeedbackFileinfo h where h.jnlno ='"+jnlno+"' ";
			//System.out.println(hql);
			
			List fileList=session.createQuery(hql).list();

			if(fileList!=null&&fileList.size()>0){
				for(int j=0;j<fileList.size();j++){
					ctffile=(ContractTransferFeedbackFileinfo)fileList.get(j);
					String filepath=folder+"/"+ctffile.getFilePath()+ctffile.getNewFileName();
					//��ͼƬת��Ϊ��������
					byte[] imgbyte=CommonUtil.imageToByte(filepath);
					
					JSONObject objf=new JSONObject();
					objf.put("filesid", ctffile.getFileSid());
					objf.put("oldfilename", ctffile.getOldFileName());
					objf.put("imgpic", base.encode(imgbyte));//��������������
					
					imgarr.put(j,objf);
				}
			}
			
			json.put("code", "200");
  			json.put("info", "OK");
            rejson.put("status", json);
      		rejson.put("data", imgarr);
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
	 * ��ͬ���������ϴ�-���淴�����ȣ��������淴�� batch
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@POST
	@Path("/savefknew")
	@Produces("application/json")
	public Response saveFkNew (@FormParam("data")JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		ContractTransferFeedback ctf = null;
		ContractTransferFeedbackFileinfo fileInfo=null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-���淴������");
		
		try{
			session=HibernateUtil.getSession();
			tx=session.beginTransaction();
			
			String billno=(String) data.get("billno");//��ˮ��
			String userid=(String) data.get("userid");//������
			String feedbacktypeid=(String) data.get("feedbacktypeid");//������������
			String transferrem=(String) data.get("transferrem");//��������
			JSONArray imagelist=(JSONArray) data.get("imagelist");//ͼƬ����
			
			String[] billnoarr=billno.split(",");//�������淴��
			
			//����ͼƬ
			String folder="ContractTransferFeedbackFileinfo.file.upload.folder";
			folder = PropertiesUtil.getProperty(folder).trim();
			String opdate=CommonUtil.getNowTime();//����ʱ��
			List piclist=new ArrayList();
			//��ͬ�������Ϸ���������
			if(imagelist!=null && imagelist.length()>0){
				String curdate=DateUtil.getDateTime("yyyyMMddHHmmss");//��ǰ����ʱ��
				for(int j=0;j<imagelist.length();j++){
					JSONObject object=(JSONObject) imagelist.get(j);
					String imgpic=(String) object.get("imgpic");//��ˮ��
					
					byte[] image=new BASE64Decoder().decodeBuffer(imgpic);
					String newfilename=userid+"_"+curdate+"_"+j+".jpg";
					String filepath=CommonUtil.getNowTime("yyyy-MM-dd")+"/";

					//����ͼƬ
					File f=new File(folder+"/"+filepath);
					f.mkdirs();
					FileOutputStream fos=new FileOutputStream(folder+"/"+filepath+newfilename);
					fos.write(image);
					fos.flush();
					fos.close();
					
					HashMap hm=new HashMap();
					hm.put("newfilename", newfilename);
					hm.put("filepath", filepath);
					
					piclist.add(hm);
					
				}
			}
			
			//��������
			for(int b=0;b<billnoarr.length;b++){
				String jnlno = CommonUtil.getBillno(CommonUtil.getToday().substring(2,4),"ContractTransferFeedback", 1)[0];
				ctf =new ContractTransferFeedback();
				ctf.setBillNo(billnoarr[b]);
				ctf.setJnlno(jnlno);
				ctf.setOperId(userid);
				ctf.setOperDate(opdate);
				ctf.setFeedbacktypeid(feedbacktypeid);
				ctf.setTransferRem(transferrem);
				session.save(ctf);

				System.out.println(">>>>>>>>billno = "+billnoarr[b]+";jnlno="+jnlno);
				
				//��ͬ�������Ϸ���������
				if(piclist!=null && piclist.size()>0){
					for(int j=0;j<piclist.size();j++){
						HashMap hmk=(HashMap)piclist.get(j);
						String newfilename=(String)hmk.get("newfilename");
						String filepath=(String)hmk.get("filepath");
						//����ͼƬ��Ϣ�����ݿ�
						fileInfo=new ContractTransferFeedbackFileinfo();
						fileInfo.setJnlno(jnlno);
						fileInfo.setOldFileName(newfilename);
						fileInfo.setNewFileName(newfilename);
						fileInfo.setFileSize(Double.valueOf(0));
						fileInfo.setFilePath(filepath);
						fileInfo.setFileFormat("-");
						fileInfo.setUploadDate(opdate);
						fileInfo.setUploader(userid);
						session.save(fileInfo);
						
					}
				}
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
	 * ������ذ�ť���������� batch 
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/savebh")
	@Produces("application/json")
	public Response saveBh (@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		ContractTransferMaster ctm=null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-���ر���");
		
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String userid=(String) data.get("userid");
			String billno=(String) data.get("billno");
			String transferrem=(String) data.get("transferrem");//����ԭ��
			
			String[] billnoarr=billno.split(",");//�������沵��
			String transfedate=CommonUtil.getNowTime();//�ϴ�ʱ��
			
			//��������
			for(int b=0;b<billnoarr.length;b++){
				System.out.println(">>>>>>>>billno = "+billnoarr[b]);
				ctm=(ContractTransferMaster) session.get(ContractTransferMaster.class, billnoarr[b]);
				ctm.setSubmitType("R");
				ctm.setTransfeId(userid);
				ctm.setTransfeDate(transfedate);
				ctm.setTransferRem(transferrem);
				
				ctm.setIsTrans("N");//�Ƿ�ת��
				ctm.setIsTransDate("");
				ctm.setIsTransId("");
				ctm.setWbgTransfeId("");
				
	            session.update(ctm);
	            
	            //ɾ�� ��ͬ�������ϸ�����
	            String delsql="delete ContractTransferFileinfo h "
	            		+ "where h.jnlno in(select jnlno from ContractTransferFileTypes h where h.billNo='"+billnoarr[b]+"')";
	            session.createQuery(delsql).executeUpdate();
	            //ɾ�� ��ͬ�������Ϸ���������
	            String delsql2="delete ContractTransferFeedbackFileinfo h "
	            		+ "where h.jnlno in(select jnlno from ContractTransferFeedback h where h.billNo='"+billnoarr[b]+"')";
	            session.createQuery(delsql2).executeUpdate();
	            //ɾ�� ��ͬ�������Ϸ�����
	            String delsql3="delete ContractTransferFeedback h where h.billNo='"+billnoarr[b]+"'";
	            session.createQuery(delsql3).executeUpdate();
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
	
	/**
	 * ��ͬ���������ϴ�-��ȡ���Ե�
	 * @param userid
	 * @param billno
	 * @return
	 */
	@POST
	@Path("/debugfilelist")
	@Produces("application/json")
	public Response getDebugFileList(@FormParam("data") JSONObject data){
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-��ȡ���Ե�");
		try{
			session = HibernateUtil.getSession();
			
			String userid=(String) data.get("userid");
			String billno =(String) data.get("billno");

			//��ȡ���ص��Ե�·��
			String pathk="D:\\contract\\���ص��Ե�·��.txt";
			BufferedReader readerk= new BufferedReader(new FileReader(pathk));
			String downloadaddrk=readerk.readLine();
			readerk.close();
			
			//�ɹ��ϴ��ĵ��Ե�
			String hqlk="from ContractTransferDebugFileinfo where billNo='"+billno.trim()+"'";
			List fileList=session.createQuery(hqlk).list();
			//System.out.println(">>>"+hql);
			int f=0;
			if(fileList!=null && fileList.size()>0){
				ContractTransferDebugFileinfo ctfdf=null;
				for(int j=0;j<fileList.size();j++){
					ctfdf=(ContractTransferDebugFileinfo)fileList.get(j);

					JSONObject objf=new JSONObject();
					objf.put("oldfilename", ctfdf.getOldFileName());
					objf.put("downloadaddrk", downloadaddrk+ctfdf.getFileSid()+"&filetype=1");
					
					jobiArray.put(f,objf);
					f++;
				}
			}
			//����ϵͳͬ���ĵ��Ե�
			String sqld="select a.FileSid,a.OldFileName from DebugSheetFileInfo a,ContractTransferMaster b "
					+ "where a.ElevatorNo=b.ElevatorNo and billNo='"+billno.trim()+"'";
			List fileList2=session.createSQLQuery(sqld).list();
			//System.out.println(">>>"+sql);
			if(fileList2!=null && fileList2.size()>0){
				for(int j=0;j<fileList2.size();j++){
					Object[] objs=(Object[])fileList2.get(j);

					JSONObject objf=new JSONObject();
					objf.put("oldfilename", objs[1]);
					objf.put("downloadaddrk", downloadaddrk+objs[0].toString()+"&filetype=2");
					
					jobiArray.put(f,objf);
					f++;
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
	 * ��ͬ���������ϴ�-��ȡ��������
	 * @param userid
	 * @param billno
	 * @return
	 */
	@POST
	@Path("/feedbacktypelist")
	@Produces("application/json")
	public Response getFeedbackTypeList(@FormParam("data") JSONObject data){
		Session session = null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-��ȡ��������");
		try{
			session = HibernateUtil.getSession();
			
			String userid=(String) data.get("userid");
			String feedbacktypename =(String) data.get("feedbacktypename");
			if("".equals(feedbacktypename)){
				feedbacktypename="%";
			}
			
			//��ͬ�������Ϸ������ͱ�
            String sql="select FeedbackTypeId,FeedbackTypeName from ContractTransferFeedbackType "
            		+ "where FeedbackTypeName like '%"+feedbacktypename+"%' and EnabledFlag='Y'";
            //System.out.println(sql);
			List typeList=session.createSQLQuery(sql).list();
			if(typeList!=null && typeList.size()>0){
				for(int i=0;i<typeList.size();i++){
					
					Object[] objs =(Object[]) typeList.get(i);
					
					JSONObject jsonObject=new JSONObject();
					
					jsonObject.put("feedbacktypeid",objs[0]);//�������ʹ���
					jsonObject.put("feedbacktypename",objs[1]);//������������

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
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }

        }

		
		return Response.status(200).entity(rejson.toString()).build();
	}
	/**
	 * ���ת�ɰ�ť������ת�� batch 
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/savezp")
	@Produces("application/json")
	public Response saveZp(@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		ContractTransferMaster ctm=null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ�-ת�ɱ���");
		
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String userid=(String) data.get("userid");
			String billno=(String) data.get("billno");
			String wbgtransfeid=(String) data.get("wbgtransfeid");//ת�ɵ�ά����
			
			String[] billnoarr=billno.split(",");//��������
			String curdate=CommonUtil.getNowTime();//�ϴ�ʱ��
			
			//��������
			for(int b=0;b<billnoarr.length;b++){
				System.out.println(">>>>>>>>billno = "+billnoarr[b]);
				ctm=(ContractTransferMaster) session.get(ContractTransferMaster.class, billnoarr[b]);
				ctm.setWbgTransfeId(wbgtransfeid);
				ctm.setIsTrans("Y");
				ctm.setIsTransId(userid);
				ctm.setIsTransDate(curdate);
	            session.update(ctm);
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
	/**
	 * ��ͬ���������ϴ�-��ȡά����
	 * @param userid
	 * @param rowed
	 * @return
	 */
	@POST
	@Path("/getmaintpersonnel")
	@Produces("application/json")
	public Response getMaintPersonnel(@FormParam("data") JSONObject data){
		Session session = null;
		Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>��ͬ���������ϴ�-��ȡά����"); 
		
		try{
			String userid=(String) data.get("userid");
			
			session = HibernateUtil.getSession();
			
			List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
			if(userList!=null&&userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			
			//ά����A50,ά��վ��A49,ά������ A03��ά�޼���ԱA53 
			String sql ="select UserID,UserName,phone from LoginUser "
					+ "where StorageID like '"+user.getStorageid()+"%' and EnabledFlag='Y' "
					+ "and RoleID in('A50','A49','A53')";
			//System.out.println(">>>>"+sql); 
			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					Object[] objs =(Object[]) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
            	  	jsonObject.put("newzpuserid", objs[0]);
            	  	jsonObject.put("newzpusername", objs[1]);
            	  	jsonObject.put("newzpusertel",objs[2]);
            	  	
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
	 * ��ͬ���������ϴ�-����б�
	 * @param userid
	 * @param elevatorno
	 * @param processstatus
	 * @return
	 */
	@POST
	@Path("/auditlist")
	@Produces("application/json")
	public Response getAuditList(@FormParam("data") JSONObject data){
		Session session = null;
		Loginuser user=null;
		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ����-�б�");
		
		try{
			String userid=(String) data.get("userid");
			String elevatorno =(String) data.get("elevatorno");//���ݱ��
			String maintcontractno =(String) data.get("maintcontractno");//ά����ͬ��
			String salescontractno =(String) data.get("salescontractno");//���ۺ�ͬ��
			//String companyid =(String) data.get("companyid");//�׷���λ
			String transfesubmittype =(String) data.get("transfesubmittype");//�ϴ��ύ��־
			//int pageno= (Integer) data.get("pageno");  //��ǰ������
			
			session = HibernateUtil.getSession();
			
			List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
			if(userList!=null&&userList.size()>0){
				user=(Loginuser) userList.get(0);
			}
			String roleid=user.getRoleid();

			String sql ="select a.billno,a.companyid,d.companyName,a.maintcontractno,a.salescontractno,"
					+ "a.elevatorno,a.maintdivision,c.comname,a.maintstation,s.storagename,"
					+ "a.contractsdate,a.contractedate,a.operid,b.username,a.operdate,a.TransfeSubmitType,"
					+ "a.auditStatus,isnull(cb.OperDate,'') as cboperdate,"
					+ "isnull(a.AuditRem,'') as auditrem,isnull(a.AuditRem2,'') as auditrem2,"
					+ "isnull(a.isTrans,'N') as isTrans,isnull(a.AuditStatus2,'N') as AuditStatus2 "
					+ "from ContractTransferMaster a "
					+ "left join ContractTransferFeedback cb on a.BillNo=cb.BillNo "
					+ "and cb.OperDate=(select max(cfb.OperDate) from ContractTransferFeedback cfb where a.BillNo=cfb.BillNo),"
					+ "Loginuser b,Customer d,Company c,Storageid s "
					+ "where a.operId=b.userid and a.companyId=d.companyId "
					+ "and a.maintDivision=c.comid and a.maintStation=s.storageid and a.submitType='Y' "
					+ "and isnull(a.isTrans,'N')='Y' and isnull(a.transfeSubmitType,'N')='Y' "
					+ "and a.maintStation='"+user.getStorageid()+"' ";
			
            if(!"".equals(elevatorno)){
            	sql +=" and a.elevatorNo like '%"+elevatorno.trim()+"%'";
            }
            if(!"".equals(maintcontractno)){
            	sql +=" and a.maintContractNo like '%"+maintcontractno.trim()+"%'";
            }
            if(!"".equals(salescontractno)){
            	sql +=" and a.salesContractNo like '%"+salescontractno.trim()+"%'";
            }
            if(!"".equals(transfesubmittype)){
            	sql +=" and isnull(a.AuditStatus2,'N') like '"+transfesubmittype.trim()+"'";
            }

			sql +=" order by a.billNo";
			
			//System.out.println(sql);
			
			List selectList=session.createSQLQuery(sql).list();
			if(selectList!=null&&selectList.size()>0){
				for(int i=0;i<selectList.size();i++){
					Object[] objs =(Object[]) selectList.get(i);
					JSONObject jsonObject=new JSONObject();
					
					jsonObject.put("billno",objs[0]);//��ˮ��
					jsonObject.put("companyid",objs[1]);//�׷���λ����
            	  	jsonObject.put("companyname", objs[2]);
					jsonObject.put("maintcontractno",objs[3]);//ά����ͬ��
					jsonObject.put("salescontractno",objs[4]);//���ۺ�ͬ��
					jsonObject.put("elevatorno",objs[5]);//���ݱ��
					jsonObject.put("maintdivision",objs[6]);//����ά���ֲ�
            		jsonObject.put("maintdivisionname",objs[7]);
					jsonObject.put("maintstation",objs[8]);//����ά��վ
            	  	jsonObject.put("maintstationname",objs[9]);
					jsonObject.put("contractsdate",objs[10]);//��ͬ��ʼ����
					jsonObject.put("contractedate",objs[11]);//��ͬ��������
					jsonObject.put("operid",objs[12]);//�ɹ���
            	  	jsonObject.put("username",objs[13]);
					jsonObject.put("operdate",objs[14]);//�ɹ�����

					String tstname="";
            	  	String tstid=objs[15].toString();
            	  	
					jsonObject.put("transfesubmittype",tstid);//�ϴ��ύ��־
            	  	jsonObject.put("transfesubmittypename", tstname);
            	  	jsonObject.put("cboperdate", objs[17]);
            	  	//jsonObject.put("auditrem", objs[18]);//��˲������

            	  	jsonObject.put("auditrem", objs[19]);//��˲������
            	  	String auditstatus2name="";
            	  	String AuditStatus2=objs[21].toString();
            	  	if("Y".equals(AuditStatus2)){
            	  		auditstatus2name="�����";
            	  	}else{
            	  		auditstatus2name="δ���";
            	  	}
            	  	jsonObject.put("auditstatus2", AuditStatus2);
            	  	jsonObject.put("auditstatus2name", auditstatus2name);

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
	 * ���ת�ɰ�ť������ת�� batch 
	 * @param data
	 * @return
	 * @throws JSONException 
	 */
	@POST
	@Path("/saveaudit")
	@Produces("application/json")
	public Response saveAudit(@FormParam("data") JSONObject data) throws JSONException{
		Session session = null;
		Transaction tx = null;
		ContractTransferMaster ctm=null;

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []
		
		System.out.println(">>>>>>>>��ͬ���������ϴ����-����");
		
		try{
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			
			String userid=(String) data.get("userid");
			String billno=(String) data.get("billno");
			String auditstatus2=(String) data.get("auditstatus2");//���״̬
			String auditrem2=(String) data.get("auditrem2");//������
			
			String curdate=CommonUtil.getNowTime();//�ϴ�ʱ��

			ctm=(ContractTransferMaster) session.get(ContractTransferMaster.class, billno);
			ctm.setAuditOperid2(userid);
			ctm.setAuditDate2(curdate);
			ctm.setAuditRem2(auditrem2);
			
			if("R".equals(auditstatus2)){
				ctm.setTransfeSubmitType("R");
				ctm.setAuditStatus2("N");
			}else{
				ctm.setAuditStatus2(auditstatus2);
			}
            session.update(ctm);

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
