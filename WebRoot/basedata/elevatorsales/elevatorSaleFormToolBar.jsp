<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);
  <logic:notPresent name="isOpen">
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
 </logic:notPresent>
 
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nelevatorSale" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  
  <logic:present name="isOpen">
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/close.gif","","",'�ر�',"65","0","window.close();");
  </logic:present>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/elevatorSaleSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
  document.elevatorSaleForm.isreturn.value = "N";
  document.elevatorSaleForm.submit();
}

//���淵��
function saveReturnMethod(){
  document.elevatorSaleForm.isreturn.value = "Y";
  document.elevatorSaleForm.submit();
}

//ɾ��
var tbs;
function deleteFile(tablename,elevatorNo,value,td){
	  tbs=td;
	    if(confirm("ȷ��ɾ����")){
	    	tbs.parentElement.style.display='';
			var uri = '<html:rewrite page="/elevatorSaleAction.do"/>?method=toDeleteFileRecord';
			uri +='&filesid='+ value;
			uri +='&folder=ElevatorArchivesInfo.file.upload.folder';
			uri +='&tablename='+tablename;
			uri +='&elevatorNo='+elevatorNo;
			sendRequestDelFile(uri);  	
			//partitionGrade();
		}
	}

//���ظ���
function downloadFile(name,tablename){
	var name1=encodeURI(name);
	name1=encodeURI(name1);
	var tablename1=encodeURI(tablename);
	tablename1=encodeURI(tablename1);
	var uri = '<html:rewrite page="/elevatorSaleAction.do"/>?method=toDownloadFileRecord';
		uri +='&filesname='+ name1;
		uri +='&folder=ElevatorArchivesInfo.file.upload.folder';
		uri +='&tablename='+tablename1;
	window.location = uri;
	//window.open(url);
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