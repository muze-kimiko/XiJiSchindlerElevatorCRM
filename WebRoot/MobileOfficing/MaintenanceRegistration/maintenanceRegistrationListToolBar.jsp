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
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","displayMethod()");
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenanceregistration" value="Y">   
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'�� ��',"65","1","modifyMethod()");
  </logic:equal>
  
  //AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
	var sdate=document.getElementById("sdate");
	var edate=document.getElementById("edate");
	if(sdate.value!=""&&edate.value!=""){
		if(sdate.value>edate.value){
			alert("��ʼ���ڱ���С�ڽ�������!");
			edate.value="";
			return;
		}
	}
	
	//serveTableForm.genReport.value = "";
	serveTableForm.target = "_self";
	document.serveTableForm.submit();
}
//�鿴
function displayMethod(){
	var index = getIndex();	
	if(index >= 0){	
	toDoMethod(index,"toDisplayRecord","","<bean:message key="javascript.role.alert1"/>");
	}else {
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}

//����
function modifyMethod(){
	var index = getIndex();	
	if(index >= 0){	
	var handleStatus=document.getElementsByName("handleStatus")[index];
	if(handleStatus.value=="3"){
		alert("��ѡ��,δ�깤�ļ�¼���д������!");
		return ;
	}
	toDoMethod(index,"toPrepareUpdateRecord","","<bean:message key="javascript.role.alert1"/>");
	}else {
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}
	



//��ת����
function toDoMethod(index,method,params,alertMsg){
		window.location = '<html:rewrite page="/maintenanceRegistrationAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
}

//��ȡѡ�м�¼�±�
function getIndex(){
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

//����name��ѡ���±��ȡԪ�ص�ֵ
function getVal(name,index){
	var obj = eval("serveTableForm."+name);
	if(obj && obj.length){
		obj = obj[index];
	}
	return obj ? obj.value : null;
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