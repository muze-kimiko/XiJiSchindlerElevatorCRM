/*
 * Created on 2005-8-23
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.util;

import java.text.ParseException;

/**
 * Created on 2005-7-12
 * <p>Title:	ͨ��У�鷽��</p>
 * <p>Description:	�ṩ��̬���ģʽ</p>
 * <p>Copyright: Copyright (c) 2005
 * <p>Company:�����Ƽ�</p>
 * @author wyj
 * @version	1.0
 */
public class ValidatorUtil {

    private static ValidatorUtil validatorUtil = new ValidatorUtil();

    private ValidatorUtil() {
    }

    public static ValidatorUtil getInstance() {
        return validatorUtil;
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
     * У���Ƿ��Ǵ�����ַ����Ƿ��Ǵ��ڻ����0������(������)
     * 
     * @param argValue
     * @return boolean
     * 
     */
    public static boolean isPlusInteger(String argValue){
        
        if(!isAllDigits(argValue)) return false;
        
        long iValue = Long.parseLong(argValue);
        //if(!(iValue >= -2147483648 && iValue <= 2147483647)) return false;
        if(!(iValue >= 0 && iValue <= 2147483647)) return false;
        return true;
    }

    /**
     * У���Ƿ��Ǵ�����ַ����Ƿ���С�ڻ����0������(������)
     * 
     * @param argValue
     * @return boolean
     * 
     */
    
    public static boolean isNegativeInteger(String argValue){
        
        if(argValue == null || argValue.length() == 0) return false;
        
        if(argValue.charAt(0) != '-'){
            if(argValue.charAt(0) != '0'){
                return false;                
            }
        }
        
        if(argValue.charAt(0) == '0'){
            if(!isAllDigits(argValue)) return false;    
        }else{
            if(!isAllDigits(argValue.substring(1))) return false;
        }
        
        long iValue = Long.parseLong(argValue);
        if(!(iValue >= -2147483648 && iValue <= 0)) return false;

        return true;
    }

    /**
     * У���Ƿ��Ǵ�����ַ����Ƿ�������,��������
     * 
     * @param argValue
     * @return boolean
     * @throws IOException
     */
    public static boolean isInteger(String argValue){
        
        if(!isPlusInteger(argValue)){
            if(!isNegativeInteger(argValue)){
                return false;
            }
        }
        return true;
    }

    /**
     * У������
     * 
     * @param argValue
     * @return boolean
     * @throws IOException
     */
    public static boolean isDate(String argValue){
        
        if(argValue==null || argValue.length()==0){
            return false;
        }
        
        try {
            CommonUtil.dateToStr(CommonUtil.strToDate(argValue, ""), "");
        } catch (ParseException e) {
            return false;
        }
        return true;
    }
    
    /**
     * �жϵ�ǰ���ļ����ĺ�׺����ΪargValue��
     * @param fileName Ҫ�Ƚϵ��ļ���
     * @param argvalue Ҫ�Ƚϵ��ļ����ĺ�׺��
     * @return boolean
     */
    public static boolean isFileName(String fileName,String argValue){
        if(fileName == null || fileName.length() < 5 || argValue == null){
            return false;
        }else{
            if(argValue.equals(fileName.substring(fileName.length()-3,fileName.length()))){
                return true;                
            }else{
                return false;
            }
        }
    }
    
}
