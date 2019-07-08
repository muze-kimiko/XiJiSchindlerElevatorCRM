package com.gzunicorn.workflow.decision;

import org.hibernate.Session;
import org.jbpm.graph.def.Node;
import org.jbpm.graph.def.ProcessDefinition;
import org.jbpm.graph.def.Transition;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.util.HibernateUtil;
import com.gzunicorn.common.util.WorkFlowConfig;
import com.gzunicorn.workflow.bo.JbpmExtBridge;


public class JumpDecisionHandler implements DecisionHandler{

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * ���ݷ������ͣ���ת����ͬ��֧
	 * 
	 * �����Ƿ����ǩ�����ô��࣬ѡ��ͬ�ķ�֧
	 */
	
	public String decide(ExecutionContext arg0) throws Exception {
		String rs="";
		ProcessBean pb=(ProcessBean)arg0.getContextInstance().getVariable(WorkFlowConfig.Flow_Bean);
		
		ProcessDefinition pd=arg0.getProcessDefinition();
		JbpmExtBridge jbpmExtBridge=new JbpmExtBridge(null);
		
		String[] jump=jbpmExtBridge.Jump(pd.getName(),pb.getApplyuserid(),null,pd.getVersion());
		
		if(jump!=null && jump[0].compareToIgnoreCase("0")>0){
			Transition leavingTransition=arg0.getNode().getLeavingTransition(jump[5]);
			if(leavingTransition!=null){
		        //��̬������������   
		        leavingTransition=new Transition(jump[5]);   
		        leavingTransition.setProcessDefinition(pd);
		        
		        //ָ����ת���Ŀ�Ľڵ���c   
		        Node fromNode=pd.getNode(jump[3]);	//from
		        Node toNode=pd.getNode(jump[5]);		//to
		       
		        leavingTransition.setFrom(fromNode);   
		        leavingTransition.setTo(toNode);
		        
		        //Ϊ��ǰ�ڵ�������ת��.   
		        fromNode.addLeavingTransition(leavingTransition);   
			}
			if(leavingTransition!=null){
				rs=leavingTransition.getName();
			}
		}
		return rs;
	}

}
