<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
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
  <logic:match name="taskname" value="�ر�����">
  		AddToolBarItemEx("printBtn","../../common/images/toolbar/print.gif","","",'��ӡ֪ͨ��',"100","1","printMethod()");
  </logic:match>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//ȥ���ո�
String.prototype.trim = function(){ return this.replace(/(^\s*)|(\s*$)/g,""); }

//��ӡ
function printMethod(){
	var jnlNo=document.getElementById("jnlNo").value;
	window.open('<html:rewrite page="/paymentManageAction.do"/>?id='+jnlNo+'&method=toPrintRecord');
}

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
	var flags=getSelectValue();
	if(flags!="" && flags!="1"){
		alert("��Ǹ,��ѡ����'��ͨ��',�����������������д��������!");
		return;
	}
	if(checkColumnInput(contractPaymentManageJbpmForm) && checkInput()){
		document.getElementById("SaveBtn").disabled=true;
		document.contractPaymentManageJbpmForm.submit();
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
 	if(val!="" && (val=="�ر�" || val=="ͨ��" || val.indexOf("�ύ")>-1)){
 	    str="1";
 	}else{
 		if(document.getElementById("approverem").value.trim()!="" || document.getElementById("approverem").value.trim().length>0){
 			str="1";
 		}
 	}
 	return str;
	
}


function checkInput(){
	var alerg="";
	<logic:match name="taskname" value="���лطý�����">
		var hfAuditNum=document.getElementsByName("hfAuditNum")[0];
		var hfAuditNum2=document.getElementsByName("hfAuditNum2")[0];
		if(hfAuditNum.value==null || isNaN(hfAuditNum.value))
			alerg+="������������ĸ����������ֻ���������֣�\n";
		if(hfAuditNum2.value==null || isNaN(hfAuditNum2.value))
			alerg+="��Ǽ�����������ĸ����������ֻ���������֣�\n";
	</logic:match>
	
	<logic:match name="taskname" value="���߹��������">
		var rxAuditNum=document.getElementsByName("rxAuditNum")[0];
		var rxAuditNum2=document.getElementsByName("rxAuditNum2")[0];
		if(rxAuditNum.value==null || isNaN(rxAuditNum.value))
			alerg+="�������������Ͷ�ߴ���ֻ���������֣�\n";
		if(rxAuditNum2.value==null || isNaN(rxAuditNum2.value))
			alerg+="��Ǽ������������Ͷ�ߴ���ֻ���������֣�\n";
	</logic:match>
	
	<logic:match name="taskname" value="�ֲ������">
		var debitMoney=document.getElementsByName("debitMoney")[0];
		if(debitMoney.value==null || isNaN(debitMoney.value))
			alerg+="�ۿ���ֻ���������֣�\n";
	</logic:match>
	
	if(alerg!=""){
		alert(alerg);
		return false;
	}else{
		return true;
	}
}

//������Ƿ�Ϊ����,���������븺�źͿ���������
function f_check_number3(){
 	if((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==46 ){
 	}else{
		event.keyCode=0;
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