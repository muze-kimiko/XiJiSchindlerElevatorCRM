<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic"%>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">
  <%--  �жϵ�λ����ģ��UnitType�Ƿ��п�д��Ȩ��,��property	--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="npulldown" value="Y">
   AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
   AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  <logic:present name="display">
  AddToolBarItemEx("PDFBtn","../../common/images/toolbar/print.gif","","",'����PDF',"85","1","pdfMethod()");
  </logic:present>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  	 window.location = '<html:rewrite page="/PullDownSearchAction.do"/>?method=toSearchRecord';
 }

//����
function saveMethod(){
  if(validatePulldownForm(document.all.item("pulldownForm"))==true){
	  var orderby=document.getElementById("orderby").value;
	  if(orderby.trim()==""){
		  alert("����� ����Ϊ�գ�");
	  }else{
		  if(isNaN(orderby.trim())){
			  alert("����� ����������!");
		  }else{
		 	  document.pulldownForm.isreturn.value = "N";
		   	  document.pulldownForm.submit();
	   	  }
   	  }
  }
}

//���淵��
function saveReturnMethod(){
	 if(validatePulldownForm(document.all.item("pulldownForm"))==true){
		 var orderby=document.getElementById("orderby").value;
		  if(orderby.trim()==""){
			  alert("����� ����Ϊ�գ�");
		  }else{
			  if(isNaN(orderby.trim())){
				  alert("����� ����������!");
			  }else{
			 	  document.pulldownForm.isreturn.value = "Y";
			   	  document.pulldownForm.submit();
		   	  }
	   	  }
 	}
}

//ȥ���ո�
 String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}
 //������Ƿ�Ϊ����
 function f_check_number(){
  	if((event.keyCode>=48 && event.keyCode<=57)){
  	}else{
		event.keyCode=0;
   	}
 }
  //���������Ƿ���������
function checkthisvalue(obj){
	if(isNaN(obj.value)){
		alert("����������!");
		obj.value="0";
		obj.focus();
		return false;
	}
}
  
//pdf����
function pdfMethod(){
 	window.location='<html:rewrite page="/ContractPdfServlet"/>?flag=PullDown&htmlName=PullDown.html';
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