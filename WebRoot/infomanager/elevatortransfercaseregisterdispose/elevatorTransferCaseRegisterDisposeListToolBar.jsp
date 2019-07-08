 <%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nelevatortransfercaseregisterdispose" value="Y"> 
    AddToolBarItemEx("ReceiveBtn","../../common/images/toolbar/edit.gif","","",'����',"65","1","receiveMethod()");
    AddToolBarItemEx("RejectBtn","../../common/images/toolbar/edit.gif","","",'����',"65","1","rejectMethod()");
    AddToolBarItemEx("TurnBtn","../../common/images/toolbar/edit.gif","","",'ת��',"65","1","turnMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'�Ǽ�',"65","1","modifyMethod()");
    AddToolBarItemEx("PrintBtn","../../common/images/toolbar/print.gif","","",'��ӡ֪ͨ��',"90","1","printMethod()");
    </logic:equal>
  
  //AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
  //serveTableForm.genReport.value = "";
  serveTableForm.target = "_self";
  document.serveTableForm.submit();
}

//�鿴
function viewMethod(){
  toDoMethod(checkedIndex(),"toDisposeDisplay","&returnMethod=toSearchRecordDispose","<bean:message key="javascript.role.alert2"/>");
}

//����
function receiveMethod(){
  var index = checkedIndex();

	if(index >= 0){	
		var processStatus=document.getElementsByName("processStatus")[index].value;//�ύ״̬
		
		if(processStatus != "0"){
			alert("��ѡ�� δ���յļ�¼,�����ղ���!"); 
			return;
		}
		
		toDoMethod(index,"toReceiveDisposeRecord","");
	
	} else {
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}

//�Ǽ�
function modifyMethod(){

	var index = checkedIndex();

	if(index >= 0){	
		var processStatus=document.getElementsByName("processStatus")[index].value;//�ύ״̬
		
		if(processStatus != "4"&&processStatus != "1"){
			alert("��ѡ�� �ѽ��ջ��ѵǼ�δ�ύ�ļ�¼,���Ǽǲ���!"); 
			return;
		}
		
		toDoMethod(index,"toPrepareDisposeRecord","");
	
	} else {
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}

//����
function rejectMethod(){

	var index = checkedIndex();

	if(index >= 0){	
		var processStatus=document.getElementsByName("processStatus")[index].value;//�ύ״̬
		
		if(processStatus != "4"&&processStatus != "0"){
			alert("��ѡ�� δ�Ǽǵļ�¼,�����ز���!"); 
			return;
		}
		
		toDoMethod(index,"toPrepareDisposeReject","");
	
	} else {
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}

//ת��
function turnMethod(){

	var index = checkedIndex();

	if(index >= 0){	
		var processStatus=document.getElementsByName("processStatus")[index].value;//�ύ״̬
		
		if(processStatus != "4"&&processStatus != "0"){
			alert("��ѡ��  δ�Ǽǵļ�¼,��ת�ɲ���!"); 
			return;
		}
		
		toDoMethod(index,"toPrepareDisposeTurn","");
	
	} else {
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}

//��ӡ
function printMethod(){
	var index = checkedIndex();
	if(index >= 0){	
		var processStatus=document.getElementsByName("processStatus")[index].value;//�ύ״̬
		
		if(processStatus != "2"&&processStatus != "3"){
			alert("����״̬Ϊ�ѵǼ����ύ������˵�,�ſ��Դ�ӡ"); 
			return;
		}
		window.open('<html:rewrite page="/elevatorTransferCaseRegisterAction.do"/>?id='+document.getElementsByName("ids")[index].value+'&method=toPreparePrintDisposeRecord');
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");		
	}
}



//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/elevatorTransferCaseRegisterAction.do"/>?id='+document.getElementsByName("ids")[index].value+'&method='+method+params;
	}else if(alertMsg && alertMsg != ""){
		alert(alertMsg);
	}
}





function checkedIndex(){
	if(document.getElementsByName("ids").length){	
		var ids = document.getElementsByName("ids");
		for(var i=0;i<ids.length;i++){
		  if(ids[i].checked == true){
		    return i;
		  }
		}				
	}
	return -1;	
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