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
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nqualityCheckManagementCheck" value="Y"> 
    AddToolBarItemEx("ExcelBtn", "../../common/images/toolbar/print.gif", "", "",'��ӡ��鵥', "90", "1", "printMethod()");
  </logic:equal>
  
  //AddToolBarItemEx("ExcelBtn","../../common/images/toolbar/xls.gif","","",'<bean:message key="toolbar.xls"/>',"90","1","excelMethod()");
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
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
				window.location = '<html:rewrite page="/qualityCheckManagementCheckAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toDisplayRecord';
				return;
			}
		}
		if(l >0)
		{
			alert("<bean:message key="javascript.role.alert2"/>");
		}
	}else if(document.serveTableForm.ids.checked == true)
	{
		window.location = '<html:rewrite page="/qualityCheckManagementCheckAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toDisplayRecord';
	}
	else
	{
		alert("<bean:message key="javascript.role.alert2"/>");
	}
}
}


//��ӡ
function printMethod(){
	var index = checkedIndex();
	if(index >= 0){	
	 	var processStatus=document.getElementsByName("processStatus")[index].value;//�ύ״̬
		if(processStatus == "2"||processStatus == "3"){ 
		window.open('<html:rewrite page="/qualityCheckManagementCheckAction.do"/>?id='+document.getElementsByName("ids")[index].value+'&method=toPreparePrintRecord');

		} 
	 	else{
			alert("����״̬Ϊ��¼�뱨������,�ſ��Դ�ӡ"); 
			return;
		}
		
	}else{
		alert("<bean:message key="javascript.role.alert.jilu"/>");		
	}
}


//�ύ
function saveSubmitMethod(){
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
					var issubmit=document.serveTableForm.submitType[i].value;
					if(issubmit=="N"){
						//alert(document.serveTableForm.ids[i].value);
						if(confirm("�Ƿ�ȷ���ύ���ݣ�"+document.serveTableForm.ids[i].value+"���ύ�󽫲����޸ļ�ɾ�������ݣ�"))
						{
							window.location = '<html:rewrite page="/qualityCheckManagementAction.do"/>?id='+document.serveTableForm.ids[i].value +'&method=toSubmitRecord';
						}
						return;
					}else{
						alert("��ά��������������ύ�������ٴ��ύ��");
						
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
			var issubmit=document.serveTableForm.submitType.value;
			if(issubmit=="N"){
			if(confirm("�Ƿ�ȷ���ύ���ݣ�"+document.serveTableForm.ids.value+"���ύ�󽫲����޸ļ�ɾ�������ݣ�"))
				{
					window.location = '<html:rewrite page="/qualityCheckManagementAction.do"/>?id='+document.serveTableForm.ids.value +'&method=toSubmitRecord';
				}
			}else{
				alert("��ά��������������ύ�������ٴ��ύ��");
			}
		}
		else
		{
			alert("<bean:message key="javascript.role.alert3"/>");
		}
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
		 
		 var url='<html:rewrite page="/qualityCheckManagementAction.do"/>?method=toStorageIDList&comid='+comid;//��ת·��
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
              //<!--
                CreateToolBar();
              //-->
            </script>
            </div>
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>