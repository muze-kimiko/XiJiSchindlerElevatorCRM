<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" charset="GBK">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nelevatortransfercaseregistermanage" value="Y"> 
    //AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="tollbar.saveandrefer"/>',"120","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/elevatorTransferCaseRegisterManageSearchAction.do"/>?method=${returnMethod}';
}
//���淵��
function saveReturnMethod(){
	var propv=document.getElementById("projectProvince").value
	if(propv.trim()==""){
		alert("��Ŀʡ�� ����Ϊ�գ�");
		return;
	}
  //inputTextTrim();
  if(checkColumnInput(elevatorTransferCaseRegisterManageForm)){
	  //rowsToJSONArray("party_a","hecis")
	  //window.document.getElementById("hecirs").value=rowsToJSONArray("party_a","hecis");
	  document.elevatorTransferCaseRegisterManageForm.submitType.value = "Y";
	  document.elevatorTransferCaseRegisterManageForm.isreturn.value = "Y";
      document.elevatorTransferCaseRegisterManageForm.submit();
      
    } 
}

function toElevator(actionName,flag)
{
	var idType=document.getElementById("salesContractType").value;
	var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
	var url="query/Search.jsp?path="+projectName+actionName+".do?idType="+idType;  
	var returnvalue = window.showModalDialog(url,window,'dialogWidth:770px;dialogHeight:500px;dialogLeft:200px;dialogTop:150px;center:yes;help:yes;resizable:yes;status:yes');	 
	toSetInputValue2(returnvalue,flag); 
}

//��ӽ��ӵ��ݼ����Ŀ
function addElevators(tableId,type,name,flag){

	var paramstring1 = "checkItems=";
	var paramstring2="examTypes=";
	var paramstring3="issueCodings=";
	var issueCodings=document.getElementsByName("issueCoding");
	var examTypes=document.getElementsByName("examType");
	var checkItems = document.getElementsByName("checkItem");
	var examType="examType="+type;
	var examName=encodeURI("examName="+name);
	examName=encodeURI(examName);
  for(i=0;i<checkItems.length;i++){
    paramstring1 += i<checkItems.length-1 ? checkItems[i].value+"|" : checkItems[i].value;
    paramstring2+=i<examTypes.length-1? examTypes[i].value+"|" : examTypes[i].value;
    paramstring3+=i<issueCodings.length-1? issueCodings[i].value+"|" : issueCodings[i].value;
  }
  paramstring1=encodeURI(paramstring1);
  paramstring1=encodeURI(paramstring1);
  paramstring2=encodeURI(paramstring2);
  paramstring2=encodeURI(paramstring2);
  paramstring3=encodeURI(paramstring3);
  paramstring3=encodeURI(paramstring3);

  var returnarray = openWindowWithParams1("searchHandoverElevatorCheckItemAction","toSearchRecord",paramstring1,paramstring2,paramstring3,examType,examName);//�����򲢷���ֵ

  if(returnarray!=null && returnarray.length>0){          
    addRows(tableId,returnarray.length);//������
    toSetInputValue3(returnarray,"elevatorTransferCaseRegisterForm",flag);//��ҳ���Ӧ�����ֵ
  }    
}
//��table��tbody�������з�װ��json�����ʽ���ַ���
function rowsToJSONArray(tableId,key){
	var table = document.getElementById(tableId);
	var tbody = table.getElementsByTagName("tbody")[0];
	var trs = tbody.childNodes
	var result = '{"'+key+'":[';
  
	for(var i=0;i<trs.length;i++){
		var tds = trs[i].cells;
		var rowJson = "";
    
		for(var j=0;j<tds.length;j++){
			var cNodes = tds[j].childNodes;
           
			for(var k=0;k<cNodes.length;k++){
				if(cNodes[k].value && cNodes[k].name && cNodes[k].name!=''){
					
						var temp = '"'+cNodes[k].name+'":"'+cNodes[k].value+'"';
						rowJson += j<tds.length-1? temp+',' : temp; 
					
				}
			}    
    	}
		var rowJson = '{'+rowJson+'}';
		result += i<trs.length-1 ? rowJson+',':rowJson;
	}
	result = result+']}';  
	return result;
}
/*
���������*�ű�ǵ��ı�������ֵ�Ƿ�Ϊ�ղ���ʾ�����������¸�ʽ
����: <td>����</td><td><input type="text"/>*</td> 
            ����ֵʱ����   ����������Ϊ�ա�
���� element ����div��table��form����
*/
function checkColumnInput(element){
	  var inputs = element.getElementsByTagName("input");
	  var selects = element.getElementsByTagName("select");
	  var msg = "";
	  
	  for(var i=0;i<inputs.length;i++){
	    if(inputs[i].type == "text"
	      && inputs[i].parentNode.innerHTML.indexOf("*")>=0 
	      && inputs[i].value.trim() == ""){
	      
	        msg += inputs[i].parentNode.previousSibling.innerHTML + "����Ϊ��\n";                       
	    }
	  }
	  for(var i=0;i<selects.length;i++){
		  if(selects[i].value.trim()=="" && selects[i].parentNode.innerHTML.indexOf("*")>0){
				  msg+="��ѡ��"+selects[i].parentNode.previousSibling.innerHTML.replace(/:$/gi,"")+"\n";
		  }
	  }
	  if(msg != ""){
		    alert(msg);
		    return false;
		  } 
		  return true;
}
//���ظ���
function downloadFile(name){
	var uri = '<html:rewrite page="/elevatorTransferCaseRegisterAction.do"/>?method=toDownloadFileRecord';
	var name=encodeURI(name);
	name=encodeURI(name);
		uri +='&filesname='+ name;
		uri +='&folder=HandoverElevatorCheckItemRegister.file.upload.folder';
	window.location = uri;
}
function isOkCheck(){
	var isCheck=document.getElementsByName("isCheck");
	var isOk=document.getElementsByName("isOk");
	for(var i=0;i<isCheck.length;i++){
		if(isCheck[i].checked == true){
			isOk[i].value="Y";
		}else{
			isOk[i].value="N";
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