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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ncontractinvoicemanage" value="Y"> 
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'�� ��',"65","1","addMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modifyMethod()");
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/digital_confirm.gif","","",'�� ��',"65","1","referMethod()");
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/edit.gif","","",'��Ʊ�Ż���',"100","1","backfillMethod()");
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/edit.gif","","",'��Ʊ',"65","1","refundMethod()");
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/add.gif","","",'��Ʊ',"65","1","ticketMethod()");
    AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod('N')");
    
    <logic:equal name="showroleid" value="A01"> 
    	AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'����Աɾ��',"95","1","deleteMethod('Y')");
    </logic:equal>
  </logic:equal>
  
  AddToolBarItemEx("printBtn","../../common/images/toolbar/print.gif","","",'��ӡ֪ͨ��',"100","1","printMethod()");
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
	toDoMethod(getIndex(),"toDisplayRecord","&returnMethod=toSearchRecord","<bean:message key="javascript.role.alert2"/>");
}

//�½�
function addMethod(){
	window.location = '<html:rewrite page="/contractInvoiceManageSearchAction.do"/>?method=toSearchNext';
}

//�޸�
function modifyMethod(){
	var index = getIndex();	
	var submitType=getVal("submitType",index);//�ύ״̬		
	if(submitType && submitType == "Y"){
		alert("�ü�¼���ύ,�����޸�!"); 
		return;
	}
	
	toDoMethod(index,"toPrepareUpdateRecord","","<bean:message key="javascript.role.alert1"/>");
}

//ɾ��
function deleteMethod(valstr){
	
	var index =getIndex();
	if(index >= 0){	
		if(valstr=='N'){
			var submitType=getVal("submitType",index);//�ύ״̬	
			if(submitType && submitType == "Y"){
				alert("�ü�¼���ύ,����ɾ��!"); 
				return;
			}
		}

		if(confirm("<bean:message key="javascript.role.deletecomfirm"/>")){
    		toDoMethod(index,"toDeleteRecord","","<bean:message key="javascript.role.alert3"/>");
		}
	}else {
		alert("<bean:message key="javascript.role.alert3"/>");
	}
	
}

// �ύ
function referMethod(){
	var index = getIndex();	

	var submitType=getVal("submitType",index);//�ύ״̬	
	if(submitType && submitType == "Y"){
		alert("�����ظ��ύ��¼!"); 
		return;
	}
	
	toDoMethod(index,"toReferRecord","","<bean:message key="javascript.role.alert.jilu"/>");
}

function backfillMethod(){
	var index = getIndex();	

	//var invoiceNo=getVal("invoiceNo",index);
	var istbp=getVal("istbp",index);
	if(istbp && istbp != ""){
		var msg="";
		if(istbp=="TP")
			msg+="�ü�¼Ϊ����Ʊ��¼�������ٴν��з�Ʊ�Ż��������\n";
		if(istbp=="BP")
			msg+="�ü�¼Ϊ�Ѳ�Ʊ��¼�������ٴν��з�Ʊ�Ż��������\n";
		if(istbp=="CX")
			msg+="�ü�¼Ϊ��Ʊ������¼�����ܽ��з�Ʊ�Ż��������\n";
		alert(msg); 
		return;
	}
	
	toDoMethod(index,"toPrepareBackfillRecord","","<bean:message key="javascript.role.alert.jilu"/>");
}

function refundMethod(){
	var index = getIndex();	

	var invoiceNo=getVal("invoiceNo",index);//��Ʊ��
	var invoiceMoney=parseFloat(getVal("invoiceMoney",index));//��Ʊ���
	var auditStatus=getVal("auditStatus",index);
	var istbp=getVal("istbp",index);
	if(auditStatus!="Y" || invoiceNo == "" || invoiceMoney<0 || istbp!=""){
		var msg="";
		if(invoiceNo==""){
			msg+="��Ʊ��Ϊ�գ����ܽ�����Ʊ����!\n";
		}
		if(invoiceMoney<0){
			msg+="�ü�¼Ϊ��Ʊ������¼�����ܽ�����Ʊ������\n";
		}
		if(auditStatus!="Y"){
			msg="�ü�¼δ���ͨ�������ܽ�����Ʊ������\n";
		}
		if(istbp=="TP"){
			msg="�ü�¼Ϊ����Ʊ��¼�������ٴν�����Ʊ������\n";
		}
		if(istbp=="BP"){
			msg="�ü�¼Ϊ�Ѳ�Ʊ��¼�������ٴν�����Ʊ������\n";
		}
		alert(msg); 
		return;
	}
	
	toDoMethod(index,"toRefundRecord","","<bean:message key="javascript.role.alert.jilu"/>");
}

function ticketMethod(){
	var index = getIndex();	

	var invoiceNo=getVal("invoiceNo",index);//��Ʊ��
	var invoiceMoney=parseFloat(getVal("invoiceMoney",index));//��Ʊ���
	var auditStatus=getVal("auditStatus",index);
	var istbp=getVal("istbp",index);
	if(auditStatus!="Y" || invoiceNo == "" || invoiceMoney<0 || istbp!=""){
		var msg="";
		if(invoiceNo==""){
			msg="��Ʊ��Ϊ�գ����ܽ��в�Ʊ����!\n";
		}
		if(invoiceMoney<0){
			msg="�ü�¼Ϊ��Ʊ������¼�����ܽ��в�Ʊ������\n";
		}
		if(auditStatus!="Y"){
			msg="�ü�¼δ���ͨ�������ܽ��в�Ʊ������\n";
		}
		if(istbp=="TP"){
			msg="�ü�¼Ϊ����Ʊ��¼�������ٴν��в�Ʊ������\n";
		}
		if(istbp=="BP"){
			msg="�ü�¼Ϊ�Ѳ�Ʊ��¼�������ٴν��в�Ʊ������\n";
		}
		alert(msg); 
		return;
	}
	
	toDoMethod(index,"toTicketRecord","","<bean:message key="javascript.role.alert.jilu"/>");
}
//��ӡ
function printMethod(){
	if(serveTableForm.ids)
	{
		var l = document.serveTableForm.ids.length;
		var jnlNo=document.getElementsByName("jnlNo");
		var auditStatus=document.getElementsByName("auditStatus");
		var invoiceMoney=document.getElementsByName("invoiceMoney");
		var istbp=document.getElementsByName("istbp");
		var msg="";
		if(l)
		{
			for(i=0;i<l;i++)
			{
				if(document.serveTableForm.ids[i].checked == true)
				{
					if(auditStatus[i].value=="Y" && parseFloat(invoiceMoney[i].value)>0 && istbp[i].value==""){
						window.open('<html:rewrite page="/contractInvoiceManageAction.do"/>?id='+jnlNo[i].value+'&method=toPrintRecord');
						return;
				}else{
					if(istbp[i].value!=""){
						msg="�ü�¼Ϊ��Ʊ��Ʊ��¼�����ܴ�ӡ��Ʊ����֪ͨ����";
					}
					if(parseFloat(invoiceMoney[i].value)<0){
						msg="�ü�¼Ϊ��Ʊ������¼�����ܴ�ӡ��Ʊ����֪ͨ����";
					}
					if(auditStatus[i].value!="Y"){
						msg="�ü�¼δ���ͨ�������ܴ�ӡ��Ʊ����֪ͨ����";
					}
					alert(msg);
					return;
				}
				}
				
				
			}
			if(l >0)
			{
				alert("��ѡ��һ��Ҫ��ӡ�ļ�¼��");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			var jnlNo=document.getElementsByName("jnlNo");
			var auditStatus=document.getElementsByName("auditStatus");
			var invoiceMoney=document.getElementsByName("invoiceMoney");
			var istbp=document.getElementsByName("istbp");
			if(auditStatus[0].value=="Y" && parseFloat(invoiceMoney[0].value)>0 && istbp[0].value==""){
				window.open('<html:rewrite page="/contractInvoiceManageAction.do"/>?id='+jnlNo[0].value+'&method=toPrintRecord');
			}else{
				if(istbp[0].value!=""){
					msg="�ü�¼Ϊ��Ʊ��Ʊ��¼�����ܴ�ӡ��Ʊ����֪ͨ����";
				}
				if(parseFloat(invoiceMoney[0].value)<0){
					msg="�ü�¼Ϊ��Ʊ������¼�����ܴ�ӡ��Ʊ����֪ͨ����";
				}
				if(auditStatus[0].value!="Y"){
					msg="�ü�¼δ���ͨ�������ܴ�ӡ��Ʊ����֪ͨ����";
				}
				alert(msg);
			}
			
			
		}
		else
		{
			alert("��ѡ��һ��Ҫ��ӡ�ļ�¼��");
		}
	}
}

//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/contractInvoiceManageAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
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
	var edates = document.getElementsByName("preDate");
	var date = new Date();
	var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	mm = mm > 10 ? mm : "0"+mm
	var dd = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var todayDate = "" + yyyy + mm + dd
	
	for ( var i = 0; i < edates.length; i++) {
		var edate = edates[i].value.replace(/-/g,"");
		if(Number(todayDate)>Number(edate)){
			var row = edates[i].parentNode.parentNode.parentNode;			
			row.style.color = "#FF0000";
			if(i>=1){
				var table=row.parentNode;
				table.removeChild(row);
				table.insertBefore(row,table.childNodes[1]);
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