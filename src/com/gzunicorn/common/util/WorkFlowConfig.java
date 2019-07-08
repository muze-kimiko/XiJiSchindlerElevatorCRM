package com.gzunicorn.common.util;

public class WorkFlowConfig {
	
	/**
	 * ���̶���
	 */
	//public static String Process_AgentGoodsPlan="AgentGoodsPlan";
	
	//public static String Process_FeeApply="feeapply";
	
	//public static String Process_CarExtSale="CarExtSale";
	
	public static String Process_InstanceId="InstanceId";
	
	public static String[][] Process_Define={
												{"enginequotemaster","enginequotemaster"},//��������״̬
												{"enginequotemasterProcessName","ά��������������"},
												{"maintcontractdelaymaster","maintcontractdelaymaster"},
												{"maintcontractdelaymasterProcessName","ά����ͬ�ӱ���������"},
												{"elevatortransfercaseregister","elevatortransfercaseregister"},
												{"elevatortransfercaseregisterProcessName","��װά�����ӵ�����������"},
												{"servicingcontractquotemaster","servicingcontractquotemaster"},
												{"servicingcontractquotemasterProcessName","ά�ı�����������"},	
												{"qualitycheckmanagement","qualitycheckmanagement"},
												{"qualitycheckmanagementProcessName","ά�����������������"},	
												{"contractpaymentmanage","contractpaymentmanage"},
												{"contractpaymentmanageProcessName","��ͬ������������"},
												{"outsourcecontractquotemaster","outsourcecontractquotemaster"},
												{"outsourcecontractquotemasterProcessName","ά��ί�б�����������"},	
												{"entrustcontractquotemaster","entrustcontractquotemaster"},
												{"entrustcontractquotemasterProcessName","ά��ί�б�����������"}										
												
											};
	
	
	public static int State_NoStart=-1;		//δ��������
	public static int State_ApplyMod=0;		//�������޸�
	public static int State_Approve=100;	//������
	public static int State_Pass=1;			//����ͨ��
	public static int State_NoPass=2;		//������ͨ��
	public static int State_CutOff=3;		//��ֹ
	
	/**
	 * [][1] 0:ͬ�������1:��ͬ�������2:������㣬[][2]=0 nopass,[][1] pass
	 */
	public static String[][] TranKeyWord={{"ͬ��","0",""},
										{"�ύ","0",""},
										{"�˻�","1",""},
										{"��ͬ��","1",""},
										{"��ֹ","2","0"},
										{"��ֹ","2","0"},
										{"ȡ��","2","0"},
										{"ͨ��","2","1"}};
	/***********************************************************************************************************/
	/**
	 * ���̽�ɫ����
	 */
	/**������*/
	public static String Role_AGP_Agent="Role_AGP_Agent";
	/**������*/
	public static String Role_AGP_ZonalManager="Role_AGP_ZonalManager";
	/**��������Ա*/
	public static String Role_AGP_OrderAdmin="Role_AGP_OrderAdmin";
	/**��������*/
	public static String Role_AGP_SaleManager="Role_AGP_SaleManager";
	
	
//	public static String RoleAgent="agent";
//	public static String RoleAgent="agent";
//	public static String RoleAgent="agent";
//	public static String RoleAgent="agent";
//	public static String RoleAgent="agent";
	
	/***********************************************************************************************************/
	public static String Flow_IssueMan="Flow_IssueMan";
	
	public static String Flow_Client="Flow_Client";
	
	public static String Flow_RefUserId="Flow_RefUserId";
	
	public static String Approve_Level="Approve_Level";
	
	public static String Approve_Result="Approve_Result";
	
	public static String Approve_Result_MsgInfo="Approve_Result_MsgInfo";
	
	public static boolean Flow_IsAutoActor=true;
	
	public static String Flow_NodeId="Flow_NodeId";
	
	public static String Decision_Amount="Decision_Amount";
	
	/**������Ϣ����*/
	public static String Flow_Bean="Flow_Bean";
	
	/**������Ϣ����*/
	public static String Appoint_Actors="Appoint_Actors";
	
	public static String Flow_Pass_Prefix="ͬ��";
	public static String Flow_NoPass_Prefix="��ͬ��";
	/***********************************************************************************************************/

	/**
	 * ���õķ���,�ɼ�ֵ��ȡ����Ӧ������ֵ
	 * @param str
	 * @param key
	 * @return
	 */
	private static synchronized String getNameByKey(String[][] str,String key ){
		String name = "";
		if(str!=null){
			int len=str.length;
			for(int i=0;i<len;i++){
				if(str[i][0].equalsIgnoreCase(key)){
					name = str[i][1];
					break;
				}
			}
		}
		return name;
	}

	/**
	 * ��ģ����ϴ����������ϴ��ļ������·��
	 * @param key
	 * @return
	 */
	public static String getProcessDefine(String key ){
		return getNameByKey(Process_Define,key);
	}
	
	/**
	 * �ж��Ƿ���ͨ���ؼ���
	 * @param selpath
	 * @return
	 */
	public static synchronized boolean isPassTranKeyWord(String selpath){
		if(selpath!=null && selpath.length()>0){
			for(int i=0;i<TranKeyWord.length;i++){
//				if(TranKeyWord[i][1].equalsIgnoreCase("0") && selpath.indexOf(TranKeyWord[i][0])!=-1){
//					return true;
//				}
				if(TranKeyWord[i][1].equalsIgnoreCase("0") && selpath.startsWith(TranKeyWord[i][0])){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	
	/**
	 * �ж��Ƿ��Ƿ�ͨ���ؼ���
	 * @param selpath
	 * @return
	 */
	public static synchronized boolean isNoPassTranKeyWord(String selpath){
		if(selpath!=null && selpath.length()>0){
			for(int i=0;i<TranKeyWord.length;i++){
//				if(TranKeyWord[i][1].equalsIgnoreCase("1") && selpath.indexOf(TranKeyWord[i][0])!=-1){
//					return true;
//				}
				if(TranKeyWord[i][1].equalsIgnoreCase("1") && selpath.startsWith(TranKeyWord[i][0])){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	
	/**
	 * �ж��Ƿ��Ƿ�ͨ���ؼ���
	 * @param selpath
	 * @return
	 */
	public static synchronized boolean isNoPassTranKeyWord(String[] selpath){
		if(selpath!=null && selpath.length>0){
			for(int j=0;j<selpath.length;j++){
				for(int i=0;i<TranKeyWord.length;i++){
	//				if(TranKeyWord[i][1].equalsIgnoreCase("1") && selpath.indexOf(TranKeyWord[i][0])!=-1){
	//					return true;
	//				}
					if(TranKeyWord[i][1].equalsIgnoreCase("1") && selpath[j].startsWith(TranKeyWord[i][0])){
						return true;
					}
				}
			}
			return false;
		}else{
			return false;
		}
	}
	
	/**
	 * �ж��Ƿ���ͨ���������
	 * @param selpath
	 * @return
	 */
	public static synchronized boolean isPassEndNodeKeyWord(String key){
		if(key!=null && key.length()>0){
			for(int i=0;i<TranKeyWord.length;i++){
				if(TranKeyWord[i][1].equalsIgnoreCase("2") && key.indexOf(TranKeyWord[i][0])!=-1 && TranKeyWord[i][2].equalsIgnoreCase("1")){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}
	
	/**
	 * �ж��Ƿ��Ƿ�ͨ���������
	 * @param selpath
	 * @return
	 */
	public static synchronized boolean isNoPassEndNodeKeyWord(String key){
		if(key!=null && key.length()>0){
			for(int i=0;i<TranKeyWord.length;i++){
				if(TranKeyWord[i][1].equalsIgnoreCase("2") && key.indexOf(TranKeyWord[i][0])!=-1 && TranKeyWord[i][2].equalsIgnoreCase("0")){
					return true;
				}
			}
			return false;
		}else{
			return false;
		}
	}

}
