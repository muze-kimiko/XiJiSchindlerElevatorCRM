
package com.gzunicorn.common.dataimport;

import java.io.*;
/**
 * implements��importInterface������Ϊ�����Ժ�ӿ���չ֮�����������õ�����ӿڵ�����һЩ����ʱ�����ý��ӿڵ����з�����ʵ��
 * ����ֱ�Ӽ̳нӿڶ��޸����г���
 * @author rr
 *
 */
public class ImportObject implements ImportInterface{

    public String[][] getItem(InputStream is) throws ImportException{
    	return null;
    }

	public String[][] getItem(InputStream is,String split,int colRow,int[] colArray) throws ImportException{
		return null;
	}
	
	public String[][] getItem(InputStream is,String split,int colRow,int[][] colArray) throws ImportException{
		return null;
	}
   
    
}
