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

  <logic:notPresent name="workisdisplay">
  
		  <logic:notPresent name="isOpen">
		  		<logic:notPresent name="isopenshow">
			  		AddToolBarItemEx("ReturnBtn","../../common/images/toolbar/back.gif","","",'<bean:message key="toolbar.return"/>',"65","0","returnMethod()");
			  	</logic:notPresent>
			  	<logic:present name="isopenshow">
			  	  	AddToolBarItemEx("CloseBtn","../../common/images/toolbar/close.gif","","",'<bean:message key="toolbar.close"/>',"65","0","closeMethod()");
			  	</logic:present>
			  		
			  	<logic:notPresent name="display">  
				  <%-- �Ƿ��п�д��Ȩ��--%>
				  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nlostelevatorreport" value="Y"> 
					    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
					    <logic:notEqual name="lostElevatorReportBean" property="submitType" value="Y">
					    	AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","",'<bean:message key="tollbar.saveandrefer"/>',"120","1","saveReturnMethod()");
					    </logic:notEqual>
				    
					    <logic:present name="isclosework">  
					  		AddToolBarItemEx("DelBtn","../../common/images/toolbar/delete.gif","","",'<bean:message key="toolbar.delete"/>',"65","1","deleteMethod()"); 
					  	</logic:present> 
				  </logic:equal>
			  </logic:notPresent>
			  
		  </logic:notPresent>
		  
		  <logic:present name="isOpen">
		  	AddToolBarItemEx("CloseBtn","../../common/images/toolbar/close.gif","","",'<bean:message key="toolbar.close"/>',"65","0","closeMethod()");
		  </logic:present>
		  
  </logic:notPresent>
  <logic:present name="workisdisplay">
  		AddToolBarItemEx("CloseBtn","../../common/images/toolbar/close.gif","","",'<bean:message key="toolbar.close"/>',"65","0","closeMethod()");
  </logic:present>
  
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}

//Ajax ɾ��
var req;
function deleteMethod(){
	
	if(confirm("ȷ��ɾ���ü�¼���ݣ�")){
		
		 if(window.XMLHttpRequest) {
			 req = new XMLHttpRequest();
		 }else if(window.ActiveXObject) {
			 req = new ActiveXObject("Microsoft.XMLHTTP");
		 }  //����response
		 
		 var deljnlno=document.getElementById("deljnlno").value;
		 var url='<html:rewrite page="/lostElevatorReportAction.do"/>?method=toAjaxDeleteRecord&jnlnostr='+deljnlno;//��ת·��
		 req.open("post",url,false);//true �첽;false ͬ��
		 req.onreadystatechange=function getnextlist(){
			
		 	if(req.readyState==4 && req.status==200){
				 var xmlDOM=req.responseXML;
				 var rows=xmlDOM.getElementsByTagName("rows");
				 if(rows!=null){
				 	//������
						var colNodes = rows[0].childNodes;
						//������
						if(colNodes != null){
							var freeid = colNodes[0].getAttribute("name");
							var freename = colNodes[0].getAttribute("value");
							if(freename=="Y"){
								alert("ɾ�����ݳɹ���\n���ȷ����ť���ر�ҳ�档");
								closeMethod();
							}else{
								alert("ɾ������ʧ�ܣ�");
							}
			            }
				 		
				 }
			}
		 };//�ص�����
		 req.send(null);//������
	}
}

//�ر�
function closeMethod(){
  window.close();
}

//����
function returnMethod(){
  window.location = '<html:rewrite page="/lostElevatorReportSearchAction.do"/>?method=${returnMethod}';
}

//����
function saveMethod(){

  if(checkColumnInput(lostElevatorReportForm)){
    document.lostElevatorReportForm.submitType.value = "N";
    document.lostElevatorReportForm.isreturn.value = "N";
    document.lostElevatorReportForm.submit();
  }
  
}

//���淵��
function saveReturnMethod(){
  inputTextTrim();  
  if(checkColumnInput(lostElevatorReportForm)){
	  document.lostElevatorReportForm.submitType.value = "Y";
      document.lostElevatorReportForm.isreturn.value = "Y";
      document.lostElevatorReportForm.submit();
    } 
}

//�ر�
function closeMethod(){
  window.close();
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