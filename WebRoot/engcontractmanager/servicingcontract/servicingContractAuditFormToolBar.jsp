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
  <logic:equal name="ServicingContractMasterList" property="auditStatus" value="δ���">
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save.gif","","","���ͨ��","80","1","Auditing('Y')");
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save.gif","","","����","60","1","Auditing('N')");
 </logic:equal>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/wgchangeContractAuditSearchAction.do"/>?method=toSearchRecord';
}
//���
function Auditing(value){
	var submitType=document.wgchangeContractForm.submitType.value;
	var auditStatus=document.wgchangeContractForm.auditStatus.value;
	if(submitType!="Y"){
		alert("�˼�¼û���ύ������ˣ�");
		return;
	}
	if(auditStatus!="N"){
		alert("�˼�¼�Ѿ���ˣ������ظ���ˣ�");
		return;
	}
	document.wgchangeContractForm.auditStatus.value=value;
	document.wgchangeContractForm.submit();
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