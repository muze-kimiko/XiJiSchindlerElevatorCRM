<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()"); 
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save.gif","","",'�� ��',"65","0","saveMethod()"); 
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save_back.gif","","",'���沢����',"90","0","saveReturnMethod()"); 
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/repairTasksRegistrationAction.do"/>?method=toPrepareUpdateRecord&id=${id}';
}

//����
function saveMethod(){
	
	var faultCode=document.getElementById("faultCode").value;
	var faultStatus=document.getElementById("faultStatus").value;
	if(faultCode==""||faultCode==null){
		alert("���ϴ��벻��Ϊ��!");
		return;
	}
	if(faultStatus==""||faultStatus==null){
		alert("����״̬����Ϊ��!");
		return;
	}

	document.getElementById("isreturn").value = "N";
    document.getElementById("Addform").submit();
}

//���淵��
function saveReturnMethod(){
	
	var faultCode=document.getElementById("faultCode").value;
	var faultStatus=document.getElementById("faultStatus").value;
	if(faultCode==""||faultCode==null){
		alert("���ϴ��벻��Ϊ��!");
		return;
	}
	if(faultStatus==""||faultStatus==null){
		alert("����״̬����Ϊ��!");
		return;
	}
	document.getElementById("isreturn").value = "Y";
    document.getElementById("Addform").submit();
}

var fileGird=0;
function AddRow(uploadtab_2){
	var upload=uploadtab_2.parentNode.parentNode.parentNode;
	var num=upload.rows.length;
	upload.insertRow(num);
	var ince0=upload.rows(num).insertCell(0);
	ince0.align="center";
	ince0.name="fileNo_"+fileGird
	ince0.innerHTML="<input type='checkbox' name='nodes' onclick=\'cancelCheckAll(this)\'>";
	var ince1=upload.rows(num).insertCell(1);
	ince1.innerHTML="<input id=\'file_"+fileGird+"\' name=\'"+fileGird+"\' type=\'file\' size=\'40\' class=\'default_input\' />";
	fileGird++;
}

function deleteFileRow(thisobj){
	var tableObj = getFirstSpecificParentNode("table",thisobj);
	
	var inputs = tableObj.getElementsByTagName("input");
	var checkboxs = new Array(); //table������checkbox
	for(var i=0;i<inputs.length;i++){
	    if(inputs[i].type=="checkbox"){
	    	checkboxs.push(inputs[i]);	      	
	    }
	}
	
	checkboxs[0].checked = false;//ȫѡ��ťȡ��ѡ�� 
	
	//��table�д�������ɾ��ѡ�е���
	for(var i=checkboxs.length-1;i>0;i--){	
		if(checkboxs[i].checked == true){
		  tableObj.deleteRow(getFirstSpecificParentNode("tr",checkboxs[i]).rowIndex);
		}				
	}
}

//�б�ȫѡ��ѡ
function checkTableFileAll(thisobj){
	var tableObj = getFirstSpecificParentNode("table",thisobj);
	var rows = tableObj.rows
	for(var i=1;i<rows.length;i++){
	  var inputs = rows[i].cells[0].getElementsByTagName("input");
	  for(j=0;j<inputs.length;j++){
	    if(inputs[j].type=="checkbox"){
	      inputs[j].checked = thisobj.checked;
	    }
	  }
	}
}

//ȫѡcheckboxȡ��ѡ��
function cancelCheckAll(thisobj){ 
  var tableObj = getFirstSpecificParentNode("table",thisobj);
  var inputs = tableObj.getElementsByTagName("input");  
  if(thisobj.checked==false){
    for(var j=0;j<inputs.length;j++){
	  if(inputs[j].type=="checkbox"){
	    inputs[j].checked = false; //table�е�һ��checkboxȡ��ѡ��
	    break;
	  }
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