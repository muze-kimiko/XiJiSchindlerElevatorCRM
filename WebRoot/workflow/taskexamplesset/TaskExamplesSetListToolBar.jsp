<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<!--
	��ɫ��Ϣҳ������
-->
<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  
  <%--  �жϽ�ɫ��Ϣģ��City�Ƿ��п�д��Ȩ��,��property	--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ntaskexamplesset" value="Y">
	//  AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'<bean:message key="toolbar.add"/>',"65","1","addMethod()");
	  AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'����',"65","1","modiyMethod()");
	  AddToolBarItemEx("CloseBtn","../../common/images/toolbar/delete.gif","","",'�ر�',"65","1","closeMethod()");
   </logic:equal>
  
 // AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
 function searchMethod(){	
	serveTableForm.genReport.value="N";
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
					var id=document.serveTableForm.ids[i].value;
					var isopenid=document.serveTableForm.isopenid[i].value;
					window.location = '<html:rewrite page="/taskExamplesSetAction.do"/>?id='+id +'&isopenid='+isopenid+'&method=toDisplayRecord';
					return;
				}
			}
			if(l >0)
			{
				alert("<bean:message key="javascript.role.alert2"/>");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			var id=document.serveTableForm.ids.value;
			var isopenid=document.serveTableForm.isopenid.value;
			window.location = '<html:rewrite page="/taskExamplesSetAction.do"/>?id='+id +'&isopenid='+isopenid+'&method=toDisplayRecord';
			return;
		}
		else
		{
			alert("<bean:message key="javascript.role.alert2"/>");
		}
	}
 }

//�޸�
 function modiyMethod(){
	if(serveTableForm.ids)
	{
		var l = document.serveTableForm.ids.length;
		var isopenid="";
		if(l)
		{
			for(i=0;i<l;i++)
			{
				if(document.serveTableForm.ids[i].checked == true)
				{   
					isopenid=document.serveTableForm.isopenid[i].value;
					if(isopenid!="0"){
						window.location = '<html:rewrite page="/taskExamplesSetAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareUpdateRecord';
					}else{
						alert("�����Ѿ��رգ�����������!");
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
			isopenid=document.serveTableForm.isopenid.value;
			if(isopenid!="0"){					
				window.location = '<html:rewrite page="/taskExamplesSetAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareUpdateRecord';
			}else{
				alert("�����Ѿ��رգ�����������!");
			}
			return;
		}else{
			alert("<bean:message key="javascript.role.alert1"/>");
		}
	}

 }
function closeMethod(){
	if(serveTableForm.ids){
		var l = document.serveTableForm.ids.length;
		if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				if(confirm("����رս����ɻָ������п���Ӱ�쵽��������ִ�У�ȷ��Ҫ�رմ�����"+document.serveTableForm.ids[i].value))
				{
					window.location = '<html:rewrite page="/taskExamplesSetAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toCloseRecord';
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
		if(confirm("����رս����ɻָ������п���Ӱ�쵽��������ִ�У�ȷ��Ҫ�رմ�����"+document.serveTableForm.ids[i].value))
			{
				window.location = '<html:rewrite page="/taskExamplesSetAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toCloseRecord';
			}
	}
	else
	{
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}
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