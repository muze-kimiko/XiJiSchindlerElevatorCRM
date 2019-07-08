package test;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class MyCopeFiles {

	/**lijun add 20140324
	 * @param args
	 */
	public static void main(String[] args) {

		copeFile();
		
		/**  �����ļ�·��.txt �����·����ʽ��
		 
		  src\com\gzunicorn\hibernate\contractmaster\SaleLfttecParm.java
		  
		  WebRoot\salecontract\ContractGroundAddT.jsp
		 */

	}

	public static void copeFile(){

		String infileurl = "D:\\Download\\XJSCRM�����ļ�·��.txt";//��ȡ�ļ���·��
		String tofileurl = "D:\\Download\\XJSCRM\\";//����ļ����ڵ��ļ���
		
		String fromfileurl = "D:\\workprojects\\XJSCRM\\";//��Ŀ��·��
		String outf = "WebRoot\\WEB-INF\\classes";
		
		File file = new File(infileurl);
		FileInputStream fis = null;
		BufferedReader br = null;
		//FileInputStream fis1 = null;
		InputStream In = null;
		
		try {
			fis = new FileInputStream(file);
			br = new BufferedReader(new InputStreamReader(fis));
			String lineData = null; 
			
			String fileurl="";
			String fileurl1="";
			String fileurl2="";
			String fileurl3="";
			int flen=0;
			System.out.println("�����ļ���ʼ");
			int flent=0;
			while((lineData = br.readLine()) != null && !lineData.trim().equals("")){
				flent++;
				if(lineData.substring(0, 3).equals("src")){
					lineData = outf + lineData.substring(3,lineData.length());
					int index = lineData.lastIndexOf(".") + 1;
					if(lineData.substring(index).equals("java")){
						lineData = lineData.substring(0, index) + "class";
					}
				}
				flen=lineData.lastIndexOf("\\");
				fileurl=tofileurl+lineData.substring(0, flen);
				fileurl1=fromfileurl+lineData.substring(0, flen);
				fileurl2=fromfileurl+lineData;
				fileurl3=tofileurl+lineData;
				
				OutputStream out =null;
				File f2 = null;
				File f1 = new File(fileurl2);   
				if (!f1.isDirectory()) {// �������һ���ļ���
				    File f = new File(fileurl);//�����ļ���
					f.mkdirs();
				    
				    out = new FileOutputStream(fileurl+"\\"+f1.getName());
				    f2=new File(fileurl1+"\\"+f1.getName());
				    In = new FileInputStream(f2);
					
					byte[] buf = new byte[8192];
					int len = 0;
					while ((len = In.read(buf)) != -1) {
						out.write(buf, 0, len);
					}
					out.flush();
					out.close();
					In.close();
					
				 } else if (f1.isDirectory()) {// ����Ǹ��ļ���
				    File f = new File(fileurl3);//�����ļ���
					f.mkdirs();
					
				    String[] filelist = f1.list();// �õ����������ļ��������ļ��У�   
				    for (int i = 0; i < filelist.length; i++) {// ѭ��ÿһ���ļ�
					     File readfile = new File(fileurl2 + "\\" + filelist[i]);
					     if (!readfile.isDirectory()) {// ��������ļ��� ͬ����Ĳ���   
						      	out = new FileOutputStream(fileurl3+"\\"+readfile.getName());
							    f2=new File(fileurl2+"\\"+readfile.getName());
							    In = new FileInputStream(f2);
								
								byte[] buf = new byte[8192];
								int len = 0;
								while ((len = In.read(buf)) != -1) {
									out.write(buf, 0, len);
								}
								out.flush();
								out.close();
								In.close();
					     }   
				    }   
				} 
			}
			System.out.println(">>>>>>������"+flent);
			System.out.println("�����ļ�����");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(br != null){
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}


}
