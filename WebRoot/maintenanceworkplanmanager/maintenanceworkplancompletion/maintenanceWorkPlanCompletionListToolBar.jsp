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
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenanceworkplancompletion" value="Y">   
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'�������',"80","1","modiyMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'����¼��',"80","1","modiyMethod2()");
    AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/print.gif", "", "",'��ӡ������', "90", "1", "printMethod()");
    AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/delete.gif", "", "",'�������״̬', "100", "1", "deleteMethod()");
    AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/delete.gif", "", "",'ɾ�������ƻ�', "100", "1", "deleteMethod2()");
    AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/delete.gif", "", "",'����ɾ�������ƻ�', "140", "1", "deleteMethod3()");
  </logic:equal>
  
  AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����ɾ��
function deleteMethod3(){
  window.location = '<html:rewrite page="/maintenanceWorkPlanCompletionAction.do"/>?method=toPrepareDeleteRecord';
}
//ɾ��
function deleteMethod2(){
	var index =checkedIndex();
	if(index >= 0){	
		var hsingleno=getVal('hsingleno',index);
		if(hsingleno!=""){
			alert("��ѡ�� �������� Ϊ�յļ�¼������ɾ����"); 
		}else{
			if(confirm("�Ƿ�ɾ�������ƻ���")){
	    		toDoMethod(index,"toDeleteRecord2","","<bean:message key="javascript.role.alert3"/>");
			}
		}
	}else {
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}
//ɾ��
function deleteMethod(){
	var index =checkedIndex();
	if(index >= 0){	
		if(confirm("�Ƿ��������״̬��")){
    		toDoMethod(index,"toDeleteRecord","","<bean:message key="javascript.role.alert3"/>");
		}
	}else {
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}
//��ѯ
function searchMethod(){
	serveTableForm.genReport.value = "";
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
}
//�鿴
function modifyMethod(){
	var index = getIndex();	
	toDoMethod(index,"toDisplayRecord","","<bean:message key="javascript.role.alert1"/>");
}

//���
function modiyMethod(){
	var index = getIndex();	
	var hmaintEndTime=getVal('hmaintEndTime',index);
	if(hmaintEndTime==""){
		alert("����δ�깤�����ܽ��б�����ˡ�");
		return;
	}
	
	var hauditType=getVal('hauditType',index);
	if(hauditType=="�����"){
		alert("��������ˣ����ܽ��б�����ˡ�");
		return;
	}
	
	toDoMethod(index,"toPrepareUpdateRecord","","<bean:message key="javascript.role.alert1"/>");

}
//���
function modiyMethod2(){
	var index = getIndex();	

	var hauditType=getVal('hauditType',index);
	if(hauditType=="δ���"){
		alert("����δ��ˣ����ܽ��е���¼�롣");
		return;
	}
	
	toDoMethod(index,"toPrepareUpdate2Record","","<bean:message key="javascript.role.alert1"/>");

}

//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/maintenanceWorkPlanCompletionAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
	}else if(alertMsg && alertMsg != ""){
		alert(alertMsg);
	}
}

//��ȡѡ�м�¼�±�
function getIndex(){
	if(serveTableForm.ids){
		var ids = serveTableForm.ids;
		if(ids.length == null){
			return 0;
		}
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked == true){
				return i;
			}
		}		
	}
	return -1;	
}

//����name��ѡ���±��ȡԪ�ص�ֵ
function getVal(name,index){
	var obj = eval("serveTableForm."+name);
	if(obj && obj.length){
		obj = obj[index];
	}
	return obj ? obj.value : null;
}

//��ӡ
function printMethod(){
	var index = checkedIndex();
	if(index >= 0){	
	 	var hmaintEndTime=document.getElementsByName("hmaintEndTime")[index].value;//��������ʱ��
		if(hmaintEndTime != ""){ 
			window.open('<html:rewrite page="/maintenanceWorkPlanCompletionAction.do"/>?id='+document.getElementsByName("ids")[index].value+'&method=toPreparePrintRecord');
		}else{
			alert("����δ�깤,���ܴ�ӡ��������"); 
			return;
		}
		
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");		
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

//����Excel
function excelMethod(){
	serveTableForm.genReport.value="Y";
	serveTableForm.target = "_blank";
	document.serveTableForm.submit();
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