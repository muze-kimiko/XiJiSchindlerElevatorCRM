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
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.historyread"/>',"90","1","viewMethod()");
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ncustreturnregister" value="Y"> 
    AddToolBarItemEx("VisitBtn","../../common/images/toolbar/add.gif","","",'<bean:message key="toolbar.visit"/>',"90","1","visitMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.returnResult"/>',"90","1","modiyMethod()");
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

//��ʷ�鿴
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
 				window.location = '<html:rewrite page="/custReturnRegisterAction.do"/>?id='+document.serveTableForm.ids[i].value+'&method=toHistoryDisplayRecord&initdate=Y';
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.custreturnregister.role.alert1"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/custReturnRegisterAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toHistoryDisplayRecord&initdate=Y';
	}
	else
	{
		alert("<bean:message key="javascript.custreturnregister.role.alert1"/>");
	}
}
}

//��ʼ�ط�
function visitMethod(){
	if(serveTableForm.ids)
	{ 
		
		var l = document.serveTableForm.ids.length;
		if(l)
		{
			for(i=0;i<l;i++)
			{
				
				if(document.serveTableForm.ids[i].checked == true)
				{
					if(document.serveTableForm.reMark[i].value=="Y"){
						if(document.serveTableForm.operDate[i].value==null 
								|| document.serveTableForm.operDate[i].value==""){
								window.location = '<html:rewrite page="/custReturnRegisterAction.do"/>?id='+document.serveTableForm.ids[i].value+'&method=toPrepareAddRecord';
								return;					
						}else
							{
								if(document.getElementsByName("isProblemkk")[i].value!=null
										&& document.getElementsByName("isProblemkk")[i].value=="Y"
											&& (document.serveTableForm.isReturnResult[i].value==null 
												|| document.serveTableForm.isReturnResult[i].value=="")){
									alert("<bean:message key="javascript.custreturnregister.role.alert6"/>");
									return;
								}else
								{
									window.location = '<html:rewrite page="/custReturnRegisterAction.do"/>?id='+document.serveTableForm.ids[i].value+'&method=toPrepareAddRecord';
									return;
								}
							}
					}else
					{
						alert("<bean:message key="javascript.custreturnregister.role.alert7"/>");
						return;
					}
				}
			}
			if(l >0)
			{
				alert("<bean:message key="javascript.custreturnregister.role.alert2"/>");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			if(document.serveTableForm.reMark.value=="Y"){
				if(document.serveTableForm.operDate.value==null || document.serveTableForm.operDate.value==""){
						window.location = '<html:rewrite page="/custReturnRegisterAction.do"/>?id='+document.serveTableForm.ids.value+'&method=toPrepareAddRecord';
						return;					
				}else
					{
						if(document.getElementsByName("isProblemkk")[0].value!=null
								&& document.getElementsByName("isProblemkk")[0].value=="Y"
									&& (document.serveTableForm.isReturnResult.value==null 
										|| document.serveTableForm.isReturnResult.value=="")){
							alert("<bean:message key="javascript.custreturnregister.role.alert6"/>");
							return;
						}else
						{
							window.location = '<html:rewrite page="/custReturnRegisterAction.do"/>?id='+document.serveTableForm.ids.value+'&method=toPrepareAddRecord';
							return;
						}
					}
			}else
			{	
				alert("<bean:message key="javascript.custreturnregister.role.alert7"/>");
				return;
			}
		}
		else
		{
			alert("<bean:message key="javascript.custreturnregister.role.alert2"/>");
		}
	}
}

//�ط÷���
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
					if(document.getElementsByName("isProblemkk")[i].value!=null
							&& document.getElementsByName("isProblemkk")[i].value=="N"){
						alert("�ط�û�����⣬���跴����");
						return;
					}
					if(document.serveTableForm.isReturnResult[i].value==null 
							|| document.serveTableForm.isReturnResult[i].value=="" 
								|| document.serveTableForm.isReturnResult[i].value=="��"){
						if(document.serveTableForm.ishandleValue[i].value=="��" 
								|| document.serveTableForm.ishandleValue[i].value==""){
						    alert("<bean:message key="javascript.custreturnregister.role.alert4"/>");
						    return;
						}else{
							
							window.location = '<html:rewrite page="/custReturnRegisterAction.do"/>?id='+document.serveTableForm.toReturnResult[i].value +'&method=toPrepareUpdateRecord';
							return;
						}
				    }else{
				    	alert("<bean:message key="javascript.custreturnregister.role.alert5"/>");
				    	return;
				    }
				}

			}
			if(l >0)
			{
				alert("<bean:message key="javascript.custreturnregister.role.alert3"/>");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			if(document.getElementsByName("isProblemkk")[0].value!=null
					&& document.getElementsByName("isProblemkk")[0].value=="N"){
				alert("�ط�û�����⣬���跴����");
				return;
			}
			
			if(document.serveTableForm.isReturnResult.value==null 
					|| document.serveTableForm.isReturnResult.value=="" 
						|| document.serveTableForm.isReturnResult.value=="��"){
				if(document.serveTableForm.ishandleValue.value=="��" 
						|| document.serveTableForm.ishandleValue.value==""){
				    alert("<bean:message key="javascript.custreturnregister.role.alert4"/>");
				    return;
				}else{
					window.location = '<html:rewrite page="/custReturnRegisterAction.do"/>?id='+document.serveTableForm.toReturnResult.value +'&method=toPrepareUpdateRecord';
					return;
				}
		    }else{
		    	alert("<bean:message key="javascript.custreturnregister.role.alert5"/>");
		    	return;
			}
		}
		else
		{
			alert("<bean:message key="javascript.custreturnregister.role.alert3"/>");
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