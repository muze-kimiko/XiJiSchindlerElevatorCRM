package com.gzunicorn.bean;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.hibernate.Session;

import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;

public class ProcessBean implements Serializable{
	
	/**
	 * 1.���л��汾�ţ���ֵ�����٣�Ҳ���ܶ��������Ӱ��ɰ汾�ķ����л���
	 * 2.��Ҫ�޸Ĵ��ֻ࣬�ܼ����Ի򷽷������ܼ���
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * ָ����Ա
	 */
	public static String AppointActor="AppointActors";
	/**
	 * ��ǩ��Ա��
	 */
	public static String SignActor="SignActors";
	
	/**
	 * UserId �������̵Ĳ�����
	 */
	private String userid;
	/**
	 * applyuserid�����̵�������
	 */
	private String applyuserid;
	/**
	 * applydeptid�����̵����벿��
	 */
	private String applydeptid;
	/**
	 * ��������
	 */
	private String processdefine;
	/**
	 * ����������Դ
	 */
	private String table;
	/**
	 * ����������Դ��¼����
	 */
	private String tablekey;
	/**
	 * ��ǰ����ʵ��Id
	 */
	private Long taskid;
	/**
	 * �����̵�����
	 */
	private Long token;
	/**
	 * ��ǰ���������Ľ��Id
	 */
	private Long nodeid;
	/**
	 * ��ǰ���������Ľ�㻷�ڣ�������ƣ�
	 */
	private String nodename;
	/**
	 * node ��־
	 */
	private int flag=0;
	/**
	 * ��Ϣ���͡����ݡ������ߡ����ͷ�ʽ_������
	 */
	private String[][][] msgs=null;
	
	private List decisionmatch=new ArrayList();
	
	
//	private int state;
	
	//�̶�ֵ
	private String selpath="";
	//�Ƚ�ֵ
	private double qtypath=0;
	
	private String approveresult;
	private String approveresultinfo;
	
	private Double price;
	
	/**
	 * ִ��״̬��0:ok,��ʧ��
	 */
	private Integer status;
	
	private String workflag;
	
	private StringBuffer AppointActors=new StringBuffer();
	
	/**
	 * ��ǩ��Ա�飬ÿ����Ա�ö��ŷָ����磺SignActors[0]=abc,bcd  SignActors[1]=efg,hij
	 */
	private String[] SignActors=null;
	/**
	 * ��ǩ��Աѡ�������
	 */
	private String signselpath="";
	
	private List NodeList=new ArrayList();
	/**
	 * �Ƿ��ǩ,Ĭ��false
	 */
	private boolean sign=true;
	/**
	 * ����
	 */
	private int dispatch;
	
	private int feetype;
	
	/**
	 * ��չ����
	 */
	private HashMap extpro=new HashMap();
		
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public boolean isSign() {
		return sign;
	}
	public void setSign(boolean sign) {
		this.sign = sign;
	}

	public int getDispatch() {
		return dispatch;
	}

	public void setDispatch(int dispatch) {
		this.dispatch = dispatch;
	}

	public int getFeetype() {
		return feetype;
	}

	public void setFeetype(int feetype) {
		this.feetype = feetype;
	}
	
	public Object getPro(String key) {
		return this.extpro.get(key);
	}

	public void setPro(String key,Object obj) {
		this.extpro.put(key,obj);
	}
	
	class ProcessNode{
		private long nodeid;
		private String targeturl;
		private String actoruser;
		
		public ProcessNode(long nodeid,String targeturl,String actoruser){
			this.nodeid=nodeid;
			this.actoruser=actoruser;
			this.targeturl=targeturl;
		}
		
		public String getActoruser() {
			return actoruser;
		}
		public void setActoruser(String actoruser) {
			this.actoruser = actoruser;
		}
		public long getNodeid() {
			return nodeid;
		}
		public void setNodeid(long nodeid) {
			this.nodeid = nodeid;
		}
		public String getTargeturl() {
			return targeturl;
		}
		public void setTargeturl(String targeturl) {
			this.targeturl = targeturl;
		}
	}

	public List getNodeList() {
		return NodeList;
	}

	public void addNode(long nodeid,String targeturl,String actoruser) {
		NodeList.add(new ProcessNode(nodeid,targeturl,actoruser));
	}
	
	public ProcessNode getNode(long nodeid){
		if(NodeList!=null && NodeList.size()>0){
			ProcessNode node=null;
			for(int i=0;i<NodeList.size();i++){
				node=(ProcessNode)NodeList.get(i);
				if(node.getNodeid()==nodeid){
					return node;
				}
			}
		}
		return null;
	}

	

	public String[] getAppointActors() {
		return AppointActors.toString().split(",");
	}

	public void addAppointActors(String actors) {
		if(actors ==null || actors.equals("")){
			AppointActors = new StringBuffer();
		}else{
			if(AppointActors.indexOf(actors)==-1){
				if(AppointActors.length()>0){
					AppointActors.append(",").append(actors);
				}else{
					AppointActors.append(actors);
				}
				
			}
		}
	}

	public String getApproveresult() {
		return approveresult;
	}

	public void setApproveresult(String approveresult) {
		this.approveresult = approveresult;
	}

	public String getApproveresultinfo() {
		return approveresultinfo;
	}

	public void setApproveresultinfo(String approveresultinfo) {
		this.approveresultinfo = approveresultinfo;
	}

	public String getProcessdefine() {
		return processdefine;
	}

	public void setProcessdefine(String processdefine) {
		this.processdefine = processdefine;
	}

	public String getTable() {
		return table;
	}

	public void setTable(String table) {
		this.table = table;
	}

	public String getTablekey() {
		return tablekey;
	}

	public void setTablekey(String tablekey) {
		this.tablekey = tablekey;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getApplyuserid() {
		return applyuserid;
	}
	

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getWorkflag() {
		return workflag;
	}

	public void setWorkflag(String workflag) {
		this.workflag = workflag;
	}

	public void setApplyuserid(String applyuserid) {
		this.applyuserid = applyuserid;
	}

	public Long getToken() {
		return token;
	}

	public void setToken(Long token) {
		this.token = token;
	}

	

	public Long getNodeid() {
		return nodeid;
	}

	public void setNodeid(Long nodeid) {
		this.nodeid = nodeid;
	}

	public String getNodename() {
		return nodename;
	}

	public void setNodename(String nodename) {
		this.nodename = nodename;
	}
	
	/**
	 * 
	 * @param msgtype:100��ͷ����Ϣ��200��ͷ:�ʼ�
	 * @param msginfo
	 * @param sender
	 * @param receiver [0]:���ͣ�[1]:������
	 */
	public void setMsg(int msgtype,String msginfo,String sender,String[] receiver){
		
	}

	

	public double getQtypath() {
		return qtypath;
	}

	public void setQtypath(double qtypath) {
		this.qtypath = qtypath;
	}

	public String getSelpath() {
		return selpath;
	}

	public void setSelpath(String selpath) {
		this.selpath = selpath;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	/**
	 * ȡ�жϽ�����һ����
	 * @param nodeid
	 * @param type����0:ָ����1:��Χ
	 * @return
	 */
	public String getDecisionTran(long nodeid,int type){
		String trans=null;
//		if(this.decisionmatch!=null && this.decisionmatch.size()>0){
//			for(int i=0;i<this.decisionmatch.size();i++){
//				
//			}
//		}else{
			//�����ݿ�ȡ
			Connection con=null;
			Session hs=null;
			try{
				hs=HibernateUtil.getSession();
				con=hs.connection();
				String sql="Sp_JbpmDescisionJump "+nodeid+",'"+this.selpath+"',"+this.qtypath+","+type;
				ResultSet rs=con.createStatement().executeQuery(sql);
				if(rs.next()){
					trans=rs.getString("tranpath");
				}
			}catch(Exception e){
				DebugUtil.print(e);
			}finally{
				if(hs!=null){
					hs.close();
				}
			}
//		}
		return trans;
	}
	/**
	 * �ⲿָ����������Χ����
	 * @param nodeid
	 * @param selpath
	 * @param qtypath
	 * @param type
	 * @return
	 */
	public String getDecisionTran2(long nodeid,String selpath,double qtypath,int type){
		String trans=null;

		Connection con=null;
		Session hs=null;
		try{
			hs=HibernateUtil.getSession();
			con=hs.connection();
			String sql="Sp_JbpmDescisionJump "+nodeid+",'"+selpath+"',"+qtypath+","+type;
			ResultSet rs=con.createStatement().executeQuery(sql);
			if(rs.next()){
				trans=rs.getString("tranpath");
			}
		}catch(Exception e){
			DebugUtil.print(e);
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
		return trans;
	}

	public Long getTaskid() {
		return taskid;
	}

	public void setTaskid(Long taskid) {
		this.taskid = taskid;
	}

	public String[] getSignActors() {
		return SignActors;
	}

	public void setSignActors(String[] signActors) {
		this.SignActors = signActors;
	}

	public synchronized void setSignselpath(String signselpath) {
		if(this.signselpath!=null && this.signselpath.length()>0){
			this.signselpath +="__"+ signselpath;
		}else{
			this.signselpath = signselpath;
		}
	}

	public void initSignselpath() {
		this.signselpath = "";
	}
	
	public String getSignselpath() {
		return signselpath;
	}

	public String getApplydeptid() {
		return applydeptid;
	}

	public void setApplydeptid(String applydeptid) {
		this.applydeptid = applydeptid;
	}

//	public Object getPro(String key) {
//		return this.pro.get(key);
//	}
//
//	public void setPro(String key,Object obj) {
//		this.pro.put(key,obj);
//	}

	// HOTLINE: ���ڼ�¼ĳ��������һ�����ʵ��ID
	private String nextTaskInstancesId;
	
	public String getNextTaskInstancesId() {
		return nextTaskInstancesId;
	}

	public void setNextTaskInstancesId(String nextTaskInstancesId) {
		this.nextTaskInstancesId = nextTaskInstancesId;
	}
	// �ڽ�����ָ����������,���ܶ��
	private String subApproveResult;
	
	public String getSubApproveResult() {
		return subApproveResult;
	}

	public void setSubApproveResult(String subApproveResult) {
		this.subApproveResult = subApproveResult;
	}
	
	// �������м�¼������һЩ��̬�ڵ�,��鵵,������
	private String staticNodeName;
	
	public String getStaticNodeName() {
		return staticNodeName;
	}

	public void setStaticNodeName(String staticNodeName) {
		this.staticNodeName = staticNodeName;
	}
	// �������м�¼������һЩ��̬�ڵ����һ����������
	private String staticNodeNextTransition;
	
	public String getStaticNodeNextTransition() {
		return staticNodeNextTransition;
	}

	public void setStaticNodeNextTransition(String staticNodeNextTransition) {
		this.staticNodeNextTransition = staticNodeNextTransition;
	}
	
	
	// 2012-05-21
	public String subFlowUsers1;
	public String subFlowUsers2;
	public String subFlowUsers3;
	public String subFlowUsers4;
	public String subFlowUsers5;
	public String getSubFlowUsers1() {
		return subFlowUsers1;
	}

	public void setSubFlowUsers1(String subFlowUsers1) {
		this.subFlowUsers1 = subFlowUsers1;
	}

	public String getSubFlowUsers2() {
		return subFlowUsers2;
	}

	public void setSubFlowUsers2(String subFlowUsers2) {
		this.subFlowUsers2 = subFlowUsers2;
	}

	public String getSubFlowUsers3() {
		return subFlowUsers3;
	}

	public void setSubFlowUsers3(String subFlowUsers3) {
		this.subFlowUsers3 = subFlowUsers3;
	}

	public String getSubFlowUsers4() {
		return subFlowUsers4;
	}

	public void setSubFlowUsers4(String subFlowUsers4) {
		this.subFlowUsers4 = subFlowUsers4;
	}

	public String getSubFlowUsers5() {
		return subFlowUsers5;
	}

	public void setSubFlowUsers5(String subFlowUsers5) {
		this.subFlowUsers5 = subFlowUsers5;
	}
	// END HOTLINE


	
}

