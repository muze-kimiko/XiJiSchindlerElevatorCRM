<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite forward='validator'/>"></script>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" src="<html:rewrite forward='pageJS'/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/math.js"/>"></script>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ncustomercisitfeedback" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    <logic:present name="submit">  
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'���沢�ύ',"90","1","saveMethod2()");
   </logic:present>
    </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

//����
function returnMethod(){
	
       window.location = '<html:rewrite page="/customerVisitFeedbackSearchAction.do"/>?method=toSearchRecord';

}

//����
function saveMethod(){
	<logic:present name="submit"> 
	inputTextTrim();
	if(checkInput()){
		document.customerVisitFeedbackForm.isreturn.value = "Y";
		document.getElementById("isSubmitType").value="N";
 		document.customerVisitFeedbackForm.submit();	
	}
	</logic:present>
	<logic:notPresent name="submit"> 
		var visitStaff=document.getElementById("visitStaff").value;
		if(visitStaff==""){
			alert("��ת���� ����Ϊ�գ�");
			return;
		}
		document.customerVisitFeedbackForm.submit();
	</logic:notPresent>
}

//�����ύ
function saveMethod2(){
	inputTextTrim();
	if(checkInput()){
		document.customerVisitFeedbackForm.isreturn.value = "Y";
		document.getElementById("isSubmitType").value="Y";
 		document.customerVisitFeedbackForm.submit();	
	}
}

//��ӷ�����Ϣ
function addElevators(tableId){
	var jnlno = customerVisitFeedbackForm.jnlno.value;	
	
	if(jnlno!=null&&jnlno!=""){
		var paramstring = "jnlno="+jnlno+"&elevatorNos=";		
		var billNos = document.getElementsByName("billNo");
		for(i=0;i<billNos.length;i++){
			paramstring += i<billNos.length-1 ? "|"+billNos[i].value+"|," : "|"+billNos[i].value+"|"		
		}
		
		var returnarray = openWindowWithParams("searchProjectNameAction","toSearchRecord",paramstring);

		if(returnarray!=null && returnarray.length>0){					
			 addRows(tableId,returnarray.length);//������
			toSetInputValue(returnarray,"customerVisitFeedbackForm");
		}		
	}else{
		alert("������ӣ�");
	}
	
}

function checkInput(){
	 //return checkRowInput("dynamictable_0","titleRow_0");
	 var visitPeople= document.getElementById("visitPeople").value;
	 if(visitPeople.trim()==""){
		 alert("���ݷ���  ����Ϊ�գ�");
		 return false;
	 }
	 var visitPeoplePhone= document.getElementById("visitPeoplePhone").value;
	 if(visitPeoplePhone.trim()==""){
		 alert("���ݷ��˵绰  ����Ϊ�գ�");
		 return false;
	 }
	 
	 var projectName = document.getElementsByName("projectName");
	 if(projectName.length>0){
		 var realvisitDate = document.getElementsByName("realvisitDate");
		 var visitContent = document.getElementsByName("visitContent");
		 var isreturn=true;
		 for(var i=0;i<projectName.length;i++){
			 if(realvisitDate[i].value==""){
				 alert("��"+(i+1)+" �� ʵ�ʰݷ����� ����Ϊ�� ��");
				 isreturn=false;
				 break;
			 }
			 if(visitContent[i].value==""){
				 alert("��"+(i+1)+" �� �ݷ�����/�ջ� ����Ϊ�� ��");
				 isreturn=false;
				 break;
			 }
		 }
		 return isreturn;
	 }else{
		 alert("����� �ݷ���Ŀ��Ϣ ��");
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