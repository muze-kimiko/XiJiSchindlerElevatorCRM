<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nadvisorycomplaintsaudit" value="Y"> 
  	AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
  	AddToolBarItemEx("RejectBtn","../../common/images/toolbar/delete.gif","","",'����',"65","1","rejectMethod()");
   // AddToolBarItemEx("SaveSubmitBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="tollbar.saveandrefer"/>',"120","1","saveSubmitMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/advisoryComplaintsManageSearchAction.do"/>?method=toSearchRecordAudit';
}

function saveMethod(){
	if(checkColumnInput(advisoryComplaintsManageForm)){
		var isClose=document.advisoryComplaintsManageForm.isClose.value;
		if(isClose=="Y"){
			if(confirm("�Ƿ�ȷ����˹رո���ѯͶ����,�رպ󽫲��ܶԸ�������޸ģ�")){
				document.advisoryComplaintsManageForm.isreturn.value = "Y";
				document.advisoryComplaintsManageForm.auditType.value = "Y";
				document.advisoryComplaintsManageForm.submit();
			}
		}else{
			document.advisoryComplaintsManageForm.isreturn.value = "Y";
			document.advisoryComplaintsManageForm.auditType.value = "Y";
			document.advisoryComplaintsManageForm.submit();
		}
		
	}
}
//����
function rejectMethod(){
	document.advisoryComplaintsManageForm.isreturn.value = "Y";
	document.advisoryComplaintsManageForm.auditType.value = "N";
	document.advisoryComplaintsManageForm.processType.value = "R";
	document.advisoryComplaintsManageForm.submit();
}
/* //�����ύ
function saveSubmitMethod(){
	if(checkColumnInput(advisoryComplaintsManageForm)){
		var isClose=document.advisoryComplaintsManageForm.isClose.value;
		if(isClose=="Y"){
			if(confirm("�Ƿ�ȷ����˹رո���ѯͶ����,�رպ󽫲��ܶԸ�������޸ģ�")){
				document.advisoryComplaintsManageForm.isreturn.value = "Y";
				document.advisoryComplaintsManageForm.auditType.value = "Y";
				document.advisoryComplaintsManageForm.submit();
			}
		}else{
			alert("����ѯͶ����δ��˹رգ������ύ��");
		}
		
	}
} */
 
//

function isFirm(issueSort2,issueSort3,ocaId){
	var isfirm=document.getElementById("issueSort1").value;
	if(isfirm=="GS"){
		document.getElementById("issueSort2").value=issueSort2;
		document.getElementById("issueSort3").value=issueSort3;
		document.getElementById("ocaId").value=ocaId;
		document.getElementById("Sort2").style.display="";
		document.getElementById("issue2").style.display="none";
		document.getElementById("Sort3").style.display="";
		document.getElementById("issue3").style.display="none";
		document.getElementById("O").style.display="";
		document.getElementById("oca").style.display="none";
	}else{
		document.getElementById("Sort2").style.display="none";
		document.getElementById("issue2").style.display="";
		document.getElementById("Sort3").style.display="none";
		document.getElementById("issue3").style.display="";
		document.getElementById("O").style.display="none";
		document.getElementById("oca").style.display="";
		document.getElementById("issueSort2").value="-";
		document.getElementById("issueSort3").value="-";
		document.getElementById("ocaId").value="-";
	}
}
 
function isShow(issueSort1){
	if(issueSort1=="GS"){
		document.getElementById("Sort2").style.display="";
		document.getElementById("issue2").style.display="none";
		document.getElementById("Sort3").style.display="";
		document.getElementById("issue3").style.display="none";
		document.getElementById("O").style.display="";
		document.getElementById("oca").style.display="none";
	}else{
		document.getElementById("issueSort2").value="-";
		document.getElementById("issueSort3").value="-";
		document.getElementById("ocaId").value="-";
	}
}
 
function Close(onoff,receiveDate){
	var isclose=onoff.value;
	var date=document.getElementById("completeDate");
	var time=document.getElementById("processTime")
	if(isclose=="Y"){
		var nowtime=new Date().toLocaleString().replace(/��|��/g, "-").replace(/��/g, " ");
		date.value=nowtime;
		var start=new Date(receiveDate.replace(/-/ig,'/'));
		var now=new Date(nowtime.replace(/-/ig,'/'));
		time.value=((now.getTime()-start.getTime())/(1000*3600)).toFixed(1);
	}else{
		date.value="";
		time.value="";
	}
}

function checkColumnInput(element){
	  var inputs = element.getElementsByTagName("input");
	  var selects = element.getElementsByTagName("select");
	  var msg = "";
	  var check=0;
	  
	  for(var i=0;i<inputs.length;i++){
	    if(inputs[i].type == "text"
	      && inputs[i].parentNode.innerHTML.indexOf("*")>=0 
	      && inputs[i].value.trim() == ""){
	      
	        msg += inputs[i].parentNode.previousSibling.innerHTML + "����Ϊ��\n";                       
	    }
	    if(inputs[i].type=="checkbox" && inputs[i].parentNode.innerHTML.indexOf("*")>=0 && inputs[i].checked==true){
	    	/* if(inputs[i].checked=true){
	    		check++;
	    	} */
	    	check++;
	    }
	  }
	   if(check<=0){
	   		msg+="������ѡ��һ������ָ��ͳ��\n";
	   }
	   
	  for(var i=0;i<selects.length;i++){
		  if(selects[i].parentElement.style.display!="none" && selects[i].value.trim()=="" && selects[i].parentNode.innerHTML.indexOf("*")>0){
			  var itemType=selects[i].parentNode.previousSibling.innerHTML;
			  if(typeof(itemType)=="string"){
				  msg+="��ѡ��"+selects[i].parentNode.previousSibling.innerHTML.replace(/:$/gi,"")+"\n";
			  }else{
				  msg+="��ѡ��"+selects[i].parentNode.parentNode.previousSibling.innerHTML.replace(/:$/gi,"")+"\n";
			  }
			  
		  }
	  }
	  if(msg != ""){
		    alert(msg);
		    return false;
		  } 
		  return true;
}

var req;
function Evenmore(listname,td){	
	//var storageid=obj.value;
	var sort1=document.getElementsByName("issueSort1")[0].value;
	var sort2=document.getElementsByName("issueSort2")[0].value;
	
	 var selectfreeid = document.getElementById(listname);
	if(sort1!=""){
	 if(window.XMLHttpRequest) {
		 req = new XMLHttpRequest();
	 }else if(window.ActiveXObject) {
		 req = new ActiveXObject("Microsoft.XMLHTTP");
	 }  //����response
	 var url='<html:rewrite page="/advisoryComplaintsManageAction.do"/>?method=toAuditSort4List&sort1='+sort1+'&sort2='+sort2;//��ת·��
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
				    	selectfreeid.add(new Option("(��)",""));	
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
                //SetToolBarAttribute();
              //-->
            </script>
            </div>
            </td>
        </tr>
      </table>
    </td>
  </tr>
</table>