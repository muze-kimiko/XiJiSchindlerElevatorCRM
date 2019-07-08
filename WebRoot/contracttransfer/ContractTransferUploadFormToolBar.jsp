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
  
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nContractTransferUpload" value="Y">
  	<logic:equal name="outflag" value="Y">
  	AddToolBarItemEx("OutBtn","../../common/images/toolbar/save.gif","","",'����',"65","1","outMethod()");
  	</logic:equal>
  	<logic:equal name="tranflag" value="Y">
  	AddToolBarItemEx("SaveDateBtn","../../common/images/toolbar/save.gif","","",'ת��',"65","1","tranMethod()");
  	</logic:equal>
  	<logic:equal name="addflag" value="Y">
  		<logic:notEqual name="isflag" value="N">
  		AddToolBarItemEx("SaveDateBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveDateMethod()");
  		</logic:notEqual>
  	AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'���沢�ύ',"100","1","saveReturnMethod()");
  	</logic:equal>
  	<logic:equal name="feedbackflag" value="Y">
  	AddToolBarItemEx("SaveDateBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","feedbackMethod()");
  	</logic:equal>
  	
  	<logic:equal name="mainenter" value="Y">
  	AddToolBarItemEx("ReferBtn","../../common/images/toolbar/edit.gif","","",'����',"65","1","feedbackMethod2()");
  	</logic:equal>
  	
  </logic:equal>
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//����
function returnMethod(){
  window.location = '<html:rewrite page="/ContractTransferUploadSearchAction.do"/>?method=toSearchRecord';
}

//����
function outMethod(){
	var transferRem = document.getElementsByName('transferRem');
	
	if(transferRem[0].value==''){
		alert("����д���������");
	}else{
		document.contractTransferUploadForm.submit();
	}
}

//ת��
function tranMethod(){
	var wbgTransfeId = document.getElementsByName('wbgTransfeId');
	
	if(wbgTransfeId[0].value==''){
		alert("��ѡ��ת��ά������");
	}else{
		document.contractTransferUploadForm.submit();
	}
}


//����
function feedbackMethod(){
	var transferRem = document.getElementsByName('transferRem');
	var feedbacktypeid = document.getElementsByName('feedbacktypeid');
	
	if(feedbacktypeid[0].value==''){
		alert("��ѡ�����������ͣ�");
	}else if(transferRem[0].value==''){
		alert("����д�������ݣ�");
	}else{
		document.contractTransferUploadForm.submit();
	}
}

//����
function feedbackMethod2(){
	var billNo = document.getElementsByName('billNo');
	window.location = '<html:rewrite page="/ContractTransferUploadAction.do"/>?id='+billNo[0].value+'&method=toPrepareFeedBackRecord';
}

//����
function saveDateMethod(){
    document.contractTransferUploadForm.submit();
}

//�����ύ
function saveReturnMethod(){
	if(checkfile()){
		document.contractTransferUploadForm.transfeSubmitType.value="Y";
		document.contractTransferUploadForm.submit();
	}else{
		alert("���ϴ�ȫ���������ύ��");
	}
}

function checkfile(){
	var jnlno = document.getElementsByName("jnlno");
	var checkflag = true;
	
	for(var i=0;i<jnlno.length;i++){
		var file = document.getElementsByName("file"+jnlno[i].value);
		
		if(file.length==0){
			var fileid = document.getElementsByName("jnlno_"+jnlno[i].value);
			if(fileid.length==0){
				checkflag = false;
				return false;
			}
			for(var j=0;j<fileid.length;j++){
				var file = document.getElementById(fileid[j].value);
				if(file == null || file.value == ""){
					checkflag = false;
					return false;
				}
			}
			if(!checkflag){
				return false;
			}
		}
	}
	
	return checkflag;
}

var fileGird=0;
function AddRow(uploadtab_2,jnlno){
	var upload=uploadtab_2.parentNode.parentNode.parentNode;
	var num=upload.rows.length;
	upload.insertRow(num);
	var ince0=upload.rows(num).insertCell(0);
	ince0.align="center";
	ince0.name="fileNo_"+jnlno+"_"+fileGird
	ince0.innerHTML="<input type='checkbox' name='nodes' onclick=\'cancelCheckAll(this)\'>";
	var ince1=upload.rows(num).insertCell(1);
	ince1.innerHTML="<input id=\'file_"+jnlno+"_"+fileGird+"\' name=\'"+jnlno+"_"+fileGird+"\' type=\'file\' class=\'default_input\' style=\'width:390px;\' />"
	+"<input type=\'hidden\' name=\'jnlno_"+jnlno+"\' value=\'file_"+jnlno+"_"+fileGird+"\' />";
	
	fileGird++;
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

//���ظ���
function downloadFile(name,oldName,filePath,folder){
	var uri = '<html:rewrite page="/ContractTransferUploadAction.do"/>?method=toDownloadFile';
	var name1=encodeURI(name);
	name1=encodeURI(name1);
	var oldName1=encodeURI(oldName);
	oldName1=encodeURI(oldName1);
	filePath=encodeURI(filePath);
	filePath=encodeURI(filePath);
	    uri +='&filePath='+ filePath;
		uri +='&filesname='+ name1;
		uri +='&folder='+folder;
		uri+='&fileOldName='+oldName1;
	window.location = uri;
	//window.open(url);
}

//ɾ������
var tbs;
function deleteFile(td,id,filePath){
	tbs=td;
    if(confirm("ȷ��ɾ����")){
    	tbs.parentElement.parentElement.style.display='';
		var uri = '<html:rewrite page="/ContractTransferUploadAction.do"/>?method=toDeleteFile';
		filePath=encodeURI(filePath);
		filePath=encodeURI(filePath);
		uri +='&filePath='+ filePath;
		uri +='&filesid='+ id;
		uri +='&folder=ContractTransferFileinfo.file.upload.folder';
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
          	
          	if(res=="Y"){
          		tbs.parentElement.parentElement.parentElement.removeChild(tbs.parentElement.parentElement);
          	}else{
          		alert("ɾ��ʧ�ܣ�");
          	}
          	
          	//alert(document.getElementById("messagestring").innerHTML+";123");
       	} else { //ҳ�治����
             window.alert("���������ҳ�����쳣��");
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