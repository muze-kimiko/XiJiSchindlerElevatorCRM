<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nlostelevatorregistermaster" value="Y">
  	<logic:present name="contract"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    </logic:present>
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
	
	var isreturn=document.getElementById("isreturn2").value;
	if(isreturn=="2")
		{
		window.location = '<html:rewrite page="/lostElevatorRegisterMasterAction.do"/>?id='+ document.getElementById("companyId").value +'&method=toHistoryDisplayRecord';
		}else
			{
            window.location = '<html:rewrite page="/lostElevatorRegisterMasterAction.do"/>?method=toSearchRecord';
			}
}

//�ж��Ƿ�Ϊ����
function onlyNumber(c){
	  if(!/^\d+$/.test(c.value)){
		   var tile="<bean:message key='markingitems.check.error.cgprice.required'/>";
		  alert(tile);
		  c.value=0;
	  }
}

//ֻ����������
function f_check_number2(){
	  	if((event.keyCode>=48 && event.keyCode<=57)){
	  	}else{
			event.keyCode=0;
	   	}
	 }
//����
function saveMethod(){
	var handle=document.getElementById("ministerHandle");	
	  if(handle.value!=""){
	  //alert(rowsToJSONArray("registerDetail","details"));
		  document.lostElevatorRegisterMasterForm.isreturn.value = "Y";
		  //document.lostElevatorRegisterMasterForm.lostElevators.value=rowsToJSONArray("registerDetail","details");
	      document.lostElevatorRegisterMasterForm.submit();
	   }else
	   {
		   alert("��ѡ���Ƿ�ֲ�������");
	   }
}

</script>

  <tr>
    <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr >
          <td height="22" class="table_outline3" valign="bottom" width="100%" >
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