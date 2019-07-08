<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/jquery-1.9.1.min.js"/>"></script>
<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">
<%--  �жϿͻ�����ģ��Area�Ƿ��п�д��Ȩ��,��property	--%>
<logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nloginUser" value="Y">  
  AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
  AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
</logic:equal>
  </logic:notPresent>
 window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  	 window.location = '<html:rewrite page="/loginuserSearchAction.do"/>?method=toSearchRecord';
 }

//����
function saveMethod(){
 if(validateLoginuserForm(document.all.item("loginuserForm"))==true){
	document.loginuserForm.isreturn.value = "N";
    document.loginuserForm.submit();
  }
}

//���淵��
function saveReturnMethod(){
 if(validateLoginuserForm(document.all.item("loginuserForm"))==true){
document.loginuserForm.isreturn.value = "Y";
document.loginuserForm.submit();
 }
}

//���������ֲ�ȡ�������б�
function setStorageidSelect(id){

	var select = $("#"+id).get(0);
	var maintDivision = $("#grcid").val();

	if(maintDivision != null && maintDivision != ""){
		var url = '<html:rewrite page="/loginuserAction.do"/>?method=getStorageidList&maintDivision='+maintDivision;

		var jsonObj = $.ajax({
			url: url,
			async:false			
		});
		
		var storageids = eval(jsonObj.responseText);
		var selectedValue = select.value;

		select.innerHTML = "";
		
		for(var i = 0; i < storageids.length; i++){
			var opt=document.createElement('option');
			opt.value = storageids[i].storageid;
			opt.text = storageids[i].storagename;
			opt.selected = selectedValue == storageids[i].storageid;
			select.add(opt);
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
              <!--
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