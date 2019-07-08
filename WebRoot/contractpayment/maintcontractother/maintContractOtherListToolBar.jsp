 <%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>

<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  
  AddToolBarItemEx("SearchBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintcontractother" value="Y">
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'�� ��',"65","1","addMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/delete.gif","","",'ɾ ��',"65","1","deleteMethod()");
   </logic:equal>
  
  AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//����Excel
function excelMethod(){
	document.serveTableForm.genReport.value="Y";
	document.serveTableForm.target = "_blank";
  	document.serveTableForm.submit();
}

//��ѯ
function searchMethod(){
	document.serveTableForm.genReport.value = "";
	document.serveTableForm.target = "_self";
	document.serveTableForm.submit();
}

//�鿴
function viewMethod(){
	toDoMethod(getIndex(),"toDisplayRecord","","<bean:message key="javascript.role.alert2"/>");
}

//�½�
function addMethod(){
	var index = getIndex();

	if(index>=0){
		var nototherfee=getVal("nototherfee",index);//δ֧�����
		
		if(nototherfee<=0){
			alert("��ѡ�� δ֧����� ����0 �ļ�¼�����½�!"); 
			return;
		}
		toDoMethod(index,"toPrepareUpdateRecord","","��ѡ��һ����¼����������������");
	}else{
		alert("��ѡ��һ����¼����������������");
	}
}

//ɾ��
function deleteMethod(){
	var index = getIndex();
	if(index>=0){
		toDoMethod(index,"toPrepareDeleteRecord","","<bean:message key="javascript.role.alert3"/>");
	}else{
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}

//��ת����
function toDoMethod(index,method,params,alertMsg){
	if(params==null){
		params='';
	}
	if(index >= 0){	
		window.location = '<html:rewrite page="/maintContractOtherAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
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


//AJAX��̬��ʾά��վ
var req;
function Evenmore(obj,listname){	
	var comid=obj.value;
	 var selectfreeid = document.getElementById(listname);
	if(comid!="" && comid!="%"){
		
		 if(window.XMLHttpRequest) {
			 req = new XMLHttpRequest();
		 }else if(window.ActiveXObject) {
			 req = new ActiveXObject("Microsoft.XMLHTTP");
		 }  //����response
		 
		 var url='<html:rewrite page="/maintContractOtherAction.do"/>?method=toStorageIDList&comid='+comid;//��ת·��
		 req.open("post",url,true);//post �첽
		 req.onreadystatechange=function getnextlist(){
			
				if(req.readyState==4 && req.status==200){
				 var xmlDOM=req.responseXML;
				 var rows=xmlDOM.getElementsByTagName('rows');
				 if(rows!=null){
					    selectfreeid.options.length=0;
					    selectfreeid.add(new Option("ȫ��","%"));	

				 		for(var i=0;i<rows.length;i++){
							var colNodes = rows[i].childNodes;
							if(colNodes != null){
								var colLen = colNodes.length;
								for(var j=0;j<colLen;j++){
									var freeid = colNodes[j].getAttribute("name");
									var freename = colNodes[j].getAttribute("value");
									selectfreeid.add(new Option(freeid,freename));
					            }
				             }
				 		}
				 	}
				
				}
		 };//�ص�����
		 req.send(null);//������
	}else{		
		selectfreeid.options.length=0;
  	selectfreeid.add(new Option("ȫ��","%"));
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