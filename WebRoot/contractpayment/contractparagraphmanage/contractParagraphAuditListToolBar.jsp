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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ncontractparagraphaudit" value="Y"> 
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'�鿴��ȷ��',"100","1","modifyMethod()");
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
	toDoMethod(getIndex(),"toDisplayRecord","&returnMethod=toSearchRecordAudit","<bean:message key="javascript.role.alert2"/>");
}

//�޸�
function modifyMethod(){
	var index = getIndex();	
	/* var submitType=getVal("submitType",index);//�ύ״̬		
	if(submitType && submitType != "N"){
		alert("�ü�¼���ύ,�����޸�!"); 
		return;
	} */
	var auditStatus=document.getElementsByName("auditStatus")[index].value;//���״̬
	if(auditStatus == "N" || auditStatus=="X"){
		toDoMethod(index,"toPrepareAuditRecord","");
	}else{
		toDoMethod(getIndex(),"toAuditDisplay","","<bean:message key="javascript.role.alert2"/>");
	}
	
	/* toDoMethod(index,"toPrepareUpdateRecord","","<bean:message key="javascript.role.alert1"/>"); */
}

function modifyMethod2(id,auditStatus){
	if(auditStatus == "N" || auditStatus=="X"){
		window.location = '<html:rewrite page="/contractParagraphAuditAction.do"/>?id='+id+'&method=toPrepareAuditRecord';
	}else{
		window.location = '<html:rewrite page="/contractParagraphAuditAction.do"/>?id='+id+'&method=toAuditDisplay';
	}
	
	/* toDoMethod(index,"toPrepareUpdateRecord","","<bean:message key="javascript.role.alert1"/>"); */
}


//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/contractParagraphAuditAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
	}else if(alertMsg && alertMsg != ""){
		alert(alertMsg);
	}
}

//��ȡѡ�м�¼�±�
function getIndex(){
	if(serveTableForm.ids){
		var ids = serveTableForm.ids;
		if(ids.length == null){
			return 0;
		}
		for(var i=0;i<ids.length;i++){
			if(ids[i].checked == true){
				return i;
			}
		}		
	}
	return -1;	
}

//����name��ѡ���±��ȡԪ�ص�ֵ
function getVal(name,index){
	var obj = eval("serveTableForm."+name);
	if(obj && obj.length){
		obj = obj[index];
	}
	return obj ? obj.value : null;
}

/* //����Excel
function excelMethod(){
  serveTableForm.genReport.value="Y";
  serveTableForm.target = "_blank";
  document.serveTableForm.submit();
} */

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