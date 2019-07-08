package com.gzunicorn.common.util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * �ַ���������
 * @author ZZG
 *
 */
public class StringUtil {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String s1="1��������Ŀ���ϼ�ʩ�����ڣ�12~15�������ա�ʩ��ʱ�䣺��һ�����壨�����ڼ��գ� 8.30~17.30����ʩ��ʱ����������Ҫ����Ҫ�ں�ͬǩ��ǰ���飩��  2��������Ŀ�����������á�ά�޹����緢��������Ŀ��������������������ơ�  3������ǰ7�츶��100%���̿  4�����й�����Ŀ�����ұ�׼���գ�����������Ϊһ�꣬��������˾ǩ���ġ����ݱ�����ͬ��������Ч��  5����ͬǩ�����ʩ���������򲻿ɿ���ԭ����ɵ���ʧ��˫������⳥���Ρ�  6������������һʽ���ݣ��׷�(ί�е�λ)Ҽ�ݣ��ҷ�(���λ)���ݡ���Ч����ʮ�졣  7��������Ŀ�����ѽ�������Ŀ�۸�ϳ��Żݣ�������Ŀ���۲����Դ�Ϊ��׼��  8���˱���Ϳ�Ļ������������ݣ�����Ϳ�Ļ��������ݣ�˫��Ӧ������Ӧ�����·�����Ч��9��10��11��  ";
//		//System.out.println(s1);
		s1 = replaceStr(s1,"#",2);
		List list = processStringToList(s1,"#");
//		//System.out.println(list.toString());
		if(null != list && !list.isEmpty()){
			for(int i = 0 ; i <list.size(); i++){
				//System.out.println(list.get(i));
			}
		}
	}
	
	/**
	 * ����ַ�����list
	 * @param strOld
	 * @param replacement	���ṩ���ַ����
	 * @return
	 */
	public static List processStringToList(String strOld,String replacement){
		List list = new ArrayList();
		String[] sArr = null;
		if(null != strOld && !"".equals(strOld)){
			sArr = strOld.split(replacement);
		}
		if(null != sArr && sArr.length >0){
			for(int i = 0 ; i < sArr.length; i++){
				String temp = (String)sArr[i];
				if(!"".equals(temp.trim())){
					Map map = new HashMap();
					map.put("val", temp);
					list.add(map);
				}
			}
		}
		return list;
	}


	/**
	 * ���ַ����е�(1��,2��,3��,4��,5��,....)�滻��ָ���ַ�replacement��
	 * @param strOld		�ַ�
	 * @param replacement	�滻��ָ���ַ�
	 * @param starFrom		��ʼ��ţ���2,���2����ʼ�滻��
	 * @return
	 */
	public static String replaceStr(String strOld,String replacement,int starFrom){
		if(null != strOld && !"".equals(strOld)){
			int i = starFrom;
			int indexof = 0;
			String temp ="";
			boolean flag = true;
			while(flag){
				temp =	i+"��";
				indexof = strOld.indexOf(temp);
//				//System.out.println(temp+" indexof "+indexof);
				if(indexof == -1){
					flag = false;
				}else{
//					//System.out.println(temp);
					strOld=strOld.replaceFirst(temp, replacement+temp);
//					//System.out.println(s);
					temp ="";
				}
				i++;
			}
		}
		return strOld;
	}

}
