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

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()"); 
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save.gif","","",'�� ��',"65","0","saveMethod()"); 
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save_back.gif","","",'���沢����',"90","0","saveReturnMethod()"); 
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/${action}.do"/>?method=toPrepareUpdateRecord&id=${id}';
}

//����
function saveMethod(){
	var newfile=document.getElementById("newfile").value;
	var oldNo=document.getElementById("oldNo").value
	if(oldNo==""||oldNo==null){
		alert("������ɼ����!!");
		return;
	}
	if(newfile==""||newfile==null){
		alert("���ϴ��ɼ�ͼƬ!!");
		return;
	}
	
	document.getElementById("isreturn").value = "N";
    document.getElementById("Addform").submit();
}

//���淵��
function saveReturnMethod(){
	var newfile=document.getElementById("newfile").value;
	var oldNo=document.getElementById("oldNo").value
	if(oldNo==""||oldNo==null){
		alert("������ɼ����!!");
		return;
	}
	if(newfile==""||newfile==null){
		alert("���ϴ��ɼ�ͼƬ!!");
		return;
	}
	document.getElementById("isreturn").value = "Y";
    document.getElementById("Addform").submit();
}

//����
function downloadFile(id){
	var uri = '<html:rewrite page="/accessoriesRequisitionAction.do"/>?method=toDownLoadFiles';
		uri +='&filename='+ id;
		uri +='&folder=AccessoriesRequisition.file.upload.folder';
	    
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
            </script>
            </div>
          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>