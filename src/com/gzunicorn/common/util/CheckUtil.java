/*
 * Created on 2005-8-24
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.util;

import java.text.ParseException;
import java.util.ArrayList;
import com.gzunicorn.common.util.SysConfig;
import com.gzunicorn.common.util.ValidatorUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

/**
 * Created on 2005-7-12
 * <p>
 * Title: ����ҵ������ͨ��У�鷽��
 * </p>
 * <p>
 * Description: �ṩ��̬���ģʽ
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
public class CheckUtil {

    private static String errorMsg = "";// ����У����Ϣ�ַ���

    private static CheckUtil checkUtil = new CheckUtil();

    private CheckUtil() {
    }

    public static CheckUtil getInstance() {
        return checkUtil;
    }
    
    /**
     * У�������Ƿ���������������
     * @param barcodeDate	������������
     * @return String �ɹ����ؿ��ַ��� ""
     */
    public static String isBarcodeDate(String barcodeDate) {
        
        boolean returnValue = true;
       
        synchronized (errorMsg) {
        if(returnValue){
            //���ж����ڵ�ͨ�ø�ʽ
            if(!ValidatorUtil.isDate(barcodeDate)){
                setErrorMsg(barcodeDate + "���Ϸ������ڸ�ʽ��");
                returnValue = false;
            }
        }
        
        if(returnValue){
            try{
                String strDate =  
                    CommonUtil.dateToStr(CommonUtil.strToDate(barcodeDate, ""), "");
                //�ж����ڷ�Χ
                if(Integer.parseInt(strDate.substring(0,4))<2005 || Integer.parseInt(strDate.substring(0,4))>2050){
                    setErrorMsg(barcodeDate + "������ݷ�Χ���Ϸ���");
                    returnValue = false;
                }    
            }catch(ParseException e){
                setErrorMsg(barcodeDate + "Ϊ���Ϸ������ڸ�ʽ��");
                returnValue = false;
            }
        }
        
        if (returnValue) {
            setErrorMsg("");
        } else {
            DebugUtil.println(getErrorMsg());
        }
        return getErrorMsg();
        }// synchronized
    }


    /**
     * ��������Ϸ�У��
     * 
     * @param procID
     *            ��������
     * @param procType
     *            �������ͣ�%��ʾ��������
     * @return String �ɹ����ؿ��ַ���
     */
    public static String isProcInfo(String procID, String procType) {

        boolean returnValue = true;
        Session session = null;
        ArrayList list = null;

        synchronized (errorMsg) {

            // У�鳤��
            if (returnValue) {
                if (procID == null || procID.length() == 0 ) {
                    setErrorMsg(procID + "���ʹ��벻��Ϊ�գ�");
                    returnValue = false;
                }
            }

            if (returnValue) {
                try {
                    // �жϵ��̱����ڵ��̱����Ƿ��
                    session = HibernateUtil.getSession();
                    Query query = session
                    .createQuery("FROM Procinfo as a WHERE a.procid=:procid AND a.proctype like :proctype");
                    query.setString("procid", procID);
                    query.setString("proctype", procType);
                    list = (ArrayList) query.list();
                    if (list == null || list.size() == 0) {
                        setErrorMsg(procID + "���ʹ��벻���ڣ�");
                        returnValue = false;
                    }

                } catch (DataStoreException dex) {
                    DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
                    setErrorMsg("HibernateUtil��Hibernate ���� ����");
                    returnValue = false;
                } catch (HibernateException hex) {
                    DebugUtil.print(hex,
                            "HibernateUtil��Hibernate Session �򿪳���");
                    setErrorMsg("HibernateUtil��Hibernate Session �򿪳���");
                    returnValue = false;
                } finally {
                    try {
                        session.close();
                    } catch (HibernateException hex) {
                        DebugUtil.print(hex,
                                "HibernateUtil��Hibernate Session �رճ���");
                    }
                }
            }
            if (returnValue) {
                setErrorMsg("");
            } else {
                DebugUtil.println(getErrorMsg());
            }
            return getErrorMsg();
        }// synchronized
        
    }

    /**
     * �����Ϸ�У��
     * 
     * @param barcode
     * @return String �ɹ����ؿ��ַ���
     */
    public static String isQTY(String qty) {

        boolean returnValue = true;
        // errorMsg��Դͬ������ֹ����߳�ͬʱ����errorMsg���Ӱ��
        synchronized (errorMsg) {

            if (returnValue) {
                if (!ValidatorUtil.isAllDigits(qty)) {
                    setErrorMsg(qty + "�ǲ��Ϸ����������ͣ�");
                    returnValue = false;
                }
            }

            if (returnValue) {
                // У��ɹ���errorMsgΪ""
                setErrorMsg("");
            } else {
                DebugUtil.println(getErrorMsg());
            }
            return getErrorMsg();
        }// synchronized
    }
    
    /**
     * ���userID�Ƿ���Ȩ�޹�shopID����
     * @param userID	�û�����
     * @param shopID	���̴���
     * @return String �ɹ����ؿ��ַ��� ""
     */
    public static String isUserShopRef(String userID, String shopID) {
        
        boolean returnValue = true;
        Session session = null;
        ArrayList list = null;
        
        synchronized (errorMsg) {
        
        
        if (returnValue) {
            try {
                // �жϵ���֮���������ϵ
                session = HibernateUtil.getSession();
                Query query = session
                        .createQuery("FROM Viewuserrefshop as a WHERE a.id.shopid=:shopid AND a.id.userid = :userid");
                query.setString("shopid", shopID);
                query.setString("userid", userID);

                list = (ArrayList) query.list();
                if (list == null || list.isEmpty() || list.size() == 0) {
                    setErrorMsg("��ǰ�û�û�в���" + shopID + "���̵�Ȩ��");
                    returnValue = false;
                }

            } catch (DataStoreException dex) {
                DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
                setErrorMsg("HibernateUtil��Hibernate ���� ����");
                returnValue = false;
            } catch (HibernateException hex) {
                DebugUtil.print(hex,
                        "HibernateUtil��Hibernate Session �򿪳���");
                setErrorMsg("HibernateUtil��Hibernate Session �򿪳���");
                returnValue = false;
            } finally {
                try {
                    session.close();
                } catch (HibernateException hex) {
                    DebugUtil.print(hex,
                            "HibernateUtil��Hibernate Session �رճ���");
                }
            }
        }
        
        if (returnValue) {
            setErrorMsg("");
        } else {
            DebugUtil.println(getErrorMsg());
        }
        return getErrorMsg();
        }// synchronized
    }
    /**
     * У��ֿ����Ƿ����
     * @param storeID	�ֿ���
     * @return String �ɹ����ؿ��ַ��� ""
     */
    public static String isStoreID(String storeID) {
        
        boolean returnValue = true;
        Session session = null;
        ArrayList list = null;
       
        synchronized (errorMsg) {
        	 if (returnValue) {
                if (storeID == null || storeID.length() == 0 ) {
                    setErrorMsg(storeID + "�ֿ���벻��Ϊ�գ�");
                    returnValue = false;
                }
            }

            if (returnValue) {
                try {
                    // �жϵ��̱����ڵ��̱����Ƿ��
                    session = HibernateUtil.getSession();
                    Query query = session
                    .createQuery("FROM Store as a WHERE a.storeid=:storeid");
                    query.setString("storeid", storeID);

                    list = (ArrayList) query.list();
                    if (list == null || list.size() == 0) {
                        setErrorMsg(storeID + "�ֿ���벻���ڣ�");
                        returnValue = false;
                    }

                } catch (DataStoreException dex) {
                    DebugUtil.print(dex, "HibernateUtil��Hibernate ���� ����");
                    setErrorMsg("HibernateUtil��Hibernate ���� ����");
                    returnValue = false;
                } catch (HibernateException hex) {
                    DebugUtil.print(hex,
                            "HibernateUtil��Hibernate Session �򿪳���");
                    setErrorMsg("HibernateUtil��Hibernate Session �򿪳���");
                    returnValue = false;
                } finally {
                    try {
                        session.close();
                    } catch (HibernateException hex) {
                        DebugUtil.print(hex,
                                "HibernateUtil��Hibernate Session �رճ���");
                    }
                }
            }
            if (returnValue) {
                setErrorMsg("");
            } else {
                DebugUtil.println(getErrorMsg());
            }
            return getErrorMsg();
        }// synchronized
    }

    
    /**
     * @return Returns the errorMsg.
     */
    public static String getErrorMsg() {
        return errorMsg;
    }

    /**
     * @param errorMsg
     *            The errorMsg to set.
     */
    public static void setErrorMsg(String errorMsg) {
        CheckUtil.errorMsg = errorMsg;
    }
}
