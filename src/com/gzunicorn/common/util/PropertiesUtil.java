package com.gzunicorn.common.util;

import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;
import java.io.FileOutputStream;

/*
 * Created on 2005-7-12
 * <p>Title:��ȡProperties�����ļ�����	</p>
 * <p>Description:	</p>
 * <p>Copyright: Copyright (c) 2005
 * <p>Company:�����Ƽ�</p>
 * @author wyj
 * @version	1.0
 */

public class PropertiesUtil {

    private final static String FILENAME = SysConfig.CONFIG_FILENAME;
    private String FILEPATH = "";  //properties�ļ�����ľ���λ��

    private Properties properties = null;
    private static PropertiesUtil instance = null;

    private PropertiesUtil(){
      try{
        properties = new Properties();
        this.FILEPATH = this.getClass().getClassLoader().getResource(FILENAME).getFile();
        InputStream in = this.getClass().getClassLoader().getResourceAsStream(FILENAME);
        //InputStream in = new FileInputStream(FILENAME);
        properties.load(in);
        in.close();
      }catch(IOException e){
        System.err.println("load Config.properties error!");
        e.printStackTrace();
      }
    }

    private static void init(){
      if(instance == null){
        instance = new PropertiesUtil();
      }
    }
    /**
     * �õ�����
     * @param name String ��������
     * @return String
     */
    public static String getProperty(String name){
      init();
      return instance.properties.getProperty(name);
    }

    /**
     * ��������Ϣд��properties�ļ�
     * @param name String
     * @param value String
     * @param description String ��ǰд���������Ե�������Ϣ
     */
    public static void setProperty(String name,String value,String description){
      if(name == null || name.trim().equals("")){
        DebugUtil.println("PropertiesUtil.java setProperty() ����Ϊ��!");
        return;
      }
      init();
      try{
        instance.properties.put(name, value);
        instance.properties.store(new FileOutputStream(instance.FILEPATH), description);
      }catch(IOException ioe){
        DebugUtil.println("PropertiesUtil.java setProperty() ����properties�����ļ�ʧ��!");
        ioe.printStackTrace();
      }
    }
    /**
     * ���properties�ļ��ľ���·��
     * @return String
     */
    public static String getFileAbsolutePath(){
      return instance.FILEPATH;
    }
  /*
    public static void main(String args[]){
      PropertiesUtil.setProperty("hq","11","user");
    }
  */
}
