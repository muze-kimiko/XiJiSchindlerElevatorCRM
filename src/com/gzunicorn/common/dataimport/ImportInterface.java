/*
 * Created on 2005-8-27
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.gzunicorn.common.dataimport;

import java.io.*;
/**
 * @author rr
 */
public interface ImportInterface {
	/**
	 * 
	 * @param is
	 * @return
	 * @throws ImportException
	 */
    public String[][] getItem(InputStream is) throws ImportException;
    
    /**
     * �����ļ�ʱ����Ҫָ���е��������ͣ����ô˷���
     * @param is
     * @param split
     * @param colRow
     * @param colArray
     * @return
     * @throws ImportException
     */
	public String[][] getItem(InputStream is,String split,int colRow,int[] colArray) throws ImportException;
	
	/**
	 * �����ļ�ʱ��Ҫָ�����������ͣ����ô˷���������excel��������ͣ���Ҫָ��;
	 * @param is	�ļ���
	 * @param split�����ָ��
	 * @param colRow�����е�����
	 * @param colArray [i][0]--��λ�ã�[i][1]--��Ӧ������
	 * @return
	 * @throws ImportException
	 */
	public String[][] getItem(InputStream is,String split,int colRow,int[][] colArray) throws ImportException;
   
    
}
