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

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nentrustcontractquotemaster" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="tollbar.saveandrefer"/>',"120","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/entrustContractQuoteMasterSearchAction.do"/>?method=${returnMethod}';
}

//����
function saveMethod(){

  if(checkColumnInput(entrustContractQuoteMasterForm)){
    //document.entrustContractMasterForm.maintContractDetails.value=rowsToJSONArray("dynamictable_0","details");
    document.entrustContractQuoteMasterForm.submitType.value = "N";
    document.entrustContractQuoteMasterForm.isreturn.value = "N";
    document.entrustContractQuoteMasterForm.submit();
  }
  
}

//���淵��
function saveReturnMethod(){
  inputTextTrim();  
  if(checkColumnInput(entrustContractQuoteMasterForm)){
	  //document.entrustContractMasterForm.maintContractDetails.value=rowsToJSONArray("dynamictable_0","details");
	  document.entrustContractQuoteMasterForm.submitType.value = "Y";
      document.entrustContractQuoteMasterForm.isreturn.value = "Y";
      document.entrustContractQuoteMasterForm.submit();
    } 
}

function choiceCustomer(){
	var returnarray=openWindowWithParams('searchCustomerAction','toSearchRecord2','quote=WB');
	toSetInputValue2(returnarray,"");
}

function calculation(){
	var standard=document.getElementsByName("standardPrice")[0];
	var real=document.getElementsByName("realPrice")[0];
	var markups=document.getElementsByName("markups")[0];
	var m=0;
	if(!isNaN(standard.value) && !isNaN(real.value)){
		m=(real.value-standard.value)/standard.value;
		if(m>0)
			markups.value=parseFloat(m*100).toFixed(2);
		else
			markups.value=0.00;
	}else{
		var agr=null;
		if(isNaN(standard.value)){
			agr="��׼ί�м۸�ֻ���������֣����������룡";
			standard.value=0;
		}
		if(isNaN(real.value)){
			agr="ʵ��ί�м۸�ֻ���������֣����������룡";
			real.value=0;
		}
		if(agr!=null){
			alert(agr);
			markups.value=0.00;
		}
	}
}

function checkInput(){
	inputTextTrim();// ҳ�������ı������ȥ��ǰ��ո�
	var standard=document.getElementsByName("standardPrice")[0];
	var real=document.getElementsByName("realPrice")[0];
	var boo=false;
	if(!isNaN(standard.value) && !isNaN(real.value)){
		boo=checkColumnInput(entrustContractQuoteMasterForm);
	}else{
		var agr=null;
		if(isNaN(standard.value)){
			agr="��׼ί�м۸�ֻ���������֣����������룡";
		}
		if(isNaN(real.value)){
			agr="ʵ��ί�м۸�ֻ���������֣����������룡";
		}
		if(agr!=null){
			alert(agr);
		}
	}
	return boo;
}

//�����ͬ��Ч��
function setContractPeriod(){
  var sd = entrustContractQuoteMasterForm.contractSdate.value.split("-");
  var ed = entrustContractQuoteMasterForm.contractEdate.value.split("-");
  if(sd != "" && ed != ""){  
    var years = Number(ed[0])-Number(sd[0]);
    var months = years>0 ? 12*years - (Number(sd[1]) - Number(ed[1])) : Number(ed[1]) - Number(sd[1]);
    months = Number(ed[2])-Number(sd[2])>0 ? months+1 : months;  
    entrustContractQuoteMasterForm.contractPeriod.value = months;     
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