package com.gzunicorn.common.dataimport;

public class TransferMsgBean {
	public TransferMsgBean(){
	}
	/**
	 * title ����ҳ����ʾʱ���б������
	 */
	private String[] title;
	/**
	 * RSRow ���뼰���յ���ʾ�б�
	 */
	private String[][] RSRow;

	/**
	 * checkMsg ����Ƿ�ͨ��
	 * 	ok ͨ��
	 * 	no ��ͨ��
	 */
	private String checkMsg;
	
	public String getCheckMsg() {
		return checkMsg;
	}
	public void setCheckMsg(String checkMsg) {
		this.checkMsg = checkMsg;
	}
	public String[][] getRSRow() {
		return RSRow;
	}
	public void setRSRow(String[][] row) {
		RSRow = row;
	}
	public String[] getTitle() {
		return title;
	}
	public void setTitle(String[] title) {
		this.title = title;
	}
}
