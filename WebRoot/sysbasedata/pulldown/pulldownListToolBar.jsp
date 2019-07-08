<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<!--
	��λ���ͱ�ҳ������
-->
<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
<%--  �жϵ�λ����ģ��UnitType�Ƿ��п�д��Ȩ��,��property	--%>
<logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="npulldown" value="Y">
  AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'<bean:message key="toolbar.add"/>',"65","1","addMethod()");
  AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modiyMethod()");
  AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()");
</logic:equal>

  AddToolBarItemEx("AddBtn","../../common/images/toolbar/print.gif","","",'�� ӡ',"65","1","printMethod()");
  AddToolBarItemEx("AddBtn","../../common/images/toolbar/upload_attach.gif","","",'�ϴ�����',"85","1","uploadFileMethod()");
 window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//�ϴ�����
function uploadFileMethod(){
	window.location = '<html:rewrite page="/PullDownAction.do"/>?method=toPrepareUploadFile';
}

function printMethod(){
	if(serveTableForm.ids)
	{
		var l = document.serveTableForm.ids.length;
		var type=document.getElementsByName("typeflag");
		var pull=document.getElementsByName("pullid");
		if(l)
		{
			for(i=0;i<l;i++)
			{
				if(document.serveTableForm.ids[i].checked == true)
				{
					window.open('<html:rewrite page="/PullDownAction.do"/>?pullid='+pull[i].value+'&type='+type[i].value+'&method=toPrintRecord');
					return;					
				}
			}
			if(l >0)
			{
				alert("��ѡ��һ��Ҫ��ӡ�ļ�¼��");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			var type=document.getElementsByName("typeflag");
			var pull=document.getElementsByName("pullid");
			window.open('<html:rewrite page="/PullDownAction.do"/>?pullid='+pull[0].value+'&type='+type[0].value+'&method=toPrintRecord');
			
		}
		else
		{
			alert("��ѡ��һ��Ҫ��ӡ�ļ�¼��");
		}
	}
}

//��ѯ
function searchMethod(){
	serveTableForm.genReport.value="";
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
}

//����
function addMethod(){
window.location = '<html:rewrite page="/PullDownAction.do"/>?method=toPrepareAddRecord';
}

//�鿴
function viewMethod(){
	if(serveTableForm.ids)
	{
		var l = document.serveTableForm.ids.length;
		var type=document.getElementsByName("typeflag");
		var pull=document.getElementsByName("pullid");
		if(l)
		{
			for(i=0;i<l;i++)
			{
				if(document.serveTableForm.ids[i].checked == true)
				{
				
				
					window.location = '<html:rewrite page="/PullDownAction.do"/>?pullid='+pull[i].value+'&type='+type[i].value+'&method=toDisplayRecord';
					return;
					
				}
			}
			if(l >0)
			{
				alert("<bean:message key="javascript.role.alert1"/>");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
		var type=document.getElementsByName("typeflag");
		var pull=document.getElementsByName("pullid");
			
				window.location = '<html:rewrite page="/PullDownAction.do"/>?pullid='+pull[0].value+'&type='+type[0].value+'&method=toDisplayRecord';
			
		}
		else
		{
			alert("<bean:message key="javascript.role.alert1"/>");
		}
	}

	}

//�޸�
function modiyMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	var type=document.getElementsByName("typeflag");
	var pull=document.getElementsByName("pullid");
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
			
			
				window.location = '<html:rewrite page="/PullDownAction.do"/>?pullid='+pull[i].value+'&type='+type[i].value+'&method=toPrepareUpdateRecord';
				return;
				
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert1"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
	var type=document.getElementsByName("typeflag");
	var pull=document.getElementsByName("pullid");
		
			window.location = '<html:rewrite page="/PullDownAction.do"/>?pullid='+pull[0].value+'&type='+type[0].value+'&method=toPrepareUpdateRecord';
		
	}
	else
	{
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}

}

//ɾ��
function deleteMethod(){
if(serveTableForm.ids)
{
	
	var l = document.serveTableForm.ids.length;
	var type=document.getElementsByName("typeflag");
	var pull=document.getElementsByName("pullid");
	if(l)
	{
		
		for(i=0;i<l;i++)
		{
		
			if(document.serveTableForm.ids[i].checked == true)
			{
				
					if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids[i].value))
					{
						window.location = '<html:rewrite page="/PullDownAction.do"/>?pullid='+pull[i].value+'&type='+type[i].value+'&method=toDeleteRecord';
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
		var type=document.getElementsByName("typeflag");
		var pull=document.getElementsByName("pullid");
				if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids.value))
				{
					window.location = '<html:rewrite page="/PullDownAction.do"/>?pullid='+pull[0].value+'&type='+type[0].value+'&method=toDeleteRecord';
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

//�鿴����
function reportMethod(){

}
</script>

<form name="toModifyPageFrm" method="POST" action="">
	<input type="hidden" name="" value=""/>
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