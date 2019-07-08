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

  AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return" />',"65","0","returnMethod()");
  <logic:notPresent name="display">  
  <%-- �Ƿ��п�д��Ȩ��--%>
  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nMaintainProjectInfo" value="Y"> 
    AddToolBarItemEx("ImportBtn2","../../common/images/toolbar/save.gif","","",'����EXCEL',"85","1","checkMethod()");
    AddToolBarItemEx("DownloadBtn","../../common/images/toolbar/download_theme.gif","","",'����ģ��',"80","1","downLoadMethod()");
  </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����

function returnMethod(){
  window.location = '<html:rewrite  page="/MaintainProjectInfoSearchAction.do"/>?method=toSearchRecord';
}

function checkMethod(){
var elevatorType=document.getElementById("elevatorType").value;
var maintType=document.getElementById("maintType").value;
  var fileName = document.getElementById("file").value;
  if(elevatorType!=""&&maintType!=""){
  if(fileName==""){
    alert("��ѡ��EXCEL2007�����ϰ汾�ļ���");
  }else{
    var filestr=fileName.substring(fileName.lastIndexOf("\.")+1,fileName.length).toLowerCase();
    if(filestr!="xlsx"){
      alert("��ѡ��EXCEL2007�����ϰ汾�ļ���");
    }else{
      if(confirm("������ά����Ŀ��ά������Ҫ����ͬ�����ݣ����ݽ����滻��ȷ���ϴ���")){
    	  document.getElementById("ReturnBtn").disabled=true;
    	  document.getElementById("ImportBtn2").disabled=true;
    	  document.getElementById("DownloadBtn").disabled=true;
          document.MaintainProjectInfoForm.submit();
      	}      
   	 }
  	} 
  }else{
  alert("��ѡ��������ͺͱ�������")
  }
}

//����ģ��
function downLoadMethod(){
   window.open("<html:rewrite  page='/uploadFile/Baoyangjiludan.xlsx'/>"); 
}

</script>

  <tr>
    <td width="100%">
      <table width="100%" border="0" cellspacing="0" cellpadding="0" >
        <tr>
          <td height="22" class="table_outline3" valign="bottom" width="100%" >
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