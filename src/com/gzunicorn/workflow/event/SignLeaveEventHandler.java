package com.gzunicorn.workflow.event;

import java.util.Collection;

import org.jbpm.graph.def.ActionHandler;
import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.exe.Token;
import org.jbpm.taskmgmt.exe.TaskInstance;
import org.jbpm.taskmgmt.exe.TaskMgmtInstance;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.util.WorkFlowConfig;

public class SignLeaveEventHandler implements ActionHandler  {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	public void execute(ExecutionContext context) throws Exception {
		Token token = context.getToken();
		TaskMgmtInstance tmi = context.getTaskMgmtInstance();   
		
		Collection<TaskInstance> unfinishedTasks = tmi.getUnfinishedTasks(token);   
		ProcessBean pb=(ProcessBean)context.getContextInstance().getVariable(WorkFlowConfig.Flow_Bean);
//		Object variable = context.getVariable("selectedTransition");   
		String variable=pb.getSignselpath();
		//System.out.println("selTrans= "+variable);
	    String[] selectedTransition;   
	    if (variable == null) {   
	        throw new RuntimeException("Jbpm transition error: user selected transition is null!");   
	    } else {   
	        // ��ȡ��ǰTaskInstanceѡ���·��   
	        selectedTransition = variable.toString().split("__");   
	    }   
	   
	    // ����û�ѡ���·�������̶������ƶ��Ĳ�ͨ����·�������Զ�����������еĻ�ǩTask��ת����Ӧ·��
	    for(int i=0;i<selectedTransition.length;i++){
		    if (WorkFlowConfig.isNoPassTranKeyWord(selectedTransition[i])) {   
		         for (TaskInstance unfinishedTaskInstance : unfinishedTasks) {
		           // �ѱ�������û����ɵ�TaskInstance��ActorId��Ϊautomatic complete   
		           if (!unfinishedTaskInstance.hasEnded()) {   
		               unfinishedTaskInstance.setActorId("automatic complete");   
		               unfinishedTaskInstance.end(selectedTransition[i]);   
		            }   
		         }	      
		       
		     }
	    }
	    

		
	}

}
