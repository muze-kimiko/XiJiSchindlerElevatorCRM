package com.gzunicorn.common.task;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import org.apache.ojb.broker.util.logging.Logger;
import org.apache.ojb.broker.util.logging.LoggerFactory;
import org.apache.struts.actions.DispatchAction;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import com.gzunicorn.common.util.CommonUtil;
import com.gzunicorn.common.util.DebugUtil;
import com.gzunicorn.common.util.HibernateUtil;

/**
 * CRMϵͳ��ң��ϵͳ�ṩ��������,
 * �Ե��������ݿ���м��������ݡ�
 * @author cb
 *
 */
public class TimeWorkTest extends DispatchAction implements Job {

	Logger log=LoggerFactory.getLogger(TimeWorkTest.class);
	public TimeWorkTest() {
	}
	
	public void execute(JobExecutionContext arg0) throws JobExecutionException {
		
		DebugUtil.printDoOtherAction("TimeWorkTest", "timeworktestzu", "start");
		String msg = timeworktestzu();
		DebugUtil.println("timeworktestzu" + " >> " + msg);

		DebugUtil.printDoOtherAction("TimeWorkTest", "timeworktestzu", "end");
	}
	/**
	 * ִ�в����м��ķ���
	 * @return
	 */
  private String timeworktestzu(){

    String msg="����������ȡ�����";
    
	return msg;
  
  }
  
  public static void main(String[] msg){
	  TimeWorkTest tt=new TimeWorkTest();
	  try {
		tt.execute(null);
	} catch (JobExecutionException e) {
		e.printStackTrace();
	}
	  
  }
}
