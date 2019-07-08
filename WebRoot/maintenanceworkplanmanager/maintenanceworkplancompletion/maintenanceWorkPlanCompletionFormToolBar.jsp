<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  
  	<logic:present name="isopenshow">
		AddToolBarItemEx("closeBtn","../../common/images/toolbar/close.gif","","",'�� ��',"65","0","closeMethod()");
	</logic:present>
	<logic:notPresent name="isopenshow">
		AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
	</logic:notPresent>
   
   <logic:notPresent name="display">  
   <%-- �Ƿ��п�д��Ȩ��--%>
   <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenanceworkplancompletion" value="Y"> 
     AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
     AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
   </logic:equal>
  </logic:notPresent>
  <logic:present name="deletemore">
  		AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/delete.gif", "", "",'ɾ ��', "65", "1", "deleteMethod()");
  </logic:present>
   
   window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");

}

//ɾ��
function deleteMethod(){
	var elevatorNo=document.getElementById("elevatorNo").value;
	var sdate=document.getElementById("sdate").value;
	var edate=document.getElementById("edate").value;
	if(elevatorNo==""){
		alert("����д����ɾ���ĵ��ݱ�ţ�");
	}else{
		if(confirm("�Ƿ�ɾ�����ݱ��Ϊ"+elevatorNo+"�µı����ƻ���")){
			document.maintenanceWorkPlanCompletionForm.submit();
		}
		
	}
	
}
//�ر�
function closeMethod(){
  window.close();
}

//����
function returnMethod(){
  window.location = '<html:rewrite page="/maintenanceWorkPlanCompletionSearchAction.do"/>?method=toSearchRecord';
}

function saveMethod(){

      document.maintenanceWorkPlanCompletionForm.isreturn.value = "N";
      document.getElementById("ReturnBtn").disabled=true;
      document.getElementById("SaveBtn").disabled=true;
      document.getElementById("SaveReturnBtn").disabled=true;
      document.maintenanceWorkPlanCompletionForm.submit();
}

//���淵��
function saveReturnMethod(){
     document.maintenanceWorkPlanCompletionForm.isreturn.value = "Y";
     document.getElementById("ReturnBtn").disabled=true;
     document.getElementById("SaveBtn").disabled=true;
     document.getElementById("SaveReturnBtn").disabled=true;
     document.maintenanceWorkPlanCompletionForm.submit();
}



//����
function downloadFile(id){
	var uri = '<html:rewrite page="/maintenanceWorkPlanCompletionAction.do"/>?method=toDownLoadFiles';
		uri +='&filesid='+ id;
		uri +='&folder=MaintenanceWorkPlan.file.upload.folder';
	window.location = uri;
	//window.open(url);
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