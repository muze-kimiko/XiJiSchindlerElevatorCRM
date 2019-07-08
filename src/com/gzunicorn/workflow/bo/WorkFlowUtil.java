package com.gzunicorn.workflow.bo;

import java.sql.Connection;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.jbpm.JbpmConfiguration;
import org.jbpm.JbpmContext;
import org.jbpm.db.GraphSession;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ProcessInstance;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;

import com.gzunicorn.common.dao.DBInterface;
import com.gzunicorn.common.dao.ObjectAchieveFactory;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.Msg;
import com.gzunicorn.hibernate.jbpmmanager.Jbpmtokenflow;
public class WorkFlowUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	/**
	 * ȡָ�����̽�ɫ�û�
	 * @param FlowRole
	 * @param RefUserId
	 * @param FlowType: 0_agentgoodsplan
	 * @param WriteFlag
	 * @return
	 */
	public List getAssignmentUser(String FlowRole,String RefUserId,int FlowType,String WriteFlag){
		DBInterface dbi=ObjectAchieveFactory.getDBInterface(ObjectAchieveFactory.MssqlImp);
		List rslist=null;
		Session hs=null;
		try{
			hs=HibernateUtil.getSession();
			Connection con=hs.connection();
			dbi.setCon(con);
			String sql="Sp_FetchFlowRoleUser '"+FlowRole+"','"+RefUserId+"',"+FlowType+",'"+WriteFlag+"'";
			rslist=dbi.queryToList(sql);
		}catch(Exception e){
			DebugUtil.print(e);
		}finally{
			hs.close();
		}
		return rslist;
	}
	/**
	 * ɾ��ָ��������
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 * @throws Exception
	 */
	public Msg toDeleteToken(ActionForm form,HttpServletRequest request,JbpmConfiguration config,JbpmContext jbpmContext,HashMap arg)throws Exception {
		Msg msg=new Msg(Msg.msg_suc,"");
		long token=-1;
		token=Long.parseLong((String)arg.get("tokenid"));
		try{	
			//System.out.println("token="+token);
			if(token!=-1){
				Token tok=jbpmContext.getToken(token);
				if(tok!=null){
					ProcessInstance pi=tok.getProcessInstance();				
					//System.out.println("pid="+pi.getId());
				    jbpmContext.getGraphSession().deleteProcessInstance(pi.getId());
				}
			}
		}catch(Exception e){
			msg.setMsg(Msg.msg_exc,e.getMessage());
			DebugUtil.print(e);
		}
		return msg;
	}
	
	/**
	 * ͨ�����쳣���ж��Ƿ�ɾ���ɹ�
	 * @param tokenid
	 * @param jbpmContext
	 * @param arg
	 * @throws Exception
	 */
	public void toDeleteProcessInstance(long tokenid,JbpmContext jbpmContext,HashMap arg)throws Exception {
		ProcessInstance pi=jbpmContext.getToken(tokenid).getProcessInstance();
//		GraphSession gs=jbpmContext.getGraphSession();
		jbpmContext.getGraphSession().deleteProcessInstance(pi.getId());
	}
	/**
	 * Ӧ�ã���������ʱ����ѡ����
	 * flag=1,����taskid ȡ��ǰʵ������Ч����.
	 * flag=2,����ti ȡ��ǰʵ������Ч����.
	 * 
	 * ע��ti2.isOpen()�����٣�ԭ�򣺵�ti2�ǹر�ʱ��ti2.getAvailableTransitions()���ص��Ƿǵ�ǰʵ����Transition
	 * @param jbpmContext
	 * @param taskid
	 * @param ti
	 * @param flag
	 * @return
	 */
	public List getTaskInstanceTransition(JbpmContext jbpmContext,long taskid,TaskInstance ti,int flag){
		List rs=null;
		switch(flag){
		case 1:
			if(jbpmContext!=null){
				TaskInstance ti2=jbpmContext.getTaskInstance(taskid);
				if(ti2!=null && ti2.isOpen()){//ע��ti2.isOpen()�����٣�ԭ�򣺵�ti2�ǹر�ʱ��ti2.getAvailableTransitions()���ص��Ƿǵ�ǰʵ����Transition
					List avatran=ti2.getAvailableTransitions();
					if(avatran!=null && avatran.size()>0){
						rs=new ArrayList();
						HashMap trans=null;
						for(int i=0;i<avatran.size();i++){
							Transition tran=(Transition)avatran.get(i);
							trans=new HashMap();
							trans.put("tranid",tran.getId());
							trans.put("tranname",tran.getName());
							rs.add(trans);
						}
					}
				}
			}
			break;
		case 2:
			if(ti!=null && ti.isOpen()){
				List tranList=ti.getAvailableTransitions();
				if(tranList!=null && tranList.size()>0){
					rs=new ArrayList();
					HashMap trans=null;
					for(int i=0;i<tranList.size();i++){
						Transition tran=(Transition)tranList.get(i);
						trans=new HashMap();
						trans.put("tranid",tran.getId());
						trans.put("tranname",tran.getName());
						rs.add(trans);
					}
				}
			}
			break;
		}
		return rs;
	}
	/**
	 * Ӧ�ã���������ʱָ��������
	 * ���б��з���ָ��������
	 * @param tranid
	 * @param ti
	 * @return
	 */
//	public Transition getSelTransition(long tranid,TaskInstance ti){
//		Transition tr=null;
//		if(ti!=null && ti.isOpen()){
//			List tranList=ti.getAvailableTransitions();
//			if(tranList!=null && tranList.size()>0){
//				Transition tran=null;
//				for(int i=0;i<tranList.size();i++){
//					tran=(Transition)tranList.get(i);
//					if(tran.getId()==tranid){
//						tr=tran;
//						break;
//					}
//				}
//			}
//		}
//		return tr;
//	}
	/**
	 * ����node id��ȡ��Actors User
	 * @param NodeId
	 * @param RefUserId
	 * @param Flag
	 * @param other
	 * @return
	 */
	public List getNodeActors(Long NodeId,String RefUserId,String RefDeptId,String Flag,HashMap other){
		DBInterface dbi=ObjectAchieveFactory.getDBInterface(ObjectAchieveFactory.MssqlImp);
		List rslist=null;
		Session hs=null;
		try{
			hs=HibernateUtil.getSession();
			Connection con=hs.connection();
			dbi.setCon(con);
			String sql="Sp_JbpmGetNodeActors "+NodeId+",'"+RefUserId+"','"+RefDeptId+"'";
			rslist=dbi.queryToList(sql);
		}catch(Exception e){
			DebugUtil.print(e);
		}finally{
			hs.close();
		}
		return rslist;
	}
	/**
	 * ��ֹ����ʵ��,�쳣�ɵ��÷������Ƿ�Ҫrollback
	 * @param processInstanceId_TokenId
	 * @param jbpmContext
	 * @param flag,0:processInstanceId,1:tokenId
	 * @param arg
	 * @return
	 */
	public void endProcessInstance(long[] Ids,JbpmContext jbpmContext,int flag,HashMap arg){
		switch(flag){
		case 0:	//processInstanceId
			if(Ids!=null && Ids.length>0){
				for(int i=0;i<Ids.length;i++){
					jbpmContext.getProcessInstance(Ids[0]).end();
				}
			}
			break;
		case 1:	//tokenId
			if(Ids!=null && Ids.length>0){
				for(int i=0;i<Ids.length;i++){
					jbpmContext.getToken(Ids[0]).getProcessInstance().end();
				}
			}
			break;
		}
	}
	
	/**
	 * ����node id��ȡ��Actors User(������������ Ӫ������/�г��ƹ㲿/��Ʒ�󻮲����� ����ǰ��ڵ�ר��)
	 * @param NodeId
	 * @param RefUserId
	 * @param Flag
	 * @param other
	 * @return
	 * @author cwy 2009-04-02
	 */
	public List getNodeAdminstrationActors(Long NodeId,String RefUserId,String RefDeptId,String Flag,HashMap other){
		DBInterface dbi=ObjectAchieveFactory.getDBInterface(ObjectAchieveFactory.MssqlImp);
		List rslist=null;
		Session hs=null;
		String refclass1id = other.get("refclass1id").toString();
		try{
			hs=HibernateUtil.getSession();
			Connection con=hs.connection();
			dbi.setCon(con);
			String sql="Sp_JbpmAdminstrationNodeActors "+NodeId+",'"+refclass1id+"','"+RefDeptId+"'";
			rslist=dbi.queryToList(sql);
		}catch(Exception e){
			DebugUtil.print(e);
		}finally{
			hs.close();
		}
		return rslist;
	}
	
	/**
	 * ����ְλ��ȡ��Actors User(������������ Ӫ������/�г��ƹ㲿/��Ʒ�󻮲����� �ڵ�ר��)
	 * @param NodeId
	 * @param RefUserId
	 * @param Flag
	 * @param other
	 * @return
	 * @author cwy 2009-04-02
	 */
	public List getNodeAdminCheckActors(Long NodeId,String RefUserId,String RefDeptId,String Flag,HashMap other){
		DBInterface dbi=ObjectAchieveFactory.getDBInterface(ObjectAchieveFactory.MssqlImp);
		List rslist=null;
		Session hs=null;
		String checkclass1id = other.get("checkclass1id").toString();
		try{
			hs=HibernateUtil.getSession();
			Connection con=hs.connection();
			dbi.setCon(con);
			String sql="Sp_JbpmAdminCheckNodeActors '"+checkclass1id+"','"+Flag+"'";
			rslist=dbi.queryToList(sql);
		}catch(Exception e){
			DebugUtil.print(e);
		}finally{
			hs.close();
		}
		return rslist;
	}
	
	/**
	 * �������������ڲ���ȡ����Ƴ��û�(������������ �Ƴ� ��˽ڵ�ר��)
	 * @param NodeId
	 * @param RefUserId
	 * @param Flag
	 * @param other
	 * @return
	 * @author cwy 2009-04-20
	 */
	public List getNodeAdminSectionActors(Long NodeId,String RefUserId,String RefDeptId,String Flag,HashMap other){
		DBInterface dbi=ObjectAchieveFactory.getDBInterface(ObjectAchieveFactory.MssqlImp);
		List rslist=null;
		Session hs=null;
		//String checkclass1id = other.get("checkclass1id").toString();
		try{
			hs=HibernateUtil.getSession();
			Connection con=hs.connection();
			dbi.setCon(con);
			String sql="Sp_JbpmAdminSectionNodeActors '"+RefUserId+"','"+Flag+"'";
			rslist=dbi.queryToList(sql);
		}catch(Exception e){
			DebugUtil.print(e);
		}finally{
			hs.close();
		}
		return rslist;
	}
	
	/**
	 * ��������ְλ(refclass1id)��ȡ��Actors User(����רԱȷ�� �����ڵ�ר��)
	 * @param NodeId
	 * @param RefUserId
	 * @param Flag
	 * @param other
	 * @return
	 * @author cwy 2009-05-08
	 */
	public List getNodeAdminCommissionerActors(Long NodeId,String RefUserId,String RefDeptId,String Flag,HashMap other){
		DBInterface dbi=ObjectAchieveFactory.getDBInterface(ObjectAchieveFactory.MssqlImp);
		List rslist=null;
		Session hs=null;
		String refclass1id = other.get("refclass1id").toString();
		try{
			hs=HibernateUtil.getSession();
			Connection con=hs.connection();
			dbi.setCon(con);
			String sql="Sp_JbpmAdminCommonissionerNodeActors '"+refclass1id+"','"+RefDeptId+"'";
			rslist=dbi.queryToList(sql);
		}catch(Exception e){
			DebugUtil.print(e);
		}finally{
			hs.close();
		}
		return rslist;
	}
}
