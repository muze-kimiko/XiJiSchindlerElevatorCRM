package com.gzunicorn.workflow.decision;

import org.jbpm.graph.exe.ExecutionContext;
import org.jbpm.graph.node.DecisionHandler;

import com.gzunicorn.bean.ProcessBean;
import com.gzunicorn.common.util.WorkFlowConfig;


public class AppointDecisionHandler implements DecisionHandler{

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
		if(pb.getSelpath() == null || pb.getSelpath().length()==0)
		{
			pb.setSelpath(pb.getPro("selpath").toString());
		}
		if(pb!=null){
			rs=pb.getDecisionTran(arg0.getNode().getId(),0);
		}
		return rs;
	}

}
