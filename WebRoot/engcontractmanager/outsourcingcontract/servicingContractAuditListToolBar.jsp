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
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="noutsourcingContractAudit" value="Y">
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/edit.gif","","",'�鿴�����',"100","1","returndisplayau()"); 
  </logic:equal>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
  serveTableForm.target = "_self";
  document.serveTableForm.submit();
}

//�鿴�����
function returndisplayau(){
	var id=checknum();
	if(id){
	window.location='<html:rewrite page="/outsourcingContractAuditAction.do"/>?id='+id+'&method=toDisplayRecord';	
	}else{
		alert("��ѡ��һ����¼���в鿴����ˣ�")
	}
}
 
//����
function checknum(){
	var ids=document.getElementsByName("ids");	
	if(ids){
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked==true){				
				return ids[i].value;
			}
		}
	}else{
	var id=document.getElementById("ids");
		if(id.checked==true){
			return id.value;
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
              CreateToolBar();
            </script>
            </div>
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>