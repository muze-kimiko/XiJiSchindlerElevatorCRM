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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="noutsourcingContract" value="Y"> 
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'�� ��',"65","1","addFromQuoteMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modifyMethod()");
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/digital_confirm.gif","","",'�ύ',"65","1","referMethod()");
    AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()");
  </logic:equal>
  //AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
  //serveTableForm.genReport.value = "";
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
        window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDisplayRecord';
        return;
      }
    }
    if(l >0)
    {
      alert("<bean:message key="javascript.role.alert2"/>");
    }
  }else if(document.serveTableForm.ids.checked == true)
  {
    window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDisplayRecord';
  }
  else
  {
    alert("<bean:message key="javascript.role.alert2"/>");
  }
}
}

 //���� 
function addFromQuoteMethod(){
  window.location = '<html:rewrite page="/outsourcingContractSearchAction.do"/>?method=toNextSearchRecord';
}

//�޸�
function modifyMethod(){
if(serveTableForm.ids)
{
  var l = document.serveTableForm.ids.length;
  if(l)
  {
    for(i=0;i<l;i++)
    {   	
      if(document.serveTableForm.ids[i].checked == true )
      {
    	  var submitType=serveTableForm.submitType[i].value;//�ύ״̬
    	if(submitType=="���ύ"){//δ�ύ
    		alert("�ü�¼���ύ,��ѡ��δ�ύ�ļ�¼!");
    		return;
    	 }else{
    		 window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareUpdateRecord';

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
	  var submitType=serveTableForm.submitType.value;//�ύ״̬
	  if(submitType=="���ύ"){//δ�ύ
		  alert("�ü�¼���ύ,��ѡ��δ�ύ�ļ�¼!");
	    }else{
		  window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareUpdateRecord';        
  	     }
	  return;
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
  
  if(l)
  {
    for(i=0;i<l;i++)
    {

      if(document.serveTableForm.ids[i].checked == true )
      {
    	var submitType=serveTableForm.submitType[i].value;//�ύ״̬
		if(submitType=="���ύ"){//δ�ύ
        if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids[i].value))
        {
        	alert("�ü�¼���ύ,��ѡ��δ�ύ�ļ�¼!"); 
          }
		}else{
			 window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDeleteRecord';
		       
		}
        return;
      }
    }
    if(l >0)
    {
      alert("<bean:message key="javascript.role.alert3"/>");
    }
  }
  else if(document.serveTableForm.ids.checked == true )
  {
	  var submitType=serveTableForm.submitType.value;//�ύ״̬
	  if(submitType=="���ύ"){//δ�ύ
    if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids.value))
      {
    	 alert("�ü�¼���ύ,��ѡ��δ�ύ�ļ�¼!");  
        }
	  }else{
		  window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDeleteRecord'; 
	  }
	  return;
  }
  else
  {
    alert("<bean:message key="javascript.role.alert3"/>");
  }
}
}

// �ύ
function referMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				var submitType=serveTableForm.submitType[i].value;//�ύ״̬
				if(submitType=="���ύ"){//δ�ύ
					alert("�ü�¼���ύ,��ѡ��δ�ύ�ļ�¼!"); 
				}else{
					window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toReferRecord';	
				}
				return;
			}
		}
		if(l>0){
			alert("<bean:message key="javascript.role.alert.jilu"/>");
		}
	}
	else if(document.serveTableForm.ids.checked == true)
	{
		var submitType=serveTableForm.submitType.value;//�ύ״̬
		if(submitType=="���ύ"){
			alert("�ü�¼���ύ,��ѡ��δ�ύ�ļ�¼!");
			return;
		}else{
			var url="";
			window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toReferRecord';
			return;
			
		}
	}
	else
	{
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}	
}


function referMethod2(){
	if(serveTableForm.ids)
	{
		var l = document.serveTableForm.ids.length;
		if(l)
		{
			for(i=0;i<l;i++)
			{
				if(document.serveTableForm.ids[i].checked == true)
				{
					var auditStatus=serveTableForm.auditStatus[i].value;//���״̬
					var taskSubFlag=serveTableForm.taskSubFlag[i].value;//�´�״̬
					if(auditStatus=="�����" && taskSubFlag=="δ�´�"){//δ�ύ
						window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=totask';
					}else{
						alert("�ü�¼δͨ����δ��˻����´�,��ѡ�������,δ�´�ļ�¼!"); 
					}
					return;
				}
			}
			if(l>0){
				alert("<bean:message key="javascript.role.alert.jilu"/>");
			}
		}
		else if(document.serveTableForm.ids.checked == true)
		{
			var auditStatus=serveTableForm.auditStatus.value;//���״̬
			var taskSubFlag=serveTableForm.taskSubFlag.value;//�´�״̬
			if(auditStatus=="�����" && taskSubFlag=="δ�´�"){
				var url="";
				window.location = '<html:rewrite page="/outsourcingContractAction.do"/>?id='+document.serveTableForm.ids.value +'&method=totask';
				return;
			}else{
				alert("�ü�¼δͨ����δ��˻����´�,��ѡ�������,δ�´�ļ�¼!");
				return;
			}
		}
		else
		{
			alert("<bean:message key="javascript.role.alert.jilu"/>");
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