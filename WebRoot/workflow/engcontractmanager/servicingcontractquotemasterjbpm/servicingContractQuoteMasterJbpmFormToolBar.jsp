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

  //AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  AddToolBarItemEx("ReturnTkBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="button.returntask"/>',"110","0","returnTaskMethod()");	
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmyTaskOA" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="button.submit"/>',"65","1","saveMethod()");
  </logic:equal>
  </logic:notPresent>
  
  AddToolBarItemEx("ViewFlow","../../common/images/toolbar/viewdetail.gif","","",'<bean:message key="toolbar.viewflow"/>',"110","1","viewFlow()");
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//ȥ���ո�
String.prototype.trim = function(){ return this.replace(/(^\s*)|(\s*$)/g,""); }

//����
/* function returnMethod(){
  window.location = '<html:rewrite page="/maintContractQuoteSearchAction.do"/>?method=toSearchRecord';
} */

//���ش����б�
function returnTaskMethod(){
	window.location ='<html:rewrite page="/myTaskOaSearchAction.do"/>?method=toDoList&jumpto=templetdoorapp';
}

//�ύ
function saveMethod(){
	var typejsp=document.getElementById("typejsp").value;
	if(typejsp!=null && typejsp=='mondity'){
		if(checkInput()){
		    var elevatorNos = document.getElementsByName("elevatorNo");
		    var materialName = document.getElementsByName("materialName");
			if(elevatorNos !=null && elevatorNos.length>0){
				if(materialName !=null && materialName.length>0){
						var flags=getSelectValue();
						if(flags!="" && flags!="1"){
							alert("��Ǹ,��ѡ����'��ͬ��',�����������������д��������!");
							return;
						}else{
							document.getElementById("SaveBtn").disabled=true;
							document.WeiGchangeForm.submit();
						}
					}else{
						alert("���ܱ��棬�����������ϸ��");
					}
			}else{
				alert("���ܱ��棬����ӵ�����Ϣ��");
			}
		}
	}else{		
			document.getElementById("SaveBtn").disabled=true;
			document.WeiGchangeForm.submit();
	
	}
}

//�鿴����
function viewFlow(){	
	var hiddenReturnStatus=document.getElementById("status");
	if(hiddenReturnStatus.value=="-1"){
		alert("����δ�������޷��鿴��������ͼ��");
	}else{
		var avf=document.getElementById("avf");
		var tokenid=document.getElementById("tokenid");
		var flowname=document.getElementById("flowname");
		if(tokenid!=null && tokenid.value!=""){
			if(avf!=null && tokenid!=null && flowname!=null){
				avf.href='<html:rewrite page="/viewProcessAction.do"/>?method=toViewProcess&tokenid='+tokenid.value+'&flowname='+flowname.value;
				avf.click();
			}else{
				alert("��ѡ��һ��Ҫ�鿴�ļ�¼��");
			}
		}else{
			alert("�ü�¼Ϊ��ʷ���ݣ��޷��鿴��������ͼ��");
		}
	}
}

//ȡselect���ı�ֵ
function getSelectValue(){
	var str="0";
	var obj=document.getElementById('approveresult');

 	var index=obj.selectedIndex; //��ţ�ȡ��ǰѡ��ѡ������	
 	var val = obj.options[index].text;
 	if(val!="" && (val=="ͬ��" || val.indexOf("�ύ")>-1)){
 	    str="1";
 	}else{
 		if(document.getElementById("approverem").value.trim()!="" || document.getElementById("approverem").value.trim().length>0){
 			str="1";
 		}
 	}
 	return str;
	
}

function fuz(){
	a=document.getElementsByName("xuhao");
	for(i=0;i<a.length;i++){
		a[i].value = i+1;
	}
} 

//��ӵ�����Ϣ
function addElevators(tableId){
		var paramstring = "elevatorNos=";		
		var elevatorNos = document.getElementsByName("elevatorNo");
		for(i=0;i<elevatorNos.length;i++){
			paramstring += i<elevatorNos.length-1 ? "|"+elevatorNos[i].value+"|," : "|"+elevatorNos[i].value+"|"		
		}

		var returnarray = openWindowWithParams("searchElevatorSaleAction","toSearchRecord2",paramstring);

		if(returnarray!=null && returnarray.length>0){
			addRows(tableId,returnarray.length);
			toSetInputValue(returnarray,"WeiGchangeForm");
		}			
}

//����Ԫ��ָ���ĸ��ڵ����
function getFirstSpecificParentNode(parentTagName,childObj){
	var parentObj = childObj.parentNode;
	
	while(parentObj){
		if(parentObj.nodeName.toLowerCase() == parentTagName.toLowerCase()){				
			break;
		}
		parentObj = parentObj.parentNode;
	}
	
	return parentObj;
}

//�ж�ҳ���ֵ
function checkInput(){
	if(checkColumnInput(WeiGchangeForm) && checkRowInput('table_22','tb_head') && checkRowInput('table_1','wordtd_header')){
		return true;
	}else{
		return false;
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