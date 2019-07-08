
package com.gzunicorn.common.util;

/**
 * Created on 2005-7-12
 * <p>Title:	</p>
 * <p>Description:	ϵͳ��������</p>
 * <p>Copyright: Copyright (c) 2005
 * <p>Company:�����Ƽ�</p>
 * @author wyj
 * @version	1.0
 */

public class SysConfig {

    public final static String WEB_APPNAME = "XJSCRM";//WebӦ�ó�������
   
    public final static String LOGIN_ACTION_NAME = "Logon.do";//��¼ACTION����
    
    public final static String LOGIN_OUT_PAGE = "logout/logout.jsp";//��¼ACTION����
    
    public final static String CONFIG_FILENAME = "com/gzunicorn/common/resources/Config.properties";//�����ļ�������
    public final static String HIBERNATE_CFG_FILENAME= "/com/gzunicorn/hibernate/hibernate.cfg.xml";//Hibernate�����ļ�������

    public final static String TOOLBAR_RIGHT = "TOOLBAR_RIGHT";//��¼�û�ToolbarȨ����Ϣ
    
    public final static String LOGIN_USER_INFO = "USER_INFO";//��¼�û���Ϣ

    public final static String BACKGROUD_USER_ID = "Background";//ϵͳ��̨�û�ID
    
    public final static String ALL_LOGIN_USER_INFO = "ALL_USER_INFO";//���е�¼�û���ϢLIST
    
    public final static int TREE_NODE_LEVEL = 2;//���ڵ�����ı��볤��
    
    public final static String TREE_ROOT_NODE = "d.add(0,-1,'XJSCRM',null,'XJSCRM','main');\n";//�����ڵ����;
    
    public final static int HTML_TABLE_LENGTH = 12;//HTML���List��¼����;
    
    public final static int HTML_TABLE_LENGTH50 = 50;//HTML���List��¼����;
    
    public final static String NULL_URL = "#";//û�����ӽڵ��URL����Ŀ¼�ڵ�;
    
    /****�������״̬*****/
    public final static String COMMERCEASSAYSTATE_A = "A";//����
    public final static String COMMERCEASSAYSTATE_B = "B";//�ڲ�������
    public final static String COMMERCEASSAYSTATE_C = "C";//����ͨ��
    public final static String COMMERCEASSAYSTATE_D = "D";//������ͨ��
    public final static String COMMERCEASSAYSTATE_E = "E";//�г�����

	 public final static String switch_flag ="N";//���ر�־:Y :��ʾ N:����ʾ

	 public final static String INFOISSUE_READER_TYPE_DEPT = "1";// ����
	 public final static String DOC_PURVIEW_TYPE_USER = "0";// ����
	 public final static String DOC_PURVIEW_TYPE_DEPT = "1";// ����
	 public final static String DOC_PURVIEW_TYPE_CLASS = "3";// ְλȺ��
	 
	 public final static int QUOTECONNECT_VERSION_JNLNO_LEN = 6;//����������汾�ų���
	 public final static String WORKSPACEBASEFIT_JNLNO_FLAG = "WO";//���۵��ݺ�ͬ��ˮ�ű�־
	 
	 /************CRM��̨�Զ��������⼼��Ҫ�����  end ************/
	 
	 /*��ȡ����ƽ̨ ����б� ��ʶֵ,��WorkspaceBaseProperty��WSKey ֵ��Ӧ*/
	 public final static String TOGET_DUTYS = "MyDutys";//��ȡ �˵������б�
	 public final static String TOGET_Fault = "FaultRepairEntry";//��ȡ ���ϱ����б�
	 public final static String TOGET_Contract = "ContractMaster";//��ȡ ά����ͬ����
	 public final static String TOGET_Complaints = "AdvisoryComplaintsManage";//��ȡ ȫ�ʰ���ѯͶ�߹���
	 public final static String TOGET_QualityCheck = "QualityCheckManagement";//��ȡ ά��������� 
	 public final static String TOGET_ElevatorTrans = "ElevatorTransferCaseRegister";//��ȡ ��װά�����ӵ������
	 public final static String TOGET_CustomerVisit = "CustomerVisitPlanDetail";//��ȡ �ͻ��ݷ÷���
	 public final static String TOGET_ContractDelay = "MaintContractDelayMaster";//ά����ͬ�ӱ��������� 
	 public final static String TOGET_ContractMaster = "MaintContractMaster";//ά����ͬ/ί�к�ͬ���� 
	 public final static String TOGET_LostElevator = "LostElevatorReport";//ά����ͬ�˱����� 
	 public final static String TOGET_MaintContractQuoteMaster = "MaintContractQuoteMaster";//ά���������ͨ������ 
	 public final static String TOGET_ContractInvoice = "ContractInvoiceManage";//��ͬ��Ʊ������� 
	 public final static String TOGET_CustReturnRegister = "CustReturnRegisterMaster";//�ͻ��طõǼ�
	 public final static String TOGET_ContractAssigned = "ContractAssigned";//ά����ͬ�´�ǩ��
	 public final static String TOGET_LoginUserAudit = "LoginUserAudit";//�û���Ϣ���
	 public final static String TOGET_HotCalloutAudit  = "HotCalloutAudit";//���ް�ȫ�������
	 public final static String TOGET_AccessoriesRequisition  = "AccessoriesRequisition";//������뵥(�ֿ����Ա)����
	 public final static String TOGET_AccessoriesRequisitionCkbl  = "AccessoriesRequisitionCkbl";//������뵥������� 
	 public final static String TOGET_TransferAssign  = "ContractTransferAssign";//��ͬ���������ɹ� 
	 public final static String TOGET_TransferUpload  = "ContractTransferUpload";//��ͬ���������ϴ�
	 public final static String TOGET_TransferAudit  = "ContractTransferAudit";//��ͬ�����������
	 public final static String TOGET_TransferFeedBack  = "ContractTransferFeedBack";//��ͬ�������Ϸ����鿴
	 public final static String TOGET_TransferManagerAudit  = "ContractTransferManagerAudit";//��ͬ�������Ͼ������
	 
     private SysConfig() {
     
    }
    
}
