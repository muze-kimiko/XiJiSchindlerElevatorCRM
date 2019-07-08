<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>
<script language="javascript" charset="GBK">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nsalarybonusmanage" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/salaryBonusManageSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
  if(verify()){
	  document.salaryBonusManageForm.projects.value=rowsToJSONArray("prb","projects");
	  document.salaryBonusManageForm.isreturn.value="N";
	  document.salaryBonusManageForm.submit();
  }
  
}

//���淵��
function saveReturnMethod(){
  inputTextTrim();  
  if(verify()){
	  document.salaryBonusManageForm.projects.value=rowsToJSONArray("prb","projects");
	  document.salaryBonusManageForm.isreturn.value="Y";
      document.salaryBonusManageForm.submit();
    } 
}

//��ӱ���������Ŀ
function addElevators(tableId){

 var maintStation=document.getElementById("maintStation").value;
 if(maintStation==""||maintStation==null){
		alert("��ѡ��ά��վ!");
		return;
 }
	
  var paramstring = "maintStation="+maintStation+"&maintContractNos=";    
  var maintContractNos = document.getElementsByName("maintContractNo");
  for(i=0;i<maintContractNos.length;i++){
    paramstring += i<maintContractNos.length-1 ? "|"+maintContractNos[i].value+"|," : "|"+maintContractNos[i].value+"|"    
  }

  var returnarray = openWindowWithParams("searchProjectReimbursementAction","toSearchRecord2",paramstring);//�����򲢷���ֵ

  if(returnarray!=null && returnarray.length>0){          
    addRows(tableId,returnarray.length);//������
    toSetInputValue(returnarray,"salaryBonusManageForm");//��ҳ���Ӧ�����ֵ
  }    
  
  //setTopRowDateInputsPropertychange(document.getElementById(tableId));
}

function sumValuesByName(tableId,name,outputId){
	 var objs = document.getElementById(tableId);
	 var inputs=objs.getElementsByTagName("input");
	 var sum = 0;
	 var value = 0;
	 for(var i=0;i<inputs.length;i++){
		 if(inputs[i].name==name){
			 value = parseFloat(inputs[i].value);
			 sum =  isNaN(value) ? sum + 0 : accAdd(sum, value);
		 }
	 }
	 document.getElementById(outputId).value = sum;
}

function Average(tableId,name,outputId){
	var objs=document.getElementById(tableId);
	var rows=(objs.rows).length-2;
	var total=document.getElementById(outputId).value;
	
	if(rows>0){
		if(total!=null && total!=""){
			var sum=parseFloat(total);
			var money=(sum/rows).toFixed(2);
			var inputs=objs.getElementsByTagName("input");
			for(var i=0;i<inputs.length;i++){
				if(inputs[i].name==name){
					inputs[i].value=money;
				}
			}
		}else{
			alert("���ʡ������ܶ�Ϊ�գ������빤�ʡ������ܶ");
		}
		
	}else{
		alert("����ӹ��ʡ�������ϸ��");
	}
}

/*
���table��*�ű�ǵ��ı�������ֵ�Ƿ�Ϊ�ղ���ʾ�����������¸�ʽ
����: <table id="XX">
   <tr id="XX"><td>����*</td><tr>
     <tr><td><input type="text"/></td><tr> 
     <table>
            ����ֵʱ����   ����������Ϊ�ա�
*/
function checkRowInput(tableId,titleRowId){
  var tableObject = document.getElementById(tableId);
  var rows = tableObject.rows; 
  var titleRowIndex = 0;
  var msg ="";

  for(var i=0;i<rows.length;i++){
    var cells = tableObject.rows[i].cells;
    var inputs = rows[i].getElementsByTagName("input");
    var selects = rows[i].getElementsByTagName("select");
    if(rows[i].id == titleRowId){
      titleRowIndex = i; 
    }
    
    for(var j=0;j<inputs.length;j++){
      var cellIndex = getFirstSpecificParentNode("td",inputs[j]).cellIndex;
      
      if(rows[titleRowIndex].cells[cellIndex]!=null&&rows[titleRowIndex].cells[cellIndex].innerHTML.indexOf("*")>=0){
        if(inputs[j].type=="text" && inputs[j].value==""){
          var title = rows[titleRowIndex].cells[cellIndex].innerHTML;            
          msg +="��"+(i-titleRowIndex)+"�� "+title.replace(/\s*<+.*>+\s*/g,"")+"����Ϊ��\n";    
        }        
      }
      
    }  
    
    for(var j=0;j<selects.length;j++){
        var cellIndex = getFirstSpecificParentNode("td",selects[j]).cellIndex;        
        if(rows[titleRowIndex].cells[cellIndex]!=null&&rows[titleRowIndex].cells[cellIndex].innerHTML.indexOf("*")>=0){
          if(selects[j].value==""){
            var title = rows[titleRowIndex].cells[cellIndex].innerHTML;            
            msg +="��"+(i-titleRowIndex)+"�� ��ѡ�� "+title.replace(/\s*<+.*>+\s*/g,"")+"\n";    
          }        
        }
        
      }
    
  }
        
  if(msg != ""){
    alert(msg);
    return false;
  } 
  return true;
}

function isNanTable(){
	var prows=document.getElementById("prb").rows;
	var msg="";
	if(prows.length<=2){
		msg+="����ӹ��ʡ�������ϸ��\n"
	}
	if(msg!=""){
		alert(msg);
		return false;
	}
	return true;
}

function verify(){
	if(isNanTable() && checkColumnInput(salaryBonusManageForm) && checkRowInput("prb","prbT_0")){
		return true;
	}
	return false;
}

function checkPact(obj){
	var rowTr=obj.parentElement.parentElement;
	var inputs=rowTr.getElementsByTagName("input");
	var bustype="";
	var billno="";
	var actionName="";
	for(var i=0;i<inputs.length;i++){
		if(inputs[i].name=="busType"){
			bustype=inputs[i].value;
		}
		if(inputs[i].name=="billno"){
			billno=inputs[i].value;
		}
	}
	//alert(bustype+":"+billno);
	if(bustype=="B"){
		actionName="maintContractAction";
	}else{
		actionName="wgchangeContractAction";
	}
	simpleOpenWindow(actionName,billno,"typejsp=Yes");
}

function displayCheck(billno,bustype){
	var actionName="";
	if(bustype=="B"){
		actionName="maintContractAction";
	}else{
		actionName="wgchangeContractAction";
	}
	simpleOpenWindow(actionName,billno,"typejsp=Yes");
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