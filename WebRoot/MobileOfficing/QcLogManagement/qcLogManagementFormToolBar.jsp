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
  window.location = '<html:rewrite page="/qcLogManagementSearchAction.do"/>?method=toSearchRecord';
}


//����
function saveMethod(){
	if(checkform()){
		document.qcLogManagementForm.isreturn.value = "N";
    	document.qcLogManagementForm.submit();
	}
	
}

//���淵��
function saveReturnMethod(){
	if(checkform()){
		document.qcLogManagementForm.isreturn.value = "Y";
    	document.qcLogManagementForm.submit();
	}
}

//У��
function checkform(){
	var maintStation = document.getElementById("maintStation");//ά��վ
	var MaintPersonnel = document.getElementById("MaintPersonnel");//ά����
	var ElevatorNo = document.getElementById("ElevatorNo");//���ݱ��
	var ydlh = document.getElementById("ydlh");//�¶�����
	var isgzfz = document.getElementById("isgzfz");//ά���������Ƿ�Թ�������
	var iszfwt = document.getElementById("iszfwt");//ά���������Ƿ������������
	var jffkwt = document.getElementById("jffkwt");//�׷���������
	var ycjkwt = document.getElementById("ycjkwt");//Զ�̼������
	
	if(maintStation.value == ""){
		alert("��ѡ��ά��վ��");
		return false;
	}
	if(MaintPersonnel.innerHTML==""){
		alert("��ѡ��ά������");
		return false;
	}
	if(ElevatorNo.value==""){
		alert("���ݱ�Ų���Ϊ�գ�");
		return false;
	}
	if(ydlh.value==""){
		alert("��ѡ���¶����ᣡ");
		return false;
	}
	if(isgzfz.value==""){
		alert("��ѡ��ά���������Ƿ�Թ�������");
		return false;
	}
	if(iszfwt.value==""){
		alert("��ѡ��ά���������Ƿ�����������⣡");
		return false;
	}
	if(jffkwt.innerHTML==""){
		alert("�׷��������ⲻ��Ϊ�գ�");
		return false;
	}
	if(ycjkwt.innerHTML==""){
		alert("Զ�̼�����ⲻ��Ϊ�գ�");
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