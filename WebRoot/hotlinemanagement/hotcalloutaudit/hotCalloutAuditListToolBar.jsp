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
	<%-- �Ƿ��п�д��Ȩ��--%>
	<logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nhotcalloutaudit" value="Y"> 
		AddToolBarItemEx("DelBtn","../../common/images/toolbar/edit.gif","","",'�� ��',"65","1","deleteMethod()");
    </logic:equal>
	window.document.getElementById("toolbar").innerHTML = GenToolBar("TabToolBar_Manage", "TextToolBar_Black", "Style_Over",
			"Style_Out", "Style_Down", "Style_Check");
}

//��ѯ
function searchMethod() {
	serveTableForm.genReport.value = "";
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
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
				window.location = '<html:rewrite page="/hotCalloutAuditAction.do"/>?method=toDisplayRecord&typejsp=sh&id='+document.serveTableForm.ids[i].value;
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert2"/>");
		}
	}
	else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/hotCalloutAuditAction.do"/>?method=toDisplayRecord&typejsp=sh&id='+document.serveTableForm.ids[i].value;
		return;
	}
	else
	{
		alert("<bean:message key="javascript.role.alert2"/>");
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