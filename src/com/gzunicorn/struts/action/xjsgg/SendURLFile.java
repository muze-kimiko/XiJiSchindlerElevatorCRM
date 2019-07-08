package com.gzunicorn.struts.action.xjsgg;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

/**
 * Զ�������ļ���ʹ��URL�������أ�HttpURLConnection
 * @author Lijun
 *
 */
public class SendURLFile {
	 
	public static void main(String[] args) {

	}
	
	/**
	 * ȷ���ļ��Ƿ����
	 * @param fileid �ļ�ID
	 * @return status ����״̬ ��2001���ļ����ڡ�
	 * @throws IOException
	 */
	public static boolean isFileExist(String fileid){
		boolean finash=false;
		BufferedReader br =null;
		try{
			String urlStr = "http://10.10.0.6:9097/PM/crm/isFileExist.do?fileId="+fileid;   
			URL url = new URL(urlStr);   
			HttpURLConnection hconn = (HttpURLConnection) url.openConnection(); //��ȡ����   
			hconn.setRequestMethod("GET"); //�������󷽷�ΪPOST, Ҳ����ΪGET
			hconn.setConnectTimeout(60*1000);//��������������ʱ����λ�����룩
			hconn.setReadTimeout(120*1000);//���ô�������ȡ���ݳ�ʱ����λ�����룩
			hconn.setDoOutput(true);// ʹ�� URL ���ӽ������
			hconn.setDoInput(true);// ʹ�� URL ���ӽ�������
			hconn.setRequestProperty("Charset", "utf-8");//�����ַ�����
			hconn.connect();
			
			InputStream is = hconn.getInputStream(); 
			br = new BufferedReader(new InputStreamReader(is)); 
			String sbstr=br.readLine();
//			StringBuilder sb = new StringBuilder();   
//			sb.append("{");
//			while (br.read() != -1) {   
//				sb.append(br.readLine());   
//			}
			br.close();
			
			System.out.println("�ж��ļ��Ƿ����; fileid="+fileid+"; ����״̬="+sbstr);
			
			JSONObject jsStr = new JSONObject(sbstr);
			String status= String.valueOf(jsStr.getInt("status"));//��ȡid��ֵ
			
			if("2001".equals(status)){
				/**
				 	����״̬��
					2001���ļ�����
					2000���ļ�������
					9000�������쳣
					9009��δ֪�쳣
				 * */
				finash=true;
			}else{
				finash=false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally { 
			try {
				if (br != null) {br.close();}
			} catch (Exception e) { 
				e.printStackTrace(); 
			}
		}
		
		return finash; 
	}

	/**
	 * Զ�������ļ�
	 * @param fileid �ļ�ID
	 * @param response
	 * @param filename �ļ�����
	 * @throws Exception
	 */
	public static void loadFile(String fileid,HttpServletResponse response,String filename){
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			String urlStr = "http://10.10.0.6:9097/PM/crm/loadFile.do?fileId="+fileid;
			URL url = new URL(urlStr);
			  
			HttpURLConnection hconn = (HttpURLConnection) url.openConnection(); //��ȡ����  		
			hconn.setRequestMethod("GET"); //�������󷽷�ΪPOST, Ҳ����ΪGET
			hconn.setConnectTimeout(60*1000);//��������������ʱ����λ�����룩
			hconn.setReadTimeout(120*1000);//���ô�������ȡ���ݳ�ʱ����λ�����룩
			hconn.setDoOutput(true);// ʹ�� URL ���ӽ������
			hconn.setDoInput(true);// ʹ�� URL ���ӽ�������
			hconn.setRequestProperty("Charset", "GBK");//�����ַ�����
			hconn.connect();
			
			System.out.println("��ʼ����Զ���ļ�>>>>fileid="+fileid);
			
			response.setContentType("application/x-msdownload");
			response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(filename, "utf-8"));

			bis = new BufferedInputStream(hconn.getInputStream());
			bos = new BufferedOutputStream(response.getOutputStream());

			int bytesRead = 0;
			byte[] buffer = new byte[5 * 1024];
			while ((bytesRead = bis.read(buffer)) != -1) {
				bos.write(buffer, 0, bytesRead);// ���ļ����͵��ͻ���
				bos.flush();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally { 
			try {
				
				if (bos != null) {bos.close();}
				if (bis != null) {bis.close();}
				
			} catch (Exception e) { 
				e.printStackTrace(); 
			}
		}
        
	}

}
