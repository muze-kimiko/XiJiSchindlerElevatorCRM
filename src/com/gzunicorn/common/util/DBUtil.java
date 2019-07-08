/*
 * Created on 2005-7-13
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.util;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * Created on 2005-7-12
 * <p>Title:	ֱ���������ݿ⹫��</p>
 * <p>Description:	û���õ����ӳأ��Թ�������</p>
 * <p>Copyright: Copyright (c) 2005
 * <p>Company:�����Ƽ�</p>
 * @author wyj
 * @version	1.0
 */
public class DBUtil {
    
    /**
     * ���MS SQL SERVER 2000���ݿ��JDBC����
     * @return Connection
     */
    public static Connection getMSSQLConnection(){
        Connection conn = null;
        try{
            Class.forName("com.microsoft.jdbc.sqlserver.SQLServerDriver").newInstance();
            conn = DriverManager.getConnection("jdbc:microsoft:sqlserver://192.168.1.9:1433","iscm","iscmgzunicorn");
        }catch(Exception e){
            DebugUtil.print("�õ�MS SQL SERVER 2000 �ĵ������ӳ���!");
        }
        return conn;
    }
    
    /**
     * ���DB2 8U���ݿ��JDBC����
     * @return Connection
     */
    public static Connection getDB2Connection(){
        Connection conn = null;
        try{
            Class.forName("COM.ibm.db2.jdbc.net.DB2Driver").newInstance();
            conn = DriverManager.getConnection("jdbc:db2://localhost:6789/���ݿ���","�û���","����");
        }catch(Exception e){
            DebugUtil.print("�õ�DB2�ĵ������ӳ���!");
        }
        return conn;
    }
    
    /**
     * ���Oracle9i���ݿ��JDBC����
     * @return Connection
     */
    public static Connection getOracleConnection(){
        Connection conn = null;
        try{
          Class.forName("oracle.jdbc.driver.OracleDriver").newInstance();
          return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:alex", "archive",
              "11");
        }catch(Exception e){
            DebugUtil.print("�õ�Oracle�ĵ������ӳ���!");
        }
        return conn;
      } 
    /**
     * ��ʽ��sql���ַ�
     * @param sqlStr
     * @return
     */
    public static String sqlStrFormat(String sqlStr){
    	String returnStr = sqlStr;
    	if(sqlStr != null){
        	if(sqlStr.equalsIgnoreCase("null")) returnStr="";
        	if(sqlStr.equalsIgnoreCase("'")) returnStr="\"";
    	}else{
    		returnStr = "";
    	}
    	
    	return returnStr;
    }
}
