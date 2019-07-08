<%@ page contentType="text/html;charset=GBK" %>
<%@ taglib uri="/WEB-INF/tld/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/tld/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/tld/struts-logic.tld" prefix="logic" %>
<%@ page import="com.gzunicorn.common.util.SysConfig" %>
<script language="javascript" src="<html:rewrite page="/common/javascript/checkinput.js"/>"></script>

<script language="javascript">

//����ToolBar
function CreateToolBar(){
  SetToolBarHandle(true);
  SetToolBarHeight(20);

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nadvisorycomplaintsdispatch" value="Y"> 
    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
     AddToolBarItemEx("RejectBtn","../../common/images/toolbar/delete.gif","","",'����',"65","1","rejectMethod()");
    //AddToolBarItemEx("SaveSubmitBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="tollbar.saveandrefer"/>',"120","1","saveSubmitMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/advisoryComplaintsManageSearchAction.do"/>?method=toSearchRecordDispatch';
}

//����
function saveMethod(){
	if(checkColumnInput(advisoryComplaintsManageForm)){
	  document.advisoryComplaintsManageForm.isreturn.value = "Y";
	  document.advisoryComplaintsManageForm.dispatchType.value="Y";
	  document.advisoryComplaintsManageForm.submit();
  }
}

//����
function rejectMethod(){
	document.advisoryComplaintsManageForm.isreturn.value = "Y";
	document.advisoryComplaintsManageForm.submitType.value="R";
	document.advisoryComplaintsManageForm.dispatchType.value="N";
	document.advisoryComplaintsManageForm.submit();
}
/* //�����ύ
function saveSubmitMethod(){
	if(checkColumnInput(advisoryComplaintsManageForm)){
		if(confirm("�Ƿ�ȷ���ύ��Ϣ���ύ�󽫲��ܶԸ���Ϣ���д���")){
		  document.advisoryComplaintsManageForm.isreturn.value = "Y";
		  document.advisoryComplaintsManageForm.submitType.value="Y";
		  document.advisoryComplaintsManageForm.submit();
		}
  }
} */


/*
���������*�ű�ǵ��ı�������ֵ�Ƿ�Ϊ�ղ���ʾ�����������¸�ʽ
����: <td>����</td><td><input type="text"/>*</td> 
            ����ֵʱ����   ����������Ϊ�ա�
���� element ����div��table��form����
*/
function checkColumnInput(element){
	  var inputs = element.getElementsByTagName("input");
	  var selects = element.getElementsByTagName("select");
	  var msg = "";
	  
	  for(var i=0;i<inputs.length;i++){
	    if(inputs[i].type == "text"
	      && inputs[i].parentNode.innerHTML.indexOf("*")>=0 
	      && inputs[i].value.trim() == ""){
	      
	        msg += inputs[i].parentNode.previousSibling.innerHTML + "����Ϊ��\n";                       
	    }
	  }
	  
	  for(var i=0;i<selects.length;i++){
		  if(selects[i].value.trim()=="" && selects[i].parentNode.innerHTML.indexOf("*")>0){
				  msg+="��ѡ��"+selects[i].parentNode.previousSibling.innerHTML.replace(/:$/gi,"")+"\n";
			  
		  }
	  }
	  if(msg != ""){
		    alert(msg);
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