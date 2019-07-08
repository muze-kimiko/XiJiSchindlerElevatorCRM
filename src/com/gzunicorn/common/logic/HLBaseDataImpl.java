/*
 * Created on 2005-7-25
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.logic;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.logging.LogFactory;
import org.apache.commons.logging.Log;

/**
 * @author Administrator 
 * ���߹�������
 */
public class HLBaseDataImpl extends BaseDataImpl {

	Log log = LogFactory.getLog(HLBaseDataImpl.class);

	/**
	 * ��ָ����������ʱ��
	 * @param srcDate
	 * @return
	 */
	public Date addMin(Date srcDate, int mins){
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setTime(srcDate);
		calendar.add(Calendar.MINUTE, mins);
		return calendar.getTime();
	}
	
	/**
	 * ������ת��Ϊ�ַ������
	 */
	public String getDateTimeStr(Date date) {
		if(date==null){
			return "";
		}
		SimpleDateFormat simpleFormatter;
		simpleFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

		String dateTimeStr = simpleFormatter.format(date);
		return dateTimeStr;
	}
	
	/**
	 * ȡ��ǰ����ʱ�䲢���ַ���ʽ���
	 * @return
	 */
	public String getNowDateTimeStr(){
		Date nowDate = new Date();
		return getDateTimeStr(nowDate);
	}
	
}
