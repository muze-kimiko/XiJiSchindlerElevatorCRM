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
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="ntechnologysupportmaintminister" value="Y"> 
   // AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="toolbar.returnsave"/>',"80","1","saveReturnMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//ȥ���ո�
String.prototype.trim = function(){return this.replace(/(^\s*)|(\s*$)/g,"");}

//����
function returnMethod(){
  window.location = '<html:rewrite page="/technologySupportMaintMinisterSearchAction.do"/>?method=toSearchRecord';
}

 //����
function saveMethod(){
	var msir=document.getElementsByName("mmisResolve");
	if(msir[0].value!=null&&msir[0].value!=""){ 
      document.technologySupportMaintMinisterForm.isreturn.value = "N";
      document.technologySupportMaintMinisterForm.submit();
     }else
    {
    	alert("�Ƿ�������Ϊ��");
    } 
}

//���淵��
function saveReturnMethod(){
	 var msir=document.getElementsByName("mmisResolve");
	if(msir[0].value!=null&&msir[0].value!=""){
		var msprocessRem=document.getElementById("mmprocessRem").value;
		if(msprocessRem.trim()==""){
			alert("������� ���");
			return;
		}
	     document.technologySupportMaintMinisterForm.isreturn.value = "Y";
	     document.technologySupportMaintMinisterForm.submit();
	}else
    {
    	alert("��ѡ�� �Ƿ�����");
    }
}

//����
function downloadFile(id){
	var uri = '<html:rewrite page="/technologySupportMaintMinisterAction.do"/>?method=toDownLoadFiles';
		uri +='&filesid='+ id;
		uri +='&folder=TechnologySupport.file.upload.folder';
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