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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ncustomervisitplan" value="Y"> 
    AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'<bean:message key="toolbar.add"/>',"65","1","addMethod()");
    AddToolBarItemEx("ModifyBtn","../../common/images/toolbar/edit.gif","","",'<bean:message key="toolbar.modify"/>',"65","1","modiyMethod()");
    AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()");
    AddToolBarItemEx("CopyBtn","../../common/images/toolbar/copy.gif","","",'���ưݷüƻ�',"105","1","CopyMethod()");
  </logic:equal>
  
  AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//�޸�
function CopyMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		var idstr="";
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				idstr+=document.serveTableForm.ids[i].value+",";
			}
		}
		if(idstr=="")
		{
			alert("��ѡ��Ҫ���Ƶļ�¼��");
		}else{
			window.location = '<html:rewrite page="/customerVisitPlanAction.do"/>?idstr='+idstr +'&method=toCopyRecord';
		}
	}
	else
	{
		alert("��ѡ��Ҫ���Ƶļ�¼��");
	}
}

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

//����
function addMethod(){
window.location = '<html:rewrite page="/customerVisitPlanAction.do"/>?method=toPrepareAddRecord';
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
				//alert(document.serveTableForm.ids[i].value);
					window.location = '<html:rewrite page="/customerVisitPlanAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDisplayRecord';
					return;
				}
			}
			if(l >0)
			{
				alert("<bean:message key="javascript.role.alert1"/>");
			}
		}else if(document.serveTableForm.ids.checked == true)
		{
			window.location = '<html:rewrite page="/customerVisitPlanAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDisplayRecord';
		}
		else
		{
			alert("<bean:message key="javascript.role.alert1"/>");
		}
	}

}

//�޸�
function modiyMethod(){
if(serveTableForm.ids)
{
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{

				var isVisit=document.getElementsByName("isVisit")[i].value;
				if(isVisit=="��"){
					alert("�ü�¼�Ѿ��ݷã������޸ģ�");
				}else{
					window.location = '<html:rewrite page="/customerVisitPlanAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toPrepareUpdateRecord';
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
		var isVisit=document.getElementsByName("isVisit")[0].value;
		if(isVisit=="��"){
			alert("�ü�¼�Ѿ��ݷã������޸ģ�");
		}else{
		window.location = '<html:rewrite page="/customerVisitPlanAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toPrepareUpdateRecord';
		}
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
	//alert(document.serveTableForm.ids);
	
	var l = document.serveTableForm.ids.length;
	if(l)
	{
		for(i=0;i<l;i++)
		{
			if(document.serveTableForm.ids[i].checked == true)
			{
				var isVisit=document.getElementsByName("isVisit")[i].value;
				if(isVisit=="��"){
					alert("�ü�¼�Ѿ��ݷã�����ɾ����");
				}else{
					if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids[i].value))
					{
							window.location = '<html:rewrite page="/customerVisitPlanAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDeleteRecord';
					}
				
				}
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert3"/>");
		}
	}
	else if(document.serveTableForm.ids.checked == true)
	{
		var isVisit=document.getElementsByName("isVisit")[0].value;
		if(isVisit=="��"){
			alert("�ü�¼�Ѿ��ݷã�����ɾ����");
		}else{
			if(confirm("<bean:message key="javascript.role.deletecomfirm"/>"+document.serveTableForm.ids.value))
			{
				window.location = '<html:rewrite page="/customerVisitPlanAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDeleteRecord';	
			}
		}
		
	}
	else
	{
		alert("<bean:message key="javascript.role.alert3"/>");
	}
}
}

//����Excel
function excelMethod(){

	

	serveTableForm.genReport.value = "Y";
	serveTableForm.target = "_blank";
	document.serveTableForm.submit();
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
	 var url='<html:rewrite page="/customerVisitPlanAction.do"/>?method=toStorageIDList&comid='+comid;//��ת·��
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