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
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/view.gif","","",'<bean:message key="toolbar.read"/>',"65","1","viewMethod()");
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ncustomercisitfeedback" value="Y"> 
  AddToolBarItemEx("UpdateBtn","../../common/images/toolbar/edit.gif","","","ת ��","65","1","updateMethod()"); 
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","","�ݷ÷���","80","1","addMethod()"); 
    AddToolBarItemEx("ReferBtn","../../common/images/toolbar/digital_confirm.gif","","","�� ��","65","1","referMethod()"); 
  </logic:equal>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
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
					window.location = '<html:rewrite page="/customerVisitFeedbackAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDisplayRecord';
					return;
				}
			}
			if(l>0)
			{
				alert("<bean:message key="customerVisitFeedback.role.alert"/>");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			window.location = '<html:rewrite page="/customerVisitFeedbackAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDisplayRecord';
		}
		else
		{
			alert("<bean:message key="customerVisitFeedback.role.alert"/>");
		}
	 }

	}



//����
function addMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				if(document.serveTableForm.submitType[i].value=="��")
				{
					alert("�ü�¼���ύ�������ظ�����");
				}else
				{
					window.location = '<html:rewrite page="/customerVisitFeedbackAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareAddRecord';
				}
				return;
			}
		}
		if(l>0)
		{
			alert("<bean:message key="customerVisitFeedback.role.alert"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		if(document.serveTableForm.submitType.value=="��")
		{
			alert("�ü�¼���ύ�������ظ�����");
		}else
		{
			window.location = '<html:rewrite page="/customerVisitFeedbackAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareAddRecord';
		}
	}
	else
	{
		alert("<bean:message key="customerVisitFeedback.role.alert"/>");
	}
 }

}

var req;
function Evenmore(obj,listname,td){	
	var comid=obj.value;
	 var selectfreeid = document.getElementById(listname);
	if(comid!="" && comid!="%"){
	 if(window.XMLHttpRequest) {
		 req = new XMLHttpRequest();
	 }else if(window.ActiveXObject) {
		 req = new ActiveXObject("Microsoft.XMLHTTP");
	 }  //����response
	 var url='<html:rewrite page="/customerVisitFeedbackAction.do"/>?method=toStorageIDList&comid='+comid;//��ת·��
	 req.open("post",url,true);//post �첽
	 req.onreadystatechange=function getnextlist(){
		
			if(req.readyState==4 && req.status==200){
			 var xmlDOM=req.responseXML;
			 var rows=xmlDOM.getElementsByTagName('rows');
			 if(rows!=null){
				    if(rows.length>0)
				    	{			    	
				    	selectfreeid.options.length=0;
				    	selectfreeid.add(new Option("��ѡ��",""));
				    	}
				    else
				    	{
				    	selectfreeid.options.length=0;
				    	selectfreeid.add(new Option("ȫ��",""));	
				    	}
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
			document.getElementById(td).style.display="";
	 };//�ص�����
	 req.send(null);//������
	}else{		
		selectfreeid.options.length=0;
    	selectfreeid.add(new Option("��ѡ��",""));
	}
}

function updateMethod(){
	var index = getIndex();	
	if(index >= 0){	
    var isVisit=getVal("isVisit",index)
	if(isVisit=="��"){
		alert("�ü�¼�ѷ���������ת��!");
		return;
	}
    var visitPosition=getVal("visitPosition",index);
    if(visitPosition=="ά��վ��"){
		alert("ά��վ��,����ת��!");
		return;
	}
    if(visitPosition=="��������רԱ"){
		alert("��������רԱ,����ת��!");
		return;
	}
	toDoMethod(index,"toPrepareUpdateRecord","");
	}else {
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}

function referMethod(){
	var index = getIndex();	
	if(index >= 0){	
	var submitType=getVal("submitType",index)
	if(submitType=="��"){
		alert("�ü�¼���ύ�������ظ��ύ!");
		return;
	}
	toDoMethod(index,"toSubmitRecord","");
	}else {
		alert("<bean:message key="javascript.role.alert.jilu"/>");
	}
}



//��ת����
function toDoMethod(index,method,params){
		window.location = '<html:rewrite page="/customerVisitFeedbackAction.do"/>?id='+getVal('ids',index)+'&method='+method+params;
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