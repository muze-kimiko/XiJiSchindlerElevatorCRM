package com.gzunicorn.struts.action.xjsgg;
import java.sql.Connection;
import java.sql.DriverManager;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.basic.common.CommonUtil;
import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.logic.HLBaseDataImpl;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.hibernate.hotlinemanagement.calloutmaster.CalloutMaster;

public class XjsggAction {
	/*
	 * �ж�ֵ�Ƿ�Ϊ��
	 */
	public String getsql(String colnum,String value){		
		String sql="";
		if(value!=null && !"".equals(value)){
			sql+=" and "+colnum+" like '%"+value.trim()+"%'";
		}else{
			sql+=" and "+colnum+" like '%'";
		}
		return sql;
	}
	
	/*
	 * ��ˣ��ύ�ȱ�־
	 * �ж��ֶ�ֵ�Ƿ�Ϊ��
	 */
	public String getnullsql(String colnum,String value){		
		String sql="";
		if(value!=null && !"".equals(value)){
			sql+=" and  isnull("+colnum+",'N') like '%"+value.trim()+"%'";
		}else{
			sql+=" and isnull("+colnum+",'N') like '%'";
		}
		return sql;
	}
	
	/*
	 * ��ȡ��ǰʱ��
	 * @parament cb
	 */
	public String getdatetime(){		
		String datetime="";
		String date=CommonUtil.getToday();
		String time=CommonUtil.getTodayTime();
		datetime=date+" "+time;
		return datetime;
	}
	/*
	 * ��ȡ����״̬�������ֵ
	 * 
	 */
	public List getProcessState(Session hs){
		List list=new ArrayList();
		List list2=new ArrayList();
		String sql="select typeid,typename from ViewFlowStatus order by orderby ";
		list=hs.createSQLQuery(sql).list();
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[])list.get(i);
			HashMap hm=new HashMap();
			hm.put("typeid", String.valueOf(obj[0]));
			hm.put("typename", String.valueOf(obj[1]));
			list2.add(hm);
		}
		return list2;
	}
	
	/*
	 * sql��ȡÿ���ֶε�ֵ
	 * table,value1,value2
	 * 
	 *
	 */
	public String getValue(Session hs,String table,String value1,String value2,String value3){
		String value="";
		String sql="Select a."+value1+" from  "+table+" a where a."+value2+"='"+value3+"'";
		List list=hs.createSQLQuery(sql).list();
		if(list!=null && list.size()>0){
			value=(String)list.get(0);
		}
		return value;
	}
   /*
    * hql��ñ����
    * ���� table �����ֶ��� value
    * 
    */
	public List getClasses(Session hs,String table,String billno,String value){
		String hql="select a from "+table+" a where a."+billno+"='"+value+"'";
		List list=hs.createQuery(hql).list();
		return list;
	}
	/*
	 * �鿴����
	 * 
	 * 
	 */
	 public List getfile(Session hs,String table,String filename,String billno){
		 List list=new ArrayList();
		 String sql="select a.*,b.username as UploaderName  from  "+table+" a ,loginuser b "
		 		+ "where a.Uploader = b.userid and a.MaterSid = '"+billno+"' and a.BusTable = '"+filename+"'";
		  list=hs.createSQLQuery(sql).list();
		 return list;
		 
	 }
	 /**
		 * ���ɼ���������
		 * @return
		 */
		public static synchronized String genCalloutMasterNum(){//create the ConsultMaster number
			// �����ͷ����
			String prefix = "BX";

			// �������ڲ���
			SimpleDateFormat simpleFormatter = new SimpleDateFormat("yyyyMM");
			String dateStr = simpleFormatter.format(new Date());
			// ����˳��Ų���
			List<CalloutMaster> list = null;
			// ����˳��Ų���
			Transaction tx = null;
			Session session = null;
			String numHead = prefix + dateStr;
			String numEnd = "";
			String hql = "FROM CalloutMaster as cm WHERE cm.calloutMasterNo like '"+ numHead + "%' order by calloutMasterNo desc";
			try {
				session = HibernateUtil.getSession();
				Query query = session.createQuery(hql);
				list = query.list();

				if (list == null || list.isEmpty() || list.size() == 0) {
					// ��û�м�¼�����кŴ�0001��ʼ
					numEnd = "0001";
				} else {
					// ����м�¼,ȥ���µļ�¼,Ȼ��+1,����Ҫ���Զ���ǰ�õ�0
					String currentNo = ((CalloutMaster) list.get(0)).getCalloutMasterNo();
					currentNo = currentNo.replace(numHead, "");
					numEnd = String.format("%04d",Integer.parseInt(currentNo) + 1);
				}

			}catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("����������: "+numHead + numEnd);
			return numHead + numEnd;
		}
		/* �������̻�
		 * �ô���״̬
		 * 
		 */
		public List getselect(Session hs,String table,String billno,String value){
        String hql="select a from Pulldown a where a.id.typeflag='CalloutMaster_HandleStatus' "
        		+ "and a.enabledflag='Y' order by orderby";
			List list=hs.createQuery(hql).list();
			return list;
		}
		/**
		 * ���Ͷ��ŷ�����ʹ�ö���è
		 * @param content
		 * @param phone
		 * @return
		 */
		public static boolean sendMessage(String smsContent, String telNo){
			Boolean finash=true;
			try{
				HLBaseDataImpl bd = new HLBaseDataImpl();
				String dbUser = "admin"; //ȱʡ�û���  
				String dbPwd = "668899";  
				String url="jdbc:odbc:driver={Microsoft Access Driver (*.mdb, *.accdb)};DBQ= D:\\����è��ģͨ�ýӿ�\\GSM_data.mdb;";//���ACCESS�ļ�λ��
			    Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
			    Connection conn=DriverManager.getConnection(url,dbUser,dbPwd);
			    String s_mob = telNo;//�ֻ��� 
			    String s_com = "1";//�豸��� Ĭ��1 
			    String s_info = smsContent;//��������Ϣ���� 
			    String s_style = "0";//0������Ϣ��1�ǳɹ���2��ʧ�� 
			    String s_time = bd.getDateTimeStr(bd.addMin(new Date(), 1));// ��ʱ����ʱ��,����ʱ���Ͳ���д�� 
			    String s_userid = "";//ʵ�ʷ���ʱ��,����д��
			    String s_client = "";//���� 
			    String s_inputtime = bd.getNowDateTimeStr();//���� 
			    String sql="insert into send_Info (s_mob,s_com,s_info,s_style,s_Inputtime,s_time) values ('" + s_mob+ "', " + s_com + ", '" +s_info+"'," + s_style+ ",'"+ s_inputtime +"','" + s_time+"')"  ;
				conn.prepareStatement(sql).executeUpdate();
				conn.commit();
				//System.out.println("-------------------------------");
				//System.out.println("SMS Send Number:" + telNo);
				//System.out.println("SMS Send Content" + smsContent);
				//System.out.println("-------------------------------");
			}catch(Exception ex){
				//System.out.println("���Ͷ���ʧ��");
				finash=false;
				ex.printStackTrace();
				
			}
			
			
		
			return finash;
		}	
	  
}
