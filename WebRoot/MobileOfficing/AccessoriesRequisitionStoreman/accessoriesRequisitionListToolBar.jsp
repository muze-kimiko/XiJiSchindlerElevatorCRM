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
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="naccessoriesrequisitionstoreman" value="Y"> 
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'�� ��',"65","1","modiyMethod()");
    AddToolBarItemEx("TurnBtn","../../common/images/toolbar/edit.gif","","",'ת ��',"65","1","turnMethod()");
  </logic:equal>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//ת��
function turnMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	var wmanger=document.getElementsByName("warehouseManager");
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				if(wmanger[i].value==""){
					window.location = '<html:rewrite page="/accessoriesRequisitionStoremanAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareTrunRecord';
				}else{
					alert("�Ѵ�����Ϣ�����ܽ���ת�ɣ�");	
				}
				return;  
			}
		}
		if(l>0)
		{
			alert("<bean:message key="technologySupport.role.alert"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		if(wmanger[0].value==""){
			window.location = '<html:rewrite page="/accessoriesRequisitionStoremanAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareTrunRecord';
		}else{
			alert("�Ѵ�����Ϣ�����ܽ���ת�ɣ�");	
		}
	}
	else
	{
		alert("<bean:message key="technologySupport.role.alert"/>");
	}
}
}


//��ѯ
function searchMethod(){
	var edate1 = document.getElementById("edate1").value;
	var sdate1 =document.getElementById("sdate1").value;
	if(sdate1!=""&&edate1!=""){	
       if(sdate1>edate1)
     	{
      	alert("��ʼ���ڱ���С�ڽ������ڣ�");
      	document.getElementById("edate1").value="";
	     return;
	   }
    
}
	serveTableForm.genReport.value = "";
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
}
//�޸�
function modiyMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	var wmanger=document.getElementsByName("warehouseManager");
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				if(wmanger[i].value==""){
					window.location = '<html:rewrite page="/accessoriesRequisitionStoremanAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareUpdateRecord';
				}else{
					window.location = '<html:rewrite page="/accessoriesRequisitionStoremanAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDisplayRecord';	
				}
				return;  
			}
		}
		if(l>0)
		{
			alert("<bean:message key="technologySupport.role.alert"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		if(wmanger[0].value==""){
			window.location = '<html:rewrite page="/accessoriesRequisitionStoremanAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareUpdateRecord';
		}else{
			window.location = '<html:rewrite page="/accessoriesRequisitionStoremanAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDisplayRecord';	
		}
	}
	else
	{
		alert("<bean:message key="technologySupport.role.alert"/>");
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