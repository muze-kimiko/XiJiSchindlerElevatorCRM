/**
 * ��ʵ������axis��ʽ���ã���Ҫ����axis���jar��
 */
package com.gzunicorn.struts.action.xjsgg;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import org.apache.axis.client.Service;
import org.apache.axis.client.Call;
import org.apache.axis.encoding.XMLType;

import javax.xml.namespace.QName;
import javax.xml.rpc.ParameterMode;
import javax.xml.rpc.ServiceException;

/**
 * ���ŷ���ΪWebservices���� ������Ѹ���ṩ
 * @author Lijun
 *
 */
public class SmsService {
		
	/**
	 * ���޷��Ͷ���
	 * @param istrap �Ƿ�����
	 * @param elevatorno ���ݱ��
	 * @param address ��Ŀ���Ƽ�¥����
	 * @param telno �ֻ�����
	 * @return
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static boolean sendSMS(String istraptext,String elevatorno,String address, String telno)
			throws MalformedURLException, ServiceException, RemoteException {
		
		Boolean finash=true;
		// Sms Service(Ŀ��web service ·��)
		String endpoint = "http://10.10.0.111:8080/API/SmsService.asmx";
		// ����Service����
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();

			// ����SmsService��ַ
			call.setTargetEndpointAddress(new URL(endpoint));
			// �Ƿ���soap action,Ĭ��Ϊtrue
			call.setUseSOAPAction(false);
			// �趨����3���Ӳ�������ʱ
			call.setTimeout(new Integer(180000));
			// �����ռ�(WSDL�ļ��е�targetNameSpace����ֵ) �Լ��������������ķ�������
			call.setOperationName(new QName("http://XjsSms.org/", "DispatchWarning"));
			// ����ҳ���ڶ�Ӧ��SOAPAction��
			call.setSOAPActionURI("http://XjsSms.org/DispatchWarning");

			/**
			 * ��������ҪҪ��webservice�ӿ��е�����һ�£�����Ҫʹ��webserviceĿ�����Ե����� ��������
			 * ��call.addParameter
			 * ("KunRen",org.apache.axis.encoding.XMLType.XSD_STRING,
			 * ParameterMode.IN); �����еķ�ʽ��webservice���޷��������
			 */
			call.addParameter(new QName("http://XjsSms.org/", "KunRen"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName("http://XjsSms.org/", "ElevatorNo"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName("http://XjsSms.org/", "Address"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);
			call.addParameter(new QName("http://XjsSms.org/", "Phone"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);

			// ����ֵ����
			call.setReturnType(XMLType.XSD_STRING);

			//����
			String KunRen = istraptext;//�Ƿ�����
			String ElevatorNo = elevatorno;// ���ݱ��
			String Address = address;// �������ڵ�ַ
			String Phone = telno;// ά��Ա���ֻ�����
			// ��ȡ����ֵ
			String resXML = (String) call.invoke(new Object[] { KunRen, ElevatorNo, Address, Phone });

			System.out.println("����=SmsService������Ϣ :  " + resXML);
			finash=true;
		} catch (Exception e) {
			finash=false;
			e.printStackTrace();
		}
		return finash;
	}
	/**
	 * ������뷢�Ͷ���
	 * @param phone �ֻ�����
	 * @return
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static boolean compSendSMS(String phone)
			throws MalformedURLException, ServiceException, RemoteException {
		
		Boolean finash=true;
		// Sms Service(Ŀ��web service ·��)
		String endpoint = "http://10.10.0.111:8080/API/SmsService.asmx";
		// ����Service����
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();

			// ����SmsService��ַ
			call.setTargetEndpointAddress(new URL(endpoint));
			// �Ƿ���soap action,Ĭ��Ϊtrue
			call.setUseSOAPAction(false);
			// �趨����3���Ӳ�������ʱ
			call.setTimeout(new Integer(180000));
			
			// �����ռ�(WSDL�ļ��е�targetNameSpace����ֵ) �Լ��������������ķ�������
			call.setOperationName(new QName("http://XjsSms.org/", "ComponentSaleNotify"));
			// ����ҳ���ڶ�Ӧ��SOAPAction��
			call.setSOAPActionURI("http://XjsSms.org/ComponentSaleNotify");

			/**
			 * ��������ҪҪ��webservice�ӿ��е�����һ�£�����Ҫʹ��webserviceĿ�����Ե����� ��������
			 * ��call.addParameter
			 * ("KunRen",org.apache.axis.encoding.XMLType.XSD_STRING,
			 * ParameterMode.IN); �����еķ�ʽ��webservice���޷��������
			 */
			call.addParameter(new QName("http://XjsSms.org/", "Phone"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);

			// ����ֵ����
			call.setReturnType(XMLType.XSD_STRING);

			// ��ȡ����ֵ
			String resXML = (String) call.invoke(new Object[] { phone });

			System.out.println("�������=SmsService������Ϣ :  " + resXML);
			finash=true;
		} catch (Exception e) {
			finash=false;
			e.printStackTrace();
		}
		return finash;
	}
	/**
	 * ����֧�����뷢�Ͷ���
	 * @param phone �ֻ�����
	 * @return
	 * @throws MalformedURLException
	 * @throws ServiceException
	 * @throws RemoteException
	 */
	public static boolean techSendSMS(String phone)
			throws MalformedURLException, ServiceException, RemoteException {
		
		Boolean finash=true;
		// Sms Service(Ŀ��web service ·��)
		String endpoint = "http://10.10.0.111:8080/API/SmsService.asmx";
		// ����Service����
		Service service = new Service();
		Call call;
		try {
			call = (Call) service.createCall();

			// ����SmsService��ַ
			call.setTargetEndpointAddress(new URL(endpoint));
			// �Ƿ���soap action,Ĭ��Ϊtrue
			call.setUseSOAPAction(false);
			// �趨����3���Ӳ�������ʱ
			call.setTimeout(new Integer(180000));
			
			// �����ռ�(WSDL�ļ��е�targetNameSpace����ֵ) �Լ��������������ķ�������
			call.setOperationName(new QName("http://XjsSms.org/", "TechSupportNotify"));
			// ����ҳ���ڶ�Ӧ��SOAPAction��
			call.setSOAPActionURI("http://XjsSms.org/TechSupportNotify");

			/**
			 * ��������ҪҪ��webservice�ӿ��е�����һ�£�����Ҫʹ��webserviceĿ�����Ե����� ��������
			 * ��call.addParameter
			 * ("KunRen",org.apache.axis.encoding.XMLType.XSD_STRING,
			 * ParameterMode.IN); �����еķ�ʽ��webservice���޷��������
			 */
			call.addParameter(new QName("http://XjsSms.org/", "Phone"), org.apache.axis.encoding.XMLType.XSD_STRING, ParameterMode.IN);

			// ����ֵ����
			call.setReturnType(XMLType.XSD_STRING);

			// ��ȡ����ֵ
			String resXML = (String) call.invoke(new Object[] { phone });

			System.out.println("����֧������=SmsService������Ϣ :  " + resXML);
			finash=true;
		} catch (Exception e) {
			finash=false;
			e.printStackTrace();
		}
		return finash;
	}
	
}


