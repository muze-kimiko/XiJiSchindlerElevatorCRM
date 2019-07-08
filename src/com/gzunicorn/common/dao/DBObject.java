
package com.gzunicorn.common.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;


/**
 * implements��DBInterface������Ϊ�����Ժ�ӿ���չ֮�����������õ�����ӿڵ�����һЩ����ʱ�����ý��ӿڵ����з�����ʵ��
 * ����ֱ�Ӽ̳нӿڶ��޸����г���
 * @author rr
 *
 */
public abstract class DBObject implements DBInterface{

//	---------��ʼ������ʼ
	/**
	 * ��ʼ������
	 * @param con
	 * @throws SQLException
	 */
	public abstract void setCon(Connection con) throws SQLException;
	
	/**
	 * �ж��Ƿ�������
	 * @return
	 * @throws SQLException
	 */
	public abstract boolean isCloseCon()throws SQLException;
		
	/**
	 * ȡ����
	 * @return
	 */
	public abstract Connection getConnection();
	
	//---------��ʼ��������
	//---------�رա���ʼ
	/**
	 *�ر����� 
	 * @throws SQLException 
	 */
	public abstract void closeCon()throws SQLException;
	//---------�رա�����
	
	//---------��������ʼ
	/**
	 * ִ�и��£�����һ��Ӱ�����ݿ�������int
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int updateToInt(String sqls)throws SQLException{
		return -1;
	}
	/**
	 * ִ�в�ѯ������һ��list�Ľ����
	 * �����ڲ��÷�ҳ
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List queryToList(String sql)throws SQLException{
		return null;
	}
	/**
	 * ִ�в�ѯ������һ��ָ����������Ӽ�
	 * �������ڷ�ҳ��
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 * @throws SQLException
	 */
	public List queryToList(String sql,int start,int end) throws SQLException{
		return null;
	}
	/**
	 * ������ִ�д洢���̣�������ʱû��������
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet queryToResultSet(String sql)throws SQLException{
		return null;
	}
	/**
	 * ִ�в�ѯ������һ��bean_list
	 * ���÷���
	 * @param sql
	 * @param className  bean ������
	 * @param map������bean �������뷵�ؽ�����������Ķ��ձ�
	 * @return
	 */
	public List queryToBean(String sql,String className,HashMap map)throws Exception{
		return null;
	}

	/**
	 * ִ�в�ѯ������һ��ָ���ӽ������bean_list
	 * ���÷���
	 * @param sql
	 * @param className��bean ������
	 * @param map������bean �������뷵�ؽ�����������Ķ��ձ�
	 * @param start
	 * @param end
	 * @return
	 */
	public List queryToBean(String sql,String className,HashMap map,int start,int end)throws Exception{
		return null;
	}
	
	
	/**
	 * ���ص�ǰ�����������
	 * @return
	 */
	public int getCount(){
		return 0;
	}
	
}
