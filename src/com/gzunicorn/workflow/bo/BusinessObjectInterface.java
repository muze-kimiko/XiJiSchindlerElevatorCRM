package com.gzunicorn.workflow.bo;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionForm;

import com.gzunicorn.common.util.Msg;

public interface BusinessObjectInterface {
	/****����������Ϣ����********************************************************************************************/
	/**
	 * �½���������
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toPrepareApply(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ���漰�ύ��������
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toSaveAndSubmitApply(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ������������
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toSaveApply(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ׼��������������
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toPrepareUpdateApply(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ������������
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toUpdateApply(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ���¼��ύ����
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toUpdateAndSubmitApply(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * �鿴������Ϣ
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toViewProcess(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ɾ��������Ϣ
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toDelProcess(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ��ѯ������Ϣ
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toSearchProcess(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ����Excel
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toExportExcel(ActionForm form,HttpServletRequest request,HashMap arg);
	
	
	/****������Ϣ����********************************************************************************************/
	/**
	 * ׼������
	 */
	public Msg toPrepareApprove(ActionForm form,HttpServletRequest request,HashMap arg);
	/**
	 * �����������
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toSaveApprove(ActionForm form,HttpServletRequest request,HashMap arg);
	/**
	 * ��������������ύ
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toSaveAndSubmitApprove(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ������ύ���ݼ��
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg toApplyCheck(ActionForm form,HttpServletRequest request,HashMap arg);
	
	/**
	 * ȡҵ�������Ϣ
	 * @param flag
	 * @param jnlno
	 * @param tokenid
	 * @return
	 */
	public Msg getBO(int flag,String jnlno,String tokenid);
	
	/**
	 * ȡҵ�������Ϣ
	 * @param form
	 * @param request
	 * @param arg
	 * @return
	 */
	public Msg getBO(ActionForm form,HttpServletRequest request,HashMap arg);
	
	
}
