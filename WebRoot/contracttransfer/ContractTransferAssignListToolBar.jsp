 <%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<!--
	����������Ϣ������
-->
<script language="javascript"> 
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  
<%--  ����������Ϣ������ģ��EngUnitInfo�Ƿ��п�д��Ȩ��,��property	--%>
<logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nContractTransferAssign" value="Y"><!-- -->
  AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'<bean:message key="toolbar.add"/>',"65","1","addMethod()");
  AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modiyMethod()");
  AddToolBarItemEx("SubmitBtn","../../common/images/toolbar/save.gif","","",'�ύ',"65","1","submitMethod()");
  AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod('N')");

  <logic:equal name="showroleid" value="A01"> 
	AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'����Աɾ��',"95","1","deleteMethod('Y')");
</logic:equal>
  </logic:equal>
  
  AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
	serveTableForm.genReport.value="";
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
}

//�鿴
function viewMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDisplayRecord';
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert2"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDisplayRecord';
	}
	else
	{
		alert("<bean:message key="javascript.role.alert2"/>");
	}
}
}

//����
function addMethod(){
	window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?method=toPrepareAddRecord';
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
			//alert(document.serveTableForm.ids[i].value);
				if(document.getElementsByName("submittype")[i].value!="���ύ"){
					
					window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareUpdateRecord';
				}else{
					alert("���������ύ�������޸ģ�");
				}
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert1"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		if(document.getElementsByName("submittype")[0].value!="���ύ"){
			
			window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareUpdateRecord';
	
		}else{
			alert("���������ύ�������޸ģ�");
		}
	}else
	{
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}

}
//�ύ
function submitMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
			//alert(document.serveTableForm.ids[i].value);
			//alert(document.getElementsByName("submittype")[i].value)
				if(document.getElementsByName("submittype")[i].value!="���ύ"){
					//alert(1)
					window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?billno='+document.serveTableForm.ids[i].value+'&isreturn=Y&submitflag=Y&method=toUpdateRecord';
				}else{
					alert("���������ύ�������ظ��ύ��");
				}
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert1"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		if(document.getElementsByName("submittype")[0].value!="���ύ"){
			window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?billno='+document.serveTableForm.ids.value+'&isreturn=Y&submitflag=Y&method=toUpdateRecord';
		}else{
			alert("���������ύ�������ظ��ύ��");
		}
	}else
	{
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}

}

//ɾ��
function deleteMethod(valstr){
	if(serveTableForm.ids)
	{
		var l = document.serveTableForm.ids.length;
		if(l)
		{
			for(i=0;i<l;i++)
			{
				if(document.serveTableForm.ids[i].checked == true)
				{
				//alert(document.serveTableForm.ids[i].value);
					if(valstr=='N'){
						if(document.getElementsByName("submittype")[i].value!="���ύ"){
							if(confirm("<bean:message key="javascript.role.deletecomfirm"/>")){
								window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDeleteRecord';
							}
						}else{
							alert("���������ύ������ɾ����");
						}
					}else{
						if(confirm("<bean:message key="javascript.role.deletecomfirm"/>")){
							window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDeleteRecord';
						}
					}
					return;
				}
			}
			if(l >0)
			{
				alert("<bean:message key="javascript.role.alert3"/>");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			if(valstr=='N'){
				if(document.getElementsByName("submittype")[0].value!="���ύ"){
					if(confirm("<bean:message key="javascript.role.deletecomfirm"/>")){
						window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDeleteRecord';
					}
				}else{
					alert("���������ύ������ɾ����");
				}
			}else{
				if(confirm("<bean:message key="javascript.role.deletecomfirm"/>")){
					window.location = '<html:rewrite page="/ContractTransferAssignAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDeleteRecord';
				}
			}
		}else
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

//�鿴����
function reportMethod(){

}

</script>

<form name="toModifyPageFrm" method="POST" action="">
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