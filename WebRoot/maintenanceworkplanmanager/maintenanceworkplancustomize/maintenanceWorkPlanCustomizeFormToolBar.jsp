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

  <logic:equal name="typejsp" value="Yes">
    AddToolBarItemEx("SaveSubmitBtn","../../common/images/toolbar/close.gif","","",'�ر�',"60","0","window.close()");
  </logic:equal>
  <logic:notPresent name="typejsp">
    AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  </logic:notPresent>
  <logic:notPresent name="display">  
	  <%-- �Ƿ��п�д��Ȩ��--%>
	  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenanceworkplancustomize" value="Y"> 
	    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod('N')");   
	    <logic:notPresent name="doType">
	      AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'���沢�ύ����',"120","1","saveReturnMethod()");
	    </logic:notPresent>

	    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'���涨�Ʊ�ע',"120","1","saveMethod('Y')");  
	  </logic:equal>
  </logic:notPresent>
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");

}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/maintenanceWorkPlanCustomizeSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(isrem){
  if(checkInput()){
	  if(isrem=="Y"){
		  var customizeRem=document.getElementById("customizeRem").value;
		  if(customizeRem==""){
			  alert("���Ʊ�ע������Ϊ�գ�");
			  return;
		  }
	  }
	 document.getElementById("issaverem").value=isrem;
     document.maintenanceWorkPlanCustomizeForm.isreturn.value = "N";
     document.maintenanceWorkPlanCustomizeForm.submit();
  }
  
}

//���淵��
function saveReturnMethod(){
 //
  if(checkInput()){  
      document.maintenanceWorkPlanCustomizeForm.isreturn.value = "Y";
      document.maintenanceWorkPlanCustomizeForm.submit();
    } 
}


function checkInput()
{
	var mainSdates=document.getElementsByName("mainSdate");
	var mainEdates=document.getElementsByName("mainEdate");
	var isReturn=true;
	for(var i = 0;i < mainSdates.length; i++)
	{
		if(mainSdates[i].value=="")
		{
			alert("��"+(i+1)+"��,������ʼʱ��Ϊ��");
			isReturn=false;
			return;
		}
		if(mainEdates[i].value=="")
		{
			alert("��"+(i+1)+"��,��������ʱ��Ϊ��");
			isReturn=false;
			return;
		}
	}
	
  return  isReturn; 
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