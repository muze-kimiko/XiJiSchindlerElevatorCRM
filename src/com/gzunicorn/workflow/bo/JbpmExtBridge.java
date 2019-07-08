package com.gzunicorn.workflow.bo;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.Session;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.dao.DBInterface;
import com.gzunicorn.common.dao.ObjectAchieveFactory;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
//-- extends JbpmContext
public class JbpmExtBridge{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JbpmContext jbpmContext=null;
	WorkFlowUtil wfu=new WorkFlowUtil();
	ProcessBean pb=null;

	public JbpmExtBridge() throws Exception {
		JbpmConfiguration config=JbpmConfiguration.getInstance();
    	jbpmContext=config.createJbpmContext();
		if(jbpmContext==null){
			throw new Exception("jbpmContext is null!");
		}
	}
	public JbpmExtBridge(JbpmContext jbpmContext) throws Exception {
		this.jbpmContext=jbpmContext;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * ����������
	 *
	 */
//	public ProcessBean startProcess_old(String processName,String UserId,String RefUserId,String processInstanceId,String tip,HashMap arg){
//		ProcessDefinition pd=jbpmContext.getGraphSession().findLatestProcessDefinition(WorkFlowConfig.getProcessDefine(processName));//"baoxiao"
//		ProcessInstance pi=pd.createProcessInstance();
//		pb=new ProcessBean();
//		pb.setUserid(UserId);
//		pb.setApplyuserid(RefUserId);
//		pb.setTablekey(processInstanceId);
//		
//		
//		TaskInstance ti=pi.getTaskMgmtInstance().createStartTaskInstance();
//		ti.setActorId(pb.getUserid());
//		pb.setToken(ti.getToken().getId());
//		pb.setTaskid(ti.getId());
//		
//		if(arg!=null){
//			if(arg.get(ProcessBean.AppointActor)!=null){
//				pb.addAppointActors((String)arg.get(ProcessBean.AppointActor));
//			}
//			if(arg.get(ProcessBean.SignActor)!=null){
//				pb.setSignActors((String[])arg.get(ProcessBean.SignActor));
//			}
//			Iterator it=arg.keySet().iterator();
//			String key=null;
//			while(it.hasNext()){
//				key=(String)it.next();
//				if(key!=null){
//					pb.setPro(key,arg.get(key));
//				}
//			}
//		}
//		ti.getContextInstance().setVariable(WorkFlowConfig.Flow_Bean, pb);
//		
//		ti.end();//�ύ����һ��
//		return pb;
//	}
	
	public ProcessBean startProcess(String processName,String UserId,String RefUserId,String processInstanceId,String RefDeptId,HashMap arg){
		pb=getPb();
		pb.setUserid(UserId);
		pb.setApplyuserid(RefUserId);
		pb.setTablekey(processInstanceId);
		pb.setApplydeptid(RefDeptId);
		
		if(arg!=null){
			if(arg.get(ProcessBean.AppointActor)!=null){
				pb.addAppointActors((String)arg.get(ProcessBean.AppointActor));
			}
			if(arg.get(ProcessBean.SignActor)!=null){
				pb.setSignActors((String[])arg.get(ProcessBean.SignActor));
			}
			Iterator it=arg.keySet().iterator();
			String key=null;
			while(it.hasNext()){
				key=(String)it.next();
				if(key!=null){
					pb.setPro(key,arg.get(key));
				}
			}
		}
		/*****************************************************************************/
		ProcessDefinition pd=jbpmContext.getGraphSession().findLatestProcessDefinition(WorkFlowConfig.getProcessDefine(processName));//"baoxiao"
		ProcessInstance pi=pd.createProcessInstance();
		TaskInstance ti=pi.getTaskMgmtInstance().createStartTaskInstance();
		ti.setActorId(pb.getUserid());
		pb.setToken(new Long(ti.getToken().getId()));
		pb.setTaskid(new Long(ti.getId()));
		
		//�������̱���processBean
		ti.getContextInstance().setVariable(WorkFlowConfig.Flow_Bean, pb);
		
		String[] jump=this.Jump(processName,RefUserId,processInstanceId,pd.getVersion());
		if(jump!=null && jump[0].compareToIgnoreCase("0")>0){
	        //��̬������������   
	        Transition leavingTransition=new Transition("Jump");   
	        leavingTransition.setProcessDefinition(pd);
	        
	        //ָ����ת���Ŀ�Ľڵ���c   
	        Node fromNode=pi.getProcessDefinition().getNode(jump[3]);	//from
	        Node toNode=pi.getProcessDefinition().getNode(jump[5]);		//to
	       
	        leavingTransition.setFrom(fromNode);   
	        leavingTransition.setTo(toNode);
	        
	        //Ϊ��ǰ�ڵ�������ת��.   
	        fromNode.addLeavingTransition(leavingTransition);   
	        //�������ת����ת����   
	        ti.end(leavingTransition);
	        //ɾ�������ʱ������ת��   
	        fromNode.removeLeavingTransition(leavingTransition);   
		}else{	
		/*****************************************************************************/
			ti.end();//�ύ����һ��
		/*****************************************************************************/
		}
		//���±��棬�Ѹ��µ����µ�processbean �� 
		ti.getContextInstance().setVariable(WorkFlowConfig.Flow_Bean, pb);
		return pb;
	}
	/**
	 * �������̶�������
	 * @param processName
	 * @param UserId
	 * @param RefUserId
	 * @param processInstanceId
	 * @param RefDeptId
	 * @param arg
	 * @return
	 */
	public ProcessBean startProcessNoJump(String processName,String UserId,String RefUserId,String processInstanceId,String RefDeptId,HashMap arg){
		pb=getPb();
		pb.setUserid(UserId);
		pb.setApplyuserid(RefUserId);
		pb.setTablekey(processInstanceId);
		pb.setApplydeptid(RefDeptId);
		
		if(arg!=null){
			if(arg.get(ProcessBean.AppointActor)!=null){
				pb.addAppointActors((String)arg.get(ProcessBean.AppointActor));
			}
			if(arg.get(ProcessBean.SignActor)!=null){
				pb.setSignActors((String[])arg.get(ProcessBean.SignActor));
			}
			Iterator it=arg.keySet().iterator();
			String key=null;
			while(it.hasNext()){
				key=(String)it.next();
				if(key!=null){
					pb.setPro(key,arg.get(key));
				}
			}
		}
		/*****************************************************************************/
		ProcessDefinition pd=jbpmContext.getGraphSession().findLatestProcessDefinition(WorkFlowConfig.getProcessDefine(processName));//"baoxiao"
		ProcessInstance pi=pd.createProcessInstance();
		TaskInstance ti=pi.getTaskMgmtInstance().createStartTaskInstance();
		ti.setActorId(pb.getUserid());
		pb.setToken(new Long(ti.getToken().getId()));
		pb.setTaskid(new Long(ti.getId()));
		
		//�������̱���processBean
		ti.getContextInstance().setVariable(WorkFlowConfig.Flow_Bean, pb);
		ti.end();//�ύ����һ��
		//���±��棬�Ѹ��µ����µ�processbean ��Ϣ
		ti.getContextInstance().setVariable(WorkFlowConfig.Flow_Bean, pb);
		return pb;
	}
	
	/**
	 * ȡ��ǰ��ת��from and to ���
	 * @param processName
	 * @param RefUserId
	 * @param processInstanceId
	 * @param version
	 * @return
	 */
	public String[] Jump(String processName,String RefUserId,String processInstanceId,int version){
		Session hs=null;
		String[] jump={"0","","","","",""};
		try{
			
			hs=HibernateUtil.getSession();
			String sql="exec Sp_JbmpJump '"+processName+"','"+RefUserId+"',"+version;
			//System.out.println(sql);
			ResultSet rs=hs.connection().prepareStatement(sql).executeQuery();
			if(rs.next()){
				jump[0]=rs.getInt("jumpflag")+"";
				jump[1]=rs.getString("jumpmsg");
				jump[2]=rs.getLong("fromid")+"";
				jump[3]=rs.getString("fromname");
				jump[4]=rs.getLong("toid")+"";
				jump[5]=rs.getString("toname");
			}
		}catch(Exception e){
			DebugUtil.print(e);
		}finally{
			hs.close();
		}
		return jump;
	}
	
	/**
	 * Ӧ�ã���������ʱָ��������
	 * ���б��з���ָ��������
	 * @param tranid
	 * @param ti
	 * @return
	 */
	public Transition getTransitionUseId(long tranid,TaskInstance ti){
		Transition tr=null;
		if(ti!=null && ti.isOpen()){
			List tranList=ti.getAvailableTransitions();
			if(tranList!=null && tranList.size()>0){
				Transition tran=null;
				for(int i=0;i<tranList.size();i++){
					tran=(Transition)tranList.get(i);
					if(tran.getId()==tranid){
						tr=tran;
						break;
					}
				}
			}
		}
		return tr;
	}
	
	/**
	 * Type 0:�������̶��壬node start��; 1:����task id��;2:����node id ��,3:TaskInstance
	 * @param type
	 * @param process	����
	 * @param tasknode  task/node
	 * @return
	 * @throws SQLException 
	 */
	public List getTransition(Connection con,int type,String process,long tasknode) throws SQLException{
		DBInterface db=ObjectAchieveFactory.getDBInterface(ObjectAchieveFactory.MssqlImp);
		db.setCon(con);
		String sql="Sp_JbpmGetTransition "+type+",'"+process+"',"+tasknode;
		return db.queryToList(sql);
		
	}
	/**
	 * ��ȡ����Bean
	 */
	public ProcessBean getProcessBeanUseTI(long tid){
		TaskInstance ti = jbpmContext.getTaskInstance(tid);
		pb=(ProcessBean)ti.getContextInstance().getVariable(WorkFlowConfig.Flow_Bean);
		return pb;
	}
	
	/**
	 * �ƶ����̵���һ��
	 * @param tid ����ʵ��id
	 * @param tranname
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ProcessBean goToNextJump(long tid,String tranname,String userid,HashMap arg) throws Exception{
		TaskInstance ti = jbpmContext.getTaskInstance(tid);
		if(ti!=null && ti.isOpen() && (ti.getActorId()==null || ti.getActorId().equalsIgnoreCase(userid))){
			ti.setActorId(userid);
//			ti.end(tranname);
			pb=(ProcessBean)ti.getContextInstance().getVariable(WorkFlowConfig.Flow_Bean);
			pb.setTaskid(new Long(tid));
			
			if(pb.getStatus().intValue()==WorkFlowConfig.State_ApplyMod){
				ProcessDefinition pd=ti.getContextInstance().getProcessInstance().getProcessDefinition();
				
				String[] jump=this.Jump(pd.getName(),pb.getApplyuserid(),null,pd.getVersion());
				if(jump!=null && jump[0].compareToIgnoreCase("0")>0){
			        //��̬������������   
			        Transition leavingTransition=new Transition("Jump");   
			        leavingTransition.setProcessDefinition(pd);
			        
			        //ָ����ת���Ŀ�Ľڵ���c   
			        Node fromNode=pd.getNode(jump[3]);	//from
			        Node toNode=pd.getNode(jump[5]);		//to
			       
			        leavingTransition.setFrom(fromNode);   
			        leavingTransition.setTo(toNode);
			        
			        //Ϊ��ǰ�ڵ�������ת��.   
			        fromNode.addLeavingTransition(leavingTransition);   
			        //�������ת����ת����   
			        ti.end(leavingTransition);
			        //ɾ�������ʱ������ת��   
			        fromNode.removeLeavingTransition(leavingTransition);   
				}else{	
				/*****************************************************************************/
					ti.end(tranname);//�ύ����һ��
				/*****************************************************************************/
				}
			}else{
				ti.end(tranname);//�ύ����һ��
			}
			
			//���ı��˵����ݣ��������л����������棨����������ȡ�ر仯�˵����ݣ�
			ti.getContextInstance().setVariable(WorkFlowConfig.Flow_Bean,pb);
			return pb;
		}else{
			throw new Exception("ti had close or be handler by other");
		}
		
	}
	/**
	 * ������һ����������������
	 * @param tid
	 * @param tranname
	 * @param userid
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public ProcessBean goToNext(long tid,String tranname,String userid,HashMap arg) throws Exception{
		TaskInstance ti = jbpmContext.getTaskInstance(tid);
		if(ti!=null && ti.isOpen() && (ti.getActorId()==null || ti.getActorId().equalsIgnoreCase(userid))){
			ti.setActorId(userid);
			ti.end(tranname);
			pb=(ProcessBean)ti.getContextInstance().getVariable(WorkFlowConfig.Flow_Bean);
			pb.setTaskid(new Long(tid));
			//���ı��˵����ݣ��������л����������棨����������ȡ�ر仯�˵����ݣ�
			ti.getContextInstance().setVariable(WorkFlowConfig.Flow_Bean,pb);
			return pb;
		}else{
			throw new Exception("ti had close or be handler by other");
		}
		
	}
	/**
	 * �ƶ����̵���һ��
	 * @param tid������ʵ��id
	 * @param tran
	 * @param userid
	 * @return
	 * @throws Exception
	 */
	public ProcessBean goToNext(long tid,Transition tran,String userid,HashMap arg) throws Exception{
		TaskInstance ti = jbpmContext.getTaskInstance(tid);
		if(ti!=null && ti.isOpen() && (ti.getActorId()==null || ti.getActorId().equalsIgnoreCase(userid))){
			ti.setActorId(userid);
			ti.end(tran);
			pb=(ProcessBean)ti.getContextInstance().getVariable(WorkFlowConfig.Flow_Bean);
			pb.setTaskid(new Long(tid));
//			���ı��˵����ݣ��������л����������棨����������ȡ�ر仯�˵����ݣ�
			ti.getContextInstance().setVariable(WorkFlowConfig.Flow_Bean,pb);
			return pb;
		}else{
			throw new Exception("ti had close or be handler by other");
		}
	}
	/**
	 * �ع�����
	 *
	 */
	public void setRollBack(){
		if(jbpmContext!=null){
			jbpmContext.setRollbackOnly();
		}
	}
	/**
	 * �ر�jbpmContext
	 *ע�⣺jbpm��closeʱ�Ž�������صĶ����ύ���������closeǰ�����쳣������ʽ����setRollBack���ܻع�����
	 */
	public void close(){
		if(jbpmContext!=null){
			jbpmContext.close();
		}
	}
	/**
	 * ȡ������ʵ��
	 *
	 */
	public void cancelProcessInstance(long tokenid)throws Exception{
		//jbpmContext.getToken(tokenid).getProcessInstance().end();
		
		//ͳһ����wfu
		long[] ids={tokenid};
		wfu.endProcessInstance(ids,this.jbpmContext,1,null);
	}
	/**
	 * ɾ������ʵ��
	 * @throws Exception 
	 *
	 */
	public void delProcessInstance(long tokenid) throws Exception{
//		ProcessInstance pi=jbpmContext.getToken(tokenid).getProcessInstance();
//		jbpmContext.getGraphSession().deleteProcessInstance(pi.getId());
		
//		ͳһ����wfu
		wfu.toDeleteProcessInstance(tokenid,this.jbpmContext,null);
	}
	/**
	 * ���������ѡ��
	 * @param taskid
	 * @param actors
	 * @return
	 */
	public boolean setTaskActor(long taskid,String[] actors){
		if(actors!=null && actors.length>0){
			if(actors.length==1){
				this.jbpmContext.getTaskInstance(taskid).setActorId(actors[0]);
			}else{
				this.jbpmContext.getTaskInstance(taskid).setPooledActors(actors);
			}
			return true;
		}
		return false;
	}
	/**
	 * ��ֹ����
	 * @param tokenid
	 * ��������
	 * @param endnodesname
	 * �����ڵ�
	 */
	public void SuspensionFlow(long tokenid,String endnodesname){
		
		Token token=jbpmContext.getToken(tokenid);
		Collection<TaskInstance> unfTasks=token.getProcessInstance().getTaskMgmtInstance().getUnfinishedTasks(token);
		ProcessDefinition pd=token.getProcessInstance().getProcessDefinition();
		//��̬������������
        Transition leavingTransition=new Transition("SuspensionFlow");   
        leavingTransition.setProcessDefinition(pd);
        
        Node fromNode=pd.getNode(token.getNode().getName());
        Node toNode=pd.getNode(endnodesname);
        //ָ����ת���Ŀ�Ľڵ���c   
        leavingTransition.setFrom(fromNode);   
        leavingTransition.setTo(toNode);
        
        //Ϊ��ǰ�ڵ�������ת��.   
        fromNode.addLeavingTransition(leavingTransition);  
        //�������ת����ת����   
        for (TaskInstance uti : unfTasks) { 
			uti.end(leavingTransition);
		}
        //ɾ�������ʱ������ת��   
        fromNode.removeLeavingTransition(leavingTransition);
		
	}

	public ProcessBean getPb() {
		if(this.pb==null){
			this.pb=new ProcessBean();
		}
		return this.pb;
	}
	// hotline start 
	public long getTaskMsnId(long tokenid){
		Token token=jbpmContext.getToken(tokenid);
		return token.getProcessInstance().getTaskMgmtInstance().getId();
		 
	}
	// hotline end 
	
}
