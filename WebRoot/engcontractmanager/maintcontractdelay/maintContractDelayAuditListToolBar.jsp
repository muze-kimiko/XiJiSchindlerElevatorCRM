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
  //AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/edit.gif","","",'�鿴�����',"90","1","viewMethod()");
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintcontractdelayaudit" value="Y">
    //AddToolBarItemEx("RestartBtn","../../common/images/toolbar/view.gif","","",'��������',"80","1","reStartMethod()");       
  </logic:equal>
  //AddToolBarItemEx("ViewFlow","../../common/images/toolbar/viewdetail.gif","","",'�鿴��������',"110","1","viewFlow()");

  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
  serveTableForm.target = "_self";
  document.serveTableForm.submit();
}

//�鿴
function viewMethod(){
  toDoMethod(getIndex(), "toDisplayRecord","","<bean:message key="javascript.role.alert2"/>");
}

//��������
function reStartMethod(){
	var index = getIndex();
	
	if(index < 0 ){
		alert('<bean:message key="javascript.role.alert.jilu"/>');
		return;
	}
	
	var status=getVal("status",index);//���״̬		
	if(status && status != "1" && status != "2"){
		alert("��ѡ�����״̬Ϊ�����ͨ������������ֹ���ļ�¼������������!"); 
		return;
	}
	
	if(confirm("��ȷ������ˮ��Ϊ:"+getVal('ids',index)+" �ļ�¼������������?")){	
		toDoMethod(index,"toReStartProcess");
	}
}

//�鿴����
function viewFlow(){
	var index = getIndex();
	
	if(index < 0){
		alert('<bean:message key="javascript.role.alert2"/>');
		return;
	}
	
	var status=getVal("status",index);//���״̬	
	var avf=document.getElementById("avf");//�鿴��������ҳ������
	var flowname=document.getElementById("flowname").value;//�������� 
	var tokenid=getVal("tokenId",index);//��������
	
	if(status && status == "-1"){
		alert("����δ�������޷��鿴��������ͼ��");
		return;
	}
	if(tokenid == null || tokenid.value==""){
		alert("�ü�¼Ϊ��ʷ���ݣ��޷��鿴��������ͼ��");
		return;
	}

	avf.href='<html:rewrite page="/viewProcessAction.do"/>?method=toViewProcess&tokenid='+tokenid+'&flowname='+flowname;
	avf.click();
}



//��ת����
function toDoMethod(index,method,params,alertMsg){
	var	actionName = "maintContractDelayAuditAction";
	params = params == null ? '' : params;
	if(index >= 0){	
		window.location = '<html:rewrite page="/'+actionName+'.do"/>?id='+getVal('ids',index)+'&method='+method+params;
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
	var date = new Date();
	var yyyy = date.getFullYear();
	var mm = date.getMonth()+1;
	mm = mm > 10 ? mm : "0"+mm
	var dd = date.getDate() < 10 ? "0"+date.getDate() : date.getDate();
	var todayDate = "" + yyyy + mm + dd

	for ( var i = 0; i < edates.length; i++) {
		var edate = edates[i].value.replace(/-/g,"");
		
		if(Number(todayDate)>Number(edate)){
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