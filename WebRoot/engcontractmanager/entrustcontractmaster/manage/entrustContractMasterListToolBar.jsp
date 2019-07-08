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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nentrustcontractmaster" value="Y"> 
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'�� ��',"65","1","addFromQuoteMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modifyMethod()");
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/digital_confirm.gif","","",'�� ��',"65","1","referMethod()");
    AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()");
    AddToolBarItemEx("SurrenderBtn","../../common/images/toolbar/edit.gif","","",'�� ��',"65","1","surrenderMethod()");
    AddToolBarItemEx("endBtn","../../common/images/toolbar/edit.gif","","",'��ͬ��ֹ',"85","1","endMethod()");
  </logic:equal>
  
  AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
  serveTableForm.genReport.value = "";
  serveTableForm.target = "_self";
  document.serveTableForm.submit();
}
//����Excel
function excelMethod(){
  serveTableForm.genReport.value="Y";
  serveTableForm.target = "_blank";
  document.serveTableForm.submit();
}

//�鿴
function viewMethod(){
  toDoMethod(checkedIndex(),"toDisplayRecord","&returnMethod=toSearchRecord","<bean:message key="javascript.role.alert2"/>");
}

//��ά������ѡȡ¼��
function addFromQuoteMethod(){
  window.location = '<html:rewrite page="/entrustContractMasterSearchAction.do"/>?method=toSearchNext';
}

//�޸�
function modifyMethod(){

	var index = checkedIndex();

	if(index >= 0){	
		var submitType=document.getElementsByName("submitType")[index].value;//�ύ״̬
		
		if(submitType == "Y"){
			alert("�ü�¼���ύ,�����޸�!"); 
			return;
		}
		
		toDoMethod(index,"toPrepareUpdateRecord","");
	
	} else {
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}

//ɾ��
function deleteMethod(){
	var index = checkedIndex();
	
	if(index >= 0){	
		var submitType=document.getElementsByName("submitType")[index].value;//�ύ״̬
		
		if(submitType == "Y"){
			alert("�ü�¼���ύ,����ɾ��!"); 
			return;
		}
		if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids[i].value)){
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
		
		if(submitType == "Y"){
			alert("�ü�¼���ύ,�����޸�!"); 
			return;
		}
		
		toDoMethod(index,"toReferRecord","");
	
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");		
	}
}

//�˱�
function surrenderMethod(){
	var index = checkedIndex();
	
	if(index >= 0){	
		var auditStatus=document.getElementsByName("auditStatus")[index].value;//�ύ״̬
		if(auditStatus != "Y"){
			alert("�ü�¼δ���,���ܽ����˱�����!"); 
			return;
		}
		var r1=document.getElementsByName("r1")[index].value;
		if(r1 != "ZB"){
			alert("��ѡ���ͬ״̬Ϊ '�ڱ�' �ļ�¼�����˱�����!"); 
			return;
		}
		
		var entrustContractNo=document.getElementsByName("entrustContractNo")[index].value;
		if(confirm("ί�к�ͬ�ţ�"+entrustContractNo+" ȷ���˱���")){
			toDoMethod(index,"toSurrenderRecord","");
		}
				
	} else {
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}
//��ͬ��ֹ
function endMethod(){
	var index = checkedIndex();
	
	if(index >= 0){	
		var auditStatus=document.getElementsByName("auditStatus")[index].value;//�ύ״̬
		if(auditStatus != "Y"){
			alert("�ü�¼δ���,���ܽ��к�ͬ��ֹ����!"); 
			return;
		}
		
		var r1=document.getElementsByName("r1")[index].value;
		if(r1 != "ZB"){
			alert("��ѡ���ͬ״̬Ϊ '�ڱ�' �ļ�¼���к�ͬ��ֹ����!"); 
			return;
		}
		
		var entrustContractNo=document.getElementsByName("entrustContractNo")[index].value;
		if(confirm("ί�к�ͬ�ţ�"+entrustContractNo+" ȷ����ֹ��ͬ��")){
			toDoMethod(index,"toPrepareEndRecord","");
		}
				
	} else {
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}
//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/entrustContractMasterAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
	}else if(alertMsg && alertMsg != ""){
		alert(alertMsg);
	}
}

function checkedIndex(){
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

//�б�����ͬ���ڵĺ�ɫ������ʾ
window.onload=function(){
	var edates = document.getElementsByName("contractEdate");
	var date = new Date();
	var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	mm = mm > 10 ? mm : "0"+mm
	var dd = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var todayDate = "" + yyyy + mm + dd

	for ( var i = 0; i < edates.length; i++) {
		var edate = edates[i].value.replace(/-/g,"");
		if(Number(todayDate)>=Number(edate)){
			row = edates[i].parentNode.parentNode;			
			row.style.color = "#FF0000"
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