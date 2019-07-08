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
 
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintContractModify" value="Y"> 
  		AddToolBarItemEx("AuditBtn","../../common/images/toolbar/edit.gif","","",'�� ��',"65","1","modifyMethod()");
  		AddToolBarItemEx("SynchBtn","../../common/images/toolbar/edit.gif","","",'ͬ��-������Ϣ',"105","1","synchMethod()");
  		AddToolBarItemEx("modifyBtn","../../common/images/toolbar/edit.gif","","",'�ĳ�δ�´�״̬',"115","1","modifyMethod2()");
  	    AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'ɾ����ͬ',"85","1","deleteMethod()");
  	  	AddToolBarItemEx("ImportBtn","../../common/images/toolbar/dl_log.gif","","","�� ��","65","1","importMethod()");
  		AddToolBarItemEx("SynchBtn2","../../common/images/toolbar/edit.gif","","",'ͬ��-���ά����������',"160","1","synchMethod2()");
  </logic:equal>
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//ͬ��-���ά����������
function synchMethod2(){
	//���ð�ť
	document.getElementById("AuditBtn").disabled=true;
	document.getElementById("SynchBtn").disabled=true;
	document.getElementById("SynchBtn2").disabled=true;
	document.getElementById("modifyBtn").disabled=true;
	document.getElementById("DelBtn").disabled=true;
	document.getElementById("ImportBtn").disabled=true;
	document.getElementById("ViewBtn").disabled=true;
	window.location = '<html:rewrite page="/maintContractModifyAction.do"/>?method=toSynchRecord2';
}

//����excel
function importMethod(){
	window.location = '<html:rewrite page="/maintContractModifyAction.do"/>?method=toPrepareImportRecord';
}

//ɾ��
function deleteMethod(){
	var index = getIndex();
	/**
	var contractStatus=getVal("contractStatus",index);//�ύ״̬	
	if(contractStatus && contractStatus != "TB"){
		alert("��ѡ���˱��ĺ�ͬ����ɾ��!"); 
		return;
	}
	*/
	var maintContractNo=getVal("maintContractNo",index);//�ύ״̬	
	if(confirm("�Ƿ�ɾ����ͬ�ţ�"+maintContractNo+"?")){
		toDoMethod(index,"toDeleteRecord","","<bean:message key="javascript.role.alert3"/>");
	}
}

//��ѯ
function searchMethod(){
  serveTableForm.target = "_self";
  document.serveTableForm.submit();
}
//�ĳ�δ�´�״̬
function modifyMethod2(){
	var index=getIndex();
	if(index >= 0){	
		var maintContractNo=getVal("maintContractNo",index);//�ύ״̬	
		if(confirm("ȷ�Ͻ���ͬ�ţ�"+maintContractNo+" �ĳ�δ�´�״̬��")){
			//���ð�ť
			document.getElementById("AuditBtn").disabled=true;
			document.getElementById("SynchBtn").disabled=true;
			document.getElementById("modifyBtn").disabled=true;
			window.location = '<html:rewrite page="/maintContractModifyAction.do"/>?method=toUpdateRecord&id='+getVal('ids',index); 
		}
	}else{
		alert("��ѡ��Ҫ�޸ĵļ�¼��");
	}
}
//ͬ����վ��
function synchMethod(){
	//���ð�ť
	document.getElementById("AuditBtn").disabled=true;
	document.getElementById("SynchBtn").disabled=true;
	document.getElementById("SynchBtn2").disabled=true;
	document.getElementById("modifyBtn").disabled=true;
	document.getElementById("DelBtn").disabled=true;
	document.getElementById("ImportBtn").disabled=true;
	document.getElementById("ViewBtn").disabled=true;
	window.location = '<html:rewrite page="/maintContractModifyAction.do"/>?method=toSynchRecord';
}
//�鿴
function modifyMethod(){
  toDoMethod(getIndex(), "toDisplayRecord","","��ѡ��Ҫ�޸ĵļ�¼��");
}

//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(index >= 0){	
		window.location = '<html:rewrite page="/maintContractModifyAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
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