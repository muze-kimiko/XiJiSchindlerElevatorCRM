package com.gzunicorn.common.grcnamelist;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;
import com.gzunicorn.hibernate.proshr.ProcessSetBranch;

public class Grcnamelist1 {

	public static List getgrcnamelist(Session hs, String userid)
			throws SQLException {
		List grcidlist = new ArrayList();
		Connection conn = null;
		conn = hs.connection();
		ResultSet re = conn.prepareCall("exec SP_GRCNAMELIST '" + userid + "'")
				.executeQuery();
		while (re.next()) {
			HashMap hm = new HashMap();
			hm.put("grcid", re.getString("grcid"));
			hm.put("grcname", re.getString("grcname"));
			grcidlist.add(hm);
		}
		return grcidlist;
	}

	public static List getgrcnamelist(String userid) {

		List grcidlist = null;
		Session hs = null;
		Connection conn = null;
		try {
			hs = HibernateUtil.getSession();
			grcidlist = new ArrayList();
			conn = hs.connection();
			ResultSet re = conn.prepareCall(
					"exec SP_GRCNAMELIST '" + userid + "'").executeQuery();
			while (re.next()) {
				HashMap hm = new HashMap();
				hm.put("grcid", re.getString("grcid"));
				hm.put("grcname", re.getString("grcname"));
				grcidlist.add(hm);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if(conn!=null){
					conn.close();
				}
				if(hs!=null){
					hs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return grcidlist;
		}

	}

	/*
	 * @parm�����������ݵ���
	 */
	public static List getgrcnamelist2(Session hs, String userid)
			throws SQLException {
		List grcidlist = new ArrayList();
		Connection conn = null;
		conn = hs.connection();
		ResultSet re = conn.prepareCall("exec SP_GRCNAMELIST2 '" + userid + "'")
				.executeQuery();
		while (re.next()) {
			HashMap hm = new HashMap();
			hm.put("grcid", re.getString("grcid"));
			hm.put("grcname", re.getString("grcname"));
			grcidlist.add(hm);
		}
		return grcidlist;
	}

	public static List getStorageName(Session hs, String userid) {
		List StorageNameList = new ArrayList();
		List StorageNameList1 = new ArrayList();
		String grcid = (String) hs.createSQLQuery("select grcid from loginuser where userid ='" + userid + "'").list().get(0);
		if (grcid.equals("00")) {
			HashMap hm = new HashMap();
			hm.put("storageid", "%");
			hm.put("storagename", "ȫ��");
			StorageNameList.add(hm);
			Query query = hs.createQuery("from Viewmugstorage where len(storagename)<=5");
			StorageNameList1 = query.list();
			StorageNameList.addAll(StorageNameList1);
		} else {
			Query query = hs.createQuery("from Viewmugstorage where len(storagename)<=5 and ComID= '"
					+ grcid + "'");
			if (query.list().size() > 0) {
				HashMap hm = new HashMap();
				hm.put("storageid", "%");
				hm.put("storagename", "ȫ��");
				StorageNameList.add(hm);
				StorageNameList1 = query.list();
				StorageNameList.addAll(StorageNameList1);
			} else {
				HashMap hm = new HashMap();
				hm.put("storageid", "");
				hm.put("storagename", "");
				StorageNameList.add(hm);
			}
		}
		return StorageNameList;
	}

	/*
	 * �����ϢԴ�б� ���ڹ���Ӫ��ģ���µĹ��̱������������ʹ�ã��������������
	 */
	public static List getStorageName2(Session hs, String userid) {
		List StorageNameList = new ArrayList();
		List StorageNameList1 = new ArrayList();
		String grcid = (String) hs
				.createSQLQuery(
						"select grcid from loginuser where userid ='" + userid
								+ "'").list().get(0);
		if (grcid.equals("001")) {
			Query query = hs.createQuery("from Viewreportinfo ");
			StorageNameList1 = query.list();
			StorageNameList.addAll(StorageNameList1);
		} else {
			Query query = hs.createQuery("from Viewreportinfo where ComID= '"
					+ grcid+"' or ComID=''");
			if (query.list().size() > 0) {
				StorageNameList1 = query.list();
				StorageNameList.addAll(StorageNameList1);
			} else {
				HashMap hm = new HashMap();
				hm.put("storageid", "");
				hm.put("storagename", "");
				StorageNameList.add(hm);
			}
		}
		return StorageNameList;
	}

	public static List getgrcnamelistold(ViewLoginUserInfo vluf) {

		List grcidlist = new ArrayList();
		Session hs = null;
		String sql = "select b.grcid,b.grcname,b.grtypeid from loginuser as a,GR_CompanyType as b "
				+ " where a.grcid=b.grcid  and b.enabledflag='Y' and a.userid='"
				+ vluf.getUserID() + "'";
		try {
			hs = HibernateUtil.getSession();
			List list1 = hs.createSQLQuery(sql).list();
			if (list1.size() > 0) {
				Object[] obj = (Object[]) list1.get(0);
				String grtypeid = String.valueOf(obj[2]);
				if (grtypeid != null && "0".equals(grtypeid)) {
					HashMap hm = new HashMap();
					hm.put("grcid", "%");
					hm.put("grcname", "ȫ��");
					grcidlist.add(hm);
					String sql1 = "select grcid,grcname from GR_CompanyType where enabledflag='Y'";
					List grclist = hs.createSQLQuery(sql1).list();
					for (int i = 0; i < grclist.size(); i++) {
						Object[] obj1 = (Object[]) grclist.get(i);
						HashMap hm1 = new HashMap();
						hm1.put("grcid", String.valueOf(obj1[0]));
						hm1.put("grcname", String.valueOf(obj1[1]));
						grcidlist.add(hm1);
					}

				} else {
					HashMap hm1 = new HashMap();
					hm1.put("grcid", String.valueOf(obj[0]));
					hm1.put("grcname", String.valueOf(obj[1]));
					grcidlist.add(hm1);
				}
			} else {
				HashMap hm1 = new HashMap();
				hm1.put("grcid", "");
				hm1.put("grcname", "");
				grcidlist.add(hm1);
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {

				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
			return grcidlist;
		}

	}

	/*
	 * ��ȡҵ��Ա�б�
	 */
	public static List getOperationList(String userid) {

		List operationList = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			operationList = new ArrayList();
			Connection conn = null;
			conn = hs.connection();
			ResultSet re = conn.prepareCall(
					"EXEC GET_OPERATION '" + userid + "'").executeQuery();
			while (re.next()) {
				HashMap hm = new HashMap();
				hm.put("userid", re.getString("userid"));
				hm.put("username", re.getString("username"));
				operationList.add(hm);
			}

		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
			return operationList;
		}

	}
	
	/**
	 * ��ȡ����ID
	 * @param processDefine ������
	 * @param operationId ҵ��Ա
	 * @return
	 */
	public static String getProcessDefineID(String processDefine,String operationId){
		
		String processDefineID = "";
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			String hql = "select a from ProcessSetBranch a,Loginuser b" +
							" where a.processDefine like '" +processDefine.trim() + "'" +
							" and a.comId = b.grcid" +
							" and b.userid like '"+operationId.trim()+"'";
			List result = hs.createQuery(hql).list();
			if (result != null && result.size()>0) {
				ProcessSetBranch processSetBranch = (ProcessSetBranch) result.get(0);
				processDefineID += processSetBranch.getProcessDefineId();
			}
						
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (DataStoreException e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}		
		return processDefineID;
	}
	/**
	 * ��ȡ����ID
	 * @param processDefine ������
	 * @param comID �����ֲ�
	 * @return
	 */
	public static String getProcessDefineID2(String processDefine,String comID){
		
		String processDefineID = "";
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			String hql = "select a from ProcessSetBranch a" +
							" where a.processDefine like '" +processDefine.trim() + "'" +
							" and a.comId like '"+comID.trim()+"'";
			List result = hs.createQuery(hql).list();
			if (result != null && result.size()>0) {
				ProcessSetBranch processSetBranch = (ProcessSetBranch) result.get(0);
				processDefineID += processSetBranch.getProcessDefineId();
			}
						
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (DataStoreException e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}		
		return processDefineID;
	}
	
	/**
	 * ���ȫ�����������
	 * @param pd ProcessBean
	 * @param processDefineID ����ID
	 * @param taskName ��������
	 * @param operationId ҵ��Ա
	 */
	public static void setAllProcessAuditoper(ProcessBean pd,String processDefineID,String taskName,String operationId){

		Session hs = null;
		try {
			hs = HibernateUtil.getSession();

			String sql = "select b.auditoperid"
					+ " from ProcessSetAuditMaster a,ProcessSetAuditDetail b,LoginUser l"
					+ " where a.processDefineID like '" + processDefineID.trim() + "'"
					+ " and a.nodename like '" + taskName.trim() + "'"
					+ " and a.billno=b.billno"
					+ " and b.auditoperid=l.UserID" 
					+ " and l.EnabledFlag='Y'";
			
			// ���ܲ�ʱֻ����������ֹ�˾�����
			if (!taskName.trim().contains("�ܲ�")) {
				sql += " and b.comid in (select grcid from loginuser where userid like '" + operationId.trim() + "')";
			}

			List result = hs.createSQLQuery(sql).list();
			if (result != null && result.size() > 0) {
				for (Object object : result) {
					String auditoper = (String) object;
					pd.addAppointActors(auditoper);
				}

			} else {
				pd.addAppointActors("admin");
			}
		    	    
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ְλID���ֲ��Ͳ���ȡ������
	 * @param pd
	 * @param processDefineID
	 * @param class1Id
	 * @param maintDivision
	 * @param storageid
	 */
	public static void setJbpmAuditopers_class(ProcessBean pd,String processDefineID,String class1Id,String maintDivision,String storageid){

		Session hs = null;
		try {
			hs = HibernateUtil.getSession();

			String sql = "select userid from LoginUser" +
						" where grcid like '" + maintDivision.trim() + "'" +
						" and Class1ID like '" + class1Id.trim() + "'" +
						" and StorageID like '" + storageid.trim() + "%'";

			List result = hs.createSQLQuery(sql).list();
			
			if (result != null && result.size() > 0) {
				for (Object object : result) {
					String auditoper = (String) object;
					pd.addAppointActors(auditoper);
				}

			} else {
				pd.addAppointActors("admin");
			}
		    	    
		} catch (Exception e) {
			e.printStackTrace();
			pd.addAppointActors("admin");
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * ���ݽ�ɫ��ȡ�����
	 * @param pd
	 * @param processDefineID
	 * @param class1Id
	 * @param maintDivision
	 * @param storageid
	 */
	public static void setJbpmAuditopers_class2(ProcessBean pd,String processDefineID,String roleid,String maintDivision,String storageid){

		Session hs = null;
		try {
			hs = HibernateUtil.getSession();

			String sql = "select userid from LoginUser "
					+ "where grcid like '" + maintDivision.trim() + "' "
					+ "and roleid like '" + roleid.trim() + "' "
					+ "and StorageID like '" + storageid.trim() + "' "
					+ "and EnabledFlag='Y'";

			List result = hs.createSQLQuery(sql).list();
			
			if (result != null && result.size() > 0) {
				for (Object object : result) {
					String auditoper = (String) object;
					pd.addAppointActors(auditoper);
				}

			} else {
				pd.addAppointActors("admin");
			}
		    	    
		} catch (Exception e) {
			e.printStackTrace();
			pd.addAppointActors("admin");
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ��ά���ֲ� ���ȫ�����������
	 * @param pd ProcessBean
	 * @param processDefineID ����ID
	 * @param taskName ��������
	 * @param grcid �ֲ�id
	 */
	public static void setJbpmAuditopers(ProcessBean pd,String processDefineID,String taskName,String grcid){

		Session hs = null;
		try {
			hs = HibernateUtil.getSession();

			String sql = "select b.auditoperid"
					+ " from ProcessSetAuditMaster a,ProcessSetAuditDetail b,LoginUser l"
					+ " where a.processDefineID like '" + processDefineID.trim() + "'"
					+ " and a.nodename like '" + taskName.trim() + "'"
					+ " and a.billno=b.billno"
					+ " and b.auditoperid=l.UserID" 
					+ " and l.EnabledFlag='Y'"
					+ " and grcid like '" + grcid.trim()+"'";

			List result = hs.createSQLQuery(sql).list();
			if (result != null && result.size() > 0) {
				for (Object object : result) {
					String auditoper = (String) object;
					pd.addAppointActors(auditoper);
				}

			} else {
				pd.addAppointActors("admin");
			}
		    	    
		} catch (Exception e) {
			e.printStackTrace();
			pd.addAppointActors("admin");
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * ���������˻�ȡά���ֲ�����ά���ܲ���
	 * @param pd
	 * @param isfbz �Ƿ�ά���ֲ���
	 * @param comid �����ֲ�
	 */
	public static void setJbpmAuditopers_roleid(ProcessBean pd,String isfbz,String comid){

		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			//ά���ܲ��� A13
			String sql = "select UserID from LoginUser where RoleID='A13' and EnabledFlag='Y'";
			if("Y".equals(isfbz)){
				//ά���ֲ��� A02
				sql = "select UserID from LoginUser where RoleID='A02' and EnabledFlag='Y' "
						+ "and grcid='"+comid.trim()+"'";
			}
			
			List result = hs.createSQLQuery(sql).list();
			
			if (result != null && result.size() > 0) {
				for (Object object : result) {
					String auditoper = (String) object;
					pd.addAppointActors(auditoper);
				}

			} else {
				pd.addAppointActors("admin");
			}
		    	    
		} catch (Exception e) {
			e.printStackTrace();
			pd.addAppointActors("admin");
		} finally {
			try {
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}

}
