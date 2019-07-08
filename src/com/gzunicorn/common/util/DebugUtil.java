/*
 * Created on 2005-7-12
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.util;

import java.text.ParseException;

/**
 * <p>Title: ����Debug������</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: �����Ƽ��Ƽ�</p>
 * @author wyj
 * @version 1.0
 */

public final class DebugUtil {

    public static final boolean debuggingOn = true;
    public static final boolean debugStackOn = true;
/*
    public static void assert(boolean condition) {
        if (!condition) {
            println("Assert Failed: ");
            throw new IllegalArgumentException();
        }
    }
*/
    public static void print(String msg) {
        if (debuggingOn) {
            System.out.println(">>>" + msg);
        }
    }

    public static void println(String msg) {
        if (debuggingOn) {
            System.out.println(">>>" + msg);
        }
    }

    public static void print(Exception e, String msg) {
        print((Throwable)e, msg);
    }

    public static void print(Exception e) {
        print(e, null);
    }

    public static void print(Throwable t, String msg) {
        if (debugStackOn) {
            System.out.println("Received throwable with Message: "+t.getMessage());
            if (msg != null)
                System.out.print(msg);
            t.printStackTrace();
        }
    }
    private static void printDoAction(String actionName,String method,String s_or_e){
    	if (debuggingOn) {
            try {
				System.out.println(CommonUtil.getToday() + " " + CommonUtil.getTodayTime() + ">>" + actionName+" : "+method+"  "+s_or_e);
			} catch (ParseException e) {
				e.printStackTrace();
			}
        }
    }
    public static void print(Throwable t) {
        print(t, null);
    }

    /**
     * ��ӡ����������action
     * @param actionName ��ǰaction����
     * @param method	 ��ǰactionִ�еķ���
     * @param s_or_e     ��ʼ�����
     */
    public static void printDoCommonAction(String actionName,String method,String s_or_e){
    	printDoAction(actionName,method,s_or_e);
    }
    /**
     * ��ӡ����������action
     * @param actionName ��ǰaction����
     * @param method	 ��ǰactionִ�еķ���
     * @param s_or_e     ��ʼ�����
     */
    public static void printDoOtherAction(String actionName,String method,String s_or_e){
    	printDoAction(actionName,method,s_or_e);
    }
    /**
     * ��ӡ�����Ļ������ݵ�action
     * @param actionName ��ǰaction����
    ��* @param method	 ��ǰactionִ�еķ���
   �� * @param s_or_e     ��ʼ�����
     */
    public static void printDoBaseAction(String actionName,String method,String s_or_e){
    	printDoAction(actionName,method,s_or_e);
     }
    /*
    public static void main(String args[]){
      PropertiesUtil.setProperty("hq","11","user");
    }
  */
}
