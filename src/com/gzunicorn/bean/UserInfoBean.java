package com.gzunicorn.bean;

import java.io.Serializable;

/* **
 * 
 * Created on 2005-11-21
 * <p>Title: </p>
 * <p>Description: �û���¼Session Bean,�������û���SessionID</p>
 * <p>Copyright: Copyright (c) 2005
 * <p>Company:��������</p>
 * @author wyj
 * @version	1.0
 */
public class UserInfoBean implements Serializable {

	
	private String sessionID;//�ỰID

	private String userID;//�û�ID

	private String userName;//�û���

	private String roleName; //��ɫ

	private String storageName;//��������
	
	private String comName; //�����ֲ�

	private	String loginDate;//��¼����
	
	private String loginTime;////��¼ʱ��
	
	private String ipAddress;//IP��ַ
	
	
	public String getStorageName() {
		return storageName;
	}

	public void setStorageName(String storageName) {
		this.storageName = storageName;
	}
		
	public String getComName() {
		return comName;
	}

	public void setComName(String comName) {
		this.comName = comName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(String loginDate) {
		this.loginDate = loginDate;
	}

	public String getLoginTime() {
		return loginTime;
	}

	public void setLoginTime(String loginTime) {
		this.loginTime = loginTime;
	}

}
