package com.gzunicorn.file;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public interface HandleFile {
	/**
	 * �½�����һ���ļ�
	 * 
	 * @param in
	 * @param dir
	 * @param fileName
	 * @throws IOException
	 */
	public void createFile(InputStream in, String dir, String fileName)
			throws IOException;

	/**
	 * �ļ���׷������
	 * 
	 * @param dir
	 * @param fileName
	 * @param content
	 * @return boolean true
	 * @throws FileNotFoundException
	 */
	public boolean writeFile(String dir, String fileName, StringBuffer content)
			throws FileNotFoundException;

	/**
	 * ���ļ�
	 * 
	 * @param dir
	 * @param fileName
	 * @return �ļ�������
	 * @throws FileNotFoundException
	 */
	public InputStream readFile(String dir, String fileName)
			throws FileNotFoundException;

	/**
	 * �޸��ļ�
	 * 
	 * @param in
	 * @param dir
	 * @param fileName
	 * @throws FileNotFoundException
	 * @throws IOException
	 */
	public void modFile(InputStream in, String dir, String fileName)
			throws FileNotFoundException, IOException;

	/**
	 * ɾ���ļ�
	 * 
	 * @param dir
	 * @param fileName
	 * @throws FileNotFoundException
	 */
	public void delFile(String dir, String fileName)
			throws FileNotFoundException;

	/**
	 * ɾ���ļ�
	 * 
	 * @param fileFullName
	 * @throws FileNotFoundException
	 */
	public void delFile(String fileFullName) throws FileNotFoundException;

	/**
	 * ȡĿ¼�µ��ļ��б�
	 * 
	 * @param dir
	 * @return �ļ��б�
	 * @throws FileNotFoundException
	 */
	public String[] getFileList(String dir) throws FileNotFoundException;

	/**
	 * ɾ���ļ���(java����ļ���ɾ��������ֻ��ɾ���ļ��Ϳ��ļ��У�������Ҫ�ݹ�)
	 * 
	 * @param folderPath
	 *            String �ļ���·�������� ��c:/fqf
	 * @return boolean
	 */
	public boolean delFolder(String folderPath) throws FileNotFoundException;

	/**
	 * ���Ƶ����ļ�
	 * 
	 * @param oldPath
	 * @param newPath
	 * @return boolean
	 * @throws FileNotFoundException
	 */
	public boolean copyFile(String oldPath, String newPath)
			throws FileNotFoundException;

	/**
	 * ���������ļ�������
	 * 
	 * @param oldPath
	 *            String ԭ�ļ�·�� �磺c:/fqf
	 * @param newPath
	 *            String ���ƺ�·�� �磺f:/fqf/ff
	 * @return boolean
	 */
	public boolean copyFolder(String oldPath, String newPath)
			throws FileNotFoundException;

	/**
	 * �ƶ��ļ���ָ��Ŀ¼
	 * 
	 * @param oldPath
	 *            String �磺c:/fqf.txt
	 * @param newPath
	 *            String �磺d:/fqf.txt
	 * @return boolean
	 */
	public void moveFile(String oldPath, String newPath)
			throws FileNotFoundException;

	/**
	 * �ƶ��ļ��е�ָ��Ŀ¼
	 * 
	 * @param oldPath
	 *            String �磺c:/fqf
	 * @param newPath
	 *            String �磺d:/fff
	 * @return boolean
	 */
	public void moveFolder(String oldPath, String newPath)
			throws FileNotFoundException;

}
