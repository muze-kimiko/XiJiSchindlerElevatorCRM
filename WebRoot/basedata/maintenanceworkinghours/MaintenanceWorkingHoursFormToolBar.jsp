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
<logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenanceworkinghours" value="Y"> 
  AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
  AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
</logic:equal>
</logic:notPresent>
 window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  	 window.location = '<html:rewrite page="/maintenanceWorkingHoursSearchAction.do"/>?method=toSearchRecord';	
 }

//����
function saveMethod(){
  if(validateMaintenanceWorkingHoursForm(document.all.item("maintenanceWorkingHoursForm"))==true){
	document.maintenanceWorkingHoursForm.isreturn.value = "N";
    document.maintenanceWorkingHoursForm.submit();
  }
}

//���淵��
function saveReturnMethod(){
  if(validateMaintenanceWorkingHoursForm(document.all.item("maintenanceWorkingHoursForm"))==true){
    document.maintenanceWorkingHoursForm.isreturn.value = "Y";
    document.maintenanceWorkingHoursForm.submit();
 }
}

//�ж��Ƿ�Ϊ����
function onlyNumber(c){
	if(c.value!=""&&c.value!=null){
	  if(!/^\d+$/.test(c.value)){
		   var tile="<bean:message key='markingitems.check.error.cgprice.required'/>";
		  alert(tile);
		  c.value=0;
	  }
    }
}

//ֻ����������
function f_check_number2(){
	  	if((event.keyCode>=48 && event.keyCode<=57)){
	  	}else{
			event.keyCode=0;
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
                //SetToolBarAttribute();
              //-->
            </script>
            </div>
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>