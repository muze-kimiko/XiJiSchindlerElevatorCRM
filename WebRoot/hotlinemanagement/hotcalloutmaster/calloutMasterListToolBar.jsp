<%@ page contentType="text/html;charset=GBK"%>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig"%>
<!--
	�ͻ�������ҳ������
-->
<script language="javascript">

//����ToolBar
function CreateToolBar() {
	SetToolBarHandle(true);
	SetToolBarHeight(20);

	AddToolBarItemEx("SearchBtn", "../../common/images/toolbar/search.gif", "","", '<bean:message key="toolbar.search"/>', "65", "0","searchMethod()");
	AddToolBarItemEx("ViewBtn", "../../common/images/toolbar/view.gif", "", "",'<bean:message key="toolbar.read"/>', "65", "1", "viewMethod()");
	 <%-- �Ƿ��п�д��Ȩ��--%>
	  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nhotphone" value="Y"> 
	AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'<bean:message key="toolbar.add"/>',"65","1","addMethod()");
	AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modiyMethod()");
	AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()");
    AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/xls.gif", "", "",'<bean:message key="toolbar.xls"/>', "90", "1", "excelMethod()");
    AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/edit.gif", "", "",'ͣ�ݻط�', "90", "1", "stopHFMethod()");
    </logic:equal>
    AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/print.gif", "", "",'��ӡ��¼��', "90", "1", "printMethod()");
	window.document.getElementById("toolbar").innerHTML = GenToolBar("TabToolBar_Manage", "TextToolBar_Black", "Style_Over",
			"Style_Out", "Style_Down", "Style_Check");
}

//��ѯ
function searchMethod() {
	serveTableForm.genReport.value = "";
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
}

//�Ǽ�ͣ�ݻطñ�ע
function stopHFMethod(){
	var index = checkedIndex();
	if(index >= 0){	
		var hisStop=document.getElementsByName("hisStop")[index].value;//ͣ��״̬
		if(hisStop=="ͣ��"){
			window.location = '<html:rewrite page="/hotphoneAction.do"/>?method=toPrepareStophfRecord&id='+document.getElementsByName("ids")[index].value;
			return;
		}else{
			alert("��ѡ��ͣ�ݵļ��޵�������ͣ�ݻطã�"); 
			return;
		}
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");		
	}
}

//�鿴
function viewMethod() {
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				window.location = '<html:rewrite page="/hotphoneAction.do"/>?method=toDisplayRecord&id='+document.serveTableForm.ids[i].value;
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert2"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/hotphoneAction.do"/>?method=toDisplayRecord&id='+document.serveTableForm.ids.value;
	}
	else
	{
		alert("<bean:message key="javascript.role.alert2"/>");
	}
}
}

//����
function addMethod(){
	window.location = '<html:rewrite page="/hotphoneAction.do"/>?method=toPrepareAddRecord';
}
//�޸�
function modiyMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{		
				if(document.serveTableForm.submitType[i].value == "δ�ύ")
				{	
				window.location = '<html:rewrite page="/hotphoneAction.do"/>?method=toPrepareUpdateRecord&id='+document.serveTableForm.ids[i].value;
				return;		
				}else{
					alert("�˼�¼���ύ����ѡ��δ�ύ�ļ�¼��");
					return;
				}
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert1"/>");
		}
	}else if(document.serveTableForm.ids.checked == true){
	
		if(document.serveTableForm.submitType.value == "δ�ύ")
		{
		window.location = '<html:rewrite page="/hotphoneAction.do"/>?method=toPrepareUpdateRecord&id='+document.serveTableForm.ids.value;
		}else{
			alert("�˼�¼���ύ����ѡ��δ�ύ�ļ�¼��");
			return;
		}
		}
	else {
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}

}

//��ӡ
function printMethod(){
	var index = checkedIndex();
	if(index >= 0){	
		var handleStatus=document.getElementsByName("handleStatus")[index].value;//�ύ״̬
		if(handleStatus == "5"||handleStatus == "6"||handleStatus=="7"){
		window.open('<html:rewrite page="/hotphoneAction.do"/>?id='+document.getElementsByName("ids")[index].value+'&method=toPreparePrintRecord');

		}else{
			alert("����״̬Ϊ��¼�뱨������,�ſ��Դ�ӡ"); 
			return;
		}
		
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");		
	}
}

//ɾ��
function deleteMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				if(document.serveTableForm.submitType[i].value == "δ�ύ")
				{
				if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids[i].value)){
					window.location = '<html:rewrite page="/hotphoneAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDeleteRecord';
					
				}
				return;
				}else{
					alert("�˼�¼���ύ����ѡ��δ�ύ�ļ�¼��");
					return;
				}
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert3"/>");
		}
	}
	else if(document.serveTableForm.ids.checked == true)
	{
		if(document.serveTableForm.submitType.value == "δ�ύ")
		{
		if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids.value)){
			window.location = '<html:rewrite page="/hotphoneAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDeleteRecord';
		}
		return;
		}else{
			alert("�˼�¼���ύ����ѡ��δ�ύ�ļ�¼��");
			return;
		}
	}
	else
	{
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}
}

//����Excel
function excelMethod(){
	serveTableForm.genReport.value="Y";
	serveTableForm.target = "_blank";
	document.serveTableForm.submit();
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

<form name="CalloutMasterjxForm" method="POST" action="">
	<input type="hidden" name="" value="" />
</form>

<tr>
	<td width="100%">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td height="22" class="table_outline3" valign="bottom" width="100%">
					<div id="toolbar" align="center">

						<script language="javascript">
<!--
                CreateToolBar();
              //-->
            </script>
					</div>
				</td>
			</tr>
		</table>
	</td>
</tr>
</table>