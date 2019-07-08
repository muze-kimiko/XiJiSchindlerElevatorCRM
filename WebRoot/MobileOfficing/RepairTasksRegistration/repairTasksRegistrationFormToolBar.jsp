<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
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
   <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenanceregistration" value="Y"> 
   <logic:notEmpty name="btn1">
     AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'${btn1}',"65","1","saveMethod()");
     </logic:notEmpty>
     <logic:notEmpty name="btn2">
     AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'${btn2}',"65","1","saveReturnMethod()");
     </logic:notEmpty>
     
     <logic:notEmpty name="btn1">
     <logic:equal name="btn1" value="�� ��">
     AddToolBarItemEx("SaveRequisitionBtn","../../common/images/toolbar/add.gif","","","������뵥","90","1","saveRequisitionMethod()");
     AddToolBarItemEx("SaveRequisition2Btn","../../common/images/toolbar/add.gif","","","���뼼��֧��","100","1","saveRequisitionMethod2()");
     </logic:equal>
     </logic:notEmpty>
     <logic:notEmpty name="btn2">
     <logic:equal name="btn2" value="�� ��">
     AddToolBarItemEx("SaveRequisitionBtn","../../common/images/toolbar/add.gif","","","������뵥","90","1","saveRequisitionMethod()");
     AddToolBarItemEx("SaveRequisition2Btn","../../common/images/toolbar/add.gif","","","���뼼��֧��","100","1","saveRequisitionMethod2()");
     </logic:equal>
     </logic:notEmpty>
     
     </logic:equal>
  </logic:notPresent>
   
   window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");

}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/repairTasksRegistrationSearchAction.do"/>?method=toSearchRecord';
}

function saveMethod(){
      var btn=document.getElementById("btn").value;
      isReturn(btn);
}

//���淵��
function saveReturnMethod(){
	var btn=document.getElementById("btn2").value;
	isReturn(btn);
}
//�������
function saveRequisitionMethod(){
	var numno=document.getElementById("calloutMasterNo").value;
	window.location = '<html:rewrite page="/repairTasksRegistrationAction.do"/>?id='+numno+'&method=toAccessoriesRequisition';
}
//���뼼��֧��
function saveRequisitionMethod2(){
	var numno=document.getElementById("calloutMasterNo").value;
	window.location = '<html:rewrite page="/repairTasksRegistrationAction.do"/>?id='+numno+'&method=toTechnologySupport';
}
//����
function downloadFile(id){
	var uri = '<html:rewrite page="/repairTasksRegistrationAction.do"/>?method=toDownLoadFiles';
		uri +='&filesid='+ id;
		uri +='&folder=MaintenanceWorkPlan.file.upload.folder';
	window.location = uri;
	//window.open(url);
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
	ince1.innerHTML="<input id=\'file_"+fileGird+"\' name=\'"+fileGird+"\' type=\'file\' class=\'default_input\' />";
	fileGird++;
}
//ɾ����
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
function isReturn(btn){
	if(btn=="�� ��"){ 
		if(document.getElementById("assignTime").value==""){
			alert("����ʱ�䲻��Ϊ��!");return;
		}
	 document.repairTasksRegistrationForm.btn.value="1";
	}else if(btn=="ת ��"){
		if(document.getElementById("turnSendTime").value==""){
			alert("ת��ʱ�䲻��Ϊ��!");return;
		}
		
		var turnSendId=document.getElementById("turnSendId").value;
		if(turnSendId==""||turnSendId==null){
			alert("ת���˲���Ϊ��!");
			return;
		}
		document.repairTasksRegistrationForm.btn.value="0";
	}else if(btn=="�� ��"){
	   var input = checkColumnInput1(arriveTable);
	    if(!input){
	    	return;
	    }
		document.repairTasksRegistrationForm.btn.value="2";
	}else if(btn=="ͣ ��"){
		var isStop=document.getElementById("isStop").value;
		if(isStop==""||isStop==null){
			alert("��ѡ���Ƿ�ͣ��!");
			return;
		}
		if(isStop=="Y"){
			if(document.getElementById("stopTime").value==""){
				alert("ͣ��ʱ�䲻��Ϊ��!");return;
			}
		}
		if(isStop=="Y"){
			if(document.getElementById("stopRem").value==""){
				alert("ͣ�ݱ�ע����Ϊ��!");return;
			}
		}
			
		document.repairTasksRegistrationForm.btn.value="3";
	}else if(btn=="�� ��"){
		var input = checkColumnInput1(completeTable);
	    if(!input){
	    	return;
	    }
	    
	    if(document.getElementById("hftId").value==""){
			alert("��ѡ���������!");return;
        }
	    /* if(document.getElementById("isToll").value==""){
				alert("��ѡ���Ƿ��շ�!");return;
	    }
		 if(document.getElementById("isToll").value=="Y"){
		    	if(document.getElementById("money").value==""){
					alert("�շѽ���Ϊ��!");return;
				}
		    } */
		document.repairTasksRegistrationForm.btn.value="4";
	}
	document.repairTasksRegistrationForm.submit();
}

function checkColumnInput1(element){
	  var inputs = element.getElementsByTagName("input");
	  var textareas = element.getElementsByTagName("textarea");
	  var selects = element.getElementsByTagName("select");
	  var msg = "";
	  for(var i=0;i<inputs.length;i++){  
	    if(inputs[i].type == "text"
	      && getFirstSpecificParentNode("td",inputs[i]) != null && inputs[i].parentNode.innerHTML.indexOf("*")>=0 
	      && inputs[i].value.trim() == ""){ 
	        msg += getFirstSpecificParentNode("td",inputs[i]).previousSibling.innerHTML.replace(":","")+"����Ϊ��\n";                       
	    }
	  }
	  
	  for(var i=0;i<textareas.length;i++){
	      if(textareas[i].parentNode.innerHTML.indexOf("*")>=0 && getFirstSpecificParentNode("td",textareas[i]) != null && textareas[i].value.trim() == ""){          
	          msg += getFirstSpecificParentNode("td",textareas[i]).previousSibling.innerHTML.replace(":","") +"����Ϊ��\n";                       
	      }
	  }
	  if(msg != ""){
	    alert(msg);
	    return false;
	  } 
	  return true;
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