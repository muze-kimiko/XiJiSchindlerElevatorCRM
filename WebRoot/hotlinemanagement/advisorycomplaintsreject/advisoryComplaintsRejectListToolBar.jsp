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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nadvisorycomplaintsreject" value="Y"> 
    //AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'<bean:message key="toolbar.add"/>',"65","1","addMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'����',"65","1","modiyMethod()");
    //AddToolBarItemEx("SaveSubmitBtn","../../common/images/toolbar/digital_confirm.gif","","",'<bean:message key="tollbar.referdata"/>',"80","1","saveSubmitMethod()");
    //AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'����',"65","1","deleteMethod()");
  </logic:equal>
  
  //AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
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
				window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toRejectDisplay';
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert2"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toRejectDisplay';
	}
	else
	{
		alert("<bean:message key="javascript.role.alert2"/>");
	}
}
}

//��תֵ����ҳ��
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
				//alert(document.serveTableForm.ids[i].value);
					window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareRejectRecord';
					return;
				}else{
					alert("����ѯͶ����δ�رգ����ܲ��أ�");
					
				}
				return;
			}
		}
		if(l>0)
		{
			alert("��ѡ��һ��Ҫ���صļ�¼��");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		var isClose=document.serveTableForm.isClose.value;
		if(isClose=="Y"){
			window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareRejectRecord';
		}else{
			alert("����ѯͶ����δ�رգ����ܲ��أ�")
		}
	}
	else
	{
		alert("��ѡ��һ��Ҫ���صļ�¼��");
	}
}

}

//ֱ�Ӳ���
function deleteMethod(){
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
				var isClose=document.serveTableForm.isClose[i].value;
				if(isClose=="Y"){
					//alert(document.serveTableForm.ids[i].value);
					if(confirm("�Ƿ�ȷ�����ؼ�¼��"+document.serveTableForm.ids[i].value))
					{
						window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toReject';
					}
					return;
				}else{
					alert("����ѯͶ����δ�رգ����ܲ��أ�");
					
				}
				return;
			}
		}
		if(l >0)
		{
			alert("��ѡ��һ��Ҫ���صļ�¼��");
		}
	}
	else if(document.serveTableForm.ids.checked == true)
	{
		var isClose=document.serveTableForm.isClose.value;
		if(isClose=="Y"){
		if(confirm("�Ƿ�ȷ�����ؼ�¼��"+document.serveTableForm.ids.value))
			{
				window.location = '<html:rewrite page="/advisoryComplaintsManageAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toReject';
			}
		}else{
			alert("����ѯͶ����δ�رգ����ܲ��أ�");
		}
	}
	else
	{
		alert("��ѡ��һ��Ҫ���صļ�¼��");
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