  <%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script type="text/javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<!--
	�ͻ�������ҳ������
-->
<script language="javascript">
//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  AddToolBarItemEx("ViewBtn","../../common/images/toolbar/search.gif","","",'<bean:message key="toolbar.search"/>',"65","0","searchMethod()"); 
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmaintenancereceipt" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'�� ��',"65","1","saveMethod()");
    AddToolBarItemEx("SaveBtn2","../../common/images/toolbar/save.gif","","",'���汸ע',"90","1","saveRem()");
  </logic:equal>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}


//��ѯ
function searchMethod(){
	var edate1 = document.getElementById("edate1").value;
	var sdate1 =document.getElementById("sdate1").value;
	if(sdate1=="" || edate1=="")
	{
		alert("�´����� ����Ϊ�գ�");
		return;
	}
	if(sdate1!="" && edate1!=""){	
       if(sdate1>edate1)
     	{
      	alert("��ʼ���ڱ���С�ڽ������ڣ�");
      	document.getElementById("edate1").value="";
	     return;
	   }
     }
	
	maintenanceReceiptForm.target = "_self";
	document.maintenanceReceiptForm.method.value="";
	document.maintenanceReceiptForm.submit();
	
}

function simpleOpenWindow(actionName,id){
	var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
	var url=projectName+actionName+".do?method=toDisplayRecord&isOpen=Y&id="+id;
	window.open(url,'','height=500px, width=1000px,scrollbars=yes, resizable=yes,directories=no');
}

function openWindow(actionName,id){
	var projectName = window.location.pathname.replace(window.location.pathname.replace(/\s*\/+.*\/+/g,''),'');
	var url=projectName+actionName+".do?method=toMaintenanceWorkPlanDisplayRecord&isOpen=Y&id="+id;
	window.open(url,'','scrollbars=yes,resizable=yes,directories=no,fullscreen=yes');
}

function changeisBox(){
	var checkboxids=document.getElementsByName("checkboxMcds");
	var isBoxs=document.getElementsByName("isBox");
	for(var i=0;i<checkboxids.length;i++){
		
		if(checkboxids[i].checked){
	      isBoxs[i].value="Y";
		}else
		{
			isBoxs[i].value="N";
		}  
	}
}

//���汸ע
function saveRem(){
	var checkboxids=document.getElementsByName("checkboxMcds");//��Ҫ����ĸ�ѡ��
	var checkflag=false;
	for(var i=0;i<checkboxids.length;i++){
		
		if(checkboxids[i].checked){
			checkflag=true;
			break;
		}
	}
	if(checkflag){
		document.maintenanceReceiptForm.method.value="toSaveRem";
		document.getElementById("ViewBtn").disabled=true;
		document.getElementById("SaveBtn").disabled=true;
		document.getElementById("SaveBtn2").disabled=true;
		document.maintenanceReceiptForm.submit();
	}else{
		alert("����ѡ��һ����¼���б��棡");
	}
} 
//����
function saveMethod(){
	var errorstr=savecheckinfo("");
	if(errorstr==""){
	document.maintenanceReceiptForm.method.value="toRecord";
	document.getElementById("ViewBtn").disabled=true;
	document.getElementById("SaveBtn").disabled=true;
	document.getElementById("SaveBtn2").disabled=true;
	document.maintenanceReceiptForm.submit();
	}else{
	alert(errorstr);	
	}
} 

function savecheckinfo(error){
	var checkboxids=document.getElementsByName("checkboxMcds");//��Ҫ����ĸ�ѡ��
	var maintPersonnels=document.getElementsByName("maintPersonnel");//ȷ�Ϸ���ά����
	var assignedSignFlags=document.getElementsByName("assignedSignFlag");//ȷ��ǩ�ձ�־
	var returnReasons=document.getElementsByName("ReturnReason");//ȷ���˻�ԭ��
	var inAssignedSignFlags=document.getElementsByName("inAssignedSignFlag");//ȷ���ѱ���ǩ�ձ�־	
	var boxnum=0;
	var sureflag=true;
	for(var i=0;i<checkboxids.length;i++){
		
		if(checkboxids[i].checked){
		var assignedSignFlag=assignedSignFlags[i].value;
		var maintPersonnel=maintPersonnels[i].value;
		var returnReason=returnReasons[i].value;
		var inAssignedSignFlag=inAssignedSignFlags[i].value;
		if(inAssignedSignFlag=="R")
			{  
			     error+=" �������˻أ������ظ�������\n";
			     checkboxids[i].checked=false;
			     changeisBox();
			}else  if(inAssignedSignFlag=="Y")
				{
				 error+=" ������ǩ�գ������ظ�������\n";
				 checkboxids[i].checked=false;
				}
			else
			{
				if(assignedSignFlag==""){
					error+=" ��ѡ��ǩ��״̬��\n";
				}
				
				if(assignedSignFlag=="Y"){
					if(maintPersonnel=="")
					{
					  error+=" �����ά������\n";
					}
				}
				if(assignedSignFlag=="R"){
					if(returnReason=="")
					{
					  error+=" ���д�˻�ԭ��\n";
					}
					sureflag=false;
				}
			}			
		boxnum++;
		if(error!=""){
			error="���"+(i+1)+"��\n"+error;
			
			break;
		} 
		}
	}
	
	if(boxnum==0){
		error="����ѡ��һ����¼���б��棡";
	}else if(sureflag==false&&!confirm("ֻ�в��Ǳ�վ�ĵ��ݣ�����ѡ���˻�")){
		error="��ȡ���˱���";
	}
	return error;
}

/* function rowstyle()
{
	var table=document.getElementById("dynamictable_0");
	table.style.display="none";
	var tr=table.rows;
	var len=tr.length>15?15:tr.length;
	
	for(var i=1;i<tr.length;i++)
	{
		
		if(i%2==0)
		{
			tr[i].className="oddRow3";
		}else
		{
			tr[i].className="evenRow3";
		}
		
	}
	table.style.display="";
	
}
 */
 function getmaintPersonnel(selectObj,station)
 {
	 //alert(selectObj.childNodes.length)
 	if(selectObj.childNodes.length<=2)
 	{
 		
 	
 	 var pathName = window.document.location.pathname;
 	var projectName = pathName.substring(0, pathName.substr(1).indexOf('/')+1);
 	var url = projectName+"/maintenanceReceiptAction.do?method=getMaintPersonnel" + 
 			"&mainStation="+station;

 	 var obj = $.ajax({
 			url: url,
 			async:false				
 		}); 
 	var maintPersonnels = eval(obj.responseText);
 	
 	for(var i=0;i<maintPersonnels.length;i++)
 	{
 		var opt = document.createElement("option");
 		opt.value = maintPersonnels[i].id;
 		opt.innerHTML = maintPersonnels[i].name;
 		selectObj.appendChild(opt);
 		//this.innerHTML+="<option value='"+maintPersonnels[i].id+"'>"+maintPersonnels[i].name+"</option>";	
 	}
 	}
    //alert(this.innerHTML);
 }

 function assignedSignFlagChange(value,index){
	 var checkboxids=document.getElementsByName("checkboxMcds");//��Ҫ����ĸ�ѡ��
	 //var inAssignedSignFlags=document.getElementsByName("inAssignedSignFlag");//�Ƿ�ǩ��/�˻�
	 var assignedSignFlags=document.getElementsByName("assignedSignFlag");//ǩ��/�˻ر�־ 
	 if(index==1||index=="1"){
	 for(var i=0;i<checkboxids.length;i++){
			//var assval=assignedSignFlags[i].value;
			//if(assval==""||assval==null){	
				assignedSignFlags[i].value=value;
			//}
		}
	 }
 }
 function maintPersonnelChange(value,index){
	 var checkboxids=document.getElementsByName("checkboxMcds");//��Ҫ����ĸ�ѡ��
	 var maintPersonnel=document.getElementsByName("maintPersonnel");//ǩ��/�˻ر�־ 
	 if(index==1||index=="1"){
	 for(var i=0;i<checkboxids.length;i++){
			//var maintper=maintPersonnel[i].value;
			//if(maintper==""||maintper==null){	
				maintPersonnel[i].value=value;
			//}
		}
	 }
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
		 
		 var url='<html:rewrite page="/maintenanceReceiptAction.do"/>?method=toStorageIDList&comid='+comid;//��ת·��
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