/*
 * Created on 2005-8-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.dataimport.importutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * ͨ�÷���Ч��
 * @author rr
 *
 */
public class ComUtil {

    private static ComUtil ComUtil = new ComUtil();

    private ComUtil() {
    }

    public static ComUtil getInstance() {
        return ComUtil;
    }


   /*******************���÷�����start*******************************************/
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
    public static Date formatStrToDate(String strDate, String dateFormat)
            throws ParseException {

        if (dateFormat == null || dateFormat.equals("")
                || dateFormat.length() == 0) {
            dateFormat = "yyyy-MM-dd";
        }
        SimpleDateFormat df = new SimpleDateFormat(dateFormat);

        return df.parse(strDate);
    }
  /**
   * Ч���Ƿ��ǺϷ������ڣ�����ǣ�����ת����ָ������Ӧ�ĸ�ʽ
   * @param strDate
   * @param dateFormat
   * @return
   */
  public static String  strToDate(String strDate, String dateFormat)
          throws ParseException {

      if (dateFormat == null || dateFormat.equals("")
              || dateFormat.length() == 0) {
          dateFormat = "yyyy-MM-dd";
      }
      SimpleDateFormat df = new SimpleDateFormat(dateFormat);

      return df.parse(strDate).toString();
  }
  
  /**
   * У���Ƿ��Ǵ�����ַ����Ƿ�ֻ�������֣�0��9��
   * ������Ϊ�ջ򳤶�Ϊ0ʱ������false
   * @param argValue
   * @return boolean
   * 
   */
  public static boolean isAllDigits(String argValue){
      
      String validChars = "0123456789";
      
      if(argValue == null || argValue.length() == 0) return false;
      
      for(int i=0; i<argValue.length(); i++){
          if(validChars.indexOf(argValue.charAt(i)) == -1) return false;    
      }

      return true;
  }
  /**
   * Ч���Ƿ��ǺϷ��ı�ʶ
   * @param id
   * @param flag
   */
  public static boolean isLegalFlag(String id,String[] flag){
	  String[] fl={"Y","N"};
	  if(flag==null||flag.length==0){
		  flag=fl;
	  }
	  for(int i=flag.length-1;i>=0;i--){
		  if(flag[i].equals(id)){
			 return true;
		  }
	  }
	  return false;  
  }
   /**
    * ��ʼ������Ƿ��ǵ�
    * @param st
    * @param en
    * @return
    */
   public static boolean isStartOrEndIsPoint(char st,char en){
	   if(isPoint(st) || isPoint(en)){
		   return true;
	   }else{
		   return false;
	   }
   }
   /**
    * Ч��Ӣ����ĸ
    * @param ch
    * @return
    */
   public static boolean isENChar(char ch){
	   if((ch>='a'&& ch<='z')||(ch>='A'&&ch<='Z')){
		   return true;
	   }else{
		   return false;
	   }
   }
   /**
    * Ч���
    * @param ch
    * @return
    */
   public static boolean isPoint(char ch){
	   if(ch=='.'){
		   return true;
	   }else{
		   return false;
	   }
   }
   /**
    * Ч������
    * @param ch
    * @return
    */
   
   public static boolean isNum(char ch){
	   if(ch>='0'&&ch<='9'){
		   return true;
	   }else{
		   return false;
	   }
   }
   /**
    * Ч����������
    * @param ch
    * @return
    */
   public static boolean isRomeNum(char ch){//��,��,��,��,��,��,��,��,��,��      ��excel��ȡ
	   if(ch>='��'&& ch<='��'){
		   return true;
	   }else{
		   return false;
	   }
   }
   /**
    * �ж��Ƿ���ֵ��
    * @param dn
    * @return
    */
   public static boolean isDouNum(String dn){
	   if(dn!=null && dn.length()>0){
		   try{
			   Double.parseDouble(dn);
			   return true;
		   }catch(Exception e){
			   return false;
		   }
	   }else{
		   return false;
	   }
	   
   }
    
  
    /*******************���÷�����end*******************************************/
}
