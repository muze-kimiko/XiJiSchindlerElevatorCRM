/*
 * Created on 2005-8-28
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.dataimport.importutil;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;


/**
 * Title: ͨ����
 * Description: ת��Text�ļ��е�Cell��ʽ(ϵͳ��ת���������߼�У��),�ṩ��̬ģʽ 
 * @author rr
 * @version 1.2
 */
public class TranTextCellUtil {

    private static TranTextCellUtil tranTextCellUtil = new TranTextCellUtil();

    private TranTextCellUtil() {
    }

    public static TranTextCellUtil getInstance() {
        return tranTextCellUtil;
    }
    
    public static String tranStr(String str){
    	if(str==null){
    		return "";
    	}else{
    		return str.trim();
    	}
    }

    /**
     * ת��Text�е�����Cell ֧��Cell�еĸ�ʽΪ��
     * 20050505,2005-05-12��2004-5-1,
     * 2004-5-01,2004-05-1
     * @param cell
     * @return String
     */
    public static String tranDate(String tranDate) {

        String strDate = tranDate;
        if (tranDate != null && tranDate.trim().length() >= 8) {
            try {
                strDate = ComUtil.dateToStr(ComUtil.formatStrToDate(tranDate.trim(), ""), "");
            } catch (ParseException e) {
                // ʧ��������������ת������߳����ݴ���
                if (tranDate.trim().length() == 8 && tranDate.trim().indexOf('-') < 0) {
                    // 20050505ת��Ϊ2005-05-12
                    strDate = tranDate.trim().substring(0, 4) + "-"
                            + tranDate.trim().substring(4, 6) + "-"
                            + tranDate.trim().substring(6, 8);
                } else {
                    if (tranDate.trim().length() == 10
                            && tranDate.trim().indexOf('-') > -1) {
                        // 2005-05-05ת��Ϊ2005-05-12
                        strDate = tranDate.trim();
                    }
                }

            }
        }
        return strDate;
    }

    /**
     * �����ַ���ת������������
     * ��123.80ת����123
     * @param cell
     * @return String
     */
    public static String tranInteger(String tranStr) {

        String returnValue = "";
        if(ComUtil.isAllDigits(tranStr.trim())){
            double d = new Double(tranStr).doubleValue();
            // ��ֹ���ֿ�ѧ�������ı�ʾ
            NumberFormat defForm = new DecimalFormat("#");
            returnValue = String.valueOf(defForm.format(d));
            
        }
        return returnValue;
    }
}
