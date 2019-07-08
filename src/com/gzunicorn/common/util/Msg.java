package com.gzunicorn.common.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Msg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int msgid;
	private String msginfo;
	
	private String JumpURL=null;//����ҪEncoding
	
	private String TargetURL=null;//����ҪEncoding
	
	private HashMap map=new HashMap();
	
	private List MsgTraceList=new ArrayList();
	
	/************************************/
	/**�ɹ�*/
	public static int msg_suc=0;
	/**ʧ��*/
	public static int msg_fal=1;
	/**�쳣*/
	public static int msg_exc=2;
	/**����������*/
	public static int msg_rec=3;
	/**�ɹ�������,��cookie��֤ʱ��*/
	public static int msg_suc_rec=4;
	/************************************/
	
	public Msg(int msgid,String msginfo){
		this.msgid=msgid;
		this.msginfo=msginfo;
		this.appendMsgTrace(msgid+"---"+msginfo);
	}
	
	public void setMsg(int msgid,String msginfo){
		this.msgid=msgid;
		this.msginfo=this.msginfo==null?msginfo:this.msginfo+"\r\n"+msginfo;
		this.appendMsgTrace(msgid+"---"+msginfo);
	}

	public int getMsgid() {
		return msgid;
	}

	public void setMsgid(int msgid) {
		this.msgid = msgid;
	}

	public String getMsginfo() {
		return msginfo;
	}

	public void setMsginfo(String msginfo) {
		this.msginfo = msginfo;
	}
	
	public void setProperty(String key,Object obj){
		map.put(key,obj);
	}
	
	public Object getProperty(String key){
		return map.get(key);
	}

	public String getJumpURL() {
		return JumpURL;
	}

	public void setJumpURL(String jumpURL) {
		JumpURL = jumpURL;
	}

	public String getTargetURL() {
		return TargetURL;
	}

	public void setTargetURL(String targetURL) {
		TargetURL = targetURL;
	}

	public List getMsgTraceList() {
		return MsgTraceList;
	}

	public void appendMsgTraceList(List msgTraceList) {
		if(msgTraceList!=null && msgTraceList.size()>0){
			this.MsgTraceList.addAll(msgTraceList);
		}
	}
	
	private void appendMsgTrace(String msg){
		this.MsgTraceList.add(msg);
	}
	
	public String getHtmlMsgTrace(){
		StringBuffer sb=new StringBuffer();
		if(this.MsgTraceList!=null && this.MsgTraceList.size()>0){
			String temp=null;
			for(int i=0;i<this.MsgTraceList.size();i++){
				temp=(String)this.MsgTraceList.get(i);
				if(temp!=null){
					sb.append(temp).append("<br/>");
				}
			}
		}
		return sb.toString();
	}
	
	public void printMsgTrace(){
		//System.out.print(this.MsgTraceList);
	}
}

