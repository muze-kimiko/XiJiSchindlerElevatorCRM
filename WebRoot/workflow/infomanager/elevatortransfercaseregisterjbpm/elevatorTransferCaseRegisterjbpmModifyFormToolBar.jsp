<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript" charset="GBK">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

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
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

//���ش����б�
function returnTaskMethod(){
	window.location ='<html:rewrite page="/myTaskOaSearchAction.do"/>?method=toDoList&jumpto=templetdoorapp';
}


//�ύ
function saveMethod(){
	var propv=document.getElementById("projectProvince").value
	if(propv.trim()==""){
		alert("��Ŀʡ�� ����Ϊ�գ�");
		return;
	}
	
	isQualified('${elevatorTransferCaseRegisterBean.elevatorType}',${elevatorTransferCaseRegisterBean.checkNum});
	if(check()){
		document.elevatorTransferCaseRegisterJbpmForm.hecirs.value=rowsToJSONArray("important","hecis");
	    document.elevatorTransferCaseRegisterJbpmForm.processStatus.value = "1";
		document.elevatorTransferCaseRegisterJbpmForm.submit();
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


/**
���ݼ��㹫ʽ��
	======���ݳ���======
	1������һ�ش󲻺ϸ�����Ϊ���ϸ�
	2��û���ش󲻺ϸ����һ�㲻�ϸ��� ���ڰ���ģ�ҲΪ���ϸ�
	3��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ��ڰ����Ϊ����ϸ�
	======���ݸ��죨����2�Ρ�3�Ρ�4�Ρ�������======
	1��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ��������Ϊ����ϸ�

ֱ�ݼ��㹫ʽ�� ����С�������в��ϸ�����ͳ�ƣ���ͬ��С������ֻ��ʾһ�����ϸ��
	======ֱ�ݳ���======
	1������һ�ش󲻺ϸ�����Ϊ���ϸ�
	2��û���ش󲻺ϸ���� һ�㲻�ϸ��� ����18��ģ�ҲΪ���ϸ�
	3��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ���18���Ϊ����ϸ�
	======ֱ�ݸ��죨����2�Ρ�3�Ρ�4�Ρ�������======
	1��û���ش󲻺ϸ����һ�㲻�ϸ���С�ڵ���8���Ϊ����ϸ�
	
�������===���㷽���� ���ݼ���ϸ񲻺ϸ���ش����һ������� ������һ�ٷ֣�-�۳�����
	ֱ�ݣ�һ����һ�ο�2�֣��ش���һ�ο�10�֡�
	���ݣ�һ����һ�ο�5�֣��ش���һ�ο�20��
*/
function isQualified(elevatorType,checkNum){

	var table=document.getElementById("important");
	var len=table.rows.length-1;
	var zNum=0;
	var yNum=0;
	var cjfs=0;
	var strarr="";
	
	var firstnum=0;
	var firstInstallation=document.getElementById("firstInstallation").value;//�Ƿ���ΰ�װ
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
					if(firstInstallation=="Y" && isyzg=="Y"){
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
							if(firstInstallation=="Y" && isyzg=="Y"){
								firstnum++;
							}else{
								strarr+=itemgroup+",";
								yNum+=1;
							}
						}
					}else{
						//����
						if(firstInstallation=="Y" && isyzg=="Y"){
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
				if(firstInstallation=="Y" && isyzg=="Y"){
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
						if(firstInstallation=="Y" && isyzg=="Y"){
							firstnum++;
						}else{
							strarr+=itemgroup+",";
							yNum+=1;
						}
					}
				}else{
					//����
					if(firstInstallation=="Y" && isyzg=="Y"){
						firstnum++;
					}else{
						yNum+=1;
					}
				}
			}
			
		}
	}
	//alert("�ش�="+zNum+"  һ��="+yNum);
	if(checkNum>1){
		//����
		if(elevatorType=="T"){
			if(zNum>=1 || yNum>8){
				document.getElementById("factoryCheckResult").value="���ϸ�";
			}else{
				document.getElementById("factoryCheckResult").value="�ϸ�";
			}
			//ֱ�ݣ��ش���һ�ο�10��,һ����һ�ο�2�֡�
			cjfs=(zNum*10)+(yNum*2);
			document.getElementById("r2").value=100-cjfs;
		}
		if(elevatorType=="F"){
			if(zNum>=1 || yNum>3){
				document.getElementById("factoryCheckResult").value="���ϸ�";
			}else{
				document.getElementById("factoryCheckResult").value="�ϸ�";
			}
			//���ݣ��ش���һ�ο�20��,һ����һ�ο�5�֡�
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
			//ֱ�ݣ��ش���һ�ο�10��,һ����һ�ο�2�֡�
			cjfs=(zNum*10)+(yNum*2);
			document.getElementById("r2").value=100-cjfs;
		}
		if(elevatorType=="F"){
			if(zNum>=1 || yNum>8){
				document.getElementById("factoryCheckResult").value="���ϸ�";
			}else{
				document.getElementById("factoryCheckResult").value="�ϸ�";
			}
			//���ݣ��ش���һ�ο�20��,һ����һ�ο�5�֡�
			cjfs=(zNum*20)+(yNum*5);
			document.getElementById("r2").value=100-cjfs;
		}
	}
	
	//��ֵ��������2
	var factoryCheckResult=document.getElementById("factoryCheckResult").value;
	var factoryCheckResult2=document.getElementById("factoryCheckResult2");
	
	if(firstnum>0 && factoryCheckResult=="�ϸ�"){
		factoryCheckResult2.value="����Ա��ĺϸ�";
	}else{
		factoryCheckResult2.value=factoryCheckResult;
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

//ɾ��
var tbs;
function deleteFile(td,id,filePath){
	  tbs=td;
	    if(confirm("ȷ��ɾ����")){
	    	tbs.parentElement.parentElement.style.display='';
			var uri = '<html:rewrite page="/elevatorTransferCaseRegisterJbpmAction.do"/>?method=toDeleteFileDispose';
			uri +='&filesid='+ id;
			uri +='&folder=HandoverElevatorCheckItemRegister.file.upload.folder';
			sendRequestDelFile(uri);  	
		}
	}

//���ظ���
function downloadFile(name,oldName,filePath){
	var uri = '<html:rewrite page="/elevatorTransferCaseRegisterAction.do"/>?method=toDownloadFileDispose';
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

function emptyFile(obj){
	var index=obj.id;
	var file=document.getElementById("file_"+index);
	file.outerHTML=file.outerHTML;
	
}

//��ӽ��ӵ��ݼ����Ŀ
function addElevators(tableId,k,elevatorType,iscopy){
	  var paramstring3="";
	  var issueCodings=document.getElementsByName("issueCoding");
	  for(i=0;i<issueCodings.length;i++){
	    paramstring3+=i<issueCodings.length-1? issueCodings[i].value+"|" : issueCodings[i].value;
	  }
	  if(paramstring3==""){
		  paramstring3="issueCodings=,";
	  }else{
	  	paramstring3="issueCodings="+encodeURI(paramstring3);
	  }
	  var paramstring=paramstring3+"&elevatorType="+elevatorType;
	  var returnarray =null;

	  if(iscopy!="Y"){
	  	returnarray = openWindowWithParams("searchHandoverElevatorCheckItemAction","toSearchRecord",paramstring);//�����򲢷���ֵ
	  }else{
	  	returnarray= openWindowWithParams("searchHandoverElevatorCheckItemAction","toSearchRecord2","elevatorType="+elevatorType);
	  }

  if(returnarray!=null && returnarray.length>0){          
    /* addRows(tableId,returnarray.length);//������
    toSetInputValue3(returnarray,"elevatorTransferCaseRegisterForm",flag);//��ҳ���Ӧ�����ֵ */
    if(iscopy=="Y"){
    	var table=document.getElementById("important");
		var l=table.rows.length;
		for(var i=l-1;i>0;i--){
			table.deleteRow(i);
		}
    }
    for(var j=0;j<returnarray.length;j++){
    	AddRow_WD(returnarray[j],k);
    }
  }
  isQualified('${elevatorTransferCaseRegisterBean.elevatorType}',${elevatorTransferCaseRegisterBean.checkNum});
}

function AddRow_WD(array,k){
	var adjtb_w=document.getElementById("important");
	var num=adjtb_w.rows.length;
	var values=new Array();
	var len=parseInt(document.getElementById("file_length").value);
	var n=0;
	if(len==0 || num>=len){
		n=num-1;
	}else{
		n=len+1;
	}

	adjtb_w.insertRow(num);
	for(var i=0;i<array.length;i++){
		values[i]=array[i].split("=");
	}
	var checkNum=document.getElementById("checkNum").value;
	var ince0=adjtb_w.rows(num).insertCell(0);
	ince0.align="center";
	ince0.innerHTML="<input type=\'checkbox\' onclick=\'cancelCheckAll('important','cbAll')\'><input type=\'hidden\' name=\'jnlno\'/>";
	var ince1=adjtb_w.rows(num).insertCell(1);
	var ince1Str=values[1][1];
	if(checkNum>1){
		ince1Str="<font style=\"color: red;\">"+values[1][1]+"</font>"
	}
	ince1.innerHTML=ince1Str+"<input type=\'hidden\' name=\'itemgroup\' value=\'"+values[5][1]+"\' /><input type=\'hidden\' name=\'examType\' value=\'"+values[0][1]+"\' /><input type=\'hidden\' name=\'isRecheck\' value=\'N\' />";
	var ince2=adjtb_w.rows(num).insertCell(2);
	//ince2.align="center";
	ince2.innerHTML=values[2][1]+"<input type=\'hidden\' name=\'checkItem\' value=\'"+values[2][1]+"\' />";
	var ince3=adjtb_w.rows(num).insertCell(3);
	ince3.align="center";
	ince3.innerHTML=values[3][1]+"<input type=\'hidden\' name=\'issueCoding\' value=\'"+values[3][1]+"\' />";
	var ince4=adjtb_w.rows(num).insertCell(4);
	ince4.innerHTML=values[4][1]+"<input type=\'hidden\' name=\'issueContents\' value=\'"+values[4][1]+"\' />";
	var ince5=adjtb_w.rows(num).insertCell(5);
	if(values.length>6){
		ince5.innerHTML="<textarea name=\'rem\' cols=\'20\' rows=\'2\' value=\'"+values[6][1]+"\' >"+values[6][1]+"</textarea>"
	}else{
		ince5.innerHTML="<textarea name=\'rem\' cols=\'20\' rows=\'2\' ></textarea>";
	}
	//ince5.innerHTML="<input type=\'text\' name=\'issueContents\' class='default_input' />";
	var ince6=adjtb_w.rows(num).insertCell(6);
	var td="&nbsp;&nbsp;<input type='button' name='toaddrow' value='+' onclick=\'AddRow(this,\"N\",\""+values[2][1]+"\",\""+values[0][1]+"\",\""+values[3][1]+"\")\'/>";
	ince6.innerHTML="<table width='100%' class='tb'>"
	+"<tr class='wordtd'><td width='5%' align='center'><input type='checkbox' onclick='checkTableFileAll(this)'></td>"
	+"<td align='left'>"+td
	+"&nbsp;&nbsp;<input type='button' name='todelrow' value='-' onclick='deleteFileRow(this)'>"
	+"</td></tr>"
	+"</table><br>";
	
	//�Ƿ�������
	var firstInstallation=document.getElementById("firstInstallation").value;
	var ince7=adjtb_w.rows(num).insertCell(7);
	ince7.align="center";
	if(firstInstallation=="Y"){
		ince7.innerHTML="<select name=\'isyzg\' id=\'isyzg\' onchange=\'isQualified(\"${elevatorTransferCaseRegisterBean.elevatorType}\",${elevatorTransferCaseRegisterBean.checkNum});\'>"+
							"<option value=\"\" >��ѡ��</option>"+
							"<option value=\'Y\' >������</option>"+
						"</select>";
	}else{
		ince7.innerHTML="<input type=\'hidden\' name=\'isyzg\' value=\"\" />";
	}
}
function isAddition(tableId){
	var table=document.getElementById(tableId);
	var num=table.rows.length-1;
	var msg="";
	if(num<=0){
		msg="����Ӽ��������⣡";
	}
	if(msg!=""){
		alert(msg);
		return false;
	}
	return true;
}

function check(){
	if(checkColumnInput(elevatorTransferCaseRegisterJbpmForm) && isAddition("important")){
		return true;
	}
	return false;
}


//-------------------------------------Ajax---------------------------------------

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
function isOkCheck(){
	var isCheck=document.getElementsByName("isCheck");
	var isOk=document.getElementsByName("isOk");
	for(var i=0;i<isCheck.length;i++){
		if(isCheck[i].checked == true){
			isOk[i].value="Y";
		}else{
			isOk[i].value="N";
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