package com.gzunicorn.common.util;

/**
 * ��ά����������
 * @author Administrator
 *
 */
public class ArrayConfig {
private static final String[][] FILE_PATH={{"news","UploadFile/news/"},
										  };


private static final String[][] LANGUAGE_TYPE = {{"China","zh_CN"},
												{"USA","en_US"}};



private static final String[][] PROCEXTEND_TYPE={
												   {"breviary","1"},
												   {"normal","2"},
												   {"blowup","3"}			   
												   };



private static final String[][] URL_PREFIX = {{"file","../../fileAction.do?method=toDownloadFile"},
											  {"",""}};

private static final String[][] TABLE_REFLECTIOIN = {{"1","Infoissueadjunct"},
													 {"2","Procextend"},
													 {"3","Agentaccreditingfile"},
													 {"4","Docfile"},
													 {"5","Engworkinglogfile"},
													 {"6","Quoteprefile"},
													 {"7","Viptrackfile"},
													 {"8","Engtroubleregisterfile"}};

/**
 * ���õķ���,�ɼ�ֵ��ȡ����Ӧ������ֵ
 * @param str
 * @param key
 * @return
 */
private static String getNameByKey(String[][] str,String key ){
	String name = "";
	if(str!=null){
		int len=str.length;
		for(int i=0;i<len;i++){
			if(str[i][0].equalsIgnoreCase(key)){
				name = str[i][1];
				break;
			}
		}
	}
	return name;
}

/**
 * ��ģ����ϴ����������ϴ��ļ������·��
 * @param key
 * @return
 */
public static String getFilePath(String key ){
	return getNameByKey(FILE_PATH,key);
}

/**
 * ��ģ����ϴ����������ϴ��ļ�����ȫ·��
 * @param key
 * @return
 */
public static String getFullFilePath(String key ){
	String temp=getNameByKey(FILE_PATH,key);
	if(temp!=null && temp.length()>0){
		temp=PropertiesUtil.getProperty("upload.file.dir")+temp;
	}
	return temp;
}
/**
 * ��ģ����ϴ����������ϴ��ļ��ڷ�������·��
 * @param key
 * @return
 */
public static String getParentFilePath(){
	return PropertiesUtil.getProperty("upload.file.dir");
}



/**
 * ����������ģ�Ӣ�ģ�
 * @param key
 * @return
 */
public static String getLanguageType(String key){
	return getNameByKey(LANGUAGE_TYPE,key);
}


/**
 * ��Ʒͼ���������ͼ�������������
 * @param key
 * @return
 */
public static String getProcextendtype(String key){
	return getNameByKey(PROCEXTEND_TYPE,key);
}


/**
 * ���Ӧ�ı�ʶ�������ļ�����ʱ�Ĳ���
 * @param key
 * @return
 */
public static String getTableReflection(String key){
	return getNameByKey(TABLE_REFLECTIOIN,key);
}




}
