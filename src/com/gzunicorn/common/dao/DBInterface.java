 
package com.gzunicorn.common.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
 
/**
 * ���ݿ����ͳһ�ӿ�
 * @author FeGe
 *
 */
public interface DBInterface{
	 
	/**
	 * ��ʼ������
	 * @param con
	 * @throws SQLException
	 */
	public void setCon(Connection con) throws SQLException;
	
	/**
	 * �ж��Ƿ�������
	 * @return
	 * @throws SQLException
	 */
	public boolean isConOrClose()throws SQLException;
		
	/**
	 * ȡ����
	 * @return
	 */
	public Connection getConnection();
	//---------��ʼ��������
	//---------�رա���ʼ
	/**
	 *�ر����� 
	 */
	public void closeCon()throws SQLException;
	//---------�رա�����
	
	//---------��������ʼ
	/**
	 * ִ�и��£�����һ��Ӱ�����ݿ�������int
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public int updateToInt(String sql)throws SQLException;
	/**
	 * ִ�в�ѯ������һ��list�Ľ����
	 * �����ڲ��÷�ҳ
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public List queryToList(String sql)throws SQLException;
	/**
	 * ִ�в�ѯ������һ��ָ����������Ӽ�
	 * �������ڷ�ҳ��
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 * @throws SQLException
	 */
	public List queryToList(String sql,int start,int end) throws SQLException;
	/**
	 * ������ִ�д洢���̣�������ʱû��������
	 * @param sql
	 * @return
	 * @throws SQLException
	 */
	public ResultSet queryToResultSet(String sql)throws SQLException;
	/**
	 * ִ�в�ѯ������һ��bean_list
	 * ���÷���
	 * @param sql
	 * @param className  bean ������
	 * @param map������bean �������뷵�ؽ�����������Ķ��ձ�
	 * @return
	 */
	public List queryToBean(String sql,String className,HashMap map)throws Exception;
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
	public List queryToBean(String sql,String className,HashMap map,int start,int end)throws Exception;
	
	
	/**
	 * ���ص�ǰ�����������
	 * @return
	 */
	public int getCount();
	
	
	//---------����������
	
}
