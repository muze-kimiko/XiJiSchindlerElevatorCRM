<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()");
  AddToolBarItemEx("AddBtn","../../common/images/toolbar/add.gif","","",'<bean:message key="toolbar.selectreturn"/>',"110","1","Evenmore()");
  AddToolBarItemEx("CloseBtn","../../common/images/toolbar/close.gif","","",'<bean:message key="toolbar.close"/>',"65","1","closeMethod()"); 
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//�ر�
function closeMethod(){
  window.close();
}
//��ѯ
function searchMethod(){
  serveTableForm.target = "_self";
  document.serveTableForm.submit();
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

/* -----------------------------------------Ajax--------------------------------------------------------- */
var req;
function Evenmore(){	
	/* var comid=obj.value;
	 var selectfreeid = document.getElementById(listname); */
	var index=checkedIndex();
	var returnarray = new Array();
	if(index!=-1){
	 if(window.XMLHttpRequest) {
		 req = new XMLHttpRequest();
	 }else if(window.ActiveXObject) {
		 req = new ActiveXObject("Microsoft.XMLHTTP");
	 }  //����response
	 var id=document.getElementsByName("ids")[index].value;
	 var url='<html:rewrite page="/searchHandoverElevatorCheckItemAction.do"/>?method=toStorageIDList&id='+id;//��ת·��
	 req.open("post",url,true);//post �첽      ʱ��¾+escape(new Date())
	 req.onreadystatechange=function getnextlist(){
		
			if(req.readyState==4 && req.status==200){
			 var xmlDOM=req.responseXML;
			 var rows=xmlDOM.getElementsByTagName('rows');
			 if(rows!=null){
			     	/* selectfreeid.options.length=0; 
			 		selectfreeid.add(new Option("��ѡ��","")); */
			 		for(var i=0;i<rows.length;i++){
			 			//alert(rows[i]);
			 			returnarray[i] = new Array();
						var colNodes = rows[i].childNodes;
						if(colNodes != null){
							var colLen = colNodes.length;
							for(var j=0;j<colLen;j++){
								var freeid = colNodes[j].getAttribute("name");
								var freename = colNodes[j].getAttribute("value");
								//selectfreeid.add(new Option(freeid,freename));
								returnarray[i].push(freeid+"="+freename);
				            }
			             }
			 		}
			 		parent.window.returnuu(returnarray);
			 	}
			}/* else { //ҳ�治����
             	alert("���������ҳ�����쳣��");
       		 } */
			//document.getElementById(td).style.display="";
	 };//�ص�����
	 req.send(null);//������
	}else{		
		//selectfreeid.parentNode.innerHTML="";
		alert("��ѡ��һ����¼��");
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
              <!--
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