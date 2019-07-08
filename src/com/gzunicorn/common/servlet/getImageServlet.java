package com.gzunicorn.common.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.gzunicorn.common.util.DateUtil;

public class getImageServlet extends HttpServlet {

	/**
	 * Constructor of the object.
	 */
	public getImageServlet() {
		super();
	}

	/**
	 * Destruction of the servlet. <br>
	 */
	public void destroy() {
		super.destroy(); // Just puts "destroy" string in log
		// Put your code here
	}

	/**
	 * The doGet method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to get.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.getImageRecord(request,response);
	}

	/**
	 * The doPost method of the servlet. <br>
	 *
	 * This method is called when a form has its tag value method equals to post.
	 * 
	 * @param request the request send by the client to the server
	 * @param response the response send by the server to the client
	 * @throws ServletException if an error occurred
	 * @throws IOException if an error occurred
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		this.getImageRecord(request,response);
	}

	/**
	 * Initialization of the servlet. <br>
	 *
	 * @throws ServletException if an error occurs
	 */
	public void init() throws ServletException {
		// Put your code here
	}
	/**
	 * ���ɶ�ά��
	 */
	private void getImageRecord(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {
		
		try {
			//��ȡ����·��
			String path = request.getContextPath();
			String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
			String pdfstr=basePath+"DownLoadApkServlet";//����ϵͳ
			
			//String pdfstr="http://10.10.0.5:8080/XJSCRM/DownLoadApkServlet";//����ϵͳ
			//String pdfstr="http://www.xjelevator.com:9000/XJSCRM/DownLoadApkServlet";//��ʽϵͳ
			//System.out.println(">>>>>="+pdfstr);

			//���ɶ�ά��ͼƬ
			pdfstr = new String(pdfstr.getBytes("UTF-8"), "ISO-8859-1"); //��ֹ��ά�������������
			BitMatrix byteMatrix = new MultiFormatWriter().encode(pdfstr, BarcodeFormat.QR_CODE, 150, 140);
			
			//��Ҫ����javase.jar��
			BufferedImage image = MatrixToImageWriter.toBufferedImage(byteMatrix);
			
			//����ά��ͼƬ����ɫ͸��,��д������
			BufferedImage image2=this.transparentImage(image,10);
			
			//��ͼƬ��д��������
			ImageIO.write(image2, "png", response.getOutputStream());
			
			/**
			//����ͼƬ������
			String stodaytime=DateUtil.getNowTime("yyyyMMddHHmmss");
			String savepath="D:\\Download\\"+stodaytime+".png";
			FileOutputStream fos=new FileOutputStream(savepath);
			ImageIO.write(image2, "png", fos);
			fos.close();
			*/
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/** 
	* ����ԴͼƬΪ����͸����������͸���� ����������
	* @param srcImage ԴͼƬ 
	* @param alpha ͸���� 1-10
	* @throws IOException 
	*/  
	private BufferedImage transparentImage(BufferedImage srcImage,int alpha) throws IOException {  
			int imgHeight = srcImage.getHeight();//ȡ��ͼƬ�ĳ��Ϳ�  
	        int imgWidth = srcImage.getWidth();  
	        int c = srcImage.getRGB(3, 3);  
	        //��ֹԽλ  
	        if (alpha < 0) {  
	            alpha = 0;  
	         } else if (alpha > 10) {  
	            alpha = 10;  
	         }  
	        
	        //�½�һ������֧��͸����BufferedImage
	        BufferedImage bi = new BufferedImage(imgWidth, imgHeight,BufferedImage.TYPE_4BYTE_ABGR);  
	        //��ԭͼƬ�����ݸ��Ƶ��µ�ͼƬ��ͬʱ�ѱ�����Ϊ͸��  
	        for(int i = 0; i < imgWidth; ++i){  
	            for(int j = 0; j < imgHeight; ++j){  
	            	//�ѱ�����Ϊ͸��  
	                if(srcImage.getRGB(i, j) == c){  
	                    bi.setRGB(i, j, c & 0x00ffffff);  
	                }else{  
		                //����͸���� 
	                	int rgb = bi.getRGB(i, j);  
	                    rgb = ((alpha * 255 / 10) << 24) | (rgb & 0x00ffffff);  
	                    bi.setRGB(i, j, rgb);  
	                }  
	            }  
	        }  
	        
	        /**============��ͼƬ������������================*/
			Graphics grap = bi.getGraphics();
			//������ɫ ��ɫ
			grap.setColor(Color.black);
			//���塢���͡��ֺ�
			grap.setFont(new Font("����",Font.PLAIN,12)); 
			//�������  [��ʾ�����ݣ� ����룬�Ͼ���]
			grap.drawString("��ɨ���ά��,���ؿͻ���",8,130);
			
	        return bi;
	}
	
}
