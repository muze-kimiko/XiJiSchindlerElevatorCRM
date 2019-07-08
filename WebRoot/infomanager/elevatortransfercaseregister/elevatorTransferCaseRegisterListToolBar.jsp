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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nelevatortransfercaseregistermanage" value="Y"> 
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'�� ��',"65","1","addFromQuoteMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modifyMethod()");
    AddToolBarItemEx("FactoryBtn","../../common/images/toolbar/edit.gif","","",'�ٴγ���',"80","1","factoryMethod()");
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/digital_confirm.gif","","",'�� ��',"65","1","referMethod()");
    AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()");
    AddToolBarItemEx("FactoryBtn","../../common/images/toolbar/close.gif","","",'�رճ���',"80","1","closeMethod()");
  </logic:equal>
  AddToolBarItemEx("PrintBtn","../../common/images/toolbar/print.gif","","",'��ӡ֪ͨ��',"90","1","printMethod()");
  
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
  toDoMethod(checkedIndex(),"toDisplayRecord","&returnMethod=toSearchRecord","<bean:message key="javascript.role.alert2"/>");
}

//��ά������ѡȡ¼��
function addFromQuoteMethod(){
  window.location = '<html:rewrite page="/elevatorTransferCaseRegisterManageAction.do"/>?method=toPrepareAddRecord';
}
//�رճ���
function closeMethod(){

	var index = checkedIndex();

	if(index >= 0){	
		var isClose=document.getElementsByName("isClose")[index].value;
		
		if(isClose == "Y"){
			alert("��ѡ��δ�رյĳ����¼!"); 
			return;
		}
		
		var elevatorNo=document.getElementsByName("elevatorNo")[index].value;
		if(confirm("�Ƿ�رճ��죬���ݱ�ţ�"+elevatorNo+"?")){
			toDoMethod(index,"toIsCloseRecord","");
		}
		
	
	} else {
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}
//�޸�
function modifyMethod(){

	var index = checkedIndex();

	if(index >= 0){	
		var submitType=document.getElementsByName("submitType")[index].value;//�ύ״̬
		
		if(submitType != "N"&&submitType != "R"){
			alert("�ü�¼���ύ����ת��,�����޸�!"); 
			return;
		}
		
		toDoMethod(index,"toPrepareUpdateRecord","");
	
	} else {
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}
//����
function copyMethod(){
	var index = checkedIndex();
	if(index >= 0){	
	toDoMethod(index,"toPrepareCopyRecord","");
	} else {
		alert("��ѡ��һ��Ҫ���Ƴ���Ա�ļ�¼��");
	}
}


//ɾ��
function deleteMethod(){
	var index = checkedIndex();
	
	if(index >= 0){	
		var submitType=document.getElementsByName("submitType")[index].value;//�ύ״̬
		
		if(submitType != "N"&&submitType != "R"){
			alert("�ü�¼���ύ����ת��,����ɾ��!"); 
			return;
		}
		
		if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids[index].value)){
			toDoMethod(index,"toDeleteRecord","");
		}
	} else {
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}

// �ύ
function referMethod(){
	var index = checkedIndex();
	if(index >= 0){	
		var submitType=document.getElementsByName("submitType")[index].value;//�ύ״̬
		
		if(submitType != "N"&&submitType != "R"){
			alert("�ü�¼���ύ����ת��,�����ύ!"); 
			return;
		}
		
		toDoMethod(index,"toReferRecord","");
	
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");		
	}
}


//�ٴγ���
function factoryMethod(){

	var index = checkedIndex();

	if(index >= 0){	
		var statusno=document.getElementsByName("status")[index].value;//���״̬
		var r1=document.getElementsByName("r1")[index].value
		var checkVersion=document.getElementsByName("checkVersion")[index].value;
		var factoryCheckResult=document.getElementsByName("factoryCheckResult")[index].value;//����״̬
		//alert(r2);
		if(checkVersion!="Y" || r1!="Y"){
				var gen="";
				if(checkVersion=="N"){
					gen="�ü�¼Ϊ��ʷ�汾�������ٴγ��죡";
				}
				if(r1!="Y"){
					gen="�ü�¼����δ�����򳧼첿��δ��ˣ������ٴγ���!";
				}
				if(factoryCheckResult=="�ϸ�")
			 	{
				 	gen="�ü�¼�����Ѻϸ�,�����ٴγ���!"; 
			 	}
				alert(gen); 
				return;
		 }else
		 {
			 if(factoryCheckResult=="�ϸ�")
			 {
				 alert("�ü�¼�����Ѻϸ�,�����ٴγ���!"); 
					return;
			 }
		 }
		
		var isClose=document.getElementsByName("isClose")[index].value;
		if(isClose=="Y"){
			alert("�ü�¼�ѹرճ��죬�����ٴγ��죡");
			return;
		}
		
		toDoMethod(index,"toPrepareFactoryRecord","");
	
	} else {
		alert("<bean:message key="javascript.role.alert1"/>");
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
		window.open('<html:rewrite page="/elevatorTransferCaseRegisterManageAction.do"/>?id='+document.getElementsByName("ids")[index].value+'&method=toPreparePrintRecord');
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");		
	}
}

//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/elevatorTransferCaseRegisterManageAction.do"/>?id='+document.getElementsByName("ids")[index].value+'&method='+method+params;
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