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
  
  AddToolBarItemEx("ViewAndAuditBtn","../../common/images/toolbar/edit.gif","","",'�鿴�����',"100","1","viewAndAuditMethod()");
  <%-- �Ƿ��п�д��Ȩ��--%>
  <%-- <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintcontractaudit" value="Y"> --%>     
  <%-- </logic:equal> --%> 
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
  serveTableForm.target = "_self";
  document.serveTableForm.submit();
}

//�鿴
function viewAndAuditMethod(){
  toDoMethod(getIndex(), "toDisplayRecord","","<bean:message key="javascript.role.alert2"/>");
}

//���
function auditMethod(){
	var index = getIndex();
	
	var submitType=getVal("submitType",index);//�ύ״̬
	var auditStatus=getVal("auditStatus",index);//���״̬
		
	if(submitType && submitType == "N"){
		alert("��ѡ�����ύ�ļ�¼!"); 
		return;
	}
	if(auditStatus && !auditStatus == "N"){
		alert("�ü�¼�����!"); 
		return;
	}
		
	toDoMethod(index,"toAuditRecord","","<bean:message key="javascript.role.alert.jilu"/>");
}

//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/maintContractAuditAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
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

//��ͬ���ڵļ�¼�ú�ɫ������ʾ
window.onload=function(){
	var edates = document.getElementsByName("contractEdate");
	var contractStatus= document.getElementsByName("contractStatus");
	var todayDate=document.getElementById("hiddatestr").value.replace(/-/g,"");
	var warningStatus=document.getElementsByName("warningStatus");
	/**
	var date = new Date();
	var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	mm = mm > 10 ? mm : "0"+mm
	var dd = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var todayDate = "" + yyyy + mm + dd
	*/

	for ( var i = 0; i < edates.length; i++) {
		var edate = edates[i].value.replace(/-/g,"");
		
		if(Number(todayDate)>=Number(edate) && contractStatus[i].value != "�˱�" && contractStatus[i].value != "��ʷ" && warningStatus[i].value!="S"){
			row = edates[i].parentNode.parentNode;			
			row.style.color = "#FF0000"
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