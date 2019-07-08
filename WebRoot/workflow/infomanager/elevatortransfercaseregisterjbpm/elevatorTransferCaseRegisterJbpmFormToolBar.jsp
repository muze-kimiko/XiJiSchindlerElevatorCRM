<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  //AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  AddToolBarItemEx("ReturnTkBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="button.returntask"/>',"110","0","returnTaskMethod()");	
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nmyTaskOA" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="button.submit"/>',"65","1","saveMethod()");
  </logic:equal>
  </logic:notPresent>
  
  AddToolBarItemEx("ViewFlow","../../common/images/toolbar/viewdetail.gif","","",'<bean:message key="toolbar.viewflow"/>',"110","1","viewFlow()");
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//ȥ���ո�
String.prototype.trim = function(){ return this.replace(/(^\s*)|(\s*$)/g,""); }

//����
/* function returnMethod(){
  window.location = '<html:rewrite page="/maintContractQuoteSearchAction.do"/>?method=toSearchRecord';
} */

//���ش����б�
function returnTaskMethod(){
	window.location ='<html:rewrite page="/myTaskOaSearchAction.do"/>?method=toDoList&jumpto=templetdoorapp';
}

//�ύ
function saveMethod(){
	if(document.getElementById("approveResult").value!="���ص�����Ա"){
		var flags=getSelectValue();
		var taskName=document.getElementById("taskName").value
		if(taskName=="���첿�����"){
			var isDelete=document.getElementsByName("isDelete");
			var deleteRem=document.getElementsByName("deleteRem");
			for(var i=0;i<isDelete.length;i++){
				if(isDelete[i].value=="Y"){
					if(deleteRem[i].value==""||deleteRem[i].value==null){
						alert("������дɾ��ԭ��");
						return;
					}
				}
				
			}
		}
		
		if(!checkColumnInput(elevatorTransferCaseRegisterJbpmForm)){
			return;
		}else{
			if(flags!="" && flags!="1"){
				alert("��Ǹ,��ѡ����'��ͬ��',�����������������д��������!");
				return;
			}
		}
		/* alert(document.getElementById("deductMoney").value); */
		if(document.getElementById("deductMoney")==null){
			document.getElementById("SaveBtn").disabled=true;
			document.elevatorTransferCaseRegisterJbpmForm.submit();
		}else{
			if(!isNaN(document.getElementById("deductMoney").value)){
				document.getElementById("SaveBtn").disabled=true;
				document.elevatorTransferCaseRegisterJbpmForm.submit();
			}else{
				alert("�ۿ���ֻ���������֣�");
			}
			
		}
	}else{
		var approveRem=document.getElementById("approveRem").value;
		if(approveRem.trim()==""){
			alert("��ѡ����'���ص�����Ա', ���������������");
		}else{
			document.getElementById("SaveBtn").disabled=true;
			document.elevatorTransferCaseRegisterJbpmForm.submit();
		}
	}
}

//�鿴����
function viewFlow(){	
	var hiddenReturnStatus=document.getElementById("status");
	if(hiddenReturnStatus.value=="-1"){
		alert("����δ�������޷��鿴��������ͼ��");
	}else{
		var avf=document.getElementById("avf");
		var tokenid=document.getElementById("tokenid");
		var flowname=document.getElementById("flowname");
		if(tokenid!=null && tokenid.value!=""){
			if(avf!=null && tokenid!=null && flowname!=null){
				avf.href='<html:rewrite page="/viewProcessAction.do"/>?method=toViewProcess&tokenid='+tokenid.value+'&flowname='+flowname.value;
				avf.click();
			}else{
				alert("��ѡ��һ��Ҫ�鿴�ļ�¼��");
			}
		}else{
			alert("�ü�¼Ϊ��ʷ���ݣ��޷��鿴��������ͼ��");
		}
	}
}

//ȡselect���ı�ֵ
function getSelectValue(){
	var str="0";
	var obj=document.getElementById("approveResult");

 	var index=obj.selectedIndex; //��ţ�ȡ��ǰѡ��ѡ������	
 	var val = obj.options[index].text;
 	if(val!="" && val.indexOf("��")<=-1){
 	    str="1";
 	}else{
 		if(document.getElementById("approveRem").value.trim()!="" || document.getElementById("approveRem").value.trim().length>0){
 			str="1";
 		}
 	}
 	return str;
	
}

//�Ƿ���ʾ�ۿ��������
function isDeductions(object,is){
	var flag="";
	val=document.getElementById("approveResult").value;
	if(val!="" && (val=="ͬ��" || val.indexOf("�ύ")>-1)){
		flag="1"
	}
	//var tr=object.parentNode.parentNode;
	var td=document.getElementById("td1");
	if(flag=="1" && is=="Y"){
		td.innerHTML="<input type=\'text\' name=\'deductMoney\' id=\'deductMoney\' onkeypress=\'f_check_number3()\'  /><font color=\'red\'>*</font>"
	}else{
		td.innerHTML="";
	}
}

//������Ƿ�Ϊ����,���������븺�źͿ���������
function f_check_number3(){
 	if((event.keyCode>=48 && event.keyCode<=57) || event.keyCode==46 ){
 	}else{
		event.keyCode=0;
  	}
}

function checkthisvalue(obj){  
	var objv=obj.value.substring(0,obj.value.length-1);
	if(isNaN(obj.value)){
		obj.value=0;
	}
}

//���ظ���
function downloadFile(name,oldName,filePath){
	var uri = '<html:rewrite page="/elevatorTransferCaseRegisterJbpmAction.do"/>?method=toDownloadFileDispose';
	var name1=encodeURI(name);
	name1=encodeURI(name1);
	var oldName1=encodeURI(oldName);
	oldName1=encodeURI(oldName1);
	filePath=encodeURI(filePath);
	filePath=encodeURI(filePath);
	    uri +='&filePath='+ filePath;
		uri +='&filesname='+ name1;
		uri +='&folder=HandoverElevatorCheckItemRegister.file.upload.folder';
		uri+='&fileOldName='+oldName1;
	window.location = uri;
	//window.open(url);
}
//ɾ��
var tbs;
function deleteFile(td,id,iswbfile){
  tbs=td;
    if(confirm("ȷ��ɾ����")){
    	tbs.parentElement.parentElement.style.display='';
		var uri = '<html:rewrite page="/elevatorTransferCaseRegisterJbpmAction.do"/>?method=toDeleteFileDispose';
		uri +='&filesid='+ id;
		uri +='&folder=HandoverElevatorCheckItemRegister.file.upload.folder';
		uri +='&iswbfile='+iswbfile
		sendRequestDelFile(uri);  	
	}
}

var XMLHttpReq = false;
//����XMLHttpRequest����       
function createXMLHttpRequest() {
	if(window.XMLHttpRequest) { //Mozilla �����
		XMLHttpReq = new XMLHttpRequest();
	}else if (window.ActiveXObject) { // IE�����
		XMLHttpReq = new ActiveXObject("Microsoft.XMLHTTP");
	}
}
//����������
function sendRequestDelFile(url) {
	createXMLHttpRequest();
	XMLHttpReq.open("post", url, true);
	XMLHttpReq.onreadystatechange = processResponseDelFile;//ָ����Ӧ����
	XMLHttpReq.send(null);  // ��������
}
// ��������Ϣ����

  function processResponseDelFile() {
   	if (XMLHttpReq.readyState == 4) { // �ж϶���״̬
       	if (XMLHttpReq.status == 200) { // ��Ϣ�Ѿ��ɹ����أ���ʼ������Ϣ        	
       	
          	var res=XMLHttpReq.responseXML.getElementsByTagName("res")[0].firstChild.data;
          	
          	//document.getElementById("messagestring").innerHTML=res;
          	if(res=="Y"){
          		tbs.parentElement.parentElement.parentElement.removeChild(tbs.parentElement.parentElement);
          		isQualified('${elevatorTransferCaseRegisterBean.elevatorType}',${elevatorTransferCaseRegisterBean.checkNum});
          	}else{
          		alert("ɾ��ʧ�ܣ�");
          	}
          	
          	//alert(document.getElementById("messagestring").innerHTML+";123");
       	} else { //ҳ�治����
             window.alert("���������ҳ�����쳣��");
       	}
     }
  }

//ɾ����
function deleteRow(i){
    var $tr=$("#"+i);
    $("#"+i).remove()
    $("#party_b").show();
	var booleam=$("#"+i+" td:eq(1)").css("color")=="red";
    if(booleam){
    	$("#party_b").append($tr);
    }else
    {
    	var my_element=$("#party_b .red").length > 0;
    	if($("#party_b .red").length > 0){
    		$("#party_b .red:first").parent().before($tr);
    	}else{
    		$("#party_b").append($tr);
    	}
    }
    $("#"+i+" td").eq($("#"+i+" td").length-4).show();
    $("#"+i+" td").eq($("#"+i+" td").length-3).show();
    var td="<input type=\"button\" value=\"ɾ��\" onclick=\"returnDeleteRow(\'"+i+"\')\"><input type=\"hidden\" name=\"isDelete\" value=\"Y\">";
    $("#"+i+" td:eq(0)").html(td);
    var $color=$("#"+i+" td:eq(1)").css("color");
    var et=document.getElementById("elevatorType").value;
    var cn=document.getElementById("checkNum").value;
    isQualified(et,cn);
}

//����ɾ����
function returnDeleteRow(i){
    var $tr=$("#"+i);
    $("#"+i).remove()
    
    //$("#party_a").show();
    var booleam=$("#"+i+" td:eq(1)").css("color")=="red";
    if(booleam){
    	$("#party_a").append($tr);
    }else
    {
    	var my_element=$("#party_a .red").length > 0;
    	if($("#party_a .red").length > 0){
    		$("#party_a .red:first").parent().before($tr);
    	}else{
    		$("#party_a").append($tr);
    	}
    }
    //$("#"+i+" td:last").hide();
    $("#"+i+" td").eq($("#"+i+" td").length-4).hide();
    $("#"+i+" td").eq($("#"+i+" td").length-3).hide();
    var td="<input type=\"button\" value=\"ɾ��\" onclick=\"deleteRow(\'"+i+"\')\"><input type=\"hidden\" name=\"isDelete\" value=\"N\">";
    $("#"+i+" td:eq(0)").html(td);
    var et=document.getElementById("elevatorType").value;
    var cn=document.getElementById("checkNum").value;
    isQualified(et,cn);
}

/**
���ݼ��㹫ʽ��
	======���ݳ���======
	1������һ�ش󲻺ϸ�����Ϊ���ϸ�
	2��û���ش󲻺ϸ����һ�㲻�ϸ��� ���ڰ���ģ�ҲΪ���ϸ�
	3��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ��ڰ����Ϊ����ϸ�
	======���ݸ��죨����2�Ρ�3�Ρ�4�Ρ�������ȥ��©����======
	1��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ��������Ϊ����ϸ�

ֱ�ݼ��㹫ʽ�� ����С�������в��ϸ�����ͳ�ƣ���ͬ��С������ֻ��ʾһ�����ϸ��
	======ֱ�ݳ���======
	1������һ�ش󲻺ϸ�����Ϊ���ϸ�
	2��û���ش󲻺ϸ���� һ�㲻�ϸ��� ����18��ģ�ҲΪ���ϸ�
	3��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ���18���Ϊ����ϸ�
	======ֱ�ݸ��죨����2�Ρ�3�Ρ�4�Ρ�������ȥ��©����======
	1��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ���8���Ϊ����ϸ�
	
�������===���㷽���� ���ݼ���ϸ񲻺ϸ���ش����һ������� ������һ�ٷ֣�-�۳�����
	ֱ�ݣ�һ����һ�ο�2�֣��ش���һ�ο�10�֡�
	���ݣ�һ����һ�ο�5�֣��ش���һ�ο�20��
*/
function isQualified(elevatorType,checkNum){
	var table=document.getElementById("party_a");
	var len=table.rows.length-2;
	var zNum=0;
	var yNum=0;
	var cjfs=0;
	var strarr="";
	
	var firstnum=0;
	if(checkNum>1){
		//����
		for(var i=0;i<len;i++){
			if(document.getElementsByName("isRecheck")[i].value=="Y"){
				var isyzg=document.getElementsByName("isyzg")[i].value;//�Ƿ�������
				var type="";
				if(document.getElementsByName("examType")[i]!=null){
					type=document.getElementsByName("examType")[i].value;
				}
				//�ش󲻺ϸ���
				if(type=="ZD"){
					if(isyzg=="Y"){
						firstnum++;
					}else{
						zNum+=1;
					}
				}
				//һ�㲻�ϸ���
				if(type=="YB"){
					if(elevatorType=="T"){
						//ֱ�� ����С�������в��ϸ�����ͳ�ƣ���ͬ��С������ֻ��ʾһ�����ϸ��
						var itemgroup=document.getElementsByName("itemgroup")[i].value;
						if(itemgroup!="" && strarr.indexOf(itemgroup+",")>-1){
							
						}else{
							if(isyzg=="Y"){
								firstnum++;
							}else{
								strarr+=itemgroup+",";
								yNum+=1;
							}
						}
					}else{
						//����
						if(isyzg=="Y"){
							firstnum++;
						}else{
							yNum+=1;
						}
					}
				}
			}
			
		}
	}else{
		//����
		for(var i=0;i<len;i++){
			var isyzg=document.getElementsByName("isyzg")[i].value;//�Ƿ�������
			var type="";
			if(document.getElementsByName("examType")[i]!=null){
				type=document.getElementsByName("examType")[i].value;
			}
			//�ش󲻺ϸ���
			if(type=="ZD"){
				if(isyzg=="Y"){
					firstnum++;
				}else{
					zNum+=1;
				}
			}
			//һ�㲻�ϸ���
			if(type=="YB"){
				if(elevatorType=="T"){
					//ֱ�� ����С����룬��������ͳ��
					var itemgroup=document.getElementsByName("itemgroup")[i].value;
					if(itemgroup!="" && strarr.indexOf(itemgroup+",")>-1){
						
					}else{
						if(isyzg=="Y"){
							firstnum++;
						}else{
							strarr+=itemgroup+",";
							yNum+=1;
						}
					}
				}else{
					//����
					if(isyzg=="Y"){
						firstnum++;
					}else{
						yNum+=1;
					}
				}
			}
		}
        
	}
	if(checkNum>1){
		//����
		if(elevatorType=="T"){
			if(zNum>=1 || yNum>8){
				document.getElementById("factoryCheckResult").value="���ϸ�";
			}else{
				document.getElementById("factoryCheckResult").value="�ϸ�";
			}
			//ֱ�ݣ��ش���һ�ο�10��,һ����һ�ο�2�֡������첿����ˣ�2018-06-04 ��Ҫ�޸ĳ��������
			cjfs=(zNum*10)+(yNum*2);
			document.getElementById("r2").value=100-cjfs;
		}
		if(elevatorType=="F"){
			if(zNum>=1 || yNum>3){
				document.getElementById("factoryCheckResult").value="���ϸ�";
			}else{
				document.getElementById("factoryCheckResult").value="�ϸ�";
			}
			//���ݣ��ش���һ�ο�20��,һ����һ�ο�5�֡������첿����ˣ�2018-06-04 ��Ҫ�޸ĳ��������
			cjfs=(zNum*20)+(yNum*5);
			document.getElementById("r2").value=100-cjfs;
		}
	}else{
		//����
		if(elevatorType=="T"){
			if(zNum>=1 || yNum>18){
				document.getElementById("factoryCheckResult").value="���ϸ�";
			}else{
				document.getElementById("factoryCheckResult").value="�ϸ�";
			}
			//ֱ�ݣ��ش���һ�ο�10��,һ����һ�ο�2�֡������첿����ˣ�2018-06-04 ��Ҫ�޸ĳ��������
			cjfs=(zNum*10)+(yNum*2);
			document.getElementById("r2").value=100-cjfs;
		}
		if(elevatorType=="F"){
			if(zNum>=1 || yNum>8){
				document.getElementById("factoryCheckResult").value="���ϸ�";
			}else{
				document.getElementById("factoryCheckResult").value="�ϸ�";
			}
			//���ݣ��ش���һ�ο�20��,һ����һ�ο�5�֡������첿����ˣ�2018-06-04 ��Ҫ�޸ĳ��������
			cjfs=(zNum*20)+(yNum*5);
			document.getElementById("r2").value=100-cjfs;
		}
	}
	
	//��ֵ��������2
	var factoryCheckResult=document.getElementById("factoryCheckResult").value;
	var factoryCheckResult2=document.getElementById("factoryCheckResult2");
	factoryCheckResult2.value=factoryCheckResult;
	if(factoryCheckResult=="�ϸ�"){
		factoryCheckResult2.value="��ĺϸ�";
	}
	
}

//ɾ����
function deleteFileRow(thisobj){
	var tableObj = getFirstSpecificParentNode("table",thisobj);
	
	var inputs = tableObj.getElementsByTagName("input");
	var checkboxs = new Array(); //table������checkbox
	for(var i=0;i<inputs.length;i++){
	    if(inputs[i].type=="checkbox"){
	    	checkboxs.push(inputs[i]);	      	
	    }
	}
	
	checkboxs[0].checked = false;//ȫѡ��ťȡ��ѡ�� 
	
	//��table�д�������ɾ��ѡ�е���
	for(var i=checkboxs.length-1;i>0;i--){	
		if(checkboxs[i].checked == true){
		  tableObj.deleteRow(getFirstSpecificParentNode("tr",checkboxs[i]).rowIndex);
		}				
	}
}

var fileGird=0;
function AddRow(uploadtab_2,value,checkItem,examType,issueCoding){
	var upload=uploadtab_2.parentNode.parentNode.parentNode;
	var num=upload.rows.length;
	upload.insertRow(num);
	var ince0=upload.rows(num).insertCell(0);
	ince0.align="center";
	ince0.name="fileNo_"+fileGird
	ince0.innerHTML="<input type='checkbox' name='nodes' onclick=\'cancelCheckAll(this)\'>";
	var ince1=upload.rows(num).insertCell(1);
	ince1.innerHTML="<input id=\'file_"+fileGird+"\' name=\'"+fileGird+"\' type=\'file\' class=\'default_input\' />"
	+"<input type=\'hidden\' name=\'isdelete_"+fileGird+"\' value=\'"+value+"\' />"
	+"<input type=\'hidden\' name=\'item_"+fileGird+"\' value=\'"+checkItem+"\' />"
	+"<input type=\'hidden\' name=\'type_"+fileGird+"\' value=\'"+examType+"\' />"
	+"<input type=\'hidden\' name=\'coding_"+fileGird+"\' value=\'"+issueCoding+"\' />";
	
	fileGird++;
}

function AddRow2(uploadtab_2){
	var upload=uploadtab_2.parentNode.parentNode.parentNode;
	var num=upload.rows.length;
	upload.insertRow(num);
	var ince0=upload.rows(num).insertCell(0);
	ince0.align="center";
	ince0.name="fileNo_"+fileGird
	ince0.innerHTML="<input type='checkbox' name='nodes' onclick=\'cancelCheckAll(this)\'>";
	var ince1=upload.rows(num).insertCell(1);
	ince1.innerHTML="<input id=\'file_"+fileGird+"\' name=\'"+fileGird+"\' type=\'file\' size=\'50\' class=\'default_input\' />"
	+"<input type=\'hidden\' name=\'primary_"+fileGird+"\' value=\'Wbfileinfo\' />"
	+"<input type=\'hidden\' name=\'item_"+fileGird+"\' value=\'Wbfileinfo\' />"
	+"<input type=\'hidden\' name=\'type_"+fileGird+"\' value=\'Wbfileinfo\' />"
	+"<input type=\'hidden\' name=\'coding_"+fileGird+"\' value=\'Wbfileinfo\' />";
	
	fileGird++;
}
//�б�ȫѡ��ѡ
function checkTableFileAll(thisobj){
	var tableObj = getFirstSpecificParentNode("table",thisobj);
	var rows = tableObj.rows
	for(var i=1;i<rows.length;i++){
	  var inputs = rows[i].cells[0].getElementsByTagName("input");
	  for(j=0;j<inputs.length;j++){
	    if(inputs[j].type=="checkbox"){
	      inputs[j].checked = thisobj.checked;
	    }
	  }
	}
}

//ȫѡcheckboxȡ��ѡ��
function cancelCheckAll(thisobj){ 
  var tableObj = getFirstSpecificParentNode("table",thisobj);
  var inputs = tableObj.getElementsByTagName("input");  
  if(thisobj.checked==false){
    for(var j=0;j<inputs.length;j++){
	  if(inputs[j].type=="checkbox"){
	    inputs[j].checked = false; //table�е�һ��checkboxȡ��ѡ��
	    break;
	  }
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