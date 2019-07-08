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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nreimbursexpensemanag" value="Y"> 
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'�� ��',"65","1","addFromQuoteMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modifyMethod()");
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
  toDoMethod(checkedIndex(),"toDisplayRecord","","<bean:message key="javascript.role.alert2"/>");
}

//��ά������ѡȡ¼��
function addFromQuoteMethod(){
  window.location = '<html:rewrite page="/reimbursExpenseManagAction.do"/>?method=toPrepareAddRecord';
}

//�޸�
function modifyMethod(){

	var index = checkedIndex();

	if(index >= 0){			
		toDoMethod(index,"toPrepareUpdateRecord","");
	
	} else {
		alert("<bean:message key="javascript.role.alert1"/>");
	}
}

//ɾ��
function deleteMethod(){
	var index = checkedIndex();
	
	if(index >= 0){	
		if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.getElementsByName("ids")[index].value)){
			toDoMethod(index,"toDeleteRecord","");
		}
	} else {
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}

//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/reimbursExpenseManagAction.do"/>?id='+document.getElementsByName("ids")[index].value+'&method='+method+params;
	}else if(alertMsg && alertMsg != ""){
		alert(alertMsg);
	}
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
              CreateToolBar();
            </script>
            </div>
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>