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

  <logic:notPresent name="workisdisplay">
	  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
	  //AddToolBarItemEx("ViewFlow","../../common/images/toolbar/viewdetail.gif","","",'�鿴��������',"110","1","viewFlow()");
	  <%-- �Ƿ��п�д��Ȩ��--%>
	  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nlostelevatorreportaudit3" value="Y">
	  	<logic:notEqual name="lostElevatorReportBean" property="auditStatus3" value="Y">
	  	AddToolBarItemEx("AuditBtn","../../common/images/toolbar/save.gif","","",'���ͨ��',"90","1","auditing('Y')");
	  	AddToolBarItemEx("RejectBtn","../../common/images/toolbar/delete.gif","","",'����',"65","1"," auditing('N')");
	  	</logic:notEqual>
	  </logic:equal>
  </logic:notPresent>
  <logic:present name="workisdisplay">
  		AddToolBarItemEx("CloseBtn","../../common/images/toolbar/close.gif","","",'<bean:message key="toolbar.close"/>',"65","0","closeMethod()");
  </logic:present>
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//�ر�
function closeMethod(){
  window.close();
}

//����
function returnMethod(){
	window.location = '<html:rewrite page="/lostElevatorReportAudit3SearchAction.do"/>?method=toSearchRecord';
}

//���
function auditing(value){
	var submitType=document.lostElevatorReportAuditForm.submitType.value;
	var auditStatus3=document.lostElevatorReportAuditForm.auditStatus3.value;
	if(submitType!="Y"){
		alert("�˼�¼û���ύ������ˣ�");
		return;
	}
	if(auditStatus3=="Y"){
		alert("�˼�¼�Ѿ���ˣ������ظ���ˣ�");
		return;
	}
	
	if(value=='N'){
		var auditRem3=document.lostElevatorReportAuditForm.auditRem3.value;
		if(auditRem3==""){
			alert("������Ҫ��д��������");
			return;
		}
	}
	
	document.lostElevatorReportAuditForm.auditStatus3.value=value;
	document.getElementById("ReturnBtn").disabled='true';
	document.getElementById("AuditBtn").disabled='true';
	document.getElementById("RejectBtn").disabled='true';
	document.lostElevatorReportAuditForm.submit();
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