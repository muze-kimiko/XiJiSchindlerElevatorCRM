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
	  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintcontractother" value="Y"> 
	    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'�� ��',"65","1","saveMethod()");   
	    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'���淵��',"85","1","saveReturnMethod()");
	  </logic:equal>
  </logic:notPresent>

  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");

}

//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}
//������Ƿ�Ϊ����,���������븺�źͿ���������
function f_number(){
 	if((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==46 ){
 	}else{
		event.keyCode=0;
  	}
}

//����
function returnMethod(){
  window.location = '<html:rewrite page="/maintContractOtherSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
	var errorstr="";
	var hidsta=document.getElementById("hidsta").value;
	if(hidsta=="update"){
		errorstr=checkinfo();
	}else{
		errorstr="";
	}
	if(errorstr==""){
		document.maintContractOtherForm.isreturn.value = "N";
		document.maintContractOtherForm.submit();
	}else{
		alert(errorstr);
	}
}

//���淵��
function saveReturnMethod(){
	var errorstr="";
	var hidsta=document.getElementById("hidsta").value;
	if(hidsta=="update"){
		errorstr=checkinfo();
	}else{
		errorstr="";
	}
	if(errorstr==""){
		document.maintContractOtherForm.isreturn.value = "Y";
		document.maintContractOtherForm.submit();
	}else{
		alert(errorstr);
	}
}

function checkinfo(){
	var errorstr="";
	var paydate=document.getElementById("paydate").value;
	if(paydate.trim()==""){
		errorstr="֧������ ����Ϊ�գ�\n";
	}
	var paymoney=document.getElementById("paymoney").value;
	var nototherfee=document.getElementById("nototherfee").value;
	if(paymoney.trim()==""){
		errorstr+="֧����� ����Ϊ�գ�\n";
	}else if(isNaN(paymoney.trim())){
		errorstr+="֧����� ����Ϊ���֣�\n";
	}else if(parseFloat(paymoney.trim())>parseFloat(nototherfee.trim())){
		errorstr+="֧����� ���ܴ��� δ֧����\n";
	}
	
	return errorstr;
}

//ȫѡ
function checkall(obj){
	var procheck=document.getElementsByName("procheck");
	for(var i=0;i<procheck.length;i++){
		procheck[i].checked=obj.checked;
	}
}
//ɾ��
function delselnode(ptable){
	var pbody=ptable.getElementsByTagName("TBODY")[0];
	var len=pbody.children.length;
	for(var i=len-1;i>=0;i--){
		if(pbody.children[i].firstChild.firstChild.checked==true){//ids
			pbody.deleteRow(i);
		}
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