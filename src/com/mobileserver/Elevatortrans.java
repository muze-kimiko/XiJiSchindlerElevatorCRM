package com.mobileserver;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.ResultSet;
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
import com.gzunicorn.hibernate.basedata.handoverelevatorcheckitem.HandoverElevatorCheckItem;
import com.gzunicorn.hibernate.infomanager.elevatortransfercasebhtype.ElevatorTransferCaseBhType;
import com.gzunicorn.hibernate.infomanager.elevatortransfercaseprocess.ElevatorTransferCaseProcess;
import com.gzunicorn.hibernate.infomanager.elevatortransfercaseregister.ElevatorTransferCaseRegister;
import com.gzunicorn.hibernate.infomanager.handoverelevatorcheckfileinfo.HandoverElevatorCheckFileinfo;
import com.gzunicorn.hibernate.infomanager.handoverelevatorcheckitemregister.HandoverElevatorCheckItemRegister;
import com.gzunicorn.hibernate.infomanager.handoverelevatorspecialregister.HandoverElevatorSpecialRegister;
import com.gzunicorn.hibernate.sysmanager.Loginuser;
import com.gzunicorn.hibernate.sysmanager.pulldown.Pulldown;
import com.gzunicorn.workflow.bo.JbpmExtBridge;

/**
 * �ֻ�APP�˵��ã���װά�����ӵ��ݵǼ���
 * @author Elevatortrans
 */
@Path("/Elevatortrans")
public class Elevatortrans {
	
	BaseDataImpl bd = new BaseDataImpl();
	
	/**
     * ��װά�����ӵ��ݵǼ�-�б�=����״̬
     * @param userid
     * @param elevatorno
     * @return
     */
	@GET
	@Path("/proStatuslist/{userid}")
	@Produces("application/json")
	public Response getProStatuslist(
			@PathParam("userid") String userid
			){

		JSONObject rejson = new JSONObject();
		JSONObject json = new JSONObject();//������� {}
		JSONArray jobiArray = new JSONArray();//�������� []

		/** 0��δ����,4���ѽ���,1���ѵǼ�δ�ύ,2���ѵǼ����ύ,3�������  */
		
		String[] proarr={"0","4","1","2","3"};
		String[] proarrname={"δ����","�ѽ���","�ѵǼ�δ�ύ","�ѵǼ����ύ","�����"};
		try{
			JSONObject object=null;
			for(int i=0;i<proarr.length;i++){
				object=new JSONObject();
				object.put("statusid", proarr[i]);//����״̬
				object.put("statusname", proarrname[i]);//����״̬
				jobiArray.put(i, object);
			}
				
			json.put("code", "200");
			json.put("info", "OK");
	        rejson.put("status", json);
	  		rejson.put("data", jobiArray);
		}catch(Exception ex){
			ex.printStackTrace();
		}
		
		return Response.status(200).entity(rejson.toString()).build();
	}
	
	        /**
	         * ��װά�����ӵ��ݵǼ�-�б�
	         * @param userid
	         * @param elevatorno
	         * @return
	         */
			@POST
			@Path("/azwblist")
			@Produces("application/json")
			public Response getAzwbList(@FormParam("data") JSONObject data){
				Session session = null;

				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{

					String userid=(String) data.get("userid");
					String elevatorno =(String) data.get("elevatorno");
					String processstatus =(String) data.get("processstatus");
					
					session = HibernateUtil.getSession();

					String sql="from ElevatorTransferCaseRegister e where e.submitType in('Y','Z') " +
							"and e.staffName='"+userid+"'";
					if(elevatorno!=null&&!elevatorno.trim().equals("")){
						sql+=" and e.elevatorNo like '%"+elevatorno.trim()+"%' ";
					}
					if(processstatus!=null && !processstatus.trim().equals("") && !processstatus.trim().equals("5")){
						sql+=" and e.processStatus like '"+processstatus.trim()+"' ";
					}
					if(processstatus!=null && !processstatus.trim().equals("") && processstatus.trim().equals("5")){
						sql+=" and e.status=0 ";
					}
					
					sql+=" order by e.billno desc";
					
					//System.out.println("��װά�����ӵ��ݵǼ�-�б�="+sql);
					
					//��ȡ���س���֪ͨ��·��
					String path="D:\\contract\\���س���֪ͨ��·��.txt";
					BufferedReader reader= new BufferedReader(new FileReader(path));
					String downloadaddr=reader.readLine();
					reader.close();
					//String downloadaddr="http://10.10.0.5:8080/XJSCRM/PrintDisposeServlet?id=";//����
					//String downloadaddr="http://www.xjelevator.com:9000/XJSCRM/PrintDisposeServlet?id=";//��ʽ
					
					List eList=session.createQuery(sql).list();
					if(eList!=null&&eList.size()>0){
						for(int i=0;i<eList.size();i++){
							ElevatorTransferCaseRegister e=(ElevatorTransferCaseRegister) eList.get(i);
							JSONObject object=new JSONObject();
							object.put("billno", e.getBillno());//��ˮ��
							object.put("checknum", e.getCheckNum());//�������
							object.put("elevatorno", e.getElevatorNo());//���ݱ��
							object.put("salescontractno", e.getSalesContractNo());//���ۺ�ͬ��
							object.put("projectname", e.getProjectName());//��Ŀ����
							object.put("projectaddress", e.getProjectAddress());//��Ŀ��ַ
							
							int status=e.getStatus();//����״̬
							String statusid=e.getProcessStatus();
							String statusname="";
							if(statusid.equals("0")){
								statusname="δ����";
							}else if(statusid.equals("4")){
								statusname="�ѽ���";
							}else if(statusid.equals("1")){
								statusname="�ѵǼ�δ�ύ";
							}else if(statusid.equals("2")){
								statusname="�ѵǼ����ύ";
							}else if(statusid.equals("3")){
								statusname="�����";
							}
							
							if(status==0){
								statusid="5";
								statusname="�ҵĴ���";
							}
							
							object.put("processstatus", statusid);//����״̬
							object.put("processstatusname", statusname);//����״̬
							
							
							if(downloadaddr!=null && !downloadaddr.trim().equals("")){
								object.put("downloadaddr", downloadaddr.trim()+e.getBillno());//����֪ͨ�����ص�ַ
							}else{
								object.put("downloadaddr", "");//����֪ͨ�����ص�ַ
							}
							
							jobiArray.put(i, object);
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
			 * ��װά�����ӵ��ݵǼ�-�鿴
			 * @param userid
			 * @param billno
			 * @return
			 */
			@GET
			@Path("/azwbdisplay/{userid}/{billno}")
			@Produces("application/json")
			public Response getAzwbDisplay(@PathParam("userid") String userid, @PathParam("billno") String billno){
				Session session = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();

					String sql="from ElevatorTransferCaseRegister e where e.billno='"+billno.trim()+"'";
					List eList=session.createQuery(sql).list();
					if(eList!=null&&eList.size()>0){
						
							ElevatorTransferCaseRegister e=(ElevatorTransferCaseRegister) eList.get(0);
							JSONObject object=new JSONObject();
							object.put("billno", e.getBillno());//��ˮ��
							object.put("checknum", e.getCheckNum());//�������
							object.put("elevatorno", e.getElevatorNo());//���ݱ��
							object.put("salescontractno", e.getSalesContractNo());//���ۺ�ͬ��
							object.put("projectname", e.getProjectName());//��Ŀ����
							object.put("projectaddress", e.getProjectAddress());//��Ŀ��ַ
							object.put("processstatus", e.getProcessStatus());//����״̬
							object.put("checktime", e.getCheckTime());//����ʱ��
							
							if(e.getCheckDate()==null){
								object.put("checkdate2", "");//ʵ�ʳ�������(��������)
							}else{
								object.put("checkdate2", e.getCheckDate());//ʵ�ʳ�������(��������)
							}
							if(e.getCheckTime2()==null){
								object.put("checktime22", "");//ʵ�ʳ���ʱ��(��������)
							}else{
								object.put("checktime22", e.getCheckTime2());//ʵ�ʳ���ʱ��(��������)
							}
							
							object.put("elevatorparam", e.getElevatorParam());//����ͺ�
							object.put("salescontracttype", e.getSalesContractType());//��ͬ����
							object.put("floor", e.getFloor());//��
							object.put("stage", e.getStage());//վ
							object.put("door", e.getDoor());//��
							object.put("high", e.getHigh());//�����߶�
							object.put("weight", e.getWeight());//����
							object.put("speed", e.getSpeed());//�ٶ�
							object.put("isxjs", e.getIsxjs());//�Ƿ�Ѹ�ﰲװ
							object.put("inscompanyname", e.getInsCompanyName());//��װ��˾����
							object.put("inslinkphone", e.getInsLinkPhone());//��װ��˾��ϵ�绰
							object.put("department",  bd.getName(session, "Company", "comname", "comid", e.getDepartment()));//��������
							object.put("staffname", bd.getName(session, "Loginuser", "username", "userid", e.getStaffName()));//������Ա����

							String ctype =e.getContractType();
							String ctypename="";
							if(ctype.equals("XS")){
								ctypename="���ۺ�ͬ";
							}else if(ctype.equals("WG")){
								ctypename="ά�ĺ�ͬ";
							}
							object.put("contracttype", ctypename);//��ͬ����
							
							String etype=e.getElevatorType();
							String etypename="";
							if(etype.equals("T")){
								etypename="ֱ��";
							}else if(etype.equals("F")){
								etypename="����";
							}
							object.put("elevatortype",etype);//��������
							object.put("elevatortypename", etypename);//������������
							//����
							object.put("projectprovince", e.getProjectProvince());//��Ŀʡ��
							object.put("projectmanager", e.getProjectManager());//��Ŀ�����������绰
							object.put("debugpers", e.getDebugPers());//������Ա�������绰
							object.put("elevatoraddress", e.getElevatorAddress());//����λ��
							object.put("receivedate", e.getReceiveDate());//��������
							
							object.put("stafflinkphone", e.getStaffLinkPhone());//������Ա��ϵ�绰
							object.put("factorycheckresult", e.getFactoryCheckResult());//������
							object.put("factorycheckresult2", e.getFactoryCheckResult2());//������2
							
							String firstInstallation=e.getFirstInstallation();
							if("Y".equals(firstInstallation)){
								firstInstallation="��";
							}else if("N".equals(firstInstallation)){
								firstInstallation="��";
							}else{
								firstInstallation="";
							}
							object.put("firstinstallation", firstInstallation);//�Ƿ���ΰ�װ
							
							if(e.getR2()==null){
								object.put("r2", "");//�������
							}else{
								object.put("r2", e.getR2());//�������
							}
							
							/**=====================��ȡͼƬ=========================*/
		     				String folder = PropertiesUtil.getProperty("ElevatorTransferCaseRegister.file.upload.folder");
		     				BASE64Encoder base=new BASE64Encoder();
		     				//�ͻ�ǩ��
		     				if(e.getCustomerSignature()!=null && !e.getCustomerSignature().trim().equals("")){
		     					String filepath=folder+e.getCustomerSignature();
		     					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������
		     					object.put("customersignature", base.encode(imgbyte));//��������������
		     				}else{
		     					object.put("customersignature", "");
		     				}
		     				//�ͻ���Ƭ
		     				if(e.getCustomerImage()!=null && !e.getCustomerImage().trim().equals("")){
		     					String filepath=folder+e.getCustomerImage();
		     					byte[] imgbyte=CommonUtil.imageToByte(filepath);//��ͼƬת��Ϊ��������		
		     					object.put("customerimage", base.encode(imgbyte));//��������������
		     				}else{
		     					object.put("customerimage", "");
		     				}
		     				/**=====================��ȡͼƬ=========================*/
								                    
							
							//����Ǽ���,���첿��ɾ���Ĳ���ʾ��
						    JSONArray detaillist=new JSONArray();
						    String sql1="select h,p.pullname,isnull(hi.itemgroup,'') "
						    		+ "from HandoverElevatorCheckItemRegister h,Pulldown p,HandoverElevatorCheckItem hi "
						    		+"where  p.id.pullid=h.examType "
						    		+" and h.elevatorTransferCaseRegister.billno='"+billno+"'"
 									+" and hi.id.examType=h.examType and hi.id.checkItem=h.checkItem " 
 									+" and hi.id.issueCoding=h.issueCoding "
                             		//+ " and h.isDelete='N'"
                             		+ " and p.id.typeflag='HandoverElevatorCheckItem_ExamType'" 
                             		+ " order by h.isRecheck desc,p.orderby,h.issueCoding  ";
						    
						    //System.out.println(">>>"+sql1);
						    
							List hList=session.createQuery(sql1).list();
							
							HandoverElevatorCheckItemRegister hec=null;

							if(hList!=null&&hList.size()>0){
								for(int i=0;i<hList.size();i++){
									Object[] objects=(Object[]) hList.get(i);
									
									hec=(HandoverElevatorCheckItemRegister) objects[0];
									JSONObject object2=new JSONObject();
									object2.put("jnlno", hec.getJnlno());//��ˮ��
									object2.put("examtype", hec.getExamType());//������ʹ���
									object2.put("examtypename", (String)objects[1]);//�����������
									object2.put("checkitem", hec.getCheckItem());//�����Ŀ
									object2.put("issuecoding", hec.getIssueCoding());//�������
									object2.put("issuecontents", hec.getIssueContents());//��������
									object2.put("itemgroup", (String)objects[2]);//С����
									object2.put("isrecheck", hec.getIsRecheck());//�Ƿ񸴼�
									object2.put("rem", hec.getRem());//��ע
									
									String isyzg=hec.getIsyzg();
									if("Y".equals(isyzg)){
										isyzg="������";
									}else{
										isyzg="";
									}
									object2.put("isyzg", isyzg);//�Ƿ�������
									
									detaillist.put(i, object2);
								}
							}
							object.put("detaillist",detaillist);
							
							//����Ҫ��
							JSONArray detaillist2=new JSONArray();
						    String sql2="from HandoverElevatorSpecialRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"'";
							List hsList=session.createQuery(sql2).list();
							
							HandoverElevatorSpecialRegister hes=null;
							if(hsList!=null&&hsList.size()>0){
								for(int i=0;i<hsList.size();i++){
									hes=(HandoverElevatorSpecialRegister) hsList.get(i);
									JSONObject object2=new JSONObject();
									object2.put("jnlno2", hes.getJnlno());//��ˮ��
									object2.put("scid", hes.getScId());//��ˮ��
									object2.put("scname", bd.getName(session, "HandoverElevatorSpecialClaim", "scName", "scId", hes.getScId()));//��ˮ��
									object2.put("isok", hes.getIsOk());//��ˮ��
									//object2.put("appendix", h.get);//����(���ͼƬ)
									detaillist2.put(i, object2);
								}
								
							}
							object.put("detaillist2",detaillist2);
							jobiArray.put(0, object);
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
			 * ��ȡ���ӵ��ݼ����Ŀ
			 * @param userid
			 * @param elevatortype
			 * @param issuecoding
			 * @return
			 */
			@POST
			@Path("/azwbcheckitem")
			@Produces("application/json")
			public Response getCheckItemList(@FormParam("data") JSONObject data){
				Session session = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					String userid=(String) data.get("userid");
					String elevatortype =(String) data.get("elevatortype");
					String issuecoding =(String) data.get("issuecoding");
					String issuecodingarr =(String) data.get("issuecodingarr");
					
					session = HibernateUtil.getSession();
				    String sql="select h,p.pullname from HandoverElevatorCheckItem h,Pulldown p " +
				    		"where p.id.pullid=h.id.examType and h.enabledFlag='Y' " +
				    		" and p.id.typeflag='HandoverElevatorCheckItem_ExamType' " +
							" and h.elevatorType ='"+elevatortype.trim()+"'";
				    
				    if(issuecodingarr!=null && !issuecodingarr.trim().equals("NULL") && !issuecodingarr.trim().equals("")){
						issuecodingarr=issuecodingarr.trim().replaceAll(",", "','");
						sql+=" and h.id.issueCoding not in('"+issuecodingarr+"') ";
					}
					if(issuecoding!=null && !issuecoding.trim().equals("NULL") && !issuecoding.trim().equals("")){
						sql+=" and h.id.issueCoding like '%"+issuecoding.trim()+"%'";
					}
				    
				    sql+=" order by h.orderby";
				    List hList=session.createQuery(sql).list();
					if(hList!=null&&hList.size()>0){
						for(int i=0;i<hList.size();i++){
							Object[] objects=(Object[]) hList.get(i);
							HandoverElevatorCheckItem h=(HandoverElevatorCheckItem) objects[0];
							JSONObject object=new JSONObject();
							object.put("examtype", h.getId().getExamType());//������ʹ���
							object.put("examtypename", objects[1]);//�����������
							object.put("checkitem", h.getId().getCheckItem());//�����Ŀ
							object.put("issuecoding", h.getId().getIssueCoding());//�������
							object.put("issuecontents", h.getIssueContents());//��������
							if(h.getItemgroup()==null){
								object.put("itemgroup", "");//С����
							}else{
								object.put("itemgroup", h.getItemgroup());//С����
							}
							object.put("isrecheck", "N");//�Ƿ񸴼�
							object.put("isyzg", "");//�Ƿ�������
							
							jobiArray.put(i, object);
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
			 * ��ȡ���ӵ��ݼ����Ŀ
			 * @param userid
			 * @param elevatortype
			 * @param issuecoding
			 * @return
			 */
			@GET
			@Path("/azwbbhtype/{userid}")
			@Produces("application/json")
			public Response getBhTypeList(
			@PathParam("userid") String userid
			){
				Session session = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();
					String sql="select p from Pulldown p where p.id.typeflag='ElevatorTransferCaseRegister_BhType' " +
							"and p.enabledflag='Y' order by p.orderby";

				    List hList=session.createQuery(sql).list();
					if(hList!=null&&hList.size()>0){
						for(int i=0;i<hList.size();i++){
							Pulldown h=(Pulldown) hList.get(i);
							JSONObject object=new JSONObject();
							object.put("bhtype", h.getId().getPullid());//���ط������
							object.put("bhtypename", h.getPullname());//���ط�������
							jobiArray.put(i, object);
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
			 * ������հ�ť
			 * @param data
			 * @return
			 * @throws JSONException 
			 */
			@GET
			@Path("/azwbjssave/{data}")
			@Produces("application/json")
			public Response saveJSAzwb (@PathParam("data") JSONObject data) throws JSONException{
				Session session = null;
				Transaction tx = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();
					String userid=(String) data.get("userid");
					String billno=(String) data.get("billno");
					String receivedate=(String) data.get("receivedate");
					tx = session.beginTransaction();
					ElevatorTransferCaseRegister e=(ElevatorTransferCaseRegister) session.get(ElevatorTransferCaseRegister.class, billno);
					e.setReceiveDate(receivedate);
					e.setProcessStatus("4");//�ѽ���
					
					session.update(e);
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
			 * ������ذ�ť
			 * @param data
			 * @return
			 * @throws JSONException 
			 */
			@POST
			@Path("/azwbbhsave")
			@Produces("application/json")
			public Response saveBHAzwb (@FormParam("data") JSONObject data) throws JSONException{
				Session session = null;
				Transaction tx = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();
					tx = session.beginTransaction();
					String userid=(String) data.get("userid");
					String billno=(String) data.get("billno");
					String bhrem=(String) data.get("bhrem");//����ԭ��
					String bhdate=(String) data.get("bhdate");//��������
					String bhtype=(String) data.get("bhtype");//���ط������
					String bhtypename=(String) data.get("bhtypename");//���ط�������
					
					System.out.println("����==>>���ر���");

					//List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
					//if(userList!=null&&userList.size()>0){
					//	user=(Loginuser) userList.get(0);
					//}
					
					ElevatorTransferCaseRegister e=(ElevatorTransferCaseRegister) session.get(ElevatorTransferCaseRegister.class, billno);
					e.setBhDate(bhdate);
					e.setBhRem(bhrem);
					e.setBhType(bhtype);
					e.setSubmitType("R");
    				e.setProcessStatus("0");
    				e.setReceiveDate("");
                    session.update(e);
                    
                    //��װά�����ӵ���������ؼ�¼��
                    ElevatorTransferCaseBhType etfcbh=new ElevatorTransferCaseBhType();
    				etfcbh.setBillno(billno);
    				etfcbh.setBhDate(bhdate);
    				etfcbh.setBhRem(bhrem);
					etfcbh.setBhType(bhtype);
					session.save(etfcbh);
                    
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
			 * ���ת�ɰ�ť
			 * @param data
			 * @return
			 * @throws JSONException 
			 */
			@GET
			@Path("/azwbzpsave/{data}")
			@Produces("application/json")
			public Response saveZPAzwb (@PathParam("data") JSONObject data) throws JSONException{
				Session session = null;
				Transaction tx = null;
				Loginuser user=null;
				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				try{
					session = HibernateUtil.getSession();
					tx = session.beginTransaction();
					String userid=(String) data.get("userid");
					
					String billno=(String) data.get("billno");
					String transferid=(String) data.get("transferid");
					//String transferdate=(String) data.get("transferdate");
					String staffid=(String) data.get("staffid");
					String stafflinkphone=(String) data.get("stafflinkphone");

					//List userList=session.createQuery("from Loginuser where userid ='"+userid+"'").list();
					//if(userList!=null&&userList.size()>0){
					//	user=(Loginuser) userList.get(0);
					//}
					
					ElevatorTransferCaseRegister e=(ElevatorTransferCaseRegister) session.get(ElevatorTransferCaseRegister.class, billno);
					e.setTransferId(transferid);//ת����
					e.setTransferDate(CommonUtil.getNowTime());	//ת������			
                    e.setStaffName(staffid);
                    e.setStaffLinkPhone(stafflinkphone);
                    e.setSubmitType("Z");
                    e.setProcessStatus("0");
                    e.setReceiveDate("");
                    
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
			 * ��ȡת�ɵĳ���Ա
			 * @param userid
			 * @return
			 */
			@GET
			@Path("/azwbchecku/{userid}")
			@Produces("application/json")
			public Response getCheckUserList(
			@PathParam("userid") String userid 
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
					String sql="from Loginuser where roleid='A51'";
					List list=session.createQuery(sql).list();
					if(list!=null&&list.size()>0){
						for(int i=0;i<list.size();i++){
							JSONObject jsonObject=new JSONObject();
							Loginuser l=(Loginuser) list.get(i);
							jsonObject.put("stuserid", l.getUserid());//������Ա����
							jsonObject.put("stusername", l.getUsername());//������Ա����
							jsonObject.put("stphone", l.getPhone());//������Ա��ϵ�绰
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
			 * �Ǽ�
			 * @param data
			 * @return
			 * @throws JSONException 
			 */
			@POST
			@Path("/azwbadd")
			@Produces("application/json")
			public Response saveAzwb (@FormParam("data") JSONObject data) throws JSONException{
				Session session = null;
				Transaction tx = null;
				JbpmExtBridge jbpmExtBridge=null;

				JSONObject rejson = new JSONObject();
				JSONObject json = new JSONObject();//������� {}
				JSONArray jobiArray = new JSONArray();//�������� []
				String processName="";
				try{
					session = HibernateUtil.getSession();
					tx = session.beginTransaction();
					 
					String userid=(String) data.get("userid");//��¼�û�����
					String billno=(String) data.get("billno");//��ˮ��
					//String factorycheckresult=(String) data.get("factorycheckresult");//������
					String processstatus=(String) data.get("processstatus");//����״̬
					String submittype=(String)data.get("submittype");//�ύ��־
					String elevatoraddress=(String)data.get("elevatoraddress");//����λ��
					
					JSONArray detaillist=(JSONArray) data.get("detaillist");//��������
					JSONArray detaillist2=(JSONArray) data.get("detaillist2");//����Ҫ����
					
					String checktime=CommonUtil.getNowTime();//����ʱ��
					String checkdate=(String)data.get("checkdate");//ʵ�ʳ�������(��������)
					String checktime2=(String)data.get("checktime2");//ʵ�ʳ���ʱ��(��������)

					
					ElevatorTransferCaseRegister etcr =(ElevatorTransferCaseRegister) session.get(ElevatorTransferCaseRegister.class, billno);
					etcr.setCheckTime(checktime);
					etcr.setCheckDate(checkdate);
					etcr.setCheckTime2(checktime2);
					etcr.setElevatorAddress(elevatoraddress);
					//etcr.setSubmitType(submittype);
					etcr.setProcessStatus(processstatus);
					
					String firstInstallation=etcr.getFirstInstallation();//�Ƿ���ΰ�װ
					
					//�����Ƿ�ϸ�
					String factorycheckresult="�ϸ�";
					String cjfs="0";
					String factorycheckresult2="�ϸ�";
					System.out.println(">>>>��ʼ���㳧�����Ƿ�ϸ�===��ʼ");
					if(detaillist!=null && detaillist.length()>0){
						HashMap hmap=this.isQualified(etcr.getCheckNum(),etcr.getElevatorType(),detaillist,firstInstallation);
						factorycheckresult=(String)hmap.get("result");
						cjfs=(String)hmap.get("cjfs");
						factorycheckresult2=(String)hmap.get("factorycheckresult2");
					}
					System.out.println("factorycheckresult="+factorycheckresult+"; factorycheckresult2="+factorycheckresult2+"; cjfs="+cjfs);
					System.out.println(">>>>��ʼ���㳧�����Ƿ�ϸ�===����");
					
					etcr.setFactoryCheckResult(factorycheckresult);//������
					etcr.setR2(cjfs);//�������
					etcr.setFactoryCheckResult2(factorycheckresult2);//������2
					
					Integer status=etcr.getStatus();
					if(status!=null && status==0){
						
						//��ȡ������Ϣ ������ͨ����
						long taskid=0;
						String taskname="";
						String sqlc="select ID_,NAME_ from JBPM_TASKINSTANCE where TOKEN_="+etcr.getTokenId()+" and isnull(END_,'')=''";
						ResultSet rs=session.connection().prepareStatement(sqlc).executeQuery();
						if(rs.next()){
							taskid=rs.getLong("ID_");
							taskname=rs.getString("NAME_");
						}
						
						//���̴���
						String processDefineID = Grcnamelist1.getProcessDefineID("elevatortransfercaseregister", etcr.getStaffName());
						/*=============== ��������������ʼ =================*/
						jbpmExtBridge = new JbpmExtBridge();
						HashMap paraMap = new HashMap();
						ProcessBean pd = jbpmExtBridge.getProcessBeanUseTI(taskid);
						
						pd.addAppointActors("");// ����̬��ӵ�����������
						if(factorycheckresult.equals("�ϸ�")){
							pd.setSelpath("Y");
							processName="�ر��������";
							Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "�ر��������", "%");// ��������
						}else if(factorycheckresult.equals("���ϸ�")){
							pd.setSelpath("N");
							processName="���첿�����";
							Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "���첿�����", "%");// ��������
						}
						
						pd = jbpmExtBridge.goToNext(taskid, "�ύ", userid, paraMap);// ���
						/**==================== ���̽��� =======================**/
				
						etcr.setStatus(pd.getStatus());
						etcr.setTokenId(pd.getToken());
						etcr.setProcessName(pd.getNodename());
						etcr.setIsDeductions("");
						etcr.setDeductMoney(null);
						etcr.setProcessResult("");
						
						ElevatorTransferCaseProcess process = new ElevatorTransferCaseProcess();
						process.setElevatorTransferCaseRegister(etcr);
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
						//�ǼǱ����ύ
						if("Y".equals(submittype)){
							String processDefineID = Grcnamelist1.getProcessDefineID("elevatortransfercaseregister", etcr.getStaffName());
							if(processDefineID == null || processDefineID.equals("")){
								System.out.println("�ֻ��˵Ǽ�>>> δ��������������Ϣ������������");
								throw new Exception();
							}
							/**=============== ����������ʵ����ʼ ===================**/
							HashMap paraMap = new HashMap();
							jbpmExtBridge=new JbpmExtBridge();
							ProcessBean pd = jbpmExtBridge.getPb();
							if(factorycheckresult.equals("�ϸ�")){
								pd.setSelpath("Y");
								processName="�ر��������";
								Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "�ر��������", "%");// ��������
							}else if(factorycheckresult.equals("���ϸ�")){
								pd.setSelpath("N");
								processName="���첿�����";
								Grcnamelist1.setAllProcessAuditoper(pd, processDefineID, "���첿�����", "%");// ��������
							}
							
							pd=jbpmExtBridge.startProcess(WorkFlowConfig.getProcessDefine(processDefineID),userid,userid,billno,"",paraMap);
							/**==================== ���̽��� =======================**/
							etcr.setStatus(pd.getStatus());
							etcr.setTokenId(pd.getToken());
							etcr.setProcessName(pd.getNodename());
							
						}else{
							//String hql="update ElevatorTransferCaseRegister set checkTime='"+checktime+"',factoryCheckResult='"+factorycheckresult+"'" +
							//		",processStatus='"+processstatus+"' where billno='"+billno+"'";
							//session.createQuery(hql).executeUpdate();
						}
					}
					/**=======================����ͼƬ=============================*/
					String folder2 = PropertiesUtil.getProperty("ElevatorTransferCaseRegister.file.upload.folder");
					String filepath2=CommonUtil.getNowTime("yyyy-MM-dd")+"/";
					//����ǩ��
					String customersignature=(String)data.get("customersignature");
					if(customersignature!=null && !customersignature.trim().equals("")){
						//customersignature=customersignature.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
						String[] signatures=customersignature.split(",");
						if(signatures!=null && signatures.length>1){
							byte[] image=new BASE64Decoder().decodeBuffer(signatures[1]);
							String newfilename=etcr.getBillno()+"_0.jpg";
							//����ͼƬ
							File f=new File(folder2+filepath2);
							f.mkdirs();
							FileOutputStream fos=new FileOutputStream(folder2+filepath2+newfilename);
							fos.write(image);
							fos.flush();
							fos.close();
							//����ͼƬ��Ϣ�����ݿ�
							etcr.setCustomerSignature(filepath2+newfilename);
						}
					}
					//��������
					String customerimage=(String)data.get("customerimage");
					if(customerimage!=null && !customerimage.trim().equals("")){
						//customerimage=customerimage.replaceAll("data:image/jpeg;base64,", "");//ȥ��ǰ׺
						String[] cimages=customerimage.split(",");
						if(cimages!=null && cimages.length>1){
							byte[] image=new BASE64Decoder().decodeBuffer(cimages[1]);
							String newfilename=etcr.getBillno()+"_1.jpg";
							//����ͼƬ
							File f=new File(folder2+filepath2);
							f.mkdirs();
							FileOutputStream fos=new FileOutputStream(folder2+filepath2+newfilename);
							fos.write(image);
							fos.flush();
							fos.close();
							//����ͼƬ��Ϣ�����ݿ�
							etcr.setCustomerImage(filepath2+newfilename);
						}
					}
					/**=======================����ͼƬ=============================*/
					
					session.save(etcr);

					/**===================�������� ��ʼ===================*/
					//��ɾ�����治���ڵļ����
					String hecirJnlnos="";
					if(detaillist!=null&&detaillist.length()>0){
						for(int i=0;i<detaillist.length();i++){
							JSONObject object=(JSONObject) detaillist.get(i);
							String jnlno=(String) object.get("jnlno");//��ˮ��
							if(jnlno!=null&&!"".equals(jnlno)){
								hecirJnlnos+=i==0 ? jnlno : "','"+jnlno;
						    }
						}
					}
					
					//��ɾ�����ݿ�����
					List filelist=null;
					if(!hecirJnlnos.equals("")){
						String filesql="from HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno in(select jnlno from HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"'))";
						filelist=session.createQuery(filesql).list();
						
						session.createQuery("delete HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno in(select jnlno from HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"'))").executeUpdate();
						session.createQuery("delete HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"' and h.jnlno not in('"+hecirJnlnos+"')").executeUpdate();
					}else{
						String filesql="from HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno in(select jnlno from HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"')";
						filelist=session.createQuery(filesql).list();

						session.createQuery("delete HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno in(select jnlno from HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"')").executeUpdate();
						session.createQuery("delete HandoverElevatorCheckItemRegister h where h.elevatorTransferCaseRegister.billno='"+billno+"'").executeUpdate();
					}
					//��ɾ��Ӳ�̵�ͼƬ
					if(filelist!=null && filelist.size()>0){
						HandoverElevatorCheckFileinfo hecf=null;
						String folder ="HandoverElevatorCheckItemRegister.file.upload.folder";
						folder = PropertiesUtil.getProperty(folder).trim();
						for(int i=0;i<filelist.size();i++){
							hecf=(HandoverElevatorCheckFileinfo) filelist.get(i);
							
							HandleFile hf = new HandleFileImpA();
							String localpath=folder+"/"+hecf.getFilePath()+hecf.getNewFileName();
							hf.delFile(localpath);
						}
					}
					
					//�ڱ��泧������
					if(detaillist!=null && detaillist.length()>0){
						HandoverElevatorCheckItemRegister hecir=null;
						for(int i=0;i<detaillist.length();i++){
							JSONObject object=(JSONObject) detaillist.get(i);
							String jnlno=(String) object.get("jnlno");//��ˮ��
							String examtype=(String) object.get("examtype");//������ʹ���
							String checkitem=(String) object.get("checkitem");//�����Ŀ
							String issuecoding=(String) object.get("issuecoding");//�������
							String issuecontents=(String) object.get("issuecontents");//�������
							String rem=(String) object.get("rem");//��ע
							if(rem==null){
								rem="";
							}
							
							if(jnlno!=null && !jnlno.equals("")){
		                		hecir = (HandoverElevatorCheckItemRegister) session.get(HandoverElevatorCheckItemRegister.class, jnlno);	
		                    	if(hecir!=null){
			                		hecir.setRem(rem);
			                    	session.update(hecir);
		                    	}
			                }else{
				                hecir = new HandoverElevatorCheckItemRegister();
								String jnlnokk="";
								try {
									String[] billno1 = CommonUtil.getBillno(CommonUtil.getToday().substring(2,4),"HandoverElevatorCheckItemRegister", 1);	
									jnlnokk=billno1[0];
									}catch (Exception e) {
										e.printStackTrace();
									}
								hecir.setJnlno(jnlnokk);
								hecir.setIsDelete("N");
								hecir.setExamType(examtype);
								hecir.setIssueCoding(issuecoding);
								hecir.setCheckItem(checkitem);
								hecir.setIssueContents(issuecontents);
								hecir.setIsRecheck("N");
								hecir.setRem(rem);
								hecir.setElevatorTransferCaseRegister(etcr);
								session.save(hecir);
								//session.flush();
			                }
						}
					}
					/**===================�������� ����===================*/
					
					//����Ҫ����
					HandoverElevatorSpecialRegister hesr=null;
					if(detaillist2!=null&&detaillist2.length()>0){
						for(int i=0;i<detaillist2.length();i++){
							JSONObject object=(JSONObject) detaillist2.get(i);
							String jnlno2 = (String) object.get("jnlno2");	
						    //String scid=(String) object.get("scid");//����Ҫ�����
						    //String scname=(String) object.get("scname");//����Ҫ��
						    String isok=(String) object.get("isok");//�Ƿ�ѡ��
						    
		            		 hesr=(HandoverElevatorSpecialRegister) session.get(HandoverElevatorSpecialRegister.class,jnlno2) ;
		            		 hesr.setIsOk(isok);
		            	     session.update(hesr);
		            	     //session.flush();
						}
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
					json.put("code", "400");
		  			json.put("info", "NOT OK");
		            rejson.put("status", json);
		      		rejson.put("data", jobiArray);
					ex.printStackTrace();
					
					if (jbpmExtBridge != null) {
						jbpmExtBridge.setRollBack();
					}
				} finally {
		            try {
		            	if(session!=null){
		            		session.close();
		            	}
		            	if(jbpmExtBridge!=null){
							jbpmExtBridge.close();
						}
		            } catch (HibernateException hex) {
		                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
		            }
		        }
				
				return Response.status(200).entity(rejson.toString()).build();
			}
			
			//���������Ƿ�ϸ�
			/**
			���ݼ��㹫ʽ��
				======���ݳ���======
				1������һ�ش󲻺ϸ�����Ϊ���ϸ�
				2��û���ش󲻺ϸ����һ�㲻�ϸ��� ���ڰ���ģ�ҲΪ���ϸ�
				3��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ��ڰ����Ϊ����ϸ�
				======���ݸ��죨����2�Ρ�3�Ρ�4�Ρ�������ȥ��©����======
				1��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ��������Ϊ����ϸ�

			ֱ�ݼ��㹫ʽ�� ����С�������в��ϸ�����ͳ�ƣ���ͬ��С������ֻ��ʾһ�����ϸ��
				======ֱ�ݳ���======
				1������һ�ش󲻺ϸ�����Ϊ���ϸ�
				2��û���ش󲻺ϸ���� һ�㲻�ϸ��� ����18��ģ�ҲΪ���ϸ�
				3��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ���18���Ϊ����ϸ�
				======ֱ�ݸ��죨����2�Ρ�3�Ρ�4�Ρ�������ȥ��©����======
				1��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ���8���Ϊ����ϸ�
				
			�������===���㷽���� ���ݼ���ϸ񲻺ϸ���ش����һ������� ������һ�ٷ֣�-�۳�����
				ֱ�ݣ�һ����һ�ο�2�֣��ش���һ�ο�10�֡�
				���ݣ�һ����һ�ο�5�֣��ش���һ�ο�20��
			 * @throws JSONException 
			*/
			private HashMap isQualified(int checkNum,String elevatorType,JSONArray detaillist,String firstInstallation) throws JSONException{
				String factorycheckresult="�ϸ�";
				int zNum=0;
				int yNum=0;
				double cjfs=0;
				String strarr="";
				
				int firstnum=0;

				if(checkNum>1){
					//����
					for(int i=0;i<detaillist.length();i++){
						JSONObject object=(JSONObject) detaillist.get(i);
						String examtype=(String) object.get("examtype");//������ʹ���
						String itemgroup=(String) object.get("itemgroup");//С����
						String isrecheck=(String) object.get("isrecheck");//�Ƿ񸴼�
						String isyzg=(String) object.get("isyzg");//�Ƿ�������
						
						System.out.println("����>>>firstInstallation="+firstInstallation+"; isyzg="+isyzg);
						
						//�ų�©����
						if(isrecheck!=null && isrecheck.trim().equals("Y")){
							//�ش󲻺ϸ���
							if(examtype.equals("ZD")){
								if("Y".equals(firstInstallation) && ("Y".equals(isyzg) || "������".equals(isyzg))){
									firstnum++;
								}else{
									zNum+=1;
								}
							}
							//һ�㲻�ϸ���
							if(examtype.equals("YB")){
								if(elevatorType.equals("T")){

									if(!itemgroup.trim().equals("") && strarr.indexOf(itemgroup+",")>-1){
										
									}else{
										if("Y".equals(firstInstallation) && ("Y".equals(isyzg) || "������".equals(isyzg))){
											firstnum++;
										}else{
											strarr+=itemgroup+",";
											yNum+=1;
										}
									}
								}else{
									//����
									if("Y".equals(firstInstallation) && ("Y".equals(isyzg) || "������".equals(isyzg))){
										firstnum++;
									}else{
										yNum+=1;
									}
								}
							}
							
						}
					}
				}else{
					//����
					for(int i=0;i<detaillist.length();i++){
						JSONObject object=(JSONObject) detaillist.get(i);
						String examtype=(String) object.get("examtype");//������ʹ���
						String itemgroup=(String) object.get("itemgroup");//С����
						String isyzg=(String) object.get("isyzg");//�Ƿ�������
						
						System.out.println("����>>>firstInstallation="+firstInstallation+"; isyzg="+isyzg);
						
						//�ش󲻺ϸ���
						if(examtype.equals("ZD")){
							if("Y".equals(firstInstallation) && ("Y".equals(isyzg) || "������".equals(isyzg))){
								firstnum++;
							}else{
								zNum+=1;
							}
						}
						//һ�㲻�ϸ���
						if(examtype.equals("YB")){
							if(elevatorType.equals("T")){

								if(!itemgroup.trim().equals("") && strarr.indexOf(itemgroup+",")>-1){
									
								}else{
									if("Y".equals(firstInstallation) && ("Y".equals(isyzg) || "������".equals(isyzg))){
										firstnum++;
									}else{
										strarr+=itemgroup+",";
										yNum+=1;
									}
								}
							}else{
								//����
								if("Y".equals(firstInstallation) && ("Y".equals(isyzg) || "������".equals(isyzg))){
									firstnum++;
								}else{
									yNum+=1;
								}
							}
						}
						
					}
				}
				
				if(checkNum>1){
					//����
					if(elevatorType.equals("T")){
						if(zNum>=1 || yNum>8){
							factorycheckresult="���ϸ�";
						}else{
							factorycheckresult="�ϸ�";
						}
						//ֱ�ݣ��ش���һ�ο�10��,һ����һ�ο�2�֡�
						cjfs=(zNum*10)+(yNum*2);
					}
					if(elevatorType.equals("F")){
						if(zNum>=1 || yNum>3){
							factorycheckresult="���ϸ�";
						}else{
							factorycheckresult="�ϸ�";
						}
						//���ݣ��ش���һ�ο�20��,һ����һ�ο�5�֡�
						cjfs=(zNum*20)+(yNum*5);
					}
				}else{
					//����
					if(elevatorType.equals("T")){
						if(zNum>=1 || yNum>18){
							factorycheckresult="���ϸ�";
						}else{
							factorycheckresult="�ϸ�";
						}
						//ֱ�ݣ��ش���һ�ο�10��,һ����һ�ο�2�֡�
						cjfs=(zNum*10)+(yNum*2);
					}
					if(elevatorType.equals("F")){
						if(zNum>=1 || yNum>8){
							factorycheckresult="���ϸ�";
						}else{
							factorycheckresult="�ϸ�";
						}
						//���ݣ��ش���һ�ο�20��,һ����һ�ο�5�֡�
						cjfs=(zNum*20)+(yNum*5);
					}
				}
				
				String factorycheckresult2=factorycheckresult;
				if(firstnum>0 && factorycheckresult=="�ϸ�"){
					factorycheckresult2="����Ա��ĺϸ�";
				}
				
				HashMap remap=new HashMap();
				remap.put("result", factorycheckresult);
				remap.put("cjfs", String.valueOf(100-cjfs));
				remap.put("factorycheckresult2", factorycheckresult2);
				
				return remap;
				
			}
			

	  /**
		 * ��ȡ������ͼƬ����
		 * @param userid
		 * @param elevatortype
		 * @param issuecoding
		 * @return
		 */
		@GET
		@Path("/azwbcheckitemimgae/{jnlno}")
		@Produces("application/json")
		public Response getImageList(@PathParam("jnlno") String jnlno){
			Session session = null;
			//Loginuser user=null;
			JSONObject rejson = new JSONObject();
			JSONObject json = new JSONObject();//������� {}
			JSONArray imgarr=new JSONArray();//�������� []
			
			HandoverElevatorCheckFileinfo hefile=null;
			//�����ļ�·��
			String folder="HandoverElevatorCheckItemRegister.file.upload.folder";
			folder = PropertiesUtil.getProperty(folder).trim();
			
			BASE64Encoder base=new BASE64Encoder();
			try{
				session = HibernateUtil.getSession();

				String hql="from HandoverElevatorCheckFileinfo h where h.handoverElevatorCheckItemRegister.jnlno ='"+jnlno.trim()+"' ";
				List fileList=session.createQuery(hql).list();
				
				if(fileList!=null&&fileList.size()>0){
					for(int j=0;j<fileList.size();j++){
						hefile=(HandoverElevatorCheckFileinfo)fileList.get(j);
						String filepath=folder+"/"+hefile.getFilePath()+hefile.getNewFileName();
						//��ͼƬת��Ϊ��������
						byte[] imgbyte=CommonUtil.imageToByte(filepath);
						
						JSONObject objf=new JSONObject();
						objf.put("filesid", hefile.getFileSid());
						objf.put("oldfilename", hefile.getOldFileName());
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
			try{
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();

				String userid=(String) data.get("userid");//��¼�û�����
				String billno=(String) data.get("billno");//��ˮ��
				String jnlno=(String) data.get("jnlno");//��ˮ��
				String examtype=(String) data.get("examtype");//������ʹ���
				String checkitem=(String) data.get("checkitem");//�����Ŀ
				String issuecoding=(String) data.get("issuecoding");//�������
				String issuecontents=(String) data.get("issuecontents");//�������
				String rem=(String) data.get("rem");//��ע
				String isyzg=(String) data.get("isyzg");//�Ƿ�������
				
				JSONArray imagelist=(JSONArray) data.get("imagelist");//�ϴ�ͼƬ
				
				ElevatorTransferCaseRegister etcr =(ElevatorTransferCaseRegister) session.get(ElevatorTransferCaseRegister.class, billno);

				//���氲װά�����ӵ�������Ǽ���Ŀ
				HandoverElevatorCheckItemRegister hecir=null;
				if(jnlno!=null && !jnlno.equals("")){
					//��ˮ�Ŵ��ھ��޸�
            		hecir = (HandoverElevatorCheckItemRegister) session.get(HandoverElevatorCheckItemRegister.class, jnlno);	
                	hecir.setRem(rem);
                	hecir.setIsyzg(isyzg);
                	session.update(hecir);
                }else{
                	//�����ھ��½�
	                hecir = new HandoverElevatorCheckItemRegister();
					String[] billno1 = CommonUtil.getBillno(CommonUtil.getToday().substring(2,4),"HandoverElevatorCheckItemRegister", 1);	
					jnlno=billno1[0];

					hecir.setJnlno(jnlno);
					hecir.setIsDelete("N");
					hecir.setExamType(examtype);
					hecir.setIssueCoding(issuecoding);
					hecir.setCheckItem(checkitem);
					hecir.setIssueContents(issuecontents);
					hecir.setIsRecheck("N");
					hecir.setRem(rem);
					hecir.setIsyzg(isyzg);
					hecir.setElevatorTransferCaseRegister(etcr);
					session.save(hecir);
					session.flush();
                }
				
				//����ͼƬ
				HandoverElevatorCheckFileinfo fileInfo=null;
				String folder ="HandoverElevatorCheckItemRegister.file.upload.folder";
				folder = PropertiesUtil.getProperty(folder).trim();
				
				String curdate=DateUtil.getDateTime("yyyyMMddHHmmss");
				for(int i=0;i<imagelist.length();i++){
					JSONObject object=(JSONObject) imagelist.get(i);
					String imgpic=(String) object.get("imgpic");//��ˮ��
					
					byte[] image=new BASE64Decoder().decodeBuffer(imgpic);
					String newfilename=userid+"_"+curdate+"_"+i+".jpg";
					String filepath=CommonUtil.getNowTime("yyyy-MM-dd")+"/";

					//����ͼƬ
					File f=new File(folder+"/"+filepath);
					f.mkdirs();
					FileOutputStream fos=new FileOutputStream(folder+"/"+filepath+newfilename);
					fos.write(image);
					fos.flush();
					fos.close();
					
					//����ͼƬ��Ϣ�����ݿ�
					fileInfo=new HandoverElevatorCheckFileinfo();
					fileInfo.setHandoverElevatorCheckItemRegister(hecir);
					fileInfo.setOldFileName(newfilename);
					fileInfo.setNewFileName(newfilename);
					fileInfo.setFileSize(Double.valueOf(0));
					fileInfo.setFilePath(filepath);
					fileInfo.setFileFormat("-");
					fileInfo.setUploadDate(CommonUtil.getNowTime());
					fileInfo.setUploader(userid);
					fileInfo.setExt1("N");
					session.save(fileInfo);
					
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
			
			HandoverElevatorCheckFileinfo hecf=null;
			try{
				session = HibernateUtil.getSession();
				tx = session.beginTransaction();
				
				String folder ="HandoverElevatorCheckItemRegister.file.upload.folder";
				folder = PropertiesUtil.getProperty(folder).trim();
				if(filesid!=null && filesid.length()>0){
					
					hecf=(HandoverElevatorCheckFileinfo) session.get(HandoverElevatorCheckFileinfo.class,Integer.valueOf(filesid));
					String filename=hecf.getNewFileName();
					String filepath=hecf.getFilePath();
					session.delete(hecf);
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

		  
}



