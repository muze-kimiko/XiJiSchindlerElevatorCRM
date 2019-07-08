package com.gzunicorn.common.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts.action.ActionError;
import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.upload.FormFile;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.gzunicorn.common.dao.mssql.DataOperation;
import com.gzunicorn.file.HandleFile;
import com.gzunicorn.file.HandleFileImpA;
import com.gzunicorn.hibernate.basedata.Fileinfo;

import jcifs.smb.*;
import java.net.*;

public class UploadFileUtil {

	/**
	 * �����ϴ��ļ�
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public List saveFile(ActionForm form,HttpServletRequest request, HttpServletResponse response,String folder,String path,String billno){
		List returnList = new ArrayList();
		int filenum=0;
		 int fileCount=0;
//		 folder ="engineQuoteProcess.file.upload.folder";
//		 path	="engineQuoteProcess.file.upload.path";
		 folder =  PropertiesUtil.getProperty(folder).trim();//		 
		 path =  PropertiesUtil.getProperty(path).trim();//�ϴ�Ŀ¼		 

		 FormFile formFile = null;
		 Fileinfo file=null;
		 if (form.getMultipartRequestHandler() != null) {
			 Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			 if (hash != null) {
				 
				 String[] filerem=request.getParameterValues("filerem");
				 String[] filetitle=request.getParameterValues("filetitle");
				 if(filerem != null){
					 fileCount=filetitle.length;
				 }
				 
				 Iterator it = hash.values().iterator();
				 HandleFile hf = new HandleFileImpA();
				 while (it.hasNext()) {
					 fileCount--;
					 formFile = (FormFile) it.next();
					 if (formFile != null) {
						 try {
							 String rem2=filerem[fileCount];
							 String title2=filetitle[fileCount];
							 
							 Map map = new HashMap();
							 map.put("title", title2);
							 map.put("oldfilename", formFile.getFileName());
							 map.put("newfilename", billno+"_"+fileCount+"_"+formFile.getFileName());
							 map.put("filepath", path+CommonUtil.getToday().substring(0,7)+"/");
							 map.put("filesize", new Double(formFile.getFileSize()));
							 map.put("fileformat",formFile.getContentType());
							 map.put("rem",rem2);
							 
//							 file = new Fileinfo();
//						 file.setMaterSid(billno);
//						 file.setBusTable("MainMugContractMaster");
//						 file.setFileTitle(title2);
//						 file.setOldFileName(formFile.getFileName());
//						 file.setNewFileName(billno+formFile.getFileName());
//						 file.setFilePath(path+CommonUtil.getToday().substring(0,7)+"/");
//						 file.setFileSize(new Double(formFile.getFileSize()));
//						 file.setFileFormat(formFile.getContentType());
//						 file.setUploadDate(CommonUtil.getToday());
//						 file.setUploader(userInfo.getUserID());
//						 file.setRemarks(rem2);
							// �����ļ����ļ�ϵͳ

							hf.createFile(formFile.getInputStream(),folder+path+CommonUtil.getToday().substring(0,7)+"/", billno+"_"+fileCount+"_"+formFile.getFileName());
							returnList.add(map);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
//						 hs.save(file);
//						 filenum++;
					 }
				 }
			 }
		 }
		return returnList;
	}
	//�����ϴ��ƻ�������ļ�
	public List saveFile1(ActionForm form, HttpServletRequest request,
			HttpServletResponse response, String folder, String billno){
		List returnList = new ArrayList();
		int filenum=0;
		 int fileCount=0;
		 folder =  PropertiesUtil.getProperty(folder).trim();//		 
				 

		 FormFile formFile = null;
		 Fileinfo file=null;
		 if (form.getMultipartRequestHandler() != null) {
			 Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			 if (hash != null) {
				 
				 String[] filerem=request.getParameterValues("filerem");
				 String[] filetitle=request.getParameterValues("filetitle");
				 if(filerem != null){
					 fileCount=filetitle.length;
				 }
				 
				 Iterator it = hash.values().iterator();
				 HandleFile hf = new HandleFileImpA();
				 while (it.hasNext()) {
					 fileCount--;
					 formFile = (FormFile) it.next();
					 if (formFile != null) {
						 try {
							 //String rem2=filerem[fileCount];
							 //String title2=filetitle[fileCount];
							 
							 Map map = new HashMap();
							 map.put("title", "�ϴ������ƻ�����");
							 map.put("oldfilename", formFile.getFileName());
							 map.put("newfilename", billno+"_"+fileCount+"_"+formFile.getFileName());
							 map.put("filepath", CommonUtil.getToday().substring(0,7)+"/");
							 map.put("filesize", new Double(formFile.getFileSize()));
							 map.put("fileformat",formFile.getContentType());
							 map.put("rem","");
							 

							// �����ļ����ļ�ϵͳ

							hf.createFile(formFile.getInputStream(),folder+CommonUtil.getToday().substring(0,7)+"/", billno+"_"+fileCount+"_"+formFile.getFileName());
							returnList.add(map);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						} catch (ParseException e) {
							e.printStackTrace();
						}
						
//						 hs.save(file);
//						 filenum++;
					 }
				 }
			 }
		 }
		return returnList;
	}
	
	/**
	 * �����ϴ��ļ�(��һ�ι���ȷ��)
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public List saveOneTime(ActionForm form,HttpServletRequest request, HttpServletResponse response,String folder,String path){
		List returnList = new ArrayList();
//		 folder ="engineQuoteProcess.file.upload.folder";
//		 path	="engineQuoteProcess.file.upload.path";
		 folder =  PropertiesUtil.getProperty(folder).trim();//		 
		 path =  PropertiesUtil.getProperty(path).trim();//�ϴ�Ŀ¼		 
		 int fileCount=0;
		 FormFile formFile = null;
		 if (form.getMultipartRequestHandler() != null) {
			 Hashtable hash = form.getMultipartRequestHandler().getFileElements();
			 if (hash != null) {
				 String [] inerno=request.getParameterValues("inerno");
				 String [] onetime = request.getParameterValues("onetime");//��һ�ι���ȷ��ʱ��
				 
				 for (int j = 0; j < onetime.length; j++) {
					 formFile = (FormFile) hash.get("onefile"+j);
					 HandleFile hf = new HandleFileImpA();
					if(formFile!=null){
						if(onetime[j]!=null&&!onetime[j].equals("")){
							 try {
								 String rem2="";
								 String title2="";
								 
								 Map map = new HashMap();
								 map.put("title", title2);
								 map.put("oldfilename", formFile.getFileName());
								 map.put("newfilename", inerno[j]+"_"+formFile.getFileName());
								 map.put("filepath", path+CommonUtil.getToday().substring(0,7)+"/");
								 map.put("filesize", new Double(formFile.getFileSize()));
								 map.put("fileformat",formFile.getContentType());
								 map.put("rem",rem2);
								hf.createFile(formFile.getInputStream(),folder+path+CommonUtil.getToday().substring(0,7)+"/", inerno[j]+"_"+formFile.getFileName());
								returnList.add(map);
							} catch (FileNotFoundException e) {
								e.printStackTrace();
							} catch (IOException e) {
								e.printStackTrace();
							} catch (ParseException e) {
								e.printStackTrace();
							}
						}
						
					}
				}
				
				 }
		 }
		return returnList;
	}
	
	
	/**
	 * ��һ�ι���ȷ�ϱ����ļ���Ϣ�����ݿ�
	 * @param fileInfoList
	 */
	public boolean saveOneTimeTodb(Session hs,HttpServletRequest request,List fileInfoList,String tableName,String userid){
		boolean saveFlag = true;
		 Fileinfo file=null;
		 Connection con = null;
		if(null != fileInfoList && !fileInfoList.isEmpty()){
			
			try {
				String [] inernos = request.getParameterValues("inerno");//�ڲ�����
				String [] billno=request.getParameterValues("billno");//װ��ƻ���ˮ��
				int len = fileInfoList.size();
				for(int i = 0 ; i < len ; i++){
					Map map = (Map)fileInfoList.get(i);
					 String title =(String) map.get("title");
					 String oldfilename =(String) map.get("oldfilename");
					 String newfilename =(String) map.get("newfilename");
					 String filepath =(String) map.get("filepath");
					 Double filesize =(Double) map.get("filesize");
					 String fileformat =(String) map.get("fileformat");
					 String rem =(String) map.get("rem");
					 
					 con = hs.connection();
					 String sql = "INSERT INTO DoComfirmFileinfo(MaterSid,BusTable,FileTitle,OldFileName,NewFileName,FilePath,FileFormat,FileSize,UploadDate,Uploader,Remarks,ext1) " +
					 		"VALUES ('"+billno[i]+"','"+tableName+"','"+title+"','"+oldfilename+"','"+newfilename
					 		+"','"+filepath+"','"+fileformat+"','"+filesize+"','"+CommonUtil.getToday()+"','"+userid+
					 		"','"+rem+"','"+inernos[i]+"')";
					 DataOperation op = new DataOperation();
					 op.setCon(con);
					 int rt = op.updateToInt(sql);
					    
					 hs.flush();
				}
			} catch (Exception e) {
				e.printStackTrace();
				saveFlag = false;
			}
		}
		return saveFlag;
	}
	
	/**
	 * �����ļ���Ϣ�����ݿ�
	 * @param fileInfoList
	 */
	public boolean saveFileInfo(Session hs,List fileInfoList,String tableName,String billno,String userid){
		boolean saveFlag = true;
		 Fileinfo file=null;
		if(null != fileInfoList && !fileInfoList.isEmpty()){
			
			try {
				int len = fileInfoList.size();
				for(int i = 0 ; i < len ; i++){
					Map map = (Map)fileInfoList.get(i);
					 String title =(String) map.get("title");
					 //String  map.put("matersid", "123456789aa");
					 String oldfilename =(String) map.get("oldfilename");
					 String newfilename =(String) map.get("newfilename");
					 String filepath =(String) map.get("filepath");
					 Double filesize =(Double) map.get("filesize");
					 String fileformat =(String) map.get("fileformat");
					 String rem =(String) map.get("rem");
					 
					 
					 file = new Fileinfo();
					 file.setMaterSid(billno);
					 file.setBusTable(tableName);
					 file.setFileTitle(title);
					 file.setOldFileName(oldfilename);
					 file.setNewFileName(newfilename);
					 file.setFilePath(filepath);
					 file.setFileSize(filesize);
					 file.setFileFormat(fileformat);
					 file.setUploadDate(CommonUtil.getToday());
					 file.setUploader(userid);
					 file.setRemarks(rem);
					 
					 hs.save(file);
					 hs.flush();
				}
			} catch (ParseException e) {
				e.printStackTrace();
				saveFlag = false;
			} catch (Exception e) {
				e.printStackTrace();
				saveFlag = false;
			}
		}
		return saveFlag;
	}
	
	/**
	 * ��ȡ���ϴ������б�
	 * @param hs
	 * @param MaterSid
	 * @return
	 */
	public List display(Session hs, String MaterSid, String BusTable)
	  {
	    List rt = new ArrayList();
	    Connection con = null;
	    try {
	      con = hs.connection();
	      String sql = "exec SP_Fileinfo_DISPLAY '" + MaterSid + "','" + BusTable + "'";
	      DataOperation op = new DataOperation();
	      op.setCon(con);
	      rt = op.queryToList(sql);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	    return rt;
	  }
	

	/**
	 * �ļ�ɾ��
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward toDeleteFileRecord(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) {
		
		Session hs = null;
		Transaction tx = null;
		String id = request.getParameter("filesid");
		String delsql="";
		List list=null;
		
		String folder = request.getParameter("folder");
		if(null == folder || "".equals(folder)){
			folder ="engineQuoteProcess.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		
		try {
			hs = HibernateUtil.getSession();
			tx = hs.beginTransaction();
			if(id!=null && id.length()>0){
				
				String sql="select a from Fileinfo a where a.fileSid='"+id.trim()+"' ";
				
				list=hs.createQuery(sql).list();	//ȡ�ļ���Ϣ
				hs.flush();
				
				delsql="delete from Fileinfo where fileSid='"+id.trim()+"'";
				hs.connection().prepareStatement(delsql).execute();
				hs.flush();
				
				HandleFile hf = new HandleFileImpA();
				if(list!=null && !list.isEmpty()){
				Fileinfo fp=(Fileinfo)list.get(0);
				
				String newfilename=fp.getNewFileName();
				String filepath=fp.getFilePath();
				String localPath = folder+filepath+newfilename;
				hf.delFile(localPath);//ɾ�������е��ļ�
				}
			}
				
						
	        response.setContentType("text/xml; charset=UTF-8");
	      
			//�������������
	        PrintWriter out = response.getWriter();
	        //������֤��������ͬ��������Ϣ	       
	        out.println("<response>");  
	        int b=list.size();
			if(b==0){
				out.println("<res>" + "N" + "</res>");
			}
			else{
				
				out.println("<res>" + "Y" + "</res>");
				
			}
			 out.println("</response>");
		     out.close();
		     	
		     tx.commit();
		} catch (Exception e) {
			try {
				tx.rollback();
			} catch (HibernateException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}finally{
			try {
				if(hs!=null){
					hs.close();
				}
			} catch (HibernateException hex) {
				DebugUtil.print(hex, "Hibernate close error!");
			}
		}	
		return null;
	}
	
	
	
	
	
	
	/**
	 * �ļ����ط���
	 * @param response
	 * @param localPath ���ش����ļ�����·�� ��:(D:/WebProjects/xxxxxx2010��ڼ���.jpg)
	 * @param loname  �ļ��߼����� ��:(2010��ڼ���.jpg)
	 * @throws IOException
	 */
	public void toDownLoadFiles(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
		throws IOException {
		/*
		 * ȡ���ļ�:�ļ�id+�ļ�·��+�ļ���+�� �ļ�id=ͨ��formbeanȡ�� �ļ�·��=ͨ��ȡ�������ļ��ķ����õ�
		 * �ļ�����=ͨ�����ݿ�õ� ��=io
		 */
		Session hs = null;

		String filesid = request.getParameter("filesid");//��ˮ��
		String localPath="";
		String oldname="";
		
		String folder = request.getParameter("folder");		//�ļ���
		if(null == folder || "".equals(folder)){
			folder ="engineQuoteProcess.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		
		try {
			hs = HibernateUtil.getSession();
			String sqlfile="select a from Fileinfo a where a.fileSid='"+filesid+"'";
			List list2=hs.createQuery(sqlfile).list();

			if(list2!=null && list2.size()>0){
				Fileinfo fp=(Fileinfo)list2.get(0);
				
				String filepath=fp.getFilePath();
				String newnamefile=fp.getNewFileName();
				oldname=fp.getOldFileName();
				String root=folder;//�ϴ�Ŀ¼
				localPath = root+filepath+newnamefile;
				
			}
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(oldname, "utf-8"));

		fis = new FileInputStream(localPath);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);

		int bytesRead = 0;
		byte[] buffer = new byte[5 * 1024];
		while ((bytesRead = bis.read(buffer)) != -1) {
			bos.write(buffer, 0, bytesRead);// ���ļ����͵��ͻ���
			bos.flush();
		}
		if (fos != null) {fos.close();}
		if (bos != null) {bos.close();}
		if (fis != null) {fis.close();}
		if (bis != null) {bis.close();}
		
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {				
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
	}
	public void toDownLoadFiles1(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response) 
		throws IOException {
		/*
		 * ȡ���ļ�:�ļ�id+�ļ�·��+�ļ���+�� �ļ�id=ͨ��formbeanȡ�� �ļ�·��=ͨ��ȡ�������ļ��ķ����õ�
		 * �ļ�����=ͨ�����ݿ�õ� ��=io
		 */
		Session hs = null;

		String matersid = request.getParameter("matersid");
		String localPath="";
		String oldname="";
		
		String folder = request.getParameter("folder");		//�ļ���
		if(null == folder || "".equals(folder)){
			folder ="elevatorLock.file.upload.folder";
		}
		folder = PropertiesUtil.getProperty(folder);
		
		try {
			hs = HibernateUtil.getSession();
			String sqlfile="select a from Fileinfo a where a.materSid='"+matersid+"'";
			List list2=hs.createQuery(sqlfile).list();

			if(list2!=null && list2.size()>0){
				Fileinfo fp=(Fileinfo)list2.get(0);
				
				String filepath=fp.getFilePath();
				String newnamefile=fp.getNewFileName();
				oldname=fp.getOldFileName();
				String root=folder;//�ϴ�Ŀ¼
				localPath = root+filepath+newnamefile;
				
			}
		
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		OutputStream fos = null;
		InputStream fis = null;

		response.setContentType("application/x-msdownload");
		response.setHeader("Content-disposition","attachment;filename="+ URLEncoder.encode(oldname, "utf-8"));

		fis = new FileInputStream(localPath);
		bis = new BufferedInputStream(fis);
		fos = response.getOutputStream();
		bos = new BufferedOutputStream(fos);

		int bytesRead = 0;
		byte[] buffer = new byte[5 * 1024];
		while ((bytesRead = bis.read(buffer)) != -1) {
			bos.write(buffer, 0, bytesRead);// ���ļ����͵��ͻ���
			bos.flush();
		}
		if (fos != null) {fos.close();}
		if (bos != null) {bos.close();}
		if (fis != null) {fis.close();}
		if (bis != null) {bis.close();}
		
		
		} catch (IOException e) {
			e.printStackTrace();
		} catch (DataStoreException e) {
			e.printStackTrace();
		} catch (HibernateException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {				
				hs.close();
			} catch (HibernateException e) {
				e.printStackTrace();
			}
		}
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
	public InputStream readFile(String fileName)
			throws FileNotFoundException {
//		fileName="smb://administrator:gzunicorn12345678@192.168.1.121/2013-12-25/113112564041.png";
//		String np=fileName.substring(6,fileName.indexOf("@"));
//		fileName=fileName.replace(np+"@","");
//		NtlmPasswordAuthentication auth = new NtlmPasswordAuthentication("",
//		    		np.split(":")[0], np.split(":")[1]);
		    
		SmbFile remoteFile=null;
		InputStream in = null;
		try {
//			remoteFile = new SmbFile(fileName,auth);
			remoteFile = new SmbFile(fileName);
			
			if(remoteFile.exists()){ 
				in = new SmbFileInputStream(fileName);
	        }
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SmbException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return in;
	}
}
