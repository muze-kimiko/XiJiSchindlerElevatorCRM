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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nadvisorycomplaintsaudit" value="Y"> 
    AddToolBarItemEx("AuditBtn","../../common/images/toolbar/edit.gif","","",'�����ܽ�',"80","1","modiyMethod()");
  </logic:equal>
  
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
				window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toAuditDisplay&returnMethod='+"toSearchRecordAudit";
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert2"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toAuditDisplay&returnMethod='+"toSearchRecordAudit";
	}
	else
	{
		alert("<bean:message key="javascript.role.alert2"/>");
	}
}
}

//���
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
					var isClose=document.serveTableForm.isClose[i].value;
					if(isClose=="Y"){
						alert("����ѯͶ�����ѹرգ����ܽ����޸ģ�");
					}else{
						//alert(document.serveTableForm.ids[i].value);
						window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareAuditRecord&returnMethod='+"toSearchRecordAudit"+'&authority='+"advisorycomplaintsaudit";
						return;
					}
					return;
				}
			}
			if(l>0)
			{
				alert("��ѡ��һ��Ҫ��˵ļ�¼��");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			var isClose=document.serveTableForm.isClose.value;
			if(isClose=="Y"){
				alert("����ѯͶ�����ѹرգ����ܽ����޸ģ�");
			}else{
				window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareAuditRecord&returnMethod='+"toSearchRecordAudit"+'&authority='+"advisorycomplaintsaudit";
			}
		}
		else
		{
			alert("��ѡ��һ��Ҫ��˵ļ�¼��");
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
              //<!--
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