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
  <logic:equal value="true" name="addflag">
  AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/save_back.gif","","",'���沢����',"90","1","saveReturnMethod()");
  </logic:equal>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/etcLogManagementSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
	if(checkform()){
		document.etcLogManagementForm.isreturn.value = "N";
    	document.etcLogManagementForm.submit();
	}
	
}

//���淵��
function saveReturnMethod(){
	if(checkform()){
		document.etcLogManagementForm.isreturn.value = "Y";
    	document.etcLogManagementForm.submit();
	}
}

//У��
function checkform(){
	var r = /^\+?[1-9][0-9]*$/;//������
	
	var contractno = document.getElementById("contractno");//��ͬ��
	var projectname = document.getElementById("projectname");//��Ŀ����
	var inscompanyname = document.getElementById("inscompanyname");//��װ��λ
	var phnum = document.getElementById("phnum");//�������
	var iscjwx = document.getElementById("iscjwx");//����/ά��
	var iszj = document.getElementById("iszj");//�Ƿ��Լ�
	var xcfkwt = document.getElementById("xcfkwt");//�ֳ���������
	var workcontent = document.getElementById("workcontent");//��������������
	
	if(contractno.value==""){
		alert("��ͬ�Ų���Ϊ�գ�");
		return false;
	}
	if(projectname.value==""){
		alert("��Ŀ���Ʋ���Ϊ�գ�");
		return false;
	}
	if(inscompanyname.innerHTML==""){
		alert("��װ��λ����Ϊ�գ�");
		return false;
	}
	if(!r.test(phnum.value)){
		alert("����������������֣�");
		return false;
	}
	if(iscjwx.value==""){
		alert("����/ά�޲���Ϊ�գ�");
		return false;
	}
	if(iszj.value==""){
		alert("��ѡ���Ƿ��Լ죡");
		return false;
	}
	if(xcfkwt.innerHTML==""){
		alert("�ֳ��������ⲻ��Ϊ�գ�");
		return false;
	}
	if(workcontent.innerHTML==""){
		alert("�������������ܲ���Ϊ�գ�");
		return false;
	}
	
	return true;
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