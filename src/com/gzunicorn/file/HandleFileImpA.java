package com.gzunicorn.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.gzunicorn.common.util.DebugUtil;

/**
 * �ļ�������
 * 
 * @version 1.0
 * @author Administrator
 * 
 */
public class HandleFileImpA implements HandleFile {

	/**
	 * ����һ�����ļ���
	 * 
	 * @param in
	 *            �ļ�������
	 * @param dir
	 *            �ļ����Ŀ¼
	 * @param fileName
	 *            �ļ�����
	 */
	public void createFile(InputStream in, String dir, String fileName)
			throws IOException {

		File f = new File(dir);
		f.mkdirs();
		OutputStream out = new FileOutputStream(dir + fileName);
		byte[] buf = new byte[8192];
		int len = 0;
		while ((len = in.read(buf)) != -1) {
			out.write(buf, 0, len);
		}
		out.flush();
		out.close();
		in.close();
	}

	public boolean writeFile(String dir, String fileName, StringBuffer content)
			throws FileNotFoundException {
		// TODO Auto-generated method stub
		return false;
	}

	/**
	 * ���ļ�����
	 * 
	 * @param dir
	 *            �ļ�Ŀ¼
	 * @param fileName
	 *            �ļ�����
	 * @return InputSteam �����ļ�������
	 */
	public InputStream readFile(String dir, String fileName)
			throws FileNotFoundException {
		File f = new File(dir + fileName);
		InputStream in = null;
		if (f.exists()) {
			in = new FileInputStream(dir + fileName);
		}
		return in;
	}

	public void modFile(InputStream in, String dir, String fileName)
			throws FileNotFoundException, IOException {
		// TODO Auto-generated method stub

	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param dir
	 *            �ļ�Ŀ¼
	 * @param fileName
	 *            �ļ�����
	 */
	public void delFile(String dir, String fileName)
			throws FileNotFoundException {
		File f = new File(dir + fileName);
		if (f.exists()) {
			f.delete();
		}
	}

	/**
	 * ɾ���ļ�
	 * 
	 * @param filePathAndName
	 *            �ļ�·��+�ļ����ơ�
	 */
	public void delFile(String filePathAndName) throws FileNotFoundException {
		try {
			String filePath = filePathAndName;
			filePath = filePath.toString();
			java.io.File myDelFile = new java.io.File(filePathAndName);
			myDelFile.delete();
		} catch (Exception e) {
			DebugUtil.print(e);
			e.printStackTrace();
		}
	}

	/**
	 * ��ȡĳ��Ŀ¼�µ������ļ��б�
	 * 
	 * @param dir
	 *            Ŀ¼
	 * @return String[] �ļ��б�����
	 */
	public String[] getFileList(String dir) throws FileNotFoundException {
		File file = new File(dir);
		File list[] = file.listFiles();
		String[] filelist = null;
		if (list != null) {
			int len = list.length;
			filelist = new String[len];
			for (int i = 0; i < len; i++) {
				filelist[i] = list[i].getName();
			}
		}
		return filelist;
	}

	/**
	 * ɾ���ļ��У��Ͱ���ɾ���ļ����µ��������ݡ� ɾ��ʱ����ɾ���ļ����µ��ļ�����ɾ���ļ����µ����ݡ�
	 * 
	 * @param folderPath
	 * @return ɾ���ɹ�����true,���򷵻�false
	 */
	public boolean delFolder(String folderPath) throws FileNotFoundException {
		boolean tag = true;
		try {
			delAllFile(folderPath); // ɾ����������������
			String filePath = folderPath;
			filePath = filePath.toString();
			java.io.File myFilePath = new java.io.File(filePath);
			myFilePath.delete(); // ɾ�����ļ���

		} catch (Exception e) {
			tag = false;
			DebugUtil.print(e);
		}
		return tag;
	}

	/**
	 * �����ļ�
	 * 
	 * @param oldPath
	 *            Դ�ļ�,����������·��
	 * @param newPath
	 *            Ŀ���ļ�
	 * @return boolean,�����ɹ�����true,ʧ�ܷ���false
	 */
	public boolean copyFile(String oldPath, String newPath)
			throws FileNotFoundException {
		boolean tag = true;
		try {
			int bytesum = 0;
			int byteread = 0;
			File oldfile = new File(oldPath);
			if (oldfile.exists()) { // �ļ�����ʱ
				InputStream inStream = new FileInputStream(oldPath); // ����ԭ�ļ�
				FileOutputStream fs = new FileOutputStream(newPath);
				byte[] buffer = new byte[8196];
				while ((byteread = inStream.read(buffer)) != -1) {
					bytesum += byteread; // �ֽ��� �ļ���С
					fs.write(buffer, 0, byteread);
				}
				inStream.close();
			}
		} catch (Exception e) {
			tag = false;
			DebugUtil.print(e);
		}
		return tag;
	}

	/**
	 * �����ļ��У������ļ����µ������ļ���Ϣ
	 * 
	 * @param oldPath
	 *            Դ�ļ�Ŀ¼
	 * @param newPath
	 *            Ŀ���ļ�Ŀ¼
	 */
	public boolean copyFolder(String oldPath, String newPath)
			throws FileNotFoundException {
		boolean tag = true;
		try {
			(new File(newPath)).mkdirs(); // ����ļ��в����� �������ļ���
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// ��������ļ���
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			tag = false;
			DebugUtil.print(e);

		}
		return tag;
	}

	/**
	 * �ƶ��ļ�
	 * 
	 * @param oldPath
	 *            ��Ŀ¼
	 * @param newPath
	 *            ��Ŀ¼
	 */
	public void moveFile(String oldPath, String newPath)
			throws FileNotFoundException {
		copyFile(oldPath, newPath);
		delFile(oldPath);
	}

	/**
	 * �ƶ��ļ���
	 * 
	 * @param oldPath
	 *            ��Ŀ¼
	 * @param newPath
	 *            ��Ŀ¼
	 */
	public void moveFolder(String oldPath, String newPath)
			throws FileNotFoundException {
		copyFolder(oldPath, newPath);
		delFolder(oldPath);
	}

	/**
	 * ɾ���ļ�������������ļ�
	 * 
	 * @param path
	 *            String �ļ���·�� �� c:/fqf
	 * @throws FileNotFoundException
	 */
	public void delAllFile(String path) throws FileNotFoundException {
		File file = new File(path);
		if (!file.exists()) {
			return;
		}
		if (!file.isDirectory()) {
			return;
		}
		String[] tempList = file.list();
		File temp = null;
		for (int i = 0; i < tempList.length; i++) {
			if (path.endsWith(File.separator)) {
				temp = new File(path + tempList[i]);
			} else {
				temp = new File(path + File.separator + tempList[i]);
			}
			if (temp.isFile()) {
				temp.delete();
			}
			if (temp.isDirectory()) {
				delAllFile(path + "/" + tempList[i]);// ��ɾ���ļ���������ļ�
				delFolder(path + "/" + tempList[i]);// ��ɾ�����ļ���
			}
		}
	}
}
