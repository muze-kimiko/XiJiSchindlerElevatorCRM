 <%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>

<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  
  AddToolBarItemEx("SearchBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  
  <logic:equal name="roleidshow" value="Y"> 
  	AddToolBarItemEx("RenewBtn","../../common/images/toolbar/add.gif","","",'�� ǩ',"65","1","renewalMethod2('ALL')");
    AddToolBarItemEx("RenewbfBtn","../../common/images/toolbar/borrow.gif","","",'������ǩ',"80","1","renewalMethod2('PART')");
    AddToolBarItemEx("ModifDateyBtn","../../common/images/toolbar/edit.gif","","",'ά���������',"105","1","modifyDateMethod()");
  </logic:equal>
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintcontract" value="Y"> 
    AddToolBarItemEx("SelectAddBtn","../../common/images/toolbar/add.gif","","",'ѡ���½�',"80","1","selectAddMethod()");
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'�� ��',"65","1","addMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modifyMethod()");
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/digital_confirm.gif","","",'�� ��',"65","1","referMethod()");
    AddToolBarItemEx("RenewBtn","../../common/images/toolbar/add.gif","","",'�� ǩ',"65","1","renewalMethod('ALL')");
    AddToolBarItemEx("ReferxdBtn","../../common/images/toolbar/add.gif","","",'�� ��',"65","1","assignTasksMethod()");
    AddToolBarItemEx("RenewbfBtn","../../common/images/toolbar/borrow.gif","","",'������ǩ',"80","1","renewalMethod2('PART')");
    //AddToolBarItemEx("SurrenderBtn","../../common/images/toolbar/edit.gif","","",'�� ��',"65","1","surrenderMethod()");
    AddToolBarItemEx("SurrenderBtn","../../common/images/toolbar/edit.gif","","",'���ڴ���',"85","1","isExpire()");
    AddToolBarItemEx("ModifyDateBtn","../../common/images/toolbar/edit.gif","","",'ά���������',"105","1","modifyDateMethod()");
    AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()");
  </logic:equal>
  
  //AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//ά���������
function modifyDateMethod(){
	toDoMethod(getIndex(),"toDisplayDateRecord","","<bean:message key="javascript.role.alert1"/>");
}

//����
function renewalMethod2(valstr){	
	var index = getIndex();	

	var auditStatus=getVal("auditStatus",index);//���״̬	
	if(auditStatus && auditStatus != "Y"){
		alert("��¼δ��ˣ�������ǩ!"); 
		return;
	}
	var contractStatus=getVal("contractStatus",index);//��ͬ״̬	
	if(contractStatus && contractStatus == "��ʷ"){
		alert("������ǩ��ʷ��¼!"); 
		return;
	}	
	if(contractStatus && contractStatus == "�˱�"){
		alert("������ǩ�˱���¼!"); 
		return;
	}
	var taskSubFlag=getVal("taskSubFlag",index);
	var r2 = getVal("r2",index);//ά����ͬ��
	if(taskSubFlag != "Y" && r2 != "Y"){
		alert("�ü�¼δ�´��δί�У����ܽ�����ǩ��");
		return;
	}
	if(index>=0){
		if(confirm("�Ƿ��� �۸����� �ĸı䣿")){
			toDoMethod(index,"toPrepareRenewalRecord2","&xqtype="+valstr,"<bean:message key="javascript.role.alert.jilu"/>");
		}else{
			alert("��ȷ��������۸�ȱ���������ٽ��б������룬��ֱ��ǩ���ͬ������ǩ�����������ͬԭ���Ļؼ��ɡ�");
		}
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
	
	
	//toDoMethod(index,"toPrepareRenewalRecord","","<bean:message key="javascript.role.alert.jilu"/>");
}

//��ѯ
function searchMethod(){
	//serveTableForm.genReport.value = "";
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
}

//�鿴
function viewMethod(){
	toDoMethod(getIndex(),"toDisplayRecord","","<bean:message key="javascript.role.alert2"/>");
}

//ѡ���½�
function selectAddMethod(){
	window.location = '<html:rewrite page="/maintContractSearchAction.do"/>?method=toSearchNext';
}

//�½�
function addMethod(){
	window.location = '<html:rewrite page="/maintContractAction.do"/>?method=toPrepareAddRecord';
}

//�޸�
function modifyMethod(){
	var index = getIndex();	
	
	var contractStatus=getVal("contractStatus",index);//��ͬ״̬	
	if(contractStatus && (contractStatus == "��ʷ" || contractStatus == "�˱�")){
		alert("�����޸ĺ�ͬ״̬Ϊ��ʷ���˱��ļ�¼!"); 
		return;
	}
	var submitType=getVal("submitType",index);//�ύ״̬		
	if(submitType && submitType == "Y"){
		alert("�ü�¼���ύ,�����޸�!"); 
		return;
	}
	
	toDoMethod(index,"toPrepareUpdateRecord","","<bean:message key="javascript.role.alert1"/>");
}

//ɾ��
function deleteMethod(){
	var index = getIndex();
	
	//var contractStatus=getVal("contractStatus",index);//��ͬ״̬	
	//if(contractStatus && contractStatus != "��ǩ"){
	//	alert("����ɾ����ͬ״̬Ϊ����ǩ�ļ�¼!"); 
	//	return;
	//}
	var submitType=getVal("submitType",index);//�ύ״̬	
	if(submitType && submitType == "Y"){
		alert("�ü�¼���ύ,����ɾ��!"); 
		return;
	}
	var maintContractNo=getVal("maintContractNo",index);//�ύ״̬	
	if(confirm("�Ƿ�ɾ����ͬ�ţ�"+maintContractNo+"?")){
		toDoMethod(index,"toDeleteRecord","","<bean:message key="javascript.role.alert3"/>");
	}
}

// �ύ
function referMethod(){
	var index = getIndex();	

	var contractStatus=getVal("contractStatus",index);//��ͬ״̬	
	if(contractStatus && (contractStatus == "��ʷ" || contractStatus == "�˱�")){
		alert("�����ύ��ͬ״̬Ϊ��ʷ���˱��ļ�¼!"); 
		return;
	}
	var submitType=getVal("submitType",index);//�ύ״̬	
	if(submitType && submitType == "Y"){
		alert("�����ظ��ύ��¼!"); 
		return;
	}
	
	toDoMethod(index,"toReferRecord","","<bean:message key="javascript.role.alert.jilu"/>");
}

//����
function renewalMethod(valstr){	
	var index = getIndex();	

	var auditStatus=getVal("auditStatus",index);//���״̬	
	if(auditStatus && auditStatus != "Y"){
		alert("��¼δ��ˣ�������ǩ!"); 
		return;
	}
	var contractStatus=getVal("contractStatus",index);//��ͬ״̬	
	if(contractStatus && contractStatus == "��ʷ"){
		alert("������ǩ��ʷ��¼!"); 
		return;
	}	
	if(contractStatus && contractStatus == "�˱�"){
		alert("������ǩ�˱���¼!"); 
		return;
	}
	var taskSubFlag=getVal("taskSubFlag",index);
	var r2 = getVal("r2",index);
	if(taskSubFlag != "Y" && r2 != "Y"){
		alert("�ü�¼δ�´��δί�У����ܽ�����ǩ��");
		return;
	}
	if(index>=0){
		if(confirm("�Ƿ��� �۸����� �ĸı䣿")){
			toDoMethod(index,"toPrepareRenewalRecord2","&xqtype="+valstr,"<bean:message key="javascript.role.alert.jilu"/>");
		}else{
			toDoMethod(index,"toPrepareRenewalRecord","","<bean:message key="javascript.role.alert.jilu"/>");
		}
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
	
}

/**
//��confirm�а�ť���ı��滻Ϊ���ǡ�/���񡱣�ֻ������IE
function window.confirm(str)
{
	execScript("n = (msgbox('"+str+"',vbYesNo, '��ʾ')=vbYes)", "vbscript");
	return(n);
}
*/


//�´�
function assignTasksMethod(){	
	var index = getIndex();	
	
	var r2 = getVal("r2",index);//ά����ͬ��
	if(r2 == "Y"){
		alert("�ú�ͬ�ѽ���ί�к�ͬ�������´�!"); 
		return;
	}	
	var taskSubFlag=getVal("taskSubFlag",index);//�����´��־
	if(taskSubFlag && taskSubFlag == "Y"){
		alert("��¼�Ѿ��´�����ظ��´�!"); 
		return;
	}
	var auditStatus=getVal("auditStatus",index);//���״̬	
	if(auditStatus && auditStatus != "Y"){
		alert("��¼δ��ˣ������´�!"); 
		return;
	}
	var contractStatus=getVal("contractStatus",index);//��ͬ״̬	
	if(contractStatus && (contractStatus == "��ʷ" || contractStatus == "�˱�")){
		alert("�����´��ͬ״̬Ϊ��ʷ���˱��ļ�¼!"); 
		return;
	}
	
	toDoMethod(index,"toPrepareAssignTasks","","<bean:message key="javascript.role.alert.jilu"/>");
}

function isExpire(){
	 var index = getIndex();
	if(index>=0){
		var edates =getVal("contractEdate",index).replace(/-/g,"");
		var contractStatus= getVal("contractStatus",index);
		var warningStatus=getVal("warningStatus",index);
		var todayDate=document.getElementById("hiddatestr").value.replace(/-/g,"");
		if(Number(todayDate)>=Number(edates) && contractStatus != "�˱�" && contractStatus != "��ʷ" && warningStatus==""){
			var taskSubFlag=getVal("taskSubFlag",index);
			if(taskSubFlag=="Y"){
				toDoMethod(index,"toPrepareExpireRecord","","<bean:message key="javascript.role.alert.jilu"/>");
			}else{
				alert("�ü�¼δ�´���ܽ��е��ڴ��������");
			}
		}else if(contractStatus == "�˱�" || contractStatus == "��ʷ"){
			alert("�ü�¼Ϊ�˱�����ʷ��ͬ�����ܽ��е��ڴ��������");
		}else{
			if(warningStatus=="Y"){
				alert("�ü�¼�ѽ��ݷüƻ������ܽ��е��ڴ��������");
			}else if(warningStatus=="S"){
				alert("�ü�¼�Ѱݷ÷��������ܽ��е��ڴ��������");
			}else{
				alert("��ͬδ���ڲ��ܽ��е��ڴ��������");
			}
		}
	}
	
	
}

//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(params==null){
		params='';
	}
	if(index >= 0){	
		window.location = '<html:rewrite page="/maintContractAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
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

/* //����Excel
function excelMethod(){
  serveTableForm.genReport.value="Y";
  serveTableForm.target = "_blank";
  document.serveTableForm.submit();
} */

//��ͬ���ڵļ�¼�ú�ɫ������ʾ
window.onload=function(){
	var edates = document.getElementsByName("contractEdate");
	var contractStatus= document.getElementsByName("contractStatus");
	var todayDate=document.getElementById("hiddatestr").value.replace(/-/g,"");
	var warningStatus=document.getElementsByName("warningStatus");
	var taskSubFlag=document.getElementsByName("taskSubFlag");

	/**
	var date = new Date();
	var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	mm = mm > 10 ? mm : "0"+mm
	var dd = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var todayDate = "" + yyyy + mm + dd
	*/

	for ( var i = 0; i < edates.length; i++) {
		var edate = edates[i].value.replace(/-/g,"");
		
		if(Number(todayDate)>=Number(edate) && contractStatus[i].value != "�˱�" 
				&& contractStatus[i].value != "��ʷ" ){
			row = edates[i].parentNode.parentNode;			
			row.style.color = "#FF0000"
		}				
	}
}

//AJAX��̬��ʾά��վ
var req;
function Evenmore(obj,listname){	
	var comid=obj.value;
	 var selectfreeid = document.getElementById(listname);
	if(comid!="" && comid!="%"){
		
		 if(window.XMLHttpRequest) {
			 req = new XMLHttpRequest();
		 }else if(window.ActiveXObject) {
			 req = new ActiveXObject("Microsoft.XMLHTTP");
		 }  //����response
		 
		 var url='<html:rewrite page="/maintContractAction.do"/>?method=toStorageIDList&comid='+comid;//��ת·��
		 req.open("post",url,true);//post �첽
		 req.onreadystatechange=function getnextlist(){
			
				if(req.readyState==4 && req.status==200){
				 var xmlDOM=req.responseXML;
				 var rows=xmlDOM.getElementsByTagName('rows');
				 if(rows!=null){
					    selectfreeid.options.length=0;
					    selectfreeid.add(new Option("ȫ��","%"));	

				 		for(var i=0;i<rows.length;i++){
							var colNodes = rows[i].childNodes;
							if(colNodes != null){
								var colLen = colNodes.length;
								for(var j=0;j<colLen;j++){
									var freeid = colNodes[j].getAttribute("name");
									var freename = colNodes[j].getAttribute("value");
									selectfreeid.add(new Option(freeid,freename));
					            }
				             }
				 		}
				 	}
				
				}
		 };//�ص�����
		 req.send(null);//������
	}else{		
		selectfreeid.options.length=0;
    	selectfreeid.add(new Option("ȫ��","%"));
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