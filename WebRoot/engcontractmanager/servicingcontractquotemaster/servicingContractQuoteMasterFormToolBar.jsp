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
	  <logic:equal name="<%=SysConfig.TOOLBAR_RIGHT%>" property="nwgchangemaster" value="Y">  
		    AddToolBarItemEx("SaveBtn","../../common/images/toolbar/save.gif","","",'<bean:message key="toolbar.save"/>',"65","1","saveMethod()");
		    AddToolBarItemEx("SaveReturnBtn","../../common/images/toolbar/save_back.gif","","","�����ύ������","120","1","saveReturnMethod()");
	    </logic:equal>
  </logic:notPresent>
  window.document.getElementById("toolbar").innerHTML=GenToolBar("TabToolBar_Manage","TextToolBar_Black","Style_Over","Style_Out","Style_Down","Style_Check");
}
//����
function returnMethod(){
  window.location = '<html:rewrite page="/wgchangeSearchAction.do"/>?method=toSearchRecord';
}

//����
function saveMethod(){
	if(checkInput()){
	    var elevatorNos = document.getElementsByName("elevatorNo");
	    var materialName = document.getElementsByName("materialName");
		if(elevatorNos !=null && elevatorNos.length>0){
			if(materialName !=null && materialName.length>0){
			document.WeiGchangeForm.isreturn.value = "N";
  			document.WeiGchangeForm.submit();
				}else{
					alert("���ܱ��棬�����������ϸ��");
				}
		}else{
			alert("���ܱ��棬����ӵ�����Ϣ��");
		}
	}
}

//���� �޸ķ���
function saveReturnMethod(){
	if(checkInput()){
	    var elevatorNos = document.getElementsByName("elevatorNo");
	    var materialName = document.getElementsByName("materialName");
		if(elevatorNos !=null && elevatorNos.length>0){
			if(materialName !=null && materialName.length>0){
			document.WeiGchangeForm.isreturn.value = "Y";
  			document.WeiGchangeForm.submit();
			}else{
				alert("���ܱ��棬�����������ϸ��");
			}
		}else{
			alert("���ܱ��棬����ӵ�����Ϣ��");
		}
	}
}

function fuz(){
	a=document.getElementsByName("xuhao");
	for(i=0;i<a.length;i++){
		a[i].value = i+1;
	}
}      

//��ӵ�����Ϣ
function addElevators(tableId){
		var paramstring = "state=WGBJ&&elevatorNos=";		
		var elevatorNos = document.getElementsByName("elevatorNo");
		for(i=0;i<elevatorNos.length;i++){
			paramstring += i<elevatorNos.length-1 ? "|"+elevatorNos[i].value+"|," : "|"+elevatorNos[i].value+"|"		
		}
		var returnarray = openWindowWithParams("searchElevatorSaleAction","toSearchRecord2",paramstring);

		if(returnarray!=null && returnarray.length>0){
			addRows(tableId,returnarray.length);
			toSetInputValue(returnarray,"WeiGchangeForm");
		}			
}

//����Ԫ��ָ���ĸ��ڵ����
function getFirstSpecificParentNode(parentTagName,childObj){
	var parentObj = childObj.parentNode;
	
	while(parentObj){
		if(parentObj.nodeName.toLowerCase() == parentTagName.toLowerCase()){				
			break;
		}
		parentObj = parentObj.parentNode;
	}
	
	return parentObj;
}


//�ж�ҳ���ֵ
function checkInput(){
	if(checkColumnInput(WeiGchangeForm) && checkRowInput('table_22','tb_head') && checkRowInput('table_1','tb_head')){
		return true;
	}else{
		return false;
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