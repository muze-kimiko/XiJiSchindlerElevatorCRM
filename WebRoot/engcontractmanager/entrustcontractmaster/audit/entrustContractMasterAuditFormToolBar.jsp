<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nentrustcontractmasteraudit" value="Y">
  AddToolBarItemEx("PassBtn","../../common/images/toolbar/digital_confirm.gif","","",'���ͨ��',"85","1","passMethod()");
  AddToolBarItemEx("RejsctBtn","../../common/images/toolbar/delete.gif","","",'�� ��',"65","1","rejectMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/entrustContractMasterSearchAction.do"/>?method=toSearchRecordAudit';
}

//���ͨ��
function passMethod(){
	if(confirm("�Ƿ�ȷ�����ͨ����ί�к�ͬ��")){
		document.entrustContractMasterForm.auditStatus.value = "Y";
	    document.entrustContractMasterForm.submitType.value = "Y";
		document.entrustContractMasterForm.isreturn.value = "Y";
	    document.entrustContractMasterForm.submit();
	}
	
}

//����
function rejectMethod(){
	if(confirm("ȷ��Ҫ���ظ�ί�к�ͬ��")){
		document.entrustContractMasterForm.auditStatus.value = "N";
	    document.entrustContractMasterForm.submitType.value = "R";
		document.entrustContractMasterForm.isreturn.value = "Y";
	    document.entrustContractMasterForm.submit();
	}
	
}
</script>

  <tr>
    <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td height="22" class="table_outline3" valign="bottom" width="100%">
            <div id="toolbar" align="center">
            <script language="javascript">
              CreateToolBar();
            </script>
            </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>