
package com.gzunicorn.common.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.*;
import org.hibernate.cfg.*;

import com.gzunicorn.common.util.SysConfig;

/**
 * Created on 2005-7-12
 * <p>Title: Hibernate��Session��������</p>
 * <p>Description:	</p>
 * <p>Copyright: Copyright (c) 2005
 * <p>Company:�����Ƽ�</p>
 * @author wyj
 * @version	1.0
 */

public class HibernateUtil {

    private static Log log = LogFactory.getLog(HibernateUtil.class);
    private static Configuration configuration;
    private	static SessionFactory sessionFactory;
    
    //��ʼ��Hibernate,����SessionFactoryʵ��
    static{
        try{
            configuration = new Configuration().configure(SysConfig.HIBERNATE_CFG_FILENAME);
            sessionFactory = configuration.buildSessionFactory();
        }catch(Throwable ex){
          log.error(ex.getMessage());
          throw new ExceptionInInitializerError(ex);
        }
     }
    
    
    /**
     * ���Hibernate��SessionFactory����
     * @return
     */
    public static SessionFactory getSessionFactory(){
        return sessionFactory;
    }
    
    /**
     * ���Hibernate��Configuration����
     * @return
     */
    public static Configuration getConfiguration(){
        return configuration;
    }
    
    /**
     * ���ݵ�ǰ������������Hibernate��SessionFactory����
     * @throws DataStoreException
     */
    public static void rebuildSessionFactory() 
    	throws DataStoreException{
        synchronized(sessionFactory){
            try{
                sessionFactory = getConfiguration().buildSessionFactory();
            }catch(Exception ex){
                log.error(ex.getMessage());
                throw new DataStoreException(ex);
            }
        }
    }
    
    /**
     * ������������������Hibernate��SessionFactory����
     * @param cfg
     * @throws DataStoreException
     */
     public static void rebuildSessionFactory(Configuration cfg)
     	throws DataStoreException{
         synchronized(sessionFactory){
             try{
                 sessionFactory = cfg.buildSessionFactory();
                 configuration = cfg;
             }catch(Exception ex){
                 log.error(ex.getMessage());
                 throw new DataStoreException(ex);
             }
         }
     }
     
     /**
      * ��ȡһ��Hibernate��Session
      * @return
      * @throws DataStoreException
      */
     public static Session getSession() throws DataStoreException{
         try{
             return sessionFactory.openSession();
         }catch(Exception ex){
             log.error(ex.getMessage());
             throw new DataStoreException(ex);
         }
     }
     
     /**
      * �ر�sessionFactory����
      * ע����Ҫ������
      */
     public static void close(){
         try{
             sessionFactory.close();
         }catch(Exception ex){
             log.error(ex.getMessage());
         }
     }

     /**
      * �ر�����Session
      * @param session
      * @throws DataStoreException
      */
     public static void closeSession(Session session) 
     	throws DataStoreException{
         try{
             session.close();
         }catch(Exception ex){
             log.error(ex.getMessage());
             throw new DataStoreException(ex);
         }
     }

}
