 <%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<!--
	�ͻ�������ҳ������
-->
<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nElevatorCoordinateLocation" value="Y"> 
  //AddToolBarItemEx("ImportBtn","../../common/images/toolbar/dl_log.gif","","",'<bean:message key="elevatorSale.toolbar.import"/>',"65","1","importMethod()");
  AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modiyMethod()");
  AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'ɾ������',"85","1","deleteMethod('N')");
  AddToolBarItemEx("DelBtn2","../../common/images/toolbar/delete.gif","","",'ɾ����Ŀ���Ƽ�¥���� ',"150","1","deleteMethod('X')");
  AddToolBarItemEx("DelBtn3","../../common/images/toolbar/delete.gif","","",'ɾ����¼',"85","1","deleteMethod('Y')");
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
				window.location = '<html:rewrite page="/elevatorCoordinateLocationAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDisplayRecord';
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert2"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/elevatorCoordinateLocationAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDisplayRecord';
	}
	else
	{
		alert("<bean:message key="javascript.role.alert2"/>");
	}
}
}

//����excel
function importMethod(){
window.location = '<html:rewrite page="/elevatorCoordinateLocationAction.do"/>?method=toPrepareImportRecord';
}


//����
function addMethod(){
window.location = '<html:rewrite page="/elevatorCoordinateLocationAction.do"/>?method=toPrepareAddRecord';
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
				window.location = '<html:rewrite page="/elevatorCoordinateLocationAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareUpdateRecord';
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert1"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/elevatorCoordinateLocationAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareUpdateRecord';
	}
	else
	{
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}

}

//ɾ��
function deleteMethod(obj){
if(serveTableForm.ids)
{
	//alert(document.serveTableForm.ids);
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				//alert(document.serveTableForm.ids[i].value);
				var showinfo="�Ƿ�ȷ��ɾ������? ";
				if(obj=='Y'){
					showinfo="<bean:message key="javascript.role.deletecomfirm"/>";
				}else if(obj=='X'){
					showinfo="�Ƿ�ȷ��ɾ����Ŀ���Ƽ�¥����? ";
				}
				if(confirm(showinfo+document.serveTableForm.ids[i].value))
				{
					window.location = '<html:rewrite page="/elevatorCoordinateLocationAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDeleteRecord&isdelete='+obj;
				}
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert3"/>");
		}
	}
	else if(document.serveTableForm.ids.checked == true)
	{
		var showinfo="�Ƿ�ȷ��ɾ������? ";
		if(obj=='Y'){
			showinfo="<bean:message key="javascript.role.deletecomfirm"/>";
		}else if(obj=='X'){
			showinfo="�Ƿ�ȷ��ɾ����Ŀ���Ƽ�¥����? ";
		}
		if(confirm(showinfo+document.serveTableForm.ids.value))
		{
			window.location = '<html:rewrite page="/elevatorCoordinateLocationAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDeleteRecord&isdelete='+obj;
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