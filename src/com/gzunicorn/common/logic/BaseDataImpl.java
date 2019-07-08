/*
 * Created on 2005-7-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.logic;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.DynaActionForm;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.common.util.DataStoreException;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.basedata.city.City;
import com.gzunicorn.hibernate.basedata.country.Country;
import com.gzunicorn.hibernate.basedata.province.Province;
import com.gzunicorn.hibernate.basedata.region.Region;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferFeedback;
import com.gzunicorn.hibernate.contracttransfer.ContractTransferMaster;
import com.gzunicorn.hibernate.sysmanager.Storageid;
import com.gzunicorn.hibernate.sysmanager.pulldown.Pulldown;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

/**
 * @author Administrator ����������������ͳһ���� TODO To change the template for this
 *         generated type comment go to Window - Preferences - Java - Code Style -
 *         Code Templates
 */
public class BaseDataImpl {

	Log log = LogFactory.getLog(BaseDataImpl.class);
	/**
	 * Get the ElevatorInfoType from ViewElevatorInfoType;
	 * 
	 * Lee Yang
	 */
	public java.util.List getElevatorInfoTypeList() {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs.createQuery("from Viewelevatorinfotype");

			list = query.list();
			// Criteria criteria =
			// hs.createCriteria(Feeappinfo.class).add(Expression.eq("enabledflag",enabledFlag));

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
			}
		}
		return list;
	}

	/***************************************************************************
	 * ��õ�ǰ�����û�����
	 * 
	 * @param request
	 * @return
	 */
	public String getOperid(HttpServletRequest request) {
		ViewLoginUserInfo loginUser;
		HttpSession httpSession = request.getSession();
		loginUser = (ViewLoginUserInfo) httpSession
				.getAttribute(SysConfig.LOGIN_USER_INFO);
		return loginUser.getUserID();
	}

	/**
	 * Get the FeeTypeInfoList from FeeTypeInfo table;
	 * 
	 * Lee Yang
	 */
	public java.util.List getContractList() {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs.createQuery("from Mugcontractmaster");

			list = query.list();
			// Criteria criteria =
			// hs.createCriteria(Feeappinfo.class).add(Expression.eq("enabledflag",enabledFlag));

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
			}
		}
		return list;
	}

	/**
	 * Get the FeeTypeInfoList from FeeTypeInfo table;
	 * 
	 * Lee Yang
	 */
	public java.util.List getFeeTypeInfoList(String enabledFlag) {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs
					.createQuery("from Feetypeinfo f where f.enabledflag =:enabledflag");
			query.setString("enabledflag", enabledFlag);
			list = query.list();
			// Criteria criteria =
			// hs.createCriteria(Feeappinfo.class).add(Expression.eq("enabledflag",enabledFlag));

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
			}
		}
		return list;
	}

	/**
	 * Get the MugMaTypesList from MugMaType table;
	 * 
	 * Lee Yang
	 */

	public java.util.List getMugMaTypesList(String enabledFlag) {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs
					.createQuery("from Mugmatype m where m.enabledflag =:enabledflag");
			query.setString("enabledflag", enabledFlag);
			list = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
			}
		}
		return list;
	}

	/**
	 * ��ñ�����ͬ���ݵĴӱ���Ϣ.���ݺ�ͬ���������. Get the MugCustomerList from MugCustomer table;
	 * 
	 * Lee Yang
	 */

	public java.util.List getMugContractDetailList(String billno) {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs
					.createQuery("from Mugcontractdetail m where billno =:billno");
			query.setString("billno", billno);
			list = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return list;

	}

	/**
	 * ���ڲ�ѯ������ά�ޣ��������ʱ����ѯ��Ŀ��������ơ�
	 * 
	 * @param sort
	 *            1,������2��ά�ޣ�3������
	 * @return
	 */

	public java.util.List getsellPaymentSortList(String sort) {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs
					.createQuery("from Sellpaymentsort as a where a.sort='"
							+ sort + "' and a.enabledflag='Y'");
			list = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return list;

	}

	/**
	 * Get the MugCustomerList from MugCustomer table;
	 * 
	 * Lee Yang
	 */

	public java.util.List getMugCustomers(String enableFlag) {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs
					.createQuery("from Mugcustomer m where enabledflag =:enabledflag");
			query.setString("enabledflag", enableFlag);
			list = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return list;

	}

	/**
	 * Get the ModuleList from module table;
	 * 
	 * @return
	 */

	public java.util.List getYearList() {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs.createQuery("from Viewgetyearlist a ");
			list = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return list;

	}

	public java.util.List getModuleList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Module a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ProcPowerListt from PowerListt table; auother: zrj function:
	 * �յ�ƥ����Ϣ��������
	 * 
	 * @return
	 */

	public java.util.List getProcPowerList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Procpower a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ProcKindListt from PowerKind table; auother: zrj function:
	 * �յ�������Ϣ��������
	 * 
	 * @return
	 */

	public java.util.List getProcKindList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Prockind a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ProcStyleListt from ProcStyle table; auother: zrj function:
	 * �յ���ʽ��Ϣ��������
	 * 
	 * @return
	 */

	public java.util.List getProcStyleList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Procstyle a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ProcTypeListt from ProcType table; auother: zrj function:
	 * �յ�������Ϣ��������
	 * 
	 * @return
	 */

	public java.util.List getProcTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Proctype a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ProcSeriesListt from ProcSeries table; auother: zrj function:
	 * ����ϵ����Ϣ��������
	 * 
	 * @return
	 */

	public java.util.List getProcSeriesList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Procseries a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the SaleModeListt from SaleMode table; auother: zrj function:
	 * ����������Ϣ��������
	 * 
	 * @return
	 */

	public java.util.List getSaleModeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Salemode a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get description from specify table.
	 * 
	 * @param tablename
	 * @param column1
	 * @param column2
	 * @param value2
	 * @return
	 */
	
	public String getElevatorid(String column) {
		String name = "";
		Session hs = null;
		try {

			hs = HibernateUtil.getSession();
			String sql = "select a.elevatorId from SaleElevatorContractDetail a,SaleLfttecParm b,ElevatorCode c where c.billNo=b.billNO and c.parmRowId= b.parmRowId and b.billNO=a.billNO and b.detailRowId=a.detailRowId and c.elevatorNo='"+column+"' ";
	
			Query query = hs.createQuery(sql);
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object Name = (Object) it.next();
				name = (String) Name;
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (DataStoreException e1) {
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil:Hibernate Session close error");
			}
		}
		return name;
	}
	
	
	

	public String getName(String tablename, String column1, String column2,
			String value2) {
		String name = "";
		Session hs = null;
		try {

			hs = HibernateUtil.getSession();

			String sql = "select a." + column1 + " from " + tablename
					+ " as a where a." + column2 + " = '" + value2 + "'";			
			Query query = hs.createQuery(sql);
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object Name = (Object) it.next();
				name = (String) Name;
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (DataStoreException e1) {
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil:Hibernate Session close error");
			}
		}
		return name;
	}
	
	public String getName_Sql(String tablename, String column1, String column2,
			String value2) {
		String name = "";
		Session hs = null;
		try {

			hs = HibernateUtil.getSession();

			String sql = "select a." + column1 + " from " + tablename
					+ " as a where a." + column2 + " = '" + value2 + "'";			
			Query query = hs.createSQLQuery(sql);
			Iterator it = query.list().iterator();
			while (it.hasNext()) {
				Object Name = (Object) it.next();
				name = (String) Name;
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (DataStoreException e1) {
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil:Hibernate Session close error");
			}
		}
		return name;
	}
	
	/**
	 * ʹ�õ�ǰ�����ݿ����Ӳ�ʹ���������SQL����ѯ��
	 * @param hs
	 * @param tablename
	 * @param column1
	 * @param column2
	 * @param value2
	 * @return
	 */
	public String getName(Session hs,String tablename, String column1, String column2,String value2) {
		String name = "";
		try {
			String sql = "select a." + column1 + ",a." + column1 + " from " + tablename
					+ " as a where a." + column2 + " = '" + value2 + "' order by a."+column1;	

			List relist=hs.createSQLQuery(sql).list();
			if(relist!=null && relist.size()>0){
				for(int i=0;i<relist.size();i++){
					Object[] obj=(Object[])relist.get(i);
					name=obj[0]+"";
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return name;
	}
	/***************************************************************************
	 * 
	 * @param tablename
	 * @param column1
	 * @param column2
	 * @param value2
	 * @param where��������
	 * @return
	 */
	public String getName(String tablename, String column1, String column2,
			String value2, String[] where) {
		String name = "";
		Session hs = null;
		try {

			hs = HibernateUtil.getSession();

			String sql = "select a." + column1 + " from " + tablename
					+ " as a where a." + column2 + " = '" + value2 + "'";
			if (where.length > 0) {
				for (int i = 0; i < where.length; i++) {
					sql += " and " + where[i];
				}
			}

			Query query = hs.createQuery(sql);
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object Name = (Object) it.next();
				name = (String) Name;
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (DataStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil:Hibernate Session close error");
			}
		}
		return name;
	}

	/**
	 * Get the AreaList from Salearea table;
	 * 
	 * @param enabledflag
	 * @return
	 */

	public java.util.List getAreaList(String enabledflag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Area a where a.enabledflag = :enabledflag");
			query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the RoleList from role table;
	 * 
	 * @param enabledflag
	 * @return
	 */

	public java.util.List getRoleList(String enabledflag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Role a where a.enabledflag = :enabledflag order by roleid");
			query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ProvinceList from Province table; ʡ����������
	 * 
	 * @return
	 */

	public java.util.List getProvinceList() {
		Session session = null;
		List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Province a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the AreaList from ElevatorFlag table;
	 * 
	 * @param enabledflag
	 * @return
	 */

	public java.util.List getElevatorFlagList(String enabledflag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Elevatorflag a where a.enabledflag = :enabledflag");
			query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the AreaList from ElevatorFunc table;
	 * 
	 * @param enabledflag
	 * @return
	 */

	public java.util.List getElevatorFuncList(String enabledflag, String flag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Elevatorfunc a where a.enabledflag = :enabledflag and a.flag = :flag");
			query.setString("enabledflag", enabledflag);
			query.setString("flag", flag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the AreaList from ElevatorType table;
	 * 
	 * @param enabledflag
	 * @return
	 */

	public java.util.List getElevatorTypeList(String enabledflag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Elevatortype a where a.enabledflag = :enabledflag");
			query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the FactioryList from Factory table;
	 * 
	 * @param enabledflag
	 * @return
	 */
	public java.util.List getFactoryList(String enabledflag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Factory a where a.enabledflag = :enabledflag");
			query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the UnitTypeList from UnitType table;
	 * 
	 * @param enabledflag
	 * @return
	 */
	public java.util.List getUnitTypeList(String enabledflag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Unittype a where a.enabledflag = :enabledflag");
			query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the UnitTypeList from UnitType table;
	 * 
	 * @param enabledflag
	 * @return
	 */
	public java.util.List getUnitTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Unittype a");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCustomerTypeList from ViewCustomerType view;
	 * 
	 * @return
	 */
	public java.util.List getViewCustomerTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcustomertype");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCustomerTypeList from ViewCustomerType view;��ȥ�������
	 * 
	 * @return
	 */
	public java.util.List getViewCustomerTypeList1() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Viewcustomertype where typeid !='1'");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCustomerLevelList from ViewCustomerLevel view;
	 * 
	 * @return
	 */
	public java.util.List getViewCustomerLevelList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcustomerlevel");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCreditLevelList from ViewCreditLevel view;
	 * 
	 * @return
	 */
	public java.util.List getViewCreditLevelList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcreditlevel");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewAreaRefList from ViewAreaRef view;
	 * 
	 * @return
	 */
	public java.util.List getViewAreaRefList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewarearef");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCompanyTypeList from ViewCompanyType view;
	 * 
	 * @return
	 */
	public java.util.List getViewCompnayTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcompanytype");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewStorageRefList from ViewStorageRef table;
	 * 
	 * @return
	 */
	public java.util.List getViewStorageRefList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewstorageref");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the CompanyList from Company table;
	 * 
	 * @return
	 */
	public java.util.List getCompanyList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Company order by comId");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}
	/**
	 * Get the Class1idList from class1id table;
	 * 
	 * @return
	 */
	public java.util.List getClass1idList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Class1 a where a.enabledflag = 'Y'");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the StorageIDList from Storageid table;
	 * 
	 * @return
	 */
	public java.util.List getStorageIDList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Storageid a where a.enabledflag = 'Y' order by storageid");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}
	
	/**
	 * Get the StorageIDList from Storageid table;
	 * 
	 * @return
	 */
	public java.util.List getStorageIDList(String comid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		if(comid != null && !comid.trim().equals("")){
					
			try {
				session = HibernateUtil.getSession();
				Query query = session.createQuery("from Storageid a where a.enabledflag = 'Y' and a.comid = '" + comid+"'");
				result = (ArrayList) query.list();
	
			} catch (DataStoreException dex) {
				log.error(dex.getMessage());
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
			} finally {
				try {
					session.close();
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
				}
			}
		
		}
		return result;
	}

	/**
	 * Get the ViewDutyList from Viewduty view;
	 * 
	 * @return
	 */
	public java.util.List getViewDutyList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewduty");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewAllowFlagList from Viewallowflag view;
	 * 
	 * @return
	 */
	public java.util.List getViewAllowFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewallowflag");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewUserAllowFlagList from Viewuserallowflag view;
	 * 
	 * @return
	 */
	public java.util.List getViewUserAllowFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewuserallowflag");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewItemStatusList from Viewitemstatus view;
	 * 
	 * @return
	 */
	public java.util.List getViewItemStatusList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewitemstatus");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewItemModelList from ViewItemModel view;
	 * 
	 * @return
	 */
	public java.util.List getViewItemModelList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewitemmodel");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewProcFlagList from ViewProcFlag view;
	 * 
	 * @return
	 */
	public java.util.List getViewProcFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewprocflag");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * ������ҵ����Ա��ҵ����Ա����ͼviewuseroperation��ѡ�����
	 * 
	 */

	public java.util.List getViewUserOperationList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewuseroperation");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * ������ĳ��¼�û���Ͻ��ҵ����Ա��ѯ������
	 * 
	 * ҵ��������԰����Ͻ��ҵ����Ա¼�뱨�������飬��ͬ��
	 * 
	 * @param userid:��¼�û�ID
	 * @return
	 */

	public java.util.List getViewUserOperationRefList(String userid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			// lzy  �������
			query = session.createQuery("from Viewuseroperationref a where a.userref = :userid and a.enabledflag='Y' order by username asc");
			query.setString("userid", userid);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}
	
	
	
	/**
	 * ���������õ�ҵ����Ա��ѯ������
	
	 * @return
	 */

	public java.util.List getSalesman() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			//lzy �������
			query = session.createQuery("from Loginuser a where a.roleid = 'A02' and a.enabledflag='Y' order by username asc ");
			//query.setString("userid", userid);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}
	
	

	/**
	 * Get the ViewCustomerCorpList from Viewcustomercorp view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewCustomerCorpList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcustomercorp");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewViewItemAmtSourceList from Viewitemamtsource view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewItemAmtSourceList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewitemamtsource");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewViewItemSourceList from Viewitemsource view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewItemSourceList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewitemsource");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewViewItemKindList from Viewitemkind view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewItemKindList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewitemkind");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the LineItemInfoListt from LineItemInfo table; ��������ģ��������Ŀ������������
	 * 
	 * @return
	 */

	public java.util.List getLineItemInfoList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Lineiteminfo a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the LineDiscretionListt from LineDiscretion table; ��������ģ�����ߴ��������������
	 * 
	 * @return
	 */

	public java.util.List getLineDiscretionList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Linediscretion a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the LineDiscretionListt from LineDignify table; ��������ģ�����߹���ԭ����������
	 * 
	 * @return
	 */

	public java.util.List getLineDignifyList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Linedignify a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewViewThreeFlagList from Viewthreeflag view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewThreeFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewthreeflag");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewViewFinishFlagList from Viewfinishflag view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewFinishFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewfinishflag");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewDelFlagList from Viewdelflag view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewDelFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewdelflag");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewStorageLineDetail from Viewstoragelinedetail view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewStorageLineDetail() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewstoragelinedetail");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * Get the ViewStorageType from Viewstoragetype view;
	 * 
	 * @param userid
	 * @return
	 */

	public java.util.List getViewStorageType() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewstoragetype");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * judge if the loginuser is operator or not.
	 * 
	 * @param tablename
	 * @param column1
	 * @param column2
	 * @param value2
	 * @return
	 */

	public boolean JudgeOperator(String loginid) {
		boolean flag = false;
		Session hs = null;
		try {

			hs = HibernateUtil.getSession();

			String sql = "select a from Viewuseroperation as a where a.userid = :userid ";
			Query query = hs.createQuery(sql);
			query.setString("userid", loginid);
			List list = query.list();
			if (list.size() > 0) {
				flag = true;
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (DataStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil:Hibernate Session close error");
			}
		}
		return flag;
	}

	/**
	 * Get the from Viewcheckflagbill view; auother: zhi function: salecontract
	 * checkstatus
	 * 
	 * @return
	 */

	public java.util.List getCheckStatusList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcheckflagbill");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the from ViewCustomerType view; auother: zhi function: search all
	 * customer :custtype
	 * 
	 * @return
	 */

	public java.util.List getCustTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcustomertype");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ViewDotFlagList from Viewdotflag view;
	 * 
	 * @return
	 */

	public java.util.List getViewDotFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewdotflag");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the from QuotationFunction table; auother: zhi function:
	 * Ϊ�˱���������¼��ģ�����ÿ���ͺŵķǱ깦��ѡ�����
	 * 
	 * 
	 * @return
	 */

	public java.util.List getFunctionRequest(String elevatorid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			// ��ѯ�Ǳ�׼��ѡ����Ҫ��
			query = session
					.createQuery("from Viewelevatorfuncall where elevatorid = :elevatorid and flag='0' and standardflag='1'");
			query.setString("elevatorid", elevatorid);
			result = query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the from QuotationFunction table; auother: zhi
	 * function:��Ϊ��ʵ���ڱ���������¼��ģ���аѷǱ��װ��ѡ�����
	 * 
	 * 
	 * @return
	 */

	public java.util.List getAdornRequest(String elevatorid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			// ��ѯ�Ǳ�׼������װ��
			query = session
					.createQuery("from Viewelevatorfuncall where elevatorid = :elevatorid and flag='1' and standardflag='1' order by parttypeid");
			query.setString("elevatorid", elevatorid);
			result = query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ViewdemandList from Viewdemand view;
	 * 
	 * @return
	 */

	public java.util.List getViewdemandList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewdemand");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ViewenvoyList from Viewenvoy view;
	 * 
	 * @return
	 */

	public java.util.List getViewenvoyList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewenvoy");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ViewfireList from Viewfire view;
	 * 
	 * @return
	 */

	public java.util.List getViewfireList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewfire");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ViewuserconList from Viewusercon view;
	 * 
	 * @return
	 */

	public java.util.List getViewuserconList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewusercon");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get max from specify table.
	 * 
	 * @param tablename
	 * @param column1
	 * @param column2
	 * @param value2
	 * @return
	 */

	public String getMaxVersionNO(String tablename, String column1) {
		String name = "";
		Session hs = null;
		try {

			hs = HibernateUtil.getSession();

			String sql = "select max(a." + column1 + ") from " + tablename
					+ "'";
			Query query = hs.createQuery(sql);
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object Name = (Object) it.next();
				name = (String) Name;
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (DataStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil:Hibernate Session close error");
			}
		}
		return name;
	}

	/**
	 * Get the ViewfunctionList from Viewfunction view;
	 * 
	 * @return
	 */

	public java.util.List getViewfunctionList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewfunction");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}
	
	public java.util.List getViewbusinesstypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from ViewBusinessType");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}
	
	/**
	 * Get the ViewfunctionList1 from Viewfunction view;
	 * 
	 * @return
	 */

	public java.util.List getViewfunctionList1() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewfunction where FlagID=0 OR FlagID=2 OR FlagID=3");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewfunctionList from Viewfunction view;
	 * 
	 * @return
	 */

	public java.util.List getViewstandardflagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewstandardflag");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * ��ȡ���ݷ����б� ��������Ʒ������� ���أ���ѯ�ó���list
	 */

	public java.util.List getElevatorTypeListByPara(String flagid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Elevatortype a where a.enabledflag = 'Y' and a.flagid like :flagid");
			query.setString("flagid", flagid);
     
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * ��ȡ���ݼ��������б� �����������ͺ� ���أ���ѯ�ó���list
	 * ���ڣ�����������¼���ʼ��ʱ�ӵ��ݼ������������ҳ���Ӧ�Ĳ�����Ȼ�󱣴���session�У�
	 */

	public java.util.List getElevatorTechList(String elevatorid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		java.util.List midList = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			String sql="select a.czrs,a.dcgd,a.dksd,a.jdk,a.jds,a.jfwz,a.jfk," +
					"a.jfs,a.jfg,a.jxmk,a.jxms,a.jxwk,a.jxws,a.jxnk,a.jxns,a.dcwz," +
					"a.ptjs,a.qdfs,a.zzbz,a.yxfs,a.dyyw,a.rem," +
					"a.zzl,a.sdid,a.QJId,a.TjkId,a.OpenTypeId," +
					"a.jdg,a.relong,a.mopboard,a.apron,a.exim,a.arm,a.ladder,a.host,a.ElevatorID " +
					" from ViewAllTechPara a where a.elevatorid = '"+elevatorid+"'";

			midList = session.createSQLQuery(sql).list();
			 if(midList.size()>0){
				 for(int i=0;i<midList.size();i++){
					 Object[] obj = (Object[])midList.get(i);
					 HashMap hm = new HashMap();
					 
					 hm.put("zzid",obj[22]==null?"":obj[22].toString());
					 hm.put("zzl",this.getName("ElevatorLoad","loadKg","zzId",obj[22]==null?"":obj[22].toString()));//����
					 hm.put("sdid",obj[23]==null?"":obj[23].toString());
					 hm.put("speed",this.getName("ElevatorSpeed","speed","sdId",obj[23]==null?"":obj[23].toString()));//�ٶ�
					 hm.put("qjid",obj[24]==null?"":obj[24].toString());
					 hm.put("fwlc",this.getName("ElevatorObliquity","obliquity","qjid",obj[24]==null?"":obj[24].toString()));//���
					 hm.put("tjkid",obj[25]==null?"":obj[25].toString());
					 hm.put("kjkd",this.getName("ElevatorStep","tjk","tjkId",obj[25]==null?"":obj[25].toString()));//�ݼ����
					 hm.put("opentypeid",obj[26]==null?"":obj[26].toString());
					 hm.put("kmfs",this.getName("ElevatorOpenType","openTypeName","openTypeId",obj[26]==null?"":obj[26].toString()));//���ŷ�ʽ
					 
					 hm.put("czrs",obj[0]==null||obj[0].toString().trim().equals("")?"0":obj[0].toString());
					 hm.put("dcgd",obj[1]==null||obj[1].toString().trim().equals("")?"0":obj[1].toString());
					 hm.put("dksd",obj[2]==null||obj[2].toString().trim().equals("")?"0":obj[2].toString());
					 hm.put("jdk",obj[3]==null||obj[3].toString().trim().equals("")?"0":obj[3].toString());
					 hm.put("jds",obj[4]==null||obj[4].toString().trim().equals("")?"0":obj[4].toString());
					 hm.put("jfwz",obj[5]==null||obj[5].toString().trim().equals("")?"":obj[5].toString());
					 hm.put("jfk",obj[6]==null||obj[6].toString().trim().equals("")?"0":obj[6].toString());
					 hm.put("jfs",obj[7]==null||obj[7].toString().trim().equals("")?"0":obj[7].toString());
					 hm.put("jfg",obj[8]==null||obj[8].toString().trim().equals("")?"0":obj[8].toString());
					 hm.put("jxmk",obj[9]==null||obj[9].toString().trim().equals("")?"0":obj[9].toString());
					 hm.put("jxms",obj[10]==null||obj[10].toString().trim().equals("")?"0":obj[10].toString());
					 hm.put("jxwk",obj[11]==null||obj[11].toString().trim().equals("")?"0":obj[11].toString());
					 hm.put("jxws",obj[12]==null||obj[12].toString().trim().equals("")?"0":obj[12].toString());
					 hm.put("jxnk",obj[13]==null||obj[13].toString().trim().equals("")?"0":obj[13].toString());
					 hm.put("jxns",obj[14]==null||obj[14].toString().trim().equals("")?"0":obj[14].toString());
					 hm.put("dcwz",obj[15]==null||obj[15].toString().trim().equals("")?"0":obj[15].toString());
					 hm.put("ptjs",obj[16]==null||obj[16].toString().trim().equals("")?"0":obj[16].toString());
					 hm.put("qdfs",obj[17]==null||obj[17].toString().trim().equals("")?"":obj[17].toString());
					 hm.put("zzbz",obj[18]==null||obj[18].toString().trim().equals("")?"":obj[18].toString());
					 hm.put("yxfs",obj[19]==null||obj[19].toString().trim().equals("")?"":obj[19].toString());
					 hm.put("dyyw",obj[20]==null||obj[20].toString().trim().equals("")?"":obj[20].toString());
					 hm.put("rem",obj[21]==null||obj[21].toString().trim().equals("")?"":obj[21].toString());
					 
					 hm.put("jdg",obj[27]==null||obj[27].toString().trim().equals("")?"0":obj[27].toString());
					 hm.put("relong",obj[28]==null||obj[28].toString().trim().equals("")?"0":obj[28].toString());
					 hm.put("mopboard",obj[29]==null||obj[29].toString().trim().equals("")?"":obj[29].toString());
					 hm.put("apron",obj[30]==null||obj[30].toString().trim().equals("")?"":obj[30].toString());
					 hm.put("exim",obj[31]==null||obj[31].toString().trim().equals("")?"":obj[31].toString());
					 hm.put("arm",obj[32]==null||obj[32].toString().trim().equals("")?"":obj[32].toString());
					 hm.put("ladder",obj[33]==null||obj[33].toString().trim().equals("")?"":obj[33].toString());
					 hm.put("host",obj[34]==null||obj[34].toString().trim().equals("")?"":obj[34].toString());
					 hm.put("elevatorid",obj[35]==null||obj[35].toString().trim().equals("")?"":obj[35].toString());
					 
					 hm.put("function1","");
					 hm.put("function2","");
					 hm.put("step","");
					 hm.put("control","");
					 hm.put("servicefloor","");
					 hm.put("contype","");
					 hm.put("opentype","");//������ʽ
					 result.add(hm);
				 }
			 }

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * ��ȡ���Ʒ�ʽ�б� ��������������(contype) ���أ���ѯ�ó���list ���ڣ�����������¼��ʱ��ʼ�����Ʒ�ʽ�������б�
	 */

	public java.util.List getConTypeList(String contype) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Viewcontype where ConType = :ConType");
			query.setString("ConType", contype);
			result = query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * ��ȡ���Ʒ�ʽ�б� �������� ���أ���ѯ�ó���list ���ڣ�����������¼��ʱ��ʼ�����Ʒ�ʽ�������б�
	 */

	public java.util.List getConTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcontype");

			result = query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * ��ȡ���ݻ���LIST ���أ���ѯ�ó���list ���ڣ�������¼��ʱ�ѻ��۴���ҳ�棬�������
	 * 
	 * @param elevatorid
	 * @param floor
	 * @param stage
	 * @param door
	 * @param high
	 * @return
	 */
	public Float getElevatorBasePriceList(String elevatorid, Integer floor,
			Integer stage, Integer door, Float high) {
		Session session = null;

		Float unitprice = new Float(0);

		try {
			session = HibernateUtil.getSession();
			Query query;
			String sql = "select a.unitprice from Elevatorprice as a where a.id.elevatorid='"
					+ elevatorid
					+ "' and a.id.floor='"
					+ floor.intValue()
					+ "' and a.id.high='" + high + "'";
			// query = session.createQuery("select a.unitprice from
			// Elevatorprice as a where a.id.elevatorid=:elevatorid and
			// a.id.floor=:floor and a.id.high=:high");
			// query.setString("elevatorid",elevatorid);
			// query.setInteger("floor",floor.intValue());
			// query.setInteger("stage",stage.intValue());
			// query.setInteger("door",door.intValue());
			// query.setFloat("high",high.floatValue());
			query = session.createQuery(sql);

			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object Name = (Object) it.next();
				unitprice = (Float) Name;
			}

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return unitprice;

	}

	/**
	 * ���������б�ֱ�ݡ����ݵȣ� ��������������
	 * 
	 * 
	 * ���أ���ѯ�ó���list
	 */

	public java.util.List getElevatorFlagID() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Elevatorflag a where a.enabledflag = 'Y'");

			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}
	/**
	 * ���������б�ֱ�ݡ����ݵȣ� ��������������
	 * 
	 * lijun add 20100528
	 * ���أ���ѯ�ó���list
	 */

	public java.util.List getElevatorFlagID2() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Elevatorflag a where a.enabledflag = 'Y' and flagid not in('CK')");

			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}
	/**
	 * Get the ViewFeeTypeInfoRefList from ViewFeeTypeInfoRef view;
	 * 
	 * @return
	 */
	public java.util.List getViewFeeTypeInfoRefList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewfeetypeinforef");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the from QuotationFunction table; auother: zhi function:
	 * Ϊ�˱���������¼��ģ�����ÿ���ͺŵı�׼����ѡ�����
	 * 
	 * 
	 * @return
	 */

	public java.util.List getFunctionStandard(String elevatorid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			// ��ѯ��׼��ѡ����Ҫ��
			query = session
					.createQuery("from Viewelevatorfuncall where elevatorid = :elevatorid and flag='0' and standardflag='0' order by r2");
			query.setString("elevatorid", elevatorid);
			result = query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the from QuotationFunction table; auother: zhi
	 * function:��Ϊ��ʵ���ڱ���������¼��ģ���аѱ�׼��װ��ѡ�����
	 * 
	 * 
	 * @return
	 */

	public java.util.List getAdornRequestStandard(String elevatorid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			// ��ѯ�Ǳ�׼������װ��
			query = session
					.createQuery("from Viewelevatorfuncall where elevatorid = :elevatorid and flag='1' and standardflag='0' order by parttypeid");
			query.setString("elevatorid", elevatorid);
			result = query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ViewGetMonthList from Viewgetmonthlist view;
	 * 
	 * @return
	 */
	public java.util.List getViewgetmonthList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewgetmonthlist");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCheckFlagSaleContractList from Viewcheckflagsalecontract
	 * view;
	 * 
	 * @return
	 */
	public java.util.List getViewCheckFlagSaleContractList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcheckflagsalecontract");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the FeeTypeInfoList from Feetypeinfo table; ���۹���ģ�����������������
	 * 
	 * @return
	 */

	public java.util.List getFeeTypeInfoList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Feetypeinfo a where a.enabledflag = 'Y' and a.feeidref !='0'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Ŀ�ģ�Get the quflag from ViewQuFlag for the operation of quoteconnec;
	 * ����������¼��ʱ����ʼ���������־��LIST��0Ϊ��ʽ���ۣ�1Ϊ���ⱨ��
	 * 
	 * @author:zhi
	 */
	public java.util.List getQuFlagList() {
		List list = null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			Query query = hs.createQuery("from Viewquflag");
			list = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
			}
		}
		return list;
	}

	/**
	 * Get the ViewPayFlagList from Viewpayflag view;
	 * 
	 * @return
	 */
	public java.util.List getViewPayFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Viewpayflag where payflagid != '0'");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewcontypeList from Viewcortroltype view;
	 * 
	 * @return
	 */
	public java.util.List getViewcConTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcontype");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCortrolTypeList from Viewcortroltype view;
	 * 
	 * @return Viewcontype
	 */
	public java.util.List getViewCortrolTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcortroltype");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the RankWeekList from Rankweek table;
	 * 
	 * @return
	 */
	public java.util.List getRankWeekList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Rankweek");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCheckFlagList from Viewcheckflag view;
	 * 
	 * @return
	 */
	public java.util.List getViewCheckFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcheckflag");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCheckStatusList from Viewcheckstatus view;
	 * 
	 * @return
	 */
	public java.util.List getViewCheckStatusList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcheckstatus");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * ��ȡ������������ϸ���е����� ����1��String billno ����2��String elevatorid ����3��int
	 * elevatorrowid
	 * 
	 * @return List
	 */
	public java.util.List getQuoteBookDetailList(String billno,
			String elevatorid, int elevatorrowid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("select a from Quotebookdetail as a where a.id.billno=:billno and a.id.elevatorid=:elevatorid and a.id.elevatorrowid =:elevatorrowid");
			query.setString("billno", billno);
			query.setString("elevatorid", elevatorid);
			query.setInteger("elevatorrowid", elevatorrowid);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * ��ȡ������������ϸ��1��װ�κ͹����е����� ����1��String billno ����2��String elevatorid ����3��int
	 * elevatorrowid
	 * 
	 * @return List
	 */
	public java.util.List getQuoteBookDetailList1(String billno,
			String elevatorid, String funcid, int elevatorrowid,
			String parttypeid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {

			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("select a from Quotebookdetail1 as a where a.id.billno=:billno and a.id.elevatorid=:elevatorid and a.id.funid =:funcid and a.id.elevatorrowid =:elevatorrowid and a.id.parttypeid = :parttypeid");
			query.setString("billno", billno);
			query.setString("elevatorid", elevatorid);
			query.setString("funcid", funcid);
			query.setInteger("elevatorrowid", elevatorrowid);
			query.setString("parttypeid", parttypeid);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * ��ȡ���������鼼���������е����� ����1��String billno ����2��String elevatorid ����3��int
	 * detailid
	 * 
	 * @return List
	 */
	public java.util.List getQuoteBookLfttecParm(String billno,
			String elevatorid, int elevatorrowid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {

			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("select a from QuoteBookLfttecParm as a where a.billNo=:billno and  a.detailId=:detailid");
			query.setString("billno", billno);
			query.setInteger("detailid", elevatorrowid);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewBillFlagList from Viewbillflag view;
	 * 
	 * @return
	 */
	public java.util.List getViewBillFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewbillflag");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * ͨ�������ͺŻ�ȡ�ͺŶ�Ӧ�ĵ��ݴ���͵������֣�������ģ������WORD�ĵ�ʱҪ�����������ɲ�ͬ�ı�� ������String elevatorid
	 * 
	 * @return List
	 */
	public java.util.List getElevatorTypeFlagList(String elevatorid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {

			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("select a from ViewElevatorAllInfo as a where a.id.elevatorId = :elevatorid");
			query.setString("elevatorid", elevatorid);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}
	
	
	/**
	 * ͨ�������ͺŻ�ȡ�ͺŶ�Ӧ�ĵ��ݴ���͵������֣�������ģ������WORD�ĵ�ʱҪ�����������ɲ�ͬ�ı�� ������String elevatorid
	 * 
	 * @return List
	 */
	public java.util.List getQuotationsPrintList(String detailid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {

			session = HibernateUtil.getSession();
			Query query;
			
			String sql="select a from ViewElevatorAllInfo a,QuoteBookDetail b " +
					"where a.id.elevatorId=b.elevatorid and b.detailId="+detailid;
			query = session.createQuery(sql);
			//query.setString("billno", detailid);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * ��ȡ�Ƿ��������ɹ�������ı�־
	 * 
	 * @return
	 */
	public java.util.List getViewQuoteBookExistsFlagList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewquotebookexistsflag");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the RankWeekList from Cartype table;
	 * 
	 * @return
	 */
	public java.util.List getCarTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Cartype");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewCreateTypeList from Viewcreatetype view;
	 * 
	 * @return
	 */
	public java.util.List getViewCreateTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcreatetype");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * Get the ViewBriTypeList from Viewbritype view;
	 * 
	 * @return
	 */
	public java.util.List getViewBriTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewbritype");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * 
	 */
	public java.util.List getPOList(String poName) {
		String po = poName;
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from " + po);
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex, "HibernateUtil Hibernate DataStoreException");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil Hibernate Session HibernateException");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil Hibernate HibernateException ");
			}
		}

		return result;
	}

	/**
	 * 
	 * @param billno:��ˮ�ŵ�ǰ���
	 * @return
	 */
	public java.util.List getQuotanoList(String billno) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Quotationmaster as a where a.billno like :billno ");
			// query.setString("enabledflag", enabledflag);
			query.setString("billno", billno);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * ��ȡ�����ݼ��������б� ��������ʶ ���أ���ѯ�ó���list ���ڣ�������session�У�
	 */
	public List getViewAllDragList(String flag) {
		Session session = null;
		java.util.List result = new ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query = null;

			query = session
					.createQuery("from Viewalldraglist as a where a.id.flag =:flag ");
			// query.setString("id",id);
			query.setString("flag", flag);
			result = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * ��ȡ ĳ���������б���Ҫ���ڼ��������ͺ�ͬ¼��ʱ�� ��������Hibernate���� ���أ���ѯ�ó���list ���ڣ�������session�У�
	 */
	public List getAllTableList(String obj) {
		Session session = null;
		java.util.List result = new ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query = null;
			query = session.createQuery("from " + obj
					+ " as a where a.enabledflag = 'Y' ");
			// query.setString("id",id);
			// query.setString("flag",flag);
			result = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}
	
	
	public List AllTableZhzListF(String obj) {
		Session session = null;
		java.util.List result = new ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query = null;
			query = session.createQuery("from " + obj
					+ " as a where a.enabledflag = 'Y' and a.flagid='F' ");
			// query.setString("id",id);
			// query.setString("flag",flag);
			result = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}
	
	
	
	
	public List AllTableZhzListT(String obj) {
		Session session = null;
		java.util.List result = new ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query = null;
			query = session.createQuery("from " + obj
					+ " as a where a.enabledflag = 'Y' and a.flagid='T' ");
			// query.setString("id",id);
			// query.setString("flag",flag);
			result = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}



	/*
	 * ��ȡ˫������ͼĳ��ֵ parm:
	 * tablenameΪĳ��ͼHibernate����column1ΪҪ��ȡ����ֵ,column2Ϊ������1��column3Ϊ������2,value2
	 * ����1ֵ��value3����2ֵ
	 * 
	 * create by cwr
	 */
	public String getViewColumnName(String tablename, String column1,
			String column2, String column3, String value2, String value3) {
		String name = "";
		Session hs = null;
		try {

			hs = HibernateUtil.getSession();

			String sql = "select a." + column1 + " from " + tablename
					+ " as a where a.id." + column2 + " = '" + value2
					+ "' and a.id." + column3 + " = '" + value3 + "'";
			Query query = hs.createQuery(sql);
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object Name = (Object) it.next();
				name = (String) Name;
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (DataStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil:Hibernate Session close error");
			}
		}
		return name;
	}

	/**
	 * Get the ViewsalelfttecparmcheckList from Viewsalelfttecparmcheck view;
	 * 
	 * @return
	 */
	public java.util.List getViewsalelfttecparmcheckList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewsalelfttecparmcheck");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	/**
	 * ȡ�õ�ǰ���� ��ʽ"yyyy-MM-dd"
	 */
	public String getNowDate() {
		SimpleDateFormat simpleFormatter;
		String date;
		simpleFormatter = new SimpleDateFormat("yyyy-MM-dd");
		date = simpleFormatter.format(new Date());
		return date;
	}
	/**
	 * ȡ�õ�ǰ����  ��ʽ"yyyy-MM-dd HH:mm:ss"
	 */
	public String getNowDateTime() {
		SimpleDateFormat simpleFormatter;
		String date;
		simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		date = simpleFormatter.format(new Date());
		return date;
	}

	public String getFirstDate() {
		SimpleDateFormat simpleFormatter;
		String date;
		simpleFormatter = new SimpleDateFormat("yyyy-MM-01");
		date = simpleFormatter.format(new Date());
		return date;
	}

	/**
	 * ������������
	 * 
	 * @return
	 */

	public java.util.List getCoinInfoList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Coininfo a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * Get the ViewcontracttypeList from Viewcontracttype view;
	 * 
	 * @return
	 */
	public java.util.List getViewcontracttypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewcontracttype");
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}

	public java.util.List getColorList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Color a where a.enabledflag = 'Y'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * ��ȡ����ģ���б�; create by cwr
	 * 
	 * @return
	 */

	public java.util.List getViewDotModelList(String procflag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewdotflag where procflag ='"
					+ procflag + "'");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * ��ȡ��ͬ�޸�Э���������б�; create by cwr
	 * 
	 * @return
	 */

	public java.util.List getViewAgreemenTypetList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewagreementtype");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}

	/**
	 * ��ȡ���ݷǱ�����б�; create by cwr
	 * 
	 * @return
	 */

	public java.util.List getViewStandardTypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Viewstandardtype");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/***************************************************************************
	 * ���������֣���ȡ�������͡�
	 * 
	 * @return
	 */
	public List getSparepartOperType() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Opertype as a where a.flag='Y'");
			result = (ArrayList) query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/***************************************************************************
	 * ���������֣���ȡ�ֿ�����
	 * 
	 * @return
	 */
	public List getSparepartStorageSetup(HttpServletRequest request) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		try {
			session = HibernateUtil.getSession();

			String userid = this.getOperid(request);
			Query query;
			query = session
					.createQuery("select distinct b from Operstorageref as a,Storagesetup as b where a.storageid=b.storageid and a.operid='"
							+ userid + "'");
			result = (ArrayList) query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/***************************************************************************
	 * ���������֣���ȡ������������
	 * 
	 * @return
	 */
	public List getMaterielClassType() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Materielclass as a where a.flag='Y'");
			result = (ArrayList) query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/**
	 * ���������֣���ȡ���ʵ�λ
	 * 
	 * @return
	 */
	public List getPurveyModulus() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Purveymodulus as a where a.flag='Y'");
			result = (ArrayList) query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/***************************************************************************
	 * ���������֣���ȡ���ʵ�λ
	 * 
	 * @return
	 */
	public List getGoodsUnit() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Goodsunit as a where a.enableflag='Y'");
			result = (ArrayList) query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/***************************************************************************
	 * ���������֣��ɹ���ʽ
	 * 
	 * @return
	 */
	public List getStockMode() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Stockmode as a where a.flag='Y'");
			result = (ArrayList) query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/***************************************************************************
	 * ��ѯ���й��̲��Ĳ��š��ñ���Ѳ����Ѿ�д���ˡ�
	 * 
	 * @return
	 */
	public List getStorageIdForSparepart() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Storageid as a where a.parentstorageid='10009'");
			result = (ArrayList) query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	/***************************************************************************
	 * ���ڲ�ѯ������ʽ��Ҳ��Ϊ�������͡�
	 * 
	 * @return ���ز�ѯlist
	 */
	public List getSendMaterielMode() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session
					.createQuery("from Sendmaterielmode as a where a.flag='Y'");
			result = (ArrayList) query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}

	public List getMaterials(String style) {
		List list = null;
		Session hs = null;
		String _style = style;

		try {
			hs = HibernateUtil.getSession();
			// ��ѯ�������,�������ơ�
			// billno

			Query query = null;
			if ("0".equals(_style)) {
				query = hs
						.createQuery("from Materielclass a where a.flag='Y' and classid='0' ");
			} else if ("m-1".equals(_style)) {
				query = hs
						.createQuery("from Materielclass a where a.flag = 'Y'");
			} else {
				String sql = "select a from Goodsinfo a where a.enabledflag='Y' and a.procsort='"
						+ _style + "'";
				query = hs.createQuery(sql);
			}
			list = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				System.out
						.println("Exception:Action.BaseDataImpl >> getSpectypeList");
			}
		}
		return list;
	}
	/**
	 * ȡ�������ĵ�ǰʱ��
	 * 
	 * @return hh:mm:ss ��ʽ��ʱ��
	 */
	public String getTodayTime() {
		String time = new Timestamp(System.currentTimeMillis()).toString();
		return time.substring(11, 19);
	}
	/**
	 * ȡ���״̬�б�
	 * @param ViewName
	 * @return
	 */
	public java.util.List getCommonCheckView(String ViewName,String Language,String flag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			String sqllist="select ListID,ListName FROM "+ViewName.trim()
				+" where Language='"+Language.trim()+"' and Flag='"+flag.trim()+"'";
			List list=session.createSQLQuery(sqllist).list();
			for(int i=0;i<list.size();i++){
				Object[] obj=(Object[])list.get(i);
				HashMap map=new HashMap();
				map.put("listid",obj[0].toString());
				map.put("listname",obj[1].toString());
				result.add(map);
			}

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}
	/**
	 * ����ȡ��ͼ
	 * @param ViewName
	 * @return
	 */
	public java.util.List getCommonView(String ViewName) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from "+ViewName);
			// query.setString("enabledflag", enabledflag);
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}
	/**
	 * ��ȡ��ֱͬ���û��б�; create by ljb
	 * 2008-03-21
	 * @return
	 */

	public java.util.List getEleCustomerList(String billno) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Directcustomerdetail a where a.id.billno='"+billno+"' ");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}
	public java.util.List getEleCustomerList2(String billno) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from Directcustomerdetail2 a where a.id.billno='"+billno+"' ");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return result;
	}
	/**
	 * ���ݵ����ͺż���������ȡ���߼�; create by cwy
	 * 2008-12-31
	 * @return cgprice
	 */

	public float getElevatorCgPrice(String elevatorid,float cgzzl) {
		Session session = null;
		Connection con = null;
		PreparedStatement stat = null;
		ResultSet rs = null;
		String sql = "";
		float cgprice = 0;//���߼�Ǯ
		try {
			int cgzzl2=(int)cgzzl;
			session = HibernateUtil.getSession(); 
			con = session.connection();
			sql = "select * from viewcgprice where elevatorid = '"+elevatorid+"' and zzid = '"+cgzzl2+"'";
			stat = con.prepareStatement(sql);
			rs = stat.executeQuery();
			while(rs.next())
			{
				cgprice = rs.getFloat("cgprice");
			}

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(session != null)session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		return cgprice;
	}
	/**���ݱ�����ѯ��������;
	 * 2010-5-21
	 * @return result
	 */
	public List getAllTableList2(String obj) {
		Session session = null;
		java.util.List result = new ArrayList();
		try {
			session = HibernateUtil.getSession();
			Query query = null;
			query = session.createQuery("from " + obj
					+ " as a where a.enabledFlag = 'Y' ");
			result = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}
	public String getViewConType(String tablename, String column1,
			String column2, String value2) {
		String name = "";
		Session hs = null;
		try {

			hs = HibernateUtil.getSession();
			String sql = "select a." + column1 + " from " + tablename
					+ " as a where a." + column2 + " = '" + value2 + "'";
			Query query = hs.createQuery(sql);
			Iterator it = query.iterate();
			while (it.hasNext()) {
				Object Name = (Object) it.next();
				name = (String) Name;
			}
		} catch (HibernateException e) {
			e.printStackTrace();

		} catch (DataStoreException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,
						"HibernateUtil:Hibernate Session close error");
			}
		}
		return name;
	}
	/**
	 * ��ȡ��ͬЭ����Ĳ���LIST
	 * @return
	 */
	public List getViewOperSelect(){
		List relist=null;
		Session hs = null;
		try {
			hs = HibernateUtil.getSession();
			String sql = "select operselectid,operselectname from ViewOperSelect where disflag='Y' order by orderno "; 
			List slist=hs.createSQLQuery(sql).list();
			if(slist!=null && slist.size()>0){
				relist=new ArrayList();
				HashMap map=null;
				for(int i=0;i<slist.size();i++){
					Object[] obj=(Object[])slist.get(i);
					map=new HashMap();
					map.put("operselectid", obj[0]);
					map.put("operselectname", obj[1]);
					relist.add(map);
				}
			}
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (DataStoreException e1) {
			e1.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
			}
		}
		return relist;
	}
	/**
	 * ��ȡ��Ӧ����ѡ����
	 * @return
	 */
	public List getEngChooseByflagId(String elevatorid){
		Session hs = null;
		List result = new ArrayList();
		String flagid="";
		try {
			hs = HibernateUtil.getSession();
			hs = HibernateUtil.getSession();
			String sql = "select distinct flagid from ViewElevatorAllInfo where elevatorid='"+elevatorid+"'";
			ResultSet rs = hs.connection().createStatement().executeQuery(sql);
			while(rs.next()){
				flagid=rs.getString("flagid");
			}
			Query query = hs.createQuery("from EngChooseFunc where flagid = :flagid order by flagid,efuncid");
			query.setString("flagid", flagid);
			result = query.list();
		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}
		
		return result;
	}
	/**
	 * Ϊ�˱���������¼��ģ�����ÿ���ͺŵķǱ깦��ѡ�����
	 * @param elevatorid
	 * @param flag ���ࣺflag 0 --����Ҫ�� 1- װ��Ҫ��,2 -����Ҫ��,3 �ߴ�Ҫ�� 
	 * @param standardflag 0��ʾ��׼��1��ʾ�Ǳ�  
	 * @return
	 */
	public java.util.List getFunctionRequest(String elevatorid,String flag,String standardflag) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			// ��ѯ�Ǳ�׼��ѡ����Ҫ��
//			query = session.createQuery("from Viewelevatorfuncall where elevatorid = :elevatorid and flag=:flag and standardflag=:standardflag");
//			query.setString("elevatorid", elevatorid);
//			query.setString("flag", flag);
//			query.setString("standardflag", standardflag);
//			result = query.list();
			
			String sql="select a from Viewelevatorfuncall a " +
					"where a.elevatorid in('"+elevatorid+"') " +
					"and a.flag in('0','1','2','3') and a.standardflag in('0','1') order by a.r2,a.parttypeid";
			result=session.createQuery(sql).list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}
	
	public  String  getId(Object obj ,String id){
		String sql="select  Max("+id+")   from " +obj.getClass().getName();
		Session hs=null;
		String id3=null;
		try {
			 hs=(Session) HibernateUtil.getSession();
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query query=hs.createQuery(sql);
		List list=query.list();
	if(list!=null&&list.size()>0){
			id=(String) list.get(0);
			if(id!=null){
				int id2=Integer.valueOf(id);
				id2++;
				id3=String.format("%03d", id2);
			}
			else{
				  String  id1="0";
				  int id2=Integer.valueOf(id1);
				  id2++;
				  id3=String.format("%03d", id2);
			}
		}
//			//System.out.print(id3);		
			return id3;
	}
	
	public java.util.List getOftypeList() {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			query = session.createQuery("from OtherFaithTypeInfo");
			result = (ArrayList) query.list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;
	}
	
public  String  getId1(Object obj ,String id){
		
		String sql="select  "+id+"  from " +obj.getClass().getName();
		Session hs=null;
		String id4;
		try {
			 hs=(Session) HibernateUtil.getSession();
		} catch (DataStoreException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Query query=hs.createQuery(sql);
		List list=query.list();
	
	if(list!=null&&list.size()>0){
		List list1 = new ArrayList<Integer>();
		for(int i=0;i<list.size();i++){
		 	id=((String) list.get(i)).substring(1,4);
		 	int id1=Integer.valueOf(id);
		 	list1.add(id1);
		}
	
		String maxid=Collections.max(list1).toString();
		
		int id2=Integer.valueOf(maxid);
		id2++;
		String id3=String.format("%03d", id2);
		id4="M"+id3;
	}
	else{
		   String  id1="0";
		   int id2=Integer.valueOf(id1);
			id2++;
			String id3=String.format("%03d", id2);
			id4=id4="M"+id3;
		   
	 }
//			//System.out.print(id4);		
			return id4;
	}

public  String  getId2(Object obj ,String id){
	
	String sql="select  "+id+"  from " +obj.getClass().getName();
	Session hs=null;
	String id4;
	try {
		 hs=(Session) HibernateUtil.getSession();
	} catch (DataStoreException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	Query query=hs.createQuery(sql);
	List list=query.list();

if(list!=null&&list.size()>0){
	List list1 = new ArrayList<Integer>();
	for(int i=0;i<list.size();i++){
	 	id=((String) list.get(i)).substring(1,4);
	 	int id1=Integer.valueOf(id);
	 	list1.add(id1);
	}

	String maxid=Collections.max(list1).toString();
	
	int id2=Integer.valueOf(maxid);
	id2++;
	String id3=String.format("%03d", id2);
	id4="E"+id3;
}
else{
	   String  id1="0";
	   int id2=Integer.valueOf(id1);
		id2++;
		String id3=String.format("%03d", id2);
		id4=id4="E"+id3;
	   
 }
//		//System.out.print(id4);		
		return id4;
}

//��ȡ�����ͺ�
public java.util.List getlsmList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from LitterSideModeInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ����ڰ����װ���ͺ�
public java.util.List getlsmdList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from LitterSideComDeckInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ���
public List getdeepList(){
	List relist=null;
	Session hs = null;
	try {
		hs = HibernateUtil.getSession();
		String sql = "select DeepTypeId, DeepTypeName from ViewEleParmDeepType where EnabledFlag='Y' "; 
		List slist=hs.createSQLQuery(sql).list();
		if(slist!=null && slist.size()>0){
			relist=new ArrayList();
			HashMap map=null;
			for(int i=0;i<slist.size();i++){
				Object[] obj=(Object[])slist.get(i);
				map=new HashMap();
				map.put("DeepTypeId", obj[0]);
				map.put("DeepTypeName", obj[1]);
				relist.add(map);
			}
		}
	} catch (HibernateException e) {
		e.printStackTrace();
	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
		}
	}
	return relist;
}

//��ȡ��ڲ��Ų���
public java.util.List getmacList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from BoxWallFloorDoorMacInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ��ڲ���װ��
public java.util.List getdeckList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from BoxWallFloorDoorDeckInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ�ζ�(����)�ͺ�
public java.util.List getlittertopmodeList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from LitterTopModeInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ����ذ�װ��
public java.util.List getbrifloordeckList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from BriFloorDeckInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ�����ͺ�
public java.util.List gethandrailmodeList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from HandrailMode  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ����λ��
public List getpositionList(){
	List relist=null;
	Session hs = null;
	try {
		hs = HibernateUtil.getSession();
		String sql = "select HandPosId,HandPosName from ViewEleParmHandPos where EnabledFlag='Y' "; 
		List slist=hs.createSQLQuery(sql).list();
		if(slist!=null && slist.size()>0){
			relist=new ArrayList();
			HashMap map=null;
			for(int i=0;i<slist.size();i++){
				Object[] obj=(Object[])slist.get(i);
				map=new HashMap();
				map.put("HandPosId", obj[0]);
				map.put("HandPosName", obj[1]);
				relist.add(map);
			}
		}
	} catch (HibernateException e) {
		e.printStackTrace();
	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
		}
	}
	return relist;
}

//��ȡ��������
public List getopendoortypeList(){
	List relist=null;
	Session hs = null;
	try {
		hs = HibernateUtil.getSession();
		String sql = "select DoorCoverTypeId,DoorCoverTypeName from ViewEleParmDoorCoverType where EnabledFlag='Y' "; 
		List slist=hs.createSQLQuery(sql).list();
		if(slist!=null && slist.size()>0){
			relist=new ArrayList();
			HashMap map=null;
			for(int i=0;i<slist.size();i++){
				Object[] obj=(Object[])slist.get(i);
				map=new HashMap();
				map.put("DoorCoverTypeId", obj[0]);
				map.put("DoorCoverTypeName", obj[1]);
				relist.add(map);
			}
		}
	} catch (HibernateException e) {
		e.printStackTrace();
	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
		}
	}
	return relist;
}

//��ȡ���������
public java.util.List getcontrolboxcodeList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from ControlBoxCodeInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ�䰴ť���
public java.util.List getboxbuttonsernoList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from BoxButtonSerNoInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ����������ͺ�
public java.util.List getcontrolboxfacemodeList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from ControlBoxFaceModeInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ�����俪���ӳ�
public List getdoorprolongList(){
	List relist=null;
	Session hs = null;
	try {
		hs = HibernateUtil.getSession();
		String sql = "select BoxDoorLongId,BoxDoorLongName from ViewEleParmBoxDoorLong where EnabledFlag='Y' "; 
		List slist=hs.createSQLQuery(sql).list();
		if(slist!=null && slist.size()>0){
			relist=new ArrayList();
			HashMap map=null;
			for(int i=0;i<slist.size();i++){
				Object[] obj=(Object[])slist.get(i);
				map=new HashMap();
				map.put("BoxDoorLongId", obj[0]);
				map.put("BoxDoorLongName", obj[1]);
				relist.add(map);
			}
		}
	} catch (HibernateException e) {
		e.printStackTrace();
	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
		}
	}
	return relist;
}

//��ȡ�ٻ������
public java.util.List getbeckonboxcodeList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from BeckonBoxCodeInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

//��ȡ�ٻ���������ʽ
public List getboxtypeList(){
	List relist=null;
	Session hs = null;
	try {
		hs = HibernateUtil.getSession();
		String sql = "select BoxBodyId,BoxBodyName from ViewEleParmBoxBody where EnabledFlag='Y' "; 
		List slist=hs.createSQLQuery(sql).list();
		if(slist!=null && slist.size()>0){
			relist=new ArrayList();
			HashMap map=null;
			for(int i=0;i<slist.size();i++){
				Object[] obj=(Object[])slist.get(i);
				map=new HashMap();
				map.put("BoxBodyId", obj[0]);
				map.put("BoxBodyName", obj[1]);
				relist.add(map);
			}
		}
	} catch (HibernateException e) {
		e.printStackTrace();
	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
		}
	}
	return relist;
}

//��ȡ�ٻ�������ͺ�
public java.util.List getbeckonboxfacemodeList() {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from BeckonBoxFaceModeInfo  where enabledFlag='Y' order by orderNo asc");
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;
}

	/**
	 * ��ȡ���������Ϣ��ҳ����ʾ
	 * @param hs
	 * @param billno
	 * @param detailrowid
	 * @param parmrowid
	 * @return
	 * @since Jan 7, 2014
	 * @author pzc 
	 * @version 1.00 Jan 7, 2014
	 */
	public static Map<String,String> getElevatorNos(Session hs,
			String billno,String detailrowid,String parmrowid){
		String elevatornos="";
		String inernos="";
		String eabledflags="";
		String rems="";
		String codestatuss="";
		String sql="select a.elevatorNo,a.inerNo,a.eabledFlag,a.rem,e.id.codeStatus "+                         
		"from ElevatorCode as a,SaleElevatorContractMaster as b,SaleElevatorContractDetail as c," +
		"SaleLfttecParm as d,Nostatus as e "+
		"where a.billNo = '"+billno+"' "+
		"and c.detailRowId = "+detailrowid+" "+
		"and a.parmRowId = "+parmrowid+" "+
		"and a.eabledFlag = 'Y' "+
		"and a.billNo = b.billNo " +                 
		"and a.parmRowId = d.parmRowId "+                          
		"and b.billNo = c.billNO "+                 
		"and c.detailRowId = d.detailRowId " +
		"and a.inerNo = e.id.inerno " +
		"and e.activeFlag='Y'  "+   
		"order by a.elevatorNo asc";
		Query query=hs.createQuery(sql);
		java.util.List relist = query.list();
		if(relist!=null && relist.size()>0){
			for(int i=0;i<relist.size();i++){
				Object[] obj=(Object[])relist.get(i);
				elevatornos=elevatornos+obj[0]+",";
				inernos=inernos+obj[1]+",";
				eabledflags=eabledflags+obj[2]+",";
				rems=rems+obj[3]+",";
				codestatuss=codestatuss+obj[4]+",";
			}
			elevatornos=elevatornos.substring(0,elevatornos.length()-1);
			inernos=inernos.substring(0,inernos.length()-1);
			eabledflags=eabledflags.substring(0,eabledflags.length()-1);
			rems=rems.substring(0,rems.length()-1);
			codestatuss=codestatuss.substring(0,codestatuss.length()-1);
		}
		Map<String,String> resultstr=new HashMap<String,String>(5);
		resultstr.put("elevatornos",elevatornos);
		resultstr.put("inernos",inernos);
		resultstr.put("eabledflags",eabledflags);
		resultstr.put("rems",rems);
		resultstr.put("codestatuss",codestatuss);
		return resultstr;
	}

	/**
	 * ��ȡ���������Ϣ��ҳ����ʾ
	 * @param hs
	 * @param billno
	 * @param detailrowid
	 * @param parmrowid
	 * @return
	 * @since Jan 7, 2014
	 * @author pzc 
	 * @version 1.00 Jan 7, 2014
	 */
	public static Map<String,String> getElevatorNos2(Session hs,
			String billno,String detailrowid,String parmrowid){
		String elevatornos="";
		String inernos="";
		String eabledflags="";
		String rems="";
		String codestatuss="";
		String sql="select a.elevatorNo,a.inerNo,a.eabledFlag,a.rem,'0' "+                         
		"from ElevatorCode2 as a,SaleElevatorContractMaster2 as b,SaleElevatorContractDetail2 as c," +
		"SaleLfttecParm2 as d "+//,Nostatus as e
		"where a.billNo = '"+billno+"' "+
		"and c.detailRowId = "+detailrowid+" "+
		"and a.parmRowId = "+parmrowid+" "+
		"and a.eabledFlag = 'Y' "+
		"and a.billNo = b.billNo "+                    
		"and a.parmRowId = d.parmRowId "+                          
		"and b.billNo = c.billNO "+                 
		"and c.detailRowId = d.detailRowId " +
		//"and a.inerNo = e.id.inerno " +
		//"and e.activeFlag='Y'  "+   
		"order by a.elevatorNo asc";
		Query query=hs.createQuery(sql);
		java.util.List relist = query.list();
		if(relist!=null && relist.size()>0){
			for(int i=0;i<relist.size();i++){
				Object[] obj=(Object[])relist.get(i);
				elevatornos=elevatornos+obj[0]+",";
				inernos=inernos+obj[1]+",";
				eabledflags=eabledflags+obj[2]+",";
				rems=rems+obj[3]+",";
				codestatuss=codestatuss+obj[4]+",";
			}
			elevatornos=elevatornos.substring(0,elevatornos.length()-1);
			inernos=inernos.substring(0,inernos.length()-1);
			eabledflags=eabledflags.substring(0,eabledflags.length()-1);
			rems=rems.substring(0,rems.length()-1);
			codestatuss=codestatuss.substring(0,codestatuss.length()-1);
		}
		Map<String,String> resultstr=new HashMap<String,String>(5);
		resultstr.put("elevatornos",elevatornos);
		resultstr.put("inernos",inernos);
		resultstr.put("eabledflags",eabledflags);
		resultstr.put("rems",rems);
		resultstr.put("codestatuss",codestatuss);
		return resultstr;
	}
	/**
	 * ��ȡ���������Ϣ��ҳ����ʾ
	 * @param hs
	 * @param billno
	 * @param detailrowid
	 * @param parmrowid
	 * @return
	 * @since Jan 7, 2014
	 * @author pzc 
	 * @version 1.00 Jan 7, 2014
	 */
	public static Map<String,String> getElevatorNos3(Session hs,
			String billno,String detailrowid,String parmrowid){
		String elevatornos="";
		String inernos="";
		String eabledflags="";
		String rems="";
		String codestatuss="";
		String sql="select a.elevatorNo,a.inerNo,a.eabledFlag,a.rem,'0' "+                         
		"from ElevatorCode3 as a,SaleElevatorContractMaster3 as b,SaleElevatorContractDetail3 as c," +
		"SaleLfttecParm3 as d "+
		"where a.billNo = '"+billno+"' "+
		"and c.detailRowId = "+detailrowid+" "+
		"and a.parmRowId = "+parmrowid+" "+
		"and a.eabledFlag = 'Y' "+
		"and a.billNo = b.billNo " +                 
		"and a.parmRowId = d.parmRowId "+                          
		"and b.billNo = c.billNO "+                 
		"and c.detailRowId = d.detailRowId " +
		//"and a.inerNo = e.id.inerno " +
		//"and e.activeFlag='Y'  "+   
		"order by a.elevatorNo asc";
		Query query=hs.createQuery(sql);
		java.util.List relist = query.list();
		if(relist!=null && relist.size()>0){
			for(int i=0;i<relist.size();i++){
				Object[] obj=(Object[])relist.get(i);
				elevatornos=elevatornos+obj[0]+",";
				inernos=inernos+obj[1]+",";
				eabledflags=eabledflags+obj[2]+",";
				rems=rems+obj[3]+",";
				codestatuss=codestatuss+obj[4]+",";
			}
			elevatornos=elevatornos.substring(0,elevatornos.length()-1);
			inernos=inernos.substring(0,inernos.length()-1);
			eabledflags=eabledflags.substring(0,eabledflags.length()-1);
			rems=rems.substring(0,rems.length()-1);
			codestatuss=codestatuss.substring(0,codestatuss.length()-1);
		}
		Map<String,String> resultstr=new HashMap<String,String>(5);
		resultstr.put("elevatornos",elevatornos);
		resultstr.put("inernos",inernos);
		resultstr.put("eabledflags",eabledflags);
		resultstr.put("rems",rems);
		resultstr.put("codestatuss",codestatuss);
		return resultstr;
	}
//��ȡ��Ȩ����
public List getViewPowerTypeList(){
	List relist=null;
	Session hs = null;
	try {
		hs = HibernateUtil.getSession();
		String sql = "select PowerTypeId,PowerTypeName from PowerType"; 
		List slist=hs.createSQLQuery(sql).list();
		if(slist!=null && slist.size()>0){
			relist=new ArrayList();
			HashMap map=null;
			for(int i=0;i<slist.size();i++){
				Object[] obj=(Object[])slist.get(i);
				map=new HashMap();
				map.put("PowerTypeId", obj[0]);
				map.put("PowerTypeName", obj[1]);
				relist.add(map);
			}
		}
	} catch (HibernateException e) {
		e.printStackTrace();
	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
		}
	}
	return relist;
}

//��ȡ��������
public List getViewUnLevelList(){
	List relist=null;
	Session hs = null;
	try {
		hs = HibernateUtil.getSession();
		String sql = "select UnLevelId,UnLevelName from View_UnLevel"; 
		List slist=hs.createSQLQuery(sql).list();
		if(slist!=null && slist.size()>0){
			relist=new ArrayList();
			HashMap map=null;
			for(int i=0;i<slist.size();i++){
				Object[] obj=(Object[])slist.get(i);
				map=new HashMap();
				map.put("UnLevelId", obj[0]);
				map.put("UnLevelName", obj[1]);
				relist.add(map);
			}
		}
	} catch (HibernateException e) {
		e.printStackTrace();
	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
		}
	}
	return relist;
}
//��ȡ�������
public List getViewEngUnitType(String flag){
	List relist=null;
	Session hs = null;
	try {
		hs = HibernateUtil.getSession();
		String sql = "select typeid,typename from ViewEngUnitType where enabledflag ='Y' and flag= '"+flag+"' "; 
		List slist=hs.createSQLQuery(sql).list();
		if(slist!=null && slist.size()>0){
			relist=new ArrayList();
			HashMap map=null;
			for(int i=0;i<slist.size();i++){
				Object[] obj=(Object[])slist.get(i);
				map=new HashMap();
				map.put("typeid", obj[0]);
				map.put("typename", obj[1]);
				relist.add(map);
			}
		}
	} catch (HibernateException e) {
		e.printStackTrace();
	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,"HibernateUtil:Hibernate Session close error");
		}
	}
	return relist;
}
public String getSQLName(String tablename, String column1, String column2,
		String value2,String column3,String value3) {
	String name = "";
	Session hs = null;
	try {

		hs = HibernateUtil.getSession();

		String sql = "select a." + column1 + " from " + tablename
				+ " as a where a." + column2 + " = '" + value2 + "' and a."+column3+" = '"+value3+"' ";			
		Query query = hs.createSQLQuery(sql);
		Iterator it = query.list().iterator();
		while (it.hasNext()) {
			Object Name = (Object) it.next();
			name = (String) Name;
		}
	} catch (HibernateException e) {
		e.printStackTrace();

	} catch (DataStoreException e1) {
		e1.printStackTrace();
	} finally {
		try {
			hs.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil:Hibernate Session close error");
		}
	}
	return name;
}

	/**
	 * �����������Ƿ�����������
	 * @param hs
	 * @param typeid ��������
	 * @return Y:�ǣ�N����
	 */
	public String getCheckTypid(Session hs,String typeid){
		String restr="N";		
		try{
			//����ͼ���������ʹ��ͬһģ��
			String sql="select * from ViewElevatorType where typeid='"+typeid+"' and EnabledFlag='Y'";
			List relist=hs.createSQLQuery(sql).list();
			if(relist!=null && relist.size()>0){
				restr="Y";
			}
		} catch (Exception e) {
			e.printStackTrace();

		} 
		return restr;
	}
	/**
	 * Get the from QuotationFunction table; auother: zhi function:
	 * Ϊ�˱���������¼��ģ�����ÿ���ͺŵķǱ깦��ѡ�����
	 * ���ñ�־Ϊ���Ҳ���
	 * @author cb
	 * @return
	 */

	public java.util.List getFunctionRequestnew22(String elevatorid) {
		Session session = null;
		java.util.List result = new java.util.ArrayList();

		try {
			session = HibernateUtil.getSession();
			Query query;
			// ��ѯ�Ǳ�׼��ѡ����Ҫ��
			//String sql="select * from  ViewElevatorFuncAll_new22 where ElevatorID = '"+elevatorid+"' and Flag='0' and StandardFlag='1'";
			String sql="select * from  ViewElevatorFuncAll_New where ElevatorID = '"+elevatorid+"' and Flag='0' and StandardFlag='1'";
			result = session.createSQLQuery(sql).list();

		} catch (DataStoreException dex) {
			log.error(dex.getMessage());
			DebugUtil.print(dex,
					"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex,
					"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
				DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
			}
		}

		return result;

	}
	





/**
 * Ϊ�˱���������¼��ģ�����ÿ���ͺŵķǱ깦��ѡ�����
 * ���뱨�������ñ�־���õ�
 * @param elevatorid
 * @param flag ���ࣺflag 0 --����Ҫ�� 1- װ��Ҫ��,2 -����Ҫ��,3 �ߴ�Ҫ�� 
 * @param standardflag 0��ʾ��׼��1��ʾ�Ǳ�  
 * @author cb
 * @return
 */
public java.util.List getFunctionRequest23(String elevatorid) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		// ��ѯ�Ǳ�׼��ѡ����Ҫ��
//		query = session.createSQLQuery("select * from ViewElevatorFuncAll23 where ElevatorID = :elevatorid and Flag=:flag and StandardFlag=:standardflag order by r2,PartTypeID");
//		query.setString("elevatorid", elevatorid);
//		query.setString("flag", flag);
//		query.setString("standardflag", standardflag);
//		result = query.list();
		
		String sqlsql="select funcid,funcname,parttypeid,typeid,flag,standardflag" +
				" from ViewElevatorFuncAll23 " +
				" where ElevatorID ='"+elevatorid+"' and Flag in('0','1','2','3') " +
				" and StandardFlag in('0','1') order by r2,PartTypeID";
		result=session.createSQLQuery(sqlsql).list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;

}

public java.util.List getBaseDataList(String refType) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		String sqlsql="select a " +
				" from BaseDataList a" +
				" where refType ='"+refType+"' and enabledFlag='Y'";
		result=session.createQuery(sqlsql).list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
		DebugUtil.print(dex,
				"HibernateUtil?1?7?1?7Hibernate l?1?7?1?7 ?1?7?1?7?");
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
		DebugUtil.print(hex,
				"HibernateUtil?1?7?1?7Hibernate Session ?1?7?1?9?");
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
			DebugUtil.print(hex, "HibernateUtil?1?7?1?7Hibernate Session ");
		}
	}

	return result;

}


/**
 * ȡ�ù����б�
 * @param enabledflag
 * @return
 */
public java.util.List getCountryList(String enabledflag) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from Country c where c.enabledFlag = :enabledflag");
		query.setString("enabledflag", enabledflag);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

/**
 * �õ�ʡ���б�
 * @param enabledflag
 * @return
 */
public java.util.List getProvinceList(String enabledflag) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session
				.createQuery("from Province p where p.enabledFlag = :enabledflag");
		query.setString("enabledflag", enabledflag);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

/**
 * �õ������б�
 * @param enabledflag
 * @return
 */
public java.util.List getCityList(String enabledflag) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from City c where c.enabledFlag = :enabledflag");
		query.setString("enabledflag", enabledflag);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

/**
 * �õ������б�
 * @param enabledflag
 * @param provinceId
 * @return
 */
public java.util.List getRegionList(String enabledflag, String cityId) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();
	
	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from Region r where r.enabledFlag = :enabledflag and r.cityId = :cityId");
		query.setString("enabledflag", enabledflag);
		query.setString("cityId", cityId);
		result = (ArrayList) query.list();
		
	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}
	
	return result;
}

/**
 * ���ɹ���
 * @return
 */
public static synchronized String genCountryNum(){//create the ConsultMaster number

	// ����˳��Ų���
	List<Country> list = null;
	// ����˳��Ų���
	Session session = null;
	String numEnd = "";
	String hql = "FROM Country order by countryId desc";
	try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		list = query.list();

		if (list == null || list.isEmpty() || list.size() == 0) {
			// ��û�м�¼�����кŴ�001��ʼ
			numEnd = "001";
		} else {
			// ����м�¼,ȥ���µļ�¼,Ȼ��+1,����Ҫ���Զ���ǰ�õ�0
			String currentNo = ((Country) list.get(0)).getCountryId();
			numEnd = String.format("%03d",Integer.parseInt(currentNo) + 1);
		}

	}catch (Exception e) {
		e.printStackTrace();
	}
	return numEnd;
}

/**
 * ����ʡ��
 * @return
 */
public static synchronized String genProvinceNum(String countryId){//create the ConsultMaster number
	

	// ����˳��Ų���
	List<Province> list = null;
	// ����˳��Ų���
	Session session = null;
	String numEnd = "";
	String hql = "FROM Province as province WHERE province.provinceId like '"+ countryId + "%' order by provinceId desc";
	try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		list = query.list();

		if (list == null || list.isEmpty() || list.size() == 0) {
			// ��û�м�¼�����кŴ�001��ʼ
			numEnd = "01";
		} else {
			// ����м�¼,ȥ���µļ�¼,Ȼ��+1,����Ҫ���Զ���ǰ�õ�0
			String currentNo = ((Province) list.get(0)).getProvinceId();
			currentNo = currentNo.replace(countryId, "");
			numEnd = String.format("%02d",Integer.parseInt(currentNo) + 1);
		}

	}catch (Exception e) {
		e.printStackTrace();
	}
	return countryId + numEnd;
}
/**
 * ���ɳ���
 * @return
 */
public static synchronized String genCityNum(String provinceId){//create the ConsultMaster number
	
	
	// ����˳��Ų���
	List<City> list = null;
	// ����˳��Ų���
	Session session = null;
	String numEnd = "";
	String hql = "FROM City as city WHERE city.cityId like '"+ provinceId + "%' order by cityId desc";
	try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		list = query.list();
		
		if (list == null || list.isEmpty() || list.size() == 0) {
			// ��û�м�¼�����кŴ�001��ʼ
			numEnd = "001";
		} else {
			// ����м�¼,ȥ���µļ�¼,Ȼ��+1,����Ҫ���Զ���ǰ�õ�0
			String currentNo = ((City) list.get(0)).getCityId();
			currentNo = currentNo.replace(provinceId, "");
			numEnd = String.format("%03d",Integer.parseInt(currentNo) + 1);
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return provinceId + numEnd;
}

/**
 * ��������
 * @return
 */
public static synchronized String genRegionNum(String cityId){//create the ConsultMaster number
	
	// ����˳��Ų���
	List<Region> list = null;
	// ����˳��Ų���
	Session session = null;
	String numEnd = "";
	String hql = "FROM Region as region WHERE region.regionId like '"+ cityId + "%' order by regionId desc";
	try {
		session = HibernateUtil.getSession();
		Query query = session.createQuery(hql);
		list = query.list();
		
		if (list == null || list.isEmpty() || list.size() == 0) {
			// ��û�м�¼�����кŴ�001��ʼ
			numEnd = "001";
		} else {
			// ����м�¼,ȥ���µļ�¼,Ȼ��+1,����Ҫ���Զ���ǰ�õ�0
			String currentNo = ((Region) list.get(0)).getRegionId();
			currentNo = currentNo.replace(cityId, "");
			numEnd = String.format("%03d",Integer.parseInt(currentNo) + 1);
		}
		
	}catch (Exception e) {
		e.printStackTrace();
	}
	return cityId + numEnd;
}

/**
 * �õ������б�
 * @param enabledflag
 * @param id
 * @return
 */
public java.util.List getCountryList(String enabledflag, String id) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session
				.createQuery("from Country c where c.enabledflag = :enabledflag and c.countryId = :countryId");
		query.setString("enabledflag", enabledflag);
		query.setString("countryId", id);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

/**
 * �õ�ʡ���б�
 * @param enabledflag
 * @param countryId
 * @return
 */
public java.util.List getProvinceList(String enabledflag, String countryId) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session
				.createQuery("from Province p where p.enabledflag = :enabledflag and p.country = :countryId");
		query.setString("enabledflag", enabledflag);
		query.setString("countryId", countryId);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

/**
 * �õ������б�
 * @param enabledflag
 * @param provinceId
 * @return
 */
public java.util.List getCityList(String enabledflag, String provinceId) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from City c where c.enabledflag = :enabledflag and c.province = :provinceId");
		query.setString("enabledflag", enabledflag);
		query.setString("provinceId", provinceId);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}
/**
 * ����������б� ���е�
 * ͨ�����²���ȡֵ�� 
 * id.pullid ѡ��ID
 * pullname ѡ������  
 * @param typeflag �������������� 
 * @return
 */
public java.util.List getPullDownAllList(String typeflag) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from Pulldown p where p.enabledflag like :enabledflag and p.id.typeflag = :typeflag order by p.orderby");
		query.setString("enabledflag", "Y");
		query.setString("typeflag", typeflag);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

/**
 * ����������б� ���õ�
 * ͨ�����²���ȡֵ�� 
 * id.pullid ѡ��ID
 * pullname ѡ������  
 * @param typeflag �������������� 
 * @return
 */
public java.util.List getPullDownList(String typeflag) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		Query query;
		query = session.createQuery("from Pulldown p where p.enabledflag like :enabledflag and p.id.typeflag = :typeflag order by p.orderby");
		query.setString("enabledflag", "Y");
		query.setString("typeflag", typeflag);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (Exception hex) {
		log.error(hex.getMessage());
		hex.printStackTrace();
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

/**
 * �����������б���Ԫ�ص�id��ȡname
 * @param id
 * @param pullDownList �����б�
 * @return
 */
public String getOptionName(String id, List pullDownList) {
	String name = "";
	for (Object obj : pullDownList) {
		Pulldown pulldown = (Pulldown) obj;
		if(id !=null && !id.equals("") && id.equals(pulldown.getId().getPullid())){
			name = pulldown.getPullname();
			break;
		}	
	}	
	return name;
}

/**
 * �����������б���Ԫ�ص�name��ȡid
 * @param name
 * @param pullDownList �����б�
 * @return
 */
public String getOptionId(String name, List pullDownList) {
	String id = "";
	for (Object obj : pullDownList) {
		Pulldown pulldown = (Pulldown) obj;
		if(name !=null && !name.equals("") && name.equals(pulldown.getPullname())){
			id = pulldown.getId().getPullid();
			break;
		}	
	}	
	return id;
}

/**
 * ����ά���ֲ���ȡ����ά��վ�б� 
 * storageid,storagename
 * @param comid �ֲ����
 * @return
 */
public java.util.List getMaintStationList(String comid) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		String hql = "select a from Storageid a,Company b" +
				" where a.comid = b.comid" +
				" and a.parentstorageid = 0" +
				" and a.storagetype = 1" +
				" and a.enabledflag = 'Y'";
		if(!"00".equals(comid)){
			hql += " and b.comid = '"+comid+"'";
		}
		hql+=" order by a.storageid";
		
		Query query = session.createQuery(hql);
		result = (ArrayList) query.list();

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}
/**
 * ����ά���ֲ���ȡ����ά��վ�б� 
 * storageid,storagename
 * @param comid �ֲ����
 * @return
 */
public java.util.List getMaintStationList(ViewLoginUserInfo userinfo) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		
		//����Ƿ���� ��A03  ά������ά��վ��Ա A48, ��װά������  068 ��  ֻ�ܿ��Լ�ά��վ���������
		String sqlss="select * from view_mainstation where roleid='"+userinfo.getRoleID()+"'";
		List vmlist=session.createSQLQuery(sqlss).list();

		String hql = "select a from Storageid a,Company b" +
				" where a.comid = b.comid" +
				" and a.parentstorageid = 0" +
				" and a.storagetype = 1" +
				" and a.enabledflag = 'Y'";
		if(!"00".equals(userinfo.getComID())){
			hql += " and b.comid = '"+userinfo.getComID()+"'";
		}
		//A03  ά������  ֻ�ܿ��Լ�ά��վ����ĺ�ͬ��ά��վ��Ա A48
		if(vmlist!=null && vmlist.size()>0){
			hql += " and a.storageid = '"+userinfo.getStorageId()+"'";
		}
		hql+=" order by a.storageid";
		
		//System.out.println(">>>"+hql);
		Query query = session.createQuery(hql);
		List relist = (ArrayList) query.list();
		
		if(relist!=null && relist.size()>0){
			Storageid sid=null;
			HashMap hmap=null;
			for(int i=0;i<relist.size();i++){
				sid=(Storageid)relist.get(i);
				hmap=new HashMap();
				hmap.put("storageid", sid.getStorageid());
				hmap.put("storagename", sid.getStoragename());
				result.add(hmap);
			}
			
		}
		//A03  ά������  ֻ�ܿ��Լ�ά��վ����ĺ�ͬ��ά��վ��Ա A48
		if(vmlist!=null && vmlist.size()>0){
			
		}else{
			HashMap hmap2=new HashMap();
			hmap2.put("storageid", "%");
			hmap2.put("storagename","��ѡ��");
			result.add(0,hmap2);
		}
		

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}
/**
 * ����ά���ֲ���ȡ����ά��վ�б� 
 * storageid,storagename
 * @param comid �ֲ����
 * @return
 */
public java.util.List getMaintStationList2(ViewLoginUserInfo userinfo,String maintDivision) {
	Session session = null;
	java.util.List result = new java.util.ArrayList();

	try {
		session = HibernateUtil.getSession();
		String hql = "select a from Storageid a,Company b" +
				" where a.comid = b.comid" +
				" and a.parentstorageid = 0" +
				" and a.storagetype = 1" +
				" and a.enabledflag = 'Y'";
		if(!"00".equals(userinfo.getComID()) && !"A02".equals(userinfo.getRoleID()) ){
			hql += " and a.storageid = '"+userinfo.getStorageId()+"'";
		}
		if(maintDivision!=null && !maintDivision.trim().equals("")){
			hql += " and a.comid like '"+maintDivision.trim()+"'";
		}
		hql+=" order by a.storageid";
		
		//System.out.println(">>>"+hql);
		Query query = session.createQuery(hql);
		List relist = (ArrayList) query.list();

		HashMap hmap0=null;
		if("00".equals(userinfo.getComID()) || "00".equals(maintDivision) || "A02".equals(userinfo.getRoleID())){
			hmap0=new HashMap();
			hmap0.put("storageid", "%");
			hmap0.put("storagename", "ȫ��");
			result.add(hmap0);
		}
		
		if(relist!=null && relist.size()>0){
			Storageid sid=null;
			HashMap hmap=null;
			for(int i=0;i<relist.size();i++){
				sid=(Storageid)relist.get(i);
				hmap=new HashMap();
				hmap.put("storageid", sid.getStorageid());
				hmap.put("storagename", sid.getStoragename());
				result.add(hmap);
			}
			
		}
		

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (Exception hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

/**
 * ��ȡ����״̬�������б�
 * @return
 */
public List getProcessStatusList(){
	Session session = null;
	java.util.List list = new java.util.ArrayList();
	java.util.List result = new java.util.ArrayList();
	try {
		session = HibernateUtil.getSession();
		String sql="select typeid,typename from ViewFlowStatus order by orderby ";
		Query query = session.createSQLQuery(sql);
		list = query.list();
		for(int i=0;i<list.size();i++){
			Object[] obj=(Object[])list.get(i);
			HashMap hm=new HashMap();
			hm.put("typeid", String.valueOf(obj[0]));
			hm.put("typename", String.valueOf(obj[1]));
			result.add(hm);
		}

	} catch (DataStoreException dex) {
		log.error(dex.getMessage());
	} catch (HibernateException hex) {
		log.error(hex.getMessage());
	} finally {
		try {
			session.close();
		} catch (HibernateException hex) {
			log.error(hex.getMessage());
		}
	}

	return result;
}

public String numToChinese(int d) {
//  String[] str = { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };
  String[] str = { "��", "һ", "��", "��", "��", "��", "��", "��", "��", "��" };
//  String ss[] = new String[] { "Ԫ", "ʰ", "��", "Ǫ", "��", "ʰ", "��", "Ǫ", "��" };
  String ss[] = new String[] { "��", "ʮ", "��", "ǧ", "��", "ʮ", "��", "ǧ", "��" };
  String s = String.valueOf(d);

  StringBuffer sb = new StringBuffer();
  for (int i = 0; i < s.length(); i++) {
      String index = String.valueOf(s.charAt(i));
      sb = sb.append(str[Integer.parseInt(index)]);
  }
  String sss = String.valueOf(sb);
  int i = 0;
  for (int j = sss.length(); j > 0; j--) {
      sb = sb.insert(j, ss[i++]);
  }
  return sb.toString();
}

	/**
	 * ��ȡ�����׼���۵����ϵ��
	 * @return
	 */
	public HashMap getCoefficientMap(){
		Session hs = null;
		HashMap hmap=new HashMap();
		try {
	
			hs = HibernateUtil.getSession();
			DataOperation op = new DataOperation();
			op.setCon(hs.connection());
			
			//��������ϵ�� 
			String etcfsql="select * from ElevatorTypeCoefficient where EnabledFlag='Y'";
			List etcfList =op.queryToList(etcfsql);
			hmap.put("etcfList", etcfList);
			//ά��վϵ�� ϵ�� 
			String mscfsql="select * from MaintStationCoefficient where EnabledFlag='Y'";
			List mscfList =op.queryToList(mscfsql);
			hmap.put("mscfList", mscfList);
			//����ϵ��
			String msssql="select * from ServiceCoefficient where EnabledFlag='Y'";
			List mssList =op.queryToList(msssql);
			hmap.put("mssList", mssList);
			//���ϵ��
			String cacfsql="select * from ContentApplyCoefficient where EnabledFlag='Y'";
			List cacfList =op.queryToList(cacfsql);
			hmap.put("cacfList", cacfList);
			//���ʽϵ��
			String pmcfsql="select * from PaymentMethodCoefficient where EnabledFlag='Y'";
			List pmcfList =op.queryToList(pmcfsql);
			hmap.put("pmcfList", pmcfList);
			//����ϵ��
			String wfcsql="select * from WeightCoefficient where EnabledFlag='Y'";
			List wfcList =op.queryToList(wfcsql);
			hmap.put("wfcList", wfcList);
			//����ϵ�� 
			String sfcsql="select * from SpeedCoefficient where EnabledFlag='Y'";
			List sfcList =op.queryToList(sfcsql);
			hmap.put("sfcList", sfcList);
			//��ϵ�� 
			String dfcsql="select * from DoorCoefficient where EnabledFlag='Y'";
			List dfcList =op.queryToList(dfcsql);
			hmap.put("dfcList", dfcList);
			//��������ϵ��  
			String eafcsql="select * from ElevatorAgeCoefficient where EnabledFlag='Y'";
			List eafcList =op.queryToList(eafcsql);
			hmap.put("eafcList", eafcList);
			//��ͬ��Ч��ϵ��   
			String cpfcsql="select * from ContractPeriodCoefficient where EnabledFlag='Y'";
			List cpfcList =op.queryToList(cpfcsql);
			hmap.put("cpfcList", cpfcList);
			//̨��ϵ��  
			String tlcfsql="select * from TlCoefficient where EnabledFlag='Y'";
			List tlcfList =op.queryToList(tlcfsql);
			hmap.put("tlcfList", tlcfList);
			
		} catch (Exception dex) {
			dex.printStackTrace();
		} finally {
			try {
				hs.close();
			} catch (HibernateException hex) {
				log.error(hex.getMessage());
			}
		}
		return hmap;
	}
	/**
	 * ά��ģ���ϴ��ļ���Ϣ��
	 * @param hs
	 * @param busTable ҵ�����
	 * @param materSid ҵ��������ˮ��
	 * @return
	 */
	public java.util.List getWbFileInfoList(Session hs,String busTable,String materSid) {
		java.util.List result = new java.util.ArrayList();

		try {
			String filesql="from Wbfileinfo where busTable='"+busTable+"' and materSid='"+materSid+"'";
			result=hs.createQuery(filesql).list();

		} catch (Exception hex) {
			hex.printStackTrace();
		} 

		return result;
	}
	
	public Boolean contractTransferDisplay(ActionForm form, HttpServletRequest request ,ActionErrors errors ,String flag){
		
		HttpSession session = request.getSession();
		ViewLoginUserInfo userInfo = (ViewLoginUserInfo)session.getAttribute(SysConfig.LOGIN_USER_INFO);
		DynaActionForm dform = (DynaActionForm) form;
		
		String id=(String)dform.get("id");
		if(id==null || id.trim().equals("")){
			id = request.getParameter("id");
		}
		
		Session hs = null;
	
		if (id != null && !id.equals("")) {			
			try {
				hs = HibernateUtil.getSession();
				
				String hql = "from ContractTransferMaster where billNo = '"+id.trim()+"' ";
				List list = hs.createQuery(hql).list();
				ContractTransferMaster master = null;
				List ContractTransferFileTypesList = new ArrayList();
				List feedbackList = new ArrayList();
				
				if (list != null && list.size() > 0) {
					
					master = (ContractTransferMaster) list.get(0);
						
					master.setCompanyId(this.getName("Customer", "companyName", "companyId", master.getCompanyId()));
					master.setMaintDivision(this.getName("Company", "comfullname", "comid", master.getMaintDivision()));
					master.setMaintStation(this.getName("Storageid", "storagename", "storageid", master.getMaintStation()));
					master.setOperId(this.getName("Loginuser", "username", "userid", master.getOperId()));
					master.setAuditOperid(this.getName("Loginuser", "username", "userid", master.getAuditOperid()));
					master.setTransfeId(this.getName("Loginuser", "username", "userid", master.getTransfeId()));
					master.setAuditOperid2(this.getName("Loginuser", "username", "userid", master.getAuditOperid2()));
						
					
					String sql = "select a.jnlno,a.FileType,b.pullname from ContractTransferFileTypes a,pulldown b " 
						+" where a.FileType = b.pullid and b.typeflag = 'ContractFileTypes_FileType' and billNo = '"+id.trim()+"' ";
					List fileTypesList = hs.createSQLQuery(sql).list();
					
					for (Object object : fileTypesList) {
						
						Object[] objs = (Object[])object;
						
						HashMap map = new HashMap();
						map.put("jnlno", objs[0]);
						map.put("fileType", objs[1]);
						map.put("fileTypeName", objs[2]);
						
						String fileHql="from ContractTransferFileinfo c where c.jnlno ='"+(String)objs[0]+"' ";
						List fileList=hs.createQuery(fileHql).list();
						if(fileList!=null&&fileList.size()>0){
							map.put("fileList", fileList);
						}
						
						ContractTransferFileTypesList.add(map);
					}
					
					
					String fbhql = "from ContractTransferFeedback where billNo = '"+id.trim()+"' order by operDate desc ";
					List fblist = hs.createQuery(fbhql).list();
					
					if (fblist!=null && fblist.size()>0) {
						for (int i = 0; i < fblist.size(); i++) {
							ContractTransferFeedback feedback = (ContractTransferFeedback) fblist.get(i);
							HashMap map = new HashMap();
							map.put("jnlno", feedback.getJnlno());
							map.put("operId", this.getName("Loginuser", "username", "userid", feedback.getOperId()));
							map.put("operDate", feedback.getOperDate());
							map.put("transferRem", feedback.getTransferRem());
							map.put("feedbacktypeid", this.getName("ContractTransferFeedbackType", "feedbackTypeName", "feedbackTypeId", feedback.getFeedbacktypeid()));
							
							String fileHql="from ContractTransferFeedbackFileinfo c where c.jnlno ='"+feedback.getJnlno()+"' ";
							List fileList=hs.createQuery(fileHql).list();
							if(fileList!=null&&fileList.size()>0){
								map.put("fileList", fileList);
							}
							
							feedbackList.add(map);
						}
					}
							
				} else {
					errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("display.recordnotfounterror"));
				}
				
				request.setAttribute("fileTypes", ContractTransferFileTypesList);
				request.setAttribute("contractTransferMasterBean", master);	
				request.setAttribute("feedbackList", feedbackList);
				request.setAttribute("billNo", id);
				
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (hs!=null) {
						hs.close();
					}
				} catch (HibernateException hex) {
					log.error(hex.getMessage());
					DebugUtil.print(hex, "HibernateUtil Hibernate Session ");
				}
			}
			
		}		
		
		return true;
	}
	
	//����ά����ҵ�ƻ���ı�׼����ʱ��
	public void updateMaintDateTime() {
		Session hs2 = null;
		Transaction tx2 = null;
		try {
			hs2 = HibernateUtil.getSession();		
			tx2 = hs2.beginTransaction();

			//ͬ�������ֲ����������� �����µ���
			String upsql="exec sp_updateMaintDateTime ";
			hs2.connection().prepareCall(upsql).execute();
			
			tx2.commit();
			
		} catch (Exception e) {
			if(tx2!=null){
				tx2.rollback();
			}
			e.printStackTrace();
		}finally {
			if(hs2 != null){
				hs2.close();				
			}				
		}
	}

}
