/*
 * Created on 2005-7-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Properties;
import java.util.Calendar;

import javax.imageio.stream.FileImageInputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.util.LabelValueBean;

import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractdetail.MaintContractDetail;
import com.gzunicorn.hibernate.engcontractmanager.maintcontractmaster.MaintContractMaster;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplandetail.MaintenanceWorkPlanDetail;
import com.gzunicorn.hibernate.maintenanceworkplanmanager.maintenanceworkplanmaster.MaintenanceWorkPlanMaster;
import com.gzunicorn.hibernate.sysmanager.Getnum;
import com.gzunicorn.hibernate.sysmanager.GetnumKey;
import com.gzunicorn.hibernate.viewmanager.ViewLoginUserInfo;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created on 2005-7-12
 * <p>
 * Title: ͨ�ó�����
 * </p>
 * <p>
 * Description:�ṩ��̬���ģʽ
 * </p>
 * <p>
 * Copyright: Copyright (c) 2005
 * <p>
 * Company:�����Ƽ�
 * </p>
 * 
 * @author wyj
 * @version 1.0
 */
public class CommonUtil {

    private static CommonUtil commonUtil = new CommonUtil();
    private static final long MSECONDS_OF_ONE_DAY = 60 * 60 * 1000 * 24;
    public final static DateFormat commonFormat = new SimpleDateFormat("yyyy-MM-dd");

    private CommonUtil() {
    }

    public static CommonUtil getInstance() {
        return commonUtil;
    }

    /**
     * ���������л��ɶ�����byte[]
     * 
     * @param obj
     *            Object
     * @throws IOException
     * @return byte[]
     */
    public static byte[] serializeObject(Object obj) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(bytes);
        out.writeObject(obj);
        out.flush();
        out.close();
        return bytes.toByteArray();
    }

    /**
     * ��byte[]�����л�Ϊ���� ��Ҫ���漰����class��import������,�����׳�ClassNotFoundException
     * 
     * @param serializeObject
     *            byte[]
     * @throws ClassNotFoundException
     * @throws IOException
     * @return Object
     */
    public static Object deSerializeObject(byte[] serializeObject)
            throws ClassNotFoundException, IOException {
        ByteArrayInputStream bytes = new ByteArrayInputStream(serializeObject);
        ObjectInputStream in = new ObjectInputStream(bytes);
        return in.readObject();
    }

    /**
     * ���ַ����ֲ�List��
     * 
     * @param str
     *            String ���ֲ���ַ���
     * @param sep
     *            String �ֲ��
     * @return List ���طֲ�������ݵ�List
     */
    public static List sepString(String str, String sep) {
        List list = new ArrayList();

        StringTokenizer st = new StringTokenizer(str, sep);
        try {
            list.add(st.nextToken());
            while (st.hasMoreElements()) {
                list.add(leftTrim(st.nextToken()));
            }
        } catch (Exception e) {
            //System.out.println("StringTokenizer�ֲ��ַ���ʧ��!");
        }
        return list;
    }

    public static String leftTrim(String str) {
        int i = 0;
        for (; i < str.length(); i++) {
            if (str.charAt(i) != 32) {
                break;
            }
        }
        return str.substring(i);
    }

    /**
     * ת����������TO�ַ�������
     * 
     * @param date
     *            Date ��������
     * @param dateFormat
     *            String ת����ĸ�ʽ
     * @return String �����������ַ���
     */
    public static String dateToStr(Date date, String dateFormat) {

        // ע��߽�ֵ�Ĵ�����߳���Ľ�׳��
        if (date == null) {
            return "";
        }

        if (dateFormat == null || dateFormat.equals("")
                || dateFormat.length() == 0) {
            dateFormat = "yyyy-MM-dd";

        }
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);

        return df.format(date);
    }

     /**
     * ת���ַ�������TO��������
     * 
     * @param strDate
     *            String ��������
     * @param dateFormat
     *            String ת����ĸ�ʽ
     * @return date ����������
     * @throws ParseException
     */
    public static Date strToDate(String strDate, String dateFormat)
            throws ParseException {

        if (dateFormat == null || dateFormat.equals("")
                || dateFormat.length() == 0) {
            dateFormat = "yyyy-MM-dd";
        }
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);

        return df.parse(strDate);
    }

    /**
     * ת���ַ�������TO SQL��������
     * 
     * @param strDate
     *            String ��������
     * @param dateFormat
     *            String ת����ĸ�ʽ
     * @return String �����������ַ���
     * @throws ParseException
     */
    public static java.sql.Date strToSQLDate(String strDate, String dateFormat)
            throws ParseException {
        if (strDate == null || strDate.trim().equals("")) {
            return null;
        }
        return new java.sql.Date(strToDate(strDate, dateFormat).getTime());
    }

    public static Time strToSQLTime(String time) {
        return Time.valueOf(time);
    }

    public static long subDate(Date from, Date to) throws ParseException {
        long value = Math.abs(to.getTime() - from.getTime());
        return value / MSECONDS_OF_ONE_DAY + 1;
    }

    public static long subDate(String from, String to) throws ParseException {
        return subDate(commonFormat.parse(from), commonFormat.parse(to));
    }

    public static String getToday() throws ParseException {

        return commonFormat.format(new Date());

    }

    /**
     * @param records
     *            String
     * @param sep
     *            String
     * @return String ����ѡ�еĵ�һ����¼�Ĺؼ���
     */
    public static String getFirstRecord(String records, String sep) {
        String record = null;
        if (records == null) {
            return null;
        }
        StringTokenizer st = new StringTokenizer(records, sep);
        try {
            record = st.nextToken();

        } catch (Exception e) {
            //System.out.println("StringTokenizer�ֲ��ַ���ʧ��!");
        }
        return record;
    }

    public static String concatByComma(String str1, String str2)
            throws Exception {
        if (str2 == null) {
            throw new Exception(
                    "The second parameter of concatByComma method cannot be null.");
        }
        if (str1 == null) {
            return str2;
        } else {
            return str1 + "," + str2;
        }
    }

    public static String listToStr(List seqs, String separator) {

        if (seqs == null || seqs.size() == 0)
            return null;
        StringBuffer str = new StringBuffer();
        if (seqs.size() > 1)
            for (int i = 0, n = seqs.size(); i < n - 1; i++)
                str.append((String) seqs.get(i) + separator);
        str.append((String) seqs.get(seqs.size() - 1));

        return str.toString();
    }

    /**
     * ��ͨ��Java Script��������"5, 6,....."��ʽ��¼�ַ���ת ����ΪOracle���趨��Χ���ַ������˷�����Ҫ����Դ��ݵļ�
     * ¼��Ϊ�ַ�����ʽ������� for example: input as : ta1, tb2, tb3, tb4 output as :
     * {'ta1', 'tb2', tb3', 'tb4'}
     * 
     * @param recordStr
     *            String
     * @return String
     */
    public static String strToLocation(String recordStr) {
        StringBuffer buf = null;
        StringTokenizer location = null;

        buf = new StringBuffer();
        location = new StringTokenizer(recordStr, " ,");
        while (location.hasMoreTokens()) {
            buf.append("'");
            buf.append(location.nextToken());
            buf.append("'");
            buf.append(",");
        }
        return buf.toString().substring(0, (buf.length() - 1));
    }

    public static String getProperty(String fileName, String name) {
        Properties properties = null;
        try {
            properties = new Properties();
            InputStream in = commonUtil.getClass().getClassLoader()
                    .getResourceAsStream(fileName);
            properties.load(in);
            in.close();
        } catch (IOException e) {
            System.err.println("load properties file error!");
            e.printStackTrace();
        }

        return properties.getProperty(name);
    }

    /**
     * @param date1
     *            Date
     * @param date2
     *            Date
     * @return int 0:date1==date2, <0:date1 <date2,>0:date1>date2
     */

    public static int compareDateWithoutTime(Date date1, Date date2) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date1);
        ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca
                .get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        date1 = ca.getTime();
        ca.setTime(date2);
        ca.set(ca.get(Calendar.YEAR), ca.get(Calendar.MONTH), ca
                .get(Calendar.DAY_OF_MONTH), 0, 0, 0);
        date2 = ca.getTime();
        return date1.compareTo(date2);

    }

    public static long getLongTime(Date date) throws Exception {
        if (date == null)
            throw new Exception();
        Calendar ca = Calendar.getInstance();
        int year = ca.get(Calendar.YEAR);
        int month = ca.get(Calendar.MONTH);
        int day = ca.get(Calendar.DAY_OF_MONTH);
        Date current = ca.getTime();
        ca.setTime(date);
        ca.set(Calendar.YEAR, year);
        ca.set(Calendar.MONTH, month);
        ca.set(Calendar.DAY_OF_MONTH, day + 1);

        date = ca.getTime();
        return date.getTime() - current.getTime();
    }

    public static long getLongTimeByStrDate(String date) throws Exception {
        DateFormat format = new SimpleDateFormat("HH:mm:ss:SSS");
        Date dDate = null;
        if (date == null || date == "")
            date = "00:00:00:000";
        try {
            dDate = format.parse(date);
        } catch (Exception e) {
            e.printStackTrace();
            dDate = new Date();
        }

        return getLongTime(dDate);
    }

    /**
     * ת���Ƿ�״̬
     * 
     * @param enabledFlag
     * @return
     */
    public static String tranEnabledFlag(String enabledFlag) {
        return enabledFlag.equals("Y") ? "��" : "��";
    }
    
    /**
     * ת������״̬
     * 
     * @param enabledFlag
     * @return
     */
    public static String tranHaveFlag(String enabledFlag) {
        return enabledFlag.equals("Y") ? "��" : "��";
    }
    
    /**
     * ת�����ݲ�Ʒ���ܱ�׼��Ǳ�׼���
     * 
     * @param flag
     */
    public static String tranFlag(String flag) 
    {
    	return flag.equals("0") ? "����Ҫ��" : "װ��Ҫ��";
    }
    
  
    /**
     * ת�����ݲ�Ʒ���ܱ�׼��Ǳ�׼���
     * 
     * @param flag
     */
    public static String tranStandardFlag(String standardflag) 
    {
    	return standardflag.equals("0") ? "��׼" : "�Ǳ�׼";
    }
    
    /**
     * ת�����ݲ�Ʒ���ܱ�׼��Ǳ�׼���
     * 
     * @param flag
     */
    public static String tranViewBusinessType(String viewbusinesstype) 
    {
    	return viewbusinesstype.equals("0") ? "����" : "ó��";
    }
    
    public static void main(String[] agrs) {
        //System.out.println(strToSQLTime("11:11:11"));

    }

    /**
     * ����ת���ַ���;
     * 
     * @param argString
     * @return
     */
    public static String setChinese(String argString) {
        String returnString = argString;
        try {
            returnString = new String(argString.getBytes("UTF-16"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return returnString;

    }

    /**
     * ����������ָ�ʽ���ַ�������
     * 
     * @param randomLen
     *            ��Ҫ���ɵ����ַ����ĳ���
     * @param argLen
     *            ��Ҫ�����ַ���������ĳ���
     * @return String[] �����ַ�������
     */
    public static String[] randomInt(int randomLen, int argLen) {

        if (argLen < 0) {
            DebugUtil.println("���鳤�Ȳ�������С��0!");
            return null;
        }

        if (randomLen <= 0) {
            DebugUtil.println("������ɵ��ַ������Ȳ�������С�ڻ����0!");
            return null;
        }

        String randomInt[] = new String[argLen];
        int tempInt = 0;
        Random r = new Random();
        for (int i = 0; i < argLen; i++) {
            StringBuffer tempStr = new StringBuffer("");
            for (int j = 0; j < randomLen; j++) {
                tempStr = tempStr.append(r.nextInt(10));
            }
            randomInt[i] = tempStr.toString();
        }
        return randomInt;
    }

    /**
     * ������ˮ�ţ���λ��ݣ���ţ�0500000010��
     * @param year1
     * 			��ǰ��ݣ���λ���ȣ�05��
     * @param deptFlag
     * 			��ˮ�ű�־���ַ��ͣ�    
     * @param jnlLen
     *            ��Ҫ���ɵ����ַ����ĳ���
     * @param argLen
     *            ��Ҫ������ˮ�ŵ�����ĳ��ȣ����кŵĸ�����
     *         dataBaseFlag 
     *            ����ǻ�������,����Y , NΪ˳���   
     * @return String[] �����ַ�������
     * 
     */
    public synchronized static String[] getJnlNo(String year1, String deptFlag, int jnlLen,
            int argLen,String dataBaseFlag ) {
    	
    	String vlaueString = new String();
    	  
        if (argLen < 0) {
            DebugUtil.println("���鳤�Ȳ�������С��0!");
            return null;
        }
    
       if  (dataBaseFlag.equals("N")){
    	   vlaueString = year1;
        if (jnlLen <= 5) {
            DebugUtil.println("������ɵ��ַ������Ȳ�������С�ڻ����4!");
            return null;
            
        }
        
       }else{
    	   vlaueString = deptFlag;
    	   
       }
      
       
       
    if (jnlLen >= 9) {
 	   DebugUtil.println("������ɵ��ַ������Ȳ������ܴ���9!");
        return null; 
      }
    
        if (year1 == null || year1.length() != 2) {
            DebugUtil.println("��ݲ������ȴ���!");
            return null;
        }

        
        String jnlNo[] = new String[argLen];
        int tempInt = 100000000;
        int nextNo = 0;
        int tempNum  = 0;
       
        tempNum = 9 - jnlLen ;
        
        ArrayList list = null;
        Session session = null;
        
        Getnum getnum = new Getnum();
    	GetnumKey  getnumkey = new GetnumKey();
    	Transaction tx = null;
        try {
        	
            session = HibernateUtil.getSession();
            Query query = session
                    .createQuery("FROM Getnum as a WHERE a.id.year1=:year1 AND a.id.deptflag = :deptflag");
            query.setString("year1", year1);
            query.setString("deptflag", deptFlag);
            
            tx = session.beginTransaction();
            list = (ArrayList) query.list();
          
            if (list == null || list.isEmpty() || list.size() == 0) {
                // ��û�м�¼�����кŴ�1��ʼ
                for (int i = 0; i < argLen; i++) {
                    jnlNo[i] = vlaueString + String.valueOf(tempInt + nextNo + 1).substring(tempNum,9).trim();
                    nextNo++;
                }
                
                getnumkey.setYear1(year1);
                getnumkey.setDeptflag(deptFlag);
                getnum.setId(getnumkey);
               
            } else {
                getnum = (Getnum) list.get(0);
                nextNo = getnum.getNextno().intValue();
                if (nextNo == 1 ){
                	nextNo = 2 ; 
                }
                
                for (int i = 0; i < argLen; i++) {
                    jnlNo[i] = vlaueString + String.valueOf(tempInt + nextNo).substring(tempNum,9).trim();
                    nextNo++;
                }
               
            }
            getnum.setNextno(new Integer(nextNo));
            session.save(getnum);
            tx.commit();
        } catch (DataStoreException dex) {
            try {
                tx.rollback();
            } catch (HibernateException e) {
                // TODO Auto-generated catch block
                DebugUtil.print(e, "Hibernate Rolenode Save error!");
            }
            DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
        } catch (HibernateException hex) {
            try {
                tx.rollback();
            } catch (HibernateException e) {
                DebugUtil.print(e, "Hibernate Rolenode Save error!");
            }
            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
        } finally {
            try {
                session.close();
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }
        }
        return jnlNo;
    }
    /**
     * ���������и����ļ�
     * @param is ������
     * @param os �����
     * @param isClose ��������Ƿ�ر�������
     * @param osClose ��������Ƿ�ر������
     * @throws IOException
     */
    public static void copyFile(InputStream is,OutputStream os,boolean isClose,boolean osClose)
        throws IOException{
            int b;//���ļ�������ֽ�
            while((b = is.read()) != -1){
                os.write(b);
            }
            
            if(is != null && isClose){
                is.close();
            }
            
            if(os != null && osClose){
                os.close();
            }
    }

    /**
     * ȡ�������ĵ�ǰʱ��
     * @return hh:mm:ss ��ʽ��ʱ��
     */
    public static String getTodayTime(){
            String time = new Timestamp(System.currentTimeMillis()).toString();
    		return time.substring(11,19);
    }
    /**
     * ȡ�������ĵ�ǰʱ��
     * @return hhmmss ��ʽ��ʱ��
     */
    public static String getTodayTimeFormat(){
            String time = new Timestamp(System.currentTimeMillis()).toString();
    		time =  time.substring(11,19);
    		return time.substring(0,2) + time.substring(3,5) + time.substring(6,8);
    }	
    
    /**
     * ȡ��һ�������
     * @param date
     * @return yyyy-mm-dd ��ʽ������
     * @throws ParseException 
     */
    public static Date getNextDay(Date date) throws ParseException{
            String Sdate = CommonUtil.dateToStr(date,"");
    		try
			{
    			String day =  Sdate.substring(8,10);
    			int Iday = Integer.parseInt(day);
    			Iday = Iday + 1;
    			Sdate = Sdate.substring(0,8)+Iday;
			}catch(Exception e)
			{
				DebugUtil.print(e, "?????????��?��??");
			}
    		
 			return strToDate(Sdate,"");

    }
    
 /**
     * ȡǰһ�������
     * @param date
     * @return yyyy-mm-dd ��ʽ������
     * @throws ParseException 
     */
    public static Date getBeforeDay(Date date) throws ParseException{
            String Sdate = CommonUtil.dateToStr(date,"");
    		try
			{
    			String day =  Sdate.substring(8,10);
    			int Iday = Integer.parseInt(day);
    			Iday = Iday - 1;
    			Sdate = Sdate.substring(0,8)+Iday;
			}catch(Exception e)
			{
				DebugUtil.print(e, "?????????��?��??");
			}
    		
 			return strToDate(Sdate,"");
    }
    
    /**
     * 
     * �淶float�������ݳ�������ʱ����E�Ŀ�ѧ�������ķ���
     * create by cwy
     */
 
    public static String formatprice (double num)
    {
    	String price = "";
    	try
    	{
    	  DecimalFormat mat = new DecimalFormat("##.#");
    	  price = mat.format(num);
    	  
    	}
    	catch(Exception e)
    	{
    		DebugUtil.print(e,"���ݸ�ʽ���쳣!");
    	}
    	
    	return price;
    }
    public static String formatprice (double num,String format)
    {
    	String price = "";
    	try
    	{
    		if(null == format || "".equals(format)){
    			format = "##.#";
    		}
    		DecimalFormat mat = new DecimalFormat(format);
    		price = mat.format(num);
    		
    	}
    	catch(Exception e)
    	{
    		DebugUtil.print(e,"���ݸ�ʽ���쳣!");
    	}
    	
    	return price;
    }
    /**
     * 
     * �淶double��������ת�����Ĵ�д
     * create by zzg
     */
    
    public static String formatPriceUpCase (Double num,Integer bit)
    {
    	int maxlen = 12;
    	String unit = "Ǫ,��,ʰ,��,Ǫ,��,ʰ,��,Ǫ,��,ʰ,Ԫ,��,��";
    	String unitArr[] = unit.split(",");
    	String upcase = "��,Ҽ,��,��,��,��,½,��,��,��";
    	String upcaseArr[] =upcase.split(",");
    	DecimalFormat mat = new DecimalFormat("##.##");
    	String price = "";
    	String rt ="";
    	try
    	{
    		if(null != bit && null != num && bit > 0 && bit <13){
    			String numStr = mat.format(num);
    			numStr=FormatNumber(numStr,2);
    			String numArr[] =numStr.split("\\.");
    			String prefix = numArr[0];
    			String backfix = numArr[1];
//    			//System.out.println(prefix);
//    			//System.out.println(backfix);
    			//12345678900
//    			for(int i = (12-bit) ;i < 14; i++ ){
//    				//System.out.print(unitArr[i]);
//    			}
    			price =prefix+backfix;
    			//System.out.println(price);
    			int len = price.length();
    			String zero = "";
    		
    			if(bit+2 > len){
    				for(int i = 0 ; i < bit+2 -len; i++){
    					zero +="0";
    				}
    			}
    			
    			price = zero+price;
    			len = price.length();
    			if(bit+2 < len){
    				bit = len;
    			}
    			for(int i = 0 ; i < len ; i++){
    				String temp = upcaseArr[Integer.parseInt(price.charAt(i)+"")];
    				String temp2 = unitArr[(maxlen-bit+i)];
//    				rt = rt+" "+temp+" "+temp2;
    				rt = rt+temp;
    			}
//    			//System.out.println(price);
    		}else{
    			
    		}
    		
//    		DecimalFormat mat = new DecimalFormat("##.#");
//    		price = mat.format(num);
    		
    	}
    	catch(Exception e)
    	{
    		DebugUtil.print(e,"���ݸ�ʽ���쳣!");
    	}
    	
    	return rt;
    }
    /**
     * 
     * �淶double��������ת�����Ĵ�д����Ǫ��ʰ��Ǫ��ʰԪ�Ƿ� ��λ
     * ����ṩ12λ����ת��,��Ǫ��
     * ��λС��
     * create by zzg
     */
    
    public static String formatPriceUpCaseWithUnit(Double num,Integer bit)
    {
    	int maxlen = 12;
    	String unit = "Ǫ,��,ʰ,��,Ǫ,��,ʰ,��,Ǫ,��,ʰ,Ԫ,��,��";
    	String unitArr[] = unit.split(",");
    	String upcase = "��,Ҽ,��,��,��,��,½,��,��,��";
    	String upcaseArr[] =upcase.split(",");
    	DecimalFormat mat = new DecimalFormat("##.##");
    	String price = "";
    	String rt ="";
    	try
    	{
    		if(null != bit && null != num && bit > 0 && bit <13){
    			String numStr = mat.format(num);
    			numStr=FormatNumber(numStr,2);
    			String numArr[] =numStr.split("\\.");
    			String prefix = numArr[0];
    			String backfix = numArr[1];
//    			//System.out.println(prefix);
//    			//System.out.println(backfix);
    			//12345678900
//    			for(int i = (12-bit) ;i < 14; i++ ){
//    				//System.out.print(unitArr[i]);
//    			}
    			price =prefix+backfix;

    			int len = price.length();
    			String zero = "";
    		
    			if(bit+2 > len){
    				for(int i = 0 ; i < bit+2 -len; i++){
    					zero +="0";
    				}
    			}
    			
    			price = zero+price;
    			len = price.length();
    			if(bit+2 < len){
    				bit = len;
    			}
    			for(int i = 0 ; i < len ; i++){
    				String temp = upcaseArr[Integer.parseInt(price.charAt(i)+"")];
    				String temp2 = unitArr[(maxlen-bit+i)];
    				rt = rt+" "+temp+" "+temp2;
    			}
//    			//System.out.println(price);
    		}else{
    			
    		}
    		
//    		DecimalFormat mat = new DecimalFormat("##.#");
//    		price = mat.format(num);
    		
    	}
    	catch(Exception e)
    	{
    		DebugUtil.print(e,"���ݸ�ʽ���쳣!");
    	}
    	
    	return rt;
    }
    
    /**
     * ��ӡ�б�
     * @param title
     * @param list
     */

    public static void toPrint(String title,List list){ 
		////System.out.println("----"+title+"----start----");
		if(list!=null){ 
			int len=list.size();
			for(int i=0;i<len;i++){
				//System.out.println("--"+i+"--"+list.get(i));
			}
		}
		////System.out.println("----"+title+"----end----");
	}
    
    

    /**
     * 
     * @param year1:���
     * @param deptFlag:����������ˮ�ŵĿ�ʼ�ַ�
     * @param jnlLen:��ˮ�ŵĳ���
     * @param dataBaseFlag:�Ƿ��deptFlag��Ϊ��ˮ�ŵĿ�ʼ�ַ��ı�־��Y�����ǣ�N�����(�ô�������)
     * @param firstFlag:�Ƿ��һ��������ˮ��
     * @param verLen:�汾�ŵĳ���
     * @return
     */
    public static String getQuoteNO(String year1, String deptFlag, int jnlLen,
    								String dataBaseFlag, String firstFlag,int verLen) {
    	
    	String vlaueString = new String();
    	
    	dataBaseFlag = "Y";
    	
    	 
    	String str_cut_before = "";
    	if(firstFlag.equals("N")){
    		String str_cut[] = deptFlag.split("_");
    		if(str_cut.length>0){
    			str_cut_before = str_cut[0];
//        		cutLen = str_cut_before.length();
        		deptFlag = str_cut_before;
    		}
    		
    	}

        if (jnlLen <= 5) {
            DebugUtil.println("������ɵ��ַ������Ȳ�������С�ڻ����4!");
            return null;
            
        }
        
        if (dataBaseFlag.equals("N")){
    	   vlaueString = year1;
 
        }else{
    	   vlaueString = deptFlag;
    	   
        }
         
        if (jnlLen >= 9) {
        	DebugUtil.println("������ɵ��ַ������Ȳ������ܴ���9!");
        	return null; 
        }
    
        if (year1 == null || year1.length() != 2) {
            DebugUtil.println("��ݲ������ȴ���!");
            return null;
        }

        
//        String jnlNo[] = new String[argLen];
        String quoteNO = new String("");
        int tempInt = 100000000;
        int nextNo = 0;
        int tempNum  = 0;
       
        tempNum = 9 - jnlLen ;
        
        ArrayList list = null;
        Session session = null;
        
        Getnum getnum = new Getnum();
    	GetnumKey  getnumkey = new GetnumKey();
    	Transaction tx = null;
        try {
        	
            session = HibernateUtil.getSession();
            
            
            
            Query query = session
                    .createQuery("FROM Getnum as a WHERE a.id.year1=:year1 AND a.id.deptflag = :deptflag");
            query.setString("year1", year1);
            query.setString("deptflag", deptFlag);
            
            tx = session.beginTransaction();
            list = (ArrayList) query.list();
          
            
            //����Ƕ�ĳһ����Ŀ��һ�����ɵĻ�
            if(firstFlag.equals("Y")){
            	
            	if (list == null || list.isEmpty() || list.size() == 0) {
                    // ��û�м�¼�����кŴ�1��ʼ
            		
            		String para = "1";
            		String verNO = "";
            		while(verLen-para.length()>0){
            			para = "0"+para;
            		}
            		verNO = para;
            		
            		nextNo = nextNo + 1;
            		
                	quoteNO = vlaueString + String.valueOf(tempInt + nextNo).substring(tempNum,9).trim() + "_" + verNO;
                    
                    getnumkey.setYear1(year1);
                    getnumkey.setDeptflag(deptFlag);
                    getnum.setId(getnumkey);
                   
                } else {
                    getnum = (Getnum) list.get(0);
                    nextNo = getnum.getNextno().intValue();
                    if (nextNo == 1 ){
                    	nextNo = 2 ; 
                    }else{
                    	nextNo = nextNo + 1;
                    }
                    
                    String para1 = "1";
            		String verNO1 = "";
            		while(verLen-para1.length()>0){
            			para1 = "0"+para1;
            		}
            		verNO1 = para1;
                    
                    
                    quoteNO = vlaueString + String.valueOf(tempInt + nextNo).substring(tempNum,9).trim() + "_" + verNO1;
                   
                }
            }
            //������Ѿ��������ɹ���
            else{
            	
            	if (list == null || list.isEmpty() || list.size() == 0) {
                    // ��û�м�¼���汾�Ŵ�2��ʼ
            		nextNo = nextNo + 2;
            		String versionno = "";
            		String nextNo_str = String.valueOf(nextNo);
            		while(verLen-nextNo_str.length()>0){
            			nextNo_str = "0"+nextNo_str;
            		}
            		versionno = nextNo_str;
            		
                	quoteNO = vlaueString + "_" + versionno;
                    
                    getnumkey.setYear1(year1);
                    getnumkey.setDeptflag(deptFlag);
                    getnum.setId(getnumkey);
                   
                } else {
                    getnum = (Getnum) list.get(0);
                    nextNo = getnum.getNextno().intValue();
                    if (nextNo == 2 ){
                    	nextNo = 3 ; 
                    }else{
                    	nextNo = nextNo + 1;
                    }
                    
                    String versionno1 = "";
                    String nextNo_str1 = String.valueOf(nextNo);
                    while(verLen-nextNo_str1.length()>0){
                    	nextNo_str1 = "0" + nextNo_str1;
                    }
                    
                    versionno1 = nextNo_str1;
                    
                    quoteNO = vlaueString + "_" + versionno1;
                   
                }
            }
            
            
            getnum.setNextno(new Integer(nextNo));
            session.save(getnum);
            tx.commit();
        } catch (DataStoreException dex) {
            try {
                tx.rollback();
            } catch (HibernateException e) {
                // TODO Auto-generated catch block
                DebugUtil.print(e, "Hibernate Rolenode Save error!");
            }
            DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
        } catch (HibernateException hex) {
            try {
                tx.rollback();
            } catch (HibernateException e) {
                DebugUtil.print(e, "Hibernate Rolenode Save error!");
            }
            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
        } finally {
            try {
                session.close();
            } catch (HibernateException hex) {
                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
            }
        }
//        return jnlNo;
        return quoteNO;
    }
    
    
    
    
    
    
    
    
    public static String  getQuotaNO(String year1, String deptFlag, int jnlLen,String dataBaseFlag ,int verLen ,String jnlno) {
    	
    	String vlaueString = new String();
    	
    	String quotano = new String ("");
    	
    	dataBaseFlag = "Y";//����һ���Դ���Ĳ���Ϊ��ˮ�ŵĿ�ͷ�ַ�

    	
    	if (jnlLen <= 5) {
            DebugUtil.println("������ɵ��ַ������Ȳ�������С�ڻ����4!");
            return null;  
        }
    	if (jnlLen >= 9) {
    	 	   DebugUtil.println("������ɵ��ַ������Ȳ������ܴ���9!");
    	        return null; 
    	}
    	
    	
    	//�����������deptFlagΪ�գ���һ����ֵ
    	if(deptFlag == null || deptFlag.equals("")){
    		try {
				deptFlag = CommonUtil.getToday().substring(2,4)+"A";
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			if  (dataBaseFlag.equals("N")){
		    	   vlaueString = year1;
		        
		        
		       }else{
		    	   vlaueString = deptFlag;
		    	   
		       }
			
			
	        int tempInt = 100000000;
	        int nextNo = 0;
	        int tempNum  = 0;
	       
	        tempNum = 9 - jnlLen ;
	        
	        ArrayList list = null;
	        Session session = null;
	        
	        Getnum getnum = new Getnum();
	    	GetnumKey  getnumkey = new GetnumKey();
	    	Transaction tx = null;
	        try {
	        	
	            session = HibernateUtil.getSession();
	            Query query = session
	                    .createQuery("FROM Getnum as a WHERE a.id.year1=:year1 AND a.id.deptflag = :deptflag");
	            query.setString("year1", year1);
	            query.setString("deptflag", deptFlag);
	            
	            tx = session.beginTransaction();
	            list = (ArrayList) query.list();
	          
	            if (list == null || list.isEmpty() || list.size() == 0) {
	                // ��û�м�¼�����кŴ�1��ʼ
	                
	            	
            		String verNO = "";
//            		String[] ver_arr = year1.split("_");
//            		if(ver_arr.length>0){
//            			verNO = ver_arr[1];
//            		}else{
//            			String para = "1";
//                		while(verLen-para.length()>0){
//                			para = "0"+para;
//                		}
//                		verNO = para;
//            		}
            		
            		String para = "1";
            		while(verLen-para.length()>0){
            			para = "0"+para;
            		}
            		verNO = para;
            		
            		
            		nextNo = nextNo + 1;
            		
                	quotano = vlaueString + String.valueOf(tempInt + nextNo).substring(tempNum,9).trim() + "_" + verNO;
                    
                    getnumkey.setYear1(year1);
                    getnumkey.setDeptflag(deptFlag);
                    getnum.setId(getnumkey);
	                
	                
	               
	            } else {
	            	getnum = (Getnum) list.get(0);
                    nextNo = getnum.getNextno().intValue();
                    if (nextNo == 1 ){
                    	nextNo = 2 ; 
                    }else{
                    	nextNo = nextNo + 1;
                    }
                    
                    
                    String verNO1 = "";
//            		String[] ver_arr1 = year1.split("_");
//            		if(ver_arr1.length>0){
//            			verNO1 = ver_arr1[1];
//            		}else{
//            			String para1 = "1";
//                		while(verLen-para1.length()>0){
//                			para1 = "0"+para1;
//                		}
//                		verNO1 = para1;
//            		}
                    String para1 = "1";
            		while(verLen-para1.length()>0){
            			para1 = "0"+para1;
            		}
            		verNO1 = para1;
            		
                    
                    
                    quotano = vlaueString + String.valueOf(tempInt + nextNo).substring(tempNum,9).trim() + "_" + verNO1;
	               
	            }
	            getnum.setNextno(new Integer(nextNo));
	            session.save(getnum);
	            tx.commit();
	        } catch (DataStoreException dex) {
	            try {
	                tx.rollback();
	            } catch (HibernateException e) {
	                // TODO Auto-generated catch block
	                DebugUtil.print(e, "Hibernate Rolenode Save error!");
	            }
	            DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
	        } catch (HibernateException hex) {
	            try {
	                tx.rollback();
	            } catch (HibernateException e) {
	                DebugUtil.print(e, "Hibernate Rolenode Save error!");
	            }
	            DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
	        } finally {
	            try {
	                session.close();
	            } catch (HibernateException hex) {
	                DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
	            }
	        }
		
    	}
    	
    	//�����Դ����Ϊ����
    	else{
    		
    		String[] arr =  jnlno.split("_");
    		String str_after = "";
    		
    		if(arr.length>0){
    			str_after = arr[1];
    		}
    		
    		quotano = deptFlag + "_" + str_after;
    		
    		
    		
    		
    	}

        return quotano;
	
    }
    /*
     * ȡ�������һ���µ�����
     * create by cwr  2006-05-19
     * @parm   ��ǰ���� 
     */
    public static String getPreMonDay(String today)throws ParseException{
		if(today==null || today.length()==0){
			today=CommonUtil.getToday();
		}
		String[] temp=today.split("-");
		int mm=Integer.parseInt(temp[1]);
		if(mm==1){
			int yy=Integer.parseInt(temp[0]);
			temp[0]=(yy-1)+"";
			temp[1]="12";
		}else{
			temp[1]=(mm-1)<10?"0"+(mm-1):(mm-1)+"";
		}
		return temp[0]+"-"+temp[1]+"-"+temp[2];
	}
    
    /*
     * ��ȡ�ַ�"-",�����ַ�"-"�������ַ�,��Ҫ������ȡ��ͬ�����������(parmid)
     * @parm:  parmid�����
     * create by cwr 2006-08-06
     */
    public static String subParmID(String parmid)
    {
    	String subparmid = "";
    	if(parmid != null && !parmid.equals(""))
    	{
    		String[] temp = parmid.split("-");
    		subparmid = temp[temp.length - 1];
    	}
    	return subparmid;
    }
    
    /*
     * ���ڸ�ʽ������,��Ҫ���û����벻�淶�����ڸ�ʽ�淶����ͳһ��ʽ
     * @parm:  date:�û����������    dateFormat:Ҫ��ʽ���ɵ����ڸ�ʽ,Ĭ�ϸ�ʽΪ"yyyy-MM-dd"
     * create by cwr 2006-09-20
     */
    public static String FormatDate(String date,String dateFormat) throws ParseException{
		if(date==null || date.trim().length()==0){
			return "";
		}
		if (dateFormat == null || dateFormat.equals("")
		         || dateFormat.length() == 0) {
		     dateFormat = "yyyy-MM-dd";
		 }
		 SimpleDateFormat df = new SimpleDateFormat(dateFormat);
		return df.format(df.parse(date));
	}

    /**
     * 
     * @param f Ҫ���������
     * @param i Ҫ������С��λ��
     * @return fn
     */
   /* public  static String FormatNumber(float f,int i){
		 String fn="";
	   	 BigDecimal b1 = new BigDecimal(Float.toString(f)); 
		 BigDecimal b2 = new BigDecimal(Double.toString(1));
	     fn=b1.divide(b2, i, BigDecimal.ROUND_HALF_UP).toString();
		 return fn;
    }
    */
    
    /**
     * 
     * @param str_num Ҫ���������
     * @param i Ҫ������С��λ��
     * @return fn
     */
    public  static String FormatNumber(String str_num,int i){
		 String d="0.00";
	   	 BigDecimal b1 = new BigDecimal(str_num); 
		 BigDecimal b2 = new BigDecimal(Double.toString(1));
	     d=b1.divide(b2, i, BigDecimal.ROUND_HALF_UP).toString();
		 return d;
}
    

	/**
	 * ת����ǧ�ַ� 000��000.00����ʽ
	 * @param str_num Ҫת�������� str
	 * @param i ����С��λ��
	 * @return
	 */
    public static String formatThousand(String str_num,int i){
		 String d="0.00";
		 String price="0";
		 double num=0.00;
		 NumberFormat format=NumberFormat.getInstance();
	   	 BigDecimal b1 = new BigDecimal(str_num); 
		 BigDecimal b2 = new BigDecimal(Double.toString(1));
	     d=b1.divide(b2, i, BigDecimal.ROUND_HALF_UP).toString();
//	     //System.out.println("str_num>>>"+str_num);
//	     //System.out.println("d>>>"+d);
	     try {
			num=format.parse(d).doubleValue();//��String ��ת����double��
		} catch (ParseException e) {
			DebugUtil.print("CommonUtil formatThousand is error!");
			e.printStackTrace();
		}
         price=format.format(num);
//         //System.out.println("num>>>"+num);
//         //System.out.println("price>>>"+price);
    	return price;
    }
	/**
		 * ���������ͨ�ó���
		 * 
		 * @param Dight:Ҫ���������
		 * @param How:������С��λ
		 * @return
		 */
	public static float getRound(float Dight, int How) {

			Dight = (float) (Math.round(Dight * Math.pow(10, How)) / Math.pow(10,
					How));
			return Dight;

	}
	
	/**
	 * ���ݷָ����ֽ��ַ��� ����ĳЩ����ķָ����确.��������split�ָ��������ʹ�ô˺��������String[]
	 * 
	 * @param arg
	 * @param split
	 * @return
	 */
	public static String[] getFliter(String arg, String split) {
		if (arg != null) {
			split = split == null || split.trim().length() == 0 ? "." : split;
			StringTokenizer fen = new StringTokenizer(arg, split);
			String[] temp = new String[fen.countTokens()];
			int i = 0;
			while (fen.hasMoreTokens()) {
				temp[i] = fen.nextToken();
				i++;
			}
			return temp;
		} else {
			return null;
		}
	}
	
	/**
	 * ��ʽ��ת�� modulenode ��� url
	 * 
	 * @param url
	 * @return
	 */
	public static String FormatUrl(String url) {
		if (url != null && url.trim().length() > 0) {
			return "/" + SysConfig.WEB_APPNAME + url.substring(2, url.length());
		} else {
			return "";
		}
	}
	
	/**
	 * ��url����
	 * @param url
	 * @param chartset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getUrlEncode(String url,String chartset) throws UnsupportedEncodingException{
		if(url!=null && url.length()>0){
			chartset=chartset==null||chartset.length()==0?"utf-8":chartset.trim();
			return URLEncoder.encode(url,chartset);
		}else{
			return "";
		}
		
	}
	/**
	 * ��url����
	 * @param url
	 * @param chartset
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	public static String getUrlDecode(String url,String chartset) throws UnsupportedEncodingException{
		if(url!=null && url.length()>0){
			chartset=chartset==null||chartset.length()==0?"utf-8":chartset.trim();
			return URLDecoder.decode(url,chartset);
		}else{
			return "";
		}
		
	}
	
	/**
	 * ȡN��������
	 * 
	 * @param date
	 * @return yyyy-mm-dd ��ʽ������
	 * @throws ParseException
	 */
	public static String getNDay(String date,int days) throws ParseException {
		Date sdate = null;
		try {
			String day = date.substring(8, 10);
			int Iday = Integer.parseInt(day);
			Iday = Iday + days;
			date = date.substring(0, 8) + Iday;
			sdate=strToDate(date, "");
			
		} catch (Exception e) {
			DebugUtil.print(e, "?????????��?��??");
		}

		return dateToStr(sdate,"");
		

	}

	public static String strToSQLLikeCondition(String colmun) {
		String rt = "%";
		if(null != colmun && !"".equals(colmun)){
			rt = "%"+colmun.trim()+"%";
		}
		return rt;
	}
	

	/*
	 * �����б�תΪbean�б�
	 */
	public static List<LabelValueBean> toLableValuebean(List<Object[]> listofarray){
	    List<LabelValueBean> colorList = new ArrayList<LabelValueBean>();
	    for(Object[] o:listofarray){
	    	colorList.add(new LabelValueBean((String)o[1],(String)o[0]));
	    }
	    return colorList;
	}

	public static String getName(HttpServletRequest request,String listattrname,String value) {
		List list=(List)request.getAttribute(listattrname);
		for(Object o:list){
			LabelValueBean lvb=(LabelValueBean)o;
			if(lvb.getValue().equals(value)){
				return lvb.getLabel().toString();
			}
		}
		return "";
	}
	
	public static String formatNum(int point, double arg) {
		String rs = "";
		DecimalFormat nf = new DecimalFormat("##.##");
		nf.setMaximumFractionDigits(point);
		nf.setMinimumFractionDigits(point);
		rs = nf.format(arg);
		return rs;
	}
	
	/**
	 * ��ˮ�����ɷ��� = Ĭ����ˮ�ų���Ϊ12λ
	 * @param year1  ��λ���(15)
	 * @param tableName ������
	 * @param count  һ��������ˮ�ŵ�����
	 * @return
	 */
	public synchronized static String[] getBillno(String year1,String tableName, int count) {

		if (count < 1) {
			count = 1;
		}
		
		if (year1 == null || year1.length() != 2) {
            DebugUtil.println("��ݲ������ȴ���!");
            return null;
        }

		int argLen = count;
		String jnlNo[] = new String[argLen];
		double tempInt = 10000000000D;
		int nextNo = 1;
		ArrayList list = null;
		Session session = null;
		Getnum getnum = new Getnum();
		GetnumKey getnumkey = new GetnumKey();
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM Getnum as a WHERE a.id.year1=:year1 AND a.id.deptflag = :deptflag");
			query.setString("year1", year1);
			query.setString("deptflag", tableName);

			list = (ArrayList) query.list();
			if (list == null || list.isEmpty() || list.size() == 0) {
				// ��û�м�¼�����кŴ�1��ʼ
				for (int i = 0; i < argLen; i++) {
					jnlNo[i] = year1+CommonUtil.formatDigitsNum(0, tempInt + nextNo).substring(1).replaceAll(",", "");
					nextNo++;
				}
				getnumkey.setYear1(year1);
                getnumkey.setDeptflag(tableName);
                getnum.setId(getnumkey);
			} else {
				getnum = (Getnum) list.get(0);
				nextNo = getnum.getNextno().intValue();
				for (int i = 0; i < argLen; i++) {
					jnlNo[i] = year1+CommonUtil.formatDigitsNum(0, tempInt + nextNo).substring(1).replaceAll(",", "");
					nextNo++;
				}
			}
			getnum.setNextno(new Integer(nextNo));
			session.save(getnum);
			tx.commit();
			
		} catch (Exception hex) {
			try {
				tx.rollback();
			} catch (HibernateException e) {
				DebugUtil.print(e, "Hibernate Rolenode Save error!");
			}
			DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
		} finally {
			try {
				if(session!=null){
					session.close();
				}
			} catch (HibernateException hex) {
				DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
			}
		}
		return jnlNo;
	}
	
	/**
	 * ��ˮ�����ɷ���
	 * @param year1  ��λ���(15)
	 * @param tableName ������
	 * @param count  һ��������ˮ�ŵ�����
	 * @param len   ��ˮ�ŵĳ���(С�ڵ���10λ)
	 * @return
	 */
	public synchronized static String[] getBillno(String year1,String tableName, int count,int len) {

		if (count < 1) {
			count = 1;
		}
		
		if(len<1){
			len = 1;
		}

		if(len >10){
			len = 10;
		}
		
		if (year1 == null || year1.length() != 2) {
            DebugUtil.println("��ݲ������ȴ���!");
            return null;
        }
		
		int argLen = count;
		String jnlNo[] = new String[argLen];
		double tempInt = 10000000000D;
		int nextNo = 1;
		ArrayList list = null;
		Session session = null;
		Getnum getnum = new Getnum();
		GetnumKey getnumkey = new GetnumKey();
		Transaction tx = null;
		try {
			session = HibernateUtil.getSession();
			tx = session.beginTransaction();
			Query query = session.createQuery("FROM Getnum as a WHERE a.id.year1=:year1 AND a.id.deptflag = :deptflag");
			query.setString("year1", year1);
			query.setString("deptflag", tableName);

			list = (ArrayList) query.list();
			if (list == null || list.isEmpty() || list.size() == 0) {
				// ��û�м�¼�����кŴ�1��ʼ
				for (int i = 0; i < argLen; i++) {
					jnlNo[i] = year1+CommonUtil.formatDigitsNum(0, tempInt + nextNo).substring(1).replaceAll(",", "").substring(10-len);
					nextNo++;
				}
				getnumkey.setYear1(year1);
                getnumkey.setDeptflag(tableName);
                getnum.setId(getnumkey);
			} else {
				getnum = (Getnum) list.get(0);
				nextNo = getnum.getNextno().intValue();
				for (int i = 0; i < argLen; i++) {
					jnlNo[i] = year1+CommonUtil.formatDigitsNum(0, tempInt + nextNo).substring(1).replaceAll(",", "").substring(10-len);
					nextNo++;
				}
			}
			getnum.setNextno(new Integer(nextNo));
			session.save(getnum);
			tx.commit();
		} catch (Exception hex) {
			try {
				tx.rollback();
			} catch (HibernateException e) {
				DebugUtil.print(e, "Hibernate Rolenode Save error!");
			}
			DebugUtil.print(hex, "HibernateUtil��Hibernate Session �򿪳���");
		} finally {
			try {
				session.close();
			} catch (HibernateException hex) {
				DebugUtil.print(hex, "HibernateUtil��Hibernate Session �رճ���");
			}
		}
		return jnlNo;
	}
	/**
	 * ����ѧ����תΪdouble��(�磺1.00585E01) point ���� �ǿ���С������λ�� arg ���� ��Ҫת���Ĳ���
	 * 
	 * @param point
	 * @param arg
	 * @return
	 */
	public static String formatDigitsNum(int point, double arg) {
		String rs = "";
		NumberFormat nf = NumberFormat.getInstance();
		nf.setMaximumFractionDigits(point);
		rs = nf.format(arg);
		return rs;
	}
	
	/**
	 * ���ɱ�� 
	 * ��Ű�����¼+1
	 * �������ɺ�ͬ�ţ�B005A B006A
	 * @param tableName ����
	 * @param colName �������
	 * @param prefix ǰ׺
	 * @param suffix ��׺
	 * @param len ��ų���
	 * @param orderstr ������
	 * @return
	 */	 
	public static String genNo(String tableName, String colName, String prefix, String suffix, int len,String orderstr){
				
		String resultNo = null;
		
		if(tableName != null && !tableName.equals("") 
				&& colName !=null && !colName.equals("")){
			
			Session session = null;
			Connection con = null;
			Statement stmt = null;
			ResultSet rs = null;
						
			char[] middle = new char[len];
			Arrays.fill(middle, '_');
			
			//�г�����ǩ �������Ҫ˳����ȥ��
			String prefix2=prefix.replaceAll("XB", "");
			
			String sql = "select " + colName + " from " + tableName + 
					" where replace("+ colName +",'XB','') like '"+ prefix2 + String.valueOf(middle) + suffix +"'" + 
					" order by replace("+orderstr+",'XB','') desc";

			try {
				
				session = HibernateUtil.getSession();
				con = session.connection();
				stmt = con.createStatement();
				rs = stmt.executeQuery(sql);
				
				//System.out.println(">>>"+sql);
				
				String currentNum = "";
				String currentNum2 = "";
				if(rs.next()){					
					currentNum = rs.getString(colName);
					currentNum2 = currentNum.replaceAll("XB", "");
					currentNum = currentNum2.replace(prefix2, "").replace(suffix, "");
					currentNum = String.format("%0"+len+"d",Integer.parseInt(currentNum) + 1);					
				}else{
					currentNum = String.format("%0"+len+"d",1);
				}
				
				resultNo = prefix + currentNum + suffix;
	
			}catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (rs != null) {
						rs.close();
					}
					if (stmt != null) {
						stmt.close();
					}
					if (con != null) {
						session.close();
					}
					if (session != null) {
						session.close();
					}
				} catch (Exception e) {
					DebugUtil.print(e, "HibernateUtil��Hibernate Session �رճ���");
				}
			}
		}
		
		return resultNo;
	}

    public static String getNowTime(String formatStr){
    	DateFormat sdf = new SimpleDateFormat(formatStr);
        return sdf.format(new Date());
    }
    
    public static String getNowTime(){
    	DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(new Date());
    }
    
    public static String getDateFormatStr(String strDate, String dateFormat){
    	try {
	    	if(strDate == null || strDate.trim().equals("")){
	    		strDate = "";
	    	} else {
	    		if (dateFormat == null || dateFormat.trim().equals("")) {	           
		        	dateFormat = "yyyy-MM-dd";
		        }
	    		DateFormat df = new SimpleDateFormat(dateFormat);	               
		        strDate = df.format(df.parse(strDate));	
	    	} 	        
    	} catch (Exception e) {
    		strDate = "";		
		}       
        return strDate;
    }
    /**
     * ���ɱ����ƻ�
     * @param mcdRowid ά����ϸ�к�
     * @param String maintPersonnel ά����
     * @param ViewLoginUserInfo userInfo
     * @param ActionErrors errors
     * @return
     */	
    public static void  toMaintenanceWorkPlan(String mcdRowid,String maintPersonnel,
    		ViewLoginUserInfo userInfo,ActionErrors errors){
    	Session hs=null;
    	Transaction tx=null;
    	try{
	    	hs = HibernateUtil.getSession();
	    	tx =hs.beginTransaction();
			MaintContractDetail mcd = (MaintContractDetail) hs.get(MaintContractDetail.class, Integer.valueOf(mcdRowid));
	
			String sql="from MaintenanceWorkPlanMaster where rowid= "+mcd.getRowid();
			List mwpmList=hs.createQuery(sql).list();
			if(mcd.getMaintPersonnel()!=null&&!mcd.getMaintPersonnel().equals(""))
			{
				maintPersonnel=mcd.getMaintPersonnel();
			}
		    MaintenanceWorkPlanMaster mwpm=null;
		    if(mwpmList!=null && mwpmList.size()>0)
			{
				mwpm= (MaintenanceWorkPlanMaster) mwpmList.get(0);
		    
				Integer halfMonth=null;
				Integer quarter=null;
				Integer halfYear=null;
				Integer yearDegree=null;
	
				//����վ�Ƿ��б�����ʱ
				String hql="select mwh.halfMonth,mwh.quarter,mwh.halfYear,mwh.yearDegree " +
						"from MaintenanceWorkingHours mwh where mwh.id.elevatorType= '"+mcd.getElevatorType()+"'" +
						" and mwh.id.floor = '"+mcd.getFloor()+"'";
				List list=hs.createQuery(hql).list();
				if(list!=null && list.size()>0)
				{
					String maintlogic=mwpm.getMaintLogic();//�����߼�
					//hs.save(mwpm); 
					//hs.flush();
					
					Object[] objects= (Object[]) list.get(0);
					 halfMonth=(Integer) objects[0];
					 quarter=(Integer) objects[1];
					 halfYear=(Integer) objects[2];
					 yearDegree=(Integer) objects[3];
					 
					if(mcd.getDelayEDate()!=null && !mcd.getDelayEDate().equals("")){
						//�ӳ������ƻ�,�̶�Ϊ���±���(����)
						String eDate=mcd.getDelayEDate();//�ӱ���������
						String date="";
						String sql1="select max(mwpd.maintDate) from MaintenanceWorkPlanDetail mwpd" +
								" where mwpd.maintenanceWorkPlanMaster.billno ='"+mwpm.getBillno()+"'";
						List mwpdList=hs.createQuery(sql1).list();
						if(mwpdList!=null&&mwpdList.size()>0)
						{
						   date=(String) mwpdList.get(0);
						}
						
						if(date!=null && !date.trim().equals("")){
							//�ӱ���ͳһΪ���±���
							date=DateUtil.getDate(date, "dd", 14);
							for(int a=1;DateUtil.compareDay(date,eDate)>0;a++)
							{
								MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
								mwpd.setMaintType("halfMonth");//���±���(����)
								mwpd.setMaintDateTime(halfMonth.toString());
								mwpd.setMaintenanceWorkPlanMaster(mwpm);
								mwpd.setMaintDate(date);
							    mwpd.setOperId(userInfo.getUserID());
							    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
							    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
							    mwpd.setMaintPersonnel(mcd.getMaintPersonnel());
							    hs.save(mwpd);
							    hs.flush();
							    date=DateUtil.getDate(date, "dd", 14);
								/**
								 if(DateUtil.getWeek(date).equals("������")){
									date=DateUtil.getDate(date, "dd", -1);
							 	 }		
								*/		
							}
						}
					
					}else{
						//���������ƻ�
						//String date=mcd.getMainSdate();//ά����ʼ����
						String date=mcd.getShippedDate();//ά���ƻ���ʼ����
						String eDate=mcd.getMainEdate();//ά����������
						
						//ɾ�������ƻ����ų��Ѿ��б������ŵ�
						String sql1="delete MaintenanceWorkPlanDetail mwpd " +
								"where mwpd.maintenanceWorkPlanMaster.billno ='"+mwpm.getBillno()+"' " +
								"and isnull(mwpd.singleno,'')=''"; 
						hs.createQuery(sql1).executeUpdate();
						hs.flush();
						
						//����ά����ʼ���ڣ�ɾ�����ݱ�ţ�֮ǰ�ı����ƻ����ų��Ѿ��б������ŵ�
						String delete="from MaintenanceWorkPlanDetail as mwpd " +
								"where mwpd.maintenanceWorkPlanMaster.elevatorNo='"+mcd.getElevatorNo()+"' " +
										"and mwpd.maintDate >= '"+date+"' and isnull(mwpd.singleno,'')=''"; 
						//System.out.println(delete);
						List deleteList=hs.createQuery(delete).list();
						if(deleteList!=null&&deleteList.size()>0){
							for(int i=0;i<deleteList.size();i++){
								hs.delete((MaintenanceWorkPlanDetail)deleteList.get(i));
							}
						}
						hs.flush();
						
						//�����߼�2,3,4,5 ����n�����±���
						if(maintlogic!=null && !maintlogic.trim().equals("")){
							int loc=Integer.parseInt(maintlogic);//�����߼�ѡ��
							for(int a3=0;a3<loc-1;a3++){
								MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
								mwpd.setMaintType("halfMonth");//���±���(����)
								mwpd.setMaintDateTime(halfMonth.toString());
								mwpd.setMaintenanceWorkPlanMaster(mwpm);
							    mwpd.setMaintDate(date);
							    mwpd.setOperId(userInfo.getUserID());
							    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
							    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
							    mwpd.setMaintPersonnel(maintPersonnel);
							    hs.save(mwpd); 
							    hs.flush();
				                date=DateUtil.getDate(date, "dd", 14);
							}
						}
						
						//�����߼�1
						int bylen=53;//����ı����ƻ�ѭ��
						for(int a=1;DateUtil.compareDay(date,eDate)>0;a++)
						{
							MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
							if(a%bylen==1 || a%bylen==28){
								//1,28 ���
								mwpd.setMaintType("yearDegree");//��ȱ���(����)
								mwpd.setMaintDateTime(yearDegree.toString());
							}else if(a%bylen==14 || a%bylen==41){
								//14,41 ����
								mwpd.setMaintType("halfYear");//���걣��(����)
								mwpd.setMaintDateTime(halfYear.toString());
							}else if(a%bylen==8 || a%bylen==21 || a%bylen==34 || a%bylen==47){
								//8,21,34,47 ����
								mwpd.setMaintType("quarter");//���ȱ���(����)
								mwpd.setMaintDateTime(quarter.toString());
							}else{
								//����
								mwpd.setMaintType("halfMonth");//���±���(����)
								mwpd.setMaintDateTime(halfMonth.toString());
							}
						    mwpd.setMaintenanceWorkPlanMaster(mwpm);
						    mwpd.setMaintDate(date);
						    mwpd.setOperId(userInfo.getUserID());
						    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
						    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
						    mwpd.setMaintPersonnel(maintPersonnel);
						    hs.save(mwpd); 
						    hs.flush();
			                date=DateUtil.getDate(date, "dd", 14);
							/**
							 if(DateUtil.getWeek(date).equals("������"))
							 {
								date=DateUtil.getDate(date, "dd", -1);
							 }
							*/
							
						}
						
						//Ĭ���½�ά����ҵ�ƻ�������3���µ�ά����
						String eDate2=DateUtil.getDate(eDate, "MM", 3);//��ǰ�����·ݼ�1 ��
						for(int a2=1;DateUtil.compareDay(date,eDate2)>0;a2++)
						{
							MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
							mwpd.setMaintType("halfMonth");//���±���(����)
							mwpd.setMaintDateTime(halfMonth.toString());
							mwpd.setMaintenanceWorkPlanMaster(mwpm);
						    mwpd.setMaintDate(date);
						    mwpd.setOperId(userInfo.getUserID());
						    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
						    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
						    mwpd.setMaintPersonnel(maintPersonnel);
						    hs.save(mwpd); 
						    hs.flush();
			                date=DateUtil.getDate(date, "dd", 14);
							/**
							 if(DateUtil.getWeek(date).equals("������")){
								date=DateUtil.getDate(date, "dd", -1);
						 	 }		
							*/		
						}
						
					}	
				}else{
					if(errors.isEmpty()){
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>&nbsp;&nbsp;��:"+mcd.getFloor()+", �Ҳ����õ��ݲ����ı���ʱ��,���ܱ���!</font>"));
					}
				}
			}
			 tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		if(errors.isEmpty()){
    			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","����ά����ҵ�ƻ�ʧ�ܣ�"));
    		}
    		if(tx!=null){tx.rollback();}
     	}finally{
    		if(hs!=null){hs.close();}
    	}

		 
    }
    /**
     * ���ɱ����ƻ�
     * @param mcdRowid ά����ϸ�к�
     * @param String maintPersonnel ά����
     * @param ViewLoginUserInfo userInfo
     * @param ActionErrors errors
     * @return
     */	
    public static void  toMaintenanceWorkPlan_old(String mcdRowid,String maintPersonnel,
    		ViewLoginUserInfo userInfo,ActionErrors errors) 
    {
    	Session hs=null;
    	Transaction tx=null;
    	try{
	    	hs = HibernateUtil.getSession();
	    	tx =hs.beginTransaction();
			MaintContractDetail mcd = (MaintContractDetail) hs.get(MaintContractDetail.class, Integer.valueOf(mcdRowid));
	
			String sql="from MaintenanceWorkPlanMaster where rowid= "+mcd.getRowid();
			List mwpmList=hs.createQuery(sql).list();
			if(mcd.getMaintPersonnel()!=null&&!mcd.getMaintPersonnel().equals(""))
			{
				maintPersonnel=mcd.getMaintPersonnel();
			}
		    MaintenanceWorkPlanMaster mwpm=null;
		    if(mwpmList!=null && mwpmList.size()>0)
			{
				mwpm= (MaintenanceWorkPlanMaster) mwpmList.get(0);
		    
				Integer halfMonth=null;
				Integer quarter=null;
				Integer halfYear=null;
				Integer yearDegree=null;
	
				//����վ�Ƿ��б�����ʱ
				String hql="select mwh.halfMonth,mwh.quarter,mwh.halfYear,mwh.yearDegree " +
						"from MaintenanceWorkingHours mwh where mwh.id.elevatorType= '"+mcd.getElevatorType()+"'" +
						" and mwh.id.floor = '"+mcd.getFloor()+"'";
				List list=hs.createQuery(hql).list();
				if(list!=null && list.size()>0)
				{
					String maintlogic=mwpm.getMaintLogic();//�����߼�
					//hs.save(mwpm); 
					//hs.flush();
					
					Object[] objects= (Object[]) list.get(0);
					 halfMonth=(Integer) objects[0];
					 quarter=(Integer) objects[1];
					 halfYear=(Integer) objects[2];
					 yearDegree=(Integer) objects[3];
					 
					if(mcd.getDelayEDate()!=null && !mcd.getDelayEDate().equals("")){
						//�ӳ������ƻ�,�̶�Ϊ���±���(����)
						String eDate=mcd.getDelayEDate();//�ӱ���������
						String date="";
						String sql1="select max(mwpd.maintDate) from MaintenanceWorkPlanDetail mwpd" +
								" where mwpd.maintenanceWorkPlanMaster.billno ='"+mwpm.getBillno()+"'";
						List mwpdList=hs.createQuery(sql1).list();
						if(mwpdList!=null&&mwpdList.size()>0)
						{
						   date=(String) mwpdList.get(0);
						}
						
						if(date!=null && !date.trim().equals("")){
							//�ӱ���ͳһΪ���±���
							date=DateUtil.getDate(date, "dd", 15);
							for(int a=1;DateUtil.compareDay(date,eDate)>0;a++)
							{
								MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
								mwpd.setMaintType("halfMonth");//���±���(����)
								mwpd.setMaintDateTime(halfMonth.toString());
								mwpd.setMaintenanceWorkPlanMaster(mwpm);
								mwpd.setMaintDate(date);
							    mwpd.setOperId(userInfo.getUserID());
							    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
							    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
							    mwpd.setMaintPersonnel(mcd.getMaintPersonnel());
							    hs.save(mwpd);
							    hs.flush();
							    date=DateUtil.getDate(date, "dd", 15);
								/**
								 if(DateUtil.getWeek(date).equals("������")){
									date=DateUtil.getDate(date, "dd", -1);
							 	 }		
								*/		
							}
						}
					
					}else{
						//���������ƻ�
						//String date=mcd.getMainSdate();//ά����ʼ����
						String date=mcd.getShippedDate();//ά���ƻ���ʼ����
						String eDate=mcd.getMainEdate();//ά����������
						
						//ɾ�������ƻ����ų��Ѿ��б������ŵ�
						String sql1="delete MaintenanceWorkPlanDetail mwpd " +
								"where mwpd.maintenanceWorkPlanMaster.billno ='"+mwpm.getBillno()+"' " +
								"and isnull(mwpd.singleno,'')=''"; 
						hs.createQuery(sql1).executeUpdate();
						hs.flush();
						
						//����ά����ʼ���ڣ�ɾ�����ݱ�ţ�֮ǰ�ı����ƻ����ų��Ѿ��б������ŵ�
						String delete="from MaintenanceWorkPlanDetail as mwpd " +
								"where mwpd.maintenanceWorkPlanMaster.elevatorNo='"+mcd.getElevatorNo()+"' " +
										"and mwpd.maintDate >= '"+date+"' and isnull(mwpd.singleno,'')=''"; 
						//System.out.println(delete);
						List deleteList=hs.createQuery(delete).list();
						if(deleteList!=null&&deleteList.size()>0){
							for(int i=0;i<deleteList.size();i++){
								hs.delete((MaintenanceWorkPlanDetail)deleteList.get(i));
							}
						}
						hs.flush();
						
						//�����߼�2,3,4,5 ����n�����±���
						if(maintlogic!=null && !maintlogic.trim().equals("")){
							int loc=Integer.parseInt(maintlogic);//�����߼�ѡ��
							for(int a3=0;a3<loc-1;a3++){
								MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
								mwpd.setMaintType("halfMonth");//���±���(����)
								mwpd.setMaintDateTime(halfMonth.toString());
								mwpd.setMaintenanceWorkPlanMaster(mwpm);
							    mwpd.setMaintDate(date);
							    mwpd.setOperId(userInfo.getUserID());
							    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
							    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
							    mwpd.setMaintPersonnel(maintPersonnel);
							    hs.save(mwpd); 
							    hs.flush();
				                date=DateUtil.getDate(date, "dd", 15);
							}
						}
						
						//�����߼�1 ԭ���ı����߼�
						for(int a=1;DateUtil.compareDay(date,eDate)>0;a++)
						{
							MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
							if(a%24==1){
								//�����ǰ����Ϊ��ȱ�����������һ��15���ͬδ���ڵģ���Ϊ��ȱ�������Ϊ���±���
								String datek=DateUtil.getDate(date, "dd", 15);
								if(DateUtil.compareDay(datek,eDate)>0){
									mwpd.setMaintType("yearDegree");//��ȱ���(����)
									mwpd.setMaintDateTime(yearDegree.toString());
								}else{
									mwpd.setMaintType("halfMonth");//���±���(����)
									mwpd.setMaintDateTime(halfMonth.toString());
								}
							}else if(a%24==7){
								mwpd.setMaintType("quarter");//���ȱ���(����)
								mwpd.setMaintDateTime(quarter.toString());
							}else if(a%24==13){
								mwpd.setMaintType("halfYear");//���걣��(����)
								mwpd.setMaintDateTime(halfYear.toString());
							}else if(a%24==19){
								mwpd.setMaintType("quarter");//���ȱ���(����)
								mwpd.setMaintDateTime(quarter.toString());
							}else{
								mwpd.setMaintType("halfMonth");//���±���(����)
								mwpd.setMaintDateTime(halfMonth.toString());
							}
						    mwpd.setMaintenanceWorkPlanMaster(mwpm);
						    mwpd.setMaintDate(date);
						    mwpd.setOperId(userInfo.getUserID());
						    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
						    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
						    mwpd.setMaintPersonnel(maintPersonnel);
						    hs.save(mwpd); 
						    hs.flush();
			                date=DateUtil.getDate(date, "dd", 15);
							/**
							 if(DateUtil.getWeek(date).equals("������"))
							 {
								date=DateUtil.getDate(date, "dd", -1);
							 }
							*/
							
						}
						
						//Ĭ���½�ά����ҵ�ƻ�������3���µ�ά����
						String eDate2=DateUtil.getDate(eDate, "MM", 3);//��ǰ�����·ݼ�1 ��
						for(int a2=1;DateUtil.compareDay(date,eDate2)>0;a2++)
						{
							MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
							mwpd.setMaintType("halfMonth");//���±���(����)
							mwpd.setMaintDateTime(halfMonth.toString());
							mwpd.setMaintenanceWorkPlanMaster(mwpm);
						    mwpd.setMaintDate(date);
						    mwpd.setOperId(userInfo.getUserID());
						    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
						    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
						    mwpd.setMaintPersonnel(maintPersonnel);
						    hs.save(mwpd); 
						    hs.flush();
			                date=DateUtil.getDate(date, "dd", 15);
							/**
							 if(DateUtil.getWeek(date).equals("������")){
								date=DateUtil.getDate(date, "dd", -1);
						 	 }		
							*/		
						}
						
					}	
				}else{
					if(errors.isEmpty()){
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>&nbsp;&nbsp;��:"+mcd.getFloor()+", �Ҳ����õ��ݲ����ı���ʱ��,���ܱ���!</font>"));
					}
				}
				//if(mcd.getMaintPersonnel()==null||mcd.getMaintPersonnel().equals("")){
				//	if(errors.isEmpty()) {  
				//		mcd.setMaintPersonnel(maintPersonnel);
				//		mcd.setAssignedSignFlag("Y");
				//		mcd.setAssignedSign(userInfo.getUserID());
				//		mcd.setAssignedSignDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
				//		hs.update(mcd);
				//	  }		
				//}
			}else{
				//String year1=CommonUtil.getToday().substring(2,4);
				//String[] billno=CommonUtil.getBillno(year1, "MaintenanceWorkPlanMaster", 1);
				//mwpm=new MaintenanceWorkPlanMaster();
				//mwpm.setRowid(mcd.getRowid());
				//mwpm.setBillno(billno[0]);
				//mwpm.setElevatorNo(mcd.getElevatorNo());
				//mwpm.setMaintLogic("1");
				
				//if(errors.isEmpty()){
				//	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>û��ά����ҵ�ƻ�!</font>"));
				//}
			}
			 tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		if(errors.isEmpty()){
    			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","����ά����ҵ�ƻ�ʧ�ܣ�"));
    		}
    		if(tx!=null){tx.rollback();}
     	}finally{
    		if(hs!=null){hs.close();}
    	}

		 
    }
    /**
     * ���ɱ����ƻ�
     * @param mcdRowid ά����ϸ�к�
     * @param String maintPersonnel ά����
     * @param ViewLoginUserInfo userInfo
     * @param ActionErrors errors
     * @param mainEdate ��ά����ͬ��������
     * @return
     */	
    public static void  toMaintenanceWorkPlan2(String mcdRowid,String maintPersonnel,
    		ViewLoginUserInfo userInfo,ActionErrors errors,String mainEdate) 
    {
    	Session hs=null;
    	Transaction tx=null;
    	try{
	    	hs = HibernateUtil.getSession();
	    	tx =hs.beginTransaction();
			MaintContractDetail mcd = (MaintContractDetail) hs.get(MaintContractDetail.class, Integer.valueOf(mcdRowid));
	
			String sql="from MaintenanceWorkPlanMaster where rowid= "+mcd.getRowid();
			List mwpmList=hs.createQuery(sql).list();
			if(mcd.getMaintPersonnel()!=null&&!mcd.getMaintPersonnel().equals(""))
			{
				maintPersonnel=mcd.getMaintPersonnel();
			}
		    MaintenanceWorkPlanMaster mwpm=null;
		    if(mwpmList!=null && mwpmList.size()>0)
			{
		    	mwpm= (MaintenanceWorkPlanMaster) mwpmList.get(0);
		    	
				Integer halfMonth=null;
				Integer quarter=null;
				Integer halfYear=null;
				Integer yearDegree=null;
	
				//����վ�Ƿ��б�����ʱ
				String hql="select mwh.halfMonth,mwh.quarter,mwh.halfYear,mwh.yearDegree " +
						"from MaintenanceWorkingHours mwh where mwh.id.elevatorType= '"+mcd.getElevatorType()+"'" +
						" and mwh.id.floor = '"+mcd.getFloor()+"'";
				List list=hs.createQuery(hql).list();
				if(list!=null && list.size()>0)
				{
					
					Object[] objects= (Object[]) list.get(0);
					 halfMonth=(Integer) objects[0];
					 quarter=(Integer) objects[1];
					 halfYear=(Integer) objects[2];
					 yearDegree=(Integer) objects[3];

					//ɾ��ά����ʼ����֮��ı����ƻ�  ��20180605�����Ѿ������ļƻ���
					String delete="from MaintenanceWorkPlanDetail as mwpd " +
							"where mwpd.maintenanceWorkPlanMaster.rowid="+mcdRowid+
									" and mwpd.maintDate>'"+mainEdate+"' and isnull(mwpd.singleno,'')=''"; 
					//System.out.println("1>>>"+delete);
					List deleteList=hs.createQuery(delete).list();
					if(deleteList!=null && deleteList.size()>0){
						for(int i=0;i<deleteList.size();i++){
							hs.delete((MaintenanceWorkPlanDetail)deleteList.get(i));
						}
					}
					hs.flush();
					
					//��ȡ����ά���ƻ�����
					boolean ismaintdate=false;
					String date=mainEdate;//ά���ƻ���ʼ����
					String sql1="select isnull(max(mwpd.maintDate),'') from MaintenanceWorkPlanDetail mwpd" +
							" where mwpd.maintenanceWorkPlanMaster.rowid="+mcdRowid+" and mwpd.maintDate<='"+mainEdate+"'";
					//System.out.println("2>>>"+sql1);
					List mwpdList=hs.createQuery(sql1).list();
					if(mwpdList!=null && mwpdList.size()>0)
					{
						String datek=(String) mwpdList.get(0);
						if(!"".equals(datek)){
						   date=datek;
						   date=DateUtil.getDate(date, "dd", 14);
						   ismaintdate=true;
						}
					}
					
					if(ismaintdate){
						//�Ѿ����ڱ����ƻ������������ƻ�
						 /**
							�Ӻ�ͬ�޸Ľ����޸ĺ�ͬ�������ڣ������ƻ���������Ϊ��ͬ��������+3���£�
						   	ͨ���޸����պϸ������޸ĺ�ͬ�������ڣ������ƻ���������Ϊ��ͬ��������+3���£�
						 */
						//Ĭ���½�ά����ҵ�ƻ�������3���µ�ά����
						String eDate2=DateUtil.getDate(mainEdate, "MM", 3);//��ǰ�����·ݼ�1 ��
						//System.out.println(mainEdate+">>>"+date+">>>"+eDate2);
						for(int a2=1;DateUtil.compareDay(date,eDate2)>0;a2++)
						{
							MaintenanceWorkPlanDetail mwpd=new MaintenanceWorkPlanDetail();
							mwpd.setMaintType("halfMonth");//���±���(����)
							mwpd.setMaintDateTime(halfMonth.toString());
							mwpd.setMaintenanceWorkPlanMaster(mwpm);
						    mwpd.setMaintDate(date);
						    mwpd.setOperId(userInfo.getUserID());
						    mwpd.setOperDate(DateUtil.getNowTime("yyyy-MM-dd HH:mm:ss"));
						    mwpd.setWeek(DateUtil.getWeek(date).substring(2, 3));
						    mwpd.setMaintPersonnel(maintPersonnel);
						    hs.save(mwpd); 
						    hs.flush();
			                date=DateUtil.getDate(date, "dd", 14);
							/**
							 if(DateUtil.getWeek(date).equals("������")){
								date=DateUtil.getDate(date, "dd", -1);
						 	 }		
							*/		
						}
					}else{
						//if(errors.isEmpty()){
						//	errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","���ݱ�ţ�"+mcd.getElevatorNo()+"���Ҳ��������ƻ���"));
						//}
						System.out.println("ά����ͬ�޸� >>>���ݱ�ţ�"+mcd.getElevatorNo()+"���Ҳ��������ƻ���");
					}

				}else{
					if(errors.isEmpty()){
						errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","<font color='red'>&nbsp;&nbsp;��:"+mcd.getFloor()+", �Ҳ����õ��ݲ����ı���ʱ��,���ܱ���!</font>"));
					}
				}
			}
			 tx.commit();
    	}catch(Exception e){
    		e.printStackTrace();
    		if(errors.isEmpty()){
    			errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","����ά����ҵ�ƻ�ʧ�ܣ�"));
    		}
    		if(tx!=null){tx.rollback();}
     	}finally{
    		if(hs!=null){hs.close();}
    	}

		 
    }
    
    public static void deleMaintenanceWorkPlan(String historyBillNo,String billNo,String date,
    		ViewLoginUserInfo userInfo,ActionErrors errors){
    	Session hs=null;
    	Transaction tx=null;
    	try {
			hs=HibernateUtil.getSession();
			tx=hs.beginTransaction();
			
			if(historyBillNo!=null && !"".equals(historyBillNo)){
				System.out.println(">>>>��ǩ��ͬ���ʱ���˱��ĵ��ݱ�ţ�ɾ����ά�������ƻ�");
				
				//�˱��ĵ��ݱ�ţ�ɾ����ά�������ƻ�
				String hql="delete a from MaintenanceWorkPlanDetail a," +
						"(select a.billno,b.RealityEDate " +
						"from MaintenanceWorkPlanMaster a,MaintContractDetail b " +
						"where a.rowid=b.rowid and b.billNo in ('"+historyBillNo+"') " +
						"and isnull(IsSurrender,'N')='Y') b " +
						//"and a.elevatorNo not in(select elevatorNo from MaintContractDetail where billNo='"+billNo+"')) b " +
						"where a.billno=b.billno and a.MaintDate>b.RealityEDate";
				//System.out.println(">>>>>>>>>>>>>>"+hql);
				int delnum=hs.connection().prepareStatement(hql).executeUpdate();
				//System.out.println(">>>>>ɾ��������"+delnum);
			}else{
				System.out.println(">>>>�˱����ʱ���˱��ĵ��ݱ�ţ�ɾ����ά�������ƻ�");
				
				//�˱�ʱ��ɾ���˱�����֮���ά���ƻ�
				String hqlkk="delete a from MaintenanceWorkPlanDetail a," +
						"(select a.billno from MaintenanceWorkPlanMaster a,MaintContractDetail b " +
						"where a.rowid=b.rowid and b.billNo='"+billNo+"') b " +
						"where a.billno=b.billno and a.MaintDate>'"+date+"'";
				//System.out.println(">>>>>>>>>>>>>>"+hqlkk);
				int delnum=hs.connection().prepareStatement(hqlkk).executeUpdate();
				//System.out.println(">>>>>ɾ��������"+delnum);
	    	}
			tx.commit();
			
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			if(errors.isEmpty()){
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","ɾ��������ҵ�ƻ�ʧ�ܣ�"));
			}
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
    }
    //���պ�ͬ��ɾ�������ƻ�
    public static void deleMaintenanceWorkPlan2(String rowid,String mainedate,
    		ViewLoginUserInfo userInfo,ActionErrors errors){
    	Session hs=null;
    	Transaction tx=null;
    	try {
			hs=HibernateUtil.getSession();
			tx=hs.beginTransaction();
			
			if(rowid!=null && !"".equals(rowid)){
				String delsql="delete b from MaintenanceWorkPlanMaster a, MaintenanceWorkPlanDetail b " +
						"where a.billno=b.billno and a.rowid="+rowid+" and b.MaintDate>'"+mainedate+"'";
				int delnum=hs.connection().prepareStatement(delsql).executeUpdate();
			}
			tx.commit();
			
		} catch (Exception e) {
			if(tx!=null){
				tx.rollback();
			}
			e.printStackTrace();
			if(errors.isEmpty()){
				errors.add(ActionErrors.GLOBAL_ERROR, new ActionError("error.string","ɾ��������ҵ�ƻ�ʧ�ܣ�"));
			}
		}finally{
			if(hs!=null){
				hs.close();
			}
		}
    }
    
    /**
   	 * ����������������ķ���
   	 * @param String
   	 * @param String
   	 * @return int
   	 * @throws Exception
   	 */
    public static double getMinute(String str1,String str2)
   	{
   	 SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
     long minute = 0;
     try {
         java.util.Date date = myFormatter.parse(str1);
         java.util.Date mydate = myFormatter.parse(str2);
         minute = (date.getTime() - mydate.getTime()) / (60 * 1000);
     } catch (Exception e) {
    	 return 0;
     }
     
     return (double) Math.abs(minute);
   		
   	}
    
    /**
	 * ���ɱ����ƻ�����
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return String
     * @throws Exception 
	 */
	public static String getNewSingleno(Session hs,String userId) throws Exception
	{
		   String sigleno ="";
		   String hql="from ViewLoginUserInfo where userID='"+userId.trim()+"'";
		   List userList=hs.createQuery(hql).list();
	       if(userList!=null&&userList.size()>0){
	    	   ViewLoginUserInfo user=(ViewLoginUserInfo) userList.get(0);
			   final String STR_FORMAT = "00000";   
		       sigleno =DateUtil.getNowTime("yyyyMM")+user.getComID();
		       //_____ 5���»���
			   String sql="select MAX(mwpd.singleno) from MaintenanceWorkPlanDetail mwpd where mwpd.singleno like '"+sigleno+"_____' ";
			   List list=hs.createSQLQuery(sql).list();
			   String no=(String) list.get(0);
			   if(no!=null&&!no.equals(""))
			   { 
				   Integer intHao = Integer.parseInt(no.substring(no.length()-5,no.length()));
				   intHao++;  
				   DecimalFormat df = new DecimalFormat(STR_FORMAT);  
				   sigleno+=df.format(intHao);
			   }else
			   {
				   sigleno+="00001";
			   }  
	       }else{
	    	   throw new Exception();
	       }
	    return sigleno;	
	}
    
	/**
	 * ��ȡ��һ�α����ƻ�����
	 * @param String MaintenanceWorkPlanDetail.numno
	 * @param String MaintenanceWorkPlanMaster.billno
	 * @return String 
	 */
	public static String togetAuditCircu(String str1,String str2)
   	{
     Session hs = null;
     String sMaintEndTime="";
    try {
		hs = HibernateUtil.getSession();
		String sql="select mwpd.maintEndTime from MaintenanceWorkPlanDetail mwpd where mwpd.numno=(select numno-1 from MaintenanceWorkPlanDetail where numno='"+str1+"' and billno='"+str2+"' and numno !=(select MIN(numno)from MaintenanceWorkPlanDetail where billno='"+str2+"'))";
		List list=hs.createSQLQuery(sql).list();
	    if(list!=null&&list.size()>0){
	    	sMaintEndTime=(String) list.get(0);
			sMaintEndTime=sMaintEndTime.substring(0, 10);	
	    }
    } catch (DataStoreException e) {
		e.printStackTrace();
	 }finally {
		 if(hs!=null){
			hs.close();
		 }
	 }
		return sMaintEndTime;
   	}
	
	/**
	 * �ֻ�App������ת��
	 * @param str1 �������
	 * @return String 
	 */
	public static String URLDecoder_decode(String str1)
   	{
		String decode="";
      try {
    	  decode=URLDecoder.decode(URLDecoder.decode(str1, "UTF-8"),"UTF-8");
    	  byte[] b = decode.getBytes("gbk");//����  
    	  decode = new String(b, "gbk");	  
      } catch (Exception e) {
		e.printStackTrace();
	 }
	return decode;
   	}
	
	/**
	 * �ֻ�App������ת��
	 * @param str1 �������
	 *  @param str2 ת���ʽ
	 * @return String 
	 */
	public static String URLDecoder_decode(String str1,String str2)
   	{
		String decode="";
      try {
    	  decode=URLDecoder.decode(URLDecoder.decode(str1, str2),str2);
	 } catch (Exception e) {
		e.printStackTrace();
	 }
	return decode;
   	}
	
	/**
	 * �ֻ�App ���"����"����
	 * @param data �ֻ�����json
	 *  @param jobiArray listJson
	 * @return 
	 * @return String 
	 * @throws JSONException 
	 */
	public static JSONArray Pagination(JSONObject data,JSONArray jobiArray) throws JSONException
   	{
		int pagelen=50; //(Integer) data.get("pagelen");//ÿһҳ������
        int pageno= (Integer) data.get("pageno");  //��ǰ������
        int start = pageno, end = pageno + pagelen - 1;
	      end = end>jobiArray.length()? jobiArray.length():end;
	      JSONArray newJobiArray = new JSONArray();//�������� []
	      int ii=0;
	      for(int i=start; i<=end; i++) {
	    	  if(i<jobiArray.length()){
	    		  newJobiArray.put(ii,jobiArray.get(i));
	    		  ii++;
	    	  }
	     }

  		return newJobiArray;
   	}
	
	/**
	 * ͼƬ��byte����
	 * @param path
	 * @return
	 */
	  public static byte[] imageToByte(String path){
	    byte[] data = null;
	    FileImageInputStream input = null;
	    ByteArrayOutputStream output = null;
	    try {
	      input = new FileImageInputStream(new File(path));
	      output = new ByteArrayOutputStream();
	      
	      byte[] buf = new byte[1024];
	      int numBytesRead = 0;
	      while ((numBytesRead = input.read(buf)) != -1) {
	    	  output.write(buf, 0, numBytesRead);
	      }
	      data = output.toByteArray();
	      output.close();
	      input.close();
	    }catch (FileNotFoundException ex1) {
	      ex1.printStackTrace();
	    }catch (Exception ex1) {
	      ex1.printStackTrace();
	    }
	    return data;
	  }

	
}




