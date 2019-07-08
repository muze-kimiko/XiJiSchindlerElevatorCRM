<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript" src="<html:rewrite page="/common/javascript/openwindow.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>
<script language="javascript" src="<html:rewrite page="/common/javascript/dynamictable.js"/>"></script>
<script language="javascript" defer="defer" src="<html:rewrite forward='DatePickerJS'/>"></script>
<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nqualitycheckregistration" value="Y"> 
  AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    AddToolBarItemEx("SaveSubmitBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="tollbar.saveandrefer"/>',"120","2","saveSubmitMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/qualityCheckManagementSearchAction.do"/>?method=toSearchRecordRegistration';
}

//����
function saveMethod(){
// alert(rowsToJSONArray("mtc",""));
	//alert(detailJson());
	
	if(verify()){
		document.qualityCheckManagementForm.Scores.value=rowsToJSONArray("mtc","scorce");
		document.qualityCheckManagementForm.details.value=detailJson();
		document.qualityCheckManagementForm.isreturn.value = "N";
		document.qualityCheckManagementForm.isdispose.value="1";
		document.qualityCheckManagementForm.submit();	
	}
	
}

//�����ύ
function saveSubmitMethod(){
	if(verify()){
		if(confirm("�Ƿ�ȷ���ύ���ݣ��ύ�󽫲��ܶԸ�������޸ģ�")){
			document.qualityCheckManagementForm.Scores.value=rowsToJSONArray("mtc","scorce");
			document.qualityCheckManagementForm.details.value=detailJson();
			document.qualityCheckManagementForm.isreturn.value = "Y";
			document.qualityCheckManagementForm.isdispose.value="2";
			document.qualityCheckManagementForm.submit();
		}
	}
}

function verify(){
	var elevatorNo=document.getElementById("elevatorNo");
	if(elevatorNo.value==""){
		alert("���ݱ�Ų���Ϊ�գ�");
		return false;
	}
	var mtc=document.getElementById("mtc");

	var vag="";
	if(mtc.rows.length<=2){
		vag+="�����ά���������۷��\n";
	}

	
	if(vag!=""){
		alert(vag);
		return false;
	}else{
		return true;
	}
}


//�������� �ܵ÷֣��÷ֵȼ�
/** 
  	�ܵ÷�=100-��ֵ�ĺϼƣ�
  	--�÷ֵȼ�=�����ܵ÷ֽ����жϣ�90���������㣬80��-90�ֺϸ�80�����²��ϸ�
  	�÷ֵȼ�=80-84��Ϊ�ϸ�85-89Ϊ���ã� 90����Ϊ���� ��2017-05-19�޸ġ�
*/
function partitionGrade(){
	var totalPoints=document.getElementsByName("totalPoints")[0];
	var total=parseFloat(totalPoints.value);
	var leve="";
	if(total>=90){
		leve="����";
	}else if(total>=85 && total<90){
		leve="����";
	}else if(total>=80 && total<85){
		leve="�ϸ�";
	}else{
		leve="���ϸ�";
	}

	document.getElementsByName("scoreLevel")[0].value=leve;
}

function isQualified(){
	var fractions=document.getElementsByName("fraction");
	var total=0.0;
	if(fractions!=null && fractions.length>0){
		for(var i=0;i<fractions.length;i++){
			total+=parseFloat(fractions[i].value);
		}
	}
	document.getElementsByName("totalPoints")[0].value=100.0-total;
}

function emptyFile(obj){
	var index=obj.id;
	var file=document.getElementById("file_"+index);
	file.outerHTML=file.outerHTML;
	partitionGrade();
}
var detail=0;
function addOneRow(tableName,array,k){
	var adjt_w=document.getElementById(tableName);
	/* var adjt=thisobj.parentElement.parentElement.parentElement;
	alert(adjt[1]); */
	var num=adjt_w.rows.length;
	var len=document.getElementById("mtc").rows.length-2;
	var values=new Array();
	for(var i=0;i<array.length;i++){
		values[i]=array[i].split("=");
	}
	
	adjt_w.insertRow(num);
	var ince0=adjt_w.rows(num).insertCell(0);
	ince0.align="center";
	ince0.innerHTML="<input type=\'checkbox\' onclick=\'cancelCheckAll(this)\'>";
	var ince1=adjt_w.rows(num).insertCell(1);
	
	ince1.innerHTML=values[1][1]+"<input type=\'hidden\' name=\'"+values[0][0]+"\' value=\'"+values[0][1]+"\'/>"
	+"<input type=\'hidden\' name=\'"+values[1][0]+"\' value=\'"+values[1][1]+"\'/>"
	+"<input type=\'hidden\' name=\'"+values[2][0]+"\' value=\'"+values[2][1]+"\'/>";
	if(tableName!="mtc"){
		var inced=adjt_w.rows(num).insertCell(2);
		inced.style.display="none";
	}
	/* for(var i=0;i<values.length;i++){
		if(i>0){
			var ince1=adjt_w.rows(num).insertCell(i);
			if(i==3){
				ince1.align="center";
			}
			ince1.innerHTML=values[i][1]+"<input type=\'hidden\' name=\'"+values[i][0]+"\' value=\'"+values[i][1]+"\' />";
		}
	} */
	//adjt_w.rows(num).cells(1).innerHTML+="<input type=\'hidden\' name=\'"+values[0][0]+"\' value=\'"+values[0][1]+"\' />";
	var td="&nbsp;&nbsp;<input type='button' name='toaddrow' value=' + ' onclick=\'AddRow(this,\""+values[0][1]+"\")\'/>";
	if(tableName=="mtc"){
		var ince2=adjt_w.rows(num).insertCell(2);
		ince2.valign="top";
		ince2.innerHTML="<table width='100%' class='tb' id='msrd"+detail+"'>"+
		"<thead><tr class='wordtd'><td width='5%' align='center'><input type='checkbox' onclick='checkTableAll(this)'/></td>"+
		"<td align='left'>&nbsp;<input type='button' value=' + ' onclick='addElevators(\"searchMarkingScoreRegisterDetailAction\",this,\"msrd"+detail+"\",\"detailId\",\""+values[0][1]+"\")'/>"+
		"&nbsp;<input type='button' value=' - ' onclick='deleteRow(this)'></td>"+
		"</tr></thead><tbody></tbody>"+
		"</table><br>";
		detail++;
		
		var ince3=adjt_w.rows(num).insertCell(3);
		ince3.innerHTML="<textarea name=\'rem\' rows=\'3\' cols=\'20\'></textarea>";
		
		var ince4=adjt_w.rows(num).insertCell(4);
		ince4.innerHTML="<table width='100%' class='tb'>"
		+"<tr class='wordtd'><td width='5%' align='center'><input type='checkbox' onclick='checkTableAll(this)'></td>"
		+"<td align='left'>"+td
		+"&nbsp;<input type='button' name='todelrow' value=' - ' onclick='deleteRow(this)'>"
		+"</td></tr>"
		+"</table><br>";
	}
}

function detailJson(){
	var details="";
	var dn=0;
	for(var i=0;i<detail;i++){
		if(document.getElementById("msrd"+i)){
			details+=i==detail-1 ? rowsToJSONArray("msrd"+i,"detail"+dn) : rowsToJSONArray("msrd"+i,"detail"+dn)+"|";
			dn++;
		}
	}
	return details;
}

var fileGird=0;
function AddRow(uploadtab_2,value){
	var upload=uploadtab_2.parentNode.parentNode.parentNode;
	var num=upload.rows.length;
	upload.insertRow(num);
	var ince0=upload.rows(num).insertCell(0);
	ince0.align="center";
	ince0.innerHTML="<input type='checkbox' name='nodes' onclick=\'cancelCheckAll(this)\'>";
	var ince1=upload.rows(num).insertCell(1);
	ince1.innerHTML="<input id=\'file_"+fileGird+"\' name=\'"+fileGird+"\' type=\'file\' class=\'default_input\' />"
	+"<input type=\'hidden\' name=\'primary_"+fileGird+"\' value=\'"+value+"\' />";
	fileGird++;
}

/* //����һ��
function addOneRow(thisobj,modelrow) {
	var tableObj = getFirstSpecificParentNode("table",thisobj);
	tableObj.getElementsByTagName("tbody")[0].appendChild(modelrow.cloneNode(true));
	cancelCheckAll(thisobj);	
}*/
      
//ɾ����
function deleteRow(thisobj){
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
	isQualified();
	partitionGrade();
}

//�б�ȫѡ��ѡ
function checkTableAll(thisobj){
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

//���ά���ʼ���
function addElevators(action,thisobj,tableName,qId,msId){
		var tableObj = getFirstSpecificParentNode("table",thisobj);
		
		var elevatorType=document.getElementById("elevatorType").value;
		var paramstring = "elevatorType="+elevatorType+"&QualityNos=";		
		var elevatorNos = document.getElementsByName(qId);
		for(i=0;i<elevatorNos.length;i++){
			paramstring += i<elevatorNos.length-1 ? "|"+elevatorNos[i].value+"|," : "|"+elevatorNos[i].value+"|"		
		}
		paramstring+="&msId="+msId;
		paramstring=encodeURI(paramstring);
		paramstring=encodeURI(paramstring);
		//alert(paramstring);

		var returnarray = openWindowWithParams(action,"toSearchRecord",paramstring);

		if(returnarray!=null && returnarray.length>0){
						
			for(var i=0;i<returnarray.length;i++){
				addOneRow(tableName,returnarray[i],i);
			}
		}
		isQualified();	
		partitionGrade();
}

//����Ԫ��ָ���ĸ��ڵ����
function getFirstSpecificParentNode(parentTagName,childObj){
	var parentObj = childObj.parentNode;
	
	while(parentObj){
		if(parentObj.nodeName.toLowerCase() == parentTagName.toLowerCase()){				
			break;
		}
		parentObj = parentObj.parentNode;
	}
	
	return parentObj;
}

//----------------------------AJAX-------------------------------------------------

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
          		tbs.parentElement.parentElement.removeChild(tbs.parentElement);
          		partitionGrade();
          	}else{
          		alert("ɾ��ʧ�ܣ�");
          	}
          	
          	//alert(document.getElementById("messagestring").innerHTML+";123");
       	} else { //ҳ�治����
             window.alert("���������ҳ�����쳣��");
       	}
     }
  }

  //ɾ��
  var tbs;
  function deleteFile(td,primary){
	  tbs=td;
	  primary=encodeURI(primary);
	  primary=encodeURI(primary);
	    if(confirm("ȷ��ɾ����")){
	    	tbs.parentElement.parentElement.style.display='';
			var uri = '<html:rewrite page="/qualityCheckManagementAction.do"/>?method=toDeleteFileRegistration';
			uri +='&filesid='+ primary;
			uri +='&folder=MTSComply.file.upload.folder';
			sendRequestDelFile(uri);  	
			partitionGrade();
		}
	}

//���ظ���
function downloadFile(name,oldName){
	var uri = '<html:rewrite page="/qualityCheckManagementSearchAction.do"/>?method=toDownloadFileRegistration';
	var name1=encodeURI(name);
	name1=encodeURI(name1);
	oledName=encodeURI(oldName);
	oledName=encodeURI(oldName);
		uri +='&filesname='+ name1;
		uri +='&folder=MTSComply.file.upload.folder';
		uri+='&fileOldName='+oldName;
	window.location = uri;
	//window.open(url);
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