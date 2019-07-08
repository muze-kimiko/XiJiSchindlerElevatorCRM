package test;

import java.io.File;  
import java.util.Date;  
import java.util.Properties;
import javax.activation.DataHandler;  
import javax.activation.DataSource;  
import javax.activation.FileDataSource;  
import javax.mail.BodyPart;  
import javax.mail.Message;  
import javax.mail.Multipart;  
import javax.mail.Session;  
import javax.mail.Transport;  
import javax.mail.internet.InternetAddress;  
import javax.mail.internet.MimeBodyPart;  
import javax.mail.internet.MimeMessage;  
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;  
  
/*** 
 *  �����ʼ�������
 * */  
public class SendEmailFile {  
    
	/**
	 * �����ʼ�
	 * @param subject ����
	 * @param toMail ��������
	 * @param content �ʼ�����
	 * @param files ��������·�������Ϊ�վͲ����������������ļ����� D:/Download��Ҳ�������ļ��� D:/Download/�����ļ�·��.txt��
	 * @return
	 */
    public static boolean sendMail(String subject,String toMail,String content,String files) {  
        boolean isFlag = false;  
        try {  
        	System.out.println("���ڷ����ʼ�.........");
        	
            String smtpFromMail = "lj@gzunicorn.com";  //�˺�  
            String pwd = "abc123456*"; //����  
            int port = 25; //�˿�  
            String host = "smtp.gzunicorn.com"; //�˿�  
            
//            String smtpFromMail = "crm@guangri.net";  //�˺�  
//            String pwd = "12345678"; //����  
//            int port = 25; //�˿�  
//            String host = "www.guangri.net"; //�˿�  
  
            Properties props = new Properties();  
            props.put("mail.smtp.host", host);  
            props.put("mail.smtp.auth", "true");  
            props.put("mail.smtp.ssl.enable", "true");  
            Session session = Session.getDefaultInstance(props);  
            session.setDebug(false);  
  
            MimeMessage message = new MimeMessage(session);  
            try {
                message.setFrom(new InternetAddress(smtpFromMail, "QQ�ʼ�����"));  
                message.addRecipient(Message.RecipientType.TO,new InternetAddress(toMail));  
                message.setSubject(subject);  
                message.addHeader("charset", "UTF-8");  

                /*�����������*/  
                Multipart multipart = new MimeMultipart();
                //����ʼ�����  
                BodyPart contentBodyPart = new MimeBodyPart();  
                //�ʼ�����  
                contentBodyPart.setContent(content, "text/html;charset=UTF-8");  
                multipart.addBodyPart(contentBodyPart); 
                  
                /*��Ӹ���*/ 
                if(files!=null && !files.trim().equals("")){
	                //for (String file : files) {  
	                    File usFile = new File(files);  
	                    MimeBodyPart fileBody = new MimeBodyPart();  
	                    DataSource source = new FileDataSource(files);  
	                    fileBody.setDataHandler(new DataHandler(source));  
	                    
	                    System.out.println("......"+usFile.getName());
	
	                    fileBody.setFileName(MimeUtility.encodeWord(usFile.getName()));
	                    multipart.addBodyPart(fileBody);  
	               // }  
                }
                message.setContent(multipart);  
                message.setSentDate(new Date());  
                message.saveChanges();  
                Transport transport = session.getTransport("smtp");  
  
                transport.connect(host, port, smtpFromMail, pwd);  
                transport.sendMessage(message, message.getAllRecipients());  
                transport.close();  
                isFlag = true;  
                
                System.out.println("�����ʼ��ɹ�.........");
            } catch (Exception e) {  
                isFlag = false;  
                e.printStackTrace();
                System.out.println("�����ʼ�ʧ��1.........");
            }  
        } catch (Exception e2) {  
            e2.printStackTrace();  
            System.out.println("�����ʼ�ʧ��2.........");
        }  
        return isFlag;  
    }  
  
    public static void main(String[] args) {
    	//String content="���Ѻþò���";
    	String content = "<a href='http://crm.guangri.net:9000/GRCRM/contractProcessAction.do?method=toDisplayRecord target='_blank'>abc�������ľٰ�˵��</a>";  
    	String filepath="D:/Download/�����ļ�·��.txt";
    	
    	boolean isFlag=SendEmailFile.sendMail("���", "wwlijun@foxmail.com", content,filepath); 
    	
    }  
      
}  






